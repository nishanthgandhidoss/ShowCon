
import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

import org.apache.commons.lang.StringEscapeUtils;

 public class showConfiguration {
	public static void main(String[] args) {
		try {
			String action = "compare";
			File oldfxmlFile = new File("E:\\Work\\CEDS CR\\CR's\\Config\\configuration_master_taxonomy_test_3.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(oldfxmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("configuration_group");
			if(action.equalsIgnoreCase("display")) {
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						System.out.println("================");
						System.out.println("configuration_group");
						System.out.println(eElement.getAttribute("name"));
						NodeList childList=eElement.getChildNodes();
						for (int temp2 = 0; temp2 < childList.getLength(); temp2++) {
							Node childnNode = childList.item(temp2);
							if (childnNode.getNodeType() == Node.ELEMENT_NODE) {
								Element childeElement = (Element) childnNode;
								System.out.println("configuration_property");
								System.out.println(childeElement.getAttribute("name"));
								System.out.println(StringEscapeUtils.escapeHtml(childeElement.getTextContent()));
								System.out.println("================");
							}
						}
					}
				}
			}
			else if(action.equalsIgnoreCase("compare")) { 
				File newXmlFile = new File("E:\\Work\\CEDS CR\\CR's\\Config\\configuration_master_taxonomy_test_4.xml");
				DocumentBuilderFactory dbFactoryNew = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilderNew = dbFactoryNew.newDocumentBuilder();
				Document docNew = dBuilderNew.parse(oldfxmlFile);
				docNew.getDocumentElement().normalize();
				NodeList nListNew = docNew.getElementsByTagName("configuration_group");
				int changeCount = 0;
				for (int i = 0; i < nListNew.getLength(); i++) {
					Node newNode = nListNew.item(i);
					if (newNode.getNodeType() == Node.ELEMENT_NODE) {
						Element newElement = (Element) newNode;
						NodeList newChildList = newElement.getChildNodes();
						for (int j = 0; j < nList.getLength(); j++) {
							Node oldNode = nList.item(j);
							if (oldNode.getNodeType() == Node.ELEMENT_NODE) {
								Element oldElement = (Element) oldNode;
								if(newElement.getAttribute("name").equalsIgnoreCase(oldElement.getAttribute("name"))) {
									NodeList oldChildList = oldElement.getChildNodes();
									for (int i1 = 0; i1 < newChildList.getLength(); i1++) {
										Node newChildNode = newChildList.item(i1);
										if (newChildNode.getNodeType() == Node.ELEMENT_NODE) {
											Element newChildElement = (Element) newChildNode;
											for (int j1 = 0; j1 < oldChildList.getLength(); j1++) {
												Node oldChildNode = oldChildList.item(j1);
												if (oldChildNode.getNodeType() == Node.ELEMENT_NODE) {
													Element oldChildElement = (Element) oldChildNode;
													if (newChildElement.getAttribute("name").equalsIgnoreCase(oldChildElement.getAttribute("name"))) {
														String newChildElementContent = StringEscapeUtils.escapeHtml(newChildElement.getTextContent());
														String oldChildElementContent = StringEscapeUtils.escapeHtml(oldChildElement.getTextContent());
														if ( !(newChildElementContent.equalsIgnoreCase(oldChildElementContent))) {
															changeCount++;
															System.out.println(newChildElement.getAttribute("name") + " under " + newElement.getAttribute("name") + " group is Changed");
															System.out.println("============");
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (changeCount == 0)
					System.out.println("XML's are similar");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}