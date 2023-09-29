/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm

import com.google.inject.Binder
import com.google.inject.name.Names
import org.eclipse.fordiac.ide.structuredtextalgorithm.naming.STAlgorithmQualifiedNameProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResourceDescriptionStrategy
import org.eclipse.fordiac.ide.structuredtextalgorithm.scoping.STAlgorithmImportedNamespaceAwareLocalScopeProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.scoping.STAlgorithmLinkingDiagnosticMessageProvider
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmMapper
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmReconciler
import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters
import org.eclipse.fordiac.ide.structuredtextcore.documentation.STCoreCommentDocumentationProvider
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameConverter
import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreResourceValidator
import org.eclipse.xtext.Constants
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.parsetree.reconstr.ICommentAssociater
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider
import org.eclipse.xtext.validation.IResourceValidator

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class STAlgorithmRuntimeModule extends AbstractSTAlgorithmRuntimeModule {
	override bindIValueConverterService() {
		return STCoreValueConverters;
	}

	override bindXtextResource() {
		return STAlgorithmResource
	}

	override configureIScopeProviderDelegate(Binder binder) {
		binder.bind(IScopeProvider).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE)).to(
			STAlgorithmImportedNamespaceAwareLocalScopeProvider)
	}

	override void configureFileExtensions(Binder binder) {
		if (properties === null || properties.getProperty(Constants.FILE_EXTENSIONS) === null) {
			binder.bind(String).annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("stalg,fbt,sys")
		}
	}

	def Class<? extends ICommentAssociater> bindICommentAssociater() {
		return STCoreCommentAssociater
	}

	def Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return STAlgorithmResourceDescriptionStrategy
	}

	def Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return STAlgorithmLinkingDiagnosticMessageProvider;
	}

	override Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return STAlgorithmQualifiedNameProvider
	}

	def Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return STCoreQualifiedNameConverter;
	}

	def Class<? extends STCorePartitioner> bindSTCorePartitioner() {
		return STAlgorithmPartitioner
	}

	def Class<? extends STCoreReconciler> bindSTCoreReconciler() {
		return STAlgorithmReconciler
	}

	def Class<? extends STCoreMapper> bindSTCoreMapper() {
		return STAlgorithmMapper
	}

	def Class<? extends IResourceValidator> bindIResourceValidator() {
		return STCoreResourceValidator
	}

	def Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return STCoreCommentDocumentationProvider;
	}
}
