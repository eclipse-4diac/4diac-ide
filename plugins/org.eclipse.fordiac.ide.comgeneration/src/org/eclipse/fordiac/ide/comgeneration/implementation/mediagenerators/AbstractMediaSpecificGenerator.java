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
import org.eclipse.fordiac.ide.comgeneration.plugin.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractMediaSpecificGenerator implements MediaSpecificGenerator {
	private static final String PALETTE_ENTRY_SOURCE_LOCAL = "iec61499::net::PUBL_"; //$NON-NLS-1$
	private static final String PALETTE_ENTRY_DESTINATION_LOCAL = "iec61499::net::SUBL_"; //$NON-NLS-1$
	private static final String PALETTE_ENTRY_SOURCE = "iec61499::net::PUBLISH_"; //$NON-NLS-1$
	private static final String PALETTE_ENTRY_DESTINATION = "iec61499::net::SUBSCRIBE_"; //$NON-NLS-1$

	private final TypeLibrary typeLib;

	protected AbstractMediaSpecificGenerator(final TypeLibrary typeLib) {
		this.typeLib = typeLib;
	}

	public TypeLibrary getTypeLibrary() {
		return typeLib;
	}

	@Override
	public FBTypeEntry getPaletteType(final ChannelEnd end, final int numDataPorts, final boolean local) {
		String commTypeName;

		if (local) {
			commTypeName = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE_LOCAL : PALETTE_ENTRY_DESTINATION_LOCAL;
		} else {
			commTypeName = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE : PALETTE_ENTRY_DESTINATION;
		}

		commTypeName += Integer.toString(numDataPorts);

		final FBTypeEntry entry = getTypeLibrary().getFBTypeEntry(commTypeName);

		if (entry == null) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.CommGenerator_FBTypeEntryNotFound, commTypeName));
		}
		return entry;
	}

	@Override
	public VarDeclaration getTargetInputData(final int index, final FB fb) {
		final String dataName = "SD_" + (index + 1); //$NON-NLS-1$
		for (final VarDeclaration inputVar : fb.getInterface().getInputVars()) {
			if (inputVar.getName().equals(dataName)) {
				return inputVar;
			}
		}
		return null;
	}

	@Override
	public VarDeclaration getTargetOutputData(final int index, final FB fb) {
		final String dataName = "RD_" + (index + 1); //$NON-NLS-1$
		for (final VarDeclaration outputVar : fb.getInterface().getOutputVars()) {
			if (outputVar.getName().equals(dataName)) {
				return outputVar;
			}
		}
		return null;
	}

}
