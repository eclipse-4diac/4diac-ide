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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
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
  private final static int FB_STATE = 0;
  
  private final static int FB_DI_FLAG = 33554432;
  
  private final static int FB_DO_FLAG = 67108864;
  
  private final static int FB_AD_FLAG = 134217728;
  
  private final static int FB_IN_FLAG = 268435456;
  
  public static CharSequence luaTypeName(final FBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("FORTE_");
    String _name = type.getName();
    _builder.append(_name, "");
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
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaInputEventName(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((event instanceof AdapterEvent)) {
        _builder.append("AEI_");
        String _name = ((AdapterEvent)event).getName();
        String _replaceAll = _name.replaceAll("\\.", "_");
        _builder.append(_replaceAll, "");
      } else {
        _builder.append("EI_");
        String _name_1 = event.getName();
        _builder.append(_name_1, "");
      }
    }
    return _builder;
  }
  
  public static CharSequence luaOutputEventName(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    {
      if ((event instanceof AdapterEvent)) {
        _builder.append("AEO_");
        String _name = ((AdapterEvent)event).getName();
        String _replaceAll = _name.replaceAll("\\.", "_");
        _builder.append(_replaceAll, "");
      } else {
        _builder.append("EO_");
        String _name_1 = event.getName();
        _builder.append(_name_1, "");
      }
    }
    return _builder;
  }
  
  public static CharSequence luaAdapterInputEventName(final Event event, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("AEI_");
    _builder.append(adapterName, "");
    _builder.append("_");
    String _name = event.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaAdapterOutputEventName(final Event event, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("AEO_");
    _builder.append(adapterName, "");
    _builder.append("_");
    String _name = event.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBInputVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DI_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBOutputVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("DO_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBAdapterInputVarName(final VarDeclaration decl, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ADI_");
    _builder.append(adapterName, "");
    _builder.append("_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBAdapterOutputVarName(final VarDeclaration decl, final String adapterName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("ADO_");
    _builder.append(adapterName, "");
    _builder.append("_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBInternalVarName(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("IN_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaVariable(final VarDeclaration decl) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR_");
    String _name = decl.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaAdapterVariable(final String name, final String adapterInstanceName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("VAR_");
    _builder.append(adapterInstanceName, "");
    _builder.append("_");
    _builder.append(name, "");
    return _builder;
  }
  
  public static CharSequence luaAlgorithmName(final Algorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("alg_");
    String _name = alg.getName();
    _builder.append(_name, "");
    return _builder;
  }
  
  public static CharSequence luaFBStateConstant() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local ");
    CharSequence _luaFBStateVarName = LuaConstants.luaFBStateVarName();
    _builder.append(_luaFBStateVarName, "");
    _builder.append(" = ");
    _builder.append(LuaConstants.FB_STATE, "");
    return _builder;
  }
  
  public static CharSequence luaStateConstants(final ECC ecc) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<ECState> _eCState = ecc.getECState();
      for(final ECState state : _eCState) {
        _builder.append("local ");
        CharSequence _luaStateName = LuaConstants.luaStateName(state);
        _builder.append(_luaStateName, "");
        _builder.append(" = ");
        EList<ECState> _eCState_1 = ecc.getECState();
        int _indexOf = _eCState_1.indexOf(state);
        _builder.append(_indexOf, "");
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
        _builder.append(_luaInputEventName, "");
        _builder.append(" = ");
        EList<Event> _eventInputs_1 = ifl.getEventInputs();
        int _indexOf = _eventInputs_1.indexOf(event);
        _builder.append(_indexOf, "");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<Event> _eventOutputs = ifl.getEventOutputs();
      for(final Event event_1 : _eventOutputs) {
        _builder.append("local ");
        CharSequence _luaOutputEventName = LuaConstants.luaOutputEventName(event_1);
        _builder.append(_luaOutputEventName, "");
        _builder.append(" = ");
        EList<Event> _eventOutputs_1 = ifl.getEventOutputs();
        int _indexOf_1 = _eventOutputs_1.indexOf(event_1);
        _builder.append(_indexOf_1, "");
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
        _builder.append(_luaFBInputVarName, "");
        _builder.append(" = ");
        EList<VarDeclaration> _inputVars_1 = ifl.getInputVars();
        int _indexOf = _inputVars_1.indexOf(decl);
        int _bitwiseOr = (LuaConstants.FB_DI_FLAG | _indexOf);
        _builder.append(_bitwiseOr, "");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<VarDeclaration> _outputVars = ifl.getOutputVars();
      for(final VarDeclaration decl_1 : _outputVars) {
        _builder.append("local ");
        CharSequence _luaFBOutputVarName = LuaConstants.luaFBOutputVarName(decl_1);
        _builder.append(_luaFBOutputVarName, "");
        _builder.append(" = ");
        EList<VarDeclaration> _outputVars_1 = ifl.getOutputVars();
        int _indexOf_1 = _outputVars_1.indexOf(decl_1);
        int _bitwiseOr_1 = (LuaConstants.FB_DO_FLAG | _indexOf_1);
        _builder.append(_bitwiseOr_1, "");
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
        EList<AdapterDeclaration> _sockets_1 = ifl.getSockets();
        CharSequence _luaFBAdapterInterfaceConstants = LuaConstants.luaFBAdapterInterfaceConstants(socket, _sockets_1);
        _builder.append(_luaFBAdapterInterfaceConstants, "");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<AdapterDeclaration> _plugs = ifl.getPlugs();
      for(final AdapterDeclaration plug : _plugs) {
        EList<AdapterDeclaration> _plugs_1 = ifl.getPlugs();
        CharSequence _luaFBAdapterInterfaceConstants_1 = LuaConstants.luaFBAdapterInterfaceConstants(plug, _plugs_1);
        _builder.append(_luaFBAdapterInterfaceConstants_1, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterInterfaceConstants(final AdapterDeclaration adapter, final EList<?> ifl) {
    StringConcatenation _builder = new StringConcatenation();
    DataType _type = adapter.getType();
    AdapterFBType _adapterFBType = ((AdapterType) _type).getAdapterFBType();
    InterfaceList aifl = _adapterFBType.getInterfaceList();
    _builder.newLineIfNotEmpty();
    {
      boolean _isIsInput = adapter.isIsInput();
      if (_isIsInput) {
        {
          EList<Event> _eventOutputs = aifl.getEventOutputs();
          for(final Event decl : _eventOutputs) {
            _builder.append("local ");
            String _name = adapter.getName();
            CharSequence _luaAdapterInputEventName = LuaConstants.luaAdapterInputEventName(decl, _name);
            _builder.append(_luaAdapterInputEventName, "");
            _builder.append(" = ");
            int _indexOf = ifl.indexOf(adapter);
            int _doubleLessThan = (_indexOf << 16);
            int _bitwiseOr = (LuaConstants.FB_AD_FLAG | _doubleLessThan);
            EList<Event> _eventOutputs_1 = aifl.getEventOutputs();
            int _indexOf_1 = _eventOutputs_1.indexOf(decl);
            int _bitwiseOr_1 = (_bitwiseOr | _indexOf_1);
            _builder.append(_bitwiseOr_1, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Event> _eventInputs = aifl.getEventInputs();
          for(final Event decl_1 : _eventInputs) {
            _builder.append("local ");
            String _name_1 = adapter.getName();
            CharSequence _luaAdapterOutputEventName = LuaConstants.luaAdapterOutputEventName(decl_1, _name_1);
            _builder.append(_luaAdapterOutputEventName, "");
            _builder.append(" = ");
            int _indexOf_2 = ifl.indexOf(adapter);
            int _doubleLessThan_1 = (_indexOf_2 << 16);
            int _bitwiseOr_2 = (LuaConstants.FB_AD_FLAG | _doubleLessThan_1);
            EList<Event> _eventInputs_1 = aifl.getEventInputs();
            int _indexOf_3 = _eventInputs_1.indexOf(decl_1);
            int _bitwiseOr_3 = (_bitwiseOr_2 | _indexOf_3);
            _builder.append(_bitwiseOr_3, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<VarDeclaration> _outputVars = aifl.getOutputVars();
          for(final VarDeclaration decl_2 : _outputVars) {
            _builder.append("local ");
            String _name_2 = adapter.getName();
            CharSequence _luaFBAdapterInputVarName = LuaConstants.luaFBAdapterInputVarName(decl_2, _name_2);
            _builder.append(_luaFBAdapterInputVarName, "");
            _builder.append(" = ");
            int _indexOf_4 = ifl.indexOf(adapter);
            int _doubleLessThan_2 = (_indexOf_4 << 16);
            int _bitwiseOr_4 = ((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DI_FLAG) | _doubleLessThan_2);
            EList<VarDeclaration> _outputVars_1 = aifl.getOutputVars();
            int _indexOf_5 = _outputVars_1.indexOf(decl_2);
            int _bitwiseOr_5 = (_bitwiseOr_4 | _indexOf_5);
            _builder.append(_bitwiseOr_5, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<VarDeclaration> _inputVars = aifl.getInputVars();
          for(final VarDeclaration decl_3 : _inputVars) {
            _builder.append("local ");
            String _name_3 = adapter.getName();
            CharSequence _luaFBAdapterOutputVarName = LuaConstants.luaFBAdapterOutputVarName(decl_3, _name_3);
            _builder.append(_luaFBAdapterOutputVarName, "");
            _builder.append(" = ");
            int _indexOf_6 = ifl.indexOf(adapter);
            int _doubleLessThan_3 = (_indexOf_6 << 16);
            int _bitwiseOr_6 = ((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DO_FLAG) | _doubleLessThan_3);
            EList<VarDeclaration> _inputVars_1 = aifl.getInputVars();
            int _indexOf_7 = _inputVars_1.indexOf(decl_3);
            int _bitwiseOr_7 = (_bitwiseOr_6 | _indexOf_7);
            _builder.append(_bitwiseOr_7, "");
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        {
          EList<Event> _eventInputs_2 = aifl.getEventInputs();
          for(final Event decl_4 : _eventInputs_2) {
            _builder.append("local ");
            String _name_4 = adapter.getName();
            CharSequence _luaAdapterInputEventName_1 = LuaConstants.luaAdapterInputEventName(decl_4, _name_4);
            _builder.append(_luaAdapterInputEventName_1, "");
            _builder.append(" = ");
            int _indexOf_8 = ifl.indexOf(adapter);
            int _doubleLessThan_4 = (_indexOf_8 << 16);
            int _bitwiseOr_8 = (LuaConstants.FB_AD_FLAG | _doubleLessThan_4);
            EList<Event> _eventInputs_3 = aifl.getEventInputs();
            int _indexOf_9 = _eventInputs_3.indexOf(decl_4);
            int _bitwiseOr_9 = (_bitwiseOr_8 | _indexOf_9);
            _builder.append(_bitwiseOr_9, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<Event> _eventOutputs_2 = aifl.getEventOutputs();
          for(final Event decl_5 : _eventOutputs_2) {
            _builder.append("local ");
            String _name_5 = adapter.getName();
            CharSequence _luaAdapterOutputEventName_1 = LuaConstants.luaAdapterOutputEventName(decl_5, _name_5);
            _builder.append(_luaAdapterOutputEventName_1, "");
            _builder.append(" = ");
            int _indexOf_10 = ifl.indexOf(adapter);
            int _doubleLessThan_5 = (_indexOf_10 << 16);
            int _bitwiseOr_10 = (LuaConstants.FB_AD_FLAG | _doubleLessThan_5);
            EList<Event> _eventOutputs_3 = aifl.getEventOutputs();
            int _indexOf_11 = _eventOutputs_3.indexOf(decl_5);
            int _bitwiseOr_11 = (_bitwiseOr_10 | _indexOf_11);
            _builder.append(_bitwiseOr_11, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<VarDeclaration> _inputVars_2 = aifl.getInputVars();
          for(final VarDeclaration decl_6 : _inputVars_2) {
            _builder.append("local ");
            String _name_6 = adapter.getName();
            CharSequence _luaFBAdapterInputVarName_1 = LuaConstants.luaFBAdapterInputVarName(decl_6, _name_6);
            _builder.append(_luaFBAdapterInputVarName_1, "");
            _builder.append(" = ");
            int _indexOf_12 = ifl.indexOf(adapter);
            int _doubleLessThan_6 = (_indexOf_12 << 16);
            int _bitwiseOr_12 = ((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DI_FLAG) | _doubleLessThan_6);
            EList<VarDeclaration> _inputVars_3 = aifl.getInputVars();
            int _indexOf_13 = _inputVars_3.indexOf(decl_6);
            int _bitwiseOr_13 = (_bitwiseOr_12 | _indexOf_13);
            _builder.append(_bitwiseOr_13, "");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          EList<VarDeclaration> _outputVars_2 = aifl.getOutputVars();
          for(final VarDeclaration decl_7 : _outputVars_2) {
            _builder.append("local ");
            String _name_7 = adapter.getName();
            CharSequence _luaFBAdapterOutputVarName_1 = LuaConstants.luaFBAdapterOutputVarName(decl_7, _name_7);
            _builder.append(_luaFBAdapterOutputVarName_1, "");
            _builder.append(" = ");
            int _indexOf_14 = ifl.indexOf(adapter);
            int _doubleLessThan_7 = (_indexOf_14 << 16);
            int _bitwiseOr_14 = ((LuaConstants.FB_AD_FLAG | LuaConstants.FB_DO_FLAG) | _doubleLessThan_7);
            EList<VarDeclaration> _outputVars_3 = aifl.getOutputVars();
            int _indexOf_15 = _outputVars_3.indexOf(decl_7);
            int _bitwiseOr_15 = (_bitwiseOr_14 | _indexOf_15);
            _builder.append(_bitwiseOr_15, "");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence luaInternalConstants(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<VarDeclaration> _internalVars = type.getInternalVars();
      for(final VarDeclaration decl : _internalVars) {
        _builder.append("local ");
        CharSequence _luaFBInternalVarName = LuaConstants.luaFBInternalVarName(decl);
        _builder.append(_luaFBInternalVarName, "");
        _builder.append(" = ");
        EList<VarDeclaration> _internalVars_1 = type.getInternalVars();
        int _indexOf = _internalVars_1.indexOf(decl);
        int _bitwiseOr = (LuaConstants.FB_IN_FLAG | _indexOf);
        _builder.append(_bitwiseOr, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaConstants(final BasicFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _luaFBStateConstant = LuaConstants.luaFBStateConstant();
    _builder.append(_luaFBStateConstant, "");
    _builder.newLineIfNotEmpty();
    ECC _eCC = type.getECC();
    CharSequence _luaStateConstants = LuaConstants.luaStateConstants(_eCC);
    _builder.append(_luaStateConstants, "");
    _builder.newLineIfNotEmpty();
    InterfaceList _interfaceList = type.getInterfaceList();
    CharSequence _luaEventConstants = LuaConstants.luaEventConstants(_interfaceList);
    _builder.append(_luaEventConstants, "");
    _builder.newLineIfNotEmpty();
    InterfaceList _interfaceList_1 = type.getInterfaceList();
    CharSequence _luaFBVariableConstants = LuaConstants.luaFBVariableConstants(_interfaceList_1);
    _builder.append(_luaFBVariableConstants, "");
    _builder.newLineIfNotEmpty();
    InterfaceList _interfaceList_2 = type.getInterfaceList();
    CharSequence _luaFBAdapterConstants = LuaConstants.luaFBAdapterConstants(_interfaceList_2);
    _builder.append(_luaFBAdapterConstants, "");
    _builder.newLineIfNotEmpty();
    CharSequence _luaInternalConstants = LuaConstants.luaInternalConstants(type);
    _builder.append(_luaInternalConstants, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public static CharSequence luaFBStateVariable() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb[");
    CharSequence _luaFBStateVarName = LuaConstants.luaFBStateVarName();
    _builder.append(_luaFBStateVarName, "");
    _builder.append("]");
    return _builder;
  }
  
  public static CharSequence luaFBVariable(final VarDeclaration decl) {
    CharSequence _xblockexpression = null;
    {
      EObject _rootContainer = EcoreUtil.getRootContainer(decl);
      final FBType type = ((FBType) _rootContainer);
      CharSequence _xifexpression = null;
      InterfaceList _interfaceList = type.getInterfaceList();
      EList<VarDeclaration> _inputVars = _interfaceList.getInputVars();
      boolean _contains = _inputVars.contains(decl);
      if (_contains) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("fb[");
        CharSequence _luaFBInputVarName = LuaConstants.luaFBInputVarName(decl);
        _builder.append(_luaFBInputVarName, "");
        _builder.append("]");
        _xifexpression = _builder;
      } else {
        CharSequence _xifexpression_1 = null;
        InterfaceList _interfaceList_1 = type.getInterfaceList();
        EList<VarDeclaration> _outputVars = _interfaceList_1.getOutputVars();
        boolean _contains_1 = _outputVars.contains(decl);
        if (_contains_1) {
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("fb[");
          CharSequence _luaFBOutputVarName = LuaConstants.luaFBOutputVarName(decl);
          _builder_1.append(_luaFBOutputVarName, "");
          _builder_1.append("]");
          _xifexpression_1 = _builder_1;
        } else {
          CharSequence _xifexpression_2 = null;
          if (((type instanceof BasicFBType) && ((BasicFBType) type).getInternalVars().contains(decl))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("fb[");
            CharSequence _luaFBInternalVarName = LuaConstants.luaFBInternalVarName(decl);
            _builder_2.append(_luaFBInternalVarName, "");
            _builder_2.append("]");
            _xifexpression_2 = _builder_2;
          } else {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("Unknown kind of variable ");
            String _name = decl.getName();
            _builder_3.append(_name, "");
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
    _builder.append("local ");
    CharSequence _luaStateVariable = LuaConstants.luaStateVariable();
    _builder.append(_luaStateVariable, "");
    _builder.append(" = ");
    CharSequence _luaFBStateVariable = LuaConstants.luaFBStateVariable();
    _builder.append(_luaFBStateVariable, "");
    _builder.newLineIfNotEmpty();
    {
      for(final VarDeclaration variable : variables) {
        _builder.append("local ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable, "");
        _builder.append(" = ");
        CharSequence _luaFBVariable = LuaConstants.luaFBVariable(variable);
        _builder.append(_luaFBVariable, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterVariablesPrefix(final Iterable<AdapterVariable> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterVariable av : variables) {
        List<AdapterVariable> _list = IterableExtensions.<AdapterVariable>toList(variables);
        int index = _list.indexOf(av);
        _builder.newLineIfNotEmpty();
        List<AdapterVariable> _list_1 = IterableExtensions.<AdapterVariable>toList(variables);
        List<AdapterVariable> sublist = _list_1.subList(0, index);
        _builder.newLineIfNotEmpty();
        {
          boolean _not = (!(ListExtensions.<AdapterVariable, VarDeclaration>map(sublist, ((Function1<AdapterVariable, VarDeclaration>) (AdapterVariable it) -> {
            return it.getVar();
          })).contains(av.getVar()) && ListExtensions.<AdapterVariable, AdapterDeclaration>map(sublist, ((Function1<AdapterVariable, AdapterDeclaration>) (AdapterVariable it) -> {
            return it.getAdapter();
          })).contains(av.getAdapter())));
          if (_not) {
            _builder.append("local ");
            VarDeclaration _var = av.getVar();
            String _name = _var.getName();
            AdapterDeclaration _adapter = av.getAdapter();
            String _name_1 = _adapter.getName();
            CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(_name, _name_1);
            _builder.append(_luaAdapterVariable, "");
            _builder.append(" = fb[");
            CharSequence _xifexpression = null;
            AdapterDeclaration _adapter_1 = av.getAdapter();
            boolean _isIsInput = _adapter_1.isIsInput();
            if (_isIsInput) {
              VarDeclaration _var_1 = av.getVar();
              AdapterDeclaration _adapter_2 = av.getAdapter();
              String _name_2 = _adapter_2.getName();
              _xifexpression = LuaConstants.luaFBAdapterInputVarName(_var_1, _name_2);
            } else {
              VarDeclaration _var_2 = av.getVar();
              AdapterDeclaration _adapter_3 = av.getAdapter();
              String _name_3 = _adapter_3.getName();
              _xifexpression = LuaConstants.luaFBAdapterOutputVarName(_var_2, _name_3);
            }
            _builder.append(_xifexpression, "");
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
        _builder.append(_luaFBVariable, "");
        _builder.append(" = ");
        CharSequence _luaVariable = LuaConstants.luaVariable(variable);
        _builder.append(_luaVariable, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public static CharSequence luaFBAdapterVariablesSuffix(final Iterable<AdapterVariable> variables) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final AdapterVariable variable : variables) {
        {
          AdapterDeclaration _adapter = variable.getAdapter();
          boolean _isIsInput = _adapter.isIsInput();
          boolean _not = (!_isIsInput);
          if (_not) {
            List<AdapterVariable> _list = IterableExtensions.<AdapterVariable>toList(variables);
            int index = _list.indexOf(variable);
            _builder.newLineIfNotEmpty();
            List<AdapterVariable> _list_1 = IterableExtensions.<AdapterVariable>toList(variables);
            List<AdapterVariable> sublist = _list_1.subList(0, index);
            _builder.newLineIfNotEmpty();
            {
              boolean _not_1 = (!(ListExtensions.<AdapterVariable, VarDeclaration>map(sublist, ((Function1<AdapterVariable, VarDeclaration>) (AdapterVariable it) -> {
                return it.getVar();
              })).contains(variable.getVar()) && ListExtensions.<AdapterVariable, AdapterDeclaration>map(sublist, ((Function1<AdapterVariable, AdapterDeclaration>) (AdapterVariable it) -> {
                return it.getAdapter();
              })).contains(variable.getAdapter())));
              if (_not_1) {
                _builder.append("fb[");
                VarDeclaration _var = variable.getVar();
                AdapterDeclaration _adapter_1 = variable.getAdapter();
                String _name = _adapter_1.getName();
                CharSequence _luaFBAdapterOutputVarName = LuaConstants.luaFBAdapterOutputVarName(_var, _name);
                _builder.append(_luaFBAdapterOutputVarName, "");
                _builder.append("] = ");
                VarDeclaration _var_1 = variable.getVar();
                String _name_1 = _var_1.getName();
                AdapterDeclaration _adapter_2 = variable.getAdapter();
                String _name_2 = _adapter_2.getName();
                CharSequence _luaAdapterVariable = LuaConstants.luaAdapterVariable(_name_1, _name_2);
                _builder.append(_luaAdapterVariable, "");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    return _builder;
  }
  
  public static CharSequence luaSendOutputEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb(");
    CharSequence _luaOutputEventName = LuaConstants.luaOutputEventName(event);
    _builder.append(_luaOutputEventName, "");
    _builder.append(")");
    return _builder;
  }
  
  public static CharSequence luaSendAdapterOutputEvent(final Event event) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fb(AEO_");
    String _name = event.getName();
    String _replaceAll = _name.replaceAll("\\.", "_");
    _builder.append(_replaceAll, "");
    _builder.append(")");
    return _builder;
  }
  
  public static int getEventWith(final Event event, final List<Integer> with, final List<VarDeclaration> vars) {
    EList<With> _with = event.getWith();
    boolean _isEmpty = _with.isEmpty();
    if (_isEmpty) {
      return (-1);
    }
    final int index = with.size();
    EList<With> _with_1 = event.getWith();
    for (final With w : _with_1) {
      VarDeclaration _variables = w.getVariables();
      int _indexOf = vars.indexOf(_variables);
      with.add(Integer.valueOf(_indexOf));
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
        int _arraySize = it.getArraySize();
        typeList.add(Integer.valueOf(_arraySize));
      }
      String _typeName = it.getTypeName();
      typeList.add(_typeName);
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
        EList<VarDeclaration> _inputVars = ifl.getInputVars();
        int _eventWith = LuaConstants.getEventWith(e, inputWith, _inputVars);
        inputWithIndexes.add(Integer.valueOf(_eventWith));
      }
      final ArrayList<Integer> outputWith = new ArrayList<Integer>();
      final ArrayList<Integer> outputWithIndexes = new ArrayList<Integer>();
      EList<Event> _eventOutputs = ifl.getEventOutputs();
      for (final Event e_1 : _eventOutputs) {
        EList<VarDeclaration> _outputVars = ifl.getOutputVars();
        int _eventWith_1 = LuaConstants.getEventWith(e_1, outputWith, _outputVars);
        outputWithIndexes.add(Integer.valueOf(_eventWith_1));
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("local interfaceSpec = {");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("numEIs = ");
      EList<Event> _eventInputs_1 = ifl.getEventInputs();
      int _size = _eventInputs_1.size();
      _builder.append(_size, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EINames = ");
      EList<Event> _eventInputs_2 = ifl.getEventInputs();
      final Function1<Event, String> _function = (Event it) -> {
        return it.getName();
      };
      List<String> _map = ListExtensions.<Event, String>map(_eventInputs_2, _function);
      CharSequence _luaStringList = LuaUtils.luaStringList(_map);
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
      EList<Event> _eventOutputs_1 = ifl.getEventOutputs();
      int _size_1 = _eventOutputs_1.size();
      _builder.append(_size_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("EONames = ");
      EList<Event> _eventOutputs_2 = ifl.getEventOutputs();
      final Function1<Event, String> _function_1 = (Event it) -> {
        return it.getName();
      };
      List<String> _map_1 = ListExtensions.<Event, String>map(_eventOutputs_2, _function_1);
      CharSequence _luaStringList_1 = LuaUtils.luaStringList(_map_1);
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
      EList<VarDeclaration> _inputVars_1 = ifl.getInputVars();
      int _size_2 = _inputVars_1.size();
      _builder.append(_size_2, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DINames = ");
      EList<VarDeclaration> _inputVars_2 = ifl.getInputVars();
      final Function1<VarDeclaration, String> _function_2 = (VarDeclaration it) -> {
        return it.getName();
      };
      List<String> _map_2 = ListExtensions.<VarDeclaration, String>map(_inputVars_2, _function_2);
      CharSequence _luaStringList_2 = LuaUtils.luaStringList(_map_2);
      _builder.append(_luaStringList_2, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DIDataTypeNames = ");
      EList<VarDeclaration> _inputVars_3 = ifl.getInputVars();
      ArrayList<Object> _typeList = LuaConstants.getTypeList(_inputVars_3);
      CharSequence _luaValueList = LuaUtils.luaValueList(_typeList);
      _builder.append(_luaValueList, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numDOs = ");
      EList<VarDeclaration> _outputVars_1 = ifl.getOutputVars();
      int _size_3 = _outputVars_1.size();
      _builder.append(_size_3, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DONames = ");
      EList<VarDeclaration> _outputVars_2 = ifl.getOutputVars();
      final Function1<VarDeclaration, String> _function_3 = (VarDeclaration it) -> {
        return it.getName();
      };
      List<String> _map_3 = ListExtensions.<VarDeclaration, String>map(_outputVars_2, _function_3);
      CharSequence _luaStringList_3 = LuaUtils.luaStringList(_map_3);
      _builder.append(_luaStringList_3, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("DODataTypeNames = ");
      EList<VarDeclaration> _outputVars_3 = ifl.getOutputVars();
      ArrayList<Object> _typeList_1 = LuaConstants.getTypeList(_outputVars_3);
      CharSequence _luaValueList_1 = LuaUtils.luaValueList(_typeList_1);
      _builder.append(_luaValueList_1, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("numAdapters = ");
      EList<AdapterDeclaration> _plugs = ifl.getPlugs();
      int _size_4 = _plugs.size();
      EList<AdapterDeclaration> _sockets = ifl.getSockets();
      int _size_5 = _sockets.size();
      int _plus = (_size_4 + _size_5);
      _builder.append(_plus, "  ");
      _builder.append(",");
      _builder.newLineIfNotEmpty();
      _builder.append("  ");
      _builder.append("adapterInstanceDefinition = {");
      _builder.newLine();
      _builder.append("    ");
      EList<AdapterDeclaration> _plugs_1 = ifl.getPlugs();
      final Function1<AdapterDeclaration, String> _function_4 = (AdapterDeclaration it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{adapterNameID = \"");
        String _name = it.getName();
        _builder_1.append(_name, "");
        _builder_1.append("\", adapterTypeNameID = \"");
        String _typeName = it.getTypeName();
        _builder_1.append(_typeName, "");
        _builder_1.append("\", isPlug = true}");
        return _builder_1.toString();
      };
      List<String> _map_4 = ListExtensions.<AdapterDeclaration, String>map(_plugs_1, _function_4);
      String _join = IterableExtensions.join(_map_4, ",\n");
      _builder.append(_join, "    ");
      _builder.newLineIfNotEmpty();
      _builder.append("    ");
      EList<AdapterDeclaration> _sockets_1 = ifl.getSockets();
      final Function1<AdapterDeclaration, String> _function_5 = (AdapterDeclaration it) -> {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("{adapterNameID = \"");
        String _name = it.getName();
        _builder_1.append(_name, "");
        _builder_1.append("\", adapterTypeNameID = \"");
        String _typeName = it.getTypeName();
        _builder_1.append(_typeName, "");
        _builder_1.append("\", isPlug = false}");
        return _builder_1.toString();
      };
      List<String> _map_5 = ListExtensions.<AdapterDeclaration, String>map(_sockets_1, _function_5);
      String _join_1 = IterableExtensions.join(_map_5, ",\n");
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
    EList<VarDeclaration> _internalVars = type.getInternalVars();
    int _size = _internalVars.size();
    _builder.append(_size, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("intVarsNames = ");
    EList<VarDeclaration> _internalVars_1 = type.getInternalVars();
    final Function1<VarDeclaration, String> _function = (VarDeclaration it) -> {
      return it.getName();
    };
    List<String> _map = ListExtensions.<VarDeclaration, String>map(_internalVars_1, _function);
    CharSequence _luaStringList = LuaUtils.luaStringList(_map);
    _builder.append(_luaStringList, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("intVarsDataTypeNames = ");
    EList<VarDeclaration> _internalVars_2 = type.getInternalVars();
    ArrayList<Object> _typeList = LuaConstants.getTypeList(_internalVars_2);
    CharSequence _luaValueList = LuaUtils.luaValueList(_typeList);
    _builder.append(_luaValueList, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }
}
