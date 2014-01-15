//physics class to manage gravity and velocity.

public class Physics 
{
	private float velx, vely;
	private float x, y;
	private float grav;


	public Physics(float xco, float yco) 
	{
		x = xco;
		y = yco;
		vely = 0;
		velx = 0;
	}

	public float jump()
	{
		vely = -9;

		return vely;
	}

	public float gravity()
	{
		grav = 1;

		return grav;
	}

}
