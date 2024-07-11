package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class ConnectionsToStructWizardPage extends WizardNewFileCreationPage {

	private Text sourceNameText;
	private Text destinationNameText;
	private Button conflictButton;

	protected ConnectionsToStructWizardPage(final String pageName, final IStructuredSelection selection) {
		super(pageName, selection);
		this.setTitle("Convert Connections to Structured Type");
		this.setDescription(
				"Store new Type in Library. Select the name of the In/Output Vars. Choose how to resolve conflicts");
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		final Composite composite = (Composite) getControl();
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
	}

	@Override
	protected void createAdvancedControls(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridData containerData = new GridData();
		containerData.horizontalAlignment = GridData.FILL;
		containerData.grabExcessHorizontalSpace = true;
		container.setLayoutData(containerData);

		final GridLayout gridl = new GridLayout();
		gridl.numColumns = 2;
		gridl.marginWidth = 0;
		container.setLayout(gridl);

		final Label sourceNameLabel = new Label(container, NONE);
		sourceNameLabel.setText("Function Block &Output Name" + ":");
		sourceNameText = new Text(container, SWT.SINGLE | SWT.BORDER);
		// TODO: wrong ID?
		sourceNameText.addListener(SWT.ERROR, this);

		final Label destinationNameLabel = new Label(container, NONE);
		destinationNameLabel.setText("Function Block &Input Name" + ":");
		destinationNameText = new Text(container, SWT.BORDER);
		destinationNameText.addListener(SWT.ERROR, this);

		final GridData textGridData = new GridData();
		textGridData.horizontalAlignment = GridData.FILL;
		textGridData.grabExcessHorizontalSpace = true;
		sourceNameText.setLayoutData(textGridData);
		destinationNameText.setLayoutData(textGridData);

		conflictButton = new Button(container, SWT.CHECK);
		conflictButton.setText("&Solve conflicts with Multiplexer/Demultiplexer");
		conflictButton.setSelection(true);
		final GridData checkboxGridData = new GridData();
		checkboxGridData.horizontalSpan = 2;
		conflictButton.setLayoutData(checkboxGridData);
		super.createAdvancedControls(parent);
	}

	public String getSourceName() {
		return sourceNameText.getText();
	}

	public String getDestinationName() {
		return destinationNameText.getText();
	}

	public boolean getConflictResolution() {
		return conflictButton.getSelection();
	}

	@Override
	protected boolean validatePage() {
		return super.validatePage() && !sourceNameText.getText().isBlank() && !destinationNameText.getText().isBlank();
	}

	@Override
	public boolean isPageComplete() {
		return super.isPageComplete();
	}

}