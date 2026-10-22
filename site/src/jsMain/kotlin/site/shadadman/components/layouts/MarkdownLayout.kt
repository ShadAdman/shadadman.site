package site.shadadman.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toAttrs
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.varabyte.kobwebx.markdown.markdown
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import site.shadadman.toSitePalette

val MarkdownStyle = CssStyle {
    base { Modifier.fillMaxSize() }

    cssRule("h1") {
        Modifier
            .fontSize(2.4.cssRem)
            .fontWeight(600)
            .margin(bottom = 1.5.cssRem)
            .lineHeight(1.2)
    }

    cssRule("h2") {
        Modifier
            .fontSize(1.8.cssRem)
            .fontWeight(600)
            .margin(topBottom = 1.25.cssRem)
    }

    cssRule("h3") {
        Modifier
            .fontSize(1.4.cssRem)
            .fontWeight(500)
            .margin(topBottom = 1.cssRem)
    }

    cssRule("h4") {
        Modifier
            .fontSize(1.1.cssRem)
            .fontWeight(FontWeight.Bolder)
            .margin(top = 0.8.cssRem, bottom = 0.4.cssRem)
    }

    cssRule("ul") {
        Modifier.fillMaxWidth().overflowWrap(OverflowWrap.BreakWord)
    }

    cssRule(" :is(li,ol,ul)") {
        Modifier.margin(bottom = 0.25.cssRem)
    }

    cssRule("code") {
        Modifier
            .color(colorMode.toPalette().color.toRgb().copyf(alpha = 0.8f))
            .fontWeight(FontWeight.Bolder)
    }

    cssRule("pre") {
        Modifier
            .margin(top = 0.5.cssRem, bottom = 1.5.cssRem)
            .fillMaxWidth()
    }

    cssRule("pre > code") {
        Modifier
            .display(DisplayStyle.Block)
            .fillMaxWidth()
            .backgroundColor(colorMode.toSitePalette().nearBackground)
            .border(1.px, LineStyle.Solid, colorMode.toPalette().color)
            .borderRadius(0.25.cssRem)
            .padding(0.5.cssRem)
            .fontSize(0.9.cssRem)
            .overflow { x(Overflow.Auto) }
    }
}

@InitRoute
fun initMarkdownLayout(ctx: InitRouteContext) {
    val filename = ctx.route.path.split("/").lastOrNull()?.let { if (it.endsWith(".md")) it else "$it.md" } ?: "Document.md"
    ctx.data.add(PageLayoutData(filename))
}

@Composable
@Layout(".components.layouts.PageLayout")
fun MarkdownLayout(content: @Composable () -> Unit) {
    Row(
        Modifier.fillMaxSize().flexWrap(FlexWrap.Wrap)
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(1.5.cssRem)
                .styleModifier { property("overflow-y", "auto") }
        ) {
            Div(MarkdownStyle.toAttrs()) {
                content()
            }
        }
    }
}
