/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Ernst Blecha - initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.create;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.CheckableStructTree;
import org.eclipse.fordiac.ide.model.CheckableStructTreeNode;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteDemuxPortCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase.State;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.params.provider.Arguments;

public class AddDeleteDemuxPortCommandTest extends CommandTestBase<State> {
	private static final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(null);

	private static final String OUTER_STRUCT_TYPE = "outerStruct"; //$NON-NLS-1$
	private static final String INNER_STRUCT_TYPE = "innerStruct"; //$NON-NLS-1$
	private static final String VARIABLE1 = "VAR1"; //$NON-NLS-1$
	private static final String VARIABLE2 = "VAR2"; //$NON-NLS-1$
	private static final String VARIABLE3 = "VAR3"; //$NON-NLS-1$
	private static final String VARIABLE4 = "VAR4"; //$NON-NLS-1$
	private static final String VARIABLE5 = "VAR5"; //$NON-NLS-1$
	private static final String VARIABLE6 = "VAR6"; //$NON-NLS-1$
	private static final String INNER_STRUCT_OBJECT1 = "innerstruct1"; //$NON-NLS-1$
	private static final String INNER_STRUCT_OBJECT2 = "innerstruct2"; //$NON-NLS-1$
	private static final String SEP = "."; //$NON-NLS-1$

	protected static class State extends CommandTestBase.StateBase {

		private Demultiplexer demux;
		private final StructuredType struct;
		private CheckableStructTree tree;

		public Demultiplexer getDemultiplexer() {
			return demux;
		}

		public StructuredType getStruct() {
			return struct;
		}

		public State() {
			struct = createSampleStruct();
			demux = createDemultiplexer();
			tree = new CheckableStructTree(demux, struct);
		}

		public void setDemultiplexer(final Demultiplexer demux) {
			this.demux = demux;
		}

		private Demultiplexer createDemultiplexer() {
			final Demultiplexer d = LibraryElementFactory.eINSTANCE.createDemultiplexer();
			d.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
			final Event inputEvent = LibraryElementFactory.eINSTANCE.createEvent();
			final Event outputEvent = LibraryElementFactory.eINSTANCE.createEvent();
			final VarDeclaration dataInput = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			d.getInterface().getEventInputs().add(inputEvent);
			d.getInterface().getEventOutputs().add(outputEvent);
			d.getInterface().getInputVars().add(dataInput);

			final FBType dummyType = LibraryElementFactory.eINSTANCE.createBasicFBType();
			dummyType.setName("Demux Type Entry"); //$NON-NLS-1$
			final FBTypeEntryMock te = new FBTypeEntryMock(dummyType, typeLib, null);
			typeLib.addTypeEntry(te);
			d.setTypeEntry(te);

			d.setDataType(struct);
			d.updateConfiguration();
			final FBNetwork dummyFbN = LibraryElementFactory.eINSTANCE.createFBNetwork();
			dummyFbN.getNetworkElements().add(d);

			dummyType.setInterfaceList(EcoreUtil.copy(d.getInterface())); // make sure the type has the same interface
			return d;
		}

		public State(final State s) {
			struct = EcoreUtil.copy(s.getStruct());
			demux = EcoreUtil.copy(s.getDemultiplexer());
		}

		private static StructuredType createSampleStruct() {
			final StructuredType outer = createSampleStructType(OUTER_STRUCT_TYPE);
			final StructuredType inner = createSampleStructType(INNER_STRUCT_TYPE);

			final VarDeclaration structVAR1 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			structVAR1.setName(INNER_STRUCT_OBJECT1);
			structVAR1.setType(inner);
			final VarDeclaration structVAR2 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			structVAR2.setName(INNER_STRUCT_OBJECT2);
			structVAR2.setType(inner);
			outer.getMemberVariables().add(structVAR1);
			outer.getMemberVariables().add(structVAR2);

			return outer;
		}

