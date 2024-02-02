/**
 * Copyright (c) 2022 Primetals Technologies GmbH
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
 * 		 - registers hover provider
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui;

import org.eclipse.fordiac.ide.structuredtextcore.ui.hovering.STCoreHoverProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.resource.STCoreResourceUIServiceProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerCreator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreMarkerTypeProvider;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.STCoreResourceUIValidatorExtension;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.hover.IEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.ui.resource.IResourceUIServiceProvider;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;
import org.eclipse.xtext.ui.validation.IResourceUIValidatorExtension;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;

@SuppressWarnings("static-method")
public class DTPUiModule extends EmfUiModule {
	public DTPUiModule(final AbstractUIPlugin plugin) {
		super(plugin);
	}

	public Class<? extends IEObjectHoverProvider> bindIEObjectHoverProvider() {
		return STCoreHoverProvider.class;
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
}
