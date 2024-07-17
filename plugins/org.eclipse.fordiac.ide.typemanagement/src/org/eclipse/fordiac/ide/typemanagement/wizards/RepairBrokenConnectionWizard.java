package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.wizard.Wizard;

public class RepairBrokenConnectionWizard extends Wizard {
	private RepairBrokenConnectionWizardPage repairPage;
	private final TypeLibrary lib;
	private final DataType errorType;
	private StructuredType type;
	private String var;

	public RepairBrokenConnectionWizard(final ErrorMarkerInterface errormarker) {
		setWindowTitle("Repair broken Connection");
		this.lib = errormarker.getFBNetworkElement().getTypeLibrary();
		this.errorType = errormarker.getType();
	}

	@Override
	public boolean performFinish() {
		type = repairPage.getType();
		var = repairPage.getVar();
		return type != null && var != null;
	}

	@Override
	public void addPages() {
		repairPage = new RepairBrokenConnectionWizardPage("Repair broken Connection", lib, errorType);
		addPage(repairPage);
	}

	public StructuredType getType() {
		return type;
	}

	public String getVar() {
		return var;
	}

}