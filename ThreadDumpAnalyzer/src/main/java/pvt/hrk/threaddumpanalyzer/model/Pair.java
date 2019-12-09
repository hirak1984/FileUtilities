package main.java.pvt.hrk.threaddumpanalyzer.model;

public class Pair<K,V> {

	K key;
	V value;
	
	
	public Pair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}


	public K getKey() {
		return key;
	}


	public V getValue() {
		return value;
	}
	
	
	
	
	
}
