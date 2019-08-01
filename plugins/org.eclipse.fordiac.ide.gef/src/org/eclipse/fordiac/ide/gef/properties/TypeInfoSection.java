/*******************************************************************************
 * Copyright (c) 2014 - 2016 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.provider.VersionContentProvider;
import org.eclipse.fordiac.ide.gef.provider.VersionLabelProvider;
import org.eclipse.fordiac.ide.model.commands.change.ChangeApplicationDomainCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAuthorCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeClassificationCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteWidget;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Properties tab which shows the FB type information of the selected FB
 * 
 */
public abstract class TypeInfoSection extends AbstractSection {
	private Text fbTypeNameText;
	private Text commentText;

	private Text standardText;
	private Text classificationText;
	private Text domainText;
	private Text functionText;
	private Text typeText;
	private Text descriptionText;

	private TableViewer versionViewer;
	private static final String VERSION_PROPERTY = "version"; //$NON-NLS-1$
	private static final String ORGANIZATION_PROPERTY = "organization"; //$NON-NLS-1$
	private static final String AUTHOR_PROPERTY = "author"; //$NON-NLS-1$
	private static final String DATE_PROPERTY = "date"; //$NON-NLS-1$
	private static final String REMARKS_PROPERTY = "remarks"; //$NON-NLS-1$

	@Override
	protected LibraryElement getType() {
		return (LibraryElement) type;
	}

	@Override
	protected void setInputInit() {
		// nothing to do here
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTypeAndCommentSection(getLeftComposite());
		createIdentificationGroup(getLeftComposite());
		createVersionInfoGroup(getRightComposite());
	}

