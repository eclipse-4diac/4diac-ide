/**
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - fixed adapter and fb number generation in connection lists
 */
package org.eclipse.fordiac.ide.export.forte_ng.composite;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class CompositeFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private CompositeFBType type;
  
  private ArrayList<FBNetworkElement> fbs = new ArrayList<FBNetworkElement>();
  
  public CompositeFBImplTemplate(final CompositeFBType type, final String name, final Path prefix) {
    super(name, prefix);
    this.type = type;
    final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement it) -> {
      FBType _type = it.getType();
      return Boolean.valueOf((!(_type instanceof AdapterFBType)));
    };
    Iterables.<FBNetworkElement>addAll(this.fbs, IterableExtensions.<FBNetworkElement>filter(type.getFBNetwork().getNetworkElements(), _function));
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
    CharSequence _generateFBNetwork = this.generateFBNetwork();
    _builder.append(_generateFBNetwork);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateFBNetwork() {
    StringConcatenation _builder = new StringConcatenation();
    {
      final Function1<FBNetworkElement, Boolean> _function = (FBNetworkElement it) -> {
        FBType _type = it.getType();
        return Boolean.valueOf((!(_type instanceof AdapterFBType)));
      };
      boolean _exists = IterableExtensions.<FBNetworkElement>exists(this.type.getFBNetwork().getNetworkElements(), _function);
      if (_exists) {
        _builder.append("const SCFB_FBInstanceData ");
        CharSequence _fBClassName = this.getFBClassName();
        _builder.append(_fBClassName);
        _builder.append("::scm_astInternalFBs[] = {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        {
          boolean _hasElements = false;
          for(final FBNetworkElement elem : this.fbs) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",\n", "  ");
            }
            _builder.append("{");
            CharSequence _fORTEString = this.getFORTEString(elem.getName());
            _builder.append(_fORTEString, "  ");
            _builder.append(", ");
            CharSequence _fORTEString_1 = this.getFORTEString(elem.getType().getName());
            _builder.append(_fORTEString_1, "  ");
            _builder.append("}");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty = this.type.getFBNetwork().getEventConnections().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("const SCFB_FBConnectionData ");
        CharSequence _fBClassName_1 = this.getFBClassName();
        _builder.append(_fBClassName_1);
        _builder.append("::scm_astEventConnections[] = {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        {
          EList<EventConnection> _eventConnections = this.type.getFBNetwork().getEventConnections();
          boolean _hasElements_1 = false;
          for(final EventConnection conn : _eventConnections) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",\n", "  ");
            }
            _builder.append("{");
            String _generateConnectionPortID = this.generateConnectionPortID(conn.getSource(), conn.getSourceElement());
            _builder.append(_generateConnectionPortID, "  ");
            _builder.append(", ");
            String _generateConnectionPortID_1 = this.generateConnectionPortID(conn.getDestination(), conn.getDestinationElement());
            _builder.append(_generateConnectionPortID_1, "  ");
            _builder.append("}");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty_1 = this.type.getFBNetwork().getDataConnections().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("const SCFB_FBConnectionData ");
        CharSequence _fBClassName_2 = this.getFBClassName();
        _builder.append(_fBClassName_2);
        _builder.append("::scm_astDataConnections[] = {");
        _builder.newLineIfNotEmpty();
        _builder.append("  ");
        {
          EList<DataConnection> _dataConnections = this.type.getFBNetwork().getDataConnections();
          boolean _hasElements_2 = false;
          for(final DataConnection conn_1 : _dataConnections) {
            if (!_hasElements_2) {
              _hasElements_2 = true;
            } else {
              _builder.appendImmediate(",\n", "  ");
            }
            _builder.append("{");
            String _generateConnectionPortID_2 = this.generateConnectionPortID(conn_1.getSource(), conn_1.getSourceElement());
            _builder.append(_generateConnectionPortID_2, "  ");
            _builder.append(", ");
            String _generateConnectionPortID_3 = this.generateConnectionPortID(conn_1.getDestination(), conn_1.getDestinationElement());
            _builder.append(_generateConnectionPortID_3, "  ");
            _builder.append("}");
          }
        }
        _builder.newLineIfNotEmpty();
        _builder.append("};");
        _builder.newLine();
        _builder.newLine();
      }
    }
    _builder.append("const SCFB_FBNData ");
    CharSequence _fBClassName_3 = this.getFBClassName();
    _builder.append(_fBClassName_3);
    _builder.append("::scm_stFBNData = {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size = this.fbs.size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_2 = this.fbs.isEmpty();
      boolean _not_2 = (!_isEmpty_2);
      if (_not_2) {
        _builder.append("scm_astInternalFBs");
      } else {
        _builder.append("nullptr");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    int _size_1 = this.type.getFBNetwork().getEventConnections().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_3 = this.type.getFBNetwork().getEventConnections().isEmpty();
      boolean _not_3 = (!_isEmpty_3);
      if (_not_3) {
        _builder.append("scm_astEventConnections");
      } else {
        _builder.append("nullptr");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("0, nullptr,");
    _builder.newLine();
    _builder.append("  ");
    int _size_2 = this.type.getFBNetwork().getDataConnections().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    {
      boolean _isEmpty_4 = this.type.getFBNetwork().getDataConnections().isEmpty();
      boolean _not_4 = (!_isEmpty_4);
      if (_not_4) {
        _builder.append("scm_astDataConnections");
      } else {
        _builder.append("nullptr");
      }
    }
    _builder.append(",");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("0, nullptr,");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("0, nullptr");
    _builder.newLine();
    _builder.append("};");
    _builder.newLine();
    return _builder;
  }
  
  protected String generateConnectionPortID(final IInterfaceElement iface, final FBNetworkElement elem) {
    String _xifexpression = null;
    boolean _contains = this.type.getFBNetwork().getNetworkElements().contains(elem);
    if (_contains) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("GENERATE_CONNECTION_PORT_ID_2_ARG(");
      CharSequence _fORTEString = this.getFORTEString(elem.getName());
      _builder.append(_fORTEString);
      _builder.append(", ");
      CharSequence _fORTEString_1 = this.getFORTEString(iface.getName());
      _builder.append(_fORTEString_1);
      _builder.append("), ");
      CharSequence _fbId = this.fbId(elem);
      _builder.append(_fbId);
      _xifexpression = _builder.toString();
    } else {
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("GENERATE_CONNECTION_PORT_ID_1_ARG(");
      CharSequence _fORTEString_2 = this.getFORTEString(iface.getName());
      _builder_1.append(_fORTEString_2);
      _builder_1.append("), -1");
      _xifexpression = _builder_1.toString();
    }
    return _xifexpression;
  }
  
  protected CharSequence _fbId(final FBNetworkElement elem) {
    StringConcatenation _builder = new StringConcatenation();
    int _indexOf = this.fbs.indexOf(elem);
    _builder.append(_indexOf);
    return _builder;
  }
  
  protected CharSequence _fbId(final AdapterFB elem) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("CCompositeFB::scm_nAdapterMarker | ");
    {
      boolean _isPlug = elem.isPlug();
      if (_isPlug) {
        int _plugIndex = this.getPlugIndex(elem);
        _builder.append(_plugIndex);
      } else {
        int _indexOf = this.type.getInterfaceList().getSockets().indexOf(elem.getAdapterDecl());
        _builder.append(_indexOf);
      }
    }
    return _builder;
  }
  
  protected int getPlugIndex(final AdapterFB elem) {
    int _size = this.type.getInterfaceList().getSockets().size();
    int _indexOf = this.type.getInterfaceList().getPlugs().indexOf(elem.getAdapterDecl());
    return (_size + _indexOf);
  }
  
  protected CharSequence fbId(final FBNetworkElement elem) {
    if (elem instanceof AdapterFB) {
      return _fbId((AdapterFB)elem);
    } else if (elem != null) {
      return _fbId(elem);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(elem).toString());
    }
  }
  
  @Pure
  protected CompositeFBType getType() {
    return this.type;
  }
}
