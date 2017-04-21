package hatbox.amqr_code;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

public class Mng_Add extends AppCompatActivity {
    EditText tipe,perolehan,detil,nilai;
    Spinner nama,lokasi,kondisi;
    String[] daftarNama,daftarLokasi,daftarKondisi;
    DataHelper dbHelp;
    protected Cursor cNama,cLokasi;
    Button simpan,kembali;
    String jsonData,path;
    File output;
    public static final String QR_ADD = "QR_ADD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__add);

        dbHelp = new DataHelper(this);//memanggil database

        refNama();//memanggil fungsi untuk menampilkan data spinner nama
        refLokasi();//memanggil fungsi untuk menampilkan data spinner lokasi
        refKondisi();//memanggil fungsi untuk menampilkan data spinner Kondisi

        tipe = (EditText)findViewById(R.id.add_Tipe);
        perolehan = (EditText)findViewById(R.id.add_Perolehan);
        detil = (EditText)findViewById(R.id.add_Detil);
        nilai = (EditText)findViewById(R.id.add_Nilai);

        kembali = (Button)findViewById(R.id.btn_addTOmng);
        simpan = (Button)findViewById(R.id.btn_Simpan);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Manager.class));
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipe.getText().length() == 0){
                    CharSequence text = "Yang ditandai bintang (*) WAJIB DIISI !";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }else{
                    toJSON();
                }
            }
        });

    }

    private void refNama(){
        SQLiteDatabase dn = dbHelp.getReadableDatabase();
        cNama = dn.rawQuery("SELECT * FROM nama ORDER BY namaBarang",null);
        daftarNama = new String[cNama.getCount()];
        for (int cc=0; cc < cNama.getCount(); cc++){
            cNama.moveToPosition(cc);
            daftarNama[cc] = cNama.getString(1).toString();
        }
        nama = (Spinner) findViewById(R.id.add_Nama);
        nama.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, daftarNama));
    }

    private void refLokasi(){
        SQLiteDatabase dl = dbHelp.getReadableDatabase();
        cLokasi = dl.rawQuery("SELECT * FROM ruang ORDER BY kode",null);
        daftarLokasi = new String[cLokasi.getCount()];
        for (int cc=0; cc < cLokasi.getCount(); cc++){
            cLokasi.moveToPosition(cc);
            daftarLokasi[cc] = cLokasi.getString(1).toString();
        }
        lokasi = (Spinner) findViewById(R.id.add_Lokasi);
        lokasi.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, daftarLokasi));
    }

    private void refKondisi(){
        daftarKondisi = new String[3];
        daftarKondisi[0] = "Baik";
        daftarKondisi[1] = "Rusak Ringan";
        daftarKondisi[2] = "Rusak Berat";

        kondisi = (Spinner)findViewById(R.id.add_Kondisi);
        kondisi.setAdapter(new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,daftarKondisi));
    }

    private String setID(String AndroidID){
        String namaAset = nama.getSelectedItem().toString();
        String lokasiAset = lokasi.getSelectedItem().toString();

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
        count++;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(nilaiInc,count).commit();

        String hasil = lokasiAset+"-"+namaAset+"-"+Integer.toString(count);
        return hasil;
    }

    private void toJSON(){
        String AndroidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String ID = setID(AndroidID);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String dirIMG = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AMQR/IMG/";
        output = new File(dirIMG, ID+".jpeg");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        path = dirIMG+ID+".jpeg";
        startActivityForResult(cameraIntent, 1);

        try{
            JSONObject object = new JSONObject();

            object.accumulate("nama_aset",nama.getSelectedItem().toString());
            object.accumulate("id_aset",ID);
            object.accumulate("tipe_aset",tipe.getText().toString());
            object.accumulate("nilai_aset","Rp. " + nilai.getText().toString());
            object.accumulate("perolehan_aset",perolehan.getText().toString());
            object.accumulate("tanggal_aset", DateFormat.getDateInstance().format(new Date()).toString());
            object.accumulate("lokasi_aset",lokasi.getSelectedItem().toString());
            object.accumulate("kondisi_aset",kondisi.getSelectedItem().toString());
            object.accumulate("detil_aset",detil.getText().toString());
            object.accumulate("perangkat_input",AndroidID);
            object.accumulate("gambar_aset",path);

            jsonData = object.toString();
        }
        catch (Exception je){}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(this,Mng_Hasil.class);
                i.putExtra(QR_ADD,jsonData);
                startActivity(i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }
}
