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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.typemanagement.util.TypeFromTemplateCreator;
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
			final EObject errorType = FordiacErrorMarker.getTarget(marker);
			if (errorType instanceof final VarDeclaration target) {
				AbstractLiveSearchContext.executeAndSave(ChangeDataTypeCommand.forDataType(target, newEntry.getType()),
						target, new NullProgressMonitor());
			} else if (errorType instanceof final StructManipulator fb && newEntry instanceof final DataType d) {
				AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, d), fb, new NullProgressMonitor());
			} else if (errorType instanceof final ConfigurableFB fb && newEntry instanceof final DataType d) {
				AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, d), fb, new NullProgressMonitor());
			}
		}
	}

	private void createNewEntry() {
		final File template = new File(TEMPLATE_PATH);
		final IFile targetFile = getTargetFile(FordiacErrorMarker.getData(this.marker)[0],
				marker.getResource().getProject().getFullPath());
		final TypeFromTemplateCreator creator = new TypeFromTemplateCreator(targetFile, template, ""); //$NON-NLS-1$
		creator.createTypeFromTemplate(new NullProgressMonitor());

		newEntry = (DataTypeEntry) creator.getTypeEntry();
	}

	private static IFile getTargetFile(final String typeName, final IPath path) {
		return ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(path + File.separator + "Type Library" + File.separator + typeName + ".dtp")); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
