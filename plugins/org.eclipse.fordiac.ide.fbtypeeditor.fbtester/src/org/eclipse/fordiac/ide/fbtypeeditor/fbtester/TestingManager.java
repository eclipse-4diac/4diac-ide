/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ISetValueListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.ITriggerEventListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;


public final class TestingManager {

	private static TestingManager instance;

	public static TestingManager getInstance() {
		if (instance == null) {
			instance = new TestingManager();
		}
		return instance;
	}

	private TestingManager() {
	}

	private final Map<String, TestElement> testElements = new HashMap<>();
	private final Map<String, TestElement> triggerElements = new HashMap<>();

	public void addTriggerElement(TestElement element) {
		triggerElements.put(generateElementString(element), element);
	}
	
	private static String generateElementString(TestElement element) {
		return element.getFBString() + "_RES." + element.getFBString() + "." + element.getInterfaceElement().getName(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public Map<String, TestElement> getTriggerElements(FBType type) {
		Map<String, TestElement> elements = new HashMap<>();
		for (Entry<String, TestElement> entry: triggerElements.entrySet()) {
			TestElement element = entry.getValue();
			if ((element.getFb().getType().equals(type)) && (element.getInterfaceElement() instanceof Event)) {
				elements.put(entry.getKey(), element);
			}
		}
		return elements;
	}

	public void addTestElement(TestElement element) {
		testElements.put(generateElementString(element), element);
	}

	public Map<String, TestElement> getTestElements(FBType type,
			ISetValueListener valueListener, ITriggerEventListener eventListener) {
		Map<String, TestElement> elements = new HashMap<>();
		for (Entry<String, TestElement> entry : testElements.entrySet()) {
			TestElement element = entry.getValue();
			if (element.getFb().getType().equals(type)) {
				element.addSetValueListener(valueListener);
				element.addTriggerEventListener(eventListener);
				if ( (element.getInterfaceElement() instanceof VarDeclaration) || 
					((element.getInterfaceElement() instanceof Event && !element.getInterfaceElement().isIsInput()))) {
					elements.put(entry.getKey(), element);
				}

			}
		}
		return elements;
	}

	public Map<String, TestElement> getTestElements(FBType type) {
		Map<String, TestElement> elements = new HashMap<String, TestElement>();
		for (Entry<String, TestElement> entry : testElements.entrySet()) {
			TestElement element = entry.getValue();
			if (element.getFb().getType().equals(type)) {
				if ((element.getInterfaceElement() instanceof VarDeclaration) ||  
						((element.getInterfaceElement() instanceof Event && !element.getInterfaceElement().isIsInput()))) {
					elements.put(entry.getKey(), element);
				}
			}
		}
		return elements;
	}


}
