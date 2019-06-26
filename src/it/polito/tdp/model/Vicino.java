package it.polito.tdp.model;

public class Vicino implements Comparable<Vicino>{
	private Integer dis;
	private Double distance;
	public Vicino(Integer dis, Double distance) {
		super();
		this.dis = dis;
		this.distance = distance;
	}
	public Integer getDis() {
		return dis;
	}
	public void setDis(Integer dis) {
		this.dis = dis;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(Vicino v) {
		return this.distance.compareTo(v.getDistance());
	}
	
}
