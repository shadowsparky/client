/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.testfx.api.FxToolkit
import ru.shadowsparky.client.mvvm.models.AdbModel
import ru.shadowsparky.client.mvvm.views.AdbView
import tornadofx.success
import tornadofx.ui

internal class AdbViewModelTest {
    private val model: AdbModel = mock()
    private val view: AdbView = mock()
    private val vm = spy(AdbViewModel(view, model))


    init {
        FxToolkit.registerPrimaryStage()
    }

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun updateDevices() {
        whenever(vm.items).thenReturn(mock())
        vm.updateDevices().ui {
            verify(model).getDevicesRequest()
            verify(vm.items.get()).clear()
        }
    }

    @Test
    fun showHelp() {
    }

    @Test
    fun startProjection() {
    }

    @Test
    fun isDisable() {
    }

    @Test
    fun getDevice() {
    }

    @Test
    fun setDevice() {
    }

    @Test
    fun getItems() {
    }
}