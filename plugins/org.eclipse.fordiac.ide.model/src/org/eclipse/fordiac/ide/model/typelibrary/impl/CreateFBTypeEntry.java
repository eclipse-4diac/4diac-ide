/********************************************************************************
 * Copyright (c) 2014 fortiss GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 *  Martin Erich Jobst
 *    - add can handle for classes
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ITypeEntryCreator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class CreateFBTypeEntry implements ITypeEntryCreator {

	@Override
	public boolean canHandle(final IFile file) {
		return (TypeLibraryTags.FB_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension()));
	}

	@Override
	public boolean canHandle(final EClass eClass) {
		return LibraryElementPackage.Literals.FB_TYPE.isSuperTypeOf(eClass)
				&& !LibraryElementPackage.Literals.FUNCTION_FB_TYPE.isSuperTypeOf(eClass)
				&& !LibraryElementPackage.Literals.SUB_APP_TYPE.isSuperTypeOf(eClass);
	}

	@Override
	public FBTypeEntry createTypeEntry() {
		return new FBTypeEntryImpl();
	}

}
