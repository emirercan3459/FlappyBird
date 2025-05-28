package com.emirercan.flappybird;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Veritabani extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FlappyBird"; // Veritabanı adı
    private static final int DATABASE_VERSION = 1; // Veritabanı versiyonu
    private static final String TABLE_SCORES = "scores"; // Skorların tutulduğu tablo adı
    private static final String COLUMN_ID = "id"; // Tablo sütunu: benzersiz kimlik
    private static final String COLUMN_SCORE = "score"; // Tablo sütunu: skor değeri

    public Veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // SQLiteOpenHelper constructor çağrılır
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // scores tablosunu oluşturur, id otomatik artan birincil anahtar, score integer
        String createTable = "CREATE TABLE " + TABLE_SCORES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SCORE + " INTEGER)";
        db.execSQL(createTable); // SQL sorgusunu çalıştırır
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Veritabanı güncellenirken eski tabloyu silip yenisini oluşturur (veriler kaybolur)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db); // Yeni tabloyu oluşturur
    }

    public void saveScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı nesnesi alır
        ContentValues values = new ContentValues(); // Veri tutucu nesne
        values.put(COLUMN_SCORE, score); // Skoru values içine koyar
        db.insert(TABLE_SCORES, null, values); // Tabloya yeni skor ekler
        db.close(); // Veritabanı bağlantısını kapatır
    }

    public int getHighScore() {
        SQLiteDatabase db = this.getReadableDatabase(); // Okunabilir veritabanı nesnesi alır
        Cursor cursor = db.query(TABLE_SCORES, // Sorgu oluşturur
                new String[]{COLUMN_SCORE}, // Sadece score sütununu seçer
                null, null, null, null, // filtre, gruplama yok
                COLUMN_SCORE + " DESC", // Skorları azalan sırada sırala
                "1"); // sadece ilk (en yüksek) kayıt alınacak

        int highScore = 0; // Varsayılan skor 0
        if (cursor.moveToFirst()) { // Eğer kayıt varsa
            highScore = cursor.getInt(0); // Skoru al
        }
        cursor.close(); // Cursor kapatılır
        db.close(); // Veritabanı kapatılır
        return highScore; // En yüksek skor döndürülür
    }
}
