/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.ExportTemplate
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportOptions
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.osgi.framework.FrameworkUtil

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class ForteNgExportTemplate extends ExportTemplate {
	
	public static final String HEADER_TEXT = "This file was generated using the 4DIAC FORTE Export Filter " + getExporterVersion() + "!"; //$NON-NLS-1$

	protected new(String name, Path prefix) {
		super(name, prefix)
	}

	def protected generateDependencyIncludes(Iterable<? extends INamedElement> dependencies) '''
		«FOR include : dependencies.flatMap[generateDefiningIncludes].toSet.sort»
			«include.generateDependencyInclude»
		«ENDFOR»
	'''

	def protected generateDependencyInclude(String path) {
		if (ForteNgExportOptions.useSystemIncludes)
			'''#include <«path»>'''
		else
			'''#include "«path»"'''
	}

	def getFileBasename() { name.replaceAll("\\.[^.]+$", "") }
	
	def static String getExporterVersion(){
		var bundle = FrameworkUtil.getBundle(ForteNgExportTemplate);
        if (bundle !== null) {
            return bundle.getVersion().toString();
        }
        return "3.0.x";
	}
}
