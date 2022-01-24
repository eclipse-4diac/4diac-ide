/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.foridac.ide.structuredtextfunctioneditor.ide;

import org.eclipse.foridac.ide.structuredtextfunctioneditor.STFunctionRuntimeModule;
import org.eclipse.foridac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class STFunctionIdeSetup extends STFunctionStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new STFunctionRuntimeModule(), new STFunctionIdeModule()));
	}

}
