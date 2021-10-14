/*******************************************************************************
 * Copyright (c) 2014 - 2015 fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

public class ChangeServiceInterfaceNameCommand extends Command {

	private final FBType fb;
	private final boolean isLeftInterface;
	private final String name;
	private String oldName;

	public ChangeServiceInterfaceNameCommand(final String name, final FBType fb, final boolean isLeftInterface) {
		this.fb = fb;
		this.isLeftInterface = isLeftInterface;
		this.name = name;
	}

	@Override
	public void execute() {
		if (fb.getService() == null) {
			fb.setService(LibraryElementFactory.eINSTANCE.createService());
		}
		if (isLeftInterface) {
			if (fb.getService().getLeftInterface() == null) {
				fb.getService().setLeftInterface(LibraryElementFactory.eINSTANCE.createServiceInterface());
				oldName = ""; //$NON-NLS-1$
			} else {
				oldName = fb.getService().getLeftInterface().getName();
			}
		} else {
			if (fb.getService().getRightInterface() == null) {
				fb.getService().setRightInterface(LibraryElementFactory.eINSTANCE.createServiceInterface());
				oldName = ""; //$NON-NLS-1$
			} else {
				oldName = fb.getService().getRightInterface().getName();
			}
		}
		setName(name);
	}

	@Override
	public void undo() {
		setName(oldName);
	}

	@Override
	public void redo() {
		setName(name);
	}

	private void setName(final String name) {
		if (isLeftInterface) {
			fb.getService().getLeftInterface().setName(name);
		} else {
			fb.getService().getRightInterface().setName(name);
		}
	}
}
