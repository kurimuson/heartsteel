package com.crimson.app.heartsteel.common

import com.crimson.app.heartsteel.model.TraitEnum
import com.crimson.app.heartsteel.model.TraitInfo
import heartsteel.composeapp.generated.resources.Res
import heartsteel.composeapp.generated.resources.Trait_Icon_10_8Bit
import heartsteel.composeapp.generated.resources.Trait_Icon_10_BigSad
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Classical
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Country
import heartsteel.composeapp.generated.resources.Trait_Icon_10_DJ
import heartsteel.composeapp.generated.resources.Trait_Icon_10_EDM
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Funk
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Heartsteel
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Hyperpop
import heartsteel.composeapp.generated.resources.Trait_Icon_10_ILLBeats
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Jazz
import heartsteel.composeapp.generated.resources.Trait_Icon_10_KDA
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Pentakill
import heartsteel.composeapp.generated.resources.Trait_Icon_10_Punk
import heartsteel.composeapp.generated.resources.Trait_Icon_10_TrueDamage
import heartsteel.composeapp.generated.resources.Trait_Panel_10_8Bit
import heartsteel.composeapp.generated.resources.Trait_Panel_10_BigSad
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Classical
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Country
import heartsteel.composeapp.generated.resources.Trait_Panel_10_DJ
import heartsteel.composeapp.generated.resources.Trait_Panel_10_EDM
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Funk
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Heartsteel
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Hyperpop
import heartsteel.composeapp.generated.resources.Trait_Panel_10_ILLBeats
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Jazz
import heartsteel.composeapp.generated.resources.Trait_Panel_10_KDA
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Pentakill
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Piano
import heartsteel.composeapp.generated.resources.Trait_Panel_10_Punk
import heartsteel.composeapp.generated.resources.Trait_Panel_10_TrueDamage

