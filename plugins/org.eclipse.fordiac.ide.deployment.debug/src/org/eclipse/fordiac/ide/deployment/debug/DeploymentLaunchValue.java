/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentLaunchValue {
	private final String name;
	private final Optional<VarDeclaration> refElement;
	private String value;

	public DeploymentLaunchValue(final String name, final Optional<VarDeclaration> refElement, final String value) {
		this.name = Objects.requireNonNull(name);
		this.refElement = Objects.requireNonNull(refElement);
		this.value = Objects.requireNonNullElse(value, ""); //$NON-NLS-1$
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = Objects.requireNonNullElse(value, ""); //$NON-NLS-1$
	}

	public String getName() {
		return name;
	}

	public Optional<VarDeclaration> getRefElement() {
		return refElement;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DeploymentLaunchValue other = (DeploymentLaunchValue) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return String.format("%s [name=%s, value=%s]", getClass().getName(), name, value); //$NON-NLS-1$
	}
}