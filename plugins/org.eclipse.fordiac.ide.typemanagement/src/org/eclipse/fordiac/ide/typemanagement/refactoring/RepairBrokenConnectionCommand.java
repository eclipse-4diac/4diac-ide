package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Optional;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
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
	private final StructuredType structType;
	private final String var;

	FBCreateCommand muxcreate;
	ChangeStructCommand changeStruct;

	public RepairBrokenConnectionCommand(final Connection con, final StructuredType structType, final String var) {
		this.con = con;
		this.structType = structType;
		this.var = var;
	}

	@Override
	public void execute() {
		final FBNetworkElement sourceElement = con.getSourceElement();
		final TypeLibrary lib = sourceElement.getTypeLibrary();
		final FBNetwork fbn = sourceElement.getFbNetwork();
		final CompoundCommand temp = new CompoundCommand();
		if (con.getSource() instanceof ErrorMarkerInterface) {
			con.getSourceElement().getInterface().getOutputs().filter(output -> output.getType().equals(structType))
					.findAny().ifPresent(output -> {
						final Optional<FBNetworkElement> optDemux = output.getOutputConnections().stream()
								.map(Connection::getDestinationElement)
								.filter(elem -> elem.getType().equals(lib.getFBTypeEntry("STRUCT_DEMUX"))).findAny();
						final FBNetworkElement demux = optDemux.orElseGet(() -> {
							final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
							pos.setX((int) sourceElement.getPosition().getX() + 1000);
							pos.setY((int) sourceElement.getPosition().getY());
							muxcreate = new FBCreateCommand(lib.getFBTypeEntry("STRUCT_DEMUX"), fbn, pos);
							muxcreate.execute();
							changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(),
									structType);
							changeStruct.execute();
							final StructDataConnectionCreateCommand createCon = new StructDataConnectionCreateCommand(
									fbn);
							createCon.setSource(output);
							createCon.setDestination(changeStruct.getNewElement().getInput("IN"));
							createCon.execute();
							return changeStruct.getNewElement();
						});
						final ReconnectDataConnectionCommand recon;
						recon = new ReconnectDataConnectionCommand(con, true, demux.getInterface().getOutput(var), fbn);
						temp.add(recon);
					});

			temp.execute();
		}
	}

}
