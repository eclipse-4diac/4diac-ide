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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.fordiac.ide.validation.ocl.OCLSourceScanner;
import org.eclipse.ocl.OCLInput;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.osgi.framework.Bundle;

public final class OCLParser {
	private static final String CONSTRAINT_DIRECTORY = "constraints"; //$NON-NLS-1$
	private static final String CONSTRAINT_FILE_FBTYPE = "ECC.ocl"; //$NON-NLS-1$

	private static final Pattern IMPORT_STATEMENT = Pattern.compile("""
			(?m)^[\\t ]*import[\\t ]+\
			(?:[A-Za-z_][A-Za-z0-9_]*[\\t ]*:[\\t ]*)?\
			'[^'\\r\\n]+'[\\t ]*;?[\\t ]*(?=\\r?$)"""); //$NON-NLS-1$

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
		try (InputStream inputStream = FileLocator.toFileURL(url).openStream()) {
			return parse(inputStream, ocl).stream().map(constraint -> new LoadedConstraint(constraint, null)).toList();
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
		try (InputStream inputStream = file.getContents()) {
			parse(inputStream, ocl).forEach(constraint -> constraints.add(new LoadedConstraint(constraint, file)));
		} catch (final ParserException | IOException | RuntimeException | CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			problems.add(new ParseProblem(file, e.getMessage()));
		}
	}

	private static List<Constraint> parse(final InputStream inputStream, final OCL ocl)
			throws ParserException, IOException {
		return ocl.parse(new OCLInput(skipImports(inputStream)));
	}

	private static InputStream skipImports(final InputStream inputStream) throws IOException {
		final String source = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		final Matcher matcher = IMPORT_STATEMENT.matcher(source);
		final StringBuilder parserSource = new StringBuilder(source.length());

		while (matcher.find()) {
			/*
			 * The classic Ecore OCL parser does not accept Complete OCL import
			 * declarations. The required generated 4diac EPackages are registered
			 * explicitly by OCLValidationSession.
			 *
			 * Remove this compatibility handling when org.eclipse.ocl.xtext.completeocl
			 * (injects JDT dependency) can be used again, as the Xtext Complete OCL parser
			 * processes import declarations directly.
			 */
			matcher.appendReplacement(parserSource, Matcher.quoteReplacement(" ".repeat(matcher.group().length()))); //$NON-NLS-1$
		}
		matcher.appendTail(parserSource);

		return new ByteArrayInputStream(parserSource.toString().getBytes(StandardCharsets.UTF_8));
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
