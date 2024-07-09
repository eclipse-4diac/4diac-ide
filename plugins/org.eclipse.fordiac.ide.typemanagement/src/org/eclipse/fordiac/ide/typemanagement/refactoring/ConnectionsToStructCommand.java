package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ConnectionsToStructCommand extends Command {
	private final FBType sourceType;
	private final FBType destinationType;
	private final DataType structType;
	private final String sourceVarName;
	private final String destinationVarName;
	private final Map<String, String> replacableConMap;
	private final boolean conflictResolution;

	Map<FBNetworkElement, FBNetworkElement> structConnectionMap;
	Map<FBNetworkElement, FBNetworkElement> demuxMap;
	Map<FBNetworkElement, FBNetworkElement> muxMap;

	private CompoundCommand editFBsCommand;
	private CompoundCommand createWithsCommand;
	// save
	private CompoundCommand updateCommands;
	private CompoundCommand muxCreateCommand;
	private CompoundCommand destinationConnectionEditCommand;
	private CompoundCommand sourceConnectionEditCommand;

//	public ConnectionsToStructCommand(List<Connection> connections) {
//		this(connections.get(0).getSourceElement().getType(),
//				connections.get(0).getDestinationElement().getType(),
//				null,
//				connections.stream().collect(Collectors.toMap(con -> con.getSource().getName(), con -> con.getDestination().getName())));
//	}

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap) {
		this(sourceType, destinationType, structType, sourceVarName, destinationVarName, conMap, true);
	}

	public ConnectionsToStructCommand(final FBType sourceType, final FBType destinationType, final DataType structType,
			final String sourceVarName, final String destinationVarName, final Map<String, String> conMap,
			final boolean conflictResolution) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.replacableConMap = conMap;
		this.structType = structType;
		this.sourceVarName = sourceVarName;
		this.destinationVarName = destinationVarName;
		this.conflictResolution = conflictResolution;
	}

	@Override
	public void undo() {
		// TODO: illegal?
		sourceType.getTypeEntry().setType(sourceType);
		destinationType.getTypeEntry().setType(destinationType);

		sourceConnectionEditCommand.undo();
		destinationConnectionEditCommand.undo();
		muxCreateCommand.undo();
		updateCommands.undo();
		createWithsCommand.undo();
		editFBsCommand.undo();
		AbstractLiveSearchContext.save(sourceType.getTypeEntry());
		AbstractLiveSearchContext.save(destinationType.getTypeEntry());
	}

	@Override
	public void redo() {
		editFBsCommand.redo();
		createWithsCommand.redo();
		AbstractLiveSearchContext.save(sourceType.getTypeEntry());
		AbstractLiveSearchContext.save(destinationType.getTypeEntry());
		updateCommands.redo();
		muxCreateCommand.redo();
		destinationConnectionEditCommand.redo();
		sourceConnectionEditCommand.redo();
	}

	@Override
	public boolean canExecute() {
		return super.canExecute();
	}

	@Override
	public void execute() {
		editFBTypes();
		updateFBs();
//		if (conflictResolution) {
		createMUX();
//		}
		reconnectCon();
	}

	private boolean editFBTypes() {
		editFBsCommand = new CompoundCommand();
		createWithsCommand = new CompoundCommand();

		final List<String> sourceWiths = sourceType.getInterfaceList().getOutputVars().stream()
				.filter(vardec -> replacableConMap.containsKey(vardec.getName())).map(VarDeclaration::getWiths)
				.flatMap(List::stream).map(width -> ((Event) width.eContainer()).getName()).distinct().toList();
		final List<String> destinationWiths = destinationType.getInterfaceList().getInputVars().stream()
				.filter(vardec -> replacableConMap.containsValue(vardec.getName())).map(VarDeclaration::getWiths)
				.flatMap(List::stream).map(width -> ((Event) width.eContainer()).getName()).distinct().toList();

		// Edit FBTypes
		replacableConMap.keySet().forEach(sourceVar -> editFBsCommand
				.add(new DeleteInterfaceCommand(sourceType.getInterfaceList().getOutput(sourceVar))));
		replacableConMap.values().forEach(destinationVar -> editFBsCommand
				.add(new DeleteInterfaceCommand(destinationType.getInterfaceList().getInput(destinationVar))));

		final CreateInterfaceElementCommand sourceStruct = new CreateInterfaceElementCommand(structType, sourceVarName,
				sourceType.getInterfaceList(), false, (int) sourceType.getInterfaceList().getOutputs().count());
		final CreateInterfaceElementCommand destinationStruct = new CreateInterfaceElementCommand(structType,
				destinationVarName, destinationType.getInterfaceList(), true,
				(int) destinationType.getInterfaceList().getInputs().count());
		editFBsCommand.add(sourceStruct);
		editFBsCommand.add(destinationStruct);

		editFBsCommand.execute();

		sourceWiths.forEach(
				width -> createWithsCommand.add(new WithCreateCommand(sourceType.getInterfaceList().getEvent(width),
						(VarDeclaration) sourceStruct.getCreatedElement())));
		destinationWiths.forEach(width -> createWithsCommand
				.add(new WithCreateCommand(destinationType.getInterfaceList().getEvent(width),
						(VarDeclaration) destinationStruct.getCreatedElement())));
		createWithsCommand.execute();

		AbstractLiveSearchContext.save(sourceType.getTypeEntry());
//		sourceType = (FBType)sourceType.getTypeEntry().getType();
		AbstractLiveSearchContext.save(destinationType.getTypeEntry());
//		destinationType = (FBType)destinationType.getTypeEntry().getType();
		return true;
	}

	private boolean updateFBs() {
		updateCommands = new CompoundCommand();
		getElementsOfType(sourceType).stream().forEach(fbnelem -> updateCommands.add(new UpdateFBTypeCommand(fbnelem)));
		getElementsOfType(destinationType).stream()
				.forEach(fbnelem -> updateCommands.add(new UpdateFBTypeCommand(fbnelem)));
		updateCommands.execute();
		return true;
	}

	private boolean createMUX() {
		muxCreateCommand = new CompoundCommand();

		structConnectionMap = new HashMap<>();
		demuxMap = new HashMap<>();
		muxMap = new HashMap<>();

		// Create map between correct connected FBs and create MUX
		getElementsOfType(destinationType).stream().forEach(instance -> {
			instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream());

			final List<Connection> cons = instance.getInterface().getErrorMarker().stream()
					.flatMap(err -> err.getInputConnections().stream())
					.filter(con -> replacableConMap.containsKey(con.getSource().getName())
							&& replacableConMap.get(con.getSource().getName()).equals(con.getDestination().getName())
							&& con.getSourceElement().getType().getName().equals(sourceType.getName()))
					.toList();

			if (cons.stream().map((Function<? super Connection, ? extends FBNetworkElement>) Connection::getSourceElement).distinct().count() == 1
					&& cons.stream().count() == replacableConMap.size()) {
				structConnectionMap.put(instance,
						instance.getInterface().getErrorMarker().stream()
								.flatMap(err -> err.getInputConnections().stream())
								.filter(con -> replacableConMap.containsValue(con.getDestination().getName()))
								.findFirst().get().getSourceElement());
			} else if (conflictResolution) {
				final FBCreateCommand muxcreate = new FBCreateCommand(
						instance.getTypeLibrary().getFBTypeEntry("STRUCT_MUX"), instance.getFbNetwork(), 0, 0);// (int)(instance.getPosition().getX()+10),
																												// (int)instance.getPosition().getY());
				muxCreateCommand.add(muxcreate);
				muxcreate.execute();
				final ChangeStructCommand changeStruct = new ChangeStructCommand(
						(StructManipulator) muxcreate.getElement(), structType);
				muxCreateCommand.add(changeStruct);
				changeStruct.execute();
				muxMap.put(instance, changeStruct.getNewElement());
			}
		});

		// Create DEMUX if needed
		if (conflictResolution) {
			getElementsOfType(sourceType).stream().forEach(instance -> {
				if (instance.getInterface().getErrorMarker().stream()
						.flatMap(err -> err.getOutputConnections().stream())
						.filter(con -> !(structConnectionMap.containsKey(con.getDestinationElement())
								&& structConnectionMap.get(con.getDestinationElement()).equals(instance)
								&& replacableConMap.containsKey(con.getSource().getName()) && replacableConMap
										.get(con.getSource().getName()).equals(con.getDestination().getName())))
						.count() != 0) {
					final FBCreateCommand muxcreate = new FBCreateCommand(
							instance.getTypeLibrary().getFBTypeEntry("STRUCT_DEMUX"), instance.getFbNetwork(), 0, 0);// (int)(instance.getPosition().getX()+10),
																														// (int)instance.getPosition().getY());
					muxCreateCommand.add(muxcreate);
					muxcreate.execute();
					final ChangeStructCommand changeStruct = new ChangeStructCommand(
							(StructManipulator) muxcreate.getElement(), structType);
					muxCreateCommand.add(changeStruct);
					changeStruct.execute();
					demuxMap.put(instance, changeStruct.getNewElement());
				}
			});
		}
		return true;
	}

	private boolean reconnectCon() {
		// Edit destination connections
		destinationConnectionEditCommand = new CompoundCommand();
		sourceConnectionEditCommand = new CompoundCommand();

		getElementsOfType(destinationType).stream().forEach(instance -> {
			if (structConnectionMap.containsKey(instance)) {
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				// TODO: getInput if input is altered during creation (due to vars with same
				// name)
				structCon.setDestination(instance.getInput(destinationVarName));
				structCon.setSource(structConnectionMap.get(instance).getOutput(sourceVarName));
				destinationConnectionEditCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName()))
						.forEach(con -> destinationConnectionEditCommand.add(new DeleteConnectionCommand(con)));
			} else {
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				structCon.setDestination(instance.getInput(destinationVarName));
				structCon.setSource(muxMap.get(instance).getOutput("OUT"));
				destinationConnectionEditCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getInputConnections().stream())
						.filter(con -> replacableConMap.containsValue(con.getDestination().getName())).forEach(con -> {
							final String var = replacableConMap.entrySet().stream()
									.filter(entry -> entry.getValue().equals(con.getDestination().getName())).findAny()
									.get().getKey();
							destinationConnectionEditCommand.add(new ReconnectDataConnectionCommand(con, false,
									muxMap.get(instance).getInput(var), instance.getFbNetwork()));
						});
			}
		});
		destinationConnectionEditCommand.execute();

		// Edit source connections
		getElementsOfType(sourceType).stream().forEach(instance -> {
			if (!structConnectionMap.containsValue(instance)) {
				final StructDataConnectionCreateCommand structCon = new StructDataConnectionCreateCommand(
						instance.getFbNetwork());
				structCon.setDestination(demuxMap.get(instance).getInput("IN"));
				structCon.setSource(instance.getOutput(sourceVarName));
				sourceConnectionEditCommand.add(structCon);
				instance.getInterface().getErrorMarker().stream().flatMap(err -> err.getOutputConnections().stream())
						.filter(con -> replacableConMap.containsKey(con.getSource().getName()))
						.forEach(con -> sourceConnectionEditCommand.add(new ReconnectDataConnectionCommand(con, true,
								demuxMap.get(instance).getOutput(con.getSource().getName()), instance.getFbNetwork())));
			}
		});
		sourceConnectionEditCommand.execute();
		return true;
	}

	private List<FBNetworkElement> getElementsOfType(final FBType type) {
		return new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream()
				.map(FBNetworkElement.class::cast).toList();
	}
}
