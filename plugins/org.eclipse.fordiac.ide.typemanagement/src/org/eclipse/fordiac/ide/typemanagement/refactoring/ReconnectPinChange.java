package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.EnumSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.IFordiacPreviewChange.ChangeState;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ReconnectPinChange extends AbstractCommandChange<FBNetworkElement> implements IFordiacPreviewChange {

	private final EnumSet<ChangeState> state = EnumSet.noneOf(ChangeState.class);
	private final String newName;
	private final String oldName;
	private final FBNetworkElement instance;

	protected ReconnectPinChange(final URI elementURI, final Class<FBNetworkElement> elementClass, final String newName,
			final String oldName, final FBNetworkElement fbNeworkElement) {
		super(elementURI, elementClass);
		this.newName = newName;
		this.oldName = oldName;
		this.instance = fbNeworkElement;
	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		return null;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {

		return new ReconnectPinByName(oldName, newName, element, ChangeState.Reconnect);

	}

	@Override
	public EnumSet<ChangeState> getState() {
		return state;
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.Reconnect, ChangeState.NO_CHANGE, ChangeState.DELETE);
	}

	@Override
	public void addState(final ChangeState newState) {
		state.add(newState);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.Reconnect);
	}

}

class ReconnectPinByName extends Command {

	final String oldName;
	final String newName;
	final FBNetworkElement element;
	private final ChangeState state;

	public ReconnectPinByName(final String oldName, final String newName, final FBNetworkElement fbNeworkElement,
			final ChangeState state) {
		this.oldName = oldName;
		this.newName = newName;
		this.element = fbNeworkElement;
		this.state = state;
	}

	@Override
	public boolean canExecute() {

		return state.equals(ChangeState.Reconnect);
	}

	@Override
	public void execute() {
		System.out.println(element);
		final IInterfaceElement interfaceElement = element.getInterfaceElement(newName);
		System.out.println("new" + interfaceElement);
		final IInterfaceElement oldinterfaceElement = element.getInterfaceElement(oldName);

		if (oldinterfaceElement instanceof final ErrorMarkerInterface errorMarkerInterface) {
			final EList<Connection> inputConnections = getConnection(errorMarkerInterface);
			final Class<? extends EList> class1 = inputConnections.getClass();

			final CompoundCommand cmds = new CompoundCommand();
			for (final Connection c : inputConnections) {

				if (c instanceof final DataConnection dc) {
					final ReconnectDataConnectionCommand cmd = new ReconnectDataConnectionCommand(dc,
							!errorMarkerInterface.isIsInput(), interfaceElement, element.getFbNetwork());
					cmds.add(cmd);
				}

			}

			cmds.execute();
		}

		// System.out.println("old" + oldinterfaceElement);
	}

	private EList<Connection> getConnection(final ErrorMarkerInterface errorMarkerInterface) {
		return errorMarkerInterface.isIsInput() ? errorMarkerInterface.getInputConnections()
				: errorMarkerInterface.getOutputConnections();
	}

}