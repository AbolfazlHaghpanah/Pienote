package com.haghpanh.pienote.feature_category.ui.utils

import com.haghpanh.pienote.R

const val CATEGORY_DIALOG_ITEM_EDIT_NAME_ID = 0
const val CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID = 0
const val CATEGORY_DIALOG_ITEM_ADD_NOTE_ID = 0

data class CategoryDialogItem(
    val id: Int,
    val title: String,
    val icon: Int
)

val categoryDialogItems = listOf(
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_EDIT_NAME_ID,
        title = "Edit Name",
        icon = R.drawable.edit
    ),
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_CHANGE_COVER_ID,
        title = "Change Cover Image",
        icon = R.drawable.folder
    ),
    CategoryDialogItem(
        id = CATEGORY_DIALOG_ITEM_ADD_NOTE_ID,
        title = "Add Note",
        icon = R.drawable.description
    )
)