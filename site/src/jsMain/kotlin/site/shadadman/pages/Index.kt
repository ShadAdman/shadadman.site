package site.shadadman.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.css.functions.clamp
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.animation.Keyframes
import com.varabyte.kobweb.silk.style.animation.toAnimation
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.style.breakpoint.displayUntil
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import site.shadadman.components.layouts.PageLayoutData
import site.shadadman.components.widgets.IconButton

val SpinAnimation = Keyframes {
    0.percent { Modifier.rotate(0.deg) }
    100.percent { Modifier.rotate(360.deg) }
}

val TextFillKeyframe = Keyframes {
    0.percent { Modifier.styleModifier { property("background-size", "0% 100%") } }
    100.percent { Modifier.styleModifier { property("background-size", "100% 100%") } }
}

val MarqueeLeftAnimation = Keyframes {
    0.percent { Modifier.styleModifier { property("transform", "translateX(0%)") } }
    100.percent { Modifier.styleModifier { property("transform", "translateX(-50%)") } }
}

val MarqueeRightAnimation = Keyframes {
    0.percent { Modifier.styleModifier { property("transform", "translateX(-50%)") } }
    100.percent { Modifier.styleModifier { property("transform", "translateX(0%)") } }
}

val KlioCardStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .fillMaxWidth()
            .backgroundColor(if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x18191C))
            .borderRadius(1.25.cssRem)
            .padding(1.5.cssRem)
            .border(1.px, LineStyle.Solid, if (isLight) Color.rgb(0xE4E4E7) else Color.rgb(0x27272A))
            .styleModifier {
                property("box-shadow", if (isLight) "0 4px 20px rgba(0, 0, 0, 0.03)" else "0 4px 20px rgba(0, 0, 0, 0.25)")
            }
    }
}

val TechTagStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .backgroundColor(if (isLight) Color.rgb(0xF4F4F5) else Color.rgb(0x27272A))
            .color(if (isLight) Color.rgb(0x18181B) else Color.rgb(0xF4F4F5))
            .padding(0.45.cssRem, 1.cssRem)
            .borderRadius(2.cssRem)
            .fontSize(0.9.cssRem)
            .fontWeight(FontWeight.Medium)
            .display(DisplayStyle.InlineBlock)
            .margin(0.3.cssRem)
    }
}

val StatNumberStyle = CssStyle {
    base {
        val isLight = colorMode.isLight
        Modifier
            .flex(1)
            .minWidth(160.px)
            .backgroundColor(if (isLight) Color.rgb(0xFBFBFB) else Color.rgb(0x141518))
            .border(1.px, LineStyle.Solid, if (isLight) Color.rgb(0xE4E4E7) else Color.rgb(0x27272A))
            .borderRadius(1.cssRem)
            .padding(1.25.cssRem)
    }
}

@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Index"))
}

