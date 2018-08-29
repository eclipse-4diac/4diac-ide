/*******************************************************************************
 * Copyright (c) 2014 - 2017 Luka Lednicki, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGeneratorFactory;
import org.eclipse.fordiac.ide.comgeneration.plugin.Activator;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
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

public class CommFBGenerator {
	private static final String GENERATED_ANNOTATION = "generatedComm"; //$NON-NLS-1$
	private static final String GENERATED_SOURCE_FB_ANNOTATION = "generatedSrcFB"; //$NON-NLS-1$
	private static final String GENERATED_DESTINATION_FB_ANNOTATION = "generatedDestFB"; //$NON-NLS-1$
	private static final String GENERATED_FOR_CONNECTION_ANNOTATION = "generatedForConnection"; //$NON-NLS-1$
	private Set<GeneratedFBInfo> generatedFBs;
	private TransferedData transferedData;
	private MediaSpecificGeneratorFactory specificGeneratorFactory;
	private int generatedFBIndex;

	public CommFBGenerator(MediaSpecificGeneratorFactory specificGeneratorFactory) {
		super();	
		this.specificGeneratorFactory = specificGeneratorFactory;
		transferedData = TransferedData.ALL;
	}
	
	public TransferedData getTransferedData() {
		return transferedData;
	}

	public void setTransferedData(TransferedData transferedData) {
		this.transferedData = transferedData;
	}

	public void generate(CommunicationModel communicationModel) {
		generatedFBs = new HashSet<GeneratedFBInfo>();
		generatedFBIndex = 0;		
		for (CommunicationChannel channel : communicationModel.getChannels().values()) {		
			generate(channel);
		}
	}
		
	private void generate(CommunicationChannel channel) {
		for (CommunicationChannelDestination destination : channel.getDestinations()) {
			generateFBs(destination);
		}
	}
		
	private void generateFBs(CommunicationChannelDestination destination) {
		if (destination.getSelectedMedia() == null || destination.getSelectedProtocolId() == null) {
			Activator.getDefault().logError("No media or protocol selected for " + destination);
			return;
		}
		int numberDataPorts = 0;
		Set<Integer> withPorts = null;
		switch (transferedData) {
		case ALL:
			numberDataPorts = destination.getCommunicationChannel().getNumberOfDataPorts();
			HashSet<Integer> allWithPorts = new HashSet<Integer>();
			for (int i = 0; i < destination.getCommunicationChannel().getSourceEvent().getWith().size(); i++) {
				allWithPorts.add(i);
			}
			withPorts = allWithPorts;
			break;
		case EXACT:
			numberDataPorts = destination.getDestinationPorts().keySet().size();
			// if it includes the event, reduce number of dataports
			if (destination.getDestinationPorts().keySet().contains(-1)) {
				numberDataPorts--;
			}
			withPorts = destination.getDestinationPorts().keySet();
			break;
		}
		MediaSpecificGenerator specificGenerator = specificGeneratorFactory.getForProtocolId(destination.getSelectedProtocolId());
		if (specificGenerator == null) {
			Activator.getDefault().logError("No generator for protocol " + destination.getSelectedProtocolId() + "!");
		} else { 
			GeneratedFBInfo sourceGeneratedFBInfo = generateFB(ChannelEnd.SOURCE, numberDataPorts, withPorts, destination, specificGenerator);
			GeneratedFBInfo destinationGeneratedFBInfo = generateFB(ChannelEnd.DESTINATION, numberDataPorts, withPorts, destination, specificGenerator);
			specificGenerator.configureFBs(sourceGeneratedFBInfo.getFb(), destinationGeneratedFBInfo.getFb(), destination.getSelectedMedia());
		}
	}
	
	private GeneratedFBInfo generateFB(ChannelEnd end, int numberOfDataPorts, Set<Integer> withPorts, CommunicationChannelDestination destination, MediaSpecificGenerator specificGenerator) {
		GeneratedFBInfo generatedFBInfo = null;
		if (end == ChannelEnd.SOURCE && !destination.isSeparated() && !specificGenerator.isSeparatedSource()) {
			generatedFBInfo = findExistingGeneratedFBInfo(end, destination.getCommunicationChannel(), withPorts, destination.getSelectedMedia().getSegment());
		}		
		if (generatedFBInfo != null) {
			generatedFBInfo.getDestinations().add(destination);
		} else {
			FBTypePaletteEntry paletteEntry = specificGenerator.getPaletteType(end, numberOfDataPorts, destination.getCommunicationChannel().isLocal());	
			Resource resource = (end == ChannelEnd.SOURCE) ? destination.getCommunicationChannel().getSourceResource() : destination.getDestinationResource();	
			FBCreateCommand command = new FBCreateCommand(paletteEntry, resource.getFBNetwork() , 10, 10);
			command.execute();
			FB generatedFB = command.getFB();
			generatedFB.setName(NameRepository.createUniqueName(generatedFB, "GENERATED_" + generatedFBIndex)); //$NON-NLS-1$
			generatedFBIndex++;
			StringBuilder newFBComment = new StringBuilder();
			newFBComment.append(GENERATED_ANNOTATION);
			newFBComment.append("; ");
			for (Connection connection : destination.getConnection()) {
				newFBComment.append(GENERATED_FOR_CONNECTION_ANNOTATION);
				newFBComment.append("=");
				newFBComment.append("; ");
				StringBuilder newConnectionComment = new StringBuilder();
				if (connection.getComment() != null) {
					newConnectionComment.append(connection.getComment());
				}
				newConnectionComment.append((end == ChannelEnd.SOURCE) ? GENERATED_SOURCE_FB_ANNOTATION : GENERATED_DESTINATION_FB_ANNOTATION);
				newConnectionComment.append("=");
				newConnectionComment.append("; ");
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
	
	private static void generateSourceConnections(GeneratedFBInfo generatedFBInfo, MediaSpecificGenerator specificGenerator) {
		// get channel for any destination, they must all be same...
		CommunicationChannel channel = generatedFBInfo.getDestinations().iterator().next().getCommunicationChannel();		
		EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(generatedFBInfo.getResource().getFBNetwork());
		Event targetEvent = generatedFBInfo.getFb().getInterface().getEventInputs().get(1);
		eventCreateCommand.setDestination(targetEvent);
		eventCreateCommand.setSource(channel.getSourceEvent());
		eventCreateCommand.execute();
		// TODO annotation
		int targetDataIndex = 0;
		for (int i = 0; i < channel.getSourceEvent().getWith().size(); i++) {
			if (generatedFBInfo.getWithPorts() != null && !generatedFBInfo.getWithPorts().contains(i)) {
				continue;
			}
			With with = channel.getSourceEvent().getWith().get(i);
			DataConnectionCreateCommand dataCreateCommand = new DataConnectionCreateCommand(generatedFBInfo.getResource().getFBNetwork());
			VarDeclaration targetData = specificGenerator.getTargetInputData(targetDataIndex, generatedFBInfo.getFb());
			dataCreateCommand.setSource(with.getVariables());
			dataCreateCommand.setDestination(targetData);
			dataCreateCommand.execute();
			// TODO annotation
			targetDataIndex++;
		}
	}
	
	private static void generateDestinationConnections(GeneratedFBInfo generatedFBInfo, MediaSpecificGenerator specificGenerator) {	
		// destinations must have only one fb generated for them...
		CommunicationChannelDestination destination = generatedFBInfo.getDestinations().iterator().next();
		for (Integer withIndex : destination.getDestinationPorts().keySet()) {
			if (withIndex == -1) {
				for (IInterfaceElement interfaceElement : destination.getDestinationPorts().get(withIndex)) {
					EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(generatedFBInfo.getResource().getFBNetwork());
					eventCreateCommand.setDestination(interfaceElement);
					Event sourceEvent = generatedFBInfo.getFb().getInterface().getEventOutputs().get(1);
					eventCreateCommand.setSource(sourceEvent);
					eventCreateCommand.execute();
					// TODO annotation
				}
			} else if (withIndex >= 0) {
				// calculated the index of port on the generated FB adding 1 for each with port used for generation which has smaller index than current with port
				int generatedPortIndex = 0;
				for (Integer generatedWithPort : generatedFBInfo.getWithPorts()) {
					if (generatedWithPort >= 0 && generatedWithPort < withIndex) {
						generatedPortIndex++;
					}
				}
				for (IInterfaceElement interfaceElement : destination.getDestinationPorts().get(withIndex)) {
					DataConnectionCreateCommand dataCreateCommand = new DataConnectionCreateCommand(generatedFBInfo.getResource().getFBNetwork());
					dataCreateCommand.setDestination(interfaceElement);
					VarDeclaration targetData = specificGenerator.getTargetOutputData(generatedPortIndex, generatedFBInfo.getFb());
					dataCreateCommand.setSource(targetData);
					dataCreateCommand.execute();
					// TODO annotation
				}
			}
		}
	}
	
	private void generateInitConnection(GeneratedFBInfo generatedFBInfo) {
		ArrayList<Event> sourceEvents = new ArrayList<Event>();
		Resource resource = generatedFBInfo.getResource();
		Event targetEvent = generatedFBInfo.getFb().getInterface().getEventInputs().get(0);
		for (GeneratedFBInfo existingInfo : generatedFBs) {
			if (resource.equals(existingInfo.getResource()) && !generatedFBInfo.getFb().equals(existingInfo.getFb())) {
				sourceEvents.add(existingInfo.getFb().getInterface().getEventOutputs().get(0));
				break;
			}
		}
		if (sourceEvents.size() == 0) {
			FB startFB = resource.getFBNetwork().getFBNamed("START");
			if (startFB == null) {
				Activator.getDefault().logError("No start FB in resource " + resource.getName() + "!");
				return;
			}
			sourceEvents.add(startFB.getInterface().getEventOutputs().get(0));
			sourceEvents.add(startFB.getInterface().getEventOutputs().get(1));
		}
		for (Event sourceEvent : sourceEvents) {
			EventConnectionCreateCommand eventCreateCommand = new EventConnectionCreateCommand(resource.getFBNetwork());
			eventCreateCommand.setSource(sourceEvent);
			eventCreateCommand.setDestination(targetEvent);
			eventCreateCommand.execute();
			// TODO annotation
		}
	}
		
	private GeneratedFBInfo findExistingGeneratedFBInfo(ChannelEnd end, CommunicationChannel channel, Set<Integer> withPorts, Segment segment) {
		for (GeneratedFBInfo generatedFBInfo : generatedFBs) {
			if ((generatedFBInfo.getEnd() == end) && (generatedFBInfo.getChannel().equals(channel)) && 
					((generatedFBInfo.getWithPorts() == null && withPorts == null) || generatedFBInfo.getWithPorts().equals(withPorts)) &&
					(generatedFBInfo.getSegment().equals(segment))) {
				return generatedFBInfo;					
			}
		}
		return null;
	}
	
	public static enum TransferedData {
		ALL,
		EXACT
	}
	
	private static class GeneratedFBInfo {
		private FB fb;
		private Set<CommunicationChannelDestination> destinations;
		private Set<Integer> withPorts;
		private Segment segment;
		private ChannelEnd end;
		
		public GeneratedFBInfo(ChannelEnd end, Segment segment) {
			super();
			this.end = end;
			destinations = new HashSet<CommunicationChannelDestination>();
			this.segment = segment;
		}
		public FB getFb() {
			return fb;
		}
		public void setFb(FB fb) {
			this.fb = fb;
		}
		public Set<Integer> getWithPorts() {
			return withPorts;
		}
		public void setWithPorts(Set<Integer> withPorts) {
			this.withPorts = withPorts;
		}
		public Set<CommunicationChannelDestination> getDestinations() {
			return destinations;
		}
		public ChannelEnd getEnd() {
			return end;
		}
		public void setEnd(ChannelEnd end) {
			this.end = end;
		}
		public Segment getSegment() {
			return segment;
		}
		public void setSegment(Segment segment) {
			this.segment = segment;
		}
		public Resource getResource() {
			return (end == ChannelEnd.SOURCE) ? 
					destinations.iterator().next().getCommunicationChannel().getSourceResource() 
					: destinations.iterator().next().getDestinationResource();
		}	
		public CommunicationChannel getChannel() {
			return destinations.iterator().next().getCommunicationChannel();
		}
	}
	
	public void removeGeneratedElements(Application application) {
		HashSet<FBNetwork> usedResources = new HashSet<FBNetwork>();
		for (FBNetworkElement fb : application.getFBNetwork().getNetworkElements()) {
			if (fb.getResource() != null) {
				usedResources.add(fb.getResource().getFBNetwork());
			}
		}	
		for (FBNetwork resourceFBNetwork : usedResources) {
			removeGeneratedElements(resourceFBNetwork);
		}
	}
	
	public void removeGeneratedElements(FBNetwork network) {
		HashSet<FBNetworkElement> fbsToRemove = new HashSet<FBNetworkElement>();
		for (FBNetworkElement fb : network.getNetworkElements()) {
			if (fb.getComment() != null && fb.getComment().contains(GENERATED_ANNOTATION)) {
				fbsToRemove.add(fb);
			}
		}
		for (FBNetworkElement fb : fbsToRemove) {
			DeleteFBNetworkElementCommand deleteFBCommand = new DeleteFBNetworkElementCommand(fb);
			deleteFBCommand.execute();
		}
	}
}
