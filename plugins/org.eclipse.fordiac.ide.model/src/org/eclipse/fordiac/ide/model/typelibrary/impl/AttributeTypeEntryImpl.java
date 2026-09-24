/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class AttributeTypeEntryImpl extends AbstractCheckedTypeEntryImpl<AttributeDeclaration>
		implements AttributeTypeEntry {

	public AttributeTypeEntryImpl() {
		super(AttributeDeclaration.class);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.ATTRIBUTE_DECLARATION;
	}

	@Override
	public String getFileExtension() {
		return TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING;
	}
}
