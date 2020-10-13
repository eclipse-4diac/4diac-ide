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

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class AdapterFBHeaderTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private AdapterFBType type;
  
  public AdapterFBHeaderTemplate(final AdapterFBType type, final String name, final Path prefix) {
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
    CharSequence _generateIncludeGuardStart = this.generateIncludeGuardStart();
    _builder.append(_generateIncludeGuardStart);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateHeaderIncludes = this.generateHeaderIncludes();
    _builder.append(_generateHeaderIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("class ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(": public CAdapter {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateFBDeclaration = this.generateFBDeclaration();
    _builder.append(_generateFBDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("private:");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateFBInterfaceDeclaration = this.generateFBInterfaceDeclaration();
    _builder.append(_generateFBInterfaceDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateFBInterfaceSpecDeclaration = this.generateFBInterfaceSpecDeclaration();
    _builder.append(_generateFBInterfaceSpecDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateAccessors = this.generateAccessors(this.type.getInterfaceList().getInputVars(), "getDI", "getDO");
    _builder.append(_generateAccessors, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateAccessors_1 = this.generateAccessors(this.type.getInterfaceList().getOutputVars(), "getDO", "getDI");
    _builder.append(_generateAccessors_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    EList<AdapterDeclaration> _sockets = this.type.getInterfaceList().getSockets();
    EList<AdapterDeclaration> _plugs = this.type.getInterfaceList().getPlugs();
    CharSequence _generateAccessors_2 = this.generateAccessors(IterableExtensions.<AdapterDeclaration>toList(Iterables.<AdapterDeclaration>concat(_sockets, _plugs)));
    _builder.append(_generateAccessors_2, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateEventAccessors = this.generateEventAccessors(this.type.getInterfaceList().getEventInputs());
    _builder.append(_generateEventAccessors, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateEventAccessors_1 = this.generateEventAccessors(this.type.getInterfaceList().getEventOutputs());
    _builder.append(_generateEventAccessors_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("private:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("FORTE_ADAPTER_DATA_ARRAY(");
    int _size = this.type.getInterfaceList().getEventInputs().size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    int _size_1 = this.type.getInterfaceList().getEventOutputs().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    int _size_2 = this.type.getInterfaceList().getInputVars().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    int _size_3 = this.type.getInterfaceList().getOutputVars().size();
    _builder.append(_size_3, "  ");
    _builder.append(", ");
    int _size_4 = this.type.getInterfaceList().getSockets().size();
    int _size_5 = this.type.getInterfaceList().getPlugs().size();
    int _plus = (_size_4 + _size_5);
    _builder.append(_plus, "  ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("ADAPTER_CTOR(");
    CharSequence _fBClassName_1 = this.getFBClassName();
    _builder.append(_fBClassName_1, "  ");
    _builder.append(") {};");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual ~");
    CharSequence _fBClassName_2 = this.getFBClassName();
    _builder.append(_fBClassName_2, "  ");
    _builder.append("() = default;");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateIncludeGuardEnd = this.generateIncludeGuardEnd();
    _builder.append(_generateIncludeGuardEnd);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence generateHeaderIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"adapter.h\"");
    _builder.newLine();
    _builder.append("#include \"typelib.h\"");
    _builder.newLine();
    CharSequence _generateHeaderIncludes = super.generateHeaderIncludes();
    _builder.append(_generateHeaderIncludes);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  protected CharSequence generateFBDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DECLARE_ADAPTER_TYPE(");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  @Override
  protected CharSequence generateFBInterfaceSpecDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static const SFBInterfaceSpec scm_stFBInterfaceSpecSocket;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("static const SFBInterfaceSpec scm_stFBInterfaceSpecPlug;");
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence generateEventConstants(final List<Event> events) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _generateEventConstants = super.generateEventConstants(events);
    _builder.append(_generateEventConstants, "\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("private:");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateAccessors(final List<VarDeclaration> vars, final String socketFunction, final String plugFunction) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final VarDeclaration v : vars) {
        _builder.append("CIEC_");
        String _typeName = v.getTypeName();
        _builder.append(_typeName);
        _builder.append(" ");
        {
          boolean _isArray = v.isArray();
          if (_isArray) {
            _builder.append("*");
          } else {
            _builder.append("&");
          }
        }
        String _name = v.getName();
        _builder.append(_name);
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        {
          boolean _isArray_1 = v.isArray();
          if (_isArray_1) {
            _builder.append("  ");
            _builder.append("return static_cast<CIEC_");
            String _typeName_1 = v.getTypeName();
            _builder.append(_typeName_1, "  ");
            _builder.append("*>(static_cast<CIEC_ARRAY *>((isSocket()) ? ");
            _builder.append(socketFunction, "  ");
            _builder.append("(");
            int _indexOf = vars.indexOf(v);
            _builder.append(_indexOf, "  ");
            _builder.append(") : ");
            _builder.append(plugFunction, "  ");
            _builder.append("(");
            int _indexOf_1 = vars.indexOf(v);
            _builder.append(_indexOf_1, "  ");
            _builder.append("))[0]); //the first element marks the start of the array");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("  ");
            _builder.append("return *static_cast<CIEC_");
            String _typeName_2 = v.getTypeName();
            _builder.append(_typeName_2, "  ");
            _builder.append("*>((isSocket()) ? ");
            _builder.append(socketFunction, "  ");
            _builder.append("(");
            int _indexOf_2 = vars.indexOf(v);
            _builder.append(_indexOf_2, "  ");
            _builder.append(") : ");
            _builder.append(plugFunction, "  ");
            _builder.append("(");
            int _indexOf_3 = vars.indexOf(v);
            _builder.append(_indexOf_3, "  ");
            _builder.append("));");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateEventAccessors(final List<Event> events) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Event event : events) {
        _builder.append("int ");
        String _name = event.getName();
        _builder.append(_name);
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("return m_nParentAdapterListEventID + scm_nEvent");
        String _name_1 = event.getName();
        _builder.append(_name_1, "  ");
        _builder.append("ID;");
        _builder.newLineIfNotEmpty();
        _builder.append("}");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  @Pure
  @Override
  protected AdapterFBType getType() {
    return this.type;
  }
}
