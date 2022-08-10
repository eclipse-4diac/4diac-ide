/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 							 				Johannes Kepler University
 * 						2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl, Monika Wenger
 *    			 - initial implementation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Christoph Binder - Using Deployment Plugin and changes necessary for reset and reload buttons
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.IFBTestConfiguration;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.IFBTestConfiguratonCreator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.TestingManager;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration.internal.TestDeploymentExecutor;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model.TestElement;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.providers.DialogSettingsProvider;
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

public class FORTERemoteTester implements IFBTestConfiguratonCreator,IDeploymentListener {
	private static final int TESTER_CYCLE_TIME_IN_MS = 500;
	private static final String LAST_IP = "lastIp"; //$NON-NLS-1$
	private static final String FORTE_REMOTE_TESTER_SETTINGS = "FORTE_REMOTE_TESTER_SETTINGS"; //$NON-NLS-1$
	private static final String UNSUPPORTED_TYPE = "UNSUPPORTED_TYPE"; //$NON-NLS-1$
	private static final String NO_SUCH_OBJECT = "NO_SUCH_OBJECT"; //$NON-NLS-1$
	private Button run;
	private FBType type;
	private boolean running;
	private IDialogSettings forteRemoteTesterSettings;
	private Text ipText;
	private Text runTimePortText;
	private TestDeploymentExecutor testDeploymentExecutor;
	private Map<String, TestElement> testElements;

