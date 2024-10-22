package pienote.commonui.utils

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.DoNotInline
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import com.haghpanh.pienote.R
import pienote.commonui.utils.MenuItemOption.Bold
import pienote.commonui.utils.MenuItemOption.Code
import pienote.commonui.utils.MenuItemOption.Copy
import pienote.commonui.utils.MenuItemOption.Cut
import pienote.commonui.utils.MenuItemOption.Paste
import pienote.commonui.utils.MenuItemOption.SelectAll
import pienote.commonui.utils.MenuItemOption.UnderLine

/**
 * A customized version of the [androidx.compose.ui.platform.AndroidTextToolbar] class that extends
 * the standard text toolbar with additional custom items and their corresponding functionality.
 *
 * This class integrates standard text operations (Copy, Paste, Cut, Select All) and allows
 * the addition of custom menu items such as Bold, Code, and Underline. The custom actions are triggered
 * via the [onCustomItemsRequest] callback.
 *
 * @property view The [View] on which the toolbar will be shown.
 * @property onCustomItemsRequest Optional callback for handling custom menu items like [MenuItemOption.Bold],
 * [MenuItemOption.Code], and [MenuItemOption.UnderLine].
 *
 * @constructor Creates an instance of [PienoteTextToolBar] with a custom [onCustomItemsRequest] handler.
 */
class PienoteTextToolBar(
    private val view: View,
    private val onCustomItemsRequest: ((MenuItemOption) -> Unit)? = null,
) : TextToolbar {

    private var actionMode: ActionMode? = null
    private val textActionModeCallback: TextActionModeCallback = TextActionModeCallback(
        onActionModeDestroy = {
            actionMode = null
        }
    )
    override var status: TextToolbarStatus = TextToolbarStatus.Hidden

    override fun hide() {
        status = TextToolbarStatus.Hidden
        actionMode?.finish()
        actionMode = null
    }

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        val shouldAddCustomRequesters = onCustomItemsRequest != null

        textActionModeCallback.rect = rect
        textActionModeCallback.onCopyRequested = onCopyRequested
        textActionModeCallback.onCutRequested = onCutRequested
        textActionModeCallback.onPasteRequested = onPasteRequested
        textActionModeCallback.onSelectAllRequested = onSelectAllRequested

        textActionModeCallback.onBoldRequested = if (shouldAddCustomRequesters) {
            { onCustomItemsRequest?.let { it(Bold) } }
        } else {
            null
        }

        textActionModeCallback.onCodeRequested = if (shouldAddCustomRequesters) {
            { onCustomItemsRequest?.let { it(Code) } }
        } else {
            null
        }

        textActionModeCallback.onUnderLineRequested = if (shouldAddCustomRequesters) {
            { onCustomItemsRequest?.let { it(UnderLine) } }
        } else {
            null
        }

        if (actionMode == null) {
            status = TextToolbarStatus.Shown
            actionMode =
                TextToolbarHelperMethods.startActionMode(
                    view,
                    FloatingTextActionModeCallback(textActionModeCallback),
                    ActionMode.TYPE_FLOATING
                )
        } else {
            actionMode?.invalidate()
            actionMode = null
        }
    }
}

private class TextActionModeCallback(
    val onActionModeDestroy: (() -> Unit)? = null,
    var rect: Rect = Rect.Zero,
    var onCopyRequested: (() -> Unit)? = null,
    var onPasteRequested: (() -> Unit)? = null,
    var onCutRequested: (() -> Unit)? = null,
    var onSelectAllRequested: (() -> Unit)? = null,
    var onBoldRequested: (() -> Unit)? = null,
    var onCodeRequested: (() -> Unit)? = null,
    var onUnderLineRequested: (() -> Unit)? = null,
) {
    fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        requireNotNull(menu) { "onCreateActionMode requires a non-null menu" }
        requireNotNull(mode) { "onCreateActionMode requires a non-null mode" }

        onBoldRequested?.let {
            addMenuItem(menu, Copy)
        }
        onCodeRequested?.let {
            addMenuItem(menu, Copy)
        }
        onUnderLineRequested?.let {
            addMenuItem(menu, Copy)
        }
        onCopyRequested?.let {
            addMenuItem(menu, Copy)
        }
        onPasteRequested?.let {
            addMenuItem(menu, Paste)
        }
        onCutRequested?.let {
            addMenuItem(menu, Cut)
        }
        onSelectAllRequested?.let {
            addMenuItem(menu, SelectAll)
        }
        return true
    }

    // this method is called to populate new menu items when the actionMode was invalidated
    fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        if (mode == null || menu == null) return false
        updateMenuItems(menu)
        // should return true so that new menu items are populated
        return true
    }

    fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            Copy.id -> onCopyRequested?.invoke()
            Paste.id -> onPasteRequested?.invoke()
            Cut.id -> onCutRequested?.invoke()
            SelectAll.id -> onSelectAllRequested?.invoke()
            Bold.id -> onBoldRequested?.invoke()
            UnderLine.id -> onUnderLineRequested?.invoke()
            Code.id -> onCodeRequested?.invoke()
            else -> return false
        }
        mode?.finish()
        return true
    }

    fun onDestroyActionMode() {
        onActionModeDestroy?.invoke()
    }

    private fun updateMenuItems(menu: Menu) {
        addOrRemoveMenuItem(menu, Copy, onCopyRequested)
        addOrRemoveMenuItem(menu, Paste, onPasteRequested)
        addOrRemoveMenuItem(menu, Cut, onCutRequested)
        addOrRemoveMenuItem(menu, SelectAll, onSelectAllRequested)
        addOrRemoveMenuItem(menu, Bold, onBoldRequested)
        addOrRemoveMenuItem(menu, Code, onCodeRequested)
        addOrRemoveMenuItem(menu, UnderLine, onUnderLineRequested)
    }

    private fun addMenuItem(menu: Menu, item: MenuItemOption) {
        menu.add(0, item.id, item.order, item.titleResource)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    private fun addOrRemoveMenuItem(
        menu: Menu,
        item: MenuItemOption,
        callback: (() -> Unit)?
    ) {
        when {
            callback != null && menu.findItem(item.id) == null -> addMenuItem(menu, item)
            callback == null && menu.findItem(item.id) != null -> menu.removeItem(item.id)
        }
    }
}

