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

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class TestSuite {
	private Service dataSource;
	private final List<TestCase> testCases = new ArrayList<>();

	public TestSuite(final FBType type) {
		if (type.getService() == null || type.getService().getServiceSequence().isEmpty()) {
			throw new IllegalArgumentException("Test suites must be defined as service model");
		}
		dataSource = type.getService();
		for (final ServiceSequence serviceSequence : type.getService().getServiceSequence()) {
			testCases.add(TestCase.createTestCase(serviceSequence));
		}

	}

	public TestSuite(final List<ServiceSequence> sequences) {
		for (final ServiceSequence serviceSequence : sequences) {
			testCases.add(TestCase.createTestCase(serviceSequence));
		}
	}

	public Service getDataSource() {
		return dataSource;
	}

	public static TestSuite createTestSuite(final FBType type) {
		final TestSuite testSuite = new TestSuite(type);
		for (final ServiceSequence serviceSequence : type.getService().getServiceSequence()) {
			testSuite.testCases.add(TestCase.createTestCase(serviceSequence));
		}
		return testSuite;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}
}
