/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil - initial API and implementation and/or initial documentation
 *   Alois Zoitl     - introduced dedicated editpart for better interaction with
 *                     property sheets
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.SimpleFBEditor.SimpleFBEditPart;
import org.eclipse.jface.viewers.IFilter;

public class SimpleFBFilter implements IFilter {

	@Override
	public boolean select(final Object toTest) {
		return toTest instanceof SimpleFBEditPart;
	}

}
