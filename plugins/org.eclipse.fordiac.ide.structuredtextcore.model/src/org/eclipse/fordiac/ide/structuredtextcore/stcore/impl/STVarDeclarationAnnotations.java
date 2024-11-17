/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;

final class STVarDeclarationAnnotations {

	static String getFullTypeName(final STVarDeclaration varDeclaration) {
		final INamedElement featureType = STCoreUtil.getFeatureType(varDeclaration);
		if (featureType != null) {
			return featureType.getName();
		}
		return varDeclaration.getTypeName();
	}

	private STVarDeclarationAnnotations() {
		throw new UnsupportedOperationException();
	}
}
