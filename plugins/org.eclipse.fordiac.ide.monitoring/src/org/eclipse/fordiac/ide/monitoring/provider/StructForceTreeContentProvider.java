package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class StructForceTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof WatchValueTreeNode) {
			final WatchValueTreeNode node = (WatchValueTreeNode) parentElement;
			return node.getChildrenAsArray();
		}
		return new Object[] {};
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			final WatchValueTreeNode node = (WatchValueTreeNode) element;
			return node.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof WatchValueTreeNode) {
			final WatchValueTreeNode node = (WatchValueTreeNode) element;
			return !node.getChildren().isEmpty();
		}
		return false;
	}

}
