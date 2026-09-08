/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberContext;
import org.eclipse.jface.viewers.IStructuredSelection;

public final class AddStructMemberPropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (!(receiver instanceof final IStructuredSelection selection) || selection.size() != 1) {
			return false;
		}
		return AddStructMemberContext
				.forStructPin(AddStructMemberRefactoringHandler.getStructPin(selection.getFirstElement())).isPresent();
	}
}
