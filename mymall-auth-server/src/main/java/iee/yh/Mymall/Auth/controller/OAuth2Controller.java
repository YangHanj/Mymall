package iee.yh.Mymall.Auth.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanghan
 * @date 2022/11/29
 */
@Controller
public class OAuth2Controller {

    final String WEIBO_URL = "https://api.weibo.com/oauth2/access_token";

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code){
        // 利用code换取token
        Map<String,String> map = new HashMap();
        map.put("client_id","");
        map.put("client_secret ","");
        map.put("grant_type","");
        map.put("redirect_uri","http://mymall.com/oauth2.0/weibo/success");
        map.put("code","");
        String res = HttpUtil.post(WEIBO_URL, JSONUtil.parseFromMap(map).toString());
        JSONObject resJson = JSONUtil.parseObj(res);
        // TODO resJson.get()
        return "redirect:http://mymall.com";
    }
}
