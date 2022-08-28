#pragma once

// All Exolix public includes

#ifdef __FreeBSD__
#error "FreeBSD is not a supported platform for Exolix"
#endif

#include "../lib/app/app.hxx"
#include "../lib/app/windowing/windowing.hxx"
#include "../lib/error/error.hxx"
#include "../lib/net/tcp/sockets.hxx"
#include "../lib/net/udp/dgram.hxx"
#include "../lib/net/address.hxx"
#include "../lib/process/process.hxx"
#include "../lib/thread/thread.hxx"
