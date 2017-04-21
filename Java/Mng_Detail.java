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

public class Mng_Detail extends AppCompatActivity {
    String out;
    Button ulang,home,edit;
    ImageView gambar;
    File gbr;
    TextView id,nama,lokasi,perolehan,pendataan,kondisi,perangkat,detil,tipe,nilai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__detail);



        id = (TextView)findViewById(R.id.dt_ID);
        nama = (TextView)findViewById(R.id.dt_Name);
        lokasi = (TextView)findViewById(R.id.dt_Lokasi);
        perolehan = (TextView)findViewById(R.id.dt_Perolehan);
        pendataan = (TextView)findViewById(R.id.dt_Pendataan);
        kondisi = (TextView)findViewById(R.id.dt_Kondisi);
        perangkat = (TextView)findViewById(R.id.dt_Perangkat);
        detil = (TextView)findViewById(R.id.dt_Detil);
        tipe = (TextView)findViewById(R.id.dt_Tipe);
        nilai = (TextView)findViewById(R.id.dt_Nilai);

        gambar = (ImageView)findViewById(R.id.dt_Gambar);

        Intent i = getIntent();
        out = i.getStringExtra(Mng_Scan.QR_MNG);

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

        ulang = (Button)findViewById(R.id.btn_detailTOscan);
        home = (Button)findViewById(R.id.btn_detailTOmng);
        edit = (Button)findViewById(R.id.btn_EDIT);

        ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Mng_Scan.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Manager.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Mng_Edit.class);
                i.putExtra("QR_EDIT",out);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        //Mengunci back button default dari android
    }
}
