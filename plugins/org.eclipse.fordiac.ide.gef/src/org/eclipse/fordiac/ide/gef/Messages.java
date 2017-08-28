/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2014 - 2016 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.gef.messages"; //$NON-NLS-1$
	
	/** The Abstract view edit part_ erro r_create figure. */
	public static String AbstractViewEditPart_ERROR_createFigure;
	
	/** The Element edit part factory_ erro r_ cant create part for model element. */
	public static String Abstract4diacEditPartFactory_ERROR_CantCreatePartForModelElement;
	
	public static String Abstract4diacEditPartFactory_LABEL_RUNTIMEException_CantCreateModelForElement;
	
	/** The Appearance property section_ labe l_ background color. */
	public static String AppearancePropertySection_LABEL_BackgroundColor;
	
	/** The Appearance property section_ labe l_ choose color. */
	public static String AppearancePropertySection_LABEL_ChooseColor;
	
	/** The Appearance property section_ labl e_ color. */
	public static String AppearancePropertySection_LABLE_Color;
	
	/** The Change comment command_ labe l_ change comment. */
	public static String ChangeCommentCommand_LABEL_ChangeComment;
		
	/** The View rename command_ labe l_ rename view. */
	public static String ViewRenameCommand_LABEL_RenameView;
	
	/** The Property util_ labe l_ description_ complianceprofile. */
	public static String PropertyUtil_LABEL_Description_Complianceprofile;
	
	/** The Property util_ labe l_ instancename. */
	public static String PropertyUtil_LABEL_Instancename;
	
	/** The Property util_ labe l_ instancecomment. */
	public static String PropertyUtil_LABEL_Instancecomment;
	
	/** The Set profile command_ labe l_ set profile. */
	public static String SetProfileCommand_LABEL_SetProfile;
	
	public static String ConnectionSection_Comment;
	public static String ConnectionSection_Source;
	public static String ConnectionSection_Target;

	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// private empty constructor
	}
}
