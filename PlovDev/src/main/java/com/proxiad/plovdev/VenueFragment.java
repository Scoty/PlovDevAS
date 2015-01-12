package com.proxiad.plovdev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class VenueFragment extends Fragment {

	private final double PLOVDEV_LAT = 42.1564311d;
	private final double PLOVDEV_LNG = 24.7546284d;
	private final LatLng PLOVDEV_LOC = new LatLng(PLOVDEV_LAT, PLOVDEV_LNG);
	private GoogleMap map;
	private SupportMapFragment mMapFragment;
	private CameraPosition cameraPosition;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_venue, container, false);
		if (savedInstanceState == null) {
			mMapFragment = SupportMapFragment.newInstance();
			mMapFragment.setRetainInstance(true);
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			transaction.add(R.id.map_container, mMapFragment).commit();
		} else {
			mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
			FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
			transaction.show(mMapFragment).commit();
			this.cameraPosition = savedInstanceState.getParcelable("cameraPosition");
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		map = mMapFragment.getMap();
		if (map != null) {
			String titleLocation = getString(R.string.title_location);
			String snippet = getString(R.string.snippet);
			map.addMarker(new MarkerOptions().position(PLOVDEV_LOC).title(titleLocation).snippet(snippet)).showInfoWindow();
			map.setMyLocationEnabled(true);
			map.setOnMarkerClickListener(new OnMarkerClickListener() {

				@Override
				public boolean onMarkerClick(Marker arg0) {
					if (map.getMyLocation() != null) {
						double myLat = map.getMyLocation().getLatitude();
						double myLon = map.getMyLocation().getLongitude();
						String url = "http://maps.google.com/maps?saddr=" + myLat + "," + myLon + "&daddr=" + PLOVDEV_LAT + "," + PLOVDEV_LNG;
						Intent startNavigation = new Intent(Intent.ACTION_VIEW);
						startNavigation.setData(Uri.parse(url));
						startActivity(startNavigation);
					}
					return false;
				}
			});
			if (cameraPosition == null) {
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(PLOVDEV_LOC, 15));
			} else {
				map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!this.isHidden()) {
			getActivity().getActionBar().setTitle(R.string.venue);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		cameraPosition = map.getCameraPosition();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("cameraPosition", cameraPosition);
	}

}
