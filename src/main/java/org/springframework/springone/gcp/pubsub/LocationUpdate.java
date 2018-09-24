package org.springframework.springone.gcp.pubsub;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationUpdate {

	private double latitude;

	private double longitude;

	@JsonProperty("ride_id")
	private UUID rideId;

	private Date timestamp;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public UUID getRideId() {
		return rideId;
	}

	public void setRideId(UUID rideId) {
		this.rideId = rideId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "LocationUpdate{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				", rideId=" + rideId +
				", timestamp=" + timestamp +
				'}';
	}

}
