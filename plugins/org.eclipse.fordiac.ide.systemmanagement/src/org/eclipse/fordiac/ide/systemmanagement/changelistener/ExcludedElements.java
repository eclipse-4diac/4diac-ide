package org.eclipse.fordiac.ide.systemmanagement.changelistener;

import java.util.List;

public class ExcludedElements {
	private static final List<String> paths = List.of("MANIFEST.MF"); //$NON-NLS-1$

	public static boolean contains(final String pathToString) {
		if (paths.contains(pathToString)) {
			return true;
		}
		return false;
	}

	public static String stringRep() {
		return paths.toString();
	}

}
