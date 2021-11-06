/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added preference driven max width for value edit parts
 *   Lisa Sonnleithner - deleted setting for corner dimensions, replaced with constant
 *   				   - moved max label size into a group
 *   				   - moved creating a group into a method
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.router.IConnectionRouterFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The Class DiagramPreferences.
 */
public class DiagramPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/** The Constant CONNECTION_ROUTER. */
	public static final String CONNECTION_ROUTER = "ConnectionRouter"; //$NON-NLS-1$

	/** The Constant CORNER_DIM. */
	public static final int CORNER_DIM = 6;
	public static final int CORNER_DIM_HALF = CORNER_DIM / 2;

	public static final String GRID_SPACING = "GridSpacing"; //$NON-NLS-1$

	public static final String SNAP_TO_GRID = "SnapToGrid"; //$NON-NLS-1$

	public static final String SHOW_GRID = "ShowGrid"; //$NON-NLS-1$

	public static final String SHOW_RULERS = "ShowRulers"; //$NON-NLS-1$

	public static final String SHOW_COMMENT_AT_PIN = "ShowCommentAtPin"; //$NON-NLS-1$

	public static final String MAX_VALUE_LABEL_SIZE = "MaxValueLabelSize"; //$NON-NLS-1$

	/**
	 * Instantiates a new diagram preferences.
	 */
	public DiagramPreferences() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.DiagramPreferences_GeneralDiagramPreferences);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	public void createFieldEditors() {

		// Create a Group to hold the ruler fields
		createGroupRulerGrid();

		// Create a Group to hold the connection router fields
		createGroupRouter();

		// Create a Group to hold label size field
		createGroupLabelSize();

		// Create a Group to hold the interface pin field
		createGroupInterfacePins();
	}

	private Group createGroup(final String title) {
		final Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText(title);
		return group;
	}

	private static void configGroup(final Group group) {
		final GridLayout gridLayout = new GridLayout(2, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;

		gridData.horizontalSpan = 2;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);
	}

	private void createGroupLabelSize() {
		final Group labelSize = createGroup(Messages.DiagramPreferences_LabelSize);
		final IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(MAX_VALUE_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumValueLabelSize, labelSize);
		integerFieldEditor.setValidRange(0, 120);
		addField(integerFieldEditor);
		configGroup(labelSize);
	}

	private void createGroupRulerGrid() {
		final Group group = createGroup(Messages.DiagramPreferences_FieldEditors_RulerAndGrid);
		// Add the fields to the group
		final BooleanFieldEditor showRulers = new BooleanFieldEditor(SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, group);
		addField(showRulers);

		final BooleanFieldEditor showGrid = new BooleanFieldEditor(SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, group);
		addField(showGrid);

		final BooleanFieldEditor snapToGrid = new BooleanFieldEditor(SNAP_TO_GRID,
				Messages.DiagramPreferences_FieldEditors_SnapToGrid, group);
		addField(snapToGrid);

		final IntegerFieldEditor gridSpacing = new IntegerFieldEditor(GRID_SPACING,
				Messages.DiagramPreferences_FieldEditors_GridSpacingInPixels, group);
		gridSpacing.setTextLimit(10);
		addField(gridSpacing);
		configGroup(group);
	}

	private void createGroupRouter() {
		final Group router = createGroup(Messages.DiagramPreferences_ConnectionRouter);

		final Map<String, IConnectionRouterFactory> connectionRouter = new HashMap<>();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID,
				"ConnectionRouterProvider"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				final String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof IConnectionRouterFactory) {
					final IConnectionRouterFactory routerFactory = (IConnectionRouterFactory) object;
					connectionRouter.put(name, routerFactory);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError("Error loading ConnectionRouter", corex); //$NON-NLS-1$
			}
		}

		final Set<String> keySet = connectionRouter.keySet();
		final String[][] nameArray = new String[keySet.size()][2];
		int i = 0;
		for (final String key : keySet) {
			nameArray[i][0] = key;
			nameArray[i][1] = key;
			i++;
		}

		final ComboFieldEditor routerEditor = new ComboFieldEditor(CONNECTION_ROUTER,
				Messages.DiagramPreferences_DefaultRouter, nameArray, router);
		addField(routerEditor);
		configGroup(router);
	}

	private void createGroupInterfacePins() {
		final Group group = createGroup(Messages.DiagramPreferences_InterfacePins);
		final BooleanFieldEditor showInput = new BooleanFieldEditor(SHOW_COMMENT_AT_PIN,
				Messages.DiagramPreferences_InterfacePins_ShowInputComment, group);
		addField(showInput);
		configGroup(group);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do here
	}

}