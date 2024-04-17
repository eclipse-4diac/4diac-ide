/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "aimirim.fordiac.ide.deployment.uao.messages"; //$NON-NLS-1$
	public static String UAODeploymentExecutor_CreateClientFailed;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}