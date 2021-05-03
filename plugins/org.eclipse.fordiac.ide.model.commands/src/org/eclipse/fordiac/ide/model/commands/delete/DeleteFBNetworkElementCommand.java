/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.editors.I4diacModelEditor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteFBNetworkElementCommand extends Command {
	private FBNetwork fbParent;
	private final FBNetworkElement element;
	private CompoundCommand cmds = new CompoundCommand();

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
		fbParent = element.getFbNetwork();
		if (element.isMapped()) {
			cmds.add(new UnmapCommand(element));
		}
		getDeleteConnections(element);
		// Before removing the fbnetwork element the connections and mapping should be
		// removed
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
	}

	@Override
	public void redo() {
		if (cmds.canRedo()) {
			cmds.redo();
		}
		fbParent.getNetworkElements().remove(element);
	}

	private void getDeleteConnections(final FBNetworkElement element) {
		for (IInterfaceElement intElement : element.getInterface().getAllInterfaceElements()) {
			EList<Connection> connList = null;
			if (intElement.isIsInput()) {
				connList = intElement.getInputConnections();
			} else {
				connList = intElement.getOutputConnections();
			}
			if (null != connList) {
				connList.forEach((Connection con) -> cmds.add(new DeleteConnectionCommand(con)));
			}
		}
	}

	private static void closeSubApplicationEditor(SubApp subapp) {
		EditorUtils.closeEditorsFiltered(editor -> ((editor instanceof I4diacModelEditor)
				&& (subapp.getSubAppNetwork() == ((I4diacModelEditor) editor).getModel())));
	}

}
