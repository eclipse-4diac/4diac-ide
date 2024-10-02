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
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;

public class CreateDataTypeMarkerResolution extends AbstractErrorMarkerResolution {

	private static final String TEMPLATE_PATH = Platform.getInstallLocation().getURL().getFile() + File.separatorChar
			+ "template" + File.separatorChar + "Struct.dtp"; //$NON-NLS-1$ //$NON-NLS-2$
	private DataTypeEntry newEntry;

	public CreateDataTypeMarkerResolution(final IMarker marker) {
		super(FordiacMessages.Repair_Dialog_New_DataType, marker);
	}

	@Override
	public void run(final IMarker marker) {
		if (newEntry == null) {
			createNewEntry();
		}

		if (newEntry != null) {
			final EObject errorType = getTargetElement(marker);
			if (errorType instanceof final IInterfaceElement target) {
				AbstractLiveSearchContext.executeAndSave(ChangeDataTypeCommand.forDataType(target, newEntry.getType()),
						target, new NullProgressMonitor());
			} else if (errorType instanceof final StructManipulator fb
					&& newEntry.getType() instanceof final StructuredType struct) {
				AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, struct), fb,
						new NullProgressMonitor());
			} else if (errorType instanceof final ConfigurableFB fb) {
				AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, newEntry.getType()), fb,
						new NullProgressMonitor());
			}
		}
	}

	private void createNewEntry() {
		final File template = new File(TEMPLATE_PATH);
		final String typeName = FordiacErrorMarker.getData(this.marker)[0];
		final IFile targetFile = getTargetFile(typeName, marker.getResource().getProject());
		final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template,
				PackageNameHelper.extractPackageName(typeName));
		creator.createTypeFromTemplate(new NullProgressMonitor());
		NewTypeWizard.openTypeEditor(targetFile);

		newEntry = (DataTypeEntry) creator.getTypeEntry();
	}

	private static IFile getTargetFile(final String typeName, final IProject project) {
		return project.getFile(Path.fromOSString(SystemManager.TYPE_LIB_FOLDER_NAME)
				.append(typeName.replace(PackageNameHelper.PACKAGE_NAME_DELIMITER, String.valueOf(IPath.SEPARATOR)))
				.addFileExtension(TypeLibraryTags.DATA_TYPE_FILE_ENDING));
	}
}
