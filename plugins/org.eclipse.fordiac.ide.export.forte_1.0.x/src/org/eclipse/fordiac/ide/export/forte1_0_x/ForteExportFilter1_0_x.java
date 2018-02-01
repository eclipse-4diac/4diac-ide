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
import java.util.StringTokenizer;
import java.util.Vector;

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
import org.eclipse.fordiac.ide.model.libraryElement.FB;
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
public class ForteExportFilter1_0_x extends ExportFilter implements
		IExportFilter {

	protected class adapterInstance {
		public String stName;
		public String stAdapterType;
		public boolean bIsPlug;
		public AdapterFBType oAdapterFBType;
	}

	private String baseClass;

	private int fannedOutEventConns = 0;

	private int fannedOutDataConns = 0;

	private int numCompFBParams = 0;

	protected int eventInCount;

	protected int eventOutCount;

	protected int adapterCount;

	protected ArrayList<adapterInstance> adapters = new ArrayList<adapterInstance>();

	Vector<FB> internalFBs = new Vector<FB>();

	protected Vector<String> eventInputs = new Vector<String>();

	private StructuredTextEmitter structuredTextEmitter = new StructuredTextEmitter(
			this);

	/**
	 * Holds all vars that have an special initial value set.
	 */
	private Vector<VarDefinition> initialValues = new Vector<VarDefinition>();

	private final ArrayList<String> anyVars = new ArrayList<String>();

	private boolean inputWithsUsed;

	// private boolean usesArrays = false;

	public ForteExportFilter1_0_x() {
		// empty default constructor
	}

	public ForteExportFilter1_0_x(final Document doc, final String dest) {
		super(doc, dest);
	}

	public void addErrorMsg(final String msg) {
		forteEmitterErrors.add(" - " + libraryType.getName() + ": " + msg);
	}

	public void addWarningMsg(final String msg) {
		forteEmitterWarnings.add(" - " + libraryType.getName() + ": "  + msg);
	}

	public void addInfoMsg(final String msg) {
		forteEmitterInfos.add(" - " + libraryType.getName() + ": "  + msg);
	}

	public Map<String, VarDefinition> getVars() {
		return vars;
	}

	public ArrayList<adapterInstance> getAdapters() {
		return adapters;
	}

	@Override
	protected void exportHeader() {

		String buf = "/*************************************************************************";
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** FORTE Library Element";
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " ***";
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x!";
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " ***";
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Name: " + name;
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Description: " + libraryType.getComment();
		pwH.println(buf);
		pwCPP.println(buf);
		buf = " *** Version: ";
		pwH.println(buf);
		pwCPP.println(buf);

		if (libraryType instanceof FBType)

			if (!((FBType) libraryType).getVersionInfo().isEmpty()) {
				for (int i = 0; i < ((FBType) libraryType).getVersionInfo()
						.size(); ++i) {
					VersionInfo vi = ((FBType) libraryType).getVersionInfo()
							.get(i);
					buf = " ***     " + vi.getVersion() + ": " + vi.getDate()
							+ "/" + vi.getAuthor() + " - "
							+ vi.getOrganization() + " - " + vi.getRemarks();
					pwH.println(buf);
					pwCPP.println(buf);
				}
			}

		buf = " *************************************************************************/";
		pwH.println(buf);
		pwCPP.println(buf);
		// export the stuff that is only in the h file
		buf = "\n#ifndef _" + libraryType.getName().toUpperCase() + "_H_";
		pwH.println(buf);
		buf = "#define _" + libraryType.getName().toUpperCase() + "_H_";
		pwH.println(buf);
		// export the stuff that is only in the cpp file
		buf = "\n#include \"" + libraryType.getName() + ".h\"";
		pwCPP.println(buf);
		
		pwCPP.println("#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP");
		pwCPP.println("#include \"" + libraryType.getName() +"_gen.cpp\"");
		pwCPP.println("#endif");
		
		exportCompilerInfoHeaders();
	}

	private void exportCompilerInfoHeaders() {
		if(libraryType instanceof CompilableType){
			CompilableType type = (CompilableType)libraryType;
			if(null != type.getCompilerInfo()){
				if(null != type.getCompilerInfo().getHeader()){
					pwCPP.println(type.getCompilerInfo().getHeader());
				}		
			}
		}		
	}

	@Override
	protected void exportClosingCode() {
		String buf = "\n};\n\n#endif //close the ifdef sequence from the beginning of the file\n";
		pwH.println(buf);
		buf = "\n";
		pwCPP.println(buf);
	}

	@Override
	protected void exportFBStarter() {
		InterfaceList interfaceList = null;

		if (libraryType instanceof BasicFBType) {
			baseClass = "CBasicFB";
			pwH.println("\n#include <basicfb.h>");
			forteEmitterInfos
					.add("  - Creating header and source files for Basic Function Block "
							+ libraryType.getName());
			interfaceList = ((FBType) libraryType).getInterfaceList();
		} else if (libraryType instanceof CompositeFBType) {
			pwH.println("\n#include <cfb.h>");
			pwH.println("#include <typelib.h>");
			baseClass = "CCompositeFB";
			forteEmitterInfos
					.add("  - Creating header and source files for Composite Function Block "
							+ libraryType.getName());
			interfaceList = ((FBType) libraryType).getInterfaceList();

		} else if (libraryType instanceof ServiceInterfaceFBType) {
			pwH.println("\n#include <funcbloc.h>");
			baseClass = "CFunctionBlock";
			forteEmitterInfos
					.add("  - Creating header and source files for Service Interface Function Block "
							+ name);
			interfaceList = ((FBType) libraryType).getInterfaceList();
		} else if (libraryType instanceof AdapterType) {
			pwH.println("\n#include <adapter.h>");
			pwH.println("#include <typelib.h>");
			baseClass = "CAdapter";
			forteEmitterInfos
					.add("  - Creating header and source files for Adapter Function Block "
							+ libraryType.getName());
			interfaceList = ((AdapterType) libraryType).getInterfaceList();
		} else {
			forteEmitterErrors
					.add("  - FB is not of supported class {Basic Function Block, Composite Function Block, Service Interface Function Block}");
		}

		if (interfaceList != null) {
			HashSet<String> datatypeNames = new HashSet<String>();
			HashSet<String> adapterNames = new HashSet<String>();
			boolean lArrayUsed = false;

			lArrayUsed = extractDataTypeNames(interfaceList.getInputVars(),
					datatypeNames, lArrayUsed);
			lArrayUsed = extractDataTypeNames(interfaceList.getOutputVars(),
					datatypeNames, lArrayUsed);
			if (libraryType instanceof BasicFBType) {
				lArrayUsed = extractDataTypeNames(
						((BasicFBType) libraryType).getInternalVars(),
						datatypeNames, lArrayUsed);
			}

			if (!datatypeNames.isEmpty()) {
				String[] x = datatypeNames.toArray(new String[0]);
				for (int i = 0; i < x.length; ++i) {
					pwH.println("#include <forte_" + x[i].toLowerCase() + ".h>");
					if (x[i].startsWith("ANY")) {
						pwH.println("\n#ERROR type contains variables of type ANY. Please check the usage of these variables as we can not gurantee correct usage on export!\n");

					} 
				}
				if (lArrayUsed) {
					pwH.println("#include <forte_array.h>");
				}
			}

			extractAdapterTypeNames(interfaceList.getPlugs(), adapterNames);
			extractAdapterTypeNames(interfaceList.getSockets(), adapterNames);
			if (!adapterNames.isEmpty()) {
				String[] x = adapterNames.toArray(new String[0]);
				for (int i = 0; i < x.length; ++i) {
					pwH.println("#include \"" + x[i] + ".h\"");
				}
			}
		}

		pwH.println("\nclass FORTE_" + name + ": public " + baseClass + "{");
		if (libraryType instanceof AdapterType) {
			pwH.print("  DECLARE_ADAPTER_TYPE");
		} else {
			pwH.print("  DECLARE_FIRMWARE_FB");
		}
		pwH.println("(FORTE_" + name + ")");
		pwH.println("\nprivate:");

		if (libraryType instanceof AdapterType) {
			pwCPP.print("\nDEFINE_ADAPTER_TYPE");
		} else {
			pwCPP.print("\nDEFINE_FIRMWARE_FB");
		}
		pwCPP.println("(FORTE_" + name + ", g_nStringId" + name + ")\n");

	}

	private static void extractAdapterTypeNames(EList<AdapterDeclaration> pa_Adapters,
			HashSet<String> pa_datatypeNames) {
		if (!pa_Adapters.isEmpty()) {
			for (int i = 0; i < pa_Adapters.size(); i++) {
				pa_datatypeNames.add(pa_Adapters.get(i).getTypeName());
			}
		}
	}

	private static Boolean extractDataTypeNames(
			final EList<VarDeclaration> pa_InputVars,
			HashSet<String> pa_datatypeNames, boolean pa_UsesArray) {
		if (!pa_InputVars.isEmpty()) {
			for (int i = 0; i < pa_InputVars.size(); i++) {
				VarDeclaration var = pa_InputVars.get(i);
				if (!(var instanceof AdapterDeclaration)) {
					pa_datatypeNames.add(var.getTypeName());
					if (var.isArray()) {
						pa_UsesArray = true;
					}
				}
			}
		}
		return pa_UsesArray;
	}

	@Override
	protected void exportCompFBExecuteEventMethod() {
	}

	@Override
	protected void exportSIFBExecuteEvent() {
		pwH.println("\n  void executeEvent(int pa_nEIID);");

		pwCPP.println("\nvoid FORTE_" + name + "::executeEvent(int pa_nEIID){");
		pwCPP.println("  switch(pa_nEIID){");

		for (int i = 0; i < eventInCount; i++) {
			pwCPP.println("    case scm_nEvent" + eventInputs.elementAt(i)
					+ "ID:");
			pwCPP.println("#error add code for " + eventInputs.elementAt(i)
					+ " event!");
			pwCPP.println("/*\n  do not forget to send output event, calling e.g.\n      sendOutputEvent(scm_nEventCNFID);\n*/");
			pwCPP.println("      break;");
		}
		pwCPP.println("  }");
		pwCPP.println("}\n");
	}

	@Override
	protected void exportFBNetworkInternalInterface() {
	}

	@Override
	protected void exportFBConstructor() {
		pwH.println("\npublic:");

		if (baseClass.equals("CBasicFB")) {
			pwH.println("  FORTE_"
					+ name
					+ "(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) : ");
			pwH.print("       "
					+ baseClass
					+ "(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId");
			if (internalCount != 0) {
				pwH.print(",\n           &scm_stInternalVars");
			} else {
				pwH.print(",\n              0");
			}
			pwH.print(", m_anFBConnData, m_anFBVarsData");

		} else {
			if (baseClass.equals("CCompositeFB")) {
				pwH.print("  COMPOSITE_FUNCTION_BLOCK_CTOR");
			} else if (libraryType instanceof AdapterType) {
				pwH.print("  ADAPTER_CTOR");
			} else {
				pwH.print("  FUNCTION_BLOCK_CTOR");
			}
			pwH.print("(FORTE_" + name);
		}

		pwH.print("){\n");
		// Currently the constructors are empty maybe this will change
		pwH.println("  };\n");

		pwH.println("  virtual ~FORTE_" + name + "(){};");

	}

	@Override
	protected void exportFBManagedObjectMethods() {
		// this is not needed any more
	}

	@Override
	protected void exportResStarter() {
		baseClass = "CResource";

		pwH.println("\n#include <resource.h>");
		pwH.println("#include <class0objhand.h>");
		forteEmitterInfos
				.add("  - Creating header and source files for Resource "
						+ name);

		exportResDevHeader();

		pwH.println("\nclass FORTE_" + name + ": public CResource{");
		pwH.println("  DECLARE_FIRMWARE_FB(FORTE_" + name + ")");
		pwH.println("\nprivate:");

		pwCPP.println("\nDEFINE_FIRMWARE_FB(FORTE_" + name + ", g_nStringId"
				+ name + ");\n");

		exportVarInputs(docel);
		exportFBInterfaceSpec();
	}

	@Override
	protected void exportResConstructor() {
		pwH.println("\n  C61499Class0ObjectHandler m_oClass0ObjectHandler;  //!< The object handler to be used for this resources");

		pwH.println("\npublic:");
		pwH.println("  FORTE_"
				+ name
				+ "(CStringDictionary::TStringId pa_nInstanceNameId, CResource* pa_poDevice);");
		pwH.println("  virtual ~FORTE_" + name + "(){};");

		pwCPP.println("FORTE_"
				+ name
				+ "::FORTE_"
				+ name
				+ "(CStringDictionary::TStringId pa_nInstanceNameId, CResource* pa_poDevice) : ");
		pwCPP.println("       CResource(pa_poDevice, &scm_stFBInterfaceSpec, pa_nInstanceNameId, m_oClass0ObjectHandler, m_anFBConnData, m_anFBVarsData),");
		pwCPP.println("       m_oClass0ObjectHandler(*this){");

		exportDevResElements("FB");
		exportResDevConnections(docel.getElementsByTagName("EventConnections"));
		exportResDevConnections(docel.getElementsByTagName("DataConnections"));

		pwCPP.println("}\n");
	}

	@Override
	protected void exportDeviceStarter() {
		baseClass = "CDevice";

		pwH.println("\n#include <device.h>");
		pwH.println("#include <class1objhand.h>");
		forteEmitterInfos
				.add("  - Creating header and source files for Device " + name);

		exportResDevHeader();

		pwH.println("\nclass FORTE_" + name + ": public CDevice{");
		pwH.println("\nprivate:");

		exportVarInputs(docel);
		exportFBInterfaceSpec();
	}

	@Override
	protected void exportDeviceConstructor() {
		pwH.println("\n  C61499Class1ObjectHandler m_oClass1ObjectHandler;  //!< The object handler to be used for this device");

		pwH.println("\npublic:");
		pwH.println("  FORTE_" + name + "();");
		pwH.println("  virtual ~FORTE_" + name + "(){};");

		pwCPP.println("FORTE_" + name + "::FORTE_" + name + "() : ");
		pwCPP.println("       CDevice(&scm_stFBInterfaceSpec, CStringDictionary::scm_nInvalidStringId, m_oClass1ObjectHandler, m_anFBConnData, m_anFBVarsData),");
		pwCPP.println("       m_oClass1ObjectHandler(*this){");

		exportDevResElements("Resource");
		exportResDevConnections(docel.getElementsByTagName("DataConnections"));

		pwCPP.println("}\n");
	}

	private void exportResDevHeader() {
		HashSet<String> resVarsTypes = new HashSet<String>();
		NodeList l1 = docel.getElementsByTagName("VarDeclaration");
		for (int i = 0; i < l1.getLength(); i++) {
			org.w3c.dom.Node node = l1.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				resVarsTypes.add(el.getAttribute("Type"));
			}
		}

		for (String element : resVarsTypes) {
			pwH.println("#include <forte_" + element.toLowerCase() + ".h>");
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
						// pwCPP
						// .println("
						// createConnection(CConnection::genConPortId(\""
						// + childel.getAttribute("Source")
						// + "\"), CConnection::genConPortId(\""
						// + childel.getAttribute("Destination")
						// + "\"));");
						String[] source = childel.getAttribute("Source").split(
								"\\.");
						String[] dest = childel.getAttribute("Destination")
								.split("\\.");

						pwCPP.println(MessageFormat
								.format("  m_roObjectHandler.createConnection({0}, {1});",
										new Object[] {
												source.length == 2 ? MessageFormat
														.format("GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId{0}, g_nStringId{1})",
																(Object[]) source)
														: MessageFormat
																.format("GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId{0})",
																		(Object[]) source),
												dest.length == 2 ? MessageFormat
														.format("GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId{0}, g_nStringId{1})",
																(Object[]) dest)
														: MessageFormat
																.format("GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId{0})",
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
				pwCPP.println("  m_roObjectHandler.addFB(CTypeLib::createFB(g_nStringId"
						+ el.getAttribute("Name")
						+ ", g_nStringId"
						+ el.getAttribute("Type") + ", this));");
				NodeList childs = el.getChildNodes();
				for (int j = 0; j < childs.getLength(); j++) {
					org.w3c.dom.Node childnode = childs.item(j);
					if (childnode instanceof Element) {
						Element childel = (Element) childnode;
						// pwCPP.println(" createValue(\""
						// + trimSTRING(childel.getAttribute("Value"))
						// + "\", \"" + childel.getAttribute("Name")
						// + "\");");
						// FIX gebenh -> createValue lead to compile erros
						// TODO azoitl -> please check whether this is a correct
						// way to set values
						pwCPP.println(MessageFormat
								.format("  m_roObjectHandler.getFB(g_nStringId{0})->getDataInput(g_nStringId{1})->fromString(\"{2}\");",
										new Object[] { el.getAttribute("Name"),
												childel.getAttribute("Name"),
												childel.getAttribute("Value") }));
					}
				}
			}
		}
	}

	@Override
	protected void exportVarNameArrays(final String namePrefix,
			final NodeList nodes) {
		int count = 0;
		StringBuilder names = new StringBuilder();
		StringBuilder typenames = new StringBuilder();
		int len = nodes.getLength();
		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = nodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("VarDeclaration")) {
					count++;
					if (count != 1) {
						names.append(", ");
						typenames.append(", ");
					}
					names.append("g_nStringId");
					names.append(el.getAttribute("Name"));
					String arraySize = el.getAttribute("ArraySize");
					if (null != arraySize) {
						if (!arraySize.equals("")) {
							typenames.append("g_nStringIdARRAY, "); 
							typenames.append(arraySize);
							typenames.append(", ");
						}
					}
					typenames.append("g_nStringId"); 
					typenames.append(el.getAttribute("Type"));
				}
			}
		}
		if (count != 0) {
			if (libraryType instanceof AdapterType) {
				pwH.println(" private:");
			}
			pwH.println("  static const CStringDictionary::TStringId scm_an" + namePrefix + "Names[];");
			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name
					+ "::scm_an" + namePrefix + "Names[] = {" + names.toString() + "};\n");

			pwH.println("  static const CStringDictionary::TStringId scm_an" + namePrefix + "TypeIds[];");

			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name
					+ "::scm_an" + namePrefix + "TypeIds[] = {" + typenames + "};\n");
			if (libraryType instanceof AdapterType) {
				pwH.println(" public:");
			}
		}

	}

	@Override
	protected void exportEvents(final String namePrefix, final NodeList nodes,
			final List<String> varNames) {

		int count = 0;
		String names = "";
		String withs = "";
		String withIndexes = "";
		int withcount[] = new int[varNames.size()];
		for (int i = 0; i < varNames.size(); ++i) {
			withcount[i] = 0;
		}
		int indexCount = 0;
		int startindex;
		int len = nodes.getLength();

		if ((libraryType instanceof AdapterType)
				&& (!(namePrefix.equals("EventInputs")))) {
			pwH.println(" public:");
		}

		for (int i = 0; i < len; i++) {
			org.w3c.dom.Node node = nodes.item(i);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("Event")) {
					exportEventID(el.getAttribute("Name"), count);
					count++;
					if (count != 1) {
						names += ", ";
						withIndexes += ", ";
					}
					names += "g_nStringId" + el.getAttribute("Name");
					if (namePrefix.equals("EventInput")) {
						eventInputs.add(el.getAttribute("Name"));
					}

					NodeList eventChilds = el.getChildNodes();
					int childsLen = eventChilds.getLength();
					int eventwithcount = 0;
					startindex = indexCount;
					for (int j = 0; j < childsLen; ++j) {
						org.w3c.dom.Node childNode = eventChilds.item(j);
						if (childNode instanceof Element) {
							Element childElement = (Element) childNode;
							if (childElement.getNodeName().equals("With")) {
								String var = childElement.getAttribute("Var");
								int pos = varNames.indexOf(var);
								++(withcount[pos]);
								if (indexCount != 0) {
									withs += ", ";
								}
								withs += pos;
								indexCount++;
								eventwithcount++;
							}
						}
					}
					if (eventwithcount == 0) {
						withIndexes += "-1";
					} else {
						withs += ", 255";
						indexCount++;
						withIndexes += startindex;
					}
				}
			}
		}

		if (libraryType instanceof AdapterType) {
			pwH.println(" private:");
		}

		if (namePrefix.equals("EventOutput")) {
			eventOutCount = count;
			pwH.println("  static const TForteInt16 scm_anEOWithIndexes[];");
			if (count != 0) {
				if (dataOutCount != 0) {
					int nowithcount = 0;
					startindex = indexCount;
					for (int i = 0; i < dataOutCount; ++i) {
						if (withcount[i] == 0) {
							if (indexCount != 0) {
								withs += ", ";
							}
							withs += i;
							++nowithcount;
							++indexCount;
						}
					}
					if (nowithcount == 0) {
						withIndexes += ", -1";
					} else {
						withs += ", 255";
						withIndexes += ", " + startindex;
					}
					pwH.println("  static const TDataIOID scm_anEOWith[];");
					pwCPP.println("const TDataIOID FORTE_" + name
							+ "::scm_anEOWith[] = {" + withs + "};");
				} else {
					withIndexes += ", -1";
				}
			} else {
				if (dataOutCount != 0) {
					pwH.println("  static const TDataIOID scm_anEOWith[];");
					for (int i = 0; i < dataOutCount; ++i) {
						withs += i + ", ";
					}
					withs += "255";
					pwCPP.println("const TDataIOID FORTE_" + name
							+ "::scm_anEOWith[] = {" + withs + "};");
					withIndexes += "0";
				} else {
					withIndexes += "-1";
				}
			}
			pwCPP.println("const TForteInt16 FORTE_" + name
					+ "::scm_anEOWithIndexes[] = {" + withIndexes + "};");
		} else {
			eventInCount = count;
			if (libraryType instanceof AdapterType) {
				pwH.println("  static const TForteInt16 scm_anEIWithIndexes[];");
				if (count != 0) {
					if (dataInCount != 0) {
						int nowithcount = 0;
						startindex = indexCount;
						for (int i = 0; i < dataInCount; ++i) {
							if (withcount[i] == 0) {
								if (indexCount != 0) {
									withs += ", ";
								}
								withs += i;
								++nowithcount;
								++indexCount;
							}
						}
						if (nowithcount == 0) {
							withIndexes += ", -1";
						} else {
							withs += ", 255";
							withIndexes += ", " + startindex;
						}
						pwH.println("  static const TDataIOID scm_anEIWith[];");
						pwCPP.println("const TDataIOID FORTE_" + name
								+ "::scm_anEIWith[] = {" + withs + "};");
					} else {
						withIndexes += ", -1";
					}
				} else {
					if (dataInCount != 0) {
						pwH.println("  static const TDataIOID scm_anEIWith[];");
						for (int i = 0; i < dataInCount; ++i) {
							withs += i + ", ";
						}
						withs += "255";
						pwCPP.println("const TDataIOID FORTE_" + name
								+ "::scm_anEIWith[] = {" + withs + "};");
						withIndexes += "0";
					} else {
						withIndexes += "-1";
					}
				}
				pwCPP.println("const TForteInt16 FORTE_" + name
						+ "::scm_anEIWithIndexes[] = {" + withIndexes + "};");
			} else {
				if (count != 0) {
					pwH.println("  static const TForteInt16 scm_anEIWithIndexes[];");
					pwCPP.println("const TForteInt16 FORTE_" + name
							+ "::scm_anEIWithIndexes[] = {" + withIndexes
							+ "};");
					if ((dataInCount != 0)  && (0 != withs.length())){
							//we have input withs
							pwH.println("  static const TDataIOID scm_anEIWith[];");
							pwCPP.println("const TDataIOID FORTE_" + name
									+ "::scm_anEIWith[] = {" + withs + "};");
					}

				}
			}
			if (dataInCount != 0) {
				inputWithsUsed = (0 != withs.length());
			}
		}
		if (count != 0) {
			pwH.println("  static const CStringDictionary::TStringId scm_an"
					+ namePrefix + "Names[];\n");
			pwCPP.println("const CStringDictionary::TStringId FORTE_" + name
					+ "::scm_an" + namePrefix + "Names[] = {" + names + "};\n");
		}
	}

	private void exportEventID(final String evName, final int count) {
		pwH.println("  static const TEventID scm_nEvent" + evName + "ID = "
				+ Integer.toString(count) + ";");

		if (libraryType instanceof AdapterType) {
			pwH.println("  int " + evName + "() {");
			pwH.println("    return m_nParentAdapterListEventID + scm_nEvent"
					+ evName + "ID;");
			pwH.println("  }");
		}
	}

	@Override
	protected void exportAlgorithm(final String algName, final String type,
			final String src) {
		pwH.println("  void alg_" + algName + "(void);");

		pwCPP.println("void FORTE_" + name + "::alg_" + algName + "(void){");

		if (type.equals("ST")) {
			structuredTextEmitter.exportStructuredTextAlgorithm(src, pwCPP);
		} else {
			pwCPP.println("#error Algorithm of type: '" + type
					+ "' not supported!!!");
			pwCPP.println(src);
		}

		pwCPP.println("\n}\n");
	}

	@Override
	protected void exportVariable(final VarDefinition newVarDef) {
		createVarAccessoryFunction(newVarDef);

		if (0 < newVarDef.arraySizeValue) {
			VarDefinition arraydef = newVarDef;
			arraydef.arraySizeValue = 0;
			arraydef.name = newVarDef.name + "_Array";
			arraydef.type = "ARRAY";
			createVarAccessoryFunction(arraydef);
		}

		if (null != newVarDef.initialValue) {
			if (!newVarDef.initialValue.isEmpty()) {
				initialValues.add(newVarDef);
			}
		}
	}

	private void createVarAccessoryFunction(final VarDefinition newVarDef) {
		pwH.print("  CIEC_" + newVarDef.type + " ");
		if (newVarDef.arraySizeValue < 1) {
			pwH.print("&");
		} else {
			pwH.print("*");
		}

		pwH.println(newVarDef.name + "() {");
		pwH.print("    return ");

		if (newVarDef.arraySizeValue < 1) {
			pwH.print("*static_cast<CIEC_" + newVarDef.type + "*>(");
		} else {
			// we have an array
			pwH.print("(CIEC_" + newVarDef.type
					+ "*)(*static_cast<CIEC_ARRAY *>(");
		}

		if (libraryType instanceof AdapterType) {
			pwH.print("(isSocket()) ? ");
		}

		pwH.print("get");

		switch (newVarDef.inoutinternal) {
		case 0:
			pwH.print("DI");
			break;
		case 1:
			pwH.print("DO");
			break;
		case 2:
			pwH.print("VarInternal");
			break;
		default:
			break;
		}

		pwH.print("(" + Integer.toString(newVarDef.count) + ")");

		if (libraryType instanceof AdapterType) {
			pwH.print(" : get");
			switch (newVarDef.inoutinternal) {
			case 0:
				pwH.print("DO");
				break;
			case 1:
				pwH.print("DI");
				break;
			default:
				break;
			}
			pwH.print("(" + Integer.toString(newVarDef.count) + ")");
		}

		pwH.print(")");
		if (newVarDef.arraySizeValue < 1) {
			pwH.println(";\n  };\n");
		} else {
			// we have an array
			pwH.println(")[0]; //the first element marks the start of the array\n  };\n");
		}

	}

	@Override
	protected void exportFBVar() {
		if (libraryType instanceof CompositeFBType) {
			FBNetwork fbNetwork = ((CompositeFBType) libraryType)
					.getFBNetwork();

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

			pwH.println("  static const SCFB_FBNData scm_stFBNData;");
			pwCPP.println("\nconst SCFB_FBNData FORTE_" + name
					+ "::scm_stFBNData = {");

			if (0 < (allIntFBs.size() - adapterCount)) {
				pwCPP.println("  " + (allIntFBs.size() - adapterCount)
						+ ", scm_astInternalFBs,");
			} else {
				pwCPP.println("  0, 0,");
			}

			if (!fbNetwork.getEventConnections().isEmpty()) {
				pwCPP.println("  "
						+ (fbNetwork.getEventConnections().size() - fannedOutEventConns)
						+ ", scm_astEventConnections,");
				if (0 != fannedOutEventConns) {
					pwCPP.println("  " + fannedOutEventConns
							+ ", scm_astFannedOutEventConnections,");
				} else {
					pwCPP.println("  0, 0,");
				}
			} else {
				pwCPP.println("  0, 0,");
				pwCPP.println("  0, 0,");
			}

			if (!fbNetwork.getDataConnections().isEmpty()) {
				pwCPP.println("  "
						+ (fbNetwork.getDataConnections().size() - fannedOutDataConns)
						+ ", scm_astDataConnections,");
				if (0 != fannedOutDataConns) {
					pwCPP.println("  " + fannedOutDataConns
							+ ", scm_astFannedOutDataConnections,");
				} else {
					pwCPP.println("  0, 0,");
				}
			} else {
				pwCPP.println("  0, 0,");
				pwCPP.println("  0, 0,");
			}

			if (0 != numCompFBParams) {
				pwCPP.println("  " + numCompFBParams + ", scm_astParamters");
			} else {
				pwCPP.println("  0, 0");
			}

			pwCPP.println("};");
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
				EList<AdapterDeclaration> mySockets = interfaceList
						.getSockets();

				adapterCount = myPlugs.size() + mySockets.size();

				for (int i = 0; i < myPlugs.size(); i++) {
					adapterInstance myLocalAdapterInst = new adapterInstance();
					myLocalAdapterInst.stName = myPlugs.get(i).getName();
					myLocalAdapterInst.stAdapterType = myPlugs.get(i)
							.getTypeName();
					myLocalAdapterInst.bIsPlug = true;
					myLocalAdapterInst.oAdapterFBType = findAdapterType(myPlugs
							.get(i));
					adapters.add(myLocalAdapterInst);
				}

				for (int i = 0; i < mySockets.size(); i++) {
					adapterInstance myLocalAdapterInst = new adapterInstance();
					myLocalAdapterInst.stName = mySockets.get(i).getName();
					myLocalAdapterInst.stAdapterType = mySockets.get(i)
							.getTypeName();
					myLocalAdapterInst.bIsPlug = false;
					myLocalAdapterInst.oAdapterFBType = findAdapterType(mySockets
							.get(i));
					adapters.add(myLocalAdapterInst);

				}
				if (adapterCount > 0) {
					pwH.println("  static const SAdapterInstanceDef scm_astAdapterInstances[];\n");
					pwCPP.println("const SAdapterInstanceDef FORTE_" + name
							+ "::scm_astAdapterInstances[] = {");
					for (int i = 0; i < adapters.size(); i++) {
						adapterInstance myAdapter = adapters.get(i);
						if (i > 0)
							pwCPP.println(",");
						pwCPP.print("{g_nStringId" + myAdapter.stAdapterType
								+ ", " + "g_nStringId" + myAdapter.stName
								+ ", ");
						if (myAdapter.bIsPlug) {
							pwCPP.print("true }");
						} else {
							pwCPP.print("false }");
						}

						pwH.print("  FORTE_" + myAdapter.stAdapterType + "& "
								+ myAdapter.stName + "() {\n"
								+ "    return (*static_cast<FORTE_"
								+ myAdapter.stAdapterType + "*>(m_apoAdapters["
								+ i + "]));\n  };\n");

						pwH.println("  static const int scm_n"
								+ myAdapter.stName + "AdpNum = " + i + ";");
					}
					pwCPP.println("};\n");
				}
			}

		}
		if (libraryType instanceof AdapterType) {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpecSocket;\n");
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name
					+ "::scm_stFBInterfaceSpecSocket = {");
		} else {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpec;\n");
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name
					+ "::scm_stFBInterfaceSpec = {");
		}
		pwCPP.print("  " + eventInCount + ",");

		if (eventInCount != 0) {
			pwCPP.print("  scm_anEventInputNames,");
			if ((dataInCount != 0) && (inputWithsUsed)) {
				pwCPP.print("  scm_anEIWith,");
			} else {
				pwCPP.print("  0,");
			}
			pwCPP.println("  scm_anEIWithIndexes,");
		} else {
			pwCPP.print("  0,");
			pwCPP.print("  0,");
			pwCPP.println("  0,");
		}

		pwCPP.print("  " + eventOutCount + ",");
		if (eventOutCount != 0) {
			pwCPP.print("  scm_anEventOutputNames,");
		} else {
			pwCPP.print("  0, ");
		}
		if (dataOutCount != 0) {
			pwCPP.print("  scm_anEOWith, scm_anEOWithIndexes,");
		} else {
			pwCPP.print("  0, 0,");
		}

		pwCPP.print("  " + dataInCount + ",");
		if (dataInCount != 0) {
			pwCPP.println("  scm_anDataInputNames, scm_anDataInputTypeIds,");
		} else {
			pwCPP.println("  0, 0, ");
		}

		pwCPP.print("  " + dataOutCount + ",");
		if (dataOutCount != 0) {
			pwCPP.println("  scm_anDataOutputNames, scm_anDataOutputTypeIds,");
		} else {
			pwCPP.println("  0, 0,");
		}
		pwCPP.print("  " + adapterCount + ",");
		if (adapterCount > 0) {
			pwCPP.print("scm_astAdapterInstances};");
		} else {
			pwCPP.print(" 0\n};");
		}

		pwCPP.println("\n");

		if (baseClass.equals("CBasicFB")) {
			if (0 != internalCount) {
				pwH.println("\n  static const SInternalVarsInformation scm_stInternalVars;\n");
				pwCPP.println("\nconst SInternalVarsInformation FORTE_" + name
						+ "::scm_stInternalVars = {" + internalCount
						+ ", scm_anInternalsNames, scm_anInternalsTypeIds};\n");
			}
		}

		if (libraryType instanceof AdapterType) {
			pwH.println("  static const SFBInterfaceSpec scm_stFBInterfaceSpecPlug;\n");
			pwCPP.println("const SFBInterfaceSpec FORTE_" + name
					+ "::scm_stFBInterfaceSpecPlug = {");

			// Mirror the interface for the plug
			pwCPP.print("  " + eventOutCount + ",");

			if (eventOutCount != 0) {
				pwCPP.print("  scm_anEventOutputNames,");
				if (dataOutCount != 0) {
					pwCPP.print("  scm_anEOWith,");
				} else {
					pwCPP.print("  0,");
				}
				pwCPP.println("  scm_anEOWithIndexes,");
			} else {
				pwCPP.print("  0,");
				pwCPP.print("  0,");
				pwCPP.println("  0,");
			}

			pwCPP.print("  " + eventInCount + ",");
			if (eventInCount != 0) {
				pwCPP.print("  scm_anEventInputNames,");
			} else {
				pwCPP.print("  0, ");
			}
			if (dataInCount != 0) {
				pwCPP.print("  scm_anEIWith, scm_anEIWithIndexes,");
			} else {
				pwCPP.print("  0, 0,");
			}

			pwCPP.print("  " + dataOutCount + ",");
			if (dataOutCount != 0) {
				pwCPP.println("  scm_anDataOutputNames, scm_anDataOutputTypeIds,");
			} else {
				pwCPP.println("  0, 0, ");
			}

			pwCPP.print("  " + dataInCount + ",");
			if (dataInCount != 0) {
				pwCPP.println("  scm_anDataInputNames, scm_anDataInputTypeIds,");
			} else {
				pwCPP.println("  0, 0,");
			}
			pwCPP.print("  " + adapterCount + ",");
			if (adapterCount > 0) {
				pwCPP.print("scm_astAdapterInstances};");
			} else {
				pwCPP.print(" 0\n};");
			}

			pwCPP.println("\n");
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
				if (el.getNodeName().equals("ECState")) {
					pwH.println("  static const TForteInt16 scm_nState"
							+ el.getAttribute("Name") + " = " + stateCount
							+ ";");
					++stateCount;
				}
			}
		}
		pwH.println("");
		pwCPP.println("");
		// Emit the state enter code
		for (int ii = 0; ii < alglen; ++ii) {
			org.w3c.dom.Node node = nodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECState")) {
					emitEnterState(el.getAttribute("Name"), el.getChildNodes());
				}
			}
		}

		stateCount--; // the highest possible state id value
		// execute event functions
		pwH.println("\n  virtual void executeEvent(int pa_nEIID);");
		pwCPP.println("void FORTE_" + name + "::executeEvent(int pa_nEIID){");
		pwCPP.println("  bool bTransitionCleared;");
		pwCPP.println("  do{");
		pwCPP.println("    bTransitionCleared = true;");
		pwCPP.println("    switch(m_nECCState){");

		for (int ii = 0; ii < alglen; ++ii) {
			org.w3c.dom.Node node = nodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECState")) {
					String stateName = el.getAttribute("Name");
					pwCPP.println("      case scm_nState" + stateName + ":");
					emitStateTransitions(getStateNamed(stateName));
					pwCPP.println("          bTransitionCleared  = false; //no transition cleared");
					pwCPP.println("        break;");

				}
			}
		}
		pwCPP.println("      default:");
		pwCPP.println("      DEVLOG_ERROR(\"The state is not in the valid range! The state value is: %d. The max value can be: "
				+ stateCount + ".\", m_nECCState.operator TForteUInt16 ());");
		pwCPP.println("        m_nECCState = 0; //0 is always the initial state");
		pwCPP.println("        break;");
		pwCPP.println("    }");
		pwCPP.println("    pa_nEIID = cg_nInvalidEventID;  // we have to clear the event after the first check in order to ensure correct behavior");
		pwCPP.println("  }while(bTransitionCleared);");
		pwCPP.println("}");
	}

	private void emitEnterState(final String stateName,
			final NodeList actionNodes) {
		pwH.println("  void enterState" + stateName + "(void);");
		pwCPP.println("void FORTE_" + name + "::enterState" + stateName
				+ "(void){");
		pwCPP.println("  m_nECCState = scm_nState" + stateName + ";");
		int actionlen = actionNodes.getLength();
		for (int ii = 0; ii < actionlen; ++ii) {
			org.w3c.dom.Node node = actionNodes.item(ii);
			if (node instanceof Element) {
				Element el = (Element) node;
				if (el.getNodeName().equals("ECAction")) {
					String buf = el.getAttribute("Algorithm");
					if (!buf.equals("")) {
						pwCPP.println("  alg_" + buf + "();");
					}
					buf = el.getAttribute("Output");
					if (!buf.equals("")) {
						if (buf.contains(".")) {
							String[] adapterevent = buf.split("\\.", 2);
							pwCPP.println("  sendAdapterEvent(scm_n"
									+ adapterevent[0] + "AdpNum, FORTE_"
									+ getAdapterTypeName(adapterevent[0]) + "::scm_nEvent"
									+ adapterevent[1] + "ID);");
						} else {
							pwCPP.println("  sendOutputEvent( scm_nEvent" + buf
									+ "ID);");
						}
					}
				}
			}
		}
		pwCPP.println("}\n");

	}

	private String getAdapterTypeName(String string) {
		InterfaceList interfaceList = ((FBType) libraryType).getInterfaceList();
		
		for (AdapterDeclaration adapter : interfaceList.getPlugs()) {
			if(adapter.getName().equals(string)){
				return adapter.getTypeName();
			}				
		}
		
		for (AdapterDeclaration adapter : interfaceList.getSockets()) {
			if(adapter.getName().equals(string)){
				return adapter.getTypeName();
			}				
		}
		return "";
	}
	
	private ECState getStateNamed(final String stateName){
		for (ECState state : ((BasicFBType) libraryType).getECC().getECState()) {
			if(state.getName().equals(stateName)){
				return state;
			}
		}
		return null;
	}

	private void emitStateTransitions(final ECState state) {
		if(null != state){
			String alternativeGuard = "";
			String alternativeEvent = "";
			
			for (ECTransition transition : state.getOutTransitions()) {
				String event = (null != transition.getConditionEvent()) ? transition.getConditionEvent().getName() : "";
				String guard = transition.getConditionExpression();					
		
				if ((event.length() != 0) || (guard.length() != 0)) {
					pwCPP.print("        if(");

					if (event.length() != 0) {
						if (guard.length() != 0) {
							pwCPP.print("(");
						}

						if (event.contains(".")) {
							String[] adapterevent = event.split("\\.", 2);
							pwCPP.print(adapterevent[0] + "()." + adapterevent[1] + "()");
						} else {
							pwCPP.print("scm_nEvent" + event + "ID");
						}

						pwCPP.print(" == pa_nEIID");

						if (guard.length() != 0) {
							pwCPP.print(") && ((");  //add a second pair of parenthesis to ensure that the guard condition is treated as whole
							emitGuardCondition(guard);
							pwCPP.print("))");
						}
					} else {
						// if we are here the guard length is definitely
						// not zero
						// TODO: Check for adapter-event!
						boolean AdapterEvent = false;
						StringTokenizer mySTok = new StringTokenizer(guard, "&", true);
						String myTestString = new String();
						
						// retrieve first element (adapter-events are
						// given as first element of guarding condition;
						// should be in events finally)
						if (mySTok.hasMoreTokens()) {
							myTestString = mySTok.nextToken();
						}
						if (myTestString.contains(".")) {
							StringTokenizer mySTok2 = new StringTokenizer(myTestString, ".", false);
							String myTestString2 = mySTok2.nextToken();

							AdapterFBType myAd = checkIfAdapter(myTestString2);
							if (null != myAd) {
								alternativeEvent = myTestString2 + "().";
								if (mySTok2.hasMoreTokens()) {
									String myTest4Event = mySTok2.nextToken();
									myTest4Event = myTest4Event.trim();
									InterfaceList myIL = myAd.getInterfaceList();
									Event myEv = null;
									EList<Event> myIEvents = myIL.getEventInputs();
									EList<Event> myOEvents = myIL.getEventOutputs();
									Iterator<Event> myIterE = myIEvents.iterator();
									while (myIterE.hasNext()) {
										Event Test4Event = myIterE.next();
										if (Test4Event.getName().equals(myTest4Event)) {
											myEv = Test4Event;
											break;
										}
									}
									if (null == myEv) {
										myIterE = myOEvents.iterator();
										while (myIterE.hasNext()) {
											Event Test4Event = myIterE.next();
											if (Test4Event.getName().equals(myTest4Event)) {
												myEv = Test4Event;
												break;
											}
										}
									}
									if (null != myEv) {
										alternativeEvent += myTest4Event
												+ "()";
										AdapterEvent = true;
										// remove separator "&" from
										// token-list...
										if (mySTok.hasMoreTokens()) {
											myTestString = mySTok.nextToken();
										}
										alternativeGuard = "";
										while (mySTok.hasMoreTokens()) {
											alternativeGuard += mySTok.nextToken();
										}
									}
								}
							}

						}
						if (AdapterEvent) {
							if (alternativeGuard.length() != 0) {
								pwCPP.print("(");
							}
							pwCPP.print(alternativeEvent
									+ " == pa_nEIID");
							if (alternativeGuard.length() != 0) {
								pwCPP.print(") && ((");
								emitGuardCondition(alternativeGuard);
								pwCPP.print("))");
							}
						} else {
							// prohibit reuse of transition!
							emitGuardCondition(guard);
						}
					}
					pwCPP.println(")");
					pwCPP.println("          enterState" + transition.getDestination().getName() + "();");
					pwCPP.println("        else");
				}
			}
							
		}else{
			//TODO add some error here
		}
	}

	private AdapterFBType checkIfAdapter(final String Name) {

		Iterator<adapterInstance> myIter = adapters.iterator();
		adapterInstance myAdapterInfo;
		while (myIter.hasNext()) {
			myAdapterInfo = myIter.next();
			if (myAdapterInfo.stName.equals(Name)) {
				return myAdapterInfo.oAdapterFBType;
			}
		}
		return null;
	}

	private void emitGuardCondition(final String guard) {
		if (guard.contains("&") || guard.toUpperCase().contains("AND")) {
			pwCPP.print("(");
		}
		structuredTextEmitter.exportGuardCondition(guard, pwCPP);
		if (guard.contains("&") || guard.toUpperCase().contains("AND")) {
			pwCPP.print(")");
		}
	}

