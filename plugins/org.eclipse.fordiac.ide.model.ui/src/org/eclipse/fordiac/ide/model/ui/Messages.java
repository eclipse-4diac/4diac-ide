package org.eclipse.fordiac.ide.model.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.model.ui.messages"; //$NON-NLS-1$
	public static String DataTypeDropdown_Type_Selection;
	public static String DataTypeDropdown_Select_Type;
	public static String DataTypeDropdown_Elementary_Types;
	public static String DataTypeDropdown_STRUCT_Types;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
