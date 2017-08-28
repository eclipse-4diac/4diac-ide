/**
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_lua.filter;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class AdapterFilter {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<String> errors = new ArrayList<String>();
  
  public CharSequence lua(final AdapterType type) {
    StringConcatenation _builder = new StringConcatenation();
    InterfaceList _interfaceList = type.getInterfaceList();
    CharSequence _luaEventDataInterfaceSpec = AdapterFilter.luaEventDataInterfaceSpec(_interfaceList);
    _builder.append(_luaEventDataInterfaceSpec, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("return {interfaceSpec = interfaceSpec}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence luaEventDataInterfaceSpec(final InterfaceList ifl) {
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
      _builder.newLineIfNotEmpty();
      _builder.append("}");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  @Pure
  public List<String> getErrors() {
    return this.errors;
  }
}
