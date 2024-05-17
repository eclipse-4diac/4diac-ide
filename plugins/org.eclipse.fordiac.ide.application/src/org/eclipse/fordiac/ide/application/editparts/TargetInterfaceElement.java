/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class TargetInterfaceElement implements Comparable<TargetInterfaceElement> {
	private final IInterfaceElement refElement;
	private final IInterfaceElement host;

	public static TargetInterfaceElement createFor(final IInterfaceElement host, final IInterfaceElement refElement,
			final FBNetwork parentNW) {
		if (refElement.getFBNetworkElement() == null || (refElement.getFBNetworkElement() instanceof final SubApp subapp
				&& subapp.getSubAppNetwork() == parentNW)) {
			return new SubappTargetInterfaceElement(host, refElement);
		}
		return new TargetInterfaceElement(host, refElement);
	}

	private TargetInterfaceElement(final IInterfaceElement host, final IInterfaceElement refElement) {
		this.host = host;
		this.refElement = refElement;
	}

	public IInterfaceElement getRefElement() {
		return refElement;
	}

	public IInterfaceElement getHost() {
		return host;
	}

	public String getRefPinFullName() {
		final StringBuilder retVal = new StringBuilder();
		final var fbelement = getRefElement().getFBNetworkElement();
		if (fbelement != null) {
			final FBNetworkElement parent = fbelement.getOuterFBNetworkElement();
			if (parent != null) {
				retVal.append(parent.getName());
				retVal.append('.');
			}
			retVal.append(fbelement.getName());
			retVal.append('.');
		}
		retVal.append(getRefElement().getName());
		return retVal.toString();
	}

	@Override
	public int compareTo(final TargetInterfaceElement other) {
		if (other instanceof SubappTargetInterfaceElement) {
			return 1;
		}
		return getRefPinFullName().compareTo(other.getRefPinFullName());
	}

	public static class SubappTargetInterfaceElement extends TargetInterfaceElement {
		private SubappTargetInterfaceElement(final IInterfaceElement host, final IInterfaceElement refElement) {
			super(host, refElement);
		}

		@Override
		public String getRefPinFullName() {
			return getRefElement().getName();
		}

		@Override
		public int compareTo(final TargetInterfaceElement other) {
			if (other instanceof SubappTargetInterfaceElement) {
				return getRefPinFullName().compareTo(other.getRefPinFullName());
			}
			return -1;
		}
	}

	public static class GroupTargetInterfaceElement extends SubappTargetInterfaceElement {
		final SubApp subapp;
		final int numConns;

		public GroupTargetInterfaceElement(final IInterfaceElement host, final IInterfaceElement refElement,
				final SubApp subapp, final int numConns) {
			super(host, refElement);
			this.subapp = subapp;
			this.numConns = numConns;
		}

		@Override
		public String getRefPinFullName() {
			return subapp.getName() + ": " + numConns; //$NON-NLS-1$
		}

	}

}