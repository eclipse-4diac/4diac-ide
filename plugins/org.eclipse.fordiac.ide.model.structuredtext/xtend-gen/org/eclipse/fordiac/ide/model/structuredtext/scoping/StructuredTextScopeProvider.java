/**
 * Copyright (c) 2015 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Martin Jobst
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.scoping;

import com.google.common.base.Function;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
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
  public SimpleScope scope_VarDeclaration_type(final VarDeclaration context, final EReference ref) {
    SimpleScope _xblockexpression = null;
    {
      DataTypeLibrary _instance = DataTypeLibrary.getInstance();
      final Collection<DataType> candidates = _instance.getDataTypes();
      Function<EObject, QualifiedName> _wrapper = QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER);
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, _wrapper);
      _xblockexpression = new SimpleScope(_scopedElementsFor, true);
    }
    return _xblockexpression;
  }
  
  public IScope scope_AdapterVariable_var(final AdapterVariable context, final EReference ref) {
    SimpleScope _xblockexpression = null;
    {
      AdapterDeclaration _adapter = context.getAdapter();
      DataType _type = null;
      if (_adapter!=null) {
        _type=_adapter.getType();
      }
      final AdapterType type = ((AdapterType) _type);
      if ((type == null)) {
        return IScope.NULLSCOPE;
      }
      final ArrayList<VarDeclaration> candidates = new ArrayList<VarDeclaration>();
      InterfaceList _interfaceList = type.getInterfaceList();
      EList<VarDeclaration> _inputVars = _interfaceList.getInputVars();
      candidates.addAll(_inputVars);
      InterfaceList _interfaceList_1 = type.getInterfaceList();
      EList<VarDeclaration> _outputVars = _interfaceList_1.getOutputVars();
      candidates.addAll(_outputVars);
      Function<EObject, QualifiedName> _wrapper = QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER);
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, _wrapper);
      _xblockexpression = new SimpleScope(_scopedElementsFor, true);
    }
    return _xblockexpression;
  }
}
