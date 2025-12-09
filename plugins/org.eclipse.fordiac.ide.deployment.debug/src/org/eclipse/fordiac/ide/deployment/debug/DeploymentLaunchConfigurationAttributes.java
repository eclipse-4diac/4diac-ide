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

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public final class DeploymentLaunchConfigurationAttributes {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.deployLaunch"; //$NON-NLS-1$
	public static final String SYSTEM = "org.eclipse.fordiac.ide.deployment.debug.system"; //$NON-NLS-1$
	public static final String SELECTION = "org.eclipse.fordiac.ide.deployment.debug.selection"; //$NON-NLS-1$
	public static final String POLLING_INTERVAL = "org.eclipse.fordiac.ide.deployment.debug.pollingInterval"; //$NON-NLS-1$
	public static final int POLLING_INTERVAL_DEFAULT = 300;
	public static final String ALLOW_TERMINATE = "org.eclipse.fordiac.ide.deployment.debug.allowTerminate"; //$NON-NLS-1$
	public static final String ALLOW_TERMINATE_DEFAULT = AllowTerminate.DEBUG_ONLY.name();

	public static final String WATCH_TARGET_NAME = "org.eclipse.fordiac.ide.deployment.debug.watch.targetName"; //$NON-NLS-1$
	public static final String WATCH_TARGET_TYPE = "org.eclipse.fordiac.ide.deployment.debug.watch.targetType"; //$NON-NLS-1$
	public static final String WATCH_FORCE_VALUE = "org.eclipse.fordiac.ide.deployment.debug.watch.forceValue"; //$NON-NLS-1$

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

	public static Duration getPollingInterval(final ILaunchConfiguration configuration) throws CoreException {
		return Duration.ofMillis(configuration.getAttribute(POLLING_INTERVAL, POLLING_INTERVAL_DEFAULT));
	}

	public static AllowTerminate getAllowTerminate(final ILaunchConfiguration configuration) throws CoreException {
		final String allowTerminate = configuration.getAttribute(ALLOW_TERMINATE, ALLOW_TERMINATE_DEFAULT);
		return AllowTerminate.valueOf(allowTerminate);
	}

	public static List<DeploymentLaunchWatchpoint> getWatches(final ILaunchConfiguration configuration)
			throws CoreException {
		final List<String> targetNames = configuration.getAttribute(WATCH_TARGET_NAME, List.of());
		final List<String> targetTypes = configuration.getAttribute(WATCH_TARGET_TYPE, List.of());
		final List<String> forceValues = configuration.getAttribute(WATCH_FORCE_VALUE, List.of());
		if (targetNames.size() != targetTypes.size() || targetNames.size() != forceValues.size()) {
			throw new CoreException(
					Status.error("Invalid watches in launch configuration: Lists must have the same size")); //$NON-NLS-1$
		}
		return IntStream.range(0, targetNames.size())
				.mapToObj(index -> new DeploymentLaunchWatchpoint(targetNames.get(index), targetTypes.get(index),
						forceValues.get(index)))
				.toList();
	}

	public static void setWatches(final ILaunchConfigurationWorkingCopy configuration,
			final List<DeploymentLaunchWatchpoint> watches) {
		if (watches == null || watches.isEmpty()) {
			configuration.removeAttribute(WATCH_TARGET_NAME);
			configuration.removeAttribute(WATCH_TARGET_TYPE);
			configuration.removeAttribute(WATCH_FORCE_VALUE);
			return;
		}
		configuration.setAttribute(WATCH_TARGET_NAME,
				watches.stream().map(DeploymentLaunchWatchpoint::targetName).toList());
		configuration.setAttribute(WATCH_TARGET_TYPE, watches.stream().map(DeploymentLaunchWatchpoint::targetType)
				.map(EcoreUtil::getURI).map(URI::toString).toList());
		configuration.setAttribute(WATCH_FORCE_VALUE,
				watches.stream().map(DeploymentLaunchWatchpoint::forceValue).toList());
	}

	private DeploymentLaunchConfigurationAttributes() {
		throw new UnsupportedOperationException();
	}

	public enum AllowTerminate {
		NEVER(Messages.DeploymentLaunchConfigurationAttributes_AllowTerminate_Never),
		DEBUG_ONLY(Messages.DeploymentLaunchConfigurationAttributes_AllowTerminate_DebugOnly),
		ALWAYS(Messages.DeploymentLaunchConfigurationAttributes_AllowTerminate_Always);

		private final String displayString;

		AllowTerminate(final String displayString) {
			this.displayString = displayString;
		}

		public String getDisplayString() {
			return displayString;
		}
	}

	public record DeploymentLaunchWatchpoint(String targetName, EClass targetType, String forceValue) {
		public DeploymentLaunchWatchpoint {
			Objects.requireNonNull(targetName);
			Objects.requireNonNull(targetType);
			Objects.requireNonNull(forceValue);
		}

		public DeploymentLaunchWatchpoint(final String targetName, final String targetTypeUri,
				final String forceValue) {
			this(targetName, URI.createURI(targetTypeUri), forceValue);
		}

		public DeploymentLaunchWatchpoint(final String targetName, final URI targetTypeUri, final String forceValue) {
			this(targetName, getTargetType(targetTypeUri), forceValue);
		}

		public DeploymentLaunchWatchpoint(final DeploymentWatchpoint watchpoint) {
			this(watchpoint.getLocation(),
					watchpoint.getTargetType().orElse(LibraryElementPackage.Literals.INAMED_ELEMENT),
					watchpoint.isForceSupported() && watchpoint.isForceEnabled() ? watchpoint.getForceValue() : ""); //$NON-NLS-1$
		}

		public Optional<INamedElement> getTarget(final Device device) {
			return getTargets(device.getAutomationSystem())
					.filter(target -> device.equals(DeploymentDebugWatchUtils.getDevice(target))).findFirst();
		}

		public Optional<INamedElement> getTarget(final AutomationSystem system) {
			return getTargets(system).findFirst();
		}

		public Stream<INamedElement> getTargets(final AutomationSystem system) {
			if (targetName == null || targetName.isEmpty() || targetType == null) {
				return Stream.empty();
			}
			return system.findByQualifiedName(targetName).filter(element -> targetType.equals(element.eClass()));
		}

		public boolean isForceEnabled() {
			return !forceValue.isEmpty();
		}

		private static EClass getTargetType(final URI targetTypeUri) {
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(targetTypeUri.trimFragment().toString());
			if (ePackage == null) {
				throw new IllegalArgumentException("Not a valid package: " + targetTypeUri); //$NON-NLS-1$
			}
			return (EClass) ePackage.eResource().getEObject(targetTypeUri.fragment());
		}
	}
}
