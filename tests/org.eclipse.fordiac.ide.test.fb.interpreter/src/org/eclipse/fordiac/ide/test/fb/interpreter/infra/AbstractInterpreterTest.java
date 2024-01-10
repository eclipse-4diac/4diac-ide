/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmenda, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek
 *   	 -cleanup and us factory methods
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.fb.interpreter.infra;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.FBTransactionBuilder;
import org.eclipse.fordiac.ide.fb.interpreter.api.ServiceFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBTestRunner;
import org.eclipse.fordiac.ide.fb.interpreter.mm.FBNetworkTestRunner.IllegalTraceException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.Test;
import org.osgi.framework.Bundle;

public abstract class AbstractInterpreterTest {
	public static final String START_STATE = "START"; //$NON-NLS-1$
	private static final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.fb.interpreter"); //$NON-NLS-1$

	@Test
	public abstract void test() throws IllegalArgumentException, IllegalTraceException;

	public static FBType loadFBType(final String name) {
		return loadFBType(name, true);
	}

	public static void runFBTest(final FBType fb, final ServiceSequence seq) {
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq);
		assertTrue(!result.isPresent());
	}

	public static void runFBTest(final FBType fb, final ServiceSequence seq, final String startStateName) {
		final Optional<String> result = FBTestRunner.runFBTest(fb, seq, startStateName);
		assertTrue(result.isEmpty());
	}

	protected static FBNetwork loadFbNetwork(final String projectName, final String systemName) {
		return loadFbNetwork(projectName, systemName, null);
	}

	protected static FBNetwork loadFbNetwork(final String projectName, final String systemName, final String appName) {
		final Path projectPath = new Path("data/" + projectName); //$NON-NLS-1$
		FordiacProjectLoader loader;
		try {
			loader = new FordiacProjectLoader(bundle, projectPath);
		} catch (CoreException | IOException e) {
			return null;
		}

		final AutomationSystem system = loader.getAutomationSystem(systemName);
		if (appName == null) {
			return system.getApplication().get(0).getFBNetwork();
		}
		return system.getApplication().stream().filter(app -> app.getName().equals(appName)).findAny().orElseThrow()
				.getFBNetwork();
	}

	protected static FBType loadFBType(final String name, final boolean emptyService) {
		final Path projectPath = new Path("data/TestFBs"); //$NON-NLS-1$
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.fb.interpreter"); //$NON-NLS-1$
		FordiacProjectLoader loader;
		try {
			loader = new FordiacProjectLoader(bundle, projectPath);
		} catch (CoreException | IOException e) {
			return null;
		}

		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(loader.getEclipseProject());
		final FBTypeEntry typeEntry = typeLib.getFBTypeEntry(name);
		final FBType fbt = typeEntry.getType();

		if (emptyService) {
			fbt.setService(ServiceFactory.createDefaultServiceModel());
		}
		return fbt;
	}

	protected static void checkNetworkResults(final EList<Transaction> returnedTransactions,
			final List<FBTransactionBuilder> expectedTs) throws IllegalTraceException {
		assert (returnedTransactions.size() == expectedTs.size());

		for (int i = 0; i < expectedTs.size(); i++) {
			FBNetworkTestRunner.checkTransaction(returnedTransactions.get(i), expectedTs.get(i));
		}
	}

}
