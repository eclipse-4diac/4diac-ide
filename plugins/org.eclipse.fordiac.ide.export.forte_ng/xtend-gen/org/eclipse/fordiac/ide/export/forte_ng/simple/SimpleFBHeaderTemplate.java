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
 *     - Add internal var generation
 */
package org.eclipse.fordiac.ide.export.forte_ng.simple;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class SimpleFBHeaderTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private SimpleFBType type;
  
  public SimpleFBHeaderTemplate(final SimpleFBType type, final String name, final Path prefix) {
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
    _builder.append(": public CSimpleFB {");
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
    {
      boolean _isEmpty = this.type.getInternalVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("        ");
        CharSequence _generateInternalVarDelcaration = this.generateInternalVarDelcaration(this.type);
        _builder.append(_generateInternalVarDelcaration, "        ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    _builder.append("          ");
    CharSequence _generateAccessors = this.generateAccessors(this.type.getInterfaceList().getInputVars(), "getDI");
    _builder.append(_generateAccessors, "          ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateAccessors_1 = this.generateAccessors(this.type.getInterfaceList().getOutputVars(), "getDO");
    _builder.append(_generateAccessors_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateAccessors_2 = this.generateAccessors(this.type.getInternalVars(), "getVarInternal");
    _builder.append(_generateAccessors_2, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    EList<AdapterDeclaration> _sockets = this.type.getInterfaceList().getSockets();
    EList<AdapterDeclaration> _plugs = this.type.getInterfaceList().getPlugs();
    CharSequence _generateAccessors_3 = this.generateAccessors(IterableExtensions.<AdapterDeclaration>toList(Iterables.<AdapterDeclaration>concat(_sockets, _plugs)));
    _builder.append(_generateAccessors_3, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateAlgorithms = this.generateAlgorithms();
    _builder.append(_generateAlgorithms, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateBasicFBDataArray = this.generateBasicFBDataArray(this.type);
    _builder.append(_generateBasicFBDataArray, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _fBClassName_1 = this.getFBClassName();
    _builder.append(_fBClassName_1, "  ");
    _builder.append("(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) : ");
    _builder.newLineIfNotEmpty();
    _builder.append("       ");
    _builder.append("CSimpleFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, ");
    {
      boolean _isEmpty_1 = this.type.getInternalVars().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("&scm_stInternalVars");
      } else {
        _builder.append("nullptr");
      }
    }
    _builder.append(", m_anFBConnData, m_anFBVarsData) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("};");
    _builder.newLine();
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
    _builder.append("#include \"simplefb.h\"");
    _builder.newLine();
    EList<VarDeclaration> _inputVars = this.type.getInterfaceList().getInputVars();
    EList<VarDeclaration> _outputVars = this.type.getInterfaceList().getOutputVars();
    Iterable<VarDeclaration> _plus = Iterables.<VarDeclaration>concat(_inputVars, _outputVars);
    EList<VarDeclaration> _internalVars = this.type.getInternalVars();
    CharSequence _generateTypeIncludes = this.generateTypeIncludes(Iterables.<VarDeclaration>concat(_plus, _internalVars));
    _builder.append(_generateTypeIncludes);
    _builder.newLineIfNotEmpty();
    EList<AdapterDeclaration> _sockets = this.type.getInterfaceList().getSockets();
    EList<AdapterDeclaration> _plugs = this.type.getInterfaceList().getPlugs();
    CharSequence _generateAdapterIncludes = this.generateAdapterIncludes(Iterables.<AdapterDeclaration>concat(_sockets, _plugs));
    _builder.append(_generateAdapterIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CompilerInfo _compilerInfo = this.type.getCompilerInfo();
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
  
  @Pure
  @Override
  protected SimpleFBType getType() {
    return this.type;
  }
}
