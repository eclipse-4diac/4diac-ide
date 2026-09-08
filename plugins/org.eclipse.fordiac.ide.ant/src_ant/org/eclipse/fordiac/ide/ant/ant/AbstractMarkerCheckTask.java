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
 *   Michael Oberlehner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractMarkerCheckTask extends Task {

	private static final Gson GSON = new GsonBuilder().serializeNulls().create();

	private static final Comparator<DiagnosticOutput> DIAGNOSTIC_COMPARATOR = Comparator
			.comparing(DiagnosticOutput::resource)
			.thenComparing(DiagnosticOutput::line, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparing(DiagnosticOutput::severity)
			.thenComparing(DiagnosticOutput::message, Comparator.nullsLast(Comparator.naturalOrder()));

	private File reportFile;

	public final void setReportFile(final File reportFile) {
		this.reportFile = reportFile;
	}

	protected static IProject requireProject(final String projectName) {
		if (projectName == null || projectName.isBlank()) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (!project.exists() || !project.isAccessible()) {
			throw new BuildException(
					"Project named '" + projectName + "' not in workspace or not accessible"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return project;
	}

	protected static void buildProject(final IProject project) {
		Import4diacProject.runFullBuild(project);
		Import4diacProject.waitBuilderJobsComplete();
	}

	protected static List<IMarker> findProblemMarkers(final IResource resource, final int depth) {
		try {
			return Arrays.asList(resource.findMarkers(IMarker.PROBLEM, true, depth));
		} catch (final CoreException e) {
			throw new BuildException("Cannot get markers for " + resource.getFullPath().toPortableString(), e); //$NON-NLS-1$
		}
	}

	protected final void reportAndFail(final String task, final IProject project, final String system,
			final List<IMarker> markers) {
		final List<DiagnosticOutput> diagnostics = markers.stream().map(AbstractMarkerCheckTask::toDiagnostic)
				.sorted(DIAGNOSTIC_COMPARATOR).toList();
		final long errors = countSeverity(diagnostics, "ERROR"); //$NON-NLS-1$
		final long warnings = countSeverity(diagnostics, "WARNING"); //$NON-NLS-1$
		final long infos = countSeverity(diagnostics, "INFO"); //$NON-NLS-1$
		final String json = GSON.toJson(new CheckOutput(task, project.getName(), system, errors == 0, errors, warnings,
				infos, diagnostics));

		log(json, Project.MSG_INFO);
		writeReport(json);

		if (errors != 0) {
			throw new BuildException(task + " failed with " + errors + " error(s)"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private static long countSeverity(final List<DiagnosticOutput> diagnostics, final String severity) {
		return diagnostics.stream().filter(diagnostic -> severity.equals(diagnostic.severity())).count();
	}

	private static DiagnosticOutput toDiagnostic(final IMarker marker) {
		final int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		return new DiagnosticOutput(convertSeverity(marker.getAttribute(IMarker.SEVERITY, -1)),
				marker.getAttribute(IMarker.MESSAGE, null),
				marker.getResource().getFullPath().toPortableString(), line >= 0 ? Integer.valueOf(line) : null);
	}

	private static String convertSeverity(final int severity) {
		return switch (severity) {
		case IMarker.SEVERITY_INFO -> "INFO"; //$NON-NLS-1$
		case IMarker.SEVERITY_WARNING -> "WARNING"; //$NON-NLS-1$
		case IMarker.SEVERITY_ERROR -> "ERROR"; //$NON-NLS-1$
		default -> "UNKNOWN"; //$NON-NLS-1$
		};
	}

	private void writeReport(final String json) {
		if (reportFile == null) {
			return;
		}

		try {
			final var path = reportFile.toPath();
			final var parent = path.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}
			Files.writeString(path, json, StandardCharsets.UTF_8);
		} catch (final IOException e) {
			throw new BuildException("Cannot write check report to " + reportFile, e); //$NON-NLS-1$
		}
	}

	private record CheckOutput(int schemaVersion, String task, String project, String system, boolean success,
			long errors, long warnings, long infos, List<DiagnosticOutput> diagnostics) {

		// Increase this number when changing the schema.
		private static final int SCHEMA_VERSION = 1;

		private CheckOutput(final String task, final String project, final String system, final boolean success,
				final long errors, final long warnings, final long infos, final List<DiagnosticOutput> diagnostics) {
			this(SCHEMA_VERSION, task, project, system, success, errors, warnings, infos, diagnostics);
		}
	}

	private record DiagnosticOutput(String severity, String message, String resource, Integer line) {
		// Data transfer object for JSON serialization
	}
}