val traitInfoMap: Map<TraitEnum, TraitInfo>
    get() {
        val mapOf = mapOf(
            TraitEnum.Trait8Bit to TraitInfo(
                key = TraitEnum.Trait8Bit,
                name = "8比特",
                href = "8bit",
                icon = Res.drawable.Trait_Icon_10_8Bit,
                panel = Res.drawable.Trait_Panel_10_8Bit,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "8bit_1_ry",
                        "tr" to "8bit_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "8bit_2_ry",
                        "tr" to "8bit_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitBigSad to TraitInfo(
                key = TraitEnum.TraitBigSad,
                name = "EMO",
                href = "bigsad",
                icon = Res.drawable.Trait_Icon_10_BigSad,
                panel = Res.drawable.Trait_Panel_10_BigSad,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "bigsad_1_ry",
                        "tr" to "bigsad_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "bigsad_2_ry",
                        "tr" to "bigsad_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitClassical to TraitInfo(
                key = TraitEnum.TraitClassical,
                name = "戏命师",
                href = "classical",
                icon = Res.drawable.Trait_Icon_10_Classical,
                panel = Res.drawable.Trait_Panel_10_Classical,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "s_1_jin",
                    ),
                    "2" to mapOf(
                        "ry" to "s_2_jin",
                    ),
                )
            ),
            TraitEnum.TraitCountry to TraitInfo(
                key = TraitEnum.TraitCountry,
                name = "乡村音乐",
                href = "country",
                icon = Res.drawable.Trait_Icon_10_Country,
                panel = Res.drawable.Trait_Panel_10_Country,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "country_1_ry",
                        "tr" to "country_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "country_2_ry",
                        "tr" to "country_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitDJ to TraitInfo(
                key = TraitEnum.TraitDJ,
                name = "DJ娑娜",
                href = "dj",
                icon = Res.drawable.Trait_Icon_10_DJ,
                panel = Res.drawable.Trait_Panel_10_DJ,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "tr" to "s_1_sona",
                    ),
                    "2" to mapOf(
                        "tr" to "s_2_sona",
                    ),
                )
            ),
            TraitEnum.TraitEDM to TraitInfo(
                key = TraitEnum.TraitEDM,
                name = "电子舞曲",
                href = "edm",
                icon = Res.drawable.Trait_Icon_10_EDM,
                panel = Res.drawable.Trait_Panel_10_EDM,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "tranceformer_1_ry",
                        "tr" to "tranceformer_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "tranceformer_2_ry",
                        "tr" to "tranceformer_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitFunk to TraitInfo(
                key = TraitEnum.TraitFunk,
                name = "迪斯科",
                href = "disco",
                icon = Res.drawable.Trait_Icon_10_Funk,
                panel = Res.drawable.Trait_Panel_10_Funk,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "disco_1_ry",
                        "tr" to "disco_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "disco_2_ry",
                        "tr" to "disco_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitHeartsteel to TraitInfo(
                key = TraitEnum.TraitHeartsteel,
                name = "心之钢",
                href = "heartsteel",
                icon = Res.drawable.Trait_Icon_10_Heartsteel,
                panel = Res.drawable.Trait_Panel_10_Heartsteel,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "heartsteel_1_ry",
                        "bg" to "heartsteel_1_bg",
                        "tr" to "heartsteel_1_tr",
                    ),
                    "2" to mapOf(
                        "ry" to "heartsteel_2_ry",
                        "bg" to "heartsteel_2_bg",
                        "tr" to "heartsteel_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitHyperpop to TraitInfo(
                key = TraitEnum.TraitHyperpop,
                name = "高能流行",
                href = "hyperpop",
                icon = Res.drawable.Trait_Icon_10_Hyperpop,
                panel = Res.drawable.Trait_Panel_10_Hyperpop,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "glitterbomb_1_bg",
                    ),
                    "2" to mapOf(
                        "ry" to "glitterbomb_2_bg",
                    ),
                )
            ),
            TraitEnum.TraitILLBeats to TraitInfo(
                key = TraitEnum.TraitILLBeats,
                name = "大触打击乐",
                href = "illbeats",
                icon = Res.drawable.Trait_Icon_10_ILLBeats,
                panel = Res.drawable.Trait_Panel_10_ILLBeats,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "tr" to "s_1_ely",
                    ),
                    "2" to mapOf(
                        "tr" to "s_2_ely",
                    ),
                )
            ),
            TraitEnum.TraitJazz to TraitInfo(
                key = TraitEnum.TraitJazz,
                name = "爵士乐",
                href = "jazz",
                icon = Res.drawable.Trait_Icon_10_Jazz,
                panel = Res.drawable.Trait_Panel_10_Jazz,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "jazz_1_ry",
                    ),
                    "2" to mapOf(
                        "ry" to "jazz_2_ry",
                    ),
                )
            ),
            TraitEnum.TraitKDA to TraitInfo(
                key = TraitEnum.TraitKDA,
                name = "KDA",
                href = "kda",
                icon = Res.drawable.Trait_Icon_10_KDA,
                panel = Res.drawable.Trait_Panel_10_KDA,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "kda_1_ry",
                        "bg" to "kda_1_bg",
                        "tr" to "kda_1_tr",
                    ),
                    "2" to mapOf(
                        "ry" to "kda_2_ry",
                        "bg" to "kda_2_bg",
                        "tr" to "kda_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitPentakill to TraitInfo(
                key = TraitEnum.TraitPentakill,
                name = "五杀摇滚",
                href = "pentakill",
                icon = Res.drawable.Trait_Icon_10_Pentakill,
                panel = Res.drawable.Trait_Panel_10_Pentakill,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "pentakill_1_ry",
                        "bg" to "pentakill_1_bg",
                        "tr" to "pentakill_1_tr",
                    ),
                    "2" to mapOf(
                        "ry" to "pentakill_2_ry",
                        "bg" to "pentakill_2_bg",
                        "tr" to "pentakill_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitPunk to TraitInfo(
                key = TraitEnum.TraitPunk,
                name = "朋克",
                href = "punk",
                icon = Res.drawable.Trait_Icon_10_Punk,
                panel = Res.drawable.Trait_Panel_10_Punk,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "bg" to "anarchist_1_ry",
                        "tr" to "anarchist_1_tr",
                    ),
                    "2" to mapOf(
                        "bg" to "anarchist_2_ry",
                        "tr" to "anarchist_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitTrueDamage to TraitInfo(
                key = TraitEnum.TraitTrueDamage,
                name = "真实伤害",
                href = "truedamage",
                icon = Res.drawable.Trait_Icon_10_TrueDamage,
                panel = Res.drawable.Trait_Panel_10_TrueDamage,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "truedamage_1_ry",
                        "bg" to "truedamage_1_bg",
                        "tr" to "truedamage_1_tr",
                    ),
                    "2" to mapOf(
                        "ry" to "truedamage_2_ry",
                        "bg" to "truedamage_2_bg",
                        "tr" to "truedamage_2_tr",
                    ),
                )
            ),
            TraitEnum.TraitPiano to TraitInfo(
                key = TraitEnum.TraitPiano,
                name = "钢琴曲",
                href = "piano",
                icon = null,
                panel = Res.drawable.Trait_Panel_10_Piano,
                musicTrackMap = mapOf(
                    "1" to mapOf(
                        "ry" to "s_1_piano",
                    ),
                    "2" to mapOf(
                        "ry" to "s_2_piano",
                    ),
                )
            ),
        )
        return mapOf
    }