/**
 * Enum class representing various menu item options used in the text toolbar.
 * Each option corresponds to an action such as Bold, Code, Underline, Copy, Paste, Cut, or Select All.
 *
 * @property id The unique identifier for each [MenuItemOption], used for ordering and referencing.
 * @property titleResource The resource ID for the string label of the menu item,
 * corresponding to the action (e.g., Bold, Code, Underline) or Android system string resources
 * (e.g., Copy, Paste, Cut, Select All).
 * @property order The order in which the item should appear, determined by the [id].
 * Items with a lower [id] will be shown before items with a higher [id].
 */
enum class MenuItemOption(val id: Int) {
    Bold(0),
    Code(1),
    UnderLine(2),
    Copy(3),
    Paste(4),
    Cut(5),
    SelectAll(6);

    // Retrieves the appropriate string resource for each menu option
    val titleResource: Int
        get() = when (this) {
            Bold -> R.string.label_bold
            Code -> R.string.label_code
            UnderLine -> R.string.label_underline
            Copy -> android.R.string.copy
            Paste -> android.R.string.paste
            Cut -> android.R.string.cut
            SelectAll -> android.R.string.selectAll
        }

    /**
     * The order in which this item will be displayed in the menu.
     * Menu items are arranged based on their [id] value, with lower [id] items appearing first.
     */
    val order = id
}

/**
 * Modifies the provided string by adding or removing a prefix and suffix based on the [MenuItemOption].
 * If the string already contains the specified prefix and suffix, they are removed.
 * Otherwise, they are added. The prefix and suffix differ depending on the [MenuItemOption] selected.
 *
 * - [MenuItemOption.Bold] adds/removes "**" around the string.
 * - [MenuItemOption.Code] adds/removes "`" around the string.
 * - [MenuItemOption.UnderLine] adds/removes "_+" and "+_" around the string.
 *
 * @param value The string to be modified.
 * @return A [Pair] where:
 * - The first value is the string with the action performed (prefix/suffix added or removed).
 * - The second value is a [Boolean] indicating whether the prefix and suffix were removed (true) or added (false).
 */
fun MenuItemOption.performAction(value: String): Pair<String, Boolean> {
    val prefix = when (this) {
        Bold -> "**"
        Code -> "`"
        UnderLine -> "_+"
        else -> ""
    }
    val suffix = when (this) {
        Bold -> "**"
        Code -> "`"
        UnderLine -> "+_"
        else -> ""
    }

    return if (value.startsWith(prefix) && value.endsWith(suffix)) {
        Pair(value.removePrefix(prefix).removeSuffix(suffix), true)
    } else {
        Pair("$prefix$value$suffix", false)
    }
}

/**
 * returns prefix for given [MenuItemOption] item.
 */
fun MenuItemOption.getPrefixOrNull(): String? {
    return when (this) {
        Bold -> "**"
        Code -> "`"
        UnderLine -> "_+"
        else -> null
    }
}

private object TextToolbarHelperMethods {
    @DoNotInline
    fun startActionMode(
        view: View,
        actionModeCallback: ActionMode.Callback,
        type: Int
    ): ActionMode? {
        return view.startActionMode(
            actionModeCallback,
            type
        )
    }
}

private class FloatingTextActionModeCallback(
    private val callback: TextActionModeCallback
) : ActionMode.Callback2() {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return callback.onActionItemClicked(mode, item)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onCreateActionMode(mode, menu)
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onPrepareActionMode(mode, menu)
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        callback.onDestroyActionMode()
    }

    override fun onGetContentRect(
        mode: ActionMode?,
        view: View?,
        outRect: android.graphics.Rect?
    ) {
        val rect = callback.rect
        outRect?.set(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )
    }
}
