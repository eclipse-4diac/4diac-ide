/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.builder.STCoreToBeBuiltComputerContribution;

public class STAlgorithmToBeBuiltComputerContribution extends STCoreToBeBuiltComputerContribution {

	@Override
	protected boolean isValidFileExtension(final IFile file) {
		return STAlgorithmResource.isValidFileExtension(file.getFileExtension());
	}
}