		private static StructuredType createSampleStructType(final String name) {
			final StructuredType type = DataFactory.eINSTANCE.createStructuredType();
			type.setName(name);
			final VarDeclaration var1 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var1.setName(VARIABLE1);
			final VarDeclaration var2 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var2.setName(VARIABLE2);
			final VarDeclaration var3 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var3.setName(VARIABLE3);
			final VarDeclaration var4 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var4.setName(VARIABLE4);
			final VarDeclaration var5 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var5.setName(VARIABLE5);
			final VarDeclaration var6 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var6.setName(VARIABLE6);
			type.getMemberVariables().add(var1);
			type.getMemberVariables().add(var2);
			type.getMemberVariables().add(var3);
			type.getMemberVariables().add(var4);
			type.getMemberVariables().add(var5);
			type.getMemberVariables().add(var6);
			typeLib.addTypeEntry(new DataTypeEntryMock(type, typeLib, null));
			return type;
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static Collection<Arguments> describeCommand(final String description,
			final StateInitializer<?> initializer, final StateVerifier<?> initialVerifier,
			final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, s -> undoCommand((State) s),
				s -> redoCommand((State) s));
	}

	protected static void verifyDefaultInitialValues(final State state, final State oldState, final TestFunction t) {
		t.test(state.getDemultiplexer());
		t.test(state.getStruct().getMemberVariables().size(),
				state.getDemultiplexer().getInterface().getOutputVars().size());
		t.test(!state.getDemultiplexer().isIsConfigured());
	}

	protected static void verifyAdded(final State state, final TestFunction t, final String name) {
		t.test(state.getDemultiplexer());
		t.test(!state.getDemultiplexer().getMemberVars().stream().filter(MemberVarDeclaration.class::isInstance)
				.filter(out -> ((MemberVarDeclaration) out).getName().equals(name)).findAny().isEmpty());
		final Attribute attribute = getVisibleChildrenAttribute(state.getDemultiplexer());
		if (state.getDemultiplexer().isIsConfigured()) {
			t.test(Arrays.asList(attribute.getValue().split(",")) //$NON-NLS-1$
					.contains(name));
		}
		t.test(state.getDemultiplexer().getMemberVars().stream().anyMatch(memVar -> memVar.getName().equals(name)));
	}

	private static Attribute getVisibleChildrenAttribute(final Demultiplexer demux) {
		final EList<Attribute> attributes = demux.getConfigurationAsAttributes();
		return attributes.stream().filter(attr -> attr.getName().equals(LibraryElementTags.DEMUX_VISIBLE_CHILDREN))
				.findFirst().orElse(null);
	}

	protected static void verifyDeleted(final State state, final TestFunction t, final String name) {
		t.test(state.getDemultiplexer());
		t.test(state.getDemultiplexer().getInterface().getOutputVars().stream()
				.filter(out -> out.getName().equals(name)).findAny().isEmpty());
		final Attribute attribute = getVisibleChildrenAttribute(state.getDemultiplexer());
		if (state.getDemultiplexer().isIsConfigured()) {
			t.test(!Arrays.asList(attribute.getValue().split(",")) //$NON-NLS-1$
					.contains(name));
		}
		t.test(state.getDemultiplexer().getMemberVars().stream().noneMatch(memVar -> memVar.getName().equals(name)));
	}

	private static State executeDeleteCommand(final State state, final String name) {
		final CheckableStructTreeNode n = (CheckableStructTreeNode) state.tree.getRoot().find(name);
		final DeleteDemuxPortCommand cmd = new DeleteDemuxPortCommand(state.getDemultiplexer(), n);
		state.setCommand(cmd);
		final State newState = commandExecution(state);
		newState.setDemultiplexer(cmd.getType());
		return newState;
	}

	private static State executeAddCommand(final State state, final String name) {
		final CheckableStructTreeNode n = (CheckableStructTreeNode) state.tree.getRoot().find(name);
		final AddDemuxPortCommand cmd = new AddDemuxPortCommand(state.getDemultiplexer(), n);
		state.setCommand(cmd);
		final State newState = commandExecution(state);
		newState.setDemultiplexer(cmd.getType());
		return newState;
	}

	private static State undoCommand(final State state) {
		final State newState = defaultUndoCommand(state);
		newState.setDemultiplexer(getDemux(state));
		return newState;
	}

	private static Demultiplexer getDemux(final State state) {
		Demultiplexer demux;
		if (state.getCommand() instanceof DeleteDemuxPortCommand) {
			demux = ((DeleteDemuxPortCommand) state.getCommand()).getType();
		} else {
			demux = ((AddDemuxPortCommand) state.getCommand()).getType();
		}
		return demux;
	}

	private static State redoCommand(final State s) {
		final State newState = defaultRedoCommand(s);
		newState.setDemultiplexer(getDemux(s));
		return newState;
	}

