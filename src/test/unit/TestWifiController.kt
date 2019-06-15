/*
 * Created by shadowsparky in 2019
 *
 */

package unit

import org.junit.jupiter.api.Test
import ru.shadowsparky.client.mvvm.models.WifiModel
import ru.shadowsparky.client.mvvm.viewmodels.WifiViewModel
import ru.shadowsparky.client.mvvm.views.BaseView
import ru.shadowsparky.client.mvvm.views.WifiView
import ru.shadowsparky.client.objects.Constants.LOCALHOST
import ru.shadowsparky.client.objects.Injection

class TestWifiController {
//    private val view: WifiView = mock()
//    private val model: WifiModel = mock()
//    private val controller = WifiViewModel(view, model)
//    private val log = Injection.provideLogger()
//
//    init {
//        BaseView.isLoaded = mock()
//        view.mInputText = mock()
//    }
//
//    @Test fun notEmptyStartProjection() {
//        given(view.mInputText.get()).willReturn(LOCALHOST)
//        controller.startProjection()
//        assert(view.mInputText.get().isNotEmpty())
//        assert(((view.projection == null) or (view.projection?.handling == false)))
//        assert(view.projection != null)
////        verifyNoMoreInteractions(view, model)
//    }
//
//    @Test fun alreadyStartedProjection() {
//        given(view.mInputText.get()).willReturn(LOCALHOST)
//        view.projection = Injection.provideProjectionWorker(view, view.mInputText.get())
//        view.projection?.handling = true
//        controller.startProjection()
//        assert(view.mInputText.get().isNotEmpty())
//        assert(!((view.projection == null) or (view.projection?.handling == false)))
//        verify(view).onError(any())
//        verifyNoMoreInteractions(view, model)
//    }
//
//    @Test fun incorrectIpAddr() {
//        given(view.mInputText.get()).willReturn("")
//        view.dialog = mock()
//        controller.startProjection()
//        view.dialog.showDialog(ERROR, INCORRECT_IP)
//        verifyNoMoreInteractions(view, model)
//    }
}