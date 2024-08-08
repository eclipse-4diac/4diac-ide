package org.eclipse.fordiac.ide.typemanagement.wizards;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String ConnectionsToStructWizard_PageTitle;
	public static String ConnectionsToStructWizard_WindowTitle;
	public static String ConnectionsToStructWizardPage_AnyStruct;
	public static String ConnectionsToStructWizardPage_Description;
	public static String ConnectionsToStructWizardPage_ExistingStruct;
	public static String ConnectionsToStructWizardPage_InName;
	public static String ConnectionsToStructWizardPage_InvalidStructName;
	public static String ConnectionsToStructWizardPage_Name;
	public static String ConnectionsToStructWizardPage_NewType;
	public static String ConnectionsToStructWizardPage_OutName;
	public static String ConnectionsToStructWizardPage_SolveConflicts;
	public static String ConnectionsToStructWizardPage_Title;
	public static String RepairBrokenConnectionWizardPage_Description;
	public static String RepairBrokenConnectionWizardPage_Dots;
	public static String RepairBrokenConnectionWizardPage_Name;
	public static String RepairBrokenConnectionWizardPage_Title;
	public static String RepairBrokenConnectionWizardPage_Type;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
