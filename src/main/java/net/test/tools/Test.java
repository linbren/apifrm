package net.test.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<String> listWithDup = new ArrayList<String>();
		listWithDup.add("1");
		listWithDup.add("2");
		listWithDup.add("3");
		listWithDup.add("1");
		listWithDup.add("1");
		listWithDup.add("1");		
		List<String> listWithoutDup = new ArrayList<String>(
				new HashSet<String>(listWithDup));
		System.out.println("list with dup:" + listWithDup);
		System.out.println("list without dup:" + listWithoutDup);
		System.out.println(removeDuplicate(listWithDup));
	}

//list 集合去重
	public static List removeDuplicate(List dupList)
	{
	    HashSet hs=new HashSet(dupList);
	    dupList.clear();
	    dupList.addAll(hs);
	    return dupList;
	}	
}