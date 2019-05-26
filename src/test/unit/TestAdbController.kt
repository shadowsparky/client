/*
 * Created by shadowsparky in 2019
 *
 */

package unit

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import ru.shadowsparky.client.adb.ADBDevice
import ru.shadowsparky.client.mvvm.viewmodels.AdbViewModel
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.mvvm.views.AdbView
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.objects.Constants.ADB_NOT_FOUND
import ru.shadowsparky.client.objects.Constants.CHOOSE_DEVICE_ERROR
import ru.shadowsparky.client.objects.Constants.ERROR
import ru.shadowsparky.client.objects.Constants.FAQ
import ru.shadowsparky.client.objects.Constants.FAQ_MESSAGE
import ru.shadowsparky.client.objects.Constants.FORWARD_ERROR
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Constants.MOCKED_DEVICE
import ru.shadowsparky.client.objects.Constants.NO_CONNECTED_DEVICES
import ru.shadowsparky.client.objects.Parser
import ru.shadowsparky.client.projection.ProjectionWorker

class TestAdbController {
//    private lateinit var view: AdbView
//    private lateinit var model: AdbModel
//    private lateinit var controller: AdbViewModel
//
//    private fun provideAdbDevices() : ArrayList<ADBDevice> = Parser.strToDevices(MOCKED_DEVICE)
//
//    init {
//        prepare()
//    }
//
//    fun prepare() {
//        view = mock()
//        model = mock()
//        controller = AdbViewModel(view, model)
//        view.dialog = mock()
//        BaseView.isLoaded = mock()
//        view.projection = mock()
//    }
//
//    // ADB найден и найдены устройства
//    @Test fun updateNotNullAndNotEmptyDevices() {
//        doReturn(provideAdbDevices()).`when`(model).getDevicesRequest()
//        controller.updateDevices()
//        verify(view, times(1)).clearDevices()
//        verify(model, times(1)).getDevicesRequest()
//        verify(view, times(1)).addDevice(ArgumentMatchers.anyString())
//        verify(view, times(1)).setDisable(false)
//    }
//
//    // ADB не найден
//    @Test fun updateNullDevices() {
//        doReturn(null).`when`(model).getDevicesRequest()
//        controller.updateDevices()
//        verify(view, times(1)).clearDevices()
//        verify(model, times(1)).getDevicesRequest()
//        verify(view, times(1)).addDevice(ADB_NOT_FOUND)
//        verify(view, times(1)).setDisable(true)
//    }
//
//    // Нет подключенных устройств
//    @Test fun updateNotNullAndEmptyDevices() {
//        doReturn(ArrayList<ADBDevice>()).`when`(model).getDevicesRequest()
//        controller.updateDevices()
//        verify(view, times(1)).clearDevices()
//        verify(model, times(1)).getDevicesRequest()
//        verify(view, times(1)).addDevice(NO_CONNECTED_DEVICES)
//        verify(view, times(1)).setDisable(true)
//    }
//
//    // Отображение справки
//    @Test fun showHelp() {
//        controller.showHelp()
//        verify(view.dialog, times(1)).showDialog(FAQ,FAQ_MESSAGE, true)
//    }
//
//    // Проецирование с "нормальным" устройством
//    @Test fun startProjectionWithNotNullDevice() {
//        controller.startProjection()
//        doReturn(LOCALHOST).`when`(view).deviceAddr
//        doReturn(true).`when`(model).forwardPort(LOCALHOST)
//        view.projection = mock()
//        verify(BaseView.isLoaded, times(1)).value = false
//        assert(view.deviceAddr != null)
//        assert(model.forwardPort(LOCALHOST))
////        verify(view).projection = ArgumentMatchers.any(ProjectionWorker::class.java)
////        verify(view.projection)!!.start()
//    }
//
//    // Начало проецирования c невыбранным устройством
//    @Test fun startProjectionWithNullDevice() {
//        doReturn(null).`when`(view).deviceAddr
//        controller.startProjection()
//        verify(BaseView.isLoaded, times(1)).value = false
//        assert(view.deviceAddr == null)
//        verify(view.dialog).showDialog(ERROR, CHOOSE_DEVICE_ERROR)
//    }
//
//    // Начало проецирования "нормальным устройством", но при
//    // переопределении порта произошла ошибка
//    @Test fun startProjectionWithForwardError() {
//        doReturn(LOCALHOST).`when`(view).deviceAddr
//        doReturn(false).`when`(model).forwardPort(LOCALHOST)
//        controller.startProjection()
//        verify(BaseView.isLoaded, times(1)).value = false
//        assert(view.deviceAddr != null)
//        assert(!model.forwardPort(LOCALHOST))
//        verify(view.dialog).showDialog(ERROR, FORWARD_ERROR, true)
//    }
}
