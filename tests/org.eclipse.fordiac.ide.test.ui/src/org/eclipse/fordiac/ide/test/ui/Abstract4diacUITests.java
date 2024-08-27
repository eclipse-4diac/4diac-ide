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
 *   Prashantkumar Khatri - Added various utility methods for testing the Type
 *   						editor and it's related UI components including FB type
 *   						creation, DataType Editor, Property Sheet and it's
 *   						tabs, and ECC edtior's flows.
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swtbot.eclipse.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefFigureCanvas;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class Abstract4diacUITests {

	protected static SWT4diacGefBot bot;

	/**
	 * Performs the necessary tasks to be able to perform the tests.
	 *
	 * The method creates the SWT4diacGefBot, starts 4diac IDE and closes the
	 * welcome window. The Timeout is increase from default value of 5 Seconds to 10
	 * Seconds. By calling the private method {@code createProject()} a new 4diac
	 * IDE project is created to be able to perform the tests.
	 */
	@BeforeAll
	protected static void beforeAll() {
		bot = new SWT4diacGefBot();
		bot.viewByTitle("Welcome").close(); //$NON-NLS-1$
		SWTBotPreferences.TIMEOUT = 30000;
		SWTBotPreferences.KEYBOARD_LAYOUT = "EN_US"; //$NON-NLS-1$
		createProject();
	}

	/**
	 * Creates a new 4diac IDE project
	 *
	 * The method creates a new 4diac IDE project with the static String of
	 * PROJECT_NAME and is called from {@link #beforeAll() method beforeAll}.
	 */
	protected static void createProject() {
		bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW).menu(UITestNamesHelper.FORDIAC_IDE_PROJECT)
				.click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(UITestNamesHelper.PROJECT_NAME_LABEL).setText(UITestNamesHelper.PROJECT_NAME);
		assertEquals(bot.textWithLabel(UITestNamesHelper.INITIAL_SYSTEM_NAME_LABEL).getText(),
				UITestNamesHelper.PROJECT_NAME);
		assertEquals(bot.textWithLabel(UITestNamesHelper.INITIAL_APPLICATION_NAME_LABEL).getText(),
				UITestNamesHelper.PROJECT_NAME + UITestNamesHelper.APP);
		bot.button(UITestNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Drags and drops a FB onto the canvas with given name and position.
	 *
	 * @param fbName The name of the Function Block.
	 * @param point  The Position of the FB on the canvas.
	 */
	protected static void dragAndDropEventsFB(final String fbName, final Point point) {
		final SWTBotTreeItem typeLibraryNode = expandTypeLibraryTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(typeLibraryNode, UITestNamesHelper.EVENTS_NODE));
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(UITestNamesHelper.EVENTS_NODE);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, fbName));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(fbName);
		eCycleNode.select();
		eCycleNode.click();

		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();

		assertNotNull(canvas);
		eCycleNode.dragAndDrop(canvas, point);
	}

	/**
	 * Checks if given element is visible in the Application of the System in the
	 * SystemExplorer Tree.
	 *
	 * @param element The name of the element (function block or SubApp) searched
	 *                for in the Application of the System in the SystemExplorer
	 *                tree.
	 */
	protected static boolean isElementInApplicationOfSystemInSystemExplorer(final String element) {
		final SWTBotTreeItem appNode = expandApplicationTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(appNode, element));
		final SWTBotTreeItem elementNode = appNode.getNode(element);
		assertNotNull(elementNode);
		return elementNode.isVisible();
	}

	/**
	 * Checks if given Function Block is visible in the SubApp node of the
	 * Application the System in the SystemExplorer Tree.
	 *
	 * @param fbName The name of the function block searched for in the SubApp node
	 *               of the Application in the System in the SystemExplorer tree.
	 */
	protected static boolean isFBInSubAppOfSystemInSystemExplorer(final String fbName) {
		final SWTBotTreeItem subAppNode = expandSubAppTreeItemInSystemExplorer();
		final SWTBotTreeItem fbNode = subAppNode.getNode(fbName);
		assertNotNull(fbNode);
		return fbNode.isVisible();
	}

	protected static boolean isSubAppNodeInSystemExplorerEmpty() {
		final SWTBotTreeItem subAppNode = expandSubAppTreeItemInSystemExplorer();
		final List<String> subAppChildren = subAppNode.getNodes();
		return subAppChildren.isEmpty();
	}

	protected static void createSubappWithDragRectangle(final int fromXPosition, final int fromYPosition,
			final int toXPosition, final int toYPosition) {
		final SWTBot4diacGefEditor editor = (SWTBot4diacGefEditor) bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		editor.drag(fromXPosition, fromYPosition, toXPosition, toYPosition);
		bot.menu(UITestNamesHelper.SOURCE).menu(UITestNamesHelper.NEW_SUBAPPLICATION).click();

	}

	/**
	 * Selects a FunctionBlock with the given Name in the editor.
	 *
	 * The function block is searched for with the given name in the editor.
	 * However, as this only selects the name, the parent is retrieved and selected.
	 * Only then is the searched function block selected as a whole.
	 *
	 * @param editor
	 * @param name
	 */
	protected static SWTBot4diacGefEditor selectFBWithFBNameInEditor(final SWTBot4diacGefEditor editor,
			final String name) {
		assertNotNull(editor);
		assertNotNull(editor.getEditPart(name));
		editor.click(name);
		final SWTBotGefEditPart parent = editor.getEditPart(name).parent();
		assertNotNull(parent);
		editor.click(parent);
		return editor;
	}

	/**
	 * Deletes a FB from the editing area.
	 *
	 * @param editor         The SWTBot4diacGefEditor from which a FB with given
	 *                       instance name should be deleted.
	 * @param FbInstanceName The instance name of the FB
	 */
	protected static void deleteFB(final SWTBot4diacGefEditor editor, final String FbInstanceName) {
		editor.setFocus();
		final SWTBotGefEditPart fb = editor.getEditPart(FbInstanceName).parent();
		fb.select().click();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();
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
	protected static SWTBot4diacGefViewer createConnection(final String pin1, final String pin2) {
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

	protected static boolean checkIfConnectionCanBeFound(final String srcPinName, final String dstPinName) {
		return findConnection(srcPinName, dstPinName) != null;
	}

	protected static ConnectionEditPart findConnection(final String srcPinName, final String dstPinName) {
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

	protected static Rectangle getAbsolutePosition(final SWTBotGefEditor editor, final String fb) {
		final SWTBotGefEditPart parent = editor.getEditPart(fb).parent();
		assertNotNull(parent);
		final IFigure figurePos = ((GraphicalEditPart) parent.part()).getFigure();
		assertNotNull(figurePos);
		final Rectangle fbBounds = figurePos.getBounds().getCopy();
		assertNotNull(fbBounds);
		figurePos.translateToAbsolute(fbBounds);
		return fbBounds;
	}

	/**
	 * Checks if FB is selected by searching selectedEditParts list and returns the
	 * corresponding boolean value.
	 *
	 * @param selectedEditParts The List of selected EditParts.
	 * @param fbName            The FB that is searched for.
	 * @return true if fbName is in the List, otherwise false.
	 */
	protected static boolean isFbSelected(final List<SWTBotGefEditPart> selectedEditParts, final String fbName) {
		return selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart).map(p -> (FBEditPart) p.part())
				.anyMatch(fb -> fb.getModel().getName().equals(fbName));
	}

	/**
	 * Checks if Subapplication is selected by searching selectedEditParts list and
	 * returns the corresponding boolean value.
	 *
	 * @param selectedEditParts The List of selected EditParts.
	 * @param subAppName        The Subapplication that is searched for.
	 * @return true if Subapplication is in the List, otherwise false.
	 */
	protected static boolean isSubappSelected(final List<SWTBotGefEditPart> selectedEditParts,
			final String subAppName) {
		return selectedEditParts.stream().filter(p -> p.part() instanceof SubAppForFBNetworkEditPart)
				.map(p -> (SubAppForFBNetworkEditPart) p.part())
				.anyMatch(fb -> subAppName.equals(fb.getModel().getName()));
	}

	/**
	 * Checks if given FB instance name can be found on the editing area.
	 *
	 * @param editor The SWTBot4diacGefEditor in which the FB instance name is
	 *               searched
	 * @param fbName The String of the FB instance name searched for
	 * @return true if FB is onto editing area, false if not
	 */
	protected static boolean isFBNamePresendOnEditingArea(final SWTBot4diacGefEditor editor, final String fbName) {
		final List<SWTBotGefEditPart> editParts = editor.editParts(new BaseMatcher<EditPart>() {
			@Override
			public boolean matches(final Object item) {
				return item instanceof FBEditPart;
			}

			@Override
			public void describeTo(final Description description) {
				// method must be implemented but empty since not needed
			}
		});
		assertFalse(editParts.isEmpty());
		return editParts.stream().filter(p -> p.part() instanceof FBEditPart).map(p -> (FBEditPart) p.part())
				.anyMatch(fb -> fb.getModel().getName().equals(fbName));
	}

	/**
	 * Returns the bounds of the searched function block
	 *
	 * @param editor
	 * @param fBname
	 * @return The rectangle with the bounds of the function block searched for
	 */
	protected static Rectangle getBoundsOfFB(final SWTBot4diacGefEditor editor, final String fBname) {
		editor.getEditPart(fBname);
		editor.click(fBname);
		final SWTBotGefEditPart parent = editor.getEditPart(fBname).parent();
		final IFigure figure = ((GraphicalEditPart) parent.part()).getFigure();
		final Rectangle fbBounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(fbBounds);
		return fbBounds;
	}

	/**
	 * Select a given FB on the editing area and double click it to access the
	 * CompositeInstanceViewer. After that the SWTBotGefEditor will be returned.
	 *
	 * @return editor The SWTBotGefEditor is returned
	 */
	protected static SWTBotGefEditor goToCompositeInstanceViewer(final String fb) {
		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		assertNotNull(editor);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		assertNotNull(viewer);
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();
		assertNotNull(canvas);
		assertNotNull(editor.getEditPart(fb));
		editor.click(fb);
		final SWTBotGefEditPart parent = editor.getEditPart(fb).parent();
		assertNotNull(parent);
		parent.doubleClick();
		return editor;
	}

	/**
	 * Creates a New Type with given name.
	 *
	 * @param parentName Name of the parent project.
	 * @param typeName   Name of the new type.
	 * @param typeLabel  Name of the Type Label.
	 */
	protected static void createFBType(final String parentName, final String typeName, final String typeLabel) {
		bot.menu(UITestNamesHelper.FILE).menu(UITestNamesHelper.NEW).menu(UITestNamesHelper.TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(UITestNamesHelper.NEW_TYPE);
		shell.activate();
		bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).setText(typeName);
		bot.textWithLabel(UITestNamesHelper.PARENT_FOLDER_NAME_LABEL).setText(parentName);
		assertEquals(bot.textWithLabel(UITestNamesHelper.TYPE_NAME_LABEL).getText(), typeName);
		bot.tableWithLabel(UITestNamesHelper.SELECT_TYPE_LABEL).getTableItem(typeLabel).select();
		bot.button(UITestNamesHelper.FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes FB Type with give name.
	 *
	 * @param typeName Name of the new type.
	 */
	protected static void deleteFBType(final String typeName) {
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		parentItem.expand();
		SWTBotTreeItem projectItem = null;
		for (final SWTBotTreeItem item : parentItem.getItems()) {
			if (item.getText().contains(typeName)) {
				projectItem = item;
				break;
			}
		}
		projectItem.select();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(UITestNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(typeName));
	}

	/**
	 * Open FB Type in editor from explorer.
	 *
	 * @param parentName Name of the parent Project.
	 * @param typeName   Name of the new type.
	 */
	protected static void openFBTypeInEditor(final String parentName, final String typeName) {
		final SWTBotView systemExplorerView = bot.viewByTitle(UITestNamesHelper.SYSTEM_EXPLORER_LABEL);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(parentName);
		parentItem.expand();
		SWTBotTreeItem projectItem = null;
		for (final SWTBotTreeItem item : parentItem.getItems()) {
			if (item.getText().contains(typeName)) {
				projectItem = item;
				break;
			}
		}
		assertTrue(projectItem.getText().contains(typeName));
	}

	/**
	 * Deletes the given pin on the FB opened the provided editor.
	 *
	 * @param editor  Selected Gef Editor
	 * @param pinName Name of pin.
	 */
	protected static void deletePin(final SWTBot4diacGefEditor editor, final String pinName) {
		final SWTBotGefEditPart pin = editor.getEditPart(pinName);
		assertNotNull(pin);
		pin.click();
		editor.clickContextMenu(UITestNamesHelper.DELETE);
		assertNull(editor.getEditPart(pinName));
	}

	/**
	 * Creates connection within FB Type from Property Sheet
	 *
	 * @param pin1   Name of pin1.
	 * @param pin2   Name of pin2.
	 * @param editor Selected Gef Editor
	 */
	protected static void createConnectionWithinFBTypeWithPropertySheet(final String pin1, final String pin2,
			final SWTBot4diacGefEditor editor) {

		final SWTBotGefEditPart port1 = editor.getEditPart(pin1);
		port1.click();

		final SWTBotGefEditPart port2 = editor.getEditPart(pin2);
		port2.click();

		SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		if (PropertySheetHelper.selectPropertyTabItem(UITestNamesHelper.EVENT, propertiesBot)) {
			propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.EVENT);
		} else {
			propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.DATA);
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
	protected static void removeConnectionWithinFBTypeWithPropertySheet(final String pin1, final String pin2,
			final SWTBot4diacGefEditor editor) {

		final SWTBotGefEditPart port1 = editor.getEditPart(pin1);
		editor.click(port1);

		final SWTBotGefEditPart port2 = editor.getEditPart(pin2);
		port2.click();

		SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		if (PropertySheetHelper.selectPropertyTabItem(UITestNamesHelper.EVENT, propertiesBot)) {
			propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.EVENT);
		} else {
			propertiesBot = selectTabFromInterfaceProperties(UITestNamesHelper.DATA);
		}

		// Find the group with the label "With"
		final SWTBotTable table = propertiesBot.tableInGroup(UITestNamesHelper.WITH);

		table.select(pin1);
		table.getTableItem(pin1).toggleCheck();
		assertFalse(table.getTableItem(pin1).isChecked());
		checkIfConnectionCanBeFound(pin1, pin2);
	}

	/**
	 * Select tab from Interface's Properties
	 *
	 * @param tabName Name of Tab on Property Sheet under Interface
	 */
	protected static SWTBot selectTabFromInterfaceProperties(final String tabName) {
		// Interface tab
		final SWTBotCTabItem interfaceTab = bot.cTabItem(UITestNamesHelper.INTERFACE);
		interfaceTab.activate();
		interfaceTab.setFocus();

		// Properties tab access
		final SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		// Tabs access inside property sheet
		PropertySheetHelper.selectPropertyTabItem(tabName, propertiesBot);
		return propertiesBot;
	}

	/**
	 * Select tab from Interface's Properties
	 *
	 * @param tabName Name of Tab on Property Sheet under ECC
	 */
	protected static SWTBot selectTabFromECCProperties(final String tabName) {
		// Interface tab
		final SWTBotCTabItem eccTab = bot.cTabItem(UITestNamesHelper.ECC);
		eccTab.activate();
		eccTab.setFocus();

		// Properties tab access
		final SWTBot propertiesBot = bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).bot();
		bot.viewByTitle(UITestNamesHelper.PROPERTIES_TITLE).setFocus();

		// Tabs access inside property sheet
		PropertySheetHelper.selectPropertyTabItem(tabName, propertiesBot);
		return propertiesBot;
	}

	/**
	 * Creates New Variable In Data Type Editor
	 *
	 * @param dataTypeTableBot NatTable in which variable need to create
	 */
	protected static void createNewVariableInDataTypeEditor(final SWTBot4diacNatTable dataTypeTableBot) {
		bot.editorByTitle(UITestNamesHelper.FBT_TEST_PROJECT2).show();
		final int rowCount = dataTypeTableBot.rowCount();
		bot.button(0).click();
		assertEquals(dataTypeTableBot.rowCount(), rowCount + 1);
	}

	/**
	 * Delete Variable From Data Type Editor
	 *
	 * @param dataTypeTableBot NatTable from which variable have to delete
	 */
	protected static void deleteVariable(final SWTBot4diacNatTable dataTypeTableBot) {
		final int rowCount = dataTypeTableBot.rowCount();
		dataTypeTableBot.click(1, 0);
		bot.button(1).click();
		assertEquals(dataTypeTableBot.rowCount(), rowCount - 1);
	}

	/**
	 * Change the value for the Cell
	 *
	 * @param dataTypeTableBot NatTable which contain the targeted cell
	 * @param newValue         The new Value which is to be set
	 * @param row              Cell's row number
	 * @param col              Cell's column number
	 */
	protected static void changeCellValueInNatTbale(final SWTBot4diacNatTable dataTypeTableBot, final String newValue,
			final int row, final int col) {
		dataTypeTableBot.doubleclick(row, col);
		dataTypeTableBot.setCellDataValueByPosition(row, col, newValue);
		dataTypeTableBot.doubleclick(row, col);
		assertEquals(dataTypeTableBot.getCellDataValueByPosition(row, col), newValue);
	}

	/**
	 * Change the algorithm or event value of an EC state action.
	 *
	 * @param editor    The ECC editor.
	 * @param stateName The name of the EC state.
	 * @param value     The value to assign (algorithm or event).
	 * @param index     The index of the item box to modify.
	 */
	protected static void changeAlgorithmAndEventValue(final SWTBot4diacGefEditor editor, final String partLabel,
			final String newValue, final int boxIndex) {
		final SWTBotGefEditPart box = getChildrenPart(editor.getEditPart(partLabel), boxIndex);
		box.doubleClick().select();
		editor.bot().ccomboBox().setSelection(newValue);
		box.doubleClick().select();
		assertEquals(editor.bot().ccomboBox().selection(), newValue);
	}

	/**
	 * Retrieves the center point of a graphical part within the editor.
	 *
	 *
	 * @param editPart The graphical part for which to get the center point.
	 * @return A Point object representing the center coordinates of the part.
	 */
	protected static Point getPoint(final SWTBotGefEditPart editPart) {
		final IFigure figure = ((GraphicalEditPart) editPart.part()).getFigure();
		assertNotNull(figure);
		final Rectangle statePartBounds = ((GraphicalEditPart) editPart.part()).getFigure().getBounds().getCopy();
		assertNotNull(statePartBounds);
		figure.translateToAbsolute(statePartBounds);
		return new Point(statePartBounds.getCenter().x, statePartBounds.getCenter().y);

	}

	/**
	 * Retrieves a child part of a given parent part at a specified index.
	 *
	 * @param parentPart The parent part from which to retrieve the child.
	 * @param index      The index of the child part to retrieve. Use 0 for the
	 *                   first child, -1 for the last child, or any other index.
	 * @return The SWTBotGefEditPart corresponding to the specified child.
	 */
	protected static SWTBotGefEditPart getChildrenPart(final SWTBotGefEditPart parentPart, final int index) {
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
	 * Returns from CompositeinstanceViewer back to Editing Area
	 */
	protected static void returnToEditingArea() {
		bot.menu(UITestNamesHelper.NAVIGATE).menu(UITestNamesHelper.GO_TO_PARENT).click();
	}

	/**
	 * Deletes 4diac IDE project.
	 */
	@AfterAll
	protected static void deleteProject() {
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);

		final SWTBotTreeItem treeItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		treeItem.select();
		bot.menu(UITestNamesHelper.EDIT).menu(UITestNamesHelper.DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(UITestNamesHelper.DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(UITestNamesHelper.DELETE_PROJECT_WARNING).select();
		bot.button(UITestNamesHelper.OK).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Cleans the canvas from all objects.
	 *
	 * First, the application node in the System Explorer is selected to ensure that
	 * all objects are selected and then deleted.
	 *
	 * @author Andrea Zoitl
	 */
	@SuppressWarnings("static-method")
	@AfterEach
	protected void cleanEditorArea() {
		final SWTBotTreeItem appNode = expandApplicationTreeItemInSystemExplorer();
		appNode.contextMenu("Open").click();

		final SWTBotGefEditor editor = bot.gefEditor(UITestNamesHelper.PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		viewer.getCanvas().setFocus();

		final SWTBotMenu editMenu = bot.menu(UITestNamesHelper.EDIT);
		editMenu.menu(UITestNamesHelper.SELECT_ALL).click();
		final SWTBotMenu deleteMenu = editMenu.menu(UITestNamesHelper.DELETE);
		if (deleteMenu.isEnabled()) {
			// not all Tests have a remaining FB
			deleteMenu.click();
		}
	}

	/**
	 * Resets the workbench after
	 */
	@AfterClass
	protected static void afterClass() {
		bot.resetWorkbench();
	}

	/**
	 * Expands the 4diac IDE Project node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return treeProjectItem the expanded Project node
	 */
	private static SWTBotTreeItem expandProjectTreeItemInSystemExplorer() {
		final SWTBotView systemExplorerView = bot.viewById(UITestNamesHelper.SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		assertNotNull(tree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(UITestNamesHelper.PROJECT_NAME);
		treeProjectItem.select();
		treeProjectItem.expand();
		return treeProjectItem;
	}

	/**
	 * Expands the System node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return systemNode the expanded System node
	 */
	private static SWTBotTreeItem expandSystemTreeItemInSystemExplorer() {
		final SWTBotTreeItem treeProjectItem = expandProjectTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(treeProjectItem, UITestNamesHelper.PROJECT_NAME_TREE_ITEM));
		final SWTBotTreeItem systemNode = treeProjectItem.getNode(UITestNamesHelper.PROJECT_NAME_TREE_ITEM);
		systemNode.select();
		systemNode.expand();
		return systemNode;
	}

	/**
	 * Expands the Type Library node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return typeLibraryNode the expanded Type Library node
	 */
	private static SWTBotTreeItem expandTypeLibraryTreeItemInSystemExplorer() {
		final SWTBotTreeItem treeProjectItem = expandProjectTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(treeProjectItem, UITestNamesHelper.TYPE_LIBRARY_NODE));
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(UITestNamesHelper.TYPE_LIBRARY_NODE);
		typeLibraryNode.select();
		typeLibraryNode.expand();
		return typeLibraryNode;
	}

	/**
	 * Expands the Application node (SWTBotTreeItem) in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return appNode the expanded Application node
	 */
	private static SWTBotTreeItem expandApplicationTreeItemInSystemExplorer() {
		final SWTBotTreeItem systemNode = expandSystemTreeItemInSystemExplorer();
		bot.waitUntil(treeItemHasNode(systemNode, UITestNamesHelper.PROJECT_NAME_APP));
		final SWTBotTreeItem appNode = systemNode.getNode(UITestNamesHelper.PROJECT_NAME_APP);
		assertNotNull(appNode);
		appNode.select();
		appNode.expand();
		return appNode;
	}

	/**
	 * Expands the Subapplication node (SWTBotTreeItem) of the 1. level of an
	 * Application in the System Explorer
	 *
	 * @author Andrea Zoitl
	 * @return appNode the expanded Application node
	 */
	private static SWTBotTreeItem expandSubAppTreeItemInSystemExplorer() {
		final SWTBotTreeItem appNode = expandApplicationTreeItemInSystemExplorer();
		final SWTBotTreeItem subAppNode = appNode.getNode(UITestNamesHelper.SUBAPP);
		assertNotNull(subAppNode);
		subAppNode.select();
		subAppNode.expand();
		return subAppNode;
	}
}
