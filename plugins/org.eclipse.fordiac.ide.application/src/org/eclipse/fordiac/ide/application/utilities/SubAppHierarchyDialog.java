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

package org.eclipse.fordiac.ide.application.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.DelegatingStyledCellLabelProvider;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class SubAppHierarchyDialog {

	private final FBNetwork root;
	private TreeNode rootNode;
	private final List<EObject> initialSelectionElements;
	private final List<TreeNode> initalSelection = new ArrayList<>();

	private final ElementTreeSelectionDialog dialog;

	public SubAppHierarchyDialog(final IProject project, final List<EObject> initialSelectionElements) {
		this.root = null;
		this.initialSelectionElements = initialSelectionElements;
		final List<TreeNode> nodeList = buildNodeList(TypeLibraryManager.INSTANCE.getTypeLibrary(project));

		dialog = new ElementTreeSelectionDialog(Display.getCurrent().getActiveShell(), new TreeNodeLabelProvider(),
				new TreeNodeContentProvider()) {
			@Override
			protected TreeViewer createTreeViewer(final Composite parent) {
				final Composite buttonsComposite = new Composite(parent, SWT.NONE);
				buttonsComposite.setLayout(new GridLayout(2, false));
				final TreeViewer viewer = super.createTreeViewer(parent);

				final Button expandAll = WidgetFactory.button(SWT.NONE).image(FordiacImage.ICON_EXPAND_ALL.getImage())
						.create(buttonsComposite);
				expandAll.addListener(SWT.Selection, event -> viewer.expandAll());
				final Button collapseAll = WidgetFactory.button(SWT.NONE)
						.image(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_COLLAPSEALL))
						.create(buttonsComposite);
				collapseAll.addListener(SWT.Selection, event -> viewer.collapseAll());
				return viewer;
			}
		};
		dialog.setInitialSelections(initalSelection.toArray(Object[]::new));
		dialog.setInput(nodeList.toArray(new TreeNode[0]));

		dialog.setTitle(Messages.SubAppHierarchyDialogTitle);
	}

	public SubAppHierarchyDialog(final FBNetworkElement root, final List<FBNetworkElement> filteredElements) {
		this.root = (FBNetwork) root.eContainer();
		this.initialSelectionElements = Collections.emptyList();
		final List<TreeNode> nodeList = buildNodeList(filteredElements);

		dialog = createDialog();
		dialog.setInput(nodeList.toArray(new TreeNode[0]));
		dialog.setTitle(Messages.MoveElementDialogTitle);
		dialog.setAllowMultiple(false);
		dialog.setInitialSelection(rootNode);
	}

	public FBNetwork mapResultToFBNetwork(final Object[] result) {
		final Object firstResult = result != null ? dialog.getResult()[0] : null;
		if (firstResult instanceof final TreeNode node && node != rootNode) {
			return switch (node.getValue()) {
			case final SubApp subapp -> subapp.getSubAppNetwork();
			case final Application app -> app.getFBNetwork();
			case final CompositeFBType cfb -> cfb.getFBNetwork();
			default -> null;
			};
		}
		return null;
	}

	public static List<URI> mapResultToURIs(final Object[] result) {
		if (result != null) {
			return Arrays.stream(result).filter(TreeNode.class::isInstance).map(node -> ((TreeNode) node).getValue())
					.map(value -> {
						if (value instanceof final EObject eobj) {
							return EcoreUtil.getURI(eobj);
						}
						return null;
					}).filter(Objects::nonNull).toList();
		}
		return Collections.emptyList();
	}

	public Object[] open() {
		dialog.open();
		return dialog.getResult();
	}

	private ElementTreeSelectionDialog createDialog() {
		return new ElementTreeSelectionDialog(Display.getCurrent().getActiveShell(),
				new DelegatingStyledCellLabelProvider(new TreeNodeLabelProvider()), new TreeNodeContentProvider()) {
			@Override
			protected TreeViewer createTreeViewer(final Composite parent) {
				final TreeViewer viewer = super.createTreeViewer(parent);
				viewer.expandAll();
				return viewer;
			}
		};
	}

	private List<TreeNode> buildNodeList(final TypeLibrary typeLib) {
		final Stream<EObject> stream = Stream.concat(typeLib.getSystems().stream().map(SystemEntry::getSystem),
				typeLib.getSubAppTypes().stream().map(SubAppTypeEntry::getType));
		return buildNodeList(stream, Collections.emptyList());
	}

	private List<TreeNode> buildNodeList(final List<FBNetworkElement> filterList) {
		final LibraryElement le = ModelHelper.getLibraryElementFromContextChecked(root);
		if (le instanceof final AutomationSystem automationSystem) {
			return buildNodeList(automationSystem.getApplication().stream(), filterList);
		}
		return buildNodeList(Stream.of(le), filterList);
	}

	private List<TreeNode> buildNodeList(final Stream<? extends EObject> roots,
			final List<FBNetworkElement> filterList) {
		final List<TreeNode> nodeList = new ArrayList<>();
		roots.forEach(r -> {
			if (r instanceof final Application application) {
				final TreeNode node = new TreeNode(application);
				nodeList.add(node);
				if (initialSelectionElements.contains(application)) {
					initalSelection.add(node);
				}
				if (root != null && application.getFBNetwork() == root) {
					rootNode = node;
				}
				addFBNetwork(node, application.getFBNetwork(), filterList);
			} else if (r instanceof final SubAppType subappType) {
				final TreeNode node = new TreeNode(subappType);
				nodeList.add(node);
				if (initialSelectionElements.contains(subappType)) {
					initalSelection.add(node);
				}
				if (root != null && subappType.getFBNetwork() == root) {
					rootNode = node;
				}
				addFBNetwork(node, subappType.getFBNetwork(), filterList);
			} else if (r instanceof final AutomationSystem automationSystem) {
				final List<TreeNode> childrenList = new ArrayList<>();
				final TreeNode systemNode = new TreeNode(automationSystem);
				nodeList.add(systemNode);
				if (initialSelectionElements.contains(automationSystem)) {
					initalSelection.add(systemNode);
				}
				automationSystem.getApplication().forEach(app -> {
					final TreeNode node = new TreeNode(app);
					node.setParent(systemNode);
					childrenList.add(node);
					if (initialSelectionElements.contains(app)) {
						initalSelection.add(node);
					}
					addFBNetwork(node, app.getFBNetwork(), filterList);
				});
				systemNode.setChildren(childrenList.toArray(new TreeNode[0]));
			}
		});
		return nodeList;
	}

	private void addFBNetwork(final TreeNode parent, final FBNetwork network, final List<FBNetworkElement> filterList) {
		final List<TreeNode> nodeList = new ArrayList<>();
		network.getNetworkElements().forEach(fbnE -> {
			if (fbnE instanceof final UntypedSubApp subapp && !filterList.contains(fbnE)) {
				final TreeNode node = new TreeNode(subapp);
				if (root != null && subapp.getSubAppNetwork() == root) {
					rootNode = node;
				}
				node.setParent(parent);
				nodeList.add(node);
				if (initialSelectionElements.contains(subapp)) {
					initalSelection.add(node);
				}
				addFBNetwork(node, subapp.getSubAppNetwork(), filterList);
			}
		});
		parent.setChildren(nodeList.toArray(new TreeNode[0]));
	}

	private class TreeNodeLabelProvider extends LabelProvider implements IStyledLabelProvider {

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
					return FordiacImage.ICON_SUB_APP.getImage();
				}
				if (node.getValue() instanceof SubAppType) {
					return FordiacImage.ICON_SUB_APP_TYPE.getImage();
				}
				if (node.getValue() instanceof Application) {
					return FordiacImage.ICON_APPLICATION.getImage();
				}
				if (node.getValue() instanceof AutomationSystem) {
					return FordiacImage.ICON_SYSTEM.getImage();
				}
			}
			return super.getImage(element);
		}

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof final TreeNode treeNode && treeNode == rootNode) {
				final StyledString styledString = new StyledString(getText(element));
				styledString.append(" - current Network", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
				return styledString;
			}
			return new StyledString(getText(element));
		}
	}
}
