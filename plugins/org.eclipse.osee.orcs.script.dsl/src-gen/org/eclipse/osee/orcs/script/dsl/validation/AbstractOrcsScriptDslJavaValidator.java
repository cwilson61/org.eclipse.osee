/*
* generated by Xtext
*/
package org.eclipse.osee.orcs.script.dsl.validation;
 
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;

public class AbstractOrcsScriptDslJavaValidator extends org.eclipse.xtext.validation.AbstractDeclarativeValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<>();
	    result.add(org.eclipse.osee.orcs.script.dsl.orcsScriptDsl.OrcsScriptDslPackage.eINSTANCE);
		return result;
	}

}
