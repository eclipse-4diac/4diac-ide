/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Matthias Plasch, 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.io.File;
import java.io.IOException;

import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;

/**
 * The Class Utils.
 * 
 * @author Gerhard Ebenhofer, gerhard.ebenhofer@profactor.at
 */
public final class Utils {

	/**
	 * Creats a backup file of the specified file.
	 * 
	 * @param in the file that should be backuped
	 * 
	 * @return the backup file
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static File createBakFile(final File in) throws IOException {
		String path = in.getAbsolutePath() + ".bak"; //$NON-NLS-1$
		File f;
		int i = 1;
		String temp = path;
		while ((f = new File(temp)).exists()) {
			temp = path + i;
			i++;
		}
		ImportUtils.copyFile(in, f);
		return f;
	}

	private Utils() {
		throw new UnsupportedOperationException("Utils utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
