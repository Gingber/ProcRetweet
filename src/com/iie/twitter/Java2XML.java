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
 
       // 创建根节点 list;
        Element root = new Element("list");
      
       // 根节点添加到文档中；
        Document Doc = new Document(root);
 
       // 此处 for 循环可替换成 遍历 数据库表的结果集操作;
       //for (int i = 0; i < 5; i++) {
          
           // 创建节点 user;
           Element elements = new Element("user");
          
           // 给 user 节点添加属性 id;
           elements.setAttribute("id", "" + "0");
          
           // 给 user 节点添加子节点并赋值；
           // new Element("name")中的 "name" 替换成表中相应字段，setText("xuehui")中 "xuehui 替换成表中记录值；
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

 
           // 给父节点list添加user子节点;
           root.addContent(elements);
           root.addContent(elements1);
           root.addContent(edges);
 
       //}
        XMLOutputter XMLOut = new XMLOutputter();
      
       // 输出 user.xml 文件；
        XMLOut.output(Doc, new FileOutputStream("user.xml"));
    }
 
    public static void main(String[] args) {
       try {
    	   Java2XML j2x = new Java2XML();
           System.out.println("生成 mxl 文件...");
           j2x.BuildXMLDoc();
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
 
}

