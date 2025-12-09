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
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst
 *    - refactor marker resolutions
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

public class CreateDataTypeMarkerResolution extends AbstractCommandMarkerResolution<ITypedElement> {

	private static final String TEMPLATE_PATH = Platform.getInstallLocation().getURL().getFile() + File.separatorChar
			+ "template" + File.separatorChar + "Struct.dtp"; //$NON-NLS-1$ //$NON-NLS-2$

	private DataTypeEntry newEntry;

	public CreateDataTypeMarkerResolution(final IMarker marker) {
		super(marker, ITypedElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final File template = new File(TEMPLATE_PATH);
		final String typeName = FordiacErrorMarker.getData(markers[0])[0];
		final IFile targetFile = getTargetFile(typeName, markers[0].getResource().getProject());
		final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template,
				PackageNameHelper.extractPackageName(typeName));
		creator.createTypeFromTemplate(new NullProgressMonitor());
		NewTypeWizard.openTypeEditor(targetFile);

		newEntry = (DataTypeEntry) creator.getTypeEntry();
		return newEntry != null;
	}

	private static IFile getTargetFile(final String typeName, final IProject project) {
		return project.getFile(Path.fromOSString(TypeLibraryTags.TYPE_LIB_FOLDER_NAME)
				.append(typeName.replace(PackageNameHelper.PACKAGE_NAME_DELIMITER, String.valueOf(IPath.SEPARATOR)))
				.addFileExtension(TypeLibraryTags.DATA_TYPE_FILE_ENDING));
	}

	@Override
	protected Command createCommand(final ITypedElement element, final IProgressMonitor monitor) throws CoreException {
		return switch (element) {
		case final IInterfaceElement interfaceElement ->
			ChangeDataTypeCommand.forDataType(interfaceElement, newEntry.getType());
		case final StructManipulator fb when newEntry.getType() instanceof StructuredType ->
			new ChangeStructCommand(fb, newEntry.getType());
		case final ConfigurableFB fb -> new ConfigureFBCommand(fb, newEntry.getType());
		default -> null;
		};
	}

	@Override
	protected boolean isApplicable(final IMarker other) {
		final int code = FordiacErrorMarker.getCode(other);
		return (code == LibraryElementValidator.ITYPED_ELEMENT__VALIDATE_TYPE
				|| code == LibraryElementValidator.CONFIGURABLE_FB__VALIDATE_DATA_TYPE)
				&& Arrays.equals(FordiacErrorMarker.getData(other), FordiacErrorMarker.getData(getMarker()));
	}

	@Override
	public String getLabel() {
		return FordiacMessages.Repair_Dialog_New_DataType;
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_New_DataType;
	}

	@Override
	public Image getImage() {
		return null;
	}
}
