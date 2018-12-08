import java.io.OutputStream;
import java.io.IOException;
import java.io.DataOutputStream;

class BitOutputStream implements AutoCloseable {
  private char bitBuffer;
  private int filled;
  private char x;
  private OutputStream out;
  private long written;

  public BitOutputStream(OutputStream out) {
    this.out = out;
    bitBuffer =(char)0x0000;
    x =(char)0x0080;
    filled = 0;
    written = 0;
  }

  public void writeBit(int bit) throws IOException{
    //System.out.print(bit+" ");
    //System.out.print((int)x+" ");
    if(bit == 1)
      bitBuffer =(char)(bitBuffer | x);
    ++filled;
    x = (char)(x>>1);
    if(filled == 8) {
      //System.out.println("filled "+bitBuffer);
      out.write(bitBuffer);
      filled = 0;
      bitBuffer = 0x0000;
      x = 0x0080;
      ++written;
      //System.out.println("filled");
    }
  }
  public long getWritten() {
    return written;
  }
  public void write(int n) throws IOException{
    int bitmask = 0x00000080;
    for(int i=0;i<8;++i) {
      int bit = (n & bitmask)>>7;
      this.writeBit(bit);
      n = n<<1;
    }
  }
  public void flush() throws IOException {
    //System.out.println("buffer flushed : "+(int)bitBuffer);
    out.write(bitBuffer);
    ++written;
    bitBuffer = 0x0000;
    x = 0x0080;
    filled = 0;
    out.flush();
  }

  @Override
  public void close() throws IOException {
    //System.out.println("closing the stream "+bitBuffer)
    flush();
    out.close();
  }
}
