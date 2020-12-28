package de.liferando.api.responses;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class CoopResponse {

    @SerializedName("action")
    private String action;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Integer data;

    public String getAction() {
        return action;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoopResponse that = (CoopResponse) o;
        return data == that.data &&
                Objects.equals(action, that.action) &&
                Objects.equals(success, that.success) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, success, message, data);
    }

    @Override
    public String toString() {
        return "CoopResponse{" +
                "action='" + action + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
