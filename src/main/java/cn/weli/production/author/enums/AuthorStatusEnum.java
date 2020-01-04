package cn.weli.production.author.enums;

public enum AuthorStatusEnum {

    pending_check(0),

    valid(1),

    locked(2),

    rejected(3)
    ;

    private int status;

    AuthorStatusEnum(int status){
        this.status =status;
    }

    public int getStatus() {
        return status;
    }

    public int status() {
        return status;
    }
}