val traitKeyListMap = mapOf(
    // 主音
    "ry" to listOf(
        TraitEnum.TraitKDA,
        TraitEnum.TraitHeartsteel,
        TraitEnum.TraitPentakill,
        TraitEnum.TraitTrueDamage,
        TraitEnum.TraitHyperpop,
        TraitEnum.TraitJazz,
        TraitEnum.TraitClassical,
        TraitEnum.TraitPiano,
    ),
    // 伴奏
    "bg" to listOf(
        TraitEnum.TraitKDA,
        TraitEnum.TraitHeartsteel,
        TraitEnum.TraitPentakill,
        TraitEnum.TraitTrueDamage,
        TraitEnum.Trait8Bit,
        TraitEnum.TraitCountry,
        TraitEnum.TraitFunk,
        TraitEnum.TraitEDM,
        TraitEnum.TraitBigSad,
        TraitEnum.TraitPunk,
    ),
    // 鼓组
    "tr" to listOf(
        TraitEnum.TraitKDA,
        TraitEnum.TraitHeartsteel,
        TraitEnum.TraitPentakill,
        TraitEnum.TraitTrueDamage,
        TraitEnum.Trait8Bit,
        TraitEnum.TraitCountry,
        TraitEnum.TraitFunk,
        TraitEnum.TraitEDM,
        TraitEnum.TraitBigSad,
        TraitEnum.TraitPunk,
        TraitEnum.TraitILLBeats,
        TraitEnum.TraitDJ,
    ),
)

val traitItemListGroupMap = mapOf(
    // 三音轨组
    "3" to listOf(
        TraitEnum.TraitKDA,
        TraitEnum.TraitHeartsteel,
        TraitEnum.TraitPentakill,
        TraitEnum.TraitTrueDamage,
    ),
    // 双音轨组
    "2" to listOf(
        TraitEnum.Trait8Bit,
        TraitEnum.TraitFunk,
        TraitEnum.TraitCountry,
        TraitEnum.TraitEDM,
        TraitEnum.TraitBigSad,
        TraitEnum.TraitPunk,
    ),
    // 单音轨组
    "1" to listOf(
        TraitEnum.TraitJazz,
        TraitEnum.TraitHyperpop,
        TraitEnum.TraitILLBeats,
        TraitEnum.TraitClassical,
        TraitEnum.TraitDJ,
    ),
)

val traitChineseNameMap = mapOf(
    // 主
    TraitEnum.TraitPiano to "钢琴曲",
    TraitEnum.Trait8Bit to "8比特",
    TraitEnum.TraitBigSad to "EMO",
    TraitEnum.TraitClassical to "戏命师",
    TraitEnum.TraitCountry to "乡村音乐",
    TraitEnum.TraitDJ to "DJ娑娜",
    TraitEnum.TraitEDM to "电子舞曲",
    TraitEnum.TraitFunk to "迪斯科",
    TraitEnum.TraitHeartsteel to "心之钢",
    TraitEnum.TraitHyperpop to "高能流行",
    TraitEnum.TraitILLBeats to "大触打击乐",
    TraitEnum.TraitJazz to "爵士乐",
    TraitEnum.TraitKDA to "KDA",
    TraitEnum.TraitPentakill to "五杀摇滚",
    TraitEnum.TraitPunk to "朋克",
    TraitEnum.TraitTrueDamage to "真实伤害",
    // 副
    TraitEnum.TraitDazzler to "耀光使",
    TraitEnum.TraitBrawler to "格斗家",
    TraitEnum.TraitMosher to "舞者",
    TraitEnum.TraitEdgelord to "刀锋领主",
    TraitEnum.TraitRapidfire to "疾射枪手",
    TraitEnum.TraitGuardian to "护卫",
    TraitEnum.TraitWarden to "秘术护卫",
    TraitEnum.TraitSuperfan to "超级粉丝",
    TraitEnum.TraitSorcerer to "法师",
    TraitEnum.TraitBigShot to "大腕射手",
    TraitEnum.TraitCrowdDiver to "音浪刺客",
    TraitEnum.TraitExecutioner to "裁决使",
    TraitEnum.TraitWildcard to "影流之镰",
    TraitEnum.TraitBreakout to "双修出道",
)
