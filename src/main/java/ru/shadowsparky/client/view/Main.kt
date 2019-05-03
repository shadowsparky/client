/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.view

import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class Main : App(MainView::class, Styles::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}