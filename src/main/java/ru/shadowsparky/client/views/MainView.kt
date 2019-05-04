/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.views

import javafx.scene.Parent
import tornadofx.addClass
import tornadofx.tab

class MainView : BaseView(){
    override val root: Parent

    init {
        root = styles.defaultTabPane.apply {
            addClass(styles.test)

            tab("WIFI") {
                add(WifiView().root)
            }

            tab("ADB") {
                //            val adb = AdbView(this@MainView)
//            adb.updateDevices()
//            add(adb.root)
            }
        }
        setStyle()
    }
}