/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.utils

import ru.shadowsparky.client.utils.Extras.Companion.NOT_FOUND_IPV4
import java.lang.RuntimeException
import java.net.Inet4Address
import java.net.NetworkInterface

class NetworkUtils {
    fun getIpv4() : String {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val iface = interfaces.nextElement()
            if (iface.isLoopback || !iface.isUp)
                continue
            val addresses = iface.inetAddresses
            while (addresses.hasMoreElements()) {
                val addr = addresses.nextElement()
                if (addr is Inet4Address) {
                    return addr.hostAddress
                }
            }
        }
        throw RuntimeException(NOT_FOUND_IPV4)
    }
}