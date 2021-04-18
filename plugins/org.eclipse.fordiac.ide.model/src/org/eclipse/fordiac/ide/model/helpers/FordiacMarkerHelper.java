/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerAttribute;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class FordiacMarkerHelper {

	public static final String TARGET_TYPE = "Target.Type"; //$NON-NLS-1$

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$
	private static final String CONNECTION_TARGET = "Connection"; //$NON-NLS-1$

	public static boolean markerTargetsFBNetworkElement(final Map<String, Object> attrs) {
		return FB_NETWORK_ELEMENT_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static void addTargetIdentifier(final INamedElement element, final Map<String, Object> attrs) {
		final String targetIdentifier = getTargetIdentifier(element);
		if (null != targetIdentifier) {
			attrs.put(TARGET_TYPE, targetIdentifier);
		}
	}

	public static void addLocation(final INamedElement element, final Map<String, Object> attrs) {
		final String location = getLocation(element);
		if (null != location) {
			attrs.put(IMarker.LOCATION, location);
		}
	}

	public static String getTargetIdentifier(final INamedElement element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		if (element instanceof Connection) {
			return CONNECTION_TARGET;
		}
		return null;
	}

	public static String getLocation(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return getFBNElementLocation((FBNetworkElement) element);
		}
		if (element instanceof FBNetwork) {
			return getFBNetworkLocation((FBNetwork) element);
		}
		return null;
	}

	private static String getFBNElementLocation(final FBNetworkElement element) {
		final EObject container = element.eContainer().eContainer();
		if (container instanceof FBType) {
			return element.getName();
		}
		final StringBuilder builder = new StringBuilder(element.getName());
		createHierarchicalName(element.eContainer().eContainer(), builder);
		return builder.toString();
	}

	private static String getFBNetworkLocation(final FBNetwork element) {
		final EObject container = element.eContainer();
		if (container instanceof FBType) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder builder = new StringBuilder();
		createHierarchicalName(container, builder);

		return builder.toString();
	}

	private static void createHierarchicalName(final EObject container, final StringBuilder builder) {
		EObject runner = container;
		while (runner instanceof SubApp) {
			final SubApp parent = (SubApp) runner;
			builder.insert(0, '.');
			builder.insert(0, parent.getName());
			runner = parent.getFbNetwork().eContainer();
		}
		if (runner instanceof Application) {
			builder.insert(0, '.');
			builder.insert(0, ((Application) runner).getName());
		}

		if (runner instanceof Resource) {
			builder.insert(0, '.');
			builder.insert(0, ((Resource) runner).getName());
			if (runner.eContainer() != null) {
				builder.insert(0, '.');
				builder.insert(0, ((Device) runner.eContainer()).getName());
			}
		}

		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1); // remove the last dot
		}
	}

	public static void createMarker(final ErrorMarkerAttribute errorMarker) {
		final IFile file = getFileFromRef(errorMarker.getErrorMarkerRef());
		createMarker(errorMarker, file);

	}

	public static void createMarker(final ErrorMarkerAttribute errorMarker, final IFile file) {
		Assert.isNotNull(file);
		try {
			final IMarker marker = file.createMarker(IMarker.PROBLEM, errorMarker.getAttributes());
			if (marker.exists()) {
				errorMarker.addId(marker.getId());
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError("could not create error marker", e); //$NON-NLS-1$
		}
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}

	public static ErrorMarkerAttribute deleteErrorMarker(final ErrorMarkerRef ie) {

		final IFile file = getFileFromRef(ie);

		return deleteMarkerInJob(file, ie);

	}

	private static IFile getFileFromRef(final ErrorMarkerRef ie) {
		final EObject rootContainer = EcoreUtil.getRootContainer(ie);
		if (rootContainer instanceof AutomationSystem) {
			return ((AutomationSystem) rootContainer).getSystemFile();
		} else if (rootContainer instanceof FBType) {
			return ((FBType) rootContainer).getPaletteEntry().getFile();
		}
		return null;
	}

	private static ErrorMarkerAttribute deleteMarkerInJob(final IFile f, final ErrorMarkerRef ie) {
		final IMarker marker = f.getMarker(ie.getFileMarkerId());
		final WorkspaceJob job = new WorkspaceJob("Remove error markers from file: " + f.getName()) { //$NON-NLS-1$
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				try {
					marker.delete();
				} catch (final CoreException e) {
					Activator.getDefault().logError("Could not delete error marker", e); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}
		};
		final ErrorMarkerAttribute errorMarkerAttribute = new ErrorMarkerAttribute(marker, ie);
		job.setRule(f.getProject());
		job.schedule();
		return errorMarkerAttribute;
	}

}
