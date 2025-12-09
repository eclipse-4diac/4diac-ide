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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;

public class DeploymentDebugWatchData {

	private final Map<String, Map<String, Port>> data;

	public DeploymentDebugWatchData(final Response response) {
		data = Optional.ofNullable(response.getWatches()).map(Watches::getResources).stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableMap(Resource::getName, DeploymentDebugWatchData::collectPorts));
	}

	protected static Map<String, Port> collectPorts(final Resource resource) {
		return resource.getFbs().stream().map(FB::getPorts).flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableMap(DeploymentDebugWatchData::getPortString, Function.identity()));
	}

	protected static String getPortString(final Port port) {
		if (port.eContainer() instanceof final FB fb) {
			return fb.getName() + "." + port.getName(); //$NON-NLS-1$
		}
		return port.getName();
	}

	public Port getPort(final org.eclipse.fordiac.ide.model.libraryElement.Resource resource, final String portString) {
		return data.getOrDefault(resource.getName(), Map.of()).get(portString);
	}

	public Data getLastData(final org.eclipse.fordiac.ide.model.libraryElement.Resource resource,
			final String portString) {
		final Port port = getPort(resource, portString);
		if (port != null && !port.getDataValues().isEmpty()) {
			return port.getDataValues().getLast();
		}
		return null;
	}
}
