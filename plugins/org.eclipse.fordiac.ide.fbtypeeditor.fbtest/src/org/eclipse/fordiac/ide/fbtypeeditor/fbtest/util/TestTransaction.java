/*******************************************************************************
 * Copyright (c) 2011, 2012, 2016 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

public class TestTransaction {
  private TestPrimitive inputPrimitive;
  private List<TestPrimitive> outputPrimitives = new ArrayList<TestPrimitive>();
  private boolean isSuccess=false;
  private String inputMessage="";
  private String failMessage="";

  private ServiceTransaction relatedModelElement=null;
  
  public ServiceTransaction getRelatedModelElement() {
	return relatedModelElement;
}
public void setRelatedModelElement(ServiceTransaction relatedModelElement) {
	this.relatedModelElement = relatedModelElement;
}
public TestPrimitive getInputPrimitive() {
	return inputPrimitive;
  }
  public void setInputPrimitive(TestPrimitive inputPrimitive) {
	this.inputPrimitive = inputPrimitive;
  }
  public List<TestPrimitive> getOutputPrimitives() {
	return outputPrimitives;
  }
  public void setOutputPrimitives(List<TestPrimitive> outputPrimitives) {
	this.outputPrimitives = outputPrimitives;
  }
  
  public void addOutputPrimitives(TestPrimitive outputPrimitive) {
	  this.outputPrimitives.add(outputPrimitive);
  }
  
  @Override
public String toString() {
	  String retval="";
	  if (null!= inputPrimitive) {
		  retval="\nInputPrimitive: "+inputPrimitive.toString()+"\n";
	  }
	  if (null!= outputPrimitives) {
	      retval += "OutputPrimitives:\n"+outputPrimitives.toString(); 
	  }
	  return retval;
  }
public boolean isSuccess() {
	return isSuccess;
}
public void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
}
public String getFailMessage() {
	return failMessage;
}
public void setFailMessage(String failMessage) {
	this.failMessage = failMessage;
}
public String getInputMessage() {
	return inputMessage;
}
public void setInputMessage(String inputMessage) {
	this.inputMessage = inputMessage;
}
	
}
