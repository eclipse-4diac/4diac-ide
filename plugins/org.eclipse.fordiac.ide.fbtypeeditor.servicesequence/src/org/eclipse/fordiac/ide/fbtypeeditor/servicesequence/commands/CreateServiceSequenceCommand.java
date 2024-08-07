/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
 *               2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Melanie Winter - clean up
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

public class CreateServiceSequenceCommand extends AbstractCreateElementCommand<ServiceSequence> {
	private final FBType fbType;
	private ServiceSequence sequence;
	private boolean emptyService;

	private static final String LEFT_INTERFACE_NAME = "external"; //$NON-NLS-1$
	private static final String RIGHT_INTERFACE_NAME = "internal_interface"; //$NON-NLS-1$
	private static final String DEFAULT_SEQUENCE_NAME = "ServiceSequence"; //$NON-NLS-1$
	private ServiceInterface leftInterface;
	private ServiceInterface rightInterface;

	public CreateServiceSequenceCommand(final Service service) {
		super(service.getServiceSequence());
		fbType = service.getFBType();

	}

	public CreateServiceSequenceCommand(final Service service, final ServiceSequence refElement) {
		super(service.getServiceSequence(), refElement);
		fbType = service.getFBType();
	}

	@Override
	protected ServiceSequence createNewElement() {
		sequence = LibraryElementFactory.eINSTANCE.createServiceSequence();
		return sequence;
	}

	private void createEmptyServiceModel() {
		if (null == fbType.getService()) {
			emptyService = true;
			fbType.setService(LibraryElementFactory.eINSTANCE.createService());
		}
		if (fbType.getService().getLeftInterface() == null) {
			leftInterface = LibraryElementFactory.eINSTANCE.createServiceInterface();
			leftInterface.setName(LEFT_INTERFACE_NAME);
			fbType.getService().setLeftInterface(leftInterface);
		}
		if (fbType.getService().getRightInterface() == null) {
			rightInterface = LibraryElementFactory.eINSTANCE.createServiceInterface();
			rightInterface.setName(RIGHT_INTERFACE_NAME);
			fbType.getService().setRightInterface(rightInterface);
		}
	}

	@Override
	public void execute() {
		createEmptyServiceModel();
		super.execute();
		sequence.setName(NameRepository.createUniqueName(sequence, DEFAULT_SEQUENCE_NAME));
	}

	@Override
	public void undo() {
		if (null != leftInterface) {
			fbType.getService().setLeftInterface(null);
		}
		if (null != rightInterface) {
			fbType.getService().setRightInterface(null);
		}
		if (emptyService) {
			fbType.setService(null);
		}
		super.undo();
	}

	@Override
	public void redo() {
		createEmptyServiceModel();
		super.redo();
	}

	@Override
	public ServiceSequence getCreatedElement() {
		return sequence;
	}

}