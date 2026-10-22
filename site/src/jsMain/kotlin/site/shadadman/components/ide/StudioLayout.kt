package site.shadadman.components.ide

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.*
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
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.navigation.UncoloredLinkVariant
import com.varabyte.kobweb.silk.components.navigation.UndecoratedLinkVariant
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import site.shadadman.components.widgets.IconButton

enum class ViewMode {
    CODE,
    PREVIEW
}

val LocalViewMode = compositionLocalOf { ViewMode.PREVIEW }

@Composable
fun StudioLayout(
    ctx: PageContext,
    activeTitle: String,
    content: @Composable (viewMode: ViewMode) -> Unit
) {
    val isLight = ColorMode.current.isLight
    var colorMode by ColorMode.currentState

    val bgMain = if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x1E1F22)
    val bgSidebar = if (isLight) Color.rgb(0xF7F8FA) else Color.rgb(0x18191B)
    val bgTopbar = if (isLight) Color.rgb(0xEBECF0) else Color.rgb(0x111214)
    val borderColor = if (isLight) Color.rgb(0xD1D5DB) else Color.rgb(0x2B2D30)
    val textColor = if (isLight) Color.rgb(0x1F2937) else Color.rgb(0xDFE1E5)
    val mutedTextColor = if (isLight) Color.rgb(0x6B7280) else Color.rgb(0x7A7E85)
    val primaryGreen = if (isLight) Color.rgb(0x2E7D32) else Color.rgb(0x3DDC84)

    val currentPath = ctx.route.path

    var isProjectOpen by remember { mutableStateOf(true) }
    var isWorklogExpanded by remember { mutableStateOf(true) }
    var viewMode by remember { mutableStateOf(ViewMode.PREVIEW) }

    Column(
        Modifier
            .fillMaxSize()
            .minHeight(100.vh)
            .backgroundColor(bgMain)
            .color(textColor)
            .fontFamily("Inter", "system-ui", "sans-serif")
            .fontSize(1.cssRem)
    ) {
        // --- 1. TOP TITLEBAR / MAIN MENU ---
        Row(
            Modifier
                .fillMaxWidth()
                .backgroundColor(bgTopbar)
                .borderBottom(1.px, LineStyle.Solid, borderColor)
                .padding(0.5.cssRem, 1.cssRem)
                .gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Android Studio Icon / Logo
            Row(Modifier.gap(0.6.cssRem), verticalAlignment = Alignment.CenterVertically) {
                Image("/ic_android.svg", alt = "Android Studio", modifier = Modifier.size(22.px))
                Span(Modifier.fontWeight(FontWeight.Bold).fontSize(0.95.cssRem).toAttrs()) {
                    Text("Android Studio")
                }
                Span(
                    Modifier
                        .fontSize(0.82.cssRem)
                        .color(mutedTextColor)
                        .backgroundColor(if (isLight) Color.rgb(0xE5E7EB) else Color.rgb(0x2B2D30))
                        .padding(0.2.cssRem, 0.5.cssRem)
                        .borderRadius(0.3.cssRem)
                        .toAttrs()
                ) {
                    Text("shadadman.site [git: better-theme]")
                }
            }

            Spacer()

            // Run / Debug Controls, Build Hammer, Gradle & Search Box
            Row(
                Modifier.gap(0.75.cssRem).display(DisplayStyle.Flex),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Build Hammer Icon
                Div(
                    Modifier
                        .cursor(Cursor.Pointer)
                        .title("Make Project (Ctrl+F9)")
                        .padding(0.2.cssRem)
                        .toAttrs()
                ) {
                    Image(
                        "/ic_hammer.svg",
                        alt = "Build",
                        modifier = Modifier
                            .size(18.px)
                            .styleModifier {
                                if (!isLight) property("filter", "brightness(0) invert(0.9)")
                            }
                    )
                }

                // Play / Run Button
                Div(
                    Modifier
                        .backgroundColor(if (isLight) Color.rgb(0xDCFCE7) else Color.rgb(0x14532D))
                        .color(primaryGreen)
                        .padding(0.25.cssRem, 0.75.cssRem)
                        .borderRadius(0.35.cssRem)
                        .fontSize(0.85.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .cursor(Cursor.Pointer)
                        .toAttrs()
                ) {
                    Text("▶ Run 'app'")
                }

                // Gradle Sync Icon
                Div(
                    Modifier
                        .cursor(Cursor.Pointer)
                        .title("Gradle Sync")
                        .padding(0.2.cssRem)
                        .toAttrs()
                ) {
                    Image(
                        "/ic_gradle.svg",
                        alt = "Gradle",
                        modifier = Modifier
                            .size(18.px)
                            .styleModifier {
                                if (!isLight) property("filter", "brightness(0) invert(0.9)")
                            }
                    )
                }

                // Search Chip
                Div(
                    Modifier
                        .backgroundColor(if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x2B2D30))
                        .border(1.px, LineStyle.Solid, borderColor)
                        .borderRadius(0.35.cssRem)
                        .padding(0.25.cssRem, 0.75.cssRem)
                        .fontSize(0.85.cssRem)
                        .color(mutedTextColor)
                        .toAttrs()
                ) {
                    Text("Search Everywhere (Shift Shift)")
                }

                // Color Mode Toggle
                IconButton(
                    onClick = { colorMode = colorMode.opposite }
                ) {
                    if (isLight) MoonIcon() else SunIcon()
                }
            }
        }

        // --- 2. MAIN WORKSPACE ROW (ACTIVITY BAR + SIDEBAR + EDITOR) ---
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Left Activity Bar (Strip)
            Column(
                Modifier
                    .width(44.px)
                    .fillMaxHeight()
                    .backgroundColor(bgSidebar)
                    .borderRight(1.px, LineStyle.Solid, borderColor)
                    .padding(top = 0.5.cssRem)
                    .gap(0.75.cssRem),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Project Icon Toggle Button
                Div(
                    Modifier
                        .size(34.px)
                        .borderRadius(0.35.cssRem)
                        .backgroundColor(if (isProjectOpen) (if (isLight) Color.rgb(0xE5E7EB) else Color.rgb(0x2B2D30)) else Colors.Transparent)
                        .cursor(Cursor.Pointer)
                        .display(DisplayStyle.Flex)
                        .alignItems(org.jetbrains.compose.web.css.AlignItems.Center)
                        .justifyContent(org.jetbrains.compose.web.css.JustifyContent.Center)
                        .onClick { isProjectOpen = !isProjectOpen }
                        .title("Project Tool Window")
                        .toAttrs()
                ) {
                    Image(
                        "/ic_folder.png",
                        alt = "Project",
                        modifier = Modifier
                            .size(20.px)
                            .styleModifier {
                                if (!isLight) property("filter", "brightness(0) invert(0.9)")
                            }
                    )
                }
            }

            // Project Tool Window (Collapsible Side Drawer)
            if (isProjectOpen) {
                Column(
                    Modifier
                        .width(280.px)
                        .fillMaxHeight()
                        .backgroundColor(bgSidebar)
                        .borderRight(1.px, LineStyle.Solid, borderColor)
                        .padding(0.6.cssRem)
                        .gap(0.5.cssRem)
                        .fontSize(0.95.cssRem)
                        .styleModifier { property("overflow-y", "auto") }
                ) {
                    // Tree Header
                    Row(
                        Modifier.fillMaxWidth().padding(bottom = 0.4.cssRem),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Span(Modifier.fontWeight(FontWeight.Bold).fontSize(0.88.cssRem).color(mutedTextColor).toAttrs()) {
                            Text("PROJECT ▾")
                        }
                        Spacer()
                        Span(Modifier.cursor(Cursor.Pointer).onClick { isProjectOpen = false }.toAttrs()) {
                            Text("✕")
                        }
                    }

                    // Tree Nodes
                    TreeFolder("shadadman.site", isExpanded = true) {
                        TreeFolder("site", isExpanded = true) {
                            TreeFolder("src/kotlin/site/shadadman", isExpanded = true) {
                                TreeFile("Index.kt", "/", isCurrent = currentPath == "/" || currentPath == "")
                                TreeFile("Resume.md", "/resume", isCurrent = currentPath == "/resume")
                                TreeFile("Videos.md", "/videos", isCurrent = currentPath == "/videos")

                                TreeFolder("worklog", isExpanded = isWorklogExpanded, onToggle = { isWorklogExpanded = !isWorklogExpanded }) {
                                    TreeFile("worklog.md", "/worklog/worklog", isCurrent = currentPath == "/worklog/worklog")
                                    TreeFile("bit24cash.md", "/worklog/bit24cash/bit24cash", isCurrent = currentPath.contains("bit24"))
                                    TreeFile("SportsGameX.md", "/worklog/SportsGameX/SportsGameX", isCurrent = currentPath.contains("SportsGameX"))
                                    TreeFile("StrAIberry.md", "/worklog/StrAIberry/StrAIberry", isCurrent = currentPath.contains("StrAIberry"))
                                    TreeFile("MarketMLM.md", "/worklog/marketmlm/marketmlm", isCurrent = currentPath.contains("marketmlm"))
                                    TreeFile("AlfaTechnology.md", "/worklog/alfatechnology/alfatechnology", isCurrent = currentPath.contains("alfatechnology"))
                                }

                                ExternalLinkNode("Articles (Medium)", "https://medium.com/@adman.shadman")
                                ExternalLinkNode("OpenSource (GitHub)", "https://github.com/ShadAdman")
                            }
                        }
                        TreeFile("build.gradle.kts", "#", isCurrent = false)
                        TreeFile("README.md", "#", isCurrent = false)
                    }
                }
            }

            // Main Editor Container
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .backgroundColor(bgMain)
            ) {
                // Editor Tabs & View Mode Selector
                Row(
                    Modifier
                        .fillMaxWidth()
                        .backgroundColor(bgSidebar)
                        .borderBottom(1.px, LineStyle.Solid, borderColor)
                        .padding(left = 0.5.cssRem, right = 0.5.cssRem)
                        .gap(0.3.cssRem),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Editor File Tabs
                    EditorTab("Index.kt", "/", isCurrent = currentPath == "/" || currentPath == "")
                    EditorTab("Resume.md", "/resume", isCurrent = currentPath == "/resume")
                    EditorTab("WorkLog.md", "/worklog/worklog", isCurrent = currentPath.startsWith("/worklog"))
                    EditorTab("Videos.md", "/videos", isCurrent = currentPath == "/videos")

                    Spacer()

                    // Code / Preview View Mode Toggle
                    Row(
                        Modifier
                            .border(1.px, LineStyle.Solid, borderColor)
                            .borderRadius(0.3.cssRem)
                            .padding(2.px)
                            .margin(0.2.cssRem)
                            .fontSize(0.82.cssRem),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ViewModeButton("Code", ViewMode.CODE, active = viewMode == ViewMode.CODE, isLight = isLight) { viewMode = ViewMode.CODE }
                        ViewModeButton("Preview", ViewMode.PREVIEW, active = viewMode == ViewMode.PREVIEW, isLight = isLight) { viewMode = ViewMode.PREVIEW }
                    }
                }

                // Breadcrumbs Bar
                Row(
                    Modifier
                        .fillMaxWidth()
                        .backgroundColor(if (isLight) Color.rgb(0xFAFAFA) else Color.rgb(0x1E1F22))
                        .borderBottom(1.px, LineStyle.Solid, borderColor)
                        .padding(0.3.cssRem, 0.9.cssRem)
                        .fontSize(0.85.cssRem)
                        .color(mutedTextColor)
                        .gap(0.4.cssRem),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpanText("shadadman.site > site > src > kotlin > site > shadadman > ")
                    SpanText(activeTitle, Modifier.color(textColor).fontWeight(FontWeight.SemiBold))
                    Spacer()
                    SpanText("✓ 0 Errors, 0 Warnings", Modifier.color(primaryGreen))
                }

                // Editor Slot
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .styleModifier { property("overflow", "auto") }
                ) {
                    CompositionLocalProvider(LocalViewMode provides viewMode) {
                        content(viewMode)
                    }
                }
            }
        }

        // --- 3. BOTTOM IDE STATUS BAR ---
        Row(
            Modifier
                .fillMaxWidth()
                .backgroundColor(bgTopbar)
                .borderTop(1.px, LineStyle.Solid, borderColor)
                .padding(0.3.cssRem, 1.cssRem)
                .fontSize(0.85.cssRem)
                .color(mutedTextColor)
                .gap(1.cssRem),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText("📍 site/src/kotlin/site/shadadman")
            Spacer()
            SpanText("git: better-theme")
            SpanText("UTF-8")
            SpanText("4 spaces")
            SpanText("Android Studio 2026.2.2")
        }
    }
}

