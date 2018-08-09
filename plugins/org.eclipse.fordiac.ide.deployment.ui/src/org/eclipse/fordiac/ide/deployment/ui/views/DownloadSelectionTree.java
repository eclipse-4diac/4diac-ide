/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GbmH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

public class DownloadSelectionTree extends ContainerCheckedTreeViewer {
	
	private static final String DOWNLOAD_DEV_SELECTION = "DOWNLOAD_DEV_SELECTION"; //$NON-NLS-1$
	private static final String DOWNLOAD_DEV_MGRID = "DOWNLOAD_DEV_MGRID"; //$NON-NLS-1$
	private static final String DOWNLOAD_DEV_PROPERTIES = "DOWNLOAD_DEV_PROPERTIES"; //$NON-NLS-1$

	
	static void initSelectedProperties(Device device) {
		ArrayList<VarDeclaration> selectedProperties = new ArrayList<VarDeclaration>();
		for (VarDeclaration varDecl : device.getVarDeclarations()) {
			if (!varDecl.getName().equalsIgnoreCase("mgr_id")) { //$NON-NLS-1$
				selectedProperties.add(varDecl);
			}
		}
		DeploymentCoordinator.getInstance().setDeviceProperties(device,
				selectedProperties);
	}
	
		
	/**
	 * The Class ViewContentProvider.
	 */
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		
		/** The adapter. */
		private final EContentAdapter adapter = new EContentAdapter() {

			@Override
			public void notifyChanged(Notification notification) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						if (!getTree().isDisposed()) {
							refresh(true);
						}
					}
				});
				super.notifyChanged(notification);
			}

		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
		 * .viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public void inputChanged(final Viewer v, final Object oldInput,
				final Object newInput) {
			// not used
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		@Override
		public void dispose() {
			// TODO check whether resorces needs to be freed
			// not used
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
		 * .lang.Object)
		 */
		@Override
		public Object[] getElements(final Object parent) {
			if (parent.equals(getInput())) {
				List<AutomationSystem> systems = SystemManager.INSTANCE.getSystems();
				
				for (AutomationSystem sys : systems) {
					if (!sys.eAdapters().contains(adapter)) {
						sys.eAdapters().add(adapter);

					}
				}
				return systems.toArray();
			}
			return getChildren(parent);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
		 * )
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
		 * Object)
		 */
		@Override
		public Object[] getChildren(final Object parent) {
			if (parent instanceof AutomationSystem) {
				SystemConfiguration sysConf = ((AutomationSystem) parent).getSystemConfiguration();
				
				if (sysConf == null) {
					return new Object[] {};
				}
				if (!sysConf.eAdapters().contains(adapter)) {
					sysConf.eAdapters().add(adapter);
				}
				List <Device> devList = new ArrayList<>(sysConf.getDevices()); 
				Collections.sort(devList, NamedElementComparator.INSTANCE);
				return devList.toArray();
			}
			if (parent instanceof Device) {
				Device device = (Device) parent;
				if (!device.eAdapters().contains(adapter)) {
					//this device is new in the list of devices we need to correctly set up its properties
					initSelectedProperties(device);
					device.eAdapters().add(adapter);
				}
				
				List<Resource> resource = new ArrayList<>();
				for (Resource res : ((Device) parent).getResource()) {
					if (!res.isDeviceTypeResource()) {
						resource.add(res);
					}
				}
				return resource.toArray();
			}
			return new Object[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
		 * Object)
		 */
		@Override
		public boolean hasChildren(final Object parent) {
			if (parent instanceof AutomationSystem) {
				return ((AutomationSystem) parent).getSystemConfiguration().getDevices().size() > 0;
			}
			if (parent instanceof Device) {
				return ((Device) parent).getResource().size() > 0;
			}
			if (parent instanceof Resource) {
				return false;
			}
			return false;
		}
	}
	
	
	/**
	 * The Class ViewLabelProvider.
	 */
	static class ViewLabelProvider extends LabelProvider{

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
		 */
		@Override
		public Image getImage(final Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof AutomationSystem) {
				return FordiacImage.ICON_SystemConfiguration.getImage();
			}
			if (obj instanceof Device) {
				return FordiacImage.ICON_Device.getImage();
			}
			if (obj instanceof Resource) {
				Resource res = (Resource) obj;
				if (res.isDeviceTypeResource()) {
					return FordiacImage.ICON_FirmwareResource.getImage();
				}
				return FordiacImage.ICON_Resource.getImage();
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}

	}
	
	static class DownloadDecoratingLabelProvider extends DecoratingLabelProvider implements ITableLabelProvider {

		public DownloadDecoratingLabelProvider(ILabelProvider provider,
				ILabelDecorator decorator) {
			super(provider, decorator);
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return getImage(element);
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0) {
				return getText(element);
			} else if (columnIndex == 1) {
				if (element instanceof Device) {
					return DeploymentHelper.getMgrID((Device)element);
				}
			} else if (columnIndex == 2) {
				if (element instanceof Device) {
					return getSelectedString(element);
				}
			}
			return ""; //$NON-NLS-1$
		}
		
	}
	
	

	public DownloadSelectionTree(Composite parent, int style) {
		super(parent, style);
		
		getTree().setHeaderVisible(true);
		TreeColumn column1 = new TreeColumn(getTree(), SWT.LEFT);
		column1.setText("Selection");
		column1.setWidth(200);
		
		TreeColumn mgrIDColumn = new TreeColumn(getTree(), SWT.LEFT);
		mgrIDColumn.setText("MGR ID");
		mgrIDColumn.setWidth(150);
		
		TreeColumn propertiesColumn = new TreeColumn(getTree(), SWT.LEFT);
		propertiesColumn.setText("Properties");
		propertiesColumn.setWidth(200);
		
		setContentProvider(new ViewContentProvider());
		ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();  		
		LabelProvider lp = new ViewLabelProvider();		
		setLabelProvider(new DownloadDecoratingLabelProvider(lp ,decorator));
		
		setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(final Object element, final String property) {
				if (property.equals(DOWNLOAD_DEV_PROPERTIES) && element instanceof Device) {
					return true;
				}
				return false;
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
		
		setCellEditors(new CellEditor[] { new TextCellEditor(), new TextCellEditor(), new DialogCellEditor(getTree()) {
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						DeviceParametersDialog dialog = new DeviceParametersDialog(cellEditorWindow.getShell());
						if (((TreeSelection) getSelection()).getFirstElement() instanceof Device) {
							dialog.setDevice((Device) ((TreeSelection) getSelection()).getFirstElement());
							int ret = dialog.open();
							if (ret == Window.OK) {
								DeploymentCoordinator.getInstance().setDeviceProperties(
										dialog.getDevice(), dialog.getSelectedProperties());
								refresh(dialog.getDevice(), true);
							} 
						}
						return null;
					}

				} });

		setColumnProperties(new String[] { DOWNLOAD_DEV_SELECTION, DOWNLOAD_DEV_MGRID,
				DOWNLOAD_DEV_PROPERTIES });
	}
	
	private static String getSelectedString(Object element) {
		List<VarDeclaration> temp = DeploymentCoordinator.getInstance()
				.getSelectedDeviceProperties((Device) element);
		if (temp != null) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("["); //$NON-NLS-1$
			boolean first = true;
			for (VarDeclaration varDeclaration : temp) {
				if (first) {
					first = false;
				} else {
					buffer.append(", "); //$NON-NLS-1$
				}
				buffer.append(varDeclaration.getName());
				buffer.append("="); //$NON-NLS-1$
				buffer.append(varDeclaration.getValue() != null ? varDeclaration
						.getValue().getValue() : ""); //$NON-NLS-1$
			}
			buffer.append("]"); //$NON-NLS-1$
			return buffer.toString();
		}
		return "[]"; //$NON-NLS-1$
	}


}
