/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stas
 */
public class IssueCriteriaParser {

    public static List<NVPair> parse(String criteria) {
        List<NVPair> params = new ArrayList<>();
        String[] pairs = criteria.split(";");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (String pair : pairs) {
            String name = pair.split(":")[0];
            String value = pair.split(":")[1];
            if (name.equals("assignee_id") || name.equals("creator_id") || name.equals("project_id")) {
                params.add(new NVPair<String, Integer>(name, Integer.parseInt(value)));
            } else if (name.equals("status") || name.equals("type") || name.equals("priority")) {
                params.add(new NVPair<String, String>(name, value));
            } else if (name.equals("modification_from") || name.equals("modification_to") || 
                    name.equals("creation_from") || name.equals("creation_to") || 
                    name.equals("creation_date") || name.equals("modification_date")){
                
                try {
                    params.add(new NVPair<String, Date>(name, df.parse(value)));
                } catch (ParseException ex) {
                    Logger.getLogger(IssueCriteriaParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return params;
    }
}
