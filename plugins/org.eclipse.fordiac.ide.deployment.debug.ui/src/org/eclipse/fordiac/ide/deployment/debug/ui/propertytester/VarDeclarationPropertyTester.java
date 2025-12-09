/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.propertytester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class VarDeclarationPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof final VarDeclaration varDeclaration && "inOutVar".equals(property)) { //$NON-NLS-1$
			return varDeclaration.isInOutVar();
		}
		return false;
	}

}
