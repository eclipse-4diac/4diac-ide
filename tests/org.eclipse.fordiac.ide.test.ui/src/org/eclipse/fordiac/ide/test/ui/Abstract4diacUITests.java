/*******************************************************************************
 * Copyright (c) 2023 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ui;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.treeItemHasNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWT4diacGefBot;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefEditor;
import org.eclipse.fordiac.ide.test.ui.swtbot.SWTBot4diacGefViewer;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
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
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
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
	protected static final String APP = "App"; //$NON-NLS-1$
	protected static final String DELETE = "Delete"; //$NON-NLS-1$
	protected static final String DELETE_PROJECT_WARNING = "Delete project contents on disk (cannot be undone)"; //$NON-NLS-1$
	protected static final String DELETE_RESOURCES = "Delete Resources"; //$NON-NLS-1$
	protected static final String EDIT = "Edit"; //$NON-NLS-1$
	protected static final String EVENTS_NODE = "events"; //$NON-NLS-1$
	protected static final String E_CTUD_FB = "E_CTUD"; //$NON-NLS-1$
	protected static final String E_CTUD_TREE_ITEM = E_CTUD_FB + " [Event-Driven Up-Down Counter]"; //$NON-NLS-1$
	protected static final String E_CYCLE_FB = "E_CYCLE"; //$NON-NLS-1$
	protected static final String E_CYCLE_TREE_ITEM = "E_CYCLE [Peroidic event generator]"; //$NON-NLS-1$
	protected static final String E_DELAY_FB = "E_DELAY"; //$NON-NLS-1$
	protected static final String E_DEMUX_FB = "E_DEMUX"; //$NON-NLS-1$
	protected static final String E_DEMUX_TREE_ITEM = E_DEMUX_FB + " [Event demultiplexor]"; //$NON-NLS-1$
	protected static final String E_D_FF_FB = "E_D_FF"; //$NON-NLS-1$
	protected static final String E_D_FF_TREE_ITEM = E_D_FF_FB + " [Data latch (d) flip flop]"; //$NON-NLS-1$
	protected static final String E_N_TABLE_FB = "E_N_TABLE"; //$NON-NLS-1$
	protected static final String E_N_TABLE_TREE_ITEM = "E_N_TABLE [Generation of a finite train of sperate events]"; //$NON-NLS-1$
	protected static final String E_SELECT_FB = "E_SELECT"; //$NON-NLS-1$
	protected static final String E_SELECT_TREE_ITEM = E_SELECT_FB
			+ " [selection between two events based on boolean input G]";
	protected static final String E_SR_FB = "E_SR"; //$NON-NLS-1$
	protected static final String E_SR_TREE_ITEM = E_SR_FB + " [Event-driven bistable]"; //$NON-NLS-1$
	protected static final String E_SWITCH_FB = "E_SWITCH"; //$NON-NLS-1$
	protected static final String E_SWITCH_TREE_ITEM = "E_SWITCH [Switching (demultiplexing) an event based on boolean input G]"; //$NON-NLS-1$
	protected static final String E_TABLE_CTRL_FB = "E_TABLE_CTRL"; //$NON-NLS-1$
	protected static final String E_TABLE_CTRL_TREE_ITEM = "E_TABLE_CTRL [Support function block for E_TABLE]"; //$NON-NLS-1$
	protected static final String FILE = "File"; //$NON-NLS-1$
	protected static final String FINISH = "Finish"; //$NON-NLS-1$
	protected static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	protected static final String F_SUB = "F_SUB"; //$NON-NLS-1$
	protected static final String GO_TO_PARENT = "Go To Parent"; //$NON-NLS-1$
	protected static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	protected static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	protected static final String NAVIGATE = "Navigate"; //$NON-NLS-1$
	protected static final String NEW = "New"; //$NON-NLS-1$
	protected static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	protected static final String OK = "OK"; //$NON-NLS-1$
	protected static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	protected static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$
	protected static final String SELECT_ALL = "Select All"; //$NON-NLS-1$
	protected static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	protected static final String TYPE_LIBRARY_NODE = "Type Library"; //$NON-NLS-1$
	protected static final String CANCEL = "Cancel"; //$NON-NLS-1$
	protected static final String SYSTEM_EXPLORER_LABEL = "System Explorer"; //$NON-NLS-1$
	protected static final String TEST_PARENT_FOLDER = "TestParentProject"; //$NON-NLS-1$
	protected static final String FBT_TEST_PROJECT1 = "FBTTestProject1"; //$NON-NLS-1$
	protected static final String FBT_TEST_PROJECT2 = "FBTTestProject2"; //$NON-NLS-1$
	protected static final String FBT_TEST_PROJECT3 = "FBTTestProject3"; //$NON-NLS-1$
	protected static final String FBT_TEST_PROJECT4 = "FBTTestProject4"; //$NON-NLS-1$
	protected static final String PACKAGE_NAME1 = "pkg1"; //$NON-NLS-1$
	protected static final String PACKAGE_NAME2 = "pkg2"; //$NON-NLS-1$
	protected static final String PACKAGE_NAME = PACKAGE_NAME1 + "::" + PACKAGE_NAME2; //$NON-NLS-1$
	protected static final String PARENT_FOLDER_NAME_LABEL = "Enter or select the parent folder:"; //$NON-NLS-1$
	protected static final String TYPE_NAME_LABEL = FordiacMessages.TypeName + ":"; //$NON-NLS-1$
	protected static final String SELECT_TYPE_LABEL = FordiacMessages.SelectType + ":"; //$NON-NLS-1$
	protected static final String NEW_TYPE = FordiacMessages.NewType;
	protected static final String PACKAGE_NAME_LABEL = FordiacMessages.Package + ":"; //$NON-NLS-1$
	protected static final String TYPE_PROJECT = "Type..."; //$NON-NLS-1$
	protected static final String TEST_TYPE_TEMPLATE_NAME = "Adapter"; //$NON-NLS-1$

	// FB pins and values
	protected static final String START = "START"; //$NON-NLS-1$
	protected static final String STOP = "STOP"; //$NON-NLS-1$
	protected static final String D = "D"; //$NON-NLS-1$
	protected static final String G = "G"; //$NON-NLS-1$
	protected static final String N = "N"; //$NON-NLS-1$
	protected static final String Q = "Q"; //$NON-NLS-1$
	protected static final String R = "R"; //$NON-NLS-1$
	protected static final String CD = "CD"; //$NON-NLS-1$
	protected static final String CV = "CV"; //$NON-NLS-1$
	protected static final String DT = "DT"; //$NON-NLS-1$
	protected static final String EO = "EO"; //$NON-NLS-1$
	protected static final String EI = "EI"; //$NON-NLS-1$
	protected static final String PV = "PV"; //$NON-NLS-1$
	protected static final String QD = "QD"; //$NON-NLS-1$
	protected static final String QU = "QU"; //$NON-NLS-1$
	protected static final String CLK = "CLK"; //$NON-NLS-1$
	protected static final String EO0 = "EO0"; //$NON-NLS-1$
	protected static final String EO1 = "EO1"; //$NON-NLS-1$
	protected static final String EO2 = "EO2"; //$NON-NLS-1$
	protected static final String DTO = "DTO"; //$NON-NLS-1$
	protected static final String IN1 = "IN1"; //$NON-NLS-1$
	protected static final String REQ = "REQ"; //$NON-NLS-1$
	protected static final String CLKO = "CLKO"; //$NON-NLS-1$
	protected static final String INIT = "INIT"; //$NON-NLS-1$
	protected static final String DEF_VAL = "T#0s"; //$NON-NLS-1$
	protected static final String NEW_VAL = "T#1s"; //$NON-NLS-1$

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
		SWTBotPreferences.TIMEOUT = 10000;
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
		bot.menu(FILE).menu(NEW).menu(FORDIAC_IDE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_4DIAC_PROJECT);
		shell.activate();
		bot.textWithLabel(PROJECT_NAME_LABEL).setText(PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_SYSTEM_NAME_LABEL).getText(), PROJECT_NAME);
		assertEquals(bot.textWithLabel(INITIAL_APPLICATION_NAME_LABEL).getText(), PROJECT_NAME + APP);
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Drags and drops a FB onto the canvas with given name and position.
	 *
	 * @param fbName The name of the Function Block.
	 * @param point  The Position of the FB on the canvas.
	 */
	protected static void dragAndDropEventsFB(final String fbName, final Point point) {
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem treeProjectItem = tree.getTreeItem(PROJECT_NAME);
		treeProjectItem.select();
		treeProjectItem.expand();
		final SWTBotTreeItem typeLibraryNode = treeProjectItem.getNode(TYPE_LIBRARY_NODE);
		typeLibraryNode.select();
		typeLibraryNode.expand();
		final SWTBotTreeItem eventsNode = typeLibraryNode.getNode(EVENTS_NODE);
		eventsNode.select();
		eventsNode.expand();
		bot.waitUntil(treeItemHasNode(eventsNode, fbName));
		final SWTBotTreeItem eCycleNode = eventsNode.getNode(fbName);
		eCycleNode.select();
		eCycleNode.click();

		// select application editor
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		final SWTBotGefFigureCanvas canvas = viewer.getCanvas();

		assertNotNull(canvas);
		eCycleNode.dragAndDrop(canvas, point);
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
		bot.menu(EDIT).menu(DELETE).click();
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
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
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
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
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
	 * @return true is fbName is in the List, otherwise false.
	 */
	protected static boolean isFbSelected(final List<SWTBotGefEditPart> selectedEditParts, final String fbName) {
		return selectedEditParts.stream().filter(p -> p.part() instanceof FBEditPart).map(p -> (FBEditPart) p.part())
				.anyMatch(fb -> fb.getModel().getName().equals(fbName));
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
	 * Select a given FB on the editing area and double click it to access the
	 * CompositeInstanceViewer. After that the SWTBotGefEditor will be returned.
	 *
	 * @return editor The SWTBotGefEditor is returned
	 */
	protected static SWTBotGefEditor goToCompositeInstanceViewer(final String fb) {
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
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
	 * @param typeName Name of the new type.
	 */
	protected static void createFBType(final String parentName, final String typeName) {
		bot.menu(FILE).menu(NEW).menu(TYPE_PROJECT).click();
		final SWTBotShell shell = bot.shell(NEW_TYPE);
		shell.activate();
		bot.textWithLabel(TYPE_NAME_LABEL).setText(typeName);
		bot.textWithLabel(PARENT_FOLDER_NAME_LABEL).setText(parentName);
		assertEquals(bot.textWithLabel(TYPE_NAME_LABEL).getText(), typeName);
		bot.tableWithLabel(SELECT_TYPE_LABEL).getTableItem(TEST_TYPE_TEMPLATE_NAME).select();
		bot.button(FINISH).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Deletes FB Type with give name.
	 *
	 * @param typeName Name of the new type.
	 */
	protected static void deleteFBType(final String typeName) {
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();

		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);
		final SWTBotTreeItem parentItem = tree.getTreeItem(PROJECT_NAME);
		parentItem.expand();
		final SWTBotTreeItem projectItem = parentItem.getNode(typeName);
		projectItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
		final List<String> nodeList = parentItem.getNodes();
		assertFalse(nodeList.contains(typeName));
	}

	/**
	 * Returns from CompositeinstanceViewer back to Editing Area
	 */
	protected static void returnToEditingArea() {
		bot.menu(NAVIGATE).menu(GO_TO_PARENT).click();
	}

	/**
	 * Deletes 4diac IDE project.
	 */
	@AfterAll
	protected static void deleteProject() {
		final SWTBotView systemExplorerView = bot.viewById(SYSTEM_EXPLORER_ID);
		systemExplorerView.show();
		final Composite systemExplorerComposite = (Composite) systemExplorerView.getWidget();
		final Tree swtTree = bot.widget(WidgetMatcherFactory.widgetOfType(Tree.class), systemExplorerComposite);
		final SWTBotTree tree = new SWTBotTree(swtTree);

		final SWTBotTreeItem treeItem = tree.getTreeItem(PROJECT_NAME);
		treeItem.select();
		bot.menu(EDIT).menu(DELETE).click();

		// the project deletion confirmation dialog
		final SWTBotShell shell = bot.shell(DELETE_RESOURCES);
		shell.activate();
		bot.checkBox(DELETE_PROJECT_WARNING).select();
		bot.button(OK).click();
		bot.waitUntil(shellCloses(shell));
	}

	/**
	 * Cleans the canvas from all objects.
	 */
	@SuppressWarnings("static-method")
	@AfterEach
	protected void cleanEditorArea() {
		final SWTBotGefEditor editor = bot.gefEditor(PROJECT_NAME);
		final SWTBot4diacGefViewer viewer = (SWTBot4diacGefViewer) editor.getSWTBotGefViewer();
		viewer.getCanvas().setFocus();

		final SWTBotMenu editMenu = bot.menu(EDIT);
		editMenu.menu(SELECT_ALL).click();
		final SWTBotMenu deleteMenu = editMenu.menu(DELETE);
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
