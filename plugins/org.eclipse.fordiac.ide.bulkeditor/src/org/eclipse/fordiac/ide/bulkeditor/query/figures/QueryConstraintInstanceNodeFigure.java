/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import java.util.Objects;

import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration;
import org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintInstance;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.swt.graphics.Color;

/** Node of a constraint instance showing the name of its declaration */
public class QueryConstraintInstanceNodeFigure extends QueryValueNodeFigure {

	private static final Color NEGATED_COLOR_HEADER_BG = QueryUIPreferenceConstants.getNegatedHeaderBackgroundColor();
	private static final int MIN_VALUE_WIDTH = 120;

	public QueryConstraintInstanceNodeFigure(final ConstraintInstance instance) {
		super(instance);
		addValue(new EditableValue(QueryValueLabel.createGrowing(MIN_VALUE_WIDTH), instance,
				QueryPackage.Literals.CONSTRAINT_INSTANCE__DECLARATION,
				QueryConstraintInstanceNodeFigure::getDeclarationName,
				name -> QueryModelHelper.findConstraintDeclaration(instance, name)));
	}

	@Override
	public ConstraintInstance getElement() {
		return (ConstraintInstance) super.getElement();
	}

	@Override
	public Color getBackgroundColor() {
		return getElement().isNegate() ? NEGATED_COLOR_HEADER_BG : super.getBackgroundColor();
	}

	private static String getDeclarationName(final Object declaration) {
		if (declaration instanceof final ConstraintDeclaration constraintDeclaration) {
			return Objects.toString(constraintDeclaration.getName(), ""); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}
}
