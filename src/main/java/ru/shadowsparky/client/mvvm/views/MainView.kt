/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.objects.Injection
import tornadofx.*

open class MainView : View("Главное меню"){
    private val styles = Injection.provideStyles()
    override val root = styles.getDefaultStackPane()
    val tabPane = styles.getDefaultTabPane()

    private fun setStyle() {
        JMetro(JMetro.Style.DARK).applyTheme(root)
    }

    init {
        root.apply {
            val adb = AdbView()
            val wifi = WifiView()

            this += tabPane.apply {
                id = "tabs"
                tab("WIFI") {
                    id = "wifi_tab"
                    log.info("Wifi attached")
                    add(wifi.root)
                }

                tab("ADB") {
                    id = "adb_tab"
                    log.info("ADB attached")
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