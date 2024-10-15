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

import java.util.Arrays;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class ChangeDataTypeMarkerResolution extends AbstractCommandMarkerResolution<ITypedElement> {

	private DataType selectedType;

	public ChangeDataTypeMarkerResolution(final IMarker marker) {
		super(marker, ITypedElement.class);
	}

	@Override
	protected boolean prepare(final IMarker[] markers, final IProgressMonitor monitor) throws CoreException {
		final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				DataTypeSelectionTreeContentProvider.INSTANCE);
		dialog.setInput(getTypeLibrary());
		if ((dialog.open() == Window.OK && dialog.getFirstResult() instanceof final TypeNode node
				&& !node.isDirectory()) && (node.getType() instanceof final DataType dataType)) {
			selectedType = dataType;
			return true;
		}
		return false;
	}

	@Override
	protected Command createCommand(final ITypedElement element, final IProgressMonitor monitor) throws CoreException {
		return switch (element) {
		case final IInterfaceElement interfaceElement ->
			ChangeDataTypeCommand.forDataType(interfaceElement, selectedType);
		case final StructManipulator fb when selectedType instanceof StructuredType ->
			new ChangeStructCommand(fb, selectedType);
		case final ConfigurableFB fb -> new ConfigureFBCommand(fb, selectedType);
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
		return FordiacMessages.Repair_Dialog_ChangeDataType;
	}

	@Override
	public String getDescription() {
		return FordiacMessages.Repair_Dialog_ChangeDataType;
	}

	@Override
	public Image getImage() {
		return null;
	}
}
