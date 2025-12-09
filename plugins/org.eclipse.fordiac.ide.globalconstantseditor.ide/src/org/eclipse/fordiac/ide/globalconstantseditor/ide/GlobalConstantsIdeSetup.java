/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *               
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsRuntimeModule;
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class GlobalConstantsIdeSetup extends GlobalConstantsStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new GlobalConstantsRuntimeModule(), new GlobalConstantsIdeModule()));
	}
	
}
