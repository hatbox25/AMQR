package hatbox.amqr_code;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Mng_Help extends AppCompatActivity implements View.OnClickListener {
    Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__help);

        ok = (Button)findViewById(R.id.btn_helpTOmng);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_helpTOmng:
                //perintah jika button Mengerti di klik
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        //Mengunci back button default dari android
    }
}
