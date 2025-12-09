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
package org.eclipse.fordiac.ide.globalconstantseditor;

import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.GlobalConstantsPackage;

import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
public class GlobalConstantsStandaloneSetup extends GlobalConstantsStandaloneSetupGenerated {

	public static void doSetup() {
		new GlobalConstantsStandaloneSetup().createInjectorAndDoEMFRegistration();
	}

	@Override
	public void register(final Injector injector) {
		GlobalConstantsPackage.eINSTANCE.eClass();
		super.register(injector);
	}

}