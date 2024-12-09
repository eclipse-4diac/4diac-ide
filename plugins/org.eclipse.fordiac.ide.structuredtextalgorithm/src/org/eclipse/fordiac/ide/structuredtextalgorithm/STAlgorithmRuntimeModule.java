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
package org.eclipse.fordiac.ide.structuredtextalgorithm;

import org.eclipse.fordiac.ide.structuredtextalgorithm.naming.STAlgorithmQualifiedNameProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResourceDescriptionManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResourceFactory;
import org.eclipse.fordiac.ide.structuredtextalgorithm.scoping.STAlgorithmImportedNamespaceAwareLocalScopeProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.scoping.STAlgorithmLinkingDiagnosticMessageProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmMapper;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmReconciler;
import org.eclipse.fordiac.ide.structuredtextalgorithm.validation.STAlgorithmCustomConfigurableIssueCodesProvider;
import org.eclipse.fordiac.ide.structuredtextcore.converter.STCoreValueConverters;
import org.eclipse.fordiac.ide.structuredtextcore.documentation.STCoreCommentDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.naming.STCoreQualifiedNameConverter;
import org.eclipse.fordiac.ide.structuredtextcore.parsetree.reconstr.STCoreCommentAssociater;
import org.eclipse.fordiac.ide.structuredtextcore.serializer.STCoreSerializer;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreMapper;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreResourceValidator;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parsetree.reconstr.ICommentAssociater;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;
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
@SuppressWarnings({ "static-method", "java:S100" })
public class STAlgorithmRuntimeModule extends AbstractSTAlgorithmRuntimeModule {
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return STCoreValueConverters.class;
	}

	@Override
	public Class<? extends XtextResource> bindXtextResource() {
		return STAlgorithmResource.class;
	}

	@Override
	public Class<? extends IResourceFactory> bindIResourceFactory() {
		return STAlgorithmResourceFactory.class;
	}

	@Override
	public void configureIScopeProviderDelegate(final Binder binder) {
		binder.<IScopeProvider>bind(IScopeProvider.class)
				.annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
				.to(STAlgorithmImportedNamespaceAwareLocalScopeProvider.class);
	}

	@Override
	public void configureFileExtensions(final Binder binder) {
		if (((this.properties == null) || (this.properties.getProperty(Constants.FILE_EXTENSIONS) == null))) {
			binder.<String>bind(String.class).annotatedWith(Names.named(Constants.FILE_EXTENSIONS))
					.toInstance("stalg,fbt,sys,sub,atp"); //$NON-NLS-1$
		}
	}

	public Class<? extends ICommentAssociater> bindICommentAssociater() {
		return STCoreCommentAssociater.class;
	}

	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return STAlgorithmResourceDescriptionStrategy.class;
	}

	public Class<? extends IResourceDescription.Manager> bindIResourceDescription$Manager() {
		return STAlgorithmResourceDescriptionManager.class;
	}

	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return STAlgorithmLinkingDiagnosticMessageProvider.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return STAlgorithmQualifiedNameProvider.class;
	}

	public Class<? extends IQualifiedNameConverter> bindIQualifiedNameConverter() {
		return STCoreQualifiedNameConverter.class;
	}

	public Class<? extends STCorePartitioner> bindSTCorePartitioner() {
		return STAlgorithmPartitioner.class;
	}

	public Class<? extends STCoreReconciler> bindSTCoreReconciler() {
		return STAlgorithmReconciler.class;
	}

	public Class<? extends STCoreMapper> bindSTCoreMapper() {
		return STAlgorithmMapper.class;
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
		return STAlgorithmCustomConfigurableIssueCodesProvider.class;
	}
}
