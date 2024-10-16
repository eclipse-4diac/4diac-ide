package org.eclipse.fordiac.ide.export.forte_ng.st;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.forte_ng.util.IHasher;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;

public final class LibraryElementHasher implements IHasher {
	String DEFAULT_HASH_ALG = "SHA3-512"; //$NON-NLS-1$
	String HASH_VERSION = "v1"; //$NON-NLS-1$ // increment this whenever anything is changed in the hashing
								// algorithm
	String FULL_HASH_STRING = HASH_VERSION + ":" + DEFAULT_HASH_ALG; //$NON-NLS-1$

	@Override
	public String getHashType() {
		return FULL_HASH_STRING;
	}

	private void process(final InterfaceList il, final List<String> parts, final String version) {
		parts.add("IINTERFACES{"); //$NON-NLS-1$
		if (il.getEventInputs().size() > 0) {
			parts.add("INPUTEVENTS{"); //$NON-NLS-1$
			for (final Event event : il.getEventInputs()) {
				parts.add(String.format("%s:%s", event.getName(), event.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}INPUTEVENTS"); //$NON-NLS-1$
		}
		if (il.getEventOutputs().size() > 0) {
			parts.add("OUTPUTEVENTS{"); //$NON-NLS-1$
			for (final Event event : il.getEventOutputs()) {
				parts.add(String.format("%s:%s", event.getName(), event.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}OUTPUTEVENTS"); //$NON-NLS-1$
		}
		if (il.getVisibleInputVars().size() > 0) {
			parts.add("DATAINPUTS{"); //$NON-NLS-1$
			for (final VarDeclaration vdec : il.getVisibleInputVars()) { // TODO VisibleInputVars == DataInputs?
				parts.add(String.format("%s:%s", vdec.getName(), vdec.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}DATAINPUTS"); //$NON-NLS-1$
		}
		if (il.getVisibleOutputVars().size() > 0) {
			parts.add("DATAOUTPUTS{"); //$NON-NLS-1$
			for (final VarDeclaration vdec : il.getVisibleOutputVars()) { // TODO getVisibleOutputVars == DataOutputs?
				parts.add(String.format("%s:%s", vdec.getName(), vdec.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}DATAOUTPUTS"); //$NON-NLS-1$
		}
		if (il.getInOutVars().size() > 0) {
			parts.add("INOUTVARS{"); //$NON-NLS-1$
			for (final VarDeclaration vdec : il.getInOutVars()) {
				parts.add(String.format("%s:%s", vdec.getName(), vdec.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}INOUTVARS"); //$NON-NLS-1$
		}
		if (il.getPlugs().size() > 0) {
			parts.add("PLUGS{"); //$NON-NLS-1$
			for (final AdapterDeclaration adec : il.getPlugs()) {
				parts.add(String.format("%s:%s", adec.getName(), adec.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}PLUGS"); //$NON-NLS-1$
		}
		if (il.getSockets().size() > 0) {
			parts.add("SOCKETS{"); //$NON-NLS-1$
			for (final AdapterDeclaration adec : il.getSockets()) {
				parts.add(String.format("%s:%s", adec.getName(), adec.getTypeName())); //$NON-NLS-1$
			}
			parts.add("}SOCKETS"); //$NON-NLS-1$
		}
		parts.add("}IINTERFACES"); //$NON-NLS-1$
	}

	private void process(final FBType fbtype, final List<String> parts, final String version) {
		process(fbtype.getInterfaceList(), parts, version);
		if (fbtype instanceof final BaseFBType bfbtype) {
			process(bfbtype, parts, version);
		}
		if (fbtype instanceof final BasicFBType bfbtype) {
			process(bfbtype, parts, version);
		}
		if (fbtype instanceof final CompositeFBType cfbtype) {
			process(cfbtype, parts, version);
		}
		if (fbtype instanceof final FunctionFBType ffbtype) {
			final FunctionBody fbtbody = ffbtype.getBody();
			process(fbtbody, parts, version);
		}
		if (fbtype instanceof final ServiceInterfaceFBType sifbtype) {
			// nothing new

		}
		if (fbtype instanceof final SimpleFBType sfbtype) {
			// nothing new
		}
		if (fbtype instanceof final SubAppType satype) {
			// nothing new
		}

	}

	private void process(final BaseFBType bfbtype, final List<String> parts, final String version) {
		for (final VarDeclaration ivar : bfbtype.getInternalVars()) {
			process(ivar, parts, version);
		}
		for (final VarDeclaration icvar : bfbtype.getInternalConstVars()) {
			process(icvar, parts, version);
		}
		for (final FB ifb : bfbtype.getInternalFbs()) {
			process(ifb, parts, version);
		}

		for (final Method method : bfbtype.getMethods()) {
			parts.add(String.format("METHOD:%s:%s", method.getQualifiedName(), hash(method, version))); //$NON-NLS-1$
		}
		for (final Algorithm algo : bfbtype.getAlgorithm()) {
			parts.add(String.format("ALGORITHM:%s:%s", algo.getQualifiedName(), hash(algo, version))); //$NON-NLS-1$
		}
	}

	private void process(final BasicFBType bfbtype, final List<String> parts, final String version) {
		final ECC ecc = bfbtype.getECC();
		process(ecc, parts, version);
	}

	private void process(final CompositeFBType cfbtype, final List<String> parts, final String version) {
		process(cfbtype.getFBNetwork(), parts, version);
	}

	private void process(final FBNetwork fbnetwork, final List<String> parts, final String version) {
		parts.add("NETWORK{"); //$NON-NLS-1$
		for (final FBNetworkElement fbnetele : fbnetwork.getNetworkElements()) {
			process(fbnetele, parts, version);
		}
		parts.add("CONNECTIONS{"); //$NON-NLS-1$
		for (final DataConnection datconn : fbnetwork.getDataConnections()) {
			process(datconn, parts, version);
		}
		for (final EventConnection evconn : fbnetwork.getEventConnections()) {
			process(evconn, parts, version);
		}
		for (final AdapterConnection adconn : fbnetwork.getAdapterConnections()) {
			process(adconn, parts, version);
		}
		parts.add("}CONNECTIONS"); //$NON-NLS-1$
		parts.add("}NETWORK"); //$NON-NLS-1$
	}

	private void process(final Algorithm alg, final List<String> parts, final String version) {
		parts.add(String.format("ALGORITHM:%s{", alg.getQualifiedName())); //$NON-NLS-1$
		if (alg instanceof final STAlgorithm stalg) {
			process(stalg, parts, version);
		} else if (alg instanceof final OtherAlgorithm oalg) {
			process(oalg, parts, version);
		}
		parts.add("}ALGORITHM"); //$NON-NLS-1$
	}

	private void process(final STAlgorithm stalg, final List<String> parts, final String version) {

		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();
		final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm parsed = StructuredTextParseUtil
				.parse(stalg, errors, warnings, infos);

		final StructuredTextSupport stsupp = new StructuredTextSupport() {

			@Override
			public Set<INamedElement> getDependencies(final Map<?, ?> options) {
				return null;
			}

			@Override
			public CharSequence generate(final Map<?, ?> options) throws ExportException {
				return null;
			}

			@Override
			public boolean prepare() {
				return false;
			}
		};
		if (stalg.getInputParameters().size() > 0) {
			parts.add("INPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stalg.getInputParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}INPARAMS"); //$NON-NLS-1$
		}

		if (stalg.getOutputParameters().size() > 0) {
			parts.add("OUTPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stalg.getOutputParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}OUTPARAMS"); //$NON-NLS-1$
		}

		if (stalg.getInOutParameters().size() > 0) {
			parts.add("INOUTPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stalg.getInOutParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}INOUTPARAMS"); //$NON-NLS-1$
		}

		if (stalg.getReturnType() != null) {
			parts.add("RETURN{"); //$NON-NLS-1$
			process(stalg.getReturnType(), parts, version);
			parts.add("}RETURN"); //$NON-NLS-1$
		}
		parts.add("VARS{"); //$NON-NLS-1$
		parts.add(stsupp.generateVariables(parsed.getBody().getVarTempDeclarations(), true).toString());
		parts.add("}VARS"); //$NON-NLS-1$
		parts.add("BODY{"); //$NON-NLS-1$
		parts.add(stsupp.generateStatementList(parsed.getBody().getStatements()).toString());
		parts.add("}BODY"); //$NON-NLS-1$

	}

	private void process(final OtherAlgorithm oalg, final List<String> parts, final String version) {
		parts.add(String.format("%s:={%s}", oalg.getLanguage(), oalg.getText())); //$NON-NLS-1$
	}

	private void process(final Method metd, final List<String> parts, final String version) {
		parts.add(String.format("METHOD:%s{", metd.getQualifiedName())); //$NON-NLS-1$
		if (metd instanceof final STMethod stmetd) {
			process(stmetd, parts, version);
		}
		if (metd instanceof final OtherMethod ometd) {
			process(ometd, parts, version);
		}
		parts.add("}METHOD"); //$NON-NLS-1$
	}

	private void process(final STMethod stmetd, final List<String> parts, final String version) {
		final List<String> errors = new ArrayList<>();
		final List<String> warnings = new ArrayList<>();
		final List<String> infos = new ArrayList<>();
		final org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod parsed = StructuredTextParseUtil
				.parse(stmetd, errors, warnings, infos);

		final StructuredTextSupport stsupp = new StructuredTextSupport() {

			@Override
			public Set<INamedElement> getDependencies(final Map<?, ?> options) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CharSequence generate(final Map<?, ?> options) throws ExportException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean prepare() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		if (stmetd.getInputParameters().size() > 0) {
			parts.add("INPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stmetd.getInputParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}INPARAMS"); //$NON-NLS-1$
		}

		if (stmetd.getOutputParameters().size() > 0) {
			parts.add("OUTPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stmetd.getOutputParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}OUTPARAMS"); //$NON-NLS-1$
		}

		if (stmetd.getInOutParameters().size() > 0) {
			parts.add("INOUTPARAMS{"); //$NON-NLS-1$
			for (final INamedElement inamed : stmetd.getInOutParameters()) {
				parts.add(String.format("%s:%s", inamed.getQualifiedName(), inamed.toString())); //$NON-NLS-1$
			}
			parts.add("}INOUTPARAMS"); //$NON-NLS-1$
		}

		if (stmetd.getReturnType() != null) {
			parts.add("RETURN{"); //$NON-NLS-1$
			process(stmetd.getReturnType(), parts, version);
			parts.add("}RETURN"); //$NON-NLS-1$
		}
		parts.add("VARS{"); //$NON-NLS-1$
		parts.add(stsupp.generateVariables(parsed.getBody().getVarDeclarations(), true).toString());
		parts.add("}VARS"); //$NON-NLS-1$
		parts.add("BODY{"); //$NON-NLS-1$
		parts.add(stsupp.generateStatementList(parsed.getBody().getStatements()).toString());
		parts.add("}BODY"); //$NON-NLS-1$
	}

	private void process(final OtherMethod ometd, final List<String> parts, final String version) {
		parts.add(String.format("%s:={%s}", ometd.getLanguage(), ometd.getText())); //$NON-NLS-1$
	}

	private void process(final DataType dt, final List<String> parts, final String version) {
		parts.add(String.format("%s:%s", dt.getQualifiedName(), dt.toString())); //$NON-NLS-1$
	}

	private void process(final AdapterDeclaration adec, final List<String> parts, final String version) { // TODO add
																											// FullTypeName
		if (adec.getAdapterFB() != null) {
			parts.add(String.format("ADEC:%s", adec.getAdapterFB().getQualifiedName())); //$NON-NLS-1$
		}
	}

	private void process(final ErrorMarkerInterface emiface, final List<String> parts, final String version) {
		if (emiface.getValue() != null) {
			parts.add(String.format("EMIFACE:%s", emiface.getValue().getValue())); //$NON-NLS-1$
		}
	}

	private void process(final Event event, final List<String> parts, final String version) { // TODO add width
		parts.add(String.format("EVENT:%s", event.getQualifiedName())); //$NON-NLS-1$
	}

	private void process(final VarDeclaration vdec, final List<String> parts, final String version) {
		// TODO handle LocalVariable or MemberVarDeclaration
		parts.add(String.format("VDEC:%s:%s", vdec.getName(), //$NON-NLS-1$
				vdec.getValue() == null ? "null" : vdec.getValue().getValue())); //$NON-NLS-1$
	}

	private void process(final DataConnection dconn, final List<String> parts, final String version) {
		parts.add(String.format("%s--DataConn-->%s", dconn.getSource().getQualifiedName(), //$NON-NLS-1$
				dconn.getDestination().getQualifiedName()));
	}

	private void process(final EventConnection econn, final List<String> parts, final String version) {
		parts.add(String.format("%s--EventConn-->%s", econn.getSource().getQualifiedName(), //$NON-NLS-1$
				econn.getDestination().getQualifiedName()));
	}

	private void process(final AdapterConnection aconn, final List<String> parts, final String version) {
		parts.add(String.format("%s--AdapterConn-->%s", aconn.getSource().getQualifiedName(), //$NON-NLS-1$
				aconn.getDestination().getQualifiedName()));
	}

	private void process(final ECC ecc, final List<String> parts, final String version) {
		parts.add(String.format("ECC{")); //$NON-NLS-1$
		parts.add(String.format("ECStates{")); //$NON-NLS-1$
		for (final ECState ecs : ecc.getECState()) {
			parts.add(ecs.getName());
		}
		parts.add(String.format("}ECStates")); //$NON-NLS-1$
		parts.add(String.format("ECTransitions{")); //$NON-NLS-1$
		for (final ECTransition ect : ecc.getECTransition()) {
			parts.add(String.format("[%s--%s-->%s]", ect.getSource().getName(), ect.getConditionText(), //$NON-NLS-1$
					ect.getDestination().getName()));
		}
		parts.add(String.format("}ECTransitions")); //$NON-NLS-1$
		parts.add(String.format("Start:%s", ecc.getStart().getName())); //$NON-NLS-1$
		parts.add(String.format("}ECC")); //$NON-NLS-1$
	}

	private void process(final FunctionBody fbody, final List<String> parts, final String version) {
		// does nothing????
	}

	private void process(final FBNetworkElement fbnetelem, final List<String> parts, final String version) {
		if (fbnetelem instanceof final ErrorMarkerFBNElement emfbnetelem) {
			parts.add(String.format("EM:%s:%s", emfbnetelem.getQualifiedName(), hash(emfbnetelem.getType(), version))); //$NON-NLS-1$

		}
		if (fbnetelem instanceof final Group group) {
			process(group, parts, version);
		}
		if (fbnetelem instanceof final FB fb) {
			process(fb, parts, version);
		}
		if (fbnetelem instanceof final SubApp subapp) {
			process(subapp, parts, version);
		}
	}

	private void process(final Group group, final List<String> parts, final String version) {
		// TODO validate Group
		parts.add(String.format("GROUP:%s{", group.getName())); //$NON-NLS-1$
		process(group.getInterface(), parts, version);
		for (final FBNetworkElement fbnetelem : group.getGroupElements()) {
			process(fbnetelem, parts, version);
		}
		parts.add("}GROUP"); //$NON-NLS-1$
	}

	private void process(final SubApp subapp, final List<String> parts, final String version) {
		// TODO validate SubApp
		if (subapp.isTyped()) {
			parts.add(String.format("TYPED_SUBAPP:%s:%s", subapp.getName(), subapp.getType().getName())); //$NON-NLS-1$
		} else {
			parts.add(String.format("SUBAPP:%s{", subapp.getName())); //$NON-NLS-1$
			process(subapp.getSubAppNetwork(), parts, version);
			parts.add("}SUBAPP"); //$NON-NLS-1$
		}
	}

	private void process(final FB fb, final List<String> parts, final String version) {
		parts.add(String.format("FB:%s:%s", fb.getName(), fb.getType().getQualifiedName())); //$NON-NLS-1$
		if (fb.getType().getInterfaceList().getAllInterfaceElements().size() > 0) {
			parts.add("OVERRIDES{"); //$NON-NLS-1$
			final EList<IInterfaceElement> orig = fb.getType().getInterfaceList().getAllInterfaceElements();
			for (final IInterfaceElement ie : fb.getInterface().getAllInterfaceElements()) {
				if (ie instanceof final VarDeclaration vdec) {
					final Value value = vdec.getValue();
					if (value != null && !value.getValue().isBlank()) {
						parts.add(String.format("%s:=%s", ie.getName(), //$NON-NLS-1$
								value.getValue()));
					}
				}

			}
			parts.add("}OVERRIDES"); //$NON-NLS-1$
		}
	}

	public List<String> constructHashableList(final EObject eobj, final String version) {
		final List<String> parts = new ArrayList<>();

		if (eobj instanceof final FBType type) {
			process(type, parts, version);
		}

		if (eobj instanceof final Algorithm alg) {
			process(alg, parts, version);
		}

		if (eobj instanceof final Method method) {
			process(method, parts, version);
		}

		return parts;
	}

	@Override
	public String getLibraryElementHash(final LibraryElement libelem, final String version) {
		final String[] verstion_parts = version.split(":"); //$NON-NLS-1$
		if (verstion_parts.length != 2) {
			return ""; // TODO raise exception maybe //$NON-NLS-1$
		}
		if (!verstion_parts[0].equals(HASH_VERSION)) {
			return ""; // TODO raise exception maybe //$NON-NLS-1$
		}

		return hash(libelem, verstion_parts[1]);
	}

	private String hash(final EObject eobj, final String version) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(version);
		} catch (final NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ""; //$NON-NLS-1$
		}

		final String hashable = String.join(";", constructHashableList(eobj, version)); //$NON-NLS-1$
		final byte[] bytes = digest.digest(hashable.getBytes(StandardCharsets.UTF_8));
		final StringBuilder sb = new StringBuilder();
		for (final byte b : bytes) {
			sb.append(String.format("%02x", b)); //$NON-NLS-1$
		}
		return hashable.replaceAll(";", "\n").replaceAll("\n", "\\\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
																		// TODO change to sb.toString() in prod
	}

	@Override
	public String getLibraryElementHash(final LibraryElement libelem) {
		return hash(libelem, DEFAULT_HASH_ALG);
	}
}