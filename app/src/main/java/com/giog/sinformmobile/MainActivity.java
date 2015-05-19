package com.giog.sinformmobile;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.giog.sinformmobile.fragments.ContactFragment;
import com.giog.sinformmobile.fragments.CourseFragment;
import com.giog.sinformmobile.fragments.GuestFragment;
import com.giog.sinformmobile.fragments.HomeFragment;
import com.giog.sinformmobile.fragments.LectureFragment;
import com.giog.sinformmobile.fragments.MapFragment;
import com.giog.sinformmobile.fragments.OrganizationFragment;
import com.giog.sinformmobile.fragments.ProgrammingFragment;
import com.giog.sinformmobile.fragments.SupportFragment;
import com.giog.sinformmobile.model.User;
import com.giog.sinformmobile.utils.LoginDialog;


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

    private boolean isHome;
    public static User SESSION_USER = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
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
                mTitle = getString(R.string.title_courses);
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
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
}
