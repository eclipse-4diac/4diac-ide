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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class SystemEntryImpl extends AbstractTypeEntryImpl implements SystemEntry {

	public SystemEntryImpl() {
		// for system entries we don't want to perform any updates on save
		setUpdateTypeOnSave(false);
	}

	@Override
	public AutomationSystem getSystem() {
		return getType();
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		if (toSave instanceof final AutomationSystem system) {
			doSaveInternal(new SystemExporter(system), monitor);
		} else {
			FordiacLogHelper.logError("Tried to save non AutomationSystem for SystemEntry");//$NON-NLS-1$
		}
	}

	@Override
	public void setSystem(final LibraryElement system) {
		setType(system);
	}

	@Override
	public synchronized AutomationSystem getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final AutomationSystem as) {
			return as;
		}
		return null;
	}

	@Override
	public synchronized void setType(final LibraryElement newType) {
		if (newType instanceof AutomationSystem) {
			super.setType(newType);
		} else {
			super.setType(null);
			if (null != newType) {
				FordiacLogHelper.logError("tried to set no AutomationSystem as type entry for SystemEntry");//$NON-NLS-1$
			}
		}
	}

	@Override
	public synchronized AutomationSystem getTypeEditable() {
		// for performance reasons the systemEntry uses only the type and not the type
		// editable
		return getSystem();
	}

	@Override
	public synchronized void setTypeEditable(final LibraryElement newTypeEditable) {
		// for performance reasons the systemEntry uses only the type and not the type
		// editable
		setSystem(newTypeEditable);
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new SystemImporter(getFile());
	}

}
