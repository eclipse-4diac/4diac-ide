/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.listeners;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.commands.QualNameChange;
import org.eclipse.fordiac.ide.model.commands.QualNameChangeListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public class UtilityMarkerListener extends QualNameChangeListener {

	@Override
	public void onCommandExecuted(final List<QualNameChange> qualNameChange) {
		removeInvalidMarkers(qualNameChange);
		super.onCommandExecuted(qualNameChange);
	}

	@Override
	public void onCommandUndoExecuted(final List<QualNameChange> qualNameChange) {
		removeInvalidMarkers(qualNameChange);
		super.onCommandUndoExecuted(qualNameChange);
	}

	@Override
	public void onCommandRedoExecuted(final List<QualNameChange> qualNameChange) {
		removeInvalidMarkers(qualNameChange);
		super.onCommandRedoExecuted(qualNameChange);
	}

	@Override
	protected List<AbstractOperation> constructExecutableOperations(final QualNameChange change,
			final Object receiver) {
		return Collections.emptyList();
	}

	@Override
	protected List<AbstractOperation> constructExecutableUndoOperations(final QualNameChange change,
			final Object receiver) {
		return Collections.emptyList();
	}

	@Override
	protected Object getReceiver(final TypeEntry key) {
		return null;
	}

	@Override
	protected void executeOperation(final AbstractOperation op) {
		// do nothing
	}

	private static void removeInvalidMarkers(final List<QualNameChange> qualNameChange) {
		final Set<String> oldQualNames = new HashSet<>();
		qualNameChange.stream().map(QualNameChange::oldQualName).forEach(oldQualNames::add);
		getAllUtilityMarkers().filter(m -> oldQualNames.contains(getLocation(m))).forEach(m -> {
			try {
				UtilityMarkerHelper.deleteElementMarker(m.getType(), m.getResource());
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		});

	}

	private static Stream<IMarker> getAllUtilityMarkers() {
		final LibraryElement libElement = EditorUtils.getCurrentActiveEditor().getAdapter(LibraryElement.class);
		if (utilityMarkerTargetLibraryElement(libElement)) {
			try {
				final IFile file = libElement.getTypeEntry().getFile();
				return Stream.concat(
						Stream.of(file.findMarkers(UtilityMarkerHelper.CONNECTION_SRC_MARKER_ID, false,
								IResource.DEPTH_ZERO)),
						Stream.of(file.findMarkers(UtilityMarkerHelper.PREDECESSOR_MARKER_ID, false,
								IResource.DEPTH_ZERO)));
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Cannot fetch marker", e); //$NON-NLS-1$
			}
		}
		return Stream.empty();
	}

	private static boolean utilityMarkerTargetLibraryElement(final LibraryElement libElement) {
		return libElement instanceof AutomationSystem || libElement instanceof SubAppType
				|| libElement instanceof CompositeFBType;
	}

	public static String getLocation(final IMarker m) {
		if (m != null) {
			try {
				if (m.getType().equals(UtilityMarkerHelper.CONNECTION_SRC_MARKER_ID)) {
					final String containerLocation = m.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
					final int lastDot = containerLocation.lastIndexOf('.');
					if (lastDot != -1) {
						return containerLocation.substring(0, lastDot);
					}
				}
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			return m.getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

}
