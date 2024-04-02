package data.model.payRoll;
/**
 * Handles tax reducations such as NSSF and PayAs you earn
 * @author KASULE PAUL
 *
 */

public interface Reducations {
	//Calaculates and returns employee NSSF reductation
	public double calNSSF();
	//Calcualates and Returns employee pay as you earn
	public double calPayE();

}
