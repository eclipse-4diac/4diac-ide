/********************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse  License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Waldemar Eisenmenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

public interface TypeLibraryTags {

	 String TOOL_LIBRARY_PROJECT_NAME = "Tool Library"; //$NON-NLS-1$

	/** The Constant for the file ending of FB type files. */
	 String FB_TYPE_FILE_ENDING = "FBT"; //$NON-NLS-1$

	 String FB_TYPE_FILE_ENDING_WITH_DOT = "."+ FB_TYPE_FILE_ENDING; //$NON-NLS-1$

	 String ADAPTER_TYPE_FILE_ENDING = "ADP"; //$NON-NLS-1$

	 String ADAPTER_TYPE_FILE_ENDING_WITH_DOT = "." + ADAPTER_TYPE_FILE_ENDING; //$NON-NLS-1$

	 String DEVICE_TYPE_FILE_ENDING = "DEV"; //$NON-NLS-1$

	 String DEVICE_TYPE_FILE_ENDING_WITH_DOT = "." + DEVICE_TYPE_FILE_ENDING; //$NON-NLS-1$

	 String RESOURCE_TYPE_FILE_ENDING = "RES"; //$NON-NLS-1$

	 String RESOURCE_TYPE_FILE_ENDING_WITH_DOT = "." + RESOURCE_TYPE_FILE_ENDING; //$NON-NLS-1$

	 String SEGMENT_TYPE_FILE_ENDING = "SEG"; //$NON-NLS-1$

	 String SEGMENT_TYPE_FILE_ENDING_WITH_DOT = "." + SEGMENT_TYPE_FILE_ENDING; //$NON-NLS-1$

	 String SUBAPP_TYPE_FILE_ENDING = "SUB"; //$NON-NLS-1$

	 String SUBAPP_TYPE_FILE_ENDING_WITH_DOT = "." + SUBAPP_TYPE_FILE_ENDING; //$NON-NLS-1$

	/** The Constant TYPE_LIBRARY. */
	 String TYPE_LIBRARY = "typelibrary";//$NON-NLS-1$

	/** The Constant TOOL_TYPE_DIR. */
	 String TOOL_TYPE_DIR = TYPE_LIBRARY + "/";//$NON-NLS-1$
	
}
