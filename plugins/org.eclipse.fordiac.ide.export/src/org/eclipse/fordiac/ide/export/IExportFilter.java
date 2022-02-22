/*******************************************************************************
 * Copyright (c) 2009, 2011, 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *     - refactor for arbitrary types in export
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * Interface for a specific ExportFilter.
 *
 * @author gebenh
 */
public interface IExportFilter {

	/**
	 * Export.
	 *
	 * @param typeFile       the path
	 * @param destination    the destination
	 * @param forceOverwrite the force overwrite
	 *
	 * @throws ExportException the export exception
	 */
	void export(IFile typeFile, String destination, boolean forceOverwrite) throws ExportException;

	/** Export.
	 *
	 * @param typeFile       the path
	 * @param destination    the destination
	 * @param forceOverwrite the force overwrite
	 * @param source         the source to export
	 *
	 * @throws ExportException the export exception
	 * @since 0.1 */
	void export(IFile typeFile, String destination, boolean forceOverwrite, EObject source) throws ExportException;

	/**
	 * Warnings occured during export.
	 *
	 * @return the warnings
	 */
	List<String> getWarnings();

	/**
	 * Errors occured during export.
	 *
	 * @return the Errors
	 */
	List<String> getErrors();

	/**
	 * Infos occured during export.
	 *
	 * @return the Infos
	 */
	List<String> getInfos();
}
