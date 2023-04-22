/*********************************************************************
 * Copyright (c) 2004, 2007 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/

package org.eclipse.osee.framework.ui.skynet.render;

import static org.eclipse.osee.framework.core.enums.PresentationType.PREVIEW;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.data.BranchToken;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.core.enums.PresentationType;
import org.eclipse.osee.framework.core.operation.IOperation;
import org.eclipse.osee.framework.core.publishing.RendererMap;
import org.eclipse.osee.framework.core.publishing.RendererOption;
import org.eclipse.osee.framework.core.publishing.RendererUtil;
import org.eclipse.osee.framework.jdk.core.type.OseeCoreException;
import org.eclipse.osee.framework.jdk.core.util.Message;
import org.eclipse.osee.framework.jdk.core.util.Strings;
import org.eclipse.osee.framework.plugin.core.util.AIFile;
import org.eclipse.osee.framework.skynet.core.artifact.Artifact;
import org.eclipse.swt.program.Program;

/**
 * @author Ryan D. Brooks
 * @author Jeff C. Phillips
 * @author Loren K. Ashley
 */

public abstract class FileSystemRenderer extends DefaultArtifactRenderer {

   private static final ArtifactFileMonitor monitor = new ArtifactFileMonitor();

   /**
    * If a renderer sub-thread's outer exception handler catches an exception, is is saved in this class global.
    */

   private Throwable rendererException;

   /**
    * If a renderer sub-thread fails to close the {@link PipedOutputStream} in the threads outer exception handler, the
    * pipe closing exception is saved in this class global.
    */

   private Throwable rendererPipeCloseException;

   /**
    * Saves a handle to the renderer sub-thread. A sub-thread is only used when the renderer has set the option
    * {@link RendererOption#CLIENT_RENDERER_CAN_STREAM} to <code>true</code>.
    */

   private Thread rendererThread;

   public FileSystemRenderer() {
      super();
   }

   public FileSystemRenderer(RendererMap rendererOptions) {
      super(rendererOptions);
      this.rendererThread = null;
      this.rendererException = null;
      this.rendererPipeCloseException = null;
   }

   /**
    * Checks for exceptions from a renderer sub-thread. The following checks are done:
    * <dl>
    * <dt>{@link #rendererException} and {@link #rendererPipeCloseException} are both non-<code>null</code>:</dt>
    * <dd>A new {@link OseeCoreException} wrapping the {@link #rendererException} and describing both exceptions is
    * thrown.</dd>
    * <dt>Just {@link #rendererException} is non-<code>null</code>:</dt>
    * <dd>If the {@link #rendererException} is a {@link RuntimeException} it is rethrown; otherwise, it is wrapped in an
    * {@link OseeCoreException} and thrown.</dd>
    * <dt>The renderer sub-thread is still alive:</dt>
    * <dd>An {@link OseeCoreException} is thrown describing the unexpectedly still living thread.</dd>
    * </dl>
    *
    * @throws OseeCoreException to wrap exceptions thrown from the sub-thread or when the sub-thread is unexpectedly
    * still alive.
    * @throws RuntimeException when the sub-thread threw an uncaught {@link RuntimeException}.
    */

   void checkRenderThreadExceptions() {

      if (Objects.nonNull(this.rendererException) && Objects.nonNull(this.rendererPipeCloseException)) {
         //@formatter:off
         throw
            new OseeCoreException
                   (
                      new Message()
                             .title( "FileSystemRender, Render sub-thread threw an exception and failed to close the pipe." )
                             .indentInc()
                             .segment( "Render", this.getName() )
                             .title( "Render Exception" )
                             .reasonFollows( this.rendererException )
                             .title( "Pipe Closing Execption" )
                             .reasonFollows( this.rendererPipeCloseException )
                             .toString(),
                             this.rendererException
                   );
         //@formatter:on
      }

      if (Objects.nonNull(this.rendererException)) {

         if (this.rendererException instanceof RuntimeException) {
            throw (RuntimeException) this.rendererException;
         }

         //@formatter:off
         throw
            new OseeCoreException
                   (
                      new Message()
                             .title( "FileSystemRender, Render sub-thread threw an uncought checked exception." )
                             .indentInc()
                             .segment( "Render", this.getName() )
                             .title( "Render Exception" )
                             .reasonFollows( this.rendererException )
                             .toString()
                   );
         //@formatter:on
      }

      try {
         this.rendererThread.join(1000);
      } catch (Exception e) {
         //skip
      }

      if (this.rendererThread.isAlive()) {
         //@formatter:off
         throw
            new OseeCoreException
                   (
                      new Message()
                         .title( "FileSystemRender, Render sub-thread is unexpectedly still alive." )
                         .indentInc()
                         .segment( "Render",    this.getName()              )
                         .segment( "Thread Id", this.rendererThread.getId() )
                         .toString()
                   );
         //@formatter:on
      }

      this.rendererException = null;
      this.rendererPipeCloseException = null;
      this.rendererThread = null;
   }

