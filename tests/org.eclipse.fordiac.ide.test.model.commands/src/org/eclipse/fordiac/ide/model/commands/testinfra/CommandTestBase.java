/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import org.hamcrest.CoreMatchers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.hamcrest.Matcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.TestAbortedException;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

/**
 * Base class for testing commands. Sets up a undo/redo structure and a state
 * description
 *
 * @param <T> type of the state description, has to be derived from
 *            CommandTestBase.StateBase
 */
public abstract class CommandTestBase<T extends CommandTestBase.StateBase> {

	/**
	 * Test Function object that can be used to access assertions
	 */
	protected static final TestFunction assertion = CommandTestBase::assertThat;
	/**
	 * Test Function object that can be used to access assumptions
	 */
	protected static final TestFunction assumption = CommandTestBase::assumeThat;

	/**
	 * assertThat method to be used to validate the current state
	 * @throws AssertionError
	 *
	 * @param reason     textual description of what is expected
	 * @param actual     actual value to be compared
	 * @param matcher    matcher-object that does the comparison
	 */
	public static <T> void assertThat(String reason, T actual, Matcher<T> matcher) {
		org.hamcrest.MatcherAssert.assertThat(reason, actual, matcher);
	}

	/**
	 * assumeThat method to be used to validate the current state
	 * @throws TestAbortedException
	 *
	 * @param reason     textual description of what is expected
	 * @param actual     actual value to be compared
	 * @param matcher    matcher-object that does the comparison
	 */
	public static <T> void assumeThat(String reason, T actual, Matcher<T> matcher) {
		try {
			assertThat(reason, actual, matcher);
		} catch (AssertionError e) {
			throw new TestAbortedException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * forwarding of the "is" hamcrest-core-matcher
	 *
	 * @param matcher    matcher-object that does the comparison
	 * @return matcher object
	 */
	protected static <T> Matcher<T> is(Matcher<T> matcher)  {
		return CoreMatchers.is(matcher);
	}
	
	/**
	 * forwarding of the "is" hamcrest-core-matcher
	 *
	 * @param matcher    value to compare against
	 * @return matcher object
	 */
	protected static <T> Matcher<T> is(T matcher)  {
		return CoreMatchers.is(matcher);
	}

	/**
	 * empty verification method
	 *
	 * @param s    current state description
	 * @param o    state description before the last state transition
	 * @param t    TestFunction object that selects either assert or assume
	 */
	protected static <T extends StateBase> void verifyNothing(T s, T o, TestFunction t) {
		// Nothing to do
	}

	/**
	 * undo method that expects that undo is possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T defaultUndoCommand(Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		assumption.test(state.getCommand().canExecute() && state.getCommand().canUndo());
		state.getCommand().undo();
		return (state);
	}

	/**
	 * redo method that expects that redo is possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T defaultRedoCommand(Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		assumption.test(state.getCommand().canExecute() && state.getCommand().canRedo());
		state.getCommand().redo();
		return (state);
	}

	/**
	 * undo method that expects that undo is not possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T disabledUndoCommand(Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		assumption.test(!(state.getCommand().canExecute() && state.getCommand().canUndo()));
		return (state);
	}

	/**
	 * redo method that expects that redo is not possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T disabledRedoCommand(Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		assumption.test(!(state.getCommand().canExecute() && state.getCommand().canRedo()));
		return (state);
	}

	/**
	 * execution method that expects that execution is not possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T disabledCommandExecution(T state) {
		assumption.test(state.getCommand());
		assumption.test(!state.getCommand().canExecute());
		return state;
	}

	/**
	 * execution method that expects that execution is possible
	 *
	 * @param stateObj    current state description
	 * @return            new state description
	 */
	protected static <T extends StateBase> T commandExecution(T state) {
		assumption.test(state.getCommand());
		assumption.test(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}
	
	/**
	 * Base type for state descriptions, used to structure class hierarchy
	 *
	 */

	public interface StateBase {
		Command getCommand();

		void setCommand(Command cmd);

		Object getClone();

	}

	/**
	 * functional interface for setting the initial state
	 *
	 */
	protected interface StateInitializer<T> {
		T initializeState();
	}

	/**
	 * functional interface for executing a state transition
	 *
	 */
	protected interface CommandExecutor<T> {
		T executeCommand(T state);
	}

	/**
	 * functional interface for calling either assert* or assume*
	 *
	 */
	protected interface TestFunction {
		/**
		 * assumeThat/assertThat method to be used to validate the current state
		 *
		 * @param message    textual description of what is expected
		 * @param actual     actual value to be compared
		 * @param matcher    matcher-object that does the comparison
		 */
		<T> void test(String message, T actual, Matcher<T> matcher);

		/**
		 * assumeThat/assertThat method to be used to validate the current state
		 *
		 * @param actual     actual value to be compared
		 * @param matcher    matcher-object that does the comparison
		 */
		default <T> void test(T actual, Matcher<T> matcher) {
			test("", actual, matcher);
		}

		/**
		 * assumeEqual/assertEqual method to be used to validate the current state
		 *
		 * @param actual     actual value to be compared
		 * @param expected   expected value for the comparision
		 */
		default <T> void test(T actual, T expected) {
			test("", actual, is(expected));
		}

		/**
		 * assumeTrue/assertTrue method to be used to validate the current state
		 *
		 * @param equals     boolean value which is expected to be true
		 */
		default void test(boolean equals) {
			test("", equals, is(true));
		}

		/**
		 * assumeNotNull/assertNotNull method to check for non-null references
		 * @throws TestAbortedException
		 *
		 * @param notNull     reference that should not be null
		 */
		default void test(Object notNull) {
			test("", notNull, org.hamcrest.CoreMatchers.notNullValue());
		}

	}

	/**
	 * functional interface for verifying a state based on changes against the state
	 * before that step (oldState)
	 *
	 */
	protected interface StateVerifier<T> {
		void verifyState(T state, T oldState, TestFunction test);
	}

	enum ExecutionType {
		COMMAND, UNDO, REDO
	}

	/**
	 * combination of functional interfaces for a state transition and verification
	 * of that transition
	 *
	 * @param <U> type of state description derived form StateBase
	 */
	protected static class ExecutionDescription<U extends StateBase> {
		final ExecutionType type;
		final CommandExecutor<U> executor;
		final StateVerifier<U> verifier;
		final String description;

		/**
		 * Constructor for creating a execution description with selectable Execution
		 * type, only used internally
		 *
		 * @param description textual description of the state transition
		 * @param type        COMMAND/UNDO/REDO as given by the enum
		 * @param executor    lambda/method reference to state transition method
		 * @param verifier    lambda/method reference to state verification method
		 */
		private ExecutionDescription(String description, ExecutionType type, CommandExecutor<U> executor,
				StateVerifier<U> verifier) {
			this.type = type;
			this.executor = executor;
			this.verifier = verifier;
			this.description = description;
		}

		/**
		 * Constructor for creating a execution description of type COMMAND
		 *
		 * @param description textual description of the state transition
		 * @param executor    lambda/method reference to state transition method
		 * @param verifier    lambda/method reference to state verification method
		 */
		public ExecutionDescription(String description, CommandExecutor<U> executor, StateVerifier<U> verifier) {
			this(description, ExecutionType.COMMAND, executor, verifier);
		}

		/**
		 * factory method to create a redo transition
		 *
		 * @return execution description that redoes a transition
		 */
		public static ExecutionDescription<? extends StateBase> redo() {
			return new ExecutionDescription<>("redo", ExecutionType.REDO, null, null); //$NON-NLS-1$
		}

		/**
		 * factory method to create a undo transition
		 *
		 * @return execution description that undoes a transition
		 */
		public static ExecutionDescription<? extends StateBase> undo() {
			return new ExecutionDescription<>("undo", ExecutionType.UNDO, null, null); //$NON-NLS-1$
		}

	}

	/**
	 * node for storing undo/redo information in a linked list
	 *
	 * @param <U> type of state description derived form StateBase
	 */
	protected class StateNode<U extends StateBase> {
		private final U state;
		private final StateVerifier<U> verifier;
		private final StateNode<U> before;
		private StateNode<U> after = null;

		/**
		 * @return current state description
		 */
		public U getState() {
			return state;
		}

		/**
		 * @return current state verifier
		 */
		public StateVerifier<U> getVerifier() {
			return verifier;
		}

		/**
		 * @return get state before last state transition
		 */
		public StateNode<U> getBefore() {
			StateNode<U> dummy = new StateNode<>(null, null, null);
			return null != before ? before : dummy;
		}

		/**
		 * @return get state from before the last undo operation
		 */
		public StateNode<U> getAfter() {
			StateNode<U> dummy = new StateNode<>(null, null, null);
			return null != after ? after : dummy;
		}

		/**
		 * @param after set state from before the last undo operation
		 */
		public void setAfter(StateNode<U> after) {
			this.after = after;
		}

		/**
		 * @param state
		 * @param verifier
		 * @param before
		 */
		public StateNode(U state, StateVerifier<U> verifier, StateNode<U> before) {
			this.state = state;
			this.verifier = verifier;
			this.before = before;
		}

		public StateNode(StateNode<U> statenode) {
			Object clone = statenode.state.getClone();
			if (!(clone instanceof CommandTestBase.StateBase)) {
				throw new RuntimeException();
			}
			@SuppressWarnings("unchecked")
			U stateClone = (U) clone;

			this.state = stateClone;
			this.verifier = statenode.getVerifier();
			this.before = statenode.getBefore();
			this.after = statenode.getAfter();
		}

	}

	/**
	 * method to describe a command to the class
	 *
	 * @param description     textual description of the command
	 * @param initializer     method to set initial state
	 * @param initialVerifier method to verify the initial state
	 * @param creator         method to prepare command infrastructure
	 * @param commands        list of commands and verifiers to be executed (use
	 *                        helper method commandList for creation)
	 * @param undo            state transition that undoes a command
	 * @param redo            state transition that redoes a command
	 * @return
	 */
	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands, CommandExecutor<?> undo,
			CommandExecutor<?> redo) {
		final Collection<Arguments> descriptions = new ArrayList<>();

		final String MESSAGE_MAIN = "{0} : {1}"; //$NON-NLS-1$
		final String MESSAGE_NO_MAIN = "{1}"; //$NON-NLS-1$

		final String MESSAGE_VERIFY_INITIAL_STATE = "Verify initial State"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_ALL_COMMANDS = "Execute all Commands"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I = "Execute until Command {0}: {1}"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO = "Execute until Command {0}: {1}, run Undo"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO = "Execute until Command {0}: {1}, run Undo, run Redo"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO_ALL = "Execute all Commands interspersed with Undo and Redo"; //$NON-NLS-1$

		descriptions.add(Arguments.of(
				MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
						MESSAGE_VERIFY_INITIAL_STATE),
				initializer, initialVerifier, Collections.emptyList(), undo, redo));

		if (commands.size() > 1) {
			descriptions.add(Arguments.of(MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN,
					description, MESSAGE_EXECUTE_ALL_COMMANDS), initializer, initialVerifier, commands, undo, redo));
		}

		int index = 0;
		final ArrayList<Object> commandsUntil = new ArrayList<>();
		ArrayList<Object> commandsWithUndoRedo;
		final ArrayList<Object> commandsWithUndoRedoAll = new ArrayList<>();
		for (final Object commandObject : commands) {
			final ExecutionDescription<?> command = (ExecutionDescription<?>) commandObject;
			index++;
			commandsUntil.add(command);
			commandsWithUndoRedoAll.add(command);
			commandsWithUndoRedo = new ArrayList<>();
			commandsWithUndoRedo.addAll(commandsUntil);
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I, index, command.description)),
					initializer, initialVerifier, commandsWithUndoRedo.clone(), undo, redo));
			commandsWithUndoRedo.add(ExecutionDescription.undo());
			commandsWithUndoRedoAll.add(ExecutionDescription.undo());
			descriptions
					.add(Arguments.of(
							MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
									MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO, index,
											command.description)),
							initializer, initialVerifier, commandsWithUndoRedo.clone(), undo, redo));
			commandsWithUndoRedo.add(ExecutionDescription.redo());
			commandsWithUndoRedoAll.add(ExecutionDescription.redo());
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO, index,
									command.description)),
					initializer, initialVerifier, commandsWithUndoRedo.clone(), undo, redo));
		}

		if (commands.size() > 1) {
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO_ALL),
					initializer, initialVerifier, commandsWithUndoRedoAll, undo, redo));
		}
		return descriptions;

	}

	/**
	 * actual JUnit test case
	 *
	 * executes commands and adds redo/undo steps and adds a meaningful description
	 */
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("data")
	public void testCommand(String description, StateInitializer<T> initializer, StateVerifier<T> initialVerifier,
			Iterable<ExecutionDescription<T>> commands, CommandExecutor<T> undo, CommandExecutor<T> redo) {
		// prepare initial state and verify
		StateNode<T> current = new StateNode<>(initializer.initializeState(), initialVerifier, null);

		// if there are more steps to be executed the initial verification is not
		// asserted but only assumed
		// execution of the test will therefore not fail on those commands due to
		// initial assumptions being wrong
		final Iterator<ExecutionDescription<T>> iterator = commands.iterator();
		TestFunction t;
		if (iterator.hasNext()) {
			t = assumption;
		} else {
			t = assertion;
		}

		initialVerifier.verifyState(current.getState(), current.getBefore().getState(), t);

		// step through all the commands/undo/redo
		while (iterator.hasNext()) {
			final ExecutionDescription<T> command = iterator.next();
			// if there are more commands to be executed use assume instead of assert
			// same reason as for initial state verifier
			if (iterator.hasNext()) {
				t = assumption;
			} else {
				t = assertion;
			}

			StateNode<T> clone = new StateNode<>(current);
			switch (command.type) {
			case COMMAND:
				// execute command if available and verify
				if (null != command.executor) {
					current = new StateNode<>(command.executor.executeCommand(current.getState()), command.verifier,
							clone);
					clone.setAfter(current);
				}
				command.verifier.verifyState(current.getState(), current.getBefore().getState(), t);
				break;
			case UNDO:
				// execute undo and fix undo list
				current = new StateNode<>(undo.executeCommand(current.getState()), current.getBefore().getVerifier(),
						clone.getBefore().getBefore());
				current.setAfter(clone);
				current.getVerifier().verifyState(current.getState(), current.getBefore().getState(), t);
				break;
			case REDO:
				// execute redo and fix undo list
				current = new StateNode<>(redo.executeCommand(current.getState()), current.getAfter().getVerifier(),
						clone);
				clone.setAfter(current);
				current.getVerifier().verifyState(current.getState(), current.getBefore().getState(), t);
				break;
			}
		}
	}

}
