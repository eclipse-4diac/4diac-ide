/*******************************************************************************
 * Copyright (c) 2020, 2024 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *      - initial implementation and documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.gef.commands.Command;

public class AddDemuxPortCommand extends Command implements ScopedCommand {

	private Demultiplexer type;
	private String newVisibleChildren;
	private final String varName;
	private Demultiplexer oldMux;
	private ChangeStructCommand cmd;
	private final CheckableStructTreeNode node;

	public AddDemuxPortCommand(final Demultiplexer type, final CheckableStructTreeNode node) {
		this.node = Objects.requireNonNull(node);
		this.type = Objects.requireNonNull(type);
		this.varName = node.getPinName();
	}

	@Override
	public void execute() {
		node.updateNode(true);
		newVisibleChildren = node.getTree().getRoot().visibleToString();
		cmd = new ChangeStructCommand(type, newVisibleChildren);
		if (cmd.canExecute()) {
			cmd.execute();
		}
		oldMux = type;
		type = (Demultiplexer) cmd.getNewMux();
	}

	@Override
	public boolean canExecute() {
		// can execute if port doesn't exist in demux yet
		return (varName != null && type.getInterfaceElement(node.getPinName()) == null);
	}

	@Override
	public void redo() {
		node.updateNode(true);
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		type.setIsConfigured(true);
	}

	@Override
	public void undo() {
		node.updateNode(false);
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