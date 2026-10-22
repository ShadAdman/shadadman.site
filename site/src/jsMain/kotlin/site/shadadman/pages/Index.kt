package site.shadadman.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import site.shadadman.components.ide.KotlinCodeView
import site.shadadman.components.ide.LocalViewMode
import site.shadadman.components.ide.ViewMode
import site.shadadman.components.layouts.PageLayoutData

val PulseAnimation = Keyframes {
    0.percent { Modifier.opacity(1f) }
    50.percent { Modifier.opacity(0.4f) }
    100.percent { Modifier.opacity(1f) }
}

val AndroidCardStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .fillMaxWidth()
            .backgroundColor(if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x131B2E))
            .borderRadius(1.25.cssRem)
            .padding(1.5.cssRem)
            .styleModifier {
                property("box-shadow", if (isLight) "0 10px 30px rgba(0, 0, 0, 0.05)" else "0 10px 30px rgba(0, 0, 0, 0.35)")
            }
            .border(1.px, LineStyle.Solid, if (isLight) Color.rgb(0xE2E8F0) else Color.rgb(0x1E293B))
    }
}

val TagPillStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .backgroundColor(if (isLight) Color.rgb(0xE8F5E9) else Color.rgb(0x103422))
            .color(if (isLight) Color.rgb(0x2E7D32) else Color.rgb(0x4CD964))
            .padding(0.35.cssRem, 0.8.cssRem)
            .borderRadius(2.cssRem)
            .fontSize(0.8.cssRem)
            .fontWeight(FontWeight.SemiBold)
            .display(DisplayStyle.InlineBlock)
            .margin(0.2.cssRem)
    }
}

val AndroidBadgeStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .backgroundColor(if (isLight) Color.rgb(0xE0F2FE) else Color.rgb(0x0C2D48))
            .color(if (isLight) Color.rgb(0x0284C7) else Color.rgb(0x38BDF8))
            .padding(0.35.cssRem, 0.8.cssRem)
            .borderRadius(2.cssRem)
            .fontSize(0.8.cssRem)
            .fontWeight(FontWeight.Medium)
            .display(DisplayStyle.InlineBlock)
            .margin(0.2.cssRem)
    }
}

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Index.kt"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    val viewMode = LocalViewMode.current

    val kotlinCodeLines = listOf(
        "package site.shadadman.pages",
        "",
        "import site.shadadman.developer.AndroidEngineer",
        "import site.shadadman.company.Alopeyk",
        "",
        "@Page",
        "@Composable",
        "fun HomePage() {",
        "    val developer = AndroidEngineer(",
        "        name = \"Shad Adman\",",
        "        title = \"Senior Android Engineer @ Alopeyk\",",
        "        scale = \"10M+ Users & 500K Active Couriers\",",
        "        bio = \"\"\"",
        "            I fell in love with coding when introduced to C++ in my student days,",
        "            and soon found myself deep in the world of Android. Over the years,",
        "            I've built robust systems across Health Tech, Digital Retail, Trading,",
        "            Fintech, and iGaming using Android & Kotlin Multiplatform.",
        "",
        "            I am fascinated by on-device AI. Give me a quirky smart home gadget",
        "            or an old Raspberry Pi, and I can bring it to life—intelligent and",
        "            fully offline, without relying on the cloud.",
        "",
        "            Currently focusing on Kotlin Multiplatform, bridging non-Kotlin languages/platforms",
        "            with Kotlin, tackling low-level systems, and talking directly to hardware.",
        "        \"\"\".trimIndent(),",
        "        specializations = listOf(",
        "            \"Android Architecture\", \"Kotlin Multiplatform (KMP)\",",
        "            \"On-Device AI\", \"C++ Low-Level Interop\", \"Hardware & BLE\",",
        "            \"Health Tech\", \"Fintech & Trading\", \"iGaming\", \"Logistics\"",
        "        )",
        "    )",
        "}"
    )

    Row(
        Modifier.fillMaxSize()
    ) {
        if (viewMode == ViewMode.CODE) {
            Box(
                Modifier.fillMaxSize()
            ) {
                KotlinCodeView(kotlinCodeLines)
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(1.25.cssRem)
                    .styleModifier { property("overflow-y", "auto") }
            ) {
                RenderHomePreview()
            }
        }
    }
}

@Composable
private fun RenderHomePreview() {
    val isLight = ColorMode.current.isLight
    val primaryColor = if (isLight) Color.rgb(0x2E7D32) else Color.rgb(0x3DDC84)

    Column(
        Modifier
            .fillMaxWidth()
            .gap(1.5.cssRem),
        horizontalAlignment = Alignment.Start
    ) {
        // Status Pill
        Div(
            Modifier
                .backgroundColor(if (isLight) Color.rgb(0xE8F5E9) else Color.rgb(0x112C20))
                .border(1.px, LineStyle.Solid, primaryColor.copyf(alpha = 0.4f))
                .borderRadius(2.cssRem)
                .padding(0.4.cssRem, 1.cssRem)
                .display(DisplayStyle.Flex)
                .alignItems(org.jetbrains.compose.web.css.AlignItems.Center)
                .gap(0.6.cssRem)
                .toAttrs()
        ) {
            Span(
                Modifier
                    .width(10.px)
                    .height(10.px)
                    .borderRadius(50.percent)
                    .backgroundColor(primaryColor)
                    .animation(PulseAnimation.toAnimation(duration = 2.s, iterationCount = AnimationIterationCount.Infinite))
                    .toAttrs()
            ) {}
            Span(
                Modifier
                    .color(primaryColor)
                    .fontSize(0.85.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .toAttrs()
            ) {
                Text("Senior Android Engineer @ Alopeyk • 10M+ Users & 500K Couriers")
            }
        }

        // Hero Title
        Column(Modifier.fillMaxWidth().gap(0.5.cssRem)) {
            H1(
                Modifier
                    .fontSize(clamp(2.cssRem, 4.vw, 3.2.cssRem))
                    .fontWeight(FontWeight.Bold)
                    .lineHeight(1.15)
                    .margin(0.px)
                    .toAttrs()
            ) {
                SpanText("Building")
                Br()
                SpanText("Android & Multiplatform Systems", Modifier.color(primaryColor))
            }

            P(
                Modifier
                    .fontSize(1.05.cssRem)
                    .lineHeight(1.5)
                    .color(if (isLight) Color.rgb(0x475569) else Color.rgb(0x94A3B8))
                    .margin(0.px)
                    .toAttrs()
            ) {
                Text("Specializing in deep Android architecture, Kotlin Multiplatform, on-device intelligence, and low-level hardware interoperability.")
            }
        }

        // Position Card
        Div(AndroidCardStyle.toModifier().toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(1.cssRem)) {
                Row(
                    Modifier.fillMaxWidth().alignItems(org.jetbrains.compose.web.css.AlignItems.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(Modifier.gap(0.4.cssRem)) {
                        Span(Modifier.width(10.px).height(10.px).borderRadius(50.percent).backgroundColor(Color.rgb(0xFF5F56)).toAttrs()) {}
                        Span(Modifier.width(10.px).height(10.px).borderRadius(50.percent).backgroundColor(Color.rgb(0xFFBD2E)).toAttrs()) {}
                        Span(Modifier.width(10.px).height(10.px).borderRadius(50.percent).backgroundColor(Color.rgb(0x27C93F)).toAttrs()) {}
                    }
                    Spacer()
                    Span(Modifier.fontSize(0.8.cssRem).color(Color.rgb(0x64748B)).fontFamily("monospace").toAttrs()) {
                        Text("current_role.kt")
                    }
                }

                Div(Modifier.height(1.px).backgroundColor(if (isLight) Color.rgb(0xF1F5F9) else Color.rgb(0x1E293B)).fillMaxWidth().toAttrs()) {}

                Column(Modifier.fillMaxWidth().gap(0.4.cssRem)) {
                    Span(Modifier.fontSize(0.8.cssRem).fontWeight(FontWeight.Bold).color(primaryColor).toAttrs()) {
                        Text("CURRENT POSITION")
                    }
                    H3(Modifier.fontSize(1.25.cssRem).fontWeight(FontWeight.Bold).margin(0.px).toAttrs()) {
                        Text("Senior Android Engineer @ Alopeyk")
                    }
                    P(Modifier.fontSize(0.95.cssRem).lineHeight(1.5).color(if (isLight) Color.rgb(0x334155) else Color.rgb(0xCBD5E1)).margin(0.px).toAttrs()) {
                        Text("Working on the core courier application for delivery logistics, empowering over 10 million users and 500,000 active couriers with 300K daily orders.")
                    }
                }
            }
        }

        // Bio Card
        Div(AndroidCardStyle.toModifier().toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(1.cssRem)) {
                H3(Modifier.fontSize(1.25.cssRem).fontWeight(FontWeight.Bold).margin(0.px).toAttrs()) {
                    Text("About Me & Journey")
                }

                P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(if (isLight) Color.rgb(0x334155) else Color.rgb(0xCBD5E1)).margin(0.px).toAttrs()) {
                    Text("I fell in love with coding when introduced to C++ in my student days, and soon found myself deep in the world of Android. Over the years, I've built robust systems across Health Tech, Digital Retail, Trading, Fintech, and iGaming using Android & Kotlin Multiplatform.")
                }

                P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(if (isLight) Color.rgb(0x334155) else Color.rgb(0xCBD5E1)).margin(0.px).toAttrs()) {
                    Text("I am fascinated by on-device AI. Give me a quirky smart home gadget or an old Raspberry Pi, and I can bring it to life—intelligent and fully offline, without relying on the cloud.")
                }

                P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(if (isLight) Color.rgb(0x334155) else Color.rgb(0xCBD5E1)).margin(0.px).toAttrs()) {
                    Text("Currently focusing on Kotlin Multiplatform, bridging non-Kotlin languages/platforms with Kotlin, tackling low-level systems, and talking directly to hardware.")
                }
            }
        }

        // Expertise Pills
        Div(AndroidCardStyle.toModifier().toAttrs()) {
            Column(Modifier.fillMaxWidth().gap(0.8.cssRem)) {
                H3(Modifier.fontSize(1.2.cssRem).fontWeight(FontWeight.Bold).margin(0.px).toAttrs()) {
                    Text("Expertise & Domains")
                }
                Div(Modifier.display(DisplayStyle.Flex).flexWrap(FlexWrap.Wrap).gap(0.3.cssRem).toAttrs()) {
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("Android") }
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("Kotlin Multiplatform (KMP)") }
                    Span(AndroidBadgeStyle.toModifier().toAttrs()) { Text("On-Device AI & TFLite") }
                    Span(AndroidBadgeStyle.toModifier().toAttrs()) { Text("C++ / Low-Level Interop") }
                    Span(AndroidBadgeStyle.toModifier().toAttrs()) { Text("Hardware & Bluetooth") }
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("Health Tech") }
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("Fintech & Trading") }
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("iGaming") }
                    Span(TagPillStyle.toModifier().toAttrs()) { Text("Digital Retail & Logistics") }
                }
            }
        }

        // Quick Links
        Row(
            Modifier.fillMaxWidth().gap(0.75.cssRem).flexWrap(FlexWrap.Wrap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Link(
                "/worklog/worklog",
                "Explore WorkLog",
                Modifier
                    .backgroundColor(primaryColor)
                    .color(if (isLight) Colors.White else Color.rgb(0x06080B))
                    .padding(0.6.cssRem, 1.25.cssRem)
                    .borderRadius(0.75.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .textDecorationLine(TextDecorationLine.None),
                variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
            )
            Link(
                "/resume",
                "View Full Resume",
                Modifier
                    .backgroundColor(if (isLight) Color.rgb(0xE2E8F0) else Color.rgb(0x1E293B))
                    .color(if (isLight) Color.rgb(0x0F172A) else Color.rgb(0xF8FAFC))
                    .padding(0.6.cssRem, 1.25.cssRem)
                    .borderRadius(0.75.cssRem)
                    .fontWeight(FontWeight.SemiBold)
                    .textDecorationLine(TextDecorationLine.None),
                variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
            )
        }
    }
}
