package com.crimson.app.heartsteel.common

import heartsteel.composeapp.generated.resources.Res
import heartsteel.composeapp.generated.resources.TFT10_Item_8bitEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_BigShotEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_BrawlerEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_CountryEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_CrowdDiverEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_DJ_Mode1
import heartsteel.composeapp.generated.resources.TFT10_Item_DJ_Mode2
import heartsteel.composeapp.generated.resources.TFT10_Item_DJ_Mode3
import heartsteel.composeapp.generated.resources.TFT10_Item_DazzlerEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_DiscoEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_EdgelordEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_EmoEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_ExecutionerEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_FighterEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_GuardianEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_HyperPopEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_JazzEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_KDAEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_PBJEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_PentakillEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_PunkEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_RapidfireEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_SpellweaverEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_SuperfanEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_TrueDamageEmblem
import heartsteel.composeapp.generated.resources.TFT10_Item_WardenEmblem
import heartsteel.composeapp.generated.resources.TFT_Assist_Cost1x5
import heartsteel.composeapp.generated.resources.TFT_Assist_RandomComponent
import heartsteel.composeapp.generated.resources.TFT_Assist_RandomOrnnItem
import heartsteel.composeapp.generated.resources.TFT_Assist_RandomRadiantItem
import heartsteel.composeapp.generated.resources.TFT_Consumable_ChampionDuplicator_III
import heartsteel.composeapp.generated.resources.TFT_Consumable_MasterworkUpgrade
import heartsteel.composeapp.generated.resources.TFT_Consumable_NeekosHelp
import heartsteel.composeapp.generated.resources.TFT_Item_DebugBase
import heartsteel.composeapp.generated.resources.TFT_Item_DebugCrit
import heartsteel.composeapp.generated.resources.TFT_Item_DebugDamage
import heartsteel.composeapp.generated.resources.TFT_Item_DebugDamageAmp
import heartsteel.composeapp.generated.resources.TFT_Item_DebugFirstHit
import heartsteel.composeapp.generated.resources.TFT_Item_DebugMana
import heartsteel.composeapp.generated.resources.TFT_Item_DebugShield
import heartsteel.composeapp.generated.resources.TFT_Item_DebugStun
import heartsteel.composeapp.generated.resources.TFT_Item_DebugTaunt
import heartsteel.composeapp.generated.resources.TFT_Item_DebugUnitID
import heartsteel.composeapp.generated.resources.TFT_Item_ForceOfNature
import heartsteel.composeapp.generated.resources.TFT_Item_FryingPan
import heartsteel.composeapp.generated.resources.TFT_Item_SentinelSwarm
import heartsteel.composeapp.generated.resources.TFT_Item_TacticiansRing
import heartsteel.composeapp.generated.resources.TFT_Item_TacticiansScepter

val imageNameList = listOf(
    "TFT_Assist_RandomComponent",
    "TFT_Assist_RandomOrnnItem",
    "TFT_Assist_RandomRadiantItem",
    "TFT_Consumable_ChampionDuplicator_III",
    "TFT_Consumable_NeekosHelp",
    "TFT_Consumable_MasterworkUpgrade",
    "TFT_Assist_Cost1x5",
    "TFT_Item_SentinelSwarm",
    "TFT_Item_FryingPan",
    "TFT_Item_ForceOfNature",
    "TFT_Item_TacticiansScepter",
    "TFT_Item_TacticiansRing",
    "TFT10_Item_DJ_Mode1",
    "TFT10_Item_DJ_Mode2",
    "TFT10_Item_DJ_Mode3",
    "TFT10_Item_KDAEmblem",
    "TFT10_Item_PBJEmblem",
    "TFT10_Item_TrueDamageEmblem",
    "TFT10_Item_PentakillEmblem",
    "TFT10_Item_8bitEmblem",
    "TFT10_Item_EmoEmblem",
    "TFT10_Item_CountryEmblem",
    "TFT10_Item_PunkEmblem",
    "TFT10_Item_BigShotEmblem",
    "TFT10_Item_BrawlerEmblem",
    "TFT10_Item_CrowdDiverEmblem",
    "TFT10_Item_DazzlerEmblem",
    "TFT10_Item_DiscoEmblem",
    "TFT10_Item_EdgelordEmblem",
    "TFT10_Item_ExecutionerEmblem",
    "TFT10_Item_FighterEmblem",
    "TFT10_Item_GuardianEmblem",
    "TFT10_Item_HyperPopEmblem",
    "TFT10_Item_JazzEmblem",
    "TFT10_Item_RapidfireEmblem",
    "TFT10_Item_SpellweaverEmblem",
    "TFT10_Item_SuperfanEmblem",
    "TFT10_Item_WardenEmblem",
    "TFT_Item_DebugBase",
    "TFT_Item_DebugCrit",
    "TFT_Item_DebugDamage",
    "TFT_Item_DebugDamageAmp",
    "TFT_Item_DebugFirstHit",
    "TFT_Item_DebugMana",
    "TFT_Item_DebugShield",
    "TFT_Item_DebugStun",
    "TFT_Item_DebugTaunt",
    "TFT_Item_DebugUnitID",
)

