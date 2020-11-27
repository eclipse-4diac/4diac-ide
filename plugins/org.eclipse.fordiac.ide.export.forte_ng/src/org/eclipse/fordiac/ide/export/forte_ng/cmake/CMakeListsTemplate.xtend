/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.cmake

import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.ExportException
import java.nio.file.Path

class CMakeListsTemplate extends ForteFBTemplate {

	new(String name, Path prefix) {
		super(name, prefix)
	}

	override protected getType() {
		return null;
	}

	override generate() throws ExportException '''
	# This is a generated build-system-configuration file for 4diac forte
	# 
	# To use the current directory-name as basis for the module-name use
	
	forte_add_directory_module()
	
	# If you want to set your own name independent of storage location you can use
	# forte_add_module("YOUR_MODULE_NAME_HERE" "short description of your module")
	# Additional parameters can be added to specify dependencies on other modules
	
	# To specify the source-files to be included in this module you can use
	
	forte_add_all_sourcefiles()
	
	# This will add all files named *.h *.cpp *.c in the current directory
	# The order the files are added in is dependent on your file system.
	#
	# If for any reason you need more control you can use 
	# forte_add_sourcefile_hcpp, forte_add_sourcefile_cpp, forte_add_sourcefile_c
	# forte_add_sourcefile_hc, ...
	#
	# For more information look at buildsupport/forte.cmake of the 4diac forte sourcecode
	'''

}