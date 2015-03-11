package org.mythtv.android.player.recordings;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.library.ui.loaders.ProgramsAsyncTaskLoader;
import org.mythtv.android.player.AbstractBaseFragment;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<List<Program>>, ProgramItemAdapter.ProgramItemClickListener {

    private static final String PROGRAM_TITLE_KEY = "program_title";

    RecyclerView mRecyclerView;
    ProgramItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;
    boolean mShowTitle = false;

    @Override
    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {

        ProgramsAsyncTaskLoader loader = new ProgramsAsyncTaskLoader( getActivity() );

        if( args.containsKey( PROGRAM_TITLE_KEY ) ) {

            loader.setTitle( args.getString( PROGRAM_TITLE_KEY ) );

        }

        return loader;
    }

    @Override
    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {

        if( !programs.isEmpty() ) {

            mAdapter = new ProgramItemAdapter( programs, this, mShowTitle );
            mRecyclerView.setAdapter( mAdapter );

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Program>> loader ) {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.program_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    public void setPrograms( String title, List<Program> programs ) {

        mShowTitle = ( null == title );

        Bundle args = new Bundle();
        if( null != title ) {
            args.putString( PROGRAM_TITLE_KEY, title );
        }

        getLoaderManager().initLoader( 0, args, this );

    }

    public void onProgramItemClicked( View v, Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( getActivity(), RecordingDetailsActivity.class );
        recordingDetails.putExtras(args);

        String transitionName = getString( R.string.recording_transition );
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(),
                v,   // The view which starts the transition
                transitionName    // The transitionName of the view we’re transitioning to
            );
        ActivityCompat.startActivity( getActivity(), recordingDetails, options.toBundle() );

    }

    @Override
    public void connected() {

        mRecyclerView.setVisibility( View.VISIBLE );
        mEmpty.setVisibility( View.GONE );

    }

    @Override
    public void notConnected() {

        mRecyclerView.setVisibility( View.GONE );
        mEmpty.setVisibility( View.VISIBLE );

    }

}
