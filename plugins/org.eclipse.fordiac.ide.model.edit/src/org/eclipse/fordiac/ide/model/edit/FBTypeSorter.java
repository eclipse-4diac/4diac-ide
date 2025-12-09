/********************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.edit;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class FBTypeSorter extends ViewerComparator {

	private static final Map<EClass, Integer> PRIORITIES = Map.of( //
			LibraryElementPackage.Literals.INTERFACE_LIST, Integer.valueOf(1), //
			LibraryElementPackage.Literals.VAR_DECLARATION, Integer.valueOf(2), //
			LibraryElementPackage.Literals.FB, Integer.valueOf(3), //
			LibraryElementPackage.Literals.ECC, Integer.valueOf(4), //
			LibraryElementPackage.Literals.FB_NETWORK, Integer.valueOf(5), //
			LibraryElementPackage.Literals.ALGORITHM, Integer.valueOf(6), //
			LibraryElementPackage.Literals.METHOD, Integer.valueOf(7), //
			LibraryElementPackage.Literals.SERVICE, Integer.valueOf(8), //
			LibraryElementPackage.Literals.ATTRIBUTE, Integer.valueOf(9) //
	);

	@Override
	public int compare(final Viewer viewer, final Object e1, final Object e2) {
		final Integer priority1 = getPriority(e1);
		final Integer priority2 = getPriority(e2);
		return priority1.compareTo(priority2);
	}

	private static Integer getPriority(final Object obj) {
		return (obj instanceof final EObject eObj && eObj.eContainer() instanceof FBType)
				? PRIORITIES.getOrDefault(eObj.eClass(), Integer.valueOf(Integer.MAX_VALUE))
				: Integer.valueOf(Integer.MAX_VALUE);
	}

}
