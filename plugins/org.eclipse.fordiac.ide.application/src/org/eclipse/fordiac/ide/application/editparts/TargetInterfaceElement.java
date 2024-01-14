/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class TargetInterfaceElement implements Comparable<TargetInterfaceElement> {
	private final IInterfaceElement refElement;

	public TargetInterfaceElement(final IInterfaceElement refElement) {
		this.refElement = refElement;
	}

	public IInterfaceElement getRefElement() {
		return refElement;
	}

	public String getRefPinFullName() {
		final var fbelement = getRefElement().getFBNetworkElement();
		final FBNetworkElement parent = fbelement.getOuterFBNetworkElement();
		return parent.getName() + "." + fbelement.getName() + "." + getRefElement().getName(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public int compareTo(final TargetInterfaceElement other) {
		return getRefPinFullName().compareTo(other.getRefPinFullName());
	}

}