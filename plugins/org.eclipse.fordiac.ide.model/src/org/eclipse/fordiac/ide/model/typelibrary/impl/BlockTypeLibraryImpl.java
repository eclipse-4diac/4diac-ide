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
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.BlockTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class BlockTypeLibraryImpl implements BlockTypeLibrary {

	private final Map<String, AdapterTypeEntry> adapterTypes = new HashMap<>();

	private final Map<String, DeviceTypeEntry> deviceTypes = new HashMap<>();

	private final Map<String, FBTypeEntry> fbTypes = new HashMap<>();

	private final Map<String, ResourceTypeEntry> resourceTypes = new HashMap<>();

	private final Map<String, SegmentTypeEntry> segmentTypes = new HashMap<>();

	private final Map<String, SubAppTypeEntry> subAppTypes = new HashMap<>();

	@Override
	public Map<String, AdapterTypeEntry> getAdapterTypes() {
		return adapterTypes;
	}

	@Override
	public Map<String, DeviceTypeEntry> getDeviceTypes() {
		return deviceTypes;
	}

	@Override
	public Map<String, FBTypeEntry> getFbTypes() {
		return fbTypes;
	}

	@Override
	public Map<String, ResourceTypeEntry> getResourceTypes() {
		return resourceTypes;
	}

	@Override
	public Map<String, SegmentTypeEntry> getSegmentTypes() {
		return segmentTypes;
	}

	@Override
	public Map<String, SubAppTypeEntry> getSubAppTypes() {
		return subAppTypes;
	}

	@Override
	public void addTypeEntry(final TypeEntry entry) {
		if (entry instanceof AdapterTypeEntry) {
			getAdapterTypes().put(entry.getTypeName(), (AdapterTypeEntry) entry);
		} else if (entry instanceof DeviceTypeEntry) {
			getDeviceTypes().put(entry.getTypeName(), (DeviceTypeEntry) entry);
		} else if (entry instanceof FBTypeEntry) {
			getFbTypes().put(entry.getTypeName(), (FBTypeEntry) entry);
		} else if (entry instanceof ResourceTypeEntry) {
			getResourceTypes().put(entry.getTypeName(), (ResourceTypeEntry) entry);
		} else if (entry instanceof SegmentTypeEntry) {
			getSegmentTypes().put(entry.getTypeName(), (SegmentTypeEntry) entry);
		} else if (entry instanceof SubAppTypeEntry) {
			getSubAppTypes().put(entry.getTypeName(), (SubAppTypeEntry) entry);
		} else {
			FordiacLogHelper.logError("Unknown type entry to be added to library: " + entry.getClass().getName()); //$NON-NLS-1$
		}
	}

	@Override
	public void removeTypeEntry(final TypeEntry entry) {
		if (entry instanceof AdapterTypeEntry) {
			getAdapterTypes().remove(entry.getTypeName());
		} else if (entry instanceof DeviceTypeEntry) {
			getDeviceTypes().remove(entry.getTypeName());
		} else if (entry instanceof FBTypeEntry) {
			getFbTypes().remove(entry.getTypeName());
		} else if (entry instanceof ResourceTypeEntry) {
			getResourceTypes().remove(entry.getTypeName());
		} else if (entry instanceof SegmentTypeEntry) {
			getSegmentTypes().remove(entry.getTypeName());
		} else if (entry instanceof SubAppTypeEntry) {
			getSubAppTypes().remove(entry.getTypeName());
		} else {
			FordiacLogHelper.logError("Unknown type entry to be removed from library: " + entry.getClass().getName()); //$NON-NLS-1$
		}
	}

}
