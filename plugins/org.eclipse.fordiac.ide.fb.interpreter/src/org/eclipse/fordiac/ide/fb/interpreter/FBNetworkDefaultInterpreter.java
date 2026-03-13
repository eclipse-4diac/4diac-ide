package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ConnectionUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class FBNetworkDefaultInterpreter extends FBWithNetworkDefaultInterpreter {

	public FBNetworkDefaultInterpreter(final EventOccurrence eventOccurrence) {
		super(eventOccurrence);
	}

	public EList<EventOccurrence> run(final FBNetworkRuntime fBNetworkRuntime) {
		if (eventOccurrence.getParentFB() == null) {
			// we are probably at the interface of a composite fb type or subapp type
			return switchNetwork(eventOccurrence.getEvent(), EcoreUtil.copy(fBNetworkRuntime));
		}
		if (eventOccurrence.getParentFB() instanceof final UntypedSubApp uSubApp) {
			return new UntypedSubApplicationDefaultInterpreter(eventOccurrence, uSubApp).run(fBNetworkRuntime);
		}

		final FBRuntimeAbstract runtime = RuntimeFactory.getOrCreateRuntime(fBNetworkRuntime,
				eventOccurrence.getParentFB());

		// sampling input & writing output is special for composite types
		if (runtime instanceof final CompositeFBTypeRuntime compTypeRT) {
			if (compTypeRT.getNetworkRuntime().getOuterNetworkRuntime() == null) {
				compTypeRT.getNetworkRuntime().setOuterNetworkRuntime(fBNetworkRuntime);
				// put the composite runtime into the inner network, so we will find our way
				// back to the outer network
				compTypeRT.getNetworkRuntime().getTypeRuntimes().put(eventOccurrence.getParentFB(), compTypeRT);
			}
			return DefaultRunFBType.runFBType(runtime, eventOccurrence);
		}

		// handle initial triggers at outputs of FBs (e.g., for SIFBs, but not for
		// Composite Types)
		if (!InterfacePinUtils.isInput(eventOccurrence.getEvent())) {
			return switchNetwork(eventOccurrence.getEvent(), EcoreUtil.copy(fBNetworkRuntime));
		}

		// sample Data Input
		sampleDataInput(runtime, fBNetworkRuntime);

		// process event by running the respective type
		final EList<EventOccurrence> typeOutputEos = DefaultRunFBType.runFBType(runtime, eventOccurrence);

		// Extract the returned values from the FBTypeRuntime to FBNetwork
		writeDataOutput(fBNetworkRuntime, typeOutputEos);

		// mapping the output event occurrences to the network
		createTransactionsForConnectedPins(typeOutputEos, fBNetworkRuntime);

		return typeOutputEos;
	}

	private void sampleDataInput(final FBRuntimeAbstract runtime, final FBNetworkRuntime fBNetworkRuntime) {
		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(eventOccurrence, fBNetworkRuntime);
		networkVarsSample.forEach(varDec -> {
			Value value = null;
			if (varDec.getInputConnections().isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = varDec.getInputConnections().get(0);
				value = fBNetworkRuntime.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}
			final VarDeclaration typeVarDec = getEquivalentDataPinFromType(runtime, varDec);
			if (!VariableUtils.isEmptyValue(value)) {
				typeVarDec.setValue(EcoreUtil.copy(value));
			}
		});
	}

	// for data pin of an FB instance: look for the equivalent data pin in the FB
	// type
	private static VarDeclaration getEquivalentDataPinFromType(final FBRuntimeAbstract runtime,
			final VarDeclaration varDec) {
		final FBType type = runtime.getModel();
		return InterfacePinUtils.findVariableInInterface(type, varDec);
	}

	private void writeDataOutput(final FBNetworkRuntime fBNetworkRuntime, final EList<EventOccurrence> typeOutputEos) {
		typeOutputEos.forEach(eo -> {
			extractOutputDataFromTypeRuntime(eo, fBNetworkRuntime);
			eo.setParentFB(eventOccurrence.getParentFB());
		});
		typeOutputEos.forEach(eo -> writeDataOutputsToConnections(eo, fBNetworkRuntime));
	}

	private void extractOutputDataFromTypeRuntime(final EventOccurrence outputEo, final FBNetworkRuntime destRuntime) {
		final FBType typeAfterExecution = outputEo.getFbRuntime().getModel();
		final Event returnedEvent = InterfacePinUtils.findEventInInterface(typeAfterExecution, outputEo.getEvent());
		final FB instance = destRuntime.getFbnetwork().getFBNamed(eventOccurrence.getParentFB().getName());
		for (final With w : returnedEvent.getWith()) {
			final VarDeclaration associatedVar = w.getVariables();
			final VarDeclaration destVar = InterfacePinUtils.findVariableInInterface(instance, associatedVar);
			destVar.setValue(EcoreUtil.copy(associatedVar.getValue()));
		}
	}

	private static void writeDataOutputsToConnections(final EventOccurrence eo, final FBNetworkRuntime runtime) {
		final EMap<Connection, Value> map = runtime.getTransferData();
		// if connection does not have a value yet, initialize it with the default value
		final FBType type = eo.getParentFB().getType();
		type.getInterfaceList().getOutputVars().forEach(pin -> getEquivalentNetworkPin(runtime, eo.getParentFB(), pin)
				.getOutputConnections().stream().forEach(conn -> {
					if (map.get(conn) == null) {
						final String val = InitialValueHelper.getInitialOrDefaultValue(pin);
						final Value value = LibraryElementFactory.eINSTANCE.createValue();
						value.setValue(val);
						map.put(conn, value);
					}
				}));

		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(eo, runtime);

		networkVarsSample.forEach(variable -> variable.getOutputConnections().stream().forEach(
				outputConnection -> map.put(outputConnection, EcoreUtil.copy(getOutputValue(variable, runtime)))));
	}

	private static Value getOutputValue(final VarDeclaration variable, final FBNetworkRuntime runtime) {
		if (!VariableUtils.isEmptyValue(variable.getValue())) {
			return variable.getValue();
		}
		final var typePin = getEquivalentDataPinFromType(runtime, variable);
		if (!VariableUtils.isEmptyValue(typePin.getValue())) {
			return typePin.getValue();
		}
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(InitialValueHelper.getDefaultValue(variable));
		return value;
	}

	private void createTransactionsForConnectedPins(final EList<EventOccurrence> typeOutputEos,
			final FBNetworkRuntime fBNetworkRuntime) {
		typeOutputEos.forEach(typeEo -> {
			// generate transactions for triggering all subsequent blocks
			final EventOccurrence networkEo = getCorrespondingNetworkEvent(typeEo, fBNetworkRuntime);
			typeEo.getCreatedTransactions().addAll(processEventConns(fBNetworkRuntime, networkEo));
		});
	}

	private EventOccurrence getCorrespondingNetworkEvent(final EventOccurrence typeEo,
			final FBNetworkRuntime fBNetworkRuntime) {
		final Event mappedEvent = InterfacePinUtils.findEventInInterface(eventOccurrence.getParentFB(),
				typeEo.getEvent());
		final EventOccurrence networkEo = EventOccFactory.createFrom(mappedEvent, EcoreUtil.copy(fBNetworkRuntime));
		networkEo.setParentFB(eventOccurrence.getParentFB());
		return networkEo;
	}

	private static List<FBTransaction> processEventConns(final FBNetworkRuntime fBNetworkRuntime,
			final EventOccurrence outputEo) {
		final List<FBTransaction> generatedT = new ArrayList<>();
		if (InterfacePinUtils.isInput(outputEo.getEvent())) {
			// very first transaction (if needed) / initial trigger pin
			generatedT.add(createNewInitialTransaction(outputEo.getEvent(), fBNetworkRuntime));
		} else {
			// Find the Original Pins for all connected FBs
			for (final Connection conn : ConnectionUtils.getOutputConnections(outputEo.getEvent())) {
				generatedT.add(createNewTransaction(conn.getDestination(), outputEo));
			}
		}
		return generatedT;
	}

	private static FBTransaction createNewInitialTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime) {
		final FBNetworkRuntime copiedRt = EcoreUtil.copy(fBNetworkRuntime);
		final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom((Event) dest, copiedRt);
		destinationEventOccurence.setParentFB(dest.getBlockFBNetworkElement());
		return TransactionFactory.createFrom(destinationEventOccurence);
	}

	private static FBTransaction createNewTransaction(IInterfaceElement dest, final EventOccurrence sourceEO) {
		if (dest instanceof final AdapterDeclaration aDecl) {
			dest = InterfacePinUtils.getContainedPin(aDecl, sourceEO.getEvent().getName());
		}
		if (!(dest instanceof Event)) {
			throw new IllegalArgumentException("cannot trigger FB with pin " + dest.getName()); //$NON-NLS-1$
		}
		final EventOccurrence destEO = EventOccFactory.createFrom((Event) dest);

		// if the destination EO does not have a parent, it might be the outgoing
		// connection for the network inside a composite
		if (destEO.getParentFB() == null
				// Interface List -> FB Type -> FB Type Runtime
				&& dest.eContainer().eContainer().eContainer() instanceof final CompositeFBTypeRuntime rt) {
			destEO.setParentFB(rt.getFbElement());
		}
		final FBTransaction transaction = TransactionFactory.createFrom(destEO);
		sourceEO.getCreatedTransactions().add(transaction);
		return transaction;
	}

}
