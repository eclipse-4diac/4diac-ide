/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk;

import org.eclipse.elk.core.service.IDiagramLayoutConnector;

import com.google.inject.Binder;
import com.google.inject.Module;

public class FordiacLayoutModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(IDiagramLayoutConnector.class)
        .to(FordiacLayoutConnector.class);
	}
	
}
