package hatbox.amqr_code;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * Created by EdwinLokyta on 08/03/2017.
 */
public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "aset.db";//nama SQLite database yang digunakan
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //TODO Auto-generated constructor stub

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //method untuk menginisiasi database SQLite yang dijalankan saat pertama kali aplikasi diinstall
        String sqlNama = "create table nama(kd text not null primary key, namaBarang text not null unique);";//membuat tabel nama
        String sqlRuang = "create table ruang(kode text not null primary key, namaRuang text not null unique);";//membuat tabel ruang
        Log.d("Data", "onCreate: " + sqlNama);
        Log.d("Data", "onCreate: " + sqlRuang);
        db.execSQL(sqlNama);
        db.execSQL(sqlRuang);

        //mendaftarkan nama aset pada tabel nama
        sqlNama = "INSERT INTO nama(kd, namaBarang) VALUES ('LK', 'Lemari Kayu')" +
                ",('RK','Rak Kayu')" +
                ",('LD','Lemari Display')" +
                ",('WB','White Board')" +
                ",('KB','Kursi Besi')" +
                ",('S','Sice')" +
                ",('MKo','Meja Komputer')" +
                ",('ACSp','A.C. Split')" +
                ",('PC','Personal Computer')" +
                ",('Pr','Printer')" +
                ",('LB','Lemari Besi')" +
                ",('MKrK','Meja Kerja Kayu')" +
                ",('MR','Meja rapat')" +
                ",('AIO','All In One PC')" +
                ",('TV','TV')" +
                ",('Mtr','Monitor')" +
                ",('Sv','Server')" +
                ",('Hb','Hub')" +
                ",('Sc','Scanner')" +
                ",('FK','Filling Kabinet')" +
                ",('Lc','Locker')" +
                ",('MA','Mesin Absen')" +
                ",('If','Infocus')" +
                ",('Wr','Wireless')" +
                ",('Ph','Pesawat Telepon')" +
                ",('Ctv','CCTV')" +
                ",('Tab','Tablet PC')" +
                ",('Sw','Switch');";

        //mendaftarkan lokasi aset pada tabel ruang
        sqlRuang = "INSERT INTO ruang(kode, namaRuang) VALUES " +
                "('UDJT0105', 'R. Ketua Koordinator & Sekretaris Prodi Teknik Informatika PPBS D')," +
                "('UDJT0108', 'R. TU Departemen Ilmu Komputer PPBS D')," +
                "('UDJT0109', 'R. Perpustakaan Departemen Ilmu Komputer PPBS D')," +
                "('UDJT0204', 'R. Lab Sistem Informasi Manajemen & Multimedia PPBS D')," +
                "('UDJT0206', 'R. Kuliah 2.6 PPBS D')," +
                "('UDJT0210', 'R. Lab Robotik & Kecerdasan Buatan PPBS D')," +
                "('UDJT0211', 'R. Kuliah 2.11 PPBS D')," +
                "('UDJT0212', 'R. Kuliah 2.12 PPBS D')," +
                "('UDJT0213', 'R. Dosen Prodi Teknik Informatika PPBS D')," +
                "('UDJT0214', 'R. Kepala Departemen Ilmu Komputer PPBS D')," +
                "('UDJT0304', 'R. Sidang Prodi Teknik Informatika PPBS D')," +
                "('UDJT0305', 'R. Lab Algoritma & Pemrograman Dasar PPBS D')," +
                "('UDJT0306', 'R. Lab Algoritma & Pemrograman Lanjut PPBS D')," +
                "('UDJT0313', 'R. Lab Sistem Informasi & Multimedia PPBS D')," +
                "('UDJT0316', 'R. Himpunan Mahasiswa Teknik Informatika PPBS D');";
        db.execSQL(sqlNama);
        db.execSQL(sqlRuang);
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
