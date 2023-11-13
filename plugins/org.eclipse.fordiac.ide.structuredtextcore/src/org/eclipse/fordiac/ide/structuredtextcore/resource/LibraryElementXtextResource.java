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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

public class LibraryElementXtextResource extends LazyLinkingResource {
	private LibraryElement libraryElement;
	private final List<EObject> additionalContent = new ArrayList<>();
	private boolean includeInternalLibraryElement = true;

	protected void updateInternalLibraryElement() {
		clearInternalLibraryElement();
		if (contents != null && !contents.isEmpty()) {
			if (libraryElement != null) {
				contents.add(copyLibraryElement(libraryElement, !includeInternalLibraryElement));
			}
			contents.addAll(EcoreUtil.copyAll(additionalContent));
			relink();
		}
	}

	protected void clearInternalLibraryElement() {
		if (contents != null) {
			contents.removeIf(LibraryElement.class::isInstance);
			contents.removeAll(additionalContent);
		}
	}

	public void setLibraryElement(final LibraryElement libraryElement) {
		this.libraryElement = libraryElement;
		updateInternalLibraryElement();
	}

	public LibraryElement getLibraryElement() {
		return libraryElement;
	}

	public LibraryElement getInternalLibraryElement() {
		return getContents().stream().filter(LibraryElement.class::isInstance).map(LibraryElement.class::cast)
				.findFirst().orElse(null);
	}

	public List<EObject> getAdditionalContent() {
		return additionalContent;
	}

	public boolean isIncludeInternalLibraryElement() {
		return includeInternalLibraryElement;
	}

	public void setIncludeInternalLibraryElement(final boolean includeInternalLibraryElement) {
		this.includeInternalLibraryElement = includeInternalLibraryElement;
		updateInternalLibraryElement();
	}

	@Override
	public synchronized EObject getEObject(final String uriFragment) {
		try {
			return super.getEObject(uriFragment);
		} catch (final IllegalArgumentException e) {
			return null;
		}
	}

	protected static LibraryElement copyLibraryElement(final LibraryElement libraryElement, final boolean shallow) {
		final Copier copier = shallow ? new ShallowCopier() : new Copier();
		final LibraryElement result = (LibraryElement) copier.copy(libraryElement);
		copier.copyReferences();
		return result;
	}

	protected static class ShallowCopier extends EcoreUtil.Copier {
		private static final long serialVersionUID = 1L;

		@Override
		protected void copyContainment(final EReference eReference, final EObject eObject, final EObject copyEObject) {
			// for a LibraryElement, copy only CompilerInfo
			if (!LibraryElementPackage.Literals.LIBRARY_ELEMENT.isSuperTypeOf(eObject.eClass())
					|| LibraryElementPackage.Literals.LIBRARY_ELEMENT__COMPILER_INFO == eReference) {
				super.copyContainment(eReference, eObject, copyEObject);
			}
		}
	}
}
