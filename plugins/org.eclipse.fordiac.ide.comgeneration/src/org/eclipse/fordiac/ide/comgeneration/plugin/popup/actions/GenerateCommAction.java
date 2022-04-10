/*******************************************************************************
 * Copyright (c) 2014 - 2015 Luka Lednicki, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Luka Lednicki, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.plugin.popup.actions;

import org.eclipse.fordiac.ide.comgeneration.implementation.Analyzer;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommFBGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommFBGenerator.TransferedData;
import org.eclipse.fordiac.ide.comgeneration.implementation.CommunicationModel;
import org.eclipse.fordiac.ide.comgeneration.implementation.ProtocolSelector;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.CanPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.EthernetPubSubGenerator;
import org.eclipse.fordiac.ide.comgeneration.implementation.mediagenerators.MediaSpecificGeneratorFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GenerateCommAction implements IObjectActionDelegate {
	private Application selectedApplication = null;

	public GenerateCommAction() {
		super();
	}

	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
		// currently nothing to do here
	}

	@Override
	public void run(final IAction action) {
		if (selectedApplication != null) {
			final TypeLibrary typeLib = selectedApplication.getAutomationSystem().getTypeLibrary();
			final MediaSpecificGeneratorFactory specificGeneratorFactory = new MediaSpecificGeneratorFactory();
			final EthernetPubSubGenerator ethernetPubSubGenerator = new EthernetPubSubGenerator(typeLib);
			ethernetPubSubGenerator.reset(61550);
			specificGeneratorFactory.addGenerator(ethernetPubSubGenerator);
			specificGeneratorFactory.addGenerator(new CanPubSubGenerator(typeLib));
			final Analyzer analyzer = new Analyzer();
			final CommunicationModel model = analyzer.analyze(selectedApplication);
			ProtocolSelector.doAutomatedProtocolSelection(model);
			CommFBGenerator.removeGeneratedElements(selectedApplication);
			final CommFBGenerator generator = new CommFBGenerator(specificGeneratorFactory);
			generator.setTransferedData(TransferedData.EXACT);
			generator.generate(model);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		selectedApplication = null;
		if (selection instanceof StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			final Object selectedObject = structuredSelection.getFirstElement();
			if (selectedObject instanceof Application) {
				selectedApplication = (Application) selectedObject;
			}
		}
	}
}
