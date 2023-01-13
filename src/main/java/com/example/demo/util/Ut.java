package com.example.demo.util;

public class Ut { //간편하게 해주려고 유틸리티 Ut를 만들었다!
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

  public static String jsHistoryBack(String msg) { //뒤로가기@@!

    if( msg == null ){
      msg = "";
    }

    return Ut.f("""
                <script>
                const msg = '%s'.trim();
                if( msg.length > 0 ){
                  alert(msg);                
                }
                history.back();
                </script>
                """, msg);
  }

  public static String jsReplace(String msg, String uri) { //돌려보내는 것!
    if( msg == null ){
      msg = "";
    }

    if (uri == null){
      uri  = "";
    }

    return Ut.f("""
                <script>
                const msg = '%s'.trim();
                if( msg.length > 0 ){
                  alert(msg);                
                }
                location.replace('%s');
                </script>
                """, msg, uri);
  }


}
