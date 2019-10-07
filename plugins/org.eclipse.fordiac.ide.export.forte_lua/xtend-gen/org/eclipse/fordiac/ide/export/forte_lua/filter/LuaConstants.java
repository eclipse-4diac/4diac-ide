/**
 * Copyright (c) 2015 fortiss GmbH
 * 				 2019 Jan Holzweber
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
 *   Jan Holzweber  - fixed adapter socket variable bug
 */
package org.eclipse.fordiac.ide.export.forte_lua.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class LuaConstants {
  private static final int FB_STATE = 0;
  
  private static final int FB_DI_FLAG = 33554432;
  
  private static final int FB_DO_FLAG = 67108864;
  
  private static final int FB_AD_FLAG = 134217728;
  
  private static final int FB_IN_FLAG = 268435456;
  
  public static CharSequence luaTypeName(final FBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FORTE_");
    String _name = type.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaStateVariable() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("STATE");
    return _builder;
  }
  
  public static CharSequence luaFBStateVarName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FB_STATE");
    return _builder;
  }
  
  public static CharSequence luaStateName(final ECState state) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ECC_");
    String _name = state.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaInputEventName(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((event instanceof AdapterEvent)) {
        _builder.append("AEI_");
        String _replaceAll = ((AdapterEvent)event).getName().replaceAll("\\.", "_");
        _builder.append(_replaceAll);
      } else {
        _builder.append("EI_");
        String _name = event.getName();
        _builder.append(_name);
      }
    }
    return _builder;
  }
  
  public static CharSequence luaOutputEventName(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((event instanceof AdapterEvent)) {
        _builder.append("AEO_");
        String _replaceAll = ((AdapterEvent)event).getName().replaceAll("\\.", "_");
        _builder.append(_replaceAll);
      } else {
        _builder.append("EO_");
        String _name = event.getName();
        _builder.append(_name);
      }
    }
    return _builder;
  }
  
  public static CharSequence luaAdapterInputEventName(final Event event, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("AEI_");
    _builder.append(adapterName);
    _builder.append("_");
    String _name = event.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaAdapterOutputEventName(final Event event, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("AEO_");
    _builder.append(adapterName);
    _builder.append("_");
    String _name = event.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBInputVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DI_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBOutputVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DO_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBAdapterInputVarName(final VarDeclaration decl, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ADI_");
    _builder.append(adapterName);
    _builder.append("_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBAdapterOutputVarName(final VarDeclaration decl, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ADO_");
    _builder.append(adapterName);
    _builder.append("_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBInternalVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("IN_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaVariable(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR_");
    String _name = decl.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaAdapterVariable(final String name, final String adapterInstanceName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR_");
    _builder.append(adapterInstanceName);
    _builder.append("_");
    _builder.append(name);
    return _builder;
  }
  
  public static CharSequence luaAlgorithmName(final Algorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("alg_");
    String _name = alg.getName();
    _builder.append(_name);
    return _builder;
  }
  
  public static CharSequence luaFBStateConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local ");
    CharSequence _luaFBStateVarName = LuaConstants.luaFBStateVarName();
    _builder.append(_luaFBStateVarName);
    _builder.append(" = ");
    _builder.append(LuaConstants.FB_STATE);
    return _builder;
  }
  
  public static CharSequence luaStateConstants(final ECC ecc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = ecc.getECState();
      for(final ECState state : _eCState) {
        _builder.append("local ");
        CharSequence _luaStateName = LuaConstants.luaStateName(state);
        _builder.append(_luaStateName);
        _builder.append(" = ");
        int _indexOf = ecc.getECState().indexOf(state);
        _builder.append(_indexOf);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaEventConstants(final InterfaceList ifl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Event> _eventInputs = ifl.getEventInputs();
      for(final Event event : _eventInputs) {
        _builder.append("local ");
        CharSequence _luaInputEventName = LuaConstants.luaInputEventName(event);
        _builder.append(_luaInputEventName);
        _builder.append(" = ");
        int _indexOf = ifl.getEventInputs().indexOf(event);
        _builder.append(_indexOf);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Event> _eventOutputs = ifl.getEventOutputs();
      for(final Event event_1 : _eventOutputs) {
        _builder.append("local ");
        CharSequence _luaOutputEventName = LuaConstants.luaOutputEventName(event_1);
        _builder.append(_luaOutputEventName);
        _builder.append(" = ");
        int _indexOf_1 = ifl.getEventOutputs().indexOf(event_1);
        _builder.append(_indexOf_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBVariableConstants(final InterfaceList ifl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<VarDeclaration> _inputVars = ifl.getInputVars();
      for(final VarDeclaration decl : _inputVars) {
        _builder.append("local ");
        CharSequence _luaFBInputVarName = LuaConstants.luaFBInputVarName(decl);
        _builder.append(_luaFBInputVarName);
        _builder.append(" = ");
        _builder.append((LuaConstants.FB_DI_FLAG | ifl.getInputVars().indexOf(decl)));
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _outputVars = ifl.getOutputVars();
      for(final VarDeclaration decl_1 : _outputVars) {
        _builder.append("local ");
        CharSequence _luaFBOutputVarName = LuaConstants.luaFBOutputVarName(decl_1);
        _builder.append(_luaFBOutputVarName);
        _builder.append(" = ");
        _builder.append((LuaConstants.FB_DO_FLAG | ifl.getOutputVars().indexOf(decl_1)));
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterConstants(final InterfaceList ifl) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<AdapterDeclaration> _sockets = ifl.getSockets();
      for(final AdapterDeclaration socket : _sockets) {
        CharSequence _luaFBAdapterInterfaceConstants = LuaConstants.luaFBAdapterInterfaceConstants(socket, ifl.getSockets(), ifl.getPlugs().size());
        _builder.append(_luaFBAdapterInterfaceConstants);
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<AdapterDeclaration> _plugs = ifl.getPlugs();
      for(final AdapterDeclaration plug : _plugs) {
        CharSequence _luaFBAdapterInterfaceConstants_1 = LuaConstants.luaFBAdapterInterfaceConstants(plug, ifl.getPlugs(), 0);
        _builder.append(_luaFBAdapterInterfaceConstants_1);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterInterfaceConstants(final AdapterDeclaration adapter, final EList<?> ifl, final int offset) {
    StringConcatenation _builder = new StringConcatenation();
    InterfaceList aifl = LuaConstants.getAdapterInterfaceList(adapter);
    _builder.append("\t\t");
    _builder.newLineIfNotEmpty();
    int _indexOf = ifl.indexOf(adapter);
    int adapterID = (_indexOf + offset);
    _builder.newLineIfNotEmpty();
    {
      EList<Event> _eventOutputs = aifl.getEventOutputs();
      for(final Event decl : _eventOutputs) {
        _builder.append("local ");
        CharSequence _luaAdapterOutputEventName = LuaConstants.luaAdapterOutputEventName(decl, adapter.getName());
        _builder.append(_luaAdapterOutputEventName);
        _builder.append(" = ");
        _builder.append(((LuaConstants.FB_AD_FLAG | (adapterID << 16)) | aifl.getEventOutputs().indexOf(decl)));
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Event> _eventInputs = aifl.getEventInputs();
      for(final Event decl_1 : _eventInputs) {
        _builder.append("local ");
        CharSequence _luaAdapterInputEventName = LuaConstants.luaAdapterInputEventName(decl_1, adapter.getName());
        _builder.append(_luaAdapterInputEventName);
        _builder.append(" = ");
        _builder.append(((LuaConstants.FB_AD_FLAG | (adapterID << 16)) | aifl.getEventInputs().indexOf(decl_1)));
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _outputVars = aifl.getOutputVars();
      for(final VarDeclaration decl_2 : _outputVars) {
        _builder.append("local ");
        CharSequence _luaFBAdapterOutputVarName = LuaConstants.luaFBAdapterOutputVarName(decl_2, adapter.getName());
        _builder.append(_luaFBAdapterOutputVarName);
        _builder.append(" = ");
        _builder.append((((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DO_FLAG) | (adapterID << 16)) | aifl.getOutputVars().indexOf(decl_2)));
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _inputVars = aifl.getInputVars();
      for(final VarDeclaration decl_3 : _inputVars) {
        _builder.append("local ");
        CharSequence _luaFBAdapterInputVarName = LuaConstants.luaFBAdapterInputVarName(decl_3, adapter.getName());
        _builder.append(_luaFBAdapterInputVarName);
        _builder.append(" = ");
        _builder.append((((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DI_FLAG) | (adapterID << 16)) | aifl.getInputVars().indexOf(decl_3)));
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static InterfaceList getAdapterInterfaceList(final AdapterDeclaration adapter) {
    boolean _isIsInput = adapter.isIsInput();
    if (_isIsInput) {
      return adapter.getType().getPlugType().getInterfaceList();
    }
    return adapter.getType().getSocketType().getInterfaceList();
  }
  
  public static CharSequence luaInternalConstants(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<VarDeclaration> _internalVars = type.getInternalVars();
      for(final VarDeclaration decl : _internalVars) {
        _builder.append("local ");
        CharSequence _luaFBInternalVarName = LuaConstants.luaFBInternalVarName(decl);
        _builder.append(_luaFBInternalVarName);
        _builder.append(" = ");
        _builder.append((LuaConstants.FB_IN_FLAG | type.getInternalVars().indexOf(decl)));
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaConstants(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _luaFBStateConstant = LuaConstants.luaFBStateConstant();
    _builder.append(_luaFBStateConstant);
    _builder.newLineIfNotEmpty();
    CharSequence _luaStateConstants = LuaConstants.luaStateConstants(type.getECC());
    _builder.append(_luaStateConstants);
    _builder.newLineIfNotEmpty();
    CharSequence _luaEventConstants = LuaConstants.luaEventConstants(type.getInterfaceList());
    _builder.append(_luaEventConstants);
    _builder.newLineIfNotEmpty();
    CharSequence _luaFBVariableConstants = LuaConstants.luaFBVariableConstants(type.getInterfaceList());
    _builder.append(_luaFBVariableConstants);
    _builder.newLineIfNotEmpty();
    CharSequence _luaFBAdapterConstants = LuaConstants.luaFBAdapterConstants(type.getInterfaceList());
    _builder.append(_luaFBAdapterConstants);
    _builder.newLineIfNotEmpty();
    CharSequence _luaInternalConstants = LuaConstants.luaInternalConstants(type);
    _builder.append(_luaInternalConstants);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence luaFBStateVariable() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb[");
    CharSequence _luaFBStateVarName = LuaConstants.luaFBStateVarName();
    _builder.append(_luaFBStateVarName);
    _builder.append("]");
    return _builder;
  }
  
  public static CharSequence luaFBVariable(final VarDeclaration decl) {
    CharSequence _xblockexpression = null;
    {
      EObject _rootContainer = EcoreUtil.getRootContainer(decl);
      final FBType type = ((FBType) _rootContainer);
      CharSequence _xifexpression = null;
      boolean _contains = type.getInterfaceList().getInputVars().contains(decl);
      if (_contains) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fb[");
        CharSequence _luaFBInputVarName = LuaConstants.luaFBInputVarName(decl);
        _builder.append(_luaFBInputVarName);
        _builder.append("]");
        _xifexpression = _builder;
      } else {
        CharSequence _xifexpression_1 = null;
        boolean _contains_1 = type.getInterfaceList().getOutputVars().contains(decl);
        if (_contains_1) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("fb[");
          CharSequence _luaFBOutputVarName = LuaConstants.luaFBOutputVarName(decl);
          _builder_1.append(_luaFBOutputVarName);
          _builder_1.append("]");
          _xifexpression_1 = _builder_1;
        } else {
          CharSequence _xifexpression_2 = null;
          if (((type instanceof BasicFBType) && ((BasicFBType) type).getInternalVars().contains(decl))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("fb[");
            CharSequence _luaFBInternalVarName = LuaConstants.luaFBInternalVarName(decl);
            _builder_2.append(_luaFBInternalVarName);
            _builder_2.append("]");
            _xifexpression_2 = _builder_2;
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("Unknown kind of variable ");
            String _name = decl.getName();
            _builder_3.append(_name);
            throw new IllegalArgumentException(_builder_3.toString());
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  public static CharSequence luaFBVariablesPrefix(final Iterable<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final VarDeclaration variable : variables) {
        _builder.append("local ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable);
        _builder.append(" = ");
        CharSequence _luaFBVariable = LuaConstants.luaFBVariable(variable);
        _builder.append(_luaFBVariable);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterVariablesPrefix(final Iterable<AdapterVariable> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterVariable av : variables) {
        int index = IterableExtensions.<AdapterVariable>toList(variables).indexOf(av);
        _builder.newLineIfNotEmpty();
        List<AdapterVariable> sublist = IterableExtensions.<AdapterVariable>toList(variables).subList(0, index);
        _builder.newLineIfNotEmpty();
        {
          boolean _not = (!(ListExtensions.<AdapterVariable, VarDeclaration>map(sublist, ((Function1<AdapterVariable, VarDeclaration>) (AdapterVariable it) -> {
            return it.getVar();
          })).contains(av.getVar()) && ListExtensions.<AdapterVariable, AdapterDeclaration>map(sublist, ((Function1<AdapterVariable, AdapterDeclaration>) (AdapterVariable it) -> {
            return it.getAdapter();
          })).contains(av.getAdapter())));
          if (_not) {
            _builder.append("local ");
            CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(av.getVar().getName(), av.getAdapter().getName());
            _builder.append(_luaAdapterVariable);
            _builder.append(" = fb[");
            CharSequence _xifexpression = null;
            boolean _isIsInput = av.getVar().isIsInput();
            if (_isIsInput) {
              _xifexpression = LuaConstants.luaFBAdapterInputVarName(av.getVar(), av.getAdapter().getName());
            } else {
              _xifexpression = LuaConstants.luaFBAdapterOutputVarName(av.getVar(), av.getAdapter().getName());
            }
            _builder.append(_xifexpression);
            _builder.append("]");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBVariablesSuffix(final Iterable<VarDeclaration> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<VarDeclaration, Boolean> _function = (VarDeclaration it) -> {
        boolean _isIsInput = it.isIsInput();
        return Boolean.valueOf((!_isIsInput));
      };
      Iterable<VarDeclaration> _filter = IterableExtensions.<VarDeclaration>filter(variables, _function);
      for(final VarDeclaration variable : _filter) {
        CharSequence _luaFBVariable = LuaConstants.luaFBVariable(variable);
        _builder.append(_luaFBVariable);
        _builder.append(" = ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable);
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterVariablesSuffix(final Iterable<AdapterVariable> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterVariable av : variables) {
        int index = IterableExtensions.<AdapterVariable>toList(variables).indexOf(av);
        _builder.newLineIfNotEmpty();
        List<AdapterVariable> sublist = IterableExtensions.<AdapterVariable>toList(variables).subList(0, index);
        _builder.newLineIfNotEmpty();
        {
          boolean _not = (!(ListExtensions.<AdapterVariable, VarDeclaration>map(sublist, ((Function1<AdapterVariable, VarDeclaration>) (AdapterVariable it) -> {
            return it.getVar();
          })).contains(av.getVar()) && ListExtensions.<AdapterVariable, AdapterDeclaration>map(sublist, ((Function1<AdapterVariable, AdapterDeclaration>) (AdapterVariable it) -> {
            return it.getAdapter();
          })).contains(av.getAdapter())));
          if (_not) {
            _builder.append("fb[");
            CharSequence _xifexpression = null;
            boolean _isIsInput = av.getVar().isIsInput();
            if (_isIsInput) {
              _xifexpression = LuaConstants.luaFBAdapterInputVarName(av.getVar(), av.getAdapter().getName());
            } else {
              _xifexpression = LuaConstants.luaFBAdapterOutputVarName(av.getVar(), av.getAdapter().getName());
            }
            _builder.append(_xifexpression);
            _builder.append("] = ");
            CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(av.getVar().getName(), av.getAdapter().getName());
            _builder.append(_luaAdapterVariable);
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence luaSendOutputEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb(");
    CharSequence _luaOutputEventName = LuaConstants.luaOutputEventName(event);
    _builder.append(_luaOutputEventName);
    _builder.append(")");
    return _builder;
  }
  
  public static CharSequence luaSendAdapterOutputEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb(AEO_");
    String _replaceAll = event.getName().replaceAll("\\.", "_");
    _builder.append(_replaceAll);
    _builder.append(")");
    return _builder;
  }
  
  public static int getEventWith(final Event event, final List<Integer> with, final List<VarDeclaration> vars) {
    boolean _isEmpty = event.getWith().isEmpty();
    if (_isEmpty) {
      return (-1);
    }
    final int index = with.size();
    EList<With> _with = event.getWith();
    for (final With w : _with) {
      with.add(Integer.valueOf(vars.indexOf(w.getVariables())));
    }
    with.add(Integer.valueOf(255));
    return index;
  }
  
  public static ArrayList<Object> getTypeList(final List<VarDeclaration> vars) {
    int _size = vars.size();
    final ArrayList<Object> typeList = new ArrayList<Object>(_size);
    final Consumer<VarDeclaration> _function = (VarDeclaration it) -> {
      boolean _isArray = it.isArray();
      if (_isArray) {
        typeList.add("ARRAY");
        typeList.add(Integer.valueOf(it.getArraySize()));
      }
      typeList.add(it.getTypeName());
    };
    vars.forEach(_function);
    return typeList;
  }
  
  public static CharSequence luaInterfaceSpec(final InterfaceList ifl) {
    CharSequence _xblockexpression = null;
    {
      final ArrayList<Integer> inputWith = new ArrayList<Integer>();
      final ArrayList<Integer> inputWithIndexes = new ArrayList<Integer>();
      EList<Event> _eventInputs = ifl.getEventInputs();
      for (final Event e : _eventInputs) {
        inputWithIndexes.add(Integer.valueOf(LuaConstants.getEventWith(e, inputWith, ifl.getInputVars())));
      }
      final ArrayList<Integer> outputWith = new ArrayList<Integer>();
      final ArrayList<Integer> outputWithIndexes = new ArrayList<Integer>();
      EList<Event> _eventOutputs = ifl.getEventOutputs();
      for (final Event e_1 : _eventOutputs) {
        outputWithIndexes.add(Integer.valueOf(LuaConstants.getEventWith(e_1, outputWith, ifl.getOutputVars())));
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("local interfaceSpec = {");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("numEIs = ");
      int _size = ifl.getEventInputs().size();
      _builder.append(_size, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EINames = ");
      final Function1<Event, String> _function = (Event it) -> {
        return it.getName();
      };
      CharSequence _luaStringList = LuaUtils.luaStringList(ListExtensions.<Event, String>map(ifl.getEventInputs(), _function));
      _builder.append(_luaStringList, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EIWith = ");
      CharSequence _luaIntegerList = LuaUtils.luaIntegerList(inputWith);
      _builder.append(_luaIntegerList, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EIWithIndexes = ");
      CharSequence _luaIntegerList_1 = LuaUtils.luaIntegerList(inputWithIndexes);
      _builder.append(_luaIntegerList_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numEOs = ");
      int _size_1 = ifl.getEventOutputs().size();
      _builder.append(_size_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EONames = ");
      final Function1<Event, String> _function_1 = (Event it) -> {
        return it.getName();
      };
      CharSequence _luaStringList_1 = LuaUtils.luaStringList(ListExtensions.<Event, String>map(ifl.getEventOutputs(), _function_1));
      _builder.append(_luaStringList_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EOWith = ");
      CharSequence _luaIntegerList_2 = LuaUtils.luaIntegerList(outputWith);
      _builder.append(_luaIntegerList_2, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EOWithIndexes = ");
      CharSequence _luaIntegerList_3 = LuaUtils.luaIntegerList(outputWithIndexes);
      _builder.append(_luaIntegerList_3, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numDIs = ");
      int _size_2 = ifl.getInputVars().size();
      _builder.append(_size_2, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DINames = ");
      final Function1<VarDeclaration, String> _function_2 = (VarDeclaration it) -> {
        return it.getName();
      };
      CharSequence _luaStringList_2 = LuaUtils.luaStringList(ListExtensions.<VarDeclaration, String>map(ifl.getInputVars(), _function_2));
      _builder.append(_luaStringList_2, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DIDataTypeNames = ");
      CharSequence _luaValueList = LuaUtils.luaValueList(LuaConstants.getTypeList(ifl.getInputVars()));
      _builder.append(_luaValueList, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numDOs = ");
      int _size_3 = ifl.getOutputVars().size();
      _builder.append(_size_3, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DONames = ");
      final Function1<VarDeclaration, String> _function_3 = (VarDeclaration it) -> {
        return it.getName();
      };
      CharSequence _luaStringList_3 = LuaUtils.luaStringList(ListExtensions.<VarDeclaration, String>map(ifl.getOutputVars(), _function_3));
      _builder.append(_luaStringList_3, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DODataTypeNames = ");
      CharSequence _luaValueList_1 = LuaUtils.luaValueList(LuaConstants.getTypeList(ifl.getOutputVars()));
      _builder.append(_luaValueList_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numAdapters = ");
      int _size_4 = ifl.getPlugs().size();
      int _size_5 = ifl.getSockets().size();
      int _plus = (_size_4 + _size_5);
      _builder.append(_plus, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("adapterInstanceDefinition = {");
      _builder.newLine();
      _builder.append("    ");
      final Function1<AdapterDeclaration, String> _function_4 = (AdapterDeclaration it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{adapterNameID = \"");
        String _name = it.getName();
        _builder_1.append(_name);
        _builder_1.append("\", adapterTypeNameID = \"");
        String _typeName = it.getTypeName();
        _builder_1.append(_typeName);
        _builder_1.append("\", isPlug = true}");
        return _builder_1.toString();
      };
      String _join = IterableExtensions.join(ListExtensions.<AdapterDeclaration, String>map(ifl.getPlugs(), _function_4), ",\n");
      _builder.append(_join, "    ");
      {
        if (((!ifl.getSockets().isEmpty()) && (!ifl.getPlugs().isEmpty()))) {
          _builder.append(",");
        }
      }
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      final Function1<AdapterDeclaration, String> _function_5 = (AdapterDeclaration it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{adapterNameID = \"");
        String _name = it.getName();
        _builder_1.append(_name);
        _builder_1.append("\", adapterTypeNameID = \"");
        String _typeName = it.getTypeName();
        _builder_1.append(_typeName);
        _builder_1.append("\", isPlug = false}");
        return _builder_1.toString();
      };
      String _join_1 = IterableExtensions.join(ListExtensions.<AdapterDeclaration, String>map(ifl.getSockets(), _function_5), ",\n");
      _builder.append(_join_1, "    ");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence luaInternalVarsInformation(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local internalVarsInformation = {");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("numIntVars = ");
    int _size = type.getInternalVars().size();
    _builder.append(_size, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("intVarsNames = ");
    final Function1<VarDeclaration, String> _function = (VarDeclaration it) -> {
      return it.getName();
    };
    CharSequence _luaStringList = LuaUtils.luaStringList(ListExtensions.<VarDeclaration, String>map(type.getInternalVars(), _function));
    _builder.append(_luaStringList, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("intVarsDataTypeNames = ");
    CharSequence _luaValueList = LuaUtils.luaValueList(LuaConstants.getTypeList(type.getInternalVars()));
    _builder.append(_luaValueList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }
}
