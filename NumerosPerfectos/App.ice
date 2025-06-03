module Demo {
    interface Suscriber {
	void onUpdate(string s);
    }

    interface Publisher{
	void addSuscriber(string name, Suscriber* o);
	void removeSuscriber(string name);
    }
}
