/*********************************************************************
 * Copyright (c) 2023 Boeing
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

package org.eclipse.osee.framework.core.xml.publishing;

import java.util.Optional;

/**
 * Class to encapsulate a list of {@link WordFieldCharacter} elements.
 *
 * @author Loren K. Ashley
 */

public class WordFieldCharacterList<P extends AbstractElement> extends AbstractElementList<P, WordFieldCharacter> {

   /**
    * {@link WordElementParserFactory} instance for creating a {@link WordFieldCharacterList} with a {@link WordRun}
    * parent.
    */

   public static WordElementParserFactory<WordRun, WordFieldCharacterList<WordRun>, WordFieldCharacter> wordRunParentFactory =
      new WordElementParserFactory<>(WordFieldCharacterList::new, WordFieldCharacter::new, WordMlTag.FIELD_CHARACTER);

   /**
    * Creates a new open and empty list for {@link WordFieldCharacter}s.
    *
    * @param wordRun the {@link WordRun} that contains this {@link WordFieldCharacterList}.
    * @throws NullPointerException when the parameter <code>wordRun</code> is <code>null</code>.
    */

   public WordFieldCharacterList(P parent) {
      super(parent);
   }

   /**
    * Gets the containing (parent) {@link WordRun}.
    *
    * @return the containing {@link WordRun}.
    */

   public Optional<WordRun> getWordRun() {
      var parent = this.getParent();
      //@formatter:off
      return
         (parent instanceof WordRun)
            ? Optional.of((WordRun) parent)
            : Optional.empty();
      //@formatter:on
   }

}

/* EOF */
