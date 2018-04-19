/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.systemconfiguration.messages"; //$NON-NLS-1$
	
	/** The Device create command_ labe l_ create device. */
	public static String DeviceCreateCommand_LABEL_CreateDevice;
		
	/** The Device edit part_ labe l_ not defined. */
	public static String DeviceEditPart_LABEL_NotDefined;

	public static String OpenSystemConfEditorAction_Name;
	
	/** The System conf palette factory_ labe l_ tools. */
	public static String SystemConfPaletteFactory_LABEL_Tools;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
