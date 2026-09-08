/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2016, 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *   Martin Melik-Merkumians
 *     - adds constructor and convenience constructor for code generation purposes
 *   Bianca Wiesmayr
 *     - command now returns newly created element
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/**
 * The Class CreateTransitionCommand.
 */
public class CreateTransitionCommand extends CreationCommand {

	/** The source. */
	private ECState source;

	/** The destination. */
	private ECState destination;

	/** The parent. */
	private ECC parent;

	/** The transition. */
	private ECTransition transition;

	/** Transition Condition Expression */
	private String conditionExpression;

	/** Transition condition event */
	private Event conditionEvent;

	/** Offset used for the X-coordinate of the bend point in a self-transition. */
	private static final int SELF_TRANSITION_X_OFFSET = -50;

	/** Offset used for the Y-coordinate of the bend point in a self-transition. */
	private static final int SELF_TRANSITION_Y_OFFSET = 15;

	public CreateTransitionCommand() {
	}

	/**
	 * Convenience constructor preloading the command with the required parameters
	 *
	 * The rationale for this convenience constructor is, that in programmatic code
	 * generation, all these parameters are known when the command is generated.
	 * With this constructor the needed code for code generation can be reduced
	 *
	 * @param source         The starting state of the transition
	 * @param destination    The end state of the transition
	 * @param conditionEvent The event triggering the transition
	 */
	public CreateTransitionCommand(final ECState source, final ECState destination, final Event conditionEvent) {
		this.source = source;
		this.destination = destination;
		this.conditionEvent = conditionEvent;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(final String condition) {
		this.conditionExpression = condition;
	}

	public void setConditionEvent(final Event conditionEvent) {
		this.conditionEvent = conditionEvent;
	}

	public Event getConditionEvent() {
		return conditionEvent;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public ECState getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(final ECState source) {
		this.source = source;
	}

	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public ECState getDestination() {
		return destination;
	}

	/**
	 * Sets the destination.
	 *
	 * @param destination the new destination
	 */
	public void setDestination(final ECState destination) {
		this.destination = destination;
	}

	@Override
	public boolean canExecute() {
		return ((null != source) && (null != destination) && (null != source.getECC()));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent = source.getECC();

		transition = LibraryElementFactory.eINSTANCE.createECTransition();

		parent.getECTransition().add(transition);

		// it is necessary to invoke the following code after adding the
		// transition to the parent, otherwise ECTransitionEditPart will
		// throw a NPE in the activate method!
		transition.setPosition(calcTransitionBendPoint());
		transition.setSource(source);
		transition.setDestination(destination);
		transition.setConditionEvent(conditionEvent);

		if (conditionExpression != null) {
			transition.setConditionExpression(conditionExpression);
		} else if (conditionEvent == null) {
			transition.setConditionExpression("1"); //$NON-NLS-1$
		}
	}

	private Position calcTransitionBendPoint() {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		if (source.equals(destination)) { // self transition
			pos.setX(source.getPosition().getX() + SELF_TRANSITION_X_OFFSET);
			pos.setY(source.getPosition().getY() + SELF_TRANSITION_Y_OFFSET);
		} else {
			pos.setX((source.getPosition().getX() + destination.getPosition().getX()) / 2.0);
			pos.setY((source.getPosition().getY() + destination.getPosition().getY()) / 2.0);
		}
		return pos;
	}

	@Override
	public boolean canUndo() {
		return parent != null;
	}

	@Override
	public void undo() {
		transition.setSource(null);
		transition.setDestination(null);
		parent.getECTransition().remove(transition);
	}

	@Override
	public void redo() {
		// Before setting source and destination the transition needs to be added to the
		// ECC otherwise we get NPE in the transition editpart when the adapter to the
		// ECC is created
		parent.getECTransition().add(transition);
		transition.setSource(source);
		transition.setDestination(destination);
	}

	@Override
	public Object getCreatedElement() {
		return transition;
	}
}