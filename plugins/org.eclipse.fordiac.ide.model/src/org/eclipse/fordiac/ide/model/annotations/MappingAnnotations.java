/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.annotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public class MappingAnnotations {
	public static MappingTarget getMappingTarget(final FBNetworkElement el) {
		return el.getResource();
	}

	public static List<? extends MappingTarget> getPossibleMappingTargets(final EObject obj) {
		if (obj instanceof FBNetworkElement) {
			return getContainedMappingTargets(((FBNetworkElement) obj).getFbNetwork().getAutomationSystem());
		}
		if (obj instanceof FBNetwork) {
			final FBNetwork network = (FBNetwork) obj;
			return getContainedMappingTargets(network.getAutomationSystem());
		}
		return Collections.emptyList();
	}

	public static List<? extends MappingTarget> getContainedMappingTargets(final EObject obj) {
		if (obj instanceof AutomationSystem) {
			final AutomationSystem system = (AutomationSystem) obj;
			final List<Resource> targets = system.getSystemConfiguration().getDevices().stream()
					.flatMap(dev -> dev.getResource().stream()).collect(Collectors.toList());
			// add communication mapping targets TODO
			return targets;
		}
		if (obj instanceof Device) {
			return ((Device) obj).getResource();
		}
		if (obj instanceof Segment) {
			return Collections.emptyList(); // TODO
		}
		return Collections.emptyList();
	}

	public static boolean containsMappingTargets(final EObject obj) {
		return (obj instanceof Device) || (obj instanceof Segment) || (obj instanceof MappingTarget);
	}

	public static List<? extends MappingTarget> getCommunicationMappingTargets(final Segment seg) {
		if (seg.getCommunication() != null) {
			return Collections.EMPTY_LIST; // TODO return communication mapping targets
		}
		return Collections.emptyList();
	}

	public static boolean isMapable(final FBNetwork network) {
		return ((network != null) && !(network.isSubApplicationNetwork() || network.isCFBTypeNetwork()
				|| (network.eContainer() instanceof MappingTarget)));
	}

	public static String getHierarchicalName(final MappingTarget target) {
		if (target instanceof Resource) {
			return ((Resource) target).getDevice().getName() + "." //$NON-NLS-1$
					+ ((Resource) target).getName();
		}
		return target.toString();
	}

	private MappingAnnotations() {
		throw new UnsupportedOperationException();
	}
}
