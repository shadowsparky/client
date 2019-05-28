/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.views

import jfxtras.styles.jmetro8.JMetro
import ru.shadowsparky.client.mvvm.Styles
import ru.shadowsparky.client.objects.Injection
import tornadofx.View
import tornadofx.addClass
import tornadofx.plusAssign
import tornadofx.tab

/**
 * Основное окно приложение с TabPane из JavaFX
 *
 * @property styles подробнее: [Styles]
 * @property root главный layout
 * @property tabPane компонент TabPane
 */
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