/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

// The inputs from the search page grouped in one place
public record ModelQuerySpec(
	    String searchString,
	    boolean checkInstanceName,
	    boolean checkPinName,
	    boolean checkType,
	    boolean checkComments,
	    boolean checkCaseSensitive,
	    boolean checkExactMatching,
	    boolean checkInterfaceValues,
	    SearchScope scope,
	    IResource source,
		EObject referenceObject
) {

	public enum SearchScope {
        WORKSPACE, PROJECT, FILE
    }
   
}
