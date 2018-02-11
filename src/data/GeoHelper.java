package data;

public class GeoHelper {

	public static final double EARTH_RADIUS_IN_KM = 6.371;

	public static double calcGeoDistanceInKm(double lat1, double lat2, double lon1, double lon2){
		double dlat = Math.abs(lat1-lat2);
		double dlon = Math.abs(lon1-lon2);
		double a = Math.pow((Math.sin(dlat/2)), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow((Math.sin(dlon/2)),2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return EARTH_RADIUS_IN_KM * c;
	}

	public static double calcGeoDistanceInKm(Location location1, Location location2){
		return calcGeoDistanceInKm(location1.latitude, location2.latitude, location1.longitude, location2.longitude);
	}

	public static double calcAngleBetweenGeoLocationsInRadians(double lat1, double lat2, double lon1, double lon2) {
		double dlat = lat2-lat1;
		double dlon = lon2-lon1;
		double angle = (Math.atan2(dlat,dlon) * 180) / Math.PI;
		return Math.toRadians(angle);
	}

	public static double calcAngleBetweenGeoLocationsInRadians(Location location1, Location location2) {
		return calcAngleBetweenGeoLocationsInRadians(location1.latitude, location2.latitude, location1.longitude, location2.longitude);
	}

}