package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hs.photo.background.changer.editor.backgrounderaser.R;


//import com.google.android.gms.ads.InterstitialAd;

import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropFragment;
import com.yalantis.ucrop.UCropFragmentCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.SplashScreen.mFirebaseAnalytics;
import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;

public class CropActivity extends AppCompatActivity implements UCropFragmentCallback {
    private MLImageSegmentationAnalyzer analyzer;
    private Bitmap bitmap;
    private UCropFragment fragment;
    // private InterstitialAd mInterstitialAd;
    private boolean mShowLoader;
    private int mToolbarCancelDrawable;
    private int mToolbarColor;
    private int mToolbarCropDrawable;
    private String mToolbarTitle;
    private int mToolbarWidgetColor;
    Uri originalUri;
    ProgressDialog pDialog;
    public static String selectedImagePath;
    public Toolbar toolbar;
    public static BitmapShader patternBMPshader;
    public static Bitmap croppedImage;

    public int categoryPos;
   // public int itemPos;
    private String backPath;
    private String forePath;
    private  String whichActivity;
    private int itemPosition;
    private FrameLayout adContainerView;

    public Dialog toastdialog;
    double count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0, count7 = 0, count8 = 0;
    int x, y;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_crop);

        Bundle bundles = new Bundle();
        bundles.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CropActivity");
        if (mFirebaseAnalytics != null) {
            mFirebaseAnalytics.logEvent("ACTIVITY_SELECTION", bundles);
        }

        adContainerView=findViewById(R.id.crop_banner_ad);

        if (RemoteConfigValues.getOurRemote().getShowBannerAd()!= null){
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")){

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAdcrop();
                    }
                });

            }
        }
       /* adContainerView.post(new Runnable() {
            @Override
            public void run() {
                CommonMethods.getInstance().loadBannerAd(adContainerView, CropActivity.this);
            }
        });*/




        init();
        parseIntentData();
        showCropView();
        // loadAd();
    }

    public void loadBannerAdcrop() {
        AdView adViewbanner = new AdView(this);
        adViewbanner.setAdUnitId(getString(R.string.banner_ad_on_mainscreen));
        adContainerView.removeAllViews();
        adContainerView.addView(adViewbanner);

        AdSize adSize = getAdSize(this, adContainerView);
        adViewbanner.setAdSize(adSize);

        AdRequest adRequest =
                new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adViewbanner.loadAd(adRequest);
    }

    public void init() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pDialog = progressDialog;
        progressDialog.setMessage("Loading...!");
        this.pDialog.setCancelable(false);
    }

    private void parseIntentData() {
        if (getIntent().hasExtra("SELECTED_URI")){

            this.originalUri = Uri.parse(getIntent().getStringExtra("SELECTED_URI"));

        }
        if (getIntent().hasExtra("categoryPos")){
            this.categoryPos=Integer.parseInt(String.valueOf(getIntent().getIntExtra("categoryPos",0)));

        }
        if (getIntent().hasExtra("backPath")){
            this.backPath=getIntent().getStringExtra("backPath");
        }
        if (getIntent().hasExtra("backPath")){
            this.forePath=getIntent().getStringExtra("forePath");
        }
        if (getIntent().hasExtra("itemPosition")){
            this.itemPosition=Integer.parseInt(String.valueOf(getIntent().getIntExtra("itemPosition",0)));

        }


       /* if (getIntent().hasExtra("fromHome")){

            whichActivity="fromHome";

        }
        else if (getIntent().hasExtra("fromBack")){
            whichActivity="fromBack";

        }*/

      //  this.itemPos=Integer.parseInt(String.valueOf(getIntent().getIntExtra("itemPos",0)));




    }

    private void showCropView() {
        setupFragment(UCrop.of(this.originalUri, Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpg"))));
    }

    private void setupFragment(UCrop uCrop) {
        this.fragment = uCrop.getFragment(uCrop.getIntent(this).getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.fragment, UCropFragment.TAG).commitAllowingStateLoss();
        setupViews();
    }

    public void onRestart() {
        super.onRestart();
        showCropView();
    }

    @SuppressLint("ResourceType")
    public void setupViews() {
        this.mToolbarColor = ContextCompat.getColor(this, R.color.toolbar_black);
        this.mToolbarCancelDrawable = R.drawable.ic_close_white;
        this.mToolbarCropDrawable = R.drawable.ic_check_black;
        this.mToolbarWidgetColor = ContextCompat.getColor(this, 17170443);
        String str = "Crop Photo";
        this.mToolbarTitle = str;
        if (str == null) {
            str = getResources().getString(R.string.ucrop_label_edit_photo);
        }
        this.mToolbarTitle = str;
        setupAppBar();
    }

    @SuppressLint("WrongConstant")
    private void setupAppBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                View childAt = CropActivity.this.toolbar.getChildAt(0);
                if ((childAt instanceof TextView)) {
                    CropActivity.this.toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        toolbar.setBackgroundColor(this.mToolbarColor);
        toolbar.setTitleTextColor(this.mToolbarWidgetColor);
        toolbar.setVisibility(0);
        TextView textView = (TextView) this.toolbar.findViewById(R.id.toolbar_title);
        textView.setTextColor(this.mToolbarWidgetColor);
        textView.setText(this.mToolbarTitle);
        Drawable drawable = ContextCompat.getDrawable(getBaseContext(), this.mToolbarCancelDrawable);
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
            this.toolbar.setNavigationIcon(drawable);
        }
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ucrop_menu_activity, menu);
        MenuItem findItem = menu.findItem(R.id.menu_loader);
        Drawable icon = findItem.getIcon();
        if (icon != null) {
            try {
                icon.mutate();
                icon.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
                findItem.setIcon(icon);
            } catch (IllegalStateException e) {
                Log.i(getClass().getName(), String.format("%s - %s", e.getMessage(), getString(R.string.ucrop_mutate_exception_hint)));
            }
            ((Animatable) findItem.getIcon()).start();
        }
        MenuItem findItem2 = menu.findItem(R.id.menu_crop);
        int i = this.mToolbarCropDrawable;
        if (i == 0) {
            i = R.drawable.ucrop_ic_done;
        }
        Drawable drawable = ContextCompat.getDrawable(this, i);
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(this.mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP);
            findItem2.setIcon(drawable);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_crop).setVisible(!this.mShowLoader);
        menu.findItem(R.id.menu_loader).setVisible(this.mShowLoader);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_crop) {
            UCropFragment uCropFragment = this.fragment;
            if (uCropFragment != null && uCropFragment.isAdded()) {
                this.fragment.cropAndSaveImage();
            }
        } else if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void loadingProgress(boolean z) {
        this.mShowLoader = z;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onCropFinish(UCropFragment.UCropResult uCropResult) {
        int i = uCropResult.mResultCode;
        if (i == -1) {
            try {
                handleCropResult(uCropResult.mResultData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (i == 96) {
            handleCropError(uCropResult.mResultData);
        }
    }

    private void handleCropResult(Intent intent) throws IOException {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            PrintStream printStream = System.out;
            printStream.println("resultUri : " + output);
            //Intent intent1 = new Intent(CropActivity.this, SpiralEditorActivity.class);
           // startActivity(intent1);
            this.selectedImagePath = getPath(this, output);
            aoutoCut();
        }
    }

    @SuppressLint("WrongConstant")
    private void handleCropError(Intent intent) {
        Throwable error = UCrop.getError(intent);
        if (error != null) {
            Toast.makeText(this, error.getMessage(), 1).show();
        }
    }

    public static String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (Build.VERSION.SDK_INT < 19 || !DocumentsContract.isDocumentUri(context, uri)) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(context, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        } else if (isExternalStorageDocument(uri)) {
            String[] split = DocumentsContract.getDocumentId(uri).split(":");
            if ("primary".equalsIgnoreCase(split[0])) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(uri)) {
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
        } else {
            if (isMediaDocument(uri)) {
                String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                String str = split2[0];
                if ("image".equals(str)) {
                    uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(str)) {
                    uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(str)) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
            }
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Method not decompiled: com.hs.photo.background.changer.editor.backgrounderaser.CropActivity.getDataColumn(android.content.Context, android.net.Uri, java.lang.String, java.lang.String[]):java.lang.String");
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void aoutoCut() {
        this.pDialog.show();
        try {
            this.bitmap = handleSamplingAndRotationBitmap(this, Uri.fromFile(new File(this.selectedImagePath)));
            analyzer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(openInputStream, null, options);
        openInputStream.close();
        options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
        options.inJustDecodeBounds = false;
        return rotateImageIfRequired(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options), uri);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        int round2 = Math.round(((float) i4) / ((float) i));
        if (round >= round2) {
            round = round2;
        }
        while (((float) (i4 * i3)) / ((float) (round * round)) > ((float) (i * i2 * 2))) {
            round++;
        }
        return round;
    }

    private static Bitmap rotateImageIfRequired(Bitmap bitmap2, Uri uri) throws IOException {
        int attributeInt = new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        if (attributeInt == 3) {
            return rotateImage(bitmap2, 180);
        }
        if (attributeInt == 6) {
            return rotateImage(bitmap2, 90);
        }
        if (attributeInt != 8) {
            return bitmap2;
        }
        return rotateImage(bitmap2, 270);
    }

    private static Bitmap rotateImage(Bitmap bitmap2, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix, true);
        bitmap2.recycle();
        return createBitmap;
    }

    private void analyzer() {
        if (this.bitmap!=null){

            this.analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(new MLImageSegmentationSetting.Factory().setExact(false).setAnalyzerType(0).setScene(0).create());
            this.analyzer.asyncAnalyseFrame(new MLFrame.Creator().setBitmap(this.bitmap).create()).addOnSuccessListener(new OnSuccessListener<MLImageSegmentation>() {

                public void onSuccess(MLImageSegmentation mLImageSegmentation) {
                    if (mLImageSegmentation != null) {
                        CropActivity.this.displaySuccess(mLImageSegmentation);
                    } else {
                        CropActivity.this.displayFailure();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(Exception exc) {
                    exc.printStackTrace();
                    CropActivity.this.displayFailure();
                }
            }).addOnCompleteListener(new OnCompleteListener<MLImageSegmentation>() {
                @Override
                public void onComplete(Task<MLImageSegmentation> task) {
                    Log.v("TAG","onComplete.."+task.isSuccessful());
                }
            });

        }
        else {

            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private void displaySuccess(MLImageSegmentation mLImageSegmentation) {

        if (this.bitmap == null) {
            displayFailure();
            return;
        }
        Bitmap foreground = mLImageSegmentation.getForeground();


// to check the fore ground image has color pixels
        for (x = 0; x < foreground.getWidth(); x++) {
            y = foreground.getHeight() / 2;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count1++;//if has color pixel the count increases
            }
        }
        for (y = 0; y < foreground.getHeight(); y++) {
            x = foreground.getWidth() / 2;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count2++;//if foreground has color pixel the count increases

            }
        }
        for (y = 0; y < foreground.getHeight(); y++) {
            x = foreground.getWidth() / 4;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count3++;//if foreground has color pixel the count increases

            }
        }
        for (y = 0; y < foreground.getHeight(); y++) {
            x = (3 * foreground.getWidth()) / 4;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count4++;//if  foreground has color pixel the count increases

            }
        }
        for (x = 0; x < foreground.getWidth(); x++) {
            y = foreground.getHeight() / 4;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count5++;//if  foreground has color pixel the count increases
            }
        }
        for (x = 0; x < foreground.getWidth(); x++) {
            y = (3 * foreground.getHeight()) / 4;
            if (foreground.getPixel(x, y) != Color.TRANSPARENT) {
                count6++;//if foreground has color pixel the count increases
            }
        }
        // if foreground has total transperent pixels then & to open a dialog toast

        if (count1 == 0 && count2 == 0 && count3 == 0 && count4 == 0 && count5 == 0 && count6 == 0) {
            System.out.println(count1);
            System.out.println(count2);
            System.out.println(count3);
            System.out.println(count4);
            System.out.println(count5);
            System.out.println(count6);

            if(this.pDialog !=null && this.pDialog.isShowing()){
                this.pDialog.dismiss();
            }
            toastdialog = new Dialog(this);
            toastdialog.setCancelable(false);
            toastdialog.setContentView(R.layout.toast_dialog_to_change_pic);
            Button choosephoto = (Button) toastdialog.findViewById(R.id.choose_photo);
            toastdialog.show();
            // to choose the other picture
            choosephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastdialog.dismiss();
                    Intent intent = new Intent(CropActivity.this, HomeActivity.class);
//                    startActivityForResult(intent,189);
                    intent.putExtra("fromActivity","Mainhome");
                    startActivity(intent);
                }
            });
        }else {
            if (foreground != null) {
                bitmap = foreground;
                // showAd();
                openNext(this.bitmap);
                //openNext(bitmap);
            } else {
                displayFailure();
            }
            this.pDialog.dismiss();
        }
        Bitmap background = mLImageSegmentation.original;
        CommonMethods.getInstance().setCROPPED_BITMAP(mLImageSegmentation.original);