   public IFile copyToNewFile(Artifact artifact, PresentationType presentationType, IFile file) {

      Objects.requireNonNull(presentationType,
         "FileSystemRender::renderToFile, parameter \"presentationType\" cannot be null.");

      Objects.requireNonNull(presentationType, "FileSystemRender::renderToFile, parameter \"file\" cannot be null.");

      /*
       * Make the artifact into a list
       */

      //@formatter:off
      var artifacts = Objects.nonNull(artifact)
                         ? Collections.<Artifact> singletonList( artifact )
                         : Collections.<Artifact> emptyList();
      //@formatter:on

      /*
       * Get the branch token
       */

      var branchToken = Objects.nonNull(artifact) ? artifact.getBranchToken() : BranchToken.SENTINEL;

      /*
       * Get the input stream
       */

      InputStream inputStream;
      try {
         inputStream = file.getContents();
      } catch (CoreException ex) {
         throw new OseeCoreException("There was an issue copying the file given, creating a new version");
      }

      //@formatter:off
      return
         this.renderToFileInternal
            (
               artifacts,
               branchToken,
               presentationType,
               null,
               inputStream
            );
      //@formatter:on
   }

   /**
    * For the specified {@link Artifact}, gets the file system extension for the type of file that would likely be used
    * for the type of data stored in the {@link Artifact} main content {@link Attribute}.
    *
    * @implSpec When the {@link Artifact} is <code>null</code> or empty, the implementation must return the default file
    * system extension.
    * @param artifact the {@link Artifact} to analyze.
    * @return the file system extension.
    */

   public String getAssociatedExtension(Artifact artifact) {

      var defaultAssociatedExtension = this.getDefaultAssociatedExtension();

      try {
         //@formatter:off
         return
            Objects.nonNull( artifact )
               ? artifact.getSoleAttributeValue(CoreAttributeTypes.Extension, defaultAssociatedExtension )
               : defaultAssociatedExtension;
         //@formatter:on
      } catch (Exception e) {
         return defaultAssociatedExtension;
      }
   }

   /**
    * For the specified {@link List} of {@link Artifact} objects, gets the file system extension for the type of file
    * that would likely be used for the type of data stored in the {@link Artifact} main content {@link Attribute} of
    * the first {@link Artifact} on the {@link List}.
    *
    * @implSPec When the {@link List} is <code>null</code> or empty, the implementation must return the default file
    * system extension.
    * @param artifact the {@link Artifact} to analyze.
    * @return the file system extension.
    */

   public String getAssociatedExtension(List<Artifact> artifacts) {
      //@formatter:off
      return
         ( Objects.nonNull( artifacts ) && !artifacts.isEmpty() )
            ? this.getAssociatedExtension( artifacts.get( 0 ) )
            : this.getDefaultAssociatedExtension();
       //@formatter:on
   }

   public abstract Program getAssociatedProgram(Artifact artifact);

   /**
    * Gets the file system extension for the type of file that would likely be used for the type of data stored in the
    * {@link Artifact} main content {@link Attribute} for the most common type of {@link Artifact} processed by the
    * {@link IRenderer} implementation.
    *
    * @implSpec Implementations must always return a non-<code>null</code> {@link String}.
    * @implSpec Implementations must not throw exceptions.
    * @return the default file system extension.
    */

   public abstract String getDefaultAssociatedExtension();

   public abstract InputStream getRenderInputStream(PresentationType presentationType, List<Artifact> artifacts);

   protected abstract IOperation getUpdateOperation(File file, List<Artifact> artifacts, BranchId branch, PresentationType presentationType);

   /**
    * {@inheritDoc}
    * <p>
    *
    * @implNote When the {@link List} of {@link Artifact} is <code>null</code> or empty, processing still needs to
    * occur. The {@link MSWordTemplateClientRenderer} needs to produce an empty Word document for artifact comparisons
    * where there is a base artifact but not a compare to artifact.
    */

