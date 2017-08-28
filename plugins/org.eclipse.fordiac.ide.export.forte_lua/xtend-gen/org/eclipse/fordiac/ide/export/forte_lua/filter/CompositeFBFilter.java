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

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
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
    InterfaceList _interfaceList = type.getInterfaceList();
    CharSequence _luaEventConstants = LuaConstants.luaEventConstants(_interfaceList);
    _builder.append(_luaEventConstants, "");
    _builder.newLineIfNotEmpty();
    InterfaceList _interfaceList_1 = type.getInterfaceList();
    CharSequence _luaFBVariableConstants = LuaConstants.luaFBVariableConstants(_interfaceList_1);
    _builder.append(_luaFBVariableConstants, "");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    InterfaceList _interfaceList_2 = type.getInterfaceList();
    CharSequence _luaInterfaceSpec = LuaConstants.luaInterfaceSpec(_interfaceList_2);
    _builder.append(_luaInterfaceSpec, "");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _luaFbnSpec = CompositeFBFilter.luaFbnSpec(type);
    _builder.append(_luaFbnSpec, "");
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
    FBNetwork _fBNetwork = type.getFBNetwork();
    CharSequence _luaInternalFBs = CompositeFBFilter.luaInternalFBs(_fBNetwork);
    _builder.append(_luaInternalFBs, "  ");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    FBNetwork _fBNetwork_1 = type.getFBNetwork();
    CharSequence _luaParameters = CompositeFBFilter.luaParameters(_fBNetwork_1);
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
    FBNetwork _fBNetwork_2 = type.getFBNetwork();
    CharSequence _luaFbnData = CompositeFBFilter.luaFbnData(_fBNetwork_2);
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
    EList<FBNetworkElement> _networkElements = fbn.getNetworkElements();
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
      return Boolean.valueOf((!(e instanceof AdapterFB)));
    };
    Iterable<FBNetworkElement> fbs = IterableExtensions.<FBNetworkElement>filter(_networkElements, _function);
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
      ArrayList<ArrayList<?>> parameters = CompositeFBFilter.getParameters(fbn);
      EList<FBNetworkElement> _networkElements = fbn.getNetworkElements();
      Iterable<AdapterFB> _filter = Iterables.<AdapterFB>filter(_networkElements, AdapterFB.class);
      int numAdapters = IterableExtensions.size(_filter);
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
          int _minus = ((((Integer) _get)).intValue() - numAdapters);
          _builder.append(_minus, "  ");
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
    FBNetwork _fBNetwork = type.getFBNetwork();
    EList<FBNetworkElement> _networkElements = _fBNetwork.getNetworkElements();
    Iterable<AdapterFB> _filter = Iterables.<AdapterFB>filter(_networkElements, AdapterFB.class);
    int numAdapters = IterableExtensions.size(_filter);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    FBNetwork _fBNetwork_1 = type.getFBNetwork();
    EList<EventConnection> allCons = _fBNetwork_1.getEventConnections();
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
        Event _source = con.getSource();
        FBNetworkElement sne = _source.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        Event _destination = con.getDestination();
        FBNetworkElement dne = _destination.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if (((!Objects.equal(null, dne)) && (!Objects.equal(null, sne)))) {
            _builder.append("  ");
            _builder.append("{");
            Event _source_1 = con.getSource();
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(sne, _source_1, numAdapters, type, "src");
            _builder.append(_luaConnectionString, "  ");
            _builder.append(", ");
            Event _destination_1 = con.getDestination();
            String _luaConnectionString_1 = CompositeFBFilter.luaConnectionString(dne, _destination_1, numAdapters, type, "dst");
            _builder.append(_luaConnectionString_1, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            boolean _equals = Objects.equal(null, dne);
            if (_equals) {
              _builder.append("  ");
              _builder.append("{");
              Event _source_2 = con.getSource();
              String _luaConnectionString_2 = CompositeFBFilter.luaConnectionString(sne, _source_2, numAdapters, type, "src");
              _builder.append(_luaConnectionString_2, "  ");
              _builder.append(", dstID = \"");
              Event _destination_2 = con.getDestination();
              String _name = _destination_2.getName();
              _builder.append(_name, "  ");
              _builder.append("\", dstFBNum = -1}");
              _builder.newLineIfNotEmpty();
            } else {
              boolean _equals_1 = Objects.equal(null, sne);
              if (_equals_1) {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                Event _source_3 = con.getSource();
                String _name_1 = _source_3.getName();
                _builder.append(_name_1, "  ");
                _builder.append("\", srcFBNum = -1, ");
                Event _destination_3 = con.getDestination();
                String _luaConnectionString_3 = CompositeFBFilter.luaConnectionString(dne, _destination_3, numAdapters, type, "dst");
                _builder.append(_luaConnectionString_3, "  ");
                _builder.append("}");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                Event _source_4 = con.getSource();
                String _name_2 = _source_4.getName();
                _builder.append(_name_2, "  ");
                _builder.append("\", srcFBNum = -1, dstID = \"");
                Event _destination_4 = con.getDestination();
                String _name_3 = _destination_4.getName();
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
  
  public static String luaConnectionString(final FBNetworkElement e, final IInterfaceElement ev, final int numAdapters, final CompositeFBType type, final String stringID) {
    InterfaceList _interfaceList = type.getInterfaceList();
    EList<AdapterDeclaration> _plugs = _interfaceList.getPlugs();
    final Function1<AdapterDeclaration, AdapterFB> _function = (AdapterDeclaration it) -> {
      return it.getAdapterFB();
    };
    List<AdapterFB> _map = ListExtensions.<AdapterDeclaration, AdapterFB>map(_plugs, _function);
    List<AdapterFB> plugs = IterableExtensions.<AdapterFB>toList(_map);
    InterfaceList _interfaceList_1 = type.getInterfaceList();
    EList<AdapterDeclaration> _sockets = _interfaceList_1.getSockets();
    final Function1<AdapterDeclaration, AdapterFB> _function_1 = (AdapterDeclaration it) -> {
      return it.getAdapterFB();
    };
    List<AdapterFB> _map_1 = ListExtensions.<AdapterDeclaration, AdapterFB>map(_sockets, _function_1);
    List<AdapterFB> sockets = IterableExtensions.<AdapterFB>toList(_map_1);
    if ((e instanceof AdapterFB)) {
      boolean _contains = plugs.contains(e);
      if (_contains) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append(stringID, "");
        _builder.append("ID = \"");
        String _name = ev.getName();
        _builder.append(_name, "");
        _builder.append("\", ");
        _builder.append(stringID, "");
        _builder.append("FBNum = ");
        int _indexOf = plugs.indexOf(e);
        int _bitwiseOr = (CompositeFBFilter.ADAPTER_MARKER | _indexOf);
        _builder.append(_bitwiseOr, "");
        return _builder.toString();
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(stringID, "");
        _builder_1.append("ID = \"");
        String _name_1 = ev.getName();
        _builder_1.append(_name_1, "");
        _builder_1.append("\", ");
        _builder_1.append(stringID, "");
        _builder_1.append("FBNum = ");
        int _size = plugs.size();
        int _indexOf_1 = sockets.indexOf(e);
        int _plus = (_size + _indexOf_1);
        int _bitwiseOr_1 = (CompositeFBFilter.ADAPTER_MARKER | _plus);
        _builder_1.append(_bitwiseOr_1, "");
        return _builder_1.toString();
      }
    } else {
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append(stringID, "");
      _builder_2.append("ID = \"");
      String _name_2 = ev.getName();
      _builder_2.append(_name_2, "");
      _builder_2.append("\", ");
      _builder_2.append(stringID, "");
      _builder_2.append("FBNum = ");
      FBNetwork _fBNetwork = type.getFBNetwork();
      EList<FBNetworkElement> _networkElements = _fBNetwork.getNetworkElements();
      int _indexOf_2 = _networkElements.indexOf(e);
      int _minus = (_indexOf_2 - numAdapters);
      _builder_2.append(_minus, "");
      return _builder_2.toString();
    }
  }
  
  public static CharSequence luaFannedOutEventConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fannedOutEventConnections = {");
    _builder.newLine();
    _builder.append("  ");
    FBNetwork _fBNetwork = type.getFBNetwork();
    EList<FBNetworkElement> _networkElements = _fBNetwork.getNetworkElements();
    Iterable<AdapterFB> _filter = Iterables.<AdapterFB>filter(_networkElements, AdapterFB.class);
    int numAdapters = IterableExtensions.size(_filter);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    FBNetwork _fBNetwork_1 = type.getFBNetwork();
    EList<EventConnection> allCons = _fBNetwork_1.getEventConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<EventConnection, Boolean> _function = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<EventConnection> _filter_1 = IterableExtensions.<EventConnection>filter(allCons, _function);
    List<EventConnection> conList = IterableExtensions.<EventConnection>toList(_filter_1);
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
          _builder.appendImmediate(",", "");
        }
        Event _destination = con.getDestination();
        FBNetworkElement dne = _destination.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          boolean _notEquals = (!Objects.equal(null, dne));
          if (_notEquals) {
            _builder.append("{connectionNum = ");
            int _connectionNumber = CompositeFBFilter.getConnectionNumber(conList, con);
            _builder.append(_connectionNumber, "");
            _builder.append(", ");
            Event _destination_1 = con.getDestination();
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(dne, _destination_1, numAdapters, type, "dst");
            _builder.append(_luaConnectionString, "");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("{dstID = \"");
            Event _destination_2 = con.getDestination();
            String _name = _destination_2.getName();
            _builder.append(_name, "");
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
    IInterfaceElement _source = con.getSource();
    EList<Connection> _outputConnections = _source.getOutputConnections();
    Connection _get = _outputConnections.get(0);
    int num = allCons.indexOf(_get);
    return num;
  }
  
  public static CharSequence luaDataConnections(final CompositeFBType type) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("dataConnections = {");
    _builder.newLine();
    _builder.append("  ");
    FBNetwork _fBNetwork = type.getFBNetwork();
    EList<FBNetworkElement> _networkElements = _fBNetwork.getNetworkElements();
    Iterable<AdapterFB> _filter = Iterables.<AdapterFB>filter(_networkElements, AdapterFB.class);
    int numAdapters = IterableExtensions.size(_filter);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    FBNetwork _fBNetwork_1 = type.getFBNetwork();
    EList<DataConnection> allCons = _fBNetwork_1.getDataConnections();
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
        VarDeclaration _source = con.getSource();
        FBNetworkElement sne = _source.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        VarDeclaration _destination = con.getDestination();
        FBNetworkElement dne = _destination.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          if (((!Objects.equal(null, dne)) && (!Objects.equal(null, sne)))) {
            _builder.append("  ");
            _builder.append("{");
            VarDeclaration _source_1 = con.getSource();
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(sne, _source_1, numAdapters, type, "src");
            _builder.append(_luaConnectionString, "  ");
            _builder.append(", ");
            VarDeclaration _destination_1 = con.getDestination();
            String _luaConnectionString_1 = CompositeFBFilter.luaConnectionString(dne, _destination_1, numAdapters, type, "dst");
            _builder.append(_luaConnectionString_1, "  ");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            boolean _equals = Objects.equal(null, dne);
            if (_equals) {
              _builder.append("  ");
              _builder.append("{");
              VarDeclaration _source_2 = con.getSource();
              String _luaConnectionString_2 = CompositeFBFilter.luaConnectionString(sne, _source_2, numAdapters, type, "src");
              _builder.append(_luaConnectionString_2, "  ");
              _builder.append(", dstID = \"");
              VarDeclaration _destination_2 = con.getDestination();
              String _name = _destination_2.getName();
              _builder.append(_name, "  ");
              _builder.append("\", dstFBNum = -1}");
              _builder.newLineIfNotEmpty();
            } else {
              boolean _equals_1 = Objects.equal(null, sne);
              if (_equals_1) {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                VarDeclaration _source_3 = con.getSource();
                String _name_1 = _source_3.getName();
                _builder.append(_name_1, "  ");
                _builder.append("\", srcFBNum = -1, ");
                VarDeclaration _destination_3 = con.getDestination();
                String _luaConnectionString_3 = CompositeFBFilter.luaConnectionString(dne, _destination_3, numAdapters, type, "dst");
                _builder.append(_luaConnectionString_3, "  ");
                _builder.append("}");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("  ");
                _builder.append("{srcID = \"");
                VarDeclaration _source_4 = con.getSource();
                String _name_2 = _source_4.getName();
                _builder.append(_name_2, "  ");
                _builder.append("\", srcFBNum = -1, dstID = \"");
                String _name_3 = dne.getName();
                _builder.append(_name_3, "  ");
                _builder.append(".");
                VarDeclaration _destination_4 = con.getDestination();
                String _name_4 = _destination_4.getName();
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
    FBNetwork _fBNetwork = type.getFBNetwork();
    EList<FBNetworkElement> _networkElements = _fBNetwork.getNetworkElements();
    Iterable<AdapterFB> _filter = Iterables.<AdapterFB>filter(_networkElements, AdapterFB.class);
    int numAdapters = IterableExtensions.size(_filter);
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    FBNetwork _fBNetwork_1 = type.getFBNetwork();
    EList<DataConnection> allCons = _fBNetwork_1.getDataConnections();
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    final Function1<DataConnection, Boolean> _function = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<DataConnection> _filter_1 = IterableExtensions.<DataConnection>filter(allCons, _function);
    List<DataConnection> conList = IterableExtensions.<DataConnection>toList(_filter_1);
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
          _builder.appendImmediate(",", "");
        }
        VarDeclaration _destination = con.getDestination();
        FBNetworkElement dne = _destination.getFBNetworkElement();
        _builder.newLineIfNotEmpty();
        {
          boolean _notEquals = (!Objects.equal(null, dne));
          if (_notEquals) {
            _builder.append("{connectionNum = ");
            int _connectionNumber = CompositeFBFilter.getConnectionNumber(conList, con);
            _builder.append(_connectionNumber, "");
            _builder.append(", ");
            VarDeclaration _destination_1 = con.getDestination();
            String _luaConnectionString = CompositeFBFilter.luaConnectionString(dne, _destination_1, numAdapters, type, "dst");
            _builder.append(_luaConnectionString, "");
            _builder.append("}");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("{\"");
            VarDeclaration _destination_2 = con.getDestination();
            String _name = _destination_2.getName();
            _builder.append(_name, "");
            _builder.append("\", -1}");
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
    EList<FBNetworkElement> _networkElements = fbn.getNetworkElements();
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement e) -> {
      return Boolean.valueOf((!(e instanceof AdapterFB)));
    };
    Iterable<FBNetworkElement> _filter = IterableExtensions.<FBNetworkElement>filter(_networkElements, _function);
    int _size = IterableExtensions.size(_filter);
    _builder.append(_size, "");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numECons = ");
    EList<EventConnection> _eventConnections = fbn.getEventConnections();
    final Function1<EventConnection, Boolean> _function_1 = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<EventConnection> _filter_1 = IterableExtensions.<EventConnection>filter(_eventConnections, _function_1);
    int _size_1 = IterableExtensions.size(_filter_1);
    _builder.append(_size_1, "");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numFECons = ");
    EList<EventConnection> _eventConnections_1 = fbn.getEventConnections();
    final Function1<EventConnection, Boolean> _function_2 = (EventConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<EventConnection> _filter_2 = IterableExtensions.<EventConnection>filter(_eventConnections_1, _function_2);
    int _size_2 = IterableExtensions.size(_filter_2);
    _builder.append(_size_2, "");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numDCons = ");
    EList<DataConnection> _dataConnections = fbn.getDataConnections();
    final Function1<DataConnection, Boolean> _function_3 = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() == 1) || ((e.getSource().getOutputConnections().size() > 1) && e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<DataConnection> _filter_3 = IterableExtensions.<DataConnection>filter(_dataConnections, _function_3);
    int _size_3 = IterableExtensions.size(_filter_3);
    _builder.append(_size_3, "");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numFDCons = ");
    EList<DataConnection> _dataConnections_1 = fbn.getDataConnections();
    final Function1<DataConnection, Boolean> _function_4 = (DataConnection e) -> {
      return Boolean.valueOf(((e.getSource().getOutputConnections().size() > 1) && (!e.getSource().getOutputConnections().get(0).equals(e))));
    };
    Iterable<DataConnection> _filter_4 = IterableExtensions.<DataConnection>filter(_dataConnections_1, _function_4);
    int _size_4 = IterableExtensions.size(_filter_4);
    _builder.append(_size_4, "");
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("numParams = ");
    int _numParameter = CompositeFBFilter.getNumParameter(fbn);
    _builder.append(_numParameter, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private static int getNumParameter(final FBNetwork fbn) {
    ArrayList<ArrayList<?>> _parameters = CompositeFBFilter.getParameters(fbn);
    return _parameters.size();
  }
  
  private static ArrayList<ArrayList<?>> getParameters(final FBNetwork fbn) {
    ArrayList<ArrayList<?>> parameters = new ArrayList<ArrayList<?>>();
    EList<FBNetworkElement> _networkElements = fbn.getNetworkElements();
    for (final FBNetworkElement ne : _networkElements) {
      InterfaceList _interface = ne.getInterface();
      EList<VarDeclaration> _inputVars = _interface.getInputVars();
      for (final VarDeclaration iv : _inputVars) {
        if ((((!Objects.equal(null, iv.getValue())) && (!Objects.equal(null, iv.getValue().getValue()))) && (!iv.getValue().getValue().isEmpty()))) {
          ArrayList<Object> list = new ArrayList<Object>();
          EList<FBNetworkElement> _networkElements_1 = fbn.getNetworkElements();
          int _indexOf = _networkElements_1.indexOf(ne);
          list.add(Integer.valueOf(_indexOf));
          String _name = iv.getName();
          list.add(_name);
          Value _value = iv.getValue();
          String _value_1 = _value.getValue();
          list.add(_value_1);
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
