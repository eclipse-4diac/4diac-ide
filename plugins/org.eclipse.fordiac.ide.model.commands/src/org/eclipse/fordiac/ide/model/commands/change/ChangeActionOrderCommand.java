/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Ernst Blecha
 *     - use AbstractChangeListElementOrderCommand as base class
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.BaseECAction;
import org.eclipse.fordiac.ide.model.libraryElement.BaseECState;

public class ChangeActionOrderCommand<T extends BaseECAction> extends AbstractChangeListElementOrderCommand<T> {

	public ChangeActionOrderCommand(final BaseECState<T> state, final T action, final boolean up) {
		super(action, up, state.getECActions());
	}

}
