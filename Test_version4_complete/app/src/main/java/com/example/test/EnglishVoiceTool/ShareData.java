package com.example.test.EnglishVoiceTool;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShareData {
    /**
     * 语速
     * 当前 0.5f 最大 1.5f
     */
    public static float voice_speed = 1.0f; //0.5f, 经调校 -> 0.8000001


    /**
     * 音调
     * 当前 1.0f  最大 2.0f
     */
    public static float voice_pitch = 0.9f; // 1.0f

    public static Map<String, Locale> LanguageList = new HashMap<String, Locale>();
    // public final class Locale implements Cloneable, Serializable
}