@Composable
private fun TreeFolder(name: String, isExpanded: Boolean, onToggle: (() -> Unit)? = null, content: @Composable () -> Unit) {
    val isLight = ColorMode.current.isLight
    Column(Modifier.padding(left = 0.4.cssRem)) {
        Row(
            Modifier
                .cursor(Cursor.Pointer)
                .padding(0.2.cssRem)
                .onClick { onToggle?.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Span(Modifier.margin(right = 0.3.cssRem).fontSize(0.8.cssRem).toAttrs()) { Text(if (isExpanded) "▾" else "▸") }
            Image(
                "/ic_folder.png",
                alt = "Folder",
                modifier = Modifier
                    .size(16.px)
                    .margin(right = 0.4.cssRem)
                    .styleModifier {
                        if (!isLight) property("filter", "brightness(0) invert(0.9)")
                    }
            )
            Span(Modifier.fontWeight(FontWeight.Medium).fontSize(0.95.cssRem).toAttrs()) { Text(name) }
        }
        if (isExpanded) {
            Column(Modifier.padding(left = 0.8.cssRem)) {
                content()
            }
        }
    }
}

@Composable
private fun TreeFile(name: String, path: String, isCurrent: Boolean) {
    val isLight = ColorMode.current.isLight
    Row(
        Modifier
            .fillMaxWidth()
            .padding(0.2.cssRem, 0.4.cssRem)
            .borderRadius(0.25.cssRem)
            .backgroundColor(if (isCurrent) (if (isLight) Color.rgb(0xDBEAFE) else Color.rgb(0x2D3748)) else Colors.Transparent)
            .color(if (isCurrent) (if (isLight) Color.rgb(0x1D4ED8) else Color.rgb(0x60A5FA)) else ColorMode.current.toPalette().color)
            .cursor(Cursor.Pointer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image("/ic_kotlin.svg", alt = "Kotlin", modifier = Modifier.size(16.px).margin(right = 0.4.cssRem))
        Link(
            path,
            name,
            Modifier.fillMaxWidth().fontSize(0.92.cssRem).textDecorationLine(TextDecorationLine.None),
            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
        )
    }
}

@Composable
private fun ExternalLinkNode(label: String, url: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(0.2.cssRem, 0.4.cssRem)
            .cursor(Cursor.Pointer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image("/ic_kotlin.svg", alt = "Kotlin", modifier = Modifier.size(16.px).margin(right = 0.4.cssRem))
        Link(
            url,
            label,
            Modifier.fillMaxWidth().fontSize(0.92.cssRem).textDecorationLine(TextDecorationLine.None),
            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
        )
    }
}

@Composable
private fun EditorTab(title: String, path: String, isCurrent: Boolean) {
    val isLight = ColorMode.current.isLight
    Row(
        Modifier
            .padding(0.4.cssRem, 0.9.cssRem)
            .borderRadius(topLeft = 0.35.cssRem, topRight = 0.35.cssRem)
            .backgroundColor(if (isCurrent) (if (isLight) Color.rgb(0xFFFFFF) else Color.rgb(0x1E1F22)) else Colors.Transparent)
            .border(1.px, LineStyle.Solid, if (isCurrent) (if (isLight) Color.rgb(0xD1D5DB) else Color.rgb(0x2B2D30)) else Colors.Transparent)
            .color(if (isCurrent) (if (isLight) Color.rgb(0x1F2937) else Color.rgb(0xFFFFFF)) else Color.rgb(0x9CA3AF))
            .fontSize(0.9.cssRem)
            .fontWeight(if (isCurrent) FontWeight.Bold else FontWeight.Normal)
            .cursor(Cursor.Pointer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image("/ic_kotlin.svg", alt = "Kotlin", modifier = Modifier.size(15.px).margin(right = 0.4.cssRem))
        Link(
            path,
            title,
            Modifier.textDecorationLine(TextDecorationLine.None),
            variant = UncoloredLinkVariant.then(UndecoratedLinkVariant)
        )
    }
}

@Composable
private fun ViewModeButton(label: String, mode: ViewMode, active: Boolean, isLight: Boolean, onClick: () -> Unit) {
    Span(
        Modifier
            .padding(0.25.cssRem, 0.6.cssRem)
            .borderRadius(0.2.cssRem)
            .backgroundColor(if (active) (if (isLight) Color.rgb(0x3B82F6) else Color.rgb(0x2563EB)) else Colors.Transparent)
            .color(if (active) Colors.White else (if (isLight) Color.rgb(0x4B5563) else Color.rgb(0x9CA3AF)))
            .cursor(Cursor.Pointer)
            .fontWeight(if (active) FontWeight.Bold else FontWeight.Normal)
            .onClick { onClick() }
            .toAttrs()
    ) {
        Text(label)
    }
}
