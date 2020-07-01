package org.eclipse.fordiac.ide.model.commands.testinfra;

import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;

public abstract class FBNetworkTestBase extends CommandTestBase<FBNetworkTestBase.State> {

	// create a state description that fits our purpose
	public static class State implements CommandTestBase.StateBase {
		private FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		private FBTypePaletteEntry palette = preparePaletteWithTypeLib();
		private FBTypePaletteEntry functionblock = createFB();

		private Command cmd;

		private FBTypePaletteEntry preparePaletteWithTypeLib() {
			FBTypePaletteEntry pallEntry = PaletteFactory.eINSTANCE.createFBTypePaletteEntry();
			TypeLibrary typelib = TypeLibrary.getTypeLibrary(null);
			pallEntry.setPalette(typelib.getBlockTypeLib());
			return pallEntry;
		}

		private static final String FUNCTIONBLOCK_NAME = "functionblock"; //$NON-NLS-1$

		private FBTypePaletteEntry createFB() {
			FBTypePaletteEntry pe = PaletteFactory.eINSTANCE.createFBTypePaletteEntry();

			BasicFBType functionBlock = LibraryElementFactory.eINSTANCE.createBasicFBType();

			functionBlock.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
			functionBlock.setName(FUNCTIONBLOCK_NAME);

			functionBlock.setECC(LibraryElementFactory.eINSTANCE.createECC());

			pe.setLabel(FUNCTIONBLOCK_NAME);
			pe.setPalette(palette.getPalette());
			pe.setType(functionBlock);

			return pe;

		}

		public FBNetwork getFbNetwork() {
			return net;
		}

		public FBTypePaletteEntry getPalette() {
			return palette;
		}

		public FBTypePaletteEntry getFunctionblock() {
			return functionblock;
		}

		public Command getCommand() {
			return cmd;
		}

		public void setCommand(Command cmd) {
			this.cmd = cmd;
		}
	}

	protected static State undoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.getCommand().canUndo());
		state.getCommand().undo();
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.getCommand().canRedo());
		state.getCommand().redo();
		return (state);
	}

	protected static Collection<Object[]> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<Object> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, FBNetworkTestBase::undoCommand,
				FBNetworkTestBase::redoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(!state.getFbNetwork().isCFBTypeNetwork());
		t.test(!state.getFbNetwork().isResourceNetwork());
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(state.getFbNetwork().getNetworkElements().isEmpty());
	}

	protected static List<Object[]> createCommands(List<Object> executionDescriptions) {
		List<Object[]> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", //
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

}
