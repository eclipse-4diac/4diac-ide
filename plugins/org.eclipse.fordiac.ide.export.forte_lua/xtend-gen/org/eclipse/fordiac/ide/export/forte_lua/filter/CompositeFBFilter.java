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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class CompositeFBFilter {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private List<String> errors = new ArrayList<String>();
  
  private final static int ADAPTER_MARKER = 0x10000;
  
  public CharSequence lua(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _luaEventConstants = LuaConstants.luaEventConstants(type.getInterfaceList());
    _builder.append(_luaEventConstants);
    _builder.newLineIfNotEmpty();
    CharSequence _luaFBVariableConstants = LuaConstants.luaFBVariableConstants(type.getInterfaceList());
    _builder.append(_luaFBVariableConstants);
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    CharSequence _luaInterfaceSpec = LuaConstants.luaInterfaceSpec(type.getInterfaceList());
    _builder.append(_luaInterfaceSpec);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaFbnSpec = CompositeFBFilter.luaFbnSpec(type);
    _builder.append(_luaFbnSpec);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("return {interfaceSpec = interfaceSpec, fbnSpec = fbnSpec}");
    _builder.newLine();
    return _builder;
  }
  
  public static CharSequence luaFbnSpec(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("local fbnSpec = {");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _luaInternalFBs = CompositeFBFilter.luaInternalFBs(type.getFBNetwork());
    _builder.append(_luaInternalFBs, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaParameters = CompositeFBFilter.luaParameters(type.getFBNetwork());
    _builder.append(_luaParameters, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaEventConnections = CompositeFBFilter.luaEventConnections(type);
    _builder.append(_luaEventConnections, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaFannedOutEventConnections = CompositeFBFilter.luaFannedOutEventConnections(type);
    _builder.append(_luaFannedOutEventConnections, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaDataConnections = CompositeFBFilter.luaDataConnections(type);
    _builder.append(_luaDataConnections, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaFannedOutDataConnections = CompositeFBFilter.luaFannedOutDataConnections(type);
    _builder.append(_luaFannedOutDataConnections, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _luaFbnData = CompositeFBFilter.luaFbnData(type.getFBNetwork());
    _builder.append(_luaFbnData, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaInternalFBs(final FBNetwork fbn) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("internalFBs = {");
    _builder.newLine();
    _builder.append("  ");
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
      return Boolean.valueOf((!(e instanceof AdapterFB)));
    };
    Iterable<FBNetworkElement> fbs = IterableExtensions.<FBNetworkElement>filter(fbn.getNetworkElements(), _function);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final FBNetworkElement fb : fbs) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "  ");
        }
        {
          if ((!(fb instanceof AdapterFB))) {
            _builder.append("  ");
            _builder.append("{fbNameID = \"");
            String _name = fb.getName();
            _builder.append(_name, "  ");
            _builder.append("\", fbTypeID = \"");
            String _typeName = fb.getTypeName();
            _builder.append(_typeName, "  ");
            _builder.append("\"}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaParameters(final FBNetwork fbn) {
    CharSequence _xblockexpression = null;
    {
      final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
        return Boolean.valueOf((!(e instanceof AdapterFB)));
      };
      Iterable<FBNetworkElement> fbs = IterableExtensions.<FBNetworkElement>filter(fbn.getNetworkElements(), _function);
      ArrayList<ArrayList<?>> parameters = CompositeFBFilter.getParameters(IterableExtensions.<FBNetworkElement>toList(fbs));
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("parameters = {");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final ArrayList<?> p : parameters) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(",", "  ");
          }
          _builder.append("  ");
          _builder.append("{fbNum = ");
          Object _get = p.get(0);
          _builder.append(((Integer) _get), "  ");
          _builder.append(", diNameID = \"");
          Object _get_1 = p.get(1);
          _builder.append(_get_1, "  ");
          _builder.append("\", paramValue = \"");
          Object _get_2 = p.get(2);
          _builder.append(_get_2, "  ");
          _builder.append("\"}");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _xblockexpression = _builder;
    }
    return _xblockexpression;
  }
  
  public static CharSequence luaEventConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("eventConnections = {");
    _builder.newLine();
    _builder.append("  ");
    EList<EventConnection> allCons = type.getFBNetwork().getEventConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<EventConnection, Boolean> _function = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<EventConnection> connections = IterableExtensions.<EventConnection>filter(allCons, _function);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final EventConnection con : connections) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "  ");
        }
        _builder.append("  ");
        FBNetworkElement sne = con.getSource().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        FBNetworkElement dne = con.getDestination().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if (((null != dne) && (null != sne))) {
            _builder.append("  ");
            _builder.append("{");
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(sne, con.getSource(), type, "src");
            _builder.append(_luaConnectionString, "  ");
            _builder.append(", ");
            String _luaConnectionString_1 = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
            _builder.append(_luaConnectionString_1, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            if ((null == dne)) {
              _builder.append("  ");
              _builder.append("{");
              String _luaConnectionString_2 = CompositeFBFilter.luaConnectionString(sne, con.getSource(), type, "src");
              _builder.append(_luaConnectionString_2, "  ");
              _builder.append(", dstID = \"");
              String _name = con.getDestination().getName();
              _builder.append(_name, "  ");
              _builder.append("\", dstFBNum = -1}");
              _builder.newLineIfNotEmpty();
            } else {
              if ((null == sne)) {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                String _name_1 = con.getSource().getName();
                _builder.append(_name_1, "  ");
                _builder.append("\", srcFBNum = -1, ");
                String _luaConnectionString_3 = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
                _builder.append(_luaConnectionString_3, "  ");
                _builder.append("}");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                String _name_2 = con.getSource().getName();
                _builder.append(_name_2, "  ");
                _builder.append("\", srcFBNum = -1, dstID = \"");
                String _name_3 = con.getDestination().getName();
                _builder.append(_name_3, "  ");
                _builder.append("\", dstFBNum = -1}");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static String luaConnectionString(final FBNetworkElement e, final IInterfaceElement ev, final CompositeFBType type, final String stringID) {
    final Function1<AdapterDeclaration, AdapterFB> _function = (AdapterDeclaration it) -> {
      return it.getAdapterFB();
    };
    List<AdapterFB> plugs = IterableExtensions.<AdapterFB>toList(ListExtensions.<AdapterDeclaration, AdapterFB>map(type.getInterfaceList().getPlugs(), _function));
    final Function1<AdapterDeclaration, AdapterFB> _function_1 = (AdapterDeclaration it) -> {
      return it.getAdapterFB();
    };
    List<AdapterFB> sockets = IterableExtensions.<AdapterFB>toList(ListExtensions.<AdapterDeclaration, AdapterFB>map(type.getInterfaceList().getSockets(), _function_1));
    if ((e instanceof AdapterFB)) {
      boolean _contains = plugs.contains(e);
      if (_contains) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(stringID);
        _builder.append("ID = \"");
        String _name = ev.getName();
        _builder.append(_name);
        _builder.append("\", ");
        _builder.append(stringID);
        _builder.append("FBNum = ");
        _builder.append((CompositeFBFilter.ADAPTER_MARKER | plugs.indexOf(e)));
        return _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(stringID);
        _builder_1.append("ID = \"");
        String _name_1 = ev.getName();
        _builder_1.append(_name_1);
        _builder_1.append("\", ");
        _builder_1.append(stringID);
        _builder_1.append("FBNum = ");
        int _size = plugs.size();
        int _indexOf = sockets.indexOf(e);
        int _plus = (_size + _indexOf);
        int _bitwiseOr = (CompositeFBFilter.ADAPTER_MARKER | _plus);
        _builder_1.append(_bitwiseOr);
        return _builder_1.toString();
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(stringID);
      _builder_2.append("ID = \"");
      String _name_2 = ev.getName();
      _builder_2.append(_name_2);
      _builder_2.append("\", ");
      _builder_2.append(stringID);
      _builder_2.append("FBNum = ");
      final Function1<FBNetworkElement, Boolean> _function_2 = (FBNetworkElement f) -> {
        return Boolean.valueOf((!(f instanceof AdapterFB)));
      };
      int _indexOf_1 = IterableExtensions.<FBNetworkElement>toList(IterableExtensions.<FBNetworkElement>filter(type.getFBNetwork().getNetworkElements(), _function_2)).indexOf(e);
      _builder_2.append(_indexOf_1);
      return _builder_2.toString();
    }
  }
  
  public static CharSequence luaFannedOutEventConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fannedOutEventConnections = {");
    _builder.newLine();
    _builder.append("  ");
    EList<EventConnection> allCons = type.getFBNetwork().getEventConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<EventConnection, Boolean> _function = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    List<EventConnection> conList = IterableExtensions.<EventConnection>toList(IterableExtensions.<EventConnection>filter(allCons, _function));
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<EventConnection, Boolean> _function_1 = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<EventConnection> connections = IterableExtensions.<EventConnection>filter(allCons, _function_1);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final EventConnection con : connections) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "  ");
        }
        _builder.append("  \t  ");
        FBNetworkElement dne = con.getDestination().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if ((null != dne)) {
            _builder.append("  ");
            _builder.append("{connectionNum = ");
            int _connectionNumber = CompositeFBFilter.getConnectionNumber(conList, con);
            _builder.append(_connectionNumber, "  ");
            _builder.append(", ");
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
            _builder.append(_luaConnectionString, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("{dstID = \"");
            String _name = con.getDestination().getName();
            _builder.append(_name);
            _builder.append("\", -1}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static int getConnectionNumber(final List<?> allCons, final Connection con) {
    int num = allCons.indexOf(con.getSource().getOutputConnections().get(0));
    return num;
  }
  
  public static CharSequence luaDataConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("dataConnections = {");
    _builder.newLine();
    _builder.append("  ");
    EList<DataConnection> allCons = type.getFBNetwork().getDataConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<DataConnection, Boolean> _function = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<DataConnection> connections = IterableExtensions.<DataConnection>filter(allCons, _function);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final DataConnection con : connections) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "  ");
        }
        _builder.append("  ");
        FBNetworkElement sne = con.getSource().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        FBNetworkElement dne = con.getDestination().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if (((null != dne) && (null != sne))) {
            _builder.append("  ");
            _builder.append("{");
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(sne, con.getSource(), type, "src");
            _builder.append(_luaConnectionString, "  ");
            _builder.append(", ");
            String _luaConnectionString_1 = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
            _builder.append(_luaConnectionString_1, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            if ((null == dne)) {
              _builder.append("  ");
              _builder.append("{");
              String _luaConnectionString_2 = CompositeFBFilter.luaConnectionString(sne, con.getSource(), type, "src");
              _builder.append(_luaConnectionString_2, "  ");
              _builder.append(", dstID = \"");
              String _name = con.getDestination().getName();
              _builder.append(_name, "  ");
              _builder.append("\", dstFBNum = -1}");
              _builder.newLineIfNotEmpty();
            } else {
              if ((null == sne)) {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                String _name_1 = con.getSource().getName();
                _builder.append(_name_1, "  ");
                _builder.append("\", srcFBNum = -1, ");
                String _luaConnectionString_3 = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
                _builder.append(_luaConnectionString_3, "  ");
                _builder.append("}");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                String _name_2 = con.getSource().getName();
                _builder.append(_name_2, "  ");
                _builder.append("\", srcFBNum = -1, dstID = \"");
                String _name_3 = dne.getName();
                _builder.append(_name_3, "  ");
                _builder.append(".");
                String _name_4 = con.getDestination().getName();
                _builder.append(_name_4, "  ");
                _builder.append("\", dstFBNum = -1}");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaFannedOutDataConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fannedOutDataConnections = {");
    _builder.newLine();
    _builder.append("  ");
    EList<DataConnection> allCons = type.getFBNetwork().getDataConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<DataConnection, Boolean> _function = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    List<DataConnection> conList = IterableExtensions.<DataConnection>toList(IterableExtensions.<DataConnection>filter(allCons, _function));
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<DataConnection, Boolean> _function_1 = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<DataConnection> connections = IterableExtensions.<DataConnection>filter(allCons, _function_1);
    _builder.newLineIfNotEmpty();
    {
      boolean _hasElements = false;
      for(final DataConnection con : connections) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "  ");
        }
        _builder.append("  \t  ");
        FBNetworkElement dne = con.getDestination().getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if ((null != dne)) {
            _builder.append("  ");
            _builder.append("{connectionNum = ");
            int _connectionNumber = CompositeFBFilter.getConnectionNumber(conList, con);
            _builder.append(_connectionNumber, "  ");
            _builder.append(", ");
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(dne, con.getDestination(), type, "dst");
            _builder.append(_luaConnectionString, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("  ");
            _builder.append("{dstID = \"");
            String _name = con.getDestination().getName();
            _builder.append(_name, "  ");
            _builder.append("\", dstFBNum = -1}");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("}");
    return _builder;
  }
  
  public static CharSequence luaFbnData(final FBNetwork fbn) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("numFBs = ");
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
      return Boolean.valueOf((!(e instanceof AdapterFB)));
    };
    int _size = IterableExtensions.size(IterableExtensions.<FBNetworkElement>filter(fbn.getNetworkElements(), _function));
    _builder.append(_size);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numECons = ");
    final Function1<EventConnection, Boolean> _function_1 = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    int _size_1 = IterableExtensions.size(IterableExtensions.<EventConnection>filter(fbn.getEventConnections(), _function_1));
    _builder.append(_size_1);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numFECons = ");
    final Function1<EventConnection, Boolean> _function_2 = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    int _size_2 = IterableExtensions.size(IterableExtensions.<EventConnection>filter(fbn.getEventConnections(), _function_2));
    _builder.append(_size_2);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numDCons = ");
    final Function1<DataConnection, Boolean> _function_3 = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    int _size_3 = IterableExtensions.size(IterableExtensions.<DataConnection>filter(fbn.getDataConnections(), _function_3));
    _builder.append(_size_3);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numFDCons = ");
    final Function1<DataConnection, Boolean> _function_4 = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    int _size_4 = IterableExtensions.size(IterableExtensions.<DataConnection>filter(fbn.getDataConnections(), _function_4));
    _builder.append(_size_4);
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numParams = ");
    int _numParameter = CompositeFBFilter.getNumParameter(fbn);
    _builder.append(_numParameter);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private static int getNumParameter(final FBNetwork fbn) {
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
      return Boolean.valueOf((!(e instanceof AdapterFB)));
    };
    return CompositeFBFilter.getParameters(IterableExtensions.<FBNetworkElement>toList(IterableExtensions.<FBNetworkElement>filter(fbn.getNetworkElements(), _function))).size();
  }
  
  private static ArrayList<ArrayList<?>> getParameters(final List<FBNetworkElement> fbs) {
    ArrayList<ArrayList<?>> parameters = new ArrayList<ArrayList<?>>();
    for (final FBNetworkElement ne : fbs) {
      EList<VarDeclaration> _inputVars = ne.getInterface().getInputVars();
      for (final VarDeclaration iv : _inputVars) {
        if ((((null != iv.getValue()) && (null != iv.getValue().getValue())) && (!iv.getValue().getValue().isEmpty()))) {
          ArrayList<Object> list = new ArrayList<Object>();
          list.add(Integer.valueOf(fbs.indexOf(ne)));
          list.add(iv.getName());
          list.add(iv.getValue().getValue());
          parameters.add(list);
        }
      }
    }
    return parameters;
  }
  
  @Pure
  public List<String> getErrors() {
    return this.errors;
  }
}
