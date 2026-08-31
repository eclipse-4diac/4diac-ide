/*******************************************************************************
 * Copyright (c) 2024, 2026 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.breakpoint;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.deployment.debug.preferences.DeploymentDebugPreferences;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class DeploymentWatchpoint extends Breakpoint {

	public static final String DEBUG_MODEL = "org.eclipse.fordiac.ide.deployment.debug.model"; //$NON-NLS-1$

	public static final String BREAKPOINT_MARKER = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker"; //$NON-NLS-1$

	public static final String INSTALLED = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker.installed"; //$NON-NLS-1$
	public static final String FORCE_VALUE = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker.forceValue"; //$NON-NLS-1$
	public static final String FORCE_ENABLED = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker.forceEnabled"; //$NON-NLS-1$
	public static final String PINNED = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker.pinned"; //$NON-NLS-1$
	public static final String SUBELEMENTS = "org.eclipse.fordiac.ide.deployment.debug.watchpointMarker.subElements"; //$NON-NLS-1$

	public DeploymentWatchpoint() {
	}

	public DeploymentWatchpoint(final IResource resource, final INamedElement element) throws CoreException {
		run(getMarkerRule(resource), _ -> createMarker(resource, element));
	}

	protected IMarker createMarker(final IResource resource, final INamedElement element) throws CoreException {
		final ErrorMarkerBuilder builder = ErrorMarkerBuilder.createErrorMarkerBuilder("Deployment Watchpoint: " //$NON-NLS-1$
				+ resource.getName() + " [qualifiedName: " + element.getQualifiedName() + "]") //$NON-NLS-1$ //$NON-NLS-2$
				.setType(getMarkerId()).setSeverity(IMarker.SEVERITY_INFO).setPriority(IMarker.PRIORITY_NORMAL)
				.setSource(DEBUG_MODEL).setTarget(element).addAdditionalAttributes(Map.of(IBreakpoint.ENABLED,
						Boolean.TRUE, IBreakpoint.PERSISTED, Boolean.TRUE, IBreakpoint.ID, getModelIdentifier()));
		if (isWatchSubElementsDefault(resource, element)) {
			builder.addAdditionalAttributes(Map.of(SUBELEMENTS, Boolean.TRUE));
		}
		final IMarker marker = builder.createMarker(resource);
		setMarker(marker);
		return marker;
	}

	public Optional<INamedElement> getTarget() {
		final IMarker marker = getMarker();
		if (marker != null && marker.getResource() instanceof final IFile file) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry instanceof final SystemEntry systemEntry) {
				final AutomationSystem system = systemEntry.getType();
				if (system != null) {
					return getTarget(system);
				}
			}
		}
		return Optional.empty();
	}

	public Optional<INamedElement> getTarget(final AutomationSystem system) {
		return getTargets(system).findFirst();
	}

	public Optional<INamedElement> getTarget(final Device device) {
		return getTargets(device.getAutomationSystem())
				.filter(target -> device.equals(DeploymentDebugWatchUtils.getDevice(target))).findFirst();
	}

	public Stream<INamedElement> getTargets(final AutomationSystem system) {
		final String location = getLocation();
		final Optional<EClass> targetType = getTargetType();
		if (location.isEmpty() || targetType.isEmpty()) {
			return Stream.empty();
		}
		return system.findByQualifiedName(location).filter(element -> targetType.get().equals(element.eClass()));
	}

	public Optional<EClass> getTargetType() {
		final IMarker marker = getMarker();
		if (marker != null) {
			return Optional.ofNullable(FordiacErrorMarker.getTargetType(marker));
		}
		return Optional.empty();
	}

	public boolean isRelevant(final AutomationSystem system) {
		final IMarker marker = getMarker();
		if (marker != null) {
			final TypeEntry typeEntry = system.getTypeEntry();
			return typeEntry != null && marker.getResource().equals(typeEntry.getFile());
		}
		return false;
	}

	public String getLocation() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isInstalled() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(INSTALLED, false);
		}
		return false;
	}

	public void setInstalled(final boolean installed) throws CoreException {
		setAttribute(INSTALLED, installed);
	}

	public boolean isForceEnabled() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(FORCE_ENABLED, false);
		}
		return false;
	}

	public void setForceEnabled(final boolean enabled) throws CoreException {
		setAttribute(FORCE_ENABLED, enabled);
	}

	public String getForceValue() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(FORCE_VALUE, ""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	public void setForceValue(final String value) throws CoreException {
		setAttribute(FORCE_VALUE, value);
	}

	public boolean isForceSupported() {
		final IMarker m = getMarker();
		if (m != null) {
			return FordiacErrorMarker.getTargetType(m) == LibraryElementPackage.Literals.VAR_DECLARATION
					&& getTarget().filter(VarDeclaration.class::isInstance).map(VarDeclaration.class::cast)
							.filter(Predicate.not(VarDeclaration::isInOutVar)).isPresent();
		}
		return false;
	}

	public boolean isForceChanged(final IMarkerDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& (isForceEnabled() != delta.getAttribute(FORCE_ENABLED, false)
						|| !getForceValue().equals(delta.getAttribute(FORCE_VALUE, ""))); //$NON-NLS-1$
	}

	public boolean isPinned() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(PINNED, false);
		}
		return false;
	}

	public void setPinned(final boolean pinned) throws CoreException {
		setAttribute(PINNED, pinned);
	}

	public boolean isPinnedChanged(final IMarkerDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED && (isPinned() != delta.getAttribute(PINNED, false));
	}

	public boolean isWatchSubElements() {
		final IMarker m = getMarker();
		if (m != null) {
			return m.getAttribute(SUBELEMENTS, false);
		}
		return false;
	}

	public void setWatchSubElements(final boolean subElements) throws CoreException {
		setAttribute(SUBELEMENTS, subElements);
	}

	public boolean isWatchSubElementsSupported() {
		final IMarker m = getMarker();
		if (m != null) {
			final EClass targetType = FordiacErrorMarker.getTargetType(m);
			return targetType != null
					&& (LibraryElementPackage.Literals.SUB_APP.isSuperTypeOf(targetType)
							|| LibraryElementPackage.Literals.CFB_INSTANCE.isSuperTypeOf(targetType))
					|| (LibraryElementPackage.Literals.FB.isSuperTypeOf(targetType)
							&& getTarget().filter(FB.class::isInstance).map(FB.class::cast).map(FB::getTypeEntry)
									.map(TypeEntry::getTypeEClass)
									.filter(LibraryElementPackage.Literals.BASE_FB_TYPE::isSuperTypeOf).isPresent());
		}
		return false;
	}

	public static boolean isWatchSubElementsDefault(final IResource resource, final INamedElement element) {
		return switch (element) {
		case final SubApp _ -> DeploymentDebugPreferences.isWatchInternalNetworksDefault(resource.getProject());
		case final CFBInstance _ -> DeploymentDebugPreferences.isWatchInternalNetworksDefault(resource.getProject());
		case final FB fb when LibraryElementPackage.Literals.BASE_FB_TYPE
				.isSuperTypeOf(fb.getTypeEntry().getTypeEClass()) ->
			DeploymentDebugPreferences.isWatchInternalVariablesDefault(resource.getProject());
		default -> false;
		};
	}

	public boolean isWatchSubElementsChanged(final IMarkerDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED
				&& (isWatchSubElements() != delta.getAttribute(SUBELEMENTS, false));
	}

	@Override
	public boolean isEnabled() {
		try {
			return super.isEnabled();
		} catch (final CoreException e) {
			// never thrown in super method
			return false;
		}
	}

	public boolean isEnabledChanged(final IMarkerDelta delta) {
		return delta.getKind() == IResourceDelta.CHANGED && (isEnabled() != delta.getAttribute(ENABLED, false));
	}

	@SuppressWarnings("static-method")
	public String getMarkerId() {
		return BREAKPOINT_MARKER;
	}

	@Override
	public String getModelIdentifier() {
		return DEBUG_MODEL;
	}
}
