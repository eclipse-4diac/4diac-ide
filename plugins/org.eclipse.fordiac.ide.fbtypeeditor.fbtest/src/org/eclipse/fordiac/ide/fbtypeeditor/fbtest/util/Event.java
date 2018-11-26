/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

public class Event {
	private String eventName;
	private int eventID;
	private boolean input;
	
	public Event(String paEventName, int paEventID, boolean paIsInput) {
		eventName = paEventName;
		eventID = paEventID;
		input = paIsInput;
	}
	
	public boolean isInput() {
		return input;
	}
	public void setInput(boolean input) {
		this.input = input;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	@Override
	public String toString() {
		String retval;
		if (input) {
			retval="IN: ";
		} else {
			retval="OUT: ";
		}
		retval+=eventName+"("+eventID+")";
		return retval;
	}
	
}
