package org.eclipse.fordiac.ide.model.commands.testinfra;

import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;

public abstract class VersionInfoTestBase extends CommandTestBase<VersionInfoTestBase.State> {

	protected static final String EMPTY = ""; //$NON-NLS-1$

	private static final String SET_NAME = "first name surname"; //$NON-NLS-1$
	private static final String SET_DATE = "1984-08-04"; //$NON-NLS-1$
	private static final String SET_ORG = "4diac"; //$NON-NLS-1$
	private static final String SET_REMARKS = "remark something"; //$NON-NLS-1$
	private static final String SET_VERSION = "13.0"; //$NON-NLS-1$

	// create a state description that fits our purpose
	public static class State implements CommandTestBase.StateBase {
		private VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		private Command cmd;

		public VersionInfo getVersionInfo() {
			return vInfo;
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
		return describeCommand(description, initializer, initialVerifier, commands, VersionInfoTestBase::undoCommand,
				VersionInfoTestBase::redoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		final String DEFAULT_NAME = vInfo.getAuthor();
		final String DEFAULT_DATE = vInfo.getDate();
		final String DEFAULT_ORG = vInfo.getOrganization();
		final String DEFAULT_REMARKS = vInfo.getRemarks();
		final String DEFAULT_VERSION = vInfo.getVersion();

		t.test(state.getVersionInfo().getAuthor().equals(DEFAULT_NAME));
		t.test(state.getVersionInfo().getDate().equals(DEFAULT_DATE));
		t.test(state.getVersionInfo().getOrganization().equals(DEFAULT_ORG));
		t.test(state.getVersionInfo().getRemarks().equals(DEFAULT_REMARKS));
		t.test(state.getVersionInfo().getVersion().equals(DEFAULT_VERSION));
	}

	protected static State setInitialValues() {
		State state = new State();
		state.getVersionInfo().setAuthor(SET_NAME);
		state.getVersionInfo().setDate(SET_DATE);
		state.getVersionInfo().setOrganization(SET_ORG);
		state.getVersionInfo().setRemarks(SET_REMARKS);
		state.getVersionInfo().setVersion(SET_VERSION);
		return state;
	}

	protected static void verifySetInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getAuthor().equals(SET_NAME));
		t.test(state.getVersionInfo().getDate().equals(SET_DATE));
		t.test(state.getVersionInfo().getOrganization().equals(SET_ORG));
		t.test(state.getVersionInfo().getRemarks().equals(SET_REMARKS));
		t.test(state.getVersionInfo().getVersion().equals(SET_VERSION));
	}

	protected static List<Object[]> createCommands(List<Object> executionDescriptions) {
		List<Object[]> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", //
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
		));

		commands.addAll(describeCommand("Start from set values", //
				() -> setInitialValues(), //
				(State state, State oldState, TestFunction t) -> verifySetInitialValues(state, oldState, t), //
				executionDescriptions //
		));
		return commands;
	}

}
