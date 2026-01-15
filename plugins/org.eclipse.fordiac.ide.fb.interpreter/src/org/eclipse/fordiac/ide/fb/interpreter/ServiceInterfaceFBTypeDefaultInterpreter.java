package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;

public class ServiceInterfaceFBTypeDefaultInterpreter {

	private final EventOccurrence eventOccurrence;

	public ServiceInterfaceFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public EList<EventOccurrence> run(final ServiceInterfaceFBTypeRuntime fbTypeRuntime) {
		// not supported
		return ECollections.emptyEList();
	}
}
