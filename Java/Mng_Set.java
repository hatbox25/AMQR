package hatbox.amqr_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mng_Set extends AppCompatActivity implements View.OnClickListener{
    Button back,loc,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__set);

        back = (Button)findViewById(R.id.btn_setTOmng);
        loc = (Button)findViewById(R.id.btn_setTOlokasi);
        name = (Button)findViewById(R.id.btn_setTOnama);

        back.setOnClickListener(this);
        loc.setOnClickListener(this);
        name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_setTOmng:
                //perintah jika button Kembali di klik
                startActivity(new Intent(this,Manager.class));
                break;
            case R.id.btn_setTOlokasi:
                //perintah jika button Atur Lokasi di klik
                startActivity(new Intent(this,Mng_Loc.class));
                break;
            case R.id.btn_setTOnama:
                //perintah jika button Atur Tipe di klik
                startActivity(new Intent(this,Mng_Name.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }
}