   @Override
   public void open(List<Artifact> artifacts, PresentationType presentationType) {

      Objects.requireNonNull(presentationType,
         "FileSystemRender::open, parameter \"presentationType\" cannot be null.");

      if (presentationType.matches(PresentationType.DEFAULT_OPEN)) {
         presentationType = PREVIEW;
      }

      /*
       * Get the render output and write the content file to the workspace
       */

      var contentFile = this.renderToFile(artifacts, presentationType, null);

      if (Objects.isNull(contentFile)) {
         //@formatter:off
         throw
            new OseeCoreException
                   (
                      "FileSystemRenderer::open, Renderer \"renderToFile\" returned null."
                   );
         //@formatter:on
      }

      /*
       * If there were no artifacts, do not display the empty content to the user.
       */

      if (Objects.isNull(artifacts) || artifacts.isEmpty()) {
         return;
      }

      /*
       * Display renderer content to the user.
       */

      var firstArtifact = artifacts.iterator().next();
      var branchToken = firstArtifact.getBranchToken();
      var program = this.getAssociatedProgram(firstArtifact);

      try {

         RenderingUtil.displayDocument(presentationType, program, contentFile);

      } catch (Exception e) {

         //@formatter:off
         var message =
            RenderingUtil.displayErrorDocument
            (
               this,
               presentationType,
               branchToken,
               artifacts,
               e.getMessage()
            );
         //@formatter:on

         throw new OseeCoreException(message, e);
      }
   }

   /**
    * Saves the renderer's output to the content file and setups file monitoring. The <code>artifacts</code> are
    * verified to all be from the same branch. The abstract method {@link #getRenderInputStream} is called to get the
    * {@link IRenderer} implementation's output. The workspace sub-directories are created as necessary. When the
    * {@link PresentationType} is {@link PresentationType#PREVIEW} the file monitoring is set for read only; otherwise,
    * the file is monitored for edit.
    * <p>
    * When the renderer option {@link RendererOption#CLIENT_RENDERER_CAN_STREAM} is set to <code>true</code>, the call
    * back to the abstract method {@link #getRenderInputStream} is run in a sub-thread connected with a
    * {@link PipedOutputStream} and {@link PipedInputStream} pair.
    *
    * @implNode This is a provided utility method to classes extending this class. It is generally called from overrides
    * of the {@link #open} method.
    * @param artifacts the artifacts that were rendered.
    * @param presentationType the type of presentation being made.
    * @return a {@link IFile} handle to the workspace file that was written with the rendering content or
    * <code>null</code> when the input parameters are <code>null</code> or empty.
    * @throws IllegalArgumentException when the provided artifacts are not from the same branch.
    */

   public IFile renderToFile(List<Artifact> artifacts, PresentationType presentationType, String pathPrefix) {

      Objects.requireNonNull(presentationType,
         "FileSystemRender::renderToFile, parameter \"presentationType\" cannot be null.");

      /*
       * Get the branch token and ensure all artifacts are from the same branch. artifactsNoNull will be an empty list
       * if the input artifact list was null, empty, or only has null entries.
       */

      List<Artifact> artifactsNoNulls = new ArrayList<>(artifacts.size());

      var branchTokens = RenderingUtil.getBranchTokens(artifacts, artifactsNoNulls);

      //@formatter:off
      if( branchTokens.size() > 1 ) {
         throw
            new IllegalArgumentException
                   (
                      new Message()
                             .title( "FileSystemRenderer::renderToFile, All of the artifacts must be on the same branch for mass editing." )
                             .indentInc()
                             .segment    ( "PresentationType", presentationType.name() )
                             .segmentSet ( "Branches Found",   branchTokens, BranchToken::getShortName )
                             .toString()
                   );
      }
      //@formatter:on

      var branchTokenIterator = branchTokens.iterator();
      var branchToken = branchTokenIterator.hasNext() ? branchTokenIterator.next() : BranchToken.SENTINEL;

      var subdirectoryPath = Strings.isValidAndNonBlank(pathPrefix) ? pathPrefix : branchToken.getShortName();

      /*
       * Get the Renderer's output
       */

      InputStream inputStream;

      try {

         if ((Boolean) this.getRendererOptionValue(RendererOption.CLIENT_RENDERER_CAN_STREAM)) {

            /*
             * It's a client side renderer that supports streaming (writing to a PipedOutputStream).
             */

            var pipedInputStream = new PipedInputStream();
            var pipedOutputStream = new PipedOutputStream(pipedInputStream);
            this.setRendererOption(RendererOption.OUTPUT_STREAM, pipedOutputStream);
            var renderer = this;

            /*
             * Create a sub-thread for the client streaming render. Running in a separate thread the renderer will write
             * its output to the output stream provided to it via the renderer options. The input stream is attached to
             * the output stream before the renderer is started.
             */

            this.rendererThread = new Thread("Renderer") {

               @Override
               public void run() {

                  try {

                     renderer.getRenderInputStream(presentationType, artifacts);

                  } catch (Exception rendererException) {

                     /*
                      * The renderer threw an exception. Shut down the piped streams.
                      */

                     try {

                        pipedOutputStream.close();

                     } catch (IOException pipeCloseException) {

                        renderer.rendererPipeCloseException = pipeCloseException;
                     }

                     /*
                      * Save the exception.
                      */

                     renderer.rendererException = rendererException;
                  }

               }
            };

            this.rendererException = null;
            this.rendererPipeCloseException = null;
            rendererThread.start();

            inputStream = pipedInputStream;

         } else {

            /*
             * It's a server side render that provides the input stream from the REST API to read from. Or, it's a
             * client side render that wrote to a buffer and the provided an input stream that reads from the buffer.
             */

            inputStream = this.getRenderInputStream(presentationType, artifacts); /* abstract */

         }
      } catch (Exception e) {
         //@formatter:off
         throw
            new OseeCoreException
                   (
                      new Message()
                             .title( "FileSystemRenderer::renderToFile, Failed to get renderer input stream." )
                             .indentInc()
                             .segment( "Renderer", this.getName() )
                             .reasonFollowsWithTrace( e )
                             .toString(),
                      e
                   );
         //@formatter:on
      }

      /*
       * Save renderer output to the content file and setup file monitoring
       */

      return renderToFileInternal(artifactsNoNulls, branchToken, presentationType, subdirectoryPath, inputStream);
   }

