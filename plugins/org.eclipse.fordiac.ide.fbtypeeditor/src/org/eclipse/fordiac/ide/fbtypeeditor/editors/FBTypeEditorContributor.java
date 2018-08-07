/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public class FBTypeEditorContributor extends
		MultiPageEditorActionBarContributor {
	
	private ActionRegistry registry = new ActionRegistry();
	private List<RetargetAction> retargetActions = new ArrayList<RetargetAction>();
	private List<String> globalActionKeys = new ArrayList<String>();

	public FBTypeEditorContributor() {
		super();
	}
	
	@Override
	public void setActiveEditor(IEditorPart editor) {
		ActionRegistry registry = editor.getAdapter(ActionRegistry.class);
		if(null != registry){
			IActionBars bars = getActionBars();
			for (String id : globalActionKeys){
				bars.setGlobalActionHandler(id, registry.getAction(id));
			}
		}
		super.setActiveEditor(editor);
	}

	@Override
	public void setActivePage(IEditorPart activeEditor) {
		if(null != activeEditor){
			ActionRegistry registry = activeEditor.getAdapter(ActionRegistry.class);
			if(null != registry){
				IActionBars bars = getActionBars();
				for (String id : globalActionKeys){
					bars.setGlobalActionHandler(id, registry.getAction(id));
				}
			}
		}
	}
	
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
	}

	
	@Override
	public void contributeToToolBar(final IToolBarManager toolBarManager) {

		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));

		toolBarManager.add(new Separator());

		toolBarManager.add(new Separator());
		String[] zoomStrings = new String[] { ZoomManager.FIT_ALL,
				ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH };
		toolBarManager
				.add(new ZoomComboContributionItem(getPage(), zoomStrings));
		
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));
	}
	
	protected IAction getAction(String id) {
		return getActionRegistry().getAction(id);
	}
	
	protected ActionRegistry getActionRegistry() {
		return registry;
	}
	
	protected void addRetargetAction(RetargetAction action) {
		addAction(action);
		retargetActions.add(action);
		getPage().addPartListener(action);
		addGlobalActionKey(action.getId());
	}
	
	protected void addAction(IAction action) {
		getActionRegistry().registerAction(action);
	}
	
	protected void addGlobalActionKey(String key) {
		globalActionKeys.add(key);
	}
	
	protected void declareGlobalActionKeys() {
		this.addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
	}
	
	@Override
	public void init(IActionBars bars) {
		buildActions();
		declareGlobalActionKeys();
		super.init(bars);
	}

}
