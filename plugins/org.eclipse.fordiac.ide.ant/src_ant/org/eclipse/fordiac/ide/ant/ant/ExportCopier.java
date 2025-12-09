/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

public class ExportCopier extends EcoreUtil.Copier {
	private static final long serialVersionUID = 1L;

	@Override
	protected EClass getTarget(final EClass eClass) {
		if (eClass == LibraryElementPackage.Literals.FUNCTION_BODY
				|| eClass == LibraryElementPackage.Literals.TEXT_FUNCTION_BODY
				|| eClass == LibraryElementPackage.Literals.ST_FUNCTION_BODY) {
			return null;
		}
		if (eClass == LibraryElementPackage.Literals.BASE_FB_TYPE
				|| eClass == LibraryElementPackage.Literals.BASIC_FB_TYPE
				|| eClass == LibraryElementPackage.Literals.SIMPLE_FB_TYPE
				|| eClass == LibraryElementPackage.Literals.COMPOSITE_FB_TYPE
				|| eClass == LibraryElementPackage.Literals.SUB_APP_TYPE) {
			return LibraryElementPackage.Literals.SERVICE_INTERFACE_FB_TYPE;
		}
		return super.getTarget(eClass);
	}

	@Override
	protected Setting getTarget(final EStructuralFeature eStructuralFeature, final EObject eObject,
			final EObject copyEObject) {
		if (!eStructuralFeature.getContainerClass().isInstance(copyEObject)) {
			return null;
		}
		return super.getTarget(eStructuralFeature, eObject, copyEObject);
	}
}
