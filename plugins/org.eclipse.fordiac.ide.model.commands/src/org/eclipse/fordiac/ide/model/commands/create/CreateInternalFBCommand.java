/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

public class CreateInternalFBCommand extends CreationCommand implements ScopedCommand {

	/** The element where the internal FB is added to */
	private final BaseFBType baseFbType;

	/** Command information */
	private FBTypeEntry fbType;
	private final String name;
	private final int index;

	/** Created internal FB */
	private FB internalFB;

	static final String DEFAULT_INTERNAL_FB_NAME = "InternalFB1"; //$NON-NLS-1$

	protected CreateInternalFBCommand(final BaseFBType baseFbType) {
		this(baseFbType, 0, null, null);
	}

	public CreateInternalFBCommand(final BaseFBType baseFbType, final int index, final String name,
			final FBTypeEntry fbType) {
		this.baseFbType = Objects.requireNonNull(baseFbType);
		this.fbType = fbType;
		if (null == fbType) {
			this.fbType = baseFbType.getTypeLibrary().getFbTypes().iterator().next();
		}
		this.name = (null != name) ? name : DEFAULT_INTERNAL_FB_NAME;
		this.index = index;
	}

	@Override
	public Object getCreatedElement() {
		return internalFB;
	}

	private EList<FB> getInteralFBList() {
		final BaseFBType type = baseFbType;
		return type.getInternalFbs();
	}

	@Override
	public void execute() {
		internalFB = LibraryElementFactory.eINSTANCE.createFB();
		internalFB.setTypeEntry(fbType);
		internalFB.setComment(""); //$NON-NLS-1$
		internalFB.setInterface(fbType.getType().getInterfaceList().copy());
		getInteralFBList().add(index, internalFB);
		internalFB.setName(NameRepository.createUniqueName(internalFB, name));
	}

	@Override
	public void undo() {
		getInteralFBList().remove(internalFB);
	}

	@Override
	public void redo() {
		getInteralFBList().add(index, internalFB);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(baseFbType);
	}
}
