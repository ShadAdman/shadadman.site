package site.shadadman.components.ide

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun KotlinCodeView(
    codeLines: List<String>,
    modifier: Modifier = Modifier
) {
    val isLight = ColorMode.current.isLight
    val gutterBg = if (isLight) Color.rgb(0xF3F4F6) else Color.rgb(0x18191B)
    val gutterText = if (isLight) Color.rgb(0x9CA3AF) else Color.rgb(0x5A5D63)
    val editorBg = if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x1E1F22)
    val borderColor = if (isLight) Color.rgb(0xE5E7EB) else Color.rgb(0x2B2D30)

    Row(
        modifier
            .fillMaxWidth()
            .backgroundColor(editorBg)
            .fontFamily("JetBrains Mono", "Fira Code", "Consolas", "monospace")
            .fontSize(1.0.cssRem)
            .lineHeight(1.65)
    ) {
        // Line Numbers Gutter
        Column(
            Modifier
                .backgroundColor(gutterBg)
                .borderRight(1.px, LineStyle.Solid, borderColor)
                .padding(topBottom = 0.6.cssRem, leftRight = 0.85.cssRem)
                .color(gutterText)
                .userSelect(UserSelect.None)
        ) {
            codeLines.indices.forEach { index ->
                Div(Modifier.textAlign(TextAlign.End).toAttrs()) {
                    Text("${index + 1}")
                }
            }
        }

        // Code Content
        Column(
            Modifier
                .weight(1f)
                .padding(0.6.cssRem, 1.1.cssRem)
                .styleModifier { property("overflow-x", "auto") }
        ) {
            codeLines.forEach { line ->
                Div(Modifier.whiteSpace(WhiteSpace.Pre).toAttrs()) {
                    RenderSyntaxHighlightedLine(line, isLight)
                }
            }
        }
    }
}

@Composable
private fun RenderSyntaxHighlightedLine(line: String, isLight: Boolean) {
    val keywordColor = if (isLight) Color.rgb(0x0033B3) else Color.rgb(0xCF8E6D)
    val annotationColor = if (isLight) Color.rgb(0x9E880D) else Color.rgb(0xBBB529)
    val stringColor = if (isLight) Color.rgb(0x067D17) else Color.rgb(0x6A8759)
    val commentColor = if (isLight) Color.rgb(0x8C8C8C) else Color.rgb(0x7A7E85)
    val defaultColor = if (isLight) Color.rgb(0x1F2937) else Color.rgb(0xDFE1E5)

    val trimmed = line.trimStart()
    when {
        trimmed.startsWith("//") -> {
            Span(Modifier.color(commentColor).toAttrs()) { Text(line) }
        }
        trimmed.startsWith("@") -> {
            Span(Modifier.color(annotationColor).toAttrs()) { Text(line) }
        }
        trimmed.startsWith("package ") || trimmed.startsWith("import ") -> {
            val parts = line.split(" ", limit = 2)
            Span(Modifier.color(keywordColor).fontWeight(FontWeight.Bold).toAttrs()) { Text(parts[0] + " ") }
            if (parts.size > 1) {
                Span(Modifier.color(defaultColor).toAttrs()) { Text(parts[1]) }
            }
        }
        else -> {
            if (line.contains("\"")) {
                val parts = line.split("\"")
                parts.forEachIndexed { i, part ->
                    if (i % 2 == 1) {
                        Span(Modifier.color(stringColor).toAttrs()) { Text("\"$part\"") }
                    } else {
                        Span(Modifier.color(defaultColor).toAttrs()) { Text(part) }
                    }
                }
            } else {
                Span(Modifier.color(defaultColor).toAttrs()) { Text(line) }
            }
        }
    }
}
