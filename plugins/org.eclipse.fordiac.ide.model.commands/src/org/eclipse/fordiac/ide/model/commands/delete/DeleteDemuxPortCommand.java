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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.StructManipulation;
import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class DeleteDemuxPortCommand extends Command {

	private Demultiplexer type;
	private final VarDeclaration variable;
	private final String oldVisibleChildren;
	private String newVisibleChildren;
	private ChangeStructCommand cmd;
	private Demultiplexer oldMux;
	private final StructTreeNode node;

	public DeleteDemuxPortCommand(final Demultiplexer type, final StructTreeNode node) {
		this.variable = (VarDeclaration) type.getInterfaceElement(node.getPinName());
		this.oldVisibleChildren = node.getRootNode().visibleToString();
		this.type = type;
		this.node = node;
	}

	@Override
	public void execute() {
		node.check(false);
		newVisibleChildren = node.getRootNode().visibleToString();
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
		configuredStruct.getMemberVariables().addAll(EcoreUtil.copyAll(getVarDeclarations(visibleChildrenNames)));
		cmd = new ChangeStructCommand(type, configuredStruct);
	}

	@Override
	public boolean canExecute() {
		return variable != null;
	}

	@Override
	public void redo() {
		node.check(false);
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		node.check(true);
		type = oldMux;
		cmd.undo();
		setVisibleChildrenAttribute(oldVisibleChildren);
	}

	private void setVisibleChildrenAttribute(final String value) {
		type.setAttribute(DEMUX_VISIBLE_CHILDREN, FordiacKeywords.STRING, value, ""); //$NON-NLS-1$
	}

	private Collection<VarDeclaration> getVarDeclarations(final List<String> varDeclNames) {
		final List<VarDeclaration> vars = new ArrayList<>();
		varDeclNames.forEach(varName -> {
			final VarDeclaration varDecl = EcoreUtil
					.copy(StructManipulation.findVarDeclarationInStruct(type.getStructType(), varName));
			if (null != varDecl) {
				varDecl.setName(varName);
				vars.add(varDecl);
			}
		});
		return vars;
	}

	public Demultiplexer getType() {
		return type;
	}

}