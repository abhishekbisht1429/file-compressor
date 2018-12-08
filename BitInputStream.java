import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
class BitInputStream implements AutoCloseable {
  private InputStream in;
  private char bitBuffer;
  private int remainingBits;
  private char bitmask = 0x0080;

  BitInputStream(InputStream in) {
    this.in = in;
    bitBuffer = 0x0000;
    remainingBits = 0;
  }

  public int readBit() throws IOException {
    if(remainingBits == 0) {
      int b = in.read();
      if(b==-1) {
        bitBuffer = 0x0000;
        return -1;
      }
      bitBuffer = (char)b; //read on byte into the buffer
      //System.out.println("read : "+(int)bitBuffer);
      remainingBits = 8;
    }
    int bit = (bitmask & bitBuffer)>>7;
    bitBuffer = (char)(bitBuffer<<1);
    --remainingBits;

    return bit;
  }
  public int read() throws IOException {
    char x = 0x0080;
    int data = 0x00000000;
    for(int i=0;i<8;++i) {
      if(readBit() == 1)
        data = (data | x);
      x = (char)(x>>1);
    }
    return data;
  }

  @Override
  public void close() throws IOException {
    bitBuffer = 0x0000;
    remainingBits = 0;
    in.close();
  }
}
