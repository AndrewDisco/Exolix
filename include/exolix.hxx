// #Start CompileHeader
#ifdef __FreeBSD__
#error "FreeBSD is not a supported platform for Exolix"
#elif _WIN32
#define WIN32_LEAN_AND_MEAN
#endif
// #End CompileHeader

// #Start NamespaceDoc
/**
 *    ---   ---   ---   Exolix   ---   ---   ---   <br />
 *    Exolix - The framework by Skylix. Exolix is a
 *    framework written in C++ designed for high
 *    performance server side and client side apps.
 *
 *    Exolix was designed to be easy to use, yet
 *    packed with useful components, classes and
 *    more to help you build the app of your dreams!
 *    <br />
 *    ---   ---   ---   #Legal   ---   ---   ---   <br />
 *    Exolix framework is licenced under the MIT
 *    (Massachusetts Institute of Technology) Licence.
 *
 *    Copyright (2021 - Current) Skylix
 *    Permission is hereby granted, free of charge, to
 *    any person obtaining a copy of this
 *    software and associated documentation files
 *    (the "Software"), to deal in the Software
 *    without restriction, including without
 *    limitation the rights to use, copy, modify,
 *    merge, publish, distribute, sublicense,
 *    and/or sell copies of the Software, and to
 *    permit persons to whom the Software is
 *    furnished to do so, subject to the following
 *    conditions:  The above copyright notice and this
 *    permission notice shall be included in all copies
 *    or substantial portions of the Software.
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 *    WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 *    DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *    ACTION OF CONTRACT, TORT OR OTHERWISE,
 *    ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
 *
 *    (This is the end of the MIT licence)
 */
namespace exolix {}
// #End NamespaceDoc

// ---                                                        --- //
// The following guards for the includes must not be removed. --- //
// The code bellow here will be auto generated by a script.   --- //
// ---                                                        --- //

// #StartInclude
#include "../lib/app/app.hxx"
#include "../lib/app/windowing/windowing.hxx"
#include "../lib/error/error.hxx"
#include "../lib/net/address.hxx"
#include "../lib/net/tcp/sockets.hxx"
#include "../lib/net/tcp/system/unix.hxx"
#include "../lib/net/tcp/system/windows.hxx"
#include "../lib/net/udp/dgram.hxx"
#include "../lib/number/condition.hxx"
#include "../lib/number/parsing.hxx"
#include "../lib/process/process.hxx"
#include "../lib/string/condition.hxx"
#include "../lib/string/token.hxx"
// #EndInclude

// End of computer assisted include file.
