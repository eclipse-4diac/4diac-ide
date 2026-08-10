/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zijun Tang - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.ethercat.model.Device;
import org.eclipse.fordiac.ide.ethercat.model.Module;
import org.eclipse.fordiac.ide.ethercat.model.Pdo;
import org.eclipse.fordiac.ide.ethercat.model.PdoEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.navigator.TypeLibRootElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EsiFileImporter extends Wizard implements IImportWizard {

	private static final String DEVICE_CONFIG_TYPE = "forte::eclipse4diac::io::ethercat::ECDeviceConfig"; //$NON-NLS-1$
	private static final String MODULE_CONFIG_TYPE = "forte::eclipse4diac::io::ethercat::ECModuleConfig"; //$NON-NLS-1$
	private static final Pattern MODULE_IDENT_PATTERN = Pattern.compile("ModuleIdent\\s*:=\\s*(\\d+)"); //$NON-NLS-1$
	private static final Pattern PRODUCT_CODE_PATTERN = Pattern.compile("ProductCode\\s*:=\\s*(\\d+)"); //$NON-NLS-1$
	private static final Pattern REVISION_ATTR_PATTERN = Pattern.compile("Name\\s*=\\s*\"" //$NON-NLS-1$
			+ Pattern.quote(TypeLibraryTags.REVISION_NO_ATTRIBUTE_FULL_NAME)
			+ "\"[^>]*Value\\s*=\\s*\"'?(\\d+)'?\""); //$NON-NLS-1$

	private IProject project;
	private String fbtSaveFolder;
	private String vendorName;
	private String vendorId;
	private EsiFileImporterWizardPage page;

	@Override
	public void addPages() {
		page = new EsiFileImporterWizardPage(Messages.EsiFileImporter_PageName);
		addPage(page);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle(Messages.EsiFileImporter_WindowTitle);
		setNeedsProgressMonitor(true);
		resolveTargetProject(selection);
	}

	private void resolveTargetProject(final IStructuredSelection selection) {
		project = null;
		fbtSaveFolder = null;
		if (selection == null || selection.isEmpty()) {
			return;
		}
		final Object element = selection.getFirstElement();
		if (element instanceof final AutomationSystem system) {
			project = system.getTypeLibrary().getProject();
		} else if (element instanceof final IProject selectedProject) {
			project = selectedProject;
		} else if (element instanceof final IFolder folder) {
			project = folder.getProject();
		} else if (element instanceof final TypeLibRootElement typeLibRoot) {
			project = typeLibRoot.getSystem().getTypeLibrary().getProject();
		}
		if (project == null) {
			return;
		}
		final IPath typeLibLocation = project.getFolder(TypeLibraryTags.TYPE_LIB_FOLDER_NAME).getLocation();
		if (typeLibLocation != null) {
			fbtSaveFolder = typeLibLocation.toOSString();
		}
	}

	@Override
	public boolean performFinish() {
		if (project == null || fbtSaveFolder == null || fbtSaveFolder.isBlank()) {
			MessageDialog.openError(getShell(), Messages.EsiFileImporter_WindowTitle,
					Messages.EsiFileImporter_NoProjectSelected);
			return false;
		}
		try {
			final EsiFileParser esiFileParser = new EsiFileParser(page.getEsiFilePath());
			vendorName = EsiFileParser.replaceSpecialChars(esiFileParser.getVendorName());
			if (vendorName == null || vendorName.isBlank()) {
				vendorName = "UnknownVendor"; //$NON-NLS-1$
			}
			vendorId = EsiFileParser.toUnsignedDecimal(esiFileParser.getVendorId());

			final String vendorRoot = fbtSaveFolder + File.separator + vendorName;
			final String devicesDir = vendorRoot + File.separator + "devices"; //$NON-NLS-1$
			final String modulesDir = vendorRoot + File.separator + "modules"; //$NON-NLS-1$

			final Set<String> existingModuleIdents = new HashSet<>();
			final Set<String> existingDeviceKeys = new HashSet<>();
			final Map<String, String> existingModuleNameToIdent = new HashMap<>();
			final Map<String, String> existingDeviceNameToKey = new HashMap<>();
			scanExistingModules(modulesDir, existingModuleIdents, existingModuleNameToIdent);
			scanExistingDevices(devicesDir, existingDeviceKeys, existingDeviceNameToKey);

			final List<Module> modules = deduplicateModules(esiFileParser.parseModulesCatalog());
			assignUniqueModuleNames(modules, existingModuleNameToIdent);

			final List<Device> devices = deduplicateDevices(esiFileParser.parseDevices());
			assignUniqueDeviceNames(devices, existingDeviceNameToKey);

			for (final Module module : modules) {
				if (existingModuleIdents.contains(module.moduleIdent)) {
					continue;
				}
				final Document moduleFB = createFB(module);
				writeDocumentToFile(moduleFB, modulesDir + File.separator + module.moduleType + ".fbt"); //$NON-NLS-1$
				existingModuleIdents.add(module.moduleIdent);
			}

			for (final Device device : devices) {
				final String deviceKey = deviceIdentityKey(device.productCode, device.revisionNo);
				if (existingDeviceKeys.contains(deviceKey)) {
					continue;
				}
				device.vendorId = vendorId;
				final Document deviceFB = createFB(device);
				writeDocumentToFile(deviceFB, devicesDir + File.separator + device.deviceType + ".fbt"); //$NON-NLS-1$
				existingDeviceKeys.add(deviceKey);
			}

			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

	private static List<Module> deduplicateModules(final List<Module> catalog) {
		final Map<String, Module> byIdent = new LinkedHashMap<>();
		for (final Module module : catalog) {
			final String ident = EsiFileParser.toUnsignedDecimal(module.moduleIdent);
			module.moduleIdent = ident;
			byIdent.putIfAbsent(ident, module);
		}
		return new ArrayList<>(byIdent.values());
	}

	private static List<Device> deduplicateDevices(final List<Device> devices) {
		final Map<String, Device> byKey = new LinkedHashMap<>();
		for (final Device device : devices) {
			device.productCode = EsiFileParser.toUnsignedDecimal(device.productCode);
			device.revisionNo = EsiFileParser.toUnsignedDecimal(device.revisionNo);
			byKey.putIfAbsent(deviceIdentityKey(device.productCode, device.revisionNo), device);
		}
		return new ArrayList<>(byKey.values());
	}

	private static void assignUniqueModuleNames(final List<Module> modules,
			final Map<String, String> existingNameToIdent) {
		final Map<String, List<Module>> byBaseName = new LinkedHashMap<>();
		for (final Module module : modules) {
			final String baseName = module.moduleType;
			byBaseName.computeIfAbsent(baseName, unused -> new ArrayList<>()).add(module);
		}
		for (final Map.Entry<String, List<Module>> entry : byBaseName.entrySet()) {
			final String baseName = entry.getKey();
			final List<Module> group = entry.getValue();
			final boolean collideInImport = group.size() > 1;
			for (final Module module : group) {
				String name = baseName;
				if (collideInImport) {
					name = baseName + "_" + EsiFileParser.toUnsignedHexSuffix(module.moduleIdent); //$NON-NLS-1$
				}
				final String existingIdent = existingNameToIdent.get(name);
				if (existingIdent != null && !existingIdent.equals(module.moduleIdent)) {
					name = baseName + "_" + EsiFileParser.toUnsignedHexSuffix(module.moduleIdent); //$NON-NLS-1$
				}
				module.moduleType = name;
			}
		}
	}

	private static void assignUniqueDeviceNames(final List<Device> devices,
			final Map<String, String> existingNameToKey) {
		final Map<String, List<Device>> byBaseName = new LinkedHashMap<>();
		for (final Device device : devices) {
			final String baseName = device.deviceType;
			byBaseName.computeIfAbsent(baseName, unused -> new ArrayList<>()).add(device);
		}
		for (final Map.Entry<String, List<Device>> entry : byBaseName.entrySet()) {
			final String baseName = entry.getKey();
			final List<Device> group = entry.getValue();
			final boolean collideInImport = group.size() > 1;
			for (final Device device : group) {
				final String deviceKey = deviceIdentityKey(device.productCode, device.revisionNo);
				String name = baseName;
				if (collideInImport) {
					name = baseName + "_" + EsiFileParser.toUnsignedHexSuffix(device.revisionNo); //$NON-NLS-1$
				}
				final String existingKey = existingNameToKey.get(name);
				if (existingKey != null && !existingKey.equals(deviceKey)) {
					name = baseName + "_" + EsiFileParser.toUnsignedHexSuffix(device.revisionNo); //$NON-NLS-1$
				}
				device.deviceType = name;
			}
		}
	}

	private static String deviceIdentityKey(final String productCode, final String revisionNo) {
		return productCode + "|" + revisionNo; //$NON-NLS-1$
	}

	private static void scanExistingModules(final String modulesDir, final Set<String> existingIdents,
			final Map<String, String> nameToIdent) {
		final File dir = new File(modulesDir);
		final File[] files = dir.listFiles((d, name) -> name.endsWith(".fbt")); //$NON-NLS-1$
		if (files == null) {
			return;
		}
		for (final File file : files) {
			final String content = readFileQuietly(file);
			if (content == null) {
				continue;
			}
			final Matcher matcher = MODULE_IDENT_PATTERN.matcher(content);
			if (!matcher.find()) {
				continue;
			}
			final String ident = matcher.group(1);
			existingIdents.add(ident);
			final String typeName = file.getName().substring(0, file.getName().length() - 4);
			nameToIdent.put(typeName, ident);
		}
	}

	private static void scanExistingDevices(final String devicesDir, final Set<String> existingKeys,
			final Map<String, String> nameToKey) {
		final File dir = new File(devicesDir);
		final File[] files = dir.listFiles((d, name) -> name.endsWith(".fbt")); //$NON-NLS-1$
		if (files == null) {
			return;
		}
		for (final File file : files) {
			final String content = readFileQuietly(file);
			if (content == null) {
				continue;
			}
			final Matcher productMatcher = PRODUCT_CODE_PATTERN.matcher(content);
			if (!productMatcher.find()) {
				continue;
			}
			final String productCode = productMatcher.group(1);
			String revisionNo = "0"; //$NON-NLS-1$
			final Matcher revisionMatcher = REVISION_ATTR_PATTERN.matcher(content);
			if (revisionMatcher.find()) {
				revisionNo = revisionMatcher.group(1);
			}
			final String key = deviceIdentityKey(productCode, revisionNo);
			existingKeys.add(key);
			final String typeName = file.getName().substring(0, file.getName().length() - 4);
			nameToKey.put(typeName, key);
		}
	}

	private static String readFileQuietly(final File file) {
		try {
			return Files.readString(file.toPath(), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			return null;
		}
	}

	private void writeDocumentToFile(final Document doc, final String filePath) {
		try {
			final File file = new File(filePath);
			final File parentDir = file.getParentFile();
			if (parentDir != null && !parentDir.exists()) {
				parentDir.mkdirs();
			}
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.transform(new DOMSource(doc), new StreamResult(file));
		} catch (final Exception e) {
			// ignore
		}
	}

	private Document createFB(final Device device) throws ParserConfigurationException, XPathExpressionException {
		final Document doc = createBasicFB(device.deviceType, device.getFBType(), device.comment, device.revisionNo);
		final Element interfaceListElement = getElementByXPath(doc, "//InterfaceList"); //$NON-NLS-1$
		final Element inputVarsElement = getElementByXPath(doc, "//InputVars"); //$NON-NLS-1$

		final String configInit = String.format("(Alias := 0, Position := 0, VendorId := %s, ProductCode := %s)", //$NON-NLS-1$
				device.vendorId, device.productCode);
		addElement(doc, inputVarsElement, "VarDeclaration", mapOf( //$NON-NLS-1$
				"Name", "Config", //$NON-NLS-1$ //$NON-NLS-2$
				"Type", DEVICE_CONFIG_TYPE, //$NON-NLS-1$
				"InitialValue", configInit)); //$NON-NLS-1$
		createFBDI(doc, inputVarsElement, device.txPdoes, "IN_"); //$NON-NLS-1$
		createFBDI(doc, inputVarsElement, device.rxPdoes, "OUT_"); //$NON-NLS-1$

		final Element plugsElement = addElement(doc, interfaceListElement, "Plugs"); //$NON-NLS-1$
		final Element socketsElement = addElement(doc, interfaceListElement, "Sockets"); //$NON-NLS-1$
		addElement(doc, plugsElement, "AdapterDeclaration", mapOf("Name", "BusAdapterOut", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, socketsElement, "AdapterDeclaration", mapOf("Name", "BusAdapterIn", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		if (device.deviceCategory == Device.DeviceCategory.GEN_Coupler) {
			addElement(doc, plugsElement, "AdapterDeclaration", mapOf("Name", "ModuleAdapterOut", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}

		return doc;
	}

	private Document createFB(final Module module) throws ParserConfigurationException, XPathExpressionException {
		final Document doc = createBasicFB(module.moduleType, module.getFbType(), module.comment, null);
		final Element interfaceListElement = getElementByXPath(doc, "//InterfaceList"); //$NON-NLS-1$
		final Element inputVarsElement = getElementByXPath(doc, "//InputVars"); //$NON-NLS-1$

		final String configInit = String.format("(ModuleIdent := %s, Slot := 0)", module.moduleIdent); //$NON-NLS-1$
		addElement(doc, inputVarsElement, "VarDeclaration", mapOf( //$NON-NLS-1$
				"Name", "Config", //$NON-NLS-1$ //$NON-NLS-2$
				"Type", MODULE_CONFIG_TYPE, //$NON-NLS-1$
				"InitialValue", configInit)); //$NON-NLS-1$

		createFBDI(doc, inputVarsElement, module.txPdoes, "IN_"); //$NON-NLS-1$
		createFBDI(doc, inputVarsElement, module.rxPdoes, "OUT_"); //$NON-NLS-1$

		final Element plugsElement = addElement(doc, interfaceListElement, "Plugs"); //$NON-NLS-1$
		final Element socketsElement = addElement(doc, interfaceListElement, "Sockets"); //$NON-NLS-1$
		addElement(doc, plugsElement, "AdapterDeclaration", mapOf("Name", "ModuleAdapterOut", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, socketsElement, "AdapterDeclaration", mapOf("Name", "ModuleAdapterIn", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		return doc;
	}

	private Document createBasicFB(final String fbName, final String fbType, final String fbComment,
			final String revisionNo) throws ParserConfigurationException {
		final Document doc = createDocument();
		final Element fbTypeElement = addElement(doc, doc, "FBType", mapOf("Name", fbName, "Comment", fbComment)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		addElement(doc, fbTypeElement, "Identification", mapOf("Standard", "61499-2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addElement(doc, fbTypeElement, "VersionInfo", mapOf("Version", "1.0", "Author", "Zijun Tang", "Date", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				LocalDate.now().toString()));
		addElement(doc, fbTypeElement, "CompilerInfo", mapOf("packageName", "eclipse4diac::io::ethercat")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addElement(doc, fbTypeElement, "Attribute", mapOf("Name", "eclipse4diac::core::TypeHash", "Value", "''")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		final String deployTypeName = "'eclipse4diac::io::ethercat::" + fbType + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		addElement(doc, fbTypeElement, "Attribute", //$NON-NLS-1$
				mapOf("Name", "eclipse4diac::core::GenericClassName", "Value", deployTypeName)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (revisionNo != null) {
			addElement(doc, fbTypeElement, "Attribute", mapOf("Name", //$NON-NLS-1$ //$NON-NLS-2$
					TypeLibraryTags.REVISION_NO_ATTRIBUTE_FULL_NAME, "Value", "'" + revisionNo + "'")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final Element interfaceListElement = addElement(doc, fbTypeElement, "InterfaceList"); //$NON-NLS-1$
		final Element eventInputsElement = addElement(doc, interfaceListElement, "EventInputs"); //$NON-NLS-1$
		final Element eventOutputsElement = addElement(doc, interfaceListElement, "EventOutputs"); //$NON-NLS-1$
		final Element mapElement = addElement(doc, eventInputsElement, "Event", mapOf("Name", "MAP", "Type", "Event")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		final Element mapOElement = addElement(doc, eventOutputsElement, "Event", //$NON-NLS-1$
				mapOf("Name", "MAPO", "Type", "Event")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		final Element indElement = addElement(doc, eventOutputsElement, "Event", mapOf("Name", "IND", "Type", "Event")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		final Element inputVarsElement = addElement(doc, interfaceListElement, "InputVars"); //$NON-NLS-1$
		addElement(doc, inputVarsElement, "VarDeclaration", mapOf("Name", "QI", "Type", "BOOL")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, mapElement, "With", "Var", "QI"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		final Element outputVarsElement = addElement(doc, interfaceListElement, "OutputVars"); //$NON-NLS-1$
		addElement(doc, outputVarsElement, "VarDeclaration", mapOf("Name", "QO", "Type", "BOOL")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, outputVarsElement, "VarDeclaration", mapOf("Name", "STATUS", "Type", "WSTRING")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, indElement, "With", "Var", "QO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addElement(doc, indElement, "With", "Var", "STATUS"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addElement(doc, mapOElement, "With", "Var", "QO"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return doc;
	}

	private void createFBDI(final Document doc, final Element inputVarsElement, final ArrayList<Pdo> pdoes,
			final String prefix) throws XPathExpressionException {
		final Element mapElement = getElementByXPath(doc, "//Event[@Name='MAP']"); //$NON-NLS-1$
		int pdoEntryIndex = 0;
		for (final Pdo pdo : pdoes) {
			for (final PdoEntry entry : pdo.pdoEntries) {
				final String diName = prefix + (pdoEntryIndex + 1);
				addElement(doc, inputVarsElement, "VarDeclaration", //$NON-NLS-1$
						mapOf("Name", diName, "Type", "WSTRING", "Comment", entry.comment)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				addElement(doc, mapElement, "With", "Var", diName); //$NON-NLS-1$ //$NON-NLS-2$
				pdoEntryIndex++;
			}
		}
	}

	private Element getElementByXPath(final Document doc, final String expression) throws XPathExpressionException {
		final XPath xpath = XPathFactory.newInstance().newXPath();
		final NodeList nodeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
		return (Element) nodeList.item(0);
	}

	private Document createDocument() throws ParserConfigurationException {
		final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

	private Element addElement(final Document doc, final Node parentElement, final String elementName,
			final String attribute, final String value) {
		final Element element = doc.createElement(elementName);
		element.setAttribute(attribute, value);
		parentElement.appendChild(element);
		return element;
	}

	private Element addElement(final Document doc, final Node parent, final String elementName,
			final Map<String, String> attributes) {
		final Element element = doc.createElement(elementName);
		attributes.forEach(element::setAttribute);
		parent.appendChild(element);
		return element;
	}

	private Element addElement(final Document doc, final Node parent, final String elementName) {
		final Element element = doc.createElement(elementName);
		parent.appendChild(element);
		return element;
	}

	public static <K, V> Map<K, V> mapOf(final Object... keyValues) {
		if (keyValues.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid number of arguments for key-value pairs."); //$NON-NLS-1$
		}
		final Map<K, V> map = new HashMap<>();
		for (int i = 0; i < keyValues.length; i += 2) {
			@SuppressWarnings("unchecked")
			final K key = (K) keyValues[i];
			@SuppressWarnings("unchecked")
			final V value = (V) keyValues[i + 1];
			map.put(key, value);
		}
		return map;
	}
}
