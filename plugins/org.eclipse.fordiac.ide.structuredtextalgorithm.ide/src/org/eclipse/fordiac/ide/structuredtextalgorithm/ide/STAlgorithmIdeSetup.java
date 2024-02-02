/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.ide;

import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmRuntimeModule;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class STAlgorithmIdeSetup extends STAlgorithmStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new STAlgorithmRuntimeModule(), new STAlgorithmIdeModule()));
	}

}
