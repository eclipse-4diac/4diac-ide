/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.dataexport.GlobalConstantsExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.GlobalConstantsImporter;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class GlobalConstantsEntryImpl extends AbstractTypeEntryImpl implements GlobalConstantsEntry {

	@Override
	public synchronized GlobalConstants getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final GlobalConstants globalConsts) {
			return globalConsts;
		}
		return null;
	}

	@Override
	public synchronized GlobalConstants getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final GlobalConstants globalConsts) {
			return globalConsts;
		}
		return null;
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		if (toSave instanceof final GlobalConstants globalConsts) {
			doSaveInternal(new GlobalConstantsExporter(globalConsts), monitor);
		} else {
			FordiacLogHelper.logError("Tried to save non GlobalConstants for GlobalConstantsTypeEntry");//$NON-NLS-1$
		}
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof GlobalConstants) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no GlobalConstants as type entry for GlobalConstantsTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new GlobalConstantsImporter(getFile());
	}

}
