/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class ChangeSubAppPinCommand extends AbstractUpdateFBNElementCommand {
	private final StructuredType type;

	public ChangeSubAppPinCommand(final FBNetworkElement oldElement, final StructuredType type) {
		super(oldElement);
		this.type = type;

	}

	@Override
	protected void createNewFB() {
		newElement = EcoreUtil.copy(oldElement);
		if (newElement instanceof final SubApp subApp) {
			final EList<IInterfaceElement> otherIn = subApp.getInterface().getAllInterfaceElements();
			otherIn.stream().filter(in -> in.getName().equals(type.getName())).forEach(s -> s.setType(type));
		}
	}
}
