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
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.CanPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.EthernetPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.plugin.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

public final class ProtocolSelector {
	
	public static void doAutomatedProtocolSelection(CommunicationModel model) {
		for (CommunicationChannel channel : model.getChannels().values()) {
			List<Segment> commonSegments = new ArrayList<>();
			Iterator<CommunicationChannelDestination> destinationIterator = channel.getDestinations().iterator();
			
			CommunicationChannelDestination destination = destinationIterator.next();
			for (CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
				commonSegments.add(mediaInfo.getSegment());
			}
			
			
			while (destinationIterator.hasNext()) {
				destination = destinationIterator.next();
				Iterator<Segment> segmentIterator = commonSegments.iterator();
				while (segmentIterator.hasNext()) {
					Segment segment = segmentIterator.next();
					boolean containsSegment = false;
					for (CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
						if (mediaInfo.getSegment() == segment) {
							containsSegment = true;
						}
					}
					if (!containsSegment) {
						segmentIterator.remove();
					}
				}
			}
			
			Segment selectedCommonSegment = null;
			if (!commonSegments.isEmpty()) {
				sortSegments(commonSegments);
				selectedCommonSegment = commonSegments.get(0);
			}
			
			destinationIterator = channel.getDestinations().iterator();
			while (destinationIterator.hasNext()) {
				destination = destinationIterator.next();
				
				Segment selectedSegment = selectedCommonSegment;
				
				if (selectedSegment == null) {
					ArrayList<Segment> availableSegments = new ArrayList<Segment>();
					for (CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
						availableSegments.add(mediaInfo.getSegment());
					}
					sortSegments(availableSegments);
					if (availableSegments.size() > 0) {
						selectedSegment = availableSegments.get(0);
					}
				}
				
				if (selectedSegment != null) {
					for (CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
						if (mediaInfo.getSegment() == selectedSegment) {
							destination.setSelectedMedia(mediaInfo);
							destination.setSelectedProtocolId(getProtocolIdForMetiaType(mediaInfo.getSegment()));
							break;
						}
					}
				} else {
					Activator.getDefault().logError("No connection available for ");
				}
				
			}
		}
	}
	
	private static String getProtocolIdForMetiaType(Segment segment) {
		if (segment.getType().getName().equalsIgnoreCase("Ethernet")) { //$NON-NLS-1$
			return EthernetPubSubGenerator.PROTOCOL_ID;
		} else if (segment.getType().getName().equalsIgnoreCase("Can")) { //$NON-NLS-1$
			return CanPubSubGenerator.PROTOCOL_ID;
		}
		return null;
	}
	
	
	private static void sortSegments(List<Segment> segmentList) {
		Collections.sort(segmentList, (Segment o1, Segment o2) -> {
				String name1 = o1.getType().getName();
				String name2 = o2.getType().getName();
				
				if (name1.equalsIgnoreCase("Can")) { //$NON-NLS-1$
					if (name2.equalsIgnoreCase("Can")) { //$NON-NLS-1$
						return 0;
					} 
					return -1;
				} else if (name1.equalsIgnoreCase("Ethernet")) { //$NON-NLS-1$
					if (name2.equalsIgnoreCase("Can")) { //$NON-NLS-1$
						return 1;
					} else if (name2.equalsIgnoreCase("Ethernet")) { //$NON-NLS-1$
						return 0;
					} 
					return -1;
				}
				return 0;
			});
	}
	
	private ProtocolSelector() {
		throw new UnsupportedOperationException("ProtocolSelector utilioty class should not be instantiated!");
	}
}
