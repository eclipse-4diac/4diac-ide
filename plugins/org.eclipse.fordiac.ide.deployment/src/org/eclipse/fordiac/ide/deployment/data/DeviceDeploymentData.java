/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

public class DeviceDeploymentData {

	private final Device dev;

	private final List<ResourceDeploymentData> resData = new ArrayList<>();

	private List<VarDeclaration> selectedDevParams = Collections.emptyList();

	private final SequencedSet<FBTypeEntry> fbTypes = new LinkedHashSet<>();

	private final SequencedSet<DataTypeEntry> dataTypes = new LinkedHashSet<>();

	public DeviceDeploymentData(final Device device) {
		dev = device;
	}

	public Device getDevice() {
		return dev;
	}

	public void addResourceData(final ResourceDeploymentData data) {
		resData.add(data);
		fbTypes.addAll(data.getFbTypes());
		dataTypes.addAll(data.getDataTypes());
	}

	public List<ResourceDeploymentData> getResData() {
		return resData;
	}

	public void setSeltectedDevParams(final List<VarDeclaration> selParams) {
		selectedDevParams = Collections.unmodifiableList(new ArrayList<>(selParams));
	}

	/**
	 * Get the unmodifyable list of the selecte params to be downloaded to this
	 * device
	 */
	public List<VarDeclaration> getSelectedDevParams() {
		return selectedDevParams;
	}

	public SequencedSet<FBTypeEntry> getFbTypes() {
		return fbTypes;
	}

	public SequencedSet<DataTypeEntry> getDataTypes() {
		return dataTypes;
	}
}
