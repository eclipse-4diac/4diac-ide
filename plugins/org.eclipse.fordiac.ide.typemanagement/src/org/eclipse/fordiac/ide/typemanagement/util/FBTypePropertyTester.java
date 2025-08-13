/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.typemanagement.util;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class FBTypePropertyTester extends PropertyTester {

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof final IFile file) {
			return getFBTypeStringFromFile(file).equals(expectedValue);
		}
		return false;
	}

	private static String getFBTypeStringFromFile(final IFile file) {
		if (TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) instanceof final FBTypeEntry fbTypeEntry) {
			final EClass typeEClass = fbTypeEntry.getTypeEClass();
			if (typeEClass != null) {
				typeEClass.getName();
			}
		}
		return ""; //$NON-NLS-1$
	}

}
