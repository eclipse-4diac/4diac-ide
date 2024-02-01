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
import org.eclipse.fordiac.ide.model.dataexport.FCTExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.FCTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.FunctionFBTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class FunctionFBTypeEntryImpl extends FBTypeEntryImpl implements FunctionFBTypeEntry {

	@Override
	public synchronized FunctionFBType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final FunctionFBType functionFBType) {
			return functionFBType;
		}
		return null;
	}

	@Override
	public synchronized FunctionFBType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final FunctionFBType functionFBType) {
			return functionFBType;
		}
		return null;
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		if (toSave instanceof final FunctionFBType functionFBType) {
			doSaveInternal(new FCTExporter(functionFBType), monitor);
		} else {
			FordiacLogHelper.logError("Tried to save non FunctionFBType for FunctionFBTypeEntry");//$NON-NLS-1$
		}
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof FunctionFBType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no FunctionFBType as type entry for FunctionFBTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new FCTImporter(getFile());
	}

}
