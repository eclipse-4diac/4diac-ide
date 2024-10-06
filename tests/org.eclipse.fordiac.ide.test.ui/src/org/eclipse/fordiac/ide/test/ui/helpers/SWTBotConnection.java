/*******************************************************************************
 * Copyright (c) 2023, 2024 Andrea Zoitl, Prashantkumar Khatri
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *                - moved methods that are related to connections from class
 *                  Abstract4diacUITests here due to architecture redesign and
 *                  created constructor and fields.
 *   Prashantkumar Khatri - added FBType related methods
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.Abstract4diacUITests;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;

public class SWTBotConnection {

	private final SWT4diacGefBot bot;

	@SuppressWarnings("static-access")
	public SWTBotConnection(final SWT4diacGefBot bot) {
		this.bot = bot;
	}

	/**
	 * Creates a connection between two pins.
	 *
	 * The method creates a connection between the two given pins, the order of the
	 * pins is not important and returns a SWTBot4diacGefViewer. Whether a
	 * connection could actually be created is not checked here.
	 *
	 * @param pin1 One of the two pins between a connection is (tried to) create.
	 * @param pin2 One of the two pins between a connection is (tried to) create.
	 * @return SWTBot4diacGefViewer
	 */
	public SWTBot4diacGefViewer createConnection(final String pin1, final String pin2) {
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		// select input pin
		editor.click(pin1);
		final SWTBotGefEditPart ei = editor.getEditPart(pin1);
		assertNotNull(ei);
		final IFigure figure = ((GraphicalEditPart) ei.part()).getFigure();
		assertNotNull(figure);
		final Rectangle inputPinBounds1 = figure.getBounds().getCopy();
		assertNotNull(inputPinBounds1);
		figure.translateToAbsolute(inputPinBounds1);
		// select output pin
		editor.click(pin2);
		final SWTBotGefEditPart eo = editor.getEditPart(pin2);
		assertNotNull(eo);
		final Rectangle inputPinBounds2 = ((GraphicalEditPart) eo.part()).getFigure().getBounds().getCopy();
		assertNotNull(inputPinBounds2);
		figure.translateToAbsolute(inputPinBounds2);
		viewer.drag(pin2, inputPinBounds1.getCenter().x, inputPinBounds1.getCenter().y);
		checkIfConnectionCanBeFound(pin1, pin2);
		return viewer;
	}

	public boolean checkIfConnectionCanBeFound(final String srcPinName, final String dstPinName) {
		return findConnection(srcPinName, dstPinName) != null;
	}

	public ConnectionEditPart findConnection(final String srcPinName, final String dstPinName) {
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final GraphicalViewer graphicalViewer = viewer.getGraphicalViewer();

		return Display.getDefault().syncCall(() -> {
			graphicalViewer.flush();
			final Map<?, ?> editPartRegistry = graphicalViewer.getEditPartRegistry();
			for (final Object obj : editPartRegistry.values()) {
				if (obj instanceof final ConnectionEditPart cEP) {
					// search for connection between the 2 pins
					final EditPart source = cEP.getSource();
					final EditPart target = cEP.getTarget();

					final IFigure srcFigure = ((GraphicalEditPart) source).getFigure();
					final Label srcLabel = (Label) srcFigure;
					final String srcText = srcLabel.getText();

					final IFigure dstFigure = ((GraphicalEditPart) target).getFigure();
					final Label dstLabel = (Label) dstFigure;
					final String dstText = dstLabel.getText();

					if (srcPinName.equals(srcText) && dstPinName.equals(dstText)) {
						return cEP;
					}

				}
			}
			return null;
		});
	}

	/**
	 * Creates connection within FB Type from Property Sheet
	 *
	 * @param pin1   Name of pin1.
	 * @param pin2   Name of pin2.
	 * @param editor Selected Gef Editor
	 */
	public void createConnectionWithinFBTypeWithPropertySheet(final String pin1, final String pin2,
			final SWTBot4diacGefEditor editor) {

		final SWTBotGefEditPart port1 = editor.getEditPart(pin1);
		port1.click();

		final SWTBotGefEditPart port2 = editor.getEditPart(pin2);
		port2.click();

		SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		if (SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.EVENT, propertiesBot)) {
			propertiesBot = Abstract4diacUITests.selectTabFromInterfaceProperties(UITestNamesHelper.EVENT);
		} else {
			propertiesBot = Abstract4diacUITests.selectTabFromInterfaceProperties(UITestNamesHelper.DATA);
		}

		// Find the group with the label "With"
		final SWTBotTable table = propertiesBot.tableInGroup(UITestNamesHelper.WITH);

		table.select(pin1);
		table.getTableItem(pin1).toggleCheck();
		assertTrue(table.getTableItem(pin1).isChecked());
		checkIfConnectionCanBeFound(pin1, pin2);
	}

	/**
	 * Remove connection within FB Type from Property Sheet
	 *
	 * @param pin1   Name of pin1.
	 * @param pin2   Name of pin2.
	 * @param editor Selected Gef Editor
	 */
	public void removeConnectionWithinFBTypeWithPropertySheet(final String pin1, final String pin2,
			final SWTBot4diacGefEditor editor) {

		final SWTBotGefEditPart port1 = editor.getEditPart(pin1);
		editor.click(port1);

		final SWTBotGefEditPart port2 = editor.getEditPart(pin2);
		port2.click();

		SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		if (SWTBotPropertySheet.selectPropertyTabItem(UITestNamesHelper.EVENT, propertiesBot)) {
			propertiesBot = Abstract4diacUITests.selectTabFromInterfaceProperties(UITestNamesHelper.EVENT);
		} else {
			propertiesBot = Abstract4diacUITests.selectTabFromInterfaceProperties(UITestNamesHelper.DATA);
		}

		// Find the group with the label "With"
		final SWTBotTable table = propertiesBot.tableInGroup(UITestNamesHelper.WITH);

		table.select(pin1);
		table.getTableItem(pin1).toggleCheck();
		assertFalse(table.getTableItem(pin1).isChecked());
		checkIfConnectionCanBeFound(pin1, pin2);
	}

}
