package com.demo.zbj.mycode.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.demo.zbj.mycode.R;
import com.demo.zbj.mycode.custom.view.CameraView;
import com.demo.zbj.mycode.custom.view.ColorTextView;
import com.demo.zbj.mycode.custom.view.FlipCameraView;
import com.demo.zbj.mycode.custom.view.MaterialEditText;
import com.demo.zbj.mycode.custom.view.TagLayout;

/**
 * @author BiJim
 * Created by BiJim on 2018/10/22.
 */

public class TestDrawActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_draw);
        //=========CameraView test=========
        /*CameraView camera = findViewById(R.id.cameraView);
        camera.startAnimator();*/

        //=========FlipCameraView test=========
        /*FlipCameraView flipView = findViewById(R.id.flipCameraView);

        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("flipBottom", 45);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("canvasRotateDegree", 270);
        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("flipTop", - 45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(flipView, bottomFlipHolder, flipRotationHolder, topFlipHolder);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(5000);
        objectAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator flipBottom = ObjectAnimator.ofFloat(flipView, "flipBottom", 45);
        flipBottom.setDuration(1500);
        ObjectAnimator canvasRotateDegree = ObjectAnimator.ofFloat(flipView, "canvasRotateDegree", 270);
        canvasRotateDegree.setDuration(3000);
        ObjectAnimator flipTop = ObjectAnimator.ofFloat(flipView, "flipTop", -45);
        flipTop.setDuration(1500);
        animatorSet.playSequentially(flipBottom,canvasRotateDegree,flipTop);
        animatorSet.setStartDelay(2000);

        flipView.setOnClickListener(v -> {
            System.out.println("flipView click");
            animatorSet.start();
        });*/


        //=========MaterialEditText test=========
       /* MaterialEditText text = findViewById(R.id.text_input_username);
        RadioGroup choice = findViewById(R.id.choice_group);
        choice.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == group.getChildAt(0).getId()) {
                text.setFloatLabel(true);
            } else {
                text.setFloatLabel(false);
            }
        });*/


       //=========TagLayout test=========
       /* TagLayout tagLayout = findViewById(R.id.tagLayout);
        String[] songs = getResources().getStringArray(R.array.songs);
        for (int i = 0; i < songs.length; i++) {
            ColorTextView colorTextView = new ColorTextView(this);
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            colorTextView.setLayoutParams(layoutParams);
            colorTextView.setText(songs[i]);
            tagLayout.addView(colorTextView);
        }*/

    }
}
