/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stasko.itracker.controller.parser;

import utils.NVPair;
import utils.IssueCriteriaParser;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Stas
 */
public class IssueCriteriaParserTest {

    private final List<NVPair> expResult = new ArrayList<>();

    public IssueCriteriaParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            expResult.add(new NVPair("creation_date", df.parse("2012-12-12")));
            expResult.add(new NVPair("modification_date", df.parse("2012-12-13")));
            expResult.add(new NVPair("creator_id", 123));
            expResult.add(new NVPair("project_id", 234));
            expResult.add(new NVPair("type", "issue"));
            expResult.add(new NVPair("status", "open"));
            expResult.add(new NVPair("priority", "high"));
        } catch (ParseException ex) {
            Logger.getLogger(IssueCriteriaParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(expResult);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of Parse method, of class IssueParser.
     */
    @Test
    public void testParseRightData() {
        String criteria = "creation_date:2012-12-12;modification_date:2012-12-13;creator_id:123;project_id:234;type:issue;status:open;priority:high";
        List<NVPair> result = IssueCriteriaParser.parse(criteria);
        System.out.println(result);
        assertEquals(expResult, result);
    }

}
