package com.smdproject.smdproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements RecyclerView.OnItemTouchListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    private GestureDetector gestureDetector;
    private GestureDetector gestureDetector1;

    private MainActivity context;

    private AdView banner;

    public MapFragment(Context c) {
        context=(MainActivity)c;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);



        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_map, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(context);

        banner=(AdView)v.findViewById(R.id.adView3);
        AdRequest request=new AdRequest.Builder().build();
        banner.loadAd(request);


        HorizontalEventAdapter adapter=null;
        if(context.getCurrentGroup()!=null)
            adapter=new HorizontalEventAdapter(context.getCurrentGroup().getEvents(),R.layout.horizontal_row_layout,context);
        else
            adapter=new HorizontalEventAdapter(null,R.layout.horizontal_row_layout,context);
        RecyclerView rc = v.findViewById(R.id.eventHorizontal);

        LinearLayoutManager layoutManager=new LinearLayoutManager((Context)context);

        rc.setLayoutManager(layoutManager);
        rc.setItemAnimator(new DefaultItemAnimator());
        rc.setAdapter(adapter);


        final RecyclerView rv=v.findViewById(R.id.eventHorizontal);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {

                if(context.getCurrentGroup()==null)return true;
                if(context.mMap==null)return true;

                View child = rv.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(child != null){

                    //if tap was performed on some recyclerview row item
                    int i = rv.getChildAdapterPosition(child);	//index of item which was clicked

                    String loc=context.getCurrentGroup().getEvents().get(i).getLocation();
                    String[] locs=loc.split(",");
                    CameraPosition position=new CameraPosition.Builder()
                            .target(new LatLng(Double.parseDouble(locs[0]),Double.parseDouble(locs[1])))
                            .zoom(17).build();
                    context.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
        rc.addOnItemTouchListener(this);



        HorizontalUsersAdapter adapter1=null;
        if(context.getCurrentGroup()!=null)
            adapter1=new HorizontalUsersAdapter(context.getCurrentGroup().getMembers(),R.layout.horizontal_row_layout,context);
        else
            adapter1=new HorizontalUsersAdapter(null,R.layout.horizontal_row_layout,context);
        RecyclerView rc1 = v.findViewById(R.id.userHorizontal);

        LinearLayoutManager layoutManager1=new LinearLayoutManager((Context)context);

        rc1.setLayoutManager(layoutManager1);
        rc1.setItemAnimator(new DefaultItemAnimator());
        rc1.setAdapter(adapter1);


        final RecyclerView rv1=v.findViewById(R.id.eventHorizontal);
        gestureDetector1 = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {

                if(context.getCurrentGroup()==null)return true;
                if(context.mMap==null)return true;

                View child = rv1.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(child != null){

                    //if tap was performed on some recyclerview row item
                    int i = rv1.getChildAdapterPosition(child);	//index of item which was clicked

                    String loc=context.getCurrentGroup().getMembers().get(i).getLocation();
                    String[] locs=loc.split(",");
                    CameraPosition position=new CameraPosition.Builder()
                            .target(new LatLng(Double.parseDouble(locs[0]),Double.parseDouble(locs[1])))
                            .zoom(17).build();
                    context.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                }
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
        rc1.addOnItemTouchListener(this);



        return v;

    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if(rv==context.findViewById(R.id.eventHorizontal)) gestureDetector.onTouchEvent(e);
        if(rv==context.findViewById(R.id.userHorizontal)) gestureDetector1.onTouchEvent(e);

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
