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
import org.eclipse.fordiac.ide.model.dataexport.FbtExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class FBTypeEntryImpl extends AbstractTypeEntryImpl implements FBTypeEntry {

	@Override
	public synchronized FBType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}

	@Override
	public synchronized FBType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final FBType fbType) {
			return fbType;
		}
		return null;
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		if (toSave instanceof final FBType fbType) {
			doSaveInternal(new FbtExporter(fbType), monitor);
		} else {
			FordiacLogHelper.logError("Tried to save non GlobalConstants for GlobalConstantsTypeEntry");//$NON-NLS-1$
		}
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof FBType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no FBType as type entry for FBTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new FBTImporter(getFile());
	}

}
