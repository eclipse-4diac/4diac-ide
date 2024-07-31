/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.monitorgen;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal.AbstractCompositeFBGenerator;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class CompositeMonitorFBGenerator extends AbstractCompositeFBGenerator {
	private FB addedSourceType;
	private final List<FBType> monitorFBs;
	private final List<FB> addedMonitorFBs = new ArrayList<>();

	public CompositeMonitorFBGenerator(final FBType type, final List<FBType> monitorFBs) {
		super(type);
		this.monitorFBs = monitorFBs;
	}

	public CompositeFBType generateCompositeFB() {
		createCompositeFB();
		return compositeFB;
	}

	// replicate withs from monitored fb
	@Override
	protected void createWiths() {
		for (int i = 0; i < sourceType.getInterfaceList().getEventInputs().size(); i++) {
			for (final With with : sourceType.getInterfaceList().getEventInputs().get(i).getWith()) {
				compositeFB.getInterfaceList().getEventInputs().get(i).getWith()
						.add(createWith(compositeFB.getInterfaceList().getVariable(with.getVariables().getName())));
			}
		}
		for (int i = 0; i < sourceType.getInterfaceList().getEventOutputs().size(); i++) {
			for (final With with : sourceType.getInterfaceList().getEventOutputs().get(i).getWith()) {
				compositeFB.getInterfaceList().getEventOutputs().get(i).getWith()
						.add(createWith(compositeFB.getInterfaceList().getVariable(with.getVariables().getName())));
			}
		}
	}

	@Override
	protected void createConnections() {
		// Connection SourceType
		for (int i = 0; i < sourceType.getInterfaceList().getEventInputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConn(compositeFB.getInterfaceList().getEventInputs().get(i),
							addedSourceType.getInterface().getEventInputs().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getEventOutputs().size(); i++) {
			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConn(addedSourceType.getInterface().getEventOutputs().get(i),
							compositeFB.getInterfaceList().getEventOutputs().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getInputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections()
					.add(createDataConn(compositeFB.getInterfaceList().getInputVars().get(i),
							addedSourceType.getInterface().getInputVars().get(i)));
		}
		for (int i = 0; i < sourceType.getInterfaceList().getOutputVars().size(); i++) {
			compositeFB.getFBNetwork().getDataConnections()
					.add(createDataConn(addedSourceType.getInterface().getOutputVars().get(i),
							compositeFB.getInterfaceList().getOutputVars().get(i)));
		}

		// Connection monitorFBs
		for (final FB fb : addedMonitorFBs) {
			for (int i = 0; i < compositeFB.getInterfaceList().getEventInputs().size(); i++) {
				compositeFB.getFBNetwork().getEventConnections()
						.add(createEventConn(compositeFB.getInterfaceList().getEventInputs().get(i),
								fb.getInterface().getEventInputs().get(i)));
			}
			for (int i = 0; i < sourceType.getInterfaceList().getEventOutputs().size(); i++) {
				compositeFB.getFBNetwork().getEventConnections()
						.add(createEventConn(addedSourceType.getInterface().getEventOutputs().get(i), fb.getInterface()
								.getEventInputs().get(i + sourceType.getInterfaceList().getEventInputs().size())));
			}
			for (int i = 0; i < compositeFB.getInterfaceList().getInputVars().size(); i++) {
				compositeFB.getFBNetwork().getDataConnections().add(createDataConn(
						compositeFB.getInterfaceList().getInputVars().get(i), fb.getInterface().getInputVars().get(i)));
			}
			for (int i = 0; i < sourceType.getInterfaceList().getOutputVars().size(); i++) {
				compositeFB.getFBNetwork().getDataConnections()
						.add(createDataConn(addedSourceType.getInterface().getOutputVars().get(i), fb.getInterface()
								.getInputVars().get(i + compositeFB.getInterfaceList().getInputVars().size())));
			}
		}
		// create connection from monitorFBs to the detected output events
		int fbCnt = 0;
		for (int i = sourceType.getInterfaceList().getEventOutputs().size(); i < compositeFB.getInterfaceList()
				.getEventOutputs().size(); i++) {

			compositeFB.getFBNetwork().getEventConnections()
					.add(createEventConn(addedMonitorFBs.get(fbCnt).getInterface().getEventOutputs().get(0),
							compositeFB.getInterfaceList().getEventOutputs().get(i)));
			fbCnt++;
		}
	}

	@Override
	protected void addFBsToNetwork() {
		final FBNetwork net = LibraryElementFactory.eINSTANCE.createFBNetwork();
		compositeFB.setFBNetwork(net);
		addedSourceType = addFBToNetwork(net, sourceType, 400, 0);
		for (int i = 0; i < monitorFBs.size(); i++) {
			addedMonitorFBs.add(addFBToNetwork(net, monitorFBs.get(i), 400, 300 * (i + 1)));
		}
	}

	@Override
	protected String getTypeName() {
		return sourceType.getName() + "_MONITOR_COMPOSITE"; //$NON-NLS-1$
	}

	// replicate data interface of monitored fb
	@Override
	protected void createData() {
		compositeFB.getInterfaceList().getInputVars().addAll(sourceType.getInterfaceList().getInputVars().stream()
				.map(n -> createInputVarDecl(n.getType(), n.getName())).toList());
		compositeFB.getInterfaceList().getOutputVars().addAll(sourceType.getInterfaceList().getOutputVars().stream()
				.map(n -> (createOutputVarDecl(n.getType(), n.getName()))).toList());

	}

	// replicate event interface of monitored fb
	// also generate for each contained monitor fb a detected output
	@Override
	protected void createEvents() {
		compositeFB.getInterfaceList().getEventInputs().addAll(sourceType.getInterfaceList().getEventInputs().stream()
				.map(n -> (createEvent(n.getName(), true))).toList());
		compositeFB.getInterfaceList().getEventOutputs().addAll(sourceType.getInterfaceList().getEventOutputs().stream()
				.map(n -> (createEvent(n.getName(), false))).toList());
		compositeFB.getInterfaceList().getEventOutputs().addAll(monitorFBs.stream()
				.map(n -> (createEvent(
						n.getName().substring(sourceType.getName().length() + 1, n.getName().length() - 8), false)))
				.toList());
	}
}
