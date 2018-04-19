/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.model.commands.messages"; //$NON-NLS-1$
	public static String AdapterConnectionNotAllowed;
	public static String ChangeBackgroundcolorCommand_LABEL_ChangeColor;
	public static String ChangeValueCommand_LABEL_ChangeValue;
	public static String DeleteFBNetworkElement;
	public static String FBCreateCommand_LABLE_CreateFunctionBlock;
	public static String ViewSetPositionCommand_LABEL_Move;
	public static String WithExists;
	
	public static String MapToCommand_STATUSMessage_AlreadyMapped;

	public static String MapToCommand_STATUSMessage_TypeNotSupported;

	/** The Link constraints_ status message_has already input connection. */
	public static String LinkConstraints_STATUSMessage_hasAlreadyInputConnection;

	/** The Link constraint outputconnections to check that adapters has only one outputconn */
	public static String LinkConstraints_STATUSMessage_hasAlreadyOutputConnection;
	
	/** The Link constraints_ status message_ i n_ i n_ ou t_ ou t_not allowed. */
	public static String LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed;
	
	/** The Link constraints_ status message_ not compatible. */
	public static String LinkConstraints_STATUSMessage_NotCompatible;
	
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
