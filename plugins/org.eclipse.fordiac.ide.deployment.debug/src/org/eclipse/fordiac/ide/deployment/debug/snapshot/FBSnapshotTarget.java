/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * A function block instance whose state can be read from the runtime for
 * snapshot debugging.
 *
 * @param instance the instance the user selected (the application side for
 *                 mapped function blocks)
 * @param resource the resource the function block runs in
 * @param type     the type of the function block
 */
public record FBSnapshotTarget(FB instance, Resource resource, FBType type) {

	public FBSnapshotTarget {
		Objects.requireNonNull(instance);
		Objects.requireNonNull(resource);
		Objects.requireNonNull(type);
	}

	/**
	 * Create a snapshot target for the given element.
	 *
	 * @param element the selected element
	 * @return the snapshot target
	 * @throws CoreException if snapshot debugging is not supported for the element,
	 *                       the status message explains why
	 * @see FBSnapshotValidator
	 */
	public static FBSnapshotTarget of(final Object element) throws CoreException {
		final IStatus status = FBSnapshotValidator.validate(element);
		if (status.matches(IStatus.ERROR)) {
			throw new CoreException(status);
		}
		final FB fb = (FB) element;
		final FB instance = fb.isMapped() && fb.getMapping().getFrom() instanceof final FB from ? from : fb;
		return new FBSnapshotTarget(instance, fb.getResource(), fb.getType());
	}

	/** @return the device the function block runs on */
	public Device device() {
		return resource.getDevice();
	}

	/**
	 * @return the name of the function block in the runtime relative to its
	 *         resource; function blocks of an application are deployed with their
	 *         full application path
	 */
	public String resourceRelativeName() {
		if (EcoreUtil.isAncestor(resource, instance)) {
			return DeploymentDebugWatchUtils.getResourceRelativeName(instance, resource);
		}
		// do not shorten the path, it may start like the path of the resource
		return instance.getQualifiedName();
	}
}
