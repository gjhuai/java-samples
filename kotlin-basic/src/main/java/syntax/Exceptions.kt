package syntax

import java.net.SocketException

/**
 * would be translated to
 * <code>void connect(String host) throws SocketException</code>
 */
@Throws(SocketException::class)
fun connect(host: String) {
}