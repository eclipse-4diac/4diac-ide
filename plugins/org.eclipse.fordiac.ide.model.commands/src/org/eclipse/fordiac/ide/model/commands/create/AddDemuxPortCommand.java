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

import java.util.Arrays;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class AddDemuxPortCommand extends AbstractCreationCommand {

	private Demultiplexer type;
	private String oldVisibleChildren;
	private String newVisibleChildren;
	private String varName;

	public AddDemuxPortCommand(Demultiplexer type, String name) {
		this.type = type;
		this.varName = name;
		oldVisibleChildren = type.getAttributeValue(DEMUX_VISIBLE_CHILDREN);
	}

	private String getNewAttributeValue() {
		if (null == oldVisibleChildren) { // default configuration
			StringBuilder sb = new StringBuilder();
			type.getStructType().getMemberVariables()
					.forEach(var -> sb.append(var.getName() + VARIABLE_SEPARATOR));
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
		setVisibleChildrenAttribute(newVisibleChildren);
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
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		if (oldVisibleChildren == null) {
			type.deleteAttribute(DEMUX_VISIBLE_CHILDREN);
		} else {
			setVisibleChildrenAttribute(oldVisibleChildren);
		}
	}

	@Override
	public Object getCreatedElement() {
		return type.getInterfaceElement(varName);
	}

	private void setVisibleChildrenAttribute(String value) {
		type.setAttribute(DEMUX_VISIBLE_CHILDREN, FordiacKeywords.STRING, value, ""); //$NON-NLS-1$
	}
}
