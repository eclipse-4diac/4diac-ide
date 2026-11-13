/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
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
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.FB_NAME;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.RESOURCE_NAME;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.addToResource;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createEdgeFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createFB;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createFunctionFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createInternalFB;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createSimpleFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.createToggleFBType;
import static org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotTestModel.withTypeEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.AbstractDelegatingDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FBSnapshotReaderTest {

	private FBSnapshotTarget target;
	private WatchInteractor interactor;

	@BeforeEach
	void setUp() throws CoreException {
		final FB fb = createFB(FB_NAME, createSimpleFBType());
		addToResource(fb);
		target = FBSnapshotTarget.of(fb);
		interactor = new WatchInteractor();
		interactor.values.put("INC", "3"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("CNF", "3"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("STEP", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("COUNT", "6"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("total", "6"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void readsVariablesAndRemovesTheWatches() throws DeploymentException {
		final FBSnapshot snapshot = FBSnapshotReader.read(target, interactor, new NullProgressMonitor());

		assertEquals(FB_NAME, snapshot.variable().getName());
		assertEquals("2", value(snapshot, "STEP")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("6", value(snapshot, "COUNT")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("6", value(snapshot, "total")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(snapshot.missing().isEmpty());
		// once for the existing watches, once for the values
		assertEquals(2, interactor.reads);
		assertEquals(List.of("Counter.INC", "Counter.CNF", "Counter.STEP", "Counter.COUNT", "Counter.total"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				interactor.added);
		assertEquals(interactor.added, interactor.removed);
	}

	@Test
	void watchesOfTheUserAreKept() throws DeploymentException {
		// the user monitors COUNT, the runtime does not count watches
		interactor.watched.add("Counter.COUNT"); //$NON-NLS-1$

		final FBSnapshot snapshot = FBSnapshotReader.read(target, interactor, new NullProgressMonitor());

		assertEquals("6", value(snapshot, "COUNT")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(List.of("Counter.INC", "Counter.CNF", "Counter.STEP", "Counter.total"), interactor.added); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		assertEquals(interactor.added, interactor.removed);
		assertEquals(Set.of("Counter.COUNT"), interactor.watched); //$NON-NLS-1$
	}

	@Test
	void internalConstantsAreNotRead() throws DeploymentException {
		final FBSnapshot snapshot = FBSnapshotReader.read(target, interactor, new NullProgressMonitor());

		assertFalse(interactor.added.contains("Counter.MAX_STEP")); //$NON-NLS-1$
		assertFalse(snapshot.missing().contains("MAX_STEP")); //$NON-NLS-1$
	}

	@Test
	void missingValuesKeepTheirInitialValue() throws DeploymentException {
		interactor.values.remove("total"); //$NON-NLS-1$

		final FBSnapshot snapshot = FBSnapshotReader.read(target, interactor, new NullProgressMonitor());

		assertEquals(List.of("total"), snapshot.missing()); //$NON-NLS-1$
		assertEquals("0", value(snapshot, "total")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void functionBlockMissingInTheRuntimeIsRejected() {
		// the runtime accepts the watches, but reports nothing for them
		interactor.values.clear();

		final DeploymentException exception = assertThrows(DeploymentException.class,
				() -> FBSnapshotReader.read(target, interactor, new NullProgressMonitor()));

		assertEquals(MessageFormat.format(Messages.FBSnapshotReader_NotInRuntime, FB_NAME, RESOURCE_NAME),
				exception.getMessage());
		assertEquals(interactor.added, interactor.removed);
	}

	@Test
	void invalidValuesAreReported() throws DeploymentException {
		interactor.values.put("COUNT", "not a number"); //$NON-NLS-1$ //$NON-NLS-2$

		final FBSnapshot snapshot = FBSnapshotReader.read(target, interactor, new NullProgressMonitor());

		assertEquals(List.of("COUNT"), snapshot.missing()); //$NON-NLS-1$
		assertEquals("0", value(snapshot, "COUNT")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void readsInternalFBsAndTheirStates() throws CoreException, DeploymentException {
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createInternalFB("trig", createEdgeFBType())); //$NON-NLS-1$
		type.getInternalFbs().add(createInternalFB("toggle", createToggleFBType())); //$NON-NLS-1$
		type.getInternalFbs().add(createInternalFB("delay", //$NON-NLS-1$
				withTypeEntry(LibraryElementFactory.eINSTANCE.createServiceInterfaceFBType(), "E_DELAY"))); //$NON-NLS-1$
		final FB fb = createFB(FB_NAME, type);
		addToResource(fb);
		interactor.values.put("trig.CLK", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("trig.Q", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("trig.MEM", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("toggle.Q", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("toggle.count", "3"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("toggle.!ECC", "1"); //$NON-NLS-1$ //$NON-NLS-2$

		final FBSnapshot snapshot = FBSnapshotReader.read(FBSnapshotTarget.of(fb), interactor,
				new NullProgressMonitor());

		assertEquals("TRUE", value(snapshot, "trig.CLK")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("FALSE", value(snapshot, "trig.Q")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("TRUE", value(snapshot, "trig.MEM")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("TRUE", value(snapshot, "toggle.Q")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("3", value(snapshot, "toggle.count")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("HIGH", value(snapshot, "toggle.__STATE")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(snapshot.missing().isEmpty());
		// the internal SIFB needs a runtime, its state is not read
		assertTrue(interactor.added.containsAll(List.of("Counter.trig.MEM", "Counter.toggle.!ECC"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(interactor.added.stream().anyMatch(name -> name.startsWith("Counter.delay"))); //$NON-NLS-1$
		assertEquals(interactor.added, interactor.removed);
	}

	@Test
	void invalidStateIsReported() throws CoreException, DeploymentException {
		final SimpleFBType type = createSimpleFBType();
		type.getInternalFbs().add(createInternalFB("toggle", createToggleFBType())); //$NON-NLS-1$
		final FB fb = createFB(FB_NAME, type);
		addToResource(fb);
		interactor.values.put("toggle.Q", "TRUE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("toggle.count", "3"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("toggle.!ECC", "7"); //$NON-NLS-1$ //$NON-NLS-2$

		final FBSnapshot snapshot = FBSnapshotReader.read(FBSnapshotTarget.of(fb), interactor,
				new NullProgressMonitor());

		assertEquals(List.of("toggle.__STATE"), snapshot.missing()); //$NON-NLS-1$
		// the start state stays
		assertEquals("START", value(snapshot, "toggle.__STATE")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	void readsTheStateOfABasicFB() throws CoreException, DeploymentException {
		final FB fb = createFB(FB_NAME, createToggleFBType());
		addToResource(fb);
		interactor.values.clear();
		interactor.values.put("TOGGLE", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("Q", "FALSE"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("count", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("!ECC", "2"); //$NON-NLS-1$ //$NON-NLS-2$

		final FBSnapshot snapshot = FBSnapshotReader.read(FBSnapshotTarget.of(fb), interactor,
				new NullProgressMonitor());

		assertEquals("LOW", value(snapshot, ECStateVariable.NAME)); //$NON-NLS-1$
		assertEquals("2", value(snapshot, "count")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(snapshot.missing().isEmpty());
		assertTrue(interactor.added.contains("Counter.!ECC")); //$NON-NLS-1$
	}

	@Test
	void readsAFunction() throws DeploymentException {
		final FunctionFBType type = createFunctionFBType();
		final FB fb = createFB(FB_NAME, type);
		final Resource resource = addToResource(fb);
		interactor.values.clear();
		interactor.values.put("REQ", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("CNF", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("IN", "4"); //$NON-NLS-1$ //$NON-NLS-2$
		interactor.values.put("OUT", "8"); //$NON-NLS-1$ //$NON-NLS-2$

		// without the structured text evaluator the function has no body that the
		// validator accepts, so the target is created directly
		final FBSnapshot snapshot = FBSnapshotReader.read(new FBSnapshotTarget(fb, resource, type), interactor,
				new NullProgressMonitor());

		assertEquals("4", value(snapshot, "IN")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("8", value(snapshot, "OUT")); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(snapshot.missing().isEmpty());
	}

	@Test
	void addedWatchesAreRemovedWhenAWatchFails() {
		interactor.rejectedWatch = "Counter.COUNT"; //$NON-NLS-1$

		assertThrows(DeploymentException.class,
				() -> FBSnapshotReader.read(target, interactor, new NullProgressMonitor()));
		assertEquals(List.of("Counter.INC", "Counter.CNF", "Counter.STEP"), interactor.added); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(interactor.added, interactor.removed);
	}

	/** @return the value of the variable with the given path in the snapshot */
	private static String value(final FBSnapshot snapshot, final String path) {
		Variable<?> variable = snapshot.variable();
		for (final String name : path.split("\\.")) { //$NON-NLS-1$
			variable = ((FBVariable) variable).getMembers().get(name);
		}
		return variable.toString();
	}

	/**
	 * Answers watch requests with the configured values of the function block.
	 * Like the runtime, it only reports the values of watched ports.
	 */
	private static final class WatchInteractor extends AbstractDelegatingDeviceManagementInteractor {

		private final Map<String, String> values = new LinkedHashMap<>();
		private final Set<String> watched = new HashSet<>();
		private final List<String> added = new ArrayList<>();
		private final List<String> removed = new ArrayList<>();
		private String rejectedWatch;
		private int reads;

		WatchInteractor() {
			super(null);
		}

		@Override
		public boolean addWatch(final Resource resource, final String name) {
			if (name.equals(rejectedWatch)) {
				return false;
			}
			added.add(name);
			watched.add(name);
			return true;
		}

		@Override
		public boolean removeWatch(final Resource resource, final String name) {
			removed.add(name);
			watched.remove(name);
			return true;
		}

		@Override
		public Response readWatches() {
			reads++;
			final Map<String, String> watchedValues = new LinkedHashMap<>(values);
			watchedValues.keySet().removeIf(name -> !watched.contains(FB_NAME + "." + name)); //$NON-NLS-1$
			return createResponse(watchedValues);
		}

		/**
		 * Like the runtime, report the values of internal function blocks under the
		 * path of the internal function block, e.g. port MEM of FB Counter.trig.
		 */
		private static Response createResponse(final Map<String, String> portValues) {
			final DevResponseFactory factory = DevResponseFactory.eINSTANCE;
			final Map<String, org.eclipse.fordiac.ide.deployment.devResponse.FB> fbs = new LinkedHashMap<>();
			portValues.forEach((name, value) -> {
				final int separator = name.lastIndexOf('.');
				final String fbName = separator < 0 ? FB_NAME : FB_NAME + "." + name.substring(0, separator); //$NON-NLS-1$
				final org.eclipse.fordiac.ide.deployment.devResponse.FB fb = fbs.computeIfAbsent(fbName, _ -> {
					final org.eclipse.fordiac.ide.deployment.devResponse.FB created = factory.createFB();
					created.setName(fbName);
					return created;
				});
				final Data data = factory.createData();
				data.setValue(value);
				final Port port = factory.createPort();
				port.setName(name.substring(separator + 1));
				port.getDataValues().add(data);
				fb.getPorts().add(port);
			});
			final org.eclipse.fordiac.ide.deployment.devResponse.Resource resource = factory.createResource();
			resource.setName(RESOURCE_NAME);
			resource.getFbs().addAll(fbs.values());
			final Watches watches = factory.createWatches();
			watches.getResources().add(resource);
			final Response response = factory.createResponse();
			response.setWatches(watches);
			return response;
		}
	}
}
