/**
 * Copyright (c) 2015, 2017 fortiss GmbH
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
package org.eclipse.fordiac.ide.model.structuredtext;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import org.eclipse.fordiac.ide.model.structuredtext.AbstractStructuredTextRuntimeModule;
import org.eclipse.fordiac.ide.model.structuredtext.converter.StructuredTextValueConverterService;
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource;
import org.eclipse.fordiac.ide.model.structuredtext.scoping.StructuredTextScopeProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.SimpleResourceDescriptionsBasedContainerManager;
import org.eclipse.xtext.scoping.IGlobalScopeProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.IgnoreCaseLinking;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleLocalScopeProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.serializer.impl.Serializer;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
@SuppressWarnings("all")
public class StructuredTextRuntimeModule extends AbstractStructuredTextRuntimeModule {
  @Override
  public Class<? extends XtextResource> bindXtextResource() {
    return StructuredTextResource.class;
  }
  
  @Override
  public Class<? extends ISerializer> bindISerializer() {
    return Serializer.class;
  }
  
  @Override
  public Class<? extends IContainer.Manager> bindIContainer$Manager() {
    return SimpleResourceDescriptionsBasedContainerManager.class;
  }
  
  @Override
  public Class<? extends IValueConverterService> bindIValueConverterService() {
    return StructuredTextValueConverterService.class;
  }
  
  @Override
  public Class<? extends IScopeProvider> bindIScopeProvider() {
    return StructuredTextScopeProvider.class;
  }
  
  @Override
  public Class<? extends IGlobalScopeProvider> bindIGlobalScopeProvider() {
    return DefaultGlobalScopeProvider.class;
  }
  
  public void configureIScopeProviderDelegate(final Binder binder) {
    binder.<IScopeProvider>bind(IScopeProvider.class).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(SimpleLocalScopeProvider.class);
  }
  
  public void configureIgnoreCaseLinking(final Binder binder) {
    binder.bindConstant().annotatedWith(IgnoreCaseLinking.class).to(true);
  }
}
