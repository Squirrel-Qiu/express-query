package org.tencent;

import com.alibaba.fastjson.JSONException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface ExpressQuery {
    String query(String expCompany, String expNo) throws IOException, NoSuchAlgorithmException, JSONException;
}
