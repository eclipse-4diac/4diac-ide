/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;

public class STAlgorithmResource extends STCoreResource {

	private static final Set<String> FILE_EXTENSIONS = Set.of(TypeLibraryTags.FB_TYPE_FILE_ENDING,
			TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING, TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING,
			TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING, TypeLibraryTags.DATA_TYPE_FILE_ENDING);

	private static final Set<String> ADDITIONAL_FILE_EXTENSIONS = Set.of(TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING,
			TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING, TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
			TypeLibraryTags.DATA_TYPE_FILE_ENDING);

	public static boolean isValidFileExtension(final String fileExtension) {
		return fileExtension != null && FILE_EXTENSIONS.contains(fileExtension.toUpperCase());
	}

	public static boolean isValidAdditionalFileExtension(final String fileExtension) {
		return fileExtension != null && ADDITIONAL_FILE_EXTENSIONS.contains(fileExtension.toUpperCase());
	}

	public static boolean isValidUri(final URI uri) {
		return uri.isPlatformResource() && isValidFileExtension(uri.fileExtension());
	}

	public static boolean isValidAdditionalUri(final URI uri) {
		return uri.isPlatformResource() && isValidAdditionalFileExtension(uri.fileExtension());
	}

	@Override
	protected boolean needsInternalLibraryElement(final LibraryElement libraryElement, final EObject sourceElement) {
		// we also need an internal library element for variables in a base FB type
		// for proper linking (not necessary for attributes)
		return super.needsInternalLibraryElement(libraryElement, sourceElement)
				|| (libraryElement instanceof BaseFBType && sourceElement.eContainer() instanceof VarDeclaration);
	}
}
