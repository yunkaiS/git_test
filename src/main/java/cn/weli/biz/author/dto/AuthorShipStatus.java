package cn.weli.biz.author.dto;

public enum AuthorShipStatus {

    /**
     * 已删除
     */
    DELETED(0),
    /**
     * 正常
     */
    OK(1),
    /**
     * 已锁定
     */
    LOCKED(2);

    private int status;

    public int status() {
        return status;
    }

    AuthorShipStatus(int status) {
        this.status = status;
    }

    public static AuthorShipStatus resolve(int status) {
        for (AuthorShipStatus authorStatus : AuthorShipStatus.values()) {
            if (authorStatus.status() == status) {
                return authorStatus;
            }
        }
        return null;
    }
}
