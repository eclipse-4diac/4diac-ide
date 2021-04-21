/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.gef.commands.Command;

public class AttributeChangeCommand extends Command {
	private final Attribute attribute;
	private final String name;
	private String nameOld;
	private final String value;
	private String valueOld;
	private final String comment;
	private String commentOld;
	private final BaseType1 type;
	private BaseType1 typeOld;

	public AttributeChangeCommand(Attribute attribute, String name, String value, BaseType1 type, String comment) {
		this.attribute = attribute;
		this.comment = comment;
		this.value = value;
		this.name = name;
		this.type = type;
	}

	public AttributeChangeCommand(Attribute attribute, String value) {
		this.attribute = attribute;
		this.value = value;
		this.comment = attribute.getComment();
		this.name = attribute.getName();
		this.type = attribute.getType();
	}

	@Override
	public boolean canExecute() {
		return null != attribute && (null != name || null != value || null != comment
				|| (null == attribute.getAttributeDeclaration() && null != type));
	}

	@Override
	public void execute() {
		nameOld = attribute.getName();
		valueOld = attribute.getValue();
		commentOld = attribute.getComment();
		typeOld = attribute.getType();
		redo();
	}

	@Override
	public void undo() {
		if (null != name) {
			attribute.setName(nameOld);
		}
		if (null != comment) {
			attribute.setComment(commentOld);
		}
		if (null != value) {
			attribute.setValue(valueOld);
		}
		if (null != type) {
			attribute.setType(typeOld);
		}
	}

	@Override
	public void redo() {
		if (null != name) {
			attribute.setName(name);
		}
		if (null != comment) {
			attribute.setComment(comment);
		}
		if (null != value) {
			attribute.setValue(value);
		}
		if (null != type) {
			attribute.setType(type);
		}
	}

}
