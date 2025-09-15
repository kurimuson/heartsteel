package com.crimson.app.heartsteel.common

import com.crimson.app.heartsteel.model.TraitChampionInfo
import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.model.TraitTip
import heartsteel.composeapp.generated.resources.Res
import heartsteel.composeapp.generated.resources.TFT10_Champion_Ahri
import heartsteel.composeapp.generated.resources.TFT10_Champion_Akali
import heartsteel.composeapp.generated.resources.TFT10_Champion_Akali_TrueDamage
import heartsteel.composeapp.generated.resources.TFT10_Champion_Amumu
import heartsteel.composeapp.generated.resources.TFT10_Champion_Annie
import heartsteel.composeapp.generated.resources.TFT10_Champion_Aphelios
import heartsteel.composeapp.generated.resources.TFT10_Champion_Bard
import heartsteel.composeapp.generated.resources.TFT10_Champion_Blitzcrank
import heartsteel.composeapp.generated.resources.TFT10_Champion_Caitlyn
import heartsteel.composeapp.generated.resources.TFT10_Champion_Corki
import heartsteel.composeapp.generated.resources.TFT10_Champion_Ekko
import heartsteel.composeapp.generated.resources.TFT10_Champion_Evelynn
import heartsteel.composeapp.generated.resources.TFT10_Champion_Ezreal
import heartsteel.composeapp.generated.resources.TFT10_Champion_Garen
import heartsteel.composeapp.generated.resources.TFT10_Champion_Gnar
import heartsteel.composeapp.generated.resources.TFT10_Champion_Gragas
import heartsteel.composeapp.generated.resources.TFT10_Champion_Illaoi
import heartsteel.composeapp.generated.resources.TFT10_Champion_Jax
import heartsteel.composeapp.generated.resources.TFT10_Champion_Jhin
import heartsteel.composeapp.generated.resources.TFT10_Champion_Jinx
import heartsteel.composeapp.generated.resources.TFT10_Champion_KSante
import heartsteel.composeapp.generated.resources.TFT10_Champion_Kaisa
import heartsteel.composeapp.generated.resources.TFT10_Champion_Karthus
import heartsteel.composeapp.generated.resources.TFT10_Champion_Katarina
import heartsteel.composeapp.generated.resources.TFT10_Champion_Kayle
import heartsteel.composeapp.generated.resources.TFT10_Champion_Kayn
import heartsteel.composeapp.generated.resources.TFT10_Champion_Kennen
import heartsteel.composeapp.generated.resources.TFT10_Champion_Lillia
import heartsteel.composeapp.generated.resources.TFT10_Champion_Lucian
import heartsteel.composeapp.generated.resources.TFT10_Champion_Lulu
import heartsteel.composeapp.generated.resources.TFT10_Champion_Lux
import heartsteel.composeapp.generated.resources.TFT10_Champion_MissFortune
import heartsteel.composeapp.generated.resources.TFT10_Champion_Mordekaiser
import heartsteel.composeapp.generated.resources.TFT10_Champion_Nami
import heartsteel.composeapp.generated.resources.TFT10_Champion_Neeko
import heartsteel.composeapp.generated.resources.TFT10_Champion_Olaf
import heartsteel.composeapp.generated.resources.TFT10_Champion_Pantheon
import heartsteel.composeapp.generated.resources.TFT10_Champion_Poppy
import heartsteel.composeapp.generated.resources.TFT10_Champion_Qiyana
import heartsteel.composeapp.generated.resources.TFT10_Champion_Riven
import heartsteel.composeapp.generated.resources.TFT10_Champion_Samira
import heartsteel.composeapp.generated.resources.TFT10_Champion_Senna
import heartsteel.composeapp.generated.resources.TFT10_Champion_Seraphine
import heartsteel.composeapp.generated.resources.TFT10_Champion_Sett
import heartsteel.composeapp.generated.resources.TFT10_Champion_Sona
import heartsteel.composeapp.generated.resources.TFT10_Champion_TahmKench
import heartsteel.composeapp.generated.resources.TFT10_Champion_Taric
import heartsteel.composeapp.generated.resources.TFT10_Champion_Thresh
import heartsteel.composeapp.generated.resources.TFT10_Champion_TwistedFate
import heartsteel.composeapp.generated.resources.TFT10_Champion_Twitch
import heartsteel.composeapp.generated.resources.TFT10_Champion_Urgot
import heartsteel.composeapp.generated.resources.TFT10_Champion_Vex
import heartsteel.composeapp.generated.resources.TFT10_Champion_Vi
import heartsteel.composeapp.generated.resources.TFT10_Champion_Viego
import heartsteel.composeapp.generated.resources.TFT10_Champion_Yasuo
import heartsteel.composeapp.generated.resources.TFT10_Champion_Yone
import heartsteel.composeapp.generated.resources.TFT10_Champion_Yorick
import heartsteel.composeapp.generated.resources.TFT10_Champion_Zac
import heartsteel.composeapp.generated.resources.TFT10_Champion_Zed
import heartsteel.composeapp.generated.resources.TFT10_Champion_Ziggs
import heartsteel.composeapp.generated.resources.Trait_Icon_10_8Bit
import heartsteel.composeapp.generated.resources.Trait_Icon_10_BigSad
import heartsteel.composeapp.generated.resources.Trait_Icon_10_BigShot
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Breakout
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Classical
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Country
import heartsteel.composeapp.generated.resources.Trait_Icon_10_CrowdDiver
import heartsteel.composeapp.generated.resources.Trait_Icon_10_DJ
import heartsteel.composeapp.generated.resources.Trait_Icon_10_EDM
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Edgelord
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Funk
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Heartsteel
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Hyperpop
import heartsteel.composeapp.generated.resources.Trait_Icon_10_ILLBeats
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Jazz
import heartsteel.composeapp.generated.resources.Trait_Icon_10_KDA
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Mosher
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Pentakill
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Punk
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Rapidfire
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Superfan
import heartsteel.composeapp.generated.resources.Trait_Icon_10_TrueDamage
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Warden
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Wildcard
import heartsteel.composeapp.generated.resources.Trait_Icon_3_Brawler
import heartsteel.composeapp.generated.resources.Trait_Icon_4_Dazzler
import heartsteel.composeapp.generated.resources.Trait_Icon_4_Executioner
import heartsteel.composeapp.generated.resources.Trait_Icon_5_Sorcerer
import heartsteel.composeapp.generated.resources.Trait_Icon_7_Guardian

