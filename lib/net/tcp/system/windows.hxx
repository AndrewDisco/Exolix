#pragma once

#include "../../../error/error.hxx"

#ifdef _WIN32
#include <string>
#include <functional>
#include <winsock2.h>
#include <process.h>
#include "../../../types.hxx"
#include <openssl/ssl.h>
#include <map>
#endif

namespace exolix {
    enum class WinsockTcpServerErrors {
        FAILED_TO_LISTEN,
        STARTUP_FAIL,
        ADDRESS_INFO_ERROR,
        SOCKET_CREATE_FAIL,
        ADDRESS_IN_USE,
        PERMISSION_ERROR,
        BINDING_ERROR,
        SSL_CERTIFICATE_LD_ERROR,
        SSL_KEY_LD_ERROR
    };

    typedef Error<WinsockTcpServerErrors> WinsockTcpServerException;

    class WinsockTcpServer {
    private:
#ifdef _WIN32
        std::function< void(SOCKET
        socketFd)>
        connectionHandler;

        WSADATA wsaData{};

        bool online = false;
        bool tls = false;

        struct addrinfo *result = nullptr;
        struct addrinfo hints{};

        int intResult = 0;

        std::string certSsl;
        std::string keySsl;

        SOCKET serverSocket = INVALID_SOCKET;
        SOCKET clientSocket = INVALID_SOCKET;

        SSL_CTX *sslCtx;

        void bind();

        void configureAddress(const std::string &host, u16 port);

        void init();

        bool pendingSocket = false;

        std::map<SOCKET, SSL *> sslSockets{};
#endif

    public:
#if _WIN32
        explicit WinsockTcpServer(std::function< void(SOCKET socketFd)

        > connectionHandlerCallback);

        ~WinsockTcpServer();

        void listen(const std::string &address, u16 port);

        void halt();

        void setTls(bool tls);

        void setCert(std::string certPath);

        void setKey(std::string keyPath);

        std::map<SOCKET, SSL *> getTlsClients();
#endif
    };
}
