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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteDemuxPortCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase.State;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

public class AddDeleteDemuxPortCommandTest extends CommandTestBase<State> {
	private static final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(null);

	protected static class State implements CommandTestBase.StateBase {

		private final Demultiplexer demux;
		private StructuredType struct;

		private Command cmd;

		public Demultiplexer getDemultiplexer() {
			return demux;
		}

		public StructuredType getStruct() {
			return struct;
		}

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command command) {
			this.cmd = command;
		}

		public State() {
			struct = createSampleStruct();
			demux = createDemultiplexer();
		}

		private Demultiplexer createDemultiplexer() {
			Demultiplexer d = LibraryElementFactory.eINSTANCE.createDemultiplexer();
			d.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
			Event inputEvent = LibraryElementFactory.eINSTANCE.createEvent();
			Event outputEvent = LibraryElementFactory.eINSTANCE.createEvent();
			VarDeclaration dataInput = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			d.getInterface().getEventInputs().add(inputEvent);
			d.getInterface().getEventOutputs().add(outputEvent);
			d.getInterface().getInputVars().add(dataInput);

			PaletteEntry dummyType = PaletteFactory.eINSTANCE.createFBTypePaletteEntry();
			dummyType.setLabel("Demux Palette Entry"); //$NON-NLS-1$
			dummyType.setType(LibraryElementFactory.eINSTANCE.createBasicFBType());
			typeLib.addPaletteEntry(dummyType);
			d.setPaletteEntry(dummyType);

			d.setStructType(struct);
			return d;
		}

		public State(State s) {
			struct = EcoreUtil.copy(s.getStruct());
			demux = EcoreUtil.copy(s.getDemultiplexer());
		}

		private static StructuredType createSampleStruct() {
			StructuredType outer = createSampleStructType("outerStruct"); //$NON-NLS-1$
			StructuredType inner = createSampleStructType("innerStruct"); //$NON-NLS-1$

			VarDeclaration structVAR1 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			structVAR1.setName("innerstruct1"); //$NON-NLS-1$
			structVAR1.setType(inner);
			VarDeclaration structVAR2 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			structVAR2.setName("innerstruct2"); //$NON-NLS-1$
			structVAR2.setType(inner);
			outer.getMemberVariables().add(structVAR1);
			outer.getMemberVariables().add(structVAR2);

			return outer;
		}

