package com.hs.photo.background.changer.editor.backgrounderaser.mytouch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MultiTouchListener3view implements View.OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    ImageView imageBack;
    ImageView imageCut;
    public boolean isRotateEnabled;
    public boolean isScaleEnabled;
    boolean isThree;
    public boolean isTranslateEnabled;
    private int mActivePointerId;
    private GestureDetector mGestureDetector;
    private float mPrevX;
    private float mPrevY;
    private ScaleGestureDetector mScaleGestureDetector;
    public float maximumScale;
    public float minimumScale;

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        @Override
        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        @Override
        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = MultiTouchListener3view.this.isScaleEnabled ? scaleGestureDetector.getScaleFactor() : 1.0f;
            float f = 0.0f;
            transformInfo.deltaAngle = MultiTouchListener3view.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouchListener3view.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (MultiTouchListener3view.this.isTranslateEnabled) {
                f = scaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener3view.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener3view.this.maximumScale;
            MultiTouchListener3view.this.move(view, transformInfo);
            MultiTouchListener3view multiTouchListener3view = MultiTouchListener3view.this;
            multiTouchListener3view.move(multiTouchListener3view.imageBack, transformInfo);
            MultiTouchListener3view multiTouchListener3view2 = MultiTouchListener3view.this;
            multiTouchListener3view2.move(multiTouchListener3view2.imageCut, transformInfo);
            return false;
        }
    }


    public class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;
    }

    public MultiTouchListener3view() {
        this.mActivePointerId = -1;
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.isThree = false;
        this.mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    }

    public MultiTouchListener3view(ImageView imageView, ImageView imageView2) {
        this.mActivePointerId = -1;
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.isThree = false;
        this.imageCut = imageView;
        this.imageBack = imageView2;
        this.mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
    }

    public void getIsThree(Boolean bool) {
        this.isThree = bool.booleanValue();
    }

    public MultiTouchListener3view(GestureDetector.SimpleOnGestureListener simpleOnGestureListener) {
        this.mActivePointerId = -1;
        this.isRotateEnabled = true;
        this.isTranslateEnabled = true;
        this.isScaleEnabled = true;
        this.minimumScale = 0.5f;
        this.maximumScale = 10.0f;
        this.isThree = false;
        this.mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
        this.mGestureDetector = new GestureDetector(simpleOnGestureListener);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        int i = 0;
        if (actionMasked == 0) {
            this.mPrevX = motionEvent.getX();
            this.mPrevY = motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (actionMasked == 1) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex != -1) {
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
                if (!this.mScaleGestureDetector.isInProgress()) {
                    adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                    adjustTranslation(this.imageBack, x - this.mPrevX, y - this.mPrevY);
                    adjustTranslation(this.imageCut, x - this.mPrevX, y - this.mPrevY);
                }
            }
        } else if (actionMasked == 3) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 6) {
            int i2 = (65280 & action) >> 8;
            if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                if (i2 == 0) {
                    i = 1;
                }
                this.mPrevX = motionEvent.getX(i);
                this.mPrevY = motionEvent.getY(i);
                this.mActivePointerId = motionEvent.getPointerId(i);
            }
        }
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        return true;
    }

    private void move(View view, TransformInfo transformInfo) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        view.setScaleX(max);
        view.setScaleY(max);
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[0] - fArr[0];
            float f4 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - f3);
            view.setTranslationY(view.getTranslationY() - f4);
        }
    }

    public void moveDefault(ImageView imageView) {
        imageView.setTranslationX(0.0f);
        imageView.setTranslationY(0.0f);
        imageView.setScaleX(1.0f);
        imageView.setScaleY(1.0f);
        imageView.setRotation(0.0f);
    }
}
