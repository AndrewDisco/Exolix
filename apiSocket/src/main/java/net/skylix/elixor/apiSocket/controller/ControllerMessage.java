package net.skylix.elixor.apiSocket.controller;

public class ControllerMessage<ServerReply, ClientMessage> {
    public Class<ServerReply> server;
    public Class<ClientMessage> client;

    public ControllerMessage(Class<ServerReply> server, Class<ClientMessage> client) {
        this.server = server;
        this.client = client;
    }

    public final Class<ServerReply> getServerClass() {
        return server;
    }

    public final Class<ClientMessage> getClientClass() {
        return client;
    }
}
