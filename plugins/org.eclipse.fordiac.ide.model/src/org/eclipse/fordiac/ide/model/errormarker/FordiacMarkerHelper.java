/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *               2022 Primetals Technologies Austria GmbH
 *               2023 Martin Erich Jobst
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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

	public static String getLocation(final EObject object) {
		if (object instanceof final INamedElement namedElement) {
			return namedElement.getQualifiedName();
		}
		if (object != null) {
			return getLocation(object.eContainer(), object.eContainingFeature());
		}
		return ""; //$NON-NLS-1$
	}

	public static String getLocation(final EObject object, final EStructuralFeature feature) {
		if (feature != null) {
			return getLocation(object) + "/" + feature.getName(); //$NON-NLS-1$
		}
		return getLocation(object);
	}

	public static IResource getResource(final EObject object) {
		if (object != null && object.eResource() != null) {
			final URI uri = EcoreUtil.getURI(object);
			if (uri.isPlatformResource()) {
				return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
			}
		}
		return null;
	}

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

	public static String getTargetUriString(final IResource resource, final EObject object) {
		final URI uri = getTargetUri(resource, object);
		if (uri != null) {
			return uri.toString();
		}
		return null;
	}

	public static Object[] getDiagnosticData(final EObject object) {
		return getDiagnosticData(object, null);
	}

	public static Object[] getDiagnosticData(final EObject object, final EStructuralFeature feature) {
		if (object == null) {
			return new Object[0];
		}
		return new Object[] { object, // [0] object itself (per EMF convention)
				getLocation(object), // [1] LOCATION
				getTargetUriString(null, object), // [2] TARGET_URI
				EcoreUtil.getURI(object.eClass()).toString(), // [3] TARGET_TYPE
				feature != null ? EcoreUtil.getURI(feature).toString() : null, // [4] TARGET_FEATURE
		};
	}

	public static EObject getDiagnosticTarget(final Diagnostic diagnostic) {
		final List<?> data = diagnostic.getData();
		if (data != null && !data.isEmpty() && data.get(0) instanceof final EObject target) {
			return target;
		}
		return null;
	}

	public static Map<String, Object> getDiagnosticAttributes(final Diagnostic diagnostic) {
		if (LibraryElementValidator.DIAGNOSTIC_SOURCE.equals(diagnostic.getSource())) {
			final List<?> data = diagnostic.getData();
			if (data != null && data.size() >= 5) {
				if (data.get(4) != null) {
					return Map.of(IMarker.LOCATION, data.get(1), FordiacErrorMarker.TARGET_URI, data.get(2),
							FordiacErrorMarker.TARGET_TYPE, data.get(3), FordiacErrorMarker.TARGET_FEATURE,
							data.get(4));
				}
				return Map.of(IMarker.LOCATION, data.get(1), FordiacErrorMarker.TARGET_URI, data.get(2),
						FordiacErrorMarker.TARGET_TYPE, data.get(3));
			}
		}
		return Collections.emptyMap();
	}

	public static List<IMarker> findMarkers(final EObject target) {
		return findMarkers(target, FordiacErrorMarker.IEC61499_MARKER);
	}

	public static List<IMarker> findMarkers(final EObject target, final String type) {
		final IResource resource = getResource(target);
		return findMarkers(resource, target, type);
	}

	public static List<IMarker> findMarkers(final IResource resource, final EObject target, final String type) {
		final List<IMarker> result = new ArrayList<>();
		if (resource != null && resource.isAccessible() && target != null) {
			final String targetUriString = getTargetUri(resource, target).toString();
			try {
				final IMarker[] markers = resource.findMarkers(type, true, IResource.DEPTH_INFINITE);
				for (final var marker : markers) {
					if (marker.getAttribute(FordiacErrorMarker.TARGET_URI, "").equals(targetUriString)) { //$NON-NLS-1$
						result.add(marker);
					}
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Could not find error markers", e); //$NON-NLS-1$
			}
		}
		return result;
	}

	public static void findAllMarkers(final IResource resource, final EObject root, final String type,
			final BiConsumer<EObject, IMarker> consumer) {
		if (resource != null && resource.isAccessible() && root != null) {
			try {
				final IMarker[] markers = resource.findMarkers(type, true, IResource.DEPTH_INFINITE);
				for (final var marker : markers) {
					final EObject target = FordiacErrorMarker.getTargetRelative(marker, root);
					if (target != null) {
						consumer.accept(target, marker);
					}
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Could not find all error markers", e); //$NON-NLS-1$
			}
		}
	}

	public static void createMarkers(final IResource resource, final List<ErrorMarkerBuilder> builders) {
		if (resource != null && resource.isAccessible()) {
			final WorkspaceJob job = new WorkspaceJob("Create error markers on resource: " + resource.getName()) { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) {
					try {
						for (final var builder : builders) {
							builder.createMarker(resource);
						}
					} catch (final CoreException e) {
						FordiacLogHelper.logError("Could not create error markers", e); //$NON-NLS-1$
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(false);
			job.setSystem(true);
			job.setPriority(Job.DECORATE);
			job.setRule(resource);
			job.schedule();
		}
	}

	public static void updateMarkers(final IResource resource, final List<ErrorMarkerBuilder> builders) {
		updateMarkers(resource, FordiacErrorMarker.IEC61499_MARKER, builders);
	}

	public static void updateMarkers(final IResource resource, final String type,
			final List<ErrorMarkerBuilder> builders) {
		if (resource != null && resource.isAccessible() && (errorMarkersNeedsUpdate(resource, type, builders))) {
			final WorkspaceJob job = new WorkspaceJob("Update error markers on resource: " + resource.getName()) { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) {
					try {
						resource.deleteMarkers(type, true, IResource.DEPTH_INFINITE);
						for (final var builder : builders) {
							builder.createMarker(resource);
						}
					} catch (final CoreException e) {
						FordiacLogHelper.logError("Could not update error markers on resource: " + resource.getName(), //$NON-NLS-1$
								e);
					}
					return Status.OK_STATUS;
				}
			};
			job.setUser(false);
			job.setSystem(true);
			job.setPriority(Job.DECORATE);
			job.setRule(resource);
			job.schedule();
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
