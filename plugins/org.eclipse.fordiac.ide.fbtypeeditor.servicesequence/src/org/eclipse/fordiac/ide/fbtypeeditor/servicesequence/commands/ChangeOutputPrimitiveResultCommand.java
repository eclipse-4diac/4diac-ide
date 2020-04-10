/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.gef.commands.Command;

public class ChangeOutputPrimitiveResultCommand extends Command {
	private OutputPrimitive primitive;
	private int results;
	private int oldResults;

	public ChangeOutputPrimitiveResultCommand(OutputPrimitive primitive, String results) {
		super();
		this.primitive = primitive;
		this.results = (!results.equals("")) ? Integer.valueOf(results) : 0; //$NON-NLS-1$
	}

	@Override
	public void execute() {
		oldResults = primitive.getTestResult();
		primitive.setTestResult(results);
	}

	@Override
	public void undo() {
		primitive.setTestResult(oldResults);
	}

	@Override
	public void redo() {
		primitive.setTestResult(results);
	}
}
