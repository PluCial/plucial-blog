package com.appspot.plucial.dao;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserUrlsModelDaoTest extends AppEngineTestCase {

    private UserUrlsModelDao dao = new UserUrlsModelDao();

    @Test
    public void test() throws Exception {
        assertThat(dao, is(notNullValue()));
    }
}
