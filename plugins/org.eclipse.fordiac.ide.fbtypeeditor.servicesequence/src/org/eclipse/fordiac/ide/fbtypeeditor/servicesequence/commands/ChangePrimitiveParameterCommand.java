/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.gef.commands.Command;

public class ChangePrimitiveParameterCommand extends Command {
	private Primitive primitive;
	private String parameters;
	private String oldParameters;

	public ChangePrimitiveParameterCommand(Primitive primitive, String parameters) {
		super();
		this.primitive = primitive;
		this.parameters = parameters;
	}

	@Override
	public void execute() {
		oldParameters = primitive.getParameters();
		primitive.setParameters(parameters);
	}

	@Override
	public void undo() {
		primitive.setParameters(oldParameters);
	}
	@Override
	public void redo() {
		primitive.setParameters(parameters);
	}
}
