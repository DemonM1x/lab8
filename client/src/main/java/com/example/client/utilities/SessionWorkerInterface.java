package com.example.client.utilities;

import main.org.example.utility.Session;

import java.io.IOException;

public interface SessionWorkerInterface {

    Session getSession() throws IOException;
}
