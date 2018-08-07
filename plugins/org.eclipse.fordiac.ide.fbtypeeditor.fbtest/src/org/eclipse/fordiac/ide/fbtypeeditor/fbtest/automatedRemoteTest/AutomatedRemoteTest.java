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
	
	private List<IEC_ANY> sendList = new ArrayList<IEC_ANY>();
	private List<IEC_ANY> recvListTemplate = new ArrayList<IEC_ANY>();
	private List<List<IEC_ANY>> recvList = new ArrayList<List<IEC_ANY>>();
	private Map<String,Integer> InputEventTable = new HashMap<>();
	private Map<String,Integer> OutputEventTable = new HashMap<>();
	private int nNumberOfInputEvents = 0;
	private int nNumberOfOutputEvents = 0;

	private Map<String,Integer> InputVarsTable = new HashMap<>();
	private Map<String,Integer> OutputVarsTable = new HashMap<>();
	
	private List<TestSequence> Tests = null;
	
	private ART_TCPClient TCPTestInterface;
	
	private ART_DeploymentMgr DMgr;
	public String DMgr_response=""; //$NON-NLS-1$
	
	public boolean prepareART (FBType fbType, List<TestSequence> paTestSequences) {
		this.fbType = fbType;

	
		sendList.clear();
		recvList.clear();
		recvList.clear();
		InputEventTable.clear();
		OutputEventTable.clear();
		InputVarsTable.clear();
		OutputVarsTable.clear();
		Tests = null;
		
		boolean retval = (prepareInterface() && prepareTestCases(paTestSequences));
		
		
		return retval;
	}
	
	private boolean prepareTestCases(List<TestSequence> paTestSequences) {
		
		Tests = paTestSequences;
		return true;
	}

	private boolean prepareInterface () {
		fbInterface=""; //$NON-NLS-1$
		IEC_UINT InputEventID = new IEC_UINT();
		IEC_UINT OutputEventID = new IEC_UINT();
		
		try {
			nNumberOfInputEvents = fbType.getInterfaceList().getEventInputs().size();
			nNumberOfOutputEvents = fbType.getInterfaceList().getEventOutputs().size();
		} catch (Exception e) {
			return false;
			//FB not testable without either Input-Events or Output-Events!
		}
		
		if (!((0!= nNumberOfInputEvents) && (0 != nNumberOfOutputEvents))) {
			//FB not testable without either Input-Events or Output-Events!
			return false;
		}

		fbInterface += "EventInputs\n";
		for(Iterator<Event> iterator = fbType.getInterfaceList().getEventInputs().iterator(); iterator.hasNext();) {
			Event entry = iterator.next();
			fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getEventInputs().indexOf(entry)+")\n";
		}
		
		sendList.add(InputEventID);
		if (fbType.getInterfaceList().getInputVars().size()>0) {
			fbInterface +="DataInputs\n";
			for(Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getInputVars().iterator(); iterator.hasNext();) {
				VarDeclaration entry = iterator.next();
				fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getInputVars().indexOf(entry)+")"+
						" -\t"+entry.getType().getName()+
						"\n";
				IEC_ANY newVar=IECDataTypeFactory.getIECTypeByTypename(entry.getType().getName());

				sendList.add(newVar);
				InputVarsTable.put(entry.getName(), (1+FBTHelper.getDIID(fbType, entry.getName())));
			}
		}		




		fbInterface +="EventOutputs\n";
		for(Iterator<Event> iterator = fbType.getInterfaceList().getEventOutputs().iterator(); iterator.hasNext();) {
			Event entry = iterator.next();
			fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getEventOutputs().indexOf(entry)+")\n";
		}

		recvListTemplate.add(OutputEventID);
		if (fbType.getInterfaceList().getOutputVars().size()>0) {
			fbInterface +="DataOutputs\n";
			for(Iterator<VarDeclaration> iterator = fbType.getInterfaceList().getOutputVars().iterator(); iterator.hasNext();) {
				VarDeclaration entry = iterator.next();
				fbInterface +="\t"+entry.getName()+" ("+fbType.getInterfaceList().getOutputVars().indexOf(entry)+")"+
						" -\t"+entry.getType().getName()+"\n";
				IEC_ANY newVar=IECDataTypeFactory.getIECTypeByTypename(entry.getType().getName());

				recvListTemplate.add(newVar);
				OutputVarsTable.put(entry.getName(), (1+FBTHelper.getDOID(fbType, entry.getName())));				
			}
		}		

		
		
		
		return true;
	}

	
	public boolean deployTestRes(String TestChanID, int paID) {
		
		//deploy
		DMgr = new ART_DeploymentMgr(fbType,MgrID,paID);
		boolean isOK = DMgr.deploy(TestChanID);
		if (!isOK) {
			DMgr_response = DMgr.MgmtCommands;
		}
		return isOK;
	}
	
	public boolean configureCommunication(String paCommID) {
		
		
		//getMaxReplies
		int ReplySize = FBTHelper.getMaxOutputPrimitives(Tests);
		recvList.clear();
		for (int i = 0; i<ReplySize+1; i++) {
			ArrayList<IEC_ANY> tempArray = new ArrayList<IEC_ANY>();
			for (Iterator<IEC_ANY> iterator = recvListTemplate.iterator(); iterator.hasNext();) {
				IEC_ANY Var = iterator.next();
				tempArray.add((IEC_ANY)(Var.clone()));
			}
			recvList.add(tempArray);
		}
		
		
		try {
			TCPTestInterface = new ART_TCPClient(paCommID, recvList, ReplySize+1);
		} catch (CommException e) {
			return false;
		}
		
//		System.out.println("Max No of Replies: "+ReplySize);
		return true;
	}
	

	public boolean runTests() {
		if (null == Tests) 
			return false;
		boolean retval=true;
		for (Iterator<TestSequence> iterator = Tests.iterator(); iterator.hasNext();) {
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
		if (null!=paTS) {
			if ((null != paTS.getTestTransactions()) && (paTS.getTestTransactions().size()>0)) {
				for (Iterator<TestTransaction> iterator = paTS.getTestTransactions().iterator(); iterator.hasNext();) {
					TestTransaction TT = iterator.next();
					TestPrimitive TP = TT.getInputPrimitive();
					org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.Event TE= TP.getEvent();
					IEC_ANY EventVar = sendList.get(0);
					EventVar.setValue(Integer.toString(TE.getEventID()));
					//set send-data
					if ((null!=TP.getData()) &&(TP.getData().size()>0)) {
						for (Iterator<DataVariable> dataIterator = TP.getData().iterator();dataIterator.hasNext();) {
							DataVariable DV = dataIterator.next();
							Integer index = InputVarsTable.get(DV.getDataName());
							IEC_ANY Var = sendList.get(index);
							Var.setValue(DV.getDataValue());
						}
					}
					try {
						int SLcounter=-1;
						for (Iterator<IEC_ANY> iteratorSL = sendList.iterator();iteratorSL.hasNext();) {
							IEC_ANY SL_Var = iteratorSL.next();
							if (SLcounter==-1) {
							TT.setInputMessage("Sent Event: "+FBTHelper.getEINameByIndex(fbType, TE.getEventID())+"\n");
							} else {
								TT.setInputMessage(TT.getInputMessage()+
										" "+FBTHelper.getDINameByIndex(fbType, SLcounter)+" := "+SL_Var.toString()+"\n");
							}
							SLcounter++;
						}
						
						TCPTestInterface.sendIECData(sendList);
					} catch (CommException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}
					
					int expectedCount=0;
					if (null != TT.getOutputPrimitives()) {
						expectedCount=TT.getOutputPrimitives().size();
					}

					try {
						if (expectedCount>0) {
							int cancelCondition = 0;
							//check for counter (with timeout)
							while (expectedCount > TCPTestInterface.getCounter()){
								Thread.sleep(100);
								++cancelCondition;
								if (cancelCondition >100) {
									TT.setSuccess(false);
									TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
									return false;
								}
							}
							List<List<IEC_ANY>> receivedDataList = TCPTestInterface.getReceiveDataList();
							
							TT.setSuccess(true);
							TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
							TT.setFailMessage("");
							for (int j=0; j<expectedCount; j++) {
								//check reply-data and set retval
								List<IEC_ANY> OPreceiveData = receivedDataList.get(j);
								if (0<TT.getOutputPrimitives().size()) {
									if (null!=TT.getOutputPrimitives().get(j)) {
										OutputPrimitive tempOP = TT.getOutputPrimitives().get(j).getRelatedOutputPrimitive();
										//Check for Event
										int expectedEventID = FBTHelper.getEOID(fbType, TT.getOutputPrimitives().get(j).getEvent().getEventName());
										IEC_ANY receivedEventID = OPreceiveData.get(0);
										if (receivedEventID instanceof IEC_UINT) {
											if (((IEC_UINT)receivedEventID).getValue() == expectedEventID) {
												TT.setSuccess(true);
												TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
												tempOP.setTestResult(OutputPrimitive.TEST_OK);
												TT.setFailMessage(TT.getFailMessage()+"Received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)receivedEventID).getValue())+" (correct)\n");
											} else {
												TT.setSuccess(false);
												TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
												tempOP.setTestResult(OutputPrimitive.TEST_FAIL);
												TT.setFailMessage(TT.getFailMessage()+"Expected Event "+FBTHelper.getEONameByIndex(fbType, expectedEventID)+" but received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)receivedEventID).getValue())+"\n");
											}
										}
										
										//Check Data
										if (null!= TT.getOutputPrimitives().get(j).getData()) {
											for (Iterator<DataVariable> dataIterator = TT.getOutputPrimitives().get(j).getData().iterator();dataIterator.hasNext();) {
												DataVariable DV = dataIterator.next();
												Integer index = OutputVarsTable.get(DV.getDataName());
												IEC_ANY Var = OPreceiveData.get(index);
												IEC_ANY CheckVar = recvListTemplate.get(index);
												CheckVar.setValue(DV.getDataValue());
												if (!Var.toString().equals(CheckVar.toString())) {
													TT.setFailMessage(TT.getFailMessage()+"  "+DV.getDataName()+" = "+Var.toString()+" (expected: "+CheckVar.toString()+")\n");
													TT.setSuccess(false);
													TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
													tempOP.setTestResult(OutputPrimitive.TEST_FAIL);
												} else {
													TT.setFailMessage(TT.getFailMessage()+"  "+DV.getDataName()+" = "+Var.toString()+" (correct)\n");
												}
											}
										}
									}
								}
								
							}
							
							
						} else {
							//no reply expected -> timeout
							Thread.sleep(2000);
							TT.setSuccess(TCPTestInterface.getCounter()==0);
							if (TT.isSuccess()) {
								TT.setFailMessage("No Event received (correct)\n");
								TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_OK);
							} else {
								TT.getRelatedModelElement().setTestResult(ServiceTransaction.TEST_FAIL);
								try {
									IEC_ANY Event_ID_received = TCPTestInterface.getReceiveDataList().get(0).get(0);
									if (Event_ID_received instanceof IEC_UINT) {
										TT.setFailMessage("Received Event "+FBTHelper.getEONameByIndex(fbType, ((IEC_UINT)Event_ID_received).getValue())+" (unexpected)\n");
									} 
								} catch (Exception e) {

								}
							}
						}
					} catch (InterruptedException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}

					if(!TT.isSuccess()) {
						//first unsuccessful Transaction stops testing of TestSequence
						return false;
					}					
				}
			}
		}
		return true;
		
	}

	public void stopCommunication() {
		try {
			TCPTestInterface.deRegister();
		} catch (CommException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	public String evaluateTestResults(List<TestSequence> testSequences, boolean DisplayAll) {
		String LineSeparator = "********************************************************************************\n";
		String retval="";
		retval+=(LineSeparator);
		retval+=(LineSeparator);
		retval+=(" Test-Results for: "+fbType.getName()+"  \t");
		Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= 
		  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		  String dateNow = formatter.format(currentDate.getTime());
		retval+=("  "+dateNow+"\n");
		retval+=(LineSeparator);
		if (DisplayAll) {
			retval+=(fbInterface);
			retval+=(LineSeparator);
		}
		for(Iterator<TestSequence> it = testSequences.iterator(); it.hasNext();) {
			TestSequence ts = it.next();
			retval+=(" Test-Sequence \""+ts.getName()+"\"");
			if (!ts.isSuccess()) {
				retval+=(" not");
			}
			retval+=(" successful\n");
			retval+=(LineSeparator);
			int count_all=0;
			int count_ok=0;
			boolean displayedFailMsg=false;
			for(Iterator<TestTransaction> itt = ts.getTestTransactions().iterator(); itt.hasNext();) {
				TestTransaction tt = itt.next();
				++count_all;
				if (tt.isSuccess()) {
					++count_ok;
					if (DisplayAll) {
						retval+=("TestTransaction "+count_all+" success\n");
						retval+=(tt.getInputMessage());
						retval+=("\n");
						retval+=(tt.getFailMessage());
						retval+=("\n");
						retval+=(LineSeparator);
					}
				}
				else {
					if (!displayedFailMsg) {
						retval+=("TestTransaction "+count_all+" failed\n");	
						retval+=(tt.getInputMessage());
						retval+=("\n");
						retval+=(tt.getFailMessage());
						retval+=("\n");
						retval+=(LineSeparator);
						displayedFailMsg=true;
					}
				}
			}
			retval+=("\n");
			retval+=(count_ok+" of "+count_all+" TestTransactions successful\n");
			retval+=(LineSeparator);
		}
		System.out.print(retval);
		return retval;
	}

	public void cleanRes() {
		DMgr.cleanRes();
		
	}


}
