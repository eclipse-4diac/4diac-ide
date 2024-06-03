package org.eclipse.fordiac.ide.typemanagement.repair;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class MissingTypeMarkerResolution implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {

		final List<IMarkerResolution> quickFixes = new ArrayList<>();

		quickFixes.add(new MissingTypeQuickFix("Create missing Type"));

		quickFixes.add(new MissingTypeQuickFix("Delete invalid pins including connections"));

		return quickFixes.toArray(new IMarkerResolution[quickFixes.size()]);
	}

	public static class CustomWizardPage extends WizardPage {

		private final IMarker marker;

		protected CustomWizardPage(final String pageName, final IMarker marker) {
			super(pageName);
			this.marker = marker;
			setTitle("Custom Quick Fix Page");
			setDescription("Provide additional information to fix the issue.");
		}

		@Override
		public void createControl(final Composite parent) {
			final Composite container = new Composite(parent, SWT.NONE);
			container.setLayout(new GridLayout(1, false));

			final Label label = new Label(container, SWT.NONE);
			label.setText("This is a custom wizard page for fixing the marker issue.");

			// Add more controls as needed

			setControl(container);
		}

	}

	public static class CustomWizard extends Wizard {

		private final IMarker marker;

		public CustomWizard(final IMarker marker) {
			this.marker = marker;
			setWindowTitle("Custom Quick Fix Wizard");
		}

		@Override
		public void addPages() {
			addPage(new CustomWizardPage("Custom Page", marker));
		}

		@Override
		public boolean performFinish() {
			// Implement the logic to be executed when the wizard finishes
			return true;
		}
	}
}
