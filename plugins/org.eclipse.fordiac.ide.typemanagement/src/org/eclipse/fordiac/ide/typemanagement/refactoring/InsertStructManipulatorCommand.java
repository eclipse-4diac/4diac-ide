package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.gef.commands.Command;

public class InsertStructManipulatorCommand extends Command {
	private final StructuredType structType;
	private final boolean isMUX;
	private final IInterfaceElement port;
	private FBCreateCommand muxcreate;
	private ChangeStructCommand changeStruct;
	private StructDataConnectionCreateCommand createCon;

	public InsertStructManipulatorCommand(final StructuredType structType, final IInterfaceElement port) {
		this.structType = Objects.requireNonNull(structType);
		this.port = Objects.requireNonNull(port);
		this.isMUX = Objects.requireNonNull(port.isIsInput());
	}

	@Override
	public void execute() {
		final FBNetworkElement element = port.getFBNetworkElement();

		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX((int) element.getPosition().getX() + (isMUX ? (-1000) : 1000));
		pos.setY((int) element.getPosition().getY());
		muxcreate = new FBCreateCommand(element.getTypeLibrary().getFBTypeEntry(isMUX ? "STRUCT_MUX" : "STRUCT_DEMUX"),
				element.getFbNetwork(), pos);
		muxcreate.execute();

		changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(), structType);
		changeStruct.execute();
		createCon = new StructDataConnectionCreateCommand(element.getFbNetwork());

		final IInterfaceElement src = isMUX ? changeStruct.getNewElement().getInterface().getOutputVars().getFirst()
				: port;
		final IInterfaceElement dest = isMUX ? port
				: changeStruct.getNewElement().getInterface().getInputVars().getFirst();
		createCon.setSource(src);
		createCon.setDestination(dest);
		createCon.execute();
	}

	public FBNetworkElement getNewElement() {
		return changeStruct.getNewElement();
	}

	@Override
	public void undo() {
		createCon.undo();
		changeStruct.undo();
		muxcreate.undo();
	}

	@Override
	public void redo() {
		muxcreate.redo();
		changeStruct.redo();
		createCon.redo();
	}
}
