module Demo {
  sequence<int> IntSeq;
  interface Suscriber {
      IntSeq onUpdate(int min, int max);
  }

  interface Publisher {
      void addSuscriber(string name, Suscriber* o);
      void removeSuscriber(string name);
      void sendNumbers(string name, int min, int max, int nodes);
  }
}
