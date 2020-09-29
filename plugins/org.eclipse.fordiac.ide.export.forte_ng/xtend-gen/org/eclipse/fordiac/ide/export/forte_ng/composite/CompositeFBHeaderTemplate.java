/**
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
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
 *     - Fix connections and parameter generation
 */
package org.eclipse.fordiac.ide.export.forte_ng.composite;

import com.google.common.collect.Iterables;
import java.nio.file.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class CompositeFBHeaderTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private CompositeFBType type;
  
  public CompositeFBHeaderTemplate(final CompositeFBType type, final String name, final Path prefix) {
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
    CharSequence _generateIncludeGuardStart = this.generateIncludeGuardStart();
    _builder.append(_generateIncludeGuardStart);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateHeaderIncludes = this.generateHeaderIncludes();
    _builder.append(_generateHeaderIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("class ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append(": public CCompositeFB {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateFBDeclaration = this.generateFBDeclaration();
    _builder.append(_generateFBDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("private:");
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateFBInterfaceDeclaration = this.generateFBInterfaceDeclaration();
    _builder.append(_generateFBInterfaceDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateFBInterfaceSpecDeclaration = this.generateFBInterfaceSpecDeclaration();
    _builder.append(_generateFBInterfaceSpecDeclaration, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateFBNetwork = this.generateFBNetwork();
    _builder.append(_generateFBNetwork, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    CharSequence _generateAccessors = this.generateAccessors(this.type.getInterfaceList().getInputVars(), "getDI");
    _builder.append(_generateAccessors, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generateAccessors_1 = this.generateAccessors(this.type.getInterfaceList().getOutputVars(), "getDO");
    _builder.append(_generateAccessors_1, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    EList<AdapterDeclaration> _sockets = this.type.getInterfaceList().getSockets();
    EList<AdapterDeclaration> _plugs = this.type.getInterfaceList().getPlugs();
    CharSequence _generateAccessors_2 = this.generateAccessors(IterableExtensions.<AdapterDeclaration>toList(Iterables.<AdapterDeclaration>concat(_sockets, _plugs)));
    _builder.append(_generateAccessors_2, "  ");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("FORTE_FB_DATA_ARRAY(");
    int _size = this.type.getInterfaceList().getEventOutputs().size();
    _builder.append(_size, "  ");
    _builder.append(", ");
    int _size_1 = this.type.getInterfaceList().getInputVars().size();
    _builder.append(_size_1, "  ");
    _builder.append(", ");
    int _size_2 = this.type.getInterfaceList().getOutputVars().size();
    _builder.append(_size_2, "  ");
    _builder.append(", ");
    int _size_3 = this.type.getInterfaceList().getSockets().size();
    int _size_4 = this.type.getInterfaceList().getPlugs().size();
    int _plus = (_size_3 + _size_4);
    _builder.append(_plus, "  ");
    _builder.append(");");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public:");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("COMPOSITE_FUNCTION_BLOCK_CTOR(");
    CharSequence _fBClassName_1 = this.getFBClassName();
    _builder.append(_fBClassName_1, "  ");
    _builder.append(") {};");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual ~");
    CharSequence _fBClassName_2 = this.getFBClassName();
    _builder.append(_fBClassName_2, "  ");
    _builder.append("() = default;");
    _builder.newLineIfNotEmpty();
    _builder.append("};");
    _builder.newLine();
    _builder.newLine();
    CharSequence _generateIncludeGuardEnd = this.generateIncludeGuardEnd();
    _builder.append(_generateIncludeGuardEnd);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  @Override
  protected CharSequence generateHeaderIncludes() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#include \"cfb.h\"");
    _builder.newLine();
    _builder.append("#include \"typelib.h\"");
    _builder.newLine();
    CharSequence _generateHeaderIncludes = super.generateHeaderIncludes();
    _builder.append(_generateHeaderIncludes);
    _builder.newLineIfNotEmpty();
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
        _builder.append("static const SCFB_FBInstanceData scm_astInternalFBs[];");
        _builder.newLine();
      }
    }
    _builder.append("static const SCFB_FBParameter scm_astParamters[];");
    _builder.newLine();
    {
      boolean _isEmpty = this.type.getFBNetwork().getEventConnections().isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("static const SCFB_FBConnectionData scm_astEventConnections[];");
        _builder.newLine();
        _builder.append("static const SCFB_FBFannedOutConnectionData scm_astFannedOutEventConnections[];");
        _builder.newLine();
      }
    }
    {
      boolean _isEmpty_1 = this.type.getFBNetwork().getDataConnections().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        _builder.append("static const SCFB_FBConnectionData scm_astDataConnections[];");
        _builder.newLine();
        _builder.append("static const SCFB_FBFannedOutConnectionData scm_astFannedOutDataConnections[];");
        _builder.newLine();
      }
    }
    _builder.append("static const SCFB_FBNData scm_stFBNData;");
    _builder.newLine();
    return _builder;
  }
  
  @Pure
  @Override
  protected CompositeFBType getType() {
    return this.type;
  }
}
