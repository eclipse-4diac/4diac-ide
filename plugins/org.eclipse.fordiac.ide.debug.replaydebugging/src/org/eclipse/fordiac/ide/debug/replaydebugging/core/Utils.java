/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;

public class Utils {

	private Utils() {
	}

	/**
	 * Collects all ports from the device and its resources, applications, and
	 * networks.
	 *
	 * @param device the device to collect ports from
	 * @return a map where keys are resource names and values are sets of port names
	 *         (qualified names)
	 */
	public static Map<String, Set<String>> collectAllPorts(final Device device) {
		final Map<String, Set<String>> result = new HashMap<>();
		for (final Resource resource : device.getResource()) {
			result.put(resource.getName(), new HashSet<>());
			collectAllPorts(resource, result);
		}
		for (final Application application : device.getAutomationSystem().getApplication()) {
			collectAllPorts(application, result);
		}

		return result;
	}

	private static void collectAllPorts(final Resource resource, final Map<String, Set<String>> result) {
		collectAllPorts(resource.getFBNetwork(), result);
	}

	private static void collectAllPorts(final Application application, final Map<String, Set<String>> result) {
		collectAllPorts(application.getFBNetwork(), result);
	}

	private static void collectAllPorts(final FBNetwork network, final Map<String, Set<String>> result) {
		if (network == null) {
			return; // no network, nothing to collect
		}
		network.getBlockFBNetworkElements().forEach(networkElement -> {
			collectAllPorts(networkElement.getInterface(), result);
			if (networkElement instanceof final CFBInstanceImpl composite) {
				collectAllPorts(composite.loadCFBNetwork(), result); // recursive call to collect ports from nested
																		// networks
			} else if (networkElement instanceof final SubAppImpl subApp) {
				collectAllPorts(subApp.loadSubAppNetwork(), result); // recursive call to collect ports from sub-app
																		// networks
			}
		});
	}

	private static void collectAllPorts(final InterfaceList interfaceList, final Map<String, Set<String>> result) {
		final String deviceName = interfaceList.getBlockFBNetworkElement().getResource().getDevice().getName();
		final String resourceName = interfaceList.getBlockFBNetworkElement().getResource().getName();
		final String prefix = deviceName + "." + resourceName + "."; //$NON-NLS-1$ //$NON-NLS-2$
		for (final Event event : interfaceList.getEventInputs()) {
			addElementToResult(event, result.get(resourceName), prefix);
		}
		for (final Event event : interfaceList.getEventOutputs()) {
			addElementToResult(event, result.get(resourceName), prefix);
		}
		for (final VarDeclaration variable : interfaceList.getInputVars()) {
			addElementToResult(variable, result.get(resourceName), prefix);
		}
		for (final VarDeclaration variable : interfaceList.getOutputVars()) {
			addElementToResult(variable, result.get(resourceName), prefix);
		}
		// check also adapters later
	}

	private static void addElementToResult(final INamedElement element, final Set<String> result, final String prefix) {
		String toAdd = element.getQualifiedName();
		if (toAdd.startsWith(prefix)) {
			toAdd = toAdd.substring(prefix.length()); // remove the prefix if present
		}
		result.add(toAdd);
	}
}
