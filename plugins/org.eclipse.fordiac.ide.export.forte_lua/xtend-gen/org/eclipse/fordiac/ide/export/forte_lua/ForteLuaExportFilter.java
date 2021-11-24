/**
 * Copyright (c) 2015, 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst, Florian Noack, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.export.forte_lua;

import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.forte_lua.filter.AdapterFilter;
import org.eclipse.fordiac.ide.export.forte_lua.filter.BasicFBFilter;
import org.eclipse.fordiac.ide.export.forte_lua.filter.CompositeFBFilter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ForteLuaExportFilter implements IExportFilter {
  @Extension
  private BasicFBFilter basicFBFilter = new BasicFBFilter();
  
  @Extension
  private AdapterFilter adapterFilter = new AdapterFilter();
  
  @Extension
  private CompositeFBFilter compositeFBFilter = new CompositeFBFilter();
  
  @Override
  public void export(final IFile typeFile, final String destination, final boolean forceOverwrite) throws ExportException {
    throw new UnsupportedOperationException("Require a library element to work on");
  }
  
  @Override
  public void export(final IFile typeFile, final String destination, final boolean forceOverwrite, final LibraryElement type) throws ExportException {
    boolean _matched = false;
    if (type instanceof BasicFBType) {
      _matched=true;
      FordiacLogHelper.logInfo(this.basicFBFilter.lua(((BasicFBType)type)));
    }
    if (!_matched) {
      if (type instanceof CompositeFBType) {
        _matched=true;
        FordiacLogHelper.logInfo(this.compositeFBFilter.lua(((CompositeFBType)type)));
      }
    }
    if (!_matched) {
      if (type instanceof AdapterType) {
        _matched=true;
        FordiacLogHelper.logInfo(this.adapterFilter.lua(((AdapterType)type)));
      }
    }
    if (!_matched) {
      String _name = type.eClass().getName();
      String _plus = ("Unknown library element type " + _name);
      throw new UnsupportedOperationException(_plus);
    }
  }
  
  public String createLUA(final LibraryElement type) {
    boolean _matched = false;
    if (type instanceof BasicFBType) {
      _matched=true;
      return String.valueOf(this.basicFBFilter.lua(((BasicFBType)type)));
    }
    if (!_matched) {
      if (type instanceof CompositeFBType) {
        _matched=true;
        return String.valueOf(this.compositeFBFilter.lua(((CompositeFBType)type)));
      }
    }
    if (!_matched) {
      if (type instanceof AdapterType) {
        _matched=true;
        return String.valueOf(this.adapterFilter.lua(((AdapterType)type)));
      }
    }
    String _name = type.eClass().getName();
    String _plus = ("Unknown library element type " + _name);
    throw new UnsupportedOperationException(_plus);
  }
  
  @Override
  public List<String> getWarnings() {
    return Collections.<String>emptyList();
  }
  
  @Override
  public List<String> getErrors() {
    return this.basicFBFilter.getErrors();
  }
  
  @Override
  public List<String> getInfos() {
    return Collections.<String>emptyList();
  }
}
