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
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface TypeEntry extends Notifier {

	String TYPE_ENTRY_FILE_FEATURE = "TYPE_ENTRY_FILE_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_TYPE_FEATURE = "TYPE_ENTRY_TYPE_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_TYPE_EDITABLE_FEATURE = "TYPE_ENTRY_TYPE_EDITABLE_FEATURE"; //$NON-NLS-1$

	IFile getFile();

	void setFile(IFile value);

	long getLastModificationTimestamp();

	void setLastModificationTimestamp(long value);

	LibraryElement getType();

	void setType(LibraryElement value);

	LibraryElement getTypeEditable();

	void setTypeEditable(LibraryElement value);

	TypeLibrary getTypeLibrary();

	void setTypeLibrary(TypeLibrary typeLib);

	/** Save the editable type to the file associated with this type entry */
	void save();

	String getTypeName();

	String getFullTypeName();

	default String getPackageName() {
		return PackageNameHelper.extractPackageName(getFullTypeName());
	}

	void refresh();

	static String getTypeNameFromFile(final IFile element) {
		return getTypeNameFromFileName(element.getName());
	}

	static String getTypeNameFromFileName(final String fileName) {
		String name = fileName;
		final int index = fileName.lastIndexOf('.');
		if (-1 != index) {
			name = fileName.substring(0, index);
		}
		return name;
	}
}
