/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies Austria GmbH and others
 *                          Martin Erich Jobst
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
 *   Martin Erich Jobst - copy and adapt ChangeInternalFBOrderCommand for attributes
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;

public class ChangeAttributeOrderCommand extends AbstractChangeListElementOrderCommand<Attribute> {

	public ChangeAttributeOrderCommand(final ConfigurableObject configurableObject, final Attribute attribute,
			final IndexUpDown updown) {
		super(attribute, isMoveUp(updown), configurableObject.getAttributes());
	}

	public ChangeAttributeOrderCommand(final ConfigurableObject configurableObject, final Attribute attribute,
			final Attribute ref) {
		super(attribute, ref, false, configurableObject.getAttributes());
	}

	private static boolean isMoveUp(final IndexUpDown updown) {
		return updown == IndexUpDown.UP;
	}
}
