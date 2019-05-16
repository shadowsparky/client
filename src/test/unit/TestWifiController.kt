/*
 * Created by shadowsparky in 2019
 *
 */

package unit

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ru.shadowsparky.client.mvvm.viewmodels.WifiViewModel
import ru.shadowsparky.client.mvvm.models.WifiModel
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.mvvm.views.WifiView
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Injection

class TestWifiController {
    private val view: WifiView = mock()
    private val model: WifiModel = mock()
    private val controller = WifiViewModel(view, model)
    private val log = Injection.provideLogger()

    init {
        view.dialog = mock()
        BaseView.isLoaded = mock()
        view.mInputText = mock()
        Mockito.`when`(view.mInputText.get()).thenReturn(LOCALHOST)
        Mockito.`when`(view.projection).thenReturn(mock())
    }

    @Test fun notEmptyStartProjection() {
        controller.startProjection()
        verify(BaseView.isLoaded).value = false
        assert(view.mInputText.get().isNotEmpty())
        assert((view.projection == null) or (view.projection?.handling == false))
        verify(view.projection)!!.start()
    }

    @Test fun alreadyStartedProjeciton() {
        Mockito.`when`(view.projection!!.handling).thenReturn(true)
        controller.startProjection()
//        doReturn(true).`when`(view.projection.handling)
//        verify(BaseView.isLoaded).value = false
//        assert(view.mInputText.get().isNotEmpty())
//        assert(!((view.projection == null) or (view.projection?.handling == false)))

//        Mockito.`when`(view.projection?.handling).thenReturn(true)
    }
}