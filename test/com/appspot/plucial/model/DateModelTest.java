package com.appspot.plucial.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DateModelTest extends AppEngineTestCase {

    private DateModel model = new DateModel();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}