val imageResourceMap = mapOf(
    "TFT_Assist_RandomComponent" to Res.drawable.TFT_Assist_RandomComponent,
    "TFT_Assist_RandomOrnnItem" to Res.drawable.TFT_Assist_RandomOrnnItem,
    "TFT_Assist_RandomRadiantItem" to Res.drawable.TFT_Assist_RandomRadiantItem,
    "TFT_Consumable_ChampionDuplicator_III" to Res.drawable.TFT_Consumable_ChampionDuplicator_III,
    "TFT_Consumable_NeekosHelp" to Res.drawable.TFT_Consumable_NeekosHelp,
    "TFT_Consumable_MasterworkUpgrade" to Res.drawable.TFT_Consumable_MasterworkUpgrade,
    "TFT_Assist_Cost1x5" to Res.drawable.TFT_Assist_Cost1x5,
    "TFT_Item_SentinelSwarm" to Res.drawable.TFT_Item_SentinelSwarm,
    "TFT_Item_FryingPan" to Res.drawable.TFT_Item_FryingPan,
    "TFT_Item_ForceOfNature" to Res.drawable.TFT_Item_ForceOfNature,
    "TFT_Item_TacticiansScepter" to Res.drawable.TFT_Item_TacticiansScepter,
    "TFT_Item_TacticiansRing" to Res.drawable.TFT_Item_TacticiansRing,
    "TFT10_Item_DJ_Mode1" to Res.drawable.TFT10_Item_DJ_Mode1,
    "TFT10_Item_DJ_Mode2" to Res.drawable.TFT10_Item_DJ_Mode2,
    "TFT10_Item_DJ_Mode3" to Res.drawable.TFT10_Item_DJ_Mode3,
    "TFT10_Item_KDAEmblem" to Res.drawable.TFT10_Item_KDAEmblem,
    "TFT10_Item_PBJEmblem" to Res.drawable.TFT10_Item_PBJEmblem,
    "TFT10_Item_TrueDamageEmblem" to Res.drawable.TFT10_Item_TrueDamageEmblem,
    "TFT10_Item_PentakillEmblem" to Res.drawable.TFT10_Item_PentakillEmblem,
    "TFT10_Item_8bitEmblem" to Res.drawable.TFT10_Item_8bitEmblem,
    "TFT10_Item_EmoEmblem" to Res.drawable.TFT10_Item_EmoEmblem,
    "TFT10_Item_CountryEmblem" to Res.drawable.TFT10_Item_CountryEmblem,
    "TFT10_Item_PunkEmblem" to Res.drawable.TFT10_Item_PunkEmblem,
    "TFT10_Item_BigShotEmblem" to Res.drawable.TFT10_Item_BigShotEmblem,
    "TFT10_Item_BrawlerEmblem" to Res.drawable.TFT10_Item_BrawlerEmblem,
    "TFT10_Item_CrowdDiverEmblem" to Res.drawable.TFT10_Item_CrowdDiverEmblem,
    "TFT10_Item_DazzlerEmblem" to Res.drawable.TFT10_Item_DazzlerEmblem,
    "TFT10_Item_DiscoEmblem" to Res.drawable.TFT10_Item_DiscoEmblem,
    "TFT10_Item_EdgelordEmblem" to Res.drawable.TFT10_Item_EdgelordEmblem,
    "TFT10_Item_ExecutionerEmblem" to Res.drawable.TFT10_Item_ExecutionerEmblem,
    "TFT10_Item_FighterEmblem" to Res.drawable.TFT10_Item_FighterEmblem,
    "TFT10_Item_GuardianEmblem" to Res.drawable.TFT10_Item_GuardianEmblem,
    "TFT10_Item_HyperPopEmblem" to Res.drawable.TFT10_Item_HyperPopEmblem,
    "TFT10_Item_JazzEmblem" to Res.drawable.TFT10_Item_JazzEmblem,
    "TFT10_Item_RapidfireEmblem" to Res.drawable.TFT10_Item_RapidfireEmblem,
    "TFT10_Item_SpellweaverEmblem" to Res.drawable.TFT10_Item_SpellweaverEmblem,
    "TFT10_Item_SuperfanEmblem" to Res.drawable.TFT10_Item_SuperfanEmblem,
    "TFT10_Item_WardenEmblem" to Res.drawable.TFT10_Item_WardenEmblem,
    "TFT_Item_DebugBase" to Res.drawable.TFT_Item_DebugBase,
    "TFT_Item_DebugCrit" to Res.drawable.TFT_Item_DebugCrit,
    "TFT_Item_DebugDamage" to Res.drawable.TFT_Item_DebugDamage,
    "TFT_Item_DebugDamageAmp" to Res.drawable.TFT_Item_DebugDamageAmp,
    "TFT_Item_DebugFirstHit" to Res.drawable.TFT_Item_DebugFirstHit,
    "TFT_Item_DebugMana" to Res.drawable.TFT_Item_DebugMana,
    "TFT_Item_DebugShield" to Res.drawable.TFT_Item_DebugShield,
    "TFT_Item_DebugStun" to Res.drawable.TFT_Item_DebugStun,
    "TFT_Item_DebugTaunt" to Res.drawable.TFT_Item_DebugTaunt,
    "TFT_Item_DebugUnitID" to Res.drawable.TFT_Item_DebugUnitID,
)
