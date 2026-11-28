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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryMarkerFactory;
import org.eclipse.fordiac.ide.library.preferences.LibraryPreferenceConstants;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.ProjectCreator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Libraries that require themselves or each other are reported as errors and
 * are not linked, and the resolution ends also if the versions of libraries
 * select each other without end. The libraries of every test are created by the
 * test, with names of their own, as a table of what each version requires.
 */
class LibraryDependencyCycleTest {
	private static final String PROJECT = "cycleproject"; //$NON-NLS-1$
	private static final String V1 = "1.0.0"; //$NON-NLS-1$
	private static final String V2 = "2.0.0"; //$NON-NLS-1$
	private static final String ANY = "[1.0.0-3.0.0)"; //$NON-NLS-1$
	private static final String UNSTABLE_MESSAGE = "The versions of the libraries "; //$NON-NLS-1$
	private static final String UNSTABLE_END = " could not be resolved consistently because their requirements depend on each other."; //$NON-NLS-1$
	private static final long BUILD_TIMEOUT_SECONDS = 45;

	private static boolean previousAutoBuilding = true;
	private static boolean previousMockDownloaderActive;

	private IProject project;

	@BeforeAll
	@Timeout(120)
	static void setupBeforeClass() throws Exception {
		// the build of the test is started explicitly
		previousAutoBuilding = LibraryTestFixtures.setAutoBuilding(false);
		previousMockDownloaderActive = LibraryTestFixtures.isMockDownloaderActive();
		// dependencies are resolved from the libraries of the tests only
		LibraryTestFixtures.setMockDownloaderActive(false);
		final IProject proj = ProjectCreator
				.of(PROJECT, ResourcesPlugin.getWorkspace().getRoot().getLocation().append(PROJECT))
				.create(new NullProgressMonitor());
		proj.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	@AfterAll
	@Timeout(120)
	static void cleanupAfterClass() throws Exception {
		// every step runs, so that a failing step does not leave the state of other tests changed
		assertAll("restore the test state", LibraryTestFixtures::waitForBuild,
				() -> LibraryTestFixtures.deleteProject(PROJECT), LibraryTestFixtures::removeFixtureLibraries,
				() -> LibraryTestFixtures.setMockDownloaderActive(previousMockDownloaderActive),
				() -> LibraryTestFixtures.setAutoBuilding(previousAutoBuilding),
				() -> assertFalse(LibraryTestFixtures.hasFixtureLibraries()));
	}

	@BeforeEach
	void setupBeforeEach() {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT);
	}

	@AfterEach
	void cleanupAfterEach() throws Exception {
		LibraryTestFixtures.resetProject(project);
		// a project level marker of one test must not be seen by the next test
		project.deleteMarkers(FordiacErrorMarker.LIBRARY_MARKER, true, IResource.DEPTH_INFINITE);
	}

