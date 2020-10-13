/**
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Johannes Kepler Unviersity Linz
 * 				 2020 TU Wien
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted base class for all types from fbtemplate
 *   Martin Melik Merkumians - adds clause to prevent generation of zero size arrays
 *   Martin Melik Merkumians - adds generation of initial value assignment
 */
package org.eclipse.fordiac.ide.export.forte_ng;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public abstract class ForteFBTemplate extends ForteLibraryElementTemplate {
  public ForteFBTemplate(final String name, final Path prefix) {
    super(name, prefix);
  }
  
  @Override
  protected abstract FBType getType();
  
  protected CharSequence generateHeaderIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    EList<VarDeclaration> _inputVars = this.getType().getInterfaceList().getInputVars();
    EList<VarDeclaration> _outputVars = this.getType().getInterfaceList().getOutputVars();
    CharSequence _generateTypeIncludes = this.generateTypeIncludes(Iterables.<VarDeclaration>concat(_inputVars, _outputVars));
    _builder.append(_generateTypeIncludes);
    _builder.newLineIfNotEmpty();
    EList<AdapterDeclaration> _sockets = this.getType().getInterfaceList().getSockets();
    EList<AdapterDeclaration> _plugs = this.getType().getInterfaceList().getPlugs();
    CharSequence _generateAdapterIncludes = this.generateAdapterIncludes(Iterables.<AdapterDeclaration>concat(_sockets, _plugs));
    _builder.append(_generateAdapterIncludes);
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
  
  protected CharSequence generateImplIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"");
    String _name = this.getType().getName();
    _builder.append(_name);
    _builder.append(".h\"");
    _builder.newLineIfNotEmpty();
    _builder.append("#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP");
    _builder.newLine();
    _builder.append("#include \"");
    String _name_1 = this.getType().getName();
    _builder.append(_name_1);
    _builder.append("_gen.cpp\"");
    _builder.newLineIfNotEmpty();
    _builder.append("#endif");
    _builder.newLine();
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
  
  protected CharSequence generateAdapterIncludes(final Iterable<AdapterDeclaration> vars) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<AdapterDeclaration, String> _function = (AdapterDeclaration it) -> {
        return it.getTypeName();
      };
      Set<String> _set = IterableExtensions.<String>toSet(IterableExtensions.<String>sort(IterableExtensions.<AdapterDeclaration, String>map(vars, _function)));
      for(final String include : _set) {
        _builder.append("#include \"");
        _builder.append(include);
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DECLARE_FIRMWARE_FB(");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateFBDefinition() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DEFINE_FIRMWARE_FB(");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(", ");
    CharSequence _fORTEString = this.getFORTEString(this.getType().getName());
    _builder.append(_fORTEString);
    _builder.append(")");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateFBInterfaceDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.getType().getInterfaceList().getInputVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("static const CStringDictionary::TStringId scm_anDataInputNames[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anDataInputTypeIds[];");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_1 = this.getType().getInterfaceList().getOutputVars().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("static const CStringDictionary::TStringId scm_anDataOutputNames[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anDataOutputTypeIds[];");
        _builder.newLine();
      }
    }
    _builder.newLine();
    CharSequence _generateFBEventInputInterfaceDecl = this.generateFBEventInputInterfaceDecl();
    _builder.append(_generateFBEventInputInterfaceDecl);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBEventOutputInterfaceDecl = this.generateFBEventOutputInterfaceDecl();
    _builder.append(_generateFBEventOutputInterfaceDecl);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      if (((!this.getType().getInterfaceList().getSockets().isEmpty()) || (!this.getType().getInterfaceList().getPlugs().isEmpty()))) {
        {
          EList<AdapterDeclaration> _sockets = this.getType().getInterfaceList().getSockets();
          for(final AdapterDeclaration adapter : _sockets) {
            _builder.append("static const int scm_n");
            String _name = adapter.getName();
            _builder.append(_name);
            _builder.append("AdpNum = ");
            int _indexOf = this.getType().getInterfaceList().getSockets().indexOf(adapter);
            _builder.append(_indexOf);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<AdapterDeclaration> _plugs = this.getType().getInterfaceList().getPlugs();
          for(final AdapterDeclaration adapter_1 : _plugs) {
            _builder.append("static const int scm_n");
            String _name_1 = adapter_1.getName();
            _builder.append(_name_1);
            _builder.append("AdpNum = ");
            int _size = this.getType().getInterfaceList().getSockets().size();
            int _indexOf_1 = this.getType().getInterfaceList().getPlugs().indexOf(adapter_1);
            int _plus = (_size + _indexOf_1);
            _builder.append(_plus);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("static const SAdapterInstanceDef scm_astAdapterInstances[];");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBEventOutputInterfaceDecl() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.getType().getInterfaceList().getEventOutputs().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.newLineIfNotEmpty();
        CharSequence _generateEventConstants = this.generateEventConstants(this.getType().getInterfaceList().getEventOutputs());
        _builder.append(_generateEventConstants);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        {
          boolean _hasOutputWith = this.hasOutputWith();
          if (_hasOutputWith) {
            _builder.append(" static const TDataIOID scm_anEOWith[]; ");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("static const TForteInt16 scm_anEOWithIndexes[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anEventOutputNames[];");
        _builder.newLine();
        _builder.append("\t\t");
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBEventInputInterfaceDecl() {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = this.getType().getInterfaceList().getEventInputs().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.newLineIfNotEmpty();
        CharSequence _generateEventConstants = this.generateEventConstants(this.getType().getInterfaceList().getEventInputs());
        _builder.append(_generateEventConstants);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        {
          boolean _hasInputWith = this.hasInputWith();
          if (_hasInputWith) {
            _builder.append(" static const TDataIOID scm_anEIWith[];");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("static const TForteInt16 scm_anEIWithIndexes[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anEventInputNames[];");
        _builder.newLine();
        _builder.append("\t\t");
      }
    }
    return _builder;
  }
  
  protected CharSequence generateEventConstants(final List<Event> events) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Event event : events) {
        _builder.newLineIfNotEmpty();
        _builder.append("static const TEventID scm_nEvent");
        String _name = event.getName();
        _builder.append(_name);
        _builder.append("ID = ");
        int _indexOf = events.indexOf(event);
        _builder.append(_indexOf);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBInterfaceDefinition() {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Object> inputWith = CollectionLiterals.<Object>newArrayList();
      final ArrayList<Object> inputWithIndexes = CollectionLiterals.<Object>newArrayList();
      final Consumer<Event> _function = (Event event) -> {
        boolean _isEmpty = event.getWith().isEmpty();
        if (_isEmpty) {
          inputWithIndexes.add(Integer.valueOf((-1)));
        } else {
          inputWithIndexes.add(Integer.valueOf(inputWith.size()));
          EList<With> _with = event.getWith();
          for (final With with : _with) {
            inputWith.add(Integer.valueOf(this.getType().getInterfaceList().getInputVars().indexOf(with.getVariables())));
          }
          inputWith.add(Integer.valueOf(255));
        }
      };
      this.getType().getInterfaceList().getEventInputs().forEach(_function);
      final ArrayList<Object> outputWith = CollectionLiterals.<Object>newArrayList();
      final ArrayList<Object> outputWithIndexes = CollectionLiterals.<Object>newArrayList();
      final Consumer<Event> _function_1 = (Event event) -> {
        boolean _isEmpty = event.getWith().isEmpty();
        if (_isEmpty) {
          outputWithIndexes.add(Integer.valueOf((-1)));
        } else {
          outputWithIndexes.add(Integer.valueOf(outputWith.size()));
          EList<With> _with = event.getWith();
          for (final With with : _with) {
            outputWith.add(Integer.valueOf(this.getType().getInterfaceList().getOutputVars().indexOf(with.getVariables())));
          }
          outputWith.add(Integer.valueOf(255));
        }
      };
      this.getType().getInterfaceList().getEventOutputs().forEach(_function_1);
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _isEmpty = this.getType().getInterfaceList().getInputVars().isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName = this.getFBClassName();
          _builder.append(_fBClassName);
          _builder.append("::scm_anDataInputNames[] = {");
          String _fORTENameList = this.getFORTENameList(this.getType().getInterfaceList().getInputVars());
          _builder.append(_fORTENameList);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName_1 = this.getFBClassName();
          _builder.append(_fBClassName_1);
          _builder.append("::scm_anDataInputTypeIds[] = {");
          String _fORTETypeList = this.getFORTETypeList(this.getType().getInterfaceList().getInputVars());
          _builder.append(_fORTETypeList);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        boolean _isEmpty_1 = this.getType().getInterfaceList().getOutputVars().isEmpty();
        boolean _not_1 = (!_isEmpty_1);
        if (_not_1) {
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName_2 = this.getFBClassName();
          _builder.append(_fBClassName_2);
          _builder.append("::scm_anDataOutputNames[] = {");
          String _fORTENameList_1 = this.getFORTENameList(this.getType().getInterfaceList().getOutputVars());
          _builder.append(_fORTENameList_1);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
          _builder.newLine();
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName_3 = this.getFBClassName();
          _builder.append(_fBClassName_3);
          _builder.append("::scm_anDataOutputTypeIds[] = {");
          String _fORTETypeList_1 = this.getFORTETypeList(this.getType().getInterfaceList().getOutputVars());
          _builder.append(_fORTETypeList_1);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        boolean _isEmpty_2 = this.getType().getInterfaceList().getEventInputs().isEmpty();
        boolean _not_2 = (!_isEmpty_2);
        if (_not_2) {
          {
            boolean _isEmpty_3 = inputWith.isEmpty();
            boolean _not_3 = (!_isEmpty_3);
            if (_not_3) {
              _builder.append("const TDataIOID ");
              CharSequence _fBClassName_4 = this.getFBClassName();
              _builder.append(_fBClassName_4);
              _builder.append("::scm_anEIWith[] = {");
              String _join = IterableExtensions.join(inputWith, ", ");
              _builder.append(_join);
              _builder.append("};");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("const TForteInt16 ");
          CharSequence _fBClassName_5 = this.getFBClassName();
          _builder.append(_fBClassName_5);
          _builder.append("::scm_anEIWithIndexes[] = {");
          String _join_1 = IterableExtensions.join(inputWithIndexes, ", ");
          _builder.append(_join_1);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName_6 = this.getFBClassName();
          _builder.append(_fBClassName_6);
          _builder.append("::scm_anEventInputNames[] = {");
          String _fORTENameList_2 = this.getFORTENameList(this.getType().getInterfaceList().getEventInputs());
          _builder.append(_fORTENameList_2);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        boolean _isEmpty_4 = this.getType().getInterfaceList().getEventOutputs().isEmpty();
        boolean _not_4 = (!_isEmpty_4);
        if (_not_4) {
          {
            boolean _isEmpty_5 = outputWith.isEmpty();
            boolean _not_5 = (!_isEmpty_5);
            if (_not_5) {
              _builder.append("const TDataIOID ");
              CharSequence _fBClassName_7 = this.getFBClassName();
              _builder.append(_fBClassName_7);
              _builder.append("::scm_anEOWith[] = {");
              String _join_2 = IterableExtensions.join(outputWith, ", ");
              _builder.append(_join_2);
              _builder.append("};");
              _builder.newLineIfNotEmpty();
            }
          }
          _builder.append("const TForteInt16 ");
          CharSequence _fBClassName_8 = this.getFBClassName();
          _builder.append(_fBClassName_8);
          _builder.append("::scm_anEOWithIndexes[] = {");
          String _join_3 = IterableExtensions.join(outputWithIndexes, ", ");
          _builder.append(_join_3);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
          _builder.append("const CStringDictionary::TStringId ");
          CharSequence _fBClassName_9 = this.getFBClassName();
          _builder.append(_fBClassName_9);
          _builder.append("::scm_anEventOutputNames[] = {");
          String _fORTENameList_3 = this.getFORTENameList(this.getType().getInterfaceList().getEventOutputs());
          _builder.append(_fORTENameList_3);
          _builder.append("};");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      {
        if (((!this.getType().getInterfaceList().getSockets().isEmpty()) || (!this.getType().getInterfaceList().getPlugs().isEmpty()))) {
          _builder.append("const SAdapterInstanceDef ");
          CharSequence _fBClassName_10 = this.getFBClassName();
          _builder.append(_fBClassName_10);
          _builder.append("::scm_astAdapterInstances[] = {");
          _builder.newLineIfNotEmpty();
          _builder.append("  ");
          {
            EList<AdapterDeclaration> _sockets = this.getType().getInterfaceList().getSockets();
            EList<AdapterDeclaration> _plugs = this.getType().getInterfaceList().getPlugs();
            Iterable<AdapterDeclaration> _plus = Iterables.<AdapterDeclaration>concat(_sockets, _plugs);
            boolean _hasElements = false;
            for(final AdapterDeclaration adapter : _plus) {
              if (!_hasElements) {
                _hasElements = true;
              } else {
                _builder.appendImmediate(",\n", "  ");
              }
              _builder.append("{");
              CharSequence _fORTEString = this.getFORTEString(adapter.getTypeName());
              _builder.append(_fORTEString, "  ");
              _builder.append(", ");
              CharSequence _fORTEString_1 = this.getFORTEString(adapter.getName());
              _builder.append(_fORTEString_1, "  ");
              _builder.append(", ");
              boolean _isIsInput = adapter.isIsInput();
              boolean _not_6 = (!_isIsInput);
              _builder.append(_not_6, "  ");
              _builder.append("}");
            }
          }
          _builder.newLineIfNotEmpty();
          _builder.append("};");
          _builder.newLine();
        }
      }
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  protected CharSequence generateFBInterfaceSpecDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static const SFBInterfaceSpec scm_stFBInterfaceSpec;");
    _builder.newLine();
    return _builder;
  }
  
  protected boolean hasInputWith() {
    final Function1<Event, Boolean> _function = (Event it) -> {
      boolean _isEmpty = it.getWith().isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    return IterableExtensions.<Event>exists(this.getType().getInterfaceList().getEventInputs(), _function);
  }
  
  protected boolean hasOutputWith() {
    final Function1<Event, Boolean> _function = (Event it) -> {
      boolean _isEmpty = it.getWith().isEmpty();
      return Boolean.valueOf((!_isEmpty));
    };
    return IterableExtensions.<Event>exists(this.getType().getInterfaceList().getEventOutputs(), _function);
  }
  
  protected CharSequence generateFBInterfaceSpecDefinition() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("const SFBInterfaceSpec ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::scm_stFBInterfaceSpec = {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size = this.getType().getInterfaceList().getEventInputs().size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty = this.getType().getInterfaceList().getEventInputs().isEmpty();
      if (_isEmpty) {
        _builder.append("nullptr, nullptr, nullptr");
      } else {
        _builder.append("scm_anEventInputNames, ");
        {
          boolean _hasInputWith = this.hasInputWith();
          if (_hasInputWith) {
            _builder.append("scm_anEIWith");
          } else {
            _builder.append("nullptr");
          }
        }
        _builder.append(", scm_anEIWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_1 = this.getType().getInterfaceList().getEventOutputs().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_1 = this.getType().getInterfaceList().getEventOutputs().isEmpty();
      if (_isEmpty_1) {
        _builder.append("nullptr, nullptr, nullptr");
      } else {
        _builder.append("scm_anEventOutputNames, ");
        {
          boolean _hasOutputWith = this.hasOutputWith();
          if (_hasOutputWith) {
            _builder.append("scm_anEOWith");
          } else {
            _builder.append("nullptr");
          }
        }
        _builder.append(", scm_anEOWithIndexes");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_2 = this.getType().getInterfaceList().getInputVars().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_2 = this.getType().getInterfaceList().getInputVars().isEmpty();
      if (_isEmpty_2) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataInputNames, scm_anDataInputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_3 = this.getType().getInterfaceList().getOutputVars().size();
    _builder.append(_size_3, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_3 = this.getType().getInterfaceList().getOutputVars().isEmpty();
      if (_isEmpty_3) {
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anDataOutputNames, scm_anDataOutputTypeIds");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_4 = this.getType().getInterfaceList().getPlugs().size();
    int _size_5 = this.getType().getInterfaceList().getSockets().size();
    int _plus = (_size_4 + _size_5);
    _builder.append(_plus, "  ");
    _builder.append(", ");
    {
      if (((!this.getType().getInterfaceList().getSockets().isEmpty()) || (!this.getType().getInterfaceList().getPlugs().isEmpty()))) {
        _builder.append("scm_astAdapterInstances");
      } else {
        _builder.append("nullptr");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateInternalVarDelcaration(final BaseFBType baseFBType) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = baseFBType.getInternalVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("static const CStringDictionary::TStringId scm_anInternalsNames[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anInternalsTypeIds[];");
        _builder.newLine();
        _builder.append("static const SInternalVarsInformation scm_stInternalVars;");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateInitialValueAssignmentDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("virtual void setInitialValues();");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateInitialValueAssignmentDefinition(final Iterable<VarDeclaration> declarationList) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void FORTE_");
    String _name = this.getType().getName();
    _builder.append(_name);
    _builder.append("::setInitialValues() {");
    _builder.newLineIfNotEmpty();
    {
      for(final VarDeclaration variable : declarationList) {
        {
          if (((null != variable.getValue()) && (!variable.getValue().getValue().isEmpty()))) {
            _builder.append("  ");
            _builder.append(ForteLibraryElementTemplate.EXPORT_PREFIX, "  ");
            CharSequence _generateInitialAssignment = this.generateInitialAssignment(variable);
            _builder.append(_generateInitialAssignment, "  ");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateInitialAssignment(final VarDeclaration variable) {
    CharSequence _switchResult = null;
    String _typeName = variable.getTypeName();
    if (_typeName != null) {
      switch (_typeName) {
        case "STRING":
          StringConcatenation _builder = new StringConcatenation();
          String _name = variable.getName();
          _builder.append(_name);
          _builder.append("() = \"");
          String _value = variable.getValue().getValue();
          _builder.append(_value);
          _builder.append("\";");
          _switchResult = _builder;
          break;
        case "WSTRING":
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name_1 = variable.getName();
          _builder_1.append(_name_1);
          _builder_1.append("() = \"");
          String _value_1 = variable.getValue().getValue();
          _builder_1.append(_value_1);
          _builder_1.append("\";");
          _switchResult = _builder_1;
          break;
        case "ARRAY":
          StringConcatenation _builder_2 = new StringConcatenation();
          String _name_2 = variable.getName();
          _builder_2.append(_name_2);
          _builder_2.append("().fromString(\"");
          String _value_2 = variable.getValue().getValue();
          _builder_2.append(_value_2);
          _builder_2.append("\");");
          _switchResult = _builder_2;
          break;
        case "TIME":
          StringConcatenation _builder_3 = new StringConcatenation();
          String _name_3 = variable.getName();
          _builder_3.append(_name_3);
          _builder_3.append("().fromString(\"");
          String _value_3 = variable.getValue().getValue();
          _builder_3.append(_value_3);
          _builder_3.append("\");");
          _switchResult = _builder_3;
          break;
        case "DATE":
          StringConcatenation _builder_4 = new StringConcatenation();
          String _name_4 = variable.getName();
          _builder_4.append(_name_4);
          _builder_4.append("().fromString(\"");
          String _value_4 = variable.getValue().getValue();
          _builder_4.append(_value_4);
          _builder_4.append("\");");
          _switchResult = _builder_4;
          break;
        case "TIME:OF_DAY":
          StringConcatenation _builder_5 = new StringConcatenation();
          String _name_5 = variable.getName();
          _builder_5.append(_name_5);
          _builder_5.append("().fromString(\"");
          String _value_5 = variable.getValue().getValue();
          _builder_5.append(_value_5);
          _builder_5.append("\");");
          _switchResult = _builder_5;
          break;
        case "DATE_AND_TIME":
          StringConcatenation _builder_6 = new StringConcatenation();
          String _name_6 = variable.getName();
          _builder_6.append(_name_6);
          _builder_6.append("().fromString(\"");
          String _value_6 = variable.getValue().getValue();
          _builder_6.append(_value_6);
          _builder_6.append("\");");
          _switchResult = _builder_6;
          break;
        case "BOOL":
          StringConcatenation _builder_7 = new StringConcatenation();
          String _name_7 = variable.getName();
          _builder_7.append(_name_7);
          _builder_7.append("() = \"");
          String _lowerCase = variable.getValue().getValue().toLowerCase();
          _builder_7.append(_lowerCase);
          _builder_7.append("\";");
          _switchResult = _builder_7;
          break;
        default:
          StringConcatenation _builder_8 = new StringConcatenation();
          String _name_8 = variable.getName();
          _builder_8.append(_name_8);
          _builder_8.append("() = ");
          String _value_7 = variable.getValue().getValue();
          _builder_8.append(_value_7);
          _builder_8.append(";");
          _switchResult = _builder_8;
          break;
      }
    } else {
      StringConcatenation _builder_8 = new StringConcatenation();
      String _name_8 = variable.getName();
      _builder_8.append(_name_8);
      _builder_8.append("() = ");
      String _value_7 = variable.getValue().getValue();
      _builder_8.append(_value_7);
      _builder_8.append(";");
      _switchResult = _builder_8;
    }
    return _switchResult;
  }
  
  protected CharSequence generateInternalVarDefinition(final BaseFBType baseFBType) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isEmpty = baseFBType.getInternalVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName = this.getFBClassName();
        _builder.append(_fBClassName);
        _builder.append("::scm_anInternalsNames[] = {");
        String _fORTENameList = this.getFORTENameList(baseFBType.getInternalVars());
        _builder.append(_fORTENameList);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName_1 = this.getFBClassName();
        _builder.append(_fBClassName_1);
        _builder.append("::scm_anInternalsTypeIds[] = {");
        String _fORTETypeList = this.getFORTETypeList(baseFBType.getInternalVars());
        _builder.append(_fORTETypeList);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const SInternalVarsInformation ");
        CharSequence _fBClassName_2 = this.getFBClassName();
        _builder.append(_fBClassName_2);
        _builder.append("::scm_stInternalVars = {");
        int _size = baseFBType.getInternalVars().size();
        _builder.append(_size);
        _builder.append(", scm_anInternalsNames, scm_anInternalsTypeIds};");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateAccessors(final List<AdapterDeclaration> adapters) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterDeclaration adapter : adapters) {
        _builder.append("FORTE_");
        String _typeName = adapter.getTypeName();
        _builder.append(_typeName);
        _builder.append("& ");
        _builder.append(ForteLibraryElementTemplate.EXPORT_PREFIX);
        String _name = adapter.getName();
        _builder.append(_name);
        _builder.append("() {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        _builder.append("return (*static_cast<FORTE_");
        String _typeName_1 = adapter.getTypeName();
        _builder.append(_typeName_1, "  ");
        _builder.append("*>(m_apoAdapters[");
        int _indexOf = adapters.indexOf(adapter);
        _builder.append(_indexOf, "  ");
        _builder.append("]));");
        _builder.newLineIfNotEmpty();
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence getFBClassName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FORTE_");
    String _name = this.getType().getName();
    _builder.append(_name);
    return _builder;
  }
  
  protected CharSequence generateBasicFBDataArray(final BaseFBType baseType) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FORTE_BASIC_FB_DATA_ARRAY(");
    int _size = baseType.getInterfaceList().getEventOutputs().size();
    _builder.append(_size);
    _builder.append(", ");
    int _size_1 = baseType.getInterfaceList().getInputVars().size();
    _builder.append(_size_1);
    _builder.append(", ");
    int _size_2 = baseType.getInterfaceList().getOutputVars().size();
    _builder.append(_size_2);
    _builder.append(", ");
    int _size_3 = baseType.getInternalVars().size();
    _builder.append(_size_3);
    _builder.append(", ");
    int _size_4 = this.getType().getInterfaceList().getSockets().size();
    int _size_5 = baseType.getInterfaceList().getPlugs().size();
    int _plus = (_size_4 + _size_5);
    _builder.append(_plus);
    _builder.append(");");
    return _builder;
  }
}
