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
    CharSequence _luaEventDataInterfaceSpec = AdapterFilter.luaEventDataInterfaceSpec(type.getInterfaceList());
    _builder.append(_luaEventDataInterfaceSpec);
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
