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
	private FBType fbType;

	private boolean isRunning=false;
	
	static int ARTCount = 0;
	int numART = 0;

	IDocument document;
	
	/**
	 * Instantiates a new adds the service sequence command.
	 * 
	 * @param fbType the fb type
	 * @param document 
	 */
	public StartAutomaticRemoteTest(FBType fbType, IDocument document) {
		this.fbType = fbType;
		numART=ARTCount;
		ARTCount++;
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
        String CommID="localhost:"+(60000+numART); //$NON-NLS-1$
        
        if (isAutomatedTest) {
			boolean isOK=true;
        	AutomatedRemoteTest ART = new AutomatedRemoteTest();
			
			isOK = ART.prepareART(fbType, testSequences);
			
			if (isOK) {
				//TODO download:
				// server, Mux, DeMUX, FBuT, configure (unique ID)
				// use deployment.iec61499
				isOK = ART.deployTestRes(CommID,numART);

				if (isOK) {
					isOK = ART.configureCommunication(CommID);

					if (isOK) {
						isOK = ART.runTests();

						String result = ART.evaluateTestResults(testSequences,true);

						document.set(result);


						ART.stopCommunication();
					}
					ART.cleanRes();
				} else {
					document.set(ART.DMgr_response);
				}
				}
			isRunning=false;
		}
		

	}


}
