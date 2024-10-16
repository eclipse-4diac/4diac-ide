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
package org.eclipse.fordiac.ide.export.forte_ng.simple;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class SimpleFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private SimpleFBType type;
  
  @Extension
  private STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
  
  public SimpleFBImplTemplate(final SimpleFBType type, final String name, final Path prefix) {
    super(name, prefix, "CSimpleFB");
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
    {
      boolean _isEmpty = this.type.getInternalVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("        ");
        CharSequence _generateInternalVarDefinition = this.generateInternalVarDefinition(this.type);
        _builder.append(_generateInternalVarDefinition, "        ");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty_1 = this.type.getInternalFbs().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.newLine();
        CharSequence _generateInteralFbDeclarations = this.generateInteralFbDeclarations(this.type);
        _builder.append(_generateInteralFbDeclarations);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _inputVars = this.type.getInterfaceList().getInputVars();
      EList<VarDeclaration> _outputVars = this.type.getInterfaceList().getOutputVars();
      Iterable<VarDeclaration> _plus = Iterables.<VarDeclaration>concat(_inputVars, _outputVars);
      EList<VarDeclaration> _internalVars = this.type.getInternalVars();
      boolean _isEmpty_2 = IterableExtensions.isEmpty(Iterables.<VarDeclaration>concat(_plus, _internalVars));
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        EList<VarDeclaration> _inputVars_1 = this.type.getInterfaceList().getInputVars();
        EList<VarDeclaration> _outputVars_1 = this.type.getInterfaceList().getOutputVars();
        Iterable<VarDeclaration> _plus_1 = Iterables.<VarDeclaration>concat(_inputVars_1, _outputVars_1);
        EList<VarDeclaration> _internalVars_1 = this.type.getInternalVars();
        Iterable<VarDeclaration> _plus_2 = Iterables.<VarDeclaration>concat(_plus_1, _internalVars_1);
        CharSequence _generateInitialValueAssignmentDefinition = this.generateInitialValueAssignmentDefinition(_plus_2);
        _builder.append(_generateInitialValueAssignmentDefinition);
        _builder.newLineIfNotEmpty();
      }
    }
    CharSequence _generateAlgorithms = this.generateAlgorithms();
    _builder.append(_generateAlgorithms);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateAlgorithms() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateAlgorithm = this.generateAlgorithm(this.type.getAlgorithm());
    _builder.append(_generateAlgorithm);
    _builder.newLineIfNotEmpty();
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
  
  protected CharSequence _generateAlgorithm(final STAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::alg_");
    String _name = alg.getName();
    _builder.append(_name);
    _builder.append("(void) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generate = this.stAlgorithmFilter.generate(alg, this.getErrors());
    _builder.append(_generate, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateAlgorithm(final OtherAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::alg_");
    String _name = alg.getName();
    _builder.append(_name);
    _builder.append("(void) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("#pragma GCC warning \"Algorithm of type: \'");
    String _language = alg.getLanguage();
    _builder.append(_language, "  ");
    _builder.append("\' may lead to unexpected results!\"");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("#pragma message (\"warning Algorithm of type: \'");
    String _language_1 = alg.getLanguage();
    _builder.append(_language_1, "  ");
    _builder.append("\' may lead to unexpected results!\")");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    String _text = alg.getText();
    _builder.append(_text, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
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
  protected SimpleFBType getType() {
    return this.type;
  }
}
