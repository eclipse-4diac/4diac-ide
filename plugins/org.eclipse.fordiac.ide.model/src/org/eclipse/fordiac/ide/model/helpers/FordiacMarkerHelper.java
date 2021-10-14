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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.dataimport.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public final class FordiacMarkerHelper {

	public static final String TARGET_TYPE = "Target.Type"; //$NON-NLS-1$

	private static final String FB_NETWORK_ELEMENT_TARGET = "FBNetworkElement"; //$NON-NLS-1$
	private static final String CONNECTION_TARGET = "Connection"; //$NON-NLS-1$
	private static final String VALUE_TARGET = "Value"; //$NON-NLS-1$

	private static final Map<Long, ErrorMarkerRef> markers = new ConcurrentHashMap<>();

	public static ErrorMarkerRef getMarkerRefById(final Long id) {
		return markers.get(id);
	}

	public static boolean markerTargetsFBNetworkElement(final Map<String, Object> attrs) {
		return FB_NETWORK_ELEMENT_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static boolean markerTargetsConnection(final Map<String, Object> attrs) {
		return CONNECTION_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static boolean markerTargetsValue(final Map<String, Object> attrs) {
		return VALUE_TARGET.equals(attrs.get(TARGET_TYPE));
	}

	public static void addTargetIdentifier(final EObject element, final Map<String, Object> attrs) {
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

	public static String getTargetIdentifier(final EObject element) {
		if (element instanceof FBNetworkElement) {
			return FB_NETWORK_ELEMENT_TARGET;
		}
		if (element instanceof Connection) {
			return CONNECTION_TARGET;
		}
		if (element instanceof Value) {
			return VALUE_TARGET;
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
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1); // remove the last dot
		}

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
	}

	public static void createMarkerInFile(final ErrorMarkerBuilder errorMarker) {
		final IFile file = getFileFromRef(errorMarker.getErrorMarkerRef());
		createMarkerInFile(errorMarker, file);

	}

	@SuppressWarnings("boxing")
	public static void createMarkerInFile(final ErrorMarkerBuilder errorMarker, final IFile file) {
		Assert.isNotNull(file);
		try {
			final IMarker marker = file.createMarker(IMarker.PROBLEM, errorMarker.getAttributes());
			if (marker.exists() && errorMarker.getErrorMarkerRef() != null) {
				markers.put(marker.getId(), errorMarker.getErrorMarkerRef());
				errorMarker.addId(marker.getId());
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError("could not create error marker", e); //$NON-NLS-1$
		}
	}

	private FordiacMarkerHelper() {
		throw new UnsupportedOperationException("FordiacMarkerHelper should not be instantiated"); //$NON-NLS-1$
	}

	public static ErrorMarkerBuilder deleteErrorMarker(final ErrorMarkerRef ie) {

		final IFile file = getFileFromRef(ie);

		return deleteMarkerInJob(file, ie);

	}

	private static ErrorMarkerBuilder deleteMarkerInJob(final IFile f, final ErrorMarkerRef ie) {
		final long markerId = ie.getFileMarkerId();
		final IMarker marker = f.getMarker(markerId);
		ie.setFileMarkerId(0);  // remove errormarker id from errorMarkerref
		markers.remove(Long.valueOf(markerId));

		final WorkspaceJob job = new WorkspaceJob(
				MessageFormat.format(Messages.FordiacMarkerHelper_RemoveErrorMarkersFromFile, f.getName())) {
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
		final ErrorMarkerBuilder errorMarkerAttribute = new ErrorMarkerBuilder(marker, ie);
		job.setRule(f.getProject());
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
			Activator.getDefault().logError("Delete marker Job interrupted", e); //$NON-NLS-1$
			Thread.currentThread().interrupt();
		}
		return errorMarkerAttribute;
	}

	private static IFile getFileFromRef(final ErrorMarkerRef ie) {
		final EObject rootContainer = EcoreUtil.getRootContainer(ie);
		IFile systemFile = null;
		if (rootContainer instanceof AutomationSystem) {
			systemFile = ((AutomationSystem) rootContainer).getSystemFile();
		} else if (rootContainer instanceof FBType) {
			systemFile = ((FBType) rootContainer).getPaletteEntry().getFile();
		}
		Assert.isNotNull(systemFile);
		return systemFile;
	}

	@SuppressWarnings("boxing")
	public static ErrorMarkerBuilder createErrorMarkerBuilder(final Map<String, Object> attrs, final int lineNumber) {
		attrs.put(IMarker.LINE_NUMBER, lineNumber);
		return new ErrorMarkerBuilder(attrs, null);

	}

	public static ErrorMarkerBuilder createConnectionErrorMarkerBuilder(final String message, final FBNetwork fbNetwork,
			final String sourceIdentifier, final String destinationIdentifier, final int lineNumber) {
		final Map<String, Object> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);

		// use a dummy connection to get target identifier
		FordiacMarkerHelper.addTargetIdentifier(LibraryElementFactory.eINSTANCE.createDataConnection(), attrs);
		final String location = FordiacMarkerHelper.getLocation(fbNetwork) + "." + sourceIdentifier + " -> " //$NON-NLS-1$ //$NON-NLS-2$
				+ destinationIdentifier;
		attrs.put(IMarker.LOCATION, location);
		return createErrorMarkerBuilder(attrs, lineNumber);

	}

	public static ErrorMarkerBuilder createValueErrorMarkerBuilder(final String message, final Value value,
			final int lineNumber) {
		final Map<String, Object> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);

		FordiacMarkerHelper.addTargetIdentifier(value, attrs);
		final IInterfaceElement ie = (IInterfaceElement) value.eContainer();
		final String location = FordiacMarkerHelper.getLocation(ie.getFBNetworkElement()) + "." + ie.getName(); //$NON-NLS-1$
		attrs.put(IMarker.LOCATION, location);
		final ErrorMarkerBuilder builder = createErrorMarkerBuilder(attrs, lineNumber);
		builder.setErrorMarkerRef(value);
		return builder;
	}

	public static ErrorMarkerBuilder createErrorMarker(final String message, final INamedElement errorLocation,
			final int lineNumber) {
		final Map<String, Object> attrs = new HashMap<>();
		attrs.put(IMarker.MESSAGE, message);
		FordiacMarkerHelper.addLocation(errorLocation, attrs);
		FordiacMarkerHelper.addTargetIdentifier(errorLocation, attrs);
		return createErrorMarkerBuilder(attrs, lineNumber);
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

	public static FBNetworkElement createTypeErrorMarkerFB(final String typeFbElement, final TypeLibrary typeLibrary,
			final EClass typeClass, final EClass entryClass) {
		final ErrorMarkerFBNElement errorFb = FordiacMarkerHelper.createErrorMarkerFB(typeFbElement);

		final FBType fbType = (FBType) LibraryElementFactory.eINSTANCE.create(typeClass);
		final PaletteEntry entry = (PaletteEntry) PaletteFactory.eINSTANCE.create(entryClass);
		entry.setType(fbType);
		entry.setLabel(typeFbElement);
		fbType.setPaletteEntry(entry);
		fbType.setName(typeFbElement);
		fbType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		errorFb.setInterface(fbType.getInterfaceList().copy());
		errorFb.setPaletteEntry(entry);
		typeLibrary.getErrorTypeLib().addPaletteEntry(entry);
		return errorFb;
	}

}
