package com.appspot.plucial.controller.task;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GetActivitysTaskControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/task/GetActivitysTask");
        ActivitysBotTaskController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
