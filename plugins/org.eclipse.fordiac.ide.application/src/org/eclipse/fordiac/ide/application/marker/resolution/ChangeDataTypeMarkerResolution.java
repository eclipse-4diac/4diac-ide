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

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

public class ChangeDataTypeMarkerResolution extends AbstractErrorMarkerResolution {

	private LibraryElement newEntry;
	private boolean canceled = false;

	public ChangeDataTypeMarkerResolution(final IMarker marker) {
		super(FordiacMessages.Repair_Dialog_ChangeDataType, marker);
	}

	@Override
	public void run(final IMarker marker) {
		if (canceled) {
			return;
		}

		if (newEntry == null) {
			createNewEntry();
		}

		if (newEntry != null) {
			final EObject errorType = FordiacErrorMarker.getTarget(marker);
			if (errorType instanceof final IInterfaceElement interfaceElement
					&& interfaceElement.getType().getTypeEntry() instanceof ErrorDataTypeEntry
					&& newEntry instanceof final DataType d) {
				AbstractLiveSearchContext.executeAndSave(ChangeDataTypeCommand.forDataType(interfaceElement, d),
						interfaceElement, new NullProgressMonitor());
			} else if (errorType instanceof final StructManipulator fb && newEntry instanceof final DataType d) {
				AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, d), fb, new NullProgressMonitor());
			} else if (errorType instanceof final ConfigurableFB fb && newEntry instanceof final DataType d) {
				AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, d), fb, new NullProgressMonitor());
			}
		}
	}

	private void createNewEntry() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				DataTypeSelectionTreeContentProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if ((dialog.open() == Window.OK)
				&& (dialog.getFirstResult() instanceof final TypeNode node && !node.isDirectory())) {
			newEntry = node.getType();
		} else {
			canceled = true;
		}
	}
}
