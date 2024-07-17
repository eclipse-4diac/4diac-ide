package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Optional;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class RepairBrokenConnectionCommand extends Command {

	private final Connection connection;
	final boolean isSourceReconnect;
	private final StructuredType structType;
	private final String var;

	FBCreateCommand muxcreate;
	ChangeStructCommand changeStruct;
	ReconnectDataConnectionCommand recon;
	StructDataConnectionCreateCommand createCon;
	CompoundCommand eventConnectionCommand;

	public RepairBrokenConnectionCommand(final Connection con, final boolean isSourceReconnect,
			final StructuredType structType, final String var) {
		this.connection = con;
		this.isSourceReconnect = isSourceReconnect;
		this.structType = structType;
		this.var = var;
	}

	@Override
	public void execute() {
		final FBNetworkElement sourceElement = connection.getSourceElement();
		final FBNetworkElement destinationElement = connection.getDestinationElement();

		final TypeLibrary lib = sourceElement.getTypeLibrary();
		final FBNetwork fbn = sourceElement.getFbNetwork();
		final CompoundCommand temp = new CompoundCommand();

		(isSourceReconnect ? connection.getSourceElement().getInterface().getOutputs()
				: connection.getDestinationElement().getInterface().getInputs())
				.filter(port -> port.getType().equals(structType)).findAny().ifPresent(port -> {
					final Optional<FBNetworkElement> optmux;
					if (isSourceReconnect) {
						optmux = port.getOutputConnections().stream().map(Connection::getDestinationElement).filter(
								elem -> elem.getType().getTypeEntry().equals(lib.getFBTypeEntry("STRUCT_DEMUX")))
								.findAny();
					} else {
						optmux = port.getInputConnections().stream().map(Connection::getSourceElement)
								.filter(elem -> elem.getType().getTypeEntry().equals(lib.getFBTypeEntry("STRUCT_MUX")))
								.findAny();
					}
					final FBNetworkElement mux = optmux.orElseGet(() -> {
						final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
						pos.setX((int) (isSourceReconnect ? sourceElement : destinationElement).getPosition().getX()
								+ (isSourceReconnect ? 1000 : (-1000)));
						pos.setY((int) (isSourceReconnect ? sourceElement : destinationElement).getPosition().getY());
						muxcreate = new FBCreateCommand(
								lib.getFBTypeEntry(isSourceReconnect ? "STRUCT_DEMUX" : "STRUCT_MUX"), fbn, pos);
						muxcreate.execute();
						changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(), structType);
						changeStruct.execute();
						createCon = new StructDataConnectionCreateCommand(fbn);
						final IInterfaceElement src = isSourceReconnect ? port
								: changeStruct.getNewElement().getInterface().getOutputVars().getFirst();
						final IInterfaceElement dest = isSourceReconnect
								? changeStruct.getNewElement().getInterface().getInputVars().getFirst()
								: port;
						createCon.setSource(src);
						createCon.setDestination(dest);
						createCon.execute();
						return changeStruct.getNewElement();
					});

					eventConnectionCommand = new CompoundCommand();
					((VarDeclaration) port).getWiths().stream().forEach(with -> {
						if (isSourceReconnect) {
							((Event) with.eContainer()).getOutputConnections().stream().filter(
									con -> connection.getDestinationElement().equals(con.getDestinationElement()))
									.forEach(con -> {
										if (!checkDuplicate(mux.getInterface().getEventOutputs().getFirst(),
												con.getDestination())) {
											final EventConnectionCreateCommand createECon = new EventConnectionCreateCommand(
													fbn);
											createECon.setSource(mux.getInterface().getEventOutputs().getFirst());
											createECon.setDestination(con.getDestination());
											eventConnectionCommand.add(createECon);
										}
										if (!checkDuplicate(con.getSource(),
												mux.getInterface().getEventInputs().getFirst())) {
											eventConnectionCommand.add(new ReconnectEventConnectionCommand(con, false,
													mux.getInterface().getEventInputs().getFirst(), fbn));
										} else {
											eventConnectionCommand.add(new DeleteConnectionCommand(con));
										}
									});

						} else {
							((Event) with.eContainer()).getInputConnections().stream()
									.filter(con -> connection.getSourceElement().equals(con.getSourceElement()))
									.forEach(con -> {
										if (!checkDuplicate(con.getSource(),
												mux.getInterface().getEventInputs().getFirst())) {
											final EventConnectionCreateCommand createECon = new EventConnectionCreateCommand(
													fbn);
											createECon.setSource(con.getSource());
											createECon.setDestination(mux.getInterface().getEventInputs().getFirst());
											eventConnectionCommand.add(createECon);
										}
										if (!checkDuplicate(mux.getInterface().getEventOutputs().getFirst(),
												con.getDestination())) {
											eventConnectionCommand.add(new ReconnectEventConnectionCommand(con, true,
													mux.getInterface().getEventOutputs().getFirst(), fbn));
										} else {
											eventConnectionCommand.add(new DeleteConnectionCommand(con));
										}
									});

						}
					});
					eventConnectionCommand.add(new ReconnectDataConnectionCommand(connection, isSourceReconnect,
							isSourceReconnect ? mux.getInterface().getOutput(var) : mux.getInterface().getInput(var),
							fbn));
					eventConnectionCommand.execute();
				});
	}

	public static boolean checkDuplicate(final IInterfaceElement source, final IInterfaceElement destination) {
		return source.getOutputConnections().stream().filter(con -> con.getDestination().equals(destination))
				.count() != 0;
	}

	@Override
	public void undo() {
		eventConnectionCommand.undo();
		if (muxcreate != null) {
			createCon.undo();
			changeStruct.undo();
			muxcreate.undo();
		}
	}

	@Override
	public void redo() {
		if (muxcreate != null) {
			muxcreate.redo();
			changeStruct.redo();
			createCon.redo();
		}
		eventConnectionCommand.redo();
	}
}
