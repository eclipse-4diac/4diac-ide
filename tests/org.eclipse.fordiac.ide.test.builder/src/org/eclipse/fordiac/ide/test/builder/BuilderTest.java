/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

@SuppressWarnings({ "nls", "static-method" })
class BuilderTest {

	private static final String DATA_FOLDER_NAME = "data";
	private static final String PROJECT_FILE = ".project";
	private static final String EXPECTED_MARKER_FILE_NAME = "markers.txt";

	private static List<FordiacProjectLoader> loaders;

	@BeforeAll
	static void setUpBeforeClass() throws CoreException, URISyntaxException, java.io.IOException {
		final Bundle bundle = FrameworkUtil.getBundle(BuilderTest.class);
		final List<java.nio.file.Path> projectPaths = discoverProjects(bundle);
		loaders = new ArrayList<>();
		for (final java.nio.file.Path projectPath : projectPaths) {
			loaders.add(new FordiacProjectLoader(bundle,
					new Path(DATA_FOLDER_NAME + File.separator + projectPath.getFileName())));
		}
		ResourcesPlugin.getWorkspace().build(IncrementalProjectBuilder.FULL_BUILD, null);
	}

	static List<java.nio.file.Path> discoverProjects(final Bundle bundle) throws URISyntaxException, IOException {
		final java.nio.file.Path dataPath = java.nio.file.Path
				.of(FileLocator.toFileURL(bundle.getEntry(DATA_FOLDER_NAME)).toURI());
		final List<java.nio.file.Path> projectPaths;
		try (Stream<java.nio.file.Path> paths = Files.list(dataPath)) {
			projectPaths = paths.filter(Files::isDirectory).filter(path -> Files.exists(path.resolve(PROJECT_FILE)))
					.sorted().toList();
		}
		return projectPaths;
	}

	static Stream<Named<IContainer>> containers() throws CoreException {
		final List<IContainer> containers = new ArrayList<>();
		for (final FordiacProjectLoader loader : loaders) {
			loader.getEclipseProject().accept((IResourceVisitor) resource -> {
				if (resource instanceof final IContainer container) {
					containers.add(container);
				}
				return true;
			});
		}
		return containers.stream().map(container -> Named.named(container.getFullPath().toString(), container));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("containers")
	void testBuild(final IContainer container) throws CoreException {
		final IFile expectedMarkerFile = container.getFile(new Path(EXPECTED_MARKER_FILE_NAME));
		final IMarker[] markers = container.findMarkers(FordiacErrorMarker.PROBLEM_MARKER, true, IResource.DEPTH_ONE);
		final String expectedMarkers = expectedMarkerFile.exists() ? expectedMarkerFile.readString().strip() : "";
		final String actualMarkers = Stream.of(markers)
				.filter(marker -> isDirectMarker(container, marker.getResource()))
				.map(marker -> toString(marker, container)).sorted().collect(Collectors.joining("\n"));
		assertEquals(expectedMarkers, actualMarkers, container.getFullPath().toString());
	}

	private static boolean isDirectMarker(final IContainer container, final IResource resource) {
		return resource.equals(container) || resource instanceof IFile;
	}

	static String toString(final IMarker marker, final IContainer container) {
		final StringBuilder result = new StringBuilder();
		result.append(marker.getResource() instanceof final IFile file ? file.getName() : ".");
		final int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		if (line >= 0) {
			result.append(":");
			result.append(line);
		}
		result.append(" ");
		result.append(getSeverityString(marker));
		result.append(" ");
		result.append(marker.getAttribute(IMarker.MESSAGE, "<no message>").strip());
		return result.toString();
	}

	static String getSeverityString(final IMarker marker) {
		return switch (marker.getAttribute(IMarker.SEVERITY, -1)) {
		case IMarker.SEVERITY_ERROR -> "ERROR";
		case IMarker.SEVERITY_WARNING -> "WARNING";
		case IMarker.SEVERITY_INFO -> "INFO";
		default -> "UNKNOWN";
		};
	}
}
