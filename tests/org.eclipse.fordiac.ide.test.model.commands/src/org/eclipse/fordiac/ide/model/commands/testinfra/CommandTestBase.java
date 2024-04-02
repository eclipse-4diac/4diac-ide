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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.TestInfo;
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
	 * Thread-Local storage for TestInfo from JUnit
	 *
	 * used to add information on what test was executed to the Exception
	 */
	private static final ThreadLocal<TestInfo> testinfo = new ThreadLocal<>();
	protected static final ThreadLocal<TestFunction> tester = new ThreadLocal<>();

	private static ErrorMessageTestReceiver emh = new ErrorMessageTestReceiver();

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
	 *
	 * @throws AssertionError signals that this test has failed because an assertion
	 *                        has failed
	 *
	 * @param <T>     generic name of the type to be verified
	 * @param reason  textual description of what is expected
	 * @param actual  actual value to be compared
	 * @param matcher matcher-object that does the comparison
	 */
	private static <T> void assertThat(final String reason, final T actual, final Matcher<T> matcher) {
		try {
			if (null != testinfo.get()) {
				org.hamcrest.MatcherAssert.assertThat(
						MessageFormat.format("{0}\n{1}", testinfo.get().getDisplayName(), reason), actual, //$NON-NLS-1$
						matcher);
			} else {
				org.hamcrest.MatcherAssert.assertThat(reason, actual, matcher);
			}
		} catch (final AssertionError e) {
			e.setStackTrace(removeInfraFromStacktrace(e.getStackTrace()));
			throw e;
		}
	}

	/**
	 * assumeThat method to be used to validate the current state
	 *
	 * @throws TestAbortedException signals that this test will be skipped because
	 *                              an assertion has failed
	 *
	 * @param <T>     generic name of the type to be verified
	 * @param reason  textual description of what is expected
	 * @param actual  actual value to be compared
	 * @param matcher matcher-object that does the comparison
	 */
	private static <T> void assumeThat(final String reason, final T actual, final Matcher<T> matcher) {
		try {
			assertThat(reason, actual, matcher);
		} catch (final AssertionError e) { // NOSONAR
			final TestAbortedException ex = new TestAbortedException(e.getMessage(), e.getCause());
			ex.setStackTrace(removeInfraFromStacktrace(ex.getStackTrace()));
			throw ex;
		}
	}

	/**
	 * remove everthing up to the call to the TestFunction-object from the callstack
	 *
	 * @param st stacktrace to be cleaned up
	 * @return cleaned stacktrace if a TestFunction was called, original stacktrace
	 *         otherwise
	 */
	private static StackTraceElement[] removeInfraFromStacktrace(final StackTraceElement[] st) {
		if (Arrays.stream(st)
				.anyMatch(item -> CommandTestBase.TestFunction.class.getName().equals(item.getClassName()))) {
			return Arrays.stream(st)
					.dropWhile(item -> !CommandTestBase.TestFunction.class.getName().equals(item.getClassName()))
					.toArray(StackTraceElement[]::new);
		}
		return st;
	}

	/**
	 * forwarding of the "is" hamcrest-core-matcher
	 *
	 * @param <T>     generic name of the type to be matched
	 * @param matcher matcher-object that does the comparison
	 * @return matcher object
	 */
	protected static <T> Matcher<T> is(final Matcher<T> matcher) {
		return CoreMatchers.is(matcher);
	}

	/**
	 * forwarding of the "is" hamcrest-core-matcher
	 *
	 * @param <T>     generic name of the type to be matched
	 * @param matcher value to compare against
	 * @return matcher object
	 */
	protected static <T> Matcher<T> is(final T matcher) {
		return CoreMatchers.is(matcher);
	}

	/**
	 * empty verification method
	 *
	 * @param <T> generic name of the state-description class
	 * @param s   current state description
	 * @param o   state description before the last state transition
	 * @param t   TestFunction object that selects either assert or assume
	 */
	protected static <T extends StateBase> void verifyNothing(final T s, final T o, final TestFunction t) {
		// Nothing to do
	}

	/**
	 * undo method that expects that undo is possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T defaultUndoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		if (state.isUndoAllowed()) {
			return enabledUndoCommand(stateObj);
		}
		return disabledUndoCommand(stateObj);
	}

	/**
	 * redo method that expects that redo is possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T defaultRedoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		if (state.isUndoAllowed()) {
			return enabledRedoCommand(stateObj);
		}
		return disabledRedoCommand(stateObj);
	}

	/**
	 * redo method that expects that redo is possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T enabledRedoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		emh.start();
		tester.get().test(state.getCommand().canExecute() && state.getCommand().canRedo());
		state.getCommand().redo();
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	/**
	 * undo method that expects that undo is possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T enabledUndoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		emh.start();
		tester.get().test(state.getCommand().canUndo());
		state.getCommand().undo();
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	/**
	 * undo method that expects that undo is not possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T disabledUndoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		emh.start();
		tester.get().test(!(state.getCommand().canExecute() && state.getCommand().canUndo()));
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	/**
	 * redo method that expects that redo is not possible
	 *
	 * @param <T>      generic name of the state-description class
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T disabledRedoCommand(final Object stateObj) {
		@SuppressWarnings("unchecked")
		final T state = (T) stateObj;
		emh.start();
		tester.get().test(!(state.getCommand().canExecute() && state.getCommand().canRedo()));
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	/**
	 * execution method that expects that execution is not possible
	 *
	 * @param <T>   generic name of the state-description class
	 * @param state current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T disabledCommandExecution(final T state) {
		tester.get().test(state.getCommand());
		emh.start();
		tester.get().test(!state.getCommand().canExecute());
		state.setMessages(emh.getMessages());
		emh.stop();
		state.setUndoAllowed(false);
		return state;
	}

	/**
	 * execution method that expects that execution is possible
	 *
	 * @param <T>   generic name of the state-description class
	 * @param state current state description
	 * @return new state description
	 */
	protected static <T extends StateBase> T commandExecution(final T state) {
		tester.get().test(state.getCommand());
		emh.start();
		tester.get().test(state.getCommand().canExecute());
		state.getCommand().execute();
		state.setMessages(emh.getMessages());
		state.setUndoAllowed(state.getCommand().canUndo());
		emh.stop();
		return state;
	}

	/**
	 * Base type for state descriptions, used to structure class hierarchy
	 *
	 */

	public abstract static class StateBase {
		private Command cmd;

		private boolean undoAllowed = true;
		private boolean viaUndo = false;

		private List<String> messages = List.of();

		public Command getCommand() {
			return cmd;
		}

		public void setCommand(final Command cmd) {
			this.cmd = cmd;
		}

		public void setUndoAllowed(final boolean undoAllowed) {
			this.undoAllowed = undoAllowed;
		}

		public boolean isUndoAllowed() {
			return undoAllowed;
		}

		public void setViaUndo() {
			viaUndo = true;
		}

		public boolean isViaUndo() {
			return viaUndo;
		}

		public void setMessages(final List<String> messages) {
			this.messages = messages;
		}

		public List<String> getMessages() {
			return messages;
		}

		protected StateBase() {
			// NOP
		}

		protected StateBase(final StateBase s) {
			this.undoAllowed = s.undoAllowed;
			this.cmd = s.cmd;
			this.viaUndo = s.viaUndo;
			this.messages = s.messages;
		}

		public abstract Object getClone();

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
		 * depending on the situation either AssertionError or TestAbortedException is
		 * thrown This marks the test either as "Fail" or "Skip"
		 *
		 * @param <T>     generic name of the type to be verified
		 * @param message textual description of what is expected
		 * @param actual  actual value to be compared
		 * @param matcher matcher-object that does the comparison
		 */
		<T> void test(String message, T actual, Matcher<T> matcher);

		/**
		 * assumeThat/assertThat method to be used to validate the current state
		 *
		 * @param <T>     generic name of the type to be verified
		 * @param actual  actual value to be compared
		 * @param matcher matcher-object that does the comparison
		 */
		default <T> void test(final T actual, final Matcher<T> matcher) {
			test(MessageFormat.format("Testing type <{0}> using a matcher", getTypeName(actual) //$NON-NLS-1$
			), actual, matcher);
		}

		/**
		 * assumeEqual/assertEqual method to be used to validate the current state
		 *
		 * @param <T>      generic name of the type to be verified
		 * @param actual   actual value to be compared
		 * @param expected expected value for the comparison
		 */
		default <T> void test(final T actual, final T expected) {
			test(MessageFormat.format("Testing type <{0}> for equality", getTypeName(actual)), actual, is(expected)); //$NON-NLS-1$
		}

		/**
		 * assumeEqual/assertEqual method to be used to validate the current state for
		 * non-boxed integers
		 *
		 * @param <T>      generic name of the type to be verified
		 * @param actual   actual value to be compared
		 * @param expected expected value for the comparison
		 */
		default <T> void test(final int actual, final int expected) { // NOSONAR
			test("Testing <int> for equality", Integer.valueOf(actual), is(Integer.valueOf(expected))); //$NON-NLS-1$
		}

		/**
		 * assumeEqual/assertEqual method to be used to validate the current state for
		 * non-boxed booleans
		 *
		 * @param <T>      generic name of the type to be verified
		 * @param actual   actual value to be compared
		 * @param expected expected value for the comparison
		 */
		default <T> void test(final boolean actual, final boolean expected) { // NOSONAR
			test("Testing <boolean> for equality", Boolean.valueOf(actual), is(Boolean.valueOf(expected))); //$NON-NLS-1$
		}

		default <T> void test(final double actual, final double expected, final double epsilon) { // NOSONAR
			test("Testing <double> for equality", Double.valueOf(actual), //$NON-NLS-1$
					org.hamcrest.number.IsCloseTo.closeTo(expected, epsilon));
		}

		/**
		 * assumeTrue/assertTrue method to be used to validate the current state
		 *
		 * @param equals boolean value which is expected to be true
		 */
		default void test(final boolean equals) {
			test("Testing <boolean> to be true", Boolean.valueOf(equals), is(Boolean.TRUE)); //$NON-NLS-1$
		}

		/**
		 * assumeNotNull/assertNotNull method to check for non-null references
		 *
		 * @param notNull reference that should not be null
		 */
		default void test(final Object notNull) {
			test("Testing <Object> to be non-null", notNull, org.hamcrest.CoreMatchers.notNullValue()); //$NON-NLS-1$
		}

		private static String getTypeName(final Object obj) {
			final String nullStringValue = "null"; //$NON-NLS-1$
			return null == obj ? nullStringValue : obj.getClass().getSimpleName();
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
		private ExecutionDescription(final String description, final ExecutionType type,
				final CommandExecutor<U> executor, final StateVerifier<U> verifier) {
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
		public ExecutionDescription(final String description, final CommandExecutor<U> executor,
				final StateVerifier<U> verifier) {
			this(description, ExecutionType.COMMAND, executor, verifier);
		}

		/**
		 * factory method to create a redo transition
		 *
		 * @return execution description that redoes a transition
		 */
		public static ExecutionDescription<? extends StateBase> redo() { // NOSONAR
			return new ExecutionDescription<>("redo", ExecutionType.REDO, null, null); //$NON-NLS-1$
		}

		/**
		 * factory method to create a undo transition
		 *
		 * @return execution description that undoes a transition
		 */
		public static ExecutionDescription<? extends StateBase> undo() { // NOSONARs
			return new ExecutionDescription<>("undo", ExecutionType.UNDO, null, null); //$NON-NLS-1$
		}

	}

	/**
	 * node for storing undo/redo information in a linked list
	 *
	 * @param <U> type of state description derived form StateBase
	 */
	protected static class StateNode<U extends StateBase> {
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
			final StateNode<U> dummy = new StateNode<>(null, null, null);
			return null != before ? before : dummy;
		}

		/**
		 * @return get state from before the last undo operation
		 */
		public StateNode<U> getAfter() {
			final StateNode<U> dummy = new StateNode<>(null, null, null);
			return null != after ? after : dummy;
		}

		/**
		 * @param after set state from before the last undo operation
		 */
		public void setAfter(final StateNode<U> after) {
			this.after = after;
		}

		/**
		 * @param state    Current state description
		 * @param verifier Verification method for the expected current state
		 * @param before   Reference to the previous state description
		 */
		public StateNode(final U state, final StateVerifier<U> verifier, final StateNode<U> before) {
			this.state = state;
			this.verifier = verifier;
			this.before = before;
		}

		public StateNode(final StateNode<U> statenode) {
			final Object clone = statenode.state.getClone();
			if (!(clone instanceof CommandTestBase.StateBase)) {
				throw new RuntimeException("Encountered invalid State"); // NOSONAR //$NON-NLS-1$
			}
			@SuppressWarnings("unchecked")
			final U stateClone = (U) clone;

			this.state = stateClone;
			this.verifier = statenode.getVerifier();
			this.before = statenode.getBefore();
			this.after = statenode.getAfter();
		}

	}

	/**
	 * method to describe a command to the class to be used inside a method "public
	 * static Collection&lt;Arguments&gt; data()" This "data()" method is used to
	 * determine the testcases to be run
	 *
	 * @param description     textual description of the command
	 * @param initializer     method to set initial state
	 * @param initialVerifier method to verify the initial state
	 * @param commands        list of commands and verifiers to be executed (use
	 *                        helper method commandList for creation)
	 * @param undo            state transition that undoes a command
	 * @param redo            state transition that redoes a command
	 * @return a collection of generated parameters to be passed to testCommand
	 */
	protected static Collection<Arguments> describeCommand(final String description,
			final StateInitializer<?> initializer, final StateVerifier<?> initialVerifier,
			final List<ExecutionDescription<?>> commands, final CommandExecutor<?> undo,
			final CommandExecutor<?> redo) {
		final Collection<Arguments> descriptions = new ArrayList<>();

		final String MESSAGE_MAIN = "{0} : {1}"; //$NON-NLS-1$
		final String MESSAGE_NO_MAIN = "{1}"; //$NON-NLS-1$

		final String MESSAGE_VERIFY_INITIAL_STATE = "Verify initial State"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I = "Execute until Command {0}: {1}"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO = "Execute until Command {0}: {1}, run Undo"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO = "Execute until Command {0}: {1}, run Undo, run Redo"; //$NON-NLS-1$
		final String MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO_ALL = "Execute all Commands interspersed with Undo and Redo"; //$NON-NLS-1$

		descriptions.add(Arguments.of(
				MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
						MESSAGE_VERIFY_INITIAL_STATE),
				initializer, initialVerifier, Collections.emptyList(), undo, redo));

		int index = 0;
		final ArrayList<Object> commandsUntil = new ArrayList<>();
		ArrayList<Object> commandsWithUndoRedo;
		final ArrayList<Object> commandsWithUndoRedoAll = new ArrayList<>();
		for (final ExecutionDescription<?> command : commands) {
			index++;
			commandsUntil.add(command);
			commandsWithUndoRedoAll.add(command);
			commandsWithUndoRedo = new ArrayList<>();
			commandsWithUndoRedo.addAll(commandsUntil);
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I, Integer.valueOf(index),
									command.description)),
					initializer, initialVerifier, commandsWithUndoRedo.clone(), undo, redo));
			commandsWithUndoRedo.add(ExecutionDescription.undo());
			commandsWithUndoRedoAll.add(ExecutionDescription.undo());
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO, Integer.valueOf(index),
									command.description)),
					initializer, initialVerifier, commandsWithUndoRedo.clone(), undo, redo));
			commandsWithUndoRedo.add(ExecutionDescription.redo());
			commandsWithUndoRedoAll.add(ExecutionDescription.redo());
			descriptions.add(Arguments.of(
					MessageFormat.format(null != description ? MESSAGE_MAIN : MESSAGE_NO_MAIN, description,
							MessageFormat.format(MESSAGE_EXECUTE_UNTIL_COMMAND_I_UNDO_REDO, Integer.valueOf(index),
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
	 * the parameters to be used are generated by a method "data", except for
	 * TestInfo which gets injected by JUnit
	 *
	 * @param description     textual description of the command
	 * @param initializer     method to set initial state
	 * @param initialVerifier method to verify the initial state
	 * @param commands        list of commands and verifiers to be executed (use
	 *                        helper method commandList for creation)
	 * @param undo            state transition that undoes a command
	 * @param redo            state transition that redoes a command
	 * @param ti              TestInfo, injected by JUnit; contains the DisplayName
	 *                        of the test
	 */
	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("data")
	public void testCommand(final String description, final StateInitializer<T> initializer,
			final StateVerifier<T> initialVerifier, final Iterable<ExecutionDescription<T>> commands,
			final CommandExecutor<T> undo, final CommandExecutor<T> redo, final TestInfo ti) {

		testinfo.set(ti);
		tester.set(assertion);

		try {

			// prepare initial state and verify
			StateNode<T> current = new StateNode<>(initializer.initializeState(), initialVerifier, null);

			// if there are more steps to be executed the initial verification is not
			// asserted but only assumed
			// execution of the test will therefore not fail on those commands due to
			// initial assumptions being wrong
			final Iterator<ExecutionDescription<T>> iterator = commands.iterator();
			if (iterator.hasNext()) {
				tester.set(assumption);
			} else {
				tester.set(assertion);
			}

			initialVerifier.verifyState(current.getState(), current.getBefore().getState(), tester.get());

			// step through all the commands/undo/redo
			while (iterator.hasNext()) {
				final ExecutionDescription<T> command = iterator.next();
				// if there are more commands to be executed use assume instead of assert
				// same reason as for initial state verifier
				if (iterator.hasNext()) {
					tester.set(assumption);
				} else {
					tester.set(assertion);
				}

				final StateNode<T> clone = new StateNode<>(current);
				switch (command.type) {
				case COMMAND:
					// execute command if available and verify
					if (null != command.executor) {
						current = new StateNode<>(command.executor.executeCommand(current.getState()), command.verifier,
								clone);
						clone.setAfter(current);
					}
					command.verifier.verifyState(current.getState(), current.getBefore().getState(), tester.get());
					break;
				case UNDO:
					// execute undo and fix undo list
					current = new StateNode<>(undo.executeCommand(current.getState()),
							current.getBefore().getVerifier(), clone.getBefore().getBefore());
					current.setAfter(clone);
					current.getState().setViaUndo();
					if (current.getState().isUndoAllowed()) {
						current.getVerifier().verifyState(current.getState(), current.getBefore().getState(),
								tester.get());
					}
					break;
				case REDO:
					// execute redo and fix undo list
					current = new StateNode<>(redo.executeCommand(current.getState()), current.getAfter().getVerifier(),
							clone);
					clone.setAfter(current);
					if (current.getState().isUndoAllowed()) {
						current.getVerifier().verifyState(current.getState(), current.getBefore().getState(),
								tester.get());
					}
					break;
				default:
					throw new RuntimeException("Unhandled Operation");// NOSONAR //$NON-NLS-1$
				}
			}
		} finally {
			testinfo.remove();
			tester.remove();
		}
	}

}
