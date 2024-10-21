/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add enablement element
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor.internal;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;

public class TypeEditorPageFactory {

	private static final String TYPE_EDITOR_PAGE_EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.typeeditor.typeEditorPage"; //$NON-NLS-1$

	private final List<TypeEditorPageDescriptor> editorPages = loadEditorPages();

	public Collection<ITypeEditorPage> getEditors(final LibraryElement type) {
		return editorPages.stream() //
				.filter(ep -> ep.handlesType(type)) //
				.sorted(Comparator.comparingInt(TypeEditorPageDescriptor::getSortIndex)) //
				.map(TypeEditorPageDescriptor::createEditorPage) //
				.filter(Objects::nonNull).toList();
	}

	private static List<TypeEditorPageDescriptor> loadEditorPages() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint(TYPE_EDITOR_PAGE_EXTENSION_POINT_ID);
		return Stream.of(point.getExtensions()).map(IExtension::getConfigurationElements).flatMap(Stream::of)
				.map(TypeEditorPageDescriptor::new).toList();
	}
}
