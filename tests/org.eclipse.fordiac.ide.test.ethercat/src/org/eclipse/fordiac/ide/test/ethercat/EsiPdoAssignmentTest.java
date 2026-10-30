/*******************************************************************************
 * Copyright (c) 2026 MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tim Grocki - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.ethercat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.ethercat.EsiFileParser;
import org.eclipse.fordiac.ide.ethercat.EsiFileParser.EsiParseException;
import org.eclipse.fordiac.ide.ethercat.model.Device;
import org.eclipse.fordiac.ide.ethercat.model.Module;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

class EsiPdoAssignmentTest {

	@Test
	void keepsTxPdosAssignedToSm0() throws Exception {
		final Device device = parseSingleDevice("data/esi/device-sm0-inputs.xml"); //$NON-NLS-1$

		assertEquals(2, device.txPdoes.size());
		assertEquals(0, device.rxPdoes.size());
		assertEquals(2, device.inputNums);
		assertEquals(0, device.outputNums);
	}

	@Test
	void keepsRxPdosAssignedToSm0() throws Exception {
		final Device device = parseSingleDevice("data/esi/device-sm0-outputs.xml"); //$NON-NLS-1$

		assertEquals(0, device.txPdoes.size());
		assertEquals(2, device.rxPdoes.size());
		assertEquals(0, device.inputNums);
		assertEquals(2, device.outputNums);
	}

	@Test
	void keepsMailboxDevicePdosOnSm2AndSm3() throws Exception {
		final Device device = parseSingleDevice("data/esi/device-mailbox-sm23.xml"); //$NON-NLS-1$

		assertEquals(1, device.txPdoes.size());
		assertEquals(1, device.rxPdoes.size());
		assertEquals(1, device.inputNums);
		assertEquals(1, device.outputNums);
	}

	@Test
	void keepsModulePdosAssignedToSm0() throws Exception {
		final List<Module> modules = parseModules("data/esi/module-sm0.xml"); //$NON-NLS-1$

		assertEquals(1, modules.size());
		assertEquals(1, modules.getFirst().txPdoes.size());
		assertEquals(0, modules.getFirst().rxPdoes.size());
	}

	@Test
	void skipsPdosWithoutSmAttribute() throws Exception {
		final Device device = parseSingleDevice("data/esi/device-sm0-inputs.xml"); //$NON-NLS-1$

		assertTrue(device.txPdoes.stream().noneMatch(pdo -> "Alt Channel".equals(pdo.name))); //$NON-NLS-1$
		assertEquals(2, device.txPdoes.size());
	}

	private static Device parseSingleDevice(final String bundlePath) throws EsiParseException, IOException {
		final List<Device> devices = parseDevices(bundlePath);
		assertEquals(1, devices.size());
		return devices.getFirst();
	}

	private static List<Device> parseDevices(final String bundlePath) throws EsiParseException, IOException {
		try (InputStream in = openFixture(bundlePath)) {
			return new EsiFileParser(in).parseDevices();
		}
	}

	private static List<Module> parseModules(final String bundlePath) throws EsiParseException, IOException {
		try (InputStream in = openFixture(bundlePath)) {
			return new EsiFileParser(in).parseModulesCatalog();
		}
	}

	private static InputStream openFixture(final String bundlePath) throws IOException {
		final Bundle bundle = FrameworkUtil.getBundle(EsiPdoAssignmentTest.class);
		final URL url = FileLocator.find(bundle, new Path(bundlePath), null);
		if (url == null) {
			throw new IOException("Missing test fixture: " + bundlePath); //$NON-NLS-1$
		}
		return url.openStream();
	}
}
