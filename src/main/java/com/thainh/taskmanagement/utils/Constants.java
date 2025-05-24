package com.thainh.taskmanagement.utils;

public class Constants {

    public static final String CREATE_SUCCESS = "Created successfully";
    public static final String UPDATE_SUCCESS = "Updated successfully";
    public static final String DELETE_SUCCESS = "Deleted successfully";
    public static final String STATUS_200 = "200";
    public static final String STATUS_201 = "201";

    public enum SEVERITY {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public enum STATUS {
        OPEN, IN_PROGRESS, DONE
    }

    public enum CATEGORY {
        BUG(0),
        FEATURE(1);

        private int code;

        private CATEGORY(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
