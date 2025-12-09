/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.marker.resolution;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeDeclarationCommand;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

public class CreateAttributMarkerResolution extends AbstractCommandMarkerResolution<Attribute> {
	private static final String TEMPLATE_PATH = Platform.getInstallLocation().getURL().getFile() + File.separatorChar
			+ "template" + File.separatorChar + "AttributeDeclaration.atp"; //$NON-NLS-1$ //$NON-NLS-2$

	private AttributeTypeEntry newEntry;

	protected CreateAttributMarkerResolution(final IMarker marker) {
		super(marker, Attribute.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final File template = new File(TEMPLATE_PATH);

		final String typeName = getMarker().getAttributes().get("location").toString(); //$NON-NLS-1$
		final IFile targetFile = getTargetFile(typeName, markers[0].getResource().getProject());
		final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template,
				PackageNameHelper.extractPackageName(typeName));
		creator.createTypeFromTemplate(new NullProgressMonitor());
		NewTypeWizard.openTypeEditor(targetFile);

		newEntry = (AttributeTypeEntry) creator.getTypeEntry();
		return null != newEntry;
	}

	private static IFile getTargetFile(final String typeName, final IProject project) {
		return project.getFile(Path.fromOSString(TypeLibraryTags.TYPE_LIB_FOLDER_NAME)
				.append(typeName.replace(PackageNameHelper.PACKAGE_NAME_DELIMITER, String.valueOf(IPath.SEPARATOR)))
				.addFileExtension(TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING.toLowerCase()));
	}

	@Override
	protected Command createCommand(final Attribute element, final IProgressMonitor monitor) throws CoreException {
		return switch (element) {
		case final Attribute attr -> ChangeAttributeDeclarationCommand.forName(attr, newEntry.getFullTypeName());
		};
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_New_Attribute;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getLabel() {
		return FordiacMessages.Repair_Dialog_New_Attribute;
	}

}
