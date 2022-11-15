package com.hs.photo.background.changer.editor.backgrounderaser.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;
import android.os.Build;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Array;
import java.util.Random;

public class BitmapProcessing {
    public static Bitmap rotate(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean z, boolean z2) {
        Matrix matrix = new Matrix();
        float f = 1.0f;
        float f2 = z ? -1.0f : 1.0f;
        if (z2) {
            f = -1.0f;
        }
        matrix.preScale(f2, f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap cdepth(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int i4 = i / 2;
                int i5 = red + i4;
                int i6 = (i5 - (i5 % i)) - 1;
                if (i6 < 0) {
                    i6 = 0;
                }
                int i7 = green + i4;
                int i8 = (i7 - (i7 % i)) - 1;
                if (i8 < 0) {
                    i8 = 0;
                }
                int i9 = blue + i4;
                int i10 = (i9 - (i9 % i)) - 1;
                if (i10 < 0) {
                    i10 = 0;
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i6, i8, i10));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap noise(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[(width * height)];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        Random random = new Random();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = (i * width) + i2;
                iArr[i3] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)) | iArr[i3];
            }
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap brightness(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                int i4 = red + i;
                int i5 = 255;
                if (i4 > 255) {
                    i4 = 255;
                } else if (i4 < 0) {
                    i4 = 0;
                }
                int i6 = green + i;
                if (i6 > 255) {
                    i6 = 255;
                } else if (i6 < 0) {
                    i6 = 0;
                }
                int i7 = blue + i;
                if (i7 <= 255) {
                    i5 = i7 < 0 ? 0 : i7;
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i4, i6, i5));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap sepia(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                int pixel = bitmap.getPixel(i, i2);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                double d = (double) red;
                Double.isNaN(d);
                double d2 = (double) green;
                Double.isNaN(d2);
                double d3 = (double) blue;
                Double.isNaN(d3);
                Double.isNaN(d);
                Double.isNaN(d2);
                Double.isNaN(d3);
                int i3 = (int) ((d * 0.3d) + (d2 * 0.59d) + (d3 * 0.11d));
                int i4 = i3 + 110;
                int i5 = 255;
                if (i4 > 255) {
                    i4 = 255;
                }
                int i6 = i3 + 65;
                if (i6 > 255) {
                    i6 = 255;
                }
                int i7 = i3 + 20;
                if (i7 <= 255) {
                    i5 = i7;
                }
                createBitmap.setPixel(i, i2, Color.argb(alpha, i4, i6, i5));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap gamma(Bitmap bitmap, double d, double d2, double d3) {
        double d4 = (d + 2.0d) / 10.0d;
        double d5 = (d2 + 2.0d) / 10.0d;
        double d6 = (d3 + 2.0d) / 10.0d;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[256];
        int i = 0;
        for (int i2 = 256; i < i2; i2 = 256) {
            double d7 = (double) i;
            Double.isNaN(d7);
            Double.isNaN(d7);
            double d8 = d7 / 255.0d;
            iArr[i] = Math.min(255, (int) ((Math.pow(d8, 1.0d / d4) * 255.0d) + 0.5d));
            iArr2[i] = Math.min(255, (int) ((Math.pow(d8, 1.0d / d5) * 255.0d) + 0.5d));
            iArr3[i] = Math.min(255, (int) ((Math.pow(d8, 1.0d / d6) * 255.0d) + 0.5d));
            i++;
        }
        for (int i3 = 0; i3 < width; i3++) {
            for (int i4 = 0; i4 < height; i4++) {
                int pixel = bitmap.getPixel(i3, i4);
                createBitmap.setPixel(i3, i4, Color.argb(Color.alpha(pixel), iArr[Color.red(pixel)], iArr2[Color.green(pixel)], iArr3[Color.blue(pixel)]));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap contrast(Bitmap bitmap, double d) {
        int i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas();
        canvas.setBitmap(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint((int) ViewCompat.MEASURED_STATE_MASK));
        double pow = Math.pow((d + 100.0d) / 100.0d, 2.0d);
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                double red = (double) Color.red(pixel);
                Double.isNaN(red);
                Double.isNaN(red);
                int i4 = (int) (((((red / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i4 < 0) {
                    i4 = 0;
                } else if (i4 > 255) {
                    i4 = 255;
                }
                double green = (double) Color.green(pixel);
                Double.isNaN(green);
                Double.isNaN(green);
                int i5 = (int) (((((green / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i5 < 0) {
                    i5 = 0;
                } else if (i5 > 255) {
                    i5 = 255;
                }
                double blue = (double) Color.blue(pixel);
                Double.isNaN(blue);
                Double.isNaN(blue);
                int i6 = (int) (((((blue / 255.0d) - 0.5d) * pow) + 0.5d) * 255.0d);
                if (i6 < 0) {
                    i = 0;
                } else {
                    i = 255;
                    if (i6 <= 255) {
                        i = i6;
                    }
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, i4, i5, i));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap saturation(Bitmap bitmap, int i) {
        double d = (double) i;
        Double.isNaN(d);
        Double.isNaN(d);
        float f = (float) (d / 100.0d);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap grayscale(Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.213f, 0.715f, 0.072f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap vignette(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double d = (double) width;
        Double.isNaN(d);
        Double.isNaN(d);
        RadialGradient radialGradient = new RadialGradient((float) (width / 2), (float) (height / 2), (float) (d / 1.2d), new int[]{0, 1426063360, ViewCompat.MEASURED_STATE_MASK}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(1, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setShader(radialGradient);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRect(new RectF(rect), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmap;
    }

    public static Bitmap hue(Bitmap bitmap, float f) {
        Bitmap copy = bitmap.copy(bitmap.getConfig(), true);
        int width = copy.getWidth();
        int height = copy.getHeight();
        float[] fArr = new float[3];
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int pixel = copy.getPixel(i2, i);
                Color.colorToHSV(pixel, fArr);
                fArr[0] = f;
                copy.setPixel(i2, i, Color.HSVToColor(Color.alpha(pixel), fArr));
            }
        }
        bitmap.recycle();
        return copy;
    }

    public static Bitmap tint(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Paint paint = new Paint((int) SupportMenu.CATEGORY_MASK);
        paint.setColorFilter(new LightingColorFilter(i, 1));
        Canvas canvas = new Canvas();
        canvas.setBitmap(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap screenBitmap(Bitmap bitmap, Bitmap bitmap2, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        paint.setShader(new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAlpha(i);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
        return createBitmap;
    }

    public static Bitmap overlaybitmap(Bitmap bitmap, Bitmap bitmap2, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        if (Build.VERSION.SDK_INT > 10) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        }
        paint.setShader(new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAlpha(i);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
        return createBitmap;
    }

    public static Bitmap cropbitmap(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() - i, bitmap.getHeight() - i, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        int i2 = i / 2;
        new Canvas(createBitmap).drawBitmap(bitmap, new Rect(i2, i2, bitmap.getWidth() - i2, bitmap.getHeight() - i2), new Rect(0, 0, bitmap.getWidth() - i, bitmap.getHeight() - i), paint);
        return createBitmap;
    }

    public static Bitmap makeGradient(Bitmap bitmap, int i, int i2, int i3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = (float) (width / 2);
        float f2 = (float) height;
        LinearGradient linearGradient = new LinearGradient(f, 0.0f, f, f2, i, i2, Shader.TileMode.REPEAT);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setShader(linearGradient);
        paint.setAlpha((i3 * 255) / 100);
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawRect(0.0f, 0.0f, (float) width, f2, paint);
        return createBitmap;
    }

    public static Bitmap invert(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        for (int i = 0; i < height; i++) {
            for (int i2 = 0; i2 < width; i2++) {
                int pixel = bitmap.getPixel(i2, i);
                createBitmap.setPixel(i2, i, Color.argb(Color.alpha(pixel), 255 - Color.red(pixel), 255 - Color.green(pixel), 255 - Color.blue(pixel)));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap boost(Bitmap bitmap, int i, float f) {
        float f2 = f / 100.0f;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        for (int i2 = 0; i2 < width; i2++) {
            for (int i3 = 0; i3 < height; i3++) {
                int pixel = bitmap.getPixel(i2, i3);
                int alpha = Color.alpha(pixel);
                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);
                if (i == 1) {
                    red = (int) (((float) red) * (1.0f + f2));
                    if (red > 255) {
                        red = 255;
                    }
                } else if (i == 2) {
                    green = (int) (((float) green) * (1.0f + f2));
                    if (green > 255) {
                        green = 255;
                    }
                } else if (i == 3 && (blue = (int) (((float) blue) * (1.0f + f2))) > 255) {
                    blue = 255;
                }
                createBitmap.setPixel(i2, i3, Color.argb(alpha, red, green, blue));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static final Bitmap sketch(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, 3, 3);
        for (int i = 0; i < height - 2; i++) {
            int i2 = 0;
            while (i2 < width - 2) {
                for (int i3 = 0; i3 < 3; i3++) {
                    for (int i4 = 0; i4 < 3; i4++) {
                        iArr[i3][i4] = bitmap.getPixel(i2 + i3, i + i4);
                    }
                }
                int alpha = Color.alpha(iArr[1][1]);
                int red = ((((Color.red(iArr[1][1]) * 6) - Color.red(iArr[0][0])) - Color.red(iArr[0][2])) - Color.red(iArr[2][0])) - Color.red(iArr[2][2]);
                int green = ((((Color.green(iArr[1][1]) * 6) - Color.green(iArr[0][0])) - Color.green(iArr[0][2])) - Color.green(iArr[2][0])) - Color.green(iArr[2][2]);
                int blue = ((((Color.blue(iArr[1][1]) * 6) - Color.blue(iArr[0][0])) - Color.blue(iArr[0][2])) - Color.blue(iArr[2][0])) - Color.blue(iArr[2][2]);
                int i5 = red + 130;
                int i6 = 255;
                if (i5 < 0) {
                    i5 = 0;
                } else if (i5 > 255) {
                    i5 = 255;
                }
                int i7 = green + 130;
                if (i7 < 0) {
                    i7 = 0;
                } else if (i7 > 255) {
                    i7 = 255;
                }
                int i8 = blue + 130;
                if (i8 < 0) {
                    i6 = 0;
                } else if (i8 <= 255) {
                    i6 = i8;
                }
                i2++;
                createBitmap.setPixel(i2, i + 1, Color.argb(alpha, i5, i7, i6));
            }
        }
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 2) {
                return flip(bitmap, true, false);
            }
            if (attributeInt == 3) {
                return rotate(bitmap, 180.0f);
            }
            if (attributeInt == 4) {
                return flip(bitmap, false, true);
            }
            if (attributeInt == 6) {
                return rotate(bitmap, 90.0f);
            }
            if (attributeInt != 8) {
                return bitmap;
            }
            return rotate(bitmap, 270.0f);
        } catch (Exception | OutOfMemoryError unused) {
            return bitmap;
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int i, int i2) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            new Canvas(createBitmap).drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, i, i2), paint);
            return createBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap overlayBlend(Bitmap bitmap, Bitmap bitmap2, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Paint paint = new Paint();
        paint.setAlpha((i * 255) / 100);
        if (Build.VERSION.SDK_INT > 10) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap applyMask(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        int width = bitmap2.getWidth();
        int height = bitmap2.getHeight();
        Bitmap copy = copy(bitmap);
        copy.setHasAlpha(true);
        if (i + width > copy.getWidth()) {
            width = copy.getWidth() - i;
        }
        if (i2 + height > copy.getHeight()) {
            height = copy.getHeight() - i2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(copy, i, i2, width, height);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        return createBitmap;
    }

    public static Bitmap copy(Bitmap bitmap) {
        return bitmap.isMutable() ? bitmap : bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
}
