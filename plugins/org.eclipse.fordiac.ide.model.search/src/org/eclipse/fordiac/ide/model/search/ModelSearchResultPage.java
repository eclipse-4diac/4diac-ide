/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - add table design, context menu
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TextMethod;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.ISelectionListener;

public class ModelSearchResultPage extends AbstractTextSearchViewPage {

	private static final String ID = "org.eclipse.fordiac.ide.model.search.ModelSearchResultPage"; //$NON-NLS-1$
	private ModelSearchTableContentProvider contentProvider;
	private String searchDescription;

	private static final int ELEMENT_KIND_COLUMN_WIDTH = 10;
	private static final int NAME_COLUMN_WIDTH = 10;
	private static final int COMMENT_COLUMN_WIDTH = 10;
	private static final int TYPE_COLUMN_WIDTH = 20;
	private static final int PATH_COLUMN_WIDTH = 20;
	private static final int LOCATION_COLUMN_WIDTH = 20;

	private static final String PATH_COLUMN = "Path"; //$NON-NLS-1$
	private static final String ELEMENT_KIND_COLUMN = "Element Kind"; //$NON-NLS-1$
	private static final String LOCATION_COLUMN = "Location"; //$NON-NLS-1$

	public ModelSearchResultPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT); // FLAG_LAYOUT_FLAT = table layout
	}

	@Override
	public void restoreState(final IMemento memento) {
		// Nothing to do here for now
	}

	@Override
	public void saveState(final IMemento memento) {
		// Nothing to do here for now
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getLabel() {
		return null != searchDescription ? searchDescription : Messages.SearchHeaderName;
	}

	@Override
	protected void elementsChanged(final Object[] objects) {
		if (contentProvider != null) {
			contentProvider.elementsChanged(objects);
		}
	}

	@Override
	protected void clear() {
		if (contentProvider != null) {
			contentProvider.clear();
		}
	}

	@Override
	public void setInput(final ISearchResult newSearch, final Object viewState) {
		super.setInput(newSearch, viewState);
		if (newSearch != null) {
			this.searchDescription = newSearch.getLabel();
		}
	}

	@Override
	protected void configureTreeViewer(final TreeViewer viewer) {
		throw new IllegalStateException("Doesn't support tree mode."); //$NON-NLS-1$
	}

	@Override
	protected TableViewer createTableViewer(final Composite parent) {
		return new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
	}

	// This method is called if the page was constructed with the flag
	// FLAG_LAYOUT_FLAT (see constructor)
	@Override
	protected void configureTableViewer(final TableViewer viewer) {
		contentProvider = new ModelSearchTableContentProvider(this);
		viewer.setContentProvider(contentProvider);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());

		final TableViewerColumn colKind = new TableViewerColumn(viewer, SWT.LEAD);
		colKind.getColumn().setText(ELEMENT_KIND_COLUMN);
		colKind.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IInterfaceElement) {
					return "Pin"; //$NON-NLS-1$
				}
				final String kind = element.getClass().getSimpleName();
				return kind.substring(0, kind.length() - 4);
			}
		});

		final TableViewerColumn colName = new TableViewerColumn(viewer, SWT.LEAD);
		colName.getColumn().setText(FordiacMessages.Name);
		colName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final INamedElement ne) {
					return ne.getName();
				}
				return super.getText(element);
			}
		});

		final TableViewerColumn colComment = new TableViewerColumn(viewer, SWT.LEAD);
		colComment.getColumn().setText(FordiacMessages.Comment);
		colComment.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final INamedElement ne) {
					return ne.getComment();
				}
				return super.getText(element);
			}
		});

		final TableViewerColumn colType = new TableViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(FordiacMessages.Type);
		colType.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final EObject eobj) {
					return eobj.eResource().getURI().lastSegment();
				}
				return super.getText(element);
			}
		});

		final TableViewerColumn path = new TableViewerColumn(viewer, SWT.LEAD);
		path.getColumn().setText(PATH_COLUMN);
		path.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final EObject eobj) {
					final var segments = eobj.eResource().getURI().segmentsList();
					if (segments.size() > 2) {
						return segments.stream().skip(1).limit(segments.size() - 2).map(URI::decode)
								.collect(Collectors.joining("/")); //$NON-NLS-1$
					}
				}
				return super.getText(element);
			}
		});

		final TableViewerColumn location = new TableViewerColumn(viewer, SWT.LEAD);
		location.getColumn().setText(LOCATION_COLUMN);
		location.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof TextAlgorithm || element instanceof TextMethod
						|| element instanceof FunctionFBType) {
					return Arrays.stream(contentProvider.getSearchResult().getMatches(element))
							.map(match -> Integer.toString(match.getLength()))
							.collect(Collectors.collectingAndThen(Collectors.joining(", "), //$NON-NLS-1$
									result -> result.isEmpty() ? "" //$NON-NLS-1$
											: (result.contains(", ") ? "Lines: " + result : "Line: " + result)));
				}

				final ModelSearchResult searchResult = (ModelSearchResult) contentProvider.getSearchResult();
				return searchResult.getDictionary().hierarchicalName(element);
			}
		});
	}

	// Double click to access the element we looked for
	private static void jumpToBlock(final OpenEvent doubleClick) {
		final StructuredSelection selectionList = (StructuredSelection) doubleClick.getSelection();
		if (!selectionList.isEmpty()) {
			final Object selection = selectionList.getFirstElement();
			if (selection instanceof final EObject eobj) {
				jumpHelper(eobj);
			}
		}
	}

	private static void jumpHelper(final EObject jumpingTo) {
		final IEditorPart editor = OpenListenerManager.openEditor(getParent(jumpingTo));
		if (editor instanceof final ISelectionListener listener) {
			// fb type editor
			listener.selectionChanged(editor, new StructuredSelection(EcoreUtil.getURI(jumpingTo)));
		} else {
			final GraphicalViewer viewer = HandlerHelper.getViewer(editor);
			HandlerHelper.selectElement(jumpingTo, viewer);
		}
	}

	private static EObject getParent(final EObject eobj) {
		if (eobj instanceof final Device dev) {
			return dev.getPosition().eContainer().eContainer();
		}
		if ((eobj instanceof Application) || (eobj instanceof FBType)) {
			return eobj;
		}
		EObject parent = null;
		if (eobj instanceof final IInterfaceElement ie) {
			parent = ie.getFBNetworkElement();
			if (parent != null) {
				parent = parent.eContainer().eContainer();
			} else {
				parent = ie.eContainer();
				if (parent.eContainer() != null) {
					parent = parent.eContainer();
				}
			}
		} else if (isInternalFb(eobj)) {
			parent = eobj.eContainer();
		} else if (eobj instanceof Algorithm || eobj instanceof Method) {
			parent = eobj.eContainer();
		} else {
			if (eobj.eContainer() == null) {
				return eobj;
			}
			parent = eobj.eContainer().eContainer();
		}
		// For unfolded subapps find the next parent that is not expanded as refElement
		while (parent instanceof final SubApp subApp && subApp.isUnfolded()) {
			parent = subApp.eContainer().eContainer();
		}
		return parent;
	}

	private static boolean isInternalFb(final EObject eobj) {
		return eobj instanceof final FB fb && fb.eContainer() instanceof FBType && !fb.isContainedInTypedInstance();
	}

	protected static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(ELEMENT_KIND_COLUMN_WIDTH));
		layout.addColumnData(new ColumnWeightData(NAME_COLUMN_WIDTH));
		layout.addColumnData(new ColumnWeightData(COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnWeightData(TYPE_COLUMN_WIDTH));
		layout.addColumnData(new ColumnWeightData(PATH_COLUMN_WIDTH));
		layout.addColumnData(new ColumnWeightData(LOCATION_COLUMN_WIDTH));
		return layout;
	}

	// override to increase visibility of this method
	@Override
	public StructuredViewer getViewer() {
		return super.getViewer();
	}

	@Override
	protected void handleOpen(final OpenEvent event) {
		ModelSearchResultPage.jumpToBlock(event);
	}

	public static void showResult(final EObject obj) {
		EObject toOpen = obj;
		if (obj instanceof final IInterfaceElement ie) {
			toOpen = ie.getFBNetworkElement();
		}
		if (obj instanceof SubApp) {
			toOpen = ((SubApp) toOpen).getOuterFBNetworkElement();
		}
		if (toOpen instanceof final FBNetworkElement fbne) {
			final IEditorPart p = HandlerHelper.openParentEditor(fbne);
			HandlerHelper.selectElement(obj, p);
		}
		HandlerHelper.openEditor(obj);
	}

	@Override
	protected void fillContextMenu(final IMenuManager mgr) {
		final Action showInEditor = new ShowInEditorAction(this);
		mgr.prependToGroup(IContextMenuConstants.GROUP_SHOW, showInEditor);
		super.fillContextMenu(mgr);
	}

	private static class ShowInEditorAction extends Action {
		private final ModelSearchResultPage fPage;

		public ShowInEditorAction(final AbstractTextSearchViewPage page) {
			super("Show in Editor"); //$NON-NLS-1$
			setToolTipText("Shows element in the editor"); //$NON-NLS-1$
			fPage = (ModelSearchResultPage) page;
		}

		@Override
		public void run() {
			showResult((EObject) fPage.getViewer().getStructuredSelection().getFirstElement());
		}
	}
}
