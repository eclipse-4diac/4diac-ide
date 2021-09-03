/*******************************************************************************
 * Copyright (c) 2012 - 2017 AIT, fortiss GmbH, Profactor GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Filip Andren, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked update fb type to accept also supapps
 *   Alois Zoitl - fixed issues in maintaining FB parameters
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/** UpdateFBTypeCommand triggers an update of the type for an FB instance */
public class UpdateFBTypeCommand extends AbstractUpdateFBNElementCommand {

	public UpdateFBTypeCommand(final FBNetworkElement fbnElement, final PaletteEntry entry) {
		super(fbnElement);
		if ((entry instanceof FBTypePaletteEntry) || (entry instanceof SubApplicationTypePaletteEntry)) {
			this.entry = entry;
		} else {
			this.entry = fbnElement.getPaletteEntry();
		}
	}

	@Override
	public boolean canExecute() {
		if ((null == entry) || (null == oldElement) || (null == network)) {
			return false;
		}
		return FBNetworkHelper.isTypeInsertionSave((FBType) entry.getType(), network);
	}

	@Override
	protected void createNewFB() {
		newElement = createCopiedFBEntry(oldElement);

		newElement.setInterface(newElement.getType().getInterfaceList().copy());

		newElement.setName(oldElement.getName());
		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));

		createValues();
	}

	protected void setEntry(final PaletteEntry entry) {
		this.entry = entry;
	}

	protected PaletteEntry getEntry() {
		return entry;
	}

	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		FBNetworkElement copy;
		if (entry instanceof SubApplicationTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createSubApp();
		} else if (entry instanceof AdapterTypePaletteEntry) {
			copy = LibraryElementFactory.eINSTANCE.createAdapterFB();
			((AdapterFB) copy).setAdapterDecl(((AdapterFB) srcElement).getAdapterDecl());
		} else if (entry.getType() instanceof CompositeFBType) {
			copy = LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else if (oldElement instanceof ErrorMarkerFBNElement && entry instanceof FBTypePaletteEntry) {
			copy = createErrorTypeFb();
		} else if (entry.getFile() == null || !entry.getFile().exists()) {
			copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		} else if (isMultiplexer()) {	// $NON-NLS-1$
			copy = createMultiplexer();
		} else {
			copy = LibraryElementFactory.eINSTANCE.createFB();
		}

		copy.setPaletteEntry(entry);
		return copy;
	}

	private FBNetworkElement createMultiplexer() {
		FBNetworkElement copy;
		StructManipulator sManipulator;
		if ("STRUCT_MUX".equals(entry.getType().getName())) { //$NON-NLS-1$
			sManipulator = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else {
			sManipulator = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		sManipulator.setStructType(new DataTypeLibrary().getStructuredTypes().get(0));
		copy = sManipulator;
		return copy;
	}

	private boolean isMultiplexer() {
		return entry.getType() instanceof ServiceInterfaceFBType && entry.getType().getName().startsWith("STRUCT");
	}

	public FBNetworkElement createErrorTypeFb() {
		FBNetworkElement copy;
		final TypeLibrary typeLibrary = oldElement.getPaletteEntry().getTypeLibrary();
		FBTypePaletteEntry fbTypeEntry = typeLibrary.getErrorTypeLib().getFBTypeEntry(oldElement.getType().getName());
		if (fbTypeEntry == null) {
			fbTypeEntry = typeLibrary.getBlockTypeLib().getFBTypeEntry(oldElement.getType().getName());
		}

		if (fbTypeEntry != null && fbTypeEntry.getFile() != null) {
			copy = LibraryElementFactory.eINSTANCE.createFB();
			copy.setPaletteEntry(fbTypeEntry);
			return copy;
		}
		copy = LibraryElementFactory.eINSTANCE.createErrorMarkerFBNElement();
		return copy;
	}
}