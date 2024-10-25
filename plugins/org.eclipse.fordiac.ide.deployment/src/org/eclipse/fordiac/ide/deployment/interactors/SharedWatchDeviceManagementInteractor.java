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
package org.eclipse.fordiac.ide.deployment.interactors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class SharedWatchDeviceManagementInteractor extends AbstractDelegatingDeviceManagementInteractor {

	private final Map<String, Map<String, Integer>> watches = new HashMap<>();

	public SharedWatchDeviceManagementInteractor(final IDeviceManagementInteractor delegate) {
		super(delegate);
	}

	@Override
	public boolean addWatch(final Resource resource, final String name) throws DeploymentException {
		final Map<String, Integer> resourceWatches = getResourceWatches(resource);
		final Integer count = resourceWatches.get(name);
		if (count != null) {
			resourceWatches.put(name, Integer.valueOf(count.intValue() + 1));
		} else if (super.addWatch(resource, name)) {
			resourceWatches.put(name, Integer.valueOf(1));
		} else {
			return false;
		}
		return true;
	}

	@Override
	public boolean removeWatch(final Resource resource, final String name) throws DeploymentException {
		final Map<String, Integer> resourceWatches = getResourceWatches(resource);
		final Integer count = resourceWatches.get(name);
		if (count != null && count.intValue() > 1) {
			resourceWatches.put(name, Integer.valueOf(count.intValue() - 1));
		} else if (super.removeWatch(resource, name)) {
			resourceWatches.remove(name);
		} else {
			return false;
		}
		return true;
	}

	protected Map<String, Integer> getResourceWatches(final Resource resource) {
		return watches.computeIfAbsent(resource.getName(), unused -> new HashMap<>());
	}
}
