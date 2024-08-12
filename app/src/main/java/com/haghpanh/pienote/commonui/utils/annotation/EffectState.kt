package com.haghpanh.pienote.commonui.utils.annotation

/**
 * This annotation used for view states fields that used to send an effect to screen
 * like showing snackbar based on them or doing some ui changes.
 *
 * parameters that has this annotation will reset after disposing screen.
 *
 * Just works when you used handleEffectsDispose function from BaseViewModel
 *
 * @see com.haghpanh.pienote.commonui.BaseViewModel.handleEffectsDispose
 */
@Target(
    AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.RUNTIME)
annotation class EffectState
