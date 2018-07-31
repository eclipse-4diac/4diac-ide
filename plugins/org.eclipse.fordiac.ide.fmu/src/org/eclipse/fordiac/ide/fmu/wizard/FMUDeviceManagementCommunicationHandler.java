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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.fordiac.ide.fmu.Activator;
import org.eclipse.fordiac.ide.fmu.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.AbstractFileManagementHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
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

public final class FMUDeviceManagementCommunicationHandler extends AbstractFileManagementHandler {
	
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
		
		
		private String mName;
		private boolean mIsInput;
		private variableScope mScope;
		private variableType mVarType;
		private String mInitialValue;
		
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

			if (this.mVarType == variableType.BOOLEAN) {
				setBooleanInitValue();
			}
			
			if(null == mInitialValue || mInitialValue.equals("")){
				switch(varType){
				case BOOLEAN:
					this.mInitialValue = "false";
					break;
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
		
		private void setBooleanInitValue() {
			if (this.mInitialValue == null || this.mInitialValue.equals("0") || this.mInitialValue.equals("")) {
				this.mInitialValue = "false";
			} else {
				this.mInitialValue = "true";
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
			} else if (text.matches("BYTE|WORD|DWORD|LWORD|INT|DINT|LINT|SINT|USINT|UINT|UDINT|ULINT|ANY_INT")){
				varType = FMUInputOutput.variableType.INTEGER;
			} else if (text.matches("STRING|WSTRING|ANY_STRING|DATE|DATE_AND_TIME|TIME_OF_DAY|ANY_DATE|TIME")){
				varType = FMUInputOutput.variableType.STRING;
			} else if (text.matches("REAL|LREAL|ANY_REAL")){
				varType = FMUInputOutput.variableType.REAL;
			}else{
				varType = FMUInputOutput.variableType.UNKNOWN;
			}
			return varType;
		}
	}
	
	private static class IOInfo{
		
		private boolean io;
		private boolean input;
		private boolean pubSub;
		private FMUInputOutput.variableType varType;
		
		public boolean isIo() {
			return io;
		}

		public void setIo(boolean io) {
			this.io = io;
		}

		public boolean isInput() {
			return input;
		}

		public void setInput(boolean input) {
			this.input = input;
		}

		public boolean isPubSub() {
			return pubSub;
		}

		public void setPubSub(boolean pubSub) {
			this.pubSub = pubSub;
		}

		public FMUInputOutput.variableType getVarType() {
			return varType;
		}

		public void setVarType(FMUInputOutput.variableType varType) {
			this.varType = varType;
		}
		
		public IOInfo() {
			io = false;
			input = false;
			pubSub = false;
			varType = FMUInputOutput.variableType.UNKNOWN;
		}
	}
	
	private static final String BINARIES_FOLDER_NAME = "binaries";
	
	private static final String RESOURCES_FOLDER_NAME = "resources";
	
	private static final int ZIP_BUFFER = 1024;
	
	private static final int TOTAL_MONITOR = 100;
	
	private List<FMUInputOutput> inputsAndOutputs = new ArrayList<>();

	private Device device;
	
	public static void createFMU(Device device, List<Resource> resources, List<String> librariesToAdd, String directory, Shell shell, IProgressMonitor monitor){
		if(null != directory){
			if (!librariesToAdd.isEmpty()) {
				monitor.beginTask("Generating FMUs for device " + device.getName(), TOTAL_MONITOR);
				String outputName = device.getAutomationSystem().getName() + "_" + device.getName();
				DeploymentCoordinator deployment = DeploymentCoordinator.getInstance();
				FMUDeviceManagementCommunicationHandler fmuFileHandler = new FMUDeviceManagementCommunicationHandler(device);
				deployment.performDeployment(resources.toArray(), fmuFileHandler, null); //will call the callbacks, sendREQ among them
				monitor.worked(TOTAL_MONITOR / 2);
				
				File destZipFile = createZipFile(directory, outputName, shell);
				if (null != destZipFile) {
					createZip(fmuFileHandler, outputName, librariesToAdd, shell, destZipFile);
					monitor.worked(TOTAL_MONITOR);
				}
				
			}else {
				IDeviceManagementCommunicationHandler.showErrorMessage("No selected libraries were found.\n", shell);
			}
		}else {
			IDeviceManagementCommunicationHandler.showErrorMessage("The directory is invalid\n", shell);
		}
	}
	
	private static void createZip(FMUDeviceManagementCommunicationHandler fmuFileHandler, String outputName,
			List<String> librariesToAdd, Shell shell, File destZipFile) {

		String tempFolder = createTempFolderWithFMUStructure(outputName, librariesToAdd, shell);
		if (null != tempFolder) {
			if(writeAllFiles(fmuFileHandler, tempFolder, outputName, shell)) {
				int res = SWT.RETRY;
				do {
					try (FileOutputStream fos = new FileOutputStream(destZipFile)) {
						
						ZipOutputStream zos = new ZipOutputStream(fos);
						zipFolder(tempFolder, tempFolder, zos);

						zos.flush();
						zos.close();
						break;
					} catch (IOException e) {
						MessageBox msgBox = new MessageBox(shell, SWT.RETRY | SWT.ICON_ERROR | SWT.CANCEL);
						msgBox.setMessage(e.getLocalizedMessage() + "\nDo you want to retry?");
						res = msgBox.open();
					}
				} while (SWT.RETRY == res);
			}
			
			deleteFolder(tempFolder);
		}
	}
	
	private static File createZipFile(String directoryPath, String outputName, Shell shell) {
		File direc = new File(directoryPath);
		if (!direc.exists() && !direc.mkdir()) {
			IDeviceManagementCommunicationHandler.showErrorMessage("Output folder " + directoryPath + " doesn't exist and couldn't be created\n", shell);
		} else {
			File destZipFile = new File(directoryPath + File.separatorChar + outputName + ".fmu");

			int res = SWT.YES;
			if (destZipFile.exists()) {
				MessageBox msgBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				msgBox.setMessage("Output fmu " + outputName + " file exists, overwrite it?");
				res = msgBox.open();
			}
			if (SWT.YES == res) {
				return destZipFile;
			}
		}
		
		return null;
	}
	
	
	
	private static String createTempFolderWithFMUStructure(String outputName, List<String> librariesToAdd, Shell shell) {
		try {
			String tempFolder = Files.createTempDirectory("temp").toString();
			File binariesDirectory = new File(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH));
			if (binariesDirectory.exists() && binariesDirectory.isDirectory()) {
				if (createTempFoldersAndFiles(tempFolder, outputName,
						librariesToAdd, shell)) {
					return tempFolder;
				}else {
					IDeviceManagementCommunicationHandler.showErrorMessage("Couldn't create the components inside the temporary folder " + tempFolder, shell);
				}
			} else {
				IDeviceManagementCommunicationHandler.showErrorMessage("Binary directory " + binariesDirectory.toPath()
						+ " does not exist. Check the FMU Page in the preferences", shell);
			}
		} catch (IOException e) {
			IDeviceManagementCommunicationHandler.showErrorMessage("Couldn't create the temporary folder", shell);
		}
		return null;
	}
	
