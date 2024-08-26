/**
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
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
 *       - adds implementations for STVarDeclaration and STFunction text
 *   Martin Erich Jobst
 *       - externalize strings
 *       - move implementation for STFunction to STFunctionLabelProvider
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.labeling;

import java.text.MessageFormat;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

@SuppressWarnings({ "static-method", "java:S1172" })
public class STCoreLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public STCoreLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public ImageDescriptor image(final STSource element) {
		return FordiacImage.ICON_ALGORITHM.getImageDescriptor();
	}

	public String text(final STVarDeclaration element) {
		return MessageFormat.format(Messages.STCoreLabelProvider_VarDeclarationText, element.getName(),
				element.getType() != null ? element.getType().getName() : null);
	}
}
