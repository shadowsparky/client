/*
 * Created by shadowsparky in 2019
 *
 */

import javafx.scene.control.TabPane
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.testfx.api.FxToolkit
import org.testfx.framework.junit5.ApplicationTest
import ru.shadowsparky.client.mvc.controllers.AdbController
import ru.shadowsparky.client.mvc.views.MainView
import tornadofx.content

class TestTest : ApplicationTest() {
    val pStage = FxToolkit.registerPrimaryStage()
    val main = MainView()

    @Test
    fun testRun() {
        val tabPane: TabPane = from(main.root.content).lookup("#tabs").query()
        assert(tabPane.tabs[0].text == "WIFI")
        assert(tabPane.tabs[1].text == "ADB")
    }

}