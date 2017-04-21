package hatbox.amqr_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Manager extends AppCompatActivity implements View.OnClickListener{
    Button scan,add,set,help,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        scan = (Button)findViewById(R.id.btn_mngTOscan);
        add = (Button)findViewById(R.id.btn_mngTOadd);
        set = (Button)findViewById(R.id.btn_mngTOset);
        help = (Button)findViewById(R.id.btn_mngTOhelp);
        logout = (Button)findViewById(R.id.btn_mngTOlogin);

        scan.setOnClickListener(this);
        add.setOnClickListener(this);
        set.setOnClickListener(this);
        help.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_mngTOscan:
                //perintah membuka halaman pindai qr-code jika button Pindai di klik
                startActivity(new Intent(this,Mng_Scan.class));
                break;
            case R.id.btn_mngTOadd:
                //perintah membuka halaman daftar aset baru jika button Daftar di klik
                startActivity(new Intent(this,Mng_Add.class));
                break;
            case R.id.btn_mngTOset:
                //perintah membuka halaman atur lokasi dan nama jika button Atur di klik
                startActivity(new Intent(this,Mng_Set.class));
                break;
            case R.id.btn_mngTOhelp:
                //perintah membuka halaman bantuan jika button Bantuan di klik
                startActivity(new Intent(this,Mng_Help.class));
                break;
            case R.id.btn_mngTOlogin:
                //perintah kembali ke halaman login jika button Keluar di klik
                startActivity(new Intent(this,Login.class));
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        //Mengunci back button default dari android
    }
}
