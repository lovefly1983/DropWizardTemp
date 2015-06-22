package hijack.dockerservice.ActionHandler;

import hijack.dockerservice.model.OperationEnum;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/22/15
 * Time:    2:10 PM
 * Purpose:
 */
public class JavaBridgeActionHandler implements IActionHandler {

    @Override
    public boolean doAction(OperationEnum operation) {
        switch (operation) {
            case LOG:
                break;
            case START:
                break;
            default:
                break;
        }
        return true;
    }
}