	private void createTypeAndCommentSection(Composite parent) {
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Type Name:");
		fbTypeNameText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, "Comment:");
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			}
		});
	}

	private void createIdentificationGroup(Composite parent) {
		Group identificationGroup = getWidgetFactory().createGroup(parent, "Identification");
		identificationGroup.setLayout(new GridLayout(2, false));
		identificationGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		getWidgetFactory().createCLabel(identificationGroup, "Standard:");
		standardText = createGroupText(identificationGroup, true);
		standardText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeStandardCommand(getType(), standardText.getText()));
			}
		});
		getWidgetFactory().createCLabel(identificationGroup, "Classification:");
		classificationText = createGroupText(identificationGroup, true);
		classificationText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeClassificationCommand(getType(), classificationText.getText()));
			}

		});
		getWidgetFactory().createCLabel(identificationGroup, "Application Domain:");
		domainText = createGroupText(identificationGroup, true);
		domainText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeApplicationDomainCommand(getType(), domainText.getText()));
			}
		});
		getWidgetFactory().createCLabel(identificationGroup, "Function: ");
		functionText = createGroupText(identificationGroup, true);
		functionText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeFunctionCommand(getType(), functionText.getText()));
			}
		});
		getWidgetFactory().createCLabel(identificationGroup, "Type: ");
		typeText = createGroupText(identificationGroup, true);
		typeText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeIdentifcationTypeCommand(getType(), typeText.getText()));
			}
		});
		CLabel label = getWidgetFactory().createCLabel(identificationGroup, "Description: ");
		label.setLayoutData(new GridData(SWT.NONE, SWT.TOP, false, false));
		descriptionText = getWidgetFactory().createText(identificationGroup, "", //$NON-NLS-1$
				SWT.WRAP | SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData descriptionTextData = new GridData(GridData.FILL, GridData.FILL, true, true);
		descriptionText.setLayoutData(descriptionTextData);
		descriptionText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(final ModifyEvent e) {
				executeCommand(new ChangeDescriptionCommand(getType(), descriptionText.getText()));
			}
		});
	}

	private void createVersionInfoGroup(Composite parent) {
		Group versionInfoGroup = getWidgetFactory().createGroup(parent, "Version Info");
		versionInfoGroup.setLayout(new GridLayout(2, false));
		versionInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		AddDeleteWidget buttons = new AddDeleteWidget();
		buttons.createControls(versionInfoGroup, getWidgetFactory());
		
		versionViewer = TableWidgetFactory.createPropertyTableViewer(versionInfoGroup);
		configureTableLayout(versionViewer.getTable());
		
		Table table = versionViewer.getTable();

		versionViewer.setContentProvider(new VersionContentProvider());
		versionViewer.setLabelProvider(new VersionLabelProvider());
		versionViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table),
				new TextCellEditor(table), new TextCellEditor(table), new TextCellEditor(table) });
		versionViewer.setColumnProperties(new String[] { VERSION_PROPERTY, ORGANIZATION_PROPERTY, AUTHOR_PROPERTY,
				DATE_PROPERTY, REMARKS_PROPERTY });


		buttons.bindToTableViewer(versionViewer, this, ref -> new AddNewVersionInfoCommand(getType()),
				ref -> new DeleteVersionInfoCommand(getType(), (VersionInfo) ref));

		versionViewer.setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(final Object element, final String property) {
				return true;
			}

			@Override
			public Object getValue(final Object element, final String property) {
				switch(property) {
				case VERSION_PROPERTY:
					return ((VersionInfo) element).getVersion();
				case ORGANIZATION_PROPERTY:
					return ((VersionInfo) element).getOrganization();
				case AUTHOR_PROPERTY:
					return ((VersionInfo) element).getAuthor();
				case DATE_PROPERTY:
					return ((VersionInfo) element).getDate();
				default:
					return ((VersionInfo) element).getRemarks();
				}
			}

			@Override
			public void modify(final Object element, final String property, final Object value) {
				TableItem tableItem = (TableItem) element;
				VersionInfo data = (VersionInfo) tableItem.getData();
				Command cmd = null;
				switch(property) {
				case VERSION_PROPERTY:
					cmd = new ChangeVersionCommand(data, value.toString());
					break;
				case ORGANIZATION_PROPERTY:
					cmd = new ChangeOrganizationCommand(data, value.toString());
					break;
				case AUTHOR_PROPERTY:
					cmd = new ChangeAuthorCommand(data, value.toString());
					break;
				case DATE_PROPERTY:
					cmd = new ChangeDateCommand(data, value.toString());
					break;
				default:
					cmd = new ChangeRemarksCommand(data, value.toString());
					break;
				}
				executeCommand(cmd);
				versionViewer.refresh(data);
			}
		});
	}

	private void configureTableLayout(Table table) {
		TableColumn column1 = new TableColumn(versionViewer.getTable(), SWT.LEFT);
		column1.setText("Version");
		TableColumn column2 = new TableColumn(versionViewer.getTable(), SWT.LEFT);
		column2.setText("Organization");
		TableColumn column3 = new TableColumn(versionViewer.getTable(), SWT.LEFT);
		column3.setText("Author");
		TableColumn column4 = new TableColumn(versionViewer.getTable(), SWT.LEFT);
		column4.setText("Date");
		TableColumn column5 = new TableColumn(versionViewer.getTable(), SWT.LEFT);
		column5.setText("Remarks");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(20, 90));
		layout.addColumnData(new ColumnWeightData(20, 90));
		layout.addColumnData(new ColumnWeightData(10, 70));
		layout.addColumnData(new ColumnWeightData(30, 100));
		table.setLayout(layout);
	}

	@Override
	public void setInputCode() {
		commentText.setEnabled(false);
		standardText.setEnabled(false);
		classificationText.setEnabled(false);
		domainText.setEnabled(false);
		functionText.setEnabled(false);
		typeText.setEnabled(false);
		descriptionText.setEnabled(false);
		versionViewer.setCellModifier(null);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			fbTypeNameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			if (getType().getIdentification() != null) {
				standardText.setText(getType().getIdentification().getStandard() != null
						? getType().getIdentification().getStandard()
						: ""); //$NON-NLS-1$
				classificationText.setText(getType().getIdentification().getClassification() != null
						? getType().getIdentification().getClassification()
						: ""); //$NON-NLS-1$
				domainText.setText(getType().getIdentification().getApplicationDomain() != null
						? getType().getIdentification().getApplicationDomain()
						: ""); //$NON-NLS-1$
				functionText.setText(getType().getIdentification().getFunction() != null
						? getType().getIdentification().getFunction()
						: ""); //$NON-NLS-1$
				typeText.setText(
						getType().getIdentification().getType() != null ? getType().getIdentification().getType() : ""); //$NON-NLS-1$
				descriptionText.setText(getType().getIdentification().getDescription() != null
						? getType().getIdentification().getDescription()
						: ""); //$NON-NLS-1$
				versionViewer.setInput(getType());
			} else {
				getType().setIdentification(LibraryElementFactory.eINSTANCE.createIdentification());
			}
		}
		commandStack = commandStackBuffer;
	}

}
