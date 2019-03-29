package org.eclipse.fordiac.ide.export.forte_ng;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.ExportTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public abstract class ForteFBTemplate extends ExportTemplate {
  public ForteFBTemplate(final String name, final Path prefix) {
    super(name, prefix);
  }
  
  protected abstract FBType getType();
  
  protected CharSequence generateHeader() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*************************************************************************");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*** FORTE Library Element");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("***");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("***");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*** Name: ");
    String _name = this.getType().getName();
    _builder.append(_name, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*** Description: ");
    String _comment = this.getType().getComment();
    _builder.append(_comment, " ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("*** Version:");
    _builder.newLine();
    {
      EList<VersionInfo> _versionInfo = this.getType().getVersionInfo();
      for(final VersionInfo info : _versionInfo) {
        _builder.append("***     ");
        String _version = info.getVersion();
        _builder.append(_version);
        _builder.append(": ");
        String _date = info.getDate();
        _builder.append(_date);
        _builder.append("/");
        String _author = info.getAuthor();
        _builder.append(_author);
        _builder.append(" - ");
        String _organization = info.getOrganization();
        _builder.append(_organization);
        _builder.append(" - ");
        String _remarks = info.getRemarks();
        _builder.append(_remarks);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append(" ");
    _builder.append("*************************************************************************/");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateIncludeGuardStart() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#ifndef _");
    String _upperCase = this.getType().getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    _builder.append("#define _");
    String _upperCase_1 = this.getType().getName().toUpperCase();
    _builder.append(_upperCase_1);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateIncludeGuardEnd() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#endif // _");
    String _upperCase = this.getType().getName().toUpperCase();
    _builder.append(_upperCase);
    _builder.append("_H_");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
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
  
  protected CharSequence generateTypeIncludes(final Iterable<VarDeclaration> vars) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<VarDeclaration, String> _function = (VarDeclaration it) -> {
        return it.getTypeName();
      };
      Set<String> _set = IterableExtensions.<String>toSet(IterableExtensions.<String>sort(IterableExtensions.<VarDeclaration, String>map(vars, _function)));
      for(final String include : _set) {
        _builder.append("#include \"forte_");
        String _lowerCase = include.toLowerCase();
        _builder.append(_lowerCase);
        _builder.append(".h\"");
        _builder.newLineIfNotEmpty();
        {
          boolean _startsWith = include.startsWith("ANY");
          if (_startsWith) {
            _builder.append("#ERROR type contains variables of type ANY. Please check the usage of these variables as we can not gurantee correct usage on export!");
            _builder.newLine();
          }
        }
      }
    }
    {
      final Function1<VarDeclaration, Boolean> _function_1 = (VarDeclaration it) -> {
        return Boolean.valueOf(it.isArray());
      };
      boolean _exists = IterableExtensions.<VarDeclaration>exists(vars, _function_1);
      if (_exists) {
        _builder.append("#include \"forte_array.h\"");
        _builder.newLine();
      }
    }
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
    {
      boolean _isEmpty_2 = this.getType().getInterfaceList().getEventInputs().isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        {
          EList<Event> _eventInputs = this.getType().getInterfaceList().getEventInputs();
          for(final Event event : _eventInputs) {
            _builder.append("static const TEventID scm_nEvent");
            String _name = event.getName();
            _builder.append(_name);
            _builder.append("ID = ");
            int _indexOf = this.getType().getInterfaceList().getEventInputs().indexOf(event);
            _builder.append(_indexOf);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("static const TDataIOID scm_anEIWith[];");
        _builder.newLine();
        _builder.append("static const TForteInt16 scm_anEIWithIndexes[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anEventInputNames[];");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_3 = this.getType().getInterfaceList().getEventOutputs().isEmpty();
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        {
          EList<Event> _eventOutputs = this.getType().getInterfaceList().getEventOutputs();
          for(final Event event_1 : _eventOutputs) {
            _builder.append("static const TEventID scm_nEvent");
            String _name_1 = event_1.getName();
            _builder.append(_name_1);
            _builder.append("ID = ");
            int _indexOf_1 = this.getType().getInterfaceList().getEventOutputs().indexOf(event_1);
            _builder.append(_indexOf_1);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("static const TDataIOID scm_anEOWith[];");
        _builder.newLine();
        _builder.append("static const TForteInt16 scm_anEOWithIndexes[];");
        _builder.newLine();
        _builder.append("static const CStringDictionary::TStringId scm_anEventOutputNames[];");
        _builder.newLine();
      }
    }
    _builder.newLine();
    {
      if (((!this.getType().getInterfaceList().getSockets().isEmpty()) || (!this.getType().getInterfaceList().getPlugs().isEmpty()))) {
        {
          EList<AdapterDeclaration> _sockets = this.getType().getInterfaceList().getSockets();
          for(final AdapterDeclaration adapter : _sockets) {
            _builder.append("static const int scm_n");
            String _name_2 = adapter.getName();
            _builder.append(_name_2);
            _builder.append("AdpNum = ");
            int _indexOf_2 = this.getType().getInterfaceList().getSockets().indexOf(adapter);
            _builder.append(_indexOf_2);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<AdapterDeclaration> _plugs = this.getType().getInterfaceList().getPlugs();
          for(final AdapterDeclaration adapter_1 : _plugs) {
            _builder.append("static const int scm_n");
            String _name_3 = adapter_1.getName();
            _builder.append(_name_3);
            _builder.append("AdpNum = ");
            int _size = this.getType().getInterfaceList().getSockets().size();
            int _indexOf_3 = this.getType().getInterfaceList().getPlugs().indexOf(adapter_1);
            int _plus = (_size + _indexOf_3);
            _builder.append(_plus);
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.newLine();
        _builder.append("const SAdapterInstanceDef scm_astAdapterInstances[];");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBInterfaceDefinition() {
    final int[] inputWith = ((int[])Conversions.unwrapArray(CollectionLiterals.<Integer>newArrayList(), int.class));
    final int[] inputWithIndexes = ((int[])Conversions.unwrapArray(CollectionLiterals.<Integer>newArrayList(), int.class));
    final Consumer<Event> _function = (Event event) -> {
      boolean _isEmpty = event.getWith().isEmpty();
      if (_isEmpty) {
        ((List<Integer>)Conversions.doWrapArray(inputWithIndexes)).add(Integer.valueOf((-1)));
      } else {
        ((List<Integer>)Conversions.doWrapArray(inputWithIndexes)).add(Integer.valueOf(((List<Integer>)Conversions.doWrapArray(inputWith)).size()));
        EList<With> _with = event.getWith();
        for (final With with : _with) {
          ((List<Integer>)Conversions.doWrapArray(inputWith)).add(Integer.valueOf(this.getType().getInterfaceList().getInputVars().indexOf(with.getVariables())));
        }
        ((List<Integer>)Conversions.doWrapArray(inputWith)).add(Integer.valueOf(255));
      }
    };
    this.getType().getInterfaceList().getEventInputs().forEach(_function);
    final int[] outputWith = ((int[])Conversions.unwrapArray(CollectionLiterals.<Integer>newArrayList(), int.class));
    final int[] outputWithIndexes = ((int[])Conversions.unwrapArray(CollectionLiterals.<Integer>newArrayList(), int.class));
    final Consumer<Event> _function_1 = (Event event) -> {
      boolean _isEmpty = event.getWith().isEmpty();
      if (_isEmpty) {
        ((List<Integer>)Conversions.doWrapArray(outputWithIndexes)).add(Integer.valueOf((-1)));
      } else {
        ((List<Integer>)Conversions.doWrapArray(outputWithIndexes)).add(Integer.valueOf(((List<Integer>)Conversions.doWrapArray(outputWith)).size()));
        EList<With> _with = event.getWith();
        for (final With with : _with) {
          ((List<Integer>)Conversions.doWrapArray(outputWith)).add(Integer.valueOf(this.getType().getInterfaceList().getOutputVars().indexOf(with.getVariables())));
        }
        ((List<Integer>)Conversions.doWrapArray(outputWith)).add(Integer.valueOf(255));
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
        CharSequence _fORTENameList = this.getFORTENameList(this.getType().getInterfaceList().getInputVars());
        _builder.append(_fORTENameList);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName_1 = this.getFBClassName();
        _builder.append(_fBClassName_1);
        _builder.append("::scm_anDataInputTypeIds[] = {");
        CharSequence _fORTETypeList = this.getFORTETypeList(this.getType().getInterfaceList().getInputVars());
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
        CharSequence _fORTENameList_1 = this.getFORTENameList(this.getType().getInterfaceList().getOutputVars());
        _builder.append(_fORTENameList_1);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName_3 = this.getFBClassName();
        _builder.append(_fBClassName_3);
        _builder.append("::scm_anDataOutputTypeIds[] = {");
        CharSequence _fORTETypeList_1 = this.getFORTETypeList(this.getType().getInterfaceList().getOutputVars());
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
        _builder.append("const TDataIOID ");
        CharSequence _fBClassName_4 = this.getFBClassName();
        _builder.append(_fBClassName_4);
        _builder.append("::scm_anEIWith[] = {");
        String _join = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(inputWith)), ", ");
        _builder.append(_join);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const TForteInt16 ");
        CharSequence _fBClassName_5 = this.getFBClassName();
        _builder.append(_fBClassName_5);
        _builder.append("::scm_anEIWithIndexes[] = {");
        String _join_1 = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(inputWithIndexes)), ", ");
        _builder.append(_join_1);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName_6 = this.getFBClassName();
        _builder.append(_fBClassName_6);
        _builder.append("::scm_anEventInputNames[] = {");
        CharSequence _fORTENameList_2 = this.getFORTENameList(this.getType().getInterfaceList().getEventInputs());
        _builder.append(_fORTENameList_2);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      boolean _isEmpty_3 = this.getType().getInterfaceList().getEventOutputs().isEmpty();
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        _builder.append("const TDataIOID ");
        CharSequence _fBClassName_7 = this.getFBClassName();
        _builder.append(_fBClassName_7);
        _builder.append("::scm_anEOWith[] = {");
        String _join_2 = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(outputWith)), ", ");
        _builder.append(_join_2);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const TForteInt16 ");
        CharSequence _fBClassName_8 = this.getFBClassName();
        _builder.append(_fBClassName_8);
        _builder.append("::scm_anEOWithIndexes[] = {");
        String _join_3 = IterableExtensions.join(((Iterable<?>)Conversions.doWrapArray(outputWithIndexes)), ", ");
        _builder.append(_join_3);
        _builder.append("};");
        _builder.newLineIfNotEmpty();
        _builder.append("const CStringDictionary::TStringId ");
        CharSequence _fBClassName_9 = this.getFBClassName();
        _builder.append(_fBClassName_9);
        _builder.append("::scm_anEventOutputNames[] = {");
        CharSequence _fORTENameList_3 = this.getFORTENameList(this.getType().getInterfaceList().getEventOutputs());
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
            boolean _not_4 = (!_isIsInput);
            _builder.append(_not_4, "  ");
            _builder.append("}");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("};");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateFBInterfaceSpecDeclaration() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("static const SFBInterfaceSpec scm_stFBInterfaceSpec;");
    _builder.newLine();
    return _builder;
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
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes");
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
        _builder.append("nullptr, nullptr");
      } else {
        _builder.append("scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes");
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
      boolean _isEmpty_3 = this.getType().getInterfaceList().getInputVars().isEmpty();
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
  
  protected CharSequence generateAccessors(final List<VarDeclaration> vars, final String function) {
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
            _builder.append("*>(static_cast<CIEC_ARRAY *>(");
            _builder.append(function, "  ");
            _builder.append("(");
            int _indexOf = vars.indexOf(v);
            _builder.append(_indexOf, "  ");
            _builder.append("))[0]); //the first element marks the start of the array");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("  ");
            _builder.append("return *static_cast<CIEC_");
            String _typeName_2 = v.getTypeName();
            _builder.append(_typeName_2, "  ");
            _builder.append("*>(");
            _builder.append(function, "  ");
            _builder.append("(");
            int _indexOf_1 = vars.indexOf(v);
            _builder.append(_indexOf_1, "  ");
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
  
  protected CharSequence generateAccessors(final List<AdapterDeclaration> adapters) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterDeclaration adapter : adapters) {
        _builder.append("FORTE_");
        String _typeName = adapter.getTypeName();
        _builder.append(_typeName);
        _builder.append("& ");
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
  
  protected CharSequence getFORTENameList(final List<? extends INamedElement> elements) {
    final Function1<INamedElement, CharSequence> _function = (INamedElement it) -> {
      return this.getFORTEString(it.getName());
    };
    return IterableExtensions.join(ListExtensions.map(elements, _function), ", ");
  }
  
  protected CharSequence getFORTETypeList(final List<? extends VarDeclaration> elements) {
    final Function1<VarDeclaration, String> _function = (VarDeclaration it) -> {
      StringConcatenation _builder = new StringConcatenation();
      {
        boolean _isArray = it.isArray();
        if (_isArray) {
          CharSequence _fORTEString = this.getFORTEString("ARRAY");
          _builder.append(_fORTEString);
          _builder.append(", ");
          int _arraySize = it.getArraySize();
          _builder.append(_arraySize);
          _builder.append(", ");
        }
      }
      CharSequence _fORTEString_1 = this.getFORTEString(it.getType().getName());
      _builder.append(_fORTEString_1);
      return _builder.toString();
    };
    return IterableExtensions.join(ListExtensions.map(elements, _function), ", ");
  }
  
  protected CharSequence getFBClassName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FORTE_");
    String _name = this.getType().getName();
    _builder.append(_name);
    return _builder;
  }
  
  protected CharSequence getFORTEString(final String s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("g_nStringId");
    _builder.append(s);
    return _builder;
  }
}
