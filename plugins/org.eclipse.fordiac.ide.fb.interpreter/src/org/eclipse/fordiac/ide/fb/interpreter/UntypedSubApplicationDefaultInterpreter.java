package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

public class UntypedSubApplicationDefaultInterpreter extends FBWithNetworkDefaultInterpreter {

	private final UntypedSubApp uSubApp;

	public UntypedSubApplicationDefaultInterpreter(final EventOccurrence eventOccurrence, final UntypedSubApp uSubApp) {
		super(eventOccurrence);
		this.uSubApp = uSubApp;
	}

	public EList<EventOccurrence> run(final FBNetworkRuntime fBNetworkRuntime) {

		FBNetworkRuntime runtime;

		if (InterfacePinUtils.isInput(eventOccurrence.getEvent())) { // we are entering the inner SubApp network
			runtime = RuntimeFactory.getOrCreateNetworkRuntime(fBNetworkRuntime, uSubApp);
		} else { // we are leaving the inner SubApp network
			runtime = RuntimeFactory.getOrCreateOuterNetworkRuntime(fBNetworkRuntime, uSubApp);
		}

		return switchNetwork(eventOccurrence.getEvent(), runtime);
	}
}
