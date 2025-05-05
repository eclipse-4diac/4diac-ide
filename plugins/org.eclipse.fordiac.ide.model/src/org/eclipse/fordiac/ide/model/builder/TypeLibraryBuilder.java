/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - move file handling over from FordiacResourceChangeListener
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.builder;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class TypeLibraryBuilder extends IncrementalProjectBuilder {

	private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("Name=\\\"(\\w*)\\\""); //$NON-NLS-1$

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Building Type Library", 1); //$NON-NLS-1$
		if (TypeLibraryManager.INSTANCE.getTypeLibrary(getProject()) != null) {
			if (kind == FULL_BUILD) {
				TypeLibraryManager.INSTANCE.getTypeLibrary(getProject()).refresh();
			} else {
				getDelta(getProject()).accept(visitor);
			}
		}
		progress.worked(1);
		SubMonitor.done(monitor);
		return new IProject[0];
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Deleting Type Library", 1); //$NON-NLS-1$
		TypeLibraryManager.INSTANCE.getTypeLibrary(getProject()).clear();
		progress.worked(1);
		SubMonitor.done(monitor);
	}

	@Override
	public ISchedulingRule getRule(final int kind, final Map<String, String> args) {
		return getProject();
	}

	IResourceDeltaVisitor visitor = delta -> {
		switch (delta.getKind()) {
		case IResourceDelta.CHANGED:
			return handleResourceChanged(delta);
		case IResourceDelta.REMOVED:
			if (!testFlags(delta, IResourceDelta.MOVED_TO)) {
				// move handled in MoveParticipant and RenameParticipant
				return handleResourceRemoved(delta);
			}
			break;
		case IResourceDelta.ADDED:
			if (!testFlags(delta, IResourceDelta.MOVED_FROM)) {
				// move handled in MoveParticipant and RenameParticipant
				return handleResourceAdded(delta);
			}
			break;
		default:
			break;
		}
		return true;
	};

	private static boolean handleResourceChanged(final IResourceDelta delta) {
		if (delta.getResource().getType() == IResource.FILE && testFlags(delta, IResourceDelta.CONTENT)) {
			refreshTypeEntry(delta);
		}
		return true;
	}

	private static boolean handleResourceRemoved(final IResourceDelta delta) {
		if (delta.getResource().getType() == IResource.FILE) {
			handleFileDelete(delta);
		}
		return true;
	}

	private static void refreshTypeEntry(final IResourceDelta delta) {
		final IFile file = (IFile) delta.getResource();

		final TypeEntry typeEntryForFile = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		if (typeEntryForFile != null
				&& typeEntryForFile.getLastModificationTimestamp() != file.getModificationStamp()) {
			typeEntryForFile.refresh();
		}
	}

	private static void handleFileDelete(final IResourceDelta delta) {
		final IFile file = (IFile) delta.getResource();
		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());

		final TypeEntry entry = typeLib.getTypeEntry(file);
		if (null != entry) {
			typeLib.removeTypeEntry(entry);
		}
	}

	private static boolean handleResourceAdded(final IResourceDelta delta) {
		if (delta.getResource().getType() == IResource.FILE) {
			handleFileAdded(delta);
		}
		return true;
	}

	private static void handleFileAdded(final IResourceDelta delta) {
		final IFile file = (IFile) delta.getResource();
		if (delta.getFlags() != IResourceDelta.MARKERS) {
			final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject());
			final TypeEntry entry = typeLib.createTypeEntry(file);
			if (null != entry && containedTypeNameIsDifferent(file)) {
				// we only need to update the type entry if the file content is different from
				// the file name this happens when a type is copied into a new project or when a
				// project is opened or imported
				final LibraryElement type = entry.getType();
				final String newTypeName = TypeEntry.getTypeNameFromFile(file);
				if ((null != type) && // this means we couldn't load the type seems
				// like a problem in the type's XML file
						(!newTypeName.equals(type.getName()))) {
					type.setName(newTypeName);
					try {
						entry.save(type);
					} catch (final CoreException e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				}
			}
		}
	}

	private static boolean containedTypeNameIsDifferent(final IFile file) {
		try (Scanner scanner = new Scanner(file.getContents())) {
			if (scanner.findWithinHorizon(TYPE_NAME_PATTERN, 0) != null) {
				final String name = scanner.match().group(1);
				final String typeName = TypeEntry.getTypeNameFromFile(file);
				return !typeName.equals(name);
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return true;
	}

	private static boolean testFlags(final IResourceDelta delta, final int flags) {
		return (delta.getFlags() & flags) == flags;
	}
}
