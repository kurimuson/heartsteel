package com.crimson.app.heartsteel.reactivex.callback;

import com.crimson.app.heartsteel.reactivex.Topic;
import io.reactivex.rxjava3.subjects.PublishSubject;

public interface OnSubscribeCallback {

    void exec(Topic topic, PublishSubject<Object> subject);

}
