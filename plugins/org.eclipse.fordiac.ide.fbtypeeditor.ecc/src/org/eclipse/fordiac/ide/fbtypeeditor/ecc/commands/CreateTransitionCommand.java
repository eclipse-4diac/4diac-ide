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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;

/**
 * The Class CreateTransitionCommand.
 */
public class CreateTransitionCommand extends CreationCommand {
	static final Point SELF_TRANS_OFFSET = new Point(10, 50);

	/** The source. */
	private ECState source;

	/** The destination. */
	private ECState destination;

	/** The source location. */
	private Point sourceLocation;

	/** The dest location. */
	private Point destLocation;

	/** The parent. */
	private ECC parent;

	/** The transition. */
	private ECTransition transition;

	/** Transition Condition Expression */
	private String conditionExpression;

	/** Transition condition event */
	private Event conditionEvent;

	/** the viewer which executed this command */
	private EditPartViewer viewer;

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
		this.sourceLocation = source.getPosition().toScreenPoint();
		this.destination = destination;
		if (destination.getPosition() != null) {
			this.destLocation = destination.getPosition().toScreenPoint();
		}
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
		return ((null != source) && (null != destination) && (null != source.getECC()) && (null != destLocation));
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
		transition.updatePositionFromScreenCoordinates(calcTransitionBendPoint());
		transition.setSource(source);
		transition.setDestination(destination);
		transition.setConditionEvent(conditionEvent);

		if (conditionExpression != null) {
			transition.setConditionExpression(conditionExpression);
		} else if (conditionEvent == null) {
			transition.setConditionExpression("1"); //$NON-NLS-1$
		}
		if (null != viewer) {
			final EditPart ep = viewer.getEditPartForModel(transition);
			if (ep != null) {
				viewer.select(ep);
			}
		}
	}

	private Point calcTransitionBendPoint() {
		final Point bendPoint = sourceLocation.getCopy();
		bendPoint.translate(destLocation.getDifference(sourceLocation).scale(0.5)); // middle between source and dest
		if (source.equals(destination)) { // self transition
			bendPoint.translate(SELF_TRANS_OFFSET);
		}
		return bendPoint;
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

	/**
	 * Sets the source location.
	 *
	 * @param location the new source location
	 */
	public void setSourceLocation(final Point location) {
		this.sourceLocation = location;
	}

	/**
	 * Sets the destination location.
	 *
	 * @param location the new destination location
	 */
	public void setDestinationLocation(final Point location) {
		this.destLocation = location;

	}

	public void setViewer(final EditPartViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public Object getCreatedElement() {
		return transition;
	}
}
