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
 *   Sichuan Qunyuan Technology Co., Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.ethercat.model.Device;
import org.eclipse.fordiac.ide.ethercat.model.Module;
import org.eclipse.fordiac.ide.ethercat.model.Pdo;
import org.eclipse.fordiac.ide.ethercat.model.PdoEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.typemanagement.navigator.TypeLibRootElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EsiFileImporter extends Wizard implements IImportWizard {

	private IProject project;
	private String fbtSaveFolder;
	private String vendorName;
	private String vendorId;
	private ArrayList<Device> devices;
	private EsiFileParser esiFileParser;
	private EsiFileImporterWizardPage page;
	private static final String SLAVE_CONFIG_TYPE = "forte::eclipse4diac::io::ethercat::ECSlaveConfig"; //$NON-NLS-1$
	private static final String MODULE_CONFIG_TYPE = "forte::eclipse4diac::io::ethercat::ECModuleConfig"; //$NON-NLS-1$

	@Override
	public void addPages() {
		page = new EsiFileImporterWizardPage(Messages.EsiFileImporter_PageName);
		addPage(page);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle(Messages.EsiFileImporter_WindowTitle);
		setNeedsProgressMonitor(true);
		final Object element = selection.getFirstElement();
		if(element instanceof AutomationSystem) {
			project = ((AutomationSystem) element).getTypeLibrary().getProject();
			fbtSaveFolder = project.getLocation().toOSString();
		} else if(element instanceof IProject) {
			project = (IProject) element;
			fbtSaveFolder = project.getLocation().toOSString();
		} else if(element instanceof IFolder) {
			project = ((IFolder) element).getProject();
			fbtSaveFolder = project.getLocation().toOSString();
		} else if(element instanceof TypeLibRootElement) {
			project = ((TypeLibRootElement) element).getSystem().getTypeLibrary().getProject();
			fbtSaveFolder = project.getLocation().toOSString();
		} else {
			fbtSaveFolder = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
		}
		//fbtSaveFolder += "/io/ethercat"; //$NON-NLS-1$
		fbtSaveFolder += "/Type Library";
	}

	@Override
	public boolean performFinish() {
		try {
			esiFileParser = new EsiFileParser(page.getEsiFilePath());
			vendorName = EsiFileParser.replaceSpecialChars(esiFileParser.getVendorName());
			if(vendorName == null || vendorName.isBlank()) {
				vendorName = "UnknownVendor"; //$NON-NLS-1$
			}
			vendorId = EsiFileParser.toUnsignedDecimal(esiFileParser.getVendorId());
			devices = esiFileParser.parseDevices();

			final String slaveFBSaveFolder = String.format("%s/%s", fbtSaveFolder, vendorName); //$NON-NLS-1$
			for(final Device device : devices) {
				device.vendorId = vendorId;
				device.productCode = EsiFileParser.toUnsignedDecimal(device.productCode);
				final Document slaveFB = createFB(device);
				final String fbSaveFolder = slaveFBSaveFolder + "/" + device.deviceType; //$NON-NLS-1$
				final String moduleFBSaveFolder = fbSaveFolder + "/modules"; //$NON-NLS-1$
				for(final Module module : device.modules) {
					module.moduleIdent = EsiFileParser.toUnsignedDecimal(module.moduleIdent);
					final Document moduleFB = createFB(module);
					final String moduleFBFilePath = moduleFBSaveFolder + "/" + module.moduleType + ".fbt"; //$NON-NLS-1$ //$NON-NLS-2$
					writeDocumentToFile(moduleFB, moduleFBFilePath);
				}
				final String slaveFBFilePath = fbSaveFolder + "/" + device.deviceType + ".fbt"; //$NON-NLS-1$ //$NON-NLS-2$
				writeDocumentToFile(slaveFB, slaveFBFilePath);
			}
			if(project != null) {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			}
		} catch(final Exception e) {
			return false;
		}
		return true;
	}

	private void writeDocumentToFile(final Document doc, final String filePath) {
		try {
			final File file = new File(filePath);
			final File parentDir = file.getParentFile();
			if(parentDir != null && !parentDir.exists()) {
				parentDir.mkdirs();
			}
			final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.transform(new DOMSource(doc), new StreamResult(file));
		} catch(final Exception e) {
			// ignore
		}
	}

	private Document createFB(final Device device) throws ParserConfigurationException, XPathExpressionException {
		final Document doc = createBasicFB(device.deviceType, device.getFBType(), device.comment);
		final Element interfaceListElement = getElementByXPath(doc, "//InterfaceList"); //$NON-NLS-1$
		final Element inputVarsElement = getElementByXPath(doc, "//InputVars"); //$NON-NLS-1$

		final String configInit = String.format("(Alias := 0, Position := 0, VendorId := %s, ProductCode := %s)", //$NON-NLS-1$
				device.vendorId, device.productCode);
		addElement(doc, inputVarsElement, "VarDeclaration", mapOf( //$NON-NLS-1$
				"Name", "Config", //$NON-NLS-1$ //$NON-NLS-2$
				"Type", SLAVE_CONFIG_TYPE, //$NON-NLS-1$
				"InitialValue", configInit)); //$NON-NLS-1$
		createFBDI(doc, inputVarsElement, device.txPdoes, "IN_"); //$NON-NLS-1$
		createFBDI(doc, inputVarsElement, device.rxPdoes, "OUT_"); //$NON-NLS-1$

		final Element plugsElement = addElement(doc, interfaceListElement, "Plugs"); //$NON-NLS-1$
		final Element socketsElement = addElement(doc, interfaceListElement, "Sockets"); //$NON-NLS-1$
		addElement(doc, plugsElement, "AdapterDeclaration", mapOf("Name", "BusAdapterOut", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, socketsElement, "AdapterDeclaration", mapOf("Name", "BusAdapterIn", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

		if(device.slaveType == Device.SlaveType.GEN_Coupler) {
			addElement(doc, plugsElement, "AdapterDeclaration", mapOf("Name", "ModuleAdapterOut", "Type", "ECBusAdapter")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		
		
		return doc;
	}

	private Document createFB(final Module module) throws ParserConfigurationException, XPathExpressionException {
		final Document doc = createBasicFB(module.moduleType, module.getFbType(), module.comment);
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

	private Document createBasicFB(final String fbName, final String fbType, final String fbComment) throws ParserConfigurationException {
		final Document doc = createDocument();
		final Element fbTypeElement = addElement(doc, doc, "FBType", mapOf("Name", fbName, "Comment", fbComment)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		addElement(doc, fbTypeElement, "Identification", mapOf("Standard", "6149902", "Type", "eclipse4diac::io::ethercat::" + fbType)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		addElement(doc, fbTypeElement, "VersionInfo", mapOf("Version", "1.0", "Author", "cqyt", "Date", LocalDate.now().toString())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		addElement(doc, fbTypeElement, "CompilerInfo", mapOf("packageName", "eclipse4diac::io::ethercat")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addElement(doc, fbTypeElement, "Attribute", mapOf("Name","eclipse4diac::core::TypeHash","Value","''")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		String genericTypeName = "";
		if(fbType.contains("ECCoupler")) {
			genericTypeName = "'GEN_ECCoupler'";			
		} else if(fbType.contains("ECSlave")) {
			genericTypeName = "'GEN_ECSlave'";
		} else {
			genericTypeName = "'GEN_ECModule'";
		}
		addElement(doc, fbTypeElement, "Attribute", mapOf("Name","eclipse4diac::core::GenericClassName", "Value", genericTypeName));//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

		final Element interfaceListElement = addElement(doc, fbTypeElement, "InterfaceList"); //$NON-NLS-1$
		final Element eventInputsElement = addElement(doc, interfaceListElement, "EventInputs"); //$NON-NLS-1$
		final Element eventOutputsElement = addElement(doc, interfaceListElement, "EventOutputs"); //$NON-NLS-1$
		final Element mapElement = addElement(doc, eventInputsElement, "Event", mapOf("Name", "MAP", "Type", "Event")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		final Element mapOElement = addElement(doc, eventOutputsElement, "Event", mapOf("Name", "MAPO", "Type", "Event")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
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

	private void createFBDI(final Document doc, final Element inputVarsElement, final ArrayList<Pdo> pdoes, final String prefix)
			throws XPathExpressionException {
		final Element mapElement = getElementByXPath(doc, "//Event[@Name='MAP']"); //$NON-NLS-1$
		int pdoEntryIndex = 0;
		for(final Pdo pdo : pdoes) {
			for(final PdoEntry entry : pdo.pdoEntries) {
				final String diName = prefix + (pdoEntryIndex + 1);
				addElement(doc, inputVarsElement, "VarDeclaration", mapOf("Name", diName, "Type", "WSTRING", "Comment", entry.comment)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
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

	private Element addElement(final Document doc, final Node parentElement, final String elementName, final String attribute, final String value) {
		final Element element = doc.createElement(elementName);
		element.setAttribute(attribute, value);
		parentElement.appendChild(element);
		return element;
	}

	private Element addElement(final Document doc, final Node parent, final String elementName, final Map<String, String> attributes) {
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
		if(keyValues.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid number of arguments for key-value pairs."); //$NON-NLS-1$
		}
		final Map<K, V> map = new HashMap<>();
		for(int i = 0; i < keyValues.length; i += 2) {
			@SuppressWarnings("unchecked")
			final K key = (K) keyValues[i];
			@SuppressWarnings("unchecked")
			final V value = (V) keyValues[i + 1];
			map.put(key, value);
		}
		return map;
	}
}
