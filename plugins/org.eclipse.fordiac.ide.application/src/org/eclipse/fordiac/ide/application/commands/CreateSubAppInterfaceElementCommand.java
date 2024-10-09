/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;

public class CreateSubAppInterfaceElementCommand extends CreateInterfaceElementCommand {

	private CreateInterfaceElementCommand mirroredElement = null;
	private ResizeGroupOrSubappCommand resizeCmd = null;

	public CreateSubAppInterfaceElementCommand(final IInterfaceElement copySrc, final boolean isInput,
			final InterfaceList targetInterfaceList, final int index) {
		super(copySrc, isInput, targetInterfaceList, index);
	}

	public CreateSubAppInterfaceElementCommand(final DataType dataType, final String name,
			final InterfaceList interfaceList, final boolean isInput, final boolean isInOut, final String arraySize,
			final int index) {
		super(dataType, name, interfaceList, isInput, isInOut, arraySize, index);
	}

	public CreateSubAppInterfaceElementCommand(final DataType dataType, final String name,
			final InterfaceList interfaceList, final boolean isInput, final int index) {
		super(dataType, name, interfaceList, isInput, index);
	}

	public CreateInterfaceElementCommand getMirroredElement() {
		return mirroredElement;
	}

	@Override
	public void execute() {
		super.execute();
		if (getInterfaceList().getFBNetworkElement().isMapped()) {
			// the subapp is mapped so we need to created the interface element also in the
			// opposite entry
			mirroredElement = createMirroredInterfaceElement();
		}

		if (EditorUtils.getCurrentActiveEditor() != null) {
			final GraphicalViewer graphicalViewer = EditorUtils.getCurrentActiveEditor()
					.getAdapter(GraphicalViewer.class);

			if (graphicalViewer != null && getInterfaceList().eContainer() instanceof SubApp) {
				final EditPart registeredEP = graphicalViewer.getEditPartForModel((getInterfaceList().eContainer()));
				if (registeredEP instanceof final SubAppForFBNetworkEditPart subAppEP
						&& subAppEP.getContentEP() != null) {
					resizeCmd = new ResizeGroupOrSubappCommand(subAppEP.getContentEP());
					resizeCmd.execute();
				}
			}
		}
	}

	@Override
	public void redo() {
		super.redo();
		if (null != mirroredElement) {
			mirroredElement.redo();
		}
		if (resizeCmd != null) {
			resizeCmd.redo();
		}
	}

	@Override
	public void undo() {
		super.undo();
		if (resizeCmd != null) {
			resizeCmd.undo();
		}
		if (null != mirroredElement) {
			mirroredElement.undo();
		}
	}

	protected CreateInterfaceElementCommand createMirroredInterfaceElement() {
		// if the subapp is mapped we need to created the interface element also in the
		// opposite entry
		final CreateInterfaceElementCommand mirroredCreateCmd = new CreateInterfaceElementCommand(getCreatedElement(),
				getCreatedElement().isIsInput(), getCreatedElement().getFBNetworkElement().getOpposite().getInterface(),
				getIndex());
		mirroredCreateCmd.execute();
		// Set the same name as the one we have also on the mirrored
		mirroredCreateCmd.getCreatedElement().setName(getCreatedElement().getName());
		return mirroredCreateCmd;
	}

}
