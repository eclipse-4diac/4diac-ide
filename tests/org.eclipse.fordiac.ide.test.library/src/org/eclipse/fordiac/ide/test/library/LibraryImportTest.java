/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.library.IArchiveDownloader;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

class LibraryImportTest {
	private static final String TEST01 = "test01"; //$NON-NLS-1$
	private static final String TEST02 = "test02"; //$NON-NLS-1$
	private static final String TEST03 = "test03"; //$NON-NLS-1$
	private static final String TEST04 = "test04"; //$NON-NLS-1$
	private static final String TEST05 = "test05"; //$NON-NLS-1$
	private static final String TEST06 = "test06"; //$NON-NLS-1$
	private static final String TEST07 = "test07"; //$NON-NLS-1$
	private static final String MATH = "math"; //$NON-NLS-1$
	private static final String V1_0_0 = "1.0.0"; //$NON-NLS-1$
	private static final String V1_1_0 = "1.1.0"; //$NON-NLS-1$
	private static final String V1_5_0 = "1.5.0"; //$NON-NLS-1$
	private static final String V2_0_0 = "2.0.0"; //$NON-NLS-1$
	private static final String LIB_LOC = "WORKSPACE_LOC/.lib/"; //$NON-NLS-1$
	private static final String PROJECT = "testproject"; //$NON-NLS-1$

	private IProject project;
	@SuppressWarnings("nls")
	public static final List<String> archives = List.of("data/test01-1.0.0.zip", "data/test01-1.1.0.zip",
			"data/test01-1.5.0.zip", "data/test01-2.0.0.zip", "data/test02-1.0.0.zip", "data/test02-1.1.0.zip",
			"data/test03-1.0.0.zip", "data/test03-1.1.0.zip", "data/test04-1.0.0.zip", "data/test04-1.1.0.zip",
			"data/test05-1.0.0.zip", "data/test06-1.0.0.zip", "data/test07-1.0.0.zip");

	@BeforeAll
	static void setupBeforeClass() throws Exception {
		final IProject proj = SystemManager.INSTANCE.createNew4diacProject(PROJECT,
				ResourcesPlugin.getWorkspace().getRoot().getLocation().append(PROJECT), Collections.emptyMap(),
				new NullProgressMonitor());
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);

		// extract test libraries

		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.library"); //$NON-NLS-1$
		for (final var archive : archives) {
			final var url = FileLocator.toFileURL(FileLocator.find(bundle, new Path(archive)));
			final var uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getFile(),
					url.getQuery(), url.getRef());
			LibraryManager.INSTANCE.extractLibrary(Paths.get(uri), null, false, false);
		}

		// deactivate other downloaders
		TypeLibraryManager.useExtensions(LibraryManager.DOWNLOADER_EXTENSION, IArchiveDownloader.class,
				downloader -> downloader.setActive(downloader instanceof MockDownloader));
	}

	@BeforeEach
	void setupBeforeEach() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
	}

	@AfterEach
	void cleanupAfterEach() throws Exception {
		final var manifest = ManifestHelper.getContainerManifest(project);
		if (manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().clear();
			ManifestHelper.saveManifest(manifest);
		}
		FordiacMarkerHelper.updateMarkers(project.getFile(LibraryManager.MANIFEST), FordiacErrorMarker.LIBRARY_MARKER,
				Collections.emptyList(), true);
		Job.getJobManager().join(FordiacMarkerHelper.FAMILY_FORDIAC_MARKER, null);
		project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME).accept(resource -> {
			if (resource instanceof final IFolder folder) {
				if (folder.getName().equals(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME)) {
					return true;
				}
				folder.delete(false, null);
			}
			return false;
		});
		project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).accept(resource -> {
			if (resource instanceof final IFolder folder) {
				if (folder.getName().equals(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME)) {
					return true;
				}
				folder.delete(false, null);
			}
			return false;
		});
	}

	@Test
	void testSimpleImport() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST01 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportUpdateNoOverwrite() {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST01 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals("[1.0.0-2.0.0)", manifest.getDependencies().getRequired().get(0).getVersion()); //$NON-NLS-1$
	}

	@Test
	void testImportUpdateOverwrite() {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST01 + "-2.0.0"), //$NON-NLS-1$
				true, false);

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V2_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(V2_0_0, manifest.getDependencies().getRequired().get(0).getVersion());
	}

	@Test
	void testCheckManifest() throws Exception {
		var manifest = ManifestHelper.getContainerManifest(project);
		ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(TEST01, "[1.0.0-2.0.0)")); //$NON-NLS-1$
		ManifestHelper.saveManifest(manifest);

		LibraryManager.INSTANCE.checkManifestFile(project);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST01, V1_5_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithoutResolve() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithResolve() {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.resolveDependencies(project, TypeLibraryManager.INSTANCE.getTypeLibrary(project));

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testImportWithRangeResolve() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST02 + "-" + V1_1_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST02, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlap() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_0_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_0_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_1_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlap2() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_0_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_5_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testRangeOverlapError() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST03 + "-" + V1_1_0), //$NON-NLS-1$
				true, false);
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST04 + "-" + V1_1_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST03, V1_1_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST04, V1_1_0, 1,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(2, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFolder(TEST01).exists());

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertEquals(1, markers.length);
		assertEquals(TEST01, markers[0].getAttribute(LibraryManager.MARKER_ATTRIBUTE, null));
	}

	@Test
	void testDependencyChain() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST06 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST06, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());

		LibraryAssert.assertLibraryLinked(project, TEST05, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertLibraryLinked(project, TEST02, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		LibraryAssert.assertLibraryLinked(project, TEST01, V1_0_0, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	@Test
	void testMissingStandardLibrary() throws Exception {
		LibraryManager.INSTANCE.importLibrary(project, null, java.net.URI.create(LIB_LOC + TEST07 + "-" + V1_0_0), //$NON-NLS-1$
				true, true);
		Job.getJobManager().join(LibraryManager.FAMILY_FORDIAC_LIBRARY, null);

		final var manifest = ManifestHelper.getContainerManifest(project);
		LibraryAssert.assertDependencyLinked(project, manifest, TEST07, V1_0_0, 0,
				TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		assertEquals(1, manifest.getDependencies().getRequired().size());

		assertFalse(project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME).getFolder(MATH).exists());

		final var manifestFile = project.getFile(LibraryManager.MANIFEST);
		final var markers = manifestFile.findMarkers(FordiacErrorMarker.LIBRARY_MARKER, false,
				IResource.DEPTH_INFINITE);

		assertEquals(1, markers.length);
		assertEquals(MATH, markers[0].getAttribute(LibraryManager.MARKER_ATTRIBUTE, null));
	}

}
