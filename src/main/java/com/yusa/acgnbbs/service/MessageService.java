package com.yusa.acgnbbs.service;

import java.util.Map;

public interface MessageService {
    boolean send(Map<String, Object> map, String phone);
}
