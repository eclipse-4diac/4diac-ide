/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
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

import static org.eclipse.fordiac.ide.model.LibraryElementTags.DEMUX_VISIBLE_CHILDREN;
import static org.eclipse.fordiac.ide.model.LibraryElementTags.VARIABLE_SEPARATOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class AddDemuxPortCommand extends Command {

	private Demultiplexer type;
	private final String oldVisibleChildren;
	private String newVisibleChildren;
	private final String varName;
	private Demultiplexer oldMux;

	private ChangeStructCommand cmd;
	private final CheckableStructTreeNode node;

	public AddDemuxPortCommand(final Demultiplexer type, final CheckableStructTreeNode node) {
		this.node = node;
		this.type = type;
		this.varName = node.getPinName();
		this.oldVisibleChildren = node.getTree().getRoot().visibleToString();
	}

	@Override
	public void execute() {
		node.updateNode(true);

		if (null == newVisibleChildren) {
			newVisibleChildren = node.getTree().getRoot().visibleToString();
		}
		createChangeStructCommand();
		cmd.execute();
		oldMux = type;
		type = (Demultiplexer) cmd.getNewMux();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	private void createChangeStructCommand() {
		final StructuredType configuredStruct = DataFactory.eINSTANCE.createStructuredType();
		configuredStruct.setName(type.getStructType().getName());

		final List<String> visibleChildrenNames = Arrays
				.asList(newVisibleChildren.trim().split(LibraryElementTags.VARIABLE_SEPARATOR));
		final List<VarDeclaration> varDecls = new ArrayList<>();
		for (final VarDeclaration varDeclaration : CheckableStructTreeNode.getVarDeclarations(visibleChildrenNames,
				node)) {
			final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			variable.setName(varDeclaration.getName());
			variable.setComment(varDeclaration.getComment());
			variable.setType(varDeclaration.getType());
			varDecls.add(variable);
		}
		configuredStruct.getMemberVariables().addAll(varDecls);
		configuredStruct.setTypeEntry(type.getStructType().getTypeEntry());
		cmd = new ChangeStructCommand(type, configuredStruct);
	}

	@Override
	public boolean canExecute() {
		// can execute if port doesn't exist in demux yet
		return (varName != null)
				&& ((oldVisibleChildren == null) || Arrays.stream(oldVisibleChildren.split(VARIABLE_SEPARATOR))
						.filter(name -> name.equals(varName)).findAny().isEmpty());
	}

	@Override
	public void redo() {
		node.updateNode(true);
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		node.updateNode(false);
		type = oldMux;
		cmd.undo();
		setVisibleChildrenAttribute(oldVisibleChildren);

	}

	private void setVisibleChildrenAttribute(final String value) {
		type.setAttribute(DEMUX_VISIBLE_CHILDREN, ElementaryTypes.STRING, value, ""); //$NON-NLS-1$
	}

	public Demultiplexer getType() {
		return type;
	}
}