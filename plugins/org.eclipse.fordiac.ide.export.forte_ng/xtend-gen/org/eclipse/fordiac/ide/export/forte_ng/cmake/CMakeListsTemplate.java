/**
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
 */
package org.eclipse.fordiac.ide.export.forte_ng.cmake;

import java.nio.file.Path;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class CMakeListsTemplate extends ForteFBTemplate {
  public CMakeListsTemplate(final String name, final Path prefix) {
    super(name, prefix, null);
  }
  
  @Override
  protected FBType getType() {
    return null;
  }
  
  @Override
  public CharSequence generate() throws ExportException {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("# This is a generated build-system-configuration file for 4diac forte");
    _builder.newLine();
    _builder.append("# ");
    _builder.newLine();
    _builder.append("# To use the current directory-name as basis for the module-name use");
    _builder.newLine();
    _builder.newLine();
    _builder.append("forte_add_directory_module()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# If you want to set your own name independent of storage location you can use");
    _builder.newLine();
    _builder.append("# forte_add_module(\"YOUR_MODULE_NAME_HERE\" \"short description of your module\")");
    _builder.newLine();
    _builder.append("# Additional parameters can be added to specify dependencies on other modules");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# To specify the source-files to be included in this module you can use");
    _builder.newLine();
    _builder.newLine();
    _builder.append("forte_add_all_sourcefiles()");
    _builder.newLine();
    _builder.newLine();
    _builder.append("# This will add all files named *.h *.cpp *.c in the current directory");
    _builder.newLine();
    _builder.append("# The order the files are added in is dependent on your file system.");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# If for any reason you need more control you can use ");
    _builder.newLine();
    _builder.append("# forte_add_sourcefile_hcpp, forte_add_sourcefile_cpp, forte_add_sourcefile_c");
    _builder.newLine();
    _builder.append("# forte_add_sourcefile_hc, ...");
    _builder.newLine();
    _builder.append("#");
    _builder.newLine();
    _builder.append("# For more information look at buildsupport/forte.cmake of the 4diac forte sourcecode");
    _builder.newLine();
    return _builder;
  }
}
