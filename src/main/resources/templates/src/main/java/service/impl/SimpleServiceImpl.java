package py.dubbosupport.sample.provider.service.impl;


import py.dubbosupport.sample.provider.service.SimpleService;

/**
 * Created by pengyu on 2017/6/15.
 */
public class SimpleServiceImpl implements SimpleService {
    @Override
    public String getUserInfo(String userId) {
        return "your userId is " + userId;
    }
}
