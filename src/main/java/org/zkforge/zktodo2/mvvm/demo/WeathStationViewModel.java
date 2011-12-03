package org.zkforge.zktodo2.mvvm.demo;

import java.util.ArrayList;
import java.util.List;

/*
ZK.forge is distributed under Lesser GPL Version see also http://www.gnu.org/licenses/lgpl.html
 */
public class WeathStationViewModel {
	
	public WeathStationViewModel(){
		for( int i = 0; i < 10; i++ ) {
			Station s = Station.random();
			stations.add(s);
		}
	}
	
	Station station = new Station();
	
	public boolean getStationIsNotNew(){
		return this.stations.contains(station);
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	List<Station> stations = new ArrayList<Station>();

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	public void create() {
		this.station = new Station();
		return;
	}
	
	public void add(){
		if( !this.stations.contains(station)) {
			this.stations.add(this.station);
		}
		return;
	}
	
	public boolean isValidToAdd(){
		boolean validToAdd = false;
		if( this.station != null ){
			if( this.station.getName() != null ){
				if( !this.station.getName().equals("")){
					validToAdd = true;
				}
			}
		}
		return validToAdd;
	}
	
	public boolean isNotValidToAdd(){
		return !this.isValidToAdd();
	}
	
}
