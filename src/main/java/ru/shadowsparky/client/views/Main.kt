/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import ru.shadowsparky.client.utils.Styles
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class Main : App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}