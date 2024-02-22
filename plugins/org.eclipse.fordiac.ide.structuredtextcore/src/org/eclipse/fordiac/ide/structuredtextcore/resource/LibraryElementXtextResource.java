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

import org.eclipse.emf.common.util.URI;
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

	public void updateInternalLibraryElement() {
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

	public static URI getExternalURI(final EObject object) {
		return toExternalURI(EcoreUtil.getURI(object));
	}

	public static URI getInternalURI(final EObject object) {
		return toInternalURI(EcoreUtil.getURI(object));
	}

	public static URI toExternalURI(final URI uri) {
		if (uri != null && uri.hasFragment() && uri.fragment().startsWith("/1")) { //$NON-NLS-1$
			return uri.trimQuery().trimFragment().appendFragment("/" + uri.fragment().substring(2)); //$NON-NLS-1$
		}
		return uri;
	}

	public static URI toInternalURI(final URI uri) {
		if (uri != null && uri.hasFragment() && !uri.fragment().startsWith("/1")) { //$NON-NLS-1$
			return uri.trimFragment().appendFragment("/1" + uri.fragment().substring(1)); //$NON-NLS-1$
		}
		return uri;
	}

	public static String toExternalFragment(final String fragment) {
		if (fragment != null && fragment.startsWith("/1")) { //$NON-NLS-1$
			return "/" + fragment.substring(2); //$NON-NLS-1$
		}
		return fragment;
	}

	public static String toInternalFragment(final String fragment) {
		if (fragment != null && !fragment.startsWith("/1")) { //$NON-NLS-1$
			return "/1" + fragment.substring(1); //$NON-NLS-1$
		}
		return fragment;
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
