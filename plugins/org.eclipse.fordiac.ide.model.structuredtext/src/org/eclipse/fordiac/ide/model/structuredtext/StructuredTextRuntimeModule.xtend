/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext

import com.google.inject.Binder
import org.eclipse.xtext.resource.impl.SimpleResourceDescriptionsBasedContainerManager
import org.eclipse.xtext.scoping.IgnoreCaseLinking
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider
import org.eclipse.fordiac.ide.model.structuredtext.converter.StructuredTextValueConverterService
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource
import org.eclipse.fordiac.ide.model.structuredtext.scoping.StructuredTextScopeProvider

/** 
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
class StructuredTextRuntimeModule extends AbstractStructuredTextRuntimeModule {
	override bindXtextResource() {
		return StructuredTextResource
	}

	override bindISerializer() {
		return org.eclipse.xtext.serializer.impl.Serializer
	}

	override bindIContainer$Manager() {
		return SimpleResourceDescriptionsBasedContainerManager
	}

	override bindIValueConverterService() {
		return StructuredTextValueConverterService
	}

	override bindIScopeProvider() {
		return StructuredTextScopeProvider
	}
	
	override bindIGlobalScopeProvider() {
		return DefaultGlobalScopeProvider
	}
	
	def void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(org.eclipse.xtext.scoping.IScopeProvider).annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(org.eclipse.xtext.scoping.impl.SimpleLocalScopeProvider);
	}

	def void configureIgnoreCaseLinking(Binder binder) {
		binder.bindConstant().annotatedWith(IgnoreCaseLinking).to(true)
	}

}
