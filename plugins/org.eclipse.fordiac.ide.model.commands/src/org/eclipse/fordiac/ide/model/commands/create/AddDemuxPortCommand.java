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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.StructManipulation;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
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

	private final StructuredType struct;
	private ChangeStructCommand cmd;


	public AddDemuxPortCommand(final Demultiplexer type, final String name) {
		this.type = type;
		this.varName = name;
		struct = type.getTypeLibrary().getDataTypeLibrary().getStructuredType(type.getStructType().getName());
		oldVisibleChildren = type.getAttributeValue(DEMUX_VISIBLE_CHILDREN);
	}

	private String getNewAttributeValue() {
		if (null == oldVisibleChildren) { // default configuration
			final StringBuilder sb = new StringBuilder();
			type.getStructType().getMemberVariables()
			.forEach(memVar -> sb.append(memVar.getName() + VARIABLE_SEPARATOR));
			if (!type.getStructType().getMemberVariables().isEmpty()) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append(VARIABLE_SEPARATOR + varName);
			return sb.toString();
		} else if ("".equals(oldVisibleChildren)) { //$NON-NLS-1$
			return varName;
		} else {
			return oldVisibleChildren + VARIABLE_SEPARATOR + varName;
		}
	}

	@Override
	public void execute() {
		newVisibleChildren = getNewAttributeValue();
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
		for (final VarDeclaration varDeclaration : getVarDeclarations(visibleChildrenNames)) {
			final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			variable.setName(varDeclaration.getName());
			variable.setType(varDeclaration.getType());
			varDecls.add(variable);
		}
		configuredStruct.getMemberVariables().addAll(varDecls);
		cmd = new ChangeStructCommand(type, configuredStruct);
	}

	@Override
	public boolean canExecute() {
		// can execute if port doesn't exist in demux yet
		return (varName != null) && ((oldVisibleChildren == null)
				|| Arrays.stream(oldVisibleChildren.split(VARIABLE_SEPARATOR))
				.filter(name -> name.equals(varName)).findAny().isEmpty());
	}

	@Override
	public void redo() {
		cmd.redo();
		type = (Demultiplexer) cmd.getNewMux();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		type = oldMux;
		cmd.undo();
		if (oldVisibleChildren == null) {
			type.deleteAttribute(DEMUX_VISIBLE_CHILDREN);
		} else {
			setVisibleChildrenAttribute(oldVisibleChildren);
		}
	}

	private void setVisibleChildrenAttribute(final String value) {
		type.setAttribute(DEMUX_VISIBLE_CHILDREN, FordiacKeywords.STRING, value, ""); //$NON-NLS-1$
	}

	private List<VarDeclaration> getVarDeclarations(final List<String> varDeclNames) {
		final List<VarDeclaration> vars = new ArrayList<>();
		varDeclNames.forEach(name -> {
			final VarDeclaration varDecl = EcoreUtil
					.copy(StructManipulation.findVarDeclarationInStruct(struct, name));
			if (null != varDecl) {
				varDecl.setName(name);
				vars.add(varDecl);
			}
		});
		return vars;
	}

	public Demultiplexer getType() {
		return type;
	}
}
