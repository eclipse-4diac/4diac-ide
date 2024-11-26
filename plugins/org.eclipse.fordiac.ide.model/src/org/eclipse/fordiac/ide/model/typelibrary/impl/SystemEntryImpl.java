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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class SystemEntryImpl extends AbstractCheckedTypeEntryImpl<AutomationSystem> implements SystemEntry {

	public SystemEntryImpl() {
		super(AutomationSystem.class);
		// for system entries we don't want to perform any updates on save
		setUpdateTypeOnSave(false);
	}

	@Override
	public AutomationSystem getSystem() {
		return getType();
	}

	@Override
	public void setSystem(final LibraryElement system) {
		setType(system);
	}

	/**
	 * @deprecated see {@link TypeEntry#getTypeEditable()}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public AutomationSystem getTypeEditable() {
		// for performance reasons the systemEntry uses only the type and not the type
		// editable
		return getSystem();
	}

	/**
	 * @deprecated see {@link TypeEntry#setTypeEditable(LibraryElement)}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public void setTypeEditable(final LibraryElement newTypeEditable) {
		// for performance reasons the systemEntry uses only the type and not the type
		// editable
		setSystem(newTypeEditable);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SystemImporter(getFile());
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
	protected synchronized NotificationChain basicSetTypeEditable(final LibraryElement newTypeEditable,
			final NotificationChain notifications) {
		return super.basicSetType(newTypeEditable, notifications);
	}

	@Override
	protected LibraryElement basicGetTypeEditable() {
		return super.basicGetType();
	}

}
