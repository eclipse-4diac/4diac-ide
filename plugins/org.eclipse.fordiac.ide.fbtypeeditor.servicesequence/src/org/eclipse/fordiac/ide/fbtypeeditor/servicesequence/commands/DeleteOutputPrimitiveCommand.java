/*******************************************************************************
 * Copyright (c) 2008, 2009  Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.gef.commands.Command;

/**
 * DeleteOutputPrimitiveCommand removes an OutputPrimitve from its parent. If
 * the primitive is the last one of the parent (and also no InputPrimitive is
 * available), then also the parent (ServiceTransaction) is removed.
 * 
 * @author gebenh
 */
public class DeleteOutputPrimitiveCommand extends Command {

	private final OutputPrimitive primitive;
	private ServiceTransaction parent;
	private int index;
	private DeleteTransactionCommand deleteTransactionCmd = null;

	/**
	 * Constructor.
	 * 
	 * @param primitive the primitive
	 */
	public DeleteOutputPrimitiveCommand(OutputPrimitive primitive) {
		this.primitive = primitive;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		EObject parentObject = primitive.eContainer();
		if (parentObject instanceof ServiceTransaction) {
			parent = (ServiceTransaction) parentObject;
			index = parent.getOutputPrimitive().indexOf(primitive);
			parent.getOutputPrimitive().remove(primitive);

			if (parent.getInputPrimitive() == null
					&& parent.getOutputPrimitive().size() == 0) {
				deleteTransactionCmd = new DeleteTransactionCommand(parent);
				deleteTransactionCmd.execute();
			}

		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.undo();
		}
		parent.getOutputPrimitive().add(index, primitive);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		index = parent.getOutputPrimitive().indexOf(primitive);
		parent.getOutputPrimitive().remove(primitive);
		if (deleteTransactionCmd != null) {
			deleteTransactionCmd.redo();
		}
	}

}
