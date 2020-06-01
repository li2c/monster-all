package com.personal.practice.dubbo.provider.callback;

import com.personal.service.CallbackListener;
import com.personal.service.ICallbackService;
import org.springframework.stereotype.Service;

@Service
public class CallbackServiceImpl implements ICallbackService {
    @Override
    public void addListener(String key, CallbackListener listener) {
        System.out.println("receive key"+key);
        listener.doSomething();
    }
}
