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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.ui.Activator;
import org.eclipse.fordiac.ide.model.ui.Messages;
import org.eclipse.fordiac.ide.model.ui.editors.BreadcrumbUtil;
import org.eclipse.gef.EditPart;
//import org.eclipse.fordiac.ide.systemmanagement.ui.linkhelpers.AbstractEditorLinkHelper;
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

		if ((model instanceof Device) || (model instanceof SystemConfiguration) || (model instanceof Application)
				|| (model instanceof SubApp) || model instanceof Resource) {
			return true;
		}

		if (model instanceof FB) {
			// if we have an Fb check if it is in a subapp or application
			return isFBInAppOrSubApp((FB) model);

		}
		return false;
	}

	@Override
	public void run() {
		Object selected = convertFromEditPart(getStructuredSelection().getFirstElement());

		Object refObject = null;
		if (selected instanceof FB && !isTypedComposite(selected)) {
			refObject = selected;
			selected = getFBRootNode((FBNetworkElement) selected);
		} else if (selected instanceof Device) {
			refObject = selected;
			selected = ((Device) selected).getSystemConfiguration();
		} else if (selected instanceof Segment) {
			refObject = selected;
			selected = ((Segment) refObject).eContainer();
		}
		final IEditorPart editor = OpenListenerManager.openEditor((EObject) selected);
		BreadcrumbUtil.selectElement(refObject, editor);
	}

	private static boolean isTypedComposite(final Object obj) {
		return (obj instanceof CFBInstance);
	}

	private static boolean isFBInAppOrSubApp(final FB fb) {
		final EObject rootNode = getFBRootNode(fb);
		return ((rootNode instanceof Application) || (rootNode instanceof SubApp));
	}

	private static EObject getFBRootNode(final FBNetworkElement fb) {
		final EObject fbCont = fb.eContainer();
		EObject rootNode = null;
		if (fbCont instanceof FBNetwork) {
			rootNode = ((FBNetwork) fbCont).eContainer();
		}
		return rootNode;
	}

	private static Object convertFromEditPart(Object model) {
		if (model instanceof EditPart) {
			model = ((EditPart) model).getModel();
		}
		return model;
	}
}
