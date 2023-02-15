/*******************************************************************************
 * Copyright (c) 2008 -2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteInterfaceCommand extends Command {
	private final IInterfaceElement interfaceElement;
	private CompoundCommand cmds;
	private InterfaceList parent;
	private int oldIndex;
	private ErrorMarkerBuilder oldErrorMarker;

	public DeleteInterfaceCommand(final IInterfaceElement interfaceElement) {
		this.interfaceElement = interfaceElement;
	}

	@Override
	public void execute() {
		parent = (InterfaceList) interfaceElement.eContainer();
		cmds = new CompoundCommand();
		handleWiths();
		handleSubAppConnections();
		if ((interfaceElement instanceof AdapterDeclaration) && (parent.eContainer() instanceof CompositeFBType)) {
			cmds.add(new DeleteFBNetworkElementCommand(((AdapterDeclaration) interfaceElement).getAdapterFB()));
		}
		deleteErrorMarker();
		performDeletion();
		if (cmds.canExecute()) {
			cmds.execute();
		}
	}

	@Override
	public void undo() {
		if (interfaceElement.isIsInput()) {
			if (interfaceElement instanceof Event) {
				parent.getEventInputs().add(oldIndex, (Event) interfaceElement);
			} else if (interfaceElement instanceof AdapterDeclaration) {
				parent.getSockets().add(oldIndex, (AdapterDeclaration) interfaceElement);
			} else if (interfaceElement instanceof VarDeclaration) {
				parent.getInputVars().add(oldIndex, (VarDeclaration) interfaceElement);
			} else if (interfaceElement instanceof ErrorMarkerInterface) {
				parent.getErrorMarker().add(oldIndex, (ErrorMarkerInterface) interfaceElement);
			}
		} else {
			if (interfaceElement instanceof Event) {
				parent.getEventOutputs().add(oldIndex, (Event) interfaceElement);
			} else if (interfaceElement instanceof AdapterDeclaration) {
				parent.getPlugs().add(oldIndex, (AdapterDeclaration) interfaceElement);
			} else if (interfaceElement instanceof VarDeclaration) {
				parent.getOutputVars().add(oldIndex, (VarDeclaration) interfaceElement);
			} else if (interfaceElement instanceof ErrorMarkerInterface) {
				parent.getErrorMarker().add(oldIndex, (ErrorMarkerInterface) interfaceElement);
			}
		}
		if (cmds.canUndo()) {
			cmds.undo();
		}
		undoErrorMarker();
	}

	private void undoErrorMarker() {
		if (oldErrorMarker != null) {
			oldErrorMarker.createMarkerInFile();
		}
	}

	@Override
	public void redo() {
		deleteErrorMarker();
		performDeletion();
		if (cmds.canRedo()) {
			cmds.redo();
		}
	}

	private void performDeletion() {
		if (interfaceElement.isIsInput()) {
			if (interfaceElement instanceof Event) {
				oldIndex = parent.getEventInputs().indexOf(interfaceElement);
				parent.getEventInputs().remove(interfaceElement);
			} else if (interfaceElement instanceof AdapterDeclaration) {
				oldIndex = parent.getSockets().indexOf(interfaceElement);
				parent.getSockets().remove(interfaceElement);
			} else if (interfaceElement instanceof VarDeclaration) {
				oldIndex = parent.getInputVars().indexOf(interfaceElement);
				parent.getInputVars().remove(interfaceElement);
			} else if (interfaceElement instanceof ErrorMarkerInterface) {
				oldIndex = parent.getErrorMarker().indexOf(interfaceElement);
				parent.getErrorMarker().remove(interfaceElement);
			}

		} else {
			if (interfaceElement instanceof Event) {
				oldIndex = parent.getEventOutputs().indexOf(interfaceElement);
				parent.getEventOutputs().remove(interfaceElement);
			} else if (interfaceElement instanceof AdapterDeclaration) {
				oldIndex = parent.getPlugs().indexOf(interfaceElement);
				parent.getPlugs().remove(interfaceElement);
			} else if (interfaceElement instanceof VarDeclaration) {
				oldIndex = parent.getOutputVars().indexOf(interfaceElement);
				parent.getOutputVars().remove(interfaceElement);
			} else if (interfaceElement instanceof ErrorMarkerInterface) {
				oldIndex = parent.getErrorMarker().indexOf(interfaceElement);
				parent.getErrorMarker().remove(interfaceElement);
			}
		}
	}

	private void deleteErrorMarker() {
		if (interfaceElement instanceof ErrorMarkerInterface) {
			oldErrorMarker = ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) interfaceElement);
		}
	}

	private void handleSubAppConnections() {
		for (final Connection con : interfaceElement.getInputConnections()) {
			cmds.add(new DeleteConnectionCommand(con));
		}
		for (final Connection con : interfaceElement.getOutputConnections()) {
			cmds.add(new DeleteConnectionCommand(con));
		}
	}

	private void handleWiths() {
		if (interfaceElement instanceof VarDeclaration) {
			final VarDeclaration varDecl = (VarDeclaration) interfaceElement;
			for (final With with : varDecl.getWiths()) {
				cmds.add(new DeleteWithCommand(with));
			}
		} else if (interfaceElement instanceof Event) {
			final Event event = (Event) interfaceElement;
			for (final With with : event.getWith()) {
				cmds.add(new DeleteWithCommand(with));
			}
		}
	}

	public InterfaceList getParent() {
		return parent;
	}
}
