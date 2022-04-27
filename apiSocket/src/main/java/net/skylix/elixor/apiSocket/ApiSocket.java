package net.skylix.elixor.apiSocket;

import com.google.gson.Gson;
import net.skylix.elixor.apiSocket.controller.Controller;
import net.skylix.elixor.apiSocket.controller.request.ControllerRequest;
import net.skylix.elixor.apiSocket.controller.socket.ControllerSocket;
import net.skylix.elixor.apiSocket.controller.socket.ControllerSocketErrorCodes;
import net.skylix.elixor.apiSocket.errors.ServerAlreadyRunning;
import net.skylix.elixor.apiSocket.errors.ServerAlreadyStopped;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The actual websocket server.
 */
class TrueServer extends WebSocketServer {
    private ArrayList<Controller> controllers = new ArrayList<>();

    /**
     * Google JSON parser.
     */
    private final Gson gson = new Gson();

    private final HashMap<WebSocket, ControllerSocket> sockets = new HashMap<>();

    public TrueServer(int port, ArrayList<Controller> controllers) {
        super(new InetSocketAddress(port));
        this.controllers = controllers;
    }

    private void dispatchOpen(WebSocket conn) {
        new Thread(() -> {
            for (Controller controller : controllers) {
                ControllerSocket socket = new ControllerSocket(conn, controller.getChannelName());
                sockets.put(conn, socket);

                controller.onActivate(socket);
            }
        }).start();
    }

    private void dispatchClose(WebSocket conn) {
        new Thread(() -> {
            for (Controller controller : controllers) {
                ControllerSocket socket = sockets.get(conn);
                controller.onDeactivate(socket);
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    private void dispatchMessage(WebSocket conn, String message) {
        new Thread(() -> {
            String channelHead = "^channel:(.*?);(.*?)";
            Pattern channelHeadPattern = Pattern.compile(channelHead);
            Matcher channelHeadMatcher = channelHeadPattern.matcher(message);

            if (!channelHeadMatcher.find()) {
                conn.send("e:" + ControllerSocketErrorCodes.INVALID_REQUEST_FORMAT + ";{}");
                return;
            }

            boolean found = false;

            for (Controller controller : controllers) {
                if (controller.getChannelName().equals(channelHeadMatcher.group(1))) {
                    ControllerRequest request;
                    ControllerSocket socket;

                    found = true;

                    try {
                        String json = message.substring(9 + channelHeadMatcher.group(1).length());
                        Object jsonData = gson.fromJson(json, controller.getMessageClass().client);
                        Field[] properties = controller.getMessageClass().client.getDeclaredFields();
                        HashMap dataResult = new HashMap<>();
                        socket = sockets.get(conn);

                        for (Field property : properties) {
                            property.setAccessible(true);
                            dataResult.put(property.getName(), property.get(jsonData));
                        }

                        request = new ControllerRequest(dataResult);

                    } catch (Exception e) {
                        conn.send("e:" + ControllerSocketErrorCodes.INVALID_REQUEST_JSON + ";{\"message\": \"" + e.getMessage() + "\"}");;
                        return;
                    }

                    controller.onRequest(socket, request);
                }
            }

            if (!found) {
                conn.send("e:" + ControllerSocketErrorCodes.INVALID_REQUEST_CHANNEL + ";{}");
            }
        }).start();
    }

    @Override
    public final void onOpen(WebSocket conn, ClientHandshake handshake) {
        dispatchOpen(conn);
    }

    @Override
    public final void onClose(WebSocket conn, int code, String reason, boolean remote) {
        dispatchClose(conn);
    }

    @Override
    public final void onMessage(WebSocket conn, String message) {
        dispatchMessage(conn, message);
    }

    @Override
    public final void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public final void onStart() {

    }
}

/**
 * A class used for creating API services with real time interactions.
 */
public class ApiSocket {
    /**
     * The connected controllers.
     */
    private final ArrayList<Controller> controllers = new ArrayList<>();

    /**
     * The actual socket host.
     */
    private final TrueServer wss;

    /**
     * If the server is fully ready.
     */
    private boolean isRunning = false;

    /**
     * If the server is currently booting or stopping.
     */
    private boolean isWorking = false;

    /**
     * Create a new realtime API service.
     */
    public ApiSocket() {
        wss = new TrueServer(8080, controllers);
    }

    /**
     * Adds a controller to the list of connected controllers.
     *
     * @param controller The controller to add.
     * @return The ID of the controller added.
     */
    public final Integer connectController(Controller controller) {
        controllers.add(controller);

        return controllers.size() - 1;
    }

    /**
     * Get a specific controller by its ID.
     *
     * @param id The ID of the controller to get.
     * @return The controller with the specified ID.
     */
    public final Controller getController(Integer id) {
        return controllers.get(id);
    }

    /**
     * Removes a controller from the list of connected controllers.
     *
     * @param controller The controller to remove.
     */
    public final void disconnectController(Controller controller) {
        controllers.remove(controller);
    }

    /**
     * Run the API live server.
     */
    public final void run() throws ServerAlreadyRunning {
        if (isRunning || (isRunning && isWorking)) {
            throw new ServerAlreadyRunning(isWorking ? "The server is already running." : "The server is currently loading.");
        }

        isWorking = true;
        wss.start();

        isWorking = false;
        isRunning = true;
    }

    public final void stop() throws ServerAlreadyStopped, InterruptedException {
        if (!isRunning || (isRunning && isWorking)) {
            throw new ServerAlreadyStopped(isWorking ? "The server is already stopping." : "The server is not running.");
        }

        try {
            wss.stop();
        } catch (Exception error) {
            throw error;
        }
    }
}
