/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SearchNameDictionary {

	private final HashMap<FBNetworkElement, Stack<List<FBNetworkElement>>> map = new HashMap<>();

	public void addEntry(final FBNetworkElement fbnetworkElement, final List<FBNetworkElement> composites) {
		map.computeIfAbsent(fbnetworkElement, elem -> new Stack<>());
		map.get(fbnetworkElement).push(composites); // list newly allocated for every depth, so no ownership problems
	}

	public String hierarchicalName(final Object element) {
		if (element instanceof final FBNetworkElement fbne) {
			if (fbne.eContainer() instanceof BasicFBType || fbne.eContainer() instanceof SimpleFBType) {
				// internal fbs
				return hierarchicalName(fbne, true);
			}
			if (map.containsKey(fbne)) {
				return hierarchicalName(fbne, false);
			}
			final var root = EcoreUtil.getRootContainer(fbne);
			final StringBuilder sb = new StringBuilder();
			if (root instanceof final AutomationSystem system) {
				sb.append(system.getName() + "."); //$NON-NLS-1$
			}
			return sb.toString() + FBNetworkHelper.getFullHierarchicalName(fbne);
		}
		if (element instanceof final IInterfaceElement ie) {
			String parentName = FBNetworkHelper.getFullHierarchicalName(ie.getFBNetworkElement());
			if (parentName == null && ie.eContainer() != null) {
				if (ie.eContainer() instanceof final INamedElement econ) {
					parentName = econ.getName();
				} else if (ie.eContainer().eContainer() instanceof final INamedElement econParent) {
					parentName = econParent.getName();
				}
			}
			return parentName + "." + ie.getName(); //$NON-NLS-1$
		}
		if (element instanceof final Device device) {
			// systemname.device
			return device.getAutomationSystem().getName() + "." + device.getName(); //$NON-NLS-1$
		}
		if (element instanceof final Resource res) {
			// systemname.devicename.resource
			return res.getDevice().getAutomationSystem().getName() + "." + res.getDevice().getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
					+ res.getName();
		}
		if (element instanceof final ICallable call && (element instanceof Algorithm || element instanceof Method)
				&& call.eContainer() instanceof final INamedElement namedElement) {
			return namedElement.getName() + "." + call.getName(); //$NON-NLS-1$
		}
		if (element instanceof final LibraryElement libElement) {
			return libElement.getTypeEntry().getFullTypeName();
		}
		if (element instanceof final INamedElement namedElement) {
			return namedElement.getName();
		}
		return element.toString();
	}

	// resolves the elements path through typed composites to a proper name
	private String hierarchicalName(final FBNetworkElement element, final boolean isInternal) {
		final var stack = map.get(element);
		if (stack == null) {
			final String name = FBNetworkHelper.getFullHierarchicalName(element);
			return isInternal ? name + "(internal)" : name; //$NON-NLS-1$
		}

		final var path = stack.pop();
		if (stack.isEmpty()) {
			map.remove(element);
		}

		final FBNetworkElement first = path.get(0);
		final String firstElementName = FBNetworkHelper.getFullHierarchicalName(first);
		final Application application = first.getFbNetwork().getApplication();

		final StringBuilder sb = new StringBuilder();

		if (application != null) {
			sb.append(application.getAutomationSystem().getName()).append("."); //$NON-NLS-1$
		}

		sb.append(firstElementName);

		for (int i = 1; i < path.size(); i++) {
			addNameToPath(path.get(i), sb, false); // is never internal
		}

		addNameToPath(element, sb, isInternal);

		return sb.toString();
	}

	private static void addNameToPath(final FBNetworkElement element, final StringBuilder sb,
			final boolean isInternal) {
		sb.append("."); //$NON-NLS-1$
		String name = isInternal ? element.getName() : FBNetworkHelper.getFullHierarchicalName(element);
		name = name.substring(name.indexOf('.') + 1); // remove type name as it is anyhow replaced by instance name
		sb.append(name);
		if (isInternal) {
			sb.append("(internal)"); //$NON-NLS-1$
		}
	}

}
