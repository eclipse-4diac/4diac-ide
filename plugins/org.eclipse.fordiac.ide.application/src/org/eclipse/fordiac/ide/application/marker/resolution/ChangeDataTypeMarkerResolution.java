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
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

public class ChangeDataTypeMarkerResolution extends AbstractErrorMarkerResolution {

	private DataType selectedType;
	private boolean canceled = false;

	public ChangeDataTypeMarkerResolution(final IMarker marker) {
		super(FordiacMessages.Repair_Dialog_ChangeDataType, marker);
	}

	@Override
	public void run(final IMarker marker) {
		if (canceled) {
			return;
		}

		if (selectedType == null) {
			selectDataType();
		}

		if (selectedType != null) {
			final EObject errorType = getTargetElement(marker);
			if (errorType instanceof final IInterfaceElement interfaceElement) {
				AbstractLiveSearchContext.executeAndSave(
						ChangeDataTypeCommand.forDataType(interfaceElement, selectedType), interfaceElement,
						new NullProgressMonitor());
			} else if (errorType instanceof final StructManipulator fb && selectedType instanceof StructuredType) {
				AbstractLiveSearchContext.executeAndSave(new ChangeStructCommand(fb, selectedType), fb,
						new NullProgressMonitor());
			} else if (errorType instanceof final ConfigurableFB fb) {
				AbstractLiveSearchContext.executeAndSave(new ConfigureFBCommand(fb, selectedType), fb,
						new NullProgressMonitor());
			}
		}
	}

	private void selectDataType() {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				DataTypeSelectionTreeContentProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if (dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory()) {
			if (node.getType() instanceof final DataType dataType) {
				selectedType = dataType;
			}
		} else {
			canceled = true;
		}
	}
}
