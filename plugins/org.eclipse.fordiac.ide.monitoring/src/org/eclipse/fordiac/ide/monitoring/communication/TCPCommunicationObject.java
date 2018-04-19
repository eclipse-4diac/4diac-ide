/*******************************************************************************
 * Copyright (c) 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.communication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager.BreakPoint;
import org.eclipse.fordiac.ide.monitoring.monCom.Data;
import org.eclipse.fordiac.ide.monitoring.monCom.FB;
import org.eclipse.fordiac.ide.monitoring.monCom.Port;
import org.eclipse.fordiac.ide.monitoring.monCom.Response;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.xml.sax.InputSource;


public class TCPCommunicationObject {

	private final MonitorInformation monitorInformation;

	/** The Constant ASN1_TAG_IECSTRING. */
	private static final int ASN1_TAG_IECSTRING = 80;

	private Socket socket = null;

	private DataOutputStream outputStream;

	private DataInputStream inputStream;

	private boolean disabled = true;

	int id = 0;

	MonitoringCommunicationOptions data = new MonitoringCommunicationOptions();

	public boolean isConnected(){
		return (socket.isConnected() && !socket.isClosed());
	}
	
	
	/**
	 * The Enum SendType.
	 */
	public enum SendType {
		REQ, addWatch, removeWatch, triggerEvent, startEventCnt, forceValue, breakPoint
	}

	public TCPCommunicationObject(MonitorInformation monitorInformation) {
		this.monitorInformation = monitorInformation;
	}

	public synchronized String sendRequest(SendType type, Device device, String request,
			String destination) {
		if (!disabled) { // only send if active

			if (socket == null || socket.isClosed() || !socket.isConnected()) {
				try {
					connect();
				} catch (IOException e) {
					String msg = "Can not connect to device "; //$NON-NLS-1$
					if(null != device){
						msg += device.getName();
					}
					msg += " at ";
					msg += monitorInformation.getInetAdress() + ":" + monitorInformation.port;
					msg += " (" + e.getMessage() + ")"; //$NON-NLS-1$							
					
					Activator.getDefault().logError(msg, e);
					return null;
				}
			}
			try {
				outputStream.writeByte(ASN1_TAG_IECSTRING);
				outputStream.writeShort(destination.length());
				outputStream.writeBytes(destination);

				outputStream.writeByte(ASN1_TAG_IECSTRING);
				outputStream.writeShort(request.length());
				outputStream.writeBytes(request);
				outputStream.flush();
				
				switch (type) {
				case REQ:
					return readResponse();
				case addWatch:
					return readResponse();
				case triggerEvent:
					// normally nothing to do - as no response expected
					break;
				case removeWatch:
					return readResponse();
				case startEventCnt:
					readResponse();
					return null;
				case breakPoint:
					readResponse();
					return null;
				case forceValue:
					// normally nothing to do - as no response expected
					break;
				}			
			} catch (IOException e) {
				Activator.getDefault().logError("Communication Problem: " + e.getMessage(), e);
				try {
					socket.close();
				} catch (IOException e1) {
					Activator.getDefault().logError("Communication Problem: " + e.getMessage(), e1);
				}
			}
		}
		return null;
	}

	private String readResponse() throws IOException {
		@SuppressWarnings("unused")
		byte b = inputStream.readByte();
		short size = inputStream.readShort();
		StringBuilder response = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			response.append((char) inputStream.readByte());
		}		
		return (0 == response.length()) ? null : response.toString();
	}

	/**
	 * Creates a new socket for the device.
	 * 
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void connect() throws IOException {
		if (socket == null || !socket.isBound() || socket.isClosed()
				|| !socket.isConnected() || socket.isInputShutdown()
				|| socket.isOutputShutdown()) {

			socket = new Socket();			
			SocketAddress sockaddr = new InetSocketAddress(monitorInformation.getInetAdress(), monitorInformation.port);			
			int timeout = PreferenceConstants.getTimeOutValue();			
			socket.connect(sockaddr, timeout);
			socket.setSoTimeout(timeout);

			outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		}
	}

	public void addWatch(MonitoringBaseElement element) {
			String request = MessageFormat.format(
				Messages.TCPCommunicationObject_Monitoring_ADD_Watch,
				new Object[] { this.id++,
						element.getQualifiedString(),
						"*" });
		String response = sendRequest(SendType.addWatch, null, request,
				element.getResourceString());
		//TODO show somehow the feeback if the response contained a reason that it didn't work
		element.setOffline(response == null);
	}

	public void removeWatch(MonitoringBaseElement element) {
		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_Monitoring_Delete_Watch,
				new Object[] { this.id++,
						element.getQualifiedString(),
						"*" });
		String response = sendRequest(SendType.removeWatch, null, request,
				element.getResourceString());
		element.setOffline(response == null);
	}

	/**
	 * { "Type" : "DEV", "requestWatches" : true, "requestForces" : true,
	 * "requestData" : true }
	 * 
	 * 
	 * @param system
	 * @param device
	 */
	public void sendReq(AutomationSystem system, Device device) {

		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_Monitoring_Read_Watches,
				new Object[] { this.id++ });

		String response = sendRequest(SendType.REQ, device, request, "");
		if (response != null) {
			evaluateResponse(system, device, response);
		}
	}

	/**
	 * { "Type" : "DEV", "requestBreakpoints" : true }
	 * 
	 * @param system
	 * @param device
	 */
	public void queryBreakpoints(AutomationSystem system, Device device) {
//		JSONObject request = new JSONObject();
//		try {
//			request.put("Type", "DEV"); //$NON-NLS-1$ //$NON-NLS-2$
//			request.put("requestBreakpoints", true); //$NON-NLS-1$
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		// String response = sendRequest(SendType.REQ, system, device, request);
		// if (response != null) {
		// evaluateBreakpointResponse(system, device, response);
		// }
	}

	/**
	 * { "breakpoints": [ { "Resource": "test_resource", "Name":
	 * "SPLIT_STRING_1", "Breakpoints": [ "REQ" ], "Enabled": [ true ], "Set": [
	 * true ] } ], "Type": "RSP" }
	 * 
	 * @param system
	 * @param device
	 * @param response
	 */
	private void evaluateBreakpointResponse(AutomationSystem system,
			Device device, String response) {
//		try {
//			JSONObject respObject = new JSONObject(response);
//			if (respObject.has("breakpoints")) { //$NON-NLS-1$
//				JSONArray allValues = respObject.getJSONArray("breakpoints"); //$NON-NLS-1$
//				String port = system.getName() + "." + device.getName() + "."; //$NON-NLS-1$ //$NON-NLS-2$
//
//				for (int i = 0; i < allValues.length(); i++) {
//					JSONObject o = allValues.getJSONObject(i);
//					JSONArray breakpointPorts = null;
//					JSONArray currentActiveBreakpoints = null;
//					if (o.has("Breakpoints") && o.has("Set")) { //$NON-NLS-1$ //$NON-NLS-2$
//						breakpointPorts = o.getJSONArray("Breakpoints"); //$NON-NLS-1$
//						currentActiveBreakpoints = o.getJSONArray("Set"); //$NON-NLS-1$
//
//					}
//					String name = o.getString("Name"); //$NON-NLS-1$
//					String resource = o.getString("Resource"); //$NON-NLS-1$
//
//					if (breakpointPorts != null) {
//						for (int j = 0; j < breakpointPorts.length(); j++) {
//							String portName = breakpointPorts.getString(j);
//
//							final MonitoringElement element = MonitoringManager
//									.getInstance().getMonitoringElement(
//											port + resource + "." + name + "." //$NON-NLS-1$ //$NON-NLS-2$
//													+ portName);
//
//							if (element != null) {
//								boolean active = currentActiveBreakpoints
//										.getBoolean(j);
//
//								if (active) {
//									if (element.getBreakpointCondition() == null
//											|| element.getBreakpointCondition() == "" //$NON-NLS-1$
//											|| element.getBreakpointCondition()
//													.equals(element
//															.getCurrentValue())) {
//										element.setBreakpointActive(true);
//									} else {
//										element.setBreakpointActive(false);
//										toggleBreakpoint(element,
//												BreakPoint.clear);
//									}
//								} else {
//									element.setBreakpointActive(false);
//								}
//
//							}
//						}
//					}
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

	}

	/**
	 * { "watches": [ { "Resource": "CALC", "Name": "FB_ADD_INT", "Ports": [
	 * "IN1", "IN2", "OUT" ] } ], "Type": "RSP" }
	 * 
	 * { "data": [ { "Resource": "CALC", "Name": "FB_ADD_INT", "Ports": [ "IN1",
	 * "IN2", "OUT" ], "values": [ "5", "3", "8" ] } ], "Type": "RSP" }
	 * 
	 * @param system
	 * @param device
	 * @param response
	 */
	private void evaluateResponse(AutomationSystem system, Device device,
			String response) {
		try {
			XMLResource resource = new XMLResourceImpl();
			InputSource source = new InputSource(new StringReader(response));
			resource.load(source, data.getLoadOptions());
			for (EObject object : resource.getContents()) {
				if (object instanceof Response) {
					Response resp = (Response) object;
					if (resp.getWatches() != null) {
						for (org.eclipse.fordiac.ide.monitoring.monCom.Resource res : resp
								.getWatches().getResources()) {

							for (FB fb : res.getFbs()) {
								for (Port p : fb.getPorts()) {
									final MonitoringBaseElement element = MonitoringManager.getInstance()
											.getMonitoringElementByPortString(system.getName(), device.getName() + "." + res.getName()
													+ "." + fb.getName() //$NON-NLS-1$
													+ "." + p.getName()); //$NON-NLS-1$
									if (element != null && element instanceof MonitoringElement) {
										MonitoringElement monitoringElement = (MonitoringElement)element;

										for (Data d : p.getDataValues()) {

											long timeAsLong = 0;
											try {
												Long.parseLong(d.getTime());
											} catch (NumberFormatException nfe) {
												timeAsLong = 0;
											}
											monitoringElement.setSec(timeAsLong / 1000);
											monitoringElement.setUsec(timeAsLong % 1000);
											monitoringElement.setCurrentValue(d.getValue());
											if (d.getForced() != null) {
												monitoringElement.setForce(d.getForced().equals("true"));
//												if (element.isForce()) {
//													element.setForceValue(d.getValue());
//												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (WrappedException ex) {
			Activator.getDefault().logError("",ex);
		} catch (IOException ex) {
			Activator.getDefault().logError("",ex);
		}

	}

	public void disable() {
		this.disabled = true;
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				Activator.getDefault().logError("Closing Socket Problem: " + e.getMessage(), e);
			}
		}
	}

	public void enable() {
		this.disabled = false;
		try {
			connect();
		} catch (IOException e) {
			Activator.getDefault().logError("Opening Socket Problem - TCPCommunicationObject:enable(): " //$NON-NLS-1$
							+ e.getMessage(), e);
		}
	}

	public void startEventCnt(MonitoringElement element) {

		// String response = sendRequest(SendType.startEventCnt, null, null,
		// watch);
		// element.setOffline(response == null);

	}

	public void triggerEvent(MonitoringElement element) {
		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_WriteParameter,
				new Object[] { this.id++,
						"$e", element.getQualifiedString()});
		 sendRequest(SendType.triggerEvent, null, request, element.getResourceString());
	}
	
	public void writeValue(MonitoringElement element, String value) {
		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_WriteParameter,
				new Object[] { this.id++, value, element.getQualifiedString()});
		sendRequest(SendType.triggerEvent, null, request, element.getResourceString());
	}

	public void forceValue(MonitoringElement element, String value) {
		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_Monitoring_Force_Value,
				new Object[] { this.id++,
						value, element.getQualifiedString(), "true"});

		sendRequest(SendType.forceValue, null, request, element.getResourceString());
	}

	public void clearForce(MonitoringElement element) {
		String request = MessageFormat.format(
				Messages.TCPCommunicationObject_Monitoring_Force_Value,
				new Object[] { this.id++,
						"*", element.getQualifiedString(), "false"});

		sendRequest(SendType.forceValue, null, request, element.getResourceString());
		// sendRequest(SendType.forceValue, null, null, forceValue);
	}

	/**
	 * { "Type" : "FB", "Name" : "<fbname>", "Resource" : "<resname>",
	 * "addBreakpoint" : [ "<eventName>" ] }
	 * 
	 * @param element
	 */
	public void toggleBreakpoint(MonitoringElement element, BreakPoint set) {
//		JSONObject setBreakpoint = new JSONObject();
//		try {
//			setBreakpoint.put("Type", "FB"); //$NON-NLS-1$ //$NON-NLS-2$
//			setBreakpoint.put("Name", element.getFBString()); //$NON-NLS-1$
//			setBreakpoint.put("Resource", element.getResourceString()); //$NON-NLS-1$
//			if (set.equals(BreakPoint.add)) {
//				setBreakpoint.append("addBreakpoint", element.getPortString()); //$NON-NLS-1$
//			} else if (set.equals(BreakPoint.remove)) {
//				setBreakpoint.append("removeBreakpoint", //$NON-NLS-1$
//						element.getPortString());
//			} else if (set.equals(BreakPoint.clear)) {
//				setBreakpoint
//						.append("clearBreakpoint", element.getPortString()); //$NON-NLS-1$
//			}
//		} catch (JSONException e1) {
//			Activator.getDefault().logError("Can not create valid Breakpoint JSON Object for " //$NON-NLS-1$
//							+ element.toString(), e1);
//			return;
//		}
		// sendRequest(SendType.breakPoint, null, null, setBreakpoint);

	}

}
