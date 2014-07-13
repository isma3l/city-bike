package com.citybike;

import com.citybike.utils.Definitions;

import android.support.v4.app.Fragment;

public class NavigationFragmentFactory implements FragmentFactory {

	@Override
	public Fragment create(String type) {
		if (type.equals(Definitions.home))
			return new FragmentMapa();
		if (type.equals(Definitions.profile))
			return new FragmentPerfil();
		if (type.equals(Definitions.event))
			return new FragmentEventos();
		if (type.equals(Definitions.contact))
			return new FragmentContactos();
		if (type.equals(Definitions.circuits))
			return new FragmentCircuitos();
		if (type.equals(Definitions.bePremium))
			return new FragmentPremium();
		return null;
	}

}
