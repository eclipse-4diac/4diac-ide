/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstants;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;

public class GlobalConstantsMatcher implements IModelMatcher {
	private final GlobalConstantsEntry gcEntry;

	public GlobalConstantsMatcher(final GlobalConstantsEntry gcEntry) {
		this.gcEntry = gcEntry;
	}

	@Override
	public boolean matches(final EObject object) {
		return object instanceof final STFeatureExpression featureExpression
				&& featureExpression.getFeature() instanceof final STVarDeclaration candidate
				&& candidate.eContainer() instanceof final STVarGlobalDeclarationBlock block
				&& block.eContainer() instanceof final STGlobalConstants constants
				&& constants.eContainer() instanceof final STGlobalConstsSource source
				&& Objects.equals(constants.getName(), gcEntry.getTypeName())
				&& Objects.equals(Objects.requireNonNullElse(source.getName(), ""), gcEntry.getPackageName()); //$NON-NLS-1$
	}

}
