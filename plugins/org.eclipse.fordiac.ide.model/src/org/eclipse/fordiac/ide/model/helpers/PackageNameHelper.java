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
package org.eclipse.fordiac.ide.model.helpers;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public final class PackageNameHelper {

	public static final String PACKAGE_NAME_DELIMITER = "::"; //$NON-NLS-1$

	public static String getPackageName(final LibraryElement libraryElement) {
		if (libraryElement != null) {
			final CompilerInfo compilerInfo = libraryElement.getCompilerInfo();
			if (compilerInfo != null) {
				final String packageName = compilerInfo.getPackageName();
				if (packageName != null) {
					return packageName;
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	public static String getContainerPackageName(final EObject object) {
		if (EcoreUtil.getRootContainer(object) instanceof final LibraryElement libraryElement) {
			return getPackageName(libraryElement);
		}
		return ""; //$NON-NLS-1$
	}

	public static void setPackageName(final LibraryElement libraryElement, final String packageName) {
		if (libraryElement.getCompilerInfo() == null) {
			libraryElement.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}
		libraryElement.getCompilerInfo().setPackageName(packageName);
	}

	public static String getFullTypeName(final LibraryElement libraryElement) {
		if (libraryElement != null) {
			final String packageName = getPackageName(libraryElement);
			if (!packageName.isEmpty()) {
				return packageName + PACKAGE_NAME_DELIMITER + libraryElement.getName();
			}
			return libraryElement.getName();
		}
		return ""; //$NON-NLS-1$
	}

	public static void setFullTypeName(final LibraryElement libraryElement, final String fullTypeName) {
		final int lastIndex = fullTypeName.lastIndexOf(PACKAGE_NAME_DELIMITER);
		if (lastIndex != -1) {
			libraryElement.setName(fullTypeName.substring(lastIndex + PACKAGE_NAME_DELIMITER.length()));
			setPackageName(libraryElement, fullTypeName.substring(0, lastIndex));
		} else {
			libraryElement.setName(fullTypeName);
		}
	}

	public static String extractPlainTypeName(final String fullTypeName) {
		final int lastIndex = fullTypeName.lastIndexOf(PACKAGE_NAME_DELIMITER);
		if (lastIndex != -1) {
			return fullTypeName.substring(lastIndex + PACKAGE_NAME_DELIMITER.length());
		}
		return fullTypeName;
	}

	public static String extractPackageName(final String fullTypeName) {
		final int lastIndex = fullTypeName.lastIndexOf(PACKAGE_NAME_DELIMITER);
		if (lastIndex != -1) {
			return fullTypeName.substring(0, lastIndex);
		}
		return ""; //$NON-NLS-1$
	}

	public static String getPackageNameFromURI(final URI uri) {
		if (uri.isPlatformResource()) {
			return getPackageNameFromFile(
					ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true))));
		}
		return uri.trimSegments(1).segmentsList().stream().collect(Collectors.joining(PACKAGE_NAME_DELIMITER));
	}

	public static String getPackageNameFromFile(final IFile file) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());
		final IPath relativePath = BuildpathUtil.findRelativePath(typeLibrary.getBuildpath(), file.getParent())
				.orElse(file.getParent().getFullPath());
		return Stream.of(relativePath.segments()).collect(Collectors.joining(PACKAGE_NAME_DELIMITER));
	}

	private PackageNameHelper() {
		throw new UnsupportedOperationException();
	}
}
