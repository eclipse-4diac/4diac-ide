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

import org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/** Node of a placeholder showing its key and its value. */
public class QueryPlaceholderNodeFigure extends QueryValueNodeFigure {

	private static final int VALUE_WIDTH = 120;

	public QueryPlaceholderNodeFigure(final Placeholder placeholder) {
		super(placeholder);
		addValue(QueryPackage.Literals.PLACEHOLDER__KEY, QueryValueLabel.createFixedWidth(VALUE_WIDTH));
		addValue(QueryPackage.Literals.PLACEHOLDER__VAL, QueryValueLabel.createFixedWidth(VALUE_WIDTH));
	}
}
