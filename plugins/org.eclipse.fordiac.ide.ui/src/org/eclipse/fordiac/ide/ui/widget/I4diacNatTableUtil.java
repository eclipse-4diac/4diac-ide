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

import org.eclipse.gef.commands.CompoundCommand;

public interface I4diacNatTableUtil {

	void addEntry(Object entry, boolean isInput, int index, CompoundCommand cmd);

	void removeEntry(Object entry, CompoundCommand cmd);

	void executeCompoundCommand(CompoundCommand cmd);

	boolean isEditable();
}
