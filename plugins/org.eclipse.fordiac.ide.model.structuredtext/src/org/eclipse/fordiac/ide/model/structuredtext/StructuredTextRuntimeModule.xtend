/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext

import com.google.inject.Binder
import com.google.inject.name.Names
import org.eclipse.fordiac.ide.model.structuredtext.converter.StructuredTextValueConverterService
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource
import org.eclipse.fordiac.ide.model.structuredtext.scoping.StructuredTextScopeProvider
import org.eclipse.xtext.resource.impl.SimpleResourceDescriptionsBasedContainerManager
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.IgnoreCaseLinking
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider
import org.eclipse.xtext.scoping.impl.SimpleLocalScopeProvider
import org.eclipse.xtext.validation.CompositeEValidator

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
		binder.bind(IScopeProvider).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(SimpleLocalScopeProvider);
	}

	def void configureIgnoreCaseLinking(Binder binder) {
		binder.bindConstant().annotatedWith(IgnoreCaseLinking).to(true)
	}

	@SuppressWarnings("static-method")
	def void configureCompositeEValidator(Binder binder) {
		// ignore dangling reference errors (until Palette vs. Resource issues have been addressed)
		binder.bindConstant().annotatedWith(Names.named(CompositeEValidator.USE_EOBJECT_VALIDATOR)).to(false);
	}
}
