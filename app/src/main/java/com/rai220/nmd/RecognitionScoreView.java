/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.rai220.nmd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

public class RecognitionScoreView extends View implements ResultsView {
  private static final float TEXT_SIZE_DIP = 24;
  private List<Classifier.Recognition> results;
  private final float textSizePx;
  private final Paint fgPaint;
  private final Paint bgPaint;
  private Context context;
  private TextToSpeech tts;

  public RecognitionScoreView(final Context context, final AttributeSet set) {
    super(context, set);
    this.context = context;

    textSizePx =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
    fgPaint = new Paint();
    fgPaint.setTextSize(textSizePx);

    bgPaint = new Paint();
    bgPaint.setColor(0xcc4285f4);

    tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int i) {
        // Do nothing
      }
    });
  }

  private volatile long lastSpeak = 0;

  @Override
  public void setResults(final List<Classifier.Recognition> results) {
    if (results != null) {
      for (Classifier.Recognition rec : results) {
        if (rec.getConfidence() > 0.95) {
          String text = null;
          if (rec.getTitle().equals("rub50")) {
            text = "Пятьдесят рублей";
          } else if (rec.getTitle().equals("rub100")) {
            text = "Сто рублей";
          } else if (rec.getTitle().equals("rub500")) {
            text = "Пятьсот рублей";
          } else if (rec.getTitle().equals("rub1000")) {
            text = "Тысяча рублей";
          } else if (rec.getTitle().equals("rub5000")) {
            text = "Пять тысяч рублей";
          }
          if (System.currentTimeMillis() - lastSpeak > 3000 && text != null) {
            lastSpeak = System.currentTimeMillis();
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
          }
        }
      }
    }

    //this.results = results;
    postInvalidate();
  }

  @Override
  public void onDraw(final Canvas canvas) {
    /*
    final int x = 10;
    int y = (int) (fgPaint.getTextSize() * 1.5f);

    canvas.drawPaint(bgPaint);

    if (results != null) {
      for (final Classifier.Recognition recog : results) {
        canvas.drawText(recog.getTitle() + ": " + recog.getConfidence(), x, y, fgPaint);
        y += fgPaint.getTextSize() * 1.5f;
      }
    } */
  }
}
