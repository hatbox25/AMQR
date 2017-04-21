package hatbox.amqr_code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mng_QR extends AppCompatActivity {
    String jsonData,lok,kode,nama;
    ImageView qr;
    Bitmap bmp;
    Button back,home,save;
    TextView title;
    public final static int WIDTH=500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mng__qr);



        qr = (ImageView)findViewById(R.id.qr_IMG);
        title = (TextView)findViewById(R.id.qr_ID);
        back = (Button)findViewById(R.id.btn_qrTOhasil);
        home = (Button)findViewById(R.id.btn_qrTOmng);
        save = (Button)findViewById(R.id.btn_qr_Save);

        try{
            JSONObject obj = new JSONObject(jsonData);
            lok = obj.getString("lokasi_aset");
            kode = obj.getString("id_aset");
            nama = obj.getString("nama_aset");
            title.setText(kode);
        }catch (JSONException je){
            je.printStackTrace();
        }

        Intent i = getIntent();
        jsonData = i.getStringExtra(Mng_Hasil.QR_RES).toString();

        Thread t = new Thread(new Runnable() {
            public void run() {
                try{
                    synchronized (this){
                        wait(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Bitmap bitmap = null;

                                    bitmap = encodeAsBitmap(jsonData);
                                    qr.setImageBitmap(bitmap);

                                    bmp = bitmap;
                                }catch (WriterException e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        t.start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Manager.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                String AndroidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AMQR/" + AndroidID +"/"+ lok +"/"+ nama ;
                File dir = new File(fullPath);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File file = new File(fullPath, kode + ".jpg");

                FileOutputStream fileOutputStream = null;
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes.toByteArray());

                    Toast.makeText(Mng_QR.this,
                            file.getAbsolutePath(),
                            Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if(fileOutputStream != null){
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    Bitmap encodeAsBitmap(String str) throws WriterException{
        BitMatrix result;
        try{
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH,WIDTH,null);
        }catch(IllegalArgumentException iae){
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for(int y = 0;y < h;y++){
            int offset = y * w;
            for(int x = 0;x < w;x++){
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0,500,0,0,w,h);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        //Mengunci back button default dari android
    }
}
