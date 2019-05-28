/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.interfaces

/**
 * Класс, имплементирующий этот интерфейс получает возможность отслеживания загрузки
 */
interface Loadingable {

    /**
     * Срабатывает при смене статуса загрузки
     *
     * @param flag статус загрузки. true - загрузка включена, false - загрузка отключена
     */
    fun setLoading(flag: Boolean)
}