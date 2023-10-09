package parking;

public class Parking {
	private Plaza[][][] parking;
	
	public Parking(int plantas, int filas, int columnas) {
		parking= new Plaza[plantas][filas][columnas];
		for(int i=0; i<plantas; i++) {
			for(int j=0; j<filas; j++) {
				for(int k=0; k<columnas; k++) {
					parking[i][j][k] = new Plaza();
				}
			}
		}
	}

	public Plaza[][][] getParking() {
		return parking;
	}

	public void setParking(Plaza[][][] parking) {
		this.parking = parking;
	}
	
	
}
