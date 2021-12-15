/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 * 				 2021 Primetals Technologies Austria GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *   Christoph Binder - Element deletion methods
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

	public void addTriggerElement(final TestElement element) {
		triggerElements.put(generateElementString(element), element);
	}
	
	public void deleteTriggerElement(TestElement element) {
		triggerElements.remove(generateElementString(element));
	}

	private static String generateElementString(final TestElement element) {
		return element.getFBString() + "_RES." + element.getFBString() + "." + element.getInterfaceElement().getName(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public Map<String, TestElement> getTriggerElements(final FBType type) {
		final Map<String, TestElement> elements = new HashMap<>();
		for (final Entry<String, TestElement> entry : triggerElements.entrySet()) {
			final TestElement element = entry.getValue();
			if ((element.getFb().getType().equals(type)) && (element.getInterfaceElement() instanceof Event)) {
				elements.put(entry.getKey(), element);
			}
		}
		return elements;
	}

	public void addTestElement(final TestElement element) {
		testElements.put(generateElementString(element), element); 
	}
	
	public void deleteTestElement(TestElement element) {
		testElements.remove(generateElementString(element));
	}

	public Map<String, TestElement> getTestElements(final FBType type, final ISetValueListener valueListener,
			final ITriggerEventListener eventListener) {
		final Map<String, TestElement> elements = new HashMap<>();
		for (final Entry<String, TestElement> entry : testElements.entrySet()) {
			final TestElement element = entry.getValue();
			if (element.getFb().getType().getName() ==  type.getName()) {
				element.addSetValueListener(valueListener);
				element.addTriggerEventListener(eventListener);
				if ((element.getInterfaceElement() instanceof VarDeclaration)
						|| ((element.getInterfaceElement() instanceof Event)
								&& (!element.getInterfaceElement().isIsInput()))) {
					elements.put(entry.getKey(), element);
				}

			}
		}
		return elements;
	}
}
