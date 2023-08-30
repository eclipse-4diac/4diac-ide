/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public class InstanceName {

	private final FBNetworkElement refElement;

	public InstanceName(final FBNetworkElement refElement) {
		this.refElement = refElement;
	}

	public FBNetworkElement getRefElement() {
		return refElement;
	}

	public String getInstanceName() {
		return getRefElement().getName();
	}

	public boolean hasErrorMarker() {
		return getRefElement().hasError();
	}
}
