/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.scene.control.TabPane
import javafx.scene.layout.StackPane
import tornadofx.addClass
import tornadofx.stackpane
import tornadofx.tab
import tornadofx.vbox

class MainView : BaseView(){
    override val root: StackPane

    init {
        val adb = AdbView()
        val tabPane = styles.defaultTabPane.apply {
            addClass(styles.test)
            tab("WIFI") {
                add(WifiView().root)
            }

            tab("ADB") {
                adb.updateDevices()
                add(adb.root)
            }
        }
        root = StackPane().apply { add(tabPane) }
        tabPane.selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
            adb.updateDevices()
        }
        setStyle()
    }
}