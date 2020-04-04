package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.fordiac.ide.application.actions.MapAction;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class MapToContributionItem extends ContributionItem {

	private IMenuListener mapToListener = this::createDeviceMenu;

	public MapToContributionItem(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		if (getParent() instanceof MenuManager) {
			((IMenuManager) getParent()).addMenuListener(mapToListener);
			((IMenuManager) getParent()).setRemoveAllWhenShown(true);
		}
		createDeviceMenu(((IMenuManager) getParent()));

	}

	private void createDeviceMenu(IMenuManager maptoMenu) {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();

		if (isFBorSubAppSelected(selection) && (activeEditor instanceof FBNetworkEditor)) {
			FBNetworkEditor fbEditor = (FBNetworkEditor) activeEditor;
			List<Device> devices = fbEditor.getSystem().getSystemConfiguration().getDevices();
			if (!devices.isEmpty()) {
				for (Device device : devices) {
					createDeviceMenuEntry(maptoMenu, device);
				}
			}
		}
	}

	private boolean isFBorSubAppSelected(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toArray()) {
				if ((element instanceof AbstractFBNElementEditPart)
						|| (element instanceof SubAppForFBNetworkEditPart)) {
					return true;
				}
			}
		}
		return false;
	}

	private void createDeviceMenuEntry(IMenuManager maptoMenu, Device device) {
		MenuManager deviceMenu = new MenuManager();
		deviceMenu.setMenuText(device.getName() == null ? FordiacMessages.Device : device.getName());
		deviceMenu.setImageDescriptor(FordiacImage.ICON_DEVICE.getImageDescriptor());
		maptoMenu.add(deviceMenu);
		deviceMenu.fill(((MenuManager) maptoMenu).getMenu(), -1);
		deviceMenu.setRemoveAllWhenShown(true);
		createResourceMenu(deviceMenu, device);
	}

	private void createResourceMenu(MenuManager parentMenuManager, Device device) {
		IMenuListener listener = new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				List<Resource> resources = device.getResource();
				IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				for (Resource resource : resources) {
					IAction action = new MapAction(activeEditor, resource);
					action.setText(resource.getName() == null ? FordiacMessages.Resource : resource.getName());
					action.setImageDescriptor(FordiacImage.ICON_RESOURCE.getImageDescriptor());
					parentMenuManager.add(action);
				}
			}
		};
		parentMenuManager.addMenuListener(listener);
	}

}
