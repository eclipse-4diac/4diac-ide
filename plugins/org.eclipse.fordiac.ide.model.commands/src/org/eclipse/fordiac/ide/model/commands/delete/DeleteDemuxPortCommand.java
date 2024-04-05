/*******************************************************************************
 * Copyright (c) 2020, 2024 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Bianca Wiesmayr - initial implementation and documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.gef.commands.Command;

public class DeleteDemuxPortCommand extends Command implements ScopedCommand {

	private Demultiplexer type;
	private final IInterfaceElement variable;
	private String newVisibleChildren;
	private ChangeStructCommand cmd;
	private Demultiplexer oldMux;
	private final CheckableStructTreeNode node;

	public DeleteDemuxPortCommand(final Demultiplexer type, final CheckableStructTreeNode node) {
		this.type = Objects.requireNonNull(type);
		this.node = Objects.requireNonNull(node);
		this.variable = type.getMemberVars().stream().map(MemberVarDeclaration.class::cast)
				.filter(member -> node.getPinName().equals(member.getFullName())).findAny().orElse(null);
	}

	@Override
	public void execute() {
		node.updateNode(false);
		newVisibleChildren = node.getTree().getRoot().visibleToString();
		cmd = new ChangeStructCommand(type, newVisibleChildren);
		if (cmd.canExecute()) {
			cmd.execute();
		}
		oldMux = type;
		type = (Demultiplexer) cmd.getNewMux();
		type.setIsConfigured(true);
	}

	@Override
	public boolean canExecute() {
		return (variable != null) && !(variable instanceof ErrorMarkerInterface)
				&& variable.getOutputConnections().isEmpty();
	}

	@Override
	public void redo() {
		node.updateNode(false);
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		type.setIsConfigured(true);
	}

	@Override
	public void undo() {
		node.updateNode(true);
		type = oldMux;
		cmd.undo();
	}

	public Demultiplexer getType() {
		return type;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (type.getFbNetwork() != null) {
			return Set.of(type.getFbNetwork());
		}
		return Set.of(type);
	}
}