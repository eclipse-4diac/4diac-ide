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
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeviceTypeEntryImpl extends AbstractTypeEntryImpl implements DeviceTypeEntry {

	@Override
	public synchronized DeviceType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final DeviceType devType) {
			return devType;
		}
		return null;
	}

	@Override
	public synchronized DeviceType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final DeviceType devType) {
			return devType;
		}
		return null;
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		// currently we can not save devices, but we also have no editor for it
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof DeviceType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no DeviceType as type entry for DeviceTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new DEVImporter(getFile());
	}

}
