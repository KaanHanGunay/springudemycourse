package com.khg.springdemoapp.model.entity.utils;

/**
 * Personelin sahip olabileceği rütbeleri temsil eder
 */
public enum Rutbe {
    POLIS_MEMURU("Polis Memuru"),
    BASPOLIS_MEMURU("Başpolis Memuru"),
    KIDEMLI_BASPOLIS_MEMURU("Kıdemkli Başpolis Memuru"),
    KOMISER_YARDIMCISI("Komiser Yardımcısı"),
    KOMISER("Komiser"),
    BASKOMISER("Başkomiser"),
    EMNIYET_AMIRI("Emniyet Amiri"),
    EMNIYET_MUDURU_4("4. Sınıf Emniyet Müdürü"),
    EMNIYET_MUDURU_3("3. Sınıf Emniyet Müdürü"),
    EMNIYET_MUDURU_2("2. Sınıf Emniyet Müdürü"),
    EMNIYET_MUDURU_1("1. Sınıf Emniyet Müdürü"),;

    Rutbe(String rankName) {
    }
}