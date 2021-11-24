/*******************************************************************************
 * Copyright (c) 2016, 2021 fortiss GmbH, Johannes Keppler University Linz
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *               - added checks for value errormarkers
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.editors.I4diacModelEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteFBNetworkElementCommand extends Command {
	private FBNetwork fbParent;
	private final FBNetworkElement element;
	private final CompoundCommand cmds = new CompoundCommand();
	private ErrorMarkerBuilder errorMarker;
	private final List<ErrorMarkerBuilder> valueErrorMarkers = new ArrayList<>();

	public DeleteFBNetworkElementCommand(final FBNetworkElement element) {
		super(Messages.DeleteFBNetworkElementCommand_DeleteFBOrSubapplication);
		this.element = element;
	}

	public FBNetworkElement getFBNetworkElement() {
		return element;
	}

	@Override
	public boolean canExecute() {
		if ((element instanceof FB) && ((FB) element).isResourceTypeFB()) {
			return false;
		}
		return null != element && null != element.getFbNetwork();
	}

	@Override
	public void execute() {
		if (element instanceof ErrorMarkerRef) {
			errorMarker = ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) element);
		}

		fbParent = element.getFbNetwork();
		if (element.isMapped()) {
			cmds.add(new UnmapCommand(element));
		}
		collectDeleteCommands(element);
		handleValueErrorMarkers();
		// Before removing the fbnetwork element the connections, value error markers, and mapping should be removed
		if (cmds.canExecute()) {
			cmds.execute();
		}
		fbParent.getNetworkElements().remove(element);
		if (element instanceof SubApp) {
			closeSubApplicationEditor((SubApp) element);
		}
	}

	@Override
	public void undo() {
		fbParent.getNetworkElements().add(element);
		if (cmds.canUndo()) {
			cmds.undo();
		}
		if (element instanceof ErrorMarkerRef && errorMarker != null) {
			errorMarker.createMarkerInFile();
		}
		restoreValueErrorMarkers();
	}

	@Override
	public void redo() {
		if (cmds.canRedo()) {
			cmds.redo();
		}
		handleValueErrorMarkers();
		if (element instanceof ErrorMarkerRef) {
			errorMarker = ErrorMarkerBuilder.deleteErrorMarker((ErrorMarkerRef) element);
		}

		fbParent.getNetworkElements().remove(element);

	}

	private void collectDeleteCommands(final FBNetworkElement element) {
		for (final IInterfaceElement intElement : element.getInterface().getAllInterfaceElements()) {
			final EList<Connection> connections = intElement.isIsInput() ? intElement.getInputConnections()
					: intElement.getOutputConnections();
			connections.forEach(con -> cmds.add(new DeleteConnectionCommand(con, element)));
		}
	}

	private static void closeSubApplicationEditor(final SubApp subapp) {
		EditorUtils.closeEditorsFiltered(editor -> ((editor instanceof I4diacModelEditor)
				&& (subapp.getSubAppNetwork() == ((I4diacModelEditor) editor).getModel())));
	}

	private void handleValueErrorMarkers() {
		for (final VarDeclaration varIn : element.getInterface().getInputVars()) {
			if ((varIn.getValue() != null) && (varIn.getValue().hasError())) {
				valueErrorMarkers.add(ErrorMarkerBuilder.deleteErrorMarker(varIn.getValue()));
			}
		}
	}

	private void restoreValueErrorMarkers() {
		for (final ErrorMarkerBuilder errorMarkerBuilder : valueErrorMarkers) {
			errorMarkerBuilder.createMarkerInFile();
		}
		valueErrorMarkers.clear();
	}

}
