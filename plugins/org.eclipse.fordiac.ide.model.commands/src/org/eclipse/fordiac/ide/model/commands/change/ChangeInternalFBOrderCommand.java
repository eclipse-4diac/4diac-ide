/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesamyr - cleanup, remove duplicated code
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;

public class ChangeInternalFBOrderCommand extends AbstractChangeListElementOrderCommand<FB> {

	public ChangeInternalFBOrderCommand(final BaseFBType baseFbtype, final FB fb, final IndexUpDown updown) {
		super(fb, isMoveUp(updown), getInternalFBList(baseFbtype));
	}

	private static boolean isMoveUp(final IndexUpDown updown) {
		return updown == IndexUpDown.UP;
	}

	private static EList<FB> getInternalFBList(final BaseFBType type) {
		return type.getInternalFbs();
	}

}
