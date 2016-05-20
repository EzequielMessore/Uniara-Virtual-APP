package br.com.uniaravirtual.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public final class AnimationUtil {

    public static void fadeIn(final View view) {
        view.animate()
                .translationY(0)
                .setDuration(350)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        super.onAnimationEnd(animation);
                    }
                });
    }

    public static void fadeOut(final View view) {
        view.animate()
                .translationY(0)
                .setDuration(350)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                });
    }
}
