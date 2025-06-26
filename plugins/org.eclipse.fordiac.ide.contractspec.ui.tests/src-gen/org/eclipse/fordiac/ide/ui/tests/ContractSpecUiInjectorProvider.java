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
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.tests;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.contractspec.ui.internal.ContractspecActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

public class ContractSpecUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return ContractspecActivator.getInstance().getInjector("org.eclipse.fordiac.ide.ContractSpec");
	}

}
