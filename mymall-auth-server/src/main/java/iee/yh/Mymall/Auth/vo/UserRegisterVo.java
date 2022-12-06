package iee.yh.Mymall.Auth.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author yanghan
 * @date 2022/11/28
 */
public class UserRegisterVo {

    @NotEmpty(message = "username is not null")
    @Length(min = 6,max = 18,message = "用户名长度需要6-18位")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 18,message = "密码长度需要6-18位")
    private String password;

    private String mail;

    private String code;

    public UserRegisterVo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
