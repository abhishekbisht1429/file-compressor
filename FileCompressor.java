import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.DataOutputStream;
public class FileCompressor {
  static void compress(File inputFile,File outputFile) {
    try(BitOutputStream bos = new BitOutputStream(new FileOutputStream(outputFile));
              FileInputStream fis = new FileInputStream(inputFile)) {
      Map<Character,String> codeMap = new HashMap<>();
      HuffmanNode root = HuffmanEncoder.generateCodes(new FileInputStream(inputFile),codeMap);
      storeCodes(bos,root);
      int b;
      while((b=fis.read())!=-1) { //read bytes from the file
        String code = codeMap.get((char)b);// get the corresponding huffman code
        //System.out.print(b+" : "+code+" : ");
        for(int i=0;i<code.length();++i) {
          //System.out.print(Integer.parseInt(code.substring(i,i+1)));
          bos.writeBit(Integer.parseInt(code.substring(i,i+1)));//write code to the file
        }
        //System.out.println();
      }
      //adding code for eof
      String eofCode = codeMap.get('\u0000');
      for(int i=0;i<eofCode.length();++i) {
        bos.writeBit(Integer.parseInt(eofCode.substring(i,i+1)));//write code for eof to the file
      }
    } catch(IOException ioE) {
      ioE.printStackTrace();
    }
  }

  private static void storeCodes(BitOutputStream bos,HuffmanNode root) {
    try {
      if(root.getLeft() == null && root.getRight() == null) {
        //System.out.println("1 "+(int)root.getChar());
        bos.writeBit(1);
        bos.write(root.getChar());
      } else {
        //System.out.print("0");
        bos.writeBit(0);
        storeCodes(bos,root.getLeft());
        storeCodes(bos,root.getRight());
      }
    } catch(IOException ioE) {
      ioE.printStackTrace();
    }
  }
}
