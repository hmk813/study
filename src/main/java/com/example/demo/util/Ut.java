package com.example.demo.util;

public class Ut {
  public static boolean empty(Object obj) { //범용적으로 받으려고 Object로 했다.
    if(obj == null){
      return true;
    }

    //넘겨받은것을 String이 아니라면 return true이다.
    if(obj instanceof  String == false){ //
      return true;
    }

    String str = (String) obj;

    return str.trim().length() == 0;
  }

  public static String f(String format, Object... args) {
    return String.format(format, args);
  }
}
