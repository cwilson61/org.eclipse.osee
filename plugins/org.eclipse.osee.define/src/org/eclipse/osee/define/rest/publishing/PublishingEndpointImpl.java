/*********************************************************************
 * Copyright (c) 2022 Boeing
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

package org.eclipse.osee.define.rest.publishing;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import org.eclipse.osee.define.api.DefineOperations;
import org.eclipse.osee.define.api.MsWordPreviewRequestData;
import org.eclipse.osee.define.api.WordTemplateContentData;
import org.eclipse.osee.define.api.WordUpdateChange;
import org.eclipse.osee.define.api.WordUpdateData;
import org.eclipse.osee.define.api.publishing.PublishingEndpoint;
import org.eclipse.osee.define.operations.publishing.PublishingPermissions;
import org.eclipse.osee.framework.core.data.ArtifactId;
import org.eclipse.osee.framework.core.data.ArtifactToken;
import org.eclipse.osee.framework.core.data.ArtifactTypeToken;
import org.eclipse.osee.framework.core.data.AttributeTypeToken;
import org.eclipse.osee.framework.core.data.BranchId;
import org.eclipse.osee.framework.core.enums.CoreUserGroups;
import org.eclipse.osee.framework.core.exception.OseeAccessDeniedException;
import org.eclipse.osee.framework.core.exception.OseeNotFoundException;
import org.eclipse.osee.framework.jdk.core.type.Pair;

/**
 * Provides the wrapper methods that expose the Publishing operations methods as REST API end points.
 *
 * @author Loren K. Ashley
 */

public class PublishingEndpointImpl implements PublishingEndpoint {

   /**
    * Verifies the threads current user is authorized to access the REST API for end points requiring Publishing Group
    * membership.
    *
    * @implNote Catches the OSEE exception and wraps it into a {@link javax.ws.rs} exception.
    * @throws NotAuthorizedException when the user is not a member of the {@link CoreUserGroups#OseeAccessAdmin}.
    */

   private static void verifyAccess() {
      try {
         PublishingPermissions.verify();
      } catch (OseeAccessDeniedException e) {
         throw new NotAuthorizedException(e.getMessage(), Response.status(Response.Status.UNAUTHORIZED).build());
      }
   }

   /**
    * Verifies the threads current user is authorized to access the REST API for end points that do not require
    * membership in the Publishing Group.
    *
    * @implNote Catches the OSEE exception and wraps it into a {@link javax.ws.rs} exception.
    * @throws NotAuthorizedException when the user is not a member of the {@link CoreUserGroups#OseeAccessAdmin}.
    */

   private static void verifyAccessNonGroup() {
      try {
         PublishingPermissions.verifyNonGroup();
      } catch (OseeAccessDeniedException e) {
         throw new NotAuthorizedException(e.getMessage(), Response.status(Response.Status.UNAUTHORIZED).build());
      }
   }

   /**
    * Saves a handle to the Define Service Publishing operations implementation.
    */

   private final DefineOperations defineOperations;

   /**
    * Creates a new REST API end point implementation for Publishing.
    *
    * @param defineOperations a handle to the Define Service Publishing operations.
    * @throws NullPointerException when the parameter <code>defineOperations</code> is <code>null</code>.
    */

   public PublishingEndpointImpl(DefineOperations defineOperations) {
      this.defineOperations = Objects.requireNonNull(defineOperations,
         "PublishingEndpointImpl::new, parameter \"defineOperations\" cannot be null.");
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user that is a member of the publishing group.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws NotFoundException when shared publishing artifacts were not found for the specified parameters.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public List<ArtifactToken> getSharedPublishingArtifacts(BranchId branch, ArtifactId view, ArtifactId sharedFolder, ArtifactTypeToken artifactType, AttributeTypeToken attributeType, String attributeValue) {

      PublishingEndpointImpl.verifyAccess();

      try {
         return this.defineOperations.getPublishingOperations().getSharedPublishingArtifacts(branch, view, sharedFolder,
            artifactType, attributeType, attributeValue);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (OseeNotFoundException onfe) {
         throw new NotFoundException(onfe.getMessage(), Response.status(Response.Status.NOT_FOUND).build(), onfe);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user that is a member of the publishing group.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public InputStream msWordPreview(BranchId branch, ArtifactId template, ArtifactId headArtifact, ArtifactId view) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().msWordPreview(branch, template, headArtifact, view);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user that is a member of the publishing group.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public InputStream msWordPreview(BranchId branch, ArtifactId template, List<ArtifactId> artifacts, ArtifactId view) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().msWordPreview(branch, template, artifacts, view);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public InputStream msWordPreview(MsWordPreviewRequestData msWordPreviewRequestData) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().msWordPreview(msWordPreviewRequestData);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public InputStream msWordTemplatePublish(BranchId branch, ArtifactId template, ArtifactId headArtifact, ArtifactId view) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().msWordTemplatePublish(branch, template, headArtifact,
            view);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public Pair<String, Set<String>> renderWordTemplateContent(WordTemplateContentData wordTemplateContentData) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().renderWordTemplateContent(wordTemplateContentData);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

   /**
    * {@inheritDoc}
    *
    * @throws NotAuthorizedException when the user is not an active login user.
    * @throws BadRequestException when the operation's method indicates any arguments were illegal.
    * @throws ServerErrorException when an unaccounted for exception is thrown by the operations method.
    */

   @Override
   public WordUpdateChange updateWordArtifacts(WordUpdateData wordUpdateData) {

      PublishingEndpointImpl.verifyAccessNonGroup();

      try {
         return this.defineOperations.getPublishingOperations().updateWordArtifacts(wordUpdateData);
      } catch (IllegalArgumentException iae) {
         throw new BadRequestException(iae.getMessage(), Response.status(Response.Status.BAD_REQUEST).build(), iae);
      } catch (Exception e) {
         throw new ServerErrorException(e.getMessage(), Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(),
            e);
      }
   }

}

/* EOF */