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
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Assumption {
	private static final int POSITION_NO = 4;
	private String event;
	private int min;
	private int max;
	private Contract owner;

	Assumption() {

	}

	static Assumption createAssumption(final String line) {
		if (line.contains("offset")) { //$NON-NLS-1$
			return AssumptionWithOffset.createAssumptionWithOffset(line);
		}
		final Assumption assumption = new Assumption();
		String[] parts = line.split(" "); //$NON-NLS-1$
		assumption.setEvent(parts[1]);
		if (parts[POSITION_NO].contains(",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			assumption.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			assumption.setMax(Integer.parseInt(parts[0]));
			return assumption;
		}
		assumption.setMax(-1);
		assumption.setMin(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		return assumption;
	}

	String getEvent() {
		return event;
	}

	public int getMin() { // public for testing
		return min;
	}

	int getMax() {
		return max;
	}

	Contract getOwner() {
		return owner;
	}

	void setEvent(final String event) {
		this.event = event;
	}

	void setMin(final int min) {
		this.min = min;
	}

	void setMax(final int max) {
		this.max = max;
	}

	void setOwner(final Contract owner) {
		this.owner = owner;
	}

	boolean isValid() {
		if (getOwner().getOwner() instanceof final SubApp subapp) {
			final EList<SubApp> subapps = new BasicEList<>();
			final EList<FB> fBs = new BasicEList<>();
			final EList<Event> inputEvents = subapp.getInterface().getEventInputs();
			final EList<FBNetworkElement> fBNetworkElements = subapp.getFbNetwork().getNetworkElements();
			for (final FBNetworkElement element : fBNetworkElements) {
				if ((element instanceof final SubApp containedSubapp
						&& containedSubapp.getName().startsWith("_CONTRACT"))) { //$NON-NLS-1$
					subapps.add(containedSubapp);
				} else if (element instanceof final FB fb) {
					fBs.add(fb);
				}
			}
			if (!subapps.isEmpty()) {
				for (final Event inputE : inputEvents) {
					if (inputE.getName().equals(event)) {
						return true;
					}
				}
			}
			if (fBs.size() == 1) {
				final EList<Event> inputFBEvents = fBs.get(0).getInterface().getEventInputs();
				for (final Event inputFBEs : inputFBEvents) {
					if (inputFBEs.getName().equals(event)) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
