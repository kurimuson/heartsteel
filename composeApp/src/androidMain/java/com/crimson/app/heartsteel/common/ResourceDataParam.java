package com.crimson.app.heartsteel.common;

import java.util.HashMap;

public class ResourceDataParam {

    private static final int MUSIC_STEP1_TIME = 3 * 60 * 1000 + 24 * 1000 + 500; // 3分24.5秒
    private static final int MUSIC_STEP2_TIME = 2 * 60 * 1000 + 49 * 1000 + 500; // 2分49.5秒

    private static final HashMap<String, String> MUSIC_STEP1_TRACK_MAP = new HashMap<>() {
        {
            put("heartsteel_1_ry", "music_track/heartsteel_1_ry.pcm");
            put("heartsteel_1_bg", "music_track/heartsteel_1_bg.pcm");
            put("heartsteel_1_tr", "music_track/heartsteel_1_tr.pcm");
            put("kda_1_ry", "music_track/kda_1_ry.pcm");
            put("kda_1_bg", "music_track/kda_1_bg.pcm");
            put("kda_1_tr", "music_track/kda_1_tr.pcm");
            put("pentakill_1_ry", "music_track/pentakill_1_ry.pcm");
            put("pentakill_1_bg", "music_track/pentakill_1_bg.pcm");
            put("pentakill_1_tr", "music_track/pentakill_1_tr.pcm");
            put("truedamage_1_ry", "music_track/truedamage_1_ry.pcm");
            put("truedamage_1_bg", "music_track/truedamage_1_bg.pcm");
            put("truedamage_1_tr", "music_track/truedamage_1_tr.pcm");
            put("8bit_1_ry", "music_track/8bit_1_ry.pcm");
            put("8bit_1_tr", "music_track/8bit_1_tr.pcm");
            put("disco_1_ry", "music_track/disco_1_ry.pcm");
            put("disco_1_tr", "music_track/disco_1_tr.pcm");
            put("bigsad_1_ry", "music_track/bigsad_1_ry.pcm");
            put("bigsad_1_tr", "music_track/bigsad_1_tr.pcm");
            put("country_1_ry", "music_track/country_1_ry.pcm");
            put("country_1_tr", "music_track/country_1_tr.pcm");
            put("anarchist_1_ry", "music_track/anarchist_1_ry.pcm");
            put("anarchist_1_tr", "music_track/anarchist_1_tr.pcm");
            put("tranceformer_1_ry", "music_track/tranceformer_1_ry.pcm");
            put("tranceformer_1_tr", "music_track/tranceformer_1_tr.pcm");
            put("jazz_1_ry", "music_track/jazz_1_ry.pcm");
            put("glitterbomb_1_bg", "music_track/glitterbomb_1_bg.pcm");
            put("s_1_jin", "music_track/s_1_jin.pcm");
            put("s_1_sona", "music_track/s_1_sona.pcm");
            put("s_1_ely", "music_track/s_1_ely.pcm");
            put("s_1_piano", "music_track/s_1_piano.pcm");
        }
    };
    private static final HashMap<String, Float> MUSIC_STEP1_VOLUME_MAP = new HashMap<>() {
        {
            put("heartsteel_1_ry", 1.0f);
            put("heartsteel_1_bg", 1.0f);
            put("heartsteel_1_tr", 1.0f);
            put("kda_1_ry", 1.0f);
            put("kda_1_bg", 1.0f);
            put("kda_1_tr", 1.0f);
            put("pentakill_1_ry", 1.0f);
            put("pentakill_1_bg", 1.0f);
            put("pentakill_1_tr", 1.0f);
            put("truedamage_1_ry", 1.0f);
            put("truedamage_1_bg", 1.0f);
            put("truedamage_1_tr", 1.0f);
            put("8bit_1_ry", 1.0f);
            put("8bit_1_tr", 1.0f);
            put("disco_1_ry", 1.0f);
            put("disco_1_tr", 1.0f);
            put("bigsad_1_ry", 1.0f);
            put("bigsad_1_tr", 1.0f);
            put("country_1_ry", 1.0f);
            put("country_1_tr", 1.0f);
            put("anarchist_1_ry", 1.0f);
            put("anarchist_1_tr", 1.0f);
            put("tranceformer_1_ry", 1.0f);
            put("tranceformer_1_tr", 1.0f);
            put("jazz_1_ry", 1.0f);
            put("glitterbomb_1_bg", 1.0f);
            put("s_1_jin", 0.7f);
            put("s_1_sona", 1.0f);
            put("s_1_ely", 0.85f);
            put("s_1_piano", 1.0f);
        }
    };
    private static final HashMap<String, String> MUSIC_STEP2_TRACK_MAP = new HashMap<>() {
        {
            put("piano_2_ry", "music_track/s_2_piano.pcm");
            put("heartsteel_2_ry", "music_track/heartsteel_2_ry.pcm");
            put("heartsteel_2_bg", "music_track/heartsteel_2_bg.pcm");
            put("heartsteel_2_tr", "music_track/heartsteel_2_tr.pcm");
            put("kda_2_ry", "music_track/kda_2_ry.pcm");
            put("kda_2_bg", "music_track/kda_2_bg.pcm");
            put("kda_2_tr", "music_track/kda_2_tr.pcm");
            put("pentakill_2_ry", "music_track/pentakill_2_ry.pcm");
            put("pentakill_2_bg", "music_track/pentakill_2_bg.pcm");
            put("pentakill_2_tr", "music_track/pentakill_2_tr.pcm");
            put("truedamage_2_ry", "music_track/truedamage_2_ry.pcm");
            put("truedamage_2_bg", "music_track/truedamage_2_bg.pcm");
            put("truedamage_2_tr", "music_track/truedamage_2_tr.pcm");
            put("8bit_2_ry", "music_track/8bit_2_ry.pcm");
            put("8bit_2_tr", "music_track/8bit_2_tr.pcm");
            put("disco_2_ry", "music_track/disco_2_ry.pcm");
            put("disco_2_tr", "music_track/disco_2_tr.pcm");
            put("bigsad_2_ry", "music_track/bigsad_2_ry.pcm");
            put("bigsad_2_tr", "music_track/bigsad_2_tr.pcm");
            put("country_2_ry", "music_track/country_2_ry.pcm");
            put("country_2_tr", "music_track/country_2_tr.pcm");
            put("anarchist_2_ry", "music_track/anarchist_2_ry.pcm");
            put("anarchist_2_tr", "music_track/anarchist_2_tr.pcm");
            put("tranceformer_2_ry", "music_track/tranceformer_2_ry.pcm");
            put("tranceformer_2_tr", "music_track/tranceformer_2_tr.pcm");
            put("jazz_2_ry", "music_track/jazz_2_ry.pcm");
            put("glitterbomb_2_bg", "music_track/glitterbomb_2_bg.pcm");
            put("s_2_jin", "music_track/s_2_jin.pcm");
            put("s_2_sona", "music_track/s_2_sona.pcm");
            put("s_2_ely", "music_track/s_2_ely.pcm");
            put("s_1_piano", "music_track/s_1_piano.pcm");
        }
    };
    private static final HashMap<String, Float> MUSIC_STEP2_VOLUME_MAP = new HashMap<>() {
        {
            put("piano_2_ry", 1.0f);
            put("heartsteel_2_ry", 1.0f);
            put("heartsteel_2_bg", 1.0f);
            put("heartsteel_2_tr", 1.0f);
            put("kda_2_ry", 1.0f);
            put("kda_2_bg", 1.0f);
            put("kda_2_tr", 1.0f);
            put("pentakill_2_ry", 1.0f);
            put("pentakill_2_bg", 1.0f);
            put("pentakill_2_tr", 1.0f);
            put("truedamage_2_ry", 1.0f);
            put("truedamage_2_bg", 1.0f);
            put("truedamage_2_tr", 1.0f);
            put("8bit_2_ry", 1.0f);
            put("8bit_2_tr", 1.0f);
            put("disco_2_ry", 1.0f);
            put("disco_2_tr", 1.0f);
            put("bigsad_2_ry", 1.0f);
            put("bigsad_2_tr", 1.0f);
            put("country_2_ry", 1.0f);
            put("country_2_tr", 1.0f);
            put("anarchist_2_ry", 1.0f);
            put("anarchist_2_tr", 1.0f);
            put("tranceformer_2_ry", 1.0f);
            put("tranceformer_2_tr", 1.0f);
            put("jazz_2_ry", 1.0f);
            put("glitterbomb_2_bg", 1.0f);
            put("s_2_jin", 0.7f);
            put("s_2_sona", 1.0f);
            put("s_2_ely", 1.0f);
            put("s_2_piano", 1.0f);
        }
    };

    public static int GET_MUSIC_STEP_TIME(int step) {
        if (step == 1) {
            return MUSIC_STEP1_TIME;
        } else if (step == 2) {
            return MUSIC_STEP2_TIME;
        } else {
            return MUSIC_STEP1_TIME;
        }
    }

    public static HashMap<String, String> GET_MUSIC_STEP_TRACK_MAP(int step) {
        if (step == 1) {
            return MUSIC_STEP1_TRACK_MAP;
        } else if (step == 2) {
            return MUSIC_STEP2_TRACK_MAP;
        } else {
            return MUSIC_STEP1_TRACK_MAP;
        }
    }

    public static HashMap<String, Float> GET_MUSIC_STEP_VOLUME_MAP(int step) {
        if (step == 1) {
            return MUSIC_STEP1_VOLUME_MAP;
        } else if (step == 2) {
            return MUSIC_STEP2_VOLUME_MAP;
        } else {
            return MUSIC_STEP1_VOLUME_MAP;
        }
    }

}
