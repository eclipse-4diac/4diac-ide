/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 Profactor GmbH, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import java.util.Hashtable;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ISetValueListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ITriggerEventListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;


public class TestingManager {

	private static TestingManager instance;

	public static TestingManager getInstance() {
		if (instance == null) {
			instance = new TestingManager();
		}
		return instance;
	}

	private TestingManager() {
	}

	private final Hashtable<String, TestElement> testElements = new Hashtable<String, TestElement>();
	private final Hashtable<String, TestElement> triggerElements = new Hashtable<String, TestElement>();

	public void addTriggerElement(TestElement element) {
		triggerElements.put(element.getFBString() + "_RES." + element.getFBString()
				+ "." + element.getInterfaceElement().getName(), element);
	}

	public Hashtable<String, TestElement> getTriggerElements(FBType type) {
		Hashtable<String, TestElement> elements = new Hashtable<String, TestElement>();
		for (String key : triggerElements.keySet()) {
			TestElement element = triggerElements.get(key);
			if (element.getFb().getType().equals(type)) {
				if (element.getInterfaceElement() instanceof Event) {
					elements.put(key, element);
				}
			}
		}
		return elements;
	}

	public void addTestElement(TestElement element) {
		testElements.put(element.getFBString() + "_RES." + element.getFBString()
				+ "." + element.getInterfaceElement().getName(), element);
	}

	public Hashtable<String, TestElement> getTestElements(FBType type,
			ISetValueListener valueListener, ITriggerEventListener eventListener) {
		Hashtable<String, TestElement> elements = new Hashtable<String, TestElement>();
		for (String key : testElements.keySet()) {
			TestElement element = testElements.get(key);
			if (element.getFb().getType().equals(type)) {
				element.addSetValueListener(valueListener);
				element.addTriggerEventListener(eventListener);
				if (element.getInterfaceElement() instanceof VarDeclaration) {
					elements.put(key, element);
				} else if (element.getInterfaceElement() instanceof Event
						&& !element.getInterfaceElement().isIsInput()) {
					elements.put(key, element);
				}

			}
		}
		return elements;
	}

	public Hashtable<String, TestElement> getTestElements(FBType type) {
		Hashtable<String, TestElement> elements = new Hashtable<String, TestElement>();
		for (String key : testElements.keySet()) {
			TestElement element = testElements.get(key);
			if (element.getFb().getType().equals(type)) {
				if (element.getInterfaceElement() instanceof VarDeclaration) {
					elements.put(key, element);
				} else if (element.getInterfaceElement() instanceof Event
						&& !element.getInterfaceElement().isIsInput()) {
					elements.put(key, element);
				}

			}
		}
		return elements;
	}


}
