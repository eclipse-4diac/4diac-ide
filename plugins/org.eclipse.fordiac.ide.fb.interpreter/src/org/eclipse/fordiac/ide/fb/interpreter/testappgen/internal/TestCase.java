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

package org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class TestCase {
	private String name;
	private ServiceSequence dataSource;
	private List<TestState> testStates = new ArrayList<>();
	
	public TestCase(ServiceSequence dataSource) { //, TestSuite testSuite
		if (dataSource == null) {
			throw new IllegalArgumentException("TestCase must not be null.");
		}
		this.dataSource = dataSource;
		name = dataSource.getName();
	}

	public static TestCase createTestCase(ServiceSequence serviceSequence) {
		TestCase testCase = new TestCase(serviceSequence);
		for (ServiceTransaction serviceTransaction : serviceSequence.getServiceTransaction()) {
			testCase.testStates.add(TestState.createTestState(serviceTransaction));
		}
		return testCase;
	}

	public String getName() {
		return name;
	}
	
	public List<TestState> getTestStates(){
		return testStates;
	}
	
	public ServiceSequence getdataSource() {
		return dataSource;
	}
}
