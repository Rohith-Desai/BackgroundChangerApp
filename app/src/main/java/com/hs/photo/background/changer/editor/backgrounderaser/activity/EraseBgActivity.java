package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.RemoteConfigValues;
import com.hs.photo.background.changer.editor.backgrounderaser.util.Datastore;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.face.MLFace;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzer;
import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods.getAdSize;
import static java.lang.Math.round;

public class EraseBgActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 189;
    private static final int CAMERA_REQUEST = 1888;

    private Bitmap bitmap;
    String originalUri;
    private MLImageSegmentationAnalyzer analyzer;
    TextView ebgBack;
    ImageView ebgImage;
    // ProgressDialog pDialog;
    CardView saveimageCard;
    CardView addBackgroundcard, erase_bg_card;
    private Bitmap savedBitmap;
    public String savePath;

    private Dialog dialogs;

    private FrameLayout adContainerView;

    public Dialog dialogremovingbg;

    public Dialog dialogpleasewait;

    public Dialog dialogsaving;
    public Dialog toastdialog;

    Boolean actionrequired = false;
    String imagepath;
    Dialog toaststatusdialog;
    EraseBgActivity eraseBgActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erase_bg);
        eraseBgActivity = this;
        ebgBack = (TextView) findViewById(R.id.btn_back_erase);
        ebgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.saveimageCard = (CardView) findViewById(R.id.save_ebg_image);
        addBackgroundcard = (CardView) findViewById(R.id.edit_ebg_image);
        erase_bg_card = (CardView) findViewById(R.id.card_edit_erase_bg);
        ebgImage = (ImageView) findViewById(R.id.erbg_image);

        adContainerView = findViewById(R.id.banner_ad_erasebg);

        if (RemoteConfigValues.getOurRemote().getShowBannerAd() != null) {
            if (RemoteConfigValues.getOurRemote().getShowBannerAd().equals("true")) {

                adContainerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBannerAderasebg();
                    }
                });

            }
        }

        if (isFirstTime()) {
            toaststatusdialog = new Dialog(this);
            toaststatusdialog.setCancelable(false);
            toaststatusdialog.setContentView(R.layout.toast_dialog_to_select_pic);
            Button btn_ok = (Button) toaststatusdialog.findViewById(R.id.btn_ok);
            toaststatusdialog.show();
            WindowManager.LayoutParams lp = toaststatusdialog.getWindow().getAttributes();
            lp.dimAmount = 0.3f;
            toaststatusdialog.getWindow().setAttributes(lp);
            toaststatusdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPhoto();//to  open camera and gallery option dialog and select image
                    toaststatusdialog.dismiss();
                }
            });
        } else {
            selectPhoto();//to  open camera and gallery option dialog and select image
        }

//        if (getIntent().hasExtra("SELECTED_IMAGE")) {
//            this.imagepath = getIntent().getStringExtra("SELECTED_IMAGE");
//            this.bitmap = BitmapFactory.decodeFile(this.imagepath);
//            ebgImage.setImageURI(Uri.fromFile(new File("" + this.imagepath)));
//
//        }
        // init();
