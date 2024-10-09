/*********************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.test.model.typelibrary;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class AttributeTypeEntryMock extends BasicNotifierImpl implements AttributeTypeEntry {

	private AttributeDeclaration attributeDeclaration;
	private TypeLibrary typelib;
	private IFile file;

	public AttributeTypeEntryMock(final AttributeDeclaration attributeDeclaration, final TypeLibrary typelib,
			final IFile file) {
		this.attributeDeclaration = attributeDeclaration;
		this.typelib = typelib;
		this.file = file;
	}

	public AttributeTypeEntryMock(final AttributeTypeEntry typeEntry) {
		this(typeEntry.getType(), typeEntry.getTypeLibrary(), typeEntry.getFile());
	}

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile value) {
		file = value;
	}

	@Override
	public long getLastModificationTimestamp() {
		return 0;
	}

	@Override
	public void setLastModificationTimestamp(final long value) {
		// currently not needed in mock
	}

	@Override
	public void setType(final LibraryElement value) {
		attributeDeclaration = (AttributeDeclaration) value;
	}

	/**
	 * @deprecated see {@link TypeEntry#setTypeEditable(LibraryElement)}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public void setTypeEditable(final LibraryElement value) {
		// currently not needed in mock
	}

	@Override
	public TypeLibrary getTypeLibrary() {
		return typelib;
	}

	@Override
	public void setTypeLibrary(final TypeLibrary typeLib) {
		this.typelib = typeLib;
	}

	@Override
	public AttributeDeclaration getType() {
		return attributeDeclaration;
	}

	/**
	 * @deprecated see {@link TypeEntry#getTypeEditable()}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public AttributeDeclaration getTypeEditable() {
		// currently not needed in mock
		return null;
	}

	@Override
	public AttributeDeclaration copyType() {
		// currently not needed in mock
		return null;
	}

	@Override
	public String getTypeName() {
		return getType().getName();
	}

	@Override
	public String getFullTypeName() {
		return PackageNameHelper.getFullTypeName(getType());
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) {
		// currently not needed in mock
	}

	@Override
	public void refresh() {
		// currently not needed in mock
	}

	@Override
	public Set<TypeEntry> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public String getComment() {
		return attributeDeclaration.getComment();
	}

	@Override
	public EClass getTypeEClass() {
		return attributeDeclaration.eClass();
	}
}