@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage() {
    var colorMode by ColorMode.currentState
    val isLight = colorMode.isLight

    val bgMain = if (isLight) Color.rgb(0xFAFAFA) else Color.rgb(0x0F0F11)
    val textColor = if (isLight) Color.rgb(0x18181B) else Color.rgb(0xF4F4F5)
    val mutedColor = if (isLight) Color.rgb(0x71717A) else Color.rgb(0xA1A1AA)
    val accentGreen = if (isLight) Color.rgb(0x10B981) else Color.rgb(0x34D399)
    val imageCardBg = if (isLight) Color.rgb(0xA2D2C8) else Color.rgb(0x1C3A34)

    Box(
        Modifier
            .fillMaxSize()
            .backgroundColor(bgMain)
            .color(textColor)
            .fontFamily("Inter", "system-ui", "-apple-system", "sans-serif")
            .lineHeight(1.6)
    ) {
        Div(
            Modifier
                .fillMaxWidth()
                .maxWidth(1120.px)
                .styleModifier {
                    property("margin-left", "auto")
                    property("margin-right", "auto")
                }
                .padding(topBottom = 3.cssRem, leftRight = 1.5.cssRem)
                .toAttrs()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .gap(3.cssRem)
                    .flexWrap(FlexWrap.Wrap)
                    .alignItems(org.jetbrains.compose.web.css.AlignItems.FlexStart),
                verticalAlignment = Alignment.Top
            ) {
                // ==================== LEFT COLUMN ====================
                Column(
                    Modifier
                        .width(clamp(360.px, 38.percent, 480.px))
                        .gap(2.cssRem)
                        .margin(top = 0.px)
                        .displayIfAtLeast(Breakpoint.MD),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Top-Left Name with Smooth Fill Animation
                    Column(Modifier.gap(0.4.cssRem).margin(top = 0.px)) {
                        H2(
                            Modifier
                                .fontSize(50.px)
                                .fontWeight(FontWeight.ExtraBold)
                                .margin(0.px)
                                .whiteSpace(WhiteSpace.NoWrap)
                                .styleModifier {
                                    property("background-image", if (isLight) "linear-gradient(to right, #18181B, #18181B)" else "linear-gradient(to right, #F4F4F5, #F4F4F5)")
                                    property("background-repeat", "no-repeat")
                                    property("-webkit-background-clip", "text")
                                    property("-webkit-text-fill-color", if (isLight) "rgba(200, 200, 200, 0.35)" else "rgba(80, 80, 80, 0.35)")
                                    property("background-size", "0% 100%")
                                }
                                .animation(
                                    TextFillKeyframe.toAnimation(
                                        duration = 2.5.s,
                                        iterationCount = AnimationIterationCount.Infinite,
                                        direction = AnimationDirection.Alternate,
                                        timingFunction = AnimationTimingFunction.EaseInOut
                                    )
                                )
                                .toAttrs()
                        ) {
                            Text("Shad Adman")
                        }

                        Span(
                            Modifier
                                .fontSize(1.45.cssRem)
                                .color(textColor)
                                .fontWeight(FontWeight.Bold)
                                .whiteSpace(WhiteSpace.NoWrap)
                                .toAttrs()
                        ) {
                            Text("Senior Android Engineer")
                        }

                        Link(
                            "mailto:adman.shadman@gmail.com",
                            "adman.shadman@gmail.com",
                            Modifier.fontSize(0.9.cssRem).color(mutedColor).textDecorationLine(TextDecorationLine.Underline),
                            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
                        )
                    }

                    // Regular Links
                    Column(Modifier.gap(0.75.cssRem).fontSize(1.05.cssRem).fontWeight(FontWeight.SemiBold)) {
                        Link(
                            "https://github.com/ShadAdman",
                            "OpenSource ↗",
                            Modifier.color(textColor).textDecorationLine(TextDecorationLine.None),
                            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
                        )
                        Link(
                            "/resume",
                            "Resume →",
                            Modifier.color(textColor).textDecorationLine(TextDecorationLine.None),
                            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
                        )
                        Link(
                            "/worklog/worklog",
                            "WorkLog →",
                            Modifier.color(textColor).textDecorationLine(TextDecorationLine.None),
                            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
                        )
                    }

                    // Color Mode Switcher Button
                    Row(
                        Modifier.gap(0.6.cssRem).alignItems(org.jetbrains.compose.web.css.AlignItems.Center).cursor(Cursor.Pointer).onClick { colorMode = colorMode.opposite },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { colorMode = colorMode.opposite }) {
                            if (isLight) MoonIcon() else SunIcon()
                        }
                        Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).toAttrs()) {
                            Text(if (isLight) "Switch to Dark Mode" else "Switch to Light Mode")
                        }
                    }
                }

                // ==================== MOBILE TOP HEADER ====================
                Column(
                    Modifier
                        .fillMaxWidth()
                        .gap(1.cssRem)
                        .displayUntil(Breakpoint.MD)
                        .padding(bottom = 1.5.cssRem)
                        .borderBottom(1.px, LineStyle.Solid, if (isLight) Color.rgb(0xE4E4E7) else Color.rgb(0x27272A))
                ) {
                    Row(
                        Modifier.fillMaxWidth().alignItems(org.jetbrains.compose.web.css.AlignItems.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Span(Modifier.fontSize(1.8.cssRem).fontWeight(FontWeight.ExtraBold).color(textColor).toAttrs()) {
                                Text("Shad Adman")
                            }
                            Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).toAttrs()) {
                                Text("Senior Android Engineer • adman.shadman@gmail.com")
                            }
                        }
                        Spacer()
                        IconButton(onClick = { colorMode = colorMode.opposite }) {
                            if (isLight) MoonIcon() else SunIcon()
                        }
                    }

                    Row(Modifier.gap(1.25.cssRem).fontSize(0.95.cssRem).fontWeight(FontWeight.SemiBold)) {
                        Link("https://github.com/ShadAdman", "OpenSource", Modifier.color(textColor), variant = UncoloredLinkVariant)
                        Link("/resume", "Resume", Modifier.color(textColor), variant = UncoloredLinkVariant)
                        Link("/worklog/worklog", "WorkLog", Modifier.color(textColor), variant = UncoloredLinkVariant)
                    }
                }

                // ==================== RIGHT COLUMN ====================
                Column(
                    Modifier
                        .weight(1f)
                        .minWidth(280.px)
                        .margin(top = 0.px)
                        .gap(3.5.cssRem)
                ) {
                    // --- HERO BLOCK WITH PICTURE & ROTATING TEXT ---
                    Column(Modifier.fillMaxWidth().margin(top = 0.px).gap(1.5.cssRem)) {
                        // Picture Container + Rotating Radial Character Circle "A D M A N"
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .margin(top = 0.px)
                                .styleModifier { property("position", "relative") }
                        ) {
                            // Rounded Mint Picture Frame
                            Box(
                                Modifier
                                    .width(260.px)
                                    .height(340.px)
                                    .backgroundColor(imageCardBg)
                                    .borderRadius(2.8.cssRem)
                                    .padding(1.25.cssRem)
                                    .styleModifier { property("overflow", "hidden") }
                            ) {
                                Image(
                                    src = "/me.jpg",
                                    alt = "Shad Adman",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .borderRadius(2.2.cssRem)
                                        .styleModifier { property("object-fit", "cover") }
                                )
                            }

                            // Rotating Circular Ring of Individual Angled Letters "A D M A N A D M A N "
                            Box(
                                Modifier
                                    .size(170.px)
                                    .styleModifier {
                                        property("position", "absolute")
                                        property("top", "15px")
                                        property("left", "205px")
                                    }
                                    .animation(SpinAnimation.toAnimation(duration = 12.s, iterationCount = AnimationIterationCount.Infinite, timingFunction = AnimationTimingFunction.Linear))
                            ) {
                                CircularLetters(text = "A D M A N A D M A N ", isLight = isLight)
                            }
                        }

                        // Hero Tagline below picture
                        H1(
                            Modifier
                                .fontSize(clamp(1.8.cssRem, 3.5.vw, 2.5.cssRem))
                                .fontWeight(FontWeight.Bold)
                                .lineHeight(1.25)
                                .margin(0.px)
                                .color(textColor)
                                .toAttrs()
                        ) {
                            Text("hi. i'm shad. ")
                            Span(Modifier.color(mutedColor).fontWeight(FontWeight.Normal).toAttrs()) {
                                Text("an Android/KMP enthusiastic.")
                            }
                        }
                    }

                    // --- STATS SECTION ---
                    Column(Modifier.fillMaxWidth().gap(1.2.cssRem)) {
                        Span(
                            Modifier
                                .fontSize(0.82.cssRem)
                                .fontWeight(FontWeight.Bold)
                                .letterSpacing(0.1.cssRem)
                                .color(accentGreen)
                                .textTransform(TextTransform.Uppercase)
                                .toAttrs()
                        ) {
                            Text("SCALE & USER IMPACT (LAST ROLE: ALOPEYK)")
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .gap(1.cssRem)
                                .flexWrap(FlexWrap.Wrap)
                        ) {
                            Div(StatNumberStyle.toModifier().toAttrs()) {
                                H2(Modifier.fontSize(2.2.cssRem).fontWeight(FontWeight.ExtraBold).margin(0.px).color(accentGreen).toAttrs()) {
                                    Text("10M+")
                                }
                                Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                    Text("Total Users")
                                }
                            }

                            Div(StatNumberStyle.toModifier().toAttrs()) {
                                H2(Modifier.fontSize(2.2.cssRem).fontWeight(FontWeight.ExtraBold).margin(0.px).color(textColor).toAttrs()) {
                                    Text("500K+")
                                }
                                Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                    Text("Active Couriers")
                                }
                            }

                            Div(StatNumberStyle.toModifier().toAttrs()) {
                                H2(Modifier.fontSize(2.2.cssRem).fontWeight(FontWeight.ExtraBold).margin(0.px).color(accentGreen).toAttrs()) {
                                    Text("300K+")
                                }
                                Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                    Text("Daily Orders")
                                }
                            }

                            Div(StatNumberStyle.toModifier().toAttrs()) {
                                H2(Modifier.fontSize(2.2.cssRem).fontWeight(FontWeight.ExtraBold).margin(0.px).color(textColor).toAttrs()) {
                                    Text("12+")
                                }
                                Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                    Text("Years Experience")
                                }
                            }
                        }
                    }

                    // --- TOP 4 EXPERIENCE SECTION ---
                    Column(Modifier.fillMaxWidth().gap(1.5.cssRem)) {
                        Row(
                            Modifier.fillMaxWidth().alignItems(org.jetbrains.compose.web.css.AlignItems.Center),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            H2(Modifier.fontSize(1.6.cssRem).fontWeight(FontWeight.Bold).margin(0.px).color(textColor).toAttrs()) {
                                Text("Experience")
                            }
                            Spacer()
                            Link(
                                "/resume",
                                "View Resume →",
                                Modifier.fontSize(0.9.cssRem).color(accentGreen).fontWeight(FontWeight.SemiBold).textDecorationLine(TextDecorationLine.None),
                                variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
                            )
                        }

                        Column(Modifier.fillMaxWidth().gap(1.25.cssRem)) {
                            // 1. Alopeyk
                            Div(KlioCardStyle.toModifier().toAttrs()) {
                                Column(Modifier.fillMaxWidth().gap(0.5.cssRem)) {
                                    Row(
                                        Modifier.fillMaxWidth().flexWrap(FlexWrap.Wrap).gap(0.5.cssRem),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Span(Modifier.fontSize(1.15.cssRem).fontWeight(FontWeight.Bold).color(textColor).toAttrs()) {
                                            Text("Senior Android Engineer")
                                        }
                                        Span(Modifier.fontSize(1.05.cssRem).color(accentGreen).fontWeight(FontWeight.Bold).toAttrs()) {
                                            Text("— Alopeyk")
                                        }
                                        Spacer()
                                        Span(Modifier.fontSize(0.82.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                            Text("Aug 2026 — Present")
                                        }
                                    }
                                    P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(mutedColor).margin(0.px).toAttrs()) {
                                        Text("Responsible for converting the single-module codebase into a multi-modular feature-based architecture (increasing build pipeline efficiency), implementing the courier driver application for a smooth driver experience, and encouraging production-scale AI adoption across teammates to speed up dev & release cycles.")
                                    }
                                }
                            }

                            // 2. Bit24.cash
                            Div(KlioCardStyle.toModifier().toAttrs()) {
                                Column(Modifier.fillMaxWidth().gap(0.5.cssRem)) {
                                    Row(
                                        Modifier.fillMaxWidth().flexWrap(FlexWrap.Wrap).gap(0.5.cssRem),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Span(Modifier.fontSize(1.15.cssRem).fontWeight(FontWeight.Bold).color(textColor).toAttrs()) {
                                            Text("Senior Android Engineer")
                                        }
                                        Span(Modifier.fontSize(1.05.cssRem).color(accentGreen).fontWeight(FontWeight.Bold).toAttrs()) {
                                            Text("— Bit24.cash")
                                        }
                                        Spacer()
                                        Span(Modifier.fontSize(0.82.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                            Text("Sept 2025 — Aug 2026")
                                        }
                                    }
                                    P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(mutedColor).margin(0.px).toAttrs()) {
                                        Text("Crypto exchange ecosystem with over 2 million active users. Responsible for implementing squad-owned features and migrating the application to Kotlin Multiplatform (KMP) to target iOS. Built automated CI/CD pipelines on KMP using GitLab Runner for Google Play & TestFlight deployments.")
                                    }
                                }
                            }

                            // 3. SportsGameX
                            Div(KlioCardStyle.toModifier().toAttrs()) {
                                Column(Modifier.fillMaxWidth().gap(0.5.cssRem)) {
                                    Row(
                                        Modifier.fillMaxWidth().flexWrap(FlexWrap.Wrap).gap(0.5.cssRem),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Span(Modifier.fontSize(1.15.cssRem).fontWeight(FontWeight.Bold).color(textColor).toAttrs()) {
                                            Text("Kotlin Multiplatform Engineer")
                                        }
                                        Span(Modifier.fontSize(1.05.cssRem).color(accentGreen).fontWeight(FontWeight.Bold).toAttrs()) {
                                            Text("— SportsGameX")
                                        }
                                        Spacer()
                                        Span(Modifier.fontSize(0.82.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                            Text("Apr 2025 — Aug 2025")
                                        }
                                    }
                                    P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(mutedColor).margin(0.px).toAttrs()) {
                                        Text("Built MVI architecture with Precompose and Koin. Integrated on-device AI models to assist users in stock trading. Developed data visualization components with TradingView on Android and iOS using reusable KMP libraries, socket.io, and automated GitHub Actions CI/CD.")
                                    }
                                }
                            }

                            // 4. StrAIberry
                            Div(KlioCardStyle.toModifier().toAttrs()) {
                                Column(Modifier.fillMaxWidth().gap(0.5.cssRem)) {
                                    Row(
                                        Modifier.fillMaxWidth().flexWrap(FlexWrap.Wrap).gap(0.5.cssRem),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Span(Modifier.fontSize(1.15.cssRem).fontWeight(FontWeight.Bold).color(textColor).toAttrs()) {
                                            Text("Lead Android Developer")
                                        }
                                        Span(Modifier.fontSize(1.05.cssRem).color(accentGreen).fontWeight(FontWeight.Bold).toAttrs()) {
                                            Text("— StrAIberry")
                                        }
                                        Spacer()
                                        Span(Modifier.fontSize(0.82.cssRem).color(mutedColor).fontWeight(FontWeight.Medium).toAttrs()) {
                                            Text("Dec 2021 — Feb 2025")
                                        }
                                    }
                                    P(Modifier.fontSize(0.95.cssRem).lineHeight(1.55).color(mutedColor).margin(0.px).toAttrs()) {
                                        Text("Led the Android team using Clean Architecture, multi-module MVVM, and TDD. Integrated on-device TFLite & YOLO models achieving 98% real-time jaw detection accuracy, live tooth detection system with Camera2 autofocus/zoom, and custom scheduling systems.")
                                    }
                                }
                            }
                        }
                    }

                    // --- TECHSTACK SECTION (LINE-MOVING MARQUEE) ---
                    Column(Modifier.fillMaxWidth().gap(1.2.cssRem)) {
                        H2(Modifier.fontSize(1.6.cssRem).fontWeight(FontWeight.Bold).margin(0.px).color(textColor).toAttrs()) {
                            Text("Techstack")
                        }

                        Div(KlioCardStyle.toModifier().toAttrs()) {
                            Column(Modifier.fillMaxWidth().gap(0.75.cssRem)) {
                                val row1 = listOf(
                                    "Android Studio", "Kotlin", "Kotlin Multiplatform (KMP)", "Jetpack Compose",
                                    "Compose Multiplatform (CMP)", "C++ / NDK", "JNI Interop", "Swift & Xcode", "Java", "XML"
                                )
                                val row2 = listOf(
                                    "Coroutines & Flow", "Clean Architecture", "MVI & MVVM", "On-Device AI",
                                    "TFLite", "YOLO", "OpenCV", "BLE & Hardware", "Camera2 & Sensors", "PyTorch Mobile"
                                )
                                val row3 = listOf(
                                    "Ktor & RestAPI", "WebSockets & Socket.io", "Hilt & Koin", "Room & SQLDelight",
                                    "GitLab Runner", "GitHub Actions", "Firebase & Analytics", "TDD & JUnit", "cinterop"
                                )

                                MarqueeRow(items = row1, isReversed = false)
                                MarqueeRow(items = row2, isReversed = true)
                                MarqueeRow(items = row3, isReversed = false)
                            }
                        }
                    }

                    // --- ABOUT SECTION ---
                    Column(Modifier.fillMaxWidth().gap(1.2.cssRem)) {
                        H2(Modifier.fontSize(1.6.cssRem).fontWeight(FontWeight.Bold).margin(0.px).color(textColor).toAttrs()) {
                            Text("About")
                        }

                        Div(KlioCardStyle.toModifier().toAttrs()) {
                            Column(Modifier.fillMaxWidth().gap(1.1.cssRem)) {
                                P(Modifier.fontSize(1.02.cssRem).lineHeight(1.65).color(textColor).margin(0.px).toAttrs()) {
                                    Text("I fell in love with programming when I first touched C++ back in my student days, and quickly got hooked on building for Android. Over the last 12+ years, I've built mobile apps across health tech, e-commerce, crypto trading, fintech, and iGaming using Android and Kotlin Multiplatform.")
                                }

                                P(Modifier.fontSize(1.02.cssRem).lineHeight(1.65).color(textColor).margin(0.px).toAttrs()) {
                                    Text("I'm deeply curious about on-device AI and hardware. Hand me a quirky smart home gadget or an old Raspberry Pi, and I'll usually find a way to get an offline AI model running on it without needing the cloud.")
                                }

                                P(Modifier.fontSize(1.02.cssRem).lineHeight(1.65).color(textColor).margin(0.px).toAttrs()) {
                                    Text("These days, I'm mostly focused on Kotlin Multiplatform, bridging C++/native libraries with Kotlin, and working close to the metal with hardware and Bluetooth.")
                                }
                            }
                        }
                    }

                    // --- FOOTER ---
                    Div(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 2.cssRem, bottom = 1.cssRem)
                            .borderTop(1.px, LineStyle.Solid, if (isLight) Color.rgb(0xE4E4E7) else Color.rgb(0x27272A))
                            .display(DisplayStyle.Flex)
                            .flexWrap(FlexWrap.Wrap)
                            .gap(1.cssRem)
                            .alignItems(org.jetbrains.compose.web.css.AlignItems.Center)
                            .toAttrs()
                    ) {
                        Span(Modifier.fontSize(0.85.cssRem).color(mutedColor).toAttrs()) {
                            Text("© 2026 Shad Adman • Senior Android Engineer")
                        }

                        Spacer()

                        Row(Modifier.gap(1.25.cssRem), verticalAlignment = Alignment.CenterVertically) {
                            Link("https://github.com/ShadAdman", "OpenSource", Modifier.fontSize(0.85.cssRem).color(mutedColor), variant = UncoloredLinkVariant)
                            Link("/resume", "Resume", Modifier.fontSize(0.85.cssRem).color(mutedColor), variant = UncoloredLinkVariant)
                            Link("/worklog/worklog", "WorkLog", Modifier.fontSize(0.85.cssRem).color(mutedColor), variant = UncoloredLinkVariant)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularLetters(text: String, isLight: Boolean) {
    val charList = text.toList()
    val totalChars = charList.size
    val radius = 62.0 // px from center
    val colorHex = if (isLight) "#18181B" else "#F4F4F5"

    Box(
        Modifier
            .fillMaxSize()
            .styleModifier { property("position", "relative") }
    ) {
        charList.forEachIndexed { index, char ->
            val angle = index * (360.0 / totalChars)
            Span(
                Modifier
                    .styleModifier {
                        property("position", "absolute")
                        property("top", "50%")
                        property("left", "50%")
                        property("transform", "rotate(${angle}deg) translate(${radius}px) rotate(90deg)")
                        property("transform-origin", "0 0")
                        property("font-size", "1.05rem")
                        property("font-weight", "700")
                        property("color", colorHex)
                        property("user-select", "none")
                        property("white-space", "pre")
                    }
                    .toAttrs()
            ) {
                Text(char.toString())
            }
        }
    }
}

@Composable
private fun MarqueeRow(items: List<String>, isReversed: Boolean) {
    val animation = if (isReversed) MarqueeRightAnimation else MarqueeLeftAnimation
    val doubleList = items + items

    Box(
        Modifier
            .fillMaxWidth()
            .styleModifier {
                property("overflow", "hidden")
                property("mask-image", "linear-gradient(to right, transparent, black 8%, black 92%, transparent)")
                property("-webkit-mask-image", "linear-gradient(to right, transparent, black 8%, black 92%, transparent)")
            }
    ) {
        Row(
            Modifier
                .styleModifier {
                    property("width", "max-content")
                    property("display", "flex")
                    property("white-space", "nowrap")
                }
                .animation(
                    animation.toAnimation(
                        duration = 26.s,
                        iterationCount = AnimationIterationCount.Infinite,
                        timingFunction = AnimationTimingFunction.Linear
                    )
                )
        ) {
            doubleList.forEach { tech ->
                Span(TechTagStyle.toModifier().toAttrs()) {
                    Text(tech)
                }
            }
        }
    }
}
