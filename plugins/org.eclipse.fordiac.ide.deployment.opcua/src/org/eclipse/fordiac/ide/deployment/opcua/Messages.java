package org.eclipse.fordiac.ide.deployment.opcua;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	private static final String BUNDLE_NAME ="org.eclipse.fordiac.ide.deployment.opcua.messages";
	public static String OPCUADeploymentExecutor_CreateResourceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String OPCUADeploymentExecutor_CreateConnectionFailed;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages() {
		// empty private constructor
	}
}