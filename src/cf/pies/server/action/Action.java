package cf.pies.server.action;

public enum Action {
    // Used to test stuff
    TEST,

    // General
    ECHO,
    EXIT,
    RELOAD_CONFIG,

    // Instance
    START_INSTANCE,
    STOP_INSTANCE,
    LIST_INSTANCE,
    OUT_INSTANCE,
    CONNECT_INSTANCE,
    SEND_INSTANCE
}