   /**
    * Writes the Renderer's output to a content file and sets up file monitoring.
    *
    * @param artifacts the artifacts rendered. Used to determine the file extension, generate the filename, and for the
    * update monitor to save a modified content file back to the artifacts.
    * @param branchToken the branch the artifacts came from. Used to generate the filename and passed to the update file
    * monitor.
    * @param presentationType the type of presentation. Used for to located the workspace presentation folder, filename
    * generation, and to determine the type of file monitoring.
    * @param pathPrefix split with both UNIX and Windows path separators; and each segment is used as sub-folder under
    * the workspace presentation folder.
    * @param renderInputStream the output from the {@link IRenderer} implementation.
    * @return an {@link IFile} handle to the file the render's output was written to.
    */

   private IFile renderToFileInternal(List<Artifact> artifacts, BranchToken branchToken, PresentationType presentationType, String pathPrefix, InputStream renderInputStream) {

      /*
       * Get a handle to the content file. This creates all the sub-directories in the user workspace to where the
       * content file is to be. It does not create or check for the existance of the content file.
       */

      //@formatter:off
      var contentFile =
         RenderingUtil
            .getRenderFile
               (
                  this,                                             /* For call back to set output file path              */
                  presentationType,                                 /* Locates workspace presentation folder              */
                  RendererUtil.makeRenderPath(pathPrefix),          /* Sub-folder under the workspace presentation folder */
                  this.getAssociatedExtension(artifacts),           /* File extension for content file                    */
                  RenderingUtil.getFileNameSegmentsFromArtifacts    /* Generates content filename segments                */
                     (
                        presentationType,
                        branchToken.getShortName(),
                        artifacts
                     )
               )
            .orElseThrow
               (
                  () -> new OseeCoreException
                               (
                                 new Message()
                                        .title( "FileSystemRenderer::renderToFileInternal, Failed to locate render file for display." )
                                        .indentInc()
                                        .segment( "Renderer",             this.getName()                       )
                                        .segment( "Presentation Type",    presentationType.name( )             )
                                        .segment( "Branch Token",         branchToken                          )
                                        .segmentIndexedList( "Artifacts", artifacts, Artifact::getIdString, 20 )
                                        .toString()
                               )
               );
      //@formatter:on

      /*
       * Write the Renderer's output to the content file.
       */

      AIFile.writeToFile(contentFile, renderInputStream);

      /*
       * If the render was running in a sub-thread, it should be complete when the file write completes.
       */

      if ((Boolean) this.getRendererOptionValue(RendererOption.CLIENT_RENDERER_CAN_STREAM)) {
         this.checkRenderThreadExceptions();
      }

      if (presentationType.matches(PresentationType.PREVIEW, PresentationType.PREVIEW_SERVER)) {

         monitor.markAsReadOnly(contentFile);

         return contentFile;
      }

      if (presentationType.matches(PresentationType.SPECIALIZED_EDIT)) {

         var location = contentFile.getLocation();

         if (Objects.nonNull(location)) {

            var file = location.toFile();

            if (Objects.nonNull(file)) {

               //@formatter:off
               monitor.addFile
                  (
                     file,
                     this.getUpdateOperation( file, artifacts, branchToken, presentationType ) /* abstract */
                  );
               //@formatter:on
            }
         }

      }

      return contentFile;
   }

}
