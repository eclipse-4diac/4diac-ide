package org.eclipse.fordiac.ide.model;

import java.util.ArrayList;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Annotation;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public enum Annotations {
	GEN;

	//*** AdapterType  ***//
	public InterfaceList getInterfaceList(AdapterType at) {
		return at.getAdapterFBType().getInterfaceList();
	}
	
	public AdapterFBType getPlugType(AdapterType adapterType) {
		AdapterFBType temp = EcoreUtil.copy(adapterType.getAdapterFBType());
		// fetch the interface to invert it; 
		ArrayList<Event> inputEvents =  new ArrayList<Event>(temp.getInterfaceList().getEventOutputs());
		for (Event event : inputEvents) {
			event.setIsInput(true);
		}
		ArrayList<Event> outputEvents =  new ArrayList<Event>(temp.getInterfaceList().getEventInputs());
		for (Event event : outputEvents) {
			event.setIsInput(false);
		}
		ArrayList<VarDeclaration> inputVars =  new ArrayList<VarDeclaration>(temp.getInterfaceList().getOutputVars());
		for (VarDeclaration varDecl : inputVars) {
			varDecl.setIsInput(true);
		}
		ArrayList<VarDeclaration> outputVars =  new ArrayList<VarDeclaration>(temp.getInterfaceList().getInputVars());
		for (VarDeclaration varDecl : outputVars) {
			varDecl.setIsInput(false);
		}	
		temp.getInterfaceList().getEventInputs().clear();
		temp.getInterfaceList().getEventOutputs().clear();
		temp.getInterfaceList().getInputVars().clear();
		temp.getInterfaceList().getOutputVars().clear();	
		temp.getInterfaceList().getEventInputs().addAll(inputEvents);
		temp.getInterfaceList().getEventOutputs().addAll(outputEvents);
		temp.getInterfaceList().getInputVars().addAll(inputVars);
		temp.getInterfaceList().getOutputVars().addAll(outputVars);	
		return temp;
	}
	
	public AdapterFBType getSocketType(AdapterType at) {
		return EcoreUtil.copy(at.getAdapterFBType());
	}

	//*** Application ***//
	public AutomationSystem getAutomationSystem(Application a) {
		return (AutomationSystem)a.eContainer();
	}
	
	//*** Connection ***//
	public FBNetworkElement getSourceElement(Connection c) {
		return (null != c.getSource()) ? c.getSource().getFBNetworkElement() : null;
	}
	
	public FBNetworkElement getDestinationElement(Connection c) {
		return (null != c.getDestination()) ? c.getDestination().getFBNetworkElement() : null;
	}
	
	public boolean isResourceConnection(Connection c) {
		//if source element is null it is a connection from a CFB interface element
		return (null != c.getSourceElement() && null != c.getSourceElement().getFbNetwork()) ? (c.getSourceElement().getFbNetwork().eContainer() instanceof Resource) : false;
	}
	
	public FBNetwork getFBNetwork(Connection c) {
		return (FBNetwork)c.eContainer();
	}

	public void checkifConnectionBroken(Connection c){
		if(!c.isResourceConnection()){
			Resource sourceRes = (null != c.getSourceElement()) ? c.getSourceElement().getResource() : null;
			Resource destinationRes = (null != c.getDestinationElement()) ? c.getDestinationElement().getResource() : null;
			c.setBrokenConnection(((null != sourceRes) && (!sourceRes.equals(destinationRes))) ||
			   ((null != destinationRes) && (!destinationRes.equals(sourceRes))));
		}
	}

	//*** Device ***//
	public AutomationSystem getAutomationSystem(Device d) {
		return d.getSystemConfiguration().getAutomationSystem();
	}
	
	public SystemConfiguration getSystemConfiguration(Device d) {
		return (SystemConfiguration)d.eContainer();
	}
		
	public Resource getResourceNamed(Device d, String name) {
		for (Resource res : d.getResource()) {			
			if (res.getName().equals(name)) {
				return res;
			}
		}
		return null;
	}
	
	//*** ECState ***//
	public boolean isStartState(ECState ecs) {
		if(null != ecs.eContainer()){
			return ecs.equals(((ECC)ecs.eContainer()).getStart());
		}
		return false;
	}
	
	//*** ECTransition ***//
	public String getConditionText(ECTransition ect) {
		String retVal = new String();
		Event event = ect.getConditionEvent();
		String expression = ect.getConditionExpression();
		if(event != null){
			retVal = event.getName();
		}
		if(expression !=  null){
			if(expression.equals("1")){ //$NON-NLS-1$
				retVal = expression;
			}else if (!expression.equals("")){ //$NON-NLS-1$
				retVal += "[" + expression + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}		
		return retVal;
	}
	
	public ECC getECC(ECTransition ecTransition) {
		return (ECC)ecTransition.eContainer();
	}

	
	//*** FB ***//
	public boolean isResourceFB(FB fb) {
		//A fB is a resource FB if the FB is in the fbnetwork of a resource and
		// the mapping is null or as preperation when we allow to map resource FBs 
		//to applications when the mapping from is equal to the fb 
		if(fb.getFbNetwork().eContainer() instanceof Resource){
			return (null == fb.getMapping()) || (fb.equals(fb.getMapping().getFrom()));
		}		
		return false;
	}
	
	public boolean isResourceTypeFB(FB fb) {
		return false;
	}
	
	//*** FBNetworkElement ***//
	public Resource getResource(FBNetworkElement fbne){
		if(null != fbne.getFbNetwork() && fbne.getFbNetwork().eContainer() instanceof Resource){
			return (Resource)fbne.getFbNetwork().eContainer();
		} else if(fbne.isMapped()){
			//get the Resource of the mapped FB
			return fbne.getMapping().getTo().getResource();
		}
		return null;
	}
	
	public IInterfaceElement getInterfaceElement(FBNetworkElement fbne, String name) {
		if (fbne.getInterface() != null) {
			return fbne.getInterface().getInterfaceElement(name);
		}
		return null;
	}
	
	public FBNetworkElement getOpposite(FBNetworkElement fbne) {
		//try to find the other coresponding mapped entity if this FBNetworkElement is mapped
		if(fbne.isMapped()){
			return (fbne == fbne.getMapping().getFrom()) ? fbne.getMapping().getTo() : fbne.getMapping().getFrom();  
		}else{
			//TODO model refactoring - if element part of subapp that is mapped recursivly find the according mapped entity 
		}
		return null;
	}
	
	public FBNetwork getFbNetwork(FBNetworkElement fbne) {
		//an FB should always be put in an fbNetwork this is at the same time also a null check
		return (fbne.eContainer() instanceof FBNetwork) ? (FBNetwork)fbne.eContainer() : null;
	}
	
	public void checkConnections(FBNetworkElement fbne) {
		for (IInterfaceElement element : fbne.getInterface().getAllInterfaceElements()) {
			//todo when lambdas are better allowed in EMF replace with .forEach(conn -> conn.checkIfConnectionBroken());
			for (org.eclipse.fordiac.ide.model.libraryElement.Connection conn : element.getInputConnections()) {
				conn.checkIfConnectionBroken();
			}
			for (org.eclipse.fordiac.ide.model.libraryElement.Connection conn : element.getOutputConnections()) {
				conn.checkIfConnectionBroken();
			}
		}
	}
	
	public boolean isMapped(FBNetworkElement fbne) {
		return null != fbne.getMapping();
	}
	
	//*** SubApp ***//
	
	//*** InterfaceList ***
	public  EList<IInterfaceElement> getAllInterfaceElements(InterfaceList il) {
		EList<IInterfaceElement> retVal = new BasicEList<IInterfaceElement>();		
		retVal.addAll(il.getEventInputs());
		retVal.addAll(il.getEventOutputs());
		retVal.addAll(il.getInputVars());
		retVal.addAll(il.getOutputVars());
		retVal.addAll(il.getPlugs());
		retVal.addAll(il.getSockets());
		return retVal;
	}
	
	public Event getEvent(InterfaceList il, String name){
		for (Event event : il.getEventInputs()) {
			if (event.getName().equals(name))
			return event;
		}
		for (Event event : il.getEventOutputs()) {
			if (event.getName().equals(name))
				return event;
		}
		return null;
	}
	
	public VarDeclaration getVariable(InterfaceList il, String name) {
		for (VarDeclaration var : il.getInputVars()) {
			if (var.getName().equals(name))
				return var;
		}
		for (VarDeclaration var : il.getOutputVars()) {
			if (var.getName().equals(name))
				return var;
		}
		return null;
	}
	
	public IInterfaceElement getInterfaceElement(InterfaceList il, String name) {
		IInterfaceElement element = il.getEvent(name);
		if (element == null) {
			element = il.getVariable(name);
		}
		if (element == null) {
			element = il.getAdapter(name);
		}
		return element;
	}
	
	public FBNetworkElement getFBNetworkElement(InterfaceList il) {
		//an FB should mostly in an FBNetworkElement otherwise it is in CFB interface this is at the same time also a null check
		return (il.eContainer() instanceof FBNetworkElement) ? (FBNetworkElement)il.eContainer() : null;
	}
	
	public AdapterDeclaration getAdapter(InterfaceList il, String name) {
		for (AdapterDeclaration adapt : il.getPlugs()) {
			if (adapt.getName().equals(name))
				return adapt;
		}
		for (AdapterDeclaration adapt : il.getSockets()) {
			if (adapt.getName().equals(name))
				return adapt;
		}
		return null;
	}
	
	//*** Mapping ***//
	public AutomationSystem getAutomationSystem(Mapping m) {
		return (null != m.eContainer()) ? (AutomationSystem)m.eContainer() : null;
	}
	
	//*** Resource ***//
	public AutomationSystem getAutomationSystem(Resource r) {
		AutomationSystem system = null; 
		if(null != r.getDevice()){ 
			system = r.getDevice().getAutomationSystem(); 
		} 
		return system;
	}
	
	//*** FBNetwork ***//
	public void addConnection(FBNetwork fbn, Connection connection) {
		if (connection instanceof EventConnection) {
			fbn.getEventConnections().add((EventConnection)connection);
		}
		if (connection instanceof DataConnection) {
			fbn.getDataConnections().add((DataConnection)connection);
		}
		if (connection instanceof AdapterConnection) {
			fbn.getAdapterConnections().add((AdapterConnection)connection);
		}
	}
	
	public void removeConnection(FBNetwork fbn, Connection connection) {
		if (connection instanceof EventConnection) {
			fbn.getEventConnections().remove(connection);
		}
		if (connection instanceof DataConnection) {
			fbn.getDataConnections().remove(connection);
		}
		if (connection instanceof AdapterConnection) {
			fbn.getAdapterConnections().remove(connection);
		}
	}
	
	public boolean isApplicationNetwork(FBNetwork fbn) {
		return fbn.eContainer() instanceof Application;
	}
	
	public boolean isSubApplicationNetwork(FBNetwork fbn) {
		return fbn.eContainer() instanceof SubApp;
	}
	
	public boolean isResourceNetwork(FBNetwork fbn) {
		return fbn.eContainer() instanceof Resource;
	}
	
	public boolean isCFBTypeNetwork(FBNetwork fbn) {
		return fbn.eContainer() instanceof CompositeFBType;
	}
	
	public AutomationSystem getAutomationSystem(FBNetwork fbn) {
		if(fbn.isApplicationNetwork() || fbn.isSubApplicationNetwork()){
			return fbn.getApplication().getAutomationSystem();
		}
		if(fbn.isResourceNetwork()){
			return ((Resource)fbn.eContainer()).getAutomationSystem();
		}else if(fbn.isCFBTypeNetwork()){
			return ((CompositeFBType)fbn.eContainer()).getPaletteEntry().getGroup().getPallete().getAutomationSystem();
		}
		return null;
	}
	
	public Application getApplication(FBNetwork fbn) {
		if(fbn.isApplicationNetwork()){
			// no null check is need as this is already done in isApplicationNetwork
			return (Application)fbn.eContainer();
		}else if(fbn.isSubApplicationNetwork() && null != ((SubApp) fbn.eContainer()).getFbNetwork()){
			return ((SubApp) fbn.eContainer()).getFbNetwork().getApplication();
		}
		return null;		
	}

	public FB getFBNamed(FBNetwork fbn, String name) {
		for (FBNetworkElement element : fbn.getNetworkElements()) {
			if((element instanceof FB) && (element.getName().equals(name))){
				return (FB)element;
			}
		}
		return null;
	}
	
	public SubApp getSubAppNamed(FBNetwork fbn, String name) {
		for (FBNetworkElement element : fbn.getNetworkElements()) {
			if(element instanceof SubApp){ 
				if(element.getName().equals(name)){
					return (SubApp)element; 
				}
				SubApp retVal = ((SubApp)element).getSubAppNetwork().getSubAppNamed(name);
				if(retVal != null){
					return retVal;
				}
			}
		}	
		return null;
	}
	
	public FBNetworkElement getElementNamed(FBNetwork fbn, String name) {
		for (FBNetworkElement element : fbn.getNetworkElements()) {
			if(element.getName().equals(name)){
				return element;
			}
		}
		return null;
	}
	
	//*** AutomationSystem ***//
	public Device getDeviceNamed(AutomationSystem as, String name) {
		if (as.getSystemConfiguration() != null) {
			return as.getSystemConfiguration().getDeviceNamed(name);
		}
		return null;
	}
	
	public Application getApplicationNamed(AutomationSystem as, String name) {
		for(Application app : as.getApplication()){
			if (app.getName().equals(name)) {
				return app;
			}
		}
		return null;
	}

	//*** VarDeclaration ***//
	public boolean isArray(VarDeclaration vd) {
		return vd.getArraySize() > 0;
	}
	
	//*** ConfigurabeleObject ***//
	public void setAttribute(ConfigurableObject object, final String attributeName, final String type, final String value, final String comment) {
		Attribute attribute = getAttribute(object, attributeName);
		if (attribute == null) {
			attribute = LibraryElementFactory.eINSTANCE.createAttribute();
			attribute.setName(attributeName);
			attribute.setValue(value);
			attribute.setType(BaseType1.getByName(type));
			attribute.setComment(comment);
			object.getAttributes().add(attribute);
		} else {
			attribute.setValue(value);
		}
	}
	
	public String getAttributeValue(ConfigurableObject object, final String attributeName) {
		Attribute a = getAttribute(object, attributeName);
		if(null != a){
			return a.getValue();
		}
		return null;
	}

	public Attribute getAttribute(ConfigurableObject object, final String attributeName) {
		if (attributeName == null) {
			return null;
		}
		for (Attribute attribute : object.getAttributes()) {
			if (attribute.getName().equalsIgnoreCase(attributeName)) {
				return attribute;
			}
		}
		return null;
	}
	
	//*** DataConnection ***//
	public VarDeclaration getDataSource(DataConnection dc) {
		return (VarDeclaration)dc.getSource();
	}
	
	public VarDeclaration getDataDestination(DataConnection dc) {
		return (VarDeclaration)dc.getDestination();
	}
	
	//*** EventConnection ***//
	public Event getEventSource(EventConnection ec) {
		return (Event)ec.getSource();
	}
	
	public Event getEventDestination(EventConnection ec) {
		return (Event)ec.getDestination();
	}
		
	//*** AdapterConnection ***//
	public AdapterDeclaration getAdapterSource(AdapterConnection ac) {
		return (AdapterDeclaration)ac.getSource();
	}
	
	public AdapterDeclaration getAdapterDestination(AdapterConnection ac) {
		return (AdapterDeclaration)ac.getDestination();
	}
	
	//*** IInterfaceElement ***//
	public FBNetworkElement getFBNetworkElement(IInterfaceElement iie) {
		return (iie.eContainer() instanceof InterfaceList) ? ((InterfaceList)iie.eContainer()).getFBNetworkElement() : null;
	}
	
	//*** Value ***//
	public VarDeclaration getVarDeclaration(Value v) {
		return (VarDeclaration)v.eContainer();
	}
	
	//*** SystemConfiguration ***//
	public AutomationSystem getAutomationSystem(SystemConfiguration sc) {
		return (AutomationSystem)sc.eContainer();
	}
	
	public Segment getSegmentNamed(SystemConfiguration sc, String name) {
		for (Segment segment : sc.getSegments()) {
			if (segment.getName().equals(name))
				return segment;
		}
		return null;
	}
	
	public Device getDeviceNamed(SystemConfiguration sc, String name) {
		for (Device device : sc.getDevices()) {
			if (device.getName().equals(name))
				return device;
		}
		return null;
	}
	
	//*** ResourceTypeFB ***//
	public boolean isResourceTypeFB() {
		return true;
	}
	
	//*** I4DIACElement ***//
	public Annotation createAnnotation(I4DIACElement i4e, String name) {
		Annotation annotation = LibraryElementFactory.eINSTANCE.createAnnotation();
		annotation.setName(name);
		i4e.getAnnotations().add(annotation);
		return annotation;
	}
	
	public void removeAnnotation(I4DIACElement i4e, Annotation a) {
		i4e.getAnnotations().remove(a);
	}
	
	//*** TypedConfigureableObject ***//
	public String getTypeName(TypedConfigureableObject tco) {
		return tco.getPaletteEntry().getLabel();
	}
	
	public LibraryElement getType(TypedConfigureableObject tco) {
		if(null != tco.getPaletteEntry()){
			return tco.getPaletteEntry().getType();
		}		
		return null;
	}
	
	//*** AdapterFB ***//
	public boolean isSocket(AdapterFB afb) {
		return !afb.isPlug();
	}
	
	public FBType getType(AdapterFB afb) {
		FBType retVal = null;
		if ((null != afb.getPaletteEntry()) && (afb.getPaletteEntry() instanceof AdapterTypePaletteEntry)  &&
				(null != afb.getAdapterDecl())){
			if (afb.isPlug()) {
				retVal = ((AdapterTypePaletteEntry) afb.getPaletteEntry()).getAdapterType().getPlugType();
			} else {
				retVal = ((AdapterTypePaletteEntry) afb.getPaletteEntry()).getAdapterType().getSocketType();
			}
		}
		return retVal;
	}
	
	public boolean isPlug(AdapterFB afb) {
		return !afb.getAdapterDecl().isIsInput();
	}
	
	//return org.eclipse.fordiac.ide.model.Annotations.GEN.isPlug(this);
}
