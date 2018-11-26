/**
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_lua.filter;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants;
import org.eclipse.fordiac.ide.export.forte_lua.filter.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class BasicFBFilter {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<String> errors = new ArrayList<String>();
  
  private STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
  
  public CharSequence lua(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _luaConstants = LuaConstants.luaConstants(type);
    _builder.append(_luaConstants);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaAlgorithms = this.luaAlgorithms(type);
    _builder.append(_luaAlgorithms);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaStates = this.luaStates(type.getECC());
    _builder.append(_luaStates);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaECC = this.luaECC(type.getECC(), this.getVariables(type));
    _builder.append(_luaECC);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaInterfaceSpec = LuaConstants.luaInterfaceSpec(type.getInterfaceList());
    _builder.append(_luaInterfaceSpec);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaInternalVarsInformation = LuaConstants.luaInternalVarsInformation(type);
    _builder.append(_luaInternalVarsInformation);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("return {ECC = executeEvent, interfaceSpec = interfaceSpec, internalVarsInformation = internalVarsInformation}");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence luaECC(final ECC ecc, final Iterable<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local function transition(fb, id)");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _luaFBVariablesPrefix = LuaConstants.luaFBVariablesPrefix(variables);
    _builder.append(_luaFBVariablesPrefix, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaTransitions = this.luaTransitions(ecc);
    _builder.append(_luaTransitions, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("end");
    _builder.newLine();
    _builder.newLine();
    _builder.append("local function executeEvent(fb, id)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("local modified = transition(fb, id)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("while modified do");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("modified = transition(fb, -1)");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }
  
  private Iterable<VarDeclaration> getVariables(final BasicFBType type) {
    EList<VarDeclaration> _inputVars = type.getInterfaceList().getInputVars();
    EList<VarDeclaration> _outputVars = type.getInterfaceList().getOutputVars();
    Iterable<VarDeclaration> _plus = Iterables.<VarDeclaration>concat(_inputVars, _outputVars);
    EList<VarDeclaration> _internalVars = type.getInternalVars();
    return Iterables.<VarDeclaration>concat(_plus, _internalVars);
  }
  
  private CharSequence luaTransitions(final ECC ecc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = ecc.getECState();
      boolean _hasElements = false;
      for(final ECState state : _eCState) {
        if (!_hasElements) {
          _hasElements = true;
          _builder.append("if ");
        } else {
          _builder.appendImmediate("\nelseif ", "");
        }
        CharSequence _luaStateName = LuaConstants.luaStateName(state);
        _builder.append(_luaStateName);
        _builder.append(" == ");
        CharSequence _luaStateVariable = LuaConstants.luaStateVariable();
        _builder.append(_luaStateVariable);
        _builder.append(" then");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        CharSequence _luaTransition = this.luaTransition(state);
        _builder.append(_luaTransition, "  ");
      }
      if (_hasElements) {
        _builder.append("\nelse return false\nend");
      }
    }
    return _builder;
  }
  
  private CharSequence luaTransition(final ECState state) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECTransition> _outTransitions = state.getOutTransitions();
      boolean _hasElements = false;
      for(final ECTransition tran : _outTransitions) {
        if (!_hasElements) {
          _hasElements = true;
          _builder.append("if ");
        } else {
          _builder.appendImmediate("\nelseif ", "");
        }
        CharSequence _luaTransitionCondition = this.luaTransitionCondition(tran);
        _builder.append(_luaTransitionCondition);
        _builder.append(" then return enter");
        CharSequence _luaStateName = LuaConstants.luaStateName(tran.getDestination());
        _builder.append(_luaStateName);
        _builder.append("(fb)");
      }
      if (_hasElements) {
        _builder.append("\nelse return false\nend");
      }
    }
    return _builder;
  }
  
  private CharSequence luaTransitionCondition(final ECTransition tran) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Event _conditionEvent = tran.getConditionEvent();
      boolean _notEquals = (!Objects.equal(_conditionEvent, null));
      if (_notEquals) {
        CharSequence _luaInputEventName = LuaConstants.luaInputEventName(tran.getConditionEvent());
        _builder.append(_luaInputEventName);
        _builder.append(" == id");
      } else {
        _builder.append("true");
      }
    }
    _builder.append(" and ");
    {
      boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(tran.getConditionExpression());
      boolean _not = (!_isNullOrEmpty);
      if (_not) {
        CharSequence _luaTransitionConditionExpression = this.luaTransitionConditionExpression(tran);
        _builder.append(_luaTransitionConditionExpression);
      } else {
        _builder.append("true");
      }
    }
    return _builder;
  }
  
  private CharSequence luaTransitionConditionExpression(final ECTransition tran) {
    CharSequence _xblockexpression = null;
    {
      EObject _rootContainer = EcoreUtil.getRootContainer(tran);
      final BasicFBType type = ((BasicFBType) _rootContainer);
      _xblockexpression = this.stAlgorithmFilter.lua(type, tran.getConditionExpression());
    }
    return _xblockexpression;
  }
  
  private CharSequence luaStates(final ECC ecc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = ecc.getECState();
      for(final ECState state : _eCState) {
        CharSequence _luaState = this.luaState(state);
        _builder.append(_luaState);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence luaState(final ECState state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local function enter");
    CharSequence _luaStateName = LuaConstants.luaStateName(state);
    _builder.append(_luaStateName);
    _builder.append("(fb)");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaFBStateVariable = LuaConstants.luaFBStateVariable();
    _builder.append(_luaFBStateVariable, "  ");
    _builder.append(" = ");
    CharSequence _luaStateName_1 = LuaConstants.luaStateName(state);
    _builder.append(_luaStateName_1, "  ");
    _builder.newLineIfNotEmpty();
    {
      EList<ECAction> _eCAction = state.getECAction();
      for(final ECAction action : _eCAction) {
        _builder.append("  ");
        {
          Algorithm _algorithm = action.getAlgorithm();
          boolean _notEquals = (!Objects.equal(null, _algorithm));
          if (_notEquals) {
            CharSequence _luaAlgorithmName = LuaConstants.luaAlgorithmName(action.getAlgorithm());
            _builder.append(_luaAlgorithmName, "  ");
            _builder.append("(fb)");
          }
        }
        _builder.newLineIfNotEmpty();
        {
          Event _output = action.getOutput();
          if ((_output instanceof AdapterEvent)) {
            _builder.append("  ");
            Event _output_1 = action.getOutput();
            CharSequence _luaSendAdapterOutputEvent = null;
            if (_output_1!=null) {
              _luaSendAdapterOutputEvent=LuaConstants.luaSendAdapterOutputEvent(_output_1);
            }
            _builder.append(_luaSendAdapterOutputEvent, "  ");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("  ");
            Event _output_2 = action.getOutput();
            CharSequence _luaSendOutputEvent = null;
            if (_output_2!=null) {
              _luaSendOutputEvent=LuaConstants.luaSendOutputEvent(_output_2);
            }
            _builder.append(_luaSendOutputEvent, "  ");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("  ");
    _builder.append("return true");
    _builder.newLine();
    _builder.append("end");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence luaAlgorithms(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Algorithm> _algorithm = type.getAlgorithm();
      for(final Algorithm alg : _algorithm) {
        String _luaAlgorithm = this.luaAlgorithm(alg);
        _builder.append(_luaAlgorithm);
        _builder.newLineIfNotEmpty();
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private String _luaAlgorithm(final Algorithm alg) {
    Class<? extends Algorithm> _class = alg.getClass();
    String _plus = ("Cannot export algorithm " + _class);
    throw new UnsupportedOperationException(_plus);
  }
  
  private String _luaAlgorithm(final STAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local function ");
    CharSequence _luaAlgorithmName = LuaConstants.luaAlgorithmName(alg);
    _builder.append(_luaAlgorithmName);
    _builder.append("(fb)");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    String _lua = this.stAlgorithmFilter.lua(alg);
    _builder.append(_lua, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("end");
    _builder.newLine();
    final String result = _builder.toString();
    final Function1<String, String> _function = (String it) -> {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Error in algorithm ");
      String _name = alg.getName();
      _builder_1.append(_name);
      _builder_1.append(": ");
      _builder_1.append(it);
      return _builder_1.toString();
    };
    this.errors.addAll(ListExtensions.<String, String>map(this.stAlgorithmFilter.getErrors(), _function));
    this.stAlgorithmFilter.getErrors().clear();
    return result;
  }
  
  private String luaAlgorithm(final Algorithm alg) {
    if (alg instanceof STAlgorithm) {
      return _luaAlgorithm((STAlgorithm)alg);
    } else if (alg != null) {
      return _luaAlgorithm(alg);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(alg).toString());
    }
  }
  
  @Pure
  public List<String> getErrors() {
    return this.errors;
  }
}
