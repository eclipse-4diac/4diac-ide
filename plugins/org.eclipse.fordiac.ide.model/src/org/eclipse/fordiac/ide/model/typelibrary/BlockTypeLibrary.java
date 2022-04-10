/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alois Zoitl - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.Map;

public interface BlockTypeLibrary {

	Map<String, AdapterTypeEntry> getAdapterTypes();

	Map<String, DeviceTypeEntry> getDeviceTypes();

	Map<String, FBTypeEntry> getFbTypes();

	Map<String, ResourceTypeEntry> getResourceTypes();

	Map<String, SegmentTypeEntry> getSegmentTypes();

	Map<String, SubAppTypeEntry> getSubAppTypes();

	void addTypeEntry(TypeEntry entry);

	void removeTypeEntry(TypeEntry entry);

} // Palette
