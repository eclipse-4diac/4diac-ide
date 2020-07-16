/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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

import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.providers.AbstractCreationCommand;

public class AddDemuxPortCommand extends AbstractCreationCommand {
	private Demultiplexer type;
	private String oldVisibleChildren;
	private String newVisibleChildren;
	private String varName;

	public AddDemuxPortCommand(Demultiplexer type, String name) {
		this.type = type;
		this.varName = name;
		oldVisibleChildren = type.getAttributeValue("VisibleChildren"); //$NON-NLS-1$
	}

	private String getNewAttributeValue() {
		if (null == oldVisibleChildren) { // default configuration
			StringBuilder sb = new StringBuilder();
			for (IInterfaceElement el : type.getInterface().getOutputVars()) {
				sb.append(el.getName() + ","); //$NON-NLS-1$
			}
			final int REMOVE_LAST_CHAR = 2;
			return sb.substring(0, sb.length() - REMOVE_LAST_CHAR);
		} else if ("".equals(oldVisibleChildren)) { //$NON-NLS-1$
			return varName;
		} else {
			return oldVisibleChildren + "," + varName; //$NON-NLS-1$
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
		return type.getInterfaceElement(varName) == null;
	}

	@Override
	public void redo() {
		setVisibleChildrenAttribute(newVisibleChildren);
	}

	@Override
	public void undo() {
		setVisibleChildrenAttribute(oldVisibleChildren);
	}

	@Override
	public Object getCreatedElement() {
		return type.getInterfaceElement(varName);
	}

	private void setVisibleChildrenAttribute(String value) {
		type.setAttribute("VisibleChildren", "STRING", value, ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
