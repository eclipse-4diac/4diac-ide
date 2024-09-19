/*******************************************************************************
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
 *   Martin Erich Jobst
 *       - move implementation for STFunction from STCoreLabelProvider
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.labeling;

import java.text.MessageFormat;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.ui.labeling.STCoreLabelProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;

import com.google.inject.Inject;

@SuppressWarnings({ "static-method", "java:S2177", "java:S1172" })
public class STFunctionLabelProvider extends STCoreLabelProvider {

	@Inject
	public STFunctionLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public ImageDescriptor image(final STFunctionSource element) {
		return FordiacImage.ICON_FUNCTION.getImageDescriptor();
	}

	public String text(final STFunction element) {
		return MessageFormat.format(Messages.STFunctionLabelProvider_FunctionText,
				STCoreResourceDescriptionStrategy.getCallableDisplayString(element));
	}

	public ImageDescriptor image(final STFunction element) {
		return FordiacImage.ICON_FUNCTION.getImageDescriptor();
	}
}
