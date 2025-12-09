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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.action.CopyDataAction;
import org.eclipse.nebula.widgets.nattable.ui.action.IKeyAction;
import org.eclipse.swt.events.KeyEvent;

public class CutDataFromTableAction implements IKeyAction {

	private final I4diacNatTableUtil section;

	public CutDataFromTableAction() {
		this.section = null;
	}

	public CutDataFromTableAction(final I4diacNatTableUtil section) {
		this.section = section;
	}

	@Override
	public void run(final NatTable natTable, final KeyEvent event) {
		new CopyDataAction().run(natTable, event);
		new DeleteDataFromTableAction(section).run(natTable, event);
	}
}
