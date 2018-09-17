/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 							 Johannes Kepler University	   	
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Monika Wenger
 *    			 - initial implementation
 *   Alois Zoitl - Harmonized deployment and monitoring 
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.iec61499.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.iec61499.ResponseMapping;
import org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.IFBTestConfiguration;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.IFBTestConfiguratonCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration.internal.Utils;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.InputSource;


public class FORTERemoteTester implements IFBTestConfiguratonCreator {
	private static final String LAST_IP = "lastIp"; //$NON-NLS-1$
	private static final String FORTE_REMOTE_TESTER_SETTINGS = "FORTE_REMOTE_TESTER_SETTINGS"; //$NON-NLS-1$
	private Button run;
	private FBType type;
	private boolean running;
	private Socket socket;
	private IDialogSettings forteRemoteTesterSettings;
	private Text ipText;
	private Text runTimePortText;
	private ResponseMapping data = new ResponseMapping();
	
	private enum SendType {
		REQ, addWatch, removeWatch, triggerEvent, startEventCnt, forceValue
	}

	public FORTERemoteTester() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		forteRemoteTesterSettings = settings.getSection(FORTE_REMOTE_TESTER_SETTINGS);
		if (forteRemoteTesterSettings == null) {
			forteRemoteTesterSettings = settings.addNewSection(FORTE_REMOTE_TESTER_SETTINGS);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		if(running){
			run.setText("Stop Testing FB");
			run.setImage(FordiacImage.ICON_Stop.getImage());
		}else{
			run.setText("Start Testing FB");
			run.setImage(FordiacImage.ICON_Start.getImage());
		}
	}

