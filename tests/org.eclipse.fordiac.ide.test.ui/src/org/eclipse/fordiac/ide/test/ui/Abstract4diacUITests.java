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
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.test.ui.helpers.SWTBotSystemExplorer;
import org.eclipse.fordiac.ide.test.ui.helpers.UITestNamesHelper;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacNatTable;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
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
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
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
	 * Select tab from Interface's Properties
	 *
	 * @param tabName Name of Tab on Property Sheet under Interface
	 */
	public static SWTBot selectTabFromInterfaceProperties(final String tabName) {
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
		final SWTBotSystemExplorer sysExBot = new SWTBotSystemExplorer(bot);
		final SWTBotTreeItem appNode = sysExBot.expandApplicationTreeItemInSystemExplorer();
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

}
