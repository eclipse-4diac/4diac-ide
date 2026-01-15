package org.eclipse.fordiac.ide.fb.interpreter;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;

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

}
