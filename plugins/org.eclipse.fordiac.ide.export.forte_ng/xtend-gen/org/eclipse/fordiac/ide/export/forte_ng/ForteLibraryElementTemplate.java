/**
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Johannes Kepler Unviersity Linz
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
 */
package org.eclipse.fordiac.ide.export.forte_ng;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.ExportTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public abstract class ForteLibraryElementTemplate extends ExportTemplate {
  public static final CharSequence EXPORT_PREFIX = "st_";
  
  public ForteLibraryElementTemplate(final String name, final Path prefix) {
    super(name, prefix);
  }
  
  protected abstract LibraryElement getType();
  
  protected CharSequence getExportPrefix() {
    return ForteLibraryElementTemplate.EXPORT_PREFIX;
  }
  
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
  
  protected CharSequence generateTypeIncludes(final Iterable<VarDeclaration> vars) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<VarDeclaration, String> _function = new Function1<VarDeclaration, String>() {
        public String apply(final VarDeclaration it) {
          return it.getTypeName();
        }
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
      final Function1<VarDeclaration, Boolean> _function_1 = new Function1<VarDeclaration, Boolean>() {
        public Boolean apply(final VarDeclaration it) {
          return Boolean.valueOf(it.isArray());
        }
      };
      boolean _exists = IterableExtensions.<VarDeclaration>exists(vars, _function_1);
      if (_exists) {
        _builder.append("#include \"forte_array.h\"");
        _builder.newLine();
      }
    }
    _builder.append("#include \"forte_array_at.h\"");
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
        CharSequence _exportPrefix = this.getExportPrefix();
        _builder.append(_exportPrefix);
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
            _builder.append("*>((*static_cast<CIEC_ARRAY *>(");
            _builder.append(function, "  ");
            _builder.append("(");
            int _indexOf = vars.indexOf(v);
            _builder.append(_indexOf, "  ");
            _builder.append(")))[0]); //the first element marks the start of the array");
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
  
  protected CharSequence getFORTEString(final String s) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("g_nStringId");
    _builder.append(s);
    return _builder;
  }
  
  protected String getFORTENameList(final List<? extends INamedElement> elements) {
    final Function1<INamedElement, CharSequence> _function = new Function1<INamedElement, CharSequence>() {
      public CharSequence apply(final INamedElement it) {
        return ForteLibraryElementTemplate.this.getFORTEString(it.getName());
      }
    };
    return IterableExtensions.join(ListExtensions.map(elements, _function), ", ");
  }
  
  protected String getFORTETypeList(final List<? extends VarDeclaration> elements) {
    final Function1<VarDeclaration, String> _function = new Function1<VarDeclaration, String>() {
      public String apply(final VarDeclaration it) {
        StringConcatenation _builder = new StringConcatenation();
        {
          boolean _isArray = it.isArray();
          if (_isArray) {
            CharSequence _fORTEString = ForteLibraryElementTemplate.this.getFORTEString("ARRAY");
            _builder.append(_fORTEString);
            _builder.append(", ");
            int _arraySize = it.getArraySize();
            _builder.append(_arraySize);
            _builder.append(", ");
          }
        }
        CharSequence _fORTEString_1 = ForteLibraryElementTemplate.this.getFORTEString(it.getType().getName());
        _builder.append(_fORTEString_1);
        return _builder.toString();
      }
    };
    return IterableExtensions.join(ListExtensions.map(elements, _function), ", ");
  }
}
