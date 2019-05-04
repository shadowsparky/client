/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.scene.control.TabPane
import tornadofx.addClass
import tornadofx.tab

class MainView : BaseView(){
    override val root: TabPane

    init {
        val adb = AdbView()
        root = styles.defaultTabPane.apply {
            addClass(styles.test)

            tab("WIFI") {
                add(WifiView().root)
            }

            tab("ADB") {
                adb.updateDevices()
                add(adb.root)
            }
        }
        root.selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
            adb.updateDevices()
        }
        setStyle()
    }
}