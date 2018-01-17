package org.eclipse.fordiac.ide.typemanagement;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.typemanagement.messages"; //$NON-NLS-1$
	public static String DeleteFBTypeParticipant_Name;
	public static String DeleteFBTypeParticipant_TypeInUseWarning;
	public static String RenameType_InvalidIdentifierErrorMessage;
	public static String RenameType_Name;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
