/**
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
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
 *   Ernst Blecha
 *     - Add array-like bitaccess
 *   Martin Melik Merkumians - adds generation of initial value assignment
 */
package org.eclipse.fordiac.ide.export.forte_ng.basic;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class BasicFBHeaderTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private BasicFBType type;
  
  public BasicFBHeaderTemplate(final BasicFBType type, final String name, final Path prefix) {
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
    _builder.append(": public CBasicFB {");
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
        CharSequence _generateInternalVarDelcaration = this.generateInternalVarDelcaration(this.type);
        _builder.append(_generateInternalVarDelcaration);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _inputVars = this.type.getInterfaceList().getInputVars();
      EList<VarDeclaration> _outputVars = this.type.getInterfaceList().getOutputVars();
      Iterable<VarDeclaration> _plus = Iterables.<VarDeclaration>concat(_inputVars, _outputVars);
      EList<VarDeclaration> _internalVars = this.type.getInternalVars();
      boolean _isEmpty_1 = IterableExtensions.isEmpty(Iterables.<VarDeclaration>concat(_plus, _internalVars));
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        CharSequence _generateInitialValueAssignmentDeclaration = this.generateInitialValueAssignmentDeclaration();
        _builder.append(_generateInitialValueAssignmentDeclaration);
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("  ");
    CharSequence _generateAccessors = this.generateAccessors(this.type.getInterfaceList().getInputVars(), "getDI");
    _builder.append(_generateAccessors, "  ");
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
    CharSequence _generateStates = this.generateStates();
    _builder.append(_generateStates, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual void executeEvent(int pa_nEIID);");
    _builder.newLine();
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
    _builder.append("(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :");
    _builder.newLineIfNotEmpty();
    _builder.append("       ");
    _builder.append("CBasicFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, ");
    {
      boolean _isEmpty_2 = this.type.getInternalVars().isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
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
    _builder.append("#include \"basicfb.h\"");
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
    {
      EList<Algorithm> _algorithm = this.type.getAlgorithm();
      for(final Algorithm alg : _algorithm) {
        CharSequence _generateAlgorithm = this.generateAlgorithm(alg);
        _builder.append(_generateAlgorithm);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence _generateAlgorithm(final Algorithm alg) {
    List<String> _errors = this.getErrors();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Cannot export algorithm ");
    Class<? extends Algorithm> _class = alg.getClass();
    _builder.append(_class);
    _errors.add(_builder.toString());
    return "";
  }
  
  protected CharSequence _generateAlgorithm(final OtherAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void alg_");
    String _name = alg.getName();
    _builder.append(_name);
    _builder.append("(void);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateAlgorithm(final STAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void alg_");
    String _name = alg.getName();
    _builder.append(_name);
    _builder.append("(void);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateStates() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = this.type.getECC().getECState();
      for(final ECState state : _eCState) {
        _builder.append("static const TForteInt16 scm_nState");
        String _name = state.getName();
        _builder.append(_name);
        _builder.append(" = ");
        int _indexOf = this.type.getECC().getECState().indexOf(state);
        _builder.append(_indexOf);
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    {
      EList<ECState> _eCState_1 = this.type.getECC().getECState();
      for(final ECState state_1 : _eCState_1) {
        CharSequence _generateState = this.generateState(state_1);
        _builder.append(_generateState);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateState(final ECState state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void enterState");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append("(void);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateAlgorithm(final Algorithm alg) {
    if (alg instanceof OtherAlgorithm) {
      return _generateAlgorithm((OtherAlgorithm)alg);
    } else if (alg instanceof STAlgorithm) {
      return _generateAlgorithm((STAlgorithm)alg);
    } else if (alg != null) {
      return _generateAlgorithm(alg);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(alg).toString());
    }
  }
  
  @Pure
  @Override
  protected BasicFBType getType() {
    return this.type;
  }
}
