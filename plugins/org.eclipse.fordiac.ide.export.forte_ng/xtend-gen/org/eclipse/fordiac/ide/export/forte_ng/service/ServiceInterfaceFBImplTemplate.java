/**
 * Copyright (c) 2019 fortiss GmbH
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
 */
package org.eclipse.fordiac.ide.export.forte_ng.service;

import java.nio.file.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ServiceInterfaceFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private ServiceInterfaceFBType type;
  
  public ServiceInterfaceFBImplTemplate(final ServiceInterfaceFBType type, final String name, final Path prefix) {
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
    CharSequence _generateExecuteEvent = this.generateExecuteEvent();
    _builder.append(_generateExecuteEvent);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateExecuteEvent() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::executeEvent(int pa_nEIID) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    _builder.append("switch(pa_nEIID) {");
    _builder.newLine();
    {
      EList<Event> _eventInputs = this.type.getInterfaceList().getEventInputs();
      for(final Event event : _eventInputs) {
        _builder.append("    ");
        _builder.append("case scm_nEvent");
        String _name = event.getName();
        _builder.append(_name, "    ");
        _builder.append("ID:");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("#error add code for ");
        String _name_1 = event.getName();
        _builder.append(_name_1, "      ");
        _builder.append(" event!");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("/*");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("do not forget to send output event, calling e.g.");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("      ");
        _builder.append("sendOutputEvent(scm_nEventCNFID);");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("break;");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  @Pure
  @Override
  protected ServiceInterfaceFBType getType() {
    return this.type;
  }
}
