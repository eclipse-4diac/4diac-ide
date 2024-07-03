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

import org.eclipse.fordiac.ide.model.typelibrary.ITypeLibraryStarter;

/**
 * Dummy class for extension point - exists to force plugin activation
 */
public class LibraryStarter implements ITypeLibraryStarter {

	@Override
	public void start() {
		// empty, as initialisation is handle through plugin
	}
}
