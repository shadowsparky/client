/*
 * Created by shadowsparky in 2019
 *
 */

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.mvc.controllers.AdbController
import ru.shadowsparky.client.mvc.models.AdbModel
import ru.shadowsparky.client.mvc.views.AdbView
import ru.shadowsparky.client.objects.Parser

class TestAdbController {
    private val view = mock<AdbView>()
    private val model = mock<AdbModel>()
    private val controller = AdbController(view, model)

    init {
        view.dialog = mock()
    }

    private fun provideAdbDevices() : ArrayList<ADBDevice> = Parser.strToDevices("192.168.31.221:5555    device product:pixys_tissot model:Mi_A1 device:tissot transport_id:1")

    @Test fun testUpdateNotNullAndNotEmptyDevices() {
        doReturn(provideAdbDevices()).`when`(model).getDevicesRequest()
        controller.updateDevices()
        verify(view, times(1)).clearDevices()
        verify(model, times(1)).getDevicesRequest()
        verify(view, times(1)).addDevice(ArgumentMatchers.anyString())
        verify(view, times(1)).setDisable(false)
        verifyNoMoreInteractions(view, model)
    }

    @Test fun testUpdateNullDevices() {
        doReturn(null).`when`(model).getDevicesRequest()
        controller.updateDevices()
        verify(view, times(1)).clearDevices()
        verify(model, times(1)).getDevicesRequest()
        verifyNoMoreInteractions(view, model)
    }

    @Test fun testUpdateNotNullAndEmptyDevices() {
        doReturn(ArrayList<ADBDevice>()).`when`(model).getDevicesRequest()
        controller.updateDevices()
        verify(view, times(1)).clearDevices()
        verify(model, times(1)).getDevicesRequest()
        verify(view, times(1)).addDevice("Нет подключенных устройств")
        verify(view, times(1)).setDisable(true)
        verifyNoMoreInteractions(view, model)
    }

    @Test fun testShowHelp() {
        controller.showHelp()
        verify(view.dialog, times(1)).showDialog(
                "Справка",
                "Для того, чтобы нажимать на экран мобильного устройства, используйте левую кнопку мыши.\n" +
                        "Для возвращения назад нажмите на кнопку Z или B, \n" +
                        "Для открытия меню недавних приложений нажмите на C или R, \n" +
                        "Для нажатия на кнопку 'Домой' нажмите на X или H.",
                true
        )
    }
}
