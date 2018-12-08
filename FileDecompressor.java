import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;
class FileDecompressor {
  public static void decompressFile(File inputFile,File outputFile) {
    try(BitInputStream bis = new BitInputStream(new FileInputStream(inputFile));
        FileOutputStream fos = new FileOutputStream(outputFile)) {
      //System.out.println("\nReading Huffman Codes");
      HuffmanNode root = readHuffmanCodes(bis);//read huffman codes from the file
      //and construct a huffman tree
      int bit;
      HuffmanNode temp = root;
      //System.out.println("\nDecompressed Output");
      while((bit=bis.readBit())!=-1) {
        //System.out.print(bit);
        if(bit==0 && temp.getLeft()!=null)
          temp = temp.getLeft();
        else if(temp.getRight()!=null)
          temp = temp.getRight();

        if(temp.isLeaf()) {
          char c = temp.getChar();
          if(c=='\u0000') { //check for eofCode
            //System.out.println("Reached end of compressed file");
            break;
          }
          fos.write(temp.getChar());
          temp = root;
        }
      }
    }catch(IOException ioE) {
      ioE.printStackTrace();
    }
  }
  private static HuffmanNode readHuffmanCodes(BitInputStream bis) {
    try {
      if(bis.readBit() == 1) {
        //System.out.print("1 ");
        char c = (char)bis.read();
        return new HuffmanNode(c,0,null,null);
      } else {
        //System.out.print("0");
        HuffmanNode left = readHuffmanCodes(bis);
        HuffmanNode right = readHuffmanCodes(bis);
        return new HuffmanNode('\u0000',0,left,right);
      }
    } catch(IOException ioE) {
      ioE.printStackTrace();
      return null;
    }
  }
}
