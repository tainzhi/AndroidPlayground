package com.tainzhi.sample.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by muqing on 2019/7/7.
 * Email: qfq61@qq.com
 */
public class CirclePercentView extends View {
	private float mRadius;
	private float mStripeWidth;
	private int mHeight;
	private int mWidth;
	
	private int mCurrentPercent;
	private int mPercent;
	
	private float centerX;
	private float centerY;
	
	private int mEndAngle;
	
	private int mSmallColor;
	private int mBigColor;
	
	private float mCenterTextSize;
	
	public CirclePercentView(Context context) { this(context, null); }
	
	public CirclePercentView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CirclePercentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CirclePercentView, defStyleAttr, 0);
		mStripeWidth = typedArray.getDimension(R.styleable.CirclePercentView_stripWidth,
				30);
		mCurrentPercent = typedArray.getInteger(R.styleable.CirclePercentView_percent,
				0);
        mSmallColor = typedArray.getColor(R.styleable.CirclePercentView_smallColor,
		        0xffafb4db);
        mBigColor = typedArray.getColor(R.styleable.CirclePercentView_bigColor,
		        0xff6950a1);
        mCenterTextSize =
		        typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_centerTextSize,
				        50);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentView_radius,
		        100);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
			mRadius = widthSize /2;
			centerX = widthSize / 2;
			centerY = heightSize / 2;
			mWidth = widthSize;
			mHeight = heightSize;
		}
		
		if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
			mWidth = (int) (mRadius * 2);
			mHeight = (int) (mRadius * 2);
			centerX = mRadius;
			centerY = mRadius;
		}
		setMeasuredDimension(mWidth, mHeight);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
        mEndAngle = (int) (mCurrentPercent * 3.6);
        Paint bigCirclepaint = new Paint();
        bigCirclepaint.setAntiAlias(true);
        bigCirclepaint.setColor(mBigColor);
        canvas.drawCircle(centerX, centerY, mRadius, bigCirclepaint);
        
        Paint sectorPaint = new Paint();
        sectorPaint.setColor(mSmallColor);
        sectorPaint.setAntiAlias(true);
        RectF rect = new RectF(0, 0, mWidth, mHeight);
        canvas.drawArc(rect, 270, mEndAngle, true, sectorPaint);
        
        Paint smallCirclePaint = new Paint();
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setColor(mBigColor);
        canvas.drawCircle(centerX, centerY, mRadius - mStripeWidth, smallCirclePaint);
        
        Paint textPaint = new Paint();
        String text = mCurrentPercent + "%";
        textPaint.setTextSize(mCenterTextSize);
        float textLength = textPaint.measureText(text);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(text, centerX - textLength/2, centerY, textPaint);
	}
	
	public void setPercent(int percent) {
		if (percent > 100) {
			throw new IllegalArgumentException("percent must less than 100!");
		}
        setCurrentPercent(percent);
	}
	
	private void setCurrentPercent(int percent) {
		mPercent = percent;
		
		new Thread(() -> {
            int sleepTime = 1;
            for (int i = 0; i < mPercent; i++) {
            	if (i%20 == 0) {
            		sleepTime += 2;
	            }
            	try {
            		Thread.sleep(sleepTime);
	            } catch (InterruptedException e) {
            		e.printStackTrace();
	            }
            	mCurrentPercent = i;
            	CirclePercentView.this.postInvalidate();
            }
		}).start();
	}
}
