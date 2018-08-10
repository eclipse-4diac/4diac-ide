/*******************************************************************************
 * Copyright (c) 2011, 2012 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.commands;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.automatedRemoteTest.AutomatedRemoteTest;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.FBTHelper;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.TestSequence;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.text.IDocument;

public class StartAutomaticRemoteTest extends Command {
	private static int artCount = 0;

	private final FBType fbType;
	private final IDocument document;
	private boolean isRunning = false;
	private int numART = 0;
	
	private static synchronized int getNewARTCount() {
		//get a thread save correct number
		int retVal = artCount;
		artCount++;		
		return retVal;
	}
	
	/**
	 * Instantiates a new adds the service sequence command.
	 * 
	 * @param fbType the fb type
	 * @param document 
	 */
	public StartAutomaticRemoteTest(FBType fbType, IDocument document) {
		this.fbType = fbType;
		numART = getNewARTCount();
		this.document=document;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return ((fbType != null) && (!isRunning));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		isRunning = true;
        boolean isAutomatedTest=true;

        List<TestSequence> testSequences = FBTHelper.extractTestSequences(fbType);
        String commID="localhost:"+(60000+numART); //$NON-NLS-1$
        
        if (isAutomatedTest) {
			boolean isOK = true;
        	AutomatedRemoteTest art = new AutomatedRemoteTest();
			
			isOK = art.prepareART(fbType, testSequences);
			
			if (isOK) {
				//TODO download:
				// server, Mux, DeMUX, FBuT, configure (unique ID)
				// use deployment.iec61499
				isOK = art.deployTestRes(commID,numART);

				if (isOK) {
					isOK = art.configureCommunication(commID);

					if (isOK) {
						isOK = art.runTests();

						String result = art.evaluateTestResults(testSequences,true);

						document.set(result);


						art.stopCommunication();
					}
					art.cleanRes();
				} else {
					document.set(art.DMgr_response);
				}
			}
			isRunning=false;
		}
		

	}


}
