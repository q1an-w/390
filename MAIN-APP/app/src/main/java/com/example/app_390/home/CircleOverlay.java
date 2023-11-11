package com.example.app_390.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.LinearGradient;
import android.util.AttributeSet;
import android.view.View;

public class CircleOverlay extends View {
    private Paint mTransparentPaint;
    private Paint mSemiBlackPaint;
    private Path mPath = new Path();

    public CircleOverlay(Context context) {
        super(context);
        initPaints();
    }

    public CircleOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public CircleOverlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaints();
    }

    private void initPaints() {
        mTransparentPaint = new Paint();
        mTransparentPaint.setColor(Color.TRANSPARENT);
        mTransparentPaint.setStrokeWidth(10);

        // Create the gradient shader
        int startColor = Color.parseColor("#6C1DAB");
        int endColor = Color.parseColor("#2C0C84");
        Shader shader = new LinearGradient(0, 0, 0, 820, startColor, endColor, Shader.TileMode.CLAMP);

        // Apply the gradient shader to the paint
        mSemiBlackPaint = new Paint();
        mSemiBlackPaint.setShader(shader);
        mSemiBlackPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();

        mPath.addCircle(canvas.getWidth() / 2f, canvas.getHeight() / 2f, 420, Path.Direction.CW);
        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);

        canvas.drawCircle(canvas.getWidth() / 2f, canvas.getHeight() / 2f, 300, mTransparentPaint);

        canvas.drawPath(mPath, mSemiBlackPaint);
        canvas.clipPath(mPath);
        canvas.drawColor(Color.TRANSPARENT); // This can be transparent if needed
    }
}
