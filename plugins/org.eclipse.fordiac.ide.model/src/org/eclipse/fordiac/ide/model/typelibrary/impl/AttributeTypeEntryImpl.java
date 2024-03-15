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
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.AttributeTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.AttributeTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;

public class AttributeTypeEntryImpl extends AbstractCheckedTypeEntryImpl<AttributeDeclaration>
		implements AttributeTypeEntry {

	public AttributeTypeEntryImpl() {
		super(AttributeDeclaration.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new AttributeTypeImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AttributeDeclaration type) {
		return new AttributeTypeExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.ATTRIBUTE_DECLARATION;
	}
}
