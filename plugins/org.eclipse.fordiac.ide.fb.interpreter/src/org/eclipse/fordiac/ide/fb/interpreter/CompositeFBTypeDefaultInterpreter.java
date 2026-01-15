package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ConnectionUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class CompositeFBTypeDefaultInterpreter extends FBWithNetworkDefaultInterpreter {

	public CompositeFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence) {
		super(eventOccurrence);
	}

	public EList<EventOccurrence> run(final CompositeFBTypeRuntime fbTypeRuntime) {
		final CompositeFBType compType = fbTypeRuntime.getModel();
		fbTypeRuntime.setFbElement(eventOccurrence.getParentFB());

		final FBNetworkRuntime innerRT = fbTypeRuntime.getNetworkRuntime();
		final FBNetworkRuntime outerRT = innerRT.getOuterNetworkRuntime();
		final String eventName = eventOccurrence.getEvent().getName();
		final Event output;
		final FBNetworkRuntime networkRT;

		if (InterfacePinUtils.isInput(eventOccurrence.getEvent())) {
			output = compType.getInterfaceList().getEvent(eventName);
			networkRT = innerRT;
			copyTransferDataToInnerRuntime(outerRT, innerRT, fbTypeRuntime);
		} else {
			output = fbTypeRuntime.getFbElement().getInterface().getEvent(eventName);
			networkRT = outerRT;
			copyTransferDataToOuter(outerRT, innerRT, fbTypeRuntime);
		}
		return switchNetwork(output, networkRT);
	}

	private void copyTransferDataToInnerRuntime(final FBNetworkRuntime outer, final FBNetworkRuntime inner,
			final CompositeFBTypeRuntime compRT) {
		for (final VarDeclaration varDec : getAssociatedDataPins(eventOccurrence, outer)) {
			Value value;
			final var dataInputs = varDec.getInputConnections();
			if (dataInputs.isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = dataInputs.get(0);
				value = outer.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}

			// write value to inner network transfer data
			final IInterfaceElement varPin = compRT.getCompositeFBType().getInterfaceList()
					.getInput(List.of(varDec.getName()));
			final List<Connection> dataOutputs = ConnectionUtils.getOutputConnections(varPin);
			for (final Connection output : dataOutputs) {
				inner.getTransferData().put(output, EcoreUtil.copy(value));
			}
		}
	}

	private void copyTransferDataToOuter(final FBNetworkRuntime outer, final FBNetworkRuntime inner,
			final CompositeFBTypeRuntime compRT) {
		for (final VarDeclaration varDec : getAssociatedDataPins(this.eventOccurrence, outer)) {
			Value value;
			final var inputs = compRT.getCompositeFBType().getInterfaceList().getOutput(List.of(varDec.getName()))
					.getInputConnections();
			if (inputs.isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = inputs.get(0);
				value = inner.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}

			// write value to outer network transfer data
			final IInterfaceElement varPin = compRT.getFbElement().getInterface().getOutput(List.of(varDec.getName()));
			final List<Connection> dataOutputs = ConnectionUtils.getOutputConnections(varPin);
			for (final Connection output : dataOutputs) {
				outer.getTransferData().put(output, value);
			}
		}
	}
}