	@Override
	public IFBTestConfiguration createConfigurationPage(Composite parent) {
		final Composite main = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(6, false);
		main.setLayout(gl);

		Label ipLabel = new Label(main, SWT.NONE);
		ipLabel.setText("IP-Address:");
		ipText = new Text(main, SWT.BORDER);
		ipText.setText("127.0.0.1");

		Label runTimeLabel = new Label(main, SWT.NONE);
		runTimeLabel.setText("Runtime port:");
		runTimePortText = new Text(main, SWT.BORDER);
		runTimePortText.setText("61499");

		run = new Button(main, SWT.TOGGLE);
		run.setEnabled(true);
		setRunning(false);
		run.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int runtimePort = Integer.parseInt(runTimePortText.getText());
				if (run.getSelection()) {
					String ipAddress = ipText.getText();					
					forteRemoteTesterSettings.put(LAST_IP, ipAddress);
					String response = Utils.deployNetwork(type, ipText.getText(), runtimePort);
					if (response != null) {
						MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
						msb.setMessage("FB can not be tested because of the following error: \n" + response);
						msb.open();
						setRunning(false);
						run.setSelection(false);
						return;
					}
					int thread = 0;
					try {
						socket = new Socket(InetAddress.getByName(ipAddress), runtimePort);
						socket.setSoTimeout(500);
						addWatches("_" + type.getName()); //$NON-NLS-1$
						setRunning(true);
						Thread t = new Thread(new TriggerRequestRunnable(socket, thread));
						t.start();
						Thread.sleep(500);
					} catch (UnknownHostException e1) {
						Activator.getDefault().logError(e1.getMessage(), e1);
					} catch (IOException e1) {
						Activator.getDefault().logError(e1.getMessage(), e1);
					} catch (InterruptedException ex) {
						System.out.println("Thread Interrupted");
					}
				} else {
					setRunning(false);		
					String response = Utils.cleanNetwork(type, ipText.getText(), runtimePort, socket);
					if (response != null) {
						MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
						msb.setMessage("FB can not be cleaned because of the following error: \n" + response);
						msb.open();
					}
					try {
						socket.close();
						socket=null;
					} catch (IOException e1) {
						Activator.getDefault().logError(e1.getMessage(), e1);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		IFBTestConfiguration configuration = new IFBTestConfiguration() {
			@Override
			public Control getControl() {
				return main;
			}

			@Override
			public void newTestConf(List<TestElement> variables, List<String> values, List<ValuedVarDecl> resultVars, Map<String, Object> params) {
				
			}
		};
		loadLastIp();
		return configuration;

	}

	private void loadLastIp() {
		if (forteRemoteTesterSettings != null) {
			String lastIp = forteRemoteTesterSettings.get(LAST_IP);
			if (lastIp != null) {
				ipText.setText(lastIp);
				run.setEnabled(true);
			}
		}
	}

	private void addWatches(String fbName) {
		testElements = TestingManager.getInstance().getTestElements(type, this, this);

		for (TestElement element : testElements.values()){
			if (element.getFBString().equals(fbName)) {
				DataOutputStream outputStream = null;
				DataInputStream inputStream = null;
				try {
					outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
					inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
					if (element.getInterfaceElement() instanceof VarDeclaration) {
						addWatch(element, outputStream, inputStream);

					}
					if (element.getInterfaceElement() instanceof Event && !element.getInterfaceElement().isIsInput()) {
						addWatch(element, outputStream, inputStream);
					}

				} catch (IOException e1) {
					Activator.getDefault().logError(e1.getMessage(), e1);
				}
			} else {
				System.out.println("element: " + element.getFBString() + " skipped");
			}
		}
	}

	private void addWatch(TestElement element, DataOutputStream outputStream, DataInputStream inputStream) {
		if (outputStream != null && inputStream != null) {
			String request = MessageFormat.format(DeploymentExecutor.ADD_WATCH,
					new Object[] { 0, element.getFBString() + "." + element.getPortString(), "*" }); //$NON-NLS-1$ //$NON-NLS-2$
			sendRequest(SendType.addWatch, element.getResourceString(), request, outputStream, inputStream);
		}
	}

	class TriggerRequestRunnable implements Runnable {
		private final Socket socket;

		public TriggerRequestRunnable(Socket socket, int thread) {
			this.socket = socket;
		}

		int i = 0;

		@Override
		public void run() {
			DataOutputStream outputStream = null;
			DataInputStream inputStream = null;
			try {
				outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			} catch (IOException e1) {
				setRunning(false);
			}
			while (running) {
				i++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					setRunning(false);
				}
				String request = MessageFormat.format(DeploymentExecutor.READ_WATCHES, new Object[] { i });

				if (running) {
					sendRequest(SendType.REQ, "", request, outputStream, inputStream); //$NON-NLS-1$
				}
			}
		}
	}

	private synchronized void sendRequest(SendType type, String destination, String request, DataOutputStream outputStream,
			DataInputStream inputStream) {
		try {
			if (socket.isConnected()) {
				
				outputStream.writeByte(80);
				outputStream.writeShort(destination.length());
				outputStream.writeBytes(destination);
				
				outputStream.writeByte(80);
				outputStream.writeShort(request.length());
				outputStream.writeBytes(request);
				outputStream.flush();
				if (type.equals(SendType.REQ)) {
					String response = parseResponse(inputStream);
					if (!response.equals("")) { //$NON-NLS-1$
							XMLResource resource = new XMLResourceImpl();
							InputSource source = new InputSource(new StringReader(response));
							resource.load(source, data.getLoadOptions());
							for (EObject object : resource.getContents()) {
								if (object instanceof Response) {
									Response resp = (Response) object;
									if (resp.getWatches() != null) {
										for (org.eclipse.fordiac.ide.deployment.devResponse.Resource res : resp
												.getWatches().getResources()) {

											for (FB fb : res.getFbs()) {
												for (Port p : fb.getPorts()) {
													for (Data d : p.getDataValues()) {

														final TestElement element = testElements.get(
																		 res.getName()
																				+ "." + fb.getName() //$NON-NLS-1$
																				+ "." + p.getName()); //$NON-NLS-1$
														if (element != null) {
															element.updateValue(d.getValue(), 0);
														}

													}
												}
											}
										}
									}
								}
							}
					}
	
				} else if (type.equals(SendType.addWatch)) {
					// TODO evaluate responses
					inputStream.available();
					inputStream.read();

				} else if (type.equals(SendType.triggerEvent)) {
					// normally nothing to do - as no response expected
				} else if (type.equals(SendType.removeWatch)) {
					// normally nothing to do - as no response expected

				} else if (type.equals(SendType.startEventCnt)) {
					// normally nothing to do - as no response expected

				} else if (type.equals(SendType.forceValue)) {
					String forceResp = parseResponse(inputStream);
					System.out.println("force response: " + forceResp);
					// normally nothing to do - as no response expected
				}
			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private static String parseResponse(DataInputStream inputStream) throws IOException {
		StringBuilder response = new StringBuilder(); 
		inputStream.readByte(); // asn.1 tag
		short size = inputStream.readShort();

		for (int i = 0; i < size; i++) {
			response.append((char) inputStream.readByte());
		}
		return response.toString();
	}

	private Map<String, TestElement> testElements;

	/**
	 * "Type" : "FB", "Name" : "FB_ADD_INT", "Resource" : "RES1", "Port" :
	 * "IN1", "force" : true, "forceValue" : "10"
	 * 
	 * @param element
	 */
	public void setValue(TestElement element) {
		if (isRunning()) {
			DataOutputStream outputStream = null;
			DataInputStream inputStream = null;
			try {
				outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				
				String request = MessageFormat.format(DeploymentExecutor.FORCE_VALUE,
						new Object[] { 0, element.getValue(), element.getFBString() + "." + element.getPortString(), "true"}); //$NON-NLS-1$ //$NON-NLS-2$
				sendRequest(SendType.forceValue, element.getResourceString(), request, outputStream, inputStream);
			}  catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	@Override
	public void sendEvent(TestElement element) {
		if (isRunning()) {
			DataOutputStream outputStream = null;
			DataInputStream inputStream = null;
			try {
				outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				if (element != null) {
					String request = MessageFormat.format(DeploymentExecutor.WRITE_PARAMETER,
							new Object[] { 0, "$e", element.getFBString() + "." + element.getPortString()}); //$NON-NLS-1$ //$NON-NLS-2$
					sendRequest(SendType.triggerEvent, element.getResourceString(), request, outputStream, inputStream);
				}
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	@Override
	public void setType(FBType type) {
		this.type = type;
	}

	@Override
	public void setValue(TestElement element, String value) {
		setValue(element);
	}

	@Override
	public void setGroup(PaletteGroup group) {
		
	}

}