	public FORTERemoteTester() {
		final IDialogSettings settings = DialogSettingsProvider.getDialogSettings(getClass());
		forteRemoteTesterSettings = settings.getSection(FORTE_REMOTE_TESTER_SETTINGS);
		if (forteRemoteTesterSettings == null) {
			forteRemoteTesterSettings = settings.addNewSection(FORTE_REMOTE_TESTER_SETTINGS);
		}
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	public void setRunning(final boolean running) {
		this.running = running;
		if (running) {
			run.setText(Messages.FORTERemoteTester_StopTestingFB);
			run.setImage(FordiacImage.ICON_STOP.getImage());
		} else {
			run.setText(Messages.FORTERemoteTester_StartTestingFB);
			run.setImage(FordiacImage.ICON_START.getImage());
		}
	}

	@Override
	public IFBTestConfiguration createConfigurationPage(final Composite parent) {
		final Composite main = new Composite(parent, SWT.NONE);
		final GridLayout gl = new GridLayout(6, false);
		main.setLayout(gl);

		final Label ipLabel = new Label(main, SWT.NONE);
		ipLabel.setText(Messages.FORTERemoteTester_IPAddress);
		ipText = new Text(main, SWT.BORDER);
		ipText.setText("127.0.0.1"); //$NON-NLS-1$

		final Label runTimeLabel = new Label(main, SWT.NONE);
		runTimeLabel.setText(Messages.FORTERemoteTester_RuntimePort);
		runTimePortText = new Text(main, SWT.BORDER);
		runTimePortText.setText("61499"); //$NON-NLS-1$

		run = new Button(main, SWT.TOGGLE);
		run.setEnabled(true);
		setRunning(false);
		run.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (run.getSelection()) {
					final int runtimePort = Integer.parseInt(runTimePortText.getText());
					final String ipAddress = ipText.getText();
					forteRemoteTesterSettings.put(LAST_IP, ipAddress);
					testDeploymentExecutor = new TestDeploymentExecutor(type,ipAddress,runtimePort,getDeploymentListener(),getTestElements());
					setRunning(true);
					final String response = testDeploymentExecutor.deployNetwork();
					if (response != null) {
						if(isRunning()) {
							final MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
							msb.setMessage(MessageFormat.format(
									Messages.FORTERemoteTester_FBCanNotBeTestedBecauseOfTheFollowingError, response));
							msb.open();
							setRunning(false);
						}
						run.setSelection(false);
						return;
					}
					testDeploymentExecutor.addWatches();
					final Thread t = new Thread(new TriggerRequestRunnable());
					t.start();
					try {
						Thread.sleep(TESTER_CYCLE_TIME_IN_MS);
					} catch (final InterruptedException ex) {
						Thread.currentThread().interrupt();  // mark interruption
						FordiacLogHelper.logError(Messages.FORTERemoteTester_ThreadInterrupted, ex);
					}
				} else {
					setRunning(false);
					final String response = testDeploymentExecutor.cleanNetwork();
					if (response != null) {
						final MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
						msb.setMessage(MessageFormat.format(
								Messages.FORTERemoteTester_FBCanNotBeCleanedBecauseOfTheFollowingError, response));
						msb.open();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});

		final IFBTestConfiguration configuration = new IFBTestConfiguration() {
			@Override
			public Control getControl() {
				return main;
			}

			@Override
			public void newTestConf(final List<TestElement> variables, final List<String> values,
					final List<ValuedVarDecl> resultVars, final Map<String, Object> params) {
				// Not needed right now
			}
		};
		loadLastIp();
		return configuration;

	}

	@Override
	public String close() {
		if(isRunning()){
			running=false; //setrunning() cannot be used here due to error when called by FBTestereditor
			return testDeploymentExecutor.cleanNetwork();
		}
		return null;
	}

	@Override
	public String clean() {
		if(isRunning()){
			setRunning(false);
			run.setSelection(false);
			return testDeploymentExecutor.cleanNetwork();
		}
		return null;
	}

	@Override
	public String resetFB() {
		if(isRunning()) {
			try {
				testDeploymentExecutor.resetFB();
			} catch (final IOException e) {
				return e.getMessage();
			}
		}
		return null;
	}

	@Override
	public String startFB() {
		if(isRunning()) {
			try {
				testDeploymentExecutor.startFB();
			} catch (final DeploymentException e) {
				return e.getMessage();
			}
		}
		return null;
	}

	private void loadLastIp() {
		if (forteRemoteTesterSettings != null) {
			final String lastIp = forteRemoteTesterSettings.get(LAST_IP);
			if (lastIp != null) {
				ipText.setText(lastIp);
				run.setEnabled(true);
			}
		}
	}

	private IDeploymentListener getDeploymentListener() {
		return this;
	}

	private Map<String, TestElement> getTestElements() {
		testElements = TestingManager.getInstance().getTestElements(type, this, this);
		return testElements;
	}

	class TriggerRequestRunnable implements Runnable {

		@Override
		public void run() {
			while (running) {
				try {
					Thread.sleep(TESTER_CYCLE_TIME_IN_MS);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();  // mark interruption
					setRunning(false);
				}
				if (running) {
					try {
						evaluateResponse(testDeploymentExecutor.readWatches());
					} catch (final DeploymentException e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
				}
			}
		}

		private void evaluateResponse(final Response resp) {
			if (resp.getWatches() != null) {
				for (final Resource res : resp.getWatches().getResources()) {
					for (final FB fb : res.getFbs()) {
						evaluateFbResponse(res, fb);
					}
				}
			}
		}

		private void evaluateFbResponse(final Resource res, final FB fb) {
			for (final Port p : fb.getPorts()) {
				for (final Data d : p.getDataValues()) {
					final TestElement element = testElements.get(res.getName() + "." + fb.getName() //$NON-NLS-1$
					+ "." + p.getName()); //$NON-NLS-1$
					if (element != null) {
						element.updateValue(d.getValue(), 0);
					}
				}
			}
		}

	}


	public void setValue(final TestElement element) {
		if (isRunning()) {
			try {
				final String value = element.getValue();
				testDeploymentExecutor.setValue(element,value);
			} catch (final DeploymentException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	/** "Type" : "FB", "Name" : "FB_ADD_INT", "Resource" : "RES1", "Port" : "IN1", "force" : true, "forceValue" : "10"
	 *
	 * @param element */
	@Override
	public void sendEvent(final TestElement element) {
		if (isRunning() && (element != null)) {
			try {
				testDeploymentExecutor.sendEvent(element);
			} catch (final DeploymentException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
	}

	@Override
	public void setType(final FBType type) {
		this.type = type;
	}

	@Override
	public void setValue(final TestElement element, final String value) {
		setValue(element);
	}

	@Override
	public void connectionOpened(final Device dev) {
		//currently not used method
	}

	@Override
	public void postCommandSent(final String info, final String destination, final String command) {
		//currently not used method
	}

	@Override
	public  void postResponseReceived(final String response, final String source) {
		if(response.contains(UNSUPPORTED_TYPE)){
			final MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
			msb.setMessage(MessageFormat.format(
					Messages.FORTERemoteTester_FBCanNotBeTestedBecauseItsTypeIsNotKnown, response));
			msb.open();
			setRunning(false);
			run.setSelection(false);
			testDeploymentExecutor.cleanNetwork();
			return;
		}
		if(response.contains(NO_SUCH_OBJECT)) {
			final MessageBox msb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR);
			msb.setMessage(MessageFormat.format(
					Messages.FORTERemoteTester_InterfaceNotTheSameAsTheOneKnownByTheDevice, response));
			msb.open();
			setRunning(false);
			run.setSelection(false);
			testDeploymentExecutor.cleanNetwork();
		}

	}

	@Override
	public void connectionClosed(final Device dev) {
		//currently not used method
	}

}
