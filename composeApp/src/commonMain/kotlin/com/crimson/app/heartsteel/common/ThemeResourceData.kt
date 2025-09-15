package com.crimson.app.heartsteel.common

import com.crimson.app.heartsteel.model.ThemeEnum
import com.crimson.app.heartsteel.model.ThemeItem
import heartsteel.composeapp.generated.resources.APP_Theme_Blue
import heartsteel.composeapp.generated.resources.APP_Theme_Green
import heartsteel.composeapp.generated.resources.APP_Theme_Red
import heartsteel.composeapp.generated.resources.APP_Theme_Yellow
import heartsteel.composeapp.generated.resources.Res

val themeMap = mapOf(
    ThemeEnum.Green to ThemeItem(
        key = ThemeEnum.Green,
        name = "清韵",
        image = Res.drawable.APP_Theme_Green
    ),
    ThemeEnum.Red to ThemeItem(
        key = ThemeEnum.Red,
        name = "绛株",
        image = Res.drawable.APP_Theme_Red
    ),
    ThemeEnum.Yellow to ThemeItem(
        key = ThemeEnum.Yellow,
        name = "暖漾",
        image = Res.drawable.APP_Theme_Yellow
    ),
    ThemeEnum.Blue to ThemeItem(
        key = ThemeEnum.Blue,
        name = "浮萤",
        image = Res.drawable.APP_Theme_Blue
    ),
)
