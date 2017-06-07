package jerry.jerrynews.weather.Ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jerry.jerrynews.R;
import jerry.jerrynews.weather.Data.Forecast;
import jerry.jerrynews.weather.Util.DensityUtils;

/**
 * Created by Administrator on 2017/6/3.
 */

public class WeatherLineChart extends View{


    private int mMargin;  //  10dp的间距
    private int mWidth; //  控件宽度
    private int mHeight;  //  控件高度
    private int max, min;  //  最大值、最小值
    private float yInterval;  //  y轴坐标间隔
    private float xInterval;  //  x轴坐标间隔


    private int mLineColor;  //  折线颜色
    private float mLineWidth; //  折线宽度
    private int mAxesColor; //  坐标轴颜色
    private float mAxesWidth; //  坐标轴宽度
    private float mCircleRadius;    //坐标点半径
    private int mCircleColor;  //  坐标点颜色
    private int mShaderColor;  //   阴影颜色
    private float mTmpTextSize;  // 文字大小
    private int mTextColor;

    private Paint mLinePaint;   //折线画笔
    private Path mLinePath;     //折线路径
    private Paint mAxesPaint;   //分隔线画笔
    private Paint mCirclePaint; //坐标点画笔
    private Paint mPaintShader; //阴影画笔
    private TextPaint mTextPaint;   //文字画笔
    private List<Forecast> mForecastList;

    public void setWeather(List<Forecast> forecastList) {
        mForecastList=forecastList;
        invalidate();
    }


    //实现3个构造函数
    public WeatherLineChart(Context context) {
        super(context);
    }

    public WeatherLineChart(Context context, AttributeSet attrs){
        super(context, attrs);
        initStyle(context, attrs);
        mMargin = DensityUtils.dp2px(context, 20);
        mForecastList = new ArrayList<>();
        initPaint();
    }

    public WeatherLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePath = new Path();

        mAxesPaint = new Paint();
        mAxesPaint.setStrokeWidth(mAxesWidth);
        mAxesPaint.setColor(mAxesColor);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);

        mPaintShader = new Paint();
        mPaintShader.setColor(mShaderColor);
        mPaintShader.setAntiAlias(true);
        mPaintShader.setStrokeWidth(2f);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTmpTextSize);
        mTextPaint.setColor(Color.BLACK);
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherTrendGraph);
        mLineColor = typedArray.getColor(R.styleable.WeatherTrendGraph_line_color, Color.GRAY);
        mLineWidth = typedArray.getDimensionPixelSize(R.styleable.WeatherTrendGraph_line_width, 5);
        mAxesColor = typedArray.getColor(R.styleable.WeatherTrendGraph_axes_color, Color.BLACK);
        mAxesWidth = typedArray.getDimensionPixelSize(R.styleable.WeatherTrendGraph_axes_width, 1);
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.WeatherTrendGraph_circle_radius, 12);
        mCircleColor = typedArray.getColor(R.styleable.WeatherTrendGraph_circle_color, Color.GRAY);
        mShaderColor = typedArray.getColor(R.styleable.WeatherTrendGraph_shadow_color, Color.GREEN);
        mTmpTextSize = typedArray.getDimension(R.styleable.WeatherTrendGraph_tmp_text_size, 45);
        mTextColor=typedArray.getColor(R.styleable.WeatherTrendGraph_text_color,Color.WHITE);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight() - mMargin * 2 - DensityUtils.sp2px(getContext(), mTmpTextSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mForecastList == null || mForecastList.size() == 0) {
            return;
        }
        max = getMaxTmp();
        min = getMinTmp();
        float ratio = (float) mHeight / (max - min);
        xInterval = (float) mWidth / (mForecastList.size());
        float X, Y;
        float Y1;
        String tmp;
        //记录高温折线路径
        for (int i = 0; i < mForecastList.size() ; i++) {
            tmp = mForecastList.get(i).temperature.max + "°";
            X = (i ) * xInterval + xInterval / 2;
            Y = (max - Integer.parseInt(mForecastList.get(i).temperature.max)) * ratio + mMargin + DensityUtils.sp2px(getContext(), mTmpTextSize);
            if (i == 0) {
                mLinePath.moveTo(X, Y);
            }
            mLinePath.quadTo(X,Y,X,Y);
            //绘制每一个温度点
            canvas.drawCircle(X, Y, mCircleRadius, mCirclePaint);
            //绘制分隔线
            canvas.drawLine(X + xInterval / 2, 0,
                    X + xInterval / 2, getHeight(),
                    mAxesPaint);
            //绘制温度
            canvas.drawText(tmp, X - mTextPaint.measureText(tmp) / 2, Y - 2 * mCircleRadius, mTextPaint);
            //绘制时间
            canvas.drawText(mForecastList.get(i).date,
                    X - mTextPaint.measureText(mForecastList.get(i).date) / 2,
                    DensityUtils.sp2px(getContext(), mTmpTextSize),
                    mTextPaint);
            //绘制天气所对应的图片
            String weatherCode = "weather_" + mForecastList.get(i).more.code;
            Bitmap  bitmap=BitmapFactory.decodeResource(getContext().getResources(),R.drawable.weather_100);
            int resId = getResources().getIdentifier(weatherCode, "drawable", getContext().getPackageName());
            if (resId != 0) {
                bitmap = BitmapFactory.decodeResource(getContext().getResources(),resId);
            }
            canvas.drawBitmap(bitmap, X - bitmap.getWidth() / 2, 0, mCirclePaint);
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        //记录低温折线路径
        for (int i = mForecastList.size()-1; i >=0; i--) {
            tmp = mForecastList.get(i).temperature.min + "°";
            X = (i ) * xInterval + xInterval / 2;
            Y = (max - Integer.parseInt(mForecastList.get(i).temperature.min)) * ratio + mMargin + DensityUtils.sp2px(getContext(), mTmpTextSize);
            if (i == mForecastList.size()-1) {
                mLinePath.moveTo(X, Y);
            }
            mLinePath.quadTo(X,Y,X,Y);
            canvas.drawCircle(X, Y, mCircleRadius, mCirclePaint);
            canvas.drawText(tmp,
                    X - mTextPaint.measureText(tmp) / 2,
                    Y + 4 * mCircleRadius,
                    mTextPaint);
        }

        canvas.drawPath(mLinePath, mLinePaint);

    }

    private int getMaxTmp() {
        int max = Integer.parseInt(mForecastList.get(0).temperature.max);
        for (int i = 0; i < mForecastList.size()-1; i++) {
            if (max < Integer.parseInt(mForecastList.get(i).temperature.max)) {
                max = Integer.parseInt(mForecastList.get(i).temperature.max);
            }
        }
        return max;
    }

    private int getMinTmp() {
        int min = Integer.parseInt(mForecastList.get(0).temperature.min);
        for (int i = 0; i < mForecastList.size()-1; i++) {
            if (min > Integer.parseInt(mForecastList.get(i).temperature.min)) {
                min = Integer.parseInt(mForecastList.get(i).temperature.min);
            }
        }
        return min;
    }


}