val traitHrefToEnum = mapOf(
    "8bit" to TraitEnum.Trait8Bit,
    "bigsad" to TraitEnum.TraitBigSad,
    "classical" to TraitEnum.TraitClassical,
    "country" to TraitEnum.TraitCountry,
    "dj" to TraitEnum.TraitDJ,
    "edm" to TraitEnum.TraitEDM,
    "disco" to TraitEnum.TraitFunk,
    "heartsteel" to TraitEnum.TraitHeartsteel,
    "hyperpop" to TraitEnum.TraitHyperpop,
    "illbeats" to TraitEnum.TraitILLBeats,
    "jazz" to TraitEnum.TraitJazz,
    "kda" to TraitEnum.TraitKDA,
    "pentakill" to TraitEnum.TraitPentakill,
    "punk" to TraitEnum.TraitPunk,
    "truedamage" to TraitEnum.TraitTrueDamage,
    "piano" to TraitEnum.TraitPiano,
)

val trailDetailMap = mapOf(
    // KDA
    TraitEnum.TraitKDA to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Lillia,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitSuperfan,
                    icon = Res.drawable.Trait_Icon_10_Superfan,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Evelynn,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitCrowdDiver,
                    icon = Res.drawable.Trait_Icon_10_CrowdDiver,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Kaisa,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitBigShot,
                    icon = Res.drawable.Trait_Icon_10_BigShot,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Seraphine,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Neeko,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitSuperfan,
                    icon = Res.drawable.Trait_Icon_10_Superfan,
                ),
                TraitTip(
                    key = TraitEnum.TraitGuardian,
                    icon = Res.drawable.Trait_Icon_7_Guardian,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Ahri,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Akali,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitKDA,
                    icon = Res.drawable.Trait_Icon_10_KDA,
                ),
                TraitTip(
                    key = TraitEnum.TraitBreakout,
                    icon = Res.drawable.Trait_Icon_10_Breakout,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
    ),
    // 心之钢
    TraitEnum.TraitHeartsteel to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_KSante,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Aphelios,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitRapidfire,
                    icon = Res.drawable.Trait_Icon_10_Rapidfire,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Yone,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
                TraitTip(
                    key = TraitEnum.TraitCrowdDiver,
                    icon = Res.drawable.Trait_Icon_10_CrowdDiver,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Sett,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Ezreal,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitBigShot,
                    icon = Res.drawable.Trait_Icon_10_BigShot,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Kayn,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHeartsteel,
                    icon = Res.drawable.Trait_Icon_10_Heartsteel,
                ),
                TraitTip(
                    key = TraitEnum.TraitWildcard,
                    icon = Res.drawable.Trait_Icon_10_Wildcard,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
            ),
        ),
    ),
    // 五杀摇滚
    TraitEnum.TraitPentakill to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Olaf,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Kayle,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Gnar,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitSuperfan,
                    icon = Res.drawable.Trait_Icon_10_Superfan,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Mordekaiser,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Viego,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Karthus,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Yorick,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPentakill,
                    icon = Res.drawable.Trait_Icon_10_Pentakill,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
    ),
    // 真实伤害
    TraitEnum.TraitTrueDamage to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Yasuo,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Kennen,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitSuperfan,
                    icon = Res.drawable.Trait_Icon_10_Superfan,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Senna,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitRapidfire,
                    icon = Res.drawable.Trait_Icon_10_Rapidfire,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Ekko,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Akali_TrueDamage,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitBreakout,
                    icon = Res.drawable.Trait_Icon_10_Breakout,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Qiyana,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitTrueDamage,
                    icon = Res.drawable.Trait_Icon_10_TrueDamage,
                ),
                TraitTip(
                    key = TraitEnum.TraitCrowdDiver,
                    icon = Res.drawable.Trait_Icon_10_CrowdDiver,
                ),
            ),
        ),
    ),
    // 8比特
    TraitEnum.Trait8Bit to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Corki,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.Trait8Bit,
                    icon = Res.drawable.Trait_Icon_10_8Bit,
                ),
                TraitTip(
                    key = TraitEnum.TraitBigShot,
                    icon = Res.drawable.Trait_Icon_10_BigShot,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Garen,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.Trait8Bit,
                    icon = Res.drawable.Trait_Icon_10_8Bit,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Riven,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.Trait8Bit,
                    icon = Res.drawable.Trait_Icon_10_8Bit,
                ),
                TraitTip(
                    key = TraitEnum.TraitEdgelord,
                    icon = Res.drawable.Trait_Icon_10_Edgelord,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Caitlyn,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.Trait8Bit,
                    icon = Res.drawable.Trait_Icon_10_8Bit,
                ),
                TraitTip(
                    key = TraitEnum.TraitRapidfire,
                    icon = Res.drawable.Trait_Icon_10_Rapidfire,
                ),
            ),
        ),
    ),
    // 迪斯科
    TraitEnum.TraitFunk to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Nami,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitFunk,
                    icon = Res.drawable.Trait_Icon_10_Funk,
                ),
                TraitTip(
                    key = TraitEnum.TraitDazzler,
                    icon = Res.drawable.Trait_Icon_4_Dazzler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Taric,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitFunk,
                    icon = Res.drawable.Trait_Icon_10_Funk,
                ),
                TraitTip(
                    key = TraitEnum.TraitGuardian,
                    icon = Res.drawable.Trait_Icon_7_Guardian,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Gragas,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitFunk,
                    icon = Res.drawable.Trait_Icon_10_Funk,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Blitzcrank,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitFunk,
                    icon = Res.drawable.Trait_Icon_10_Funk,
                ),
                TraitTip(
                    key = TraitEnum.TraitWarden,
                    icon = Res.drawable.Trait_Icon_10_Warden,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_TwistedFate,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitFunk,
                    icon = Res.drawable.Trait_Icon_10_Funk,
                ),
                TraitTip(
                    key = TraitEnum.TraitDazzler,
                    icon = Res.drawable.Trait_Icon_4_Dazzler,
                ),
            ),
        ),
    ),
    // 乡村音乐
    TraitEnum.TraitCountry to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_TahmKench,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitCountry,
                    icon = Res.drawable.Trait_Icon_10_Country,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Katarina,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitCountry,
                    icon = Res.drawable.Trait_Icon_10_Country,
                ),
                TraitTip(
                    key = TraitEnum.TraitCrowdDiver,
                    icon = Res.drawable.Trait_Icon_10_CrowdDiver,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Urgot,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitCountry,
                    icon = Res.drawable.Trait_Icon_10_Country,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Samira,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitCountry,
                    icon = Res.drawable.Trait_Icon_10_Country,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Thresh,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitCountry,
                    icon = Res.drawable.Trait_Icon_10_Country,
                ),
                TraitTip(
                    key = TraitEnum.TraitGuardian,
                    icon = Res.drawable.Trait_Icon_7_Guardian,
                ),
            ),
        ),
    ),
    // 电子舞曲
    TraitEnum.TraitEDM to listOf(
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Jax,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitEDM,
                    icon = Res.drawable.Trait_Icon_10_EDM,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Lux,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitEDM,
                    icon = Res.drawable.Trait_Icon_10_EDM,
                ),
                TraitTip(
                    key = TraitEnum.TraitDazzler,
                    icon = Res.drawable.Trait_Icon_4_Dazzler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Zac,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitEDM,
                    icon = Res.drawable.Trait_Icon_10_EDM,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Zed,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitEDM,
                    icon = Res.drawable.Trait_Icon_10_EDM,
                ),
                TraitTip(
                    key = TraitEnum.TraitCrowdDiver,
                    icon = Res.drawable.Trait_Icon_10_CrowdDiver,
                ),
            ),
        ),
    ),
    // EMO
    TraitEnum.TraitBigSad to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Annie,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitBigSad,
                    icon = Res.drawable.Trait_Icon_10_BigSad,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Amumu,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitBigSad,
                    icon = Res.drawable.Trait_Icon_10_BigSad,
                ),
                TraitTip(
                    key = TraitEnum.TraitGuardian,
                    icon = Res.drawable.Trait_Icon_7_Guardian,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Vex,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitBigSad,
                    icon = Res.drawable.Trait_Icon_10_BigSad,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 4,
            image = Res.drawable.TFT10_Champion_Poppy,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitBigSad,
                    icon = Res.drawable.Trait_Icon_10_BigSad,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
    ),
    // 朋克
    TraitEnum.TraitPunk to listOf(
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Vi,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPunk,
                    icon = Res.drawable.Trait_Icon_10_Punk,
                ),
                TraitTip(
                    key = TraitEnum.TraitMosher,
                    icon = Res.drawable.Trait_Icon_10_Mosher,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 1,
            image = Res.drawable.TFT10_Champion_Jinx,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPunk,
                    icon = Res.drawable.Trait_Icon_10_Punk,
                ),
                TraitTip(
                    key = TraitEnum.TraitRapidfire,
                    icon = Res.drawable.Trait_Icon_10_Rapidfire,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Pantheon,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPunk,
                    icon = Res.drawable.Trait_Icon_10_Punk,
                ),
                TraitTip(
                    key = TraitEnum.TraitGuardian,
                    icon = Res.drawable.Trait_Icon_7_Guardian,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Twitch,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitPunk,
                    icon = Res.drawable.Trait_Icon_10_Punk,
                ),
                TraitTip(
                    key = TraitEnum.TraitExecutioner,
                    icon = Res.drawable.Trait_Icon_4_Executioner,
                ),
            ),
        ),
    ),
    // 爵士乐
    TraitEnum.TraitJazz to listOf(
        TraitChampionInfo(
            level = 2,
            image = Res.drawable.TFT10_Champion_Bard,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitJazz,
                    icon = Res.drawable.Trait_Icon_10_Jazz,
                ),
                TraitTip(
                    key = TraitEnum.TraitDazzler,
                    icon = Res.drawable.Trait_Icon_4_Dazzler,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_MissFortune,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitJazz,
                    icon = Res.drawable.Trait_Icon_10_Jazz,
                ),
                TraitTip(
                    key = TraitEnum.TraitBigShot,
                    icon = Res.drawable.Trait_Icon_10_BigShot,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Lucian,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitJazz,
                    icon = Res.drawable.Trait_Icon_10_Jazz,
                ),
                TraitTip(
                    key = TraitEnum.TraitRapidfire,
                    icon = Res.drawable.Trait_Icon_10_Rapidfire,
                ),
            ),
        ),
    ),
    // 高能流行
    TraitEnum.TraitHyperpop to listOf(
        TraitChampionInfo(
            level = 3,
            image = Res.drawable.TFT10_Champion_Lulu,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHyperpop,
                    icon = Res.drawable.Trait_Icon_10_Hyperpop,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
            ),
        ),
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Ziggs,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitHyperpop,
                    icon = Res.drawable.Trait_Icon_10_Hyperpop,
                ),
                TraitTip(
                    key = TraitEnum.TraitDazzler,
                    icon = Res.drawable.Trait_Icon_4_Dazzler,
                ),
            ),
        ),
    ),
    // 大触打击乐
    TraitEnum.TraitILLBeats to listOf(
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Illaoi,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitILLBeats,
                    icon = Res.drawable.Trait_Icon_10_ILLBeats,
                ),
                TraitTip(
                    key = TraitEnum.TraitBrawler,
                    icon = Res.drawable.Trait_Icon_3_Brawler,
                ),
            ),
        ),
    ),
    // 戏命师
    TraitEnum.TraitClassical to listOf(
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Jhin,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitClassical,
                    icon = Res.drawable.Trait_Icon_10_Classical,
                ),
                TraitTip(
                    key = TraitEnum.TraitBigShot,
                    icon = Res.drawable.Trait_Icon_10_BigShot,
                ),
            ),
        ),
    ),
    // DJ娑娜
    TraitEnum.TraitDJ to listOf(
        TraitChampionInfo(
            level = 5,
            image = Res.drawable.TFT10_Champion_Sona,
            tipList = listOf(
                TraitTip(
                    key = TraitEnum.TraitDJ,
                    icon = Res.drawable.Trait_Icon_10_DJ,
                ),
                TraitTip(
                    key = TraitEnum.TraitSorcerer,
                    icon = Res.drawable.Trait_Icon_5_Sorcerer,
                ),
            ),
        ),
    ),
)