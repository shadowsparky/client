package ru.shadowsparky.client

data class EncodedBuffer(
    val data: ByteArray,
    val length: Int //,
//    val flags: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EncodedBuffer

        if (!data.contentEquals(other.data)) return false
        if (length != other.length) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + length
        return result
    }
}