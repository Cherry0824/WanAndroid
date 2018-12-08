package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Banner;
import com.rxjava.net.bean.ProjectMessage;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/2.
 */

public interface HomeContracts {
    interface View extends CollectionContracts.View {
        void resultBanner(List<Banner> banners);

        void resultHomeArticle(ArticleMessage articles);


        void resultHomeArticleTop(List<ArticleItem> articleItem);


        void resultHomeProject(ProjectMessage projectMessage);
    }

    interface Presenter extends CollectionContracts.Presenter{
        void requestBanner();

        void requestHomeArticle(int page);

        void requestHomeArticleTop();

        void requestHomeProject(int page);
    }
}