//        selectPhoto();//to  open camera and gallery option dialog and select image






      /*  if (getIntent().hasExtra("SELECTED_IMG_URI")){

            this.originalUri = getIntent().getStringExtra("SELECTED_IMG_URI");
            ebgImage.setImageURI(Uri.parse(  this.originalUri));
            try {
                this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(  this.originalUri));
            } catch (IOException e) {
                e.printStackTrace();
            }

            analyzer();

        }*/
        erase_bg_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*ByteArrayOutputStream bstream = new ByteArrayOutputStream();
                savedBitmap.compress(Bitmap.CompressFormat.PNG,100,bstream);
                byte[] byteArray = bstream.toByteArray();
                Intent intent = new Intent(EraseBgActivity.this, EraserActivity.class);
                intent.putExtra("fromActivity", "Erasebg");
                intent.putExtra("SELECTED_IMAGE", byteArray );
                startActivityForResult(intent, 1);*/


                if (savedBitmap != null) {
                    actionrequired = true;
                    new gotoHomescreen().execute(new Void[0]);
                } else {
                    Toast.makeText(EraseBgActivity.this, "Please a Image To erase background", Toast.LENGTH_SHORT).show();
                }

            }
        });
        this.saveimageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedBitmap != null) {
                    new saveAndGoimagbg().execute(new Void[0]);
                } else {
                    Toast.makeText(EraseBgActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    selectPhoto();
                }


            }
        });
        addBackgroundcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedBitmap != null) {
                   /* pDialog.setMessage("Please wait...");
                    pDialog.show();
                    pDialog.setCancelable(false);*/
                    //  gotoHomeActivity();
                    actionrequired = false;
                    new gotoHomescreen().execute(new Void[0]);
                } else {
                    Toast.makeText(EraseBgActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                    selectPhoto();
                }

            }
        });

    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    public void loadBannerAderasebg() {
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

    @SuppressLint("StaticFieldLeak")
    public class gotoHomescreen extends AsyncTask<Void, Void, String> {
        @Override
        public String doInBackground(Void... voidArr) {
            File file2 = null;

            savedBitmap.setHasAlpha(true);
            long currentTimeMillis = System.currentTimeMillis();
            String str = "_" + currentTimeMillis + ".";
            File file = new File(getCacheDir(), "Cutouts");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                file2 = new File(file, str);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                savedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                // Intent intent = new Intent(this, ChangeBackgroundActivity.class);


            } catch (IOException e) {
                e.printStackTrace();
            }


            return file2.getAbsolutePath();
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
//            if (actionrequired==false) {
            Dialog dialog = new Dialog(EraseBgActivity.this);
            EraseBgActivity.this.dialogpleasewait = dialog;
            dialog.setContentView(R.layout.please_wait_laytout);
            EraseBgActivity.this.dialogpleasewait.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            EraseBgActivity.this.dialogpleasewait.show();
            EraseBgActivity.this.dialogpleasewait.setCancelable(false);

//            }
        }

        @SuppressLint("WrongConstant")
        @Override
        public void onPostExecute(String str) {

            // BackgroundnewActvity backgroundnewActvity = BackgroundnewActvity.this;
            //  backgroundnewActvity.savePath = backgroundnewActvity.savePhoto();

            if (EraseBgActivity.this.dialogpleasewait != null && EraseBgActivity.this.dialogpleasewait.isShowing()) {
                EraseBgActivity.this.dialogpleasewait.dismiss();
            }
            if (str != null) {

                if (str.equals("")) {
                    Toast.makeText(EraseBgActivity.this, "Couldn't save photo, error", 0).show();
                    finish();
                } else {
                    if (actionrequired) {
                        Intent intent = new Intent(EraseBgActivity.this, EraserActivity.class);
                        intent.putExtra("fromActivity", "Erasebg");
                        intent.putExtra("SELECTED_IMAGE", str);
                        startActivityForResult(intent, 1);

                    } else {
                        Intent intent = new Intent(EraseBgActivity.this, HomeActivity.class);
                        intent.putExtra("fromActivity", "Erasebg");
                        intent.putExtra("SELECTED_IMAGE", str);
                        startActivity(intent);
                    }
                }


            } else {

                Toast.makeText(EraseBgActivity.this, "Couldn't save photo, error", 0).show();
                finish();

            }

        }
    }

    private void gotoHomeActivity() {
       /* CommonMethods.getInstance().setCROPPED_BITMAP(this.savedBitmap);
        Intent intent=new Intent(EraseBgActivity.this,HomeActivity.class);
        intent.putExtra("fromActivity","Erasebg");
        startActivity(intent);*/
        if (savedBitmap != null) {
            savedBitmap.setHasAlpha(true);
            long currentTimeMillis = System.currentTimeMillis();
            String str = "_" + currentTimeMillis + ".";
            File file = new File(getCacheDir(), "Cutouts");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File file2 = new File(file, str);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                savedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                // Intent intent = new Intent(this, ChangeBackgroundActivity.class);

                if (dialogs != null && dialogs.isShowing()) {
                    dialogs.dismiss();
                }

                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("fromActivity", "Erasebg");
                intent.putExtra("SELECTED_IMAGE", file2.getAbsolutePath());
                startActivity(intent);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            @SuppressLint("WrongConstant") Toast makeText = Toast.makeText(this, "Please edit photo before save..", 0);
            makeText.setGravity(16, 0, 0);
            makeText.show();
        }
    }

    public void selectPhoto() {

        dialogs = new Dialog(this);
        dialogs.setCancelable(false);
        dialogs.setContentView(R.layout.select_image_layout);
        LinearLayout galleryImage = dialogs.findViewById(R.id.linear_gallery_portland);
        final LinearLayout cameraImage = dialogs.findViewById(R.id.linear_camera_portland);
        ImageView closeImage = (ImageView) dialogs.findViewById(R.id.close_portland);
        dialogs.show();
        galleryImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.PICK");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        cameraImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                launchCamera();

            }
        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialogs.dismiss();
            }
        });
    }

    public void launchCamera() {
        String SAVE_PATH = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                : Environment.getExternalStorageDirectory().toString();

        if (ContextCompat.checkSelfPermission(EraseBgActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            File photo = null;
            try {
                photo = createImageFiles();
                Uri photoURI = FileProvider.getUriForFile(EraseBgActivity.this, getApplicationContext().getPackageName() + ".fileprovider", photo);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                saveUriPath(Uri.fromFile(photo).getPath());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Start the image capture intent to take photo
                    startActivityForResult(intent, CAMERA_REQUEST);
                }

            } catch (IOException e) {

            }
        }


    }

    private static final String PHOTO_PREFERENCE_NAME = "background_temp_data";
    private static final String PHOTO_KEY = "BACKGROUND_IMAGE_URI_PATH";

    public void saveUriPath(String selectedImageUriPath) {
        SharedPreferences savePhotoData = getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = savePhotoData.edit();
        prefEditor.putString(PHOTO_KEY, selectedImageUriPath);
        prefEditor.apply();
    }

    public String getUriPath() {
        SharedPreferences getSelectedImageUriPath = getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return getSelectedImageUriPath.getString(PHOTO_KEY, null);
    }

    private File createImageFiles() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }

    @SuppressLint("StaticFieldLeak")
    public class saveAndGoimagbg extends AsyncTask<Void, Void, String> {
        @Override
        public String doInBackground(Void... voidArr) {

            EraseBgActivity eraseBgActivity = EraseBgActivity.this;
            eraseBgActivity.savePath = eraseBgActivity.savePhoto();
            return "";
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            Dialog dialog = new Dialog(EraseBgActivity.this);
            EraseBgActivity.this.dialogsaving = dialog;
            dialog.setContentView(R.layout.saving_laytout);
            EraseBgActivity.this.dialogsaving.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            EraseBgActivity.this.dialogsaving.show();
            EraseBgActivity.this.dialogsaving.setCancelable(false);


        }

        @SuppressLint("WrongConstant")
        @Override
        public void onPostExecute(String str) {

            // BackgroundnewActvity backgroundnewActvity = BackgroundnewActvity.this;
            //  backgroundnewActvity.savePath = backgroundnewActvity.savePhoto();

            if (EraseBgActivity.this.dialogsaving != null && EraseBgActivity.this.dialogsaving.isShowing()) {
                EraseBgActivity.this.dialogsaving.dismiss();
            }
            if (EraseBgActivity.this.savePath.equals("")) {
                Toast.makeText(EraseBgActivity.this, "Couldn't save photo, error", 0).show();
            } else {
                openNext();
            }
        }
    }

    public void openNext() {
        Intent intent = new Intent().setClass(this, SaveImageActivity.class);
        intent.setData(Uri.parse(this.savePath));
        startActivity(intent);
        //finish();
    }

    @SuppressLint("WrongConstant")
    public String savePhoto() {
        String str = "";
        try {

            Bitmap drawingCache = this.savedBitmap;
            String str2 = new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.valueOf(System.currentTimeMillis()) + ".png";
            File file = new File(pathtoSave("SpiralPhoto"));
            if (!file.exists()) {
                file.mkdirs();
            }
            try {

                File file2 = new File(file, str2);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                drawingCache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();


               /* ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file2 = new File(file.getAbsolutePath() + File.separator + str2);
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();*/
                str = file.getAbsolutePath() + File.separator + str2;
                MediaScannerConnection.scanFile(this, new String[]{file2.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                if (Build.VERSION.SDK_INT < 29) {
                    addImageGallery(str);
                }
            } catch (Exception e) {
                Log.i("Photos to Collage", e.getMessage());
            } catch (Throwable th) {
            }
        } catch (Exception unused) {
        }
        return str;
    }

    private String pathtoSave(String str) {
        String SAVE_PATH = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ?
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                : Environment.getExternalStorageDirectory().toString();
        return new File(SAVE_PATH + "/" + getString(R.string.app_name) + "/ErasePhoto").getPath();
    }

    private void addImageGallery(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("mime_type", "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

   /* public void init() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pDialog = progressDialog;
        progressDialog.setMessage("Removing background...");
        this.pDialog.setCancelable(false);
    }*/

    private void analyzer() {
        if (this.bitmap != null) {
            this.analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(new MLImageSegmentationSetting.Factory().setExact(false).setAnalyzerType(0).setScene(0).create());
            this.analyzer.asyncAnalyseFrame(new MLFrame.Creator().setBitmap(this.bitmap).create()).addOnSuccessListener(new OnSuccessListener<MLImageSegmentation>() {

                public void onSuccess(MLImageSegmentation mLImageSegmentation) {
                    if (mLImageSegmentation != null) {

                        EraseBgActivity.this.displaySuccess(mLImageSegmentation);

//                                if (ebgImage.getDrawable() !=null){
//                                    Toast.makeText(EraseBgActivity.this, "Please take another picture", Toast.LENGTH_SHORT).show();
//                                }


//                             EraseBgActivity.this.displaySuccess(mLImageSegmentation);

                    } else {
                        Toast.makeText(EraseBgActivity.this, "check The photo upload once ", Toast.LENGTH_SHORT).show();
                        EraseBgActivity.this.displayFailure();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(Exception exc) {
                    exc.printStackTrace();
                    EraseBgActivity.this.displayFailure();
                }
            }).addOnCompleteListener(new OnCompleteListener<MLImageSegmentation>() {
                @Override
                public void onComplete(Task<MLImageSegmentation> task) {
                    Log.v("TAG", "onComplete.." + task.isSuccessful());
                }
            });

        } else {

            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    private void displaySuccess(MLImageSegmentation mLImageSegmentation) {
        double count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0, count6 = 0, count7 = 0, count8 = 0;
        int x, y;
        float m = (10) / (6);
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
//            Toast.makeText(this, "Please Take another picture ", Toast.LENGTH_SHORT).show();
            if (EraseBgActivity.this.dialogremovingbg != null && EraseBgActivity.this.dialogremovingbg.isShowing()) {
                EraseBgActivity.this.dialogremovingbg.dismiss();
            }
            toastdialog = new Dialog(this);
            toastdialog.setCancelable(false);
            toastdialog.setContentView(R.layout.toast_dialog_to_change_pic);
            Button choosephoto = (Button) toastdialog.findViewById(R.id.choose_photo);
            toastdialog.show();
            WindowManager.LayoutParams lp = toastdialog.getWindow().getAttributes();
            lp.dimAmount = 0.3f;
            toastdialog.getWindow().setAttributes(lp);
            toastdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            // to choose the other picture
            choosephoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toastdialog.dismiss();
                    dialogs.show();
                }
            });
        }
        Bitmap background = mLImageSegmentation.original;
        CommonMethods.getInstance().setCROPPED_BITMAP(mLImageSegmentation.original);
        if (foreground != null) {
            bitmap = foreground;
            // showAd();
            openNext(this.bitmap);
            //openNext(bitmap);
        } else {
            displayFailure();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (EraseBgActivity.this.dialogremovingbg != null && EraseBgActivity.this.dialogremovingbg.isShowing()) {
                    EraseBgActivity.this.dialogremovingbg.dismiss();
                } else {
//                    Toast.makeText(EraseBgActivity.this, "", Toast.LENGTH_SHORT).show();
                }

            }
        }, 4000);

        // this.pDialog.dismiss();
    }

    @SuppressLint("WrongConstant")
    private void displayFailure() {
        Toast.makeText(getApplicationContext(), "Fail", 0).show();
    }

    public void openNext(Bitmap bitmap2) {
        if (bitmap2 != null) {
            bitmap2.setHasAlpha(true);
            ebgImage.setImageBitmap(bitmap2);
            this.savedBitmap = bitmap2;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {// to get the image after background ersered from eraser activity
            this.bitmap = Datastore.getOurRemote().getStickerBimap();
            if (this.bitmap != null) {
                openNext(this.bitmap);
                ebgImage.setImageBitmap(this.bitmap);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == -1 && data != null && data.getData() != null) {

            if (dialogs != null && dialogs.isShowing()) {
                dialogs.dismiss();
            }
            Dialog dialog = new Dialog(EraseBgActivity.this);
            EraseBgActivity.this.dialogremovingbg = dialog;
            dialog.setContentView(R.layout.gotohome_laytout);
            EraseBgActivity.this.dialogremovingbg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            EraseBgActivity.this.dialogremovingbg.show();
            EraseBgActivity.this.dialogremovingbg.setCancelable(false);

            Uri dataa = data.getData();

            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dataa);
                if (b != null) {
                    if (ebgImage != null) {
                        ebgImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dataa);

            } catch (IOException e) {
                e.printStackTrace();
            }

            analyzer();// method to change the back ground


        } else if (requestCode == CAMERA_REQUEST && resultCode == -1) {

            if (dialogs != null && dialogs.isShowing()) {
                dialogs.dismiss();
            }
//            Bitmap imgbitmp = BitmapFactory.decodeFile(getUriPath());
//            int w = imgbitmp.getWidth();
//            int h = imgbitmp.getHeight();
//            for(int i =  0; i < w; i++){
//                for(int j = 0; j < h; j++) {
//                    int pixel =  imgbitmp.getPixel(i, j);
//
//                    if(pixel == Color.WHITE) {
//                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
            if (getUriPath() != null) {

                Dialog dialog = new Dialog(EraseBgActivity.this);
                EraseBgActivity.this.dialogremovingbg = dialog;
                dialog.setContentView(R.layout.gotohome_laytout);
                EraseBgActivity.this.dialogremovingbg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                EraseBgActivity.this.dialogremovingbg.show();
                EraseBgActivity.this.dialogremovingbg.setCancelable(false);
                Uri stringUri = Uri.fromFile(new File(getUriPath()));
                Matrix matrix = new Matrix();

                try {
                    Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), stringUri);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);

                    ebgImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  ebgImage.setImageURI(stringUri);
                try {
                    this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), stringUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                matrix.postRotate(90);
                if (this.bitmap != null) {
                    this.bitmap = Bitmap.createBitmap(this.bitmap, 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight(), matrix, true);
                }

                analyzer();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}