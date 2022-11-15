package com.hs.photo.background.changer.editor.backgrounderaser.bitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;

import com.hs.photo.background.changer.editor.backgrounderaser.SpiralApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;

public class BitmapUtil {
    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap byteToBitmap(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
    }

    public static String bitmapToString(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByte(bitmap), 0);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return ((BitmapDrawable) drawable).getBitmap();
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(bitmap);
    }

    public static Bitmap scaleImageTo(Bitmap bitmap, int i, int i2) {
        return scaleImage(bitmap, ((float) i) / ((float) bitmap.getWidth()), ((float) i2) / ((float) bitmap.getHeight()));
    }

    public static Bitmap scaleImage(Bitmap bitmap, float f, float f2) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int height2 = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(height2, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, height2, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        float f = (float) (height2 / 2);
        canvas.drawCircle(f, (float) (height / 2), f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitmap, boolean z) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(120.0f / ((float) width), 120.0f / ((float) height));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (z) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static boolean saveBitmap(Bitmap bitmap, File file) {
        if (bitmap == null) {
            return false;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, String str) {
        return saveBitmap(bitmap, new File(str));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 <= i2 && i4 <= i) {
            return 1;
        }
        int round = Math.round(((float) i3) / ((float) i2));
        int round2 = Math.round(((float) i4) / ((float) i));
        return round < round2 ? round : round2;
    }

    public static Bitmap getSmallBitmap(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(str, options);
    }

    public static Intent buildGalleryPickIntent(Uri uri, int i, int i2, int i3, int i4, boolean z) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uri);
        intent.putExtra("aspectX", i);
        intent.putExtra("aspectY", i2);
        intent.putExtra("outputX", i3);
        intent.putExtra("outputY", i4);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", z);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildImagePickIntent(Uri uri, Uri uri2, int i, int i2, int i3, int i4, boolean z) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", uri2);
        intent.putExtra("aspectX", i);
        intent.putExtra("aspectY", i2);
        intent.putExtra("outputX", i3);
        intent.putExtra("outputY", i4);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", z);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        return intent;
    }

    public static Intent buildCaptureIntent(Uri uri) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        return intent;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = (float) i;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static Bitmap margeBitmap(Bitmap bitmap, Bitmap bitmap2, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, bitmap.getWidth(), bitmap.getHeight(), true);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        paint.setAlpha(i);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException unused) {
            return null;
        }
    }

    public static byte[] decodeFile(byte[] bArr) throws Exception {
        Cipher instance = Cipher.getInstance("AES");
        SpiralApp.getInstance();
        return instance.doFinal(bArr);
    }
}
