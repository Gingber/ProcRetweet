/**
 * 
 */
package com.iie.twitter;


import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;
 
public class Java2XML {
 
    public void BuildXMLDoc() throws IOException, JDOMException {
 
       // �������ڵ� list;
        Element root = new Element("list");
      
       // ���ڵ���ӵ��ĵ��У�
        Document Doc = new Document(root);
 
       // �˴� for ѭ�����滻�� ���� ���ݿ��Ľ��������;
       //for (int i = 0; i < 5; i++) {
          
           // �����ڵ� user;
           Element elements = new Element("user");
          
           // �� user �ڵ�������� id;
           elements.setAttribute("id", "" + "0");
          
           // �� user �ڵ�����ӽڵ㲢��ֵ��
           // new Element("name")�е� "name" �滻�ɱ�����Ӧ�ֶΣ�setText("xuehui")�� "xuehui �滻�ɱ��м�¼ֵ��
           elements.addContent(new Element("name").setText("xuehui"));
           elements.addContent(new Element("age").setText("28"));
           elements.addContent(new Element("sex").setText("Male"));

           Element elements1 = new Element("user");
           elements1.setAttribute("id", "" + "1");
           
           elements1.addContent(new Element("name").setText("hanmeimei"));
           elements1.addContent(new Element("age").setText("26"));
           elements1.addContent(new Element("sex").setText("Female"));
           
           Element edges = new Element("edges");
           
           edges.setAttribute("id", "0");
           edges.setAttribute("source", "0");
           edges.setAttribute("target", "1");
           
           edges.setAttribute("r", "255");
           edges.setAttribute("g", "0");
           edges.setAttribute("b", "0");

 
           // �����ڵ�list���user�ӽڵ�;
           root.addContent(elements);
           root.addContent(elements1);
           root.addContent(edges);
 
       //}
        XMLOutputter XMLOut = new XMLOutputter();
      
       // ��� user.xml �ļ���
        XMLOut.output(Doc, new FileOutputStream("user.xml"));
    }
 
    public static void main(String[] args) {
       try {
    	   Java2XML j2x = new Java2XML();
           System.out.println("���� mxl �ļ�...");
           j2x.BuildXMLDoc();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
 
}

