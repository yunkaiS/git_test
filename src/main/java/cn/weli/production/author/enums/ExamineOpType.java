package cn.weli.production.author.enums;

public enum ExamineOpType {

    input(0),

    login(1),

    update(2),

    examine(3)
    ;

    private int status;

    ExamineOpType(int status){
        this.status =status;
    }

    public int getStatus() {
        return status;
    }

    public int status() {
        return status;
    }
}
