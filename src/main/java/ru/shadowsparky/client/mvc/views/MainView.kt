/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvc.views

import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.utils.objects.Injection
import tornadofx.*

open class MainView : View("Главное меню"){
    private val styles = Injection.provideStyles()
    override val root = styles.getDefaultStackPane()

    private fun setStyle() {
        JMetro(JMetro.Style.DARK).applyTheme(root)
    }

    init {
        val adb = AdbView()
        root.apply {
            this += styles.getDefaultTabPane().apply {
                tab("WIFI") {
                    add(WifiView().root)
                }

                tab("ADB") {
                    adb.updateDevices()
                    add(adb.root)
                }
                selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
                    adb.updateDevices()
                }
            }
            addClass(styles.test)
        }
        setStyle()
    }
}