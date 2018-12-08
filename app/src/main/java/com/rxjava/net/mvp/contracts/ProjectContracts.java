package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectMessage;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public interface ProjectContracts {
    interface View extends IBaseView {
        void resultProjectTree(List<ProjectClass> projectClasses);

        void resultProjectList(ProjectMessage projectMessage);

    }

    interface Presenter {
        void requestProjectTree();

        void requestProjectList(int page, int cid);
    }
}
