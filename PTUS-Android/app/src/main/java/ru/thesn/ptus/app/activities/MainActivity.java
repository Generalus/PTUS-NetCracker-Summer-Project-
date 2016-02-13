package ru.thesn.ptus.app.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import ru.thesn.ptus.app.R;
import ru.thesn.ptus.app.tools.StyledSnackBar;




public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static long back_pressed;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initializing Toolbar and setting it as the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.myFAB);
        ColorStateList rippleColor = getResources().getColorStateList(R.color.fab_ripple_color);
        myFab.setBackgroundTintList(rippleColor);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: обработать нажатие на кнопку
            }
        });
        myFab.animate();

        //Initializing NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Intent intent = new Intent(MainActivity.this, AppConfigActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.starred:
                        Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent2);
                        return true;
                    //case R.id.google_play:
                    //    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //    intent.setData(Uri.parse("market://details?id=")); // TODO указать адрес приложения
                    //    startActivity(intent);
                    //    return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Возникла ошибка...", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) { super(fm); }
        @Override
        public Fragment getItem(int position) {
            return null;
        }
        @Override
        public int getCount() { return 3; }
        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("DEV_", "MyPagerAdapter getPageTitle");
            switch (position) {
                case 0: return "Первый";
                case 1: return "Второй";
                case 2: return "Третий";
                default: return "Not found";
            }
        }
    }




    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            StyledSnackBar.showGreenSnackBar(getCurrentFocus(), "Нажмите снова для выхода!");
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onPostResume() {
        Log.i("DEV__", "Main onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        Log.i("DEV_", "Main onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("DEV_", "Main onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        Log.i("DEV_", "Main onPostCreate");
        super.onPostCreate(savedInstanceState);
    }
}

