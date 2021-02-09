/**
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.typemanagement.handlers;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pair;

@SuppressWarnings("all")
public class ExportXMIHandler extends AbstractHandler {
  private static final String SYNTHETIC_URI_NAME = "__synthetic";
  
  private static final String URI_SEPERATOR = ".";
  
  private static final String FB_URI_EXTENSION = "xtextfbt";
  
  private static final String ST_URI_EXTENSION = "st";
  
  private static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(URI.createURI(((ExportXMIHandler.SYNTHETIC_URI_NAME + ExportXMIHandler.URI_SEPERATOR) + ExportXMIHandler.ST_URI_EXTENSION)));
  
  protected URI computeUnusedUri(final ResourceSet resourceSet, final String fileExtension) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, Integer.MAX_VALUE, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final URI syntheticUri = URI.createURI((((ExportXMIHandler.SYNTHETIC_URI_NAME + i) + ExportXMIHandler.URI_SEPERATOR) + fileExtension));
        Resource _resource = resourceSet.getResource(syntheticUri, false);
        boolean _tripleEquals = (_resource == null);
        if (_tripleEquals) {
          return syntheticUri;
        }
      }
    }
    throw new IllegalStateException();
  }
  
  public void createFBResource(final XtextResourceSet resourceSet, final SimpleFBType fbType) {
    final Resource fbResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, ExportXMIHandler.FB_URI_EXTENSION));
    fbResource.getContents().add(fbType);
    final Consumer<AdapterDeclaration> _function = (AdapterDeclaration adp) -> {
      this.createAdapterResource(resourceSet, adp);
    };
    fbType.getInterfaceList().getSockets().forEach(_function);
    final Consumer<AdapterDeclaration> _function_1 = (AdapterDeclaration adp) -> {
      this.createAdapterResource(resourceSet, adp);
    };
    fbType.getInterfaceList().getPlugs().forEach(_function_1);
    final Consumer<VarDeclaration> _function_2 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInterfaceList().getInputVars().forEach(_function_2);
    final Consumer<VarDeclaration> _function_3 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInterfaceList().getOutputVars().forEach(_function_3);
    final Consumer<VarDeclaration> _function_4 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInternalVars().forEach(_function_4);
  }
  
  public void createAdapterResource(final XtextResourceSet resourceSet, final AdapterDeclaration adapter) {
    final Resource adapterResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, ExportXMIHandler.FB_URI_EXTENSION));
    adapterResource.getContents().add(adapter.getType().getAdapterFBType());
  }
  
  public void createStructResource(final XtextResourceSet resourceSet, final VarDeclaration variable) {
    DataType _type = variable.getType();
    if ((_type instanceof StructuredType)) {
      final Resource structResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, ExportXMIHandler.FB_URI_EXTENSION));
      DataType _type_1 = variable.getType();
      final StructuredType type = ((StructuredType) _type_1);
      structResource.getContents().add(type);
      final Consumer<VarDeclaration> _function = (VarDeclaration v) -> {
        this.createStructResource(resourceSet, v);
      };
      type.getMemberVariables().forEach(_function);
    }
  }
  
  @Override
  public Object execute(final ExecutionEvent event) throws ExecutionException {
    try {
      ISelection _currentSelection = HandlerUtil.getCurrentSelection(event);
      final IStructuredSelection selection = ((IStructuredSelection) _currentSelection);
      Object _firstElement = null;
      if (selection!=null) {
        _firstElement=selection.getFirstElement();
      }
      final IFile fbFile = ((IFile) _firstElement);
      final PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(fbFile);
      LibraryElement _type = null;
      if (entry!=null) {
        _type=entry.getType();
      }
      final SimpleFBType simpleType = ((SimpleFBType) _type);
      Algorithm _algorithm = null;
      if (simpleType!=null) {
        _algorithm=simpleType.getAlgorithm();
      }
      String _text = null;
      if (((STAlgorithm) _algorithm)!=null) {
        _text=((STAlgorithm) _algorithm).getText();
      }
      final String algorithmText = _text;
      ResourceSet _get = ExportXMIHandler.SERVICE_PROVIDER.<ResourceSet>get(ResourceSet.class);
      final XtextResourceSet resourceSet = ((XtextResourceSet) _get);
      this.createFBResource(resourceSet, simpleType);
      Resource _createResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, ExportXMIHandler.ST_URI_EXTENSION));
      final XtextResource resource = ((XtextResource) _createResource);
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(algorithmText);
      Pair<String, Boolean> _mappedTo = Pair.<String, Boolean>of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      resource.load(_lazyStringInputStream, Collections.<String, Boolean>unmodifiableMap(CollectionLiterals.<String, Boolean>newHashMap(_mappedTo)));
      EObject _rootASTElement = resource.getParseResult().getRootASTElement();
      final StructuredTextAlgorithm stalg = ((StructuredTextAlgorithm) _rootASTElement);
      final Consumer<VarDeclaration> _function = (VarDeclaration v) -> {
        this.createStructResource(resourceSet, v);
      };
      stalg.getLocalVariables().forEach(_function);
      final Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
      final Map<String, Object> m = registry.getExtensionToFactoryMap();
      XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
      m.put("xmi", _xMIResourceFactoryImpl);
      final ResourceSetImpl xmiResourceSet = new ResourceSetImpl();
      IPath _makeAbsolute = fbFile.getLocation().makeAbsolute();
      String _plus = (_makeAbsolute + ".xmi");
      final URI uri = URI.createFileURI(new File(_plus).getAbsolutePath());
      final Resource xmiRessource = xmiResourceSet.createResource(uri);
      xmiRessource.getContents().add(simpleType);
      xmiRessource.getContents().add(resource.getContents().get(0));
      EcoreUtil.resolveAll(xmiRessource);
      try {
        xmiRessource.save(Collections.EMPTY_MAP);
      } catch (final Throwable _t) {
        if (_t instanceof IOException) {
          final IOException e = (IOException)_t;
          e.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return null;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public void setEnabled(final Object evaluationContext) {
    Object _variable = HandlerUtil.getVariable(evaluationContext, 
      ISources.ACTIVE_CURRENT_SELECTION_NAME);
    ISelection selection = ((ISelection) _variable);
    if (((!selection.isEmpty()) && (selection instanceof IStructuredSelection))) {
      IStructuredSelection structuredSelection = ((IStructuredSelection) selection);
      if (((structuredSelection.size() == 1) && (structuredSelection.getFirstElement() instanceof IFile))) {
        Object _firstElement = structuredSelection.getFirstElement();
        IFile fbFile = ((IFile) _firstElement);
        PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(fbFile);
        if ((null != entry)) {
          LibraryElement _type = entry.getType();
          this.setBaseEnabled((_type instanceof SimpleFBType));
          return;
        }
      }
    }
    this.setBaseEnabled(false);
  }
}
