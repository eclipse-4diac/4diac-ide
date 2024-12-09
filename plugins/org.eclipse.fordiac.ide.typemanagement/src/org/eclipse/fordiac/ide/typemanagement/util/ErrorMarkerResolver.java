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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class ErrorMarkerResolver {

	public static final String TEMPLATE_PATH = Platform.getInstallLocation().getURL().getFile() + File.separatorChar
			+ "template"; //$NON-NLS-1$

	public static void repairMissingStructuredDataType(final VarDeclaration target) {
		final File template = new File(ErrorMarkerResolver.TEMPLATE_PATH + File.separatorChar + "Struct.dtp"); //$NON-NLS-1$

		final EObject rootContainer = EcoreUtil.getRootContainer(target);

		IPath fullPath = null;

		if (rootContainer instanceof final LibraryElement le) {
			fullPath = le.getTypeEntry().getTypeLibrary().getProject().getFullPath();
		}

		final DataType type = target.getType();

		if (type instanceof ErrorMarkerDataType) {
			final IFile targetFile = getTargetFile(type.getName(), fullPath, ".dtp"); //$NON-NLS-1$
			final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template, ""); //$NON-NLS-1$
			creator.createTypeFromTemplate(new NullProgressMonitor());
		}
	}

	private static IFile getTargetFile(final String typeName, final IPath path, final String fileEnding) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(
				path + File.separator + TypeLibraryTags.TYPE_LIB_FOLDER_NAME + File.separator + typeName + fileEnding));

	}
}
