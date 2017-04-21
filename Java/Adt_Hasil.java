package hatbox.amqr_code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class Adt_Hasil extends AppCompatActivity {
    String out;
    Button ulang,home;
    ImageView gambar;
    File gbr;
    TextView id,nama,lokasi,perolehan,pendataan,kondisi,perangkat,detil,tipe,nilai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adt__hasil);

        Intent i = getIntent();
        out = i.getStringExtra(Adt_Scan.QR_ADT);

        id = (TextView)findViewById(R.id.adt_ID);
        nama = (TextView)findViewById(R.id.adt_Name);
        lokasi = (TextView)findViewById(R.id.adt_Lokasi);
        perolehan = (TextView)findViewById(R.id.adt_Perolehan);
        pendataan = (TextView)findViewById(R.id.adt_Pendataan);
        kondisi = (TextView)findViewById(R.id.adt_Kondisi);
        perangkat = (TextView)findViewById(R.id.adt_Perangkat);
        detil = (TextView)findViewById(R.id.adt_Detil);
        tipe = (TextView)findViewById(R.id.adt_Tipe);
        nilai = (TextView)findViewById(R.id.adt_Nilai);

        gambar = (ImageView)findViewById(R.id.adt_Gambar);

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
            perangkat.setText(obj.getString("perangkat_input"));
            nilai.setText(obj.getString("nilai_aset"));

            gbr = new  File(obj.getString("gambar_aset"));

            if(gbr.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(gbr.getAbsolutePath());
                gambar.setImageBitmap(myBitmap);
            }
        }catch (JSONException je){
            je.printStackTrace();
        }

        ulang = (Button)findViewById(R.id.btn_adt_hslTOscan);
        home  = (Button)findViewById(R.id.btn_adt_hasilTOadt);

        ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adt_Scan.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Auditor.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }
}
