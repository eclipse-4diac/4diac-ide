/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Contract {
	private final EList<Assumption> assumptions = new BasicEList<>();
	private final EList<Guarantee> guarantees = new BasicEList<>();
	private boolean isValid;
	private boolean isChanged;
	private FBNetworkElement owner;

	Contract() {
		isChanged = true;
	}

	public static Contract getContractFromComment(final String comment) {
		final Contract contract = new Contract();
		final String[] lines = comment.split("\n"); //$NON-NLS-1$
		for (final String line : lines) {
			if (line.startsWith("ASSUMPTION")) { //$NON-NLS-1$
				final Assumption toAdd = Assumption.createAssumption(line);
				toAdd.setOwner(contract);
				contract.add(toAdd);
			} else if (line.startsWith("GUARANTEE")) { //$NON-NLS-1$
				final Guarantee toAdd = Guarantee.createGuarantee(line);
				toAdd.setOwner(contract);
				contract.add(toAdd);
			}
		}
		return contract;
	}

	public void setOwner(final FBNetworkElement owner) {
		isChanged = true;
		this.owner = owner;
	}

	public FBNetworkElement getOwner() {
		return owner;
	}

	public EList<Assumption> getAssumptions() {
		return assumptions;
	}

	public EList<Guarantee> getGuarantees() {
		return guarantees;
	}

	void add(final Assumption assumption) {
		if (assumption.getOwner() == this) {
			isChanged = true;
			assumptions.add(assumption);
		}
	}

	void add(final Guarantee guarantee) {
		isChanged = true;
		guarantees.add(guarantee);
	}

	void removeAssumption(final int index) {
		isChanged = true;
		assumptions.remove(index);
	}

	void removeGuarantee(final int index) {
		isChanged = true;
		guarantees.remove(index);
	}

	public boolean isValid() {
		if (isChanged) {
			checkValidity();
			isChanged = false;
		}
		return isValid;
	}

	private void checkValidity() {
		if (owner instanceof final SubApp subapp && subapp.getName().startsWith("_CONTRACT")) { //$NON-NLS-1$
			if (assumptions.isEmpty() && guarantees.isEmpty()) {
				isValid = false;
				return;
			}

			for (final Assumption assumption : assumptions) {
				if (!assumption.isValid()) {
					isValid = false;
					return;
				}
			}
		}
		isValid = true;

	}

}
