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
 *   Daniel Lindhuber
 *      - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.breadcrumb;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemContentProvider;
import org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer.SystemLabelProvider;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
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

class BreadcrumbItem {

	private static final int SHELL_WIDTH = 250;
	private static final int SHELL_HEIGHT = 250;

	private SystemLabelProvider labelProvider = new SystemLabelProvider();
	private SystemContentProvider contentProvider = new SystemContentProvider() {
		@Override
		public Object[] getChildren(Object parentElement) {
			return Arrays.stream(super.getChildren(parentElement))
					.filter(obj -> obj instanceof IFile || obj instanceof SystemConfiguration
							|| obj instanceof Application || (obj instanceof SubApp && ((SubApp) obj).getType() == null)
							|| obj instanceof Device || obj instanceof Resource)
					.collect(Collectors.toList()).toArray();
		}

		@Override
		public boolean hasChildren(Object element) {
			return getChildren(element).length != 0;
		}

	};

	private Object current;
	private ToolItem text;
	private ToolItem arrow;
	private BreadcrumbWidget parent;
	private Shell shell;

	BreadcrumbItem(BreadcrumbWidget parent, Object current) {
		this.current = current;
		this.parent = parent;

		text = new ToolItem(parent.getToolBar(), SWT.PUSH);
		text.setText(labelProvider.getText(current));
		text.setImage(labelProvider.getImage(current));
		text.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				updateBreadcrumb(current);
			}
		});

		if (contentProvider.hasChildren(current)) {
			arrow = new ToolItem(parent.getToolBar(), SWT.PUSH);
			arrow.setImage(new BreadcrumbArrowDescriptor().createImage());
			arrow.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					openShell();
				}
			});
		}

	}

	void dispose() {
		text.dispose();
		if (arrow != null) {
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

		TreeViewer viewer = new TreeViewer(shell);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setInput(current);
		viewer.getControl().addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				if (shell != null) {
					shell.close();
				}
			}
		});
		viewer.addSelectionChangedListener(this::handleTreeSelection);

		Point location = new Point(arrow.getBounds().x, arrow.getBounds().y);
		Rectangle rect = arrow.getBounds();
		Point menuLocation = new Point(location.x - 1, location.y + rect.height);
		shell.setLocation(display.map(arrow.getParent(), null, menuLocation));
		shell.open();
	}

	private void updateBreadcrumb(Object obj) {
		parent.setInput(obj);
		if (shell != null && !shell.isDisposed() && shell.isEnabled()) {
			shell.close();
		}
	}

	private void handleTreeSelection(SelectionChangedEvent event) {
		IStructuredSelection selection = event.getStructuredSelection();
		if (!selection.isEmpty()) {
			updateBreadcrumb(selection.getFirstElement());
		}
	}

	private final class BreadcrumbArrowDescriptor extends CompositeImageDescriptor {

		@Override
		protected void drawCompositeImage(int width, int height) {
			Display display = Display.getCurrent();
			final int SIZE = 5;
			ImageDataProvider imageProvider = zoom -> {
				Image image = new Image(display, SIZE, SIZE * 2);

				GC gc = new GC(image, SWT.LEFT_TO_RIGHT);

				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				gc.fillPolygon(new int[] { 0, 0, SIZE, SIZE, 0, SIZE * 2 });
				gc.dispose();

				ImageData imageData = image.getImageData(zoom);
				image.dispose();

				int whitePixel = imageData.palette.getPixel(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB());
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
