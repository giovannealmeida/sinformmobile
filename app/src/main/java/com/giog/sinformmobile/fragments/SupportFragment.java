package com.giog.sinformmobile.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giog.sinformmobile.R;

/**
 * Subcalsse de {@link android.support.v4.app.Fragment}.
 * <br/>
 * Use o método {@link com.giog.sinformmobile.fragments.SupportFragment#newInstance(int)} para
 * criar uma nova instância desse fragmento.
 * <br/>
 * Exemplo:
 * Fragment newFragment = HomeFragment.newInstance(sectionNumber);
 */
public class SupportFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Número da posição do item selecionado no Drawer.
     * @return A new instance of fragment HomeFragment.
     */

    public static SupportFragment newInstance(int sectionNumber) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        args.putInt("section_number", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
