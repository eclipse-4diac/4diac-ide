/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013, 2015 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import java.util.Iterator;

import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * The Class DeleteECStateCommand.
 */
public class DeleteECStateCommand extends Command {

	/** The state. */
	private final ECState state;

	/** The parent. */
	private final ECC parent;

	/** The delete actions. */
	private CompoundCommand deleteActions;

	/** The delete in transitions. */
	private CompoundCommand deleteInTransitions;

	/** The delete out transitions. */
	private CompoundCommand deleteOutTransitions;

	/**
	 * Instantiates a new delete ec state command.
	 * 
	 * @param state the state
	 */
	public DeleteECStateCommand(final ECState state) {
		super();
		this.state = state;
		parent = state.getECC();
	}
	
	@Override
	public boolean canExecute() {
		return (null != state) && (null != parent) && (parent.getStart() != state);  //don't allow inital states to be deleted
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {

		
		deleteActions = new CompoundCommand();
		for (Iterator<?> iterator = state.getECAction().iterator(); iterator
				.hasNext();) {
			ECAction ecAction = (ECAction) iterator.next();
			deleteActions.add(new DeleteECActionCommand(ecAction));
		}

		if (deleteActions.canExecute()) {
			deleteActions.execute();
		}

		deleteInTransitions = new CompoundCommand();
		for (Iterator<?> iterator = state.getInTransitions().iterator(); iterator
				.hasNext();) {
			ECTransition transition = (ECTransition) iterator.next();
			DeleteTransitionCommand cmd = new DeleteTransitionCommand(
					transition);
			deleteInTransitions.add(cmd);
		}
		if (deleteInTransitions.canExecute()) {
			deleteInTransitions.execute();
		}
		deleteOutTransitions = new CompoundCommand();
		for (Iterator<?> iterator = state.getOutTransitions().iterator(); iterator
				.hasNext();) {
			ECTransition transition = (ECTransition) iterator.next();
			DeleteTransitionCommand cmd = new DeleteTransitionCommand(
					transition);
			deleteOutTransitions.add(cmd);
		}
		if (deleteOutTransitions.canExecute()) {
			deleteOutTransitions.execute();
		}

		if (parent != null) {			
			if (state.isStartState()) {
				parent.setStart(null);
			}
			parent.getECState().remove(state);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (parent != null) {
			if (parent.getStart() == null) {
				parent.setStart(state);
			}
			parent.getECState().add(state);
		}
		deleteOutTransitions.undo();
		deleteInTransitions.undo();
		deleteActions.undo();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		deleteOutTransitions.redo();
		deleteInTransitions.redo();
		deleteActions.redo();
		if (parent != null) {
			if (state.isStartState()) {
				parent.setStart(null);
			}
			parent.getECState().remove(state);
		}
	}
}
