package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class Utils {

	private Utils() {

	}

	public static String getCacheKey(final EventOccurrence eventOccurrence) {
		if (eventOccurrence.getParentFB() != null) {
			return eventOccurrence.getParentFB().getQualifiedName();
		}
		if (eventOccurrence.getFbRuntime() != null) {
			return eventOccurrence.getFbRuntime().getModel().getName();
		}
		return "no-name"; //$NON-NLS-1$
	}

	public static void isConsumed(final EventOccurrence eo) {
		eo.setActive(false);
		// The event was consumed, so it was not ignored
		eo.setIgnored(false);
	}

	public static EventOccurrence createOutputEventOccurrence(final FBRuntimeAbstract runtime, final Event output,
			final FBType executedFbtype) {
		// Copy FBTypeRuntime
		final FBRuntimeAbstract newFBTypeRT = EcoreUtil.copy(runtime);
		// Copy FBType
		final FBType copyFBType = EcoreUtil.copy(executedFbtype);
		// Add copy FBType to the RuntimeFBType
		switch (newFBTypeRT) {
		case final BasicFBTypeRuntime b -> b.setBasicfbtype((BasicFBType) copyFBType);
		case final SimpleFBTypeRuntime s -> s.setSimpleFBType((SimpleFBType) copyFBType);
		case final FunctionFBTypeRuntime f -> f.setFunctionFBType((FunctionFBType) copyFBType);
		case final ServiceInterfaceFBTypeRuntime si -> si.setServiceFBType((ServiceInterfaceFBType) copyFBType);
		default -> throw new UnsupportedOperationException();
		}

		final var newEventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		newEventOccurrence.setFbRuntime(newFBTypeRT);
		newEventOccurrence.setEvent(output);
		if (runtime instanceof FBNetworkRuntime) {
			newEventOccurrence.setParentFB(output.getBlockFBNetworkElement());
		}
		return newEventOccurrence;
	}
}
