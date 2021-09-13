/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class ErrorMarkerBuilder {

	public static final String TARGET_TYPE = "Target.Type"; //$NON-NLS-1$

	private Map<String, Object> attributes;
	private ErrorMarkerRef errorMarkerRef;

	private String type = IMarker.PROBLEM;

	private static final Map<Long, ErrorMarkerRef> markers = new ConcurrentHashMap<>();

	public static ErrorMarkerRef getMarkerRef(final IMarker marker) {
		return markers.get(Long.valueOf(marker.getId()));
	}

	public ErrorMarkerBuilder() {
		this.attributes = new HashMap<>();
		this.attributes.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_HIGH));
		this.attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_ERROR));
		this.errorMarkerRef = null;
	}

	public ErrorMarkerBuilder(final Map<String, Object> attributes, final ErrorMarkerRef errorMarkerIe) {
		this.attributes = attributes;
		this.attributes.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_HIGH));
		this.attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_ERROR));
		this.errorMarkerRef = errorMarkerIe;
	}

	public ErrorMarkerBuilder(final IMarker marker, final ErrorMarkerRef errorMarkerRef) {
		this.errorMarkerRef = errorMarkerRef;
		try {
			if (marker.exists()) {
				this.attributes = marker.getAttributes();
			} else {
				this.attributes = Collections.emptyMap();
			}
		} catch (final CoreException e) {
			Activator.getDefault().getLog().error("Error Marker not found", e); //$NON-NLS-1$
		}


	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public ErrorMarkerRef getErrorMarkerRef() {
		return errorMarkerRef;
	}

	public void setErrorMarkerRef(final ErrorMarkerRef errorMarkerRef) {
		this.errorMarkerRef = errorMarkerRef;
	}

	public void addId(final long id) {
		if (errorMarkerRef != null) {
			errorMarkerRef.setFileMarkerId(id);
		}
	}

	public void addTargetIdentifier(final EObject element) {
		final String targetIdentifier = FordiacMarkerHelper.getTargetIdentifier(element);
		if (null != targetIdentifier) {
			attributes.put(TARGET_TYPE, targetIdentifier);
		}
	}

	public void addLocation(final INamedElement element) {
		final String location = FordiacMarkerHelper.getLocation(element);
		addLocation(location);
	}

	public void addLocation(final String location) {
		if (null != location) {
			attributes.put(IMarker.LOCATION, location);
		}
	}

	public void addLineNumber(final int lineNumber) {
		attributes.put(IMarker.LINE_NUMBER, Integer.valueOf(lineNumber));
	}

	public void addMessage(final String message) {
		if (null != message) {
			attributes.put(IMarker.MESSAGE, message);
		}
	}

	public void setSeverityWarning() {
		attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_WARNING));
	}

	public void setSeverityInfo() {
		attributes.put(IMarker.SEVERITY, Integer.valueOf(IMarker.SEVERITY_INFO));
	}

	public void createMarkerInFile() {
		final IFile file = getFileFromRef(this.getErrorMarkerRef());
		createMarkerInFile(file);

	}

	public void createMarkerInFile(final IFile file) {
		Assert.isNotNull(file);
		try {
			final IMarker marker = file.createMarker(type, getAttributes());
			if (marker.exists() && getErrorMarkerRef() != null) {
				markers.put(Long.valueOf(marker.getId()), getErrorMarkerRef());
				addId(marker.getId());
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError("could not create error marker", e); //$NON-NLS-1$
		}
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

	public void createMarkerInResource(final IResource iresource) {
		Assert.isNotNull(iresource);
		try {
			final IMarker marker = iresource.createMarker(type, getAttributes());
			if (marker.exists() && getErrorMarkerRef() != null) {
				markers.put(Long.valueOf(marker.getId()), getErrorMarkerRef());
				addId(marker.getId());
			}
		} catch (final CoreException e) {
			Activator.getDefault().logError("could not create error marker", e); //$NON-NLS-1$
		}

	}

	public void setMarkerType(final String type) {
		if (null != type) {
			this.type = type;
		}

	}
}
