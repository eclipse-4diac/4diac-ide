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
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	private final LinkedList<JsonObject> msgReceived = new LinkedList<>();
	
    /** Initialize UAOClient class
	 * @param endpoint Server endpoint address.
     * @param timeoutms Connection timeout.
     * @param useSsl True to use SSL on connection.*/
	public UAOClient(String endpoint, int timeoutms, boolean useSsl) throws IOException {
		this.ws = new WebSocketFactory()
            .setConnectionTimeout(timeoutms)																					
            .createSocket(endpoint);
		
		this.ws.addListener(new WebSocketAdapter() {
			public void onTextMessage(WebSocket websocket, String text) {
				msgReceived.add((JsonObject) JsonParser.parseString(text));
			}
			public void onError(WebSocket websocket, WebSocketException cause) {
				FordiacLogHelper.logError("UAOClient | Error:" + cause.getMessage()); //$NON-NLS-1$
			}
		});
		
	}
	
    /** Add callbacks to the connection
     * @param listener method overwrite implemented as described in
     * `WebSocketListener` interface.*/
	public void addListener(WebSocketListener listener) {
		ws.addListener(listener);
	}

    /** Blocking message receive.
     * @param timeout Timeout (milliseconds) for the wait on server message
     * @return Message received
     * @throws InterruptedException Thread interrupted while still waiting
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
     * @param json The message to send*/
	public void send(JsonObject json) {
		ws.sendText(json.toString());
	}
	
	/** Send json object to the server and wait for the response with
	 * a default timeout of 2000ms.
	 * @param json The message to send
	 * @return The server response 
	 * @throws DeploymentException Different messages according to the context.*/
	public JsonObject sendAndWaitResponse(JsonObject json) throws DeploymentException {
		return(sendAndWaitResponse(json,2000));
	}
	
	/** Send json object to the server and wait for the response.
	 * @param json The message to send
	 * @param timeout Max time to wait the server for a response in ms
	 * @return The server response
	 * @throws DeploymentException Different messages according to the context.*/
	public JsonObject sendAndWaitResponse(JsonObject json, int timeout) throws DeploymentException {
		ws.sendText(json.toString());
		try {
			JsonObject RESP = receive(timeout);
			FordiacLogHelper.logWarning("UAODeploymentExecutor | Received: "+RESP.toString());
			int status = RESP.get("result").getAsInt();
			if(status!=200) {
				String reason = RESP.get("error").getAsJsonObject().get("desc").getAsString();
				throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestRejected,status,reason));
			}
			return(RESP);
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
}

