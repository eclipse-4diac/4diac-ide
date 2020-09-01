/**
 * Copyright (c) 2015 fortiss GmbH
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
 *   Alois Zoitl - Changed to a per project Type and Data TypeLibrary
 */
package org.eclipse.fordiac.ide.model.structuredtext.scoping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterRoot;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;

/**
 * This class contains custom scoping description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation.html#scoping
 * on how and when to use it
 */
@SuppressWarnings("all")
public class StructuredTextScopeProvider extends AbstractDeclarativeScopeProvider {
  public SimpleScope scope_DataType(final EObject context, final EReference ref) {
    EObject _rootContainer = EcoreUtil.getRootContainer(context);
    final StructuredTextAlgorithm rootElement = ((StructuredTextAlgorithm) _rootContainer);
    Resource _eResource = rootElement.eResource();
    final XtextResource res = ((XtextResource) _eResource);
    Resource _get = res.getResourceSet().getResources().get(0);
    EList<EObject> _contents = null;
    if (_get!=null) {
      _contents=_get.getContents();
    }
    EObject _get_1 = null;
    if (_contents!=null) {
      _get_1=_contents.get(0);
    }
    final List<DataType> candidates = ((FBType) _get_1).getTypeLibrary().getDataTypeLibrary().getDataTypes();
    Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER));
    return new SimpleScope(_scopedElementsFor, false);
  }
  
  public IScope scope_AdapterVariable_var(final AdapterVariable context, final EReference ref) {
    SimpleScope _xblockexpression = null;
    {
      final DataType type = this.getType(context);
      if ((type == null)) {
        return IScope.NULLSCOPE;
      }
      final List<VarDeclaration> candidates = this.getScopeCandidates(type);
      boolean _isEmpty = candidates.isEmpty();
      if (_isEmpty) {
        return IScope.NULLSCOPE;
      }
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER));
      _xblockexpression = new SimpleScope(_scopedElementsFor, false);
    }
    return _xblockexpression;
  }
  
  public DataType getType(final AdapterVariable variable) {
    DataType _xblockexpression = null;
    {
      final AdapterVariable head = variable.getCurr();
      DataType _switchResult = null;
      boolean _matched = false;
      if (head instanceof AdapterRoot) {
        _matched=true;
        _switchResult = ((AdapterRoot)head).getAdapter().getType();
      }
      if (!_matched) {
        if (head instanceof AdapterVariable) {
          _matched=true;
          _switchResult = head.getVar().getType();
        }
      }
      if (!_matched) {
        _switchResult = null;
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  protected List<VarDeclaration> _getScopeCandidates(final AdapterType context) {
    final ArrayList<VarDeclaration> candidates = new ArrayList<VarDeclaration>();
    candidates.addAll(context.getInterfaceList().getInputVars());
    candidates.addAll(context.getInterfaceList().getOutputVars());
    return candidates;
  }
  
  protected List<VarDeclaration> _getScopeCandidates(final StructuredType context) {
    EList<VarDeclaration> _memberVariables = context.getMemberVariables();
    return new ArrayList<VarDeclaration>(_memberVariables);
  }
  
  protected List<VarDeclaration> _getScopeCandidates(final DataType context) {
    return Collections.<VarDeclaration>emptyList();
  }
  
  public IScope scope_VarDeclaration(final Variable context, final EReference ref) {
    final DataType type = ((DataType) context);
    if ((type == null)) {
      return IScope.NULLSCOPE;
    }
    return null;
  }
  
  public List<VarDeclaration> getScopeCandidates(final DataType context) {
    if (context instanceof StructuredType) {
      return _getScopeCandidates((StructuredType)context);
    } else if (context instanceof AdapterType) {
      return _getScopeCandidates((AdapterType)context);
    } else if (context != null) {
      return _getScopeCandidates(context);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(context).toString());
    }
  }
}
