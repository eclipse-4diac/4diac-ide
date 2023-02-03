/********************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import org.eclipse.draw2d.Label;
import org.eclipse.fordiac.ide.gef.editparts.LabelDirectEditManager;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;

public class PinValueDirectEditManager extends LabelDirectEditManager {

	private boolean isValid;

	final KeyAdapter listener = new KeyAdapter() {
		@Override
		public void keyPressed(final KeyEvent e) {
			// hook enter key pressed to save also default values in pin
			if (e.character == '\r') {
				setDirty(true);
			}
		}

		@Override
		public void keyReleased(final KeyEvent e) {
			final String value = (String) getCellEditor().getValue();
			final IInterfaceElement ie = ((Value) getEditPart().getModel()).getParentIE();

			if (!value.isBlank() && (ie instanceof VarDeclaration)) {
				final String validationMsg = VariableOperations.validateValue((VarDeclaration) ie, value);
				isValid = !((validationMsg != null) && !validationMsg.trim().isEmpty());
				if (!isValid) {
					ErrorMessenger.popUpErrorMessage(validationMsg);
				}
			}
		}
	};

	public PinValueDirectEditManager(final GraphicalEditPart source, final Label label) {
		super(source, label);
	}

	@Override
	protected void initCellEditor() {
		super.initCellEditor();
		((Text) getCellEditor().getControl()).addKeyListener(listener);
	}

}
