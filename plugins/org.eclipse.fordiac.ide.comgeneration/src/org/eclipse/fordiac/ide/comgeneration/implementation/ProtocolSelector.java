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
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.CanPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.EthernetPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.plugin.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class ProtocolSelector {

	private static final String CAN = "Can"; //$NON-NLS-1$
	private static final String ETHERNET = "Ethernet"; //$NON-NLS-1$

	public static void doAutomatedProtocolSelection(final CommunicationModel model) {
		for (final CommunicationChannel channel : model.getChannels().values()) {
			final List<Segment> commonSegments = new ArrayList<>();
			Iterator<CommunicationChannelDestination> destinationIterator = channel.getDestinations().iterator();

			CommunicationChannelDestination destination = destinationIterator.next();
			for (final CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
				commonSegments.add(mediaInfo.getSegment());
			}

			while (destinationIterator.hasNext()) {
				destination = destinationIterator.next();
				final Iterator<Segment> segmentIterator = commonSegments.iterator();
				while (segmentIterator.hasNext()) {
					final Segment segment = segmentIterator.next();
					boolean containsSegment = false;
					for (final CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
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
					final ArrayList<Segment> availableSegments = new ArrayList<>();
					for (final CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
						availableSegments.add(mediaInfo.getSegment());
					}
					sortSegments(availableSegments);
					if (!availableSegments.isEmpty()) {
						selectedSegment = availableSegments.get(0);
					}
				}

				if (selectedSegment != null) {
					for (final CommunicationMediaInfo mediaInfo : destination.getAvailableMedia()) {
						if (mediaInfo.getSegment() == selectedSegment) {
							destination.setSelectedMedia(mediaInfo);
							destination.setSelectedProtocolId(getProtocolIdForMetiaType(mediaInfo.getSegment()));
							break;
						}
					}
				} else {
					FordiacLogHelper.logError(Messages.ProtocolSelector_NoConnectionAvailable);
				}

			}
		}
	}

	private static String getProtocolIdForMetiaType(final Segment segment) {
		if (segment.getType().getName().equalsIgnoreCase(ETHERNET)) {
			return EthernetPubSubGenerator.PROTOCOL_ID;
		} else if (segment.getType().getName().equalsIgnoreCase(CAN)) {
			return CanPubSubGenerator.PROTOCOL_ID;
		}
		return null;
	}

	private static void sortSegments(final List<Segment> segmentList) {
		Collections.sort(segmentList, (final Segment o1, final Segment o2) -> {
			final String name1 = o1.getType().getName();
			final String name2 = o2.getType().getName();

			if (name1.equalsIgnoreCase(CAN)) {
				if (name2.equalsIgnoreCase(CAN)) {
					return 0;
				}
				return -1;
			} else if (name1.equalsIgnoreCase(ETHERNET)) {
				if (name2.equalsIgnoreCase(CAN)) {
					return 1;
				} else if (name2.equalsIgnoreCase(ETHERNET)) {
					return 0;
				}
				return -1;
			}
			return 0;
		});
	}

	private ProtocolSelector() {
		throw new UnsupportedOperationException("ProtocolSelector utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
