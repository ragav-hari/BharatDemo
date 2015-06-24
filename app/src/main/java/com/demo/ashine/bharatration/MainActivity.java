package com.demo.ashine.bharatration;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import demo.adapter.CardAdapter;
import demo.model.Brand;
import demo.model.BrandAttribute;
import demo.model.TabContent;
import demo.model.Type;
import demo.utils.AppConstants;
import demo.utils.RestfulConnection;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    JSONObject jsonObject = null;
    private String mdata = "";
    ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        /* Tab Coding Starts */
        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("Essentials");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Essentials");

        TabHost.TabSpec spec2=tabHost.newTabSpec("Women");
        spec2.setIndicator("Women");
        spec2.setContent(R.id.tab2);

        TabHost.TabSpec spec3=tabHost.newTabSpec("Kids and Men");
        spec3.setIndicator("Kids and Men");
        spec3.setContent(R.id.tab3);

        TabHost.TabSpec spec4=tabHost.newTabSpec("Others");
        spec4.setIndicator("Others");
        spec4.setContent(R.id.tab4);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        new GetCardData().execute();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * Asynctask to get data from php
     */

    private class GetCardData extends AsyncTask<String , String , String>
    {
        Context context;
        @Override
        protected void onPreExecute()
        {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading Please Wait...");
            progressDialog.show();
        }

        protected String doInBackground(String... arg0)
        {
            mdata = RestfulConnection.source(AppConstants.URLConstants.essentials);
            return mdata;
        }

        protected void onPostExecute(String data)
        {
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            ProcessData processData = new ProcessData();
            processData.getData(data);
        }

    }


    public class ProcessData
    {
        public void getData(String data)
        {
            //System.out.println("Get"+ " "+data);
            Gson gson  = new GsonBuilder().create();
            Type type = gson.fromJson(data, Type.class);

            try {
                JSONObject jsonObject = new JSONObject(data);
                String result = jsonObject.getString("Result");
                JSONArray jsonArray = new JSONArray(result);
                ArrayList<Type> types = new ArrayList<Type>();

                for(int i = 0 ; i < jsonArray.length() ; i++)
                {
                    jsonObject = jsonArray.getJSONObject(i);
                    String type_name = jsonObject.getString("type_name");
                    Type type1 = new Type();
                    type1.setType_name(type_name);
                    types.add(type1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /* Set Pojo for Creating object */
        /*    TabContent tabContent = new TabContent();
            TabContent tabContent1 = new TabContent();
            ArrayList<TabContent> tabContents = new ArrayList<TabContent>();

            Type salt  = new Type();
            Type sugar = new Type();

            Brand ionisedsalt   = new Brand();
            Brand ordinarysalt  = new Brand();
            Brand refinedsugar  = new Brand();
            Brand ordinarysugar = new Brand();

            BrandAttribute ionsaltattr1 = new BrandAttribute();
            BrandAttribute ionsaltattr2 = new BrandAttribute();
            BrandAttribute ordsaltattr1 = new BrandAttribute();
            BrandAttribute ordsaltattr2 = new BrandAttribute();
            BrandAttribute refsugrattr1 = new BrandAttribute();
            BrandAttribute refsugrattr2 = new BrandAttribute();
            BrandAttribute ordsugrattr1 = new BrandAttribute();
            BrandAttribute ordsugrattr2 = new BrandAttribute();

            ArrayList<Brand>  sugarlist = new ArrayList<Brand>();
            ArrayList<Brand>  saltlist  = new ArrayList<Brand>();

            ArrayList<BrandAttribute> sugarlistattr = new ArrayList<BrandAttribute>();
            ArrayList<BrandAttribute> saltlistattr = new ArrayList<BrandAttribute>();

            salt.setType_id(1);
            salt.setType_name("Salt");

            sugar.setType_id(2);
            sugar.setType_name("Sugar");

            ionsaltattr1.setWeight("10 KG");
            ionsaltattr1.setPrice("Rs 50");

            ionsaltattr2.setWeight("5 KG");
            ionsaltattr2.setPrice("Rs 80");

            refsugrattr1.setWeight("15 KG");
            refsugrattr2.setPrice("Rs 100");

            refsugrattr2.setWeight("20 KG");
            refsugrattr2.setPrice("Rs 150");

            saltlistattr.add(ionsaltattr1);
            saltlistattr.add(ionsaltattr2);

            sugarlistattr.add(refsugrattr1);
            sugarlistattr.add(refsugrattr2);

            ionisedsalt.setBrand_id(1);
            ionisedsalt.setBrand_name("Ionised Salt");
            ionisedsalt.setBrandAttributeList(saltlistattr);

            refinedsugar.setBrand_id(2);
            refinedsugar.setBrand_name("Refined Sugar");
            refinedsugar.setBrandAttributeList(sugarlistattr);

            saltlist.add(ionisedsalt);
            sugarlist.add(refinedsugar);

            tabContent.setType(salt);
            tabContent.setBrandList(saltlist);

            tabContent1.setType(sugar);
            tabContent1.setBrandList(sugarlist);

            tabContents.add(tabContent);
            tabContents.add(tabContent1);
*/
          //  System.out.println(gson.toJson(tabContents));





            //System.out.println("Type"+ " "+type.toString());
            //System.out.println("Type Name"+ " "+type.getType_name());
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
