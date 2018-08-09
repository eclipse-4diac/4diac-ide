/*******************************************************************************
 * Copyright (c) 2009, - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Martin Melik Merkumians, Monika Wenger, 
 *   Ingo Hegny, Matthias Plasch, Jose Cabral, Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte1_0_x;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.export.utils.ExportException;
import org.eclipse.fordiac.ide.export.utils.IExportFilter;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * export functionality to create *.cpp and *.h files in the FORTE V1.0.x format
 * 
 */
public class ForteExportFilter1_0_x extends ExportFilter implements IExportFilter {

	protected class AdapterInstance {
		public AdapterInstance(String name, String adapterType, boolean isPlug,
				org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType adapterFBType) {
			this.name = name;
			this.adapterType = adapterType;
			this.isPlug = isPlug;
			this.adapterFBType = adapterFBType;
		}

		private String name;
		private String adapterType;
		private boolean isPlug;
		private AdapterFBType adapterFBType;

		public String getAdapterType() {
			return adapterType;
		}

		public boolean isPlug() {
			return isPlug;
		}

		public String getName() {
			return name;
		}

		public AdapterFBType getAdapterFBType() {
			return adapterFBType;
		}
	}

	private String baseClass;

	private int fannedOutEventConns = 0;

	private int fannedOutDataConns = 0;

	private int numCompFBParams = 0;

	protected int eventInCount;

	protected int eventOutCount;

	protected int adapterCount;

	protected List<AdapterInstance> adapters = new ArrayList<>();

	protected List<String> eventInputs = new ArrayList<>();

	private StructuredTextEmitter structuredTextEmitter = new StructuredTextEmitter(this);

	/**
	 * Holds all vars that have an special initial value set.
	 */
	private List<VarDefinition> initialValues = new ArrayList<>();

	private final List<String> anyVars = new ArrayList<>();

	private boolean inputWithsUsed;

	public ForteExportFilter1_0_x() {
		// empty default constructor
	}

	public ForteExportFilter1_0_x(final Document doc, final String dest) {
		super(doc, dest);
	}

