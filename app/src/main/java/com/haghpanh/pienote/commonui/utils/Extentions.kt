package com.haghpanh.pienote.commonui.utils

fun <T> List<T>.chunkedEven(): List<List<T>> {
    if (this.isEmpty()) return emptyList()
    val firstChunkSize = if (this.size % 2 == 0) 2 else 1
    val firstChunk = this.take(firstChunkSize)
    val remainingChunks = this.drop(firstChunkSize).chunked(2)
    return listOf(firstChunk) + remainingChunks
}
