/*******************************************************************************
 * Copyright (c) 2014 - 2017 Luka Lednicki, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGeneratorFactory;
import org.eclipse.fordiac.ide.comgeneration.plugin.Messages;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class CommFBGenerator {
	private static final String GENERATED_ANNOTATION = "generatedComm"; //$NON-NLS-1$
	private static final String GENERATED_SOURCE_FB_ANNOTATION = "generatedSrcFB"; //$NON-NLS-1$
	private static final String GENERATED_DESTINATION_FB_ANNOTATION = "generatedDestFB"; //$NON-NLS-1$
	private static final String GENERATED_FOR_CONNECTION_ANNOTATION = "generatedForConnection"; //$NON-NLS-1$
	private Set<GeneratedFBInfo> generatedFBs;
	private TransferedData transferedData;
	private final MediaSpecificGeneratorFactory specificGeneratorFactory;
	private int generatedFBIndex;

	public CommFBGenerator(final MediaSpecificGeneratorFactory specificGeneratorFactory) {
		super();
		this.specificGeneratorFactory = specificGeneratorFactory;
		transferedData = TransferedData.ALL;
	}

	public TransferedData getTransferedData() {
		return transferedData;
	}

	public void setTransferedData(final TransferedData transferedData) {
		this.transferedData = transferedData;
	}

	public void generate(final CommunicationModel communicationModel) {
		generatedFBs = new HashSet<>();
		generatedFBIndex = 0;
		for (final CommunicationChannel channel : communicationModel.getChannels().values()) {
			generate(channel);
		}
	}

	private void generate(final CommunicationChannel channel) {
		for (final CommunicationChannelDestination destination : channel.getDestinations()) {
			generateFBs(destination);
		}
	}

	private void generateFBs(final CommunicationChannelDestination destination) {
		if (destination.getSelectedMedia() == null || destination.getSelectedProtocolId() == null) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.CommFBGenerator_NoSelectionFor, destination));
			return;
		}
		int numberDataPorts = 0;
		Set<Integer> withPorts = null;
		switch (transferedData) {
		case ALL:
			numberDataPorts = destination.getCommunicationChannel().getNumberOfDataPorts();
			final HashSet<Integer> allWithPorts = new HashSet<>();
			for (int i = 0; i < destination.getCommunicationChannel().getSourceEvent().getWith().size(); i++) {
				allWithPorts.add(Integer.valueOf(i));
			}
			withPorts = allWithPorts;
			break;
		case EXACT:
			numberDataPorts = destination.getDestinationPorts().keySet().size();
			// if it includes the event, reduce number of dataports
			if (destination.getDestinationPorts().keySet().contains(Integer.valueOf(-1))) {
				numberDataPorts--;
			}
			withPorts = destination.getDestinationPorts().keySet();
			break;
		default:
		}
		final MediaSpecificGenerator specificGenerator = specificGeneratorFactory
				.getForProtocolId(destination.getSelectedProtocolId());
		if (specificGenerator == null) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.CommFBGenerator_NoGeneratorForProtocol,
					destination.getSelectedProtocolId()));
		} else {
			final GeneratedFBInfo sourceGeneratedFBInfo = generateFB(ChannelEnd.SOURCE, numberDataPorts, withPorts,
					destination, specificGenerator);
			final GeneratedFBInfo destinationGeneratedFBInfo = generateFB(ChannelEnd.DESTINATION, numberDataPorts, withPorts,
					destination, specificGenerator);
			specificGenerator.configureFBs(sourceGeneratedFBInfo.getFb(), destinationGeneratedFBInfo.getFb(),
					destination.getSelectedMedia());
		}
	}

	private GeneratedFBInfo generateFB(final ChannelEnd end, final int numberOfDataPorts, final Set<Integer> withPorts,
			final CommunicationChannelDestination destination, final MediaSpecificGenerator specificGenerator) {
		GeneratedFBInfo generatedFBInfo = null;
		if (end == ChannelEnd.SOURCE && !destination.isSeparated() && !specificGenerator.isSeparatedSource()) {
			generatedFBInfo = findExistingGeneratedFBInfo(end, destination.getCommunicationChannel(), withPorts,
					destination.getSelectedMedia().getSegment());
		}
		if (generatedFBInfo != null) {
			generatedFBInfo.getDestinations().add(destination);
		} else {
			final FBTypeEntry paletteEntry = specificGenerator.getPaletteType(end, numberOfDataPorts,
					destination.getCommunicationChannel().isLocal());
			final Resource resource = (end == ChannelEnd.SOURCE) ? destination.getCommunicationChannel().getSourceResource()
					: destination.getDestinationResource();
			final FBCreateCommand command = new FBCreateCommand(paletteEntry, resource.getFBNetwork(), 10, 10);
			command.execute();
			final FB generatedFB = command.getFB();
			generatedFB.setName(NameRepository.createUniqueName(generatedFB, "GENERATED_" + generatedFBIndex)); //$NON-NLS-1$
			generatedFBIndex++;
			final StringBuilder newFBComment = new StringBuilder();
			newFBComment.append(GENERATED_ANNOTATION);
			newFBComment.append("; "); //$NON-NLS-1$
			for (final Connection connection : destination.getConnection()) {
				newFBComment.append(GENERATED_FOR_CONNECTION_ANNOTATION);
				newFBComment.append("="); //$NON-NLS-1$
				newFBComment.append("; "); //$NON-NLS-1$
				final StringBuilder newConnectionComment = new StringBuilder();
				if (connection.getComment() != null) {
					newConnectionComment.append(connection.getComment());
				}
				newConnectionComment.append((end == ChannelEnd.SOURCE) ? GENERATED_SOURCE_FB_ANNOTATION
						: GENERATED_DESTINATION_FB_ANNOTATION);
				newConnectionComment.append("="); //$NON-NLS-1$
				newConnectionComment.append("; "); //$NON-NLS-1$
				connection.setComment(newConnectionComment.toString());
			}
			generatedFB.setComment(newFBComment.toString());
			// TODO annotation
			generatedFBInfo = new GeneratedFBInfo(end, destination.getSelectedMedia().getSegment());
			generatedFBInfo.getDestinations().add(destination);
			generatedFBInfo.setFb(generatedFB);
			generatedFBInfo.setWithPorts(withPorts);
			generatedFBs.add(generatedFBInfo);
			if (end == ChannelEnd.SOURCE) {
				generateSourceConnections(generatedFBInfo, specificGenerator);
			} else {
				generateDestinationConnections(generatedFBInfo, specificGenerator);
			}
			generateInitConnection(generatedFBInfo);
		}
		return generatedFBInfo;
	}

	private static void generateSourceConnections(final GeneratedFBInfo generatedFBInfo,
			final MediaSpecificGenerator specificGenerator) {
		// get channel for any destination, they must all be same...
		final CommunicationChannel channel = generatedFBInfo.getDestinations().iterator().next().getCommunicationChannel();
		final EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(
				generatedFBInfo.getResource().getFBNetwork());
		final Event targetEvent = generatedFBInfo.getFb().getInterface().getEventInputs().get(1);
		eventCreateCommand.setDestination(targetEvent);
		eventCreateCommand.setSource(channel.getSourceEvent());
		eventCreateCommand.execute();
		// TODO annotation
		int targetDataIndex = 0;
		for (int i = 0; i < channel.getSourceEvent().getWith().size(); i++) {
			if (generatedFBInfo.getWithPorts() != null
					&& !generatedFBInfo.getWithPorts().contains(Integer.valueOf(i))) {
				continue;
			}
			final With with = channel.getSourceEvent().getWith().get(i);
			final DataConnectionCreateCommand dataCreateCommand = new DataConnectionCreateCommand(
					generatedFBInfo.getResource().getFBNetwork());
			final VarDeclaration targetData = specificGenerator.getTargetInputData(targetDataIndex, generatedFBInfo.getFb());
			dataCreateCommand.setSource(with.getVariables());
			dataCreateCommand.setDestination(targetData);
			dataCreateCommand.execute();
			// TODO annotation
			targetDataIndex++;
		}
	}

	private static void generateDestinationConnections(final GeneratedFBInfo generatedFBInfo,
			final MediaSpecificGenerator specificGenerator) {
		// destinations must have only one fb generated for them...
		final CommunicationChannelDestination destination = generatedFBInfo.getDestinations().iterator().next();
		for (final Integer withIndex : destination.getDestinationPorts().keySet()) {
			if (withIndex.intValue() == -1) {
				for (final IInterfaceElement interfaceElement : destination.getDestinationPorts().get(withIndex)) {
					final EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(
							generatedFBInfo.getResource().getFBNetwork());
					eventCreateCommand.setDestination(interfaceElement);
					final Event sourceEvent = generatedFBInfo.getFb().getInterface().getEventOutputs().get(1);
					eventCreateCommand.setSource(sourceEvent);
					eventCreateCommand.execute();
					// TODO annotation
				}
			} else if (withIndex.intValue() >= 0) {
				// calculated the index of port on the generated FB adding 1 for each with port
				// used for generation which has smaller index than current with port
				int generatedPortIndex = 0;
				for (final Integer generatedWithPort : generatedFBInfo.getWithPorts()) {
					if (generatedWithPort.intValue() >= 0 && generatedWithPort.intValue() < withIndex.intValue()) {
						generatedPortIndex++;
					}
				}
				for (final IInterfaceElement interfaceElement : destination.getDestinationPorts().get(withIndex)) {
					final DataConnectionCreateCommand dataCreateCommand = new DataConnectionCreateCommand(
							generatedFBInfo.getResource().getFBNetwork());
					dataCreateCommand.setDestination(interfaceElement);
					final VarDeclaration targetData = specificGenerator.getTargetOutputData(generatedPortIndex,
							generatedFBInfo.getFb());
					dataCreateCommand.setSource(targetData);
					dataCreateCommand.execute();
					// TODO annotation
				}
			}
		}
	}

	private void generateInitConnection(final GeneratedFBInfo generatedFBInfo) {
		final ArrayList<Event> sourceEvents = new ArrayList<>();
		final Resource resource = generatedFBInfo.getResource();
		final Event targetEvent = generatedFBInfo.getFb().getInterface().getEventInputs().get(0);
		for (final GeneratedFBInfo existingInfo : generatedFBs) {
			if (resource.equals(existingInfo.getResource()) && !generatedFBInfo.getFb().equals(existingInfo.getFb())) {
				sourceEvents.add(existingInfo.getFb().getInterface().getEventOutputs().get(0));
				break;
			}
		}
		if (sourceEvents.isEmpty()) {
			final FB startFB = resource.getFBNetwork().getFBNamed("START"); //$NON-NLS-1$
			if (startFB == null) {
				FordiacLogHelper.logError(
						MessageFormat.format(Messages.CommFBGenerator_NoStartFBInResource, resource.getName()));
				return;
			}
			sourceEvents.add(startFB.getInterface().getEventOutputs().get(0));
			sourceEvents.add(startFB.getInterface().getEventOutputs().get(1));
		}
		for (final Event sourceEvent : sourceEvents) {
			final EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(resource.getFBNetwork());
			eventCreateCommand.setSource(sourceEvent);
			eventCreateCommand.setDestination(targetEvent);
			eventCreateCommand.execute();
			// TODO annotation
		}
	}

	private GeneratedFBInfo findExistingGeneratedFBInfo(final ChannelEnd end, final CommunicationChannel channel,
			final Set<Integer> withPorts, final Segment segment) {
		for (final GeneratedFBInfo generatedFBInfo : generatedFBs) {
			if ((generatedFBInfo.getEnd() == end) && (generatedFBInfo.getChannel().equals(channel))
					&& ((generatedFBInfo.getWithPorts() == null && withPorts == null)
							|| generatedFBInfo.getWithPorts().equals(withPorts))
					&& (generatedFBInfo.getSegment().equals(segment))) {
				return generatedFBInfo;
			}
		}
		return null;
	}

	public enum TransferedData {
		ALL, EXACT
	}

	private static class GeneratedFBInfo {
		private FB fb;
		private final Set<CommunicationChannelDestination> destinations;
		private Set<Integer> withPorts;
		private Segment segment;
		private ChannelEnd end;

		public GeneratedFBInfo(final ChannelEnd end, final Segment segment) {
			super();
			this.end = end;
			destinations = new HashSet<>();
			this.segment = segment;
		}

		public FB getFb() {
			return fb;
		}

		public void setFb(final FB fb) {
			this.fb = fb;
		}

		public Set<Integer> getWithPorts() {
			return withPorts;
		}

		public void setWithPorts(final Set<Integer> withPorts) {
			this.withPorts = withPorts;
		}

		public Set<CommunicationChannelDestination> getDestinations() {
			return destinations;
		}

		public ChannelEnd getEnd() {
			return end;
		}

		public void setEnd(final ChannelEnd end) {
			this.end = end;
		}

		public Segment getSegment() {
			return segment;
		}

		public void setSegment(final Segment segment) {
			this.segment = segment;
		}

		public Resource getResource() {
			return (end == ChannelEnd.SOURCE)
					? destinations.iterator().next().getCommunicationChannel().getSourceResource()
							: destinations.iterator().next().getDestinationResource();
		}

		public CommunicationChannel getChannel() {
			return destinations.iterator().next().getCommunicationChannel();
		}
	}

	public static void removeGeneratedElements(final Application application) {
		final HashSet<FBNetwork> usedResources = new HashSet<>();
		for (final FBNetworkElement fb : application.getFBNetwork().getNetworkElements()) {
			if (fb.getResource() != null) {
				usedResources.add(fb.getResource().getFBNetwork());
			}
		}
		for (final FBNetwork resourceFBNetwork : usedResources) {
			removeGeneratedElements(resourceFBNetwork);
		}
	}

	public static void removeGeneratedElements(final FBNetwork network) {
		final HashSet<FBNetworkElement> fbsToRemove = new HashSet<>();
		for (final FBNetworkElement fb : network.getNetworkElements()) {
			if (fb.getComment() != null && fb.getComment().contains(GENERATED_ANNOTATION)) {
				fbsToRemove.add(fb);
			}
		}
		for (final FBNetworkElement fb : fbsToRemove) {
			final DeleteFBNetworkElementCommand deleteFBCommand = new DeleteFBNetworkElementCommand(fb);
			deleteFBCommand.execute();
		}
	}
}
