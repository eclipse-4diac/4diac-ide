/*******************************************************************************
 * Copyright (c) 2008 -2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Johannes Kepler University
 *               2023 Martin Erich Jobst
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
 *   Martin Jobst - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteInterfaceCommand extends Command {
	private final IInterfaceElement interfaceElement;
	private final InterfaceList parent;
	private final List<? extends IInterfaceElement> targetList;
	private CompoundCommand cmds;
	private int oldIndex;
	private final CompoundCommand deleteMarkersCmds = new CompoundCommand();

	public DeleteInterfaceCommand(final IInterfaceElement interfaceElement) {
		this.interfaceElement = getInterfaceElement(interfaceElement);
		parent = (InterfaceList) interfaceElement.eContainer();
		targetList = getTargetList(this.interfaceElement);  // this has to be the adjust this.interfaceElement
	}

	@Override
	public void execute() {
		cmds = new CompoundCommand();
		handleWiths();
		handleSubAppConnections();
		if ((interfaceElement instanceof final AdapterDeclaration adp)
				&& (parent.eContainer() instanceof CompositeFBType)) {
			cmds.add(new DeleteFBNetworkElementCommand(adp.getAdapterNetworkFB()));
		}
		deleteMarkersCmds.add(
				FordiacMarkerCommandHelper.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(interfaceElement)));
		deleteMarkersCmds.execute();
		performDeletion();
		if (cmds.canExecute()) {
			cmds.execute();
		}
	}

	@Override
	public void undo() {
		@SuppressWarnings("unchecked")
		final EList<IInterfaceElement> temp = (EList<IInterfaceElement>) targetList;
		temp.add(oldIndex, interfaceElement);
		if (cmds.canUndo()) {
			cmds.undo();
		}
		deleteMarkersCmds.undo();
	}

	@Override
	public void redo() {
		deleteMarkersCmds.redo();
		targetList.remove(oldIndex);
		if (cmds.canRedo()) {
			cmds.redo();
		}
	}

	private void performDeletion() {
		oldIndex = targetList.indexOf(interfaceElement);
		targetList.remove(oldIndex);
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
		if (interfaceElement instanceof final VarDeclaration varDecl) {
			for (final With with : varDecl.getWiths()) {
				cmds.add(new DeleteWithCommand(with));
			}
		} else if (interfaceElement instanceof final Event event) {
			for (final With with : event.getWith()) {
				cmds.add(new DeleteWithCommand(with));
			}
		}
	}

	public InterfaceList getParent() {
		return parent;
	}

	private static IInterfaceElement getInterfaceElement(final IInterfaceElement ie) {
		if (ie instanceof final VarDeclaration varDecl && varDecl.isInOutVar() && !ie.isIsInput()) {
			final InterfaceList il = (InterfaceList) ie.eContainer();
			return il.getInOutVars().get(il.getOutMappedInOutVars().indexOf(ie));
		}
		return ie;
	}

	private static List<? extends IInterfaceElement> getTargetList(final IInterfaceElement ie) {
		final InterfaceList il = (InterfaceList) ie.eContainer();

		if (ie instanceof ErrorMarkerInterface) {
			return il.getErrorMarker();
		}

		if (ie.isIsInput()) {
			if (ie instanceof Event) {
				return il.getEventInputs();
			}
			if (ie instanceof AdapterDeclaration) {
				return il.getSockets();
			}
			if (ie instanceof final VarDeclaration varDecl) {
				if (varDecl.isInOutVar()) {
					return il.getInOutVars();
				}
				return il.getInputVars();
			}

		} else {
			if (ie instanceof Event) {
				return il.getEventOutputs();
			}

			if (ie instanceof AdapterDeclaration) {
				return il.getPlugs();
			}
			if (ie instanceof VarDeclaration) {
				return il.getOutputVars();
			}
		}
		return Collections.emptyList();
	}
}
