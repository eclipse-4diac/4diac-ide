/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao.helpers;

import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;

public class Constants {

	public static final Response EMPTY_RESPONSE;

	static {
		// ensure that all entries in the empty response return appropriate empty values
		EMPTY_RESPONSE = DevResponseFactory.eINSTANCE.createResponse();
		EMPTY_RESPONSE.setFblist(DevResponseFactory.eINSTANCE.createFBList());
		EMPTY_RESPONSE.setID("0"); //$NON-NLS-1$
		EMPTY_RESPONSE.setWatches(DevResponseFactory.eINSTANCE.createWatches());
	}

	private Constants() {
		// empty private constructor
	}
}
