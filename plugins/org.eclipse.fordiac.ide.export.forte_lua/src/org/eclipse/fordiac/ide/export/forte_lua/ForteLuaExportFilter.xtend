/*******************************************************************************
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst, Florian Noack, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua 

import java.util.Collections
import org.eclipse.core.resources.IFile
import org.eclipse.fordiac.ide.export.forte_lua.filter.AdapterFilter
import org.eclipse.fordiac.ide.export.forte_lua.filter.BasicFBFilter
import org.eclipse.fordiac.ide.export.ExportException
import org.eclipse.fordiac.ide.export.IExportFilter
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.export.forte_lua.filter.CompositeFBFilter

class ForteLuaExportFilter implements IExportFilter {

	extension BasicFBFilter basicFBFilter = new BasicFBFilter
	extension AdapterFilter adapterFilter = new AdapterFilter
	extension CompositeFBFilter compositeFBFilter = new CompositeFBFilter

	override export(IFile typeFile, String destination, boolean forceOverwrite) throws ExportException {
		throw new UnsupportedOperationException("Require a library element to work on")
	}

	override export(IFile typeFile, String destination, boolean forceOverwrite, LibraryElement type) throws ExportException {
		switch (type) {
			BasicFBType: Activator.getDefault().logInfo(type.lua)
			CompositeFBType: Activator.getDefault().logInfo(type.lua)
			AdapterType: Activator.getDefault().logInfo(type.lua)
			default: throw new UnsupportedOperationException("Unknown library element type " + type.eClass.name)
		}
	}
	
	def String createLUA(LibraryElement type) {
		switch (type) {
			BasicFBType: return String.valueOf(type.lua)
			CompositeFBType: return String.valueOf(type.lua)
			AdapterType: return String.valueOf(type.lua)
			default: throw new UnsupportedOperationException("Unknown library element type " + type.eClass.name)
		}
	}

	override getWarnings() {
		Collections.emptyList
	}

	override getErrors() {
		basicFBFilter.errors
	}

	override getInfos() {
		Collections.emptyList
	}

}
