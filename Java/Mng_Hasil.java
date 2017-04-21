package hatbox.amqr_code;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class Mng_Hasil extends AppCompatActivity {
    String out;
    Button salah,benar,copy;
    ImageView gambar;
    File gbr;
    TextView id,nama,lokasi,perolehan,pendataan,kondisi,perangkat,detil,tipe,nilai;
    public static final String QR_RES = "QR_RES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__hasil);

        Intent i = getIntent();
        out = i.getStringExtra("QR_ADD").toString();//mengambil JSON dari Mng_Add.Activity

        salah = (Button)findViewById(R.id.btn_false);
        benar = (Button)findViewById(R.id.btn_true);
        copy = (Button)findViewById(R.id.btn_copy);

        gambar = (ImageView)findViewById(R.id.hsl_Gambar);

        id = (TextView)findViewById(R.id.hsl_ID);
        nama = (TextView)findViewById(R.id.hsl_Nama);
        lokasi = (TextView)findViewById(R.id.hsl_Lokasi);
        perolehan = (TextView)findViewById(R.id.hsl_Perolehan);
        pendataan = (TextView)findViewById(R.id.hsl_Pendataan);
        kondisi = (TextView)findViewById(R.id.hsl_Kondisi);
        perangkat = (TextView)findViewById(R.id.hsl_Perangkat);
        detil = (TextView)findViewById(R.id.hsl_Detil);
        tipe = (TextView)findViewById(R.id.hsl_Tipe);
        nilai = (TextView)findViewById(R.id.hsl_Nilai);

        try{
            JSONObject obj = new JSONObject(out);

            nama.setText(obj.getString("nama_aset"));
            id.setText(obj.getString("id_aset"));
            tipe.setText(obj.getString("tipe_aset"));
            perolehan.setText(obj.getString("perolehan_aset"));
            pendataan.setText(obj.getString("tanggal_aset"));
            lokasi.setText(obj.getString("lokasi_aset"));
            kondisi.setText(obj.getString("kondisi_aset"));
            detil.setText(obj.getString("detil_aset"));
            nilai.setText(obj.getString("nilai_aset"));
            perangkat.setText(obj.getString("perangkat_input"));

            gbr = new  File(obj.getString("gambar_aset"));

            if(gbr.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(gbr.getAbsolutePath());
                gambar.setImageBitmap(myBitmap);
            }
        }catch (JSONException je){
            je.printStackTrace();
        }

        salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbr.delete();
                resetID();
                finish();
            }
        });

        benar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(),Mng_QR.class);
                j.putExtra(QR_RES,out);
                startActivity(j);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }

    private void resetID(){
        String AndroidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String namaAset = nama.getText().toString();
        String lokasiAset = lokasi.getText().toString();

        DataHelper dbHelp= new DataHelper(this);
        SQLiteDatabase dn = dbHelp.getReadableDatabase();
        Cursor cN = dn.rawQuery("SELECT * FROM nama WHERE namaBarang = '"+namaAset+"'",null);
        cN.moveToFirst();
        namaAset = cN.getString(0).toString();
        Cursor cL = dn.rawQuery("SELECT * FROM ruang WHERE namaRuang = '"+lokasiAset+"'",null);
        cL.moveToFirst();
        lokasiAset = cL.getString(0).toString();

        String nilai = namaAset + lokasiAset;
        String nilaiInc = nilai + AndroidID;

        SharedPreferences settings = getSharedPreferences(nilai,0);
        int count = settings.getInt(nilaiInc,0);
        count--;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(nilaiInc,count).commit();
    }
}
