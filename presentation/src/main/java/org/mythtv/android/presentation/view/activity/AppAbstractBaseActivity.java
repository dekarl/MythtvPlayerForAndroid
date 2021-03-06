package org.mythtv.android.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.mythtv.android.R;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.presentation.navigation.AppNavigator;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 *
 * Created by dmfrey on 8/30/15.
 */
public abstract class AppAbstractBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = AppAbstractBaseActivity.class.getSimpleName();

    @Inject
    AppNavigator navigator;

    @Nullable @Bind( R.id.navigation_view ) protected NavigationView navigationView;
    @Nullable @Bind( R.id.toolbar ) protected Toolbar toolbar;

    @Nullable @Bind( R.id.drawer_layout ) protected DrawerLayout drawerLayout;

    public abstract int getLayoutResource();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.getApplicationComponent().inject( this );
        setContentView( getLayoutResource() );
        ButterKnife.bind( this );

        if( null != navigationView ) {
            navigationView.setNavigationItemSelectedListener( this );
        }

        if( toolbar != null ) {
            setSupportActionBar( toolbar );

            final ActionBar actionBar = getSupportActionBar();
            if( null != actionBar ) {

                if( null != navigationView ) {

                    actionBar.setHomeAsUpIndicator( R.drawable.ic_menu_white_24dp );
                }

                actionBar.setDisplayHomeAsUpEnabled( true );
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.main, menu );

        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.search_action ) .getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setIconifiedByDefault( false );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                if( null != navigationView ) {

                    drawerLayout.openDrawer( GravityCompat.START );

                    return true;
                }

        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem menuItem ) {
        Log.i(TAG, "onNavigationItemSelected : enter");

        menuItem.setChecked( true );
        drawerLayout.closeDrawers();

        switch( menuItem.getItemId() ) {

            case R.id.navigation_item_home :

                navigator.navigateToHome( this );

                return true;

            case R.id.navigation_item_watch_recordings :
                Log.i( TAG, "onNavigationItemSelected : watch recordings clicked" );

                navigator.navigateToTitleInfos( this );

                return true;

            case R.id.navigation_item_watch_videos :
                Log.i( TAG, "onNavigationItemSelected : watch videos clicked" );

                navigator.navigateToVideos( this );

//                String videoView  = MainApplication.getInstance().getVideoView();
//                if( "grid".equals( videoView ) ) {

//                    Intent videos = new Intent( this, VideosActivity.class );
//                    videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( videos );

//                } else {

//                    Intent videos = new Intent( this, VideoDirActivity.class );
//                    videos.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//                    startActivity( videos );

//                }

                return true;

            case R.id.navigation_item_watch_settings :
                Log.i( TAG, "onNavigationItemSelected : settings clicked" );

                navigator.navigateToSettings( this );

                return true;
        }

        return false;
    }

    public void setNavigationMenuItemChecked( int index ) {

        navigationView.getMenu().getItem( index ).setChecked( true );

    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment( int containerViewId, Fragment fragment ) {

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( containerViewId, fragment );
        fragmentTransaction.commit();

    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.components.ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {

        return ( (AndroidApplication) getApplication() ).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.ActivityModule}
     */
    protected ActivityModule getActivityModule() {

        return new ActivityModule( this );
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( this );
    }

}
