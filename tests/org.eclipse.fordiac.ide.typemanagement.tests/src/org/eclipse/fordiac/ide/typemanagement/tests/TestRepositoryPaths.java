/*******************************************************************************
 * Copyright (c) 2026
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dimitrios Kalligaridis - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Filesystem locations inside the repository source tree, resolved through OSGi
 * from the test bundle location. Lets tests reach shared resources that live
 * outside the bundle, such as the standard libraries under data/typelibrary,
 * without depending on the JVM working directory.
 */
public record TestRepositoryPaths(Path repositoryRoot, Path standardLibraries) {

	private static final String STANDARD_LIBRARIES_DIR = "data/typelibrary"; //$NON-NLS-1$

	public static TestRepositoryPaths resolve() throws IOException {
		final Bundle bundle = FrameworkUtil.getBundle(TestRepositoryPaths.class);
		final URL bundleRoot = FileLocator.toFileURL(bundle.getEntry("/")); //$NON-NLS-1$
		// The test bundle lives at <repositoryRoot>/tests/<bundle>, so two parent
		// segments from its root reach the repository root.
		final Path repositoryRoot = Paths.get(bundleRoot.getPath()).getParent().getParent().toAbsolutePath()
				.normalize();
		return new TestRepositoryPaths(repositoryRoot, repositoryRoot.resolve(STANDARD_LIBRARIES_DIR));
	}
}
