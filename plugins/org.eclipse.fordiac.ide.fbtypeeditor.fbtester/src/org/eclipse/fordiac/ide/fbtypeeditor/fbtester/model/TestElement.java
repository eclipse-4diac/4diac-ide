/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.TestEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.TestEventEditPart;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


/**
 * The Class TestElement.
 */
public class TestElement  extends PositionableElementImpl implements IEditPartCreator{

	/** The monitoring element. */
	private String monitoringElement;

	/** The element. */
	private IInterfaceElement element;

	/** The value. */
	private String value;
	
	
	private int nrOfHistory = 500;
	private List<String> historyValues = new ArrayList<>(nrOfHistory);
	private List<Integer> historyCycles = new ArrayList<>(nrOfHistory);
	private List<Long> historySec = new ArrayList<>(nrOfHistory);
	private List<Long> historyUSec = new ArrayList<>(nrOfHistory);
	private int currentInt = 0;
	

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	public String getCurrentValue() {
		if (currentInt == 0) {
			return "0";
		}
		return historyValues.get((currentInt % nrOfHistory ) -1 );
	}

	
	/**
	 * Sets the value.
	 * 
	 * @param value
	 *          the new value
	 */
	public void setValue(String value) {
		this.value = value;
		for (ISetValueListener valueListener : valueListeners){
			valueListener.setValue(this, value);
		}
	}

	/** The fb. */
	private FB fb;

	/**
	 * Gets the fb.
	 * 
	 * @return the fb
	 */
	public FB getFb() {
		return fb;
	}

	/**
	 * Sets the fb.
	 * 
	 * @param fb
	 *          the new fb
	 */
	public void setFb(FB fb) {
		this.fb = fb;
	}

	/** The monitoring element as array. */
	private String[] monitoringElementAsArray;

	/**
	 * Gets the monitoring element as string.
	 * 
	 * @return the monitoring element as string
	 */
	public String getMonitoringElementAsString() {
		return monitoringElement;
	}

	/**
	 * Sets the monitoring element.
	 * 
	 * @param monitoringElement
	 *          the new monitoring element
	 */
	public void setMonitoringElement(String monitoringElement) {
		this.monitoringElement = monitoringElement;
		monitoringElementAsArray = this.monitoringElement.split("\\.");
	}

	/**
	 * Gets the resource string.
	 * 
	 * @return the resource string
	 */
	public String getResourceString() {
		return getFBString() + "_RES";
	}

	/**
	 * Gets the fB string.
	 * 
	 * @return the fB string
	 */
	public String getFBString() {
		return "_" + getFb().getType().getName();
	}

	/**
	 * Gets the port string.
	 * 
	 * @return the port string
	 */
	public String getPortString() {
		return getInterfaceElement().getName();
	}

	/**
	 * Sets the element.
	 * 
	 * @param element
	 *          the new element
	 */
	public void setElement(IInterfaceElement element) {
		this.element = element;
	}

	/**
	 * Gets the interface element.
	 * 
	 * @return the interface element
	 */
	public IInterfaceElement getInterfaceElement() {
		return element;
	}

	/** The part. */
	private TestEditPart part;

	/**
	 * Gets the part.
	 * 
	 * @return the part
	 */
	public TestEditPart getPart() {
		return part;
	}

	@Override
	public EditPart createEditPart() {
		if (part == null) {
			if (getInterfaceElement() instanceof Event
					&& getInterfaceElement().isIsInput()) {
				part = new TestEventEditPart();
			} else {
				part = new TestEditPart();
			}
		}
		return part;
	}

	/** The value listeners. */
	private final List<ISetValueListener> valueListeners = new ArrayList<>();

	/**
	 * Adds the set value listener.
	 * 
	 * @param listener
	 *          the listener
	 */
	public void addSetValueListener(ISetValueListener listener) {
		if (!valueListeners.contains(listener)) {
			valueListeners.add(listener);
		}
	}

	/** The event listners. */
	private final List<ITriggerEventListener> eventListners = new ArrayList<>();

	/**
	 * Adds the trigger event listener.
	 * 
	 * @param listener
	 *          the listener
	 */
	public void addTriggerEventListener(ITriggerEventListener listener) {
		if (!eventListners.contains(listener)) {
			eventListners.add(listener);
		}
	}

	/**
	 * Send event.
	 */
	public void sendEvent() {
		for (ITriggerEventListener eventListner : eventListners){
			eventListner.sendEvent(this);
		}
	}

	/**
	 * Update value.
	 * 
	 * @param value
	 *          the value
	 */
	public void updateValue(final String value, int cycle) {
		historyValues.add(currentInt % nrOfHistory, value);
		historyCycles.add(currentInt % nrOfHistory, cycle);
//		historySec.add(currentInt % nrOfHistory, getSec());
//		historyUSec.add(currentInt % nrOfHistory, getUsec());
		currentInt++;
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				getPart().setValue(value);

			}
		});
	}


	/**
	 * Sets the background color of the element
	 * @param color
	 */
	public void setColor(final Color color) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				part.setBackgroundColor(color);
			}
		});
	}
	
}
