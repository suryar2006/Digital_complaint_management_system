import java.util.Scanner;

abstract class Person {
    private int id;
    private String name;
    Person(int id, String name) {
        this.id=id;
        this.name=name;
    }
    int getId() {
        return id;
    }
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name=name;
    }
    abstract void showDetails();
}

class Complaint {
    private int id;
    private String desc;
    private String status;
    private String priority;
    private static int nextId=1;
    Complaint(String desc, String priority) {
        this.id=nextId++;
        this.desc=desc;
        this.priority=priority;
        this.status="Pending";
    }
    int getId() {
        return id;
    }
    String getStatus() {
        return status;
    }
    void updateStatus(String status) {
        this.status=status;
    }
    void viewDetails() {
        System.out.println("\nComplaint ID: "+id);
        System.out.println("Description : "+desc);
        System.out.println("Priority    : "+priority);
        System.out.println("Status      : "+status);
    }
}

class User extends Person {
    private String contact;
    private Complaint[] complaints;
    private int count;
    User(int id, String name, String contact) {
        super(id, name);
        this.contact=contact;
        complaints=new Complaint[1000];
        count=0;
    }
    void submitComplaint(String desc, String priority) {
        if (count<complaints.length) {
            complaints[count]=new Complaint(desc, priority);
            System.out.println("Complaint submitted successfully.");
            System.out.println("Complaint ID: "+complaints[count].getId());
            count++;
        } 
        else {
            System.out.println("Complaint limit reached.");
        }
    }
    void viewStatus() {
        if (count==0) {
            System.out.println("No complaints submitted yet.");
            return;
        }
        for (int i=0; i<count; i++) {
            complaints[i].viewDetails();
        }
    }
    Complaint[] getComplaints() {
        return complaints;
    }

    int getCount() {
        return count;
    }
    void showDetails() {
        System.out.println("\nUser Details");
        System.out.println("User ID : "+getId());
        System.out.println("Name    : "+getName());
        System.out.println("Contact : "+contact);
    }
}

class Officer extends Person {
    private String dept;
    Officer(int id, String name, String dept) {
        super(id, name);
        this.dept=dept;
    }
    void viewComplaints(User user) {
        System.out.println("\nComplaints assigned/viewed by Officer");
        user.viewStatus();
    }
    void resolveComplaint(Complaint c) {
        if (c!=null) {
            c.updateStatus("Resolved");
            System.out.println("Complaint resolved successfully.");
        } 
        else {
            System.out.println("Invalid complaint.");
        }
    }

    void showDetails() {
        System.out.println("\nOfficer Details");
        System.out.println("Officer ID : "+getId());
        System.out.println("Name       : "+getName());
        System.out.println("Department : "+dept);
    }
}

class Admin extends Person {
    Admin(int id, String name) {
        super(id, name);
    }

    void assignOfficer(Complaint c, Officer o) {
        if (c!=null) {
            c.updateStatus("Assigned to Officer");
            System.out.println("Complaint assigned to " + o.getName()+".");
        } 
        else {
            System.out.println("Invalid complaint.");
        }
    }
    void monitorComplaints(User u) {
        System.out.println("\nAll Complaints");
        u.viewStatus();
    }
    void escalateComplaint(Complaint c) {
        if (c!=null) {
            c.updateStatus("Escalated");
            System.out.println("Complaint escalated successfully.");
        } 
        else {
            System.out.println("Invalid complaint.");
        }
    }
    void showDetails() {
        System.out.println("\nAdmin Details");
        System.out.println("Admin ID : "+getId());
        System.out.println("Name     : "+getName());
    }
}

public class ComplaintManagementSystem {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        User user=new User(1, "New User", "example@gmail.com");
        Officer officer=new Officer(2, "Officer 1", "Maintenance");
        Admin admin=new Admin(3, "Admin 1");
        int ch;
        do {
            System.out.println("\nCOMPLAINT MANAGEMENT SYSTEM\n1. Submit Complaint\n2. View Complaint Status\n3. Assign Officer\n4. Officer View Complaints\n5. Resolve Complaint\n6. Monitor Complaints\n7. Escalate Complaint");
            System.out.println("8. Show Details");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            ch=sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    System.out.print("Enter complaint description: ");
                    String desc=sc.nextLine();
                    System.out.print("Enter priority: ");
                    String priority=sc.nextLine();
                    user.submitComplaint(desc, priority);
                    break;
                case 2:
                    user.viewStatus();
                    break;
                case 3:
                    System.out.print("Enter complaint number to assign: ");
                    int a=sc.nextInt();
                    if (a>0 && a<=user.getCount()) {
                        admin.assignOfficer(user.getComplaints()[a-1], officer);
                    } 
                    else {
                        System.out.println("Complaint not found.");
                    }
                    break;
                case 4:
                    officer.viewComplaints(user);
                    break;
                case 5:
                    System.out.print("Enter complaint number to resolve: ");
                    int r=sc.nextInt();
                    if (r>0 && r<=user.getCount()) {
                        officer.resolveComplaint(user.getComplaints()[r-1]);
                    } 
                    else {
                        System.out.println("Complaint not found.");
                    }
                    break;
                case 6:
                    admin.monitorComplaints(user);
                    break;
                case 7:
                    System.out.print("Enter complaint number to escalate: ");
                    int e=sc.nextInt();
                    if (e>0 && e<=user.getCount()) {
                        admin.escalateComplaint(user.getComplaints()[e-1]);
                    } 
                    else {
                        System.out.println("Complaint not found.");
                    }
                    break;
                case 8:
                    user.showDetails();
                    officer.showDetails();
                    admin.showDetails();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (ch!=9);
        sc.close();
    }
}