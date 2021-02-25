/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk;

import org.eclipse.elk.core.service.ILayoutSetup;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class FordiacLayoutSetup implements ILayoutSetup {

	@Override
	public boolean supports(Object object) {
		return true;
	}

	@Override
	public Injector createInjector(Module defaultModule) {
	    return Guice.createInjector(Modules.override(defaultModule).with(new FordiacLayoutModule()));
	}

}
