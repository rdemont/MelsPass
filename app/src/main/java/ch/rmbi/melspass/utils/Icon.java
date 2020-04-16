package ch.rmbi.melspass.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import ch.rmbi.melspass.R;

public class Icon {
    private int index;
    private boolean isDefault;
    private Drawable drawable;
    private String description;

    public Icon(Context context, Node node) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        index = ((Double)xPath.evaluate("@index",node, XPathConstants.NUMBER)).intValue();
        isDefault = (boolean)xPath.evaluate("@default",node, XPathConstants.BOOLEAN);
        String draw = (String)xPath.evaluate("Drawable/text()",node, XPathConstants.STRING);
        String desc = (String)xPath.evaluate("String/text()",node, XPathConstants.STRING);

        if (draw != null)
        {
            drawable = context.getResources().getDrawable(Tools.getRessourceId(draw, R.drawable.class),null);
        }

        if (desc != null)
        {
            description = context.getResources().getString(Tools.getRessourceId(desc, R.string.class),null);
        }

    }

    public int getIndex() {
        return index;
    }


    public boolean isDefault() {
        return isDefault;
    }


    public Drawable getDrawable() {
        return drawable;
    }


    public String getDescription() {
        return description;
    }



}
