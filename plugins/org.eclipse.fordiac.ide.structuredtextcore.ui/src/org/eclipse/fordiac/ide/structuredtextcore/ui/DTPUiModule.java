/**
 * Copyright (c) 2022, 2024 Primetals Technologies GmbH
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
 *       - registers hover provider
 *   Martin Erich Jobst
 *       - add refactoring bindings
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverDocumentationProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreChangeConverter;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreChangeSerializer;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreLinkedPositionGroupCalculator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRelatedEmfResourceUpdater;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameElementProcessor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameNameValidator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreRenameStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreResourceLifecycleManager;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreSimpleNameProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.STCoreSyncUtil;
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceUIServiceProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerCreator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerTypeProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreResourceUIValidatorExtension;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ide.refactoring.IRenameNameValidator;
import org.eclipse.xtext.ide.refactoring.IRenameStrategy2;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.ide.serializer.impl.RelatedEmfResourceUpdater;
import org.eclipse.xtext.ide.serializer.impl.ResourceLifecycleManager;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hover.html.IEObjectHoverDocumentationProvider;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.refactoring.ILinkedPositionGroupCalculator;
import org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.rename.ISimpleNameProvider;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;
import org.eclipse.xtext.ui.shared.Access;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;

import com.google.inject.Provider;

@SuppressWarnings({ "restriction", "static-method" })
public class DTPUiModule extends EmfUiModule {
	public DTPUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Provider<? extends IAllContainersState> provideIAllContainersState() {
		return Access.getWorkspaceProjectsState();
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
	}

	public Class<? extends IEObjectHoverDocumentationProvider> bindIEObjectHoverDocumentationProvider() {
		return STCoreHoverDocumentationProvider.class;
	}

	public Class<? extends IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return STCoreResourceUIServiceProvider.class;
	}

	public Class<? extends MarkerCreator> bindMarkerCreator() {
		return STCoreMarkerCreator.class;
	}

	public Class<? extends MarkerTypeProvider> bindMarkerTypeProvider() {
		return STCoreMarkerTypeProvider.class;
	}

	public Class<? extends IResourceUIValidatorExtension> bindIResourceUIValidatorExtension() {
		return STCoreResourceUIValidatorExtension.class;
	}

	public Class<? extends AbstractRenameProcessor> bindAbstractRenameProcessor() {
		return STCoreRenameElementProcessor.class;
	}

	public Class<? extends ILinkedPositionGroupCalculator> bindILinkedPositionGroupCalculator() {
		return STCoreLinkedPositionGroupCalculator.class;
	}

	public Class<? extends ISimpleNameProvider> bindISimpleNameProvider() {
		return STCoreSimpleNameProvider.class;
	}

	public Class<? extends IRenameStrategy2> bindIRenameStrategy2() {
		return STCoreRenameStrategy.class;
	}

	public Class<? extends IRenameNameValidator> bindIRenameNameValidator() {
		return STCoreRenameNameValidator.class;
	}

	public Class<? extends IChangeSerializer> bindIChangeSerializer() {
		return STCoreChangeSerializer.class;
	}

	public Class<? extends RelatedEmfResourceUpdater> bindRelatedEmfResourceUpdater() {
		return STCoreRelatedEmfResourceUpdater.class;
	}

	public Class<? extends ChangeConverter.Factory> bindChangeConverter$Factory() {
		return STCoreChangeConverter.Factory.class;
	}

	public Class<? extends SyncUtil> bindSyncUtil() {
		return STCoreSyncUtil.class;
	}

	public Class<? extends ResourceLifecycleManager> bindResourceLifecycleManager() {
		return STCoreResourceLifecycleManager.class;
	}
}
