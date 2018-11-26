/*******************************************************************************
 * Copyright (c) 2011, 2012 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class TestSequence {
   private List<TestTransaction> testTransactions=new ArrayList<TestTransaction>();
   private boolean isSuccess=false;
   private String name="";
   private ServiceSequence relatedModelElement=null;
   
public ServiceSequence getRelatedModelElement() {
	return relatedModelElement;
}

public void setRelatedModelElement(ServiceSequence relatedModelElement) {
	this.relatedModelElement = relatedModelElement;
}

public List<TestTransaction> getTestTransactions() {
	return testTransactions;
}

public void setTestTransactions(List<TestTransaction> testTransactions) {
	this.testTransactions = testTransactions;
}
	
public void addTestTransaction(TestTransaction testTransaction) {
	this.testTransactions.add(testTransaction);
}

@Override
public String toString() {
	return testTransactions.toString();
}

public boolean isSuccess() {
	return isSuccess;
}

public void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
