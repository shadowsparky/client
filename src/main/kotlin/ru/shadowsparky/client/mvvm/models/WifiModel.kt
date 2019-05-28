/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.models

import ru.shadowsparky.client.exceptions.EmptyAddressException
import ru.shadowsparky.client.exceptions.ProjectionAlreadyStartedException
import ru.shadowsparky.client.projection.ProjectionWorker

/**
 * Model из MVVM для работы с wifi
 */
open class WifiModel {

    /**
     * Проверка введенного ip адреса на пустоту
     *
     * @exception EmptyAddressException вызывается, если введеный IP адрес пуст
     */
    fun checkIpAddress(ip: String) {
        if (ip.isEmpty())
            throw EmptyAddressException()
    }

    /**
     * Проверка проецирования на повторный запуск.
     *
     * @exception ProjectionAlreadyStartedException вызывается, если проецирование уже запущено
     */
    fun checkProjection(projection: ProjectionWorker?) {
        if ((projection != null) or (projection?.handling != false))
            throw ProjectionAlreadyStartedException()
    }
}