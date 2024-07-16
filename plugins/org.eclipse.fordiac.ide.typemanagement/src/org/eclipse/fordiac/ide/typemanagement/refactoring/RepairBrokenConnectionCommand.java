package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class RepairBrokenConnectionCommand extends Command {

	private final Connection con;
	final boolean isSourceReconnect;
	private final StructuredType structType;
	private final String var;

	FBCreateCommand muxcreate;
	ChangeStructCommand changeStruct;
	ReconnectDataConnectionCommand recon;
	StructDataConnectionCreateCommand createCon;

	// TODO: extends reconnect??
	public RepairBrokenConnectionCommand(final Connection con, final boolean isSourceReconnect,
			final StructuredType structType, final String var) {
		this.con = con;
		this.isSourceReconnect = isSourceReconnect;
		this.structType = structType;
		this.var = var;
	}

	@Override
	public void execute() {
		final FBNetworkElement sourceElement = con.getSourceElement();
		final FBNetworkElement destinationElement = con.getDestinationElement();

		final TypeLibrary lib = sourceElement.getTypeLibrary();
		final FBNetwork fbn = sourceElement.getFbNetwork();
		final CompoundCommand temp = new CompoundCommand();
		if (isSourceReconnect) {
			con.getSourceElement().getInterface().getOutputs().filter(output -> output.getType().equals(structType))
					.findAny().ifPresent(output -> {
						final Optional<FBNetworkElement> optDemux = output.getOutputConnections().stream()
								.map(Connection::getDestinationElement).filter(elem -> elem.getType().getTypeEntry()
										.equals(lib.getFBTypeEntry("STRUCT_DEMUX")))
								.findAny();
						final FBNetworkElement demux = optDemux.orElseGet(() -> {
							final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
							pos.setX((int) sourceElement.getPosition().getX() + 1000);
							pos.setY((int) sourceElement.getPosition().getY());
							muxcreate = new FBCreateCommand(lib.getFBTypeEntry("STRUCT_DEMUX"), fbn, pos);
							muxcreate.execute();
							changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(),
									structType);
							changeStruct.execute();
							createCon = new StructDataConnectionCreateCommand(fbn);
							createCon.setSource(output);
							createCon.setDestination(
									changeStruct.getNewElement().getInterface().getInputVars().getFirst());
							createCon.execute();
							return changeStruct.getNewElement();
						});
						recon = new ReconnectDataConnectionCommand(con, true, demux.getInterface().getOutput(var), fbn);
						recon.execute();
					});

		} else {
			con.getDestinationElement().getInterface().getInputs().filter(input -> input.getType().equals(structType))
					.findAny().ifPresent(input -> {
						FBNetworkElement mux;
						try {
							mux = input.getInputConnections().getFirst().getSourceElement();
						} catch (final NoSuchElementException e) {
							final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
							pos.setX((int) destinationElement.getPosition().getX() - 1000);
							pos.setY((int) destinationElement.getPosition().getY());
							muxcreate = new FBCreateCommand(lib.getFBTypeEntry("STRUCT_MUX"), fbn, pos);
							muxcreate.execute();
							changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(),
									structType);
							changeStruct.execute();
							createCon = new StructDataConnectionCreateCommand(fbn);
							createCon.setSource(changeStruct.getNewElement().getInterface().getOutputVars().getFirst());
							createCon.setDestination(input);
							createCon.execute();
							mux = changeStruct.getNewElement();
						}
						if (mux.getType().getTypeEntry().equals(lib.getFBTypeEntry("STRUCT_MUX"))) {
							recon = new ReconnectDataConnectionCommand(con, false, mux.getInterface().getInput(var),
									fbn);
						}
						recon.execute();
					});
		}

	}

	@Override
	public void undo() {
		recon.undo();
		if (createCon != null) {
			createCon.undo();
			changeStruct.undo();
			muxcreate.undo();
		}
	}
}
