/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 * 				 2020 Johannes Kepler Universiy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted this class from TypeInfoSectionto make it usable in
 *                 other places (e.g., system editor)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.widgets;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.gef.provider.VersionContentProvider;
import org.eclipse.fordiac.ide.gef.provider.VersionLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeApplicationDomainCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAuthorCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeClassificationCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDateCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDescriptionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFunctionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeIdentifcationTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeOrganizationCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeRemarksCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStandardCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVersionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewVersionInfoCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteVersionInfoCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class TypeInfoWidget implements CommandExecutor {

	private static final int MAX_MINUS_T_MIN = 70;

	private static final int MAX_MINUS_MIN = 90;

	private static final int T_MIN = 30;

	private static final int D_MIN = 20;

	private static final int MAX = 100;

	private static final int MIN = 10;

	private final FormToolkit widgetFactory;

	private Consumer<Command> commandExecutor;

	private LibraryElement type;

	private Text standardText;
	private Text classificationText;
	private Text domainText;
	private Text functionText;
	private Text typeText;
	private Text descriptionText;

	protected boolean blockListeners;

	private TableViewer versionViewer;
	private AddDeleteWidget addDeleteVersionInfoButtons;
	private static final String VERSION_PROPERTY = "version"; //$NON-NLS-1$
	private static final String ORGANIZATION_PROPERTY = "organization"; //$NON-NLS-1$
	private static final String AUTHOR_PROPERTY = "author"; //$NON-NLS-1$
	private static final String DATE_PROPERTY = "date"; //$NON-NLS-1$
	private static final String REMARKS_PROPERTY = "remarks"; //$NON-NLS-1$

	public TypeInfoWidget(final FormToolkit widgetFactory) {
		this.widgetFactory = widgetFactory;
	}

	protected FormToolkit getWidgetFactory() {
		return widgetFactory;
	}

	public Composite createControls(final Composite parent) {
		createControls(createComposite(parent), createComposite(parent));
		return parent;
	}

	public void createControls(final Composite leftComposite, final Composite rightComposite) {
		createIdentificationGroup(leftComposite);
		createVersionInfoGroup(rightComposite);
	}

	private void createIdentificationGroup(final Composite parent) {
		final Group identificationGroup = createGroup(parent, FordiacMessages.Identification);
		identificationGroup.setLayout(new GridLayout(2, false));
		identificationGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		getWidgetFactory().createLabel(identificationGroup, FordiacMessages.Standard + ":"); //$NON-NLS-1$
		standardText = createGroupText(identificationGroup, true);
		standardText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeStandardCommand(type, standardText.getText()));
			}
		});

		getWidgetFactory().createLabel(identificationGroup, FordiacMessages.Classification + ":"); //$NON-NLS-1$
		classificationText = createGroupText(identificationGroup, true);
		classificationText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeClassificationCommand(type, classificationText.getText()));
			}
		});

		getWidgetFactory().createLabel(identificationGroup, FordiacMessages.ApplicationDomain + ":"); //$NON-NLS-1$
		domainText = createGroupText(identificationGroup, true);
		domainText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeApplicationDomainCommand(type, domainText.getText()));
			}
		});

		getWidgetFactory().createLabel(identificationGroup, FordiacMessages.Function + ":"); //$NON-NLS-1$
		functionText = createGroupText(identificationGroup, true);
		functionText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeFunctionCommand(type, functionText.getText()));
			}
		});

		getWidgetFactory().createLabel(identificationGroup, FordiacMessages.Type + ":"); //$NON-NLS-1$
		typeText = createGroupText(identificationGroup, true);
		typeText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeIdentifcationTypeCommand(type, typeText.getText()));
			}
		});

		final Label label = getWidgetFactory().createLabel(identificationGroup, FordiacMessages.Description + ":"); //$NON-NLS-1$
		label.setLayoutData(new GridData(SWT.NONE, SWT.TOP, false, false));
		descriptionText = getWidgetFactory().createText(identificationGroup, "", //$NON-NLS-1$
				SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		final GridData descriptionTextData = new GridData(GridData.FILL, GridData.FILL, true, true);
		descriptionText.setLayoutData(descriptionTextData);
		descriptionText.addModifyListener(e -> {
			if (!blockListeners) {
				executeCommand(new ChangeDescriptionCommand(type, descriptionText.getText()));
			}
		});
	}

	private void createVersionInfoGroup(final Composite parent) {
		final Group versionInfoGroup = createGroup(parent, FordiacMessages.VersionInfo);
		versionInfoGroup.setLayout(new GridLayout(2, false));
		versionInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		addDeleteVersionInfoButtons = new AddDeleteWidget();
		addDeleteVersionInfoButtons.createControls(versionInfoGroup, getWidgetFactory());

		versionViewer = TableWidgetFactory.createPropertyTableViewer(versionInfoGroup);
		configureTableLayout(versionViewer.getTable());

		final Table table = versionViewer.getTable();

		versionViewer.setContentProvider(new VersionContentProvider());
		versionViewer.setLabelProvider(new VersionLabelProvider());
		versionViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table),
				new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		versionViewer.setColumnProperties(new String[] { VERSION_PROPERTY, ORGANIZATION_PROPERTY, AUTHOR_PROPERTY,
				DATE_PROPERTY, REMARKS_PROPERTY });

		addDeleteVersionInfoButtons.bindToTableViewer(versionViewer, this, ref -> new AddNewVersionInfoCommand(type),
				ref -> new DeleteVersionInfoCommand(type, (VersionInfo) ref));

		versionViewer.setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(final Object element, final String property) {
				return true;
			}

			@Override
			public Object getValue(final Object element, final String property) {
				return switch (property) {
				case VERSION_PROPERTY -> ((VersionInfo) element).getVersion();
				case ORGANIZATION_PROPERTY -> ((VersionInfo) element).getOrganization();
				case AUTHOR_PROPERTY -> ((VersionInfo) element).getAuthor();
				case DATE_PROPERTY -> ((VersionInfo) element).getDate();
				default -> ((VersionInfo) element).getRemarks();
				};
			}

			@Override
			public void modify(final Object element, final String property, final Object value) {
				final TableItem tableItem = (TableItem) element;
				final VersionInfo data = (VersionInfo) tableItem.getData();
				Command cmd = null;
				cmd = switch (property) {
				case VERSION_PROPERTY -> new ChangeVersionCommand(data, value.toString());
				case ORGANIZATION_PROPERTY -> new ChangeOrganizationCommand(data, value.toString());
				case AUTHOR_PROPERTY -> new ChangeAuthorCommand(data, value.toString());
				case DATE_PROPERTY -> new ChangeDateCommand(data, value.toString());
				default -> new ChangeRemarksCommand(data, value.toString());
				};
				executeCommand(cmd);
				versionViewer.refresh(data);
			}
		});
	}

	private static void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Version);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Organization);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Author);
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(FordiacMessages.Date);
		final TableColumn column5 = new TableColumn(table, SWT.LEFT);
		column5.setText(FordiacMessages.Remarks);
		final TableLayout layout = new TableLayout(true);
		layout.addColumnData(new ColumnWeightData(D_MIN, MAX_MINUS_T_MIN));
		layout.addColumnData(new ColumnWeightData(D_MIN, MAX_MINUS_MIN));
		layout.addColumnData(new ColumnWeightData(D_MIN, MAX_MINUS_MIN));
		layout.addColumnData(new ColumnWeightData(MIN, MAX_MINUS_T_MIN));
		layout.addColumnData(new ColumnWeightData(T_MIN, MAX));
		table.setLayout(layout);
	}

	private Composite createComposite(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		return composite;
	}

	protected Text createGroupText(final Composite group, final boolean editable) {
		final Text text = getWidgetFactory().createText(group, "", SWT.BORDER); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(editable);
		text.setEnabled(editable);
		return text;
	}

	public void initialize(final LibraryElement type, final Consumer<Command> commandExecutor) {
		this.commandExecutor = commandExecutor;
		this.type = type;
	}

	public void refresh() {
		if (null != type && versionViewer != null && !versionViewer.getControl().isDisposed()) {
			final Consumer<Command> commandExecutorBuffer = commandExecutor;
			commandExecutor = null;
			if (null != type.getIdentification()) {
				setIdentTextAll();
			}
			if (null != type.getVersionInfo()) {
				versionViewer.setInput(type);
			}
			commandExecutor = commandExecutorBuffer;
		}
	}

	private void setIdentTextAll() {
		final Identification id = type.getIdentification();
		standardText.setText((null != id.getStandard()) ? id.getStandard() : ""); //$NON-NLS-1$
		classificationText.setText((null != id.getClassification()) ? id.getClassification() : ""); //$NON-NLS-1$
		domainText.setText((null != id.getApplicationDomain()) ? id.getApplicationDomain() : ""); //$NON-NLS-1$
		functionText.setText((null != id.getFunction()) ? id.getFunction() : ""); //$NON-NLS-1$
		typeText.setText((null != id.getType()) ? id.getType() : ""); //$NON-NLS-1$
		descriptionText.setText((null != id.getDescription()) ? id.getDescription() : ""); //$NON-NLS-1$
	}

	@Override
	public void executeCommand(final Command cmd) {
		if (commandExecutor != null) {
			blockListeners = true;
			commandExecutor.accept(cmd);
			blockListeners = false;
		}
	}

	protected Group createGroup(final Composite parent, final String text) {
		final Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(text);
		getWidgetFactory().adapt(group);
		return group;
	}

	public void setEnabled(final boolean enablement) {
		standardText.setEnabled(enablement);
		classificationText.setEnabled(enablement);
		domainText.setEnabled(enablement);
		functionText.setEnabled(enablement);
		typeText.setEnabled(enablement);
		descriptionText.setEnabled(enablement);
		addDeleteVersionInfoButtons.setEnabled(enablement);
		versionViewer.setCellModifier(null);
	}

	protected LibraryElement getType() {
		return type;
	}

	protected Consumer<Command> getCommandExecutor() {
		return commandExecutor;
	}

	protected void setCommandExecutor(final Consumer<Command> commandExecutor) {
		this.commandExecutor = commandExecutor;
	}
}
