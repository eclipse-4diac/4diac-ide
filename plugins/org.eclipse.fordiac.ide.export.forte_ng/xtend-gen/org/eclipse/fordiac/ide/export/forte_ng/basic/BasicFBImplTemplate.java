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
 *     - Add internal var generation, fix adapter generation
 *   Martin Melik Merkumians - adds generation of initial value assignment
 */
package org.eclipse.fordiac.ide.export.forte_ng.basic;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class BasicFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private BasicFBType type;
  
  @Extension
  private STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
  
  public BasicFBImplTemplate(final BasicFBType type, final String name, final Path prefix) {
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
    {
      boolean _isEmpty = this.type.getInternalVars().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        CharSequence _generateInternalVarDefinition = this.generateInternalVarDefinition(this.type);
        _builder.append(_generateInternalVarDefinition);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
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
        EList<VarDeclaration> _inputVars_1 = this.type.getInterfaceList().getInputVars();
        EList<VarDeclaration> _outputVars_1 = this.type.getInterfaceList().getOutputVars();
        Iterable<VarDeclaration> _plus_1 = Iterables.<VarDeclaration>concat(_inputVars_1, _outputVars_1);
        EList<VarDeclaration> _internalVars_1 = this.type.getInternalVars();
        Iterable<VarDeclaration> _plus_2 = Iterables.<VarDeclaration>concat(_plus_1, _internalVars_1);
        CharSequence _generateInitialValueAssignmentDefinition = this.generateInitialValueAssignmentDefinition(_plus_2);
        _builder.append(_generateInitialValueAssignmentDefinition);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    CharSequence _generateAlgorithms = this.generateAlgorithms();
    _builder.append(_generateAlgorithms);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateStates = this.generateStates();
    _builder.append(_generateStates);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateECC = this.generateECC();
    _builder.append(_generateECC);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
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
        _builder.newLine();
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
  
  protected CharSequence generateStates() {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = this.type.getECC().getECState();
      for(final ECState state : _eCState) {
        CharSequence _generateState = this.generateState(state);
        _builder.append(_generateState);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  protected CharSequence generateState(final ECState state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::enterState");
    String _name = state.getName();
    _builder.append(_name);
    _builder.append("(void) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("m_nECCState = scm_nState");
    String _name_1 = state.getName();
    _builder.append(_name_1, "  ");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      EList<ECAction> _eCAction = state.getECAction();
      for(final ECAction action : _eCAction) {
        {
          Algorithm _algorithm = action.getAlgorithm();
          boolean _tripleNotEquals = (_algorithm != null);
          if (_tripleNotEquals) {
            _builder.append("  ");
            _builder.append("alg_");
            String _name_2 = action.getAlgorithm().getName();
            _builder.append(_name_2, "  ");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Event _output = action.getOutput();
          boolean _tripleNotEquals_1 = (_output != null);
          if (_tripleNotEquals_1) {
            _builder.append("  ");
            CharSequence _generateSendEvent = this.generateSendEvent(action.getOutput());
            _builder.append(_generateSendEvent, "  ");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateSendEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("sendOutputEvent(scm_nEvent");
    String _name = event.getName();
    _builder.append(_name);
    _builder.append("ID);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected String getAdapterEventName(final AdapterEvent event) {
    return event.getName().split("\\.")[1];
  }
  
  protected CharSequence _generateSendEvent(final AdapterEvent event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("sendAdapterEvent(scm_n");
    String _name = event.getAdapterDeclaration().getName();
    _builder.append(_name);
    _builder.append("AdpNum, FORTE_");
    String _typeName = event.getAdapterDeclaration().getTypeName();
    _builder.append(_typeName);
    _builder.append("::scm_nEvent");
    String _adapterEventName = this.getAdapterEventName(event);
    _builder.append(_adapterEventName);
    _builder.append("ID);");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence generateECC() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::executeEvent(int pa_nEIID){");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("bool bTransitionCleared;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("do {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bTransitionCleared = true;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("switch(m_nECCState) {");
    _builder.newLine();
    {
      EList<ECState> _eCState = this.type.getECC().getECState();
      for(final ECState state : _eCState) {
        _builder.append("      ");
        _builder.append("case scm_nState");
        String _name = state.getName();
        _builder.append(_name, "      ");
        _builder.append(":");
        _builder.newLineIfNotEmpty();
        {
          EList<ECTransition> _outTransitions = state.getOutTransitions();
          boolean _hasElements = false;
          for(final ECTransition transition : _outTransitions) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("\nelse", "        ");
            }
            {
              if (((transition.getConditionEvent() != null) && (!StringExtensions.isNullOrEmpty(transition.getConditionExpression())))) {
                _builder.append("      ");
                _builder.append("  ");
                _builder.append("if((");
                CharSequence _generateTransitionEvent = this.generateTransitionEvent(transition.getConditionEvent());
                _builder.append(_generateTransitionEvent, "        ");
                _builder.append(" == pa_nEIID) && (");
                CharSequence _generate = this.stAlgorithmFilter.generate(transition.getConditionExpression(), this.type, this.getErrors());
                _builder.append(_generate, "        ");
                _builder.append("))");
                _builder.newLineIfNotEmpty();
              } else {
                Event _conditionEvent = transition.getConditionEvent();
                boolean _tripleNotEquals = (_conditionEvent != null);
                if (_tripleNotEquals) {
                  _builder.append("      ");
                  _builder.append("  ");
                  _builder.append("if(");
                  CharSequence _generateTransitionEvent_1 = this.generateTransitionEvent(transition.getConditionEvent());
                  _builder.append(_generateTransitionEvent_1, "        ");
                  _builder.append(" == pa_nEIID)");
                  _builder.newLineIfNotEmpty();
                } else {
                  boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(transition.getConditionExpression());
                  boolean _not = (!_isNullOrEmpty);
                  if (_not) {
                    _builder.append("      ");
                    _builder.append("  ");
                    _builder.append("if(");
                    CharSequence _generate_1 = this.stAlgorithmFilter.generate(transition.getConditionExpression(), this.type, this.getErrors());
                    _builder.append(_generate_1, "        ");
                    _builder.append(")");
                    _builder.newLineIfNotEmpty();
                  } else {
                    _builder.append("      ");
                    _builder.append("  ");
                    _builder.append("if(1)");
                    _builder.newLine();
                  }
                }
              }
            }
            _builder.append("      ");
            _builder.append("  ");
            _builder.append("  ");
            _builder.append("enterState");
            String _name_1 = transition.getDestination().getName();
            _builder.append(_name_1, "          ");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("      ");
        _builder.append("  ");
        {
          boolean _isEmpty = state.getOutTransitions().isEmpty();
          boolean _not_1 = (!_isEmpty);
          if (_not_1) {
            _builder.append("else");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("      ");
        _builder.append("    ");
        _builder.append("bTransitionCleared  = false; //no transition cleared");
        _builder.newLine();
        _builder.append("      ");
        _builder.append("  ");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("      ");
    _builder.append("default:");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("DEVLOG_ERROR(\"The state is not in the valid range! The state value is: %d. The max value can be: ");
    int _size = this.type.getECC().getECState().size();
    _builder.append(_size, "        ");
    _builder.append(".\", m_nECCState.operator TForteUInt16 ());");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("m_nECCState = 0; // 0 is always the initial state");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("break;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("pa_nEIID = cg_nInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("} while(bTransitionCleared);");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _generateTransitionEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("scm_nEvent");
    String _name = event.getName();
    _builder.append(_name);
    _builder.append("ID");
    return _builder;
  }
  
  protected CharSequence _generateTransitionEvent(final AdapterEvent event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(ForteLibraryElementTemplate.EXPORT_PREFIX);
    String _name = event.getAdapterDeclaration().getName();
    _builder.append(_name);
    _builder.append("().");
    String _adapterEventName = this.getAdapterEventName(event);
    _builder.append(_adapterEventName);
    _builder.append("()");
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
  
  protected CharSequence generateSendEvent(final Event event) {
    if (event instanceof AdapterEvent) {
      return _generateSendEvent((AdapterEvent)event);
    } else if (event != null) {
      return _generateSendEvent(event);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(event).toString());
    }
  }
  
  protected CharSequence generateTransitionEvent(final Event event) {
    if (event instanceof AdapterEvent) {
      return _generateTransitionEvent((AdapterEvent)event);
    } else if (event != null) {
      return _generateTransitionEvent(event);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(event).toString());
    }
  }
  
  @Pure
  @Override
  protected BasicFBType getType() {
    return this.type;
  }
}
