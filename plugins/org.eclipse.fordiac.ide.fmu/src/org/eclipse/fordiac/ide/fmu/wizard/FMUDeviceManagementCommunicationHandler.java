/*******************************************************************************
 * Copyright (c) 2007 - 2013 4DIAC - consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.fordiac.ide.fmu.Activator;
import org.eclipse.fordiac.ide.fmu.Preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl;
import org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class FMUDeviceManagementCommunicationHandler extends AbstractDeviceManagementCommunicationHandler {
	
	public static class FMUInputOutput{
		
		public enum variableType{
			BOOLEAN,
			INTEGER,
			REAL,
			STRING,
			UNKNOWN,
		}
		
		public enum variableScope{
			IO,
			INTERNAL, 
			PARAM
		}
		
		
		String mName;
		boolean mIsInput;
		variableScope mScope;
		variableType mVarType;
		String mInitialValue;
		
		public FMUInputOutput() {
			this.mName = "";
			this.mIsInput = false;
			this.mVarType = variableType.INTEGER;
			this.mScope = variableScope.INTERNAL;
			this.mInitialValue = "";
		}
		
		public FMUInputOutput(String name, boolean input, variableScope variable, variableType varType, String initialValue){
			this.mName = name;
			this.mIsInput = input;
			this.mVarType = varType;
			this.mScope = variable;
			this.mInitialValue = initialValue;
			if(this.mVarType == variableType.BOOLEAN){
				if(this.mInitialValue.equals("0") || this.mInitialValue.equals("") || this.mInitialValue == null){
					this.mInitialValue = "false"; 
				}else{
					this.mInitialValue = "true";
				}
			}
			
			if(null == mInitialValue || mInitialValue.equals("")){
				switch(varType){
				case BOOLEAN:
					this.mInitialValue = "false";
				case INTEGER:
					this.mInitialValue = "0";
					break;
				case REAL:
					this.mInitialValue = "0.0";
					break;
				case STRING:
					this.mInitialValue = "";
					break;
				case UNKNOWN: 
					break;
				}
			}
		}
		
		public void setType(variableType type) {
			this.mVarType = type;
		}
		
		public String getName(){
			return this.mName;
		}
		
		public boolean getInput(){
			return this.mIsInput;
		}
		
		public variableScope getScope(){
			return this.mScope;
		}
		
		public variableType getVarType() {
			return mVarType;
		}
		
		public String getInitialValue() {
			return mInitialValue;
		}
		
		static variableType getTypeFromString(String text){
			variableType varType;
			if (text.equals("BOOL")) {
				varType = FMUInputOutput.variableType.BOOLEAN;
			} else if (text.equals("BYTE") 
					|| text.equals("WORD") || text.equals("DWORD") || text.equals("LWORD")
					|| text.equals("INT") || text.equals("DINT") || text.equals("LINT")
					|| text.equals("SINT") || text.equals("USINT") 
					|| text.equals("UINT") || text.equals("UDINT") || text.equals("ULINT")
					|| text.equals("ANY_INT")) {
				varType = FMUInputOutput.variableType.INTEGER;
			} else if (text.equals("STRING") || text.equals("WSTRING") || text.equals("ANY_STRING") || text.equals("DATE") || text.equals("DATE_AND_TIME") 
					|| text.equals("TIME_OF_DAY") || text.equals("ANY_DATE") || text.equals("TIME")) {
				varType = FMUInputOutput.variableType.STRING;
			} else if (text.equals("REAL") || text.equals("LREAL") || text.equals("ANY_REAL")){
				varType = FMUInputOutput.variableType.REAL;
			}else{
				varType = FMUInputOutput.variableType.UNKNOWN;
			}
			return varType;
		}
	}
	
	private StringBuffer bootfileResult = new StringBuffer();
	
	public StringBuffer getBootfileResult() {
		return bootfileResult;
	}

	private ArrayList<FMUInputOutput> inputsAndOutputs = new ArrayList<FMUInputOutput>();
	private String error = "";
	
	/** The original MgrID. */
	public String origMgrID;
	
	private Device device;
	
	public String getError(){
		return error;
	}

	static public void createFMU(Device device, List<Resource> resources, HashMap<String, Boolean> librariesToAdd, String directory, Shell shell, IProgressMonitor monitor){
		if(null != directory){
			monitor.beginTask("Generating FMUs for device " + device.getName(), 100);
			String outputName = device.getAutomationSystem().getName() + "_" + device.getName();
			DeploymentCoordinator deployment = DeploymentCoordinator.getInstance();
			FMUDeviceManagementCommunicationHandler FMUFileHandler = new FMUDeviceManagementCommunicationHandler(device);
			deployment.performDeployment(resources.toArray(), FMUFileHandler); //will call the callbacks, sendREQ among them
			monitor.worked(70);
			
			if (!FMUFileHandler.getError().equals("")){
				showErrorMessage("Error in application. Cannot create FMU\n" + FMUFileHandler.getError(), shell);
				return;
			}
			
			File direc = new File(directory);
			if (!direc.exists()){
				if (!direc.mkdir()){
					showErrorMessage("Output folder " + directory + " doesn't exist and couldn't be created\n", shell);
					return;
				}
			}
			
			File destZipFile = new File(directory + File.separatorChar + outputName + ".fmu");
			try {
				int res = SWT.YES;
				if (destZipFile.exists()) {
					MessageBox msgBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
					msgBox.setMessage("Output fmu " + outputName + " file exists, overwrite it?");
					res = msgBox.open();
				}
				if(SWT.YES == res) {
					String tempFolder = Files.createTempDirectory("temp").toString();
					do {
					    File binariesDirectory = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH));
						if(!binariesDirectory.exists() || !binariesDirectory.isDirectory()) { 
							showErrorMessage("Binary directory " + binariesDirectory.toPath() + " does not exist. Check the FMU Page in the preferences", shell);
							break;
						}
						if (!FMUDeviceManagementCommunicationHandler.createTempFoldersAndFiles(tempFolder, outputName, librariesToAdd, shell)){
							break;
						}
						
						if (!writeToFile(
								new File(tempFolder + File.separatorChar + "resources" + File.separatorChar
										+ "forte.fboot").getAbsolutePath(),
								FMUFileHandler.getBootfileResult().toString(), shell)) {
							showErrorMessage("Couldn't write to bootfile in the resource folder of the FMU", shell);
							break;
						}

						if (!writeToFile(
								new File(tempFolder + File.separatorChar + "modelDescription.xml").getAbsolutePath(),
								FMUFileHandler.createModelDescription(outputName).toString(), shell)) {
							showErrorMessage("Couldn't write to the modelDescription of the FMU", shell);
							break;
						}
						
						monitor.worked(90);
						try {
							FileOutputStream fos = new FileOutputStream(destZipFile);
							ZipOutputStream zos = new ZipOutputStream(fos);
							
							FMUDeviceManagementCommunicationHandler.zipFolder(tempFolder, tempFolder,  zos);
							monitor.worked(100);
							
							zos.flush();
							zos.close();
							break;
						} catch (IOException e) {
							MessageBox msgBox = new MessageBox(shell, SWT.RETRY | SWT.ICON_ERROR | SWT.CANCEL);
							msgBox.setMessage(e.getLocalizedMessage() + "\nDo you want to retry?");
							res = msgBox.open();
						}
					}while (SWT.RETRY == res);
					FMUDeviceManagementCommunicationHandler.deleteFolder(tempFolder);
				}
				
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}
	
	private static void showErrorMessage(String message, Shell shell) {
		MessageBox msgBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
		msgBox.setMessage(message);
		msgBox.open();
	}
	
	private static boolean deleteFolder(String pathS) {
		File path = new File(pathS);
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFolder(files[i].getAbsolutePath());
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	private static boolean createTempFoldersAndFiles(String root, String outputName, HashMap<String, Boolean> librariesToAdd, Shell shell){
	/*Creates all the folders structure needed for the FMU*/
		
		File sourceFile = new File(""); //the new if to avoid errors when used later
		File tempFile = new File(root + File.separatorChar + "binaries");
		tempFile.mkdir();
		
		tempFile = new File(root + File.separatorChar + "resources");
		tempFile.mkdir();
		tempFile = new File(root + File.separatorChar + "resources" + File.separatorChar + "forte.fboot");
		try {
			tempFile.createNewFile();
			tempFile = new File(root + File.separatorChar + "modelDescription.xml");
			tempFile.createNewFile();
		} catch (IOException e1) {
			Activator.getDefault().logError(e1.getMessage(), e1);
			MessageBox msgBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
			msgBox.setMessage("Couldn't write the files in the fmu\n");
			msgBox.open();
			return false;
		}
		
		//copy libraries
		int librariesCount = 0;
		ArrayList<String> missingLibraries = new ArrayList<String>();
		for (String entry : librariesToAdd.keySet()) {
			if (librariesToAdd.get(entry)) {
				librariesCount++;
				if (entry.equals(PreferenceConstants.P_FMU_WIN32)) {
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "win32");
					tempFile.mkdir();
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "win32" + File.separatorChar + outputName + ".dll");
					sourceFile = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH) + File.separatorChar + "win32Forte.dll");
				} else if (entry.equals(PreferenceConstants.P_FMU_WIN64)) {
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "win64");
					tempFile.mkdir();
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "win64" + File.separatorChar + outputName + ".dll");
					sourceFile = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH) + File.separatorChar + "win64Forte.dll");
				} else if (entry.equals(PreferenceConstants.P_FMU_LIN32)) {
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "linux32");
					tempFile.mkdir();
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "linux32" + File.separatorChar + outputName + ".so");
					sourceFile = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH) + File.separatorChar + "linux32Forte.so");

				} else if (entry.equals(PreferenceConstants.P_FMU_LIN64)) {
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "linux64");
					tempFile.mkdir();
					tempFile = new File(root + File.separatorChar + "binaries" + File.separatorChar + "linux64" + File.separatorChar + outputName + ".so");
					sourceFile = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH) + File.separatorChar + "linux64Forte.so");
				}
				if (sourceFile.exists()) {
					try {
						Files.copy(sourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						MessageBox msg = new MessageBox(shell, SWT.ERROR);
						msg.setMessage("FMU creation error: Internal error when copying the file " + sourceFile.getAbsolutePath() + " into " + tempFile.getAbsolutePath() + "\n");
						msg.open();
						Activator.getDefault().logError(e.getMessage(), e);
						return false;
					}
				} else {
					missingLibraries.add(sourceFile.getAbsolutePath());
				}
			}
		}
	
		if (0 == librariesCount){
			MessageBox msg = new MessageBox(shell, SWT.ERROR);
			msg.setMessage("FMU creation error: No selected libraries were found.\n");
			msg.open();
			return false;
		}
		
		if (0 != missingLibraries.size()){
			MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING);
			String message = "FMU creation warning: The following libraries were selected but not found:\n";
			for (String lib : missingLibraries){
				message += lib + "\n"; 
			}
			msg.setMessage(message);
			msg.open();
		}
		return true;
	}
	
	/*take the current state of the string buffer and write it to the given file */
	private static boolean writeToFile(String fileName, String toWrite, Shell shell) {
		File outputFile = new File(fileName);

		if (!outputFile.exists()) {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
				return false;
			}
		}
		try {
			PrintWriter boot = new PrintWriter(new FileOutputStream(outputFile));
			boot.write(toWrite);
			boot.flush();
			boot.close();
		} catch (FileNotFoundException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private static void zipFolder(String root, String directoryPath, ZipOutputStream zos){
		
		for (String dirElement : new File(directoryPath).list()) {

			String dirElementPath = directoryPath + "/" + dirElement; //File.separatorChar doesn't work properly

			if (new File(dirElementPath).isDirectory()) {
				FMUDeviceManagementCommunicationHandler.zipFolder(root, dirElementPath, zos);
			} else {
				try {
					ZipEntry ze = new ZipEntry(dirElementPath.substring(root.length() + 1));
					try {
						zos.putNextEntry(ze);

						FileInputStream fis = new FileInputStream(dirElementPath);

						byte[] bytesRead = new byte[1024];

						int bytesNum;
						while ((bytesNum = fis.read(bytesRead)) > 0) {
							zos.write(bytesRead, 0, bytesNum);
						}
						zos.closeEntry();
						fis.close();
						
					} catch (IOException e) {
						Activator.getDefault().logError(e.getMessage(), e);
					}
				} catch (PatternSyntaxException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
		}
	}
	
	/* only the static function of this class should be able to create an instance */
	private FMUDeviceManagementCommunicationHandler(Device device) {
		super();
		this.device = device;
	}


	@Override
	public String sendREQ(String destination, String request) throws IOException {

		if (!request.contains("Action=\"START\"")) { //don't print the start request, since the it should be started from the FMI
			bootfileResult.append(destination + ";" + request + "\n"); 

			if (request.contains("Action=\"CREATE\"><FB ") && !destination.equals("")) {
				int typeIndex = request.indexOf("Type=\"") + 6;
				String type = request.substring(typeIndex, request.indexOf("\"", typeIndex));
				String FBName = request.substring(request.indexOf("Name=\"") + 6, request.indexOf(" Type") - 1);
				if(!populateInputsAndOutputs(FBName, type, device.getResourceNamed(destination).getFBNetwork(), "")) {
					error = getErrorFromRequest(request) + error;
				}
			}

			String info = origMgrID;
			if (!destination.equals("")) { //$NON-NLS-1$
				info += ": " + destination; //$NON-NLS-1$
			}

			postCommandSent(info, destination, request);
		}
		return "";
	}
	
	private boolean populateInputsAndOutputs(String FBName, String FBType, FBNetwork pa_FBNetwork, String previousNames){
		
		boolean IO = false;
		boolean input = false;
		boolean pubSub = false;
		FMUInputOutput.variableType varType = FMUInputOutput.variableType.UNKNOWN;
		if (FBType.equals("IX")) {
			IO = true;
			input = true;
			varType = FMUInputOutput.variableType.BOOLEAN;
		} else if (FBType.equals("QX")) {
			IO = true;
			varType = FMUInputOutput.variableType.BOOLEAN;
		} else if (FBType.equals("IW")) {
			IO = true;
			input = true;
			varType = FMUInputOutput.variableType.INTEGER;
		} else if (FBType.equals("QW")) {
			IO = true;
			varType = FMUInputOutput.variableType.INTEGER;
		} else if ( FBType.regionMatches(0, "PUBLISH_", 0, 8)	|| 
					FBType.regionMatches(0, "SUBSCRIBE_", 0, 10) ||
					FBType.regionMatches(0, "CLIENT_", 0, 7) ||
					FBType.regionMatches(0, "SERVER_", 0, 7)) {

			Value value = pa_FBNetwork.getFBNamed(FBName).getInterface().getVariable("ID").getValue();
			if (value != null && null != value.getValue() && "fmu[]".equals(value.getValue())) { //has some literal
				pubSub = true;
				IO = true;						
			}
		}

		if (IO) {
			if (pubSub) { 
				FB commFB = pa_FBNetwork.getFBNamed(FBName);
				DataType destinationType = null; 
				
				int skipper = 0;
				for(VarDeclaration inputVar : commFB.getInterface().getInputVars()){
					if(skipper < 2) {
						skipper++;
						continue;
					}
					for (DataConnection con : pa_FBNetwork.getDataConnections()) { //TODO: What happens when SD or RD has no connection? And if it has a literal?
						if(con.getDestinationElement().getName().equals(FBName) && con.getDestination().getName().equals(inputVar.getName())){
							destinationType = con.getSource().getType();
							if (null != destinationType) {
								varType = FMUInputOutput.getTypeFromString(destinationType.getName());
								inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName + "@" + con.getSourceElement().getName(), false, FMUInputOutput.variableScope.IO,  varType, ""));	//@ to avoid problems if some part of the interface has the same name
								break;
							}
							break;
						}
					}
				}
				
				skipper = 0;
				for(VarDeclaration outputVar : commFB.getInterface().getOutputVars()){
					if(skipper < 2) {
						skipper++;
						continue;
					}
					for (DataConnection con : pa_FBNetwork.getDataConnections()) {
						if(con.getSourceElement().getName().equals(FBName) && con.getSource().getName().equals(outputVar.getName())){
							destinationType = con.getDestination().getType();
							if (null != destinationType) {
								varType = FMUInputOutput.getTypeFromString(destinationType.getName());
								inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName + "@" + con.getDestinationElement().getName(), true, FMUInputOutput.variableScope.IO, varType, ""));	
								break;
							}
							break;
						}
					}
				}
			}else{
				inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName, input, FMUInputOutput.variableScope.IO, varType, ""));	
			}
		}
		
		getAllVariablesFromInterface(FBName, pa_FBNetwork, previousNames);
		FBType typeFB = pa_FBNetwork.getFBNamed(FBName).getType();
		if (typeFB instanceof BasicFBType) {
			BasicFBType basic = (BasicFBType) typeFB;
			for (VarDeclaration var : basic.getInternalVars()) {
				// store internal variables
				varType = FMUInputOutput.getTypeFromString(var.getTypeName());
				if (FMUInputOutput.variableType.UNKNOWN == varType) {
					continue;
				}
				inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName + "." + var.getName(), false, FMUInputOutput.variableScope.INTERNAL,
						varType, (null != var.getVarInitialization()) ? var.getVarInitialization().getInitialValue() : null));
			}
			// store ECC
			inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName + ".$ECC", false, FMUInputOutput.variableScope.INTERNAL,
					FMUInputOutput.variableType.INTEGER, "0"));
		} else if (typeFB instanceof CompositeFBType) {
			// store internal FB. Take care of the names
			CompositeFBType composite = (CompositeFBType) typeFB;
			for (FBNetworkElement elem : composite.getFBNetwork().getNetworkElements()) {
				if (elem instanceof FBImpl && !(elem instanceof AdapterFBImpl)) {
					FBImpl fb = (FBImpl) elem;
					populateInputsAndOutputs(fb.getName(), fb.getTypeName(), composite.getFBNetwork(), previousNames + FBName + ".");
				}
			}
		}
		return true;	
	}
	
	private void getAllVariablesFromInterface(String FBName, FBNetwork paFBNetwork, String previousNames){
		//Add the interface var and events as variables
		InterfaceList FBInterface = paFBNetwork.getFBNamed(FBName).getInterface();
		
		ArrayList<EList<? extends IInterfaceElement>> interfaceLists = new ArrayList<EList<? extends IInterfaceElement>>();
		interfaceLists.add(FBInterface.getInputVars());
		interfaceLists.add(FBInterface.getOutputVars());
		interfaceLists.add(FBInterface.getEventInputs());
		interfaceLists.add(FBInterface.getEventOutputs());
		
		
		for(EList<? extends IInterfaceElement> list : interfaceLists){
 			for(IInterfaceElement var : list){
 				FMUInputOutput varInfo = new FMUInputOutput();
				if(var instanceof VarDeclaration){
					varInfo = getInfoFromVar(paFBNetwork, FBName, var);
					if (FMUInputOutput.variableType.UNKNOWN == varInfo.getVarType()) {
						continue;
					}
				}else{//event
					//do nothing;
				}
				inputsAndOutputs.add(new FMUInputOutput(previousNames + FBName + "." + var.getName(), false, varInfo.getScope(), varInfo.getVarType(), varInfo.getInitialValue()));
			}
		}
	}
	
	private FMUInputOutput getInfoFromVar(FBNetwork paFBNetwork, String FBName, IInterfaceElement var){
		
		FMUInputOutput returnValue = new FMUInputOutput();
		FB commFB = paFBNetwork.getFBNamed(FBName);
		String destinationType = null; 
		FMUInputOutput.variableType type = FMUInputOutput.variableType.UNKNOWN;
		Value value = var.getValue();
		String initialValue = "";
		
		type = FMUInputOutput.getTypeFromString(commFB.getInterface().getVariable(var.getName()).getTypeName());
		
		if(FMUInputOutput.variableType.UNKNOWN == type){ //It's an abstract type, check the other side of the connection
			boolean isInput = commFB.getInterface().getVariable(var.getName()).isIsInput();
			for (DataConnection con : paFBNetwork.getDataConnections()) {
				if ( (isInput && con.getDestinationElement().getName().equals(FBName)
						&& con.getDestination().getName().equals(var.getName()))
						||
						(!isInput && con.getSourceElement().getName().equals(FBName)
								&& con.getSource().getName().equals(var.getName()))
						)
				{
					destinationType = isInput ? con.getSource().getTypeName() : con.getDestination().getTypeName();
					if (null != destinationType) {
						returnValue.setType(FMUInputOutput.getTypeFromString(destinationType));
					}
					break;
				}
			}
		}
			

		if (value != null && null != value.getValue() && !"".equals(value.getValue())) { // has some literal
			initialValue = value.getValue();
			if (initialValue.contains("%")) {
				String replaced = SystemManager.INSTANCE.getReplacedString(paFBNetwork.getAutomationSystem(),
						initialValue);
				if (replaced != null) {
					initialValue = replaced;
				}
			}
			if (-1 != initialValue.indexOf("#")) {
				if(FMUInputOutput.variableType.UNKNOWN == type){
					type = FMUInputOutput.getTypeFromString(initialValue.substring(0, initialValue.indexOf("#")));
				}
				
				initialValue = initialValue.substring(initialValue.indexOf("#") + 1);
			}
			returnValue = new FMUInputOutput("", false, FMUInputOutput.variableScope.PARAM, type, initialValue);
		}

		
		return returnValue;
	}
	
	
	public StringBuffer createModelDescription(String outputName){
		StringBuffer modelDescription = new StringBuffer();
		modelDescription.append(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<fmiModelDescription\n  fmiVersion=\"2.0\"\n  modelName=\"" + outputName + "\"\n  guid=\"" + outputName + "\">"
				+ "\n\n<CoSimulation\n  modelIdentifier=\"" + outputName + "\"\n  canHandleVariableCommunicationStepSize=\"true\"/>\n\n<LogCategories>\n  <Category name=\"logAll\"/>\n  <Category name=\"logError\"/>\n  <Category name=\"logCalls\"/>\n</LogCategories>");
		
		modelDescription.append("\n\n<ModelVariables>\n");
		
		int outputIndex = 0;
		int noOfOutputs = 0;
		
		ArrayList<ArrayList<FMUInputOutput>> allToWrite = new ArrayList<ArrayList<FMUInputOutput>>();
		allToWrite.add(inputsAndOutputs);
		
		for (ArrayList<FMUInputOutput> list : allToWrite) {
			for (FMUInputOutput element : list) {
				writeVariableToBuffer(modelDescription, outputIndex, element);
				if (!element.getInput() && (element.getScope() == FMUInputOutput.variableScope.IO)) {
					noOfOutputs++;
				}
				outputIndex++;
			}
		}
		
		modelDescription.append("</ModelVariables>\n\n<ModelStructure>\n");
		if (0 != noOfOutputs) {
			modelDescription.append("  <Outputs>\n");

			outputIndex = 0;
			for (FMUInputOutput element : inputsAndOutputs) {
				outputIndex++;
				if (!element.getInput() && (element.getScope() == FMUInputOutput.variableScope.IO)){ 
					modelDescription.append("    <Unknown index=\"" + outputIndex + "\" dependencies=\"\"/>\n");
				}

			}
			modelDescription.append("  </Outputs>\n");
		}
		modelDescription.append("</ModelStructure>\n\n</fmiModelDescription>");
		return modelDescription;
	}
	
	private void writeVariableToBuffer(StringBuffer modelDescription, int outputIndex, FMUInputOutput element){
		
		modelDescription.append("  <ScalarVariable name=\"" + element.getName() + "\" "
				+ "valueReference=\"" + outputIndex + "\" "
				+ "description=\"\" "
				+ "causality=\"" + ((element.getScope() == FMUInputOutput.variableScope.PARAM) ?  
										"parameter"
										: ((element.getScope() == FMUInputOutput.variableScope.INTERNAL) ? 
										"local" 
										: ((element.getInput() ? 
												"input" 
												: "output")))) 
				 + "\" variability=\"" + ((element.getScope() == FMUInputOutput.variableScope.PARAM) ?
						 					"fixed\""
						 					: (FMUInputOutput.variableType.REAL == element.getVarType() ? 
						 					"continuous\"" 
						 					: "discrete\""))
				+ (!element.getInput() || (element.getScope() == FMUInputOutput.variableScope.PARAM) ? 
						" initial=\"exact\">"
						: ">" ) 
				+ "\n    <");
		switch(element.getVarType()){
		case BOOLEAN:
			modelDescription.append("Boolean ");
			break;
		case INTEGER:
			modelDescription.append("Integer ");
			break;
		case REAL:
			modelDescription.append("Real ");
			break;
		case STRING:
			modelDescription.append("String ");
			break;
		default:
			
			break;
		}
		modelDescription.append("start=\"" + element.getInitialValue() + "\"/>\n  </ScalarVariable>\n");
	}
	
	private String getErrorFromRequest(String request){
		String source = request.substring(request.indexOf("Source=") + 8);
		source = source.substring(0, source.indexOf("\""));
		String input = request.substring(request.indexOf("Destination=") + 12);
		input = input.substring(0, input.indexOf("\""));
		return "Parameter " + source + " in input " + input + "\n";
	}

	@Override
	public void connect(String address) throws DeploymentException {
		origMgrID = address;
	}

	@Override
	public void disconnect() throws DeploymentException {
		
	}

	@Override
	public boolean isConnected() {
		return true;
	}
}
