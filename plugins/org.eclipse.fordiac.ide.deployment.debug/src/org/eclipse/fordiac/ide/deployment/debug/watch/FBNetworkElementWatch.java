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

import java.util.List;

import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class FBNetworkElementWatch extends AbstractContainerWatch {

	final FBNetworkElementValue value;

	public FBNetworkElementWatch(final String name, final FBNetworkElement element,
			final DeploymentDebugDevice target) {
		super(name, element, target);
		value = new FBNetworkElementValue(element, target);
	}

	public FBNetworkElementWatch(final String name, final FBNetworkElement element, final Resource resource,
			final String resourceRelativeName, final DeploymentDebugDevice target) {
		super(name, element, target);
		value = new FBNetworkElementValue(element, resource, resourceRelativeName, target);
	}

	@Override
	public FBNetworkElementValue getValue() {
		return value;
	}

	@Override
	public List<IWatch> getSubWatches() {
		return value.getWatches();
	}

	@Override
	public FBNetworkElement getWatchedElement() {
		return (FBNetworkElement) super.getWatchedElement();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == FBNetworkElement.class) {
			return adapter.cast(getWatchedElement());
		}
		return super.getAdapter(adapter);
	}
}
