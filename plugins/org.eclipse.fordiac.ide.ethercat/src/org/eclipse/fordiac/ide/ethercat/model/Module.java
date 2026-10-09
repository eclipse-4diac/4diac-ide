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

public class Module {
	public String moduleType;
	public ArrayList<Pdo> rxPdoes;
	public ArrayList<Pdo> txPdoes;
	public String comment;
	public int inputNums = 0;
	public int outputNums = 0;
	public String moduleIdent;

	public Module(final String moduleType) {
		this.moduleType = moduleType;
		rxPdoes = new ArrayList<>();
		txPdoes = new ArrayList<>();
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

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setModuleIdent(final String moduleIdent) {
		this.moduleIdent = moduleIdent;
	}

	public String getFbType() {
		return "ECModule_" + inputNums + "_" + outputNums; //$NON-NLS-1$ //$NON-NLS-2$
	}
}
