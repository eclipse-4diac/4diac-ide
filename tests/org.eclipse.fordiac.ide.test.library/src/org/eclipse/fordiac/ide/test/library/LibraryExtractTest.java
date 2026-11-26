/*******************************************************************************
 * Copyright (c) 2026 MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrii Kozhushko - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryPermission;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Extracting a library archive must only touch the folder of that library, must
 * leave the extracted libraries as they were if it fails, and must only accept
 * an archive that is a library. Archives are generated here, so the tests do
 * not depend on binary fixtures.
 */
class LibraryExtractTest {
	private static final String UNRELATED = "test01-1.0.0"; //$NON-NLS-1$
	private static final String LIBRARY = "extracttest-1.0.0"; //$NON-NLS-1$
	private static final String ANOTHER = "another-1.0.0"; //$NON-NLS-1$
	private static final String MANIFEST = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" //$NON-NLS-1$
			+ "<Manifest Scope=\"Library\"><Product Name=\"Extract Test\" SymbolicName=\"extracttest\">" //$NON-NLS-1$
			+ "<VersionInfo Version=\"1.0.0\" Author=\"test\" Date=\"2026-11-23\"/></Product></Manifest>\n"; //$NON-NLS-1$
	private static final List<String> WELL_FORMED = List.of(LIBRARY + "/", LIBRARY + "/MANIFEST.MF", //$NON-NLS-1$ //$NON-NLS-2$
			LIBRARY + "/typelib/"); //$NON-NLS-1$

	private Path tempDir;

	@BeforeEach
	void setup() throws Exception {
		tempDir = Files.createTempDirectory("extracttest"); //$NON-NLS-1$
		LibraryTestFixtures.extract(UNRELATED);
	}

	@AfterEach
	void cleanup() throws Exception {
		deleteTree(getLibraryFolder().resolve(LIBRARY));
		deleteTree(getLibraryFolder().resolve(ANOTHER));
		deleteTree(getLibraryFolder().resolve(".staging")); //$NON-NLS-1$
		deleteTree(tempDir);
		LibraryTestFixtures.removeFixtureLibraries();
	}