	private static boolean writeAllFiles(FMUDeviceManagementCommunicationHandler fmuFileHandler, String tempFolder,
			String outputName, Shell shell) {
		return (
				fmuFileHandler.writeToBootFile(new File(tempFolder + File.separatorChar + RESOURCES_FOLDER_NAME + File.separatorChar + "forte.fboot")
						.getAbsolutePath(), true, shell) 
				&&
				writeToAnyFile(new File(tempFolder + File.separatorChar + "modelDescription.xml").getAbsolutePath(),
					fmuFileHandler.createModelDescription(outputName).toString(), true, shell));
	}
	
	private static boolean deleteFolder(String pathToDelete) {
		File path = new File(pathToDelete);
		File[] files = path.listFiles();
		
		if (path.exists() && null != files ) {
			for (File file : files) {
				if(!deleteFileOrFolder(file)) {
					return false;
				}
			}
		}
		try {
			Files.delete(Paths.get(path.getAbsolutePath()));
		} catch (IOException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	private static boolean deleteFileOrFolder(File file) {
		if (file.isDirectory()) {
			return deleteFolder(file.getAbsolutePath());
		} else {
			try {
				Files.delete(Paths.get(file.getAbsolutePath()));
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}
	
	private static boolean createTempFoldersAndFiles(String root, String outputName, List<String> librariesToAdd, Shell shell){

		Map<String, String> librariesToNames = new HashMap<>();
		
		librariesToNames.put( PreferenceConstants.P_FMU_WIN32,  PreferenceConstants.P_FMU_WIN32_LIBRARY);
		librariesToNames.put( PreferenceConstants.P_FMU_WIN64,  PreferenceConstants.P_FMU_WIN64_LIBRARY);
		librariesToNames.put( PreferenceConstants.P_FMU_LIN32,  PreferenceConstants.P_FMU_LIN32_LIBRARY);
		librariesToNames.put( PreferenceConstants.P_FMU_LIN64,  PreferenceConstants.P_FMU_LIN64_LIBRARY);
		
		if (createNotBinaryFiles(root, shell)) {
			// copy libraries
			for (String name : librariesToAdd) {
				String libraryName = librariesToNames.get(name);
				if (!copyLibraries(root + File.separatorChar + BINARIES_FOLDER_NAME + File.separatorChar + name,
						outputName,
						Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PATH)
								+ File.separatorChar + libraryName,
						libraryName.substring(libraryName.indexOf('.')), shell)) {
					return false;
				}
			}
		}else {
			return false;
		}

		return true;
	}
	
	private static boolean createNotBinaryFiles(String root, Shell shell) {
		File[] folders = { new File(root + File.separatorChar + BINARIES_FOLDER_NAME), 
				new File(root + File.separatorChar + RESOURCES_FOLDER_NAME)};
		
		for(File folder : folders) {
			if (!folder.mkdir()) {
				IDeviceManagementCommunicationHandler.showErrorMessage("Couldn't create " + folder.getAbsolutePath() + " in the temporary folder", shell);
				return false;
			}
		}
		
		return true;
		
	}
	
	private static boolean copyLibraries(String outputFolder, String outputName, String sourceBinary, String extension, Shell shell) {
		File tempFile = new File(outputFolder);
		if(tempFile.mkdir()) {
			tempFile = new File(outputFolder + File.separatorChar + outputName + extension);
			File sourceFile = new File(sourceBinary);
			
			if (sourceFile.exists()) {
				try {
					Files.copy(sourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					return true;
				} catch (IOException e) {
					IDeviceManagementCommunicationHandler.showErrorMessage("Internal error when copying the file" + sourceFile.getAbsolutePath() + " into " + tempFile.getAbsolutePath() + "\n" + e.getMessage(), shell);
				}
			} else {
				IDeviceManagementCommunicationHandler.showErrorMessage("Library " + sourceFile.getAbsolutePath() + "couldn't be found", shell);
			}
			
		}else {
			IDeviceManagementCommunicationHandler.showErrorMessage("Unable to create " + outputFolder, shell);
		}
		
		return false;
	}
	
	private static void zipFolder(String root, String directoryPath, ZipOutputStream zos){
		
		String[] listOfDirectories = new File(directoryPath).list();
		if (null != listOfDirectories) {
			for (String dirElement : listOfDirectories) {
				zipFoderCore(root, directoryPath, dirElement, zos);
			}
		}
	}
	
	private static void zipFoderCore(String root, String directoryPath, String dirElement, ZipOutputStream zos) {
		String dirElementPath = directoryPath + '/' + dirElement; //there are some problems when File.separatorChar is used here

		if (new File(dirElementPath).isDirectory()) {
			FMUDeviceManagementCommunicationHandler.zipFolder(root, dirElementPath, zos);
		} else {
			try (FileInputStream fis =
					new FileInputStream(dirElementPath)) {
				ZipEntry ze = new ZipEntry(dirElementPath.substring(root.length() + 1));
				zos.putNextEntry(ze);

				byte[] bytesRead = new byte[ZIP_BUFFER];

				int bytesNum;
				while ((bytesNum = fis.read(bytesRead)) > 0) {
					zos.write(bytesRead, 0, bytesNum);
				}
				zos.closeEntry();
				
			} catch (PatternSyntaxException | IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
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
			if (request.contains("Action=\"CREATE\"><FB ") && !destination.equals("")) {
				String type = getSubstringAfterMatch(request, "Type=\"");
				type = type.substring(0, type.indexOf('"'));
				String fbName =  getSubstringAfterMatch(request, "Name=\"");
				fbName = fbName.substring(0, fbName.indexOf('"'));
				populateInputsAndOutputs(fbName, type, device.getResourceNamed(destination).getFBNetwork(), "");
			}
			return super.sendREQ(destination, request);
		}
		return "";
	}
	
	private void handlePubSubVars(FBNetwork fbNetwork, String fbName, String previousNames, EList<VarDeclaration> var, boolean isInput) {
		for(int i = 2; i <  var.size(); i++){ //skip two first variables
			for (DataConnection con : fbNetwork.getDataConnections()) { //If an SD or RD has no connected endpoint and therefore the type is unknown, the variable shouldn't be added
				if(isItsConnection(isInput, con, fbName, var.get(i))){
					IInterfaceElement otherEndpoint = (isInput) ? con.getSource() : con.getDestination();
					FMUInputOutput.variableType varType = FMUInputOutput.getTypeFromString(otherEndpoint.getType().getName());
					if (FMUInputOutput.variableType.UNKNOWN != varType) {
						inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "@" + otherEndpoint.getFBNetworkElement().getName() + "." + otherEndpoint.getName(), !isInput, FMUInputOutput.variableScope.IO, varType, ""));	//@ to avoid problems if some part of the interface has the same name
					}
					break;
				}
			}
		}
	}
	
	private void handleIO(IOInfo info, String previousNames, String fbName, FBNetwork fbNetwork) {
		if (info.isPubSub()) { 
			FB commFB = fbNetwork.getFBNamed(fbName);
			handlePubSubVars(fbNetwork, fbName, previousNames, commFB.getInterface().getInputVars(), true);
			handlePubSubVars(fbNetwork, fbName, previousNames, commFB.getInterface().getOutputVars(), false);
		}else{
			inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName, info.isInput(), FMUInputOutput.variableScope.IO, info.getVarType(), ""));	
		}
	}
	
	private IOInfo getInfoFromIOAndComm(String fbName, String fbType, FBNetwork fbNetwork){
		IOInfo returnValue;
		if (fbType.matches("IX|QX|IW|QW")) {
			returnValue = getInfoFromIO(fbType);
		} else if ( 0 == fbType.indexOf("PUBLISH_")   || 
				    0 == fbType.indexOf("SUBSCRIBE_") || 
				    0 == fbType.indexOf("CLIENT_")    || 
				    0 == fbType.indexOf("SERVER_")) {
			returnValue = getInfoFromComm(fbName, fbNetwork);
		}else {
			returnValue = new IOInfo();
		}
		
		return returnValue;
	}
	
	private IOInfo getInfoFromIO(String fbType){
		
		IOInfo returnValue = new IOInfo();
		returnValue.setIo(true);
		returnValue.setPubSub(false);
		if (fbType.equals("IX")) {
			returnValue.setInput(true);
			returnValue.setVarType(FMUInputOutput.variableType.BOOLEAN);
		} else if (fbType.equals("QX")) {
			returnValue.setVarType(FMUInputOutput.variableType.BOOLEAN);
		} else if (fbType.equals("IW")) {
			returnValue.setInput(true);
			returnValue.setVarType(FMUInputOutput.variableType.INTEGER);
		} else if (fbType.equals("QW")) {
			returnValue.setVarType(FMUInputOutput.variableType.INTEGER);
		}
		return returnValue;
	}
	
	private IOInfo getInfoFromComm(String fbName, FBNetwork fbNetwork){
		IOInfo returnValue = new IOInfo();
		returnValue.setIo(true);
		returnValue.setPubSub(true);
		Value value = fbNetwork.getFBNamed(fbName).getInterface().getVariable("ID").getValue();
		if (value != null && null != value.getValue() && "fmu[]".equals(value.getValue())) { //has some literal
			returnValue.setPubSub(true);
			returnValue.setIo(true);						
		}
		return returnValue;
	}
	
	private void populateInputsAndOutputs(String fbName, String fbType, FBNetwork fbNetwork, String previousNames){
		IOInfo info = getInfoFromIOAndComm(fbName, fbType, fbNetwork);
		if (info.isIo()) {
			handleIO(info, previousNames, fbName, fbNetwork);
		}
		
		getAllVariablesFromInterface(fbName, fbNetwork, previousNames);
		FBType typeFB = fbNetwork.getFBNamed(fbName).getType();
		if (typeFB instanceof BasicFBType) {
			handleBasicFB((BasicFBType) typeFB, previousNames, fbName);
		} else if (typeFB instanceof CompositeFBType) {
			handleCompositeFB((CompositeFBType) typeFB, previousNames, fbName);
		}
	}
	
	private void handleBasicFB(BasicFBType basic, String previousNames, String fbName ) {
		if(basic.getName().matches("E_CTU|E_D_FF|E_DEMUX|E_MERGE|E_PERMIT|E_REND|E_RS|E_SELECT|E_SPLIT|E_SR|E_SWITCH")) { //these FBs are implemented as SIFB in the 4diac-RTE
			return;
		}
		
		for (VarDeclaration var : basic.getInternalVars()) {
			// store internal variables
			FMUInputOutput.variableType varType = FMUInputOutput.getTypeFromString(var.getTypeName());
			if (FMUInputOutput.variableType.UNKNOWN == varType) {
				continue;
			}
			inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "." + var.getName(), false, FMUInputOutput.variableScope.INTERNAL,
					varType, (null != var.getVarInitialization()) ? var.getVarInitialization().getInitialValue() : null));
		}
		// store ECC
		inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + ".$ECC", false, FMUInputOutput.variableScope.INTERNAL,
				FMUInputOutput.variableType.INTEGER, "0"));
	}
	
	private void handleCompositeFB(CompositeFBType composite, String previousNames, String fbName) {
		if(composite.getName().matches("E_CYCLE|E_F_TRIG|E_R_TRIG|E_TimeOut")) { //these FBs are implemented as SIFB in the 4diac-RTE
			return;
		}
		
		// store internal FB. Take care of the names
		for (FBNetworkElement elem : composite.getFBNetwork().getNetworkElements()) {
			if (elem instanceof FBImpl && !(elem instanceof AdapterFBImpl)) {
				FBImpl fb = (FBImpl) elem;
				populateInputsAndOutputs(fb.getName(), fb.getTypeName(), composite.getFBNetwork(), previousNames + fbName + ".");
			}
		}
	}
	
	private void getAllVariablesFromInterface(String fbName, FBNetwork paFBNetwork, String previousNames){
		//Add the interface var and events as variables
		InterfaceList fbInterface = paFBNetwork.getFBNamed(fbName).getInterface();
		
		ArrayList<EList<? extends IInterfaceElement>> interfaceLists = new ArrayList<>();
		interfaceLists.add(fbInterface.getInputVars());
		interfaceLists.add(fbInterface.getOutputVars());
		interfaceLists.add(fbInterface.getEventInputs());
		interfaceLists.add(fbInterface.getEventOutputs());
		
		
		for(EList<? extends IInterfaceElement> list : interfaceLists){
 			for(IInterfaceElement var : list){
 				FMUInputOutput varInfo = new FMUInputOutput();
				if(var instanceof VarDeclaration){
					varInfo = getInfoFromVar(paFBNetwork, fbName, var);
					if (FMUInputOutput.variableType.UNKNOWN == varInfo.getVarType()) {
						continue;
					}
				}else{//event: don't do anything
					
				}
				inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "." + var.getName(), false, varInfo.getScope(), varInfo.getVarType(), varInfo.getInitialValue()));
			}
		}
	}
	
	private FMUInputOutput getInfoFromVar(FBNetwork paFBNetwork, String fbName, IInterfaceElement var){
		
		FMUInputOutput returnValue = new FMUInputOutput();
		FB commFB = paFBNetwork.getFBNamed(fbName);
		FMUInputOutput.variableType type;
		Value value = var.getValue();
		String initialValue = "";
		
		type = FMUInputOutput.getTypeFromString(commFB.getInterface().getVariable(var.getName()).getTypeName());
		
		if(FMUInputOutput.variableType.UNKNOWN == type){ //It's an abstract type, check the other side of the connection
			returnValue.setType(getInfoFromConnectedFB(commFB, paFBNetwork, fbName, var));
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
			if (-1 != initialValue.indexOf('#')) {
				if(FMUInputOutput.variableType.UNKNOWN == type){
					type = FMUInputOutput.getTypeFromString(initialValue.substring(0, initialValue.indexOf('#')));
				}
				
				initialValue = initialValue.substring(initialValue.indexOf('#') + 1);
			}
			returnValue = new FMUInputOutput("", false, FMUInputOutput.variableScope.PARAM, type, initialValue);
		}

		
		return returnValue;
	}
	
	private FMUInputOutput.variableType getInfoFromConnectedFB(FB commFB, FBNetwork paFBNetwork, String fbName, IInterfaceElement var) {
		FMUInputOutput.variableType returnValue = FMUInputOutput.variableType.UNKNOWN;
		boolean isInput = commFB.getInterface().getVariable(var.getName()).isIsInput();
		for (DataConnection con : paFBNetwork.getDataConnections()) {
			if (isItsConnection(isInput, con, fbName, var))
			{
				String destinationType = isInput ? con.getSource().getTypeName() : con.getDestination().getTypeName();
				if (null != destinationType) {
					returnValue = FMUInputOutput.getTypeFromString(destinationType);
				}
				break;
			}
		}
		return returnValue;
	}
	
	private boolean isItsConnection(boolean isInput, DataConnection con, String fbName, IInterfaceElement var) {
		if(isInput){
			return (con.getDestinationElement().getName().equals(fbName)
					&& con.getDestination().getName().equals(var.getName()));
		}else {
			return (con.getSourceElement().getName().equals(fbName)
					&& con.getSource().getName().equals(var.getName()));
		}
	}
	
	
	public StringBuilder createModelDescription(String outputName){
		StringBuilder modelDescription = new StringBuilder();
		modelDescription.append(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<fmiModelDescription\n  fmiVersion=\"2.0\"\n  modelName=\"" + outputName + "\"\n  guid=\"" + outputName + "\">"
				+ "\n\n<CoSimulation\n  modelIdentifier=\"" + outputName + "\"\n  canHandleVariableCommunicationStepSize=\"true\"/>\n\n<LogCategories>\n  <Category name=\"logAll\"/>\n  <Category name=\"logError\"/>\n  <Category name=\"logCalls\"/>\n</LogCategories>");
		
		modelDescription.append("\n\n<ModelVariables>\n");
		
		int outputIndex = 0;
		int noOfOutputs = 0;
		
		List<List<FMUInputOutput>> allToWrite = new ArrayList<>();
		allToWrite.add(inputsAndOutputs);
		
		for (List<FMUInputOutput> list : allToWrite) {
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
	
	private void writeVariableToBuffer(StringBuilder modelDescription, int outputIndex, FMUInputOutput element){
		
		String causality = getCausality(element);
		String variability= getVariability(element);
		
		modelDescription.append("  <ScalarVariable name=\"" + element.getName() + "\" "
				+ "valueReference=\"" + outputIndex + "\" "
				+ "description=\"\" "
				+ "causality=\"" + causality
				 + "\" variability=\"" + variability + "\""
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
	
	private String getCausality(FMUInputOutput element) {
		switch(element.getScope()) {
		case PARAM:
			return "parameter";
		case INTERNAL:
			return "local";
		case IO:
			return element.getInput() ? "input" : "output";
		default:
			return "";
		}
	}
	
	private String getVariability(FMUInputOutput element) {
		if(FMUInputOutput.variableScope.PARAM == element.getScope()) {
			return "fixed";
		}
		
		return (FMUInputOutput.variableType.REAL == element.getVarType()) ?  "continuous" : "discrete";
	}
	
	private String getSubstringAfterMatch(String source, String toLook) {
		return source.substring(source.indexOf(toLook) + toLook.length());
	}
}
