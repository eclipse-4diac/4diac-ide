/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.views;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.AbstractStructTreeNode;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public class WatchValueTreeNode extends AbstractStructTreeNode {

	private String value;
	private String varName;

	private final StructuredType structType;
	private final MonitoringBaseElement monitoringBaseElement;

	private boolean isArray;

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement) {
		this.structType = null;
		this.monitoringBaseElement = monitoringBaseElement;
	}

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement, final StructuredType structType) {
		this.structType = structType;
		this.monitoringBaseElement = monitoringBaseElement;
	}

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement, final StructuredType structType,
			final String varName, final String value, final VarDeclaration variable, final WatchValueTreeNode parent) {
		this.structType = structType;
		this.monitoringBaseElement = monitoringBaseElement;
		this.varName = varName;

		if (value != null && variable != null && variable.getType() != null
				&& WatchValueTreeNodeUtils.isHexDecorationNecessary(value, variable.getType())) {
			this.value = WatchValueTreeNodeUtils.decorateHexNumber(value);
		} else {
			this.value = value;
		}
		setVariable(variable);
		setParent(parent);
		updateIsArray(value);
	}

	@Override
	public WatchValueTreeNode addChild(final EObject monitoringBaseElement) {
		if (monitoringBaseElement instanceof MonitoringBaseElement) {
			final WatchValueTreeNode node = createNode((MonitoringBaseElement) monitoringBaseElement, this);
			if (node != null) {
				getChildren().add(node);
			}
			return node;
		}
		return null;
	}

	public static WatchValueTreeNode createNode(final MonitoringBaseElement monitoringBaseElement,
			final WatchValueTreeNode root) {
		final DataType type = monitoringBaseElement.getPort().getInterfaceElement().getType();
		WatchValueTreeNode node = null;
		if (monitoringBaseElement instanceof final MonitoringElement monitoringElement) {
			if ((type instanceof StructuredType) && (type != IecTypes.GenericTypes.ANY_STRUCT)) {
				node = createStructNode(monitoringBaseElement, type, root);
			} else if (type != IecTypes.GenericTypes.ANY_STRUCT) {
				node = createVariableNode(monitoringElement);
			}
		}
		return node;
	}

	private static WatchValueTreeNode createVariableNode(final MonitoringElement monitoringElement) {
		return new WatchValueTreeNode(monitoringElement);

	}

	public static WatchValueTreeNode createStructNode(final MonitoringBaseElement monitoringBaseElement,
			final DataType type, final WatchValueTreeNode parent) {
		if (monitoringBaseElement instanceof MonitoringElement
				&& !((MonitoringElement) monitoringBaseElement).isOffline()
				&& !((MonitoringElement) monitoringBaseElement).getCurrentValue().equals("N/A")) { //$NON-NLS-1$
			return createOnlineNode(monitoringBaseElement, type, parent);
		}

		return createOfflineNode(monitoringBaseElement, type, parent);
	}

	public static WatchValueTreeNode createOfflineNode(final MonitoringBaseElement monitoringBaseElement,
			final DataType type, final WatchValueTreeNode parent) {
		final String pinName = monitoringBaseElement.getPort().getInterfaceElement().getName();
		final WatchValueTreeNode structRoot = new WatchValueTreeNode(monitoringBaseElement, (StructuredType) type, "", //$NON-NLS-1$
				pinName, null, parent);

		StructParser.buildTree((StructuredType) type, structRoot, monitoringBaseElement);
		return structRoot;
	}

	public static WatchValueTreeNode createOnlineNode(final MonitoringBaseElement monitoringBaseElement,
			final DataType type, final WatchValueTreeNode parent) {
		final String currentValue = ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		return StructParser.createStructFromString(currentValue, (StructuredType) type,
				((MonitoringElement) monitoringBaseElement), parent);
	}

	public WatchValueTreeNode addChildBase(final MonitoringBaseElement monitoringBaseElement) {

		final WatchValueTreeNode treeNode = new WatchValueTreeNode(monitoringBaseElement);

		final String portString = monitoringBaseElement.getPort().getPortString();
		treeNode.varName = portString;

		getChildren().add(treeNode);

		return treeNode;
	}

	public void addChild(final WatchValueTreeNode child) {
		getChildren().add(child);
	}

	public AbstractStructTreeNode addChild(final VarDeclaration memberVariable, final MonitoringBaseElement element,
			final StructuredType type) {

		final WatchValueTreeNode treeNode = new WatchValueTreeNode(element, type, memberVariable.getName(), null,
				memberVariable, this);
		getChildren().add(treeNode);

		return treeNode;
	}

	public MonitoringBaseElement getMonitoringBaseElement() {
		return monitoringBaseElement;
	}

	public String getWatchedElementString() {

		if (isStructLeaf() || isStructNode() && !isStructRootNode()) {

			return varName != null ? varName : "N/A"; //$NON-NLS-1$
		}

		if (monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getPortString();
		}

		return "N/A"; //$NON-NLS-1$
	}

	public String getValue() {

		if (!isStructNode() && monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		}

		if (isStructRootNode() && monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		}

		if (isStructLeaf()) {
			return value != null ? value : "N/A"; //$NON-NLS-1$
		}

		if (isStructNode()) {
			return ""; //$NON-NLS-1$
		}

		return "N/A"; //$NON-NLS-1$
	}

	public void setValue(final String value) {
		this.value = value;
		updateIsArray(value);
	}

	public boolean isStructRootNode() {
		return (structType != null) && (getParent() != null && getParent().getParent() == null);
	}

	public boolean isStructNode() {
		return (structType != null);
	}

	public boolean isStructLeaf() {
		return !hasChildren() && isStructNode();
	}

	public boolean isArray() {
		return isArray;
	}

	// high level nodes are often initialised with null as value (as to not show any value in the watch tree)
	// so we have to allow for the array flag to be manually set if needed (e.g. for hex decoration)
	public void setIsArray(final boolean isArray) {
		this.isArray = isArray;
	}

	private void updateIsArray(final String value) {
		isArray = value != null ? value.startsWith("[") : false; //$NON-NLS-1$
	}

	public String getVarName() {
		return varName;
	}

	public WatchValueTreeNode getRoot() {
		AbstractStructTreeNode node = this;
		while (node.getParent().getParent() != null) {
			node = node.getParent();
		}
		return (WatchValueTreeNode) node;
	}

	@Override
	public String toString() {
		return getWatchedElementString();
	}
}
