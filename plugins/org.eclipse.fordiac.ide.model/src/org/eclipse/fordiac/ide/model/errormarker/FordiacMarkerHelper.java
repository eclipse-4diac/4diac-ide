/*******************************************************************************
 * Copyright (c) 2020, 2024 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - extension for connection error markers
 *   Michael Oberlehner
 *               - Refactoring of API
 *   Martin Jobst - refactor marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.errormarker;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobGroup;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class FordiacMarkerHelper {
	public static final JobGroup JOB_GROUP = new JobGroup("FordiacMarkerHelper JobGroup", 0, 0); //$NON-NLS-1$

	/**
	 * Get the human-readable location string for an object.
	 *
	 * @param object The object
	 * @return The location string or an empty string if not applicable
	 * @see INamedElement#getQualifiedName()
	 * @implSpec The location string is based on the
	 *           {@link INamedElement#getQualifiedName()} of the object if it is a
	 *           {@link INamedElement} or the location of its parent together with
	 *           the containing feature.
	 */
	public static String getLocation(final EObject object) {
		if (object instanceof final INamedElement namedElement) {
			return namedElement.getQualifiedName();
		}
		if (object != null) {
			return getLocation(object.eContainer(), object.eContainingFeature());
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Get the human-readable location string for an object and feature.
	 *
	 * @param object  The object
	 * @param feature The feature (may be null)
	 * @return The location string or an empty string if not applicable
	 * @see #getLocation()
	 */
	public static String getLocation(final EObject object, final EStructuralFeature feature) {
		if (feature != null) {
			return getLocation(object) + "/" + feature.getName(); //$NON-NLS-1$
		}
		return getLocation(object);
	}

	/**
	 * Get the target URI of an object
	 *
	 * @param resource The workspace resource
	 * @param object   The object
	 * @return The target URI or null if not applicable
	 */
	public static URI getTargetUri(final IResource resource, final EObject object) {
		if (object != null) {
			final URI uri = EcoreUtil.getURI(object);
			if (uri.isRelative() && resource != null) {
				return URI.createPlatformResourceURI(resource.getFullPath().toString(), true)
						.appendFragment(uri.fragment());
			}
			return uri;
		}
		return null;
	}

	/**
	 * Get the target URI of an object as a string
	 *
	 * @param resource The workspace resource
	 * @param object   The object
	 * @return The target URI string or null if not applicable
	 */
	public static String getTargetUriString(final IResource resource, final EObject object) {
		final URI uri = getTargetUri(resource, object);
		if (uri != null) {
			return uri.toString();
		}
		return null;
	}

	/**
	 * Get the diagnostic data to be used for an object
	 *
	 * @param object The object
	 * @param data   The additional data
	 * @return The diagnostic data
	 * @see Diagnostic#getData()
	 * @see #getDiagnosticData(EObject, EStructuralFeature, String...)
	 */
	public static Object[] getDiagnosticData(final EObject object, final String... data) {
		return getDiagnosticData(object, null, data);
	}

	/**
	 * Get the diagnostic data to be used for an object
	 *
	 * @param object  The object
	 * @param feature The originating feature
	 * @param data    The additional data
	 * @return The diagnostic data
	 * @see Diagnostic#getData()
	 * @see #getDiagnosticAttributes(Diagnostic)
	 * @implSpec The diagnostic data follows a specific structure so that it may
	 *           later be converted to a marker or issue. It consists of:
	 *           <ul>
	 *           <li>{@code [0]} the object itself (per EMF convention),
	 *           <li>{@code [1]} the location for the object using
	 *           {@link #getLocation(EObject)},
	 *           <li>{@code [2]} the target URI for the object using
	 *           {@link #getTargetUriString(IResource, EObject)},
	 *           <li>{@code [3]} the URI of the {@link EClass} of the object as a
	 *           string,
	 *           <li>{@code [4]} the URI of the {@link EStructuralFeature} feature
	 *           as a string (or null).
	 *           </ul>
	 */
	public static Object[] getDiagnosticData(final EObject object, final EStructuralFeature feature,
			final String... data) {
		if (object == null) {
			return data;
		}
		return Stream.concat(Stream.of(object, // [0] object itself (per EMF convention)
				getLocation(object), // [1] LOCATION
				getTargetUriString(null, object), // [2] TARGET_URI
				EcoreUtil.getURI(object.eClass()).toString(), // [3] TARGET_TYPE
				feature != null ? EcoreUtil.getURI(feature).toString() : null // [4] TARGET_FEATURE
		), Stream.of(data)).toArray();
	}

	/**
	 * Get the target from the data of a diagnostic
	 *
	 * @param diagnostic The diagnostic
	 * @return The target object or null if not applicable
	 */
	public static EObject getDiagnosticTarget(final Diagnostic diagnostic) {
		final List<?> data = diagnostic.getData();
		if (data != null && !data.isEmpty() && data.get(0) instanceof final EObject target) {
			return target;
		}
		return null;
	}

	/**
	 * Get the marker attributes for a diagnostic
	 *
	 * @param diagnostic The diagnostic
	 * @return The marker attributes
	 */
	public static Map<String, Object> getDiagnosticAttributes(final Diagnostic diagnostic) {
		if (LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(diagnostic.getSource())) {
			final List<?> data = diagnostic.getData();
			if (data != null && data.size() >= 5) {
				if (data.get(4) != null) {
					return Map.of(//
							FordiacErrorMarker.CODE, Integer.valueOf(diagnostic.getCode()), //
							FordiacErrorMarker.SOURCE, diagnostic.getSource(), //
							IMarker.LOCATION, data.get(1), //
							FordiacErrorMarker.TARGET_URI, data.get(2), //
							FordiacErrorMarker.TARGET_TYPE, data.get(3), //
							FordiacErrorMarker.TARGET_FEATURE, data.get(4), //
							FordiacErrorMarker.DATA, data.subList(5, data.size()).stream().map(Object::toString)
									.collect(Collectors.joining("\u0000")) //$NON-NLS-1$
					);
				}
				return Map.of(//
						FordiacErrorMarker.CODE, Integer.valueOf(diagnostic.getCode()), //
						FordiacErrorMarker.SOURCE, diagnostic.getSource(), //
						IMarker.LOCATION, data.get(1), //
						FordiacErrorMarker.TARGET_URI, data.get(2), //
						FordiacErrorMarker.TARGET_TYPE, data.get(3), //
						FordiacErrorMarker.DATA, data.subList(5, data.size()).stream().map(Object::toString)
								.collect(Collectors.joining("\u0000")) //$NON-NLS-1$
				);
			}
		}
		return Map.of(//
				FordiacErrorMarker.CODE, Integer.valueOf(diagnostic.getCode()), //
				FordiacErrorMarker.SOURCE, diagnostic.getSource()//
		);
	}

	public static void createMarkers(final IResource resource, final List<ErrorMarkerBuilder> builders) {
		if (resource != null && resource.isAccessible()) {
			final ISchedulingRule currentRule = Job.getJobManager().currentRule();
			if (currentRule != null && currentRule.contains(resource)) {
				createMarkersInWorkspace(resource, builders);
			} else {
				final WorkspaceJob job = new WorkspaceJob("Create error markers on resource: " + resource.getName()) { //$NON-NLS-1$
					@Override
					public IStatus runInWorkspace(final IProgressMonitor monitor) {
						createMarkersInWorkspace(resource, builders);
						return Status.OK_STATUS;
					}
				};
				job.setUser(false);
				job.setSystem(true);
				job.setPriority(Job.DECORATE);
				job.setRule(resource);
				job.setJobGroup(JOB_GROUP);
				job.schedule();
			}
		}
	}

	private static void createMarkersInWorkspace(final IResource resource, final List<ErrorMarkerBuilder> builders) {
		try {
			for (final var builder : builders) {
				builder.createMarker(resource);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not create error markers", e); //$NON-NLS-1$
		}
	}

	public static void updateMarkers(final IResource resource, final String type,
			final List<ErrorMarkerBuilder> builders, final boolean force) {
		if (resource != null && resource.isAccessible()
				&& (force || errorMarkersNeedsUpdate(resource, type, builders))) {
			final ISchedulingRule currentRule = Job.getJobManager().currentRule();
			if (currentRule != null && currentRule.contains(resource)) {
				createMarkersInWorkspace(resource, builders);
			} else {
				final WorkspaceJob job = new WorkspaceJob("Update error markers on resource: " + resource.getName()) { //$NON-NLS-1$
					@Override
					public IStatus runInWorkspace(final IProgressMonitor monitor) {
						updateMarkersInWorkspace(resource, type, builders);
						return Status.OK_STATUS;
					}
				};
				job.setUser(false);
				job.setSystem(true);
				job.setPriority(Job.DECORATE);
				job.setRule(resource);
				job.setJobGroup(JOB_GROUP);
				job.schedule();
			}
		}
	}

	private static void updateMarkersInWorkspace(final IResource resource, final String type,
			final List<ErrorMarkerBuilder> builders) {
		try {
			resource.deleteMarkers(type, true, IResource.DEPTH_INFINITE);
			for (final var builder : builders) {
				builder.createMarker(resource);
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not update error markers on resource: " + resource.getName(), //$NON-NLS-1$
					e);
		}
	}

	public static FBNetworkElement createTypeErrorMarkerFB(final String typeFbElement, final TypeLibrary typeLibrary,
			final EClass typeClass) {
		final ErrorMarkerFBNElement errorFb = createErrorMarkerFB(typeFbElement);
		final TypeEntry entry = typeLibrary.createErrorTypeEntry(typeFbElement, typeClass);
		errorFb.setTypeEntry(entry);
		return errorFb;
	}

	public static ErrorMarkerFBNElement createErrorMarkerFB(final String name) {
		final ErrorMarkerFBNElement createErrorMarkerFBNElement = LibraryElementFactory.eINSTANCE
				.createErrorMarkerFBNElement();
		createErrorMarkerFBNElement.setName(name);
		createErrorMarkerFBNElement.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		final Position position = LibraryElementFactory.eINSTANCE.createPosition();
		position.setX(0);
		position.setY(0);
		createErrorMarkerFBNElement.setPosition(position);
		return createErrorMarkerFBNElement;
	}

	private static boolean errorMarkersNeedsUpdate(final IResource resource, final String type,
			final List<ErrorMarkerBuilder> builders) {
		try {
			return !builders.isEmpty() || resource.findMarkers(type, true, IResource.DEPTH_INFINITE).length != 0;
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning("Couldn't determine marker state of resoruce: " + resource.getName(), e); //$NON-NLS-1$

		}
		return false;
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}
}
