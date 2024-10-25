/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.watch;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class DeploymentDebugWatchUtils {

	public static Resource getResource(final INamedElement element) {
		return switch (element) {
		case final IInterfaceElement interfaceElement -> getResource(interfaceElement.getFBNetworkElement());
		case final FBNetworkElement networkElement -> networkElement.getResource();
		default -> null;
		};
	}

	public static String getResourceRelativeName(final INamedElement element, final Resource resource) {
		final String qualifiedName = element.getQualifiedName();
		final String resourceName = resource.getQualifiedName();
		if (qualifiedName.startsWith(resourceName)) {
			return qualifiedName.substring(resourceName.length() + 1);
		}
		return qualifiedName;
	}

	@SuppressWarnings("unchecked")
	public static <T extends IInterfaceElement> Stream<T> resolveSubappInterfaceConnections(final T element) {
		if (!(element.getFBNetworkElement() instanceof final SubApp subapp)) {
			return Stream.of(element);
		}
		subapp.loadSubAppNetwork(); // ensure network is loaded
		if (element.isIsInput()) {
			return (Stream<T>) element.getOutputConnections().stream().map(Connection::getDestination)
					.flatMap(DeploymentDebugWatchUtils::resolveSubappInterfaceConnections);
		}
		return (Stream<T>) element.getInputConnections().stream().map(Connection::getSource)
				.flatMap(DeploymentDebugWatchUtils::resolveSubappInterfaceConnections);
	}

	private DeploymentDebugWatchUtils() {
		throw new UnsupportedOperationException();
	}
}
