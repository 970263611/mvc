package com.dahuaboke.mvc.web.listener;

import com.dahuaboke.mvc.model.MvcListener;

import java.util.TreeSet;

/**
 * @Author dahua
 * @Date 2021/5/4 15:30
 * @Description mvc
 */
public class MvcListenerFactory {

    private TreeSet<MvcListener> listeners = new TreeSet<>((o1, o2) -> {
        if (o1.getOrder() > o2.getOrder()) {
            return 1;
        }
        return -1;
    });

    public TreeSet<MvcListener> getListeners() {
        return listeners;
    }

    public void addListener(MvcListener listener) {
        listeners.add(listener);
    }
}
