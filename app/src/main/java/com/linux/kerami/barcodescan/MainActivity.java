package com.linux.kerami.barcodescan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by ozsoy.kerami on 25.10.2016.
 */
public class MainActivity extends Activity {


    EditText editTextBarcode = null;

    ImageView imageViewCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        editTextBarcode = (EditText)findViewById(R.id.editTextBarcode);
        imageViewCamera = (ImageView)findViewById(R.id.imageViewCamera);

        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan.scanBarcode(MainActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==  1111) {
            if(resultCode == Activity.RESULT_OK){
                String scanResult = data.getStringExtra("SCAN_RESULT");
                editTextBarcode.setText(scanResult);
            }
            if(resultCode == Activity.RESULT_CANCELED){
                // bla bla
            }

        }

    }
}
