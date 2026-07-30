/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - load project and build path OCL files
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.fordiac.ide.validation.ocl.OCLSourceScanner;
import org.eclipse.ocl.pivot.Constraint;
import org.eclipse.ocl.pivot.resource.ASResource;
import org.eclipse.ocl.pivot.resource.CSResource;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.ocl.pivot.utilities.PivotUtil;
import org.osgi.framework.Bundle;

public final class OCLParser {
	private static final String CONSTRAINT_DIRECTORY = "constraints"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_FBTYPE = "ECC.ocl"; //$NON-NLS-1$

	private OCLParser() {
		throw new UnsupportedOperationException();
	}

	public static ParseResult loadOCLConstraints(final IProject project, final OCL ocl) {
		final List<LoadedConstraint> constraints = new ArrayList<>();
		final List<ParseProblem> problems = new ArrayList<>();
		constraints.addAll(loadBundledOCLConstraints(CONSTRAINT_FILE_FBTYPE, ocl));
		loadBuildpathOCLConstraints(project, ocl, constraints, problems);
		return new ParseResult(constraints, problems);
	}

	// Deprecated: retained for legacy ECC constraints until they are transformed to
	// model invariants
	private static List<LoadedConstraint> loadBundledOCLConstraints(final String constraintFile, final OCL ocl) {
		final Bundle bundle = Activator.getDefault().getBundle();
		final URL url = FileLocator.find(bundle, new Path(CONSTRAINT_DIRECTORY + IPath.SEPARATOR + constraintFile));
		if (url == null) {
			return List.of();
		}
		try {
			final URI uri = URI.createURI(FileLocator.toFileURL(url).toExternalForm());
			return parse(uri, ocl).stream().map(constraint -> new LoadedConstraint(constraint, null)).toList();
		} catch (ParserException | IOException | RuntimeException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
		return List.of();
	}

	private static void loadBuildpathOCLConstraints(final IProject project, final OCL ocl,
			final List<LoadedConstraint> constraints, final List<ParseProblem> problems) {
		if (project == null || !project.isAccessible()) {
			return;
		}
		for (final IFile file : OCLSourceScanner.findOclFiles(project)) {
			loadOCLConstraints(file, ocl, constraints, problems);
		}
	}

	private static void loadOCLConstraints(final IFile file, final OCL ocl, final List<LoadedConstraint> constraints,
			final List<ParseProblem> problems) {
		try {
			final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			parse(uri, ocl).forEach(constraint -> constraints.add(new LoadedConstraint(constraint, file)));
		} catch (final ParserException | IOException | RuntimeException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			problems.add(new ParseProblem(file, e.getMessage()));
		}
	}

	private static List<Constraint> parse(final URI uri, final OCL ocl) throws ParserException, IOException {
		final CSResource concreteSyntax = ocl.getCSResource(uri);
		PivotUtil.checkResourceErrors("Failed to parse '" + uri + "'", concreteSyntax); //$NON-NLS-1$ //$NON-NLS-2$
		final ASResource abstractSyntax = ocl.cs2as(concreteSyntax);
		PivotUtil.checkResourceErrors("Failed to parse '" + uri + "'", abstractSyntax); //$NON-NLS-1$ //$NON-NLS-2$

		final List<Constraint> constraints = new ArrayList<>();
		for (final TreeIterator<EObject> iterator = abstractSyntax.getAllContents(); iterator.hasNext();) {
			if (iterator.next() instanceof final Constraint constraint) {
				constraints.add(constraint);
			}
		}
		return constraints;
	}

	public record ParseResult(List<LoadedConstraint> constraints, List<ParseProblem> problems) {
		public ParseResult {
			constraints = List.copyOf(constraints);
			problems = List.copyOf(problems);
		}
	}

	public record LoadedConstraint(Constraint constraint, IFile source) {
		// empty record body
	}

	public record ParseProblem(IFile source, String message) {
		// empty record body
	}
}
