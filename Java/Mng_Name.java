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

public class Mng_Name extends AppCompatActivity {
    String[] daftarNama,daftarKode;
    ListView list;
    EditText nama,kd;
    Button back,home;
    ImageButton add;
    protected Cursor cursorNama;
    DataHelper dbcenter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__name);

        dbcenter1 = new DataHelper(this);//memanggil database SQLite
        add = (ImageButton)findViewById(R.id.btn_new_Name);
        back = (Button)findViewById(R.id.btn_nmTOset);
        home = (Button)findViewById(R.id.btn_nmTOmng);

        kd = (EditText)findViewById(R.id.new_kd_Name);
        nama = (EditText)findViewById(R.id.new_Name);

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
                    if(nama.getText().length() != 0 && kd.getText().length() != 0){
                        SQLiteDatabase db = dbcenter1.getWritableDatabase();
                        db.execSQL("INSERT INTO nama(kd,namaBarang) VALUES ('"+kd.getText().toString()+"','"+nama.getText().toString()+"')");
                        RefListNama();
                        nama.setText("");
                        kd.setText("");
                    }else{
                        CharSequence text = "Nama Aset tidak boleh kosong!";
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

        RefListNama();
    }

    public void RefListNama(){
        SQLiteDatabase db = dbcenter1.getReadableDatabase();//memanggil database SQLite
        cursorNama = db.rawQuery("SELECT * FROM nama",null);//mengambil seluruh informasi dari tabel
        daftarNama = new String[cursorNama.getCount()];
        daftarKode = new String[cursorNama.getCount()];
        cursorNama.moveToFirst();
        for (int cc=0; cc < cursorNama.getCount(); cc++){
            cursorNama.moveToPosition(cc);
            daftarNama[cc] = cursorNama.getString(1).toString();
            daftarKode[cc] = cursorNama.getString(0).toString();
        }
        list= (ListView) findViewById(R.id.list_Name);
        list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarNama));

        list.setSelected(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftarNama[position];//mengambil item pada posisi yg di klik
                final CharSequence[] dialog = {"Ubah Data Nama Aset","Hapus Nama Aset"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Mng_Name.this);
                builder.setTitle(daftarKode[position]);
                builder.setItems(dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //perintah jika button Ubah Data pada Pilihan di klik
                                SQLiteDatabase d = dbcenter1.getReadableDatabase();
                                cursorNama = d.rawQuery("SELECT * FROM nama WHERE namaBarang = '"+selection+"'",null);
                                cursorNama.moveToFirst();
                                if(cursorNama.getCount()>0){
                                    cursorNama.moveToPosition(0);
                                    nama.setText(cursorNama.getString(1).toString());
                                    kd.setText(cursorNama.getString(0).toString());

                                    CharSequence text = "Ubah data Nama Aset, kemudian klik tombol (+)";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                                    toast.show();
                                }
                                SQLiteDatabase dd = dbcenter1.getWritableDatabase();
                                dd.execSQL("DELETE FROM nama where namaBarang = '"+selection+"'");
                                RefListNama();
                                break;
                            case 1:
                                //perintah jika button Hapus Data pada Pilihan di klik
                                SQLiteDatabase db = dbcenter1.getWritableDatabase();
                                db.execSQL("DELETE FROM nama where namaBarang = '"+selection+"'");
                                RefListNama();
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
