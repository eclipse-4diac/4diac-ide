package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ConnectionUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class FBWithNetworkDefaultInterpreter {

	protected final EventOccurrence eventOccurrence;

	public FBWithNetworkDefaultInterpreter(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	protected static EList<VarDeclaration> getAssociatedDataPins(final EventOccurrence sourceEventOcurrence,
			final FBNetworkRuntime runtime) {
		// Sample data
		final Event sourceTypeEvent = getEquivalentEventTypePin(sourceEventOcurrence);
		final EList<VarDeclaration> varsToSample = sourceTypeEvent.getWith().stream().map(With::getVariables)
				.collect(Collectors.toCollection(BasicEList::new));

		// Find the pins on the network
		final EList<VarDeclaration> networkVarsSample = new BasicEList<>();
		for (final VarDeclaration iel : varsToSample) {
			final IInterfaceElement interfaceElement = getEquivalentNetworkPin(runtime,
					sourceEventOcurrence.getParentFB(), iel);
			networkVarsSample.add((VarDeclaration) interfaceElement);
		}
		return networkVarsSample;
	}

	// for event pin of an FB instance: look for the equivalent event pin in the FB
	// type
	protected static Event getEquivalentEventTypePin(final EventOccurrence sourceEventOccurrence) {
		final BlockFBNetworkElement fbElem = sourceEventOccurrence.getParentFB();
		if (fbElem != null) {
			return InterfacePinUtils.findEventInInterface(fbElem, sourceEventOccurrence.getEvent());
		}
		// otherwise it might be an interpretation of a composite fb type, where we
		// reuse this code
		if (sourceEventOccurrence.getFbRuntime() instanceof CompositeFBTypeRuntime) {
			return sourceEventOccurrence.getEvent();
		}
		return null;
	}

	protected static IInterfaceElement getEquivalentNetworkPin(final FBNetworkRuntime runtime,
			final FBNetworkElement parentFB, final VarDeclaration pin) {
		final var equivalentFb = runtime.getFbnetwork().getFBNamed(parentFB.getName());
		return InterfacePinUtils.findPinInInterface(equivalentFb, pin);
	}

	protected EList<EventOccurrence> switchNetwork(final Event event, final FBNetworkRuntime runtime) {
		final EList<Connection> outputs = ConnectionUtils.getOutputConnections(event);
		final EventOccurrence outputEO = EventOccFactory.createFrom(eventOccurrence.getEvent(), runtime);
		for (final Connection conn : outputs) {
			// add transactions
			final EventConnection eventConn = (EventConnection) conn;
			final EventOccurrence inputEO = EventOccFactory.createFrom(eventConn.getEventDestination());
			inputEO.setFbRuntime(EcoreUtil.copy(runtime));
			final FBTransaction fbTrans = TransactionFactory.createFrom(inputEO);
			outputEO.getCreatedTransactions().add(fbTrans);
		}
		return ECollections.asEList(outputEO);
	}

}
