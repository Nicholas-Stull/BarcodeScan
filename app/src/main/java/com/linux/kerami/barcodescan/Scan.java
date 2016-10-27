package com.linux.kerami.barcodescan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

/**
 * Created by ozsoy.kerami on 25.10.2016.
 */
public class Scan {

    public static final String TAG = Scan.class.getName();


    public static void scanBarcode(final Activity activity){

        boolean isZxingInstalled = false;
        Intent intent = null;
        try {
            ApplicationInfo info = activity.getPackageManager().getApplicationInfo("com.google.zxing.client.android", 0 );
            isZxingInstalled = true;
        } catch(PackageManager.NameNotFoundException e) {
            isZxingInstalled=false;
        }

        if(isZxingInstalled) {
            Log.i(TAG, "scanBarcode");

            intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.setPackage("com.google.zxing.client.android");
            intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "PRODUCT_MODE");
            activity.startActivityForResult(intent, 1111);
        } else {
            Log.e(TAG, "Zxing cihazda yok");
            try{
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                boolean isPlayStoreInstalled;
                                try {
                                    ApplicationInfo i = activity.getPackageManager().getApplicationInfo("com.google.vending", 0 );
                                    isPlayStoreInstalled = true;
                                } catch(PackageManager.NameNotFoundException e) {
                                    isPlayStoreInstalled=false;
                                }

                                String url;
                                if(isPlayStoreInstalled) {
                                    url = "market://detailsid=com.google.zxing.client.android";
                                } else {
                                    url = "https://play.google.com/store/apps/details?id=com.google.zxing.client.android";
                                }
                                Intent DownloadZxing = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                activity.startActivity(DownloadZxing);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                        dialog.dismiss();
                    }
                };

                showDialog(activity,
                        "Master WO",
                        activity.getResources().getString(R.string.barcodeApplicationNotFound),
                        activity.getResources().getString(R.string.yes),activity.getResources().getString(R.string.no),
                        0,
                        listener);

            }catch(Exception e){
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }


    private static void showDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, int icon, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder dialog =new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        if (icon != 0)
            dialog.setIcon(icon);

        if (!positiveButtonText.isEmpty())
            dialog.setPositiveButton(positiveButtonText, listener);
        if (!negativeButtonText.isEmpty())
            dialog.setNegativeButton(negativeButtonText, listener);
        dialog.show();
    }

}
