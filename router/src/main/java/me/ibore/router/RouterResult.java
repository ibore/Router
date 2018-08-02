package me.ibore.router;

public class RouterResult {
    public static final int STATUS_ERROR = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_NOT_FOUND = 2;
    public static final int STATUS_NOT_FINISH = 3;

    private int code;
    private String reason;
    private Object target;

    public RouterResult(int code, String reason, Object target) {
        this.code = code;
        this.reason = reason;
        this.target = target;
    }

    public static RouterResult error(String reason) {
        return new RouterResult(STATUS_ERROR, reason, null);
    }

    public static RouterResult notFound(String url) {
        return new RouterResult(STATUS_NOT_FOUND, "Page not found: " + url, null);
    }

    public static RouterResult success(Object target) {
        return new RouterResult(STATUS_SUCCESS, "success", target);
    }

    public static RouterResult success() {
        return success(null);
    }

    public static RouterResult notFinished() {
        return new RouterResult(STATUS_NOT_FINISH, "", null);
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public Object getTarget() {
        return target;
    }

    public boolean isSuccess() {
        return getCode() == STATUS_SUCCESS;
    }

    public boolean isFinished() {
        return getCode() != STATUS_NOT_FINISH;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RouterResult) {
            return this.code == ((RouterResult) obj).code && this.target == ((RouterResult) obj).target;
        }
        return false;
    }

    @Override
    public String toString() {
        switch (code) {
            case STATUS_ERROR:
                return "Result: ERROR, " + reason;
            case STATUS_SUCCESS:
                if (target == null) {
                    return "Result: SUCCESS";
                } else {
                    return "Result: SUCCESS, " + target;
                }
            case STATUS_NOT_FOUND:
                return "Result: NOT_FOUND, " + reason;
            case STATUS_NOT_FINISH:
                return "Result: NOT_FINISH";
        }
        return "Result: UNKNOWN";
    }
}
