package hatbox.amqr_code;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Adt_Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    private String QRhasilAdt;
    public static final String QR_ADT = "QR_ADT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adt__scan);

        runScan();
    }

    private void runScan(){
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
        Log.w("handleResult",result.getText());
        QRhasilAdt = result.getText();

        if(isJSONValid(QRhasilAdt)) {
            try {
                JSONObject obj = new JSONObject(QRhasilAdt);

                if (!obj.has("nama_aset")) {
                    new AlertDialog.Builder(this)
                            .setTitle("QR-Code SALAH")
                            .setMessage("Apakah ingin memindai ulang ?")
                            .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    finish();
                                    startActivity(getIntent());
                                }
                            })
                            .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), Auditor.class));
                                }
                            }).show();
                } else {
                    Intent i = new Intent(this, Adt_Hasil.class);
                    i.putExtra(QR_ADT, QRhasilAdt);

                    if (QRhasilAdt.length() != 0) {
                        final MediaPlayer mp = MediaPlayer.create(this, R.raw.notif);
                        mp.start();
                        startActivity(i);
                    }
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }else{
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
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),Auditor.class));
    }

    public boolean isJSONValid(String test) {
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
}
