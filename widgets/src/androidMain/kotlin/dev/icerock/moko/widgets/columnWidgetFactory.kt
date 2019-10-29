/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets

import android.view.ViewGroup
import android.widget.LinearLayout
import dev.icerock.moko.widgets.core.VFC
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.ext.applyPadding
import dev.icerock.moko.widgets.style.ext.toLinearLayoutOrientation
import dev.icerock.moko.widgets.style.ext.toPlatformSize

actual var linearWidgetViewFactory: VFC<LinearWidget> = { context, widget ->
    val ctx = context.context
    val dm = ctx.resources.displayMetrics
    val style = widget.style

    val container = LinearLayout(ctx).apply {
        layoutParams = ViewGroup.LayoutParams(
            style.size.width.toPlatformSize(dm),
            style.size.height.toPlatformSize(dm)
        )
        orientation = style.orientation.toLinearLayoutOrientation()

        applyPadding(style.padding)
    }

    widget.childs.forEach { child ->
        val view = child.buildView(
            ViewFactoryContext(
                context = ctx,
                lifecycleOwner = context.lifecycleOwner,
                parent = container
            )
        )
        container.addView(view)
    }

    container
}