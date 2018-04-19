/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GbmH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class Utils {

	/**
	 * Creats a backup file of the specified file.
	 * 
	 * @param in
	 *            the file that should be backuped
	 * 
	 * @return the backup file
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
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

}
