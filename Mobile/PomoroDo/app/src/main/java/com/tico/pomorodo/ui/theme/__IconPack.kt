package com.tico.pomorodo.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.tico.pomorodo.ui.iconpack.IcTitle
import kotlin.collections.List as ____KtList

object IconPack

private var __Logo: ____KtList<ImageVector>? = null

val IconPack.Logo: ____KtList<ImageVector>
    get() {
        if (__Logo != null) {
            return __Logo!!
        }
        __Logo = listOf(IcTitle)
        return __Logo!!
    }
