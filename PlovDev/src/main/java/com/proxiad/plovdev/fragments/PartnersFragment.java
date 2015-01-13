package com.proxiad.plovdev.fragments;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import com.proxiad.plovdev.R;
import com.proxiad.plovdev.adapters.PartnerAdapter;
import com.proxiad.plovdev.utils.parsers.DataParser;

public class PartnersFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_partners, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PartnerAdapter adapter = new PartnerAdapter(getActivity(), DataParser.getPartners());
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!this.isHidden()) {
            getActivity().getActionBar().setTitle(R.string.partners);
        }
    }
}
