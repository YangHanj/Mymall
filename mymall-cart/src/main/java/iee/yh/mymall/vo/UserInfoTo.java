package iee.yh.mymall.vo;

/**
 * @author yanghan
 * @date 2022/12/4
 */
public class UserInfoTo {
    private Long userId;
    private String userKey;
    private Boolean tempUser = false;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Boolean getTempUser() {
        return tempUser;
    }

    public void setTempUser(Boolean tempUser) {
        this.tempUser = tempUser;
    }
}
