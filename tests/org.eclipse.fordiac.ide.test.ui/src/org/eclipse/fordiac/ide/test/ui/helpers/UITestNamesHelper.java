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
 *                - added static final fields for SWTBot tests
 *                - restructured fields for SWTBot tests in this helper class
 *   Prashantkumar Khatri - added static final fields for SWTBot tests
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.ui.helpers;

import org.eclipse.fordiac.ide.ui.FordiacMessages;

public final class UITestNamesHelper {

	// alphabetical order of static variable names
	public static final String ADAPTER = "Adapter"; //$NON-NLS-1$
	public static final String ADD_ACTION = "Add Action"; //$NON-NLS-1$
	public static final String ADD_STATE = "Add State"; //$NON-NLS-1$
	public static final String APP = "App"; //$NON-NLS-1$
	public static final String ATTRIBUTE1 = "Attribute1"; //$NON-NLS-1$
	public static final String ATTRIBUTE2 = "Attribute2"; //$NON-NLS-1$
	public static final String ATTRIBUTE3 = "Attribute3"; //$NON-NLS-1$
	public static final String ATTRIBUTES = "Attributes"; //$NON-NLS-1$
	public static final String BOOL2BOOL = "BOOL2BOOL"; //$NON-NLS-1$
	public static final String CANCEL = "Cancel"; //$NON-NLS-1$
	public static final String CNF = "CNF"; //$NON-NLS-1$
	public static final String COMMENT = "Comment:"; //$NON-NLS-1$
	public static final String CONDITION_LABEL = "Condition:"; //$NON-NLS-1$
	public static final String CONSTANTS = "Constants"; //$NON-NLS-1$
	public static final String CONVERT = "convert"; //$NON-NLS-1$
	public static final String CREATE_INPUT_EVENT = "Create Input Event"; //$NON-NLS-1$
	public static final String CREATE_OUTPUT_EVENT = "Create Output Event"; //$NON-NLS-1$
	public static final String CREATE_DATA_INPUT = "Create Data Input"; //$NON-NLS-1$
	public static final String CREATE_DATA_OUTPUT = "Create Data Output"; //$NON-NLS-1$
	public static final String DATA = "Data"; //$NON-NLS-1$
	public static final String DELETE = "Delete"; //$NON-NLS-1$
	public static final String DELETE_PROJECT_WARNING = "Delete project contents on disk (cannot be undone)"; //$NON-NLS-1$
	public static final String DELETE_RESOURCES = "Delete Resources"; //$NON-NLS-1$
	public static final String DEINIT = "DeInit"; //$NON-NLS-1$
	public static final String DEINITIALIZE = "deInitialize"; //$NON-NLS-1$
	public static final String DOT_BUTTON = "..."; //$NON-NLS-1$
	public static final String ECC = "ECC"; //$NON-NLS-1$
	public static final String EDIT = "Edit"; //$NON-NLS-1$
	public static final String ELEMENTARY_TYPE = "Elementary Types"; //$NON-NLS-1$
	public static final String EVENT = "Event"; //$NON-NLS-1$
	public static final String EVENTS_NODE = "events"; //$NON-NLS-1$
	public static final String EVENT_IN_AND_OUTPUTS = "Event In- & Outputs"; //$NON-NLS-1$
	public static final String E_CTUD_FB = "E_CTUD"; //$NON-NLS-1$
	public static final String E_CTUD_TREE_ITEM = E_CTUD_FB + " [Event-Driven Up-Down Counter]"; //$NON-NLS-1$
	public static final String E_CYCLE_FB = "E_CYCLE"; //$NON-NLS-1$
	public static final String E_CYCLE_TREE_ITEM = "E_CYCLE [Peroidic event generator]"; //$NON-NLS-1$
	public static final String E_DELAY_FB = "E_DELAY"; //$NON-NLS-1$
	public static final String E_DEMUX_FB = "E_DEMUX"; //$NON-NLS-1$
	public static final String E_DEMUX_TREE_ITEM = E_DEMUX_FB + " [Event demultiplexor]"; //$NON-NLS-1$
	public static final String E_D_FF_FB = "E_D_FF"; //$NON-NLS-1$
	public static final String E_D_FF_TREE_ITEM = E_D_FF_FB + " [Data latch (d) flip flop]"; //$NON-NLS-1$
	public static final String E_N_TABLE_FB = "E_N_TABLE"; //$NON-NLS-1$
	public static final String E_N_TABLE_TREE_ITEM = "E_N_TABLE [Generation of a finite train of sperate events]"; //$NON-NLS-1$
	public static final String E_SELECT_FB = "E_SELECT"; //$NON-NLS-1$
	public static final String E_SELECT_TREE_ITEM = E_SELECT_FB
			+ " [selection between two events based on boolean input G]"; //$NON-NLS-1$
	public static final String E_SR_FB = "E_SR"; //$NON-NLS-1$
	public static final String E_SR_TREE_ITEM = E_SR_FB + " [Event-driven bistable]"; //$NON-NLS-1$
	public static final String E_SWITCH_FB = "E_SWITCH"; //$NON-NLS-1$
	public static final String E_SWITCH_TREE_ITEM = "E_SWITCH [Switching (demultiplexing) an event based on boolean input G]"; //$NON-NLS-1$
	public static final String E_TABLE_CTRL_FB = "E_TABLE_CTRL"; //$NON-NLS-1$
	public static final String E_TABLE_CTRL_TREE_ITEM = "E_TABLE_CTRL [Support function block for E_TABLE]"; //$NON-NLS-1$
	public static final String FALSE_SMALL = "false"; //$NON-NLS-1$
	public static final String FBT_TEST_PROJECT1 = "FBTTestProject1"; //$NON-NLS-1$
	public static final String FBT_TEST_PROJECT2 = "FBTTestProject2"; //$NON-NLS-1$
	public static final String FBT_TEST_PROJECT3 = "FBTTestProject3"; //$NON-NLS-1$
	public static final String FBT_TEST_PROJECT4 = "FBTTestProject4"; //$NON-NLS-1$
	public static final String FB_TYPES = "FB Types"; //$NON-NLS-1$
	public static final String FILE = "File"; //$NON-NLS-1$
	public static final String FINISH = "Finish"; //$NON-NLS-1$
	public static final String FORBIDDEN_TYPE_NAME = "00_fbtype"; //$NON-NLS-1$
	public static final String FORDIAC_IDE_PROJECT = "4diac IDE Project..."; //$NON-NLS-1$
	public static final String FUNCTIONAL__BLOCKS = "Function Blocks"; //$NON-NLS-1$
	public static final String F_SUB = "F_SUB"; //$NON-NLS-1$
	public static final String GO_TO_CHILD = "Go To Child"; //$NON-NLS-1$
	public static final String GO_TO_PARENT = "Go To Parent"; //$NON-NLS-1$
	public static final String IF = "IF"; //$NON-NLS-1$
	public static final String INITIAL_APPLICATION_NAME_LABEL = "Initial application name"; //$NON-NLS-1$
	public static final String INITIAL_SYSTEM_NAME_LABEL = "Initial system name"; //$NON-NLS-1$
	public static final String INITIALIZE = "initialize"; //$NON-NLS-1$
	public static final String INSERT_FB = "Insert FB"; //$NON-NLS-1$
	public static final String INT_SMALL = "int"; //$NON-NLS-1$
	public static final String INIT = "INIT"; //$NON-NLS-1$
	public static final String INIT_SMALL = "Init"; //$NON-NLS-1$
	public static final String INTERFACE = "Interface"; //$NON-NLS-1$
	public static final String INTERNALCONSTVAR1 = "INTERNALCONSTVAR1"; //$NON-NLS-1$
	public static final String INTERNALCONSTVAR2 = "INTERNALCONSTVAR2"; //$NON-NLS-1$
	public static final String INTERNALCONSTVAR3 = "INTERNALCONSTVAR3"; //$NON-NLS-1$
	public static final String INTERNALFB1 = "InternalFB1"; //$NON-NLS-1$
	public static final String INTERNALFB2 = "InternalFB2"; //$NON-NLS-1$
	public static final String INTERNALFB3 = "InternalFB3"; //$NON-NLS-1$
	public static final String INTERNALVAR1 = "INTERNALVAR1"; //$NON-NLS-1$
	public static final String INTERNALVAR2 = "INTERNALVAR2"; //$NON-NLS-1$
	public static final String INTERNALVAR3 = "INTERNALVAR3"; //$NON-NLS-1$
	public static final String MOVE_ELEMENTS_UP = "Move element(s) up"; //$NON-NLS-1$
	public static final String MOVE_ELEMENTS_DOWN = "Move element(s) down"; //$NON-NLS-1$
	public static final String NAME = "Name"; //$NON-NLS-1$
	public static final String NAME_LABEL = "Name:"; //$NON-NLS-1$
	public static final String NAVIGATE = "Navigate"; //$NON-NLS-1$
	public static final String NEW = "New"; //$NON-NLS-1$
	public static final String NEW_4DIAC_PROJECT = "New 4diacProject"; //$NON-NLS-1$
	public static final String NEW_SUBAPPLICATION = "New subapplication"; //$NON-NLS-1$
	public static final String NEW_TYPE = FordiacMessages.NewType;
	public static final String OK = "OK"; //$NON-NLS-1$
	public static final String PACKAGE_NAME1 = "pkg1"; //$NON-NLS-1$
	public static final String PACKAGE_NAME2 = "pkg2"; //$NON-NLS-1$
	public static final String PACKAGE_NAME = PACKAGE_NAME1 + "::" + PACKAGE_NAME2; //$NON-NLS-1$
	public static final String PACKAGE_NAME_LABEL = FordiacMessages.Package + ":"; //$NON-NLS-1$
	public static final String PARENT_FOLDER_NAME_LABEL = "Enter or select the parent folder:"; //$NON-NLS-1$
	public static final String PROJECT_NAME = "UiTestProject"; //$NON-NLS-1$
	public static final String PROJECT_NAME1 = "UiTestProject1"; //$NON-NLS-1$
	public static final String PROJECT_NAME2 = "UiTestProject2"; //$NON-NLS-1$
	public static final String PROJECT_NAME3 = "UiTestProject3"; //$NON-NLS-1$
	public static final String PROJECT_NAME4 = "UiTestProject4"; //$NON-NLS-1$
	public static final String PROJECT_NAME5 = "UiTestProject5"; //$NON-NLS-1$
	public static final String PROJECT_NAME_APP = PROJECT_NAME + "App"; //$NON-NLS-1$
	public static final String PROJECT_NAME_TREE_ITEM = PROJECT_NAME + " []"; //$NON-NLS-1$
	public static final String PROJECT_NAME_LABEL = "Project name:"; //$NON-NLS-1$
	public static final String PROPERTIES_TITLE = "Properties"; //$NON-NLS-1$
	public static final String REFACTORING = "Refactoring"; //$NON-NLS-1$
	public static final String RENAME_ELEMENT = "Rename Element"; //$NON-NLS-1$
	public static final String REQ = "REQ"; //$NON-NLS-1$
	public static final String REQUEST_FROM_IDEAL_SOCKET = "Request from ideal Socket"; //$NON-NLS-1$
	public static final String SELECT_ALL = "Select All"; //$NON-NLS-1$
	public static final String SELECT_TYPE_LABEL = FordiacMessages.SelectType + ":"; //$NON-NLS-1$
	public static final String SOURCE = "Source"; //$NON-NLS-1$
	public static final String SUBAPP = "SubApp"; //$NON-NLS-1$
	public static final String START = "START"; //$NON-NLS-1$
	public static final String STATE = "State"; //$NON-NLS-1$
	public static final String STRUCT = "Struct"; //$NON-NLS-1$
	public static final String SYSTEM_EXPLORER_ID = "org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"; //$NON-NLS-1$
	public static final String SYSTEM_EXPLORER_LABEL = "System Explorer"; //$NON-NLS-1$
	public static final String TEMPLATEBASIC = "TemplateBasic"; //$NON-NLS-1$
	public static final String TEST_PARENT_FOLDER = "TestParentProject"; //$NON-NLS-1$
	public static final String TESTSTATE = "TestState"; //$NON-NLS-1$
	public static final String TEST_COMMENT = "Test Comment"; //$NON-NLS-1$
	public static final String TESTVAR = "TestVar"; //$NON-NLS-1$
	public static final String TOOLBAR_BUTTON_ZOOM_FIT_PAGE = "Zoom fit Page"; //$NON-NLS-1$
	public static final String TRANSITION = "Transition"; //$NON-NLS-1$
	public static final String TRUE = "TRUE"; //$NON-NLS-1$
	public static final String TRUE_SMALL = "true"; //$NON-NLS-1$
	public static final String TYPE_LABEL = "Type:"; //$NON-NLS-1$
	public static final String TYPE_LIBRARY_NODE = "Type Library"; //$NON-NLS-1$
	public static final String TYPE_NAME_LABEL = FordiacMessages.TypeName + ":"; //$NON-NLS-1$
	public static final String TYPE_PROJECT = "Type..."; //$NON-NLS-1$
	public static final String TYPE_SELECTION = "Type Selection"; //$NON-NLS-1$
	public static final String VAR1 = "VAR1"; //$NON-NLS-1$
	public static final String VAR2 = "VAR2"; //$NON-NLS-1$
	public static final String VAR3 = "VAR3"; //$NON-NLS-1$
	public static final String VAR_INOUT = "Var InOuts"; //$NON-NLS-1$
	public static final String VAR_INTERNALS = "Var Internals"; //$NON-NLS-1$
	public static final String VAR_IN_AND_OUTPUTS = "Var In- & Outputs"; //$NON-NLS-1$
	public static final String WITH = "With"; //$NON-NLS-1$

}
