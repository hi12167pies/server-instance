package cf.pies.server.action;

public enum Action {
    TEST, // Used to test stuff
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
