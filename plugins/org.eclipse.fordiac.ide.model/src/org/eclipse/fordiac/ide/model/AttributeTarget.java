/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

@SuppressWarnings("squid:S115") // Constant names are used as values here
public enum AttributeTarget {
	//	 @formatter:off
	AutomationSystem(Messages.AttributeTarget_Systems_display, Messages.AttributeTarget_Systems_tooltip),
	Application(Messages.AttributeTarget_Applications_display, Messages.AttributeTarget_Applications_tooltip),
	GlobalConstant(Messages.AttributeTarget_GlobalConstants_display, Messages.AttributeTarget_GlobalConstants_tooltip),
	Connection(Messages.AttributeTarget_Connections_display, Messages.AttributeTarget_Connections_tooltip),
	Comment(Messages.AttributeTarget_Comments_display, Messages.AttributeTarget_Comments_tooltip),
	Group(Messages.AttributeTarget_Groups_display, Messages.AttributeTarget_Groups_tooltip),
	Link(Messages.AttributeTarget_Link_display, Messages.AttributeTarget_Links_tooltip),
	ServiceSequence(Messages.AttributeTarget_ServiceSequence_display, Messages.AttributeTarget_ServiceSequences_tooltip),

	FBType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_FunctionBlock_display, Messages.AttributeTarget_FBTypes_tooltip),
	SubAppType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_SubApp_display, Messages.AttributeTarget_SubAppTypes_tooltip),
	DeviceType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_Device_display, Messages.AttributeTarget_DeviceType_tooltip),
	ResourceType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_Resource_display, Messages.AttributeTarget_ResourceType_tooltip),
	SegmentType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_Segment_display, Messages.AttributeTarget_SegmentType_tooltip),
	DataType(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_DataTypes_display, Messages.AttributeTarget_DataTypes_tooltip),
	AttributeDeclaration(Messages.AttributeTarget_Types_group, Messages.AttributeTarget_AttributeTypes_display, Messages.AttributeTarget_AttributeTypes_tooltip),

	// Instances
	FB(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_FunctionBlock_display, Messages.AttributeTarget_FBInstances_tooltip),
	TypedSubApp(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_TypedSubApp_display, Messages.AttributeTarget_TypedSubApps_tooltip),
	UntypedSubApp(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_UntypedSubApp_display, Messages.AttributeTarget_UntypedSubApps_tooltip),
	Device(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_Device_display, Messages.AttributeTarget_DeviceInstances_tooltip),
	Resource(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_Resource_display, Messages.AttributeTarget_ResourceInstances_tooltip),
	Segment(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_Segment_display, Messages.AttributeTarget_SegmentInstances_tooltip),
	DataTypeMember(Messages.AttributeTarget_Instances_group, Messages.AttributeTarget_DataTypeMember_display, Messages.AttributeTarget_DataTypeMember_tooltip),

	// IInterface Instance
	InstanceEvent(Messages.AttributeTarget_InstancePins_group, Messages.AttributeTarget_Event_display, Messages.AttributeTarget_InstanceEvent_tooltip),
	InstanceVarDecl(Messages.AttributeTarget_InstancePins_group, Messages.AttributeTarget_Data_display, Messages.AttributeTarget_InstanceData_tooltip),
	InstanceAdapter(Messages.AttributeTarget_InstancePins_group, Messages.AttributeTarget_Adapter_display, Messages.AttributeTarget_InstanceAdapter_tooltip),

	// IInterface Type
	TypeEvent(Messages.AttributeTarget_TypePins_group, Messages.AttributeTarget_Event_display, Messages.AttributeTarget_TypeEvent_tooltip),
	TypeVarDecl(Messages.AttributeTarget_TypePins_group, Messages.AttributeTarget_Data_display, Messages.AttributeTarget_TypeData_tooltip),
	TypeAdapter(Messages.AttributeTarget_TypePins_group, Messages.AttributeTarget_Adapter_display, Messages.AttributeTarget_TypeAdapter_tooltip),

	// IInterface Untyped SubApp
	Untyped_SubAppEvent(Messages.AttributeTarget_UntypedSubAppPins_group, Messages.AttributeTarget_Event_display, Messages.AttributeTarget_UntypedSubAppEvent_tooltip),
	Untyped_SubAppVarDecl(Messages.AttributeTarget_UntypedSubAppPins_group, Messages.AttributeTarget_Data_display, Messages.AttributeTarget_UntypedSubAppData_tooltip),
	Untyped_SubAppAdapter(Messages.AttributeTarget_UntypedSubAppPins_group, Messages.AttributeTarget_Adapter_display, Messages.AttributeTarget_UntypedSubAppAdapter_tooltip);
//	 @formatter:on

	private final String category;
	private final String displayName;
	private final String tooltip;

	AttributeTarget(final String displayName, final String tooltip) {
		this.category = Messages.AttributeTarget_General_group;
		this.displayName = displayName;
		this.tooltip = tooltip;
	}

	AttributeTarget(final String category, final String displayName, final String tooltip) {
		this.category = category;
		this.displayName = displayName;
		this.tooltip = tooltip;
	}

	public String getCategory() {
		return category;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getToolTip() {
		return tooltip;
	}

	public static AttributeTarget fromName(final String name) {
		for (final AttributeTarget target : values()) {
			if (target.name().equals(name)) {
				return target;
			}
		}
		return null;
	}

	public static boolean checkTargetName(final String name, final String displayName, final String category) {
		for (final AttributeTarget target : AttributeTarget.values()) {
			if (target.name().equals(name) && target.getDisplayName().equals(displayName)
					&& target.getCategory().equals(category)) {
				return true;
			}
		}
		return false;
	}
}
