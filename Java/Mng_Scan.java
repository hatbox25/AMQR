package hatbox.amqr_code;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Mng_Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private String QRhasilMng,key;
    public static final String QR_MNG = "QR_MNG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__scan);

        runScan();
    }

    private void runScan(){
        //perintah menjalakan kamera sebagai scanner QR-Code
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //method untuk menampilkan hasil pindai
        Log.w("handleResult",result.getText());
        QRhasilMng = result.getText();

        if(isJSONValid(QRhasilMng)){
            try{
                JSONObject obj = new JSONObject(QRhasilMng);

                if(!obj.has("nama_aset")){
                    hasilSalah();
                }
                else{
                    Intent i = new Intent(this,Mng_Detail.class);
                    i.putExtra(QR_MNG,QRhasilMng);

                    if(QRhasilMng.length()!= 0){
                        final MediaPlayer mp = MediaPlayer.create(this,R.raw.notif);
                        mp.start();
                        startActivity(i);
                    }
                }
            }catch (JSONException je){
                je.printStackTrace();
            }
        }else{
            hasilSalah();
        }
    }

    public boolean isJSONValid(String test) {
        //method untuk memeriksa hasil pindah berbentuk JSON
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    private void hasilSalah(){
        //method keluran jika ditemukan hasil pindai tidak sesuai ketentuan
        new AlertDialog.Builder(this)
            .setTitle("QR-Code SALAH")
            .setMessage("Apakah ingin memindai ulang ?")
            .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                    startActivity(getIntent());
                }})
            .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(new Intent(getApplicationContext(),Manager.class));
                }
            }).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),Manager.class));
    }
}
