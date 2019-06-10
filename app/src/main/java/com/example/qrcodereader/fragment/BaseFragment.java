package com.example.qrcodereader.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this , view);
        configTransitionAnimation();
    }

    private void configTransitionAnimation() {
        Transition fadeIn =
                new Fade(Fade.MODE_IN)
                        .setDuration(150);
        Transition fadeOut =
                new Fade(Fade.MODE_OUT)
                        .setDuration(150);
        setEnterTransition(fadeIn);
        setReenterTransition(fadeIn);
        setExitTransition(fadeOut);
        setReturnTransition(fadeOut);
//        setAllowEnterTransitionOverlap(true);
//        setAllowReturnTransitionOverlap(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
