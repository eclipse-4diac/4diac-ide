/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - add linking diagnostic message provider
 *       - add function FB bindings
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor;

import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters;
import org.eclipse.fordiac.ide.structuredtextcore.documentation.STCoreCommentDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameConverter;
import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.resource.TypeLibraryAllContainersState;
import org.eclipse.fordiac.ide.structuredtextcore.resource.TypeLibraryResourceDescriptions;
import org.eclipse.fordiac.ide.structuredtextcore.serializer.STCoreSerializer;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreCustomConfigurableIssueCodesProvider;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreResourceValidator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.naming.STFunctionQualifiedNameProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.resource.STFunctionResource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.scoping.STFunctionImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.scoping.STFunctionLinkingDiagnosticMessageProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionMapper;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionPartitioner;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionReconciler;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parsetree.reconstr.ICommentAssociater;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
@SuppressWarnings("static-method")
public class STFunctionRuntimeModule extends AbstractSTFunctionRuntimeModule {

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return STCoreValueConverters.class;
	}

	@Override
	public Class<? extends XtextResource> bindXtextResource() {
		return STFunctionResource.class;
	}

	@Override
	public void configureIScopeProviderDelegate(final Binder binder) {
		binder.bind(IScopeProvider.class).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
				.to(STFunctionImportedNamespaceAwareLocalScopeProvider.class);
	}

	@Override
	public void configureFileExtensions(final Binder binder) {
		if (properties == null || properties.getProperty(Constants.FILE_EXTENSIONS) == null) {
			binder.bind(String.class).annotatedWith(Names.named(Constants.FILE_EXTENSIONS)).toInstance("stfunc,fct"); //$NON-NLS-1$
		}
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return STFunctionQualifiedNameProvider.class;
	}

	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return STCoreQualifiedNameConverter.class;
	}

	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return STCoreResourceDescriptionStrategy.class;
	}

	@Override
	public void configureIResourceDescriptions(final com.google.inject.Binder binder) {
		binder.bind(IResourceDescriptions.class).to(TypeLibraryResourceDescriptions.class);
	}

	@Override
	public void configureIResourceDescriptionsPersisted(final Binder binder) {
		binder.bind(IResourceDescriptions.class)
				.annotatedWith(Names.named(ResourceDescriptionsProvider.PERSISTED_DESCRIPTIONS))
				.to(TypeLibraryResourceDescriptions.class);
	}

	@Override
	public Class<? extends IAllContainersState.Provider> bindIAllContainersState$Provider() {
		return TypeLibraryAllContainersState.ProviderImpl.class;
	}

	public Class<? extends ICommentAssociater> bindICommentAssociater() {
		return STCoreCommentAssociater.class;
	}

	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return STFunctionLinkingDiagnosticMessageProvider.class;
	}

	public Class<? extends STCorePartitioner> bindSTCorePartitioner() {
		return STFunctionPartitioner.class;
	}

	public Class<? extends STCoreReconciler> bindSTCoreReconciler() {
		return STFunctionReconciler.class;
	}

	public Class<? extends STCoreMapper> bindSTCoreMapper() {
		return STFunctionMapper.class;
	}

	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return STCoreResourceValidator.class;
	}

	public Class<? extends IEObjectDocumentationProvider> bindIEObjectDocumentationProvider() {
		return STCoreCommentDocumentationProvider.class;
	}

	@Override
	public Class<? extends ISerializer> bindISerializer() {
		return STCoreSerializer.class;
	}

	@Override
	public Class<? extends ConfigurableIssueCodesProvider> bindConfigurableIssueCodesProvider() {
		return STCoreCustomConfigurableIssueCodesProvider.class;
	}
}
