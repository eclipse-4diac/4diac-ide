package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.application.properties.messages"; //$NON-NLS-1$
	public static String StructManipulatorSection_MEMBERVAR_COLUMN_COMMENT;
	public static String StructManipulatorSection_MEMBERVAR_COLUMN_NAME;
	public static String StructManipulatorSection_MEMBERVAR_COLUMN_TYPE;
	public static String StructManipulatorSection_OPEN_IN_EDITOR_BUTTON;
	public static String StructManipulatorSection_STRUCTURED_TYPE;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