	@Test
	@Timeout(60)
	void testCompatibleCycleIsReported() throws Exception {
		LibraryTestFixtures.createLibrary("cyc1a", V1, "cyc1b " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc1b", V1, "cyc1a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc1ok", V1); //$NON-NLS-1$

		requireAndBuild("cyc1a", V1, "cyc1ok", V1); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(List.of("Cyclic library dependency: cyc1a 1.0.0 -> cyc1b 1.0.0 -> cyc1a 1.0.0."), errors()); //$NON-NLS-1$
		// the marker refers to the library like the other markers of dependencies
		assertEquals(List.of("cyc1a"), //$NON-NLS-1$
				Arrays.stream(project.getFile(LibraryManager.MANIFEST).findMarkers(FordiacErrorMarker.LIBRARY_MARKER, true,
						IResource.DEPTH_ZERO)).map(marker -> marker.getAttribute(LibraryMarkerFactory.MARKER_ATTRIBUTE, "")) //$NON-NLS-1$
						.toList());
		assertNotLinked("cyc1a", "cyc1b"); //$NON-NLS-1$ //$NON-NLS-2$
		// a library that is not part of the cycle is still linked
		assertLinked("cyc1ok", V1); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testLibraryRequiringItselfIsReported() throws Exception {
		// the version that the library requires is not the version of the project
		LibraryTestFixtures.createLibrary("cyc2", V1, "cyc2 " + V2); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc2", V1); //$NON-NLS-1$

		assertEquals(List.of("Library cyc2 1.0.0 requires itself (version 2.0.0)."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc2"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testLibraryRequiringItselfInItsOwnVersionIsReported() throws Exception {
		LibraryTestFixtures.createLibrary("cyc3", V1, "cyc3 " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc3", V1); //$NON-NLS-1$

		assertEquals(List.of("Library cyc3 1.0.0 requires itself (version 1.0.0)."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc3"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testCyclicLibraryIsNotLoadedByTheForceLoadPreference() throws Exception {
		LibraryTestFixtures.createLibrary("cyc4", V1, "cyc4 " + V2); //$NON-NLS-1$ //$NON-NLS-2$
		final var preferences = InstanceScope.INSTANCE.getNode(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID);
		final String previous = preferences.get(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, null);
		preferences.putBoolean(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, true);
		try {
			requireAndBuild("cyc4", V1); //$NON-NLS-1$
			// a second build ends with the same result
			build();
		} finally {
			if (previous == null) {
				preferences.remove(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES);
			} else {
				preferences.put(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, previous);
			}
		}

		assertEquals(List.of("Library cyc4 1.0.0 requires itself (version 2.0.0)."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc4"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testCycleOfThreeLibrariesIsReported() throws Exception {
		LibraryTestFixtures.createLibrary("cyc5p", V1, "cyc5q " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc5q", V1, "cyc5r " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc5r", V1, "cyc5p " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc5q", V1); //$NON-NLS-1$

		// the cycle starts with the smallest name, whichever library the project requires
		assertEquals(List.of("Cyclic library dependency: cyc5p 1.0.0 -> cyc5q 1.0.0 -> cyc5r 1.0.0 -> cyc5p 1.0.0."), //$NON-NLS-1$
				errors());
		assertNotLinked("cyc5p", "cyc5q", "cyc5r"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	@Timeout(60)
	void testEveryLibraryOfOverlappingCyclesIsNamed() throws Exception {
		// a requires b and c, both require d, d requires a: one closing requirement, two ways around
		LibraryTestFixtures.createLibrary("cyc6a", V1, "cyc6b " + V1, "cyc6c " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary("cyc6b", V1, "cyc6d " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc6c", V1, "cyc6d " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc6d", V1, "cyc6a " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc6a", V1); //$NON-NLS-1$

		assertEquals(List.of(
				"Cyclic library dependency: cyc6a 1.0.0 -> cyc6b 1.0.0 -> cyc6d 1.0.0 -> cyc6a 1.0.0. Also involved: cyc6c."), //$NON-NLS-1$
				errors());
		assertNotLinked("cyc6a", "cyc6b", "cyc6c", "cyc6d"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Test
	@Timeout(60)
	void testVersionsThatSelectEachOtherAreReportedAndOtherLibrariesAreLinked() throws Exception {
		// a 2.0.0 requires b 2.0.0, which requires a 1.0.0, which requires b 1.0.0, which requires a 2.0.0
		LibraryTestFixtures.createLibrary("cyc7a", V1, "cyc7b " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc7a", V2, "cyc7b " + V2, "cyc7x " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary("cyc7b", V1, "cyc7a " + V2); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc7b", V2, "cyc7a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		// x has no requirements, the project requires it, and a 2.0.0 narrows its range to 1.0.0 again and again
		LibraryTestFixtures.createLibrary("cyc7x", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc7x", V2); //$NON-NLS-1$

		requireAndBuild("cyc7a", ANY, "cyc7b", ANY, "cyc7x", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(List.of(
				"The versions of the libraries cyc7a, cyc7b could not be resolved consistently because their requirements depend on each other."), //$NON-NLS-1$
				errors());
		assertNotLinked("cyc7a", "cyc7b"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFolder("cyc7x").exists()); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testConflictInsideACycleIsReported() throws Exception {
		LibraryTestFixtures.createLibrary("cyc8a", V1, "cyc8b [1.0.0-9.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc8b", V1, "cyc8a [2.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc8a", V1); //$NON-NLS-1$

		// the requirements of the two libraries exclude each other: the range of a is empty
		assertEquals(List.of(UNSTABLE_MESSAGE + "cyc8a, cyc8b" + UNSTABLE_END), withoutConflicts(errors())); //$NON-NLS-1$
		assertEquals(1, conflictsOf("cyc8a").size()); //$NON-NLS-1$
		assertNotLinked("cyc8a", "cyc8b"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(60)
	void testLibraryThatTheProjectRequiresIsNotBlockedWithALibraryOfAConflict() throws Exception {
		// a 3.0.0 requires b, which requires a in versions that exclude 3.0.0: a conflict that never settles.
		// x has no requirements, the project requires it, and a narrows it again and again
		LibraryTestFixtures.createLibrary("cyc15a", "3.0.0", "cyc15b [1.0.0-2.0.0)", "cyc15x " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc15b", V1, "cyc15a [1.0.0-2.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc15x", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc15x", V2); //$NON-NLS-1$

		requireAndBuild("cyc15a", "[3.0.0-4.0.0)", "cyc15x", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(List.of(UNSTABLE_MESSAGE + "cyc15a, cyc15b" + UNSTABLE_END), withoutConflicts(errors())); //$NON-NLS-1$
		// the conflict is reported as well, with the requirements that exclude each other
		assertEquals(1, conflictsOf("cyc15a").size()); //$NON-NLS-1$
		assertNotLinked("cyc15a", "cyc15b"); //$NON-NLS-1$ //$NON-NLS-2$
		assertLinked("cyc15x", V2); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testBlockedLibrariesDoNotDependOnTheOrderOfTheRequirementsOfTheProject() throws Exception {
		createOscillation("cyc16"); //$NON-NLS-1$

		requireAndBuild("cyc16a", ANY, "cyc16x", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertOscillationOnly("cyc16"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testBlockedLibrariesDoNotDependOnTheOrderOfTheRequirementsOfTheProjectReversed() throws Exception {
		// x is required first: its name sorts before the name of a
		createOscillation("cyc17b", "cyc17c", "cyc17a"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		requireAndBuild("cyc17a", ANY, "cyc17b", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertOscillationOnly("cyc17b", "cyc17c", "cyc17a"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	@Timeout(60)
	void testLibraryOnACycleWithTheVersionsThatSelectEachOtherIsNamed() throws Exception {
		// a and b select each other as in cyc7; y requires a and is required by both versions of a: a cycle of
		// a and y that does not change while the versions of a and b do
		LibraryTestFixtures.createLibrary("cyc18a", V1, "cyc18b " + V1, "cyc18y " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc18a", V2, "cyc18b " + V2, "cyc18y " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc18b", V1, "cyc18a " + V2); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc18b", V2, "cyc18a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc18y", V1, "cyc18a " + ANY); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc18a", ANY, "cyc18b", ANY, "cyc18y", V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		assertEquals(List.of(UNSTABLE_MESSAGE + "cyc18a, cyc18b, cyc18y" + UNSTABLE_END), errors()); //$NON-NLS-1$
		assertNotLinked("cyc18a", "cyc18b", "cyc18y"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	@Timeout(60)
	void testCycleBesideAnUnstableLibrariesIsReportedSeparately() throws Exception {
		// a and r select each other, z and y require each other and z is required by the project in both versions
		LibraryTestFixtures.createLibrary("cyc19a", V1, "cyc19r " + V1, "cyc19z " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc19a", V2, "cyc19r " + V2); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc19r", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc19r", V2, "cyc19a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc19z", V1, "cyc19y " + ANY); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc19z", V2, "cyc19y " + ANY); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc19y", V1, "cyc19z " + ANY); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc19r", ANY, "cyc19a", ANY, "cyc19z", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		final List<String> errors = errors();
		assertEquals(2, errors.size(), errors::toString);
		assertTrue(errors.contains(UNSTABLE_MESSAGE + "cyc19a, cyc19r" + UNSTABLE_END), errors::toString); //$NON-NLS-1$
		assertTrue(errors.stream().anyMatch(message -> message.startsWith("Cyclic library dependency: cyc19y 1.0.0 -> cyc19z ")), //$NON-NLS-1$
				errors::toString);
		assertNotLinked("cyc19a", "cyc19r", "cyc19y", "cyc19z"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Test
	@Timeout(60)
	void testCycleThatTheRequirementsOfAnotherCycleCauseIsNotReported() throws Exception {
		// a and b require each other and narrow x to 1.0.0; x 1.0.0 requires c, which accepts both versions of x.
		// Without a and b, x is 2.0.0, which requires nothing: the cycle of x and c is a consequence of the first
		LibraryTestFixtures.createLibrary("cyc20a", V1, "cyc20b " + V1, "cyc20x " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc20b", V1, "cyc20a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc20x", V1, "cyc20c " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc20x", V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc20c", V1, "cyc20x " + ANY); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc20a", V1, "cyc20x", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(List.of("Cyclic library dependency: cyc20a 1.0.0 -> cyc20b 1.0.0 -> cyc20a 1.0.0."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc20a", "cyc20b", "cyc20c"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertLinked("cyc20x", V2); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testLibraryWithTheNameOfTheProjectCauseKeepsTheRequirementOfTheProject() throws Exception {
		// a 2.0.0 requires the library "Project", which requires z; b narrows a to 1.0.0, so that "Project" is
		// not needed any more. The project requires z itself: that requirement stays
		LibraryTestFixtures.createLibrary("cyc21a", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc21a", V2, "Project " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("Project", V1, "cyc21z " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary("cyc21b", V1, "cyc21a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc21z", V1); //$NON-NLS-1$

		requireAndBuild("cyc21a", ANY, "cyc21b", V1, "cyc21z", V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		assertEquals(List.of(), errors());
		assertLinked("cyc21z", V1); //$NON-NLS-1$
		assertLinked("cyc21a", V1); //$NON-NLS-1$
		assertNotLinked("Project"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testRangeThatADroppedDependencyIsStillNeededForIsResolvedAgain() throws Exception {
		// x 2.0.0 requires y 1.x, w narrows x to 1.0.0: y is still required by the project, in all versions,
		// so that it ends in 2.0.0 and its requirement z is not needed any more
		LibraryTestFixtures.createLibrary("cyc22x", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc22x", V2, "cyc22y [1.0.0-2.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc22w", V1, "cyc22x " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc22y", V1, "cyc22z " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc22y", V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc22z", V1); //$NON-NLS-1$

		requireAndBuild("cyc22x", ANY, "cyc22w", V1, "cyc22y", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		assertEquals(List.of(), errors());
		assertLinked("cyc22x", V1); //$NON-NLS-1$
		assertLinked("cyc22y", V2); //$NON-NLS-1$
		assertNotLinked("cyc22z"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testBlockedLibraryWithAnEmptyRangeIsNotForceLoaded() throws Exception {
		// as cyc8, but the project requires a in an exact version and loads such requirements by force
		LibraryTestFixtures.createLibrary("cyc23a", V1, "cyc23b [1.0.0-9.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc23b", V1, "cyc23a [2.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		final var preferences = InstanceScope.INSTANCE.getNode(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID);
		final String previous = preferences.get(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, null);
		preferences.putBoolean(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, true);
		try {
			requireAndBuild("cyc23a", V1); //$NON-NLS-1$
		} finally {
			if (previous == null) {
				preferences.remove(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES);
			} else {
				preferences.put(LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, previous);
			}
		}

		assertEquals(List.of(UNSTABLE_MESSAGE + "cyc23a, cyc23b" + UNSTABLE_END), withoutConflicts(errors())); //$NON-NLS-1$
		assertNotLinked("cyc23a", "cyc23b"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(60)
	void testTwoUnstableGroupsAreReportedSeparately() throws Exception {
		createOscillation("cyc24"); //$NON-NLS-1$
		createOscillation("cyc25"); //$NON-NLS-1$

		requireAndBuild("cyc24a", ANY, "cyc24x", ANY, "cyc25a", ANY, "cyc25x", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$

		final List<String> errors = errors();
		assertEquals(2, errors.size(), errors::toString);
		assertTrue(errors.contains(UNSTABLE_MESSAGE + "cyc24a, cyc24b" + UNSTABLE_END), errors::toString); //$NON-NLS-1$
		assertTrue(errors.contains(UNSTABLE_MESSAGE + "cyc25a, cyc25b" + UNSTABLE_END), errors::toString); //$NON-NLS-1$
		assertNotLinked("cyc24a", "cyc24b", "cyc25a", "cyc25b"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		assertLinked("cyc24x", V2); //$NON-NLS-1$
		assertLinked("cyc25x", V2); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testResolutionEndsIfEveryRoundRemovesUnneededLibraries() throws Exception {
		// found with random graphs: n2 3.0.0 requires n1 1.0.0, which narrows n2 to an empty range; the libraries
		// that n2 and n1 required lose their causes and the libraries that need them are visited again, round after round
		final String p = "cyc27n"; //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "0", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "0", V2, p + "2 [1.0.0-4.0.0)", p + "1 " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "1", V1, p + "0 [1.0.0-4.0.0)", p + "2 " + V2); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "1", V2, p + "5 [1.0.0-4.0.0)", p + "6 [1.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "1", "3.0.0", p + "4 [1.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "2", V1, p + "5 3.0.0", p + "6 [1.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "2", V2, p + "4 [2.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "2", "3.0.0", p + "1 " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "4", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "4", V2, p + "2 3.0.0"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "4", "3.0.0"); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "5", V1, p + "6 " + V1, p + "2 " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "5", V2, p + "5 " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "6", V1, p + "2 [1.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild(p + "4", ANY); //$NON-NLS-1$

		// the build ends (it is cancelled otherwise) and reports what it cannot resolve
		assertFalse(errors().isEmpty());
	}

	@Test
	@Timeout(60)
	void testResolutionEndsIfALibraryRequiresALibraryThatRequiresTheProjectLibraryAgain() throws Exception {
		// found with random graphs: the project requires n4 1.0.0, which requires n0, which requires n4 2.0.0
		final String p = "cyc28n"; //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "4", V1, p + "0 [2.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "5", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "5", V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "5", "3.0.0", p + "3 [1.0.0-2.0.0]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "0", V1, p + "1 [1.0.0-4.0.0)", p + "4 [1.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "0", V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "0", "3.0.0", p + "4 2.0.0", p + "2 [2.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary(p + "1", V1, p + "1 [1.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "1", V2, p + "1 " + V2, p + "3 [2.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary(p + "2", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "2", V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(p + "2", "3.0.0", p + "1 [1.0.0-3.0.0)", p + "4 [2.0.0-4.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary(p + "3", V1, p + "0 [1.0.0-2.0.0]"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(p + "3", V2, p + "2 [2.0.0-4.0.0)", p + "4 [1.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		requireAndBuild(p + "4", V1); //$NON-NLS-1$

		assertFalse(errors().isEmpty());
	}

	@Test
	@Timeout(60)
	void testCycleUpstreamOfAnUnstableGroupDoesNotBlockLibrariesOutsideOfIt() throws Exception {
		// as cyc18 without y, and u and w require each other: u requires a, so the cycle of u and w is upstream
		// of the versions that select each other, but never changes. x does not belong to either of them
		createOscillation("cyc29"); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc29u", V1, "cyc29w " + V1, "cyc29a " + ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc29w", V1, "cyc29u " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc29a", ANY, "cyc29x", ANY, "cyc29u", V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		final List<String> errors = errors();
		assertEquals(2, errors.size(), errors::toString);
		assertTrue(errors.contains(UNSTABLE_MESSAGE + "cyc29a, cyc29b" + UNSTABLE_END), errors::toString); //$NON-NLS-1$
		assertTrue(errors.contains("Cyclic library dependency: cyc29u 1.0.0 -> cyc29w 1.0.0 -> cyc29u 1.0.0."), //$NON-NLS-1$
				errors::toString);
		assertNotLinked("cyc29a", "cyc29b", "cyc29u", "cyc29w"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		assertLinked("cyc29x", V2); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testUnstableLibrariesAreFoundIfTheirRequirementsCloseTheCycleOnlyInDifferentVersions() throws Exception {
		// the project requires b, x and a in this order (sorted by name)
		createCrossOscillation("cyc31r", "cyc31p", "cyc31q"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		requireAndBuild("cyc31p", ANY, "cyc31q", ANY, "cyc31r", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertOscillationOnly("cyc31r", "cyc31p", "cyc31q"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	@Timeout(60)
	void testUnstableLibrariesAreFoundIfTheirRequirementsCloseTheCycleOnlyInDifferentVersionsReversed()
			throws Exception {
		// the project requires x, b and a in this order (sorted by name)
		createCrossOscillation("cyc32r", "cyc32q", "cyc32p"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		requireAndBuild("cyc32p", ANY, "cyc32q", ANY, "cyc32r", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertOscillationOnly("cyc32r", "cyc32q", "cyc32p"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Test
	@Timeout(60)
	void testCycleIsShownEvenIfTheFirstLibraryRequiresItselfToo() throws Exception {
		LibraryTestFixtures.createLibrary("cyc30a", V1, "cyc30a " + V1, "cyc30b " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		LibraryTestFixtures.createLibrary("cyc30b", V1, "cyc30a " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc30a", V1); //$NON-NLS-1$

		assertEquals(List.of("Cyclic library dependency: cyc30a 1.0.0 -> cyc30b 1.0.0 -> cyc30a 1.0.0."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc30a", "cyc30b"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(60)
	void testSelfRequirementWithARangeNamesTheRange() throws Exception {
		LibraryTestFixtures.createLibrary("cyc26", V1, "cyc26 " + ANY); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc26", V1); //$NON-NLS-1$

		assertEquals(List.of("Library cyc26 1.0.0 requires itself (version [1.0.0-3.0.0))."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc26"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testDependencyThatANewVersionDoesNotRequireIsNotLinked() throws Exception {
		// x 2.0.0 requires y, which requires z; w requires x 1.0.0, so that x ends in 1.0.0 without requirements
		LibraryTestFixtures.createLibrary("cyc9x", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc9x", V2, "cyc9y " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc9y", V1, "cyc9z " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc9z", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc9w", V1, "cyc9x " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc9x", ANY, "cyc9w", V1); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(List.of(), errors());
		assertLinked("cyc9x", V1); //$NON-NLS-1$
		assertLinked("cyc9w", V1); //$NON-NLS-1$
		assertNotLinked("cyc9y", "cyc9z"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(60)
	void testCycleThatIsNotNeededAnyMoreIsNotReported() throws Exception {
		// x 2.0.0 leads into a cycle of n1 and n2, which requires z 1.0.0; y narrows x to 1.0.0, so the cycle
		// is not needed and must not narrow z, which the project requires in all versions
		LibraryTestFixtures.createLibrary("cyc10x", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc10x", V2, "cyc10n1 " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc10y", V1, "cyc10x " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc10n1", V1, "cyc10n2 " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc10n2", V1, "cyc10n1 " + V1, "cyc10z " + V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		LibraryTestFixtures.createLibrary("cyc10z", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc10z", V2); //$NON-NLS-1$

		requireAndBuild("cyc10x", ANY, "cyc10y", V1, "cyc10z", ANY); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(List.of(), errors());
		assertLinked("cyc10x", V1); //$NON-NLS-1$
		assertLinked("cyc10z", V2); //$NON-NLS-1$
		assertNotLinked("cyc10n1", "cyc10n2"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(60)
	void testSelfRequirementOfAVersionThatIsNotResolvedIsNotReported() throws Exception {
		// s 2.0.0 requires itself, s 1.0.0 does not; t narrows s to 1.0.0 after s was resolved to 2.0.0
		LibraryTestFixtures.createLibrary("cyc11s", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc11s", V2, "cyc11s " + V2); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc11t", V1, "cyc11s " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc11s", ANY, "cyc11t", V1); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(List.of(), errors());
		assertLinked("cyc11s", V1); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testNoCycleIsReportedIfAnotherLibraryCannotBeResolved() throws Exception {
		// a 2.0.0 and b require each other, but t narrows a to 1.0.0 later: the missing library m stops the
		// resolution before t is visited, so the cycle is not final
		LibraryTestFixtures.createLibrary("cyc12a", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc12a", V2, "cyc12b " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc12b", V1, "cyc12a [1.0.0-3.0.0)"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc12t", V1, "cyc12a " + V1); //$NON-NLS-1$ //$NON-NLS-2$

		requireAndBuild("cyc12a", ANY, "cyc12b", V1, "cyc12m", V1, "cyc12t", V1); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		assertTrue(errors().stream().noneMatch(message -> message.startsWith("Cyclic library dependency")), //$NON-NLS-1$
				errors()::toString);
		assertFalse(errors().isEmpty(), "the missing library is reported"); //$NON-NLS-1$
	}

	@Test
	@Timeout(60)
	void testExistingLinkOfCyclicLibraryIsRemoved() throws Exception {
		final var uri = LibraryTestFixtures.createLibrary("cyc13a", V1, "cyc13b " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary("cyc13b", V1, "cyc13a " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(LibraryManager.INSTANCE.importLibrary(project, uri, false, false));
		assertLinked("cyc13a", V1); //$NON-NLS-1$

		requireAndBuild("cyc13a", V1); //$NON-NLS-1$

		assertEquals(List.of("Cyclic library dependency: cyc13a 1.0.0 -> cyc13b 1.0.0 -> cyc13a 1.0.0."), errors()); //$NON-NLS-1$
		assertNotLinked("cyc13a", "cyc13b"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	@Timeout(120)
	void testRangeOfASharedLibraryCanNarrowMoreThanAHundredTimes() throws Exception {
		// every library of the chain narrows the range of the shared library once more: no limit of visits
		final int length = 105;
		for (int i = 0; i < length; i++) {
			final String shared = "cyc14s [1.0.0-" + (150 - i) + ".0.0)"; //$NON-NLS-1$ //$NON-NLS-2$
			LibraryTestFixtures.createLibrary("cyc14l" + i, V1, i + 1 < length //$NON-NLS-1$
					? new String[] { "cyc14l" + (i + 1) + " " + V1, shared } //$NON-NLS-1$ //$NON-NLS-2$
					: new String[] { shared });
		}
		LibraryTestFixtures.createLibrary("cyc14s", V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary("cyc14s", V2); //$NON-NLS-1$

		requireAndBuild("cyc14l0", V1); //$NON-NLS-1$

		assertEquals(List.of(), errors());
		assertLinked("cyc14l0", V1); //$NON-NLS-1$
		assertLinked("cyc14l" + (length - 1), V1); //$NON-NLS-1$ //$NON-NLS-2$
		assertLinked("cyc14s", V2); //$NON-NLS-1$
	}

	/**
	 * Libraries {@code <prefix>a}, {@code <prefix>b} and {@code <prefix>x}: a 2.0.0 requires b and narrows x to
	 * 1.0.0, b requires a 1.0.0, so that the versions of a and b select each other. x has no requirements.
	 */
	private static void createOscillation(final String prefix) throws Exception {
		createOscillation(prefix + "a", prefix + "b", prefix + "x"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * The same with the names given: the manifest of the project keeps its requirements sorted by name, so the
	 * names decide in which order the libraries are required.
	 */
	private static void createOscillation(final String a, final String b, final String x) throws Exception {
		LibraryTestFixtures.createLibrary(a, V1);
		LibraryTestFixtures.createLibrary(a, V2, b + " " + V1, x + " " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(b, V1, a + " " + V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(x, V1);
		LibraryTestFixtures.createLibrary(x, V2);
	}

	/**
	 * Libraries {@code <prefix>a}, {@code <prefix>b} and {@code <prefix>x}: a 2.0.0 requires b 1.0.0 and narrows x
	 * to 1.0.0, b 1.0.0 requires a 1.0.0, a 1.0.0 requires b 2.0.0, which requires nothing. At no time do a and b
	 * require each other, but the requirements of their versions go around.
	 */
	private static void createCrossOscillation(final String a, final String b, final String x) throws Exception {
		LibraryTestFixtures.createLibrary(a, V1, b + " " + V2); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(a, V2, b + " " + V1, x + " " + V1); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.createLibrary(b, V1, a + " " + V1); //$NON-NLS-1$
		LibraryTestFixtures.createLibrary(b, V2);
		LibraryTestFixtures.createLibrary(x, V1);
		LibraryTestFixtures.createLibrary(x, V2);
	}

	/** a and b are reported and not linked, x does not belong to the instability and is linked in its newest version. */
	private void assertOscillationOnly(final String prefix) throws Exception {
		assertOscillationOnly(prefix + "a", prefix + "b", prefix + "x"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private void assertOscillationOnly(final String a, final String b, final String x) throws Exception {
		final String named = Stream.of(a, b).sorted().collect(Collectors.joining(", ")); //$NON-NLS-1$
		assertEquals(List.of(UNSTABLE_MESSAGE + named + UNSTABLE_END), errors());
		assertNotLinked(a, b);
		assertLinked(x, V2);
	}

	private static List<String> withoutConflicts(final List<String> errors) {
		return errors.stream().filter(message -> !message.contains("version range is empty")).toList(); //$NON-NLS-1$
	}

	private List<String> conflictsOf(final String symbolicName) throws Exception {
		return errors().stream().filter(message -> message.startsWith("Library " + symbolicName + ": version range is empty")) //$NON-NLS-1$ //$NON-NLS-2$
				.toList();
	}

	private List<String> errors() throws Exception {
		return Arrays
				.stream(project.getFile(LibraryManager.MANIFEST).findMarkers(FordiacErrorMarker.LIBRARY_MARKER, true,
						IResource.DEPTH_ZERO))
				.filter(marker -> marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO) == IMarker.SEVERITY_ERROR)
				.map(marker -> marker.getAttribute(IMarker.MESSAGE, "")).toList(); //$NON-NLS-1$
	}

	private void assertLinked(final String symbolicName, final String version) {
		LibraryAssert.assertLibraryLinked(project, symbolicName, version, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
	}

	private void assertNotLinked(final String... symbolicNames) {
		for (final String symbolicName : symbolicNames) {
			assertFalse(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME).getFolder(symbolicName).exists(),
					symbolicName + " is linked"); //$NON-NLS-1$
		}
	}

	/** Adds the libraries as dependencies of the project, each as symbolic name and version, and builds. */
	private void requireAndBuild(final String... requirements) throws Exception {
		final var manifest = ManifestHelper.getContainerManifest(project);
		for (int i = 0; i < requirements.length; i += 2) {
			ManifestHelper.addDependency(manifest, ManifestHelper.createRequired(requirements[i], requirements[i + 1]));
		}
		ManifestHelper.saveManifest(manifest);
		build();
	}

	private void build() throws Exception {
		// a build that does not end is cancelled, so that the test fails instead of hanging in the build
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final ScheduledExecutorService watchdog = Executors.newSingleThreadScheduledExecutor();
		try {
			watchdog.schedule(() -> monitor.setCanceled(true), BUILD_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		} finally {
			watchdog.shutdownNow();
		}
		assertFalse(monitor.isCanceled(), "the build did not end within " + BUILD_TIMEOUT_SECONDS + " s"); //$NON-NLS-1$ //$NON-NLS-2$
		LibraryTestFixtures.waitForBuild();
	}
}
