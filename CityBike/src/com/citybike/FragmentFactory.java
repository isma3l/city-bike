package com.citybike;

import android.support.v4.app.Fragment;

public interface FragmentFactory {
	public Fragment create(String type);
}
