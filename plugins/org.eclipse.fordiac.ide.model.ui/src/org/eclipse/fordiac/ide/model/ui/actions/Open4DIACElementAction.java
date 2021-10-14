/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University
 * 				 2021 Primetals Technologies Austria GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Michael Oberlehner
 *     - adapted for typed subapp and typed cfb
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.ui.Activator;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

public class Open4DIACElementAction extends BaseSelectionListenerAction {

	public static final String ID = Activator.PLUGIN_ID + ".OpenAction";//$NON-NLS-1$

	public Open4DIACElementAction(final IWorkbenchPart part) {
		super(Messages.OpenEditorAction_text);
		setId(ID);
	}

	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {

		if (selection.size() != 1) {
			return false;
		}

		final Object model = convertFromEditPart(getStructuredSelection().getFirstElement());
		customizeOpenMenu(model);

		return ((model instanceof Device) || (model instanceof SystemConfiguration) || (model instanceof Application)
				|| (model instanceof SubApp) || (model instanceof CFBInstance) || (model instanceof Resource));
	}

	private void customizeOpenMenu(final Object model) {
		if (isTypedSubAppOrCFBInstance(model)) {
			setText(Messages.OpenEditorAction_viewertext);
		} else {
			setText(Messages.OpenEditorAction_text);
		}
	}

	@Override
	public void run() {
		Object selected = convertFromEditPart(getStructuredSelection().getFirstElement());

		Object refObject = null;
		if (!(selected instanceof FB) || isTypedComposite(selected)) {
			if (selected instanceof Device) {
				refObject = selected;
				selected = ((Device) selected).getSystemConfiguration();
			} else if (selected instanceof Segment) {
				refObject = selected;
				selected = ((Segment) refObject).eContainer();
			}
		}
		final IEditorPart editor = OpenListenerManager.openEditor((EObject) selected);
		HandlerHelper.selectElement(refObject, editor);
	}

	private static boolean isTypedSubAppOrCFBInstance(final Object obj) {
		if (obj instanceof SubApp) {
			return ((SubApp) obj).isTyped() || ((SubApp) obj).isContainedInTypedInstance();
		} else if (obj instanceof FB) {
			return isTypedComposite(obj);
		} else {
			return false;
		}
	}

	private static boolean isTypedComposite(final Object obj) {
		return (obj instanceof CFBInstance);
	}

	private static Object convertFromEditPart(Object model) {
		if (model instanceof EditPart) {
			model = ((EditPart) model).getModel();
		}
		return model;
	}
}
