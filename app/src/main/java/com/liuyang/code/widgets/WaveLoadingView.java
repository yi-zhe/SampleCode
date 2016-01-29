package com.liuyang.code.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.liuyang.code.R;

public class WaveLoadingView extends View {
    private final Paint mWavePaint;
    private Paint mPaint;
    private Paint mTextPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int y;
    private int x;

    private PorterDuffXfermode mMode;
    private Path mPath;
    private boolean isLeft;
    private int mWidth;
    private int mHeight;
    private int mPercent;
    private String mText = "";

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveLoadingView);
        int bgColor = a.getColor(R.styleable.WaveLoadingView_bg_color, Color.WHITE);
        int waveColor = a.getColor(R.styleable.WaveLoadingView_wave_color, Color.WHITE);
        float textSize = a.getDimension(R.styleable.WaveLoadingView_text_size, 0);
        int textColor = a.getColor(R.styleable.WaveLoadingView_text_color, Color.WHITE);
        a.recycle();
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mPaint = new Paint();

        mPaint.setStrokeWidth(10);
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(bgColor);

        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mWavePaint.setColor(waveColor);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        y = mHeight;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (x > mWidth / 2) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }
        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        y = (int) ((1 - mPercent / 100f) * mHeight);
        mPath.moveTo(0, y);
        mPath.cubicTo(100 + x * 2, 50 + y, 100 + x * 2, y - 50, mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        mBitmap.eraseColor(Color.TRANSPARENT);
        mCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mWavePaint);
        mPaint.setXfermode(mMode);
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawText(mText, mWidth / 2 - mTextPaint.measureText(mText) / 2, mHeight / 2 + 15, mTextPaint);
        postInvalidateDelayed(30);
    }

    public void setPercent(int percent) {
        mPercent = percent;
    }

    public void setText(String text) {
        mText = text;
    }
}