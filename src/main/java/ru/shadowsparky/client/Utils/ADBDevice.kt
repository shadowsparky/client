package ru.shadowsparky.client.Utils

data class ADBDevice(
    val id: String,
    val model: String
) {
    override fun toString(): String {
        return "$model, id:$id"
    }

    companion object {
        fun parseDevice(str: String) : ADBDevice? {
            val model = str.substringBefore(", id:")
            val id = str.substringAfter(", id:")
            if ((model.isNotEmpty()) && (id.isNotEmpty()))
                return (ADBDevice(id, model))
            return null
        }
    }
}