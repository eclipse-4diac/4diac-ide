/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 - 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - adapted ChangeTypeCommand for multiplexer use, sets struct
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

public class ChangeStructCommand extends AbstractUpdateFBNElementCommand {

	private final StructuredType newStruct;

	public ChangeStructCommand(final StructManipulator mux, final StructuredType newStruct) {
		super(mux);
		this.newStruct = newStruct;
		entry = mux.getPaletteEntry();
	}

	@Override
	protected void createNewFB() {
		if (oldElement instanceof Multiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (oldElement instanceof Demultiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		newElement.setPaletteEntry(entry);
		newElement.setInterface(EcoreUtil.copy(oldElement.getType().getInterfaceList()));
		newElement.setName(oldElement.getName());

		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));
		newElement.getAttributes().addAll(EcoreUtil.copyAll(oldElement.getAttributes()));
		newElement.deleteAttribute("VisibleChildren"); // TODO use constant
		((StructManipulator) newElement).setStructTypeElementsAtInterface(newStruct);
		createValues();
	}

	public StructManipulator getNewMux() {
		return (StructManipulator) newElement;
	}

	public StructManipulator getOldMux() {
		return (StructManipulator) oldElement;
	}
}
