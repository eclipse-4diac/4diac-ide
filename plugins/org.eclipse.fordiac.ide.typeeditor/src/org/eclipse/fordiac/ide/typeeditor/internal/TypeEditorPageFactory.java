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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typeeditor.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPageDescriptor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class TypeEditorPageFactory {

	private static final String TYPE_EDITOR_PAGE_EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.typeeditor.typeEditorPage"; //$NON-NLS-1$

	private final record TypeEditorPageEntry(ITypeEditorPageDescriptor editorPageDescriptor, int sortIndex) {

	}

	private final List<TypeEditorPageEntry> editorPages = loadEditorPages();

	public Collection<ITypeEditorPage> getEditors(final LibraryElement type) {
		return editorPages.stream() //
				.filter(ep -> ep.editorPageDescriptor().handlesType(type)) //
				.sorted(Comparator.comparingInt(TypeEditorPageEntry::sortIndex)) //
				.map(ep -> ep.editorPageDescriptor().createEditorPage()) //
				.toList();
	}

	private static List<TypeEditorPageEntry> loadEditorPages() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry.getExtensionPoint(TYPE_EDITOR_PAGE_EXTENSION_POINT_ID);
		final List<TypeEditorPageEntry> editorPages = new ArrayList<>();
		for (final IExtension extension : point.getExtensions()) {
			for (final IConfigurationElement confEl : extension.getConfigurationElements()) {
				try {
					if (confEl.createExecutableExtension(
							"class") instanceof final ITypeEditorPageDescriptor editorPageDescriptor) { //$NON-NLS-1$
						editorPages.add(new TypeEditorPageEntry(editorPageDescriptor,
								Integer.parseInt(confEl.getAttribute("sortIndex")))); //$NON-NLS-1$
					}
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
		return editorPages;
	}

}
