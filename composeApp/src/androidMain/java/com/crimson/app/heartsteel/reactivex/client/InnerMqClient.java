package com.crimson.app.heartsteel.reactivex.client;

import com.crimson.app.heartsteel.reactivex.Topic;
import com.crimson.app.heartsteel.reactivex.callback.OnMessageCallback;
import com.crimson.app.heartsteel.reactivex.callback.OnSubscribeCallback;
import io.reactivex.rxjava3.subjects.PublishSubject;

public interface InnerMqClient {

    void setOnSubscribeCallback(OnSubscribeCallback onSubscribeCallback);

    String getId();

    PublishSubject<Object> getSubject(Topic topic);

    boolean isDestroyed();

    <T> void sub(Topic topic, OnMessageCallback<T> callback);

    void destroy();

}
