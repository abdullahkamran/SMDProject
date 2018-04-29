package com.smdproject.smdproject;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.*;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import database.Event;
import database.Group;
import database.Member;
import database.Message;
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

    public GoogleMap mMap;
    ArrayList<Marker> markers=new ArrayList<>();

    private static int TAB_COUNT=4;
    private FirebaseAuth mAuth;


    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;
    private ArrayList<Group> joined;


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

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private DatabaseReference mdb;
    private DatabaseReference mdbu;
    private DatabaseReference mdbg;
    private DatabaseReference meventc;
    private DatabaseReference mpostc;
    private DatabaseReference mevent;
    private DatabaseReference mpost;

    TTSManager ttsManager = null;


    public void setNav(){
        
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        View v=navigationView.getHeaderView(0);

        if(currentGroup.getGroupPic()!=null)
            ((ImageView)v.findViewById(R.id.groupPicOnNav)).setImageURI(Uri.parse(currentGroup.getGroupPic()));
        ((TextView)v.findViewById(R.id.groupNameOnNav)).setText(currentGroup.getName());
        ((TextView)v.findViewById(R.id.groupidOnNav)).setText("Group ID:"+currentGroup.getGroupId());

        if(currentUser.dp!=null)
            ((ImageView)v.findViewById(R.id.dpOnNav)).setImageURI(Uri.parse(currentUser.dp));
        String name=currentUser.getName();

        if(currentGroup.getNicknames().containsKey(currentUser.getUid()))
            name=name+" @"+currentGroup.getNicknames().get(currentUser.getUid());

        ((TextView)v.findViewById(R.id.userNameOnNav)).setText(name);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        ttsManager.shutDown();

    }


    @Override
    protected void onPause() {
        super.onPause();

        saveCurrent();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        joined=new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();//firebase
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase=LoginActivity.getDatabase().getReference();
        mStorage=LoginActivity.getStorage().getReference();
        mevent = LoginActivity.getDatabase().getReference("Events");
        mevent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(currentGroup != null && currentGroup.getEvents().isEmpty()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Event e=ds.getValue(Event.class);
                        if(e.getGid().equals(currentGroup.getGroupId())) {
                            currentGroup.getEvents().add(0, e);
                            if(((RecyclerView)findViewById(R.id.eventview)).getAdapter()!=null)
                                ((RecyclerView)findViewById(R.id.eventview)).getAdapter().notifyDataSetChanged();

                            if(((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter()!=null)
                                ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();
                        }
                    }
                }
                else {
                    Event e = dataSnapshot.getValue(Event.class);
                    if (currentGroup != null && e.getEid()!=null) {
                        for (int i = 0; i < currentGroup.getEvents().size(); i++)
                            if (!currentGroup.getEvents().get(i).getEid().equals(e.getEid())  && currentGroup.getGroupId().equals(e.getEid())) {
                                currentGroup.getEvents().add(e);
                                ((RecyclerView)findViewById(R.id.eventview)).getAdapter().notifyDataSetChanged();
                                ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();
                                break;
                            }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });

        mpost = LoginActivity.getDatabase().getReference("Posts");
        mpost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(currentGroup!=null && currentGroup.getPosts().isEmpty()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Post e=ds.getValue(Post.class);
                        if(e.getGid().equals(currentGroup.getGroupId())) {
                            currentGroup.getPosts().add(0, e);
                            if(((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter()!=null)
                                ((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter().notifyDataSetChanged();
                        }
                    }
                }
                else {
                    Post e = dataSnapshot.getValue(Post.class);
                    if (currentGroup != null && e.getPid()!=null) {
                        for (int i = 0; i < currentGroup.getPosts().size(); i++)
                            if (!currentGroup.getPosts().get(i).getPid().equals(e.getPid()) && e.getGid().equals(currentGroup.getGroupId())) {
                                currentGroup.getPosts().add(e);
                                ((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter().notifyDataSetChanged();
                                break;
                            }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });





        mdbu = LoginActivity.getDatabase().getReference("amembers");
        mdbu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(currentGroup!=null && !currentGroup.getMembers().isEmpty()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Member e=ds.getValue(Member.class);
                        if(e.getGid().equals(currentGroup.getGroupId())) {
                            for(int i=0;i<currentGroup.getMembers().size();i++){
                                if(currentGroup.getMembers().get(i).getUid()!=e.getUser().getUid()){
                                    currentGroup.getMembers().add(0, e.getUser());
                                    if(((RecyclerView)findViewById(R.id.eventHorizontal))!=null)
                                        ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
                else {
                    Member e = dataSnapshot.getValue(Member.class);
                    if (currentGroup != null && e!=null && e.getGid()!=null) {
                        for (int i = 0; i < currentGroup.getMembers().size(); i++)
                            if (!currentGroup.getMembers().get(i).getUid().equals(e.getUser().getUid()) && e.getGid().equals(currentGroup.getGroupId())) {
                                currentGroup.getMembers().add(e.getUser());
                                ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();
                                break;
                            }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });

        mdb=LoginActivity.getDatabase().getReference("currentGroup");
        mdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (currentGroup != null) {
                    if (currentGroup.getGroupId() == null) {
                        currentGroup.setGroupId(dataSnapshot.getKey());
                        mDatabase.child("currentGroup").child(currentGroup.getGroupId()).child("groupId").setValue(currentGroup.getGroupId());

                        Member m=new Member(currentUser,currentGroup.getGroupId());
                        mDatabase.child("amembers").push().setValue(m);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mdbg = LoginActivity.getDatabase().getReference("currentGroup");
        mdbg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(currentGroup!=null){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Group e=ds.getValue(Group.class);
                        if(e.getGroupId().equals(currentGroup.getGroupId())) {
                            currentGroup.setName(e.getName());
                            currentGroup.setAdmin(e.getAdmin());
                            currentGroup.setGroupPic(e.getGroupPic());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Database", "Failed to read value.", error.toException());
            }
        });

        meventc=LoginActivity.getDatabase().getReference("Events");
        meventc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(currentGroup!=null && !currentGroup.getEvents().isEmpty()) {
                    if (dataSnapshot.child("gid").equals(currentGroup.getGroupId())) {
                        currentGroup.getEvents().get(0).setEid(dataSnapshot.getKey());
                        mDatabase.child("Events").child(dataSnapshot.getKey()).child("eid").setValue(dataSnapshot.getKey());

                        ((RecyclerView)findViewById(R.id.eventview)).getAdapter().notifyDataSetChanged();
                        ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mpostc=LoginActivity.getDatabase().getReference("Posts");
        mpostc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(currentGroup!=null && !currentGroup.getPosts().isEmpty()) {
                    if (currentGroup.getGroupId().equals(dataSnapshot.child("gid"))) {
                        currentGroup.getPosts().get(0).setPid(dataSnapshot.getKey());
                        mDatabase.child("Posts").child(dataSnapshot.getKey()).child("pid").setValue(dataSnapshot.getKey());

                        ((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter().notifyDataSetChanged();

                        if(postImage!=null) {
                            currentGroup.getPosts().get(0).setImage("Images/" + currentUser.getUid() + "," + currentGroup.getPosts().get(0).getPid());
                            StorageReference mSref = mStorage.child("Images/" + currentUser.getUid() + "," + currentGroup.getPosts().get(0).getPid());
                            mSref.putFile(postImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Image Upload Failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(getIntent()!=null && getIntent().getExtras()!=null) {
            currentUser = (User)getIntent().getSerializableExtra("user");
            if(currentUser!=null)
                mDatabase.child("currentUser").child(currentUser.getUid()).setValue(currentUser);
            currentGroup= (Group)getIntent().getSerializableExtra("group");
            if(currentGroup!=null && currentGroup.getGroupId()==null)
                mDatabase.child("currentGroup").push().setValue(currentGroup);
            if(currentUser!=null && currentGroup!=null && currentGroup.getGroupId()!=null) {

                Member m=new Member(currentUser,currentGroup.getGroupId());

                mDatabase.child("amembers").push().setValue(m);

            }
        }

        MobileAds.initialize(this, "ca-app-pub-7909585213116372~6827984341");

        ttsManager = new TTSManager();
        ttsManager.init(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mViewPager.setOffscreenPageLimit(3);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(currentGroup==null && currentUser==null)
            retrieveCurrent();

    }

    private void retrieveCurrent() {
        SharedPreferences mPrefs = getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user = mPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(user, User.class);

        currentGroup=new Group();
        currentGroup.setGroupId(mPrefs.getString("currentGroupId",""));
        if(mPrefs.getString("currentGroupPic","").equals(""))
            currentGroup.setGroupPic(null);
        else
            currentGroup.setGroupPic(mPrefs.getString("currentGroupPic",""));
        currentGroup.setName(mPrefs.getString("currentGroupName",""));
        currentGroup.setAdmin(mPrefs.getString("currentGroupAdmin",""));

        currentGroup.setPosts(new ArrayList<Post>());
        currentGroup.setNicknames(new HashMap<Integer, String>());
        currentGroup.setEvents(new ArrayList<Event>());
        currentGroup.setMessages(new ArrayList<Message>());
        currentGroup.setMembers(new ArrayList<User>());

//        String hash=mPrefs.getString("hashmap","");
//        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
//        HashMap<Integer,String> testHashMap2 = gson.fromJson(hash, type);
//        Type t=new TypeToken<ArrayList<Event>>(){}.getType();
//        currentGroup.setEvents((ArrayList<Event>) gson.fromJson(mPrefs.getString("currentGroupEvents",""), t));
//        currentGroup.setMembers(gson.fromJson(mPrefs.getString("currentGroupMembers",""), ArrayList.class));
//        currentGroup.setMessages(gson.fromJson(mPrefs.getString("currentGroupMessages",""), ArrayList.class));
//        currentGroup.setPosts(gson.fromJson(mPrefs.getString("currentGroupPosts",""), ArrayList.class));
//
//        currentGroup.setNicknames(testHashMap2);

        if(mPrefs.getString("currentGroupId","").equals(""))
            currentGroup=null;
    }

    private void saveCurrent() {
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String user = gson.toJson(currentUser);
        //String group = gson.toJson(currentGroup);

        prefsEditor.putString("currentUser", user);
        prefsEditor.putString("currentGroupId", currentGroup.getGroupId());
        prefsEditor.putString("currentGroupName", currentGroup.getName());
        prefsEditor.putString("currentGroupAdmin", currentGroup.getAdmin());
        if(currentGroup.getGroupPic()!=null)
            prefsEditor.putString("currentGroupPic", currentGroup.getGroupPic().toString());
        else
            prefsEditor.putString("currentGroupPic", "");

//        List<Event>e=currentGroup.getEvents();
//        String events=gson.toJson(e);
//        prefsEditor.putString("currentGroupEvents",events);
//        prefsEditor.putString("currentGroupMembers",gson.toJson(currentGroup.getMembers()));
//        prefsEditor.putString("currentGroupMessages",gson.toJson(currentGroup.getMessages()));
//        prefsEditor.putString("currentGroupPosts",gson.toJson(currentGroup.getPosts()));
//
//        String hashMapString = gson.toJson(currentGroup.getNicknames());
//        prefsEditor.putString("hashmap",hashMapString);

        prefsEditor.apply();
    }

     public void sendMyLocation(){

         if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                if (currentUser != null) {
                    currentUser.setLocation(location.getLatitude()+","+location.getLongitude());
                    String s = currentUser.getLocation();
                    mDatabase.child("currentUser").child(currentUser.getUid()).child("location").setValue(s);
                }
            }
        }
    }




    @Override
    public void onStart() {

        super.onStart();


       // saveCurrent();




        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent auth=new Intent(this,LoginActivity.class);
            startActivity(auth);
            finish();
        }
        else{
            if(this.currentGroup==null && this.currentUser==null){
                Toast.makeText(this, "both are null", Toast.LENGTH_SHORT).show();
            }
            else if(this.currentGroup==null && this.currentUser!=null){
                Toast.makeText(this, "group is null", Toast.LENGTH_SHORT).show();
            }
            else if(this.currentUser==null && this.currentGroup!=null){
                Toast.makeText(this, "usr is null", Toast.LENGTH_SHORT).show();
            }
            else if(this.currentGroup!=null && this.currentGroup!=null){
                Toast.makeText(this, "both are ok", Toast.LENGTH_SHORT).show();
                sendMyLocation();
                setNav();
            }
        }
    }



    private void startInstalledAppDetailsActivity() {
        Intent i = new Intent();
        i.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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



    public void sendMessage(View v){

        EditText msgText=(EditText)findViewById(R.id.chatEditText);

        if(msgText.getText().toString().equalsIgnoreCase("")){
            Toast t=Toast.makeText(this,"Message should not be empty.",Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        Message message=new Message(currentGroup,currentUser,msgText.getText().toString(), new Date());

        currentGroup.getMessages().add(message);

        ((RecyclerView)findViewById(R.id.chatRecycler)).getAdapter().notifyDataSetChanged();
        ((RecyclerView)findViewById(R.id.chatRecycler)).getLayoutManager().scrollToPosition(currentGroup.getMessages().size()-1);

        msgText.setText("");

        PendingIntent piSent = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();

        String msg=message.getText();
        msg="@squadApp@"+currentGroup.getGroupId()+"\n"+msg;

        int length = msg.length();
        if(length > MAX_SMS_MESSAGE_LENGTH) {
            ArrayList<String> messagelist = smsManager.divideMessage(msg);

            for(User u:currentGroup.getMembers())
                if(u!=currentUser && u.getPhone()!=null)
                    smsManager.sendMultipartTextMessage(u.getPhone(), null, messagelist, null, null);
        }
        else
            for(User u:currentGroup.getMembers())
                if(u!=currentUser && u.getPhone()!=null)
                    smsManager.sendTextMessage(u.getPhone(), null, msg, piSent, piDelivered);

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
        Date d =new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        post = new Post(currentGroup.getGroupId(), currentUser, statusText.getText().toString(), null, df.format(d));

        currentGroup.getPosts().add(0,post);
        mDatabase.child("Posts").push().setValue(post);

        //((RecyclerView)findViewById(R.id.feedRecycler)).getAdapter().notifyDataSetChanged();

        statusText.setText("");

        ((ImageView)findViewById(R.id.feedAttachThumbnail)).setImageDrawable(null);
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

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void cameraStatus(View v){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    postImage = FileProvider.getUriForFile(this,
                            "com.smdproject.smdproject.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, postImage);
                    startActivityForResult(takePictureIntent, 1);
                }
            }


        }

    }

    public void deleteAttachment(View v){

        ((ImageView)findViewById(R.id.feedAttachThumbnail)).setImageDrawable(null);
        ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.GONE);
        postImage=null;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==0 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri uri=data.getData();

            postImage=uri;

            ImageView imageview=(ImageView)findViewById(R.id.feedAttachThumbnail);

            imageview.setImageURI(uri);

            ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.VISIBLE);
        }
        else if(requestCode==1 && resultCode==RESULT_OK){


            //Bitmap bm=BitmapFactory.decodeFile(mCurrentPhotoPath,op);

            Uri uri=Uri.parse(mCurrentPhotoPath);

            postImage=uri;

            ImageView imageview=(ImageView)findViewById(R.id.feedAttachThumbnail);
            imageview.setImageURI(uri);

            ((Button)findViewById(R.id.deleteAttachment)).setVisibility(Button.VISIBLE);


        }
        else if (requestCode==123 && resultCode==RESULT_OK && data!=null && data.getExtras()!=null){
            String latlng="";
            String address="";

            if(data.getExtras().getString("eplace")!=null) {
                latlng= data.getExtras().getString("eplace");
                address = data.getExtras().getString("eadd");
            }


            Event e=new Event(currentGroup.getGroupId(), address,data.getExtras().getString("ename"),data.getExtras().getString("edes"),data.getExtras().getString("edate")+" "+data.getExtras().getString("etime")+":00",latlng);
            currentGroup.getEvents().add(0,e);
            mDatabase.child("Events").push().setValue(e);
//            ((RecyclerView)findViewById(R.id.eventview)).getAdapter().notifyDataSetChanged();
//            ((RecyclerView)findViewById(R.id.eventHorizontal)).getAdapter().notifyDataSetChanged();

        }
        else if (requestCode==100 && resultCode==RESULT_OK && data!=null && data.getExtras()!=null){

            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((EditText)findViewById(R.id.postEditText))
                    .setText(((EditText)findViewById(R.id.postEditText)).getText().toString()+" "+result.get(0));
        }
        else if (requestCode==101 && resultCode==RESULT_OK && data!=null && data.getExtras()!=null){

            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((EditText)findViewById(R.id.chatEditText))
                    .setText(((EditText)findViewById(R.id.chatEditText)).getText().toString()+" "+result.get(0));
        }
//        else if(requestCode==102 && resultCode==RESULT_OK && data!=null && data.getExtras()!=null){
//
//            currentGroup = (Group) data.getSerializableExtra("Group");
//
//            ///get user picture from fb login or //google login
//            //make user
//            //add to group
//
//            //currentGroup.getNicknames().put(currentUser.getUid(), (String) currentUser.getName().subSequence(2,5));
//            currentGroup.getMembers().add(0,currentUser);
//        }
//        else if(requestCode==103 && resultCode==RESULT_OK && data!=null && data.getExtras()!=null){
//            String s= data.getExtras().getString("p_name");
//            currentUser = new User(mAuth.getCurrentUser().getUid(),null,s,mAuth.getCurrentUser().getPhoneNumber());
//            saveCurrentUser();
//            mDatabase.child("currentUser").child(currentUser.getUid()).setValue(currentUser);
//        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putSerializable("currentUser",currentUser);
        savedInstanceState.putSerializable("currentGroup",currentGroup);
        if(postImage!=null)savedInstanceState.putString("postImage",postImage.toString());
        else savedInstanceState.putString("postImage","");
        savedInstanceState.putString("filename",mCurrentPhotoPath);


        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        currentUser=(User)savedInstanceState.getSerializable("currentUser");
        currentGroup=(Group)savedInstanceState.getSerializable("currentGroup");
        if(savedInstanceState.getString("postImage").equalsIgnoreCase(""))postImage=null;
        else postImage=Uri.parse(savedInstanceState.getString("postImage"));
        mCurrentPhotoPath=savedInstanceState.getString("filename");


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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You did not give permission to access Location.", Toast.LENGTH_SHORT).show();
            } else mMap.setMyLocationEnabled(true);
        }else mMap.setMyLocationEnabled(true);



        if(currentGroup!=null) {

            for (int i = 0; i < currentGroup.getMembers().size(); i++) {
                if (currentGroup.getMembers().get(i).getLocation() != null) {
                    if(currentGroup.getMembers().get(i)!=currentUser) {
                        String[] arrOfStr = currentGroup.getMembers().get(i).getLocation().split(",", 2);
                        Double l1 = Double.parseDouble(arrOfStr[0]);
                        Double l2 = Double.parseDouble(arrOfStr[1]);
                        markers.add(
                                mMap.addMarker(new MarkerOptions().position
                                        (new LatLng(l1,l2)).title(currentGroup.getMembers().get(i).getName() + " is here!!"))
                        );
                    }

                }
            }
            for (int i = 0; i < currentGroup.getEvents().size(); i++) {
                if (currentGroup.getEvents().get(i).getLocation() != null) {

                        String[] arrOfStr = currentGroup.getEvents().get(i).getLocation().split(",", 2);
                        Double l1 = Double.parseDouble(arrOfStr[0]);
                        Double l2 = Double.parseDouble(arrOfStr[1]);
                        markers.add(
                                mMap.addMarker(new MarkerOptions().position
                                        (new LatLng(l1,l2)).title("Event: "+currentGroup.getEvents().get(i).getName() ))
                        );


                }
            }

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {

                    Location mlocation=mMap.getMyLocation();
                    LatLng mlatlng=new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
                    if(markers.isEmpty()){
                        CameraPosition position=new CameraPosition.Builder()
                                .target(mlatlng)
                                .zoom(17).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                        return;
                    }

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    builder.include(mlatlng);

                    LatLngBounds bounds = builder.build();

                    int padding = 100; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                    mMap.animateCamera(cu);
                }
            });

        }

    }



    private void startVoiceInputPost() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void startVoiceInputChat() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");
        try {
            startActivityForResult(intent, 101);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void voicePost(View v){
        startVoiceInputPost();
    }
    public void voiceChat(View v){
        startVoiceInputChat();
    }



    private String saveToInternalStorage(Bitmap bitmapImage){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }



    public void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                .putExtra(AlarmClock.EXTRA_SKIP_UI,false);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void showAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_HOUR,3)
                .putExtra(AlarmClock.EXTRA_MESSAGE,"evening tea")
                .putExtra(AlarmClock.EXTRA_SKIP_UI,true);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}