	// define here the list of test sequences
	// multiple execution descriptions are possible -> define in test class
	protected static List<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();
		// test series 1
		commands.addAll(describeCommand("Starting from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) AddDeleteDemuxPortCommandTest::verifyDefaultInitialValues, //
				executionDescriptions //
		));
		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add inner struct's variable", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, INNER_STRUCT_OBJECT1 + SEP + VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyAdded(s, t,
								INNER_STRUCT_OBJECT1 + SEP + VARIABLE1)), //
				new ExecutionDescription<>("Delete inner struct's variable 1", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, INNER_STRUCT_OBJECT1 + SEP + VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t,
								INNER_STRUCT_OBJECT1 + SEP + VARIABLE1)), //
				new ExecutionDescription<>("Delete first variable", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE1)), //
				new ExecutionDescription<>("Delete second variable", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE2),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE2)), //
				new ExecutionDescription<>("Add first variable", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyAdded(s, t, VARIABLE1)), //
				new ExecutionDescription<>("Add second variable", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, VARIABLE2), //
						(final State s, final State o, final TestFunction t) -> {// $NON-NLS-1$
							verifyAdded(s, t, VARIABLE2);
							verifyAdded(s, t, VARIABLE1);
						}),
				new ExecutionDescription<>("Add first inner struct's variable 2", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, INNER_STRUCT_OBJECT1 + SEP + VARIABLE2), //
						(final State s, final State o, final TestFunction t) -> {// $NON-NLS-1$
							verifyAdded(s, t, VARIABLE2);
							verifyAdded(s, t, VARIABLE1);
							verifyAdded(s, t, INNER_STRUCT_OBJECT1 + SEP + VARIABLE2);
						}),
				new ExecutionDescription<>("Add second inner struct's variable 6", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, INNER_STRUCT_OBJECT2 + SEP + VARIABLE6),
						(final State s, final State o, final TestFunction t) -> {
							verifyAdded(s, t, VARIABLE2);
							verifyAdded(s, t, VARIABLE1);
							verifyAdded(s, t, INNER_STRUCT_OBJECT1 + SEP + VARIABLE2);
							verifyAdded(s, t, INNER_STRUCT_OBJECT2 + SEP + VARIABLE6);
						}),
				new ExecutionDescription<>("Delete inner struct's variable 2", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, INNER_STRUCT_OBJECT1 + SEP + VARIABLE2),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t,
								INNER_STRUCT_OBJECT1 + SEP + VARIABLE2)),
				new ExecutionDescription<>("Delete inner struct's variable 6", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, INNER_STRUCT_OBJECT2 + SEP + VARIABLE6),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t,
								INNER_STRUCT_OBJECT2 + SEP + VARIABLE6)),
				// empty the output ports
				new ExecutionDescription<>("Delete all: 1", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE1)), //
				new ExecutionDescription<>("Delete all: 2", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE2),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE2)), //
				new ExecutionDescription<>("Delete all: 3", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE3),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE3)), //
				new ExecutionDescription<>("Delete all: 4", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE4),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE4)), //
				new ExecutionDescription<>("Delete all: 5", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE5),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE5)), //
				new ExecutionDescription<>("Delete all: 6", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE6),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE6)),
				new ExecutionDescription<>("Delete all: innerstruct1", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, INNER_STRUCT_OBJECT1),
						(final State s, final State o,
								final TestFunction t) -> verifyDeleted(s, t, INNER_STRUCT_OBJECT1)),
				new ExecutionDescription<>("Delete all: innerstruct2", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, INNER_STRUCT_OBJECT2),
						(final State s, final State o, final TestFunction t) -> { //
							t.test(s.getDemultiplexer().getInterface().getOutputVars().isEmpty());
							verifyDeleted(s, t, INNER_STRUCT_OBJECT2); //
						}),
				new ExecutionDescription<>("Add variable from empty", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyAdded(s, t, VARIABLE1)), //
				new ExecutionDescription<>("Delete all: 1", // //$NON-NLS-1$
						(final State s) -> executeDeleteCommand(s, VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyDeleted(s, t, VARIABLE1)), //
				new ExecutionDescription<>("Add struct-type variable from empty", // //$NON-NLS-1$
						(final State s) -> executeAddCommand(s, INNER_STRUCT_OBJECT1 + SEP + VARIABLE1),
						(final State s, final State o, final TestFunction t) -> verifyAdded(s, t,
								INNER_STRUCT_OBJECT1 + SEP + VARIABLE1)) //
		); //

		return createCommands(executionDescriptions);
	}
}
