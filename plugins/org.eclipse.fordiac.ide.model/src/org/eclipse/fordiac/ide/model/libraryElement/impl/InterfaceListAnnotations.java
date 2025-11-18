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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ContainerVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

final class InterfaceListAnnotations {

	public static Stream<IInterfaceElement> getAllInputs(final InterfaceList il) {
		// Users of getAllInterfaceElements expect to get all elements for ui and
		// connection checks. Therefore we need to deliver also the member access pins
		// as well as the mapped output side of var_in_outs here
		// @formatter:off
		return Stream.concat(
				Stream.concat(
					Stream.of(il.getEventInputs(),
							il.getInOutVars(),
							il.getSockets())
						.flatMap(List::stream),
					il.getInputVars().stream().flatMap(InterfaceListAnnotations::flattenAccessPins)),
				il.getErrorMarker().stream().filter(ErrorMarkerInterface::isIsInput)
			);
		// @formatter:on
	}

	public static Stream<IInterfaceElement> getAllOutputs(final InterfaceList il) {
		// Users of getAllInterfaceElements expect to get all elements for ui and
		// connection checks. Therefore we need to deliver also the member access pins
		// as well as the mapped output side of var_in_outs here
		// @formatter:off
		return Stream.concat(
				Stream.concat(
					Stream.of(il.getEventOutputs(),
						il.getOutMappedInOutVars(),
						il.getPlugs())
					.flatMap(List::stream),
					il.getOutputVars().stream().flatMap(InterfaceListAnnotations::flattenAccessPins)),
				il.getErrorMarker().stream().filter(em -> !em.isIsInput())
			);
		// @formatter:on
	}

	// *** InterfaceList ***
	public static Stream<IInterfaceElement> getAllInterfaceElements(final InterfaceList il) {
		return Stream.concat(getAllInputs(il), getAllOutputs(il));
	}

	private static Stream<IInterfaceElement> flattenAccessPins(final IInterfaceElement ie) {
		if (ie instanceof final ContainerVarDeclaration contVarDecl) {
			return Stream.concat(Stream.of(ie),
					contVarDecl.getCachedMembers().stream().flatMap(InterfaceListAnnotations::flattenAccessPins));
		}
		return Stream.of(ie);
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

	public static IInterfaceElement getInterfaceElement(final InterfaceList il, final List<String> path) {
		return getInterfaceElement(il, path, false);
	}

	public static IInterfaceElement getInput(final InterfaceList il, final List<String> path) {
		final IInterfaceElement ie = getRootInput(il, path.get(0));

		if (path.size() == 1) {
			return ie;
		}

		return handleMemberAccess(ie, path, false);
	}

	public static IInterfaceElement getOutput(final InterfaceList il, final List<String> path) {
		final IInterfaceElement ie = getRootOutput(il, path.get(0));

		if (path.size() == 1) {
			return ie;
		}

		return handleMemberAccess(ie, path, false);
	}

	public static IInterfaceElement getInterfaceElement(final InterfaceList il, final IInterfaceElement refElement) {
		if (refElement == null || il == refElement.eContainer()) {
			return refElement;
		}

		final List<String> path = refElement.getBlockRelativePath();
		return (refElement.isIsInput()) ? getInput(il, path) : getOutput(il, path);
	}

	public static IInterfaceElement getInterfaceElement(final InterfaceList il, final List<String> path,
			final boolean demandCreate) {
		if (path.isEmpty()) {
			return null;
		}

		final IInterfaceElement ie = getRootIInterfaceElement(il, path.get(0));

		if (path.size() == 1) {
			return ie;
		}

		return handleMemberAccess(ie, path, demandCreate);
	}

	private static IInterfaceElement handleMemberAccess(final IInterfaceElement ie, final List<String> path,
			final boolean demandCreate) {
		if (!(ie instanceof final ContainerVarDeclaration contVarDecl)) {
			return null;
		}
		return contVarDecl.getCachedMember(path.subList(1, path.size()), demandCreate);
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

	private static Stream<IInterfaceElement> getRootInputs(final InterfaceList il) {
		return Stream.of(il.getEventInputs(), il.getInputVars(), il.getInOutVars(), il.getSockets())
				.flatMap(List::stream);
	}

	private static Stream<IInterfaceElement> getRootOutputs(final InterfaceList il) {
		return Stream.of(il.getEventOutputs(), il.getOutputVars(), il.getOutMappedInOutVars(), il.getPlugs())
				.flatMap(List::stream);
	}

	private static Stream<IInterfaceElement> getRootInterfaceElements(final InterfaceList il) {
		return Stream.concat(getRootInputs(il), getRootOutputs(il));
	}

	private static IInterfaceElement getRootInput(final InterfaceList il, final String name) {
		return getRootInputs(il).filter(ie -> ie.getName().equals(name)).findAny().orElse(null);
	}

	private static IInterfaceElement getRootOutput(final InterfaceList il, final String name) {
		return getRootOutputs(il).filter(ie -> ie.getName().equals(name)).findAny().orElse(null);
	}

	private static IInterfaceElement getRootIInterfaceElement(final InterfaceList il, final String name) {
		return getRootInterfaceElements(il).filter(ie -> ie.getName().equals(name)).findFirst().orElse(null);
	}

	private InterfaceListAnnotations() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
