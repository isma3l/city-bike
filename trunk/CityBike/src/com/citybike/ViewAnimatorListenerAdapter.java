package com.citybike;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;


public class ViewAnimatorListenerAdapter extends AnimatorListenerAdapter {
	View view;
	final boolean show;
	public ViewAnimatorListenerAdapter(View view,final boolean show) {
		super();
		this.view = view;
		this.show=show;
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		view.setVisibility(show ? View.GONE: View.VISIBLE);
	}

}
