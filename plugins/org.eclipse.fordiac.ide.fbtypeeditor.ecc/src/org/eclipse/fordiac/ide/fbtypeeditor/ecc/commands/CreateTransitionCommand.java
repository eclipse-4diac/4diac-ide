/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2016, 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Melik-Merkumians
 *     - adds constructor and convenience constructor for code generation purposes
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateTransitionCommand.
 */
public class CreateTransitionCommand extends Command {

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
		super();
	}

	/**
	 * Convenience constructor preloading the command with the required parameters
	 * 
	 * The rationale for this convenience constructor is, that in programmatic code
	 * generation, all these parameters are known when the command is generated.
	 * With this constructor the needed code for code generation can be reduced
	 * 
	 * @param source
	 *            The starting state of the transition
	 * @param destination
	 *            The end state of the transition
	 * @param conditionEvent
	 *            The event triggering the transition
	 */
	public CreateTransitionCommand(ECState source, ECState destination, Event conditionEvent) {
		this.source = source;
		this.sourceLocation = new Point(this.source.getX(), this.source.getY());
		this.destination = destination;
		this.destLocation = new Point(this.destination.getX(), this.destination.getY());
		this.conditionEvent = conditionEvent;
	}

	public String getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(String condition) {
		this.conditionExpression = condition;
	}

	public void setConditionEvent(Event conditionEvent) {
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
	 * @param source
	 *            the new source
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
	 * @param destination
	 *            the new destination
	 */
	public void setDestination(final ECState destination) {
		this.destination = destination;
	}

	@Override
	public boolean canExecute() {
		return (null != source && null != destination && null != source.eContainer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parent = (ECC) source.eContainer();

		transition = LibraryElementFactory.eINSTANCE.createECTransition();

		int x1 = sourceLocation.x;
		int x2 = destLocation.x;
		int y1 = sourceLocation.y;
		int y2 = destLocation.y;

		int x = 0;
		int y = 0;

		if (x1 > x2) {
			x = (x1 - x2) / 2 + x2;
		} else if (x2 > x1) {
			x = (x2 - x1) / 2 + x1;
		} else {
			x = x1;
		}

		if (y1 > y2) {
			y = (y1 - y2) / 2 + y2;
		} else if (y2 > y1) {
			y = (y2 - y1) / 2 + y1;
		} else {
			y = y1;
		}

		parent.getECTransition().add(transition);

		// it is necessary to invode the following code after adding the
		// transition to the parent, otherwise ECTransitionEditPart will
		// throw an nullpointer in the activate method!

		if (source.equals(destination)) { // self transition
			transition.setX(x + 10);
			transition.setY(y + 50);
		} else {
			transition.setX(x);
			transition.setY(y);
		}
		transition.setSource(source);
		transition.setDestination(destination);
		transition.setConditionEvent(conditionEvent);

		if (conditionExpression != null) {
			transition.setConditionExpression(conditionExpression);
		} else {
			if (conditionEvent == null) {
				transition.setConditionExpression("1"); //$NON-NLS-1$
			}
		}
		if (null != viewer) {
			Object obj = viewer.getEditPartRegistry().get(transition);
			if (null != obj) {
				viewer.select((EditPart) obj);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return parent != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transition.setSource(null);
		transition.setDestination(null);
		parent.getECTransition().remove(transition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		transition.setSource(source);
		transition.setDestination(destination);
		parent.getECTransition().add(transition);
	}

	/**
	 * Sets the source location.
	 * 
	 * @param location
	 *            the new source location
	 */
	public void setSourceLocation(final Point location) {
		this.sourceLocation = location;
	}

	/**
	 * Sets the destination location.
	 * 
	 * @param location
	 *            the new destination location
	 */
	public void setDestinationLocation(final Point location) {
		this.destLocation = location;

	}

	public void setViewer(EditPartViewer viewer) {
		this.viewer = viewer;
	}
}
