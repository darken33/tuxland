package darken.games.utils ;

/**
* Title:
* Description:	 Classe d'encodage et de decodage utilisant l'algorithme UUEncodage
* Copyright:    Copyright (c) 2001
* Company:
* @author	Laurent Burger
* @version 1.0
*/

public class Encoder
{

   public Encoder()
   {
   }

  /** encode un atom de 3 bytes et renvoie 4 bytes
   * @param byte[3]
   * @return byte[4]
   */
   private static String encodeAtom(byte[] atom)
   {
      byte a,b,c;
      int c1, c2, c3, c4;
      byte[] result = new byte[4];
      a = atom[0];
      b = atom[1];
      c = atom[2];
      c1 = 0x20 + (( a >> 2 ) & 0x3F);
      c2 = 0x20 + (((a << 4)  | ((b >> 4) & 0xF)) & 0x3F);
      c3 = 0x20 + (((b << 2)  | ((c >> 6) & 0x3)) & 0x3F);
      c4 = 0x20 + (( c ) & 0x3F);
      result[0] = (byte)c1;
      result[1] = (byte)c2;
      result[2] = (byte)c3;
      result[3] = (byte)c4;
      return new String(result);
   }

   /** encode une chaîne de caractère avec la méthode d'UUEncodage
   * @param String contient la chaîne à encoder
   * @return String	: contient la chaîne encodée
   */

   public static String encode(String str)
   {
      String result = "";
      byte toEncode[] = str.getBytes();
      byte[] tmp = new byte[3];
      int strBound = str.length() -1;
      for (int i=0; i<str.length(); i+=3)
      {
         if (i > strBound) tmp[0] = 0;
         else tmp[0] = toEncode[i];
         if (i+1 > strBound) tmp[1] = 0;
         else tmp[1] = toEncode[i+1];
         if (i+2 > strBound) tmp[2] = 0;
         else tmp[2] = toEncode[i+2];
         result += encodeAtom(tmp);
      }
      return result;
   }

   /** decode un atom de 3 bytes et renvoie 4 bytes
   * @param byte[4]
   * @return byte[3]
   */
   private static String decodeAtom(byte[] atom)
   {
      byte a,b,c;
      byte[] decoded = new byte[3];
      int size = 0;
      decoded[0] = (byte)((((atom[0]-0x20) << 2) & 0xfc) | (((atom[1]-0x20) >>> 4) & 3) );
      decoded[1] = (byte)((((atom[1]-0x20) << 4) & 0xf0) | (((atom[2]-0x20) >>> 2) & 0xf));
      decoded[2] = (byte)((((atom[2]-0x20) << 6) & 0xc0) | ((atom[3]-0x20) & 0x3f));
      return new String(decoded);
   }

   /** decode une chaîne de caractère avec la méthode d'UUEncodage
   * @param String contient la chaîne encodée
   * @return String : contient la chaîne décodée
   */
   public static String decode(String str) throws Exception
   {
      String result = "";
      if ((str.length()== 0))
      {
         return null;
      }

      byte[] toDecode = str.getBytes();
      byte[] tmp = new byte[4];
      for (int i=0; i<str.length(); i+=4)
      {
         tmp[0] = toDecode[i];
         tmp[1] = toDecode[i+1];
         tmp[2] = toDecode[i+2];
         tmp[3] = toDecode[i+3];
         result += decodeAtom(tmp);
      }
      int i = result.length() -1;
      while (result.getBytes()[i] == 0)
      {
         i--;
      }
      if (i != result.length() -1)
      {
         result = result.substring(0, i+1);
      }
      return result;
   }
}