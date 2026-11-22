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

import org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/**
 * Node of a constraint declaration showing its name, which its instances show.
 */
public class QueryConstraintDeclarationNodeFigure extends QueryValueNodeFigure {

	private static final int MIN_VALUE_WIDTH = 120;

	public QueryConstraintDeclarationNodeFigure(final ConstraintDeclaration declaration) {
		super(declaration);
		addValue(QueryPackage.Literals.CONSTRAINT_DECLARATION__NAME, QueryValueLabel.createGrowing(MIN_VALUE_WIDTH));
	}
}
