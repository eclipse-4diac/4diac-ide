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

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.StructTreeNode;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;

public class WatchValueTreeNode extends StructTreeNode {

	private String value;
	private String varName;

	private final StructuredType structType;
	private final MonitoringBaseElement monitoringBaseElement;

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement) {
		super();
		this.structType = null;
		this.monitoringBaseElement = monitoringBaseElement;
	}

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement, final StructuredType structType) {
		super();
		this.structType = structType;
		this.monitoringBaseElement = monitoringBaseElement;
	}

	public WatchValueTreeNode(final MonitoringBaseElement monitoringBaseElement, final StructuredType structType,
			final String varName, final String value, final VarDeclaration variable, final WatchValueTreeNode parent) {
		super();
		this.structType = structType;
		this.monitoringBaseElement = monitoringBaseElement;
		this.varName = varName;
		this.value = value;
		this.variable = variable;
		this.parent = parent;
		this.root = parent.root;
	}


	public WatchValueTreeNode addChild(final MonitoringBaseElement monitoringBaseElement) {

		final DataType type = monitoringBaseElement.getPort().getInterfaceElement().getType();
		WatchValueTreeNode node = null;
		if (monitoringBaseElement instanceof MonitoringElement) {
			final MonitoringElement monitoringElement = ((MonitoringElement) monitoringBaseElement);
			if ((type instanceof StructuredType) && (type != IecTypes.GenericTypes.ANY_STRUCT)) {

				node = createStructNode(monitoringBaseElement, type, this);
			} else if (type != IecTypes.GenericTypes.ANY_STRUCT) {
				node = createVariableNode(monitoringElement);
			}

			if (node != null) {
				this.children.add(node);
			}

		}

		return null;
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
		final String pinName = ((MonitoringElement) monitoringBaseElement).getPort().getInterfaceElement().getName();
		final WatchValueTreeNode structRoot = new WatchValueTreeNode(monitoringBaseElement, (StructuredType) type, "",
				pinName, null,
				parent);

		StructParser.buildTree((StructuredType) type, structRoot, monitoringBaseElement);
		return structRoot;
	}

	public static WatchValueTreeNode createOnlineNode(final MonitoringBaseElement monitoringBaseElement,
			final DataType type, final WatchValueTreeNode parent) {
		final String currentValue = ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		final StructParser parser = new StructParser();
		return parser.createStructFromString(currentValue, (StructuredType) type,
				((MonitoringElement) monitoringBaseElement), parent);
	}

	public WatchValueTreeNode addChildBase(final MonitoringBaseElement monitoringBaseElement) {

		final WatchValueTreeNode treeNode = new WatchValueTreeNode(monitoringBaseElement);

		final String portString = monitoringBaseElement.getPort().getPortString();
		treeNode.varName = portString;

		this.children.add(treeNode);

		return treeNode;
	}

	public void addChild(final WatchValueTreeNode child) {
		this.children.add(child);
	}


	public StructTreeNode addChild(final VarDeclaration memberVariable, final MonitoringBaseElement element,
			final StructuredType type) {

		final WatchValueTreeNode treeNode = new WatchValueTreeNode(element, type,
				memberVariable.getName(), null, memberVariable, this);
		this.children.add(treeNode);

		return treeNode;
	}

	public MonitoringBaseElement getMonitoringBaseElement() {
		return monitoringBaseElement;
	}

	public String getWatchedElementString() {

		if (isStructLeaf() || isStructNode() && !isStructRootNode()) {

			return varName != null ? varName : "N/A";
		}

		if (monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getPortString();
		}

		return "N/A";
	}

	public String getValue() {


		if (!isStructNode() && monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		}


		if (isStructRootNode() && monitoringBaseElement instanceof MonitoringElement) {
			return ((MonitoringElement) monitoringBaseElement).getCurrentValue();
		}

		if (isStructLeaf()) {
			return value != null ? value : "N/A";
		}

		if (isStructNode()) {
			return "";
		}



		return "N/A";
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public boolean isStructRootNode() {
		return (structType != null) && (parent == root);
	}

	public boolean isStructNode() {
		return (structType != null);
	}

	public boolean isStructLeaf() {
		return !hasChildren() && isStructNode();
	}

}
