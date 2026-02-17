package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;

public class ServiceInterfaceFBTypeDefaultInterpreter {

	private final EventOccurrence eventOccurrence;

	public ServiceInterfaceFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public EList<EventOccurrence> run(final ServiceInterfaceFBTypeRuntime fbTypeRuntime) {
		// TODO this should probably be replaced by an extensible structure with
		// simulators to also cover other SIFB functionalities.
		if (this.eventOccurrence.getEvent().getName().equals("INIT")) { //$NON-NLS-1$
			final var outputEvent = InterfacePinUtils.findEventInInterface(fbTypeRuntime.getModel(), "INITO"); //$NON-NLS-1$
			return ECollections
					.asEList(Utils.createOutputEventOccurrence(fbTypeRuntime, outputEvent, fbTypeRuntime.getModel()));
		}
		// not supported
		return ECollections.emptyEList();
	}
}
