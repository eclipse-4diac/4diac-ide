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
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class TypeCreator {

	public static final String TEMPLATE_PATH = Platform.getInstallLocation().getURL().getFile() + File.separatorChar
			+ "template";

	public static void run(final IMarker marker) {

		try {
			final EObject target = FordiacErrorMarker.getTarget(marker);

			if (target instanceof VarDeclaration) {
				repairMissingDataType(target);

			}

		} catch (IllegalArgumentException | CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// MessageDialog.openInformation(null, "MissingTypeQuickFix Demo", "This
		// quick-fix
		// is not yet implemented");

		// Display.getDefault().asyncExec(() -> {
		// final CustomWizard wizard = new CustomWizard(marker);
		// final WizardDialog dialog = new WizardDialog(
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
		// dialog.open();
		// dialog.open();
//		});

	}

	public static void repairMissingDataType(final EObject target) {
		final File template = new File(TypeCreator.TEMPLATE_PATH + File.separatorChar + "Struct.dtp");

		final EObject rootContainer = EcoreUtil.getRootContainer(target);

		IPath fullPath = null;

		if (rootContainer instanceof final AutomationSystem system) {
			fullPath = system.getTypeEntry().getTypeLibrary().getProject().getFullPath();
		}
		if (rootContainer instanceof final FBType fbType) {
			fullPath = fbType.getTypeEntry().getTypeLibrary().getProject().getFullPath();
		}

		final DataType type = ((VarDeclaration) target).getType();

		if (type instanceof final ErrorMarkerDataType errorType) {
			final TypeEntry typeEntry = errorType.getTypeEntry();

			final IFile file = typeEntry.getFile();
			final int k = 0;

			// final IFile targetTypeFile = getTargetFile();

			// final TypeFromTemplateCreator creator = new
			// TypeFromTemplateCreator(getTargetFile(), template,
			// packageName);

			final IFile targetFile = getTargetFile(type.getName(), fullPath);
			final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template, "");
			creator.createTypeFromTemplate(new NullProgressMonitor());
		}
	}

	private static IFile getTargetFile(final String typeName, final IPath path) {
		return ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(path + File.separator + "Type Library" + File.separator + typeName + ".dtp"));

	}
}
