module Demo {
  interface Suscriber {
      void onUpdate(string min, string max);
  }
  interface Publisher {
      void addSuscriber(string name, Suscriber* o);
      void removeSuscriber(string name);
  }
}