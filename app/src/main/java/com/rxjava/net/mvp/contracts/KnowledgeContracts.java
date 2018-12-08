package com.rxjava.net.mvp.contracts;

import com.rxjava.net.base.IBaseView;
import com.rxjava.net.bean.Knowledge;
import com.rxjava.net.bean.ProjectClass;
import com.rxjava.net.bean.ProjectMessage;

import java.util.List;

/**
 * Created by Lenovo on 2018/12/4.
 */

public interface KnowledgeContracts {
    interface View extends IBaseView {
        void resultKnowledge(List<Knowledge> knowledge);
    }

    interface Presenter {
        void requestKnowledge();
    }
}
