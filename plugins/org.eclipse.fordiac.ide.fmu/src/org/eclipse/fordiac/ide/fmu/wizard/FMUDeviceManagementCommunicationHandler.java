/*******************************************************************************
 * Copyright (c) 2017, 2023 fortiss GmbH, Johanees Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Prankur Agarwal
 *     - add handling for Internal Constant Variables
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.AbstractFileManagementHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.fmu.Messages;
import org.eclipse.fordiac.ide.fmu.preferences.FMUPreferenceConstants;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
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
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public final class FMUDeviceManagementCommunicationHandler extends AbstractFileManagementHandler {

	public static class FMUInputOutput {

		public enum variableType {
			BOOLEAN, INTEGER, REAL, STRING, UNKNOWN,
		}

		public enum variableScope {
			IO, INTERNAL, PARAM
		}

		private final String mName;
		private final boolean mIsInput;
		private final variableScope mScope;
		private variableType mVarType;
		private String mInitialValue;

		public FMUInputOutput() {
			this.mName = ""; //$NON-NLS-1$
			this.mIsInput = false;
			this.mVarType = variableType.INTEGER;
			this.mScope = variableScope.INTERNAL;
			this.mInitialValue = ""; //$NON-NLS-1$
		}

		public FMUInputOutput(final String name, final boolean input, final variableScope variable,
				final variableType varType, final String initialValue) {
			this.mName = name;
			this.mIsInput = input;
			this.mVarType = varType;
			this.mScope = variable;
			this.mInitialValue = initialValue;

			if (this.mVarType == variableType.BOOLEAN) {
				setBooleanInitValue();
			}

			if (null == mInitialValue || mInitialValue.equals("")) { //$NON-NLS-1$
				switch (varType) {
				case BOOLEAN:
					this.mInitialValue = "false"; //$NON-NLS-1$
					break;
				case INTEGER:
					this.mInitialValue = "0"; //$NON-NLS-1$
					break;
				case REAL:
					this.mInitialValue = "0.0"; //$NON-NLS-1$
					break;
				case STRING:
					this.mInitialValue = ""; //$NON-NLS-1$
					break;
				case UNKNOWN:
					break;
				default:
				}
			}
		}

		private void setBooleanInitValue() {
			if (this.mInitialValue == null || this.mInitialValue.equals("0") || this.mInitialValue.equals("")) { //$NON-NLS-1$ //$NON-NLS-2$
				this.mInitialValue = "false"; //$NON-NLS-1$
			} else {
				this.mInitialValue = "true"; //$NON-NLS-1$
			}
		}

		public void setType(final variableType type) {
			this.mVarType = type;
		}

		public String getName() {
			return this.mName;
		}

		public boolean getInput() {
			return this.mIsInput;
		}

		public variableScope getScope() {
			return this.mScope;
		}

		public variableType getVarType() {
			return mVarType;
		}

		public String getInitialValue() {
			return mInitialValue;
		}

		private static String orString(final String... strings) {
			return String.join("|", strings); //$NON-NLS-1$
		}

		static variableType getTypeFromString(final String text) {
			variableType varType;
			if (text.equals(FordiacKeywords.BOOL)) {
				varType = FMUInputOutput.variableType.BOOLEAN;
			} else if (text.matches(orString(FordiacKeywords.BYTE, FordiacKeywords.WORD, FordiacKeywords.DWORD,
					FordiacKeywords.LWORD, FordiacKeywords.INT, FordiacKeywords.DINT, FordiacKeywords.LINT,
					FordiacKeywords.SINT, FordiacKeywords.USINT, FordiacKeywords.UINT, FordiacKeywords.UDINT,
					FordiacKeywords.ULINT, FordiacKeywords.ANY_INT))) {
				varType = FMUInputOutput.variableType.INTEGER;
			} else if (text.matches(orString(FordiacKeywords.STRING, FordiacKeywords.WSTRING,
					FordiacKeywords.ANY_STRING, FordiacKeywords.DATE, FordiacKeywords.DATE_AND_TIME,
					FordiacKeywords.TIME_OF_DAY, FordiacKeywords.ANY_DATE, FordiacKeywords.TIME))) {
				varType = FMUInputOutput.variableType.STRING;
			} else if (text.matches(orString(FordiacKeywords.REAL, FordiacKeywords.LREAL, FordiacKeywords.ANY_REAL))) {
				varType = FMUInputOutput.variableType.REAL;
			} else {
				varType = FMUInputOutput.variableType.UNKNOWN;
			}
			return varType;
		}
	}

	private static class IOInfo {

		private boolean io;
		private boolean input;
		private boolean pubSub;
		private FMUInputOutput.variableType varType;

		public boolean isIo() {
			return io;
		}

		public void setIo(final boolean io) {
			this.io = io;
		}

		public boolean isInput() {
			return input;
		}

		public void setInput(final boolean input) {
			this.input = input;
		}

		public boolean isPubSub() {
			return pubSub;
		}

		public void setPubSub(final boolean pubSub) {
			this.pubSub = pubSub;
		}

		public FMUInputOutput.variableType getVarType() {
			return varType;
		}

		public void setVarType(final FMUInputOutput.variableType varType) {
			this.varType = varType;
		}

		public IOInfo() {
			io = false;
			input = false;
			pubSub = false;
			varType = FMUInputOutput.variableType.UNKNOWN;
		}
	}

	private static final String BINARIES_FOLDER_NAME = "binaries"; //$NON-NLS-1$

	private static final String RESOURCES_FOLDER_NAME = "resources"; //$NON-NLS-1$

	private static final int ZIP_BUFFER = 1024;

	private static final int TOTAL_MONITOR = 100;

	private final List<FMUInputOutput> inputsAndOutputs = new ArrayList<>();

	private final Device device;

	public static void createFMU(final Device device, final List<Resource> resources, final List<String> librariesToAdd,
			final String directory, final Shell shell, final IProgressMonitor monitor) {
		if (null != directory) {
			if (!librariesToAdd.isEmpty()) {
				monitor.beginTask(
						MessageFormat.format(Messages.FMUDeviceManagementCommunicationHandler_GeneratingFMUsForDevice,
								device.getName()),
						TOTAL_MONITOR);
				final String outputName = device.getAutomationSystem().getName() + "_" + device.getName(); //$NON-NLS-1$
				final FMUDeviceManagementCommunicationHandler fmuFileHandler = new FMUDeviceManagementCommunicationHandler(
						device);
				DeploymentCoordinator.performDeployment(resources.toArray(), fmuFileHandler, null); // will
				// call the callbacks, sendREQ among them
				monitor.worked(TOTAL_MONITOR / 2);

				final File destZipFile = createZipFile(directory, outputName, shell);
				if (null != destZipFile) {
					createZip(fmuFileHandler, outputName, librariesToAdd, shell, destZipFile);
					monitor.worked(TOTAL_MONITOR);
				}

			} else {
				IDeviceManagementCommunicationHandler.showErrorMessage(
						Messages.FMUDeviceManagementCommunicationHandler_NoSelectedLibrariesWereFound, shell);
			}
		} else {
			IDeviceManagementCommunicationHandler
					.showErrorMessage(Messages.FMUDeviceManagementCommunicationHandler_TheDirectoryIsInvalid, shell);
		}
	}

	private static void createZip(final FMUDeviceManagementCommunicationHandler fmuFileHandler, final String outputName,
			final List<String> librariesToAdd, final Shell shell, final File destZipFile) {

		final String tempFolder = createTempFolderWithFMUStructure(outputName, librariesToAdd, shell);
		if (null != tempFolder) {
			if (writeAllFiles(fmuFileHandler, tempFolder, outputName, shell)) {
				int res = SWT.RETRY;
				do {
					try (FileOutputStream fos = new FileOutputStream(destZipFile)) {

						final ZipOutputStream zos = new ZipOutputStream(fos);
						zipFolder(tempFolder, tempFolder, zos);

						zos.flush();
						zos.close();
						break;
					} catch (final IOException e) {
						final MessageBox msgBox = new MessageBox(shell, SWT.RETRY | SWT.ICON_ERROR | SWT.CANCEL);
						msgBox.setMessage(
								MessageFormat.format(Messages.FMUDeviceManagementCommunicationHandler_DoYouWantToRetry,
										e.getLocalizedMessage()));
						res = msgBox.open();
					}
				} while (SWT.RETRY == res);
			}

			deleteFolder(tempFolder);
		}
	}

	private static File createZipFile(final String directoryPath, final String outputName, final Shell shell) {
		final File direc = new File(directoryPath);
		if (!direc.exists() && !direc.mkdir()) {
			IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
					Messages.FMUDeviceManagementCommunicationHandler_OutputFolderDoesNotExistAndCouldNotBeCreated,
					directoryPath), shell);
		} else {
			final File destZipFile = new File(directoryPath + File.separatorChar + outputName + ".fmu"); //$NON-NLS-1$

			int res = SWT.YES;
			if (destZipFile.exists()) {
				final MessageBox msgBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				msgBox.setMessage(MessageFormat.format(
						Messages.FMUDeviceManagementCommunicationHandler_OutputFMUFileExistsOverwriteIt, outputName));
				res = msgBox.open();
			}
			if (SWT.YES == res) {
				return destZipFile;
			}
		}

		return null;
	}

	private static String createTempFolderWithFMUStructure(final String outputName, final List<String> librariesToAdd,
			final Shell shell) {
		try {
			final String tempFolder = Files.createTempDirectory("temp").toString(); //$NON-NLS-1$
			final File binariesDirectory = new File(
					FMUPreferenceConstants.STORE.getString(FMUPreferenceConstants.P_PATH));
			if (binariesDirectory.exists() && binariesDirectory.isDirectory()) {
				if (createTempFoldersAndFiles(tempFolder, outputName, librariesToAdd, shell)) {
					return tempFolder;
				}
				IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
						Messages.FMUDeviceManagementCommunicationHandler_CouldNotCreateTheComponentsInsideTheTemporaryFolder,
						tempFolder), shell);
			} else {
				IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
						Messages.FMUDeviceManagementCommunicationHandler_BinaryDirectoryDoesNotExist,
						binariesDirectory.toPath()), shell);
			}
		} catch (final IOException e) {
			IDeviceManagementCommunicationHandler.showErrorMessage(
					Messages.FMUDeviceManagementCommunicationHandler_CouldNotCreateTheTemporaryFolder, shell);
		}
		return null;
	}

	private static boolean writeAllFiles(final FMUDeviceManagementCommunicationHandler fmuFileHandler,
			final String tempFolder, final String outputName, final Shell shell) {
		return (fmuFileHandler.writeToBootFile(
				new File(tempFolder + File.separatorChar + RESOURCES_FOLDER_NAME + File.separatorChar + "forte.fboot") //$NON-NLS-1$
						.getAbsolutePath(),
				true, shell)
				&& writeToAnyFile(new File(tempFolder + File.separatorChar + "modelDescription.xml").getAbsolutePath(), //$NON-NLS-1$
						fmuFileHandler.createModelDescription(outputName).toString(), true, shell));
	}

	private static boolean deleteFolder(final String pathToDelete) {
		final File path = new File(pathToDelete);
		final File[] files = path.listFiles();

		if (path.exists() && null != files) {
			for (final File file : files) {
				if (!deleteFileOrFolder(file)) {
					return false;
				}
			}
		}
		try {
			Files.delete(Paths.get(path.getAbsolutePath()));
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return false;
		}

		return true;
	}

	private static boolean deleteFileOrFolder(final File file) {
		if (file.isDirectory()) {
			return deleteFolder(file.getAbsolutePath());
		}
		try {
			Files.delete(Paths.get(file.getAbsolutePath()));
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return false;
		}
		return true;
	}

	private static boolean createTempFoldersAndFiles(final String root, final String outputName,
			final List<String> librariesToAdd, final Shell shell) {

		final Map<String, String> librariesToNames = new HashMap<>();

		librariesToNames.put(FMUPreferenceConstants.P_FMU_WIN32, FMUPreferenceConstants.P_FMU_WIN32_LIBRARY);
		librariesToNames.put(FMUPreferenceConstants.P_FMU_WIN64, FMUPreferenceConstants.P_FMU_WIN64_LIBRARY);
		librariesToNames.put(FMUPreferenceConstants.P_FMU_LIN32, FMUPreferenceConstants.P_FMU_LIN32_LIBRARY);
		librariesToNames.put(FMUPreferenceConstants.P_FMU_LIN64, FMUPreferenceConstants.P_FMU_LIN64_LIBRARY);

		if (!createNotBinaryFiles(root, shell)) {
			return false;
		}
		// copy libraries
		for (final String name : librariesToAdd) {
			final String libraryName = librariesToNames.get(name);
			if (!copyLibraries(root + File.separatorChar + BINARIES_FOLDER_NAME + File.separatorChar + name, outputName,
					FMUPreferenceConstants.STORE.getString(FMUPreferenceConstants.P_PATH) + File.separatorChar
							+ libraryName,
					libraryName.substring(libraryName.indexOf('.')), shell)) {
				return false;
			}
		}

		return true;
	}

	private static boolean createNotBinaryFiles(final String root, final Shell shell) {
		final File[] folders = { new File(root + File.separatorChar + BINARIES_FOLDER_NAME),
				new File(root + File.separatorChar + RESOURCES_FOLDER_NAME) };

		for (final File folder : folders) {
			if (!folder.mkdir()) {
				IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
						Messages.FMUDeviceManagementCommunicationHandler_CouldNotCreateFolderInTheTemporaryFolder,
						folder.getAbsolutePath()), shell);
				return false;
			}
		}

		return true;

	}

	private static boolean copyLibraries(final String outputFolder, final String outputName, final String sourceBinary,
			final String extension, final Shell shell) {
		File tempFile = new File(outputFolder);
		if (tempFile.mkdir()) {
			tempFile = new File(outputFolder + File.separatorChar + outputName + extension);
			final File sourceFile = new File(sourceBinary);

			if (sourceFile.exists()) {
				try {
					Files.copy(sourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					return true;
				} catch (final IOException e) {
					IDeviceManagementCommunicationHandler.showErrorMessage(
							MessageFormat.format(Messages.FMUDeviceManagementCommunicationHandler_InternalCopyingError,
									sourceFile.getAbsolutePath(), tempFile.getAbsolutePath(), e.getMessage()),
							shell);
				}
			} else {
				IDeviceManagementCommunicationHandler.showErrorMessage(
						MessageFormat.format(Messages.FMUDeviceManagementCommunicationHandler_LibraryCouldNotBeFound,
								sourceFile.getAbsolutePath()),
						shell);
			}

		} else {
			IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
					Messages.FMUDeviceManagementCommunicationHandler_UnableToCreateFolder, outputFolder), shell);
		}

		return false;
	}

	private static void zipFolder(final String root, final String directoryPath, final ZipOutputStream zos) {

		final String[] listOfDirectories = new File(directoryPath).list();
		if (null != listOfDirectories) {
			for (final String dirElement : listOfDirectories) {
				zipFoderCore(root, directoryPath, dirElement, zos);
			}
		}
	}

	private static void zipFoderCore(final String root, final String directoryPath, final String dirElement,
			final ZipOutputStream zos) {
		final String dirElementPath = directoryPath + '/' + dirElement; // there are some problems when
																		// File.separatorChar is
		// used here

		if (new File(dirElementPath).isDirectory()) {
			FMUDeviceManagementCommunicationHandler.zipFolder(root, dirElementPath, zos);
		} else {
			try (FileInputStream fis = new FileInputStream(dirElementPath)) {
				final ZipEntry ze = new ZipEntry(dirElementPath.substring(root.length() + 1));
				zos.putNextEntry(ze);

				final byte[] bytesRead = new byte[ZIP_BUFFER];

				int bytesNum;
				while ((bytesNum = fis.read(bytesRead)) > 0) {
					zos.write(bytesRead, 0, bytesNum);
				}
				zos.closeEntry();

			} catch (PatternSyntaxException | IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}

		}
	}

	/*
	 * only the static function of this class should be able to create an instance
	 */
	private FMUDeviceManagementCommunicationHandler(final Device device) {
		this.device = device;
	}

	@Override
	public String sendREQ(final String destination, final String request) throws IOException {

		if (!request.contains("Action=\"START\"")) { // don't print the start request, since the it should //$NON-NLS-1$
			// be started from the FMI
			if (request.contains("Action=\"CREATE\"><FB ") && !destination.equals("")) { //$NON-NLS-1$ //$NON-NLS-2$
				String type = getSubstringAfterMatch(request, "Type=\"");//$NON-NLS-1$
				type = type.substring(0, type.indexOf('"'));
				String fbName = getSubstringAfterMatch(request, "Name=\"");//$NON-NLS-1$
				fbName = fbName.substring(0, fbName.indexOf('"'));
				populateInputsAndOutputs(fbName, type, device.getResourceNamed(destination).getFBNetwork(),
						destination + "."); //$NON-NLS-1$
			}
			return super.sendREQ(destination, request);
		}
		return ""; //$NON-NLS-1$
	}

	private void handlePubSubVars(final FBNetwork fbNetwork, final String fbName, final String previousNames,
			final EList<VarDeclaration> vars, final boolean isInput) {
		for (int i = 2; i < vars.size(); i++) { // skip two first variables
			for (final DataConnection con : fbNetwork.getDataConnections()) { // If an SD or RD has no connected
																				// endpoint and
				// therefore the type is unknown, the variable
				// shouldn't be added
				if (isItsConnection(isInput, con, fbName, vars.get(i))) {
					final IInterfaceElement otherEndpoint = (isInput) ? con.getSource() : con.getDestination();
					final FMUInputOutput.variableType varType = FMUInputOutput
							.getTypeFromString(otherEndpoint.getType().getName());
					if (FMUInputOutput.variableType.UNKNOWN != varType) {
						inputsAndOutputs.add(new FMUInputOutput(
								previousNames + fbName + "@" + otherEndpoint.getFBNetworkElement().getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
										+ otherEndpoint.getName(),
								!isInput, FMUInputOutput.variableScope.IO, varType, "")); // @ to avoid //$NON-NLS-1$
						// problems if some part of
						// the interface has the
						// same name
					}
					break;
				}
			}
		}
	}

	private void handleIO(final IOInfo info, final String previousNames, final String fbName,
			final FBNetwork fbNetwork) {
		if (info.isPubSub()) {
			final FB commFB = fbNetwork.getFBNamed(fbName);
			handlePubSubVars(fbNetwork, fbName, previousNames, commFB.getInterface().getInputVars(), true);
			handlePubSubVars(fbNetwork, fbName, previousNames, commFB.getInterface().getOutputVars(), false);
		} else {
			inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName, info.isInput(),
					FMUInputOutput.variableScope.IO, info.getVarType(), "")); //$NON-NLS-1$
		}
	}

	private static IOInfo getInfoFromIOAndComm(final String fbName, final String fbType, final FBNetwork fbNetwork) {
		IOInfo returnValue;
		if (fbType.matches("IX|QX|IW|QW")) { //$NON-NLS-1$
			returnValue = getInfoFromIO(fbType);
		} else if (0 == fbType.indexOf("PUBLISH_") || //$NON-NLS-1$
				0 == fbType.indexOf("SUBSCRIBE_") || //$NON-NLS-1$
				0 == fbType.indexOf("CLIENT_") || //$NON-NLS-1$
				0 == fbType.indexOf("SERVER_")) { //$NON-NLS-1$
			returnValue = getInfoFromComm(fbName, fbNetwork);
		} else {
			returnValue = new IOInfo();
		}

		return returnValue;
	}

	private static IOInfo getInfoFromIO(final String fbType) {

		final IOInfo returnValue = new IOInfo();
		returnValue.setIo(true);
		returnValue.setPubSub(false);
		if (fbType.equals("IX")) { //$NON-NLS-1$
			returnValue.setInput(true);
			returnValue.setVarType(FMUInputOutput.variableType.BOOLEAN);
		} else if (fbType.equals("QX")) { //$NON-NLS-1$
			returnValue.setVarType(FMUInputOutput.variableType.BOOLEAN);
		} else if (fbType.equals("IW")) { //$NON-NLS-1$
			returnValue.setInput(true);
			returnValue.setVarType(FMUInputOutput.variableType.INTEGER);
		} else if (fbType.equals("QW")) { //$NON-NLS-1$
			returnValue.setVarType(FMUInputOutput.variableType.INTEGER);
		}
		return returnValue;
	}

	private static IOInfo getInfoFromComm(final String fbName, final FBNetwork fbNetwork) {
		final IOInfo returnValue = new IOInfo();
		returnValue.setIo(true);
		returnValue.setPubSub(true);
		final Value value = fbNetwork.getFBNamed(fbName).getInterface().getVariable("ID").getValue(); //$NON-NLS-1$
		if (value != null && null != value.getValue() && "fmu[]".equals(value.getValue())) { // has some //$NON-NLS-1$
			// literal
			returnValue.setPubSub(true);
			returnValue.setIo(true);
		}
		return returnValue;
	}

	private void populateInputsAndOutputs(final String fbName, final String fbType, final FBNetwork fbNetwork,
			final String previousNames) {
		final IOInfo info = getInfoFromIOAndComm(fbName, fbType, fbNetwork);
		if (info.isIo()) {
			handleIO(info, previousNames, fbName, fbNetwork);
		}

		getAllVariablesFromInterface(fbName, fbNetwork, previousNames);
		final FBType typeFB = fbNetwork.getFBNamed(fbName).getType();
		if (typeFB instanceof BasicFBType) {
			handleBasicFB((BasicFBType) typeFB, previousNames, fbName);
		} else if (typeFB instanceof CompositeFBType) {
			handleCompositeFB((CompositeFBType) typeFB, previousNames, fbName);
		}
	}

	private void handleBasicFB(final BasicFBType basic, final String previousNames, final String fbName) {
		if (basic.getName()
				.matches("E_CTU|E_D_FF|E_DEMUX|E_MERGE|E_PERMIT|E_REND|E_RS|E_SELECT|E_SPLIT|E_SR|E_SWITCH")) { // these //$NON-NLS-1$
			// FBs
			// are
			// implemented
			// as
			// SIFB
			// in
			// the
			// 4diac&nbsp;FORTE
			return;
		}

		for (final VarDeclaration varInternal : basic.getInternalVars()) {
			// store internal variables
			final FMUInputOutput.variableType varType = FMUInputOutput.getTypeFromString(varInternal.getTypeName());
			if (FMUInputOutput.variableType.UNKNOWN == varType) {
				continue;
			}
			inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "." + varInternal.getName(), false, //$NON-NLS-1$
					FMUInputOutput.variableScope.INTERNAL, varType,
					(null != varInternal.getValue()) ? varInternal.getValue().getValue() : null));
		}

		for (final VarDeclaration varInternal : basic.getInternalConstVars()) {
			// store internal variables
			final FMUInputOutput.variableType varType = FMUInputOutput.getTypeFromString(varInternal.getTypeName());
			if (FMUInputOutput.variableType.UNKNOWN == varType) {
				continue;
			}
			inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "." + varInternal.getName(), false, //$NON-NLS-1$
					FMUInputOutput.variableScope.INTERNAL, varType,
					(null != varInternal.getValue()) ? varInternal.getValue().getValue() : null));
		}
		// store ECC
		inputsAndOutputs
				.add(new FMUInputOutput(previousNames + fbName + ".$ECC", false, FMUInputOutput.variableScope.INTERNAL, //$NON-NLS-1$
						FMUInputOutput.variableType.INTEGER, "0")); //$NON-NLS-1$
	}

	private void handleCompositeFB(final CompositeFBType composite, final String previousNames, final String fbName) {
		if (composite.getName().matches("E_CYCLE|E_F_TRIG|E_R_TRIG|E_TimeOut")) { // these FBs are //$NON-NLS-1$
			// implemented as SIFB in the
			// 4diac&nbsp;FORTE
			return;
		}

		// store internal FB. Take care of the names
		for (final FBNetworkElement elem : composite.getFBNetwork().getNetworkElements()) {
			if (elem instanceof final FB fb && !(elem instanceof AdapterFB)) {
				populateInputsAndOutputs(fb.getName(), fb.getTypeName(), composite.getFBNetwork(),
						previousNames + fbName + "."); //$NON-NLS-1$
			}
		}
	}

	private void getAllVariablesFromInterface(final String fbName, final FBNetwork paFBNetwork,
			final String previousNames) {
		// Add the interface var and events as variables
		final InterfaceList fbInterface = paFBNetwork.getFBNamed(fbName).getInterface();

		final ArrayList<EList<? extends IInterfaceElement>> interfaceLists = new ArrayList<>();
		interfaceLists.add(fbInterface.getInputVars());
		interfaceLists.add(fbInterface.getOutputVars());
		interfaceLists.add(fbInterface.getEventInputs());
		interfaceLists.add(fbInterface.getEventOutputs());

		for (final EList<? extends IInterfaceElement> list : interfaceLists) {
			for (final IInterfaceElement variable : list) {
				FMUInputOutput varInfo = new FMUInputOutput();
				if (variable instanceof VarDeclaration) {
					varInfo = getInfoFromVar(paFBNetwork, fbName, (VarDeclaration) variable);
					if (FMUInputOutput.variableType.UNKNOWN == varInfo.getVarType()) {
						continue;
					}
				} else {// event: don't do anything

				}
				inputsAndOutputs.add(new FMUInputOutput(previousNames + fbName + "." + variable.getName(), false, //$NON-NLS-1$
						varInfo.getScope(), varInfo.getVarType(), varInfo.getInitialValue()));
			}
		}
	}

	private static FMUInputOutput getInfoFromVar(final FBNetwork paFBNetwork, final String fbName,
			final VarDeclaration variable) {
		FMUInputOutput returnValue = new FMUInputOutput();
		final FB commFB = paFBNetwork.getFBNamed(fbName);
		FMUInputOutput.variableType type;
		final Value value = variable.getValue();
		String initialValue = ""; //$NON-NLS-1$

		type = FMUInputOutput.getTypeFromString(commFB.getInterface().getVariable(variable.getName()).getTypeName());

		if (FMUInputOutput.variableType.UNKNOWN == type) { // It's an abstract type, check the other side of the
			// connection
			returnValue.setType(getInfoFromConnectedFB(commFB, paFBNetwork, fbName, variable));
		}

		if (value != null && !value.getValue().isEmpty()) { // has some literal
			try {
				initialValue = DeploymentHelper.getVariableValue(variable);
			} catch (final DeploymentException e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
			if (-1 != initialValue.indexOf('#')) {
				if (FMUInputOutput.variableType.UNKNOWN == type) {
					type = FMUInputOutput.getTypeFromString(initialValue.substring(0, initialValue.indexOf('#')));
				}

				initialValue = initialValue.substring(initialValue.indexOf('#') + 1);
			}
			returnValue = new FMUInputOutput("", false, FMUInputOutput.variableScope.PARAM, type, initialValue); //$NON-NLS-1$
		}

		return returnValue;
	}

	private static FMUInputOutput.variableType getInfoFromConnectedFB(final FB commFB, final FBNetwork paFBNetwork,
			final String fbName, final IInterfaceElement variable) {
		FMUInputOutput.variableType returnValue = FMUInputOutput.variableType.UNKNOWN;
		final boolean isInput = commFB.getInterface().getVariable(variable.getName()).isIsInput();
		for (final DataConnection con : paFBNetwork.getDataConnections()) {
			if (isItsConnection(isInput, con, fbName, variable)) {
				final String destinationType = isInput ? con.getSource().getTypeName()
						: con.getDestination().getTypeName();
				if (null != destinationType) {
					returnValue = FMUInputOutput.getTypeFromString(destinationType);
				}
				break;
			}
		}
		return returnValue;
	}

	private static boolean isItsConnection(final boolean isInput, final DataConnection con, final String fbName,
			final IInterfaceElement variable) {
		if (isInput) {
			return (con.getDestinationElement().getName().equals(fbName)
					&& con.getDestination().getName().equals(variable.getName()));
		}
		return (con.getSourceElement().getName().equals(fbName)
				&& con.getSource().getName().equals(variable.getName()));
	}

	public StringBuilder createModelDescription(final String outputName) {
		final StringBuilder modelDescription = new StringBuilder();
		modelDescription.append(
				"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<fmiModelDescription\n  fmiVersion=\"2.0\"\n  modelName=\"" //$NON-NLS-1$
						+ outputName + "\"\n  guid=\"" + outputName + "\">\n\n<CoSimulation\n  modelIdentifier=\"" //$NON-NLS-1$ //$NON-NLS-2$
						+ outputName
						+ "\"\n  canHandleVariableCommunicationStepSize=\"true\"/>\n\n<LogCategories>\n  <Category name=\"logAll\"/>\n  <Category name=\"logError\"/>\n  <Category name=\"logCalls\"/>\n</LogCategories>"); //$NON-NLS-1$

		modelDescription.append("\n\n<ModelVariables>\n"); //$NON-NLS-1$

		int outputIndex = 0;
		int noOfOutputs = 0;

		final List<List<FMUInputOutput>> allToWrite = new ArrayList<>();
		allToWrite.add(inputsAndOutputs);

		for (final List<FMUInputOutput> list : allToWrite) {
			for (final FMUInputOutput element : list) {
				writeVariableToBuffer(modelDescription, outputIndex, element);
				if (!element.getInput() && (element.getScope() == FMUInputOutput.variableScope.IO)) {
					noOfOutputs++;
				}
				outputIndex++;
			}
		}

		modelDescription.append("</ModelVariables>\n\n<ModelStructure>\n"); //$NON-NLS-1$
		if (0 != noOfOutputs) {
			modelDescription.append("  <Outputs>\n"); //$NON-NLS-1$

			outputIndex = 0;
			for (final FMUInputOutput element : inputsAndOutputs) {
				outputIndex++;
				if (!element.getInput() && (element.getScope() == FMUInputOutput.variableScope.IO)) {
					modelDescription.append("    <Unknown index=\"" + outputIndex + "\" dependencies=\"\"/>\n"); //$NON-NLS-1$ //$NON-NLS-2$
				}

			}
			modelDescription.append("  </Outputs>\n"); //$NON-NLS-1$
		}
		modelDescription.append("</ModelStructure>\n\n</fmiModelDescription>"); //$NON-NLS-1$
		return modelDescription;
	}

	private static void writeVariableToBuffer(final StringBuilder modelDescription, final int outputIndex,
			final FMUInputOutput element) {

		final String causality = getCausality(element);
		final String variability = getVariability(element);

		modelDescription.append("  <ScalarVariable name=\"" + element.getName() + "\" " //$NON-NLS-1$ //$NON-NLS-2$
				+ "valueReference=\"" + outputIndex + "\" " //$NON-NLS-1$ //$NON-NLS-2$
				+ "description=\"\" " //$NON-NLS-1$
				+ "causality=\"" + causality //$NON-NLS-1$
				+ "\" variability=\"" + variability + "\"" //$NON-NLS-1$ //$NON-NLS-2$
				+ (!element.getInput() || (element.getScope() == FMUInputOutput.variableScope.PARAM)
						? " initial=\"exact\">" //$NON-NLS-1$
						: ">") //$NON-NLS-1$
				+ "\n    <"); //$NON-NLS-1$
		switch (element.getVarType()) {
		case BOOLEAN:
			modelDescription.append("Boolean "); //$NON-NLS-1$
			break;
		case INTEGER:
			modelDescription.append("Integer "); //$NON-NLS-1$
			break;
		case REAL:
			modelDescription.append("Real "); //$NON-NLS-1$
			break;
		case STRING:
			modelDescription.append("String "); //$NON-NLS-1$
			break;
		default:

			break;
		}
		modelDescription.append("start=\"" + element.getInitialValue() + "\"/>\n  </ScalarVariable>\n"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String getCausality(final FMUInputOutput element) {
		return switch (element.getScope()) {
		case PARAM -> "parameter"; //$NON-NLS-1$
		case INTERNAL -> "local"; //$NON-NLS-1$
		case IO -> element.getInput() ? "input" : "output"; //$NON-NLS-1$ //$NON-NLS-2$
		default -> ""; //$NON-NLS-1$
		};
	}

	private static String getVariability(final FMUInputOutput element) {
		if (FMUInputOutput.variableScope.PARAM == element.getScope()) {
			return "fixed"; //$NON-NLS-1$
		}

		return (FMUInputOutput.variableType.REAL == element.getVarType()) ? "continuous" : "discrete"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String getSubstringAfterMatch(final String source, final String toLook) {
		return source.substring(source.indexOf(toLook) + toLook.length());
	}
}
