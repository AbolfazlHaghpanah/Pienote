package pienote.commondomain.model

data class NoteDomainModel(
    val id: Int,
    val title: String,
    val note: String,
    val image: String? = null,
    val addedTime: String,
    val lastChangedTime: String? = null,
    val categoryId: Int? = null,
    val color: String? = null
)
