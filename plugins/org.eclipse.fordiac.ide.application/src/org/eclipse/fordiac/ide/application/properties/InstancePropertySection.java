/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - multline comments and cleanup
 *   Sebastian Hollersbacher - change to nebula NatTable
 *   Hesam Rezaee - Variable configuration for Global Constants
 *   Martin Jobst - add initial value cell editor support
 *   Dario Romano - fixed renaming bug for instances
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnEditableRule;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InstancePropertySection extends AbstractSection {

	private static final int ONE_COLUMN = 1;
	protected static final int TWO_COLUMNS = 2;

	private Text nameText;
	private Text commentText;

	private NatTable inputTable;
	private NatTable outputTable;

	private IChangeableRowDataProvider<VarDeclaration> inputDataProvider;
	private IChangeableRowDataProvider<VarDeclaration> outputDataProvider;

	IAction[] defaultCopyPasteCut = new IAction[3];
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	protected Composite leftComposite;
	protected Composite rightComposite;
	protected Composite upperComposite;
	protected Composite lowerComposite;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createSubsectionLayout(parent);
	}

	protected void createSubsectionLayout(final Composite parent) {
		createSingleRowLayout(parent);
		createFBInfoGroup(upperComposite);
		createTableSection(lowerComposite);
	}

	protected void createDoubleColumnLayout(final Composite parent) {
		parent.setLayout(new GridLayout(2, true));
		leftComposite = createComposite(parent);
		rightComposite = createComposite(parent);
		final GridData gridLayoutData = new GridData(GridData.FILL, GridData.FILL, true, false);
		parent.setLayoutData(gridLayoutData);
	}

	protected void createSingleRowLayout(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		upperComposite = createComposite(parent);
		// the upper composite should not grab vertical space as it has always the same
		// size and give the rest to the tables below
		upperComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		lowerComposite = createComposite(parent);
		final GridData gridLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		parent.setLayoutData(gridLayoutData);
	}

	@Override
	protected void performRefresh() {
		if (!nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			outputTable.refresh();
			inputTable.refresh();
		}
	}

	@Override
	protected void performRefreshAnnotations() {
		inputTable.refresh(false);
		outputTable.refresh(false);
	}

	protected void createTableSection(final Composite parent) {
		final Composite tableSectionComposite = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(tableSectionComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableSectionComposite);

		final Group inputComposite = getWidgetFactory().createGroup(tableSectionComposite,
				Messages.CommentPropertySection_DataInputs);
		final Group outputComposite = getWidgetFactory().createGroup(tableSectionComposite,
				Messages.CommentPropertySection_DataOutputs);

		inputComposite.setText(Messages.CommentPropertySection_DataInputs);
		outputComposite.setText(Messages.CommentPropertySection_DataOutputs);

		inputComposite.setLayout(new GridLayout(ONE_COLUMN, false));
		outputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		inputDataProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		outputDataProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));

		final DataLayer inputDataLayer = new VarDeclarationDataLayer(inputDataProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		final DataLayer outputDataLayer = new VarDeclarationDataLayer(outputDataProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);

		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(inputDataProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));
		outputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(outputDataProvider,
				this::getAnnotationModel, VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG));

		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG);
		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer, columnProvider,
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE,
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG,
						VarDeclarationTableColumn.DEFAULT_EDITABLE));
		outputTable = NatTableWidgetFactory.createNatTable(outputComposite, outputDataLayer, columnProvider,
				new NatTableColumnEditableRule<>(IEditableRule.ALWAYS_EDITABLE,
						VarDeclarationTableColumn.DEFAULT_COLUMNS_WITH_VISIBLE_AND_VAR_CONFIG,
						VarDeclarationTableColumn.DEFAULT_EDITABLE_NO_INITIAL_VALUE));

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());

		inputTable.addConfiguration(new InitialValueEditorConfiguration(inputDataProvider));
		outputTable.addConfiguration(new InitialValueEditorConfiguration(outputDataProvider));

		inputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));
		outputTable.addConfiguration(
				new DefaultImportCopyPasteLayerConfiguration(columnProvider, this::getCurrentCommandStack));

		inputTable.configure();
		outputTable.configure();

		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(outputComposite);

		tableSectionComposite.layout();
	}

	protected void createFBInfoGroup(final Composite parent) {
		final Composite fbInfoGroup = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbInfoGroup);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbInfoGroup);

		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(fbInfoGroup, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
			addContentAdapter();
		});

		final CLabel commentLabel = getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(commentLabel);

		commentText = createGroupText(fbInfoGroup, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false)
				.hint(SWT.DEFAULT, 3 * commentText.getLineHeight()).applyTo(commentText);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			final Command cmd = createChangeCommentCommand();
			executeCommand(cmd);
			addContentAdapter();
		});
	}

	protected Command createChangeCommentCommand() {
		Command cmd = new ChangeCommentCommand(getType(), commentText.getText());
		if (EditorUtils.getGraphicalViewerFromCurrentActiveEditor() != null && getType() instanceof SubApp) {
			final EditPart editPart = EditorUtils.getGraphicalViewerFromCurrentActiveEditor()
					.getEditPartForModel(getType());
			if (editPart instanceof final SubAppForFBNetworkEditPart subAppforFBNetworkEditPart
					&& subAppforFBNetworkEditPart.getContentEP() != null) {
				cmd = new ResizeGroupOrSubappCommand(subAppforFBNetworkEditPart.getContentEP(), cmd);
			}
		}
		return cmd;
	}

	@Override
	public void aboutToBeShown() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = getActionBars();
		if (bars != null) {
			defaultCopyPasteCut[0] = bars.getGlobalActionHandler(ActionFactory.COPY.getId());
			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), null);
			defaultCopyPasteCut[1] = bars.getGlobalActionHandler(ActionFactory.PASTE.getId());
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), null);
			defaultCopyPasteCut[2] = bars.getGlobalActionHandler(ActionFactory.CUT.getId());
			bars.setGlobalActionHandler(ActionFactory.CUT.getId(), null);
			bars.updateActionBars();
		}

		super.aboutToBeShown();
	}

	@Override
	public void aboutToBeHidden() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = getActionBars();
		if (bars != null) {
			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), defaultCopyPasteCut[0]);
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), defaultCopyPasteCut[1]);
			bars.setGlobalActionHandler(ActionFactory.CUT.getId(), defaultCopyPasteCut[2]);
			bars.updateActionBars();
		}

		super.aboutToBeHidden();
	}

	protected IActionBars getActionBars() {
		if (tabbedPropertySheetPage != null && tabbedPropertySheetPage.getSite() != null) {
			return tabbedPropertySheetPage.getSite().getActionBars();
		}
		return null;
	}

	@Override
	protected Object getInputType(final Object input) {
		return InstanceSectionFilter.getFBNetworkElementFromSelectedElement(input);
	}

	@Override
	protected FBNetworkElement getType() {
		if (type instanceof final FBNetworkElement fbNetworkElement) {
			return fbNetworkElement;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// Nothing for now
	}

	@Override
	protected void setInputInit() {
		if (getType() != null) {
			inputDataProvider.setInput(getType().getInterface().getInputVars());
			outputDataProvider.setInput(getType().getInterface().getOutputVars());
		}
		inputTable.refresh();
		outputTable.refresh();
	}

	protected final Adapter interfaceAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	protected final Adapter fbnElementAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected void addContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().add(fbnElementAdapter);
			getType().getInterface().eAdapters().add(interfaceAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		// for performance reasons (we could have many children) do not call super here.
		if (getType() != null) {
			getType().eAdapters().remove(fbnElementAdapter);
			getType().getInterface().eAdapters().remove(interfaceAdapter);
		}
	}

	@Override
	protected boolean shouldRefresh() {
		// as we have our own adapters we need our own shouldRefresh implementation
		return (null != getType()) && getType().eAdapters().contains(fbnElementAdapter) && !blockRefresh;
	}
}
