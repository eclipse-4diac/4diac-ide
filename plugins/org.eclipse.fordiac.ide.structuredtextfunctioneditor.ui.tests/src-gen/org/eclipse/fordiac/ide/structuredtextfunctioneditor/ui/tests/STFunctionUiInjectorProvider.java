/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH, 
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.tests;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.internal.StructuredtextfunctioneditorActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

public class STFunctionUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return StructuredtextfunctioneditorActivator.getInstance().getInjector("org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunction");
	}

}
