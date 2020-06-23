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
 *   Alois Zoitl  - added support for structured types
 */
package org.eclipse.fordiac.ide.export.forte_ng;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.fordiac.ide.export.IExportTemplate;
import org.eclipse.fordiac.ide.export.TemplateExportFilter;
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBImplTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBImplTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBImplTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBImplTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBImplTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructBaseTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeHeaderTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeImplTemplate;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class ForteNgExportFilter extends TemplateExportFilter {
  @Override
  protected Set<? extends IExportTemplate> getTemplates(final LibraryElement type) {
    Set<IExportTemplate> _switchResult = null;
    boolean _matched = false;
    if (type instanceof BasicFBType) {
      _matched=true;
      StringConcatenation _builder = new StringConcatenation();
      String _name = ((BasicFBType)type).getName();
      _builder.append(_name);
      _builder.append(".h");
      Path _get = Paths.get("");
      BasicFBHeaderTemplate _basicFBHeaderTemplate = new BasicFBHeaderTemplate(((BasicFBType)type), _builder.toString(), _get);
      StringConcatenation _builder_1 = new StringConcatenation();
      String _name_1 = ((BasicFBType)type).getName();
      _builder_1.append(_name_1);
      _builder_1.append(".cpp");
      Path _get_1 = Paths.get("");
      BasicFBImplTemplate _basicFBImplTemplate = new BasicFBImplTemplate(((BasicFBType)type), _builder_1.toString(), _get_1);
      _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_basicFBHeaderTemplate, _basicFBImplTemplate));
    }
    if (!_matched) {
      if (type instanceof SimpleFBType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((SimpleFBType)type).getName();
        _builder.append(_name);
        _builder.append(".h");
        Path _get = Paths.get("");
        SimpleFBHeaderTemplate _simpleFBHeaderTemplate = new SimpleFBHeaderTemplate(((SimpleFBType)type), _builder.toString(), _get);
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = ((SimpleFBType)type).getName();
        _builder_1.append(_name_1);
        _builder_1.append(".cpp");
        Path _get_1 = Paths.get("");
        SimpleFBImplTemplate _simpleFBImplTemplate = new SimpleFBImplTemplate(((SimpleFBType)type), _builder_1.toString(), _get_1);
        _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_simpleFBHeaderTemplate, _simpleFBImplTemplate));
      }
    }
    if (!_matched) {
      if (type instanceof CompositeFBType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((CompositeFBType)type).getName();
        _builder.append(_name);
        _builder.append(".h");
        Path _get = Paths.get("");
        CompositeFBHeaderTemplate _compositeFBHeaderTemplate = new CompositeFBHeaderTemplate(((CompositeFBType)type), _builder.toString(), _get);
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = ((CompositeFBType)type).getName();
        _builder_1.append(_name_1);
        _builder_1.append(".cpp");
        Path _get_1 = Paths.get("");
        CompositeFBImplTemplate _compositeFBImplTemplate = new CompositeFBImplTemplate(((CompositeFBType)type), _builder_1.toString(), _get_1);
        _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_compositeFBHeaderTemplate, _compositeFBImplTemplate));
      }
    }
    if (!_matched) {
      if (type instanceof AdapterType) {
        _matched=true;
        AdapterFBType _adapterFBType = ((AdapterType)type).getAdapterFBType();
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((AdapterType)type).getName();
        _builder.append(_name);
        _builder.append(".h");
        Path _get = Paths.get("");
        AdapterFBHeaderTemplate _adapterFBHeaderTemplate = new AdapterFBHeaderTemplate(_adapterFBType, _builder.toString(), _get);
        AdapterFBType _adapterFBType_1 = ((AdapterType)type).getAdapterFBType();
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = ((AdapterType)type).getName();
        _builder_1.append(_name_1);
        _builder_1.append(".cpp");
        Path _get_1 = Paths.get("");
        AdapterFBImplTemplate _adapterFBImplTemplate = new AdapterFBImplTemplate(_adapterFBType_1, _builder_1.toString(), _get_1);
        _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_adapterFBHeaderTemplate, _adapterFBImplTemplate));
      }
    }
    if (!_matched) {
      if (type instanceof ServiceInterfaceFBType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        String _name = ((ServiceInterfaceFBType)type).getName();
        _builder.append(_name);
        _builder.append(".h");
        Path _get = Paths.get("");
        ServiceInterfaceFBHeaderTemplate _serviceInterfaceFBHeaderTemplate = new ServiceInterfaceFBHeaderTemplate(((ServiceInterfaceFBType)type), _builder.toString(), _get);
        StringConcatenation _builder_1 = new StringConcatenation();
        String _name_1 = ((ServiceInterfaceFBType)type).getName();
        _builder_1.append(_name_1);
        _builder_1.append(".cpp");
        Path _get_1 = Paths.get("");
        ServiceInterfaceFBImplTemplate _serviceInterfaceFBImplTemplate = new ServiceInterfaceFBImplTemplate(((ServiceInterfaceFBType)type), _builder_1.toString(), _get_1);
        _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_serviceInterfaceFBHeaderTemplate, _serviceInterfaceFBImplTemplate));
      }
    }
    if (!_matched) {
      if (type instanceof StructuredType) {
        _matched=true;
        StringConcatenation _builder = new StringConcatenation();
        CharSequence _structuredTypeFileName = StructBaseTemplate.structuredTypeFileName(((StructuredType)type));
        _builder.append(_structuredTypeFileName);
        _builder.append(".h");
        Path _get = Paths.get("");
        StructuredTypeHeaderTemplate _structuredTypeHeaderTemplate = new StructuredTypeHeaderTemplate(((StructuredType)type), _builder.toString(), _get);
        StringConcatenation _builder_1 = new StringConcatenation();
        CharSequence _structuredTypeFileName_1 = StructBaseTemplate.structuredTypeFileName(((StructuredType)type));
        _builder_1.append(_structuredTypeFileName_1);
        _builder_1.append(".cpp");
        Path _get_1 = Paths.get("");
        StructuredTypeImplTemplate _structuredTypeImplTemplate = new StructuredTypeImplTemplate(((StructuredType)type), _builder_1.toString(), _get_1);
        _switchResult = Collections.<IExportTemplate>unmodifiableSet(CollectionLiterals.<IExportTemplate>newHashSet(_structuredTypeHeaderTemplate, _structuredTypeImplTemplate));
      }
    }
    if (!_matched) {
      Set<IExportTemplate> _xblockexpression = null;
      {
        List<String> _errors = this.getErrors();
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Unknown library element type ");
        String _name = type.getClass().getName();
        _builder.append(_name);
        _errors.add(_builder.toString());
        _xblockexpression = CollectionLiterals.<IExportTemplate>emptySet();
      }
      _switchResult = _xblockexpression;
    }
    return _switchResult;
  }
}