	@Test
	void extractsOnlyTheLibraryFolder() throws Exception {
		final Path archive = createArchive("plain.zip", WELL_FORMED); //$NON-NLS-1$

		final URI uri = LibraryManager.INSTANCE.extractLibrary(archive, null, false, false);

		assertTrue(uri.toString().endsWith(LIBRARY));
		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(LIBRARY).resolve("MANIFEST.MF"))); //$NON-NLS-1$
		assertTrue(Files.isDirectory(getLibraryFolder().resolve(UNRELATED)), "an unrelated library must stay"); //$NON-NLS-1$
	}

	@Test
	void ignoresAnEntryThatNamesTheLibraryRoot() throws Exception {
		// first entry "./": resolves to the library root and used to delete the whole library store
		final List<String> entries = new ArrayList<>(List.of("./")); //$NON-NLS-1$
		entries.addAll(WELL_FORMED);
		final Path archive = createArchive("root.zip", entries); //$NON-NLS-1$

		final URI uri = LibraryManager.INSTANCE.extractLibrary(archive, null, false, false);

		assertTrue(Files.isDirectory(getLibraryFolder().resolve(UNRELATED)),
				"an unrelated library must not be deleted by an archive with a './' entry"); //$NON-NLS-1$
		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(LIBRARY).resolve("MANIFEST.MF"))); //$NON-NLS-1$
		assertTrue(uri.toString().endsWith(LIBRARY), uri.toString());
	}

	@Test
	void usesTheTopLevelFolderWhenTheFirstEntryIsAFile() throws Exception {
		final Path archive = createArchive("file-first.zip", List.of(LIBRARY + "/MANIFEST.MF", LIBRARY + "/typelib/")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		final URI uri = LibraryManager.INSTANCE.extractLibrary(archive, null, false, false);

		assertTrue(uri.toString().endsWith(LIBRARY), uri.toString());
		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(LIBRARY).resolve("MANIFEST.MF"))); //$NON-NLS-1$
		assertTrue(Files.isDirectory(getLibraryFolder().resolve(UNRELATED)));
	}

	@Test
	void rejectsEntriesOutsideTheLibraryStore() throws Exception {
		final Path archive = createArchive("escape.zip", List.of("../outside.txt")); //$NON-NLS-1$ //$NON-NLS-2$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		assertTrue(Files.isDirectory(getLibraryFolder().resolve(UNRELATED)));
		assertFalse(Files.exists(getLibraryFolder().getParent().resolve("outside.txt"))); //$NON-NLS-1$
	}

	@Test
	void rejectsAnAbsoluteEntryName() throws Exception {
		// an absolute name that points into the folder of another library
		final String absolute = getLibraryFolder().resolve(UNRELATED).resolve("new.txt").toString().replace('\\', '/'); //$NON-NLS-1$
		final Path archive = createArchive("absolute.zip", List.of(absolute, LIBRARY + "/MANIFEST.MF")); //$NON-NLS-1$ //$NON-NLS-2$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(UNRELATED).resolve("MANIFEST.MF")), //$NON-NLS-1$
				"the unrelated library must stay as it was"); //$NON-NLS-1$
		assertFalse(Files.exists(getLibraryFolder().resolve(UNRELATED).resolve("new.txt"))); //$NON-NLS-1$
	}

	@Test
	void ignoresFilesInTheArchiveRootAndMacMetadata() throws Exception {
		final List<String> entries = new ArrayList<>(List.of("./", "LICENSE", "__MACOSX/", "__MACOSX/._" + LIBRARY)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		entries.addAll(WELL_FORMED);
		final Path archive = createArchive("loose.zip", entries); //$NON-NLS-1$

		final URI uri = LibraryManager.INSTANCE.extractLibrary(archive, null, false, false);

		assertTrue(uri.toString().endsWith(LIBRARY), uri.toString());
		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(LIBRARY).resolve("MANIFEST.MF"))); //$NON-NLS-1$
		assertFalse(Files.exists(getLibraryFolder().resolve("LICENSE"))); //$NON-NLS-1$
		assertFalse(Files.exists(getLibraryFolder().resolve("__MACOSX"))); //$NON-NLS-1$
	}

	@Test
	void rejectsAnEntryThatLeadsIntoAnotherLibraryWithParentSegments() throws Exception {
		// resolves to the folder of the unrelated library and must neither delete nor change it
		final Path archive = createArchive("detour.zip", //$NON-NLS-1$
				List.of(LIBRARY + "/../" + UNRELATED + "/new.txt", LIBRARY + "/MANIFEST.MF")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(UNRELATED).resolve("MANIFEST.MF")), //$NON-NLS-1$
				"the unrelated library must stay as it was"); //$NON-NLS-1$
		assertFalse(Files.exists(getLibraryFolder().resolve(UNRELATED).resolve("new.txt"))); //$NON-NLS-1$
	}

	@Test
	void treatsAnEntryEndingWithABackslashAsDirectory() throws Exception {
		// archives written on Windows may separate the names with a backslash
		final String top = LIBRARY + "\\"; //$NON-NLS-1$
		final Path archive = createArchive("backslash.zip", //$NON-NLS-1$
				List.of(top, top + "MANIFEST.MF", top + "typelib" + "\\")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		final URI uri = LibraryManager.INSTANCE.extractLibrary(archive, null, false, false);

		assertTrue(uri.toString().endsWith(LIBRARY), uri.toString());
		assertTrue(Files.isRegularFile(getLibraryFolder().resolve(LIBRARY).resolve("MANIFEST.MF"))); //$NON-NLS-1$
		assertTrue(Files.isDirectory(getLibraryFolder().resolve(LIBRARY).resolve("typelib"))); //$NON-NLS-1$
	}

	@Test
	void rejectsAnEntryOutsideTheFirstLibraryFolder() throws Exception {
		// a second top-level folder could be the folder of another library
		final List<String> entries = new ArrayList<>(WELL_FORMED);
		entries.add(ANOTHER + "/new.txt"); //$NON-NLS-1$
		final Path archive = createArchive("second-root.zip", entries); //$NON-NLS-1$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		assertFalse(Files.exists(getLibraryFolder().resolve(ANOTHER)));
	}

	@Test
	void keepsTheInstalledLibraryWhenALaterEntryFails() throws Exception {
		final List<String> installed = new ArrayList<>(WELL_FORMED);
		installed.add(LIBRARY + "/old.txt"); //$NON-NLS-1$
		LibraryManager.INSTANCE.extractLibrary(createArchive("installed.zip", installed), null, false, false); //$NON-NLS-1$
		// the new version fails at its last entry, after its other files were written
		final List<String> broken = new ArrayList<>(WELL_FORMED);
		broken.add(LIBRARY + "/new.txt"); //$NON-NLS-1$
		broken.add(ANOTHER + "/new.txt"); //$NON-NLS-1$
		final Path archive = createArchive("broken.zip", broken); //$NON-NLS-1$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		final Path library = getLibraryFolder().resolve(LIBRARY);
		assertTrue(Files.isRegularFile(library.resolve("old.txt")), "the installed library must stay as it was"); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(Files.exists(library.resolve("new.txt"))); //$NON-NLS-1$
		assertNoStagingLeft();
	}

	@Test
	void keepsTheInstalledLibraryWhenTheContentOfAnEntryIsCorrupt() throws Exception {
		final List<String> installed = new ArrayList<>(WELL_FORMED);
		installed.add(LIBRARY + "/old.txt"); //$NON-NLS-1$
		LibraryManager.INSTANCE.extractLibrary(createArchive("installed.zip", installed), null, false, false); //$NON-NLS-1$
		final byte[] before = Files.readAllBytes(getLibraryFolder().resolve(LIBRARY).resolve("old.txt")); //$NON-NLS-1$
		// the archive ends in the middle of the data of its last entry
		final Path complete = tempDir.resolve("complete.zip"); //$NON-NLS-1$
		try (OutputStream out = Files.newOutputStream(complete); ZipOutputStream zip = new ZipOutputStream(out)) {
			for (final String entry : WELL_FORMED) {
				zip.putNextEntry(new ZipEntry(entry));
				if (!entry.endsWith("/")) { //$NON-NLS-1$
					zip.write(MANIFEST.getBytes(StandardCharsets.UTF_8));
				}
				zip.closeEntry();
			}
			zip.putNextEntry(new ZipEntry(LIBRARY + "/new.txt")); //$NON-NLS-1$
			final byte[] data = new byte[256 * 1024];
			new Random(1).nextBytes(data);
			zip.write(data);
			zip.closeEntry();
		}
		final byte[] bytes = Files.readAllBytes(complete);
		final Path archive = tempDir.resolve("truncated.zip"); //$NON-NLS-1$
		Files.write(archive, Arrays.copyOf(bytes, bytes.length / 2));

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		final Path library = getLibraryFolder().resolve(LIBRARY);
		assertTrue(Arrays.equals(before, Files.readAllBytes(library.resolve("old.txt"))), "the installed library must stay as it was"); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(Files.exists(library.resolve("new.txt"))); //$NON-NLS-1$
		assertNoStagingLeft();
	}

	@Test
	void replacesTheInstalledLibrary() throws Exception {
		final List<String> installed = new ArrayList<>(WELL_FORMED);
		installed.add(LIBRARY + "/old.txt"); //$NON-NLS-1$
		LibraryManager.INSTANCE.extractLibrary(createArchive("installed.zip", installed), null, false, false); //$NON-NLS-1$
		final List<String> update = new ArrayList<>(WELL_FORMED);
		update.add(LIBRARY + "/new.txt"); //$NON-NLS-1$

		LibraryManager.INSTANCE.extractLibrary(createArchive("update.zip", update), null, false, false); //$NON-NLS-1$

		final Path library = getLibraryFolder().resolve(LIBRARY);
		assertTrue(Files.isRegularFile(library.resolve("new.txt"))); //$NON-NLS-1$
		assertFalse(Files.exists(library.resolve("old.txt")), "the previous version must be replaced, not merged"); //$NON-NLS-1$ //$NON-NLS-2$
		assertNoStagingLeft();
	}

	@Test
	void rejectsAnArchiveWithoutLibraryFolder() throws Exception {
		final Path archive = createArchive("flat.zip", List.of("./", "LICENSE")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertThrows(IOException.class, () -> LibraryManager.INSTANCE.extractLibrary(archive, null, false, false));

		assertTrue(Files.isDirectory(getLibraryFolder().resolve(UNRELATED)));
	}

	private static void assertNoStagingLeft() throws IOException {
		final Path staging = getLibraryFolder().resolve(".staging"); //$NON-NLS-1$
		if (Files.isDirectory(staging)) {
			try (var content = Files.list(staging)) {
				assertTrue(content.findAny().isEmpty(), "the staging folder must be empty"); //$NON-NLS-1$
			}
		}
	}

	private static Path getLibraryFolder() {
		return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath()
				.resolve(LibraryManager.EXTRACTED_LIB_DIRECTORY);
	}

	private Path createArchive(final String name, final List<String> entries) throws IOException {
		final Path archive = tempDir.resolve(name);
		try (OutputStream out = Files.newOutputStream(archive); ZipOutputStream zip = new ZipOutputStream(out)) {
			for (final String entry : entries) {
				zip.putNextEntry(new ZipEntry(entry));
				if (!entry.endsWith("/") && !entry.endsWith("\\")) { //$NON-NLS-1$ //$NON-NLS-2$
					zip.write(MANIFEST.getBytes(StandardCharsets.UTF_8));
				}
				zip.closeEntry();
			}
		}
		return archive;
	}

	private static void deleteTree(final Path root) throws IOException {
		if (!Files.exists(root)) {
			return;
		}
		try (var walk = Files.walk(root)) {
			for (final Path p : walk.sorted(java.util.Comparator.reverseOrder()).toList()) {
				LibraryPermission.setPathEditable(p);
				Files.delete(p);
			}
		}
	}
}
