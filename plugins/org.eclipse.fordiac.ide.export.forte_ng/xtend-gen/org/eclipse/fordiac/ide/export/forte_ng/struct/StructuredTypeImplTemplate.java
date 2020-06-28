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
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class StructuredTypeImplTemplate extends StructBaseTemplate {
  public StructuredTypeImplTemplate(final StructuredType type, final String name, final Path prefix) {
    super(type, name, prefix);
  }
  
  @Override
  public CharSequence generate() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateHeader = this.generateHeader();
    _builder.append(_generateHeader);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateImplIncludes = this.generateImplIncludes();
    _builder.append(_generateImplIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("DEFINE_FIRMWARE_DATATYPE(");
    String _name = this.getType().getName();
    _builder.append(_name);
    _builder.append(", ");
    CharSequence _fORTEString = this.getFORTEString(this.getType().getName());
    _builder.append(_fORTEString);
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _structClassName = this.getStructClassName();
    _builder.append(_structClassName);
    _builder.append("::");
    CharSequence _structClassName_1 = this.getStructClassName();
    _builder.append(_structClassName_1);
    _builder.append("() :");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("CIEC_STRUCT(");
    CharSequence _fORTEString_1 = this.getFORTEString(this.getType().getName());
    _builder.append(_fORTEString_1, "    ");
    _builder.append(", ");
    int _size = this.getType().getMemberVariables().size();
    _builder.append(_size, "    ");
    _builder.append(", scmElementTypes, scmElementNames, e_APPLICATION + e_CONSTRUCTED + 1) {");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("const CStringDictionary::TStringId ");
    CharSequence _structClassName_2 = this.getStructClassName();
    _builder.append(_structClassName_2);
    _builder.append("::scmElementNames[] = {");
    String _fORTENameList = this.getFORTENameList(this.getType().getMemberVariables());
    _builder.append(_fORTENameList);
    _builder.append("};");
    _builder.newLineIfNotEmpty();
    _builder.append("const CStringDictionary::TStringId ");
    CharSequence _structClassName_3 = this.getStructClassName();
    _builder.append(_structClassName_3);
    _builder.append("::scmElementTypes[] = {");
    String _fORTETypeList = this.getFORTETypeList(this.getType().getMemberVariables());
    _builder.append(_fORTETypeList);
    _builder.append("};");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateImplIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    CharSequence _structuredTypeFileName = StructBaseTemplate.structuredTypeFileName(this.getType());
    _builder.append(_structuredTypeFileName);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP");
    _builder.newLine();
    _builder.append("#include \"");
    CharSequence _structuredTypeFileName_1 = StructBaseTemplate.structuredTypeFileName(this.getType());
    _builder.append(_structuredTypeFileName_1);
    _builder.append("_gen.cpp\"");
    _builder.newLineIfNotEmpty();
    _builder.append("#endif");
    _builder.newLine();
    return _builder;
  }
}
