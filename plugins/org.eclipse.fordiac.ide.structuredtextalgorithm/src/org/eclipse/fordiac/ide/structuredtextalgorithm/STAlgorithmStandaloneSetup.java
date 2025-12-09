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
package org.eclipse.fordiac.ide.structuredtextalgorithm;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension
 * registry.
 */
public class STAlgorithmStandaloneSetup extends STAlgorithmStandaloneSetupGenerated {

	public static void doSetup() {
		new STAlgorithmStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public void register(final Injector injector) {
		STAlgorithmPackage.eINSTANCE.eClass();
		super.register(injector);
	}
}
