/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
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
package org.eclipse.fordiac.ide.export.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.export.ui.messages"; //$NON-NLS-1$
	
	/** The Fordiac export wizard_ descriptio n_ wizard page. */
	public static String FordiacExportWizard_DESCRIPTION_WizardPage;
	
	/** The Fordiac export wizard_ error. */
	public static String FordiacExportWizard_ERROR;
	
	/** The Fordiac export wizard_ labe l_ window_ title. */
	public static String FordiacExportWizard_LABEL_Window_Title;
	
	/** The Fordiac export wizard_ titl e_ wizard page. */
	public static String FordiacExportWizard_TITLE_WizardPage;
	
	/** The Fordiac export wizard_ wizard page. */
	public static String FordiacExportWizard_WizardPage;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
