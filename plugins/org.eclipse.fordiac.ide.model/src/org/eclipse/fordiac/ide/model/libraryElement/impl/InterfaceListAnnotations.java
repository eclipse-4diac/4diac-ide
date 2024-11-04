/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Monika Wenger - extracted the model helper methods into this annotations class
 *   Monika Wenger - introduced IEC 61499 attribute support into the model
 *   Alois Zoitl   - reworked model helper functions for better mapping and sub-app support
 *   Hesam Rezaee  - add variable configuration for global constants
 *   Alois Zoitl   - extracted from org.eclipse.fordiac.ide.model.Annotations
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

final class InterfaceListAnnotations {

	// *** InterfaceList ***
	public static EList<IInterfaceElement> getAllInterfaceElements(final InterfaceList il) {
		final EList<IInterfaceElement> retVal = new BasicEList<>();
		retVal.addAll(il.getEventInputs());
		retVal.addAll(il.getInputVars());
		retVal.addAll(il.getInOutVars());
		retVal.addAll(il.getSockets());
		retVal.addAll(il.getEventOutputs());
		retVal.addAll(il.getOutputVars());
		// Users of getAllInterfaceElements expect to get all elements for ui and
		// connection checks. Therefore we need
		// to deliver also the mapped output side here
		retVal.addAll(il.getOutMappedInOutVars());
		retVal.addAll(il.getPlugs());
		retVal.addAll(il.getErrorMarker());
		return retVal;
	}

	public static Event getEvent(final InterfaceList il, final String name) {
		for (final Event event : il.getEventInputs()) {
			if (event.getName().equals(name)) {
				return event;
			}
		}
		for (final Event event : il.getEventOutputs()) {
			if (event.getName().equals(name)) {
				return event;
			}
		}
		return null;
	}

	public static VarDeclaration getVariable(final InterfaceList il, final String name) {
		for (final VarDeclaration inVar : il.getInputVars()) {
			if (inVar.getName().equals(name)) {
				return inVar;
			}
		}
		for (final VarDeclaration outVar : il.getOutputVars()) {
			if (outVar.getName().equals(name)) {
				return outVar;
			}
		}
		for (final VarDeclaration inOutVar : il.getInOutVars()) {
			if (inOutVar.getName().equals(name)) {
				return inOutVar;
			}
		}
		return null;
	}

	public static IInterfaceElement getInterfaceElement(final InterfaceList il, final String name) {
		IInterfaceElement element = il.getEvent(name);
		if (element == null) {
			element = il.getVariable(name);
		}
		if (element == null) {
			element = il.getAdapter(name);
		}

		if (element == null) {
			element = il.getErrorMarker().stream().filter(e -> e.getName().equals(name)).findAny().orElse(null);
		}

		return element;
	}

	public static FBNetworkElement getFBNetworkElement(final InterfaceList il) {
		// an FB should mostly in an FBNetworkElement otherwise it is in CFB interface
		// this is at the same time also a null check
		return (il.eContainer() instanceof final FBNetworkElement fbnEl) ? fbnEl : null;
	}

	public static AdapterDeclaration getAdapter(final InterfaceList il, final String name) {
		for (final AdapterDeclaration adapt : il.getPlugs()) {
			if (adapt.getName().equals(name)) {
				return adapt;
			}
		}
		for (final AdapterDeclaration adapt : il.getSockets()) {
			if (adapt.getName().equals(name)) {
				return adapt;
			}
		}
		return null;
	}

	public static Stream<IInterfaceElement> getInputs(final InterfaceList il) {
		return Stream.concat(Stream.concat(il.getEventInputs().stream(), il.getInputVars().stream()),
				Stream.concat(il.getInOutVars().stream(), il.getSockets().stream()));
	}

	public static Stream<IInterfaceElement> getOutputs(final InterfaceList il) {
		return Stream.concat(Stream.concat(il.getEventOutputs().stream(), il.getOutputVars().stream()),
				Stream.concat(il.getOutMappedInOutVars().stream(), il.getPlugs().stream()));
	}

	private InterfaceListAnnotations() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