//	private void extractDataTypes(final NodeList nodes, final HashSet datatypes) {
//		int len = nodes.getLength();
//		for (int ii = 0; ii < len; ++ii) {
//			org.w3c.dom.Node node = nodes.item(ii);
//			if (node instanceof Element) {
//				Element el = (Element) node;
//				if (el.getNodeName().equals("VarDeclaration")) {
//					String type = el.getAttribute("Type");
//					if (type.length() != 0) {
//						datatypes.add(type);
//					}
//					if (el.getAttribute("ArraySize") != null) {
//						usesArrays = true;
//					}
//				}
//			}
//		}
//	}

	@Override
	public void export(IFile typeFile, final String destination,
			final boolean forceOverwrite) throws ExportException {

		eventInCount = 0;

		eventOutCount = 0;

		eventInputs = new Vector<String>();

		adapterCount = 0;

		adapters.clear();

		initialValues.clear();

		anyVars.clear();

		// usesArrays = false;

		if (typeFile.exists()) {

			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				dbf.setNamespaceAware(false);
				DocumentBuilder db;

				// TODO: set local dtd for validating!
				dbf.setAttribute(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
						Boolean.FALSE);
				db = dbf.newDocumentBuilder();
				Document document;
				document = db.parse(typeFile.getContents());

				docel = document.getDocumentElement();
				convertToLibraryElement2(docel);
				this.destDir = destination;
				name = docel.getAttribute("Name");
				startExport(forceOverwrite);

			} catch (Exception e) {
				forteEmitterErrors.add(" - " + name + " " + e.getMessage()
						+ " (" + typeFile.getName() + ")");
			}
		}
	}

	protected void handleNotPresentEOTag() {
		StringBuilder withs = new StringBuilder();
		String withIndexes = "-1";

		if (dataOutCount != 0) {
			pwH.println("  static const TDataIOID scm_anEOWith[];");
			for (int i = 0; i < dataOutCount; ++i) {
				withs.append(i);
				withs.append(", ");
			}
			withs.append("255");
			pwCPP.println("const TDataIOID FORTE_" + name
					+ "::scm_anEOWith[] = {" + withs.toString() + "};");
			withIndexes = "0";
		}
		pwH.println("  static const TForteInt16 scm_anEOWithIndexes[];");
		pwCPP.println("const TForteInt16 FORTE_" + name
				+ "::scm_anEOWithIndexes[] = {" + withIndexes + "};");
	}

	int getInputIdForContainedFB(String name, String paramName) {
		int retVal = 0;
		FBNetwork containedNetwork = ((CompositeFBType) libraryType)
				.getFBNetwork();

		EList<VarDeclaration> inputs = containedNetwork.getFBNamed(name)
				.getInterface().getInputVars();

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
		return "FORTE Export for FORTE 1.x";
	}

	@Override
	public String getExportFilterName() {
		return "FORTE 1.x";
	}

	private void exportInitialValues() {

		pwH.println("\nvirtual void setInitialValues();");

		pwCPP.println("\nvoid FORTE_" + name + "::setInitialValues(){");

		for (VarDefinition var : initialValues) {
			if (!var.initialValue.isEmpty()) {
				pwCPP.print("  " + var.name + "()");
				if (var.type.equals("STRING") || var.type.equals("WSTRING")) {
					pwCPP.println(" = \"" + var.initialValue + "\";");
					continue;
				}
				else{
					if(var.type.equals("ARRAY")) {
						pwCPP.println(".fromString(\"" + var.initialValue
								+ "\");");
						continue;
					}
					else{				
						if((var.type.equals("TIME")) ||
								(var.type.equals("DATE")) ||
								(var.type.equals("TIME_OF_DAY")) ||
								(var.type.equals("DATE_AND_TIME"))
								){
							pwCPP.print(".fromString(\"" + var.initialValue + "\")");
						}
						else{
							if(var.type.equals("BOOL")){
								pwCPP.print(" = " + var.initialValue.toLowerCase());
							}
							else{
								pwCPP.print(" = " + var.initialValue);
							}
						}
					}
					pwCPP.println(";");
				}
			}
		}

		pwCPP.println("}\n");
	}

	private void exportCFBFBs(EList<FBNetworkElement> fbs) {
		if (!fbs.isEmpty()) {
			pwH.println("\n  static const SCFB_FBInstanceData scm_astInternalFBs[];");
			pwCPP.println("\nconst SCFB_FBInstanceData FORTE_" + name
					+ "::scm_astInternalFBs[] = {");

			for (FBNetworkElement fb : fbs) {
				if (!(fb.getType() instanceof AdapterFBType)) {
					pwCPP.println("  {g_nStringId" + fb.getName()
							+ ", g_nStringId" + fb.getTypeName() + "},");
				}
			}
			pwCPP.println("};");
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
					if (value != null && v.getInputConnections().size() == 0) { 
						// to be sure that there is no input connection --> the input can have a parameter
						if (value.getValue() != null && !value.getValue().isEmpty()) { 
							// to be sure that value is not null(not set) and value is not empty
							paramString.append("  {"); 
							paramString.append(getCompFBIndex(fbs, fb));
							paramString.append(", g_nStringId"); 
							paramString.append(v.getName());
							paramString.append(", \"");
							paramString.append(value.getValue()); 
							paramString.append("\"},\n");
							numCompFBParams++;
						}
					}
				}
			}
		}

		if (0 != numCompFBParams) {
			pwH.println("\n  static const SCFB_FBParameter scm_astParamters[];");
			pwCPP.println("\nconst SCFB_FBParameter FORTE_" + name
					+ "::scm_astParamters[] = {");
			pwCPP.print(paramString.toString());
			pwCPP.println("};");
		}
	}

	private void exportCFBEventConns(EList<EventConnection> eConns,
			EList<FBNetworkElement> fbs) {
		fannedOutEventConns = 0;
		if (!eConns.isEmpty()) {
			HashSet<EventConnection> conSet = new HashSet<EventConnection>();
			StringBuilder fannedOutConns = new StringBuilder();

			pwH.println("\n  static const SCFB_FBConnectionData scm_astEventConnections[];");
			pwCPP.println("\nconst SCFB_FBConnectionData FORTE_" + name
					+ "::scm_astEventConnections[] = {");
			int eConnNumber = 0;
			for (EventConnection eConn : eConns) {
				if (!conSet.contains(eConn)) {

					conSet.add(eConn);

					Event src = eConn.getEventSource();
					Event dst = eConn.getEventDestination();

					INamedElement srcFB = (INamedElement) src.eContainer()
							.eContainer();
					INamedElement dstFB = (INamedElement) dst.eContainer()
							.eContainer();

					pwCPP.println(genConnString(src.getName(), srcFB.getName(),
							getCompFBIndex(fbs, srcFB), dst.getName(),
							dstFB.getName(), getCompFBIndex(fbs, dstFB)));

					if ((src.getOutputConnections().size() > 1)) {
						// we have fan out
						Iterator<Connection> itRunner = src.getOutputConnections().iterator();
						itRunner.next(); // we don't want to start with the
						// first
						while (itRunner.hasNext()) {
							eConn = (EventConnection)itRunner.next();
							conSet.add(eConn);

							src = eConn.getEventSource();
							dst = eConn.getEventDestination();

							srcFB = src.getFBNetworkElement();
							dstFB = dst.getFBNetworkElement();

							fannedOutConns.append(genFannedOutConnString(
									eConnNumber, getCompFBIndex(fbs, srcFB),
									dst.getName(), (null != dstFB) ? dstFB.getName() : "",  ////$NON-NLS-1$
									getCompFBIndex(fbs, dstFB)));
							fannedOutEventConns++;
						}
					}
					eConnNumber++;
				}
			}

			pwCPP.println("};");

			if (0 != fannedOutEventConns) {
				pwH.println("\n  static const SCFB_FBFannedOutConnectionData scm_astFannedOutEventConnections[];");
				pwCPP.println("\nconst SCFB_FBFannedOutConnectionData FORTE_"
						+ name + "::scm_astFannedOutEventConnections[] = {");
				pwCPP.print(fannedOutConns.toString());
				pwCPP.println("};");
			}
		}
	}

	private String genConnString(String srcName, String srcFBName,
			int srcFBNum, String dstName, String dstFBName, int dstFBNum) {
		String retVal = new String("  {");
		retVal += genConnPortPartString(srcName, srcFBName, srcFBNum);
		retVal += ", ";
		retVal += genConnPortPartString(dstName, dstFBName, dstFBNum);
		retVal += "},";
		return retVal;
	}

	private String genConnPortPartString(String name, String fBName, int fBNum) {
		StringBuilder retVal = new StringBuilder();
		if (-1 == fBNum) { // Interface of CFB
			retVal.append("GENERATE_CONNECTION_PORT_ID_1_ARG(g_nStringId");
		} else {
			retVal.append("GENERATE_CONNECTION_PORT_ID_2_ARG(g_nStringId");
			retVal.append(fBName);
			retVal.append(", g_nStringId");
		}
		if (-2 == fBNum) { // Adapter
			retVal.append(name); 
			retVal.append("), CCompositeFB::scm_nAdapterMarker |");
			for (int i = 0; i < adapterCount; i++) {
				if (adapters.get(i).stName.equals(fBName)) {
					retVal.append(i);
					break;
				}
			}
		} else {
			retVal.append(name);
			retVal.append("), "); 
			retVal.append(fBNum);
		}
		return retVal.toString();
	}

	private String genFannedOutConnString(int connNum, int srcFBNum,
			String dstName, String dstFBName, int dstFBNum) {
		String retVal = new String("  {");
		retVal += connNum + ", ";
		retVal += genConnPortPartString(dstName, dstFBName, dstFBNum);
		retVal += "},\n";
		return retVal;
	}

	private void exportCFBDataConns(EList<DataConnection> dataConns,
			EList<FBNetworkElement> fbs) {
		fannedOutDataConns = 0;
		if (!dataConns.isEmpty()) {
			HashSet<DataConnection> conSet = new HashSet<DataConnection>();
			StringBuilder fannedOutConns = new StringBuilder();

			pwH.println("\n  static const SCFB_FBConnectionData scm_astDataConnections[];");
			pwCPP.println("\nconst SCFB_FBConnectionData FORTE_" + name
					+ "::scm_astDataConnections[] = {");
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

					if((null != src) && (null != dst)){
						INamedElement srcFB = (INamedElement) src.eContainer()
								.eContainer();
						INamedElement dstFB = (INamedElement) dst.eContainer()
								.eContainer();
						int primDstIndex = getCompFBIndex(fbs, dstFB);
	
						pwCPP.println(genConnString(src.getName(), srcFB.getName(),
								getCompFBIndex(fbs, srcFB), dst.getName(),
								dstFB.getName(), primDstIndex));
	
						if ((src.getOutputConnections().size() > 1)) {
							// we have fan out
							for (Connection itRunner : src.getOutputConnections()) {
								DataConnection dataConn = (DataConnection)itRunner;
								if (!conSet.contains(itRunner)) {
									conSet.add(dataConn);
	
									src = dataConn.getDataSource();
									dst = dataConn.getDataDestination();
	
									srcFB = (INamedElement) src.eContainer()
											.eContainer();
									dstFB = (INamedElement) dst.eContainer()
											.eContainer();
	
									int dstIndex = getCompFBIndex(fbs, dstFB);
									if ((-1 == dstIndex) && (-1 == primDstIndex)) {
										fannedOutConns.append("#error a fannout to several composite FB's outputs is currently not supported: ");
										forteEmitterErrors
												.add(" - "
														+ name
														+ " FORTE does currently not allow that a data a composite's data connection may be connected to several data outputs of the composite FB.");
									}
	
									fannedOutConns.append(genFannedOutConnString(dConnNumber, getCompFBIndex(fbs, srcFB),
											dst.getName(), dstFB.getName(), dstIndex));
									fannedOutDataConns++;
								}
							}
						}					
						dConnNumber++;
					}
				}
			}
			pwCPP.println("};");

			if (0 != fannedOutDataConns) {
				pwH.println("\n  static const SCFB_FBFannedOutConnectionData scm_astFannedOutDataConnections[];");
				pwCPP.println("\nconst SCFB_FBFannedOutConnectionData FORTE_"
						+ name + "::scm_astFannedOutDataConnections[] = {");
				pwCPP.print(fannedOutConns.toString());
				pwCPP.println("};");
			}
		}
	}

	private void exportFBDataArray() {
		if (baseClass.equals("CBasicFB")) {
			pwH.print("   FORTE_BASIC_FB_DATA_ARRAY(");
		} else {
			if (libraryType instanceof AdapterType) {
				pwH.print("   FORTE_ADAPTER_DATA_ARRAY(" + eventInCount + ", ");
			} else {
				pwH.print("   FORTE_FB_DATA_ARRAY(");
			}
		}

		pwH.print(eventOutCount + ", " + dataInCount + ", " + dataOutCount);
		if (baseClass.equals("CBasicFB")) {
			pwH.print(", " + internalCount);
		}
		// TODO add correct number of adapters here
		pwH.println(", " + adapterCount + ");");
	}

	private DataConnection getInterfaceDstedDataConn(
			EList<Connection> dataConns) {
		for (Connection dc : dataConns) {
			if ((null != dc.getDestination()) &&  
			     (null!= dc.getDestination().eContainer()) && 
			     (dc.getDestination().eContainer().eContainer() instanceof CompositeFBType)) {
				return (DataConnection)dc;
			}
		}
		return null;
	}

	private int getCompFBIndex(EList<FBNetworkElement> fbs, INamedElement fb) {
		int retval = -1;
		retval = fbs.indexOf(fb);
		if (retval != -1) {
			if (fbs.get(retval).getType() instanceof AdapterFBType) {
				retval = -2;
			}
		}
		return retval;
	}
}
