/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.nio.file.Path;
import java.util.List;

public interface IExportTemplate {

	/**
	 * Return the template name.
	 */
	String getName();

	/**
	 * Return the template path.
	 */
	Path getPath();

	/**
	 * Generate the template contents.
	 */
	CharSequence generate() throws ExportException;

	/**
	 * Return the errors.
	 */
	List<String> getErrors();

	/**
	 * Return the warnings.
	 */
	List<String> getWarnings();

	/**
	 * Return the infos.
	 */
	List<String> getInfos();
}
