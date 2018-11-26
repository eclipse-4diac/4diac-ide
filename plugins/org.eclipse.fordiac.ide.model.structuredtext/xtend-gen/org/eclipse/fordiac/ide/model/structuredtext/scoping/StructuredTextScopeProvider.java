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

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
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
      final Collection<DataType> candidates = DataTypeLibrary.getInstance().getDataTypes();
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER));
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
      candidates.addAll(type.getInterfaceList().getInputVars());
      candidates.addAll(type.getInterfaceList().getOutputVars());
      Iterable<IEObjectDescription> _scopedElementsFor = Scopes.<EObject>scopedElementsFor(candidates, QualifiedName.<EObject>wrapper(SimpleAttributeResolver.NAME_RESOLVER));
      _xblockexpression = new SimpleScope(_scopedElementsFor, true);
    }
    return _xblockexpression;
  }
}
