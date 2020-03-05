/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The Class DiagramPreferences.
 */
public class DiagramPreferences extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/** The Constant CONNECTION_ROUTER. */
	public static final String CONNECTION_ROUTER = "ConnectionRouter"; //$NON-NLS-1$

	/** The Constant SELECTION_COLOR. */
	public static final String SELECTION_COLOR = "SelectionColor"; //$NON-NLS-1$

	/** The Constant CORNER_DIM. */
	public static final String CORNER_DIM = "CornerDim"; //$NON-NLS-1$

	public static final String GRID_SPACING = "GridSpacing"; //$NON-NLS-1$

	public static final String SNAP_TO_GRID = "SnapToGrid"; //$NON-NLS-1$

	public static final String SHOW_GRID = "ShowGrid"; //$NON-NLS-1$

	public static final String SHOW_RULERS = "ShowRulers"; //$NON-NLS-1$

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
		Group group = new Group(getFieldEditorParent(), SWT.NONE);
		group.setText(Messages.DiagramPreferences_FieldEditors_RulerAndGrid);

		GridLayout gridLayout = new GridLayout(2, false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		// Add the fields to the group
		BooleanFieldEditor showRulers = new BooleanFieldEditor(SHOW_RULERS,
				Messages.DiagramPreferences_FieldEditors_ShowRuler, group);
		addField(showRulers);

		BooleanFieldEditor showGrid = new BooleanFieldEditor(SHOW_GRID,
				Messages.DiagramPreferences_FieldEditors_ShowGrid, group);
		addField(showGrid);

		BooleanFieldEditor snapToGrid = new BooleanFieldEditor(SNAP_TO_GRID,
				Messages.DiagramPreferences_FieldEditors_SnapToGrid, group);
		addField(snapToGrid);

		IntegerFieldEditor gridSpacing = new IntegerFieldEditor(GRID_SPACING,
				Messages.DiagramPreferences_FieldEditors_GridSpacingInPixels, group);
		gridSpacing.setTextLimit(10);
		addField(gridSpacing);

		group.setLayoutData(gridData);
		group.setLayout(gridLayout);

		// Create a Group to hold the connection router fields
		Group router = new Group(getFieldEditorParent(), SWT.NONE);
		router.setText(Messages.DiagramPreferences_ConnectionRouter);
		GridLayout routerLayout = new GridLayout(2, false);

		Map<String, IConnectionRouterFactory> connectionRouter = new HashMap<>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(Activator.PLUGIN_ID,
				"ConnectionRouterProvider"); //$NON-NLS-1$
		for (int i = 0; i < elems.length; i++) {
			IConfigurationElement element = elems[i];
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				String name = element.getAttribute("name"); //$NON-NLS-1$
				if (object instanceof IConnectionRouterFactory) {
					IConnectionRouterFactory routerFactory = (IConnectionRouterFactory) object;
					connectionRouter.put(name, routerFactory);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("Error loading ConnectionRouter", corex); //$NON-NLS-1$
			}
		}

		Set<String> keySet = connectionRouter.keySet();
		String[][] nameArray = new String[keySet.size()][2];
		int i = 0;
		for (String key : keySet) {
			nameArray[i][0] = key;
			nameArray[i][1] = key;
			i++;
		}

		ComboFieldEditor routerEditor = new ComboFieldEditor(CONNECTION_ROUTER,
				Messages.DiagramPreferences_DefaultRouter, nameArray, router);
		addField(routerEditor);

		GridData routerData = new GridData(GridData.FILL_HORIZONTAL);
		routerData.grabExcessHorizontalSpace = true;
		routerData.horizontalSpan = 2;

		router.setLayoutData(routerData);
		router.setLayout(routerLayout);

		// Create a Group to hold the connection router fields
		Group color = new Group(getFieldEditorParent(), SWT.NONE);
		color.setText(Messages.DiagramPreferences_Colors);
		GridLayout colorLayout = new GridLayout(2, false);

		ColorFieldEditor selectionBorderColor = new ColorFieldEditor(SELECTION_COLOR,
				Messages.DiagramPreferences_SelectionColor, color);

		addField(selectionBorderColor);
		color.setLayout(colorLayout);
		color.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create a Group to specify the arc of the figure edges
		Group arc = new Group(getFieldEditorParent(), SWT.NONE);
		arc.setText(Messages.DiagramPreferences_FB);
		GridLayout arcLayout = new GridLayout(2, false);

		IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(CORNER_DIM,
				Messages.DiagramPreferences_CornerDimension, arc);
		integerFieldEditor.setValidRange(0, 15);
		addField(integerFieldEditor);

		createMaxValueSizeField(getFieldEditorParent());

		arc.setLayout(arcLayout);
		arc.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void createMaxValueSizeField(Composite parent) {
		IntegerFieldEditor integerFieldEditor = new IntegerFieldEditor(MAX_VALUE_LABEL_SIZE,
				Messages.DiagramPreferences_MaximumValueLabelSize, parent);
		integerFieldEditor.setValidRange(0, 120);
		addField(integerFieldEditor);
	}

	@Override
	public void init(final IWorkbench workbench) {
		// nothing to do here
	}

}