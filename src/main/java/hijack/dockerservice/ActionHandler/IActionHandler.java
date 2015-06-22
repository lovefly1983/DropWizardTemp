package hijack.dockerservice.ActionHandler;

import hijack.dockerservice.model.OperationEnum;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/22/15
 * Time:    2:05 PM
 * Purpose:
 */
public interface IActionHandler {
    boolean doAction(OperationEnum operation);
}
