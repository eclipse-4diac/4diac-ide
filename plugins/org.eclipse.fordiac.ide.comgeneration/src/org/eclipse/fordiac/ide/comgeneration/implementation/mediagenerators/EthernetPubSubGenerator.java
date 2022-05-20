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
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class EthernetPubSubGenerator extends AbstractMediaSpecificGenerator {
	public static final String PROTOCOL_ID = "EthernetPubSub"; //$NON-NLS-1$

	private static final String DEFAULT_HOST = "225.0.0.1"; //$NON-NLS-1$
	private static final int DEFAULT_START_PORT = 61550;

	private final String host;
	private int startPort;
	private int currentPort;

	public EthernetPubSubGenerator(final TypeLibrary typeLib) {
		super(typeLib);
		startPort = DEFAULT_START_PORT;
		host = DEFAULT_HOST;
		reset();
	}

	@Override
	public String getMediaType() {
		return "Ethernet"; //$NON-NLS-1$
	}

	@Override
	public String getProtocolId() {
		return PROTOCOL_ID;
	}

	@Override
	public void configureFBs(final FB sourceFB, final FB destinationFB, final CommunicationMediaInfo mediaInfo) {

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
		if (sourceValue.isEmpty()) {
			final StringBuilder sb = new StringBuilder();
			sb.append(host);
			sb.append(":"); //$NON-NLS-1$
			sb.append(currentPort);
			currentPort++;
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
		currentPort = startPort;
	}

	public void reset(final int startPort) {
		this.startPort = startPort;
		reset();
	}

	@Override
	public boolean isSeparatedSource() {
		return false;
	}

}
