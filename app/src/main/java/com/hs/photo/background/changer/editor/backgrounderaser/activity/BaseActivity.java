package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.hs.photo.background.changer.editor.backgrounderaser.R;

public class BaseActivity extends AppCompatActivity {
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private AlertDialog mAlertDialog;

    @Override
    public void onStop() {
        super.onStop();
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
    }

    public void requestPermission(final String str, String str2, int i) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
            showAlertDialog(getString(R.string.permission_title_rationale), str2, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(BaseActivity.this, new String[]{str}, i);
                }
            }, "OK", null, "Cancel");
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{str}, i);
    }

    public void showAlertDialog(String str, String str2, DialogInterface.OnClickListener onClickListener, String str3, DialogInterface.OnClickListener onClickListener2, String str4) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setPositiveButton(str3, onClickListener);
        builder.setNegativeButton(str4, onClickListener2);
        this.mAlertDialog = builder.show();
    }
}
