/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GbmH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.bootfile.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.bootfile.Messages;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

public class DownloadSelectionTree extends ContainerCheckedTreeViewer {

	private static final String DOWNLOAD_DEV_SELECTION = "DOWNLOAD_DEV_SELECTION"; //$NON-NLS-1$
	private static final String DOWNLOAD_DEV_MGRID = "DOWNLOAD_DEV_MGRID"; //$NON-NLS-1$
	private static final String DOWNLOAD_DEV_PROPERTIES = "DOWNLOAD_DEV_PROPERTIES"; //$NON-NLS-1$

	/**
	 * The Class ViewContentProvider.
	 */
	private static class ViewContentProvider implements ITreeContentProvider {

		@Override
		public void inputChanged(final Viewer v, final Object oldInput, final Object newInput) {
			// not used
		}

		@Override
		public Object[] getElements(final Object parent) {
			if (parent instanceof List) {
				return ((List<?>) parent).toArray();
			}
			return getChildren(parent);
		}

		@Override
		public Object getParent(final Object child) {
			if (child instanceof Device) {
				return ((Device) child).eContainer();
			}
			if (child instanceof Resource) {
				return ((Resource) child).getDevice();
			}
			return null;
		}

		@Override
		public Object[] getChildren(final Object parent) {
			if (parent instanceof AutomationSystem) {
				final SystemConfiguration sysConf = ((AutomationSystem) parent).getSystemConfiguration();

				if (sysConf == null) {
					return new Object[] {};
				}
				final List<Device> devList = new ArrayList<>(sysConf.getDevices());
				Collections.sort(devList, NamedElementComparator.INSTANCE);
				return devList.toArray();
			}
			if (parent instanceof Device) {
				final List<Resource> resource = new ArrayList<>();
				for (final Resource res : ((Device) parent).getResource()) {
					if (!res.isDeviceTypeResource()) {
						resource.add(res);
					}
				}
				return resource.toArray();
			}
			return new Object[0];
		}

		@Override
		public boolean hasChildren(final Object parent) {
			if (parent instanceof AutomationSystem) {
				return !((AutomationSystem) parent).getSystemConfiguration().getDevices().isEmpty();
			}
			if (parent instanceof Device) {
				return !((Device) parent).getResource().isEmpty();
			}
			return (parent instanceof Resource);
		}
	}

	/**
	 * The Class ViewLabelProvider.
	 */
	static class ViewLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object obj) {
			if (obj instanceof AutomationSystem) {
				return ((AutomationSystem) obj).getName();
			}
			if (obj instanceof INamedElement) {
				return ((INamedElement) obj).getName();
			}
			return obj.toString();
		}

		@Override
		public Image getImage(final Object obj) {
			if (obj instanceof AutomationSystem) {
				return FordiacImage.ICON_SYSTEM_CONFIGURATION.getImage();
			}
			if (obj instanceof Device) {
				return FordiacImage.ICON_DEVICE.getImage();
			}
			if (obj instanceof final Resource res) {
				if (res.isDeviceTypeResource()) {
					return FordiacImage.ICON_FIRMWARE_RESOURCE.getImage();
				}
				return FordiacImage.ICON_RESOURCE.getImage();
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}

	}

	static class DownloadDecoratingLabelProvider extends DecoratingLabelProvider implements ITableLabelProvider {

		public DownloadDecoratingLabelProvider(final ILabelProvider provider, final ILabelDecorator decorator) {
			super(provider, decorator);
		}

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			if (columnIndex == 0) {
				return getImage(element);
			}
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			switch (columnIndex) {
			case 0:
				return getText(element);
			case 1:
				if (element instanceof Device) {
					return DeploymentHelper.getMgrIDSafe((Device) element);
				}
				break;
			case 2:
				if (element instanceof Device) {
					return getSelectedString(element);
				}
				break;
			default:
				break;
			}
			return ""; //$NON-NLS-1$
		}

	}

	public DownloadSelectionTree(final Composite parent, final int style) {
		super(parent, style);

		getTree().setHeaderVisible(true);
		final TreeColumn column1 = new TreeColumn(getTree(), SWT.LEFT);
		column1.setText(Messages.DownloadSelectionTree_Selection);
		column1.setWidth(200);

		final TreeColumn mgrIDColumn = new TreeColumn(getTree(), SWT.LEFT);
		mgrIDColumn.setText(Messages.DownloadSelectionTree_MgrId);
		mgrIDColumn.setWidth(150);

		final TreeColumn propertiesColumn = new TreeColumn(getTree(), SWT.LEFT);
		propertiesColumn.setText(Messages.DownloadSelectionTree_Properties);
		propertiesColumn.setWidth(200);

		setContentProvider(new ViewContentProvider());
		final ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
		final LabelProvider lp = new ViewLabelProvider();
		setLabelProvider(new DownloadDecoratingLabelProvider(lp, decorator));

		setCellModifier(new ICellModifier() {
			@Override
			public boolean canModify(final Object element, final String property) {
				return (property.equals(DOWNLOAD_DEV_PROPERTIES) && element instanceof Device);
			}

			@Override
			public Object getValue(final Object element, final String property) {
				if (DOWNLOAD_DEV_PROPERTIES.equals(property)) {
					return getSelectedString(element);
				}
				return null;
			}

			@Override
			public void modify(final Object element, final String property, final Object value) {
				// nothing to do
			}
		});

		setCellEditors(new CellEditor[] { new TextCellEditor(), new TextCellEditor(), null });

		setColumnProperties(new String[] { DOWNLOAD_DEV_SELECTION, DOWNLOAD_DEV_MGRID, DOWNLOAD_DEV_PROPERTIES });
	}

	private static String getSelectedString(final Object element) {
		final StringBuilder buffer = new StringBuilder();
		buffer.append("["); //$NON-NLS-1$
		boolean first = true;
		for (final VarDeclaration varDeclaration : ((Device) element).getVarDeclarations()) {
			if (first) {
				first = false;
			} else {
				buffer.append(", "); //$NON-NLS-1$
			}
			buffer.append(varDeclaration.getName());
			buffer.append("="); //$NON-NLS-1$
			buffer.append(varDeclaration.getValue() != null ? varDeclaration.getValue().getValue() : ""); //$NON-NLS-1$
		}
		buffer.append("]"); //$NON-NLS-1$
		return buffer.toString();
	}

}
