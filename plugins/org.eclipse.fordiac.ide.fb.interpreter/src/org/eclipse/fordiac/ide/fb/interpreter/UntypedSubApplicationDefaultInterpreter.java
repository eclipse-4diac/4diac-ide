package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

public class UntypedSubApplicationDefaultInterpreter extends FBWithNetworkDefaultInterpreter {

	public UntypedSubApplicationDefaultInterpreter(final EventOccurrence eventOccurrence) {
		super(eventOccurrence);
	}

	public EList<EventOccurrence> run(final FBNetworkRuntime fBNetworkRuntime, final UntypedSubApp uSubApp) {
		FBNetworkRuntime runtime;
		if (InterfacePinUtils.isInput(eventOccurrence.getEvent())) { // we are entering the inner SubApp network
			// try to get existing network runtime for this SubApp or create new
			runtime = (FBNetworkRuntime) fBNetworkRuntime.getTypeRuntimes().get(uSubApp);
			if (runtime == null) {
				runtime = RuntimeFactory.createFrom(uSubApp.getSubAppNetwork());
				runtime.setOuterNetworkRuntime(fBNetworkRuntime);
				fBNetworkRuntime.getTypeRuntimes().put(uSubApp, runtime);
			}
		} else { // we are leaving the inner SubApp network
			runtime = fBNetworkRuntime.getOuterNetworkRuntime();
			// can still be null if we started the trace in the inner network
			if (runtime == null) {
				runtime = RuntimeFactory.createFrom(uSubApp.getFbNetwork());
			}
		}
		return switchNetwork(eventOccurrence.getEvent(), runtime);
	}
}
