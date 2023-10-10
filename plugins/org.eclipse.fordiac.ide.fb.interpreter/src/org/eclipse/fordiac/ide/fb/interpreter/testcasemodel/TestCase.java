/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.testcasemodel;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class TestCase {
	private final String name;
	private final ServiceSequence dataSource;
	private final List<TestState> testStates = new ArrayList<>();

	public TestCase(final ServiceSequence dataSource) { // , TestSuite testSuite
		if (dataSource == null) {
			throw new IllegalArgumentException("TestCase must not be null."); //$NON-NLS-1$
		}
		this.dataSource = dataSource;
		name = dataSource.getName();
	}

	public static TestCase createTestCase(final ServiceSequence serviceSequence) {
		final TestCase testCase = new TestCase(serviceSequence);
		serviceSequence.getServiceTransaction().stream()
				.forEach(n -> testCase.testStates.add(TestState.createTestState(n)));
		return testCase;
	}

	public String getName() {
		return name;
	}

	public List<TestState> getTestStates() {
		return testStates;
	}

	public ServiceSequence getdataSource() {
		return dataSource;
	}
}
