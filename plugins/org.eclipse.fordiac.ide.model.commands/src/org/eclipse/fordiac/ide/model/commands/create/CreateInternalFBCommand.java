/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.commands.Command;

public class CreateInternalFBCommand extends Command implements CreationCommand {

	/** The element where the internal FB is added to */
	private final BaseFBType baseFbType;

	/** Command information */
	private PaletteEntry fbType;
	private final String name;
	private final int index;

	/** Created internal FB */
	private FB internalFB;

	static final String DEFAULT_INTERNAL_FB_NAME = "InternalFB1"; //$NON-NLS-1$

	protected CreateInternalFBCommand(final BaseFBType baseFbType) {
		this(baseFbType, 0, null, null);
	}

	public CreateInternalFBCommand(final BaseFBType baseFbType, final int index, final String name,
			final PaletteEntry fbType) {
		this.baseFbType = baseFbType;
		this.fbType = fbType;
		if (null == fbType) {
			final EMap<String, FBTypePaletteEntry> typeLib = baseFbType.getTypeLibrary().getBlockTypeLib().getFbTypes();
			this.fbType = typeLib.get(0).getValue();
		}
		this.name = (null != name) ? name : DEFAULT_INTERNAL_FB_NAME;
		this.index = index;
	}

	public CreateInternalFBCommand(final BaseFBType baseFbType, int index, String name, PaletteEntry fbType,
			Palette palette) {
		this.baseFbType = baseFbType;
		this.fbType = fbType;
		if (null == fbType) {
			this.fbType = (PaletteEntry) palette.eContents().get(0);
		}
		this.name = (null != name) ? name : DEFAULT_INTERNAL_FB_NAME;
		this.index = index;
	}

	@Override
	public Object getCreatedElement() {
		return internalFB;
	}

	private EList<FB> getInteralFBList() {
		final BaseFBType type = baseFbType;
		return type.getInternalFbs();
	}

	@Override
	public void execute() {
		internalFB = LibraryElementFactory.eINSTANCE.createFB();
		internalFB.setPaletteEntry(fbType);
		internalFB.setComment(""); //$NON-NLS-1$
		redo();
		internalFB.setName(NameRepository.createUniqueName(internalFB, name));
	}

	@Override
	public void undo() {
		getInteralFBList().remove(internalFB);
	}

	@Override
	public void redo() {
		getInteralFBList().add(index, internalFB);
	}

}
