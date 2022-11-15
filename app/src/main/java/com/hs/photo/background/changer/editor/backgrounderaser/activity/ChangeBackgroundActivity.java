package com.hs.photo.background.changer.editor.backgrounderaser.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hs.photo.background.changer.editor.backgrounderaser.R;
import com.hs.photo.background.changer.editor.backgrounderaser.adapter.CommonAdapter;
import com.hs.photo.background.changer.editor.backgrounderaser.ads.CommonMethods;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.MultiTouchListener;
import com.hs.photo.background.changer.editor.backgrounderaser.mytouch.MultiTouchListener2view;
import com.hs.photo.background.changer.editor.backgrounderaser.util.ColorFilterGenerator;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer;
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationSetting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.hs.photo.background.changer.editor.backgrounderaser.activity.CropActivity.selectedImagePath;

public class ChangeBackgroundActivity extends AppCompatActivity implements View.OnClickListener {

    private String stingBG;
    private String stingCut;
    public String[] listItem;
    private CommonAdapter commonAdapter;
    private RecyclerView backgroundsRecycler;
    MultiTouchListener multiTouchListener;
    MultiTouchListener2view multiTouchListener2view;
    private ImageView imgFront, imgBack;
    private TextView btnBack;
    private LinearLayout btnSave;
    private ConstraintLayout rlBody;
    private RelativeLayout rlMain;
    private String changedImagePath = null;
    ProgressDialog pDialog;
    private Bitmap bitmap = null;
    private MLImageSegmentationAnalyzer analyzer;
    private String ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_background);


        this.stingBG = getIntent().getStringExtra("SELECTED_IMAGE_PATH");
        this.stingCut = getIntent().getStringExtra("SELECTED_IMAGE");
        this.ratio = getIntent().getStringExtra("ratio");

        init();

        this.rlBody = (ConstraintLayout) findViewById(R.id.rlBody);
        this.rlMain = (RelativeLayout) findViewById(R.id.rlMain);

        setConstraintLayout("720:1080");

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgFront = (ImageView) findViewById(R.id.img_front);
        this.btnSave = (LinearLayout) findViewById(R.id.btn_save);
        this.btnBack = (TextView) findViewById(R.id.btn_back);
        this.btnSave.setOnClickListener(this);
        this.btnBack.setOnClickListener(this);
        multiTouchListener = new MultiTouchListener();
        multiTouchListener2view = new MultiTouchListener2view(this.imgFront);
        imgFront.setOnTouchListener(multiTouchListener2view);
        backgroundsRecycler = findViewById(R.id.backgrounds_recycler);

        imgFront.setImageBitmap(BitmapFactory.decodeFile(stingCut));
        imgBack.setImageBitmap(BitmapFactory.decodeFile(stingBG));
        loadBackgrounds("background");

    }

    private void setConstraintLayout(String ratio) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.rlBody);
        constraintSet.setDimensionRatio(this.rlMain.getId(), ratio);
        constraintSet.applyTo(this.rlBody);
    }

    public void init() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pDialog = progressDialog;
        progressDialog.setMessage("Loading...!");
        this.pDialog.setCancelable(false);
    }

    public void loadBackgrounds(String str) {
        String strCpy = str;

        try {
            this.listItem = getAssets().list("sp/" + str + "/thumb");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (this.listItem != null) {
            ArrayList arrayList = new ArrayList();
            String[] strArr = this.listItem;
            for (int i = 0; i < strArr.length; i++) {
                arrayList.add("sp/" + str + "/thumb/" + strArr[i]);
            }
            String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
            this.listItem = strArr2;
            CommonAdapter commonAdapter2 = new CommonAdapter(strArr2, this);
            this.commonAdapter = commonAdapter2;
            this.backgroundsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            this.backgroundsRecycler.setAdapter(commonAdapter2);
            //Objects.requireNonNull(backgroundsRecycler.getLayoutManager()).scrollToPosition(1);
            loadAssetInGlide("", imgBack, true);
            this.commonAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {

                @SuppressLint({"WrongConstant", "ClickableViewAccessibility"})
                @Override
                public void onItemClick(View view, String str) {
                    Log.v("REMOVE", "str : " + str);
                    if (str.contains("remove_new")) {
                        //imgBack.setImageBitmap(BitmapFactory.decodeFile(stingBG));
                        setConstraintLayout(ratio);
                        imgBack.setImageBitmap(CommonMethods.getInstance().getCROPPED_BITMAP());
                        // imgFront.setImageResource(R.drawable.default_tra);
                        imgFront.setOnTouchListener(null);
                        multiTouchListener2view.moveDefault(imgFront);
                        return;
                    } else {

                        //SpiralEditorActivity. this.multiTouchListener2view.moveDefault( imgCut);
                        //multiTouchListener2view.moveDefault(imgFront);
                        //multiTouchListener2view.moveDefault(imgBack);
                        // SpiralEditorActivity. this.multiTouchListener.moveDefault( rl3view);
                        //hueSeekBar.setProgress(50);
                        setConstraintLayout("720:1080");
                        imgBack.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                        imgFront.setColorFilter(ColorFilterGenerator.adjustHue(1.0f));
                        //rlHue.setVisibility(0);
                        imgFront.setOnTouchListener(multiTouchListener2view);
                        loadAssetInGlide(str.replace("thumb", "back"), imgBack, false);
                       /* if (strCpy.equals("background")) {
                            loadAssetInGlide(str.replace("thumb", ""), imgBack);
                            loadAssetInGlide(str.replace("thumb", ""), imgFront);
                        } else {*/

                        //loadAssetInGlide(str.replace("thumb", "front"), imgFront);
                        // }

                    }

                }
            });
        }
    }

    public void loadAssetInGlide(String str, ImageView imageView, boolean first) {
        getEncriptedImage(str, imageView, first);
    }

    public void getEncriptedImage(String str, ImageView imageView, boolean first) {
        if (first)
            Glide.with((FragmentActivity) this).load(Uri.parse("file:///android_asset/sp/background/back/back_1.webp")).into(imageView);
        else {
            try {
                InputStream open = getAssets().open(str);
                byte[] bArr = new byte[open.available()];
                open.read(bArr);
                open.close();
                String ASSET_IMG = str;

                Glide.with((FragmentActivity) this).load(Uri.parse("file:///android_asset/" + ASSET_IMG)).into(imageView);
                // Glide.with((FragmentActivity) this).load(BitmapUtil.decodeFile(bArr)).into(imageView);
            } catch (IOException e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.default_tra);
            } catch (Exception e2) {
                e2.printStackTrace();
                imageView.setImageResource(R.drawable.default_tra);
            }
        }
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
                Intent intent = new Intent(this, SpiralEditorActivity.class);

                intent.putExtra("SELECTED_IMAGE_PATH", changedImagePath);
                intent.putExtra("SELECTED_IMAGE", file2.getAbsolutePath());
                intent.putExtra("ratio", bitmap2.getWidth() + ":" + bitmap2.getHeight());
                startActivity(intent);
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

    @SuppressLint("WrongConstant")
    public String savePhoto() {
        String str = "";
        try {
            this.rlMain.setDrawingCacheEnabled(true);
            this.rlMain.setDrawingCacheQuality(1048576);
            Bitmap drawingCache = this.rlMain.getDrawingCache();
            String str2 = new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.valueOf(System.currentTimeMillis()) + ".jpg";
            File file = new File(pathtoSave("SpiralPhoto"));
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                File file2 = new File(file.getAbsolutePath() + File.separator + str2);
                file2.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                fileOutputStream.write(byteArrayOutputStream.toByteArray());
                fileOutputStream.flush();
                fileOutputStream.close();
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
            this.rlMain.setDrawingCacheEnabled(false);
        } catch (Exception unused) {
        }
        return str;
    }

    private void addImageGallery(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("mime_type", "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    private String pathtoSave(String str) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/SpiralPhoto").getPath();
    }

    @Override
    public void onClick(View v) {
        if (v == btnBack)
            onBackPressed();
        else if (v == btnSave) {
            this.pDialog.show();
            changedImagePath = savePhoto();
            bitmap = BitmapFactory.decodeFile(changedImagePath);
            analyzer();
        }

    }

    @SuppressLint("WrongConstant")
    private Bitmap getDrawingCache() {
        this.rlMain.setDrawingCacheEnabled(true);
        this.rlMain.setDrawingCacheQuality(1048576);
        Bitmap drawingCache = this.rlMain.getDrawingCache();
        this.rlMain.setDrawingCacheEnabled(false);
        return drawingCache;
    }

    private void analyzer() {
        this.analyzer = MLAnalyzerFactory.getInstance().getImageSegmentationAnalyzer(new MLImageSegmentationSetting.Factory().setExact(false).setAnalyzerType(0).setScene(0).create());
        this.analyzer.asyncAnalyseFrame(new MLFrame.Creator().setBitmap(this.bitmap).create()).addOnSuccessListener(new OnSuccessListener<MLImageSegmentation>() {

            public void onSuccess(MLImageSegmentation mLImageSegmentation) {
                if (mLImageSegmentation != null) {
                    displaySuccess(mLImageSegmentation);
                } else {
                    displayFailure();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(Exception exc) {
                exc.printStackTrace();
                displayFailure();
            }
        }).addOnCompleteListener(new OnCompleteListener<MLImageSegmentation>() {
            @Override
            public void onComplete(Task<MLImageSegmentation> task) {
                Log.v("TAG", "onComplete.." + task.isSuccessful());
            }
        });
    }

    private void displaySuccess(MLImageSegmentation mLImageSegmentation) {

        if (this.bitmap == null) {
            displayFailure();
            return;
        }
        Bitmap foreground = mLImageSegmentation.getForeground();
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

    @SuppressLint("WrongConstant")
    private void displayFailure() {
        Toast.makeText(getApplicationContext(), "Fail", 0).show();
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
       // CommonMethods.getInstance().activitiesAD(ChangeBackgroundActivity.this);
    }
}