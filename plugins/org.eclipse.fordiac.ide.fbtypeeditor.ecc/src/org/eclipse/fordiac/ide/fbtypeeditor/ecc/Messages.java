/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard EbenhoferIngo Hegny, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.messages"; //$NON-NLS-1$
	public static String ECCActions_AddAction;
	public static String ECCActions_InitialState;
	public static String ECCActions_AddState;
	public static String ECCEditor_LABEL_ECCEditorTabName;
	public static String ECCPaletteFactory_LABEL_Action;
	public static String ECCPaletteFactory_LABEL_STAlgorithm;
	public static String ECCPaletteFactory_LABEL_ECCGroup;
	public static String ECCPaletteFactory_LABEL_State;
	public static String ECCPaletteFactory_TOOLTIP_Action;
	public static String ECCPaletteFactory_TOOLTIP_State;
	public static String ECCPaletteFactory_TOOLTIP_STAlgorithm;
	public static String ECStateSetPositionCommand_LABEL_Move;
	public static String StateCreationFactory_LABEL_NewECState;	
	
	/** The Fordiac ECC Editor preference page_ label_ preference page description. */
	public static String FordiacECCPreferencePage_LABEL_PreferencePageDescription;	
	
	/** The Fordiac ECC Editor preference page_ label_ default ECC State color. */             
	public static String FordiacECCPreferencePage_LABEL_ECCStateColor;          
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC State border color. */      
	public static String FordiacECCPreferencePage_LABEL_ECCStateBorderColor;    
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC Algorithm color. */         
	public static String FordiacECCPreferencePage_LABEL_ECCAlgorithmColor;      
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC Algorithm border color. */  
	public static String FordiacECCPreferencePage_LABEL_ECCAlgorithmBorderColor;
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC Event color. */             
	public static String FordiacECCPreferencePage_LABEL_ECCEventColor;          
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC Event border color. */      
	public static String FordiacECCPreferencePage_LABEL_ECCEventBorderColor;    
	                                                                                
	/** The Fordiac ECC Editor preference page_ label_ default ECC Transition color. */        
	public static String FordiacECCPreferencePage_LABEL_ECCTransitionColor;
	
	public static String ECAlgorithmGroup_Title;
	
	public static String AlgorithmComposite_Language;
	
	public static String AlgorithmComposite_Comment;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	private Messages() {
	}
}
