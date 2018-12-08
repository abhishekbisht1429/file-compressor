import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.InputStream;
import java.util.Map;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

public class HuffmanEncoder {
  private static HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> pq) {
    System.out.println(pq.size());
    while(pq.size()>1) {
      HuffmanNode first = pq.poll();
      HuffmanNode second = pq.poll();
      HuffmanNode newNode = new HuffmanNode(Character.MIN_VALUE,first.getFrequency()+second.getFrequency(),first,second);
      pq.add(newNode);
    }
    return pq.poll();
  }

  public static void findCodes(HuffmanNode root,String s,Map<Character,String> codeMap) {
    if(root.getLeft() == null && root.getRight() == null) {
      codeMap.put(root.getChar(),s);
      return;
    }
    findCodes(root.getLeft(),s+"0",codeMap);
    findCodes(root.getRight(),s+"1",codeMap);
  }

  public static HuffmanNode generateCodes(InputStream inputStream,Map<Character,String> codeMap) {
    int b;
    char c;
    Map<Character,HuffmanNode> map = new HashMap<>();
    HuffmanNode root;
    try {
      while((b = inputStream.read())!=-1) {
        c = (char)b;
        //System.out.print(c);
        if(!map.containsKey(c)) {
          map.put(c,new HuffmanNode(c,1,null,null));
        } else {
          map.get(c).incrFreq();
        }
      }
      // add the dummy EOF character
      map.put('\u0000',new HuffmanNode('\u0000',1,null,null));
      root = buildHuffmanTree(new PriorityQueue<>(map.values()));
      findCodes(root,"",codeMap);
    }catch(IOException ioE) {
      ioE.printStackTrace();
      return null;
    }
    return root;
  }
}
