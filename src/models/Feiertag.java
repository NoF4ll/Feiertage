package models;

import java.time.LocalDate;
import java.sql.Date;

public class Feiertag {
	private LocalDate datum;
	private String wochentag;
	
	public Feiertag(final LocalDate datum) {
		this.datum = datum;
		this.wochentag = datum.getDayOfWeek().name();
	}
	
	public LocalDate getDatum() {
		return this.datum;
	}
	  
	public Date getSQLDatum() {
		return Date.valueOf(this.datum);
	}
	
	public String getWochentag() {
		return this.wochentag;
	}
	
}
