/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial commit of contract specification editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.fordiac.ide.ContractSpecRuntimeModule;
import org.eclipse.fordiac.ide.ContractSpecStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class ContractSpecIdeSetup extends ContractSpecStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new ContractSpecRuntimeModule(), new ContractSpecIdeModule()));
	}
	
}
