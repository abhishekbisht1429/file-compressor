import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.IOException;
class Main {
  public static void main(String[] args) {
    if(args.length!=3) {
      System.out.println("Invalid argument length");
      return;
    }
    File inputFile = new File(args[0]);
    File outputFile = new File(args[1]);
    int opt = Integer.parseInt(args[2]);
    if(opt == 1)
      FileCompressor.compress(inputFile,outputFile);
    else
      FileDecompressor.decompressFile(inputFile,outputFile);
  }
}
