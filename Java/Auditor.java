package hatbox.amqr_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Auditor extends AppCompatActivity {
    Button scan,help,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditor);

        scan = (Button)findViewById(R.id.btn_adtTOscan);
        help = (Button)findViewById(R.id.btn_adtTOhelp);
        logout = (Button)findViewById(R.id.btn_adtTOlogin);

        scan.setOnClickListener(new View.OnClickListener() {
            //perintah membuka halaman pindai qr-code jika button Pindai di klik
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adt_Scan.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            //perintah membuka halaman bantuan jika button Bantuan di klik
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Adt_Help.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            //perintah kembali ke halaman login jika button Keluar di klik
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
