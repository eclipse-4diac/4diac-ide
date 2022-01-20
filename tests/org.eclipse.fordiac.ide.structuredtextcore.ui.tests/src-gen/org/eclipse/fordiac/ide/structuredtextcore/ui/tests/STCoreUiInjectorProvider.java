/**
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.tests;

import com.google.inject.Injector;
import org.eclipse.fordiac.ide.structuredtextcore.ui.internal.StructuredtextcoreActivator;
import org.eclipse.xtext.testing.IInjectorProvider;

public class STCoreUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return StructuredtextcoreActivator.getInstance().getInjector("org.eclipse.fordiac.ide.structuredtextcore.STCore");
	}

}
