package transport;

import java.util.Date;

public abstract class PublicTransport extends TransportMode{
	
	public float price; 
	public Date startHour; 
	public Date endHour; 
	public int frequency;
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public Date getStartHour() {
		return startHour;
	}
	
	public void setStartHour(Date startHour) {
		this.startHour = startHour;
	}
	
	public Date getEndHour() {
		return endHour;
	}
	
	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	
	

}
