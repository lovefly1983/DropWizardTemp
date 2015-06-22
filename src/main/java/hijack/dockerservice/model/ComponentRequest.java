package hijack.dockerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import hijack.dockerservice.ActionHandler.IActionHandler;
import hijack.dockerservice.ActionHandler.JavaBridgeActionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * User:    cwang
 * Date:    1/21/15
 * Time:    4:36 PM
 * Purpose:
 */
public class ComponentRequest {
    public static final String LOG = "log";
    public static final String BUILD = "build";
    public static final String REBUILD = "rebuild";
    public static final String START = "start";
    public static final String STOP = "stop";
    public static final String RESTART = "restart";
    public static final List<String> supportedOperations = new ArrayList<String>(Arrays.asList(new String[]{LOG, BUILD, REBUILD, START, STOP, RESTART}));

    /**
     * Components
     */
    public static final String DOCKER = "docker";
    public static final String MYSQL = "mysql";
    public static final String HTTPD = "httpd";
    public static final String JAVABRIDGE = "javaBridge";
    public static final String OLAPSTICH = "olapStitch";
    public static final String SPRINGSERVER = "springServer";
    public static final String ACDC = "acdc";
    public static final String LINKINGSERVICE = "linkingService";
    public static final String DRIPDROP = "dripDrop";
    public static final Map<String, IActionHandler> handlers = new HashMap<String, IActionHandler>();
    static {
        handlers.put(DOCKER, new JavaBridgeActionHandler());
        // TODO: add more handlers for each component
    }


    @JsonProperty
    private String operation;

    @JsonProperty
    public String getOperation() {
        return operation;
    }

    @JsonProperty
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ComponentRequest() {
        // no op
    }

    public ComponentRequest(String operation) {
        this.operation = operation;
    }

    public boolean isVaildOperation(String operation) {
        return supportedOperations.contains(operation);
    }

    public static Map<String, IActionHandler> getHandlers() {
        return handlers;
    }


}
