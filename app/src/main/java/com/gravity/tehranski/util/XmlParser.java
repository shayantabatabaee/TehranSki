package com.gravity.tehranski.util;

import com.gravity.tehranski.business.model.ForeCast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public final class XmlParser {

    static ArrayList<ForeCast> tempArrayList;
    static ForeCast tempColumn ;
    public static ArrayList<ForeCast> ParseXml(Document xmldoc, String heightlevel){

        tempArrayList =  new ArrayList<>();

        NodeList periods = xmldoc.getDocumentElement().getElementsByTagName("period");
        int counter = 0;
        while (tempArrayList.size() < 6){

            Node period = periods.item(counter);
            Element xmlelement = (Element) period;

            if(counter == 0 || xmlelement.getElementsByTagName("_plcname").item(0).getTextContent().equals("morning")) {

                tempColumn = new ForeCast();
                tempColumn.set_plcname(xmlelement.getElementsByTagName("_plcname").item(0).getTextContent());
                tempColumn.set_pdayname(xmlelement.getElementsByTagName("_pdayname").item(0).getTextContent());

                NodeList hLevel = xmlelement.getElementsByTagName(heightlevel);
                Element xElement = (Element) hLevel.item(0);

                tempColumn.set_pmax(xElement.getElementsByTagName("_pmax").item(0).getTextContent());
                tempColumn.set_pmin(xElement.getElementsByTagName("_pmin").item(0).getTextContent());
                tempColumn.set_pminchill(xElement.getElementsByTagName("_pminchill").item(0).getTextContent());
                tempColumn.set_pprec(xElement.getElementsByTagName("_pprec").item(0).getTextContent()+" cm");
                tempColumn.set_psnow(xElement.getElementsByTagName("_psnow").item(0).getTextContent()+" cm");

                String _pwsymbol = xElement.getElementsByTagName("_pwsymbol").item(0).getTextContent();
                int index1 = _pwsymbol.indexOf('d') + 1;
                int index2 = _pwsymbol.indexOf('m');

                _pwsymbol = _pwsymbol.substring(index1,index2);

                tempColumn.set_pwsymbol(_pwsymbol);
                tempColumn.set_psymbol(xElement.getElementsByTagName("_psymbol").item(0).getTextContent());


                tempArrayList.add(tempColumn);

            }
            counter++;

        }
        return tempArrayList;

    }
}
