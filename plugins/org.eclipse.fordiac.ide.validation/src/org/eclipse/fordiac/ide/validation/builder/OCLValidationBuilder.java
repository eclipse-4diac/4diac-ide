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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.nature.FordiacNature;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.ocl.OCLMarkerManager;
import org.eclipse.fordiac.ide.validation.ocl.OCLSourceScanner;
import org.eclipse.fordiac.ide.validation.ocl.OCLValidationSession;

public class OCLValidationBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.fordiac.ide.validation.oclbuilder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		if (!FordiacNature.isOCLValidationBuilderEnabled(getProject())) {
			OCLMarkerManager.deleteMarkers(getProject());
			return new IProject[0];
		}

		final SubMonitor progress = SubMonitor.convert(monitor, IProgressMonitor.UNKNOWN);
		switch (kind) {
		case FULL_BUILD -> fullBuild(progress);
		case INCREMENTAL_BUILD, AUTO_BUILD -> incrementalBuild(progress);
		default -> {
			// nothing to do
		}
		}
		return new IProject[0];
	}

	private void fullBuild(final SubMonitor monitor) throws CoreException {
		final List<IFile> files = OCLSourceScanner.findValidationTargets(getProject());
		monitor.setWorkRemaining(files.size() * 2 + 1);
		final List<INamedElement> validationTargets = new ArrayList<>(files.size());
		for (final IFile file : files) {
			if (isBuildCanceled(monitor)) {
				throw new OperationCanceledException();
			}
			final INamedElement validationTarget = loadValidationTarget(file);
			if (validationTarget != null) {
				validationTargets.add(validationTarget);
			}
			monitor.worked(1);
		}

		try (OCLValidationSession session = OCLValidationSession.create(getProject(), validationTargets)) {
			monitor.worked(1);
			for (final INamedElement validationTarget : validationTargets) {
				if (isBuildCanceled(monitor)) {
					throw new OperationCanceledException();
				}
				session.validate(validationTarget, monitor.split(1));
			}
			OCLMarkerManager.replaceMarkers(getProject(), session.getMarkers());
		}
	}

	private void incrementalBuild(final SubMonitor monitor) throws CoreException {
		final IResourceDelta projectDelta = getDelta(getProject());
		if (projectDelta == null || needsFullOclBuild(projectDelta)) {
			fullBuild(monitor);
			return;
		}
	}

	private static boolean needsFullOclBuild(final IResourceDelta delta) throws CoreException {
		final boolean[] result = { false };
		delta.accept((IResourceDeltaVisitor) resourceDelta -> {
			if (resourceDelta.getResource() instanceof final IFile file
					&& (OCLSourceScanner.isOclFile(file) || OCLSourceScanner.isValidationTargetFile(file)
							|| BuildpathUtil.BUILDPATH_FILE_NAME.equals(file.getName()))) {
				result[0] = true;
				return false;
			}
			return !result[0];
		});
		return result[0];
	}

	private static INamedElement loadValidationTarget(final IFile file) {
		try {
			TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (entry == null) {
				entry = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject()).createTypeEntry(file);
			}
			if (entry != null && entry.getType() instanceof final INamedElement namedElement) {
				return namedElement;
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return null;
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		OCLMarkerManager.deleteMarkers(getProject());
	}

	private boolean isBuildCanceled(final IProgressMonitor monitor) {
		return isInterrupted() || monitor.isCanceled();
	}
}
