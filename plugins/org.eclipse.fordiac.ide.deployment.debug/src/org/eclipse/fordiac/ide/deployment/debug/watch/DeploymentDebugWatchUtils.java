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

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

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

	private DeploymentDebugWatchUtils() {
		throw new UnsupportedOperationException();
	}
}
