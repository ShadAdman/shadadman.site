package site.shadadman

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

/**
 * @property nearBackground A useful color to apply to a container that should differentiate itself from the background
 *   but just a little.
 */
class SitePalette(
    val nearBackground: Color,
    val cobweb: Color,
    val brand: Brand,
) {
    class Brand(
        val primary: Color = Color.rgb(0x3DDC84),
        val accent: Color = Color.rgb(0x64B5F6),
    )
}

object SitePalettes {
    val light = SitePalette(
        nearBackground = Color.rgb(0xF1F5F9),
        cobweb = Colors.LightGray,
        brand = SitePalette.Brand(
            primary = Color.rgb(0x2E7D32),
            accent = Color.rgb(0x1565C0),
        )
    )
    val dark = SitePalette(
        nearBackground = Color.rgb(0x111827),
        cobweb = Colors.LightGray.inverted(),
        brand = SitePalette.Brand(
            primary = Color.rgb(0x3DDC84),
            accent = Color.rgb(0x64B5F6),
        )
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(0xF8FAFC)
    ctx.theme.palettes.light.color = Color.rgb(0x0F172A)
    ctx.theme.palettes.dark.background = Color.rgb(0x0B0F17)
    ctx.theme.palettes.dark.color = Color.rgb(0xF8FAFC)
}
