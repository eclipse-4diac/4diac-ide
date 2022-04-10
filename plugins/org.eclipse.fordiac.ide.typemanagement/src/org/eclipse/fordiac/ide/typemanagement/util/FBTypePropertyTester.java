/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FBTypePropertyTester extends PropertyTester {

	public FBTypePropertyTester() {
	}

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		if (receiver instanceof IFile) {
			return getFBTypeStringFromFile((IFile) receiver).equals(expectedValue);
		}
		return false;
	}

	private static String getFBTypeStringFromFile(final IFile file) {
		final FBType type = getFBTypeFromFile(file);
		if (type != null) {
			return type.eClass().getName();
		}
		return "";
	}

	private static FBType getFBTypeFromFile(final IFile file) {
		final TypeEntry entry = TypeLibrary.getTypeEntryForFile(file);
		if (entry instanceof FBTypeEntry) {
			return ((FBTypeEntry) entry).getType();
		}
		return null;
	}
}