		private static StructuredType createSampleStructType(String name) {
			StructuredType type = DataFactory.eINSTANCE.createStructuredType();
			type.setName(name);
			VarDeclaration var1 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var1.setName("VAR1"); //$NON-NLS-1$
			VarDeclaration var2 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var2.setName("VAR2"); //$NON-NLS-1$
			VarDeclaration var3 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var3.setName("VAR3"); //$NON-NLS-1$
			VarDeclaration var4 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var4.setName("VAR4"); //$NON-NLS-1$
			VarDeclaration var5 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var5.setName("VAR5"); //$NON-NLS-1$
			VarDeclaration var6 = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			var6.setName("VAR6"); //$NON-NLS-1$
			type.getMemberVariables().add(var1);
			type.getMemberVariables().add(var2);
			type.getMemberVariables().add(var3);
			type.getMemberVariables().add(var4);
			type.getMemberVariables().add(var5);
			type.getMemberVariables().add(var6);
			DataTypePaletteEntry dataEntry = PaletteFactory.eINSTANCE.createDataTypePaletteEntry();
			dataEntry.setType(type);
			dataEntry.setLabel(name);
			typeLib.getDataTypeLibrary().addPaletteEntry(dataEntry);
			return type;
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}


	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				CommandTestBase::defaultUndoCommand, CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getDemultiplexer());
		t.test(state.getStruct().getMemberVariables().size(), state.getDemultiplexer().getInterface().getOutputVars()
				.size());
	}

	protected static void verifyAdded(State state, State oldState, TestFunction t, String name) {
		t.test(state.getDemultiplexer());
		t.test(!state.getDemultiplexer().getInterface().getOutputVars().stream()
				.filter(out -> out.getName().equals(name)).findAny().isEmpty());
		t.test(Arrays
				.asList(state.getDemultiplexer().getAttribute(LibraryElementTags.DEMUX_VISIBLE_CHILDREN).getValue()
						.split(",")) //$NON-NLS-1$
				.contains(name));
	}

	protected static void verifyDeleted(State state, State oldState, TestFunction t, String name) {
		t.test(state.getDemultiplexer());
		t.test(state.getDemultiplexer().getInterface().getOutputVars().stream()
				.filter(out -> out.getName().equals(name)).findAny().isEmpty());
		t.test(!Arrays
				.asList(state.getDemultiplexer().getAttribute(LibraryElementTags.DEMUX_VISIBLE_CHILDREN).getValue()
						.split(",")) //$NON-NLS-1$
				.contains(name));
	}

	private static State executeDeleteCommand(State state, String name) {
		state.setCommand(new DeleteDemuxPortCommand(state.getDemultiplexer(), name));

		return commandExecution(state);
	}

	private static State executeAddCommand(State state, String name) {
		state.setCommand(new AddDemuxPortCommand(state.getDemultiplexer(), name));

		return commandExecution(state);
	}

	// define here the list of test sequences
	// multiple execution descriptions are possible -> define in test class
	protected static List<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		List<Arguments> commands = new ArrayList<>();
		// test series 1
		commands.addAll(describeCommand("Starting from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
		));
		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "innerstruct1.VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyAdded(s, o, t, "innerstruct1.VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "innerstruct1.VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "innerstruct1.VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete first variable", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete second variable", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR2"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR2")), // //$NON-NLS-1$
				new ExecutionDescription<>("Add first variable", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyAdded(s, o, t, "VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Add second variable", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "VAR2"), //
						(State s, State o, TestFunction t) -> {// $NON-NLS-1$
							verifyAdded(s, o, t, "VAR2");//$NON-NLS-1$
							verifyAdded(s, o, t, "VAR1");//$NON-NLS-1$
						}),
				new ExecutionDescription<>("Add first inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "innerstruct1.VAR2"), //
						(State s, State o, TestFunction t) -> {// $NON-NLS-1$
							verifyAdded(s, o, t, "VAR2");//$NON-NLS-1$
							verifyAdded(s, o, t, "VAR1");//$NON-NLS-1$
							verifyAdded(s, o, t, "innerstruct1.VAR2"); //$NON-NLS-1$
						}),
				new ExecutionDescription<>("Add second inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "innerstruct2.VAR6"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> {
							verifyAdded(s, o, t, "VAR2");//$NON-NLS-1$
							verifyAdded(s, o, t, "VAR1");//$NON-NLS-1$
							verifyAdded(s, o, t, "innerstruct1.VAR2"); //$NON-NLS-1$
							verifyAdded(s, o, t, "innerstruct2.VAR6"); //$NON-NLS-1$
						}),
				new ExecutionDescription<>("Delete inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "innerstruct1.VAR2"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "innerstruct1.VAR2")), //$NON-NLS-1$
				new ExecutionDescription<>("Delete inner struct's variable", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "innerstruct2.VAR6"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "innerstruct2.VAR6")),
				// empty the output ports
				new ExecutionDescription<>("Delete all: 1", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 2", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR2"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR2")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 3", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR3"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR3")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 4", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR4"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR4")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 5", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR5"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR5")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 6", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR6"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR6")),
				new ExecutionDescription<>("Delete all: innerstruct1", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "innerstruct1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "innerstruct1")),
				new ExecutionDescription<>("Delete all: innerstruct2", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "innerstruct2"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> { //
							t.test(s.getDemultiplexer().getInterface().getOutputVars().isEmpty());
							verifyDeleted(s, o, t, "innerstruct2"); // //$NON-NLS-1$
						}),
				new ExecutionDescription<>("Add variable from empty", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyAdded(s, o, t, "VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Delete all: 1", // //$NON-NLS-1$
						(State s) -> executeDeleteCommand(s, "VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyDeleted(s, o, t, "VAR1")), // //$NON-NLS-1$
				new ExecutionDescription<>("Add struct-type variable from empty", // //$NON-NLS-1$
						(State s) -> executeAddCommand(s, "innerstruct1.VAR1"), //$NON-NLS-1$
						(State s, State o, TestFunction t) -> verifyAdded(s, o, t, "innerstruct1.VAR1")) // //$NON-NLS-1$
		); //

		return createCommands(executionDescriptions);
	}
}
