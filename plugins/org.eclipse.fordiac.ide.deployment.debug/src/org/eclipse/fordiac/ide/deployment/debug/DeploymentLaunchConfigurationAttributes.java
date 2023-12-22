/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public final class DeploymentLaunchConfigurationAttributes {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.deployLaunch"; //$NON-NLS-1$
	public static final String SYSTEM = "org.eclipse.fordiac.ide.deployment.debug.system"; //$NON-NLS-1$
	public static final String SELECTION = "org.eclipse.fordiac.ide.deployment.debug.selection"; //$NON-NLS-1$

	public static IResource getSystemResource(final ILaunchConfiguration configuration) throws CoreException {
		final String systemAttribute = configuration.getAttribute(SYSTEM, ""); //$NON-NLS-1$
		if (systemAttribute != null && !systemAttribute.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(systemAttribute));
		}
		return null;
	}

	public static SystemEntry getSystemEntry(final ILaunchConfiguration configuration) throws CoreException {
		final IResource systemResource = getSystemResource(configuration);
		if (systemResource instanceof final IFile systemFile && systemResource.exists() && TypeLibraryManager.INSTANCE
				.getTypeEntryForFile(systemFile) instanceof final SystemEntry systemEntry) {
			return systemEntry;
		}
		return null;
	}

	public static AutomationSystem getSystem(final ILaunchConfiguration configuration) throws CoreException {
		final SystemEntry systemEntry = getSystemEntry(configuration);
		if (systemEntry != null) {
			return systemEntry.getSystem();
		}
		return null;
	}

	public static Set<INamedElement> getSelection(final ILaunchConfiguration configuration,
			final AutomationSystem system) throws CoreException {
		final var selectionAttribute = configuration.getAttribute(SELECTION, Collections.emptySet());
		if (selectionAttribute != null && system != null) {
			return selectionAttribute.stream().map(qualifiedName -> findSelectedElement(qualifiedName, system))
					.flatMap(Optional::stream).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}

	private static Optional<INamedElement> findSelectedElement(final String qualifiedName,
			final AutomationSystem system) {
		final int separatorIndex = qualifiedName.indexOf('.');
		if (separatorIndex <= 0) {
			return Optional.ofNullable(system.getSystemConfiguration().getDeviceNamed(qualifiedName));
		}
		final String deviceName = qualifiedName.substring(0, separatorIndex);
		final String resourceName = qualifiedName.substring(separatorIndex + 1);
		final Device device = system.getSystemConfiguration().getDeviceNamed(deviceName);
		if (device != null) {
			return Optional.ofNullable(device.getResourceNamed(resourceName));
		}
		return Optional.empty();
	}

	private DeploymentLaunchConfigurationAttributes() {
		throw new UnsupportedOperationException();
	}
}
