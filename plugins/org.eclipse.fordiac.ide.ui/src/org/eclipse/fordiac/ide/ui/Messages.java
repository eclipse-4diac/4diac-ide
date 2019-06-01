/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.ui.messages"; //$NON-NLS-1$
	
	/** The Directory chooser control_ labe l_ browse. */
	public static String DirectoryChooserControl_LABEL_Browse;
	
	/** The Directory chooser control_ labe l_ choose directory. */
	public static String DirectoryChooserControl_LABEL_ChooseDirectory;
	
	/** The Directory chooser control_ labe l_ selectd directory dialog message. */
	public static String DirectoryChooserControl_LABEL_SelectdDirectoryDialogMessage;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// private empty constructor
	}
}
