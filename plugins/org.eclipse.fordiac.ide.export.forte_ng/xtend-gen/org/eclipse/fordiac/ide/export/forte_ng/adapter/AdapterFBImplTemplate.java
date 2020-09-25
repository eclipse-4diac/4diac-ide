/**
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
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
 *   Alois Zoitl
 *     - Fix issues in adapter code generation
 */
package org.eclipse.fordiac.ide.export.forte_ng.adapter;

import java.nio.file.Path;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class AdapterFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private AdapterFBType type;
  
  public AdapterFBImplTemplate(final AdapterFBType type, final String name, final Path prefix) {
    super(name, prefix);
    this.type = type;
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
    CharSequence _generateFBDefinition = this.generateFBDefinition();
    _builder.append(_generateFBDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBInterfaceDefinition = this.generateFBInterfaceDefinition();
    _builder.append(_generateFBInterfaceDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBInterfaceSpecDefinition = this.generateFBInterfaceSpecDefinition();
    _builder.append(_generateFBInterfaceSpecDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence generateFBDefinition() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DEFINE_ADAPTER_TYPE(");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(", ");
    CharSequence _fORTEString = this.getFORTEString(this.type.getName());
    _builder.append(_fORTEString);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence generateFBInterfaceSpecSocket() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("const SFBInterfaceSpec ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::scm_stFBInterfaceSpecSocket = {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size = this.type.getInterfaceList().getEventInputs().size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty = this.type.getInterfaceList().getEventInputs().isEmpty();
      if (_isEmpty) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_1 = this.type.getInterfaceList().getEventOutputs().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_1 = this.type.getInterfaceList().getEventOutputs().isEmpty();
      if (_isEmpty_1) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_2 = this.type.getInterfaceList().getInputVars().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_2 = this.type.getInterfaceList().getInputVars().isEmpty();
      if (_isEmpty_2) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataInputNames, scm_anDataInputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_3 = this.type.getInterfaceList().getOutputVars().size();
    _builder.append(_size_3, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_3 = this.type.getInterfaceList().getOutputVars().isEmpty();
      if (_isEmpty_3) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataOutputNames, scm_anDataOutputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_4 = this.type.getInterfaceList().getPlugs().size();
    int _size_5 = this.type.getInterfaceList().getSockets().size();
    int _plus = (_size_4 + _size_5);
    _builder.append(_plus, "  ");
    _builder.append(", nullptr");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateFBInterfaceSpecPlug() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("const SFBInterfaceSpec ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::scm_stFBInterfaceSpecPlug = {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size = this.type.getInterfaceList().getEventOutputs().size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty = this.type.getInterfaceList().getEventOutputs().isEmpty();
      if (_isEmpty) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_1 = this.type.getInterfaceList().getEventInputs().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_1 = this.type.getInterfaceList().getEventInputs().isEmpty();
      if (_isEmpty_1) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_2 = this.type.getInterfaceList().getOutputVars().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_2 = this.type.getInterfaceList().getOutputVars().isEmpty();
      if (_isEmpty_2) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataOutputNames, scm_anDataOutputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_3 = this.type.getInterfaceList().getInputVars().size();
    _builder.append(_size_3, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_3 = this.type.getInterfaceList().getInputVars().isEmpty();
      if (_isEmpty_3) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataInputNames, scm_anDataInputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_4 = this.type.getInterfaceList().getPlugs().size();
    int _size_5 = this.type.getInterfaceList().getSockets().size();
    int _plus = (_size_4 + _size_5);
    _builder.append(_plus, "  ");
    _builder.append(", nullptr");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence generateFBInterfaceSpecDefinition() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateFBInterfaceSpecSocket = this.generateFBInterfaceSpecSocket();
    _builder.append(_generateFBInterfaceSpecSocket);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBInterfaceSpecPlug = this.generateFBInterfaceSpecPlug();
    _builder.append(_generateFBInterfaceSpecPlug);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Pure
  @Override
  protected AdapterFBType getType() {
    return this.type;
  }
}
