package org.mythtv.android.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import org.mythtv.android.R;
import org.mythtv.android.domain.exception.ErrorBundle;
import org.mythtv.android.domain.interactor.DefaultSubscriber;
import org.mythtv.android.presentation.model.TvCategoryModel;
import org.mythtv.android.presentation.view.TvCategoryListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 1/28/16.
 */
public class TvCategoryListPresenter extends DefaultSubscriber<List<TvCategoryModel>> implements Presenter {

    private TvCategoryListView viewListView;

    private final Context context;

    @Inject
    public TvCategoryListPresenter( Context context ) {

        this.context = context;

    }

    public void setView( @NonNull TvCategoryListView view ) {
        this.viewListView = view;
    }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    @Override
    public void destroy() { }

    /**
     * Initializes the presenter by start retrieving the tvCategory list.
     */
    public void initialize() {

        this.loadTvCategoryList();

    }

    /**
     * Loads all tvCategories.
     */
    private void loadTvCategoryList() {

        this.hideViewRetry();
        this.showViewLoading();
        this.getTvCategoryList();

    }

    public void onTvCategoryClicked( TvCategoryModel tvCategoryModel ) {

        this.viewListView.viewTvCategory( tvCategoryModel );

    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage( ErrorBundle errorBundle ) {

    }

    private void showTvCategoriesCollectionInView( Collection<TvCategoryModel> tvCategoriesCollection ) {

        this.viewListView.renderTvCategoryList( tvCategoriesCollection );

    }

    private void getTvCategoryList() {

        List<TvCategoryModel> tvCategoryModelsCollection = new ArrayList<>();

        String[] titles = new String[] {
                context.getResources().getString( R.string.drawer_item_watch_recordings ),
                context.getResources().getString( R.string.drawer_item_watch_videos ),
                context.getResources().getString( R.string.drawer_item_preferences )
        };

        Integer[] categories = new Integer[] {
                R.drawable.tv_watch_recordings,
                R.drawable.tv_watch_videos,
                R.drawable.tv_setting
        };

        tvCategoryModelsCollection.add( new TvCategoryModel( 0, titles[ 0 ], categories[ 0 ] ) );
        tvCategoryModelsCollection.add( new TvCategoryModel( 1, titles[ 1 ], categories[ 1 ] ) );
        tvCategoryModelsCollection.add( new TvCategoryModel( 2, titles[ 2 ], categories[ 2 ] ) );

        showTvCategoriesCollectionInView( tvCategoryModelsCollection );

    }

}
