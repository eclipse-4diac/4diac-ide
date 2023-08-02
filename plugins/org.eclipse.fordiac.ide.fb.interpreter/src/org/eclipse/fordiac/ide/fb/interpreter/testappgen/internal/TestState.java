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

import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class TestState {
	private ServiceTransaction dataSource;
	
	public TestState(ServiceTransaction dataSource) {
		if (dataSource == null) {
			throw new IllegalArgumentException("TestState must not be null.");
		}
		this.dataSource = dataSource;
	}

	public static TestState createTestState(ServiceTransaction serviceTransaction) {
		TestState testState = new TestState(serviceTransaction);
		
		return testState; 
	}
	
	public InputPrimitive getTestTrigger() {
		return dataSource.getInputPrimitive();
	}
}
