/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
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

import static org.eclipse.fordiac.ide.model.LibraryElementTags.DEMUX_VISIBLE_CHILDREN;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public class DeleteDemuxPortCommand extends Command {

	private Demultiplexer type;
	private final IInterfaceElement variable;
	private final String oldVisibleChildren;
	private String newVisibleChildren;
	private ChangeStructCommand cmd;
	private Demultiplexer oldMux;
	private final CheckableStructTreeNode node;

	public DeleteDemuxPortCommand(final Demultiplexer type, final CheckableStructTreeNode node) {
		this.variable = type.getInterfaceElement(node.getPinName());
		this.oldVisibleChildren = node.getTree().getRoot().visibleToString();
		this.type = type;
		this.node = node;
	}

	@Override
	public void execute() {
		node.updateNode(false);
		newVisibleChildren = node.getTree().getRoot().visibleToString();
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
		configuredStruct.getMemberVariables()
				.addAll(EcoreUtil.copyAll(CheckableStructTreeNode.getVarDeclarations(visibleChildrenNames, node)));
		configuredStruct.setTypeEntry(type.getStructType().getTypeEntry());
		cmd = new ChangeStructCommand(type, configuredStruct);
	}

	@Override
	public boolean canExecute() {
		return (variable != null) && !(variable instanceof ErrorMarkerInterface);
	}

	@Override
	public void redo() {
		node.updateNode(false);
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		node.updateNode(true);
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