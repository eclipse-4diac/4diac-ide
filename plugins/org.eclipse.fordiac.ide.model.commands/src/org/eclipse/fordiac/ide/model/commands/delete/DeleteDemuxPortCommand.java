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

import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class DeleteDemuxPortCommand extends Command {

	private Demultiplexer type;
	private VarDeclaration var;
	private String name;
	private String oldVisibleChildren;
	private String newVisibleChildren;

	public DeleteDemuxPortCommand(Demultiplexer type, String name) {
		this.type = type;
		this.name = name;
		this.var = (VarDeclaration) type.getInterfaceElement(name);
		this.oldVisibleChildren = type.getAttributeValue("VisibleChildren"); //$NON-NLS-1$

	}

	private String getNewAttributeValue() {
		if (null == oldVisibleChildren) {
			StringBuilder sb = new StringBuilder();
			for (VarDeclaration var : type.getStructType().getMemberVariables()) {
				if (!var.getName().equals(name)) {
					sb.append(var.getName() + ","); //$NON-NLS-1$
				}
			}
			if (sb.charAt(sb.length() - 1) == ',') {
				sb.deleteCharAt(sb.length() - 1);
			}
			oldVisibleChildren = sb.toString();
		}
		return cutVarFromAttribute();
	}

	private String cutVarFromAttribute() {
		int startIndex = oldVisibleChildren.indexOf(name);
		if ((startIndex == -1) || (oldVisibleChildren.length() == name.length())) {
			return ""; //$NON-NLS-1$
		}
		int endIndex = startIndex + name.length();
		StringBuilder sb = new StringBuilder(oldVisibleChildren);
		sb.delete(startIndex, endIndex);
		if (sb.charAt(sb.length() - 1) == ',') {
			return sb.substring(0, sb.length() - 1);
		}
		if (sb.charAt(0) == ',') {
			return sb.substring(1);
		}
		return sb.toString();
	}

	@Override
	public void execute() {
		newVisibleChildren = getNewAttributeValue();
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public boolean canExecute() {
		return var != null;
	}

	@Override
	public void redo() {
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		setVisibleChildrenAttribute(oldVisibleChildren);
	}

	private void setVisibleChildrenAttribute(String value) {
		type.setAttribute("VisibleChildren", "STRING", value, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
