/*******************************************************************************
 * Copyright (c) 2024 AIMIRIM STI - https://en.aimirimsti.com.br/
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo
 *   Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao.helpers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.w3c.dom.Document;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketListener;

import aimirim.fordiac.ide.deployment.uao.Messages;

public class UAOClient {
	
	private WebSocket ws;
	private String endpoint;
	private SecureRandom rand = new SecureRandom();
	private ECPrivateKeyParameters privKey;
	private ECPublicKeyParameters pubKey;
	private ECNamedCurveParameterSpec curveSpec;
	private ECDomainParameters curveParams;
	private byte[] sharedSecret;
	private byte[] ticket;
	private int session_id;
	private int reqid = 1;
	private int msgnr = 0;
	private final LinkedList<JsonObject> msgReceived = new LinkedList<>();
	
    /** Initialize UAOClient class
	 * @param endpoint Server endpoint address.
     * @param timeoutms Connection timeout.
     * @param useSsl True to use SSL on connection.
     * @throws DeploymentException Failed to instantiate websocket client.*/
	public UAOClient(String uri, int timeoutms, boolean useSsl) throws DeploymentException {
		this.endpoint = uri;
		String wsEndpoint = String.format("ws://%s",endpoint);
		
		this.ws = setWebsocket(wsEndpoint,timeoutms);
		setEllipticCurve();
		regenKeyPair();
	}
	
    /** Add more callbacks to the connection.
     * @param listener method overwrite implemented as described in
     * `WebSocketListener` interface.*/
	public void addListener(WebSocketListener listener) {
		ws.addListener(listener);
	}

    /** Blocking message receive.
     * @param timeout Timeout (milliseconds) for the wait on server message.
     * @return Message received.
     * @throws InterruptedException Thread interrupted while still waiting.
     * @throws TimeoutException Timeout waiting for the answer reached.*/
	public JsonObject receive(long timeout) throws InterruptedException, TimeoutException {
		long start = System.currentTimeMillis();
		long elapsed = 0;
        while (msgReceived.isEmpty() & elapsed<timeout ) {
        	elapsed = System.currentTimeMillis()-start;
        	TimeUnit.MILLISECONDS.sleep(50);
        }
    	if(elapsed>timeout) {
    		throw new TimeoutException("Websocket receive Timeout reached");
    	}
        return(msgReceived.pop());
	}
	
    /** Client connect action.*/
	public void connect() throws WebSocketException {
		ws.connect();
	}
	
    /** Client disconnect action.*/
	public void disconnect() {
		ws.disconnect();
	}
	
    /** Send json object to the server.
     * @param payload The message to send*/
	public void send(JsonObject payload) {
		ws.sendText(payload.toString());
	}
	
	/** Send json object to the server and wait for the response with
	 * a default timeout of 2000ms.
	 * @param payload The message to send.
	 * @return The server response.
	 * @throws DeploymentException Different messages according to the context.*/
	public JsonObject sendAndWaitResponse(JsonObject payload) throws DeploymentException {
		return(sendAndWaitResponse(payload,2000));
	}
	
	/** Send json object to the server and wait for the response.
	 * @param json The message to send.
	 * @param timeout Max time to wait the server for a response in ms.
	 * @return The server response.
	 * @throws DeploymentException Different messages according to the context.*/
	public JsonObject sendAndWaitResponse(JsonObject payload, int timeout) throws DeploymentException {
		ws.sendText(payload.toString());
		incrementCounters();
		try {
			JsonObject response = receive(timeout);
			return(response);
		} catch (InterruptedException e) {
			throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestInterrupted, e.getMessage()));
		} catch (TimeoutException e) {
			throw new DeploymentException(Messages.UAODeploymentExecutor_ClientRequestTimeout);
		}
		
	}
	
    /** Check if connection is open.*/
	public boolean isOpen() {
		return(ws.isOpen());
	}

	/** Send a status request and check if the response is OK.
	 * @return True if received a good response from the runtime
	 * @throws DeploymentException Server did not respond the requests in time.*/
	public boolean connectionCheck() throws DeploymentException {
		JsonObject payload = getMessageBody("stat");
		JsonObject response = sendAndWaitResponse(payload);
		return(checkResponse(response));
	}
	
	/** Perform an authentication with the runtime.
	 * @throws DeploymentException Server did not respond the requests in time.*/
	public boolean authenticate() throws DeploymentException {
		boolean check = false;
		JsonObject result_keyxchg = cmd_keyxchg();
		parseError(result_keyxchg);
		if (checkResponse(result_keyxchg)) {
			JsonElement pubkobj = result_keyxchg.get("pubkey");
			if (pubkobj!= null) {
				String runtimekey = pubkobj.getAsString();
				buildSharedKey( pubkey_decode(runtimekey) );
			}
			
			JsonObject result_auth = cmd_auth();
			parseError(result_auth);
			check = checkResponse(result_auth);
			if (check) {
				JsonElement ticketobj = result_auth.get("ticket");
		        if (ticketobj!= null) {
		        	byte[] ivbytes =  decode(result_auth.get("authnonce").getAsString());
		        	ticket = decrypt(ivbytes, decode(ticketobj.getAsString()) );
		        	session_id = result_auth.get("sessid").getAsInt();
		        }
			}
		}
		return(check);
	}
	
	/** Send a flow controlling command to runtime.
	 * i.e. "start", "stop", "clean". 
	 * @param cmd Command name.*/
	public void flow_command(String cmd) throws DeploymentException {
		byte[] iv = change_role("deploy");
		if(iv!=null) {
			JsonObject response = cmd_transition(cmd);
			cmd_relrole();
			parseError(response);
		}
	}
	
	/** Send a restart command */
	public void restart() throws DeploymentException {
		byte[] iv = change_role("deploy");
		if(iv!=null) {
			JsonObject payload = getMessageBody("restart");
			payload.addProperty("reboot",false);
			JsonObject response = sendAndWaitResponse(payload);
			if (!checkResponse(response)) {
				// Rebooted Device won't answer if succeeded.
				cmd_relrole();	
			}
			parseError(response);
		}
	}

	/** Generate a private-public key pair.*/
	public void regenKeyPair() {
	    ECKeyGenerationParameters keyParams = new ECKeyGenerationParameters(curveParams, rand);

	    ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
	    keyGen.init(keyParams);
	    AsymmetricCipherKeyPair keyPair = keyGen.generateKeyPair();
	    this.privKey = (ECPrivateKeyParameters)keyPair.getPrivate();
	    this.pubKey = (ECPublicKeyParameters)keyPair.getPublic();
	}

	/** Perform a deploy operation.
	 * @param doc XML Document.
	 * @param projId Project UUID.
	 * @param snapId Snapshot UIID.
	 * @throws DeploymentException Operation failed.*/
	public void deploy(Document doc, String projId, String snapId) throws DeploymentException {
		Map<String,byte[]> deployList = new TreeMap<String,byte[]>();
		
		MessageDigest flistHash = null;
		try {flistHash = MessageDigest.getInstance("SHA-256");}
		catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		
		// TODO: Loop in all files to send. Currently only the Sys project is sent.
		UAOBinFile binProj = new UAOBinFile(doc); 
		byte[] binProjBytes = binProj.parseToBin();
		
		flistHash.update(hash_sha256(binProjBytes));
		deployList.put("Device.bin",binProjBytes);
		// --- End Loop
		
		String flistHashHex = Hex.encodeHexString(flistHash.digest());
		
		String template = "{\"snapshot\": {\"guid\": \"\",\"hash\": \"\",\"app\": {\"hash\": \"\",\"items\": [\"Device.bin\"]}}}";
		JsonObject filelist = JsonParser.parseString(template).getAsJsonObject();
		JsonObject snp = filelist.get("snapshot").getAsJsonObject();
		snp.addProperty("guid", snapId);
		snp.addProperty("hash", flistHashHex);
		snp.get("app").getAsJsonObject().addProperty("hash", flistHashHex);
		
		deployList.put("FileList.json",filelist.toString().getBytes());
		
		boolean uploadOk = false;
		try { 
			List<HttpResponse> responseList = sendFiles(deployList,snapId);
			uploadOk = checkHttpResponses(responseList);
			}
		catch (DeploymentException | IOException e) {e.printStackTrace();}
		
		if (uploadOk) {
			cmd_deploy(projId, snapId);
			flow_command("start");
		}
	}
	
	/** Search 'stat' response for current device state.
	 * @return Runtime state name */
	public String getDeviceState() throws DeploymentException {
		byte[] iv = change_role("deploy");
		String state = null;
		
		if(iv!=null) {
			JsonObject response = sendAndWaitResponse(getMessageBody("stat"));
			JsonElement stateobj = response.get("device_state");
			if (stateobj!=null) {
				state = stateobj.getAsString();
			}
			cmd_relrole();
			parseError(response);	
		}
		return(state);
	}

	/** Access runtime response and parse the error as an exception.
	 * @param response Runtime response.*/
	private void parseError(JsonObject response) throws DeploymentException {
		if (!checkResponse(response)) {
			int status = response.get("result").getAsInt();
			String reason = response.get("error").getAsJsonObject().get("desc").getAsString();
			throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestRejected,status,reason));
		}
	}
	
	/** Create the websocket client.
	 * @param endpoint Server endpoint address.
     * @param timeoutms Connection timeout.
     * @throws DeploymentException Failed to instantiate websocket client.*/
	private WebSocket setWebsocket(String endpoint, int timeoutms) throws DeploymentException {
		WebSocket websock = null;
		try {
			websock = new WebSocketFactory().setConnectionTimeout(timeoutms).createSocket(endpoint);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_CreateClientFailed, e.getMessage()));
		}
		
		websock.addListener(new WebSocketAdapter() {
			public void onTextMessage(WebSocket websocket, String text) {
				msgReceived.add((JsonObject) JsonParser.parseString(text));
			}
			public void onError(WebSocket websocket, WebSocketException cause) {
				FordiacLogHelper.logError("UAOClient | Error:" + cause.getMessage()); //$NON-NLS-1$
			}
		});
		return(websock);
	}

	/** Set the Elliptic Curve configurations.*/
	private void setEllipticCurve() {
	    this.curveSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
	    this.curveParams = new ECDomainParameters(curveSpec.getCurve(), curveSpec.getG(), curveSpec.getN(), curveSpec.getH(), curveSpec.getSeed());
	}
	
	
	/** Build message body.
	 * @param operation The operation mode to ask the runtime.*/
	private JsonObject getMessageBody(final String operation) {
		JsonObject payload = new JsonObject();
		payload.addProperty("op",operation);
		payload.addProperty("msgnr",msgnr);
		payload.addProperty("reqid",reqid);
		return(payload);
	}
	
	/** Check for response status.
	 * @param response Server response.
	 * @return True if the response has status 200.*/
	private boolean checkResponse(JsonObject response) {
		boolean isok=false;
		JsonElement resobj = response.get("result");
		if (resobj!=null) {
			isok = resobj.getAsInt()==200;
		}
		return(isok);
	}
	
	/** Get the encryption algorithm
	 * @param mode Can be `Cipher.ENCRYPT_MODE` and `Cipher.DECRYPT_MODE`
	 * @param iv Random number.
	 * @return Encryption algorithm*/
	private Cipher getCipher(int mode, byte[] iv) {
		Cipher cipher = null;
		try {cipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");} 
		catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {e.printStackTrace();} // Dummy Exception catch
		
		SecretKeySpec key = new SecretKeySpec(sharedSecret, "AES");
		IvParameterSpec param =  new IvParameterSpec(iv);
		
		try {cipher.init(mode, key, param);}
		catch (InvalidKeyException | InvalidAlgorithmParameterException e) {e.printStackTrace();} // Dummy Exception catch
		
		return(cipher);
	}
	
	/** Encrypt giver bytes.
	 * @param iv Random number.
	 * @param data Bytes to encrypt.
	 * @return Encrypted bytes*/
	private byte[] encrypt(byte[] iv, byte[] data) {
		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, iv);
		byte[] data_encryp = null;
		try { data_encryp = cipher.doFinal(data); } 
		catch (IllegalBlockSizeException | BadPaddingException e) {e.printStackTrace();}
		
		return(data_encryp);	
	}
	
	/** Decrypt given bytes.
	 * @param iv Random number.
	 * @param data_encryp Encrypted bytes.
	 * @return normal bytes*/
	private byte[] decrypt(byte[] iv, byte[] data_encryp) {
		Cipher cipher = getCipher(Cipher.DECRYPT_MODE, iv);
		byte[] data = null;
		try { data = cipher.doFinal(data_encryp); } 
		catch (IllegalBlockSizeException | BadPaddingException e) {e.printStackTrace();}
		
		return(data);	
	}
		
	/** Increment message counter. */
	private void incrementCounters() {
		msgnr +=1;
		reqid +=1;
	}
	
	/** Decode an elliptic curve Public Key.
	 * @param public_key_encoded The encoded key.
	 * @return Public Key.*/
	private ECPublicKeyParameters pubkey_decode(String public_key_encoded) {
		byte [] public_key_data = decode(public_key_encoded);
		ECPublicKeyParameters public_key = new ECPublicKeyParameters(curveSpec.getCurve().decodePoint(public_key_data), curveParams);
		return(public_key);
	}

	/** Decode an elliptic curve Private Key.
	 * @param private_key_encoded The encoded key.
	 * @return Private Key.*/
	@SuppressWarnings("unused")
	private ECPrivateKeyParameters privkey_decode(String private_key_encoded) {
		byte [] private_key_data = decode(private_key_encoded);
		BigInteger private_key_int = new BigInteger(private_key_data);
		ECPrivateKeyParameters private_key = new ECPrivateKeyParameters(private_key_int, curveParams);
		return(private_key);
	}
	
	/** Encode an elliptic curve Public Key.
	 * @param public_key The key.
	 * @return Public Key encoded string.*/
	private String pubkey_encode(ECPublicKeyParameters public_key) {
		return(encode(public_key.getQ().getEncoded(false)));
	}

	/** Encode an elliptic curve Private Key.
	 * @param private_key The key.
	 * @return Private Key encoded string.*/
	@SuppressWarnings("unused")
	private String privkey_encode(ECPrivateKeyParameters private_key) {
		return(encode(private_key.getD().toByteArray()));
	}
	
	/** Decode string to bytes.
	 * @param datab64 Base64 bytes as string.
	 * @return Data in bytes.*/
	private byte[] decode(String datab64) {
		byte[] databyte = Base64.getDecoder().decode(datab64);
		return(databyte);
	}
	
	/** Encode bytes into string.
	 * @param databyte Data in bytes.
	 * @return Base64 bytes as string.*/
	private String encode(byte[] databyte) {
		String datab64 = Base64.getEncoder().encodeToString(databyte);
		return(datab64);
	}

	/** Hash bytes with SHA256 algorithm.
	 * @param data Bytes.
	 * @return Hashed bytes.*/
	private byte[] hash_sha256(byte[] data) {
		MessageDigest hashinstance = null;
		try {hashinstance = MessageDigest.getInstance("SHA-256");}
		catch (NoSuchAlgorithmException e) {e.printStackTrace();} // Dummy Exception catch
		
		return(hashinstance.digest(data));
	}
	
	/** Build a shared secret between their key and ours 
	 * @param public_key received key.*/
	private void buildSharedKey(ECPublicKeyParameters public_key) {
		ECDHBasicAgreement keyAg = new ECDHBasicAgreement();
		keyAg.init(privKey);
		byte[] aggrement = keyAg.calculateAgreement(public_key).toByteArray();
		
		sharedSecret = hash_sha256(aggrement);
	}
	
	/** Call 'keyxchg' command on the runtime
	 * @return runtime response */
	private JsonObject cmd_keyxchg() throws DeploymentException {
		JsonObject payload = getMessageBody("keyxchg");
		payload.addProperty("pubkey", pubkey_encode(pubKey));
		JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
		
		return(response);
	}
	
	/** Call 'auth' command on the runtime
	 * @return runtime response */
	private JsonObject cmd_auth() throws DeploymentException {
		JsonObject payload = getMessageBody("auth");
		
		byte[] ivbytes = new byte[16];
		rand.nextBytes(ivbytes);
		
		JsonObject cred = new JsonObject();
		byte[] encrypted_creds = encrypt(ivbytes,cred.toString().getBytes());
		
		JsonObject back = new JsonObject();
		back.addProperty("method","anonymous");
        payload.addProperty("authnonce",encode(ivbytes));
        payload.add("backend",back);
        payload.addProperty("credentials", encode(encrypted_creds));
		
        JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
        
		return(response);
	}
	
	/** Calculate the HMAC algorithm on a message
	 * @param message Byte message.
	 * @return The message with HMAC.*/
	private byte[] calculateHmac(byte[] message) {
		Mac sha256_HMAC = null;
		try {sha256_HMAC = Mac.getInstance("HmacSHA256");}
		catch (NoSuchAlgorithmException e) {e.printStackTrace();} // Dummy Exception catch
		
		SecretKeySpec key = new SecretKeySpec(ticket,"HmacSHA256");
		
		try {sha256_HMAC.init(key);}
		catch (InvalidKeyException e) {e.printStackTrace();} // Dummy Exception catch
		
		byte[] hmac = sha256_HMAC.doFinal(message);
		return(hmac);
	}
	
	/** Call 'rqnonce' command on the runtime.
	 * @param role.
	 * @return runtime response */
	private JsonObject cmd_rqnonce(String role) throws DeploymentException {
		JsonObject payload = getMessageBody("rqnonce");
		payload.addProperty("role", role);
		
		JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
		return(response);
	}
	
	/** Call 'rqrole' command on the runtime.
	 * @param role.
	 * @param iv Random number.
	 * @return runtime response */
	private JsonObject cmd_rqrole(String role, byte[] iv) throws DeploymentException {
		JsonObject payload = getMessageBody("rqrole");
		payload.addProperty("role",role);
		payload.addProperty("sessid",session_id);
		payload.addProperty("hmac",encode(calculateHmac(iv)));

		JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
		return(response);
	}
	
	/** Execute the change role steps.
	 * @param role.
	 * @return A number received.*/
	private byte[] change_role(String role) throws DeploymentException {
		JsonObject nonce_result = cmd_rqnonce(role);
		
		if (checkResponse(nonce_result)) {
			JsonElement nonceobj = nonce_result.get("nonce");
			if(nonceobj!=null) {
				byte[] ivbytes = decode(nonceobj.getAsString());
				
				JsonObject role_result = cmd_rqrole(role,ivbytes);				
				if(checkResponse(role_result)) {
					return(ivbytes);
				}
			}
		}
		return(null);
	}
	
	/** Call 'relrole' command on the runtime.
	 * @return runtime response */
	private JsonObject cmd_relrole() throws DeploymentException {
		JsonObject payload = getMessageBody("relrole");
		JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
		return(response);
	}

	/** Call 'transition' command on the runtime.
	 * @param cmd Flow command to execute. i.e 'start','stop','clean' ...
	 * @return runtime response */
	private JsonObject cmd_transition(String cmd) throws DeploymentException {
		JsonObject payload = getMessageBody("transition");
		payload.addProperty("command",cmd);
		JsonObject response = sendAndWaitResponse(payload);
		parseError(response);
		return(response);
	}

	/** Call 'deploy' command on the runtime.
	 * @param projId UUID for the project.
	 * @param snapId UUID for the deploy.
	 * @return runtime response */
	private JsonObject cmd_deploy(String projId, String snapId) throws DeploymentException {
		byte[] iv = change_role("deploy");
		JsonObject response = null;
		
		if(iv!=null) {
			JsonObject payload = getMessageBody("transition");
			payload.addProperty("command","deploy");
			payload.addProperty("project_guid",projId);
			payload.addProperty("snapshot_guid",snapId);
			
			response = sendAndWaitResponse(payload);
			cmd_relrole();
			parseError(response);
		}
		
		return(response);
	}
	
	/** Send a list of files to the Runtime. 
	 * @param fileMap a Filename:Filebytes kind of map.
	 * @param snpId Snapshot UUID.
	 * @return List of Http responses.
	 * @throws DeploymentException Runtime failed to respond. */
	private List<HttpResponse> sendFiles(Map<String,byte[]> fileMap, String snpId) throws DeploymentException, ClientProtocolException, IOException {
		String httpEndpoint = String.format("http://%s/upload/",endpoint);

        CloseableHttpClient httpClient = HttpClients.createDefault();
		BasicCookieStore cookieStore = new BasicCookieStore();
        BasicHttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        
		List<HttpResponse> responseList = new ArrayList<>();
        
		byte[] iv = change_role("deploy");
		if(iv!=null) {
			int n=0;
			for (Map.Entry<String,byte[]> file : fileMap.entrySet()) {
				n++;
				
				byte[] nbytes = UAOBinFile.write_word(n, ByteOrder.BIG_ENDIAN);
				byte[] concat = ByteBuffer.allocate(iv.length+nbytes.length).put(iv).put(nbytes).array();
				String hmac = encode(calculateHmac(concat));
				String filehash = Hex.encodeHexString(hash_sha256(file.getValue()));
				
				MultipartEntityBuilder entity = MultipartEntityBuilder.create();
				entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.addBinaryBody(file.getKey(), file.getValue(), ContentType.DEFAULT_BINARY, file.getKey());
				
				RequestBuilder request = RequestBuilder.put(httpEndpoint);
				request.setEntity(entity.build());
				request.addHeader("Accept", "*/*");
				String auth = String.format("session-id=%d;nonce=\"%s\";counter=%d;hmac=\"%s\"",session_id,encode(iv),n,hmac);
				String xfer = String.format("filesize=\"%d\" ;directory=\"Working\"; hash=\"%s\"; snapshot-guid=\"%s\"",file.getValue().length,filehash,snpId);
				request.addHeader("SRT61499N-Auth", auth);
				request.addHeader("SRT61499N-Xfer", xfer);
				
				HttpUriRequest multipartRequest = request.build();

				responseList.add(httpClient.execute(multipartRequest,httpContext));
			}
		}
		return(responseList);
	}
	
	/** Check if all Http responses have status 200
	 * @param responseList a List of HttpResponse objects.
	 * @return True is is all 200.*/
	private boolean checkHttpResponses(List<HttpResponse> responseList) {
		boolean check = true;
		for (HttpResponse response : responseList) {
			check = check && response.getStatusLine().getStatusCode()==200;
		}
		return(check);
	}
	
}

