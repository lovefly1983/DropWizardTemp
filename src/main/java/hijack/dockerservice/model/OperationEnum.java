package hijack.dockerservice.model;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/22/15
 * Time:    2:13 PM
 * Purpose:
 */
public enum OperationEnum {
    LOG("log"),
    BUILD("build"),
    REBUILD("rebuild"),
    START("start"),
    STOP("stop"),
    RESTART("restart");

    private String operation;
    OperationEnum(String operation) {
        this.operation = operation;
    }
}
