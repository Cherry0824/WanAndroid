package com.rxjava.net.comm.api;

import com.rxjava.net.base.BaseResult;
import com.rxjava.net.bean.ArticleItem;
import com.rxjava.net.bean.ArticleMessage;
import com.rxjava.net.bean.Banner;
import com.rxjava.net.bean.CollectionMessage;
import com.rxjava.net.bean.HotTerms;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.Navigation;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectMessage;
import com.rxjava.net.bean.User;
import com.rxjava.net.bean.Website;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Lenovo on 2018/11/30.
 */

public interface ApiService {
    //登录
    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResult<User>> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    Observable<BaseResult<User>> userRegister(
            @Field("username") String username,
            @Field("password") String password,
            @Field("repassword") String repassword
    );

    //退出
    @GET("/user/logout/json")
    Observable<BaseResult> userLogout();

    //首页Banner
    @GET("/banner/json")
    Observable<BaseResult<List<Banner>>> requestBanner();

    //首页文章列表（第一个tab）
    @GET("/article/list/{page}/json")
    Observable<BaseResult<ArticleMessage>> requestHomeArticle(
            @Path("page") int page
    );

    //首页置顶文章
    @GET("/article/top/json")
    Observable<BaseResult<List<ArticleItem>>> requestHomeArticleTop();

    //首页项目列表（第二个tab）
    @GET("/article/listproject/{page}/json")
    Observable<BaseResult<ProjectMessage>> requestHomeProject(
            @Path("page") int page
    );

    //常用网站
    @GET("/friend/json")
    Observable<BaseResult<List<Website>>> usefulWeb();

    //搜索热词
    @GET("/hotkey/json")
    Observable<BaseResult<List<HotTerms>>> searchHotTerms();

    //知识体系
    @GET("/tree/json")
    Observable<BaseResult<List<Knowledge>>> requestKnowledge();

    //知识体系下文章
    @GET("/article/list/{page}/json")
    Observable<BaseResult<ArticleMessage>> requestKnowledgeArticle(
            @Path("page") int page,
            @Query("cid") int cid
    );

    //获取导航数据
    @GET("/navi/json")
    Observable<BaseResult<List<Navigation>>> requestNavigationData();

    //获取项目分类
    @GET("/project/tree/json")
    Observable<BaseResult<List<ProjectClass>>> requestProjectClass();

    //获取项目列表
    @GET("/project/list/{page}/json")
    Observable<BaseResult<ProjectMessage>> requestProjectItem(
            @Path("page") int page,
            @Query("cid") int cid
    );

    //获取用户自己的收藏列表
    @GET("/lg/collect/list/{page}/json")
    Observable<BaseResult<CollectionMessage>> requestCollection(
            @Path("page") int page
    );

    //收藏列表取消收藏
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    Observable<BaseResult> requestRemoveCollectList(
            @Path("id") int id,
            @Field("originId") int originId
    );

    //取消收藏
    @POST("/lg/uncollect_originId/{id}/json")
    Observable<BaseResult> requestRemoveCollect(
            @Path("id") int id
    );

    //添加收藏
    @POST("/lg/collect/{id}/json")
    Observable<BaseResult> requestAddCollect(
            @Path("id") int id
    );

    //搜索
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    Observable<BaseResult<ArticleMessage>> requestSearch(
            @Path("page") int page,
            @Field("k") String key
    );
}
