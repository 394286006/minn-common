package p.minn.common.type;

import java.util.EnumSet;

/**
 * @author minn
 * @QQ:394286006
 *
 */
public enum LoginType {
     SSH(1),HTTP(2),PWD(3),QRCODE(4),THIRDPART(5),Undefined(0);
     
     public int type;
     
     private static EnumSet<LoginType> currEnumSet = EnumSet.range(PWD, THIRDPART);
     
     private LoginType(int type){
       this.type=type;
     }
     public static LoginType valueOf(int type){
       LoginType temp=LoginType.Undefined;
       for (LoginType loginType : currEnumSet) {
            if(loginType.type==type){
              temp=loginType;
              break;
            }
       }
       return temp;
     }
}
