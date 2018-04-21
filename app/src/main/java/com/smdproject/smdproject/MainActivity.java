package com.smdproject.smdproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.Event;
import database.Group;
import database.Post;
import database.User;

public class MainActivity extends AppCompatActivity
        implements FeedFragment.OnFragmentInteractionListener,
                    EventFragment.OnFragmentInteractionListener,
                    MapFragment.OnFragmentInteractionListener,
                    ChatFragment.OnFragmentInteractionListener,
                    NavigationView.OnNavigationItemSelectedListener,
                    OnMapReadyCallback {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private GoogleMap mMap;

    private static int TAB_COUNT=4;
    private FirebaseAuth mAuth;

    public Group getCurrentGroup() {
        return currentGroup;
    }

    public void setCurrentGroup(Group currentGroup) {
        this.currentGroup = currentGroup;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private Group currentGroup=null;
    private User currentUser=null;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Uri postImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();//firebase
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-7909585213116372~6827984341");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }
    @Override
    public void onStart() {

        super.onStart();

        //do not update group here
        //make global variable
        if(currentUser==null)
            currentUser=new User(Uri.parse("res:///"+R.drawable.com_facebook_button_icon_blue),1,"Abdullah","Kamran");

        if(currentGroup==null) {
            currentGroup = new Group("Koders");
            currentGroup.getMembers().add(currentUser);
        }

        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            Intent auth=new Intent(this,LoginActivity.class);
//            startActivity(auth);
//            finish();
//        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }
        else if (id == R.id.nav_gallery) {

        }
        else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_manage) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri){}


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String tabNames[]=new String[]{"Squad Feed","Squad Events","Squad Map","Squad Chat"};
        private Context context;

        public SectionsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context=context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            Fragment f=null;
            if(position==0){
                return new FeedFragment(context);
            }
            else if(position==1){
                return new EventFragment(context);
            }
            else if(position==2){
                return new MapFragment(context);
            }
            else if(position==3){
                return new ChatFragment(context);
            }

            return null;
        }
        @Override
        public CharSequence getPageTitle(int position){
            return tabNames[position];
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }


    public void postStatus(View v){
        EditText statusText=(EditText)findViewById(R.id.postEditText);
        ImageView imageview=(ImageView)findViewById(R.id.feedAttachThumbnail);


        if(statusText.getText().toString().equalsIgnoreCase("") && imageview.getDrawable()==null){
            Toast t=Toast.makeText(this,"Status should have text or media to post.",Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        Post post=null;

        if(postImage==null)
            post=new Post(currentGroup,currentUser,statusText.getText().toString(), null, new Date());
        else
            post=new Post(currentGroup,currentUser,statusText.getText().toString(), postImage, new Date());



        currentGroup.getPosts().add(0,post);

        ((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter().notifyDataSetChanged();

        statusText.setText("");

        ((ImageView)findViewById(R.id.feedAttachThumbnail)).setImageDrawable(null);
        ((ImageView)findViewById(R.id.playsign)).setVisibility(ImageView.INVISIBLE);
        ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.GONE);
        postImage=null;

        InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

    public void addEvent(View v){
        startActivityForResult(new Intent(MainActivity.this,AddEvent.class),123);
    }

    public void attachStatus(View v){

        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i,0);

    }

    public void deleteAttachment(View v){

        ((ImageView)findViewById(R.id.feedAttachThumbnail)).setImageDrawable(null);
        ((ImageView)findViewById(R.id.playsign)).setVisibility(ImageView.INVISIBLE);
        ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.GONE);
        postImage=null;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data!=null && data.getData()!=null){
            Uri uri=data.getData();
            if(uri.toString().contains("image")){

                postImage=uri;

                ImageView imageview=(ImageView)findViewById(R.id.feedAttachThumbnail);
                ((ImageView)findViewById(R.id.playsign)).setVisibility(ImageView.INVISIBLE);

                Glide.with(this)
                        .load(uri)
                        .into(imageview);

                ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.VISIBLE);

            }

        }
        else if (requestCode==123 && data!=null && data.getExtras()!=null){
            super.onActivityResult(requestCode, resultCode, data);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date d=null;
            if(data.getExtras().getString("edate")!=null && data.getExtras().getString("etime")!=null){
                String s=data.getExtras().getString("edate")+" "+data.getExtras().getString("etime")+":00";
                try {
                    d = df.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Event e=new Event(currentGroup,data.getExtras().getString("ename"),data.getExtras().getString("edes"),d,null);
            currentGroup.getEvents().add(0,e);
            ((RecyclerView)findViewById(R.id.eventview)).getAdapter().notifyDataSetChanged();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        //savedInstanceState.putFloat("finalWeightage",finalWeightage);
        //savedInstanceState.putFloat("projectWeightage",projectWeightage);


        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        //finalWeightage=savedInstanceState.getFloat("finalWeightage");
        //projectWeightage=savedInstanceState.getFloat("projectWeightage");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for(int i=0;i<currentGroup.getMembers().size();i++) {
            if(currentGroup.getMembers().get(i).getLocation()!=null) {
                mMap.addMarker(new MarkerOptions().position
                        (currentGroup.getMembers().get(i).getLocation()).title(currentGroup.getMembers().get(i).getName() + " is here!!"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentGroup.getMembers().get(i).getLocation()));
            }
        }
    }

}