package com.example.hostel;
// Gargi Rai

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.example.hostel.R;

import com.example.hostel.Adapters.HotelsAdapter;
import com.example.hostel.Decoration.GridSpacingItemDecoration;
import com.example.hostel.Util.Bookings;
import com.example.hostel.Util.CurrentUser;
import com.example.hostel.Util.Hotel;
import com.example.hostel.Util.HotelView;
import com.example.hostel.Util.Reader;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HotelsAdapter adapter;
    private List<HotelView> hotelList;
    private List<HotelView> hotelListAll;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Intent intent = getIntent();

        username = CurrentUser.username;
        initCollapsingToolbar();

        recyclerView =  findViewById(R.id.recycler_view);

        hotelList = new ArrayList<>();
        hotelListAll = new ArrayList<>();
        adapter = new HotelsAdapter(this, hotelList, hotelListAll);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        prepareHotels();

        /*SearchView mySearchView = (SearchView) findViewById(R.id.searchView);
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });*/

        try {
            Glide.with(this).load(R.drawable.back).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // Initializing collapsing toolbar Will show and hide the toolbar title on scroll
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Hostels/PG");
                    isShow = true;

                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private Bookings getBooked(){
        ArrayList<Bookings> bookings = Reader.getBookingsList(getApplicationContext());
        if(bookings == null)
            return null;
        for(Bookings book : bookings){
            String name = book.getName();
            if(name == null) continue;
            if(name.equals(username))
            {

                return book;

            }
        }

        return null;
    }

    private void prepareHotels() {
        int[] cover = {R.drawable.hicon1,
                R.drawable.hicon2,
                R.drawable.hicon3,
                R.drawable.hicon4};
        Random random = new Random();
       Bookings booking = getBooked();
        HotelView hotelview ;
        List<Hotel> hotels = Reader.getRestaurantList(getApplicationContext());

        if(booking==null)
        {
        for(Hotel h : hotels) {
            int idx = random.nextInt(12);
            idx = idx%4;
                hotelview = new HotelView(h.getName(),h.getLocation(),cover[idx],h.getRating(),h.getFeats());
                hotelList.add(hotelview);
            }
        } else {
            List<Integer> ids = booking.getId();
            for(Hotel h : hotels) {
                int idx = random.nextInt(12);
                idx = idx%4;

                if(!ids.contains(h.getId())) {
                    hotelview = new HotelView(h.getName(), h.getLocation(), cover[idx], h.getRating(), h.getFeats());
                    hotelList.add(hotelview);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    //RecyclerView item decoration - give equal margin around grid item
    //Converting dp to pixel
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        finishAffinity();

        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_hotel, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.show_bookings:
                Bookings booking = getBooked();
                ArrayList<Bookings> hotels = Reader.getBookingsList(getApplicationContext());
                if(booking==null)
                    Toast.makeText(getApplicationContext(),"No Bookings for you",Toast.LENGTH_LONG).show();
                else {
                    List<Integer> ids = booking.getId();
                    String res = "";
                    for(Bookings h : hotels) {

                        if(ids.contains(h.getId())) {
                            res = h.getName();
                        }
                        else{
                            res = res + "\n"+ h.getName();
                        }
                    }
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginAcitvity.class));
                finishAffinity();;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

