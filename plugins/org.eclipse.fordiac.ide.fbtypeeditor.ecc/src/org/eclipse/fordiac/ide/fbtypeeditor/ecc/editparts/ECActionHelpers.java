/*******************************************************************************
 * Copyright (c) 2016, 2013, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;


/** Helper functions need by the action edit parts.
 * 
 *
 */
public final class ECActionHelpers {
		
	public static List<Event> getOutputEvents(BasicFBType type) {
		List<Event> events = new ArrayList<>();
		if(null != type){
			events.addAll(type.getInterfaceList().getEventOutputs());
	
			for (AdapterDeclaration socket : type.getInterfaceList().getSockets()) {
				if(socket.getType() instanceof AdapterType){
					events.addAll(createAdapterEventList(((AdapterType) socket
						.getType()).getInterfaceList().getEventInputs(), socket));
				}
			}
	
			for (AdapterDeclaration plug : type.getInterfaceList().getPlugs()) {
				if(plug.getType() instanceof AdapterType){
					events.addAll(createAdapterEventList(((AdapterType) plug.getType())
						.getInterfaceList().getEventOutputs(), plug));
				}
			}

			Collections.sort(events, NamedElementComparator.INSTANCE);
		}

		return events;
	}

	public static List<String> getOutputEventNames(BasicFBType type) {
		List<String> eventNames = getOutputEvents(type).stream().map(ev -> ev.getName()).collect(Collectors.toList());
		eventNames.add(" "); //$NON-NLS-1$
		return eventNames;		
	}

	// TODO move to a utility class as same function is used in
	// ECTransitionEditPart
	public static List<Event> createAdapterEventList(EList<Event> events,
			AdapterDeclaration adapter) {
		List<Event> adapterEvents = new ArrayList<>();

		for (Event event : events) {
			AdapterEvent ae = LibraryElementFactory.eINSTANCE
					.createAdapterEvent();
			ae.setName(event.getName());
			ae.setComment(event.getComment());
			ae.setAdapterDeclaration(adapter);
			adapterEvents.add(ae);
		}
		return adapterEvents;
	}

	public static List<Algorithm> getAlgorithms(BasicFBType type) {
		List<Algorithm> algorithms = new ArrayList<>();
		algorithms.addAll(type.getAlgorithm());

		Collections.sort(algorithms, NamedElementComparator.INSTANCE);
		return algorithms;
	}
	
	public static List<String> getAlgorithmNames(BasicFBType type) {
		List<String> algNames = getAlgorithms(type).stream().map(alg -> alg.getName()).collect(Collectors.toList());
		algNames.add(" "); //$NON-NLS-1$
		return algNames;
	}

	public static BasicFBType getFBType(ECAction action) {
		if (action.eContainer() != null
				&& action.eContainer().eContainer() != null
				&& action.eContainer().eContainer().eContainer() != null) {
			return (BasicFBType) action.eContainer().eContainer().eContainer();
		}
		return null;
	}
	
	static boolean setOutputEventRunning = false;
	
	private ECActionHelpers() {
		throw new UnsupportedOperationException("ECActionHelpers should not be instantiated!");
	}
}
