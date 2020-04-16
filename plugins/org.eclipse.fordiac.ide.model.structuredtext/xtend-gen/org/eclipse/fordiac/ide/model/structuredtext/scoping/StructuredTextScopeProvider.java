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
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
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
  public IScope scope_DataType(final EObject context, final EReference ref) {
    if ((context instanceof LocalVariable)) {
      EObject _eContainer = ((LocalVariable)context).eContainer();
      Resource _eResource = ((StructuredTextAlgorithm) _eContainer).eResource();
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
      return new SimpleScope(_scopedElementsFor, true);
    }
    return IScope.NULLSCOPE;
  }
  
  public IScope scope_AdapterVariable_var(final AdapterVariable context, final EReference ref) {
    SimpleScope _xblockexpression = null;
    {
      AdapterDeclaration _adapter = context.getAdapter();
      AdapterType _type = null;
      if (_adapter!=null) {
        _type=_adapter.getType();
      }
      final AdapterType type = ((AdapterType) _type);
      if ((type == null)) {
        return IScope.NULLSCOPE;
      }
      final ArrayList<VarDeclaration> candidates = new ArrayList<VarDeclaration>();
      candidates.addAll(type.getInterfaceList().getInputVars());
      candidates.addAll(type.getInterfaceList().getOutputVars());
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER));
      _xblockexpression = new SimpleScope(_scopedElementsFor, true);
    }
    return _xblockexpression;
  }
}
