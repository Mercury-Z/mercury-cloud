package cn.mercury.mercurycloud.domain;

public class User {
    private Integer user_id;

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                '}';
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
