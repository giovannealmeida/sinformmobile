package com.giog.sinformmobile;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.giog.sinformmobile.fragments.ContactFragment;
import com.giog.sinformmobile.fragments.CourseFragment;
import com.giog.sinformmobile.fragments.GuestFragment;
import com.giog.sinformmobile.fragments.HomeFragment;
import com.giog.sinformmobile.fragments.LectureFragment;
import com.giog.sinformmobile.fragments.LoginFragment;
import com.giog.sinformmobile.fragments.MapFragment;
import com.giog.sinformmobile.fragments.OrganizationFragment;
import com.giog.sinformmobile.fragments.ProgrammingFragment;
import com.giog.sinformmobile.fragments.SupportFragment;
import com.giog.sinformmobile.model.User;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private ListView lvMenu;

    public static Menu menu;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private boolean isHome;
    public static User SESSION_USER = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvMenu = (ListView) findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(this);
        lvMenu.setAdapter(new ArrayAdapter<String>(
//                getSupportActionBar().getThemedContext(),
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.title_home),
                        getString(R.string.title_programming),
                        getString(R.string.title_courses),
                        getString(R.string.title_lectures),
                        getString(R.string.title_guests),
                        getString(R.string.title_map),
                        getString(R.string.title_organization),
                        getString(R.string.title_support),
                        getString(R.string.title_contact),
                }));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        lvMenu.performItemClick(null,0,0);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if (!drawerLayout.isDrawerOpen(lvMenu)) {
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
        super.onOptionsItemSelected(item);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.container, new LoginFragment()).addToBackStack(null);
        transaction.commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        isHome = false;
        Fragment fragment = null;
        switch (position) {
            case 0:
                mTitle = getString(R.string.title_home);
                fragment = HomeFragment.newInstance(position + 1);
                isHome = true;
                break;
            case 1:
                mTitle = getString(R.string.title_programming);
                fragment = ProgrammingFragment.newInstance(position+1);
                break;
            case 2:
                mTitle = getString(R.string.title_courses);
                fragment = CourseFragment.newInstance(position+1);
                break;
            case 3:
                mTitle = getString(R.string.title_lectures);
                fragment = LectureFragment.newInstance(position + 1);
                break;
            case 4:
                mTitle = getString(R.string.title_guests);
                fragment = GuestFragment.newInstance(position + 1);
                break;
            case 5:
                mTitle = getString(R.string.title_map);
                fragment = MapFragment.newInstance(position + 1);
                break;
            case 6:
                mTitle = getString(R.string.title_organization);
                fragment = OrganizationFragment.newInstance(position + 1);
                break;
            case 7:
                mTitle = getString(R.string.title_support);
                fragment = SupportFragment.newInstance(position + 1);
                break;
            case 8:
                mTitle = getString(R.string.title_contact);
                fragment = ContactFragment.newInstance(position + 1);
                break;
        }
        if(fragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }

        if(drawerLayout.isDrawerOpen(lvMenu)){
            drawerLayout.closeDrawer(lvMenu);
        }
    }
}
