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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
	public UAOClient(String endpoint, int timeoutms, boolean useSsl) throws DeploymentException {
		this.ws = setWebsocket(endpoint,timeoutms);
		setEllipticCurve();
		genKeyPair();
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
		FordiacLogHelper.logWarning("UAOClient | Sended: "+payload.toString());
		ws.sendText(payload.toString());
		incrementCounters();
		try {
			JsonObject response = receive(timeout);
			FordiacLogHelper.logWarning("UAOClient | Received: "+response.toString());
			if(!checkResponse(response)) {
				int status = response.get("result").getAsInt();
				String reason = response.get("error").getAsJsonObject().get("desc").getAsString();
				throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestRejected,status,reason));
			}
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
	public void authenticate() throws DeploymentException {
		
		JsonObject result_keyxchg = cmd_keyxchg();
		if (checkResponse(result_keyxchg)) {
			if (result_keyxchg.get("pubkey") != null) {
				String runtimekey = result_keyxchg.get("pubkey").getAsString();
				buildSharedKey( pubkey_decode(runtimekey) );
			}
			
			JsonObject result_auth = cmd_auth();
			if (checkResponse(result_auth)) {
		        if (result_auth.get("ticket") != null) {
		        	byte[] ivbytes =  decode(result_auth.get("authnonce").getAsString());
		        	byte[] encrypted_ticket = decode(result_auth.get("ticket").getAsString());
		        	ticket = decrypt(ivbytes,encrypted_ticket);
		        	session_id = result_auth.get("sessid").getAsInt();
		        }
			}
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
	
	/** Generate a private-public key pair.*/
	private void genKeyPair() {
		SecureRandom secureRandom = new SecureRandom();
	    ECKeyGenerationParameters keyParams = new ECKeyGenerationParameters(curveParams, secureRandom);

	    ECKeyPairGenerator keyGen = new ECKeyPairGenerator();
	    keyGen.init(keyParams);
	    AsymmetricCipherKeyPair keyPair = keyGen.generateKeyPair();
	    this.privKey = (ECPrivateKeyParameters)keyPair.getPrivate();
        this.pubKey = (ECPublicKeyParameters)keyPair.getPublic();
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
		if (response.get("result")!=null) {
			isok = response.get("result").getAsInt()==200;
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
	
	/** Encode an elliptic curve Public Key.
	 * @param public_key The key.
	 * @return Public Key encoded string.*/
	private String pubkey_encode(ECPublicKeyParameters public_key) {
		return(encode(public_key.getQ().getEncoded(false)));
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
		
		return(response);
	}
	
	/** Call 'auth' command on the runtime
	 * @return runtime response */
	private JsonObject cmd_auth() throws DeploymentException {
		JsonObject payload = getMessageBody("auth");
		
		SecureRandom random = new SecureRandom();
		byte[] ivbytes = new byte[16];
		random.nextBytes(ivbytes);
		
		byte[] encrypted_creds = encrypt(ivbytes,"{}".getBytes());
		
		JsonObject back = new JsonObject();
		back.addProperty("method","anonymous");
        payload.addProperty("authnonce",encode(ivbytes));
        payload.add("backend",back);
        payload.addProperty("credentials", encode(encrypted_creds));
		
        JsonObject response = sendAndWaitResponse(payload);
        
		return(response);
	}
}

