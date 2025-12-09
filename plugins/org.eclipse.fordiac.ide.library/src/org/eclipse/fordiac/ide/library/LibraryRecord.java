/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.net.URI;
import java.nio.file.Path;

import org.osgi.framework.Version;

public record LibraryRecord(String symbolicName, String name, Version version, String comment, Path path, URI uri) {
	public LibraryRecord(final String symbolicName, final String Name, final String version, final String Comment,
			final Path path, final URI uri) {
		this(symbolicName, Name, new Version(version), Comment, path, uri);
	}
}
