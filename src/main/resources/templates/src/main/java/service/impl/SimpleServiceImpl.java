package ${basePackageName}.service.impl;


import ${basePackageName}.service.SimpleService;

/**
 * Created by Generator.
 */
public class SimpleServiceImpl implements SimpleService {
    @Override
    public String getUserInfo(String userId) {
        return "your userId is " + userId;
    }
}
