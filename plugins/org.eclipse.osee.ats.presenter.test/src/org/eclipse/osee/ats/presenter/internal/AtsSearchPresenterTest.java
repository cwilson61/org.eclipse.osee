/*******************************************************************************
 * Copyright (c) 2011 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.eclipse.osee.ats.presenter.internal;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.eclipse.osee.ats.api.components.AtsSearchHeaderComponent;
import org.eclipse.osee.ats.api.data.AtsSearchParameters;
import org.eclipse.osee.ats.mocks.MockAtsArtifactProvider;
import org.eclipse.osee.ats.mocks.MockAtsSearchHeaderComponent;
import org.eclipse.osee.display.api.data.ViewId;
import org.eclipse.osee.display.presenter.mocks.MockSearchNavigator;
import org.eclipse.osee.display.presenter.mocks.MockSearchResultsListComponent;
import org.eclipse.osee.framework.core.enums.CoreAttributeTypes;
import org.eclipse.osee.framework.jdk.core.util.GUID;
import org.eclipse.osee.orcs.data.ReadableArtifact;
import org.eclipse.osee.orcs.data.ReadableAttribute;
import org.eclipse.osee.orcs.mock.MockArtifact;
import org.eclipse.osee.orcs.mock.MockAttribute;
import org.eclipse.osee.orcs.mock.MockMatch;
import org.eclipse.osee.orcs.search.Match;
import org.junit.Test;

/**
 * @author John Misinco
 */
public class AtsSearchPresenterTest {

   @Test
   public void testSelectProgram() {
      MockAtsArtifactProvider provider = new MockAtsArtifactProvider();
      AtsSearchPresenterImpl<AtsSearchHeaderComponent> presenter =
         new AtsSearchPresenterImpl<AtsSearchHeaderComponent>(provider);
      MockAtsSearchHeaderComponent comp = new MockAtsSearchHeaderComponent();
      ViewId program = new ViewId("prg1Guid_18H74Zqo3gA", "program1");
      presenter.selectProgram(program, comp);
      Assert.assertEquals(2, comp.getBuilds().size());
   }

   @Test
   public void testSelectSearch() {
      AtsSearchPresenterImpl<AtsSearchHeaderComponent> presenter =
         new AtsSearchPresenterImpl<AtsSearchHeaderComponent>(null);
      MockSearchNavigator navigator = new MockSearchNavigator();
      String programGuid = GUID.create();
      String buildGuid = GUID.create();
      ViewId program = new ViewId(programGuid, "prgName");
      ViewId build = new ViewId(buildGuid, "bldName");
      AtsSearchParameters params = new AtsSearchParameters("test search phrase", true, false, build, program);
      presenter.selectSearch(params, navigator);
      String url = navigator.getResultsUrl();
      String expected =
         "/program=" + programGuid + "&build=" + buildGuid + "&nameOnly=true&search=test%20search%20phrase&verbose=false";
      Assert.assertEquals(expected, url);
   }

   @Test
   public void testInitSearchResults() {
      MockAtsArtifactProvider provider = new MockAtsArtifactProvider();
      List<Match<ReadableArtifact, ReadableAttribute<?>>> resultList =
         new ArrayList<Match<ReadableArtifact, ReadableAttribute<?>>>();
      MockArtifact art = new MockArtifact("guid1", "matchArt");
      MockAttribute attr = new MockAttribute(CoreAttributeTypes.Name, "matchArt");
      Match match = new MockMatch(art, attr);
      resultList.add(match);
      provider.setResultList(resultList);
      AtsSearchPresenterImpl<AtsSearchHeaderComponent> presenter =
         new AtsSearchPresenterImpl<AtsSearchHeaderComponent>(provider);
      MockAtsSearchHeaderComponent headerComp = new MockAtsSearchHeaderComponent();
      MockSearchResultsListComponent resultsComponent = new MockSearchResultsListComponent();
      presenter.initSearchResults(null, headerComp, resultsComponent);
      Assert.assertEquals(3, headerComp.getPrograms().size());

      String programGuid = GUID.create();
      String buildGuid = GUID.create();
      String url =
         "/program=" + programGuid + "&build=" + buildGuid + "&nameOnly=true&search=test%20search%20phrase&verbose=false";
      presenter.initSearchResults(url, headerComp, resultsComponent);
      Assert.assertEquals(1, resultsComponent.getSearchResults().size());
   }
}
