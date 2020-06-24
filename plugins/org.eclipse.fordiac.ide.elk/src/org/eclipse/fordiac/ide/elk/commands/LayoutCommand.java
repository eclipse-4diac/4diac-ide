/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppInternalInterfaceEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public class LayoutCommand extends Command {

	private Map<ElkEdge, ConnectionEditPart> connEditParts = new HashMap<>();
	private Map<ElkNode, AbstractFBNElementEditPart> nodeMapping = new HashMap<>();
	private Map<AbstractFBNElementEditPart, Point> oldFBPositions = new HashMap<>();

	private ElkNode graph;

	public LayoutCommand(ElkNode graph, Map<ElkEdge, ConnectionEditPart> connEditParts,
			Map<ElkNode, AbstractFBNElementEditPart> nodeMapping) {
		this.graph = graph;
		this.connEditParts = connEditParts;
		this.nodeMapping = nodeMapping;
	}

	@Override
	public void execute() {
		for (AbstractFBNElementEditPart part : nodeMapping.values()) {
			oldFBPositions.put(part, new Point(part.getModel().getX(), part.getModel().getY()));
		}
		updateLayout(graph);
	}

	@Override
	public void redo() {
		IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.eclipse.fordiac.ide.elk.layout", null);
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
			org.eclipse.fordiac.ide.elk.Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	@Override
	public void undo() {
		for (AbstractFBNElementEditPart part : nodeMapping.values()) {
			Point p = oldFBPositions.get(part);
			part.getModel().setX(p.x);
			part.getModel().setY(p.y);
		}
		for (ConnectionEditPart conn : connEditParts.values()) {
			// set everything to 0 so that the connection gets routed
			conn.getModel().setDx1(0);
			conn.getModel().setDx2(0);
			conn.getModel().setDy(0);
		}
		IHandlerService handlerService = PlatformUI.getWorkbench().getService(IHandlerService.class);
		try {
			handlerService.executeCommand("org.eclipse.fordiac.ide.elk.resetInterfaces", null);
		} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException e) {
			org.eclipse.fordiac.ide.elk.Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private void updateLayout(ElkNode graph) {
		graph.getContainedEdges().forEach(edge -> {
			ConnectionEditPart part = connEditParts.get(edge);
			PointList pointList = ((AbstractPointListShape) part.getFigure()).getPoints();

			ElkPort startPort = ((ElkPort) edge.getSources().get(0));
			ElkPort endPort = ((ElkPort) edge.getTargets().get(0));

			pointList.removeAllPoints();
			pointList.addPoint((int) (startPort.getX() + startPort.getParent().getX()),
					(int) (startPort.getY() + startPort.getParent().getY()));
			edge.getSections().forEach(edgeSection -> {
				edgeSection.getBendPoints()
						.forEach(point -> pointList.addPoint((int) point.getX(), (int) point.getY()));
			});
			pointList.addPoint((int) (endPort.getX() + endPort.getParent().getX()),
					(int) (endPort.getY() + endPort.getParent().getY()));
			((AbstractPointListShape) part.getFigure()).setPoints(pointList);

			layoutSubAppInterfaces(part, startPort, endPort);
			updateModel(part.getModel(), pointList);
		});
		updateFBPositions(graph);
	}

	private void updateFBPositions(ElkNode graph) {
		graph.getChildren().forEach(node -> {
			AbstractFBNElementEditPart part = nodeMapping.get(node);
			if (null != part) {
				part.getModel().setX((int) node.getX());
				part.getModel().setY((int) node.getY());
			}
		});
	}

	private void layoutSubAppInterfaces(ConnectionEditPart part, ElkPort startPort, ElkPort endPort) {
		if (part.getSource() instanceof SubAppInternalInterfaceEditPart) {
			SubAppInternalInterfaceEditPart interfacePart = (SubAppInternalInterfaceEditPart) part.getSource();
			freeInterfaceFigure(interfacePart);
			IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) (startPort.getX() - fig.getBounds().preciseWidth()),
					(int) (startPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		}
		if (part.getTarget() instanceof SubAppInternalInterfaceEditPart) {
			SubAppInternalInterfaceEditPart interfacePart = (SubAppInternalInterfaceEditPart) part.getTarget();
			freeInterfaceFigure(interfacePart);
			IFigure fig = interfacePart.getFigure();
			fig.getBounds().setLocation((int) endPort.getX(),
					(int) (endPort.getY() - (fig.getBounds().preciseHeight() * 0.5)));
		}
	}

	private void freeInterfaceFigure(SubAppInternalInterfaceEditPart interfacePart) {
		if (interfacePart.getParent() instanceof UISubAppNetworkEditPart) {
			((UISubAppNetworkEditPart) interfacePart.getParent()).enableElkLayouting(interfacePart);
		}
	}

	private void updateModel(org.eclipse.fordiac.ide.model.libraryElement.Connection connModel, PointList pointList) {
		if (pointList.size() > 2) {
			// 3 segments
			connModel.setDx1((int) (pointList.getPoint(1).preciseX() - pointList.getFirstPoint().preciseX()));
			connModel.setDx2(
					(int) (pointList.getLastPoint().preciseX() - pointList.getPoint(pointList.size() - 2).preciseX()));
			if (pointList.size() > 4) {
				// 5 segments
				connModel.setDy((int) (pointList.getPoint(2).preciseY() - pointList.getFirstPoint().preciseY()));
			} else {
				connModel.setDy(0);
			}
		} else {
			// straight connection
			connModel.setDx1(0);
			connModel.setDx2(0);
			connModel.setDy(0);
		}
	}

}
