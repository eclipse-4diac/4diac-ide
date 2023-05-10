/*******************************************************************************
 * Copyright (c) 2023 Universidad Autonoma de Madrid and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.design;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.ConnectionToValueMapImpl;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The services class used by VSM.
 */
public class Services {
    
    /**
    * See http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.sirius.doc%2Fdoc%2Findex.html&cp=24 for documentation on how to write service methods.
    */
    public EObject myService(EObject self, String arg) {
       // TODO Auto-generated code
      return self;
    }
    
    public String transactionName(Transaction self) {
       final int index = ((EventManager) self.eContainer()).getTransactions().indexOf(self);
       return "T" + index + ":" +self.getInputEventOccurrence().getParentFB().getName();
     }
    
    public String getConnectionSource(ConnectionToValueMapImpl entry) {
    	return entry.getKey().getSourceElement().getName() + "." 
    			+ entry.getKey().getSource().getName();
    }
    
    public String getConnectionDestination(ConnectionToValueMapImpl entry) {
    	return entry.getKey().getDestinationElement().getName() + "." 
    			+ entry.getKey().getDestination().getName();
    }
    
    public String getConnectionValue(ConnectionToValueMapImpl entry) {
    	return entry.getValue().getValue();
    }
    
    public Collection<VarDeclaration> getAllOutputVariables(FBNetworkRuntime fbNetworkRuntime) {
    	final EventOccurrence eventOccurrence= (EventOccurrence)fbNetworkRuntime.eContainer();
    	final FBRuntimeAbstract runtime = 
    			fbNetworkRuntime.getTypeRuntimes().get(eventOccurrence.getParentFB());
    	if (runtime.getModel() instanceof FBType type) {
    		return type.getInterfaceList().getOutputVars();
    	}    	
    	return Collections.emptyList();
    }
}
