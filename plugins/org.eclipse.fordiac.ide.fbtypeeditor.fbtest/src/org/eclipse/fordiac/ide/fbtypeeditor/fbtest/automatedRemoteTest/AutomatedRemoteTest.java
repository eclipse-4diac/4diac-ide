/*******************************************************************************
 * Copyright (c) 2011 - 2014 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 						2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.automatedRemoteTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.DataVariable;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.FBTHelper;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.TestPrimitive;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.TestSequence;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.TestTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.comm.datatypes.IECDataTypeFactory;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_UINT;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;

public class AutomatedRemoteTest {

	private FBType fbType;
	private String fbInterface=""; //$NON-NLS-1$
	private String MgrID;
	
	private List<IEC_ANY> sendList = new ArrayList<>();
	private List<IEC_ANY> recvListTemplate = new ArrayList<>();
	private List<List<IEC_ANY>> recvList = new ArrayList<>();
	private Map<String,Integer> inputEventTable = new HashMap<>();
	private Map<String,Integer> outputEventTable = new HashMap<>();

	private Map<String,Integer> inputVarsTable = new HashMap<>();
	private Map<String,Integer> outputVarsTable = new HashMap<>();
	
	private List<TestSequence> tests = null;
	
	private ART_TCPClient tcpTestInterface;
	
	private ART_DeploymentMgr depMgr;
	private String depMgrResponse=""; //$NON-NLS-1$
	
	public String getResponse() {
		return depMgrResponse;
	}
	
	public boolean prepareART (FBType fbType, List<TestSequence> paTestSequences) {
		this.fbType = fbType;

	
		sendList.clear();
		recvList.clear();
		recvList.clear();
		inputEventTable.clear();
		outputEventTable.clear();
		inputVarsTable.clear();
		outputVarsTable.clear();
		tests = null;
		
		boolean retval = (prepareInterface() && prepareTestCases(paTestSequences));
		
		
		return retval;
	}
	
	private boolean prepareTestCases(List<TestSequence> paTestSequences) {
		
		tests = paTestSequences;
		return true;
	}

	private boolean prepareInterface () {
		fbInterface=""; //$NON-NLS-1$
		IEC_UINT inputEventID = new IEC_UINT();
		IEC_UINT outputEventID = new IEC_UINT();
		
		int numberOfInputEvents = 0;
		int numberOfOutputEvents = 0;
		
		try {
			numberOfInputEvents = fbType.getInterfaceList().getEventInputs().size();
			numberOfOutputEvents = fbType.getInterfaceList().getEventOutputs().size();
		} catch (Exception e) {
			return false;
			//FB not testable without either Input-Events or Output-Events!
		}
		
		if (!((0!= numberOfInputEvents) && (0 != numberOfOutputEvents))) {
			//FB not testable without either Input-Events or Output-Events!
			return false;
		}

		fbInterface += "EventInputs\n";
		for(Iterator<Event> iterator = fbType.getInterfaceList().getEventInputs().iterator(); iterator.hasNext();) {
			Event entry = iterator.next();
			fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getEventInputs().indexOf(entry)+")\n";
		}
		
		sendList.add(inputEventID);
		if (fbType.getInterfaceList().getInputVars().size()>0) {
			fbInterface +="DataInputs\n";
			for(Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getInputVars().iterator(); iterator.hasNext();) {
				VarDeclaration entry = iterator.next();
				fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getInputVars().indexOf(entry)+")"+
						" -\t"+entry.getType().getName()+
						"\n";
				IEC_ANY newVar=IECDataTypeFactory.getIECTypeByTypename(entry.getType().getName());

				sendList.add(newVar);
				inputVarsTable.put(entry.getName(), (1+FBTHelper.getDIID(fbType, entry.getName())));
			}
		}		




		fbInterface +="EventOutputs\n";
		for(Iterator<Event> iterator = fbType.getInterfaceList().getEventOutputs().iterator(); iterator.hasNext();) {
			Event entry = iterator.next();
			fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getEventOutputs().indexOf(entry)+")\n";
		}

		recvListTemplate.add(outputEventID);
		if (fbType.getInterfaceList().getOutputVars().size()>0) {
			fbInterface +="DataOutputs\n";
			for(Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getOutputVars().iterator(); iterator.hasNext();) {
				VarDeclaration entry = iterator.next();
				fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getOutputVars().indexOf(entry)+")"+
						" -\t"+entry.getType().getName()+"\n";
				IEC_ANY newVar=IECDataTypeFactory.getIECTypeByTypename(entry.getType().getName());

				recvListTemplate.add(newVar);
				outputVarsTable.put(entry.getName(), (1+FBTHelper.getDOID(fbType, entry.getName())));				
			}
		}		

		
		
		
		return true;
	}

	
	public boolean deployTestRes(String testChanID, int paID) {
		
		//deploy
		depMgr = new ART_DeploymentMgr(fbType,MgrID,paID);
		boolean isOK = depMgr.deploy(testChanID);
		if (!isOK) {
			depMgrResponse = depMgr.getMgmtCommands();
		}
		return isOK;
	}
	
	public boolean configureCommunication(String paCommID) {
		//getMaxReplies
		int replySize = FBTHelper.getMaxOutputPrimitives(tests);
		recvList.clear();
		for (int i = 0; i<replySize+1; i++) {
			ArrayList<IEC_ANY> tempArray = new ArrayList<IEC_ANY>();
			for (Iterator<IEC_ANY> iterator = recvListTemplate.iterator(); iterator.hasNext();) {
				IEC_ANY var = iterator.next();
				tempArray.add((IEC_ANY)(var.clone()));
			}
			recvList.add(tempArray);
		}
		
		
		try {
			tcpTestInterface = new ART_TCPClient(paCommID, recvList, replySize+1);
		} catch (CommException e) {
			return false;
		}
		
		return true;
	}
	

	public boolean runTests() {
		if (null == tests) {
			return false;
		}
		boolean retval=true;
		for (Iterator<TestSequence> iterator = tests.iterator(); iterator.hasNext();) {
			TestSequence testSequence = iterator.next();
			if (!runSingleTestsequence(testSequence)) {
				testSequence.setSuccess(false);
				testSequence.getRelatedModelElement().setTestResult(ServiceSequence.TEST_FAIL);
				retval = false;
			} else {
				testSequence.setSuccess(true);
				testSequence.getRelatedModelElement().setTestResult(ServiceSequence.TEST_OK);
			}
		}
		return retval;
	}
	
	private boolean runSingleTestsequence(TestSequence paTS) {
		if (null!=paTS && null != paTS.getTestTransactions() && !paTS.getTestTransactions().isEmpty()) {				
			for (TestTransaction tt : paTS.getTestTransactions()) {
				TestPrimitive testPrim = tt.getInputPrimitive();
				org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.Event testEvent= testPrim.getEvent();
				IEC_ANY eventVar = sendList.get(0);
				eventVar.setValue(Integer.toString(testEvent.getEventID()));
				//set send-data
				if (null != testPrim.getData() && !testPrim.getData().isEmpty()) {
					for (DataVariable dataVar : testPrim.getData()) {
						Integer index = inputVarsTable.get(dataVar.getDataName());
						IEC_ANY var = sendList.get(index);
						var.setValue(dataVar.getDataValue());
					}
				}
				try {
					int slCounter=-1;
					for (IEC_ANY slVar : sendList) {
						if (slCounter==-1) {
							tt.setInputMessage("Sent Event: "+FBTHelper.getEINameByIndex(fbType, testEvent.getEventID())+"\n");
						} else {
							tt.setInputMessage(tt.getInputMessage()+
									" "+FBTHelper.getDINameByIndex(fbType, slCounter)+" := "+slVar.toString()+"\n");
						}
						slCounter++;
					}
					
					tcpTestInterface.sendIECData(sendList);
				} catch (CommException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				
				int expectedCount=0;
				if (null != tt.getOutputPrimitives()) {
					expectedCount=tt.getOutputPrimitives().size();
				}

				try {
					if (expectedCount>0) {
						int cancelCondition = 0;
						//check for counter (with timeout)
						while (expectedCount > tcpTestInterface.getCounter()){
							Thread.sleep(100);
							++cancelCondition;
							if (cancelCondition >100) {
								tt.setSuccess(false);
								tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
								return false;
							}
						}
						List<List<IEC_ANY>> receivedDataList = tcpTestInterface.getReceiveDataList();
						
						tt.setSuccess(true);
						tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
						tt.setFailMessage("");
						for (int j=0; j<expectedCount; j++) {
							//check reply-data and set retval
							List<IEC_ANY> oPreceiveData = receivedDataList.get(j);
							if (0<tt.getOutputPrimitives().size()) {
								if (null!=tt.getOutputPrimitives().get(j)) {
									OutputPrimitive tempOP = tt.getOutputPrimitives().get(j).getRelatedOutputPrimitive();
									//Check for Event
									int expectedEventID = FBTHelper.getEOID(fbType, tt.getOutputPrimitives().get(j).getEvent().getEventName());
									IEC_ANY receivedEventID = oPreceiveData.get(0);
									if (receivedEventID instanceof IEC_UINT) {
										if (((IEC_UINT)receivedEventID).getValue() == expectedEventID) {
											tt.setSuccess(true);
											tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
											tempOP.setTestResult(OutputPrimitive.TEST_OK);
											tt.setFailMessage(tt.getFailMessage()+"Received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)receivedEventID).getValue())+" (correct)\n");
										} else {
											tt.setSuccess(false);
											tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
											tempOP.setTestResult(OutputPrimitive.TEST_FAIL);
											tt.setFailMessage(tt.getFailMessage()+"Expected Event "+FBTHelper.getEONameByIndex(fbType, expectedEventID)+" but received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)receivedEventID).getValue())+"\n");
										}
									}
									
									//Check Data
									if (null!= tt.getOutputPrimitives().get(j).getData()) {
										for (DataVariable dv : tt.getOutputPrimitives().get(j).getData()) {
											Integer index = outputVarsTable.get(dv.getDataName());
											IEC_ANY var = oPreceiveData.get(index);
											IEC_ANY checkVar = recvListTemplate.get(index);
											checkVar.setValue(dv.getDataValue());
											if (!var.toString().equals(checkVar.toString())) {
												tt.setFailMessage(tt.getFailMessage()+"  "+dv.getDataName()+" = "+var.toString()+" (expected: "+checkVar.toString()+")\n");
												tt.setSuccess(false);
												tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
												tempOP.setTestResult(OutputPrimitive.TEST_FAIL);
											} else {
												tt.setFailMessage(tt.getFailMessage()+"  "+dv.getDataName()+" = "+var.toString()+" (correct)\n");
											}
										}
									}
								}
							}
							
						}
						
						
					} else {
						//no reply expected -> timeout
						Thread.sleep(2000);
						tt.setSuccess(tcpTestInterface.getCounter()==0);
						if (tt.isSuccess()) {
							tt.setFailMessage("No Event received (correct)\n");
							tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
						} else {
							tt.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
							try {
								IEC_ANY eventIDReceived = tcpTestInterface.getReceiveDataList().get(0).get(0);
								if (eventIDReceived instanceof IEC_UINT) {
									tt.setFailMessage("Received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)eventIDReceived).getValue())+" (unexpected)\n");
								} 
							} catch (Exception e) {

							}
						}
					}
				} catch (InterruptedException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}

				if(!tt.isSuccess()) {
					//first unsuccessful Transaction stops testing of TestSequence
					return false;
				}					
			}
		}
		return true;
		
	}

	public void stopCommunication() {
		try {
			tcpTestInterface.deRegister();
		} catch (CommException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	public String evaluateTestResults(List<TestSequence> testSequences, boolean displayAll) {
		String lineSeparator = "********************************************************************************\n";
		String retval="";
		retval+=(lineSeparator);
		retval+=(lineSeparator);
		retval+=(" Test-Results for: "+fbType.getName()+"  \t");
		Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= 
		  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		  String dateNow = formatter.format(currentDate.getTime());
		retval+=("  "+dateNow+"\n");
		retval+=(lineSeparator);
		if (displayAll) {
			retval+=(fbInterface);
			retval+=(lineSeparator);
		}
		for(Iterator<TestSequence> it = testSequences.iterator(); it.hasNext();) {
			TestSequence ts = it.next();
			retval+=(" Test-Sequence \""+ts.getName()+"\"");
			if (!ts.isSuccess()) {
				retval+=(" not");
			}
			retval+=(" successful\n");
			retval+=(lineSeparator);
			int countAll=0;
			int countOk=0;
			boolean displayedFailMsg=false;
			for(Iterator<TestTransaction> itt = ts.getTestTransactions().iterator(); itt.hasNext();) {
				TestTransaction tt = itt.next();
				++countAll;
				if (tt.isSuccess()) {
					++countOk;
					if (displayAll) {
						retval+=("TestTransaction "+countAll+" success\n");
						retval+=(tt.getInputMessage());
						retval+=("\n");
						retval+=(tt.getFailMessage());
						retval+=("\n");
						retval+=(lineSeparator);
					}
				}
				else {
					if (!displayedFailMsg) {
						retval+=("TestTransaction "+countAll+" failed\n");	
						retval+=(tt.getInputMessage());
						retval+=("\n");
						retval+=(tt.getFailMessage());
						retval+=("\n");
						retval+=(lineSeparator);
						displayedFailMsg=true;
					}
				}
			}
			retval+=("\n");
			retval+=(countOk+" of "+countAll+" TestTransactions successful\n");
			retval+=(lineSeparator);
		}
		System.out.print(retval);
		return retval;
	}

	public void cleanRes() {
		depMgr.cleanRes();
		
	}


}
