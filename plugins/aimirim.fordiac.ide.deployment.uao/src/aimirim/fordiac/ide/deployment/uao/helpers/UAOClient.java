/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
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
	
	public void addListener(WebSocketListener listener) {
		ws.addListener(listener);
	}

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
	
	public void connect() throws WebSocketException {
		ws.connect();
	}
	
	public void disconnect() {
		ws.disconnect();
	}
	
	public void send(JsonObject json) {
		ws.sendText(json.toString());
	}
	
	public boolean isOpen() {
		return(ws.isOpen());
	}
}

