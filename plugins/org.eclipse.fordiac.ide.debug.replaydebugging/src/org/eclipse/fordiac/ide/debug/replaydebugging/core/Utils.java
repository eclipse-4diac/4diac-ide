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

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;

public class Utils {

	private Utils() {
	}

	private static final Set<String> SIFB_IN_FORTE_NOT_IN_IDE = Set.of("E_CYCLE");

	/**
	 * Collects all ports from the device and its resources, applications, and
	 * networks.
	 *
	 * @param device the device to collect ports from
	 * @return a map where keys are resource names and values are sets of port names
	 *         (qualified names)
	 */
	public static Map<String, Set<String>> collectAllValueHolders(final Device device) {
		return transformToNames(collectAllValueHolderElements(device));
	}

	public static Map<String, Set<IInterfaceElement>> collectAllValueHolderElements(final Device device) {
		final Map<String, Set<IInterfaceElement>> result = new HashMap<>();
		for (final Resource resource : device.getResource()) {
			result.put(resource.getName(), new HashSet<>());
			collectAllValueHolderElements(resource, result);
		}

		return result;
	}

	public static Map<String, Set<String>> transformToNames(final Map<String, Set<IInterfaceElement>> elements) {
		final Map<String, Set<String>> result = new HashMap<>();
		for (final var entry : elements.entrySet()) {
			final Set<String> names = new HashSet<>();
			for (final IInterfaceElement interfaceElement : entry.getValue()) {
				names.add(getWatchName(interfaceElement));
			}
			result.put(entry.getKey(), names);
		}
		return result;
	}

	public static String getWatchName(final IInterfaceElement interfaceElement) {
		final String deviceName = interfaceElement.getBlockFBNetworkElement().getResource().getDevice().getName();
		final String resourceName = interfaceElement.getBlockFBNetworkElement().getResource().getName();
		final String prefix = deviceName + "." + resourceName + ".";
		final String toAdd = interfaceElement.getQualifiedName();
		if (toAdd.startsWith(prefix)) {
			return toAdd.substring(prefix.length()); // remove the prefix if present
		}
		return toAdd;
	}

	public static boolean isSIFB(final FBType fbType) {
		if (SIFB_IN_FORTE_NOT_IN_IDE.contains(fbType.getName())) {
			return true;
		}

		return (fbType instanceof ServiceInterfaceFBType);
	}

	// returns the instance and its parent.
	public static FB getInstanceFB(final Resource resource, final String fbName) {

		// look in the resource first
		final FB fb = resource.getFBNetwork().getFBNamed(fbName);
		if (fb != null) {
			return fb;
		}
		// then look in the applications
		if (!fbName.contains(".")) { //$NON-NLS-1$
			return null; // no point in looking further if there is no dot
		}

		final var prefix = fbName.substring(0, fbName.indexOf('.'));

		// check if the prefix is an application
		final var application = resource.getAutomationSystem().getApplication().stream()
				.filter(app -> app.getName().equals(prefix)).findFirst();
		if (!application.isPresent()) {
			System.err.println("Application name " + prefix + " does not exist"); //$NON-NLS-1$
			return null;
		}

		final var suffix = fbName.substring(fbName.indexOf('.') + 1);
		return getInstanceFB(resource.getFBNetwork(), suffix);
	}

	private static FB getInstanceFB(final FBNetwork network, final String name) {
		if (network == null) {
			return null;
		}
		if (!name.contains(".")) {//$NON-NLS-1$
			return network.getFBNamed(name);
		}

		final var prefix = name.substring(0, name.indexOf('.'));
		final var suffix = name.substring(name.indexOf('.') + 1);

		for (final var networkElement : network.getNetworkElements()) {
			if (networkElement.getName().equals(prefix)) {
				if (networkElement instanceof final CFBInstance cfb) {
					return getInstanceFB(cfb.loadCFBNetwork(), suffix);
				}
				if (networkElement instanceof final SubApp subApp) {
					var internalNetwork = subApp.loadSubAppNetwork();
					if (internalNetwork == null) {
						internalNetwork = ((SubApp) subApp.getOpposite()).loadSubAppNetwork();
					}
					return getInstanceFB(internalNetwork, suffix);
				}
				System.err.println("Element with name " + prefix + " has not internal network"); //$NON-NLS-1$
			}
		}
		return null;
	}

	private static void collectAllValueHolderElements(final Resource resource,
			final Map<String, Set<IInterfaceElement>> result) {
		collectAllValueHolderElements(resource.getFBNetwork(), result);
	}

	private static void collectAllValueHolderElements(final FBNetwork network,
			final Map<String, Set<IInterfaceElement>> result) {
		if (network == null) {
			return; // no network, nothing to collect
		}

		// collect value holders from the current network
		final TreeIterator<EObject> it = network.eAllContents();
		while (it.hasNext()) {
			final EObject obj = it.next();
			if (obj instanceof final IInterfaceElement varDecl) {
				result.get(varDecl.getBlockFBNetworkElement().getResource().getName()).add(varDecl);

			}
		}

		// go deeper into network elements
		for (final FBNetworkElement networkElement : network.getNetworkElements()) {
			if (networkElement instanceof final CFBInstanceImpl composite) {
				collectAllValueHolderElements(composite.loadCFBNetwork(), result); // recursive call to collect ports
																					// from nested networks
			} else if (networkElement instanceof final SubAppImpl subApp) {
				var internalNetwork = subApp.loadSubAppNetwork();
				if (internalNetwork == null) {
					internalNetwork = ((SubApp) subApp.getOpposite()).loadSubAppNetwork();
				}
				collectAllValueHolderElements(internalNetwork, result); // recursive call to collect ports
			}
		}
	}

}
