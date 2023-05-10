/**
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

/**
 * Provides labels for EObjects.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#label-provider
 */
@SuppressWarnings("static-method")
public class STCoreLabelProvider extends DefaultEObjectLabelProvider {

	private static final String TYPE_SEPARATOR = " : "; //$NON-NLS-1$

	@Inject
	public STCoreLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	String text(final STVarDeclaration stVarDeclaration) {
		return stVarDeclaration.getName() + TYPE_SEPARATOR + stVarDeclaration.getType().getName();
	}

	String text(final STFunction function) {
		var labelString = function.getName();
		if (function.getReturnType() != null) {
			labelString += TYPE_SEPARATOR + function.getReturnType().getName();
		}
		return labelString;
	}
}
