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

import java.util.Objects;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.deployment.debug.IDeploymentDebugTarget;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public abstract class AbstractContainerWatch extends DeploymentDebugElement implements IContainerWatch {

	protected final String name;
	protected final ITypedElement element;
	protected final String qualifiedName;
	protected final Resource resource;

	protected AbstractContainerWatch(final String name, final ITypedElement element,
			final IDeploymentDebugTarget target) {
		super(target);
		this.name = name;
		this.element = element;
		qualifiedName = element.getQualifiedName();
		resource = Objects.requireNonNull(DeploymentDebugWatchUtils.getResource(element), "element not in a resource"); //$NON-NLS-1$
	}

	@Override
	public void addWatch() throws DebugException {
		for (final IWatch watch : getSubWatches()) {
			watch.addWatch();
		}
	}

	@Override
	public void removeWatch() throws DebugException {
		for (final IWatch watch : getSubWatches()) {
			watch.removeWatch();
		}
	}

	@Override
	public void updateValue(final DeploymentDebugWatchData watchData) {
		for (final IWatch watch : getSubWatches()) {
			watch.updateValue(watchData);
		}
	}

	@Override
	public boolean isAlive() {
		return getSubWatches().stream().allMatch(IWatch::isAlive);
	}

	@Override
	public String getReferenceTypeName() {
		return element.getTypeName();
	}

	@Override
	public boolean hasValueChanged() {
		return false; // prevents annoying flickering in variables view
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public boolean supportsValueModification() {
		return false;
	}

	@Override
	public boolean verifyValue(final String expression) {
		return false;
	}

	@Override
	public boolean verifyValue(final IValue value) {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQualifiedName() {
		return qualifiedName;
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public ITypedElement getWatchedElement() {
		return element;
	}

	@Override
	public DeploymentDebugDevice getDebugTarget() {
		return (DeploymentDebugDevice) super.getDebugTarget();
	}

	@Override
	public int hashCode() {
		return getQualifiedName().hashCode();
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
		final AbstractContainerWatch other = (AbstractContainerWatch) obj;
		return Objects.equals(getQualifiedName(), other.getQualifiedName());
	}

}