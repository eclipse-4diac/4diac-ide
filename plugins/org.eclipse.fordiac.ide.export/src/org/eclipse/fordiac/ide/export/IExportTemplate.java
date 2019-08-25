/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
