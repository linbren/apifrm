package net.test.tools;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class WebSocket {
	private static final Set<WebSocket> connections = new CopyOnWriteArraySet<WebSocket>();
	private String id;
	private static Map<String, WebSocket> idMap = new HashMap<String, WebSocket>();
	private static Map<String, String> uuidMap = new HashMap<String, String>();
	private final String uuid;
	private Session session;

	public WebSocket() {
		uuid = UUID.randomUUID().toString();
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		if (null == id) {
			if (null == idMap.get(message)) {
				id = message;
				idMap.put(id, this);
				uuidMap.put(uuid, id);
				broadcast(String.format("* %s %s", id, "has joined!  from " + uuid));
			} else {
				session.getBasicRemote().sendText("from:system;用户已存在");
			}
		} else {
			if (message.startsWith("to:")) {
				int index = message.indexOf(";");
				String user = message.substring(3, index);
				String messageString = message.substring(index + 1, message.length());
				if(!sendMessage(user, id, messageString)){
					session.getBasicRemote().sendText("from:system;用户不存在");
				}
			} else {
				broadcast(id+":"+message);
			}
		}
	}

	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		connections.add(this);
		broadcast(uuid + " has connected!");
	}

	@OnClose
	public void onClose() {
		connections.remove(this);
		String id = uuidMap.remove(uuid);
		if (null != id) {
			idMap.remove(id);
			broadcast(id + " has gone!");
		} else {
			broadcast(uuid + " has been disconnected!");
		}
	}

	public static boolean sendMessage(String toId, String fromId, String message) throws IOException {
		WebSocket ws = idMap.get(toId);
		if (null != ws) {
			ws.session.getBasicRemote().sendText("from:" + fromId + ";" + message);
			return true;
		}
		return false;
	}

	public static void broadcast(String msg) {
		for (WebSocket client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					// Ignore
				}
				if (null != client.id) {
					String message = String.format("* %s %s", client.id, "has been gone.");
					broadcast(message);
				} else {
					String message = String.format("* %s %s", client.uuid, "has been disconnected.");
					broadcast(message);
				}
			}
		}
	}
}