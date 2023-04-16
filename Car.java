
public class Car
{
    //Data Members
    String make, model;
    int year,speed;
    float vx, vy, vz;
    int x,y,z;
    
    //Methods
    void accelerate(int speed_increment)
    {
        //Direction remaining the same , speed is increased.Each component increases by same factor
        vx = vx/speed;   //Here the variable speed has its old value
        vy = vy/speed;
        vz = vz/speed;
        speed = speed + speed_increment;   
        vx = vx*speed;   //Here the variable speed has its new value
        vy = vy*speed;
        vz = vz*speed;

    }
    void brake(int speed_decrement)
    {
        //Direction remaining the same , speed is decreased.Each component decreases by same factor
        vx = vx/speed;    //Here the variable speed has its old value
        vy = vy/speed;
        vz = vz/speed;
        speed = speed - speed_decrement;
        vx = vx*speed;    //Here the variable speed has its new value
        vy = vy*speed;
        vz = vz*speed;
    }
    void move(float time)
    {
        //The co-ordinates are changed based on the current speed, direction remaining the same
        x = x + (int)(vx*time);
        y = y + (int)(vy*time);
        z = z + (int)(vz*time);
    }
    void change_dir(float a, float b, float c)
    {
        //Direction is either assigned or changed 
        vx = (float) (a*speed/Math.sqrt(Math.pow(a,2)+Math.pow(b,2)+Math.pow(c,2))); //The input vector may not be a unit vector. Hence we divide by its magnitude
        vy = (float) (b*speed/Math.sqrt(Math.pow(a,2)+Math.pow(b,2)+Math.pow(c,2)));
        vz = (float) (c*speed/Math.sqrt(Math.pow(a,2)+Math.pow(b,2)+Math.pow(c,2)));
    }
    
    int detect_collision(Car car2)
    {
        if(x == car2.x && y == car2.y && z == car2.z)
            return 1;
        else
            return 0;
    }

    float time_to_collision(Car car2)
    {
        //Finding relative velocity
        float rel_vx = vx - car2.vx;
        float rel_vy = vy - car2.vy;
        float rel_vz = vz - car2.vz;
        //Finding relative position 
        int rel_x = x - car2.x;
        int rel_y = y - car2.y;
        int rel_z = z - car2.z;
        if(!(rel_vx/rel_x == rel_vy/rel_y && rel_vy/rel_y == rel_vz/rel_z))//Cars can collide only if relative position vector is anti-parallel to the relative velocity vector
            return (float) -1.0;
        else
        {
            float time;
            if(rel_vx!=0)                    //Finding time using the direction in which relative velocity is non-zero
               time = -rel_x/rel_vx;
            else if(rel_vy!=0)
               time = -rel_y/rel_vy;
            else if(rel_vz!=0)
               time = -rel_z/rel_vz;
            else
               time = (float) 0.0;
            return time;
        }
    }

    public static void main(String args[])
   {
    Car car1 = new Car();
    Car car2 = new Car();
    car1.make = "Maruti";
    car2.make = "Hyundai";
    car1.model = "Alto";
    car2.model = "Creta";
    car1.year = 2022;
    car2.year = 2023;
    car1.speed = 30;                              //Unit:miles per hour
    car2.speed = 45;                              //Unit:miles per hour
    car1.x = car1.y = car1.z = 0;
    car2.x = car2.y = car2.z = 1;
    car1.change_dir(1,1,1);
    car2.change_dir(-1,0,0);
    car1.move((float) 0.2);                      //Time in hours
    car2.move((float) 0.1);                      //Time in hours
    car2.brake(3);
    car2.move((float) 0.2);
    car1.accelerate(3);
    car1.move((float) 0.1);
    int a = car1.detect_collision(car2);
    if(a==1)
       System.out.println("Cars collided");
    else
       System.out.println("Cars have not collided");
    float t = car1.time_to_collision(car2);
    if(t<0) //If t<0 , relative velocity and relative position vectors are either non-parallel or strictly parallel(not anti)(in this case, cars separate instead of approaching)
      System.out.println("Cars won't collide unless direction(s) or speed(s) is/are changed");//Speeds must be changed in case the relative position and relative velocity vectors are strictly parallel
    else if(t==0)
      System.out.println("Cars have collided");
    else 
      System.out.println("Cars will collide in time"+ t + "hours");
    }
}