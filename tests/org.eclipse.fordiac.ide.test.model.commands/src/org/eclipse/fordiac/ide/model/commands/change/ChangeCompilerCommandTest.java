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
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.AddNewCompilerCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteCompilerCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeCompilerCommandTest extends FBNetworkTestBase {

	private static final String VENDOR_STRING = "Boring"; //$NON-NLS-1$
	private static final String PRODUCT_STRING = "Whitespace2CPP"; //$NON-NLS-1$
	private static final String VERSION_STRING = "3.14159265"; //$NON-NLS-1$

	public static State executeAddCommand(final State state) {
		state.setCommand(new AddNewCompilerCommand(state.getFbNetwork().getNetworkElements().get(0).getType()));

		return commandExecution(state);
	}

	public static void verifyDefaultState(final State state, final TestFunction t, final int index, final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo);
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.OTHER);
		t.test(compilerInfo.getCompiler().get(index).getProduct(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVendor(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeDeleteCommand(final State state) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler = compilerInfo.getCompiler().get(0);
		state.setCommand(new DeleteCompilerCommand(compilerInfo, compiler));

		return commandExecution(state);
	}

	public static State executeChangeLanguageCommand(final State state) {
		state.setCommand(new ChangeCompilerLanguageCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				Language.CPP));

		return commandExecution(state);
	}

	public static void verifyChangedLanguageState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.CPP);
		t.test(compilerInfo.getCompiler().get(index).getProduct(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVendor(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeChangeVendorCommand(final State state) {
		state.setCommand(new ChangeCompilerVendorCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				VENDOR_STRING));

		return commandExecution(state);
	}

	public static void verifyChangedVendorState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.CPP);
		t.test(compilerInfo.getCompiler().get(index).getVendor(), VENDOR_STRING);
		t.test(compilerInfo.getCompiler().get(index).getProduct(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeChangeProductCommand(final State state) {
		state.setCommand(new ChangeCompilerProductCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				PRODUCT_STRING));

		return commandExecution(state);
	}

	public static void verifyChangedProductState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.CPP);
		t.test(compilerInfo.getCompiler().get(index).getVendor(), VENDOR_STRING);
		t.test(compilerInfo.getCompiler().get(index).getProduct(), PRODUCT_STRING);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeChangeVersionCommand(final State state) {
		state.setCommand(new ChangeCompilerVersionCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				VERSION_STRING));

		return commandExecution(state);
	}

	public static void verifyChangedVersionState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.CPP);
		t.test(compilerInfo.getCompiler().get(index).getVendor(), VENDOR_STRING);
		t.test(compilerInfo.getCompiler().get(index).getProduct(), PRODUCT_STRING);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), VERSION_STRING);
	}

	public static State executeChangeVendorToNullCommand(final State state) {
		state.setCommand(new ChangeCompilerVendorCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				null));

		return commandExecution(state);
	}

	public static void verifyChangedVendorToNullState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.OTHER);
		t.test(compilerInfo.getCompiler().get(index).getVendor().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getProduct(), FordiacMessages.Unknown);
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeChangeProductToNullCommand(final State state) {
		state.setCommand(new ChangeCompilerProductCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				null));

		return commandExecution(state);
	}

	public static void verifyChangedProductToNullState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.OTHER);
		t.test(compilerInfo.getCompiler().get(index).getVendor().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getProduct().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getVersion(), "1.0"); //$NON-NLS-1$
	}

	public static State executeChangeVersionToNullCommand(final State state) {
		state.setCommand(new ChangeCompilerVersionCommand(
				state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo().getCompiler().get(0),
				null));

		return commandExecution(state);
	}

	public static void verifyChangedVersionToNullState(final State state, final TestFunction t, final int index,
			final int size) {
		final CompilerInfo compilerInfo = state.getFbNetwork().getNetworkElements().get(0).getType().getCompilerInfo();
		t.test(compilerInfo.getCompiler().size(), size);
		t.test(compilerInfo);
		t.test(!compilerInfo.getCompiler().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getLanguage(), Language.OTHER);
		t.test(compilerInfo.getCompiler().get(index).getVendor().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getProduct().isEmpty());
		t.test(compilerInfo.getCompiler().get(index).getVersion().isEmpty());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				), //
				new ExecutionDescription<>("Add Compiler Info to Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeAddCommand, //
						(final State s, final State o, final TestFunction t) -> verifyDefaultState(s, t, 0, 1)), //
				new ExecutionDescription<>("Add second Compiler Info to Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeAddCommand, //
						(final State s, final State o, final TestFunction t) -> {
							verifyDefaultState(s, t, 0, 2);
							verifyDefaultState(s, t, 1, 2);
						} //
				), //
				new ExecutionDescription<>("Delete first Compiler Info from Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeDeleteCommand, //
						(final State s, final State o, final TestFunction t) -> verifyDefaultState(s, t, 0, 1) //
				), //
				new ExecutionDescription<>("Change Compiler Language on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeLanguageCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedLanguageState(s, t, 0, 1) //
				), //
				new ExecutionDescription<>("Change Compiler Vendor on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeVendorCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedVendorState(s, t, 0, 1) //
				), //
				new ExecutionDescription<>("Change Compiler Product on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeProductCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedProductState(s, t, 0, 1) //
				), //
				new ExecutionDescription<>("Change Compiler Version on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeVersionCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedVersionState(s, t, 0, 1) //
				), //
				new ExecutionDescription<>("Add another Compiler Info to Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeAddCommand, //
						(final State s, final State o, final TestFunction t) -> {
							verifyChangedVersionState(s, t, 0, 2);
							verifyDefaultState(s, t, 1, 2);
						} //
				), //
				new ExecutionDescription<>("Delete first Compiler Info from Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeDeleteCommand, //
						(final State s, final State o, final TestFunction t) -> verifyDefaultState(s, t, 0, 1)), //
				new ExecutionDescription<>("Change Compiler Vendor to NULL on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeVendorToNullCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedVendorToNullState(s, t, 0,
								1) //
				), //
				new ExecutionDescription<>("Change Compiler Product to NULL on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeProductToNullCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedProductToNullState(s, t, 0,
								1) //
				), //
				new ExecutionDescription<>("Change Compiler Version to NULL on Functionblock", //$NON-NLS-1$
						ChangeCompilerCommandTest::executeChangeVersionToNullCommand, //
						(final State s, final State o, final TestFunction t) -> verifyChangedVersionToNullState(s, t, 0,
								1) //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
