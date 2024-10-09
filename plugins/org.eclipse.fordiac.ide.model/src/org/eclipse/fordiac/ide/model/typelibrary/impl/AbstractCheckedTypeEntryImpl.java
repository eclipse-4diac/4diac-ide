/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

abstract class AbstractCheckedTypeEntryImpl<T extends LibraryElement> extends AbstractTypeEntryImpl {

	private final Class<T> typeClass;

	protected AbstractCheckedTypeEntryImpl(final Class<T> typeClass) {
		this.typeClass = typeClass;
	}

	@Override
	public T getType() {
		final LibraryElement type = super.getType();
		if (typeClass.isInstance(type)) {
			return typeClass.cast(type);
		}
		return null;
	}

	/**
	 * @deprecated see {@link TypeEntry#getTypeEditable()}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public T getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (typeClass.isInstance(type)) {
			return typeClass.cast(type);
		}
		return null;
	}

	@Override
	public T copyType() {
		final LibraryElement type = super.copyType();
		if (typeClass.isInstance(type)) {
			return typeClass.cast(type);
		}
		return null;
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		if (typeClass.isInstance(toSave)) {
			doSaveInternal(getTypeExporter(typeClass.cast(toSave)), monitor);
		} else {
			FordiacLogHelper.logError("Tried to save wrong type entry for " + getClass().getName());//$NON-NLS-1$
		}
	}

	@Override
	public void setType(final LibraryElement type) {
		if (typeClass.isInstance(type)) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("Tried to set wrong type entry for " + getClass().getName()); //$NON-NLS-1$
			}
		}
	}

	protected abstract AbstractTypeExporter getTypeExporter(T type);
}
