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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.FBTypeEditDomain;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestDataLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.gef.FordiacContextMenuProvider;
import org.eclipse.fordiac.ide.gef.editparts.ZoomScalableFreeformRootEditPart;
import org.eclipse.fordiac.ide.gef.ruler.FordiacRulerComposite;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.rulers.RulerComposite;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class FBTester extends GraphicalEditor implements IFBTEditorPart {
	public static final String EXPECTED_EVENTS = "ExpectedEvents"; //$NON-NLS-1$
	public static final String EVENT = "Event"; //$NON-NLS-1$
	public static final String NAME = "Name"; //$NON-NLS-1$
	public static final String COLUMN_TYPE = "ColumnType"; //$NON-NLS-1$
	public static final String INPUT_VARIABLE = "InputVariable"; //$NON-NLS-1$
	public static final String OUTPUT_VARIABLE = "OutputVariable"; //$NON-NLS-1$
	private FBType type;
	private String path;
	private KeyHandler sharedKeyHandler;
	private StackLayout stack;
	private TypeLibrary typeLib;
	private final Map<String, IFBTestConfiguration> configurations = new HashMap<>();
	private Composite configurationParent;
	private TableViewer testDataViewer;
	private CommandStack commandStack;
	private final Map<String, TableColumn> dataColumns = new HashMap<>();
	private final List<TestData> testDataCollection = new ArrayList<>();

	private final Adapter eContentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			testDataViewer.refresh();
		}
	};

	public FBTester() {
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
		final SashForm s = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);

		final Composite interfaceEditing = new Composite(s, SWT.BORDER);
		interfaceEditing.setLayout(new GridLayout(2, false));

		final Label testConfiguraionLabel = new Label(interfaceEditing, SWT.NONE);
		testConfiguraionLabel.setText(Messages.FBTester_TestConfiguration);

		final CCombo configurationCombo = ComboBoxWidgetFactory.createCombo(interfaceEditing);
		configurationCombo.setItems(getTestConfigurations().toArray(new String[0]));
		final GridData configurationComboData = new GridData(SWT.FILL, SWT.TOP, false, false);
		configurationCombo.setLayoutData(configurationComboData);
		configurationCombo.addListener(SWT.Selection, event -> {
			final IFBTestConfiguration testConfiguration = configurations.get(configurationCombo.getText());
			stack.topControl = testConfiguration.getControl();
			configurationParent.layout();
		});

		final GridData configurationParentData = new GridData(GridData.FILL, GridData.FILL, true, true);
		configurationParentData.horizontalSpan = 2;
		configurationParent = new Group(interfaceEditing, SWT.NONE);
		configurationParent.setLayout(stack = new StackLayout());
		configurationParent.setLayoutData(configurationParentData);
		createTestConfigurations(configurationParent);

		final SashForm horizontal = new SashForm(s, SWT.HORIZONTAL | SWT.SMOOTH);

		final Composite testData = new Composite(horizontal, SWT.NONE);
		testData.setLayout(new GridLayout(1, true));
		createTestData(testData);

		final Composite graphicalEditor = new Composite(horizontal, SWT.NONE);
		graphicalEditor.setLayout(new FillLayout());
		s.setWeights(30, 70);
		horizontal.setWeights(50, 50);
		super.createPartControl(graphicalEditor);
	}

	private void createTestData(final Composite parent) {
		createTestToolbar(parent);
		testDataViewer = TableWidgetFactory.createTableViewer(parent, SWT.CHECK);

		testDataViewer.addDoubleClickListener(event -> {
			if (event.getSelection() instanceof IStructuredSelection) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.getFirstElement() instanceof TestData) {
					final TestData data = (TestData) selection.getFirstElement();
					for (final Entry<String, TestElement> entry : TestingManager.getInstance().getTestElements(type)
							.entrySet()) {
						final String value = data.getValueFor(entry.getValue().getPortString());
						if (null != value) {
							entry.getValue().setValue(value);
						}
					}
					TestElement eventToTrigger = null;
					for (final Entry<String, TestElement> entry : TestingManager.getInstance().getTriggerElements(type)
							.entrySet()) {
						if (entry.getValue().getInterfaceElement().getName().equals(data.getEvent().getName())) {
							eventToTrigger = entry.getValue();
						}
					}
					if (null != eventToTrigger) {
						eventToTrigger.sendEvent();
					}
				}
			}
		});
		configureTableLayout();
		if (null != type && null != type.getInterfaceList()) {
			createInputVariableColumns(testDataViewer, type.getInterfaceList().getInputVars());
			createOutputVariableColumns(testDataViewer, type.getInterfaceList().getOutputVars());
		}

		parseTestData();
		testDataViewer.setContentProvider(new IStructuredContentProvider() {
			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
				// nothing to do here
			}

			@Override
			public void dispose() {
				// nothing to do here
			}

			@Override
			public Object[] getElements(final Object inputElement) {
				return testDataCollection.toArray();
			}
		});
		testDataViewer.setLabelProvider(new TestDataLabelProvider(type, testDataViewer.getTable()));
		testDataViewer.setInput(new Object());

		final MenuManager popupMenu = new MenuManager();
		final IAction newRowAction = new NewTestDataAction();
		popupMenu.add(newRowAction);
		final IAction saveAction = new SaveTestDataAction();
		popupMenu.add(saveAction);
		final Menu menu = popupMenu.createContextMenu(testDataViewer.getTable());
		testDataViewer.getTable().setMenu(menu);
	}

	private void createTestToolbar(final Composite parent) {
		final ToolBar tb = new ToolBar(parent, SWT.FLAT | SWT.HORIZONTAL);
		final ToolItem runItem = new ToolItem(tb, SWT.NONE);
		runItem.setToolTipText(Messages.FBTester_RunselectedTests);
		runItem.setImage(FordiacImage.ICON_RUN_DEBUG.getImage());
		final ToolItem stopItem = new ToolItem(tb, SWT.NONE);
		stopItem.setToolTipText(Messages.FBTester_Stop);
		stopItem.setImage(FordiacImage.ICON_STOP.getImage());
		final ToolItem saveItem = new ToolItem(tb, SWT.NONE);
		saveItem.setToolTipText(Messages.FBTester_SaveTestData);
		saveItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ETOOL_SAVE_EDIT));
		saveItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				saveTestData();
			}
		});
		final ToolItem addItem = new ToolItem(tb, SWT.NONE);
		addItem.setToolTipText(Messages.FBTester_AddTest);
		addItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		addItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				addTest();
			}
		});
		final ToolItem deleteItem = new ToolItem(tb, SWT.NONE);
		deleteItem.setToolTipText(Messages.FBTester_DeleteTest);
		deleteItem.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
		deleteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent event) {
				final Object selection = ((StructuredSelection) testDataViewer.getSelection()).getFirstElement();
				testDataCollection.remove(selection);
				testDataViewer.refresh();
			}
		});
	}

	private void configureTableLayout() {
		final TableViewerColumn nameColumn = new TableViewerColumn(testDataViewer, SWT.LEFT);
		nameColumn.getColumn().setWidth(80);
		nameColumn.getColumn().setText(NAME);
		nameColumn.getColumn().setData(COLUMN_TYPE, NAME);
		nameColumn.setEditingSupport(new TestDataEditingSupport(testDataViewer, nameColumn.getColumn()));
		final TableViewerColumn eventCol = new TableViewerColumn(testDataViewer, SWT.LEFT);
		eventCol.getColumn().setWidth(80);
		eventCol.getColumn().setText(EVENT);
		eventCol.getColumn().setData(COLUMN_TYPE, EVENT);
		final TableViewerColumn expectedEvents = new TableViewerColumn(testDataViewer, SWT.LEFT);
		expectedEvents.getColumn().setWidth(100);
		expectedEvents.getColumn().setText(Messages.FBTester_ExpectedEvents);
		expectedEvents.getColumn().setData(COLUMN_TYPE, EXPECTED_EVENTS);
	}

	private void saveTestData() {
		if (null != path) {
			final File testDataFile = new File(path + File.separatorChar + type.getName() + ".forte"); //$NON-NLS-1$
			try (FileOutputStream outStream = new FileOutputStream(testDataFile);
					DataOutputStream dataStream = new DataOutputStream(outStream);
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dataStream));) {

				for (final TestData testData : testDataCollection) {
					writer.write(testData._getLine());
					writer.newLine();
				}
				writer.flush();
			} catch (final Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	private void parseTestData() {
		if (null != path) {
			final File testDataFile = new File(path + File.separatorChar + type.getName() + ".forte"); //$NON-NLS-1$
			if (testDataFile.exists()) {
				try (FileInputStream fstream = new FileInputStream(testDataFile);
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader data = new BufferedReader(new InputStreamReader(in));) {
					String line;
					while (null != (line = data.readLine())) {
						final TestData testData = createTestData(line);
						testDataCollection.add(testData);
					}
				} catch (final Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}
	}

	private TestData createTestData(final String line) {
		final TestData testData = TestdataFactory.eINSTANCE.createTestData();
		testData.eAdapters().add(eContentAdapter);
		testData.setType(type);
		testData.setLine(line);
		return testData;
	}

	private void createInputVariableColumns(final TableViewer testData, final List<VarDeclaration> list) {
		for (final VarDeclaration input : list) {
			final TableViewerColumn column = new TableViewerColumn(testData, SWT.LEFT);
			column.getColumn().setWidth(40);
			column.getColumn().setText(input.getName());
			column.getColumn().setData(COLUMN_TYPE, INPUT_VARIABLE);
			column.getColumn().setData(INPUT_VARIABLE, input);
			column.setEditingSupport(new TestDataEditingSupport(testDataViewer, column.getColumn()));
			dataColumns.put(input.getName(), column.getColumn());
		}
	}

	private void createOutputVariableColumns(final TableViewer testData, final List<VarDeclaration> list) {
		for (final VarDeclaration output : list) {
			final TableViewerColumn column = new TableViewerColumn(testData, SWT.LEFT);
			column.getColumn().setWidth(40);
			column.getColumn().setText(output.getName());
			column.getColumn().setData(COLUMN_TYPE, OUTPUT_VARIABLE);
			column.getColumn().setData(OUTPUT_VARIABLE, output);
			column.setEditingSupport(new TestDataEditingSupport(testDataViewer, column.getColumn()));
			dataColumns.put(output.getName(), column.getColumn());
		}
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
		final ZoomScalableFreeformRootEditPart root = new ZoomScalableFreeformRootEditPart(getSite(), getActionRegistry());
		viewer.setRootEditPart(root);
		viewer.setEditPartFactory(getEditpartFactory());
		// configure the context menu provider
		final ContextMenuProvider cmProvider = new FordiacContextMenuProvider(viewer, root.getZoomManager(),
				getActionRegistry()) {
			@Override
			public void buildContextMenu(final IMenuManager menu) {
				super.buildContextMenu(menu);

				final IAction action = getRegistry().getAction(ActionFactory.DELETE.getId());
				menu.appendToGroup(GEFActionConstants.GROUP_COPY, action);
			}
		};
		viewer.setContextMenu(cmProvider);
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.MOD1), MouseWheelZoomHandler.SINGLETON);
		final KeyHandler viewerKeyHandler = new GraphicalViewerKeyHandler(viewer).setParent(getCommonKeyHandler());
		viewer.setKeyHandler(viewerKeyHandler);
	}

	public ZoomManager getZoomManger() {
		return ((ScalableFreeformRootEditPart) (getGraphicalViewer().getRootEditPart())).getZoomManager();
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

	@Override
	protected void createGraphicalViewer(final Composite parent) {
		final RulerComposite rulerComp = new FordiacRulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
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
				} catch (final Exception e) {
					// nothing to do
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
						((IFBTestConfiguratonCreator) obj).setType(type);

						final IFBTestConfiguration configuration = ((IFBTestConfiguratonCreator) obj)
								.createConfigurationPage(parent);
						final String lang = element.getAttribute("label"); //$NON-NLS-1$
						configurations.put(lang, configuration);
					}
				} catch (final Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}
	}

	void addTest() {
		final StringBuilder dataLine = new StringBuilder("Testname;"); //$NON-NLS-1$
		dataLine.append(type.getName());
		dataLine.append(";"); //$NON-NLS-1$
		if (!type.getInterfaceList().getEventInputs().isEmpty()) {
			final Event event = type.getInterfaceList().getEventInputs().get(0);
			dataLine.append(event.getWith().size()).append(";");//$NON-NLS-1$ // nr of inputs are the number of
			// connected withs
			for (final With with : event.getWith()) {
				final VarDeclaration var = with.getVariables();
				dataLine.append(var.getName()).append(";val;"); //$NON-NLS-1$
			}
			dataLine.append(event.getName()).append(";"); //$NON-NLS-1$
		}
		if (!type.getInterfaceList().getEventOutputs().isEmpty()) {
			dataLine.append("1;"); //$NON-NLS-1$
			dataLine.append(type.getInterfaceList().getEventOutputs().get(0).getName());
			dataLine.append(";"); //$NON-NLS-1$
		} else {
			dataLine.append("0;"); //$NON-NLS-1$
		}
		if (!type.getInterfaceList().getOutputVars().isEmpty()) {
			dataLine.append(type.getInterfaceList().getOutputVars().size());
			dataLine.append(";"); //$NON-NLS-1$
			for (final VarDeclaration var : type.getInterfaceList().getOutputVars()) {
				dataLine.append(var.getName()).append(";val;"); //$NON-NLS-1$
			}
		}
		final TestData testData = createTestData(dataLine.toString());
		testDataCollection.add(testData);
		testDataViewer.refresh();
	}

	private class SaveTestDataAction extends Action {
		public SaveTestDataAction() {
			super(Messages.FBTester_SaveTestData);
		}

		@Override
		public void run() {
			saveTestData();
		}
	}

	private class NewTestDataAction extends Action {
		public NewTestDataAction() {
			super(Messages.FBTester_NewTest);
			setImageDescriptor(
					PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		}

		@Override
		public void run() {
			addTest();
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

}
