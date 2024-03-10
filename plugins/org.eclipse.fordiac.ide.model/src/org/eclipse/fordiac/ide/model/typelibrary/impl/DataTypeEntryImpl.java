/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.DataTypeImporter;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;

public class DataTypeEntryImpl extends AbstractCheckedTypeEntryImpl<AnyDerivedType> implements DataTypeEntry {

	public DataTypeEntryImpl() {
		super(AnyDerivedType.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new DataTypeImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AnyDerivedType type) {
		return new DataTypeExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return DataPackage.Literals.ANY_DERIVED_TYPE;
	}
}
