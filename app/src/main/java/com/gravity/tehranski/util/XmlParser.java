package com.Gravity.Tehranski.util;

import com.Gravity.Tehranski.business.model.ForeCast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public final class XmlParser {

    static ArrayList<ForeCast> temparraylist;
    static ForeCast tempColumn ;
    public static ArrayList<ForeCast> ParseXml(Document xmldoc, String heightlevel){

        temparraylist =  new ArrayList<>();

        NodeList periods = xmldoc.getDocumentElement().getElementsByTagName("period");
        int counter = 0;
        while (temparraylist.size() < 6){

            Node period = periods.item(counter);
            Element xmlelement = (Element) period;

            if(counter == 0 || xmlelement.getElementsByTagName("_plcname").item(0).getTextContent().equals("morning")) {

                tempColumn = new ForeCast();
                tempColumn.set_plcname(xmlelement.getElementsByTagName("_plcname").item(0).getTextContent());
                tempColumn.set_pdayname(xmlelement.getElementsByTagName("_pdayname").item(0).getTextContent());

                NodeList hlevel = xmlelement.getElementsByTagName(heightlevel);
                Element xelement = (Element) hlevel.item(0);

                tempColumn.set_pmax(xelement.getElementsByTagName("_pmax").item(0).getTextContent());
                tempColumn.set_pmin(xelement.getElementsByTagName("_pmin").item(0).getTextContent());
                tempColumn.set_pminchill(xelement.getElementsByTagName("_pminchill").item(0).getTextContent());
                tempColumn.set_pprec(xelement.getElementsByTagName("_pprec").item(0).getTextContent()+" cm");
                tempColumn.set_psnow(xelement.getElementsByTagName("_psnow").item(0).getTextContent()+" cm");

                String _pwsymbol = xelement.getElementsByTagName("_pwsymbol").item(0).getTextContent();
                int index1 = _pwsymbol.indexOf('d') + 1;
                int index2 = _pwsymbol.indexOf('m');

                _pwsymbol = _pwsymbol.substring(index1,index2);

                tempColumn.set_pwsymbol(_pwsymbol);
                tempColumn.set_psymbol(xelement.getElementsByTagName("_psymbol").item(0).getTextContent());


                temparraylist.add(tempColumn);

            }
            counter++;

        }
        return temparraylist ;

    }
}
