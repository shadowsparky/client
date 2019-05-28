/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.interfaces

/**
 * Класс, имплементирующий этот интерфейс получает возможность оповещения о статусе
 * выполнения задачи
 */
interface Resultable {

    /**
     * Срабатывает, если необходимая задача была выполнена
     */
    fun onSuccess()

    /**
     * Срабатывает, если во время выполнения задачи произошла ошибка
     *
     * @param e ошибка
     */
    fun onError(e: Exception)
}