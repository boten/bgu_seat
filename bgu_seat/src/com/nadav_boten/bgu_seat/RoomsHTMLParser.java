package com.nadav_boten.bgu_seat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.util.Log;

public class RoomsHTMLParser {
	TagNode rootNode;
	ArrayList<Room> roomsState = new ArrayList<Room>();

    public RoomsHTMLParser(InputStream in) throws IOException
    {
    	HtmlCleaner cleaner = new HtmlCleaner();
//    	CleanerProperties props = cleaner.getProperties();
//        props.setAllowHtmlInsideAttributes(false);
//        props.setAllowMultiWordAttributes(true);
//        props.setRecognizeUnicodeChars(true);
//        props.setOmitComments(true);
//        props.setTranslateSpecialEntities(true);
//        props.setTransResCharsToNCR(true);
//        props.setRecognizeUnicodeChars(true);
        rootNode = cleaner.clean(in,"windows-1255");
     
    }
    
//    @SuppressWarnings("deprecation")
//	public RoomsHTMLParser(URL url) throws IOException
//    {
//        HtmlCleaner cleaner = new HtmlCleaner();
//        rootNode = cleaner.clean(url,"UTF-8");
//
//    }
    
    public void getRoomsStates() {
    	List<TagNode> roomsList = getRoomsTagNodesList();
    	for (Iterator<TagNode> iterator = roomsList.iterator(); iterator.hasNext();)
        {
            TagNode trElement = (TagNode) iterator.next();
            TagNode[] cells = trElement.getElementsByName("td", false);
            if (cells.length == 5) {
            	Room result = new Room();
            	
            	Pattern p = Pattern.compile("(\\d+)");
            	Matcher m = p.matcher(cells[3].getText().toString());
            	
            	result.building = cells[0].getText().toString();
            	result.room = cells[1].getText().toString();
            	Log.v("Rooms", result.room);
            	if (m.find()) {
            		result.occupied = Integer.parseInt(m.group());
            	} else {
            		result.occupied = 0;
            	}
            	if (m.find()) {
            		result.total = Integer.parseInt(m.group());
            	} else {
            		result.total = 0;
            	}
            	
            	TagNode[] links = cells[4].getElementsByName("a", false);
        		result.link = links[0].getAttributeByName("href");
            	roomsState.add(result);
            }
        }
    }

    public List<TagNode> getRoomsTagNodesList()
    {
        List<TagNode> roomsList = new ArrayList<TagNode>();

        TagNode rowElements[] = rootNode.getElementsByName("tr", true);
        for (int i = 1; rowElements != null && i < rowElements.length; i++)
        {
            roomsList.add(rowElements[i]);
        }

        return roomsList;
    }
    
    public ArrayList<Room> get_room_list(){
    	
    	return roomsState;
    	
    	
    	
    }
    
   
}
