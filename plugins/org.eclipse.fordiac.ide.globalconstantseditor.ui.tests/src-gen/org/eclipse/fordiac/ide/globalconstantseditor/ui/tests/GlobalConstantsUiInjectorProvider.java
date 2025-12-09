/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.tests;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.internal.GlobalconstantseditorActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

public class GlobalConstantsUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return GlobalconstantseditorActivator.getInstance().getInjector("org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstants");
	}

}
