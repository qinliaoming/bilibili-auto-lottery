package org.carcinus.tools.bean.response.relation;

public enum RelationModifyActionType {
    FOLLOW("1"),
    UN_FOLLOW("2"),
    QUIETLY_FOLLOW("3"),
    UN_QUIETLY_FOLLOW("4"),
    PULL_BLACK("5"),
    MOVE_BLACK("6");

    private String value;

    public String getValue() {
        return value;
    }

    RelationModifyActionType(String value) {
        this.value = value;
    }
}
