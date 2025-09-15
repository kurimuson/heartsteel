package com.crimson.app.heartsteel.model

import kotlinx.serialization.Serializable

@Serializable
enum class TraitEnum {

    // 主
    TraitPiano,
    Trait8Bit,
    TraitBigSad,
    TraitClassical,
    TraitCountry,
    TraitDJ,
    TraitEDM,
    TraitFunk,
    TraitHeartsteel,
    TraitHyperpop,
    TraitILLBeats,
    TraitJazz,
    TraitKDA,
    TraitPentakill,
    TraitPunk,
    TraitTrueDamage,

    // 副
    TraitDazzler, // 耀光使
    TraitBrawler, // 格斗家
    TraitMosher, // 舞者
    TraitEdgelord, // 刀锋领主
    TraitRapidfire, // 疾射枪手
    TraitGuardian, // 护卫
    TraitWarden, // 秘术护卫
    TraitSuperfan, // 超级粉丝
    TraitSorcerer, // 法师
    TraitBigShot, // 大腕射手
    TraitCrowdDiver, // 音浪刺客
    TraitExecutioner, // 裁决使
    TraitWildcard, // 影流之镰
    TraitBreakout, // 双修出道

}