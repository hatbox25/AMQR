package hatbox.amqr_code;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Mng_Loc extends AppCompatActivity {
    String[] daftarLokasi,daftarKode;
    ListView list;
    EditText kode,lokasi;
    Button back,home;
    ImageButton add;
    protected Cursor cursorLokasi;
    DataHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__loc);

        dbcenter = new DataHelper(this);//memanggil database SQLite

        back = (Button)findViewById(R.id.btn_locTOset);
        home = (Button)findViewById(R.id.btn_locTOmng);
        add = (ImageButton)findViewById(R.id.btn_new_Loc);

        kode = (EditText)findViewById(R.id.new_Loc_Kode);
        lokasi = (EditText)findViewById(R.id.new_Loc);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perintah jika button Kembali di klik
                startActivity(new Intent(getApplicationContext(),Mng_Set.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perintah jika button Halaman Utama di klik
                startActivity(new Intent(getApplicationContext(),Manager.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perintah menambah data lokasi aset baru jika tombol (+) di klik
                try{
                    if(kode.getText().length() != 0 && lokasi.getText().length() != 0 ){
                        SQLiteDatabase db = dbcenter.getWritableDatabase();
                        db.execSQL("INSERT INTO ruang(kode,namaRuang) VALUES ('"+kode.getText().toString()+"','"+lokasi.getText().toString()+"')");
                        RefListRuang();
                        kode.setText("");
                        lokasi.setText("");
                    }else{
                        CharSequence text = "Kode Lokasi & Nama Lokasi tidak boleh kosong!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    }
                }catch (SQLiteException ex){
                    CharSequence text = ex.toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }

            }
        });

        RefListRuang();
    }

    public void RefListRuang(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursorLokasi = db.rawQuery("SELECT * FROM ruang ORDER BY kode",null);
        daftarLokasi = new String[cursorLokasi.getCount()];
        daftarKode = new String[cursorLokasi.getCount()];
        cursorLokasi.moveToFirst();
        for (int cc=0; cc < cursorLokasi.getCount(); cc++){
            cursorLokasi.moveToPosition(cc);
            daftarLokasi[cc] = cursorLokasi.getString(1).toString();
            daftarKode[cc] = cursorLokasi.getString(0).toString();
        }
        list= (ListView) findViewById(R.id.list_Loc);
        list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarLokasi));

        list.setSelected(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftarLokasi[position];//mengambil item pada posisi yg di klik
                final CharSequence[] dialog = {"Ubah Data Lokasi Aset","Hapus Lokasi Aset"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Mng_Loc.this);
                builder.setTitle(daftarKode[position]);
                builder.setItems(dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //perintah jika button Ubah Data pada Pilihan di klik
                                SQLiteDatabase d = dbcenter.getReadableDatabase();
                                cursorLokasi = d.rawQuery("SELECT * FROM ruang WHERE namaRuang = '"+selection+"'",null);
                                cursorLokasi.moveToFirst();
                                if(cursorLokasi.getCount()>0){
                                    cursorLokasi.moveToPosition(0);
                                    kode.setText(cursorLokasi.getString(0).toString());
                                    lokasi.setText(cursorLokasi.getString(1).toString());

                                    CharSequence text = "Ubah data Kode Lokasi dan Nama Lokasi, kemudian klik tombol (+)";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                                    toast.show();
                                }
                                SQLiteDatabase dd = dbcenter.getWritableDatabase();
                                dd.execSQL("DELETE FROM ruang where namaRuang = '"+selection+"'");
                                RefListRuang();
                                break;
                            case 1:
                                //perintah jika button Hapus Data pada Pilihan di klik
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM ruang where namaRuang = '"+selection+"'");
                                RefListRuang();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)list.getAdapter()).notifyDataSetInvalidated();
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }
}
