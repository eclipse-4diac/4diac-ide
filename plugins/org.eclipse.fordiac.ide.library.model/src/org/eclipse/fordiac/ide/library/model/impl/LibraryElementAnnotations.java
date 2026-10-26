package org.eclipse.fordiac.ide.library.model.impl;

import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

public final class LibraryElementAnnotations {

	public static boolean validatePattern(final LibraryElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final String pattern = element.getValue();

		if (pattern != null && isValidPattern(pattern)) {
			return true;
		}

		if (diagnostics != null) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryValidator.DIAGNOSTIC_SOURCE,
					LibraryValidator.LIBRARY_ELEMENT__VALIDATE_PATTERN,
					"Invalid package pattern declaration: " + pattern, //$NON-NLS-1$
					FordiacMarkerHelper.getDiagnosticData(element, LibraryPackage.Literals.LIBRARY_ELEMENT__VALUE,
							pattern)));
		}

		return false;
	}

	private static boolean isValidPattern(final String pattern) {
		final Pattern packageSegmentPattern = Pattern.compile("(?:\\*{1,2}|\\w+)"); //$NON-NLS-1$
		final String[] segments = pattern.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);
		for (final String segment : segments) {
			if (!packageSegmentPattern.matcher(segment).matches()) {
				return false;
			}
		}
		return true;
	}

}
