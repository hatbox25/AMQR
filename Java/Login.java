package hatbox.amqr_code;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{
    Button login;
    EditText user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.btn_login);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);

        switch (v.getId()){
            case R.id.btn_login://jika button MASUK diklik
                if(user.getText().toString().equals("manager") && pass.getText().toString().equals("passmng")){
                    //login sebagai manager aset
                    startActivity(new Intent(this,Manager.class));
                }else if(user.getText().toString().equals("auditor") && pass.getText().toString().equals("passaudit")){
                    //login sebagai auditor
                    startActivity(new Intent(this,Auditor.class));
                }else{
                    //masukan login salah
                    Context context = getApplicationContext();
                    CharSequence text = "Nama Pengguna dan/atau Kata Kunci yang dimasukan SALAH!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
