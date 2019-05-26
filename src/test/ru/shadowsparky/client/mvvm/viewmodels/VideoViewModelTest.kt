/*
 * Created by shadowsparky in 2019
 *
 */

package ru.shadowsparky.client.mvvm.viewmodels

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import ru.shadowsparky.client.ConnectionType
import ru.shadowsparky.client.mvvm.models.VideoModel
import ru.shadowsparky.client.mvvm.views.VideoView
import java.awt.Canvas

internal class VideoViewModelTest {
    private val view: VideoView = mock()
    private val model: VideoModel = mock()
    private val viewModel: VideoViewModel = VideoViewModel(view, ConnectionType.adb, model)

    init {
        Mockito.`when`(view.canvas).thenReturn(Canvas())
    }

    @Test
    fun onKeyPressed() {
        viewModel.onKeyPressed(null)

    }

    @Test
    fun onMouseClicked() {
    }

    @Test
    fun getFixedSize() {
    }

    @Test
    fun updateIncfelicity() {
    }

    @Test
    fun getType() {
    }
}