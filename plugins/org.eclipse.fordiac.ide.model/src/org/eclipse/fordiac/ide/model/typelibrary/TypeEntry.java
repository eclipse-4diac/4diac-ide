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

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public interface TypeEntry extends Notifier {

	String TYPE_ENTRY_FILE_FEATURE = "TYPE_ENTRY_FILE_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_FILE_CONTENT_FEATURE = "TYPE_ENTRY_FILE_CONTENT_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_TYPE_FEATURE = "TYPE_ENTRY_TYPE_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_TYPE_EDITABLE_FEATURE = "TYPE_ENTRY_TYPE_EDITABLE_FEATURE"; //$NON-NLS-1$
	String TYPE_ENTRY_TYPE_LIBRARY_FEATURE = "TYPE_ENTRY_TYPE_LIBRARY"; //$NON-NLS-1$
	String TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE = "TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE"; //$NON-NLS-1$

	int TYPE_ENTRY_FILE_FEATURE_ID = 1;
	int TYPE_ENTRY_FILE_CONTENT_FEATURE_ID = 2;
	int TYPE_ENTRY_TYPE_FEATURE_ID = 3;
	int TYPE_ENTRY_TYPE_EDITABLE_FEATURE_ID = 4;
	int TYPE_ENTRY_TYPE_LIBRARY_FEATURE_ID = 5;
	int TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE_ID = 6;

	IFile getFile();

	void setFile(IFile value);

	long getLastModificationTimestamp();

	void setLastModificationTimestamp(long value);

	LibraryElement getType();

	void setType(LibraryElement value);

	/**
	 * @deprecated The "editable" type may not be identical to the type currently
	 *             used in editors due to automatic reload from disk on changes.
	 *             However, it was often used this way. In the future, either get a
	 *             private copy to edit the type via {@link #copyType()} or get the
	 *             type directly from an editor via
	 *             {@code Adapters.adapt(editor, LibraryElement.class)}.
	 */
	@Deprecated(since = "3.0.0", forRemoval = true)
	LibraryElement getTypeEditable();

	/**
	 * @deprecated The "editable" type may not be identical to the type currently
	 *             used in editors due to automatic reload from disk on changes.
	 *             However, it was often used this way. In the future, use
	 *             {@link #save(LibraryElement, IProgressMonitor)} to save a
	 *             modified type or update the type directly inside an editor.
	 */
	@Deprecated(since = "3.0.0", forRemoval = true)
	void setTypeEditable(LibraryElement value);

	TypeLibrary getTypeLibrary();

	void setTypeLibrary(TypeLibrary typeLib);

	LibraryElement copyType();

	/** Save the editable type to the file associated with this type entry */
	default void save(final LibraryElement toSave) throws CoreException {
		save(toSave, new NullProgressMonitor());
	}

	/** Save the editable type to the file associated with this type entry */
	void save(LibraryElement toSave, IProgressMonitor monitor) throws CoreException;

	String getTypeName();

	String getFullTypeName();

	String getComment();

	EClass getTypeEClass();

	default String getPackageName() {
		return PackageNameHelper.extractPackageName(getFullTypeName());
	}

	Set<TypeEntry> getDependencies();

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

	default URI getURI() {
		final IFile file = getFile();
		if (file != null) {
			return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		}
		return null;
	}
}
