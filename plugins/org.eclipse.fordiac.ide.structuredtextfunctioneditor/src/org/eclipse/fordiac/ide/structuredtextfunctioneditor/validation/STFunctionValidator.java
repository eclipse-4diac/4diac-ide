/*******************************************************************************
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
 * 	 Ulzii Jargalsaikhan
 *       - custom validation for cross references to user defined variables
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;

@SuppressWarnings("nls")
public class STFunctionValidator extends AbstractSTFunctionValidator {

	public static final String WRONG_NAME_CASE = "wrongNameCase";

	private static final String WRONG_CASE_MESSAGE = "Variable names should be cased as declared";

	@Check
	public void checkFeatureExpression(final STFeatureExpression featureExpression) {

		final INamedElement feature = featureExpression.getFeature();
		final INode node = NodeModelUtils.getNode(featureExpression);

		if (node != null && feature != null) {

			final String originalName = feature.getName();
			final String nameInText = node.getText().trim().substring(0, originalName.length());

			if (originalName.equalsIgnoreCase(nameInText) && !originalName.equals(nameInText)) {
				warning(WRONG_CASE_MESSAGE, STCorePackage.Literals.ST_FEATURE_EXPRESSION__FEATURE, WRONG_NAME_CASE,
						nameInText, originalName);
			}
		}

	}

}
