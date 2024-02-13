/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.FCTExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.FCTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.FunctionFBTypeEntry;

public class FunctionFBTypeEntryImpl extends AbstractCheckedTypeEntryImpl<FunctionFBType>
		implements FunctionFBTypeEntry {

	public FunctionFBTypeEntryImpl() {
		super(FunctionFBType.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new FCTImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final FunctionFBType type) {
		return new FCTExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.FUNCTION_FB_TYPE;
	}
}
