package com.kodeco.recipefinder.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import androidx.compose.ui.unit.sp
import com.kodeco.recipefinder.R

// Set of Material typography styles to start with
val Typography = Typography(
  bodyLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
  )
  /* Other default text styles to override
  titleLarge = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Normal,
      fontSize = 22.sp,
      lineHeight = 28.sp,
      letterSpacing = 0.sp
  ),
  labelSmall = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Medium,
      fontSize = 11.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp
  )
  */
)

private val googleFontProvider: Provider by lazy {
  Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
  )
}

val robotoFont = GoogleFont(name = "Roboto")

val RobotoFontFamily = FontFamily(
  Font(googleFont = robotoFont, fontProvider = googleFontProvider),
  Font(googleFont = robotoFont, fontProvider = googleFontProvider, weight = FontWeight.Medium),
  Font(googleFont = robotoFont, fontProvider = googleFontProvider, weight = FontWeight.Bold)
)

val GroceryTitle = TextStyle(
  fontFamily = RobotoFontFamily,
  fontWeight = FontWeight.W600,
  fontSize = 24.sp,
  lineHeight = 24.sp,
)

val HeadlineSmall = TextStyle(
  fontFamily = RobotoFontFamily,
  fontWeight = FontWeight.W400,
  fontSize = 24.sp,
  lineHeight = 32.sp,
)

val LabelLarge = TextStyle(
  fontFamily = RobotoFontFamily,
  fontWeight = FontWeight.W500,
  fontSize = 14.sp,
  lineHeight = 20.sp,
)

val BodyLarge = TextStyle(
  fontFamily = RobotoFontFamily,
  fontWeight = FontWeight.W400,
  fontSize = 16.sp,
  lineHeight = 24.sp,
)

private fun getGoogleFontFamily(
  name: String,
  provider: Provider = googleFontProvider,
  weights: List<FontWeight>
): FontFamily {
  return FontFamily(
    weights.map {
      Font(GoogleFont(name), provider, it)
    }
  )
}

