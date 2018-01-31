/*******************************************************************************
 * Copyright (c) 2011, 2015, 2016 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionHelpers;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.commands.Command;

public class ChangeConditionEventCommand extends Command {

	private final ECTransition transition;
	private final ArrayList<Event> eventList = new ArrayList<Event>();
	private String conditionEvent;
	private String oldConditionEvent;
	
	//if the string is 1 we need to set capture the condition expression
	private String oldConditionExpression;
	
	
	/** 
	 *  @param transition
	 *  @param conditonEvent  name of the new event for the transition condition, in addition to an 
	 *  						event name the values may also be an empty string for setting the no 
	 *                          event on the transition condition or 1 for setting the transition condtiion to always true.
	 */
	public ChangeConditionEventCommand(final ECTransition transition, final String conditionEvent) {
		super();
		this.transition = transition;
		this.conditionEvent = conditionEvent;
		getEvents();
	}

	@Override
	public boolean canExecute() {
		return conditionEvent.equals("") || (eventList != null && ! eventList.isEmpty()); //$NON-NLS-1$
	}
	
	public ArrayList<Event> getEvents(){
		eventList.clear();
		BasicFBType types = (BasicFBType) transition.eContainer().eContainer();
		this.eventList.addAll(types.getInterfaceList().getEventInputs());
		for (AdapterDeclaration socket : types.getInterfaceList().getSockets()) {
			if(socket.getType() instanceof AdapterType){
				this.eventList.addAll(ECActionHelpers.createAdapterEventList(
					((AdapterType) socket.getType()).getInterfaceList().getEventOutputs(), socket));
			}
		}
		for (AdapterDeclaration plug : types.getInterfaceList().getPlugs()) {
			if(plug.getType() instanceof AdapterType){
				this.eventList.addAll(ECActionHelpers.createAdapterEventList(
					((AdapterType) plug.getType()).getInterfaceList().getEventInputs(), plug));
			}
		}
		return eventList;
	}
	
	@Override
	public void execute() {
		oldConditionEvent = transition.getConditionEvent() != null ? transition.getConditionEvent().getName() : ""; //$NON-NLS-1$
		if(conditionEvent.equals("1")){ //$NON-NLS-1$
			oldConditionExpression = transition.getConditionExpression();
		}
		if("1".equals(transition.getConditionExpression())){ //$NON-NLS-1$
			oldConditionExpression = transition.getConditionExpression();
			transition.setConditionExpression(""); //$NON-NLS-1$
		}
		redo();
	}

	@Override
	public void undo() {
		if(null != oldConditionExpression){
			transition.setConditionExpression(oldConditionExpression);
		}
		transition.setConditionEvent(getEvent(oldConditionEvent));
	}

	@Override
	public void redo() {
		if(conditionEvent.equals("1")){ //$NON-NLS-1$
			// one has been selected
			transition.setConditionExpression("1"); //$NON-NLS-1$
			transition.setConditionEvent(null);
		}else {
			transition.setConditionEvent(getEvent(conditionEvent));
		}
	}
	
	private Event getEvent(String event){
		for(Event e : eventList){
			if(e.getName().equals(event)){
				return e;
			}
		}
		return null;
	}
}
