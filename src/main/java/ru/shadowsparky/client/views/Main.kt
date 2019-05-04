/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import ru.shadowsparky.client.views.MainView
import ru.shadowsparky.client.view.Styles
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class Main : App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}