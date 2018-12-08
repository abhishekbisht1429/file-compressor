class HuffmanNode implements Comparable<HuffmanNode> {
  char c;
  int freq;
  HuffmanNode left,right;

  HuffmanNode(char c,int freq,HuffmanNode left,HuffmanNode right) {
    this.c = c;
    this.freq = freq;
    this.left = left;
    this.right = right;
  }
  void incrFreq() {
    ++this.freq;
  }
  char getChar() {
    return c;
  }

  int getFrequency() {
    return freq;
  }

  void setLeft(HuffmanNode left) {
    this.left = left;
  }
  void setRight(HuffmanNode right) {
    this.right = right;
  }
  HuffmanNode getLeft() {
    return left;
  }
  HuffmanNode getRight() {
    return right;
  }

  boolean isLeaf() {
    return (getLeft()==null && getRight()==null);
  }

  @Override
  public boolean equals(Object o) {
    return getChar() == ((HuffmanNode)o).getChar();
  }

  @Override
  public int compareTo(HuffmanNode n) {
    return getFrequency() - n.getFrequency();
  }
}
