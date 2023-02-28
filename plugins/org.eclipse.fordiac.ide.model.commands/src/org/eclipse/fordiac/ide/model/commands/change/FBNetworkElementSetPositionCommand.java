/*******************************************************************************
 * Copyright (c) 2021 Primetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Position;

public class FBNetworkElementSetPositionCommand extends SetPositionCommand implements ConnectionLayoutTagger {

	private static final Consumer<IInterfaceElement> INPUT_CONSUMER = ie -> ie.getInputConnections()
			.forEach(con -> con.getRoutingData().setNeedsValidation(true));
	private static final Consumer<IInterfaceElement> OUTPUT_CONSUMER = ie -> ie.getOutputConnections()
			.forEach(con -> con.getRoutingData().setNeedsValidation(true));

	public FBNetworkElementSetPositionCommand(final FBNetworkElement fbe, final int dx, final int dy) {
		super(fbe, dx, dy);
	}

	@Override
	public FBNetworkElement getPositionableElement() {
		return (FBNetworkElement) super.getPositionableElement();
	}

	@Override
	protected void setPosition(final Position pos) {
		super.setPosition(pos);
		invalidateInputConnections(getPositionableElement().getInterface());
		invalidateOutputConnections(getPositionableElement().getInterface());
	}

	private static void invalidateInputConnections(final InterfaceList il) {
		il.getEventInputs().forEach(INPUT_CONSUMER);
		il.getInputVars().forEach(INPUT_CONSUMER);
		il.getSockets().forEach(INPUT_CONSUMER);
	}

	private static void invalidateOutputConnections(final InterfaceList il) {
		il.getEventOutputs().forEach(OUTPUT_CONSUMER);
		il.getOutputVars().forEach(OUTPUT_CONSUMER);
		il.getPlugs().forEach(OUTPUT_CONSUMER);
	}

}
