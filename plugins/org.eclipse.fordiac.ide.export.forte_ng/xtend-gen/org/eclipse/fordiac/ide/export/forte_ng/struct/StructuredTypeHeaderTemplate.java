/**
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_ng.struct;

import java.nio.file.Path;
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructBaseTemplate;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class StructuredTypeHeaderTemplate extends StructBaseTemplate {
  public StructuredTypeHeaderTemplate(final StructuredType type, final String name, final Path prefix) {
    super(type, name, prefix);
  }
  
  @Override
  public CharSequence generate() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateHeader = this.generateHeader();
    _builder.append(_generateHeader);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateIncludeGuardStart = this.generateIncludeGuardStart();
    _builder.append(_generateIncludeGuardStart);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateHeaderIncludes = this.generateHeaderIncludes();
    _builder.append(_generateHeaderIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("class ");
    CharSequence _structClassName = this.getStructClassName();
    _builder.append(_structClassName);
    _builder.append(": public CIEC_STRUCT {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("DECLARE_FIRMWARE_DATATYPE(");
    String _name = this.getType().getName();
    _builder.append(_name, "  ");
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("public:");
    _builder.newLine();
    _builder.append("      ");
    CharSequence _structClassName_1 = this.getStructClassName();
    _builder.append(_structClassName_1, "      ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("      ");
    _builder.append("virtual ~");
    CharSequence _structClassName_2 = this.getStructClassName();
    _builder.append(_structClassName_2, "      ");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("          ");
    CharSequence _generateAccessors = this.generateAccessors(this.getType().getMemberVariables(), "getMember");
    _builder.append(_generateAccessors, "          ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("private:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("static const CStringDictionary::TStringId scmElementTypes[];");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("static const CStringDictionary::TStringId scmElementNames[];");
    _builder.newLine();
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateIncludeGuardEnd = this.generateIncludeGuardEnd();
    _builder.append(_generateIncludeGuardEnd);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateHeaderIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"forte_struct.h\"");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateTypeIncludes = this.generateTypeIncludes(this.getType().getMemberVariables());
    _builder.append(_generateTypeIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CompilerInfo _compilerInfo = this.getType().getCompilerInfo();
    String _header = null;
    if (_compilerInfo!=null) {
      _header=_compilerInfo.getHeader();
    }
    _builder.append(_header);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateAlgorithms() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void alg_REQ(void);");
    _builder.newLine();
    return _builder;
  }
}
