/*
 * Copyright (c) 2015 VA programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ru.vas7n.va.widgets


import com.jfoenix.controls.JFXTextField
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.scene.control.TextField

import java.util.ArrayList

class MaskField : JFXTextField() {


    private var objectMask: MutableList<Position> = ArrayList()

    /**
     * простой текст без применения маски
     */
    private var plainText: StringProperty? = null


    /**
     * это сама маска видимая в поле ввода
     */
    private var mask: StringProperty? = null


    /**
     * если маска должна отображать символы которые зарезервированы для маски, то задается дополнительная подсказка где символ маски, а где просто символ
     */
    private var whatMask: StringProperty? = null


    /**
     * это символы замещения
     */
    private var placeholder: StringProperty? = null

    fun getPlainText(): String {
        return plainTextProperty().get()
    }

    fun setPlainText(value: String) {
        plainTextProperty().set(value)
        updateShowingField()
    }

    fun plainTextProperty(): StringProperty {
        if (plainText == null)
            plainText = SimpleStringProperty(this, "plainText", "")
        return plainText!!
    }

    fun getMask(): String {
        return maskProperty().get()
    }

    fun setMask(value: String) {
        maskProperty().set(value)
        rebuildObjectMask()
        updateShowingField()
    }

    fun maskProperty(): StringProperty {
        if (mask == null)
            mask = SimpleStringProperty(this, "mask")

        return mask!!
    }

    fun getWhatMask(): String? {
        return whatMaskProperty().get()
    }

    fun setWhatMask(value: String) {
        whatMaskProperty().set(value)
        rebuildObjectMask()
        updateShowingField()
    }

    fun whatMaskProperty(): StringProperty {
        if (whatMask == null) {
            whatMask = SimpleStringProperty(this, "whatMask")
        }
        return whatMask!!
    }

    fun getPlaceholder(): String? {
        return placeholderProperty().get()
    }

    fun setPlaceholder(value: String) {
        placeholderProperty().set(value)
        rebuildObjectMask()
        updateShowingField()
    }

    fun placeholderProperty(): StringProperty {
        if (placeholder == null)
            placeholder = SimpleStringProperty(this, "placeholder")
        return placeholder!!
    }


    private inner class Position(var mask: Char, var whatMask: Char, var placeholder: Char) {

        val isPlainCharacter: Boolean
            get() = whatMask == WHAT_MASK_CHAR

        fun isCorrect(c: Char): Boolean {
            when (mask) {
                MASK_DIGIT -> return Character.isDigit(c)
                MASK_CHARACTER -> return Character.isLetter(c)
                MASK_DIG_OR_CHAR -> return Character.isLetter(c) || Character.isDigit(c)
            }
            return false
        }
    }


    /**
     * формирует список объектов Position по каждому символу маски
     */
    private fun rebuildObjectMask() {
        objectMask = ArrayList()

        for (i in 0 until getMask().length) {
            val m = getMask()[i]
            var w = WHAT_MASK_CHAR
            var p = PLACEHOLDER_CHAR_DEFAULT

            if (getWhatMask() != null && i < getWhatMask()!!.length) {
                //конкретно указано символ маски это или нет
                if (getWhatMask()!![i] != WHAT_MASK_CHAR) {
                    w = WHAT_MASK_NO_CHAR
                }
            } else {
                //так как не указано что за символ - понимаем самостоятельно
                //и если символ не находится среди символов маски - то это считается простым литералом
                if (m != MASK_CHARACTER && m != MASK_DIG_OR_CHAR && m != MASK_DIGIT)
                    w = WHAT_MASK_NO_CHAR

            }

            if (getPlaceholder() != null && i < getPlaceholder()!!.length)
                p = getPlaceholder()!![i]

            objectMask.add(Position(m, w, p))
        }
    }


    /**
     * функция как бы накладывает просто текст plainText на заданную маску,
     * корректирует позицию каретки
     */
    private fun updateShowingField() {
        var counterPlainCharInMask = 0
        var lastPositionPlainCharInMask = 0
        var firstPlaceholderInMask = -1
        var textMask = ""
        var textPlain = getPlainText()
        for (i in objectMask.indices) {
            val p = objectMask[i]
            if (p.isPlainCharacter) {
                if (textPlain.length > counterPlainCharInMask) {

                    var c = textPlain[counterPlainCharInMask]
                    while (!p.isCorrect(c)) {
                        //вырезаем то что не подошло
                        textPlain = textPlain.substring(0, counterPlainCharInMask) + textPlain.substring(counterPlainCharInMask + 1)

                        if (textPlain.length > counterPlainCharInMask)
                            c = textPlain[counterPlainCharInMask]
                        else
                            break
                    }

                    textMask += c
                    lastPositionPlainCharInMask = i
                } else {
                    textMask += p.placeholder
                    if (firstPlaceholderInMask == -1)
                        firstPlaceholderInMask = i
                }

                counterPlainCharInMask++

            } else {
                textMask += p.mask
            }
        }

        text = textMask

        if (firstPlaceholderInMask == -1)
            firstPlaceholderInMask = 0

        val caretPosition = if (textPlain.length > 0) lastPositionPlainCharInMask + 1 else firstPlaceholderInMask
        selectRange(caretPosition, caretPosition)

        if (textPlain.length > counterPlainCharInMask)
            textPlain = textPlain.substring(0, counterPlainCharInMask)

        if (textPlain != getPlainText())
            setPlainText(textPlain)

    }


    private fun interpretMaskPositionInPlainPosition(posMask: Int): Int {
        var posPlain = 0

        var i = 0
        while (i < objectMask.size && i < posMask) {
            val p = objectMask[i]
            if (p.isPlainCharacter)
                posPlain++
            i++
        }

        return posPlain
    }


    override fun replaceText(start: Int, end: Int, text: String) {


        val plainStart = interpretMaskPositionInPlainPosition(start)
        val plainEnd = interpretMaskPositionInPlainPosition(end)

        var plainText1 = ""
        if (getPlainText().length > plainStart)
            plainText1 = getPlainText().substring(0, plainStart)
        else
            plainText1 = getPlainText()


        var plainText2 = ""
        if (getPlainText().length > plainEnd)
            plainText2 = getPlainText().substring(plainEnd)
        else
            plainText2 = ""


        setPlainText(plainText1 + text + plainText2)

    }

    companion object {


        /**
         * позиция в маске позволит ввести только цифры
         */
        val MASK_DIGIT = 'D'

        /**
         * позиция в маске позволит ввести буквы и цифры
         */
        val MASK_DIG_OR_CHAR = 'W'

        /**
         * позиция в маске позволит ввести только буквы
         */
        val MASK_CHARACTER = 'A'


        val WHAT_MASK_CHAR = '#'
        val WHAT_MASK_NO_CHAR = '-'


        val PLACEHOLDER_CHAR_DEFAULT = '_'
    }


}