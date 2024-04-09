/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class MoveThroughHierarchyHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (null != editor) {
			final ISelection selection = HandlerUtil.getCurrentSelection(event);
			final List<FBNetworkElement> fbelements = HandlerHelper.getSelectedFBNElements(selection);

			if (!fbelements.isEmpty()) {

				final List<TreeNode> nodeList = new ArrayList<>();
				EObject container = fbelements.get(0).eContainer();
				while (container != null) {
					if (container instanceof INamedElement) {
						nodeList.add(new TreeNode(container));
					}
					container = container.eContainer();
				}

				final ElementTreeSelectionDialog dia = new ElementTreeSelectionDialog(
						Display.getCurrent().getActiveShell(), new TreeNodeLabelProvider(),
						new TreeNodeContentProvider());
				dia.setInput(nodeList.toArray(new TreeNode[0]));
				dia.open();
				final Object res = ((TreeNode) dia.getResult()[0]).getValue();
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);

		final List<FBNetworkElement> fbElements = HandlerHelper.getSelectedFBNElements(selection);
		if ((!fbElements.isEmpty()) && (fbElements.get(0).getFbNetwork().eContainer() instanceof SubApp)) {
			// we are inside of a subapp
			final FBNetwork parent = fbElements.get(0).getFbNetwork();
			final boolean sameParentNetwork = fbElements.stream().allMatch(el -> parent.equals(el.getFbNetwork()));
			setBaseEnabled(sameParentNetwork);
			return;
		}

		setBaseEnabled(false);
	}

	public static class TreeNodeLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof final TreeNode treeNode && treeNode.getValue() instanceof final INamedElement e) {
				return e.getName();
			}
			return element.toString();
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof final TreeNode node) {
				if (node.getValue() instanceof UntypedSubApp) {
					return FordiacImage.ICON_SUB_APP.getImage();
				}
				if (node.getValue() instanceof TypedSubApp) {
					return FordiacImage.ICON_SUB_APP_TYPE.getImage();
				}
				if (node.getValue() instanceof Application) {
					return FordiacImage.ICON_APPLICATION.getImage();
				}
			}
			return super.getImage(element);
		}

		@Override
		public StyledString getStyledText(final Object element) {
			return null;
		}
	}
}
