package ch.rmbi.melspass.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import ch.rmbi.melspass.R;

public class IconList {


    private IconList(){
        ;
    }
    public static IconList getInstance(Context context) {
        if (instance == null){
            instance = new IconList();
        }
        if (instance.icons == null) {
            instance.loadIcons(context);
        }
        return instance;
    }

    private List<Icon> icons = null;

    public List<Icon> getIconList() {
        return icons;
    }

    public Icon getDefaultIcon(){
        for (int i = 0 ; i<icons.size();i++)
        {
            if (icons.get(i).isDefault()){
                return icons.get(i);
            }
        }
        return null;
    }

    public Icon getIcon(int index) {

        Icon def = null ;
        for (int i = 0 ; i<icons.size();i++)
        {
            if (icons.get(i).isDefault()){
                def = icons.get(i);
            }
            if (icons.get(i).getIndex() == index){
                return icons.get(i);
            }
        }
        return def;
    }


    private void loadIcons(Context context)  {
        icons = new ArrayList<>();

        //XmlResourceParser xmlResourceParser =  context.getResources().getXml(R.xml.icon_picker_list);
        InputSource inputSource = new InputSource(context.getResources().openRawResource(R.raw.icon_picker_list));
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            NodeList nodes = (NodeList)xPath.evaluate("/Icons/Icon",inputSource, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
            {
                icons.add(new Icon(context,nodes.item(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static IconList instance = null;


}
