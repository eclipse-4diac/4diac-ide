/*******************************************************************************
 * Copyright (c) 2022 JKU
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class AppendServiceSequenceCommand extends AbstractCreateElementCommand<ServiceSequence> {

	private final ServiceSequence toCopy;

	public AppendServiceSequenceCommand(final Service service, final ServiceSequence copy) {
		super(service.getServiceSequence());
		toCopy = copy;
	}

	@Override
	protected ServiceSequence createNewElement() {
		return toCopy;
	}
}
