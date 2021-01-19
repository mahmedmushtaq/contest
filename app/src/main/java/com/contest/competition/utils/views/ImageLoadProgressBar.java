package com.contest.competition.utils.views;

/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.facebook.drawee.drawable.ProgressBarDrawable;

public class ImageLoadProgressBar extends ProgressBarDrawable {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mLevel = 0;
    private int maxLevel = 10000;
    private int color = getColor();

    public ImageLoadProgressBar(int color) {
        this.color = color;
    }

    public ImageLoadProgressBar() {
    }

    @Override
    protected boolean onLevelChange(int level) {
        mLevel = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (getHideWhenZero() && mLevel == 0) {
            return;
        }
        drawBar(canvas, maxLevel, getBackgroundColor());
        drawBar(canvas, mLevel, this.color);
    }

    private void drawBar(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        RectF rectF =
                new RectF(
                        (float) (bounds.right * .4),
                        (float) (bounds.bottom * .4),
                        (float) (bounds.right * .6),
                        (float) (bounds.bottom * .6));
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        if (level != 0) canvas.drawArc(rectF, 0, (float) (level * 360 / maxLevel), false, mPaint);
    }
}
