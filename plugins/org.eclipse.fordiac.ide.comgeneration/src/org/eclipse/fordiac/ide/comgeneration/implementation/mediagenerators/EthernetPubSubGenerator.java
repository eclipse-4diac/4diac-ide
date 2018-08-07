/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators;

import org.eclipse.fordiac.ide.comgeneration.implementation.ChannelEnd;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationMediaInfo;
import org.eclipse.fordiac.ide.comgeneration.plugin.Activator;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class EthernetPubSubGenerator extends AbstractMediaSpecificGenerator {
	private static final String[] PALETTE_ENTRY_SOURCE_LOCAL = {"net/PUBL_0", "net/PUBL_1", "net/PUBL_2", "net/PUBL_3", "net/PUBL_4"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private static final String[] PALETTE_ENTRY_DESTINATION_LOCAL = {"net/SUBL_0", "net/SUBL_1", "net/SUBL_2", "net/SUBL_3", "net/SUBL_4"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private static final String[] PALETTE_ENTRY_SOURCE = {"net/PUBLISH_0", "net/PUBLISH_1", "net/PUBLISH_2", "net/PUBLISH_3", "net/PUBLISH_4"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	private static final String[] PALETTE_ENTRY_DESTINATION = {"net/SUBSCRIBE_0", "net/SUBSCRIBE_1", "net/SUBSCRIBE_2", "net/SUBSCRIBE_3", "net/SUBSCRIBE_4"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	public static final String PROTOCOL_ID = "EthernetPubSub"; //$NON-NLS-1$
	
	private static final String DEFAULT_HOST = "225.0.0.1"; //$NON-NLS-1$
	private static final int DEFAULT_START_PORT = 61550;
	
	
	private String host;
	private int startPort; 
	private int currentPort;
	
	public EthernetPubSubGenerator(Palette palette) {
		super(palette);
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
	public FBTypePaletteEntry getPaletteType(ChannelEnd end, int numDataPorts, boolean local) {
		String[] palletEntries;
		
		if (local) {
			palletEntries = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE_LOCAL : PALETTE_ENTRY_DESTINATION_LOCAL;
		} else {
			palletEntries = (end == ChannelEnd.SOURCE) ? PALETTE_ENTRY_SOURCE : PALETTE_ENTRY_DESTINATION;
		}
		
		String[] paletteEntryPath = palletEntries[numDataPorts].split("/"); //$NON-NLS-1$
		
		PaletteGroup group = palette.getRootGroup();
		
		for (int i = 0; i < paletteEntryPath.length; i++) {
			String currentPath = paletteEntryPath[i];
			if (i == paletteEntryPath.length - 1) {
				for (PaletteEntry entry : group.getEntries()) {
					if (entry instanceof FBTypePaletteEntry) {
						FBTypePaletteEntry fbTypeEntry = (FBTypePaletteEntry) entry;
						if (fbTypeEntry.getLabel().equals(currentPath)) {
							return fbTypeEntry;
						}
					}
				}
				Activator.getDefault().logError("FB type palette entry '" + currentPath + "' not found!");
				
			} else {
				boolean foundSubGroup = false;
				for (PaletteGroup subGroup : group.getSubGroups()) {
					if (subGroup.getLabel().equals(currentPath)) {
						group = subGroup;
						foundSubGroup = true;
						break;
					}
				}
				if (!foundSubGroup) {
					Activator.getDefault().logError("No subgroup '" + currentPath + "'!");
					return null;
				} 
			}
			
		}
		
		return null;
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

	@Override
	public void reset() {
		currentPort = startPort;
	}
	
	public void reset(int startPort) {
		this.startPort = startPort;
		reset();
	}

	@Override
	public boolean isSeparatedSource() {
		return false;
	}
	
	
	
	
	
}
