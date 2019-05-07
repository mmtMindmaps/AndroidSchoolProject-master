package mmt.source.com.schoolproject.model;

public class SvTrack {
    private static SvTrack svt_inst = null;

    public static SvTrack getInstance()
    {
        if (svt_inst == null) {
            svt_inst = new SvTrack();
        }

        return svt_inst;
    }

    private User userDetails;
    private Student studentDetails;
    private Vehicle vehicleDetails;

    SvTrack() {
        userDetails = new User();
        studentDetails = new Student();
        vehicleDetails = new Vehicle();
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public Student getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(Student studentDetails) {
        this.studentDetails = studentDetails;
    }

    public Vehicle getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(Vehicle vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
}
