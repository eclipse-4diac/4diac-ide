/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber - initial implementation and/or documentation
 *   Prankur Agarwal - sorting the items alphabetically
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;

public class BreadcrumbItem {
	private static final int SHELL_WIDTH = 250;
	private static final int SHELL_HEIGHT = 250;

	private final AdapterFactoryLabelProvider labelProvider;
	private final ITreeContentProvider contentProvider;
	private final Object current;
	private final ToolItem text;
	private ToolItem arrow;
	private final BreadcrumbWidget parent;
	private Shell shell;

	BreadcrumbItem(final BreadcrumbWidget parent, final Object current, final AdapterFactoryLabelProvider labelProvider,
			final AdapterFactoryContentProvider contentProvider) {
		this.current = current;
		this.parent = parent;
		this.labelProvider = labelProvider;
		this.contentProvider = new FilteredBreadCrumbContentProvider(contentProvider);

		text = new ToolItem(parent.getToolBar(), SWT.PUSH);
		text.setText(labelProvider.getText(current));
		text.setImage(labelProvider.getImage(current));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				super.widgetSelected(e);
				updateBreadcrumb(current);
			}
		});

		if (this.contentProvider.hasChildren(current)) {
			arrow = new ToolItem(parent.getToolBar(), SWT.PUSH);
			arrow.setImage(new BreadcrumbArrowDescriptor().createImage());
			arrow.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					super.widgetSelected(e);
					openShell();
				}
			});
		}

	}

	public Object getModel() {
		return current;
	}

	public String getText() {
		return labelProvider.getText(current);
	}

	void dispose() {
		text.dispose();
		if (arrow != null) {
			if (arrow.getImage() != null) {
				arrow.getImage().dispose();
			}
			arrow.dispose();
		}
	}

	private void openShell() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault();
		}
		shell = new Shell(display, SWT.ON_TOP | SWT.NO_FOCUS | SWT.NO_TRIM);
		shell.setSize(SHELL_WIDTH, SHELL_HEIGHT);
		shell.setLayout(new FillLayout());

		final TreeViewer viewer = new TreeViewer(shell);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setInput(current);
		viewer.getControl().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				super.focusLost(e);
				if (shell != null) {
					shell.close();
				}
			}
		});
		viewer.addSelectionChangedListener(this::handleTreeSelection);

		final Point location = new Point(arrow.getBounds().x, arrow.getBounds().y);
		final Rectangle rect = arrow.getBounds();
		final Point menuLocation = new Point(location.x - 1, location.y + rect.height);
		shell.setLocation(display.map(arrow.getParent(), null, menuLocation));
		shell.open();
	}

	private void updateBreadcrumb(final Object obj) {
		parent.setInput(obj);
		if (shell != null && !shell.isDisposed() && shell.isEnabled()) {
			shell.close();
		}
	}

	private void handleTreeSelection(final SelectionChangedEvent event) {
		final IStructuredSelection selection = event.getStructuredSelection();
		if (!selection.isEmpty()) {
			updateBreadcrumb(selection.getFirstElement());
		}
	}

	private static class FilteredBreadCrumbContentProvider implements ITreeContentProvider {

		final AdapterFactoryContentProvider nestedContentProvider;

		public FilteredBreadCrumbContentProvider(final AdapterFactoryContentProvider nestedContentProvider) {
			this.nestedContentProvider = nestedContentProvider;
		}

		@Override
		public Object[] getElements(final Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			return Arrays.stream(nestedContentProvider.getChildren(parentElement))
					.filter(obj -> (obj instanceof IFile) || (obj instanceof SystemConfiguration)
							|| (obj instanceof Application) || (obj instanceof SubApp) || (obj instanceof CFBInstance)
							|| (obj instanceof Device) || (obj instanceof Resource))
					.sorted(new ChildrenSortComparator()).toArray();
		}

		@Override
		public Object getParent(final Object element) {
			return nestedContentProvider.getParent(element);
		}

		@Override
		public boolean hasChildren(final Object element) {
			return getChildren(element).length != 0;
		}
	}

	private static final class BreadcrumbArrowDescriptor extends CompositeImageDescriptor {

		@Override
		protected void drawCompositeImage(final int width, final int height) {
			final Display display = Display.getCurrent();
			final int SIZE = 5;
			final ImageDataProvider imageProvider = zoom -> {
				final Image image = new Image(display, SIZE, SIZE * 2);

				final GC gc = new GC(image, SWT.LEFT_TO_RIGHT);

				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				gc.fillPolygon(new int[] { 0, 0, SIZE, SIZE, 0, SIZE * 2 });
				gc.dispose();

				final ImageData imageData = image.getImageData(zoom);
				image.dispose();

				final int whitePixel = imageData.palette
						.getPixel(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB());
				imageData.transparentPixel = whitePixel;

				return imageData;
			};
			drawImage(imageProvider, (width / 2) - (SIZE / 2), (height / 2) - SIZE);
		}

		@Override
		protected Point getSize() {
			return new Point(16, 16);
		}

	}

}

class ChildrenSortComparator implements Comparator<Object> {
	@Override
	public int compare(final Object obj1, final Object obj2) {
		if (obj1 instanceof SystemConfiguration) {
			if (obj2 instanceof SystemConfiguration) {
				return -1;
			}
			return 1;
		}

		return getName(obj1).compareTo(getName(obj2));
	}

	@SuppressWarnings("static-method")
	private String getName(final Object obj) {
		if (obj instanceof final IFile file) {
			return file.getName();
		}
		if (obj instanceof final INamedElement ne) {
			return ne.getName();
		}

		// would not come here as already a filter in Arrays.stream
		return ""; //$NON-NLS-1$
	}
}
