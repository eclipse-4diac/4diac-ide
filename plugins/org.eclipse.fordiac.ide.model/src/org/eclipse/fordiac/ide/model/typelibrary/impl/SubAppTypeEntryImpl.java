/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
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

import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SubApplicationTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;

public class SubAppTypeEntryImpl extends AbstractCheckedTypeEntryImpl<SubAppType> implements SubAppTypeEntry {

	public SubAppTypeEntryImpl() {
		super(SubAppType.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SubAppTImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final SubAppType type) {
		return new SubApplicationTypeExporter(type);
	}

}
