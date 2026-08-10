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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.fordiac.ide.ethercat.model.Device;
import org.eclipse.fordiac.ide.ethercat.model.Module;
import org.eclipse.fordiac.ide.ethercat.model.Pdo;
import org.eclipse.fordiac.ide.ethercat.model.PdoEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EsiFileParser {

	private static final String VENDOR_ENGLISH_NAME_XPATH = "//Vendor/Name[@LcId='1033']/text()"; //$NON-NLS-1$
	private static final String VENDOR_NORMAL_NAME_XPATH = "//Vendor/Name/text()"; //$NON-NLS-1$
	private static final String VENDOR_ID_XPATH = "//Vendor/Id/text()"; //$NON-NLS-1$

	private Document document;
	private XPath xpath;

	private static final Pattern NON_ALPHANUMERIC_PATTERN = Pattern.compile("[^a-zA-Z0-9_]"); //$NON-NLS-1$
	private static final Pattern TRAILING_UNDERSCORES_PATTERN = Pattern.compile("_+$"); //$NON-NLS-1$

	public static String replaceSpecialChars(final String input) {
		if(input == null) {
			return null;
		}
		String result = NON_ALPHANUMERIC_PATTERN.matcher(input).replaceAll("_"); //$NON-NLS-1$
		result = result.replaceAll("__+", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		return TRAILING_UNDERSCORES_PATTERN.matcher(result).replaceAll(""); //$NON-NLS-1$
	}

	public EsiFileParser(final String esiFile) throws EsiParseException {
		this(new File(esiFile));
	}

	public EsiFileParser(final File esiFile) throws EsiParseException {
		try(FileInputStream fis = new FileInputStream(esiFile)) {
			init(fis);
		} catch(final IOException e) {
			throw new EsiParseException(Messages.EsiFileParser_ReadFileErrorPrefix + esiFile.getPath(), e);
		}
	}

	public EsiFileParser(final InputStream inputStream) throws EsiParseException {
		init(inputStream);
	}

	private void init(final InputStream inputStream) throws EsiParseException {
		try {
			final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			final DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(inputStream);
			xpath = XPathFactory.newInstance().newXPath();
		} catch(final ParserConfigurationException | SAXException | IOException e) {
			throw new EsiParseException(Messages.EsiFileParser_ParseFileError, e);
		}
	}

	public String getVendorName() {
		try {
			String name = (String) xpath.evaluate(VENDOR_ENGLISH_NAME_XPATH, document, XPathConstants.STRING);
			if(name.isEmpty()) {
				name = (String) xpath.evaluate(VENDOR_NORMAL_NAME_XPATH, document, XPathConstants.STRING);
			}
			return (name != null && !name.trim().isEmpty()) ? name.trim() : null;
		} catch(final XPathExpressionException e) {
			return null;
		}
	}

	public String getVendorId() {
		try {
			final String vendorId = (String) xpath.evaluate(VENDOR_ID_XPATH, document, XPathConstants.STRING);
			return (vendorId != null && !vendorId.trim().isEmpty()) ? vendorId.trim() : null;
		} catch(final XPathExpressionException e) {
			return null;
		}
	}

	public ArrayList<Device> parseDevices() {
		final ArrayList<Device> devices = new ArrayList<>();
		try {
			final NodeList deviceNodes = (NodeList) xpath.evaluate("//Device", document, XPathConstants.NODESET); //$NON-NLS-1$
			for(int i = 0; i < deviceNodes.getLength(); i++) {
				final Device device = parseDevice((Element) deviceNodes.item(i));
				if(device != null) {
					devices.add(device);
				}
			}
		} catch(final XPathExpressionException e) {
			// ignore and return parsed part
		}
		return devices;
	}

	public ArrayList<Module> parseModulesCatalog() {
		final ArrayList<Module> modules = new ArrayList<>();
		try {
			final NodeList moduleNodes = (NodeList) xpath.evaluate("//Modules/Module", document, XPathConstants.NODESET); //$NON-NLS-1$
			for(int i = 0; i < moduleNodes.getLength(); i++) {
				final Module module = parseModule((Element) moduleNodes.item(i));
				if(module != null) {
					modules.add(module);
				}
			}
		} catch(final XPathExpressionException e) {
			// ignore and return parsed part
		}
		return modules;
	}

	private Device parseDevice(final Element deviceElement) {
		try {
			final Element typeElement = (Element) deviceElement.getElementsByTagName("Type").item(0); //$NON-NLS-1$
			if(typeElement == null) {
				return null;
			}

			final String originalDeviceType = typeElement.getTextContent();
			final String deviceType = replaceSpecialChars(originalDeviceType);
			final Device device = new Device(deviceType);
			device.setOriDeviceType(originalDeviceType);

			if(!"GL20_RTU_ECT".equals(deviceType)) { //$NON-NLS-1$
				parseDevicePdos(deviceElement, device);
			}

			final Element commentElement = (Element) deviceElement.getElementsByTagName("Comment").item(0); //$NON-NLS-1$
			if(commentElement != null) {
				device.setComment(commentElement.getTextContent());
			}
			device.productCode = typeElement.getAttribute("ProductCode"); //$NON-NLS-1$
			device.revisionNo = typeElement.getAttribute("RevisionNo"); //$NON-NLS-1$
			device.setDeviceCategory(
					hasSlots(deviceElement) ? Device.DeviceCategory.GEN_Coupler : Device.DeviceCategory.GEN_Device);
			return device;
		} catch(final Exception e) {
			return null;
		}
	}

	private static boolean hasSlots(final Element deviceElement) {
		final NodeList slotsNodes = deviceElement.getElementsByTagName("Slots"); //$NON-NLS-1$
		for(int i = 0; i < slotsNodes.getLength(); i++) {
			final Element slotsElement = (Element) slotsNodes.item(i);
			if(slotsElement.getElementsByTagName("Slot").getLength() > 0) { //$NON-NLS-1$
				return true;
			}
		}
		return false;
	}

	private void parseDevicePdos(final Element deviceElement, final Device device) {
		final NodeList childNodes = deviceElement.getChildNodes();
		for(int i = 0; i < childNodes.getLength(); i++) {
			if(!(childNodes.item(i) instanceof Element)) {
				continue;
			}
			final Element childElement = (Element) childNodes.item(i);
			final String tagName = childElement.getTagName();
			if("RxPdo".equals(tagName) || "TxPdo".equals(tagName)) { //$NON-NLS-1$ //$NON-NLS-2$
				final String smValue = childElement.getAttribute("Sm"); //$NON-NLS-1$
				if("2".equals(smValue) || "3".equals(smValue)) { //$NON-NLS-1$ //$NON-NLS-2$
					final Pdo.PdoType pdoType = "RxPdo".equals(tagName) ? Pdo.PdoType.RxPdo : Pdo.PdoType.TxPdo; //$NON-NLS-1$
					final Pdo pdo = parsePdo(childElement, pdoType);
					if(pdo != null) {
						device.addPdo(pdo);
					}
				}
			}
		}
	}

	private Module parseModule(final Element moduleElement) {
		try {
			final Element typeElement = (Element) moduleElement.getElementsByTagName("Type").item(0); //$NON-NLS-1$
			final String moduleIdent = typeElement.getAttribute("ModuleIdent"); //$NON-NLS-1$
			final String moduleType = "M_" + replaceSpecialChars(typeElement.getTextContent()); //$NON-NLS-1$
			final Module module = new Module(moduleType);

			final Element nameElement = (Element) moduleElement.getElementsByTagName("Name").item(0); //$NON-NLS-1$
			if(nameElement != null) {
				module.setComment(nameElement.getTextContent());
			}
			module.setModuleIdent(moduleIdent);
			parseModulePdos(moduleElement, module);
			return module;
		} catch(final Exception e) {
			return null;
		}
	}

	private void parseModulePdos(final Element moduleElement, final Module module) {
		final NodeList childNodes = moduleElement.getChildNodes();
		for(int i = 0; i < childNodes.getLength(); i++) {
			if(!(childNodes.item(i) instanceof Element)) {
				continue;
			}
			final Element childElement = (Element) childNodes.item(i);
			final String tagName = childElement.getTagName();
			if("RxPdo".equals(tagName) && "2".equals(childElement.getAttribute("Sm"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				final Pdo pdo = parsePdo(childElement, Pdo.PdoType.RxPdo);
				if(pdo != null) {
					module.addPdo(pdo);
				}
			} else if("TxPdo".equals(tagName) && "3".equals(childElement.getAttribute("Sm"))) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				final Pdo pdo = parsePdo(childElement, Pdo.PdoType.TxPdo);
				if(pdo != null) {
					module.addPdo(pdo);
				}
			}
		}
	}

	private Pdo parsePdo(final Element pdoElement, final Pdo.PdoType pdoType) {
		try {
			final Element nameElement = (Element) pdoElement.getElementsByTagName("Name").item(0); //$NON-NLS-1$
			final String pdoName = (nameElement != null) ? nameElement.getTextContent() : "Unknown Pdo"; //$NON-NLS-1$
			final Pdo pdo = new Pdo(pdoName, pdoType);
			final NodeList childNodes = pdoElement.getChildNodes();
			for(int i = 0; i < childNodes.getLength(); i++) {
				if(!(childNodes.item(i) instanceof Element)) {
					continue;
				}
				final Element childElement = (Element) childNodes.item(i);
				if("Entry".equals(childElement.getTagName()) && isPdoEntryValidate(childElement)) { //$NON-NLS-1$
					final PdoEntry entry = parsePdoEntry(childElement, pdoName, pdoType);
					if(entry != null) {
						pdo.addEntry(entry);
					}
				}
			}
			return pdo;
		} catch(final Exception e) {
			return null;
		}
	}

	private boolean isPdoEntryValidate(final Element entryElement) {
		if(entryElement == null) {
			return false;
		}
		final Element indexElement = (Element) entryElement.getElementsByTagName("Index").item(0); //$NON-NLS-1$
		if(indexElement == null) {
			return false;
		}
		return !"#x0".equals(indexElement.getTextContent()); //$NON-NLS-1$
	}

	private PdoEntry parsePdoEntry(final Element entryElement, final String pdoName, final Pdo.PdoType pdoType) {
		try {
			final Element nameElement = (Element) entryElement.getElementsByTagName("Name").item(0); //$NON-NLS-1$
			final String entryName = (nameElement != null) ? nameElement.getTextContent() : "Unknown Entry"; //$NON-NLS-1$

			final Element commentElement = (Element) entryElement.getElementsByTagName("Comment").item(0); //$NON-NLS-1$
			String comment = commentElement == null ? entryName + "(" + pdoName + ")" : commentElement.getTextContent(); //$NON-NLS-1$ //$NON-NLS-2$

			final Element dataTypeElement = (Element) entryElement.getElementsByTagName("DataType").item(0); //$NON-NLS-1$
			final String dataTypeContent = dataTypeElement != null ? dataTypeElement.getTextContent() : ""; //$NON-NLS-1$

			if("BOOL".equals(dataTypeContent)) { //$NON-NLS-1$
				comment += pdoType == Pdo.PdoType.TxPdo ? ", map to an IX" : ", map to an QX"; //$NON-NLS-1$ //$NON-NLS-2$
			} else if("USINT".equals(dataTypeContent) || "SINT".equals(dataTypeContent)) { //$NON-NLS-1$ //$NON-NLS-2$
				comment += pdoType == Pdo.PdoType.TxPdo ? ", map to an IB" : ", map to an QB"; //$NON-NLS-1$ //$NON-NLS-2$
			} else if("UINT".equals(dataTypeContent) || "INT".equals(dataTypeContent)) { //$NON-NLS-1$ //$NON-NLS-2$
				comment += pdoType == Pdo.PdoType.TxPdo ? ", map to an IW" : ", map to an QW"; //$NON-NLS-1$ //$NON-NLS-2$
			} else if("UDINT".equals(dataTypeContent) || "DINT".equals(dataTypeContent)) { //$NON-NLS-1$ //$NON-NLS-2$
				comment += pdoType == Pdo.PdoType.TxPdo ? ", map to an ID" : ", map to an QD"; //$NON-NLS-1$ //$NON-NLS-2$
			} else if("ULINT".equals(dataTypeContent) || "LINT".equals(dataTypeContent)) { //$NON-NLS-1$ //$NON-NLS-2$
				comment += pdoType == Pdo.PdoType.TxPdo ? ", map to an IL" : ", map to an QL"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			return new PdoEntry(entryName, comment);
		} catch(final Exception e) {
			return null;
		}
	}

	public static String toUnsignedDecimal(final String value) {
		if(value == null) {
			return "0"; //$NON-NLS-1$
		}
		String raw = value.trim();
		if(raw.isEmpty()) {
			return "0"; //$NON-NLS-1$
		}
		int radix = 10;
		if(raw.startsWith("#x") || raw.startsWith("#X")) { //$NON-NLS-1$ //$NON-NLS-2$
			raw = raw.substring(2);
			radix = 16;
		} else if(raw.startsWith("0x") || raw.startsWith("0X")) { //$NON-NLS-1$ //$NON-NLS-2$
			raw = raw.substring(2);
			radix = 16;
		}
		try {
			return new BigInteger(raw, radix).toString(10);
		} catch(final NumberFormatException e) {
			return "0"; //$NON-NLS-1$
		}
	}

	/** Hex suffix without leading zeros, e.g. {@code #x001022cf} → {@code 1022CF}. */
	public static String toUnsignedHexSuffix(final String value) {
		if(value == null) {
			return "0"; //$NON-NLS-1$
		}
		String raw = value.trim();
		if(raw.isEmpty()) {
			return "0"; //$NON-NLS-1$
		}
		int radix = 10;
		if(raw.startsWith("#x") || raw.startsWith("#X")) { //$NON-NLS-1$ //$NON-NLS-2$
			raw = raw.substring(2);
			radix = 16;
		} else if(raw.startsWith("0x") || raw.startsWith("0X")) { //$NON-NLS-1$ //$NON-NLS-2$
			raw = raw.substring(2);
			radix = 16;
		}
		try {
			return new BigInteger(raw, radix).toString(16).toUpperCase();
		} catch(final NumberFormatException e) {
			return "0"; //$NON-NLS-1$
		}
	}

	public static class EsiParseException extends Exception {
		private static final long serialVersionUID = 1L;

		public EsiParseException(final String message) {
			super(message);
		}

		public EsiParseException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}
}
