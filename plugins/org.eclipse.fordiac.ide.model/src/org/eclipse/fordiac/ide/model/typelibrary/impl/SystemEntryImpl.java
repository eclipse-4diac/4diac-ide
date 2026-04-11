/*******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
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
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorAutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class SystemEntryImpl extends AbstractCheckedTypeEntryImpl<AutomationSystem> implements SystemEntry {

	public SystemEntryImpl() {
		super(AutomationSystem.class);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SystemImporter(getFile());
	}

	@Override
	protected ErrorAutomationSystem createErrorLibraryElement() {
		final ErrorAutomationSystem errorSystem = LibraryElementFactory.eINSTANCE.createErrorAutomationSystem();
		errorSystem.setSystemConfiguration(LibraryElementFactory.eINSTANCE.createSystemConfiguration());
		return errorSystem;
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final AutomationSystem type) {
		return new SystemExporter(type);
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.AUTOMATION_SYSTEM;
	}

	@Override
	public String getFileExtension() {
		return TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING;
	}
}
