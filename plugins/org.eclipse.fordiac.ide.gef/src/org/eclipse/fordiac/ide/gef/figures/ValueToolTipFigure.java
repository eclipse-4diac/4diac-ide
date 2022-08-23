/*******************************************************************************
 * Copyright (c) 2012, 2014, 2017 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.ValueValidator;

/**
 * The Class ToolTipFigure.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ValueToolTipFigure extends ToolTipFigure {
	/**
	 * Instantiates a new tool tip figure.
	 *
	 * @param element the element
	 */

	private final Label valueLabel;
	private final Label errorMsg;

	public ValueToolTipFigure(final INamedElement element, final Value value) {
		super(element);
		valueLabel = new Label();
		getLine().add(valueLabel);
		errorMsg = new Label();
		getLine().add(errorMsg);
		setValue(element, value);
	}

	public void setValue(final INamedElement element, final Value value) {
		valueLabel.setText(value.getValue());
		if (element instanceof VarDeclaration) {
			final String validationMsg = ValueValidator.validateValue((VarDeclaration) element, value.getValue());
			if ((null != validationMsg) && !validationMsg.isBlank()) {
				errorMsg.setText(validationMsg);
			}
		}
	}
}
