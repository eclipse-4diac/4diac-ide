/*******************************************************************************
 * Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zijun Tang - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ethercat.model;

import java.util.ArrayList;

import org.eclipse.fordiac.ide.ethercat.model.Pdo.PdoType;

public class Device {
	public String deviceType;
	public String oriDeviceType;
	public ArrayList<Pdo> rxPdoes;
	public ArrayList<Pdo> txPdoes;
	public ArrayList<Module> modules;
	public String comment;
	public int inputNums = 0;
	public int outputNums = 0;
	public String vendorId;
	public String productCode;

	public enum DeviceCategory {
		GEN_Device,
		GEN_Coupler
	}

	public DeviceCategory deviceCategory;

	public Device(final String deviceType) {
		this.deviceType = deviceType;
		rxPdoes = new ArrayList<>();
		txPdoes = new ArrayList<>();
		modules = new ArrayList<>();
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void addPdo(final Pdo pdo) {
		if(pdo.pdoType == PdoType.RxPdo) {
			rxPdoes.add(pdo);
			outputNums += pdo.pdoEntries.size();
		} else if(pdo.pdoType == PdoType.TxPdo) {
			txPdoes.add(pdo);
			inputNums += pdo.pdoEntries.size();
		}
	}

	public void setDeviceCategory(final DeviceCategory deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public void setOriDeviceType(final String oriDeviceType) {
		this.oriDeviceType = oriDeviceType;
	}

	public String getFBType() {
		return this.deviceCategory == DeviceCategory.GEN_Coupler
				? "ECCoupler_" + inputNums + "_" + outputNums //$NON-NLS-1$ //$NON-NLS-2$
				: "ECDevice_" + inputNums + "_" + outputNums; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
