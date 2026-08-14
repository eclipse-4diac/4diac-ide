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

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.handlers.IValidationMarker;
import org.eclipse.fordiac.ide.validation.handlers.OCLParser;
import org.eclipse.fordiac.ide.validation.handlers.ValidationHelper;
import org.eclipse.fordiac.ide.validation.ocl.OCLSourceScanner;
import org.eclipse.fordiac.ide.validation.preferences.PreferenceConstants;
import org.eclipse.ocl.ecore.Constraint;

public class OCLValidationBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.fordiac.ide.validation.oclbuilder"; //$NON-NLS-1$

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		if (!isOclValidationEnabled()) {
			getProject().deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
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

	private void fullBuild(final SubMonitor monitor) {
		final List<Constraint> constraints = OCLParser.loadOCLConstraints(getProject());
		for (final IFile file : OCLSourceScanner.findValidationTargets(getProject())) {
			if (isBuildCanceled(monitor)) {
				throw new OperationCanceledException();
			}
			validateFile(file, constraints, monitor.split(1));
		}
	}

	private void incrementalBuild(final SubMonitor monitor) throws CoreException {
		final IResourceDelta delta = getDelta(getProject());
		if (delta == null || needsFullOclBuild(delta)) {
			fullBuild(monitor);
			return;
		}
		final List<Constraint> constraints = OCLParser.loadOCLConstraints(getProject());
		delta.accept((IResourceDeltaVisitor) resourceDelta -> {
			if (isBuildCanceled(monitor)) {
				throw new OperationCanceledException();
			}
			if (resourceDelta.getResource() instanceof final IFile file
					&& resourceDelta.getKind() != IResourceDelta.REMOVED && OCLSourceScanner.isValidationTargetFile(file)) {
				validateFile(file, constraints, monitor.split(1));
			}
			return true;
		}, IResourceDelta.ADDED | IResourceDelta.CHANGED | IResourceDelta.CONTENT | IResourceDelta.REMOVED);
	}

	private static boolean needsFullOclBuild(final IResourceDelta delta) throws CoreException {
		final boolean[] result = { false };
		delta.accept((IResourceDeltaVisitor) resourceDelta -> {
			if (resourceDelta.getResource() instanceof final IFile file
					&& (OCLSourceScanner.isOclFile(file) || BuildpathUtil.BUILDPATH_FILE_NAME.equals(file.getName()))) {
				result[0] = true;
				return false;
			}
			return !result[0];
		});
		return result[0];
	}

	private static void validateFile(final IFile file, final List<Constraint> constraints,
			final IProgressMonitor monitor) {
		try {
			TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (entry == null) {
				entry = TypeLibraryManager.INSTANCE.getTypeLibrary(file.getProject()).createTypeEntry(file);
			}
			if (entry != null && entry.getType() instanceof final INamedElement namedElement) {
				ValidationHelper.validateSync(namedElement, constraints, monitor);
			}
		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		getProject().deleteMarkers(IValidationMarker.TYPE, true, IResource.DEPTH_INFINITE);
	}

	private boolean isOclValidationEnabled() {
		final IEclipsePreferences preferences = new ProjectScope(getProject())
				.getNode(PreferenceConstants.VALIDATION_PREFERENCES_ID);
		return preferences.getBoolean(PreferenceConstants.ENABLE_OCL_VALIDATION_BUILDER,
				PreferenceConstants.DEFAULT_ENABLE_OCL_VALIDATION_BUILDER);
	}

	private boolean isBuildCanceled(final IProgressMonitor monitor) {
		return isInterrupted() || monitor.isCanceled();
	}
}
