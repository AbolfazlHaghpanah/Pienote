package com.haghpanah.pienote.category.component

import org.jetbrains.compose.resources.DrawableResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.description
import pienote.ui.base.generated.resources.edit
import pienote.ui.base.generated.resources.folder

const val CATEGORY_DIALOG_ITEM_EDIT_NAME_ID = 0
const val CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID = 1
const val CATEGORY_DIALOG_ITEM_ADD_NOTE_ID = 2

data class CategoryDialogItem(
    val id: Int,
    val title: String,
    val icon: DrawableResource,
)

val categoryDialogItems = listOf(
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_EDIT_NAME_ID,
        title = "Edit Name",
        icon = Res.drawable.edit
    ),
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID,
        title = "Change Cover Image",
        icon = Res.drawable.folder
    ),
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_ADD_NOTE_ID,
        title = "Add Note",
        icon = Res.drawable.description
    )
)