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
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketListener;

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
     * @param listener method overwride implemented as descripted in
     * `WebSocketListener` interface.*/
	public void addListener(WebSocketListener listener) {
		ws.addListener(listener);
	}

    /** Blocking message receive.
     * @param timeout Timeout (milliseconds) for the wait on server message
     * @return message received.*/
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
	
    /** Send json object to the server.*/.
	public void send(JsonObject json) {
		ws.sendText(json.toString());
	}
	
    /** Check if connection is open.*/
	public boolean isOpen() {
		return(ws.isOpen());
	}
}

