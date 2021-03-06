package com.example.mf.movielibrary.activities.favouritescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by RK on 05-04-2018.
 */
class FavouritesActivityContract {

    interface FavoritesView : BaseView {
        fun setupViewPager()
        fun setNoFavouritesLayout()
    }
    interface FavoritesPresenter : BasePresenter<FavoritesView> {
        fun requestSetupViewPager()
        fun requestNoFavouritesLayout()
    }
}