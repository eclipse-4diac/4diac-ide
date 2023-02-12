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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public class MappingAnnotations {
	public static MappingTarget getMappingTarget(final FBNetworkElement el) {
		if (el instanceof CommunicationChannel) {
			return (MappingTarget) el.getMapping().getTo().eContainer();
		}
		return el.getResource();
	}

	public static List<MappingTarget> getPossibleMappingTargets(final EObject obj) {
		if (obj instanceof CommunicationChannel) {
			return getContainedCommunicationMappingTargets(
					((CommunicationChannel) obj).getFbNetwork().getAutomationSystem());
		}
		if (obj instanceof FBNetworkElement) {
			return getContainedMappingTargets(((FBNetworkElement) obj).getFbNetwork().getAutomationSystem());
		}
		if (obj instanceof FBNetwork) {
			final FBNetwork network = (FBNetwork) obj;
			return getContainedMappingTargets(network.getAutomationSystem());
		}
		return Collections.emptyList();
	}

	public static List<MappingTarget> getContainedMappingTargets(final EObject obj) {
		if (obj instanceof AutomationSystem) {
			final AutomationSystem system = (AutomationSystem) obj;
			return system.getSystemConfiguration().getDevices().stream().flatMap(dev -> dev.getResource().stream())
					.collect(Collectors.toList());
		}
		if (obj instanceof Device) {
			return new ArrayList<>(((Device) obj).getResource());
		}
		return Collections.emptyList();
	}

	public static List<MappingTarget> getContainedCommunicationMappingTargets(final EObject obj) {
		if (obj instanceof AutomationSystem) {
			final AutomationSystem system = (AutomationSystem) obj;
			return system.getSystemConfiguration().getSegments().stream().map(Segment::getCommunication)
					.filter(Objects::nonNull).flatMap(comm -> comm.getMappingTargets().stream())
					.collect(Collectors.toList());
		}
		if (obj instanceof Segment) {
			final CommunicationConfiguration communication = ((Segment) obj).getCommunication();
			if (communication != null) {
				return new ArrayList<>(communication.getMappingTargets());
			}
		}
		return Collections.emptyList();
	}

	public static boolean containsMappingTargets(final EObject obj) {
		return (obj instanceof Device) || (obj instanceof Segment) || (obj instanceof MappingTarget);
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
		if ((target instanceof CommunicationMappingTarget) && (target.eContainer() != null)) {
			return ((Segment) target.eContainer().eContainer()).getName() + "." //$NON-NLS-1$
					+ ((CommunicationMappingTarget) target).getName();
		}
		return target.toString();
	}

	public static String getHierarchicalName(final FBNetworkElement element) {
		if (element instanceof CommunicationChannel) {
			if (element.eContainer() instanceof MappingTarget) {
				return getHierarchicalName((MappingTarget) element.eContainer());
			}
			return getHierarchicalName((MappingTarget) element.getOpposite().eContainer());
		}
		return getHierarchicalName(element.getResource());
	}

	private MappingAnnotations() {
		throw new UnsupportedOperationException();
	}
}
