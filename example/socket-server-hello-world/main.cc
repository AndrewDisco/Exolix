#include <exolix/net.h>

using namespace exolix::net;

void log(const std::string& message) {
    std::cout << message << std::endl;
}

int main() {
    SocketServer server(5030);

    server.setOnSocketOpen([&server] (int id) {
         Socket socket(id);
         log("New connection: " + std::to_string(socket.socketHandle));

         socket.setOnMessage([&socket] (const std::string& message) {
             log("New message [" + std::to_string(socket.socketHandle) + "]: " + message);
                socket.send("You sent: " + message);
         });

         socket.block();
         log("Connection closed: " + std::to_string(socket.socketHandle));
    });

    server.bind();
    server.block();

    return 0;
}
