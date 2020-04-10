/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.comgeneration.implementation.ChannelEnd;
import org.eclipse.fordiac.ide.comgeneration.plugin.Activator;
import org.eclipse.fordiac.ide.comgeneration.plugin.Messages;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractMediaSpecificGenerator implements MediaSpecificGenerator {
	private static final String[] PALETTE_ENTRY_SOURCE_LOCAL = { "PUBL_0", "PUBL_1", "PUBL_2", "PUBL_3", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"PUBL_4" }; //$NON-NLS-1$
	private static final String[] PALETTE_ENTRY_DESTINATION_LOCAL = { "SUBL_0", "SUBL_1", "SUBL_2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"net/SUBL_3", "net/SUBL_4" }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String[] PALETTE_ENTRY_SOURCE = { "PUBLISH_0", "PUBLISH_1", "PUBLISH_2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"PUBLISH_3", "PUBLISH_4" }; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String[] PALETTE_ENTRY_DESTINATION = { "SUBSCRIBE_0", "SUBSCRIBE_1", "UBSCRIBE_2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"SUBSCRIBE_3", "SUBSCRIBE_4" }; //$NON-NLS-1$ //$NON-NLS-2$

	private final Palette palette;

	public AbstractMediaSpecificGenerator(Palette palette) {
		super();
		this.palette = palette;
	}

	public Palette getPalette() {
		return palette;
	}

	@Override
	public FBTypePaletteEntry getPaletteType(ChannelEnd end, int numDataPorts, boolean local) {
		String[] paletteEntries;

		if (local) {
			paletteEntries = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE_LOCAL : PALETTE_ENTRY_DESTINATION_LOCAL;
		} else {
			paletteEntries = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE : PALETTE_ENTRY_DESTINATION;
		}

		FBTypePaletteEntry entry = getPalette().getFBTypeEntry(paletteEntries[numDataPorts]);

		if (null == entry) {
			Activator.getDefault().logError(MessageFormat.format(Messages.CommGenerator_FBTypePaletteEntryNotFound,
					paletteEntries[numDataPorts]));
		}
		return entry;
	}

	@Override
	public VarDeclaration getTargetInputData(int index, FB fb) {
		String dataName = "SD_" + (index + 1); //$NON-NLS-1$
		for (VarDeclaration var : fb.getInterface().getInputVars()) {
			if (var.getName().equals(dataName)) {
				return var;
			}
		}
		return null;
	}

	@Override
	public VarDeclaration getTargetOutputData(int index, FB fb) {
		String dataName = "RD_" + (index + 1); //$NON-NLS-1$
		for (VarDeclaration var : fb.getInterface().getOutputVars()) {
			if (var.getName().equals(dataName)) {
				return var;
			}
		}
		return null;
	}

}
