/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.labeling;

import java.text.MessageFormat;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResourceDescriptionStrategy;
import org.eclipse.fordiac.ide.structuredtextcore.ui.labeling.STCoreLabelProvider;

import com.google.inject.Inject;

@SuppressWarnings("static-method")
public class STAlgorithmLabelProvider extends STCoreLabelProvider {

	@Inject
	public STAlgorithmLabelProvider(final AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	public String text(final STAlgorithm element) {
		return MessageFormat.format(Messages.STAlgorithmLabelProvider_AlgorithmText, element.getName());
	}

	public String text(final STMethod element) {
		return MessageFormat.format(Messages.STAlgorithmLabelProvider_MethodText,
				STCoreResourceDescriptionStrategy.getCallableDisplayString(element));
	}
}
