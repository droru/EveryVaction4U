package Model;

import javax.imageio.stream.ImageInputStream;

public class User
{
    private String UserName;
    private String FirstName;
    private String LastName ;
    private String Password;
    private String BirthDate ;
    private String City;
    private String Email;



    private String ProfilePicPath;


    public User(){}

    public User(String userName, String firstName, String lastName, String password, String birthDate, String city, String email,String ProfilePicpath) {
        UserName = userName;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        BirthDate = birthDate;
        City = city;
        Email=email;
        ProfilePicPath=ProfilePicpath;

    }


    public String getProfilePicPath() {
        return ProfilePicPath;
    }

    public void setProfilePicPath(String profilePicPath) {
        ProfilePicPath = profilePicPath;
    }
    public String getEmail(){return Email;}
    public void setEmail(String email){Email=email; }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public void print()
    {
        System.out.println(UserName);
        System.out.println(FirstName);
        System.out.println(LastName);
        System.out.println(Password);
        System.out.println(BirthDate);
        System.out.println(City);
        System.out.println(Email);
        System.out.println(ProfilePicPath);


    }
}
