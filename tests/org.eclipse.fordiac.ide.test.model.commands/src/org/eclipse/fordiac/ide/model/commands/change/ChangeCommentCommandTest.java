package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.runners.Parameterized.Parameters;

public class ChangeCommentCommandTest extends FBNetworkTestBase {

	private static final String COMMENT1 = "first comment";
	private static final String COMMENT2 = "second comment";

	public static State executeCommand(State state, String comment) {
		state.setCommand(new ChangeCommentCommand(state.getFbNetwork().getNetworkElements().get(0).getType(), comment));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	public static void verifyStateBefore(State state, State oldState, TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getComment().isEmpty());
	}

	public static void verifyState(State state, State oldState, TestFunction t, String comment) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getComment().equals(comment));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						ChangeCommentCommandTest::verifyStateBefore //
				), //
				new ExecutionDescription<State>("Change comment", //$NON-NLS-1$
						(State state) -> executeCommand(state, COMMENT1), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, COMMENT1) //
				), //
				new ExecutionDescription<State>("Change comment", //$NON-NLS-1$
						(State state) -> executeCommand(state, COMMENT2), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, COMMENT2) //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
