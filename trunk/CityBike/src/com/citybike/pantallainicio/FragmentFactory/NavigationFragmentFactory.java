package com.citybike.pantallainicio.FragmentFactory;

import com.citybike.pantallainicio.Fragments.FragmentCircuitos;
import com.citybike.pantallainicio.Fragments.FragmentContactos;
import com.citybike.pantallainicio.Fragments.FragmentEventos;
import com.citybike.pantallainicio.Fragments.FragmentHomeMap;
import com.citybike.pantallainicio.Fragments.FragmentPerfil;
import com.citybike.pantallainicio.Fragments.FragmentPremium;
import com.citybike.utils.Definitions;

import android.support.v4.app.Fragment;

public class NavigationFragmentFactory implements FragmentFactory {

	@Override
	public Fragment create(String type) {
		if (type.equals(Definitions.home))
			return FragmentHomeMap.newInstance(null);
		if (type.equals(Definitions.profile))
			return FragmentPerfil.newInstance(null	);
		if (type.equals(Definitions.event))
			return FragmentEventos.newInstance(null);
		if (type.equals(Definitions.contact))
			return FragmentContactos.newInstance(null);
		if (type.equals(Definitions.circuits))
			return FragmentCircuitos.newInstance(null);
		if (type.equals(Definitions.bePremium))
			return FragmentPremium.newInstance(null);
		return null;
	}

}
