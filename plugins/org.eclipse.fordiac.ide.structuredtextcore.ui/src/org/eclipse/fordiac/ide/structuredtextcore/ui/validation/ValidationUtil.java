/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.validation;

import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.Issue;

public final class ValidationUtil {

	public static boolean shouldProcess(final Issue issue, final boolean ignoreWarnings) {
		return issue.getSeverity() == Severity.ERROR || !ignoreWarnings;
	}

	public static boolean isIgnoreWarnings(final IFile file) {
		return Boolean.parseBoolean(findSourceFolder(file).map(sourceFolder -> BuildpathAttributes
				.getAttributeValue(sourceFolder.getAttributes(), BuildpathAttributes.IGNORE_WARNINGS)).orElse(null));
	}

	public static Optional<SourceFolder> findSourceFolder(final IFile file) {
		return getBuildpath(file).flatMap(buildpath -> BuildpathUtil.findSourceFolder(buildpath, file));
	}

	public static Optional<Buildpath> getBuildpath(final IFile file) {
		return Optional.ofNullable(file).map(IFile::getProject).map(TypeLibraryManager.INSTANCE::getTypeLibrary)
				.map(TypeLibrary::getBuildpath);
	}

	private ValidationUtil() {
		throw new UnsupportedOperationException();
	}
}
