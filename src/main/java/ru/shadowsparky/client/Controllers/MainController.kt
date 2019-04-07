/*
 * Created by shadowsparky in 2019
 */

package ru.shadowsparky.client.Controllers

import javafx.fxml.FXML
import javafx.scene.control.TabPane
import javafx.scene.layout.BorderPane
import org.jcodec.api.NotImplementedException
import ru.shadowsparky.client.Utils.Controllerable
import ru.shadowsparky.client.Utils.Injection
import ru.shadowsparky.client.Utils.Injector
import ru.shadowsparky.client.Utils.LayoutConsts

enum class ConnectionType {
    adb, wifi
}

class MainController : Controllerable {
    @FXML private lateinit var root: BorderPane
    @FXML private lateinit var tabPane: TabPane
    private val log = Injection.provideLogger()
    private var injector: Injector? = null

    @FXML fun initialize() {
        injector = Injection.provideInjector(root)
        injector?.injectScreen(LayoutConsts.WIFI_LAYOUT, WifiController())
        initTab()
    }

    private fun initTab() {
        tabPane.selectionModel.selectedItemProperty().addListener { obs, ov, nv ->
            when(nv.text) {
                "WIFI" -> {
                    log.printInfo("Wifi Clicked")
                    injector?.injectScreen(LayoutConsts.WIFI_LAYOUT, WifiController())
                }
                "ADB" -> {
                    log.printInfo("ADB Clicked")
                    injector?.injectScreen(LayoutConsts.ADB_LAYOUT_FXML, AdbController())
                }
                "Settings" -> {
//                    throw NotImplementedException("Данный раздел находится в разработке")
                }
            }
        }
    }
}