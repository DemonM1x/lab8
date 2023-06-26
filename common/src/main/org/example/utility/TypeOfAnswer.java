package main.org.example.utility;

import java.io.Serializable;

public enum TypeOfAnswer implements Serializable {
    OBJECTNOTEXIST,
    DUPLICATESDETECTED,
    ISNTMAX,
    ISNTMIN,
    PERMISSIONDENIED,
    SUCCESSFUL,
    SQLPROBLEM,
    EMPTYCOLLECTION,
    SERVERNOTAVAILABLE,
    NETPROBLEM,
    ANOTHERVERSION,
    COMMANDNOTGO,
    NOTSERIALIZED,
    ALREADYREGISTERED,
    NOTMATCH,
    NOTVALIDATE,
    RECURSIONDETECTED,
    NOGREATER,
    NOLOWER
}
