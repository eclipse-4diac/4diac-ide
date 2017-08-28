package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration;

import java.util.ArrayList;
import java.util.Random;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Activator;
import org.eclipse.ui.externaltools.internal.model.IExternalToolConstants;

public class TestingUtils {

	private static ArrayList<Integer> usedPorts = new ArrayList<Integer>();

	public static int getUniquePort() {
		Random rand = new Random();
		int nextRand = rand.nextInt(64512) + 1025; // 64512 = (2^16 Ports - 1024)
		// because the
		// first
		// 1024 ports are not allowed to be used + 1025 to be sure that the port >
		// 1024
		while (usedPorts.contains(nextRand)) {
			nextRand = rand.nextInt(64512) + 1025;
		}
		usedPorts.add(nextRand);
		return nextRand;
	}

	public static void removePort(int port) {
		usedPorts.remove(Integer.valueOf(port));
	}

	public static ILaunch startRuntime(String runtime, int port) {
		/** Launch configuration. */
		ILaunchConfiguration config;
		/** The launch configuration's working copy. */
		ILaunchConfigurationWorkingCopy wc;
		ILaunch launcht = null;

		// Get the default launch manager
		DebugPlugin debug = DebugPlugin.getDefault();
		ILaunchManager lm = debug.getLaunchManager();
		// Set launch configuration type to 'Program'
		ILaunchConfigurationType configType = lm
				.getLaunchConfigurationType("org.eclipse.ui.externaltools.ProgramLaunchConfigurationType");
		try {
			wc = configType.newInstance(null, "FORTE");
			// Set necessary attributes for the launch configuration
			wc
					.setAttribute("org.eclipse.debug.core.appendEnvironmentVariables",
							true);
			wc.setAttribute("org.eclipse.ui.externaltools.ATTR_LOCATION", runtime);
			wc.setAttribute(IExternalToolConstants.ATTR_TOOL_ARGUMENTS,
					"-c localhost:" + port);

			// wc.setAttribute("org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY",
			// runtime);
			config = wc.doSave();
			launcht = config.launch(ILaunchManager.RUN_MODE, null);
			// launcht = wc.launch(ILaunchManager.RUN_MODE, null);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				Activator.getDefault().logError(e1.getMessage(), e1);
			}
			// config.launch(ILaunchManager.RUN_MODE, null);
		} catch (CoreException e1) {
			Activator.getDefault().logError(e1.getMessage(), e1);
		}
		return launcht;
	}

	public static void terminateRuntime(ILaunch launch) {
		if (launch != null && launch.canTerminate()) {
			try {
				launch.terminate();
			} catch (DebugException e1) {
				Activator.getDefault().logError(e1.getMessage(), e1);
			}
		}
	}

}
