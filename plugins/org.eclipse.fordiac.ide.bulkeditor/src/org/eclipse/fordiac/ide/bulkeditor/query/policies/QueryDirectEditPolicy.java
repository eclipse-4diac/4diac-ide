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
package org.eclipse.fordiac.ide.bulkeditor.query.policies;

import org.eclipse.fordiac.ide.bulkeditor.commands.ChangeQueryFeatureCommand;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;
import org.eclipse.fordiac.ide.bulkeditor.query.figures.QueryNodeFigure.EditableValue;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

/** Changes the value of a query element edited in the label of its node. */
public class QueryDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (request.getDirectEditFeature() instanceof final EditableValue editableValue
				&& request.getCellEditor().getValue() instanceof final String value && !value.equals(
						QueryModelHelper.getFeatureText(editableValue.element(), editableValue.featureName()))) {
			return new ChangeQueryFeatureCommand(editableValue.element(), editableValue.featureName(), value);
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		if (request.getDirectEditFeature() instanceof final EditableValue editableValue
				&& request.getCellEditor().getValue() instanceof final String value) {
			editableValue.label().setText(value);
		}
	}
}
