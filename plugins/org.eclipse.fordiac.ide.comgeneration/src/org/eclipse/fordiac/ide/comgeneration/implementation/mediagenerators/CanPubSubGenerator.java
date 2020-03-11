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

import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationMediaInfo;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class CanPubSubGenerator extends AbstractMediaSpecificGenerator {

	public static final String PROTOCOL_ID = "CanPubSub"; //$NON-NLS-1$

	private static final int DEFAULT_START_MESSAGE_ID = 500;

	private int startMessageId;
	private int currentMessageId;

	public CanPubSubGenerator(Palette palette) {
		super(palette);
		startMessageId = DEFAULT_START_MESSAGE_ID;
		reset();
	}

	@Override
	public String getMediaType() {
		return "CAN"; //$NON-NLS-1$
	}

	@Override
	public String getProtocolId() {
		return PROTOCOL_ID;
	}

	@Override
	public void configureFBs(FB sourceFB, FB destinationFB, CommunicationMediaInfo mediaInfo) {
		VarDeclaration sourceQI;
		VarDeclaration sourceId;
		VarDeclaration destinationQI;
		VarDeclaration destinationId;

		if (mediaInfo.getSourceLink().getDevice().equals(mediaInfo.getDestinationLink().getDevice())) {
			sourceQI = null;
			sourceId = sourceFB.getInterface().getInputVars().get(0);
			destinationQI = null;
			destinationId = destinationFB.getInterface().getInputVars().get(0);
		} else {
			sourceQI = sourceFB.getInterface().getInputVars().get(0);
			sourceId = sourceFB.getInterface().getInputVars().get(1);
			destinationQI = destinationFB.getInterface().getInputVars().get(0);
			destinationId = destinationFB.getInterface().getInputVars().get(1);
		}

		String sourceValue = sourceId.getValue().getValue();
		if (sourceValue == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("CAN:"); //$NON-NLS-1$
			sb.append(mediaInfo.getSegment().getName());
			sb.append(":"); //$NON-NLS-1$
			sb.append(currentMessageId);
			currentMessageId++;
			sourceValue = sb.toString();
			sourceId.getValue().setValue(sourceValue);
			if (sourceQI != null) {
				sourceQI.getValue().setValue("1"); //$NON-NLS-1$
			}
		}

		destinationId.getValue().setValue(sourceValue);
		if (destinationQI != null) {
			destinationQI.getValue().setValue("1"); //$NON-NLS-1$
		}
	}

	@Override
	public void reset() {
		currentMessageId = startMessageId;
	}

	public void reset(int startMessageId) {
		this.startMessageId = startMessageId;
		reset();
	}

	@Override
	public boolean isSeparatedSource() {
		return false;
	}
}
