/*******************************************************************************
 * Copyright (c) 2024 Prashantkumar Khatri, Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prashantkumar Khatri - initial API and implementation and/or initial documentation
 *   Andrea Zoitl - Creation of a fluid API design for UI SWTBot testing
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;

public class SWTBotECC {

	private final SWT4diacGefBot bot;

	@SuppressWarnings("static-access")
	public SWTBotECC(final SWT4diacGefBot bot) {
		this.bot = bot;
	}

	/**
	 * Change the algorithm or event value of an EC state action.
	 *
	 * @param editor    The ECC editor.
	 * @param stateName The name of the EC state.
	 * @param value     The value to assign (algorithm or event).
	 * @param index     The index of the item box to modify.
	 */
	public void changeAlgorithmAndEventValue(final SWTBot4diacGefEditor editor, final String partLabel,
			final String newValue, final int boxIndex) {
		final SWTBotGefEditPart box = getChildrenPart(editor.getEditPart(partLabel), boxIndex);
		box.doubleClick().select();
		editor.bot().ccomboBox().setSelection(newValue);
		box.doubleClick().select();
		assertEquals(editor.bot().ccomboBox().selection(), newValue);
	}

	/**
	 * Retrieves a child part of a given parent part at a specified index.
	 *
	 * @param parentPart The parent part from which to retrieve the child.
	 * @param index      The index of the child part to retrieve. Use 0 for the
	 *                   first child, -1 for the last child, or any other index.
	 * @return The SWTBotGefEditPart corresponding to the specified child.
	 */
	@SuppressWarnings("static-method")
	public SWTBotGefEditPart getChildrenPart(final SWTBotGefEditPart parentPart, final int index) {
		final List<SWTBotGefEditPart> childParts = parentPart.children();
		assertNotNull(childParts);
		return switch (index) {
		case 0 -> {
			final SWTBotGefEditPart childPart = childParts.getFirst();
			assertNotNull(childPart);
			yield childPart;
		}
		case -1 -> {
			final SWTBotGefEditPart childPart = childParts.getLast();
			assertNotNull(childPart);
			yield childPart;
		}
		default -> {
			final SWTBotGefEditPart childPart = childParts.get(index);
			assertNotNull(childPart);
			yield childPart;
		}
		};
	}

	/**
	 * Retrieves the center point of a graphical part within the editor.
	 *
	 *
	 * @param editPart The graphical part for which to get the center point.
	 * @return A Point object representing the center coordinates of the part.
	 */
	@SuppressWarnings("static-method")
	public Point getPoint(final SWTBotGefEditPart editPart) {
		final IFigure figure = ((GraphicalEditPart) editPart.part()).getFigure();
		assertNotNull(figure);
		final Rectangle statePartBounds = ((GraphicalEditPart) editPart.part()).getFigure().getBounds().getCopy();
		assertNotNull(statePartBounds);
		figure.translateToAbsolute(statePartBounds);
		return new Point(statePartBounds.getCenter().x, statePartBounds.getCenter().y);
	}

}