	public void addErrorMsg(final String msg) {
		forteEmitterErrors.add(" - " + libraryType.getName() + ": " + msg); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void addWarningMsg(final String msg) {
		forteEmitterWarnings.add(" - " + libraryType.getName() + ": " + msg); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void addInfoMsg(final String msg) {
		forteEmitterInfos.add(" - " + libraryType.getName() + ": " + msg); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public Map<String, VarDefinition> getVars() {
		return vars;
	}

	public List<AdapterInstance> getAdapters() {
		return adapters;
	}

	@Override
	protected void exportHeader() {

		String buf = "/*************************************************************************"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** FORTE Library Element"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " ***"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x!"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " ***"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Name: " + name; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Description: " + libraryType.getComment(); //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Version: "; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);

		if (libraryType instanceof FBType && !((FBType) libraryType).getVersionInfo().isEmpty()) {
			for (VersionInfo vi : ((FBType) libraryType).getVersionInfo()) {
				buf = " ***     " + vi.getVersion() + ": " + vi.getDate() //$NON-NLS-1$ //$NON-NLS-2$
						+ "/" + vi.getAuthor() + " - " //$NON-NLS-1$//$NON-NLS-2$
						+ vi.getOrganization() + " - " + vi.getRemarks(); //$NON-NLS-1$
				pwH.println(buf);
				pwCPP.println(buf);
			}
		}

		buf = " *************************************************************************/"; //$NON-NLS-1$
		pwH.println(buf);
		pwCPP.println(buf);
		// export the stuff that is only in the h file
		buf = "\n#ifndef _" + libraryType.getName().toUpperCase() + "_H_"; //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println(buf);
		buf = "#define _" + libraryType.getName().toUpperCase() + "_H_"; //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println(buf);
		// export the stuff that is only in the cpp file
		buf = "\n#include \"" + libraryType.getName() + ".h\""; //$NON-NLS-1$ //$NON-NLS-2$
		pwCPP.println(buf);

		pwCPP.println("#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP"); //$NON-NLS-1$
		pwCPP.println("#include \"" + libraryType.getName() + "_gen.cpp\""); //$NON-NLS-1$ //$NON-NLS-2$
		pwCPP.println("#endif"); //$NON-NLS-1$

		exportCompilerInfoHeaders();
	}

	private void exportCompilerInfoHeaders() {
		if (libraryType instanceof CompilableType) {
			CompilableType type = (CompilableType) libraryType;
			if (null != type.getCompilerInfo()) {
				if (null != type.getCompilerInfo().getHeader()) {
					pwCPP.println(type.getCompilerInfo().getHeader());
				}
			}
		}
	}

	@Override
	protected void exportClosingCode() {
		String buf = "\n};\n\n#endif //close the ifdef sequence from the beginning of the file\n"; //$NON-NLS-1$
		pwH.println(buf);
		buf = "\n"; //$NON-NLS-1$
		pwCPP.println(buf);
	}

	@Override
	protected void exportFBStarter() {
		InterfaceList interfaceList = null;

		if (libraryType instanceof BasicFBType) {
			baseClass = "CBasicFB"; //$NON-NLS-1$
			pwH.println("\n#include <basicfb.h>"); //$NON-NLS-1$
			forteEmitterInfos
					.add("  - Creating header and source files for Basic Function Block " + libraryType.getName());
			interfaceList = ((FBType) libraryType).getInterfaceList();
		} else if (libraryType instanceof CompositeFBType) {
			pwH.println("\n#include <cfb.h>"); //$NON-NLS-1$
			pwH.println("#include <typelib.h>"); //$NON-NLS-1$
			baseClass = "CCompositeFB"; //$NON-NLS-1$
			forteEmitterInfos
					.add("  - Creating header and source files for Composite Function Block " + libraryType.getName());
			interfaceList = ((FBType) libraryType).getInterfaceList();

		} else if (libraryType instanceof ServiceInterfaceFBType) {
			pwH.println("\n#include <funcbloc.h>"); //$NON-NLS-1$
			baseClass = "CFunctionBlock"; //$NON-NLS-1$
			forteEmitterInfos.add("  - Creating header and source files for Service Interface Function Block " + name);
			interfaceList = ((FBType) libraryType).getInterfaceList();
		} else if (libraryType instanceof AdapterType) {
			pwH.println("\n#include <adapter.h>"); //$NON-NLS-1$
			pwH.println("#include <typelib.h>"); //$NON-NLS-1$
			baseClass = "CAdapter"; //$NON-NLS-1$
			forteEmitterInfos
					.add("  - Creating header and source files for Adapter Function Block " + libraryType.getName());
			interfaceList = ((AdapterType) libraryType).getInterfaceList();
		} else {
			forteEmitterErrors.add(
					"  - FB is not of supported class {Basic Function Block, Composite Function Block, Service Interface Function Block}");
		}

		if (interfaceList != null) {
			Set<String> datatypeNames = new HashSet<>();
			Set<String> adapterNames = new HashSet<>();
			boolean lArrayUsed = false;

			lArrayUsed = extractDataTypeNames(interfaceList.getInputVars(), datatypeNames, lArrayUsed);
			lArrayUsed = extractDataTypeNames(interfaceList.getOutputVars(), datatypeNames, lArrayUsed);
			if (libraryType instanceof BasicFBType) {
				lArrayUsed = extractDataTypeNames(((BasicFBType) libraryType).getInternalVars(), datatypeNames,
						lArrayUsed);
			}

			if (!datatypeNames.isEmpty()) {
				String[] x = datatypeNames.toArray(new String[0]);
				for (int i = 0; i < x.length; ++i) {
					pwH.println("#include <forte_" + x[i].toLowerCase() + ".h>"); //$NON-NLS-1$ //$NON-NLS-2$
					if (x[i].startsWith("ANY")) { //$NON-NLS-1$
						pwH.println(
								"\n#ERROR type contains variables of type ANY. Please check the usage of these variables as we can not gurantee correct usage on export!\n"); //$NON-NLS-1$

					}
				}
				if (lArrayUsed) {
					pwH.println("#include <forte_array.h>"); //$NON-NLS-1$
				}
			}

			extractAdapterTypeNames(interfaceList.getPlugs(), adapterNames);
			extractAdapterTypeNames(interfaceList.getSockets(), adapterNames);
			if (!adapterNames.isEmpty()) {
				String[] x = adapterNames.toArray(new String[0]);
				for (int i = 0; i < x.length; ++i) {
					pwH.println("#include \"" + x[i] + ".h\""); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}

		pwH.println("\nclass FORTE_" + name + ": public " + baseClass + "{"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (libraryType instanceof AdapterType) {
			pwH.print("  DECLARE_ADAPTER_TYPE"); //$NON-NLS-1$
		} else {
			pwH.print("  DECLARE_FIRMWARE_FB"); //$NON-NLS-1$
		}
		pwH.println("(FORTE_" + name + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println("\nprivate:"); //$NON-NLS-1$

		if (libraryType instanceof AdapterType) {
			pwCPP.print("\nDEFINE_ADAPTER_TYPE"); //$NON-NLS-1$
		} else {
			pwCPP.print("\nDEFINE_FIRMWARE_FB"); //$NON-NLS-1$
		}
		pwCPP.println("(FORTE_" + name + ", g_nStringId" + name + ")\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}

	private static void extractAdapterTypeNames(EList<AdapterDeclaration> adapters, Set<String> dataTypeNames) {
		if (!adapters.isEmpty()) {
			for (int i = 0; i < adapters.size(); i++) {
				dataTypeNames.add(adapters.get(i).getTypeName());
			}
		}
	}

	private static boolean extractDataTypeNames(final EList<VarDeclaration> inputVars, Set<String> datatypeNames,
			boolean usesArray) {
		for (VarDeclaration var : inputVars) {
			if (!(var instanceof AdapterDeclaration)) {
				datatypeNames.add(var.getTypeName());
				if (var.isArray()) {
					usesArray = true;
				}
			}
		}
		return usesArray;
	}

	@Override
	protected void exportCompFBExecuteEventMethod() {
	}

	@Override
	protected void exportSIFBExecuteEvent() {
		pwH.println("\n  void executeEvent(int pa_nEIID);"); //$NON-NLS-1$

		pwCPP.println("\nvoid FORTE_" + name + "::executeEvent(int pa_nEIID){"); //$NON-NLS-1$//$NON-NLS-2$
		pwCPP.println("  switch(pa_nEIID){"); //$NON-NLS-1$

		for (String eventInput : eventInputs) {
			pwCPP.println("    case scm_nEvent" + eventInput //$NON-NLS-1$
					+ "ID:"); //$NON-NLS-1$
			pwCPP.println("#error add code for " + eventInput //$NON-NLS-1$
					+ " event!"); //$NON-NLS-1$
			pwCPP.println(
					"/*\n  do not forget to send output event, calling e.g.\n      sendOutputEvent(scm_nEventCNFID);\n*/"); //$NON-NLS-1$
			pwCPP.println("      break;"); //$NON-NLS-1$
		}
		pwCPP.println("  }"); //$NON-NLS-1$
		pwCPP.println("}\n"); //$NON-NLS-1$
	}

	@Override
	protected void exportFBNetworkInternalInterface() {
	}

	@Override
	protected void exportFBConstructor() {
		pwH.println("\npublic:"); //$NON-NLS-1$

		if (baseClass.equals("CBasicFB")) { //$NON-NLS-1$
			pwH.println("  FORTE_" //$NON-NLS-1$
					+ name + "(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) : "); //$NON-NLS-1$
			pwH.print("       " //$NON-NLS-1$
					+ baseClass + "(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId"); //$NON-NLS-1$
			if (internalCount != 0) {
				pwH.print(",\n           &scm_stInternalVars"); //$NON-NLS-1$
			} else {
				pwH.print(",\n              0"); //$NON-NLS-1$
			}
			pwH.print(", m_anFBConnData, m_anFBVarsData"); //$NON-NLS-1$

		} else {
			if (baseClass.equals("CCompositeFB")) { //$NON-NLS-1$
				pwH.print("  COMPOSITE_FUNCTION_BLOCK_CTOR"); //$NON-NLS-1$
			} else if (libraryType instanceof AdapterType) {
				pwH.print("  ADAPTER_CTOR"); //$NON-NLS-1$
			} else {
				pwH.print("  FUNCTION_BLOCK_CTOR"); //$NON-NLS-1$
			}
			pwH.print("(FORTE_" + name); //$NON-NLS-1$
		}

		pwH.print("){\n"); //$NON-NLS-1$
		// Currently the constructors are empty maybe this will change
		pwH.println("  };\n"); //$NON-NLS-1$

		pwH.println("  virtual ~FORTE_" + name + "(){};"); //$NON-NLS-1$ //$NON-NLS-2$

	}

	@Override
	protected void exportFBManagedObjectMethods() {
		// this is not needed any more
	}

	@Override
	protected void exportResStarter() {
		baseClass = "CResource"; //$NON-NLS-1$

		pwH.println("\n#include <resource.h>"); //$NON-NLS-1$
		pwH.println("#include <class0objhand.h>"); //$NON-NLS-1$
		forteEmitterInfos.add("  - Creating header and source files for Resource " + name);

		exportResDevHeader();

		pwH.println("\nclass FORTE_" + name + ": public CResource{"); //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println("  DECLARE_FIRMWARE_FB(FORTE_" + name + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println("\nprivate:"); //$NON-NLS-1$

		pwCPP.println("\nDEFINE_FIRMWARE_FB(FORTE_" + name + ", g_nStringId" //$NON-NLS-1$//$NON-NLS-2$
				+ name + ");\n"); //$NON-NLS-1$

		exportVarInputs(docel);
		exportFBInterfaceSpec();
	}

	@Override
	protected void exportResConstructor() {
		pwH.println(
				"\n  C61499Class0ObjectHandler m_oClass0ObjectHandler;  //!< The object handler to be used for this resources"); //$NON-NLS-1$

		pwH.println("\npublic:"); //$NON-NLS-1$
		pwH.println("  FORTE_" //$NON-NLS-1$
				+ name + "(CStringDictionary::TStringId pa_nInstanceNameId, CResource* pa_poDevice);"); //$NON-NLS-1$
		pwH.println("  virtual ~FORTE_" + name + "(){};"); //$NON-NLS-1$//$NON-NLS-2$

		pwCPP.println("FORTE_" //$NON-NLS-1$
				+ name + "::FORTE_" //$NON-NLS-1$
				+ name + "(CStringDictionary::TStringId pa_nInstanceNameId, CResource* pa_poDevice) : "); //$NON-NLS-1$
		pwCPP.println(
				"       CResource(pa_poDevice, &scm_stFBInterfaceSpec, pa_nInstanceNameId, m_oClass0ObjectHandler, m_anFBConnData, m_anFBVarsData),"); //$NON-NLS-1$
		pwCPP.println("       m_oClass0ObjectHandler(*this){"); //$NON-NLS-1$

		exportDevResElements("FB"); //$NON-NLS-1$
		exportResDevConnections(docel.getElementsByTagName("EventConnections")); //$NON-NLS-1$
		exportResDevConnections(docel.getElementsByTagName("DataConnections")); //$NON-NLS-1$

		pwCPP.println("}\n"); //$NON-NLS-1$
	}

	@Override
	protected void exportDeviceStarter() {
		baseClass = "CDevice"; //$NON-NLS-1$

		pwH.println("\n#include <device.h>"); //$NON-NLS-1$
		pwH.println("#include <class1objhand.h>"); //$NON-NLS-1$
		forteEmitterInfos.add("  - Creating header and source files for Device " + name);

		exportResDevHeader();

		pwH.println("\nclass FORTE_" + name + ": public CDevice{"); //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println("\nprivate:"); //$NON-NLS-1$

		exportVarInputs(docel);
		exportFBInterfaceSpec();
	}

	@Override
	protected void exportDeviceConstructor() {
		pwH.println(
				"\n  C61499Class1ObjectHandler m_oClass1ObjectHandler;  //!< The object handler to be used for this device"); //$NON-NLS-1$

		pwH.println("\npublic:"); //$NON-NLS-1$
		pwH.println("  FORTE_" + name + "();"); //$NON-NLS-1$ //$NON-NLS-2$
		pwH.println("  virtual ~FORTE_" + name + "(){};"); //$NON-NLS-1$ //$NON-NLS-2$

		pwCPP.println("FORTE_" + name + "::FORTE_" + name + "() : "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		pwCPP.println(
				"       CDevice(&scm_stFBInterfaceSpec, CStringDictionary::scm_nInvalidStringId, m_oClass1ObjectHandler, m_anFBConnData, m_anFBVarsData),"); //$NON-NLS-1$
		pwCPP.println("       m_oClass1ObjectHandler(*this){"); //$NON-NLS-1$

		exportDevResElements("Resource"); //$NON-NLS-1$
		exportResDevConnections(docel.getElementsByTagName("DataConnections")); //$NON-NLS-1$

		pwCPP.println("}\n"); //$NON-NLS-1$
	}

	private void exportResDevHeader() {
		Set<String> resVarsTypes = new HashSet<>();
		NodeList l1 = docel.getElementsByTagName("VarDeclaration"); //$NON-NLS-1$
		for (int i = 0; i < l1.getLength(); i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				resVarsTypes.add(el.getAttribute("Type")); //$NON-NLS-1$
			}
		}

		for (String element : resVarsTypes) {
			pwH.println("#include <forte_" + element.toLowerCase() + ".h>"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private void exportResDevConnections(NodeList l1) {
		if (0 < l1.getLength()) {
			org.w3c.dom.Node node = l1.item(0);
			if (node instanceof Element) {
				Element el = (Element) node;
				NodeList childs = el.getChildNodes();
				for (int i = 0; i < childs.getLength(); i++) {
					org.w3c.dom.Node childnode = childs.item(i);
					if (childnode instanceof Element) {
						Element childel = (Element) childnode;

						String[] source = childel.getAttribute("Source").split( //$NON-NLS-1$
								"\\."); //$NON-NLS-1$
						String[] dest = childel.getAttribute("Destination") //$NON-NLS-1$
								.split("\\."); //$NON-NLS-1$

						pwCPP.println(MessageFormat.format("  m_roObjectHandler.createConnection({0}, {1});", //$NON-NLS-1$
								new Object[] { source.length == 2
										? MessageFormat.format(
												"GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId{0}, g_nStringId{1})", //$NON-NLS-1$
												(Object[]) source)
										: MessageFormat.format("GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId{0})", //$NON-NLS-1$
												(Object[]) source),
										dest.length == 2 ? MessageFormat.format(
												"GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId{0}, g_nStringId{1})", //$NON-NLS-1$
												(Object[]) dest)
												: MessageFormat.format(
														"GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId{0})", //$NON-NLS-1$
														(Object[]) dest) }));
					}
				}
			}
		}
	}

	private void exportDevResElements(String elementType) {
		NodeList l1 = docel.getElementsByTagName(elementType);
		for (int i = 0; i < l1.getLength(); i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				pwCPP.println("  m_roObjectHandler.addFB(CTypeLib::createFB(g_nStringId" //$NON-NLS-1$
						+ el.getAttribute("Name") //$NON-NLS-1$
						+ ", g_nStringId" //$NON-NLS-1$
						+ el.getAttribute("Type") + ", this));"); //$NON-NLS-1$ //$NON-NLS-2$
				NodeList childs = el.getChildNodes();
				for (int j = 0; j < childs.getLength(); j++) {
					org.w3c.dom.Node childnode = childs.item(j);
					if (childnode instanceof Element) {
						Element childel = (Element) childnode;
						pwCPP.println(MessageFormat.format(
								"  m_roObjectHandler.getFB(g_nStringId{0})->getDataInput(g_nStringId{1})->fromString(\"{2}\");", //$NON-NLS-1$
								new Object[] { el.getAttribute("Name"), //$NON-NLS-1$
										childel.getAttribute("Name"), //$NON-NLS-1$
										childel.getAttribute("Value") })); //$NON-NLS-1$
					}
				}
			}
		}
	}

	@Override
	protected void exportVarNameArrays(final String namePrefix, final NodeList nodes) {
		int count = 0;
		StringBuilder names = new StringBuilder();
		StringBuilder typenames = new StringBuilder();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = nodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("VarDeclaration")) { //$NON-NLS-1$
					count++;
					if (count != 1) {
						names.append(", "); //$NON-NLS-1$
						typenames.append(", "); //$NON-NLS-1$
					}
					names.append("g_nStringId"); //$NON-NLS-1$
					names.append(el.getAttribute("Name")); //$NON-NLS-1$
					String arraySize = el.getAttribute("ArraySize"); //$NON-NLS-1$
					if (null != arraySize) {
						if (!arraySize.equals("")) { //$NON-NLS-1$
							typenames.append("g_nStringIdARRAY, "); //$NON-NLS-1$
							typenames.append(arraySize);
							typenames.append(", "); //$NON-NLS-1$
						}
					}
					typenames.append("g_nStringId"); //$NON-NLS-1$
					typenames.append(el.getAttribute("Type")); //$NON-NLS-1$
				}
			}
		}
		if (count != 0) {
			if (libraryType instanceof AdapterType) {
				pwH.println(" private:"); //$NON-NLS-1$
			}
			pwH.println("  static const CStringDictionary::TStringId scm_an" + namePrefix + "Names[];"); //$NON-NLS-1$ //$NON-NLS-2$
			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name //$NON-NLS-1$
					+ "::scm_an" + namePrefix + "Names[] = {" + names.toString() + "};\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			pwH.println("  static const CStringDictionary::TStringId scm_an" + namePrefix + "TypeIds[];"); //$NON-NLS-1$ //$NON-NLS-2$

			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name //$NON-NLS-1$
					+ "::scm_an" + namePrefix + "TypeIds[] = {" + typenames + "};\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			if (libraryType instanceof AdapterType) {
				pwH.println(" public:"); //$NON-NLS-1$
			}
		}

	}

	@Override
	protected void exportEvents(final String namePrefix, final NodeList nodes, final List<String> varNames) {

		int count = 0;
		StringBuilder names = new StringBuilder();
		StringBuilder withs = new StringBuilder();
		StringBuilder withIndexes = new StringBuilder();
		int withcount[] = new int[varNames.size()];
		for (int i = 0; i < varNames.size(); ++i) {
			withcount[i] = 0;
		}
		int indexCount = 0;
		int startindex;
		int len = nodes.getLength();

		if ((libraryType instanceof AdapterType) && (!(namePrefix.equals("EventInputs")))) { //$NON-NLS-1$
			pwH.println(" public:"); //$NON-NLS-1$
		}

		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = nodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("Event")) { //$NON-NLS-1$
					exportEventID(el.getAttribute("Name"), count); //$NON-NLS-1$
					count++;
					if (count != 1) {
						names.append(", "); //$NON-NLS-1$
						withIndexes.append(", "); //$NON-NLS-1$
					}
					names.append("g_nStringId").append(el.getAttribute("Name")); //$NON-NLS-1$ //$NON-NLS-2$
					if (namePrefix.equals("EventInput")) { //$NON-NLS-1$
						eventInputs.add(el.getAttribute("Name")); //$NON-NLS-1$
					}

					NodeList eventChilds = el.getChildNodes();
					int childsLen = eventChilds.getLength();
					int eventwithcount = 0;
					startindex = indexCount;
					for (int j = 0; j < childsLen; ++j) {
						org.w3c.dom.Node childNode = eventChilds.item(j);
						if (childNode instanceof Element) {
							Element childElement = (Element) childNode;
							if (childElement.getNodeName().equals("With")) { //$NON-NLS-1$
								String var = childElement.getAttribute("Var"); //$NON-NLS-1$
								int pos = varNames.indexOf(var);
								++(withcount[pos]);
								if (indexCount != 0) {
									withs.append(", "); //$NON-NLS-1$
								}
								withs.append(pos);
								indexCount++;
								eventwithcount++;
							}
						}
					}
					if (eventwithcount == 0) {
						withIndexes.append("-1"); //$NON-NLS-1$
					} else {
						withs.append(", 255"); //$NON-NLS-1$
						indexCount++;
						withIndexes.append(startindex);
					}
				}
			}
		}

		if (libraryType instanceof AdapterType) {
			pwH.println(" private:"); //$NON-NLS-1$
		}

		if (namePrefix.equals("EventOutput")) { //$NON-NLS-1$
			eventOutCount = count;
			pwH.println("  static const TForteInt16 scm_anEOWithIndexes[];"); //$NON-NLS-1$
			if (count != 0) {
				if (dataOutCount != 0) {
					int nowithcount = 0;
					startindex = indexCount;
					for (int i = 0; i < dataOutCount; ++i) {
						if (withcount[i] == 0) {
							if (indexCount != 0) {
								withs.append(", "); //$NON-NLS-1$
							}
							withs.append(i);
							++nowithcount;
							++indexCount;
						}
					}
					if (nowithcount == 0) {
						withIndexes.append(", -1"); //$NON-NLS-1$
					} else {
						withs.append(", 255"); //$NON-NLS-1$
						withIndexes.append(", ").append(startindex); //$NON-NLS-1$
					}
					pwH.println("  static const TDataIOID scm_anEOWith[];"); //$NON-NLS-1$
					pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
							+ "::scm_anEOWith[] = {" + withs + "};"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					withIndexes.append(", -1"); //$NON-NLS-1$
				}
			} else {
				if (dataOutCount != 0) {
					pwH.println("  static const TDataIOID scm_anEOWith[];"); //$NON-NLS-1$
					for (int i = 0; i < dataOutCount; ++i) {
						withs.append(i);
						withs.append(", "); //$NON-NLS-1$
					}
					withs.append("255"); //$NON-NLS-1$
					pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
							+ "::scm_anEOWith[] = {" + withs + "};"); //$NON-NLS-1$ //$NON-NLS-2$
					withIndexes.append("0"); //$NON-NLS-1$
				} else {
					withIndexes.append("-1"); //$NON-NLS-1$
				}
			}
			pwCPP.println("const TForteInt16 FORTE_" + name //$NON-NLS-1$
					+ "::scm_anEOWithIndexes[] = {" + withIndexes + "};"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			eventInCount = count;
			if (libraryType instanceof AdapterType) {
				pwH.println("  static const TForteInt16 scm_anEIWithIndexes[];"); //$NON-NLS-1$
				if (count != 0) {
					if (dataInCount != 0) {
						int nowithcount = 0;
						startindex = indexCount;
						for (int i = 0; i < dataInCount; ++i) {
							if (withcount[i] == 0) {
								if (indexCount != 0) {
									withs.append(", "); //$NON-NLS-1$
								}
								withs.append(i);
								++nowithcount;
								++indexCount;
							}
						}
						if (nowithcount == 0) {
							withIndexes.append(", -1"); //$NON-NLS-1$
						} else {
							withs.append(", 255"); //$NON-NLS-1$
							withIndexes.append(", ").append(startindex); //$NON-NLS-1$
						}
						pwH.println("  static const TDataIOID scm_anEIWith[];"); //$NON-NLS-1$
						pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
								+ "::scm_anEIWith[] = {" + withs + "};"); //$NON-NLS-1$ //$NON-NLS-2$
					} else {
						withIndexes.append(", -1"); //$NON-NLS-1$
					}
				} else {
					if (dataInCount != 0) {
						pwH.println("  static const TDataIOID scm_anEIWith[];"); //$NON-NLS-1$
						for (int i = 0; i < dataInCount; ++i) {
							withs.append(i);
							withs.append(", "); //$NON-NLS-1$
						}
						withs.append("255"); //$NON-NLS-1$
						pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
								+ "::scm_anEIWith[] = {" + withs + "};"); //$NON-NLS-1$ //$NON-NLS-2$
						withIndexes.append("0"); //$NON-NLS-1$
					} else {
						withIndexes.append("-1"); //$NON-NLS-1$
					}
				}
				pwCPP.println("const TForteInt16 FORTE_" + name //$NON-NLS-1$
						+ "::scm_anEIWithIndexes[] = {" + withIndexes + "};"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				if (count != 0) {
					pwH.println("  static const TForteInt16 scm_anEIWithIndexes[];"); //$NON-NLS-1$
					pwCPP.println("const TForteInt16 FORTE_" + name //$NON-NLS-1$
							+ "::scm_anEIWithIndexes[] = {" + withIndexes //$NON-NLS-1$
							+ "};"); //$NON-NLS-1$
					if ((dataInCount != 0) && (0 != withs.length())) {
						// we have input withs
						pwH.println("  static const TDataIOID scm_anEIWith[];"); //$NON-NLS-1$
						pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
								+ "::scm_anEIWith[] = {" + withs + "};"); //$NON-NLS-1$ //$NON-NLS-2$
					}

				}
			}
			if (dataInCount != 0) {
				inputWithsUsed = (0 != withs.length());
			}
		}
		if (count != 0) {
			pwH.println("  static const CStringDictionary::TStringId scm_an" //$NON-NLS-1$
					+ namePrefix + "Names[];\n"); //$NON-NLS-1$
			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name //$NON-NLS-1$
					+ "::scm_an" + namePrefix + "Names[] = {" + names + "};\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	private void exportEventID(final String evName, final int count) {
		pwH.println("  static const TEventID scm_nEvent" + evName + "ID = " //$NON-NLS-1$ //$NON-NLS-2$
				+ Integer.toString(count) + ";"); //$NON-NLS-1$

		if (libraryType instanceof AdapterType) {
			pwH.println("  int " + evName + "() {"); //$NON-NLS-1$ //$NON-NLS-2$
			pwH.println("    return m_nParentAdapterListEventID + scm_nEvent" //$NON-NLS-1$
					+ evName + "ID;"); //$NON-NLS-1$
			pwH.println("  }"); //$NON-NLS-1$
		}
	}

	@Override
	protected void exportAlgorithm(final String algName, final String type, final String src) {
		pwH.println("  void alg_" + algName + "(void);"); //$NON-NLS-1$ //$NON-NLS-2$

		pwCPP.println("void FORTE_" + name + "::alg_" + algName + "(void){"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (type.equals("ST")) { //$NON-NLS-1$
			structuredTextEmitter.exportStructuredTextAlgorithm(src, pwCPP);
		} else {
			pwCPP.println("#error Algorithm of type: '" + type //$NON-NLS-1$
					+ "' not supported!!!"); //$NON-NLS-1$
			pwCPP.println(src);
		}

		pwCPP.println("\n}\n"); //$NON-NLS-1$
	}

	@Override
	protected void exportVariable(final VarDefinition newVarDef) {
		createVarAccessoryFunction(newVarDef);

		if (0 < newVarDef.arraySizeValue) {
			VarDefinition arraydef = newVarDef;
			arraydef.arraySizeValue = 0;
			arraydef.name = newVarDef.name + "_Array"; //$NON-NLS-1$
			arraydef.type = "ARRAY"; //$NON-NLS-1$
			createVarAccessoryFunction(arraydef);
		}

		if (null != newVarDef.initialValue) {
			if (!newVarDef.initialValue.isEmpty()) {
				initialValues.add(newVarDef);
			}
		}
	}

	private void createVarAccessoryFunction(final VarDefinition newVarDef) {
		pwH.print("  CIEC_" + newVarDef.type + " "); //$NON-NLS-1$ //$NON-NLS-2$
		if (newVarDef.arraySizeValue < 1) {
			pwH.print("&"); //$NON-NLS-1$
		} else {
			pwH.print("*"); //$NON-NLS-1$
		}

		pwH.println(newVarDef.name + "() {"); //$NON-NLS-1$
		pwH.print("    return "); //$NON-NLS-1$

		if (newVarDef.arraySizeValue < 1) {
			pwH.print("*static_cast<CIEC_" + newVarDef.type + "*>("); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			// we have an array
			pwH.print("(CIEC_" + newVarDef.type //$NON-NLS-1$
					+ "*)(*static_cast<CIEC_ARRAY *>("); //$NON-NLS-1$
		}

		if (libraryType instanceof AdapterType) {
			pwH.print("(isSocket()) ? "); //$NON-NLS-1$
		}

		pwH.print("get"); //$NON-NLS-1$

		switch (newVarDef.inoutinternal) {
		case 0:
			pwH.print("DI"); //$NON-NLS-1$
			break;
		case 1:
			pwH.print("DO"); //$NON-NLS-1$
			break;
		case 2:
			pwH.print("VarInternal"); //$NON-NLS-1$
			break;
		default:
			break;
		}

		pwH.print("(" + Integer.toString(newVarDef.count) + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		if (libraryType instanceof AdapterType) {
			pwH.print(" : get"); //$NON-NLS-1$
			switch (newVarDef.inoutinternal) {
			case 0:
				pwH.print("DO"); //$NON-NLS-1$
				break;
			case 1:
				pwH.print("DI"); //$NON-NLS-1$
				break;
			default:
				break;
			}
			pwH.print("(" + Integer.toString(newVarDef.count) + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		pwH.print(")"); //$NON-NLS-1$
		if (newVarDef.arraySizeValue < 1) {
			pwH.println(";\n  };\n"); //$NON-NLS-1$
		} else {
			// we have an array
			pwH.println(")[0]; //the first element marks the start of the array\n  };\n"); //$NON-NLS-1$
		}

	}

	@Override
	protected void exportFBVar() {
		if (libraryType instanceof CompositeFBType) {
			FBNetwork fbNetwork = ((CompositeFBType) libraryType).getFBNetwork();

			EList<FBNetworkElement> allIntFBs = fbNetwork.getNetworkElements();

			// move adapters to back of list
			for (int i = 0; i < (allIntFBs.size() - adapterCount); i++) {
				if (allIntFBs.get(i).getType() instanceof AdapterFBType) {
					allIntFBs.move(allIntFBs.size() - 1, i);
					i--;
				}
			}

			if ((allIntFBs.size() - adapterCount) > 0) {
				exportCFBFBs(allIntFBs);
			}

			exportCFBParams(allIntFBs);
			exportCFBEventConns(fbNetwork.getEventConnections(), allIntFBs);
			exportCFBDataConns(fbNetwork.getDataConnections(), allIntFBs);

			pwH.println("  static const SCFB_FBNData scm_stFBNData;"); //$NON-NLS-1$
			pwCPP.println("\nconst SCFB_FBNData FORTE_" + name //$NON-NLS-1$
					+ "::scm_stFBNData = {"); //$NON-NLS-1$

			if (0 < (allIntFBs.size() - adapterCount)) {
				pwCPP.println("  " + (allIntFBs.size() - adapterCount) //$NON-NLS-1$
						+ ", scm_astInternalFBs,"); //$NON-NLS-1$
			} else {
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
			}

			if (!fbNetwork.getEventConnections().isEmpty()) {
				pwCPP.println("  " //$NON-NLS-1$
						+ (fbNetwork.getEventConnections().size() - fannedOutEventConns)
						+ ", scm_astEventConnections,"); //$NON-NLS-1$
				if (0 != fannedOutEventConns) {
					pwCPP.println("  " + fannedOutEventConns //$NON-NLS-1$
							+ ", scm_astFannedOutEventConnections,"); //$NON-NLS-1$
				} else {
					pwCPP.println("  0, 0,"); //$NON-NLS-1$
				}
			} else {
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
			}

			if (!fbNetwork.getDataConnections().isEmpty()) {
				pwCPP.println("  " //$NON-NLS-1$
						+ (fbNetwork.getDataConnections().size() - fannedOutDataConns) + ", scm_astDataConnections,"); //$NON-NLS-1$
				if (0 != fannedOutDataConns) {
					pwCPP.println("  " + fannedOutDataConns //$NON-NLS-1$
							+ ", scm_astFannedOutDataConnections,"); //$NON-NLS-1$
				} else {
					pwCPP.println("  0, 0,"); //$NON-NLS-1$
				}
			} else {
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
			}

			if (0 != numCompFBParams) {
				pwCPP.println("  " + numCompFBParams + ", scm_astParamters"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				pwCPP.println("  0, 0"); //$NON-NLS-1$
			}

			pwCPP.println("};"); //$NON-NLS-1$
		}
	}

	@Override
	protected void exportEC() {
	}

	@Override
	protected void exportDC() {
	}

	private AdapterFBType findAdapterType(final AdapterDeclaration paAdapter) {
		DataType myDT = paAdapter.getType();
		AdapterType myAT = null;
		if (myDT instanceof AdapterType) {
			myAT = (AdapterType) myDT;
			return myAT.getAdapterFBType();
		}

		return null;
	}

	@Override
	protected void exportFBInterfaceSpec() {

		if (null != libraryType) {
			InterfaceList interfaceList = null;
			if (libraryType instanceof FBType) {
				interfaceList = ((FBType) libraryType).getInterfaceList();
			} else if (libraryType instanceof AdapterType) {
				interfaceList = ((AdapterType) libraryType).getInterfaceList();
			}

			if (interfaceList != null) {
				// we are a FB and only FBs have adapters
				EList<AdapterDeclaration> myPlugs = interfaceList.getPlugs();
				EList<AdapterDeclaration> mySockets = interfaceList.getSockets();

				adapterCount = myPlugs.size() + mySockets.size();

				for (AdapterDeclaration adapterDeclaration : myPlugs) {
					AdapterInstance myLocalAdapterInst = new AdapterInstance(adapterDeclaration.getName(),
							adapterDeclaration.getTypeName(), true, findAdapterType(adapterDeclaration));
					adapters.add(myLocalAdapterInst);
				}

				for (AdapterDeclaration adapterDeclaration : mySockets) {
					AdapterInstance myLocalAdapterInst = new AdapterInstance(adapterDeclaration.getName(),
							adapterDeclaration.getTypeName(), false, findAdapterType(adapterDeclaration));
					adapters.add(myLocalAdapterInst);

				}
				if (adapterCount > 0) {
					pwH.println("  static const SAdapterInstanceDef scm_astAdapterInstances[];\n"); //$NON-NLS-1$
					pwCPP.println("const SAdapterInstanceDef FORTE_" + name //$NON-NLS-1$
							+ "::scm_astAdapterInstances[] = {"); //$NON-NLS-1$
					for (int i = 0; i < adapters.size(); i++) {
						AdapterInstance myAdapter = adapters.get(i);
						if (i > 0)
							pwCPP.println(","); //$NON-NLS-1$
						pwCPP.print("{g_nStringId" + myAdapter.getAdapterType() //$NON-NLS-1$
								+ ", " + "g_nStringId" + myAdapter.getName() //$NON-NLS-1$ //$NON-NLS-2$
								+ ", "); //$NON-NLS-1$
						if (myAdapter.isPlug()) {
							pwCPP.print("true }"); //$NON-NLS-1$
						} else {
							pwCPP.print("false }"); //$NON-NLS-1$
						}

						pwH.print("  FORTE_" + myAdapter.getAdapterType() + "& " //$NON-NLS-1$ //$NON-NLS-2$
								+ myAdapter.getName() + "() {\n" //$NON-NLS-1$
								+ "    return (*static_cast<FORTE_" //$NON-NLS-1$
								+ myAdapter.getAdapterType() + "*>(m_apoAdapters[" //$NON-NLS-1$
								+ i + "]));\n  };\n"); //$NON-NLS-1$

						pwH.println("  static const int scm_n" //$NON-NLS-1$
								+ myAdapter.getName() + "AdpNum = " + i + ";"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					pwCPP.println("};\n"); //$NON-NLS-1$
				}
			}

		}
		if (libraryType instanceof AdapterType) {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpecSocket;\n"); //$NON-NLS-1$
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name //$NON-NLS-1$
					+ "::scm_stFBInterfaceSpecSocket = {"); //$NON-NLS-1$
		} else {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpec;\n"); //$NON-NLS-1$
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name //$NON-NLS-1$
					+ "::scm_stFBInterfaceSpec = {"); //$NON-NLS-1$
		}
		pwCPP.print("  " + eventInCount + ","); //$NON-NLS-1$ //$NON-NLS-2$

		if (eventInCount != 0) {
			pwCPP.print("  scm_anEventInputNames,"); //$NON-NLS-1$
			if ((dataInCount != 0) && (inputWithsUsed)) {
				pwCPP.print("  scm_anEIWith,"); //$NON-NLS-1$
			} else {
				pwCPP.print("  0,"); //$NON-NLS-1$
			}
			pwCPP.println("  scm_anEIWithIndexes,"); //$NON-NLS-1$
		} else {
			pwCPP.print("  0,"); //$NON-NLS-1$
			pwCPP.print("  0,"); //$NON-NLS-1$
			pwCPP.println("  0,"); //$NON-NLS-1$
		}

		pwCPP.print("  " + eventOutCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
		if (eventOutCount != 0) {
			pwCPP.print("  scm_anEventOutputNames,"); //$NON-NLS-1$
		} else {
			pwCPP.print("  0, "); //$NON-NLS-1$
		}
		if (dataOutCount != 0) {
			pwCPP.print("  scm_anEOWith, scm_anEOWithIndexes,"); //$NON-NLS-1$
		} else {
			pwCPP.print("  0, 0,"); //$NON-NLS-1$
		}

		pwCPP.print("  " + dataInCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
		if (dataInCount != 0) {
			pwCPP.println("  scm_anDataInputNames, scm_anDataInputTypeIds,"); //$NON-NLS-1$
		} else {
			pwCPP.println("  0, 0, "); //$NON-NLS-1$
		}

		pwCPP.print("  " + dataOutCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
		if (dataOutCount != 0) {
			pwCPP.println("  scm_anDataOutputNames, scm_anDataOutputTypeIds,"); //$NON-NLS-1$
		} else {
			pwCPP.println("  0, 0,"); //$NON-NLS-1$
		}
		pwCPP.print("  " + adapterCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
		if (adapterCount > 0) {
			pwCPP.print("scm_astAdapterInstances};"); //$NON-NLS-1$
		} else {
			pwCPP.print(" 0\n};"); //$NON-NLS-1$
		}

		pwCPP.println("\n"); //$NON-NLS-1$

		if (baseClass.equals("CBasicFB")) { //$NON-NLS-1$
			if (0 != internalCount) {
				pwH.println("\n  static const SInternalVarsInformation scm_stInternalVars;\n"); //$NON-NLS-1$
				pwCPP.println("\nconst SInternalVarsInformation FORTE_" + name //$NON-NLS-1$
						+ "::scm_stInternalVars = {" + internalCount //$NON-NLS-1$
						+ ", scm_anInternalsNames, scm_anInternalsTypeIds};\n"); //$NON-NLS-1$
			}
		}

		if (libraryType instanceof AdapterType) {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpecPlug;\n"); //$NON-NLS-1$
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name //$NON-NLS-1$
					+ "::scm_stFBInterfaceSpecPlug = {"); //$NON-NLS-1$

			// Mirror the interface for the plug
			pwCPP.print("  " + eventOutCount + ","); //$NON-NLS-1$ //$NON-NLS-2$

			if (eventOutCount != 0) {
				pwCPP.print("  scm_anEventOutputNames,"); //$NON-NLS-1$
				if (dataOutCount != 0) {
					pwCPP.print("  scm_anEOWith,"); //$NON-NLS-1$
				} else {
					pwCPP.print("  0,"); //$NON-NLS-1$
				}
				pwCPP.println("  scm_anEOWithIndexes,"); //$NON-NLS-1$
			} else {
				pwCPP.print("  0,"); //$NON-NLS-1$
				pwCPP.print("  0,"); //$NON-NLS-1$
				pwCPP.println("  0,"); //$NON-NLS-1$
			}

			pwCPP.print("  " + eventInCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
			if (eventInCount != 0) {
				pwCPP.print("  scm_anEventInputNames,"); //$NON-NLS-1$
			} else {
				pwCPP.print("  0, "); //$NON-NLS-1$
			}
			if (dataInCount != 0) {
				pwCPP.print("  scm_anEIWith, scm_anEIWithIndexes,"); //$NON-NLS-1$
			} else {
				pwCPP.print("  0, 0,"); //$NON-NLS-1$
			}

			pwCPP.print("  " + dataOutCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
			if (dataOutCount != 0) {
				pwCPP.println("  scm_anDataOutputNames, scm_anDataOutputTypeIds,"); //$NON-NLS-1$
			} else {
				pwCPP.println("  0, 0, "); //$NON-NLS-1$
			}

			pwCPP.print("  " + dataInCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
			if (dataInCount != 0) {
				pwCPP.println("  scm_anDataInputNames, scm_anDataInputTypeIds,"); //$NON-NLS-1$
			} else {
				pwCPP.println("  0, 0,"); //$NON-NLS-1$
			}
			pwCPP.print("  " + adapterCount + ","); //$NON-NLS-1$ //$NON-NLS-2$
			if (adapterCount > 0) {
				pwCPP.print("scm_astAdapterInstances};"); //$NON-NLS-1$
			} else {
				pwCPP.print(" 0\n};"); //$NON-NLS-1$
			}

			pwCPP.println("\n"); //$NON-NLS-1$
		}

		exportFBDataArray();

		if (!initialValues.isEmpty()) {
			exportInitialValues();
		}

	}

	@Override
	protected void exportECC(final Element eccNode) {
		NodeList nodes = eccNode.getChildNodes();
		int alglen = nodes.getLength();
		int stateCount = 0;
		for (int ii = 0; ii < alglen; ++ii) {
			org.w3c.dom.Node node = nodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECState")) { //$NON-NLS-1$
					pwH.println("  static const TForteInt16 scm_nState" //$NON-NLS-1$
							+ el.getAttribute("Name") + " = " + stateCount //$NON-NLS-1$ //$NON-NLS-2$
							+ ";"); //$NON-NLS-1$
					++stateCount;
				}
			}
		}
		pwH.println(""); //$NON-NLS-1$
		pwCPP.println(""); //$NON-NLS-1$
		// Emit the state enter code
		for (int ii = 0; ii < alglen; ++ii) {
			org.w3c.dom.Node node = nodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECState")) { //$NON-NLS-1$
					emitEnterState(el.getAttribute("Name"), el.getChildNodes()); //$NON-NLS-1$
				}
			}
		}

		stateCount--; // the highest possible state id value
		// execute event functions
		pwH.println("\n  virtual void executeEvent(int pa_nEIID);"); //$NON-NLS-1$
		pwCPP.println("void FORTE_" + name + "::executeEvent(int pa_nEIID){"); //$NON-NLS-1$ //$NON-NLS-2$
		pwCPP.println("  bool bTransitionCleared;"); //$NON-NLS-1$
		pwCPP.println("  do{"); //$NON-NLS-1$
		pwCPP.println("    bTransitionCleared = true;"); //$NON-NLS-1$
		pwCPP.println("    switch(m_nECCState){"); //$NON-NLS-1$

		for (int ii = 0; ii < alglen; ++ii) {
			org.w3c.dom.Node node = nodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECState")) { //$NON-NLS-1$
					String stateName = el.getAttribute("Name"); //$NON-NLS-1$
					pwCPP.println("      case scm_nState" + stateName + ":"); //$NON-NLS-1$ //$NON-NLS-2$
					emitStateTransitions(getStateNamed(stateName));
					pwCPP.println("          bTransitionCleared  = false; //no transition cleared"); //$NON-NLS-1$
					pwCPP.println("        break;"); //$NON-NLS-1$

				}
			}
		}
		pwCPP.println("      default:"); //$NON-NLS-1$
		pwCPP.println(
				"      DEVLOG_ERROR(\"The state is not in the valid range! The state value is: %d. The max value can be: " //$NON-NLS-1$
						+ stateCount + ".\", m_nECCState.operator TForteUInt16 ());"); //$NON-NLS-1$
		pwCPP.println("        m_nECCState = 0; //0 is always the initial state"); //$NON-NLS-1$
		pwCPP.println("        break;"); //$NON-NLS-1$
		pwCPP.println("    }"); //$NON-NLS-1$
		pwCPP.println(
				"    pa_nEIID = cg_nInvalidEventID;  // we have to clear the event after the first check in order to ensure correct behavior"); //$NON-NLS-1$
		pwCPP.println("  }while(bTransitionCleared);"); //$NON-NLS-1$
		pwCPP.println("}"); //$NON-NLS-1$
	}

	private void emitEnterState(final String stateName, final NodeList actionNodes) {
		pwH.println("  void enterState" + stateName + "(void);"); //$NON-NLS-1$ //$NON-NLS-2$
		pwCPP.println("void FORTE_" + name + "::enterState" + stateName //$NON-NLS-1$ //$NON-NLS-2$
				+ "(void){"); //$NON-NLS-1$
		pwCPP.println("  m_nECCState = scm_nState" + stateName + ";"); //$NON-NLS-1$ //$NON-NLS-2$
		int actionlen = actionNodes.getLength();
		for (int ii = 0; ii < actionlen; ++ii) {
			org.w3c.dom.Node node = actionNodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECAction")) { //$NON-NLS-1$
					String buf = el.getAttribute("Algorithm"); //$NON-NLS-1$
					if (!buf.equals("")) { //$NON-NLS-1$
						pwCPP.println("  alg_" + buf + "();"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					buf = el.getAttribute("Output"); //$NON-NLS-1$
					if (!buf.equals("")) { //$NON-NLS-1$
						if (buf.contains(".")) { //$NON-NLS-1$
							String[] adapterevent = buf.split("\\.", 2); //$NON-NLS-1$
							pwCPP.println("  sendAdapterEvent(scm_n" //$NON-NLS-1$
									+ adapterevent[0] + "AdpNum, FORTE_" //$NON-NLS-1$
									+ getAdapterTypeName(adapterevent[0]) + "::scm_nEvent" //$NON-NLS-1$
									+ adapterevent[1] + "ID);"); //$NON-NLS-1$
						} else {
							pwCPP.println("  sendOutputEvent( scm_nEvent" + buf //$NON-NLS-1$
									+ "ID);"); //$NON-NLS-1$
						}
					}
				}
			}
		}
		pwCPP.println("}\n"); //$NON-NLS-1$

	}

	private String getAdapterTypeName(String string) {
		InterfaceList interfaceList = ((FBType) libraryType).getInterfaceList();

		for (AdapterDeclaration adapter : interfaceList.getPlugs()) {
			if (adapter.getName().equals(string)) {
				return adapter.getTypeName();
			}
		}

		for (AdapterDeclaration adapter : interfaceList.getSockets()) {
			if (adapter.getName().equals(string)) {
				return adapter.getTypeName();
			}
		}
		return ""; //$NON-NLS-1$
	}

	private ECState getStateNamed(final String stateName) {
		for (ECState state : ((BasicFBType) libraryType).getECC().getECState()) {
			if (state.getName().equals(stateName)) {
				return state;
			}
		}
		return null;
	}

	private void emitStateTransitions(final ECState state) {
		if (null != state) {
			StringBuilder alternativeGuard = new StringBuilder();
			StringBuilder alternativeEvent = new StringBuilder();

			for (ECTransition transition : state.getOutTransitions()) {
				String event = (null != transition.getConditionEvent()) ? transition.getConditionEvent().getName() : ""; //$NON-NLS-1$
				String guard = transition.getConditionExpression();

				if ((event.length() != 0) || (guard.length() != 0)) {
					pwCPP.print("        if("); //$NON-NLS-1$

					if (event.length() != 0) {
						if (guard.length() != 0) {
							pwCPP.print("("); //$NON-NLS-1$
						}

						if (event.contains(".")) { //$NON-NLS-1$
							String[] adapterevent = event.split("\\.", 2); //$NON-NLS-1$
							pwCPP.print(adapterevent[0] + "()." + adapterevent[1] + "()"); //$NON-NLS-1$ //$NON-NLS-2$
						} else {
							pwCPP.print("scm_nEvent" + event + "ID"); //$NON-NLS-1$ //$NON-NLS-2$
						}

						pwCPP.print(" == pa_nEIID"); //$NON-NLS-1$

						if (guard.length() != 0) {
							pwCPP.print(") && (("); // add a second pair of parenthesis to ensure that the //$NON-NLS-1$
													// guard condition is treated as whole
							emitGuardCondition(guard);
							pwCPP.print("))"); //$NON-NLS-1$
						}
					} else {
						// if we are here the guard length is definitely
						// not zero
						// TODO: Check for adapter-event!
						boolean AdapterEvent = false;
						StringTokenizer mySTok = new StringTokenizer(guard, "&", true); //$NON-NLS-1$
						String myTestString = ""; //$NON-NLS-1$

						// retrieve first element (adapter-events are
						// given as first element of guarding condition;
						// should be in events finally)
						if (mySTok.hasMoreTokens()) {
							myTestString = mySTok.nextToken();
						}
						if (myTestString.contains(".")) { //$NON-NLS-1$
							StringTokenizer mySTok2 = new StringTokenizer(myTestString, ".", false); //$NON-NLS-1$
							String myTestString2 = mySTok2.nextToken();

							AdapterFBType myAd = checkIfAdapter(myTestString2);
							if (null != myAd) {
								alternativeEvent.append(myTestString2).append("()."); //$NON-NLS-1$
								if (mySTok2.hasMoreTokens()) {
									String myTest4Event = mySTok2.nextToken();
									myTest4Event = myTest4Event.trim();
									InterfaceList myIL = myAd.getInterfaceList();
									Event myEv = null;
									EList<Event> myIEvents = myIL.getEventInputs();
									EList<Event> myOEvents = myIL.getEventOutputs();
									Iterator<Event> myIterE = myIEvents.iterator();
									while (myIterE.hasNext()) {
										Event test4Event = myIterE.next();
										if (test4Event.getName().equals(myTest4Event)) {
											myEv = test4Event;
											break;
										}
									}
									if (null == myEv) {
										myIterE = myOEvents.iterator();
										while (myIterE.hasNext()) {
											Event test4Event = myIterE.next();
											if (test4Event.getName().equals(myTest4Event)) {
												myEv = test4Event;
												break;
											}
										}
									}
									if (null != myEv) {
										alternativeEvent.append(myTest4Event).append("()"); //$NON-NLS-1$
										AdapterEvent = true;
										// remove separator "&" from
										// token-list...
										if (mySTok.hasMoreTokens()) {
											mySTok.nextToken();
										}
										alternativeGuard.setLength(0); // Reset sting
										while (mySTok.hasMoreTokens()) {
											alternativeGuard.append(mySTok.nextToken());
										}
									}
								}
							}

						}
						if (AdapterEvent) {
							if (alternativeGuard.length() != 0) {
								pwCPP.print("("); //$NON-NLS-1$
							}
							pwCPP.print(alternativeEvent + " == pa_nEIID"); //$NON-NLS-1$
							if (alternativeGuard.length() != 0) {
								pwCPP.print(") && (("); //$NON-NLS-1$
								emitGuardCondition(alternativeGuard.toString());
								pwCPP.print("))"); //$NON-NLS-1$
							}
						} else {
							// prohibit reuse of transition!
							emitGuardCondition(guard);
						}
					}
					pwCPP.println(")"); //$NON-NLS-1$
					pwCPP.println("          enterState" + transition.getDestination().getName() + "();"); //$NON-NLS-1$ //$NON-NLS-2$
					pwCPP.println("        else"); //$NON-NLS-1$
				}
			}

		} else {
			// TODO add some error here
		}
	}

	private AdapterFBType checkIfAdapter(final String Name) {

		Iterator<AdapterInstance> myIter = adapters.iterator();
		AdapterInstance myAdapterInfo;
		while (myIter.hasNext()) {
			myAdapterInfo = myIter.next();
			if (myAdapterInfo.getName().equals(Name)) {
				return myAdapterInfo.getAdapterFBType();
			}
		}
		return null;
	}

	private void emitGuardCondition(final String guard) {
		if (guard.contains("&") || guard.toUpperCase().contains("AND")) { //$NON-NLS-1$ //$NON-NLS-2$
			pwCPP.print("("); //$NON-NLS-1$
		}
		structuredTextEmitter.exportGuardCondition(guard, pwCPP);
		if (guard.contains("&") || guard.toUpperCase().contains("AND")) { //$NON-NLS-1$ //$NON-NLS-2$
			pwCPP.print(")"); //$NON-NLS-1$
		}
	}

	@Override
	public void export(IFile typeFile, final String destination, final boolean forceOverwrite) throws ExportException {

		eventInCount = 0;

		eventOutCount = 0;

		eventInputs = new ArrayList<>();

		adapterCount = 0;

		adapters.clear();

		initialValues.clear();

		anyVars.clear();

		if (typeFile.exists()) {

			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(false);
				DocumentBuilder db;

				// TODO: set local dtd for validating!
				dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
				db = dbf.newDocumentBuilder();
				Document document;
				document = db.parse(typeFile.getContents());

				docel = document.getDocumentElement();
				convertToLibraryElement2(docel);
				this.destDir = destination;
				name = docel.getAttribute("Name"); //$NON-NLS-1$
				startExport(forceOverwrite);

			} catch (Exception e) {
				forteEmitterErrors.add(" - " + name + " " + e.getMessage() //$NON-NLS-1$ //$NON-NLS-2$
						+ " (" + typeFile.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	@Override
	protected void handleNotPresentEOTag() {
		StringBuilder withs = new StringBuilder();
		String withIndexes = "-1"; //$NON-NLS-1$

		if (dataOutCount != 0) {
			pwH.println("  static const TDataIOID scm_anEOWith[];"); //$NON-NLS-1$
			for (int i = 0; i < dataOutCount; ++i) {
				withs.append(i);
				withs.append(", "); //$NON-NLS-1$
			}
			withs.append("255"); //$NON-NLS-1$
			pwCPP.println("const TDataIOID FORTE_" + name //$NON-NLS-1$
					+ "::scm_anEOWith[] = {" + withs.toString() + "};"); //$NON-NLS-1$ //$NON-NLS-2$
			withIndexes = "0"; //$NON-NLS-1$
		}
		pwH.println("  static const TForteInt16 scm_anEOWithIndexes[];"); //$NON-NLS-1$
		pwCPP.println("const TForteInt16 FORTE_" + name //$NON-NLS-1$
				+ "::scm_anEOWithIndexes[] = {" + withIndexes + "};"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	int getInputIdForContainedFB(String name, String paramName) {
		int retVal = 0;
		FBNetwork containedNetwork = ((CompositeFBType) libraryType).getFBNetwork();

		EList<VarDeclaration> inputs = containedNetwork.getFBNamed(name).getInterface().getInputVars();

		Iterator<VarDeclaration> itRunner = inputs.iterator();
		while (itRunner.hasNext()) {
			VarDeclaration var = itRunner.next();
			if (var.getName().equals(paramName)) {
				return retVal;
			}
			retVal++;
		}

		return -1;
	}

	@Override
	public String getExportFilterDescription() {
		return "FORTE Export for FORTE 1.x"; //$NON-NLS-1$
	}

	@Override
	public String getExportFilterName() {
		return "FORTE 1.x"; //$NON-NLS-1$
	}

	private void exportInitialValues() {

		pwH.println("\nvirtual void setInitialValues();"); //$NON-NLS-1$

		pwCPP.println("\nvoid FORTE_" + name + "::setInitialValues(){"); //$NON-NLS-1$ //$NON-NLS-2$

		for (VarDefinition var : initialValues) {
			if (!var.initialValue.isEmpty()) {
				pwCPP.print("  " + var.name + "()"); //$NON-NLS-1$ //$NON-NLS-2$
				if (var.type.equals("STRING") || var.type.equals("WSTRING")) { //$NON-NLS-1$ //$NON-NLS-2$
					pwCPP.println(" = \"" + var.initialValue + "\";"); //$NON-NLS-1$ //$NON-NLS-2$
					continue;
				} else {
					if (var.type.equals("ARRAY")) { //$NON-NLS-1$
						pwCPP.println(".fromString(\"" + var.initialValue //$NON-NLS-1$
								+ "\");"); //$NON-NLS-1$
						continue;
					} else {
						if ((var.type.equals("TIME")) || //$NON-NLS-1$
								(var.type.equals("DATE")) || //$NON-NLS-1$
								(var.type.equals("TIME_OF_DAY")) || //$NON-NLS-1$
								(var.type.equals("DATE_AND_TIME")) //$NON-NLS-1$
						) {
							pwCPP.print(".fromString(\"" + var.initialValue + "\")"); //$NON-NLS-1$ //$NON-NLS-2$
						} else {
							if (var.type.equals("BOOL")) { //$NON-NLS-1$
								pwCPP.print(" = " + var.initialValue.toLowerCase()); //$NON-NLS-1$
							} else {
								pwCPP.print(" = " + var.initialValue); //$NON-NLS-1$
							}
						}
					}
					pwCPP.println(";"); //$NON-NLS-1$
				}
			}
		}

		pwCPP.println("}\n"); //$NON-NLS-1$
	}

	private void exportCFBFBs(EList<FBNetworkElement> fbs) {
		if (!fbs.isEmpty()) {
			pwH.println("\n  static const SCFB_FBInstanceData scm_astInternalFBs[];"); //$NON-NLS-1$
			pwCPP.println("\nconst SCFB_FBInstanceData FORTE_" + name //$NON-NLS-1$
					+ "::scm_astInternalFBs[] = {"); //$NON-NLS-1$

			for (FBNetworkElement fb : fbs) {
				if (!(fb.getType() instanceof AdapterFBType)) {
					pwCPP.println("  {g_nStringId" + fb.getName() //$NON-NLS-1$
							+ ", g_nStringId" + fb.getTypeName() + "},"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			pwCPP.println("};"); //$NON-NLS-1$
		}
	}

	private void exportCFBParams(EList<FBNetworkElement> fbs) {
		numCompFBParams = 0;
		StringBuilder paramString = new StringBuilder();

		// TODO: check for adapters!

		for (FBNetworkElement fb : fbs) {
			InterfaceList il = fb.getInterface();
			if (il != null) { // normally interface has to be set
				for (VarDeclaration v : il.getInputVars()) {
					Value value = v.getValue();
					if (value != null && v.getInputConnections().isEmpty()) {
						// to be sure that there is no input connection --> the input can have a
						// parameter
						if (value.getValue() != null && !value.getValue().isEmpty()) {
							// to be sure that value is not null(not set) and value is not empty
							paramString.append("  {"); //$NON-NLS-1$
							paramString.append(getCompFBIndex(fbs, fb));
							paramString.append(", g_nStringId"); //$NON-NLS-1$
							paramString.append(v.getName());
							paramString.append(", \""); //$NON-NLS-1$
							paramString.append(value.getValue());
							paramString.append("\"},\n"); //$NON-NLS-1$
							numCompFBParams++;
						}
					}
				}
			}
		}

		if (0 != numCompFBParams) {
			pwH.println("\n  static const SCFB_FBParameter scm_astParamters[];"); //$NON-NLS-1$
			pwCPP.println("\nconst SCFB_FBParameter FORTE_" + name //$NON-NLS-1$
					+ "::scm_astParamters[] = {"); //$NON-NLS-1$
			pwCPP.print(paramString.toString());
			pwCPP.println("};"); //$NON-NLS-1$
		}
	}

	private void exportCFBEventConns(EList<EventConnection> eConns, EList<FBNetworkElement> fbs) {
		fannedOutEventConns = 0;
		if (!eConns.isEmpty()) {
			Set<EventConnection> conSet = new HashSet<>();
			StringBuilder fannedOutConns = new StringBuilder();

			pwH.println("\n  static const SCFB_FBConnectionData scm_astEventConnections[];"); //$NON-NLS-1$
			pwCPP.println("\nconst SCFB_FBConnectionData FORTE_" + name //$NON-NLS-1$
					+ "::scm_astEventConnections[] = {"); //$NON-NLS-1$
			int eConnNumber = 0;
			for (EventConnection eConn : eConns) {
				if (!conSet.contains(eConn)) {

					conSet.add(eConn);

					Event src = eConn.getEventSource();
					Event dst = eConn.getEventDestination();

					INamedElement srcFB = (INamedElement) src.eContainer().eContainer();
					INamedElement dstFB = (INamedElement) dst.eContainer().eContainer();

					pwCPP.println(genConnString(src.getName(), srcFB.getName(), getCompFBIndex(fbs, srcFB),
							dst.getName(), dstFB.getName(), getCompFBIndex(fbs, dstFB)));

					if ((src.getOutputConnections().size() > 1)) {
						// we have fan out
						Iterator<Connection> itRunner = src.getOutputConnections().iterator();
						itRunner.next(); // we don't want to start with the
						// first
						while (itRunner.hasNext()) {
							eConn = (EventConnection) itRunner.next();
							conSet.add(eConn);

							src = eConn.getEventSource();
							dst = eConn.getEventDestination();

							srcFB = src.getFBNetworkElement();
							dstFB = dst.getFBNetworkElement();

							fannedOutConns.append(genFannedOutConnString(eConnNumber, getCompFBIndex(fbs, srcFB),
									dst.getName(), (null != dstFB) ? dstFB.getName() : "", ////$NON-NLS-1$
									getCompFBIndex(fbs, dstFB)));
							fannedOutEventConns++;
						}
					}
					eConnNumber++;
				}
			}

			pwCPP.println("};"); //$NON-NLS-1$

			if (0 != fannedOutEventConns) {
				pwH.println("\n  static const SCFB_FBFannedOutConnectionData scm_astFannedOutEventConnections[];"); //$NON-NLS-1$
				pwCPP.println("\nconst SCFB_FBFannedOutConnectionData FORTE_" //$NON-NLS-1$
						+ name + "::scm_astFannedOutEventConnections[] = {"); //$NON-NLS-1$
				pwCPP.print(fannedOutConns.toString());
				pwCPP.println("};"); //$NON-NLS-1$
			}
		}
	}

	private String genConnString(String srcName, String srcFBName, int srcFBNum, String dstName, String dstFBName,
			int dstFBNum) {
		StringBuilder retVal = new StringBuilder("  {"); //$NON-NLS-1$
		retVal.append(genConnPortPartString(srcName, srcFBName, srcFBNum));
		retVal.append(", "); //$NON-NLS-1$
		retVal.append(genConnPortPartString(dstName, dstFBName, dstFBNum));
		retVal.append("},"); //$NON-NLS-1$
		return retVal.toString();
	}

	private String genConnPortPartString(String name, String fBName, int fBNum) {
		StringBuilder retVal = new StringBuilder();
		if (-1 == fBNum) { // Interface of CFB
			retVal.append("GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId"); //$NON-NLS-1$
		} else {
			retVal.append("GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId"); //$NON-NLS-1$
			retVal.append(fBName);
			retVal.append(", g_nStringId"); //$NON-NLS-1$
		}
		if (-2 == fBNum) { // Adapter
			retVal.append(name);
			retVal.append("), CCompositeFB::scm_nAdapterMarker |"); //$NON-NLS-1$
			for (int i = 0; i < adapterCount; i++) {
				if (adapters.get(i).getName().equals(fBName)) {
					retVal.append(i);
					break;
				}
			}
		} else {
			retVal.append(name);
			retVal.append("), "); //$NON-NLS-1$
			retVal.append(fBNum);
		}
		return retVal.toString();
	}

	private String genFannedOutConnString(int connNum, int srcFBNum, String dstName, String dstFBName, int dstFBNum) {
		StringBuilder retVal = new StringBuilder("  {"); //$NON-NLS-1$
		retVal.append(connNum).append(", "); //$NON-NLS-1$
		retVal.append(genConnPortPartString(dstName, dstFBName, dstFBNum));
		retVal.append("},\n"); //$NON-NLS-1$
		return retVal.toString();
	}

	private void exportCFBDataConns(EList<DataConnection> dataConns, EList<FBNetworkElement> fbs) {
		fannedOutDataConns = 0;
		if (!dataConns.isEmpty()) {
			Set<DataConnection> conSet = new HashSet<>();
			StringBuilder fannedOutConns = new StringBuilder();

			pwH.println("\n  static const SCFB_FBConnectionData scm_astDataConnections[];"); //$NON-NLS-1$
			pwCPP.println("\nconst SCFB_FBConnectionData FORTE_" + name //$NON-NLS-1$
					+ "::scm_astDataConnections[] = {"); //$NON-NLS-1$
			int dConnNumber = 0;
			for (DataConnection dConn : dataConns) {
				if (!conSet.contains(dConn)) {
					DataConnection primConn = getInterfaceDstedDataConn(dConn.getSource().getOutputConnections());
					if (null == primConn) {
						primConn = dConn;
					}
					conSet.add(primConn);

					VarDeclaration src = primConn.getDataSource();
					VarDeclaration dst = primConn.getDataDestination();

					if ((null != src) && (null != dst)) {
						INamedElement srcFB = (INamedElement) src.eContainer().eContainer();
						INamedElement dstFB = (INamedElement) dst.eContainer().eContainer();
						int primDstIndex = getCompFBIndex(fbs, dstFB);

						pwCPP.println(genConnString(src.getName(), srcFB.getName(), getCompFBIndex(fbs, srcFB),
								dst.getName(), dstFB.getName(), primDstIndex));

						if ((src.getOutputConnections().size() > 1)) {
							// we have fan out
							for (Connection itRunner : src.getOutputConnections()) {
								DataConnection dataConn = (DataConnection) itRunner;
								if (!conSet.contains(itRunner)) {
									conSet.add(dataConn);

									src = dataConn.getDataSource();
									dst = dataConn.getDataDestination();

									srcFB = (INamedElement) src.eContainer().eContainer();
									dstFB = (INamedElement) dst.eContainer().eContainer();

									int dstIndex = getCompFBIndex(fbs, dstFB);
									if ((-1 == dstIndex) && (-1 == primDstIndex)) {
										fannedOutConns.append(
												"#error a fannout to several composite FB's outputs is currently not supported: "); //$NON-NLS-1$
										forteEmitterErrors.add(" - " + name
												+ " FORTE does currently not allow that a data a composite's data connection may be connected to several data outputs of the composite FB."); //$NON-NLS-1$
									}

									fannedOutConns.append(genFannedOutConnString(dConnNumber,
											getCompFBIndex(fbs, srcFB), dst.getName(), dstFB.getName(), dstIndex));
									fannedOutDataConns++;
								}
							}
						}
						dConnNumber++;
					}
				}
			}
			pwCPP.println("};"); //$NON-NLS-1$

			if (0 != fannedOutDataConns) {
				pwH.println("\n  static const SCFB_FBFannedOutConnectionData scm_astFannedOutDataConnections[];"); //$NON-NLS-1$
				pwCPP.println("\nconst SCFB_FBFannedOutConnectionData FORTE_" //$NON-NLS-1$
						+ name + "::scm_astFannedOutDataConnections[] = {"); //$NON-NLS-1$
				pwCPP.print(fannedOutConns.toString());
				pwCPP.println("};"); //$NON-NLS-1$
			}
		}
	}

	private void exportFBDataArray() {
		if (baseClass.equals("CBasicFB")) { //$NON-NLS-1$
			pwH.print("   FORTE_BASIC_FB_DATA_ARRAY("); //$NON-NLS-1$
		} else {
			if (libraryType instanceof AdapterType) {
				pwH.print("   FORTE_ADAPTER_DATA_ARRAY(" + eventInCount + ", "); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				pwH.print("   FORTE_FB_DATA_ARRAY("); //$NON-NLS-1$
			}
		}

		pwH.print(eventOutCount + ", " + dataInCount + ", " + dataOutCount); //$NON-NLS-1$ //$NON-NLS-2$
		if (baseClass.equals("CBasicFB")) { //$NON-NLS-1$
			pwH.print(", " + internalCount); //$NON-NLS-1$
		}
		// TODO add correct number of adapters here
		pwH.println(", " + adapterCount + ");"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private DataConnection getInterfaceDstedDataConn(EList<Connection> dataConns) {
		for (Connection dc : dataConns) {
			if ((null != dc.getDestination()) && (null != dc.getDestination().eContainer())
					&& (dc.getDestination().eContainer().eContainer() instanceof CompositeFBType)) {
				return (DataConnection) dc;
			}
		}
		return null;
	}

	private int getCompFBIndex(EList<FBNetworkElement> fbs, INamedElement fb) {
		int retval = -1;
		retval = fbs.indexOf(fb);
		if (retval != -1 && fbs.get(retval).getType() instanceof AdapterFBType) {
			retval = -2;
		}
		return retval;
	}
}
