package com.proxiad.plovdev.activities;

import java.util.List;
import java.util.Locale;

import com.proxiad.plovdev.fragments.AboutFragment;
import com.proxiad.plovdev.fragments.MainFragment;
import com.proxiad.plovdev.fragments.NavigationDrawerFragment;
import com.proxiad.plovdev.fragments.PartnersFragment;
import com.proxiad.plovdev.R;
import com.proxiad.plovdev.fragments.SpeakersFragment;
import com.proxiad.plovdev.fragments.VenueFragment;
import com.proxiad.plovdev.utils.parsers.DataParser;
import com.proxiad.plovdev.utils.ImageUtils;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    // tags for easier find
    private final String mainFragmentTag = "mainFragment";
    private final String speakersFragmentTag = "speakersFragment";
    private final String venueFragmentTag = "venueFragment";
    private final String partnersFragmentTag = "partnersFragment";
    private final String aboutFragmentTag = "aboutFragment";

    private Fragment mainFragment;
    private Fragment speakersFragment;
    private Fragment venueFragment;
    private Fragment partnersFragment;
    private Fragment aboutFragment;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    // holds the position of the currently selected item in the Nav Drawer
    // fragment
    private int position;
    // holds the title of the fragment being shown
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataParser.context = this;
        setLocale(this);
        setContentView(R.layout.activity_main);
        ImageView logoImageView = (ImageView) findViewById(R.id.imageLogoPlovdev);
        logoImageView.setImageResource(R.drawable.logo_plovdev);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActionBar().setTitle(R.string.first_day);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageUtils.cleanupCache();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mainFragment = fragmentManager.getFragment(savedInstanceState, mainFragmentTag);
        speakersFragment = fragmentManager.getFragment(savedInstanceState, speakersFragmentTag);
        venueFragment = fragmentManager.getFragment(savedInstanceState, venueFragmentTag);
        partnersFragment = fragmentManager.getFragment(savedInstanceState, partnersFragmentTag);
        aboutFragment = fragmentManager.getFragment(savedInstanceState, aboutFragmentTag);
        position = savedInstanceState.getInt("position");
        onNavigationDrawerItemSelected(position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save the currently selected fragment and the fragments instances for
        // reuse (should I use retained fragments ?)
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mainFragment != null) {
            fragmentManager.putFragment(outState, mainFragmentTag, mainFragment);
        }
        if (speakersFragment != null) {
            fragmentManager.putFragment(outState, speakersFragmentTag, speakersFragment);
        }
        if (venueFragment != null) {
            fragmentManager.putFragment(outState, venueFragmentTag, venueFragment);
        }
        if (partnersFragment != null) {
            fragmentManager.putFragment(outState, partnersFragmentTag, partnersFragment);
        }
        if (aboutFragment != null) {
            fragmentManager.putFragment(outState, aboutFragmentTag, aboutFragment);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by showing and hiding fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        hideAllFragments(fragmentManager);
        this.position = position;
        switch (position) {
            case 0:
                mTitle = getString(R.string.first_day);
                mainFragment = fragmentManager.findFragmentByTag(mainFragmentTag);
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    fragmentManager.beginTransaction().add(R.id.container, mainFragment, mainFragmentTag).commit();
                }
                fragmentManager.beginTransaction().show(mainFragment).commit();
                break;
            case 1:
                mTitle = getString(R.string.speakers);
                speakersFragment = fragmentManager.findFragmentByTag(speakersFragmentTag);
                if (speakersFragment == null) {
                    speakersFragment = new SpeakersFragment();
                    fragmentManager.beginTransaction().add(R.id.container, speakersFragment, speakersFragmentTag).commit();
                }
                fragmentManager.beginTransaction().show(speakersFragment).commit();
                break;
            case 2:
                mTitle = getString(R.string.venue);
                venueFragment = fragmentManager.findFragmentByTag(venueFragmentTag);
                if (venueFragment == null) {
                    venueFragment = new VenueFragment();
                    fragmentManager.beginTransaction().add(R.id.container, venueFragment, venueFragmentTag).commit();
                }
                fragmentManager.beginTransaction().show(venueFragment).commit();
                break;
            case 3:
                mTitle = getString(R.string.partners);
                partnersFragment = fragmentManager.findFragmentByTag(partnersFragmentTag);
                if (partnersFragment == null) {
                    partnersFragment = new PartnersFragment();
                    fragmentManager.beginTransaction().add(R.id.container, partnersFragment, partnersFragmentTag).commit();
                }
                fragmentManager.beginTransaction().show(partnersFragment).commit();
                break;
            case 4:
                mTitle = getString(R.string.about);
                aboutFragment = fragmentManager.findFragmentByTag(aboutFragmentTag);
                if (aboutFragment == null) {
                    aboutFragment = new AboutFragment();
                    fragmentManager.beginTransaction().add(R.id.container, aboutFragment, aboutFragmentTag).commit();
                }
                fragmentManager.beginTransaction().show(aboutFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Back pressed are auto-handled if parent activity is declared
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Custom navigation -> on back pressed always show the main fragment, or
     * close the application if the main fragment is shown
     * <p/>
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
            return;
        }
        if (mainFragment == null) {
            mainFragment = fragmentManager.findFragmentByTag(mainFragmentTag);
            if (mainFragment == null) {
                mainFragment = new MainFragment();
                fragmentManager.beginTransaction().add(R.id.container, mainFragment, mainFragmentTag).commit();
            }
            hideAllFragmentsAndShowTheMainFragment(fragmentManager);
        } else if (mainFragment.isHidden()) {
            hideAllFragmentsAndShowTheMainFragment(fragmentManager);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Click listener for handling the clicks of the logo used in
     * activity_main.xml
     */
    public void onLogoClicked(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mainFragment == null) {
            this.position = 0;
            mainFragment = fragmentManager.findFragmentByTag(mainFragmentTag);
            if (mainFragment == null) {
                mainFragment = new MainFragment();
                fragmentManager.beginTransaction().add(R.id.container, mainFragment, mainFragmentTag).commit();
            }
            hideAllFragments(fragmentManager);
            fragmentManager.beginTransaction().show(mainFragment).commit();
            mNavigationDrawerFragment.setCurrentPosition(0);
            getActionBar().setTitle(R.string.first_day);
        } else {
            this.position = 0;
            hideAllFragments(fragmentManager);
            fragmentManager.beginTransaction().show(mainFragment).commit();
            mNavigationDrawerFragment.setCurrentPosition(0);
            getActionBar().setTitle(R.string.first_day);
        }
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

    private void setLocale(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = sharedPreferences.getString(SettingsActivity.KEY_PREF_LIST_LANGUAGE, "en");
        Locale locale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

    /**
     * Hide all fragments and shows only the main one
     *
     * @param fragmentManager
     */
    private void hideAllFragmentsAndShowTheMainFragment(FragmentManager fragmentManager) {
        this.position = 0;
        hideAllFragments(fragmentManager);
        fragmentManager.beginTransaction().show(mainFragment).commit();
        mNavigationDrawerFragment.setCurrentPosition(0);
        getActionBar().setTitle(R.string.first_day);
    }

    /**
     * Hide all fragments
     *
     * @param fragmentManager
     */
    private void hideAllFragments(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getId() != R.id.navigation_drawer) {
                fragmentManager.beginTransaction().hide(fragment).commit();
            }
        }
    }

    /**
     * Restores the action bar on recreation
     */
    private void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
}
