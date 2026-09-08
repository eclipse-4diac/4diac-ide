/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.osgi.framework.Version;

public class LibraryChange {

	public enum ChangeType {
		REMOVE, UPDATE, DOWNGRADE, NOP, ADD
	}

	private final String symbolicName;
	private final String currentVersion;
	private String targetVersion;
	private ChangeType type;

	public static LibraryChange createChange(final String symbolicName, final String currentVersion,
			final String targetVersion) {
		final int result = new VersionComparator().compare(currentVersion, targetVersion);
		if (result == 0) {
			return new LibraryChange(symbolicName, currentVersion, targetVersion, ChangeType.NOP);
		}
		return result < 0 ? new LibraryChange(symbolicName, currentVersion, targetVersion, ChangeType.UPDATE)
				: new LibraryChange(symbolicName, currentVersion, targetVersion, ChangeType.DOWNGRADE);
	}

	public static LibraryChange createAdd(final String symbolicName, final String version) {
		return new LibraryChange(symbolicName, Version.emptyVersion.toString(), version, ChangeType.ADD);
	}

	public static LibraryChange createRemove(final String symbolicName, final String version) {
		return new LibraryChange(symbolicName, version, Version.emptyVersion.toString(), ChangeType.REMOVE);
	}

	public static LibraryChange createEmpty(final String symbolicName, final String version) {
		return new LibraryChange(symbolicName, version, Version.emptyVersion.toString(), ChangeType.NOP);
	}

	public LibraryChange(final String symbolicName, final String currentVersion, final String targetVersion,
			final ChangeType type) {
		this.symbolicName = symbolicName;
		this.currentVersion = currentVersion;
		this.targetVersion = targetVersion;
		this.type = type;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	public void setTargetVersion(final String targetVersion) {
		this.targetVersion = targetVersion;
	}

	public void setType(final ChangeType type) {
		this.type = type;
	}

	public ChangeType getType() {
		return this.type;
	}

	public String getText() {
		return switch (getType()) {
		case REMOVE: {
			yield Messages.LibraryChange_Remove;
		}
		case UPDATE: {
			yield MessageFormat.format(Messages.LibraryChange_Update, getTargetVersion());
		}
		case DOWNGRADE: {
			yield MessageFormat.format(Messages.LibraryChange_Downgrade, getTargetVersion());
		}
		case NOP: {
			yield Messages.LibraryChange_Select + " .."; //$NON-NLS-1$
		}
		default:
			yield ""; //$NON-NLS-1$
		};
	}

	public String getDescription() {
		switch (this.getType()) {
		case REMOVE:
			return MessageFormat.format(Messages.LibraryChange_Remove_fullText, getSymbolicName());
		case UPDATE:
			return MessageFormat.format(Messages.LibraryChange_Update_fullText, getSymbolicName(), getCurrentVersion(),
					getTargetVersion());
		case DOWNGRADE:
			return MessageFormat.format(Messages.LibraryChange_Downgrade_fullText, getSymbolicName(),
					getCurrentVersion(), getTargetVersion());
		case ADD:
			return MessageFormat.format(Messages.LibraryChange_Add_fullText, getSymbolicName(), getTargetVersion());
		default:
			break;
		}
		return ""; //$NON-NLS-1$
	}

	public static void performChanges(final List<LibraryChange> changes, final IProject project,
			final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, changes.size() + 1);

		final Map<String, LinkedLibrary> linkedLibraries = LinkedLibrary.getAll(project, progress.split(1))
				.collect(Collectors.toMap(LinkedLibrary::getSymbolicName, f -> f));

		for (final LibraryChange change : changes) {
			if (progress.isCanceled()) {
				throw new OperationCanceledException();
			}

			progress.subTask(change.getDescription());

			switch (change.getType()) {
			case REMOVE -> removeLibrary(linkedLibraries.get(change.getSymbolicName()), progress.split(1));
			case ADD -> addLibrary(change.getSymbolicName(), change.getTargetVersion(), project, progress.split(1));
			case UPDATE, DOWNGRADE -> {
				final SubMonitor changeProgress = progress.split(1).setWorkRemaining(2);
				removeLibrary(linkedLibraries.get(change.getSymbolicName()), changeProgress.split(1));
				addLibrary(change.getSymbolicName(), change.getTargetVersion(), project, changeProgress.split(1));
			}
			default -> progress.worked(1);
			}
		}
	}

	private static void removeLibrary(final LinkedLibrary linkedLibrary, final IProgressMonitor monitor)
			throws CoreException {
		if (linkedLibrary != null && linkedLibrary.isValid()) {
			linkedLibrary.getFolder().delete(true, monitor);
		}
	}

	private static void addLibrary(final String symbolicName, final String version, final IProject project,
			final IProgressMonitor monitor) throws CoreException {
		final java.net.URI libUri = LibraryManager.INSTANCE.getLibraryURI(project, symbolicName, new Version(version),
				monitor);

		if ((libUri != null) && !LibraryManager.INSTANCE.importLibrary(project, libUri, false, false)) {
			throw new CoreException(Status
					.error(MessageFormat.format("Error while importing Library {0} - {1}", symbolicName, version))); //$NON-NLS-1$
		}
	}

}
