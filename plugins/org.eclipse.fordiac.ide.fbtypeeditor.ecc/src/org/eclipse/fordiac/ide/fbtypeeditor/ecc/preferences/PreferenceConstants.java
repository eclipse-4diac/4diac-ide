/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH, TU Wien ACIN
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

/**
 * Constant definitions for plug-in preferences
 */
public final class PreferenceConstants {

	/** The Constant P_ECC_STATE_COLOR. */                                                          
	public static final String P_ECC_STATE_COLOR = "ECCEditorStateColor";                            //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_STATE_BORDER_COLOR. */                                                   
	public static final String P_ECC_STATE_BORDER_COLOR = "ECCEditorStateBorderColor";               //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_TRANSITION_COLOR. */                                                     
	public static final String P_ECC_TRANSITION_COLOR = "ECCEditorTransitionColor";                  //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_ALGORITHM_COLOR. */                                                      
	public static final String P_ECC_ALGORITHM_COLOR = "ECCEditorAlgorithmColor";                    //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_ALGORITHM_BORDER_COLOR. */                                               
	public static final String P_ECC_ALGORITHM_BORDER_COLOR = "ECCEditorAlgorithmBorderColor";       //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_EVENT_COLOR. */                                                          
	public static final String P_ECC_EVENT_COLOR = "ECCEditorEventColor";                            //$NON-NLS-1$
	                                                                                                
	/** The Constant P_ECC_EVENT_BORDER_COLOR. */                                                   
	public static final String P_ECC_EVENT_BORDER_COLOR = "ECCEditorEventBorderColor";     //$NON-NLS-1$
	
	private PreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
