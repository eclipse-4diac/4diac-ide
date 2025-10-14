/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.cmake;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.export.ExportException;

public final class CMakeListsUtil {

	public static List<Path> getSubdirs(final Path path, final String name) throws ExportException {
		try (Stream<Path> walk = Files.walk(path.resolve(name))) {
			return walk.filter(Files::isDirectory).map(path::relativize).toList();
		} catch (final IOException e) {
			throw new ExportException(e.getMessage());
		}
	}

	private CMakeListsUtil() {
		throw new UnsupportedOperationException();
	}

	public static boolean isSourceFile(final Path path) {
		return Files.isRegularFile(path) && CMakeListsTemplate.SOURCE_EXTENSIONS.contains(getFileExtension(path));
	}

	public static String getFileExtension(final Path path) {
		final String fileName = path.getFileName().toString();
		final int lastDot = fileName.lastIndexOf('.');
		if (lastDot > 0) { // make sure it is not just a hidden file without extension
			return fileName.substring(lastDot);
		}
		return ""; //$NON-NLS-1$
	}
}
