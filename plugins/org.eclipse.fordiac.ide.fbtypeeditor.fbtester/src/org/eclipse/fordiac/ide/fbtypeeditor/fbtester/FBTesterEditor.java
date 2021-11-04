/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial implementation
 *   Bianca Wiesmayr
 *    - clean up to extract TableViewer creation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.gef.figures.MinSpaceFreeformFigure;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.GridLayer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;

public class FBTesterEditor extends GraphicalEditor implements IFBTEditorPart {
	public static final String EXPECTED_EVENTS = "ExpectedEvents"; //$NON-NLS-1$
	public static final String EVENT = "Event"; //$NON-NLS-1$
	public static final String NAME = "Name"; //$NON-NLS-1$
	public static final String COLUMN_TYPE = "ColumnType"; //$NON-NLS-1$
	public static final String INPUT_VARIABLE = "InputVariable"; //$NON-NLS-1$
	public static final String OUTPUT_VARIABLE = "OutputVariable"; //$NON-NLS-1$
	private FBType type;
	private KeyHandler sharedKeyHandler;
	private TypeLibrary typeLib;
	private final Map<String, IFBTestConfiguration> configurations = new HashMap<>();
	private Composite configurationParent;
	private CommandStack commandStack;

	public FBTesterEditor() {
		// empty constructor
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// not used
	}

	@Override
	public void doSaveAs() {
		// not used
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInput(input);
		if (input instanceof FBTypeEditorInput) {
			final FBTypeEditorInput untypedInput = (FBTypeEditorInput) input;
			type = untypedInput.getContent();
			typeLib = type.getTypeLibrary();
		}
		setSite(site);
		setEditDomain(new FBTypeEditDomain(this, commandStack));
		setPartName(Messages.FBTester_FBTester);
		setTitleImage(FordiacImage.ICON_FB_TESTER.getImage());
		super.init(site, input);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		final SashForm topEditorContents = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);

		final Composite testConfigSelection = new Composite(topEditorContents, SWT.BORDER);
		testConfigSelection.setLayout(new GridLayout(2, false));
		final Label testConfigurationLabel = new Label(testConfigSelection, SWT.NONE);
		testConfigurationLabel.setText(Messages.FBTester_TestConfiguration);
		final CCombo configurationCombo = ComboBoxWidgetFactory.createCombo(testConfigSelection);
		configurationCombo.setItems(getTestConfigurations().toArray(new String[0]));

		final GridData configurationComboData = new GridData(SWT.FILL, SWT.TOP, false, false);
		configurationCombo.setLayoutData(configurationComboData);

		final StackLayout configPageLayout = new StackLayout();

		configurationCombo.addListener(SWT.Selection, event -> {
			final IFBTestConfiguration configPage = configurations.get(configurationCombo.getText());
			configPageLayout.topControl = configPage.getControl();
			configurationParent.layout();
		});

		final GridData configurationParentData = new GridData(GridData.FILL, GridData.FILL, true, true);
		configurationParentData.horizontalSpan = 2;
		configurationParent = new Group(testConfigSelection, SWT.NONE);
		configurationParent.setLayout(configPageLayout);
		configurationParent.setLayoutData(configurationParentData);
		createTestConfigurations(configurationParent);
		preselectFirstConfiguration(configurationCombo, configPageLayout);

		final SashForm horizontal = new SashForm(topEditorContents, SWT.HORIZONTAL | SWT.SMOOTH);
		final Composite graphicalEditor = new Composite(horizontal, SWT.NONE);
		graphicalEditor.setLayout(new FillLayout());
		super.createPartControl(graphicalEditor);
	}

	private void preselectFirstConfiguration(final CCombo configurationCombo, final StackLayout stack) {
		if (configurationCombo.getItems().length > 0) {
			configurationCombo.select(0);
		}
		final IFBTestConfiguration testConfiguration = configurations.get(configurationCombo.getText());
		stack.topControl = testConfiguration.getControl();
		configurationParent.layout();
	}

	@Override
	public void setFocus() {
		// nothing to be done here
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// nothing to be done here
	}

	@Override
	protected void initializeGraphicalViewer() {
		final GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(getEditorInput());
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		final ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		final ZoomScalableFreeformRootEditPart root = createRootEditPart();
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		// configure the context menu provider
		viewer.setContextMenu(new FordiacContextMenuProvider(viewer, root.getZoomManager(), getActionRegistry()));
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		final KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);
	}

	private ZoomScalableFreeformRootEditPart createRootEditPart() {
		return new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry()) {
			@Override
			protected AbstractFreeformFigure createDrawingAreaContainer() {
				return new MinSpaceFreeformFigure();
			}

			@Override
			protected IFigure createFigure() {
				final IFigure rootFigure = super.createFigure();
				final GridLayer grid = (GridLayer) getLayer(GRID_LAYER);
				if (grid != null) {
					// it does not make sense to have a grid in the interface layer so hide it
					grid.setVisible(false);
				}
				return rootFigure;
			}

			@Override
			protected void refreshGridLayer() {
				// empty to be sure that grid will not be drawn
			}
		};
	}

	public ZoomManager getZoomManger() {
		return ((ScalableFreeformRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
	}

	protected FBInterfaceEditPartFactory getEditpartFactory() {
		return new FBInterfaceEditPartFactory(this, typeLib);
	}

	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			sharedKeyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
					getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
			sharedKeyHandler.put(/* CTRL + '=' */
					KeyStroke.getPressed('+', 0x3d, SWT.CTRL),
					getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		}
		return sharedKeyHandler;
	}

	public static List<String> getTestConfigurations() {
		final ArrayList<String> testConfigurations = new ArrayList<>();
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry
				.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fbtester.fbTesterConfiguration"); //$NON-NLS-1$

		for (final IExtension extension : point.getExtensions()) {
			final IConfigurationElement[] elements = extension.getConfigurationElements();
			for (final IConfigurationElement element : elements) {
				try {
					final Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IFBTestConfiguratonCreator) {
						final String label = element.getAttribute("label"); //$NON-NLS-1$
						testConfigurations.add(label);
					}
				} catch (final CoreException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
		return testConfigurations;
	}

	public void createTestConfigurations(final Composite parent) {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IExtensionPoint point = registry
				.getExtensionPoint("org.eclipse.fordiac.ide.fbtypeeditor.fbtester.fbTesterConfiguration"); //$NON-NLS-1$

		for (final IExtension extension : point.getExtensions()) {
			final IConfigurationElement[] elements = extension.getConfigurationElements();

			for (final IConfigurationElement element : elements) {
				try {
					final Object obj = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IFBTestConfiguratonCreator) {
						final IFBTestConfiguratonCreator contributor = (IFBTestConfiguratonCreator) obj;
						contributor.setType(type);
						final IFBTestConfiguration configuration = contributor.createConfigurationPage(parent);
						final String lang = element.getAttribute("label"); //$NON-NLS-1$
						configurations.put(lang, configuration);
					}
				} catch (final Exception e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// For now we don't handle markers in this editor
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// For now we don't handle markers in this editor
		return false;
	}

	@Override
	public void reloadType(final FBType type) {
		this.type = type;
		final FBTypeEditorInput fbTypeEditorInput = (FBTypeEditorInput) getEditorInput();
		fbTypeEditorInput.setFbType(type);
		getGraphicalViewer().setContents(fbTypeEditorInput);
	}

	@Override
	public Object getSelectableEditPart() {
		// nothing todo here
		return null;
	}

}