//        if (foreground != null) {
//            bitmap = foreground;
//            // showAd();
//            openNext(this.bitmap);
//            //openNext(bitmap);
//        } else {
//            displayFailure();
//        }
//        this.pDialog.dismiss();
    }

    @SuppressLint("WrongConstant")
    private void displayFailure() {
        Toast.makeText(getApplicationContext(), "Fail", 0).show();
    }

    public void openNext(Bitmap bitmap2) {
        if (bitmap2 != null) {
            bitmap2.setHasAlpha(true);
            long currentTimeMillis = System.currentTimeMillis();
            String str = "_" + currentTimeMillis + ".";
            File file = new File(getCacheDir(), "Cutouts");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File file2 = new File(file, str);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
               // Intent intent = new Intent(this, ChangeBackgroundActivity.class);

                Intent intent = new Intent(this, BackgroundnewActvity.class);
                intent.putExtra("SELECTED_IMAGE_PATH", this.selectedImagePath);
                intent.putExtra("SELECTED_IMAGE", file2.getAbsolutePath());
                intent.putExtra("catPos",this.categoryPos);
                intent.putExtra("backPath",this.backPath);
                intent.putExtra("forePath",this.forePath);
                intent.putExtra("ratio", bitmap2.getWidth() + ":" + bitmap2.getHeight());
                intent.putExtra("currentItem",this.itemPosition);
                startActivity(intent);
                finish();
               /* if (whichActivity.equals("fromHome")){
                    Intent intent = new Intent(this, BackgroundnewActvity.class);
                    intent.putExtra("SELECTED_IMAGE_PATH", this.selectedImagePath);
                    intent.putExtra("SELECTED_IMAGE", file2.getAbsolutePath());
                    intent.putExtra("catPos",this.categoryPos);
                    intent.putExtra("backPath",this.backPath);
                    intent.putExtra("forePath",this.forePath);
                    intent.putExtra("ratio", bitmap2.getWidth() + ":" + bitmap2.getHeight());
                    intent.putExtra("originalPath",this.originalUri.toString());
                    intent.putExtra("currentItem",this.itemPosition);
                    startActivity(intent);
                    finish();

                }
                else {

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("SELECTED_IMAGE", file2.getAbsolutePath());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();

                }*/

               // finish();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            @SuppressLint("WrongConstant") Toast makeText = Toast.makeText(this, "Please edit photo before save..", 0);
            makeText.setGravity(16, 0, 0);
            makeText.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLImageSegmentationAnalyzer mLImageSegmentationAnalyzer = this.analyzer;
        if (mLImageSegmentationAnalyzer != null) {
            try {
                mLImageSegmentationAnalyzer.stop();
            } catch (IOException e) {
                Log.e("Auto crop", "Stop failed: " + e.getMessage());
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
