/*
 * Created by shadowsparky in 2019
 */

@file:Suppress("NON_EXHAUSTIVE_WHEN")

package ru.shadowsparky.client.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import javafx.stage.WindowEvent
import ru.shadowsparky.client.client.Client
import ru.shadowsparky.client.utils.Controllerable
import ru.shadowsparky.client.utils.ImageCallback
import ru.shadowsparky.client.utils.Injection
import ru.shadowsparky.client.utils.adb.ADBStatus
import java.awt.Dimension
import java.awt.Toolkit


class VideoController : ImageCallback, Controllerable {
    private var client: Client? = null
    @FXML private lateinit var image: ImageView
    private val log = Injection.provideLogger()
    private var stage: Stage? = null
    private var infelicity_width: Double = 0.0
    private var infelicity_height: Double = 0.0
    private val adb =  Injection.provideAdb()

    override fun handleImage(image: Image) = Platform.runLater {
        this.image.image = image
        this.image.fitHeight = getScreenSize(image).getHeight()
        this.image.fitWidth = getScreenSize(image).getWidth()
        infelicity_width = image.width / this.image.fitWidth
        infelicity_height = image.height / this.image.fitHeight
    }

    private fun getScreenSize(image: Image) : Dimension {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        if (image.width < image.height) {
            val height = screenSize.height
            screenSize.width = (height * 0.6).toInt()
        }
        return screenSize
    }

    fun onDestroy(event: WindowEvent) {
        log.printInfo("Window destroyed")
        client!!.handling = false
    }

    fun attachClient(client: Client) {
        this.client = client
    }

    private fun enableADBActions() {
        setupMouse()
        setupKeyboard()
    }

    private fun setupKeyboard() {
        stage!!.scene!!.setOnKeyPressed {
            when(it.code) {
                KeyCode.UP ->  adb.invokeScrollUp()
                KeyCode.DOWN -> adb.invokeScrollDown()
                KeyCode.LEFT-> adb.invokeScrollLeft()
                KeyCode.RIGHT -> adb.invokeScrollRight()
                KeyCode.SHIFT -> adb.invokeRecentApplicationsButton()
            }
        }
    }

    //FIXME Неправильно работает в вертикальном режиме
    private fun setupMouse() {
        image.addEventHandler(MouseEvent.MOUSE_CLICKED) {
            when(it.button) {
                MouseButton.PRIMARY -> {
                    val x = infelicity_width * it.x
                    val y = infelicity_height * it.y
                    val res = adb.tapToScreen(x, y)
                    if (res.status == ADBStatus.ERROR) {
                        log.printError("TAP ERROR: ${res.info}")
                        return@addEventHandler
                    }
                    log.printInfo("CoordX: (${x}) ${it.x} CoordY: (${y}) ${it.y}")
                }
                MouseButton.SECONDARY -> adb.invokeBackButton()
                MouseButton.MIDDLE -> adb.invokeHomeButton()
            }
        }
    }

    fun attachStage(stage: Stage?) {
        this.stage = stage
        if (this.client!!.addr == "127.0.0.1") {
            enableADBActions()
        }
    }

    fun start() {
        client!!.start()
    }

    fun stop() {
        client?.handling = false
    }

}