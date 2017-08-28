/*******************************************************************************
 * Copyright (c) 2009, 2011, 2014 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.utils;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * Interface for a specific ExportFilter.
 * 
 * @author gebenh
 */
public interface IExportFilter {

	/**
	 * Export.
	 * 
	 * @param typeFile
	 *          the path
	 * @param destination
	 *          the destination
	 * @param forceOverwrite
	 *          the force overwrite
	 * 
	 * @throws ExportException
	 *           the export exception
	 */
	void export(IFile typeFile, String destination, boolean forceOverwrite)
			throws ExportException;

	/**
	 * Export.
	 * 
	 * @param typeFile
	 *          the path
	 * @param destination
	 *          the destination
	 * @param forceOverwrite
	 *          the force overwrite
	 * @param type
	 *          the LibraryElement
	 * 
	 * @throws ExportException
	 *           the export exception
	 * @since 0.1
	 */
	void export(IFile typeFile, String destination, boolean forceOverwrite,
			LibraryElement type) throws ExportException;

	/**
	 * Gets the export filter name.
	 * 
	 * @return the export filter name
	 */
	String getExportFilterName();

	/**
	 * Gets the export filter description.
	 * 
	 * @return the export filter description
	 */
	String getExportFilterDescription();

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
