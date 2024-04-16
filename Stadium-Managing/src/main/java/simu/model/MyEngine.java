package simu.model;

import controller.IControllerForM;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
import entity.ResultDb;
import javafx.application.Application;
import javafx.fxml.FXML;
import simu.framework.Clock;
import simu.framework.Engine;
import simu.framework.ArrivalProcess;
import simu.framework.Event;
import dao.*;
import entity.*;
import view.SimulatorGUI;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyEngine extends Engine {

    private ArrivalProcess arrivalProcess;

    private ResultDao ResultDao;

    private ResultDb ResultDb;

    private CustomerDao CustomerDao;

    private ServicePoint[] servicePoints;

    private int[] settings = {0, 0, 0, 0}; // this will define how many servers

    public MyEngine(IControllerForM controller) {
        super(controller);
        servicePoints = new ServicePoint[20];

        servicePoints[0] = new Security(new Normal(10, 6), eventList, EventType.SECURITY1, 321, 392);
        servicePoints[1] = new Security(new Normal(10, 6), eventList, EventType.SECURITY2, 265, 423);
        servicePoints[2] = new Security(new Normal(10, 6), eventList, EventType.SECURITY3, 328, 430);
        servicePoints[3] = new Security(new Normal(10, 6), eventList, EventType.SECURITY4, 270, 457);
        servicePoints[4] = new Security(new Normal(10, 6), eventList, EventType.SECURITY5, 305, 457); // Adjusted value
        servicePoints[5] = new Ticket(new Normal(10, 6), eventList, EventType.TICKET_SELL1, 263, 360);
        servicePoints[6] = new Ticket(new Normal(10, 6), eventList, EventType.TICKET_SELL2, 234, 373);
        servicePoints[7] = new Ticket(new Normal(10, 6), eventList, EventType.TICKET_SELL3, 201, 389);
        servicePoints[8] = new Ticket(new Normal(10, 6), eventList, EventType.TICKET_SELL4, 166, 404);
        servicePoints[9] = new CheckIn(new Normal(10, 6), eventList, EventType.CHECK_IN1, 308, 524);
        servicePoints[10] = new CheckIn(new Normal(10, 6), eventList, EventType.CHECK_IN2, 324, 517);
        servicePoints[11] = new CheckIn(new Normal(10, 6), eventList, EventType.CHECK_IN3, 342, 505);
        servicePoints[12] = new CheckIn(new Normal(10, 6), eventList, EventType.CHECK_IN4, 387, 481);
        servicePoints[13] = new CheckIn(new Normal(10, 6), eventList, EventType.CHECK_IN5, 387, 501);
        servicePoints[14] = new Catering(new Normal(10, 6), eventList, EventType.CATERING1, 322, 250);
        servicePoints[15] = new Catering(new Normal(10, 6), eventList, EventType.CATERING2, 201, 389);
        servicePoints[16] = new Catering(new Normal(10, 6), eventList, EventType.CATERING3, 166, 404);
        servicePoints[17] = new Catering(new Normal(10, 6), eventList, EventType.CATERING4, 134, 422);
        servicePoints[18] = new NormalCustomer(new Normal(10, 6), eventList, EventType.CUSTOMER_ARRIVAL, 257, 157);
        servicePoints[19] = new VipCustomer(new Normal(10, 6), eventList, EventType.VIP_CUSTOMER_ARRIVAL, 51, 359);

        arrivalProcess = new ArrivalProcess(new Negexp(15, 5), eventList, EventType.ARR1);
    }

    public void setSettings(int[] settings) {
        this.settings = settings;
    }
    public int[] getSettings() {
        return settings;
    }
    @Override
    public int getSimulationTime() {
        return 0;
    }
    @Override
    protected void covers() {
        arrivalProcess.generateNextEvent(); // First arrival to Simulation
        ResultDao = new ResultDao();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("The Date works");
        ResultDb = new ResultDb(settings, strDate);
        ResultDao.persist(ResultDb);
        CustomerDao = new CustomerDao();
    }

    @Override
    protected void executeEvents(Event t) {
        System.out.println("Processing event: " + t.getType()); // Add this line for debugging
        Customer a;
        CustomerDb e;
        boolean linecheck;
        int j;
        switch ((EventType) t.getType()) {
            case ARR1:
                linecheck = true;
                j = 0;
                while (linecheck) {
                    for (int i = 0; i < settings[0]; i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            a = new Customer();
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to line " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                arrivalProcess.generateNextEvent();
                break;
            case SECURITY1:
                a = (Customer) servicePoints[0].TakeFromTheLine(); //Customer moves from security to tickets
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 5; i < (5 + settings[1]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Security number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case SECURITY2:
                a = (Customer) servicePoints[1].TakeFromTheLine(); //Customer moves from security to tickets
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 5; i < (5 + settings[1]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Security number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case SECURITY3:
                a = (Customer) servicePoints[2].TakeFromTheLine(); //Customer moves from security to tickets
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 5; i < (5 + settings[1]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Security number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case SECURITY4:
                a = (Customer) servicePoints[3].TakeFromTheLine(); //Customer moves from security to tickets
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 5; i < (5 + settings[1]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Security number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case SECURITY5:
                a = (Customer) servicePoints[4].TakeFromTheLine(); //Customer moves from security to tickets
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 5; i < (5 + settings[1]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Security number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case TICKET_SELL1:
                a = (Customer) servicePoints[5].TakeFromTheLine(); //Customer moves from Tickets to checkin
                if(a.isNormalCustomer()) {
                    linecheck = true;
                    j = 0;
                    while (linecheck) {

                        for (int i = 10; i < (10 + settings[2]); i++) {
                            if (servicePoints[i].GetLineSize() == j) {
                                servicePoints[i].AddToTheLine(a);
                                System.out.println("Customer added to Ticket Sells number " + i);
                                linecheck = false;
                                break;
                            }
                        }
                        j++;
                    }} else {
                    servicePoints[10].AddToTheLine(a);
                }
                break;
            case TICKET_SELL2:
                a = (Customer) servicePoints[6].TakeFromTheLine(); //Customer moves from Tickets to checkin
                if(a.isNormalCustomer()) {
                    linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 10; i < (10 + settings[2]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer added to Ticket Sells number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }} else {
                    servicePoints[11].AddToTheLine(a);
                }
                break;
            case TICKET_SELL3:
                a = (Customer) servicePoints[7].TakeFromTheLine(); //Customer moves from Tickets to checkin
                if(a.isNormalCustomer()) {

                    linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 10; i < (10 + settings[2]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer added to Ticket Sells number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }} else {
                    servicePoints[12].AddToTheLine(a);
                }
                break;
            case TICKET_SELL4:
                a = (Customer) servicePoints[8].TakeFromTheLine(); //Customer moves from Tickets to checkin
                if(a.isNormalCustomer()) {

                    linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 10; i < (10 + settings[2]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer added to Ticket Sells number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }} else {
                    servicePoints[13].AddToTheLine(a);
                }
                break;
            case CHECK_IN1:
                a = (Customer) servicePoints[9].TakeFromTheLine(); //Customer moves from checkin to catering
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 15; i < (15 + settings[3]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Check In number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case CHECK_IN2:
                a = (Customer) servicePoints[10].TakeFromTheLine(); //Customer moves from checkin to catering
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 15; i < (15 + settings[3]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Check In number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case CHECK_IN3:
                a = (Customer) servicePoints[11].TakeFromTheLine(); //Customer moves from checkin to catering
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 15; i < (15 + settings[3]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Check In number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;
            case CHECK_IN4:
                a = (Customer) servicePoints[12].TakeFromTheLine(); //Customer moves from checkin to catering
                linecheck = true;
                j = 0;
                while (linecheck) {

                    for (int i = 15; i < (15 + settings[3]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Check In number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;

            case CHECK_IN5:
                a = (Customer) servicePoints[13].TakeFromTheLine(); // Customer moves from checkin to catering
                linecheck = true;
                j = 0;
                while (linecheck) {
                    for (int i = 15; i < (15 + settings[3]); i++) {
                        if (servicePoints[i].GetLineSize() == j) {
                            servicePoints[i].AddToTheLine(a);
                            System.out.println("Customer Added to Check In number " + i);
                            linecheck = false;
                            break;
                        }
                    }
                    j++;
                }
                break;

            case CATERING1:
                a = (Customer) servicePoints[14].TakeFromTheLine();
                servicePoints[18].AddToTheLine(a);
                System.out.println("Customer arrived to Catering");
                break;

            case CATERING2:
                a = (Customer) servicePoints[15].TakeFromTheLine();
                servicePoints[18].AddToTheLine(a);
                System.out.println("Customer arrived to Catering");
                break;

            case CATERING3:
                a = (Customer) servicePoints[16].TakeFromTheLine();
                servicePoints[18].AddToTheLine(a);
                System.out.println("Customer arrived to Catering");
                break;

            case CATERING4:
                a = (Customer) servicePoints[17].TakeFromTheLine();
                servicePoints[19].AddToTheLine(a);
                System.out.println("Customer arrived to Catering");
                break;

            case CUSTOMER_ARRIVAL:
                a = (Customer) servicePoints[18].TakeFromTheLine();
                a.setFinistime(Clock.getInstance().getClock());
                e = new CustomerDb(a, this.ResultDb.getId());
                CustomerDao.persist(e);
                ResultDb.setAvgtime(a.raport());
                ResultDb.setCustomers(Customer.getCount());
                ResultDb.setTotalTime(Clock.getInstance().getClock());
                ResultDao.update(ResultDb);
                a.raport();
                System.out.println("Customer arrived and added to the database");
                break;

            case VIP_CUSTOMER_ARRIVAL:
                a = (Customer) servicePoints[19].TakeFromTheLine();
                a.setFinistime(Clock.getInstance().getClock());
                e = new CustomerDb(a, this.ResultDb.getId());
                CustomerDao.persist(e);
                a.raport();
                System.out.println("VIP Customer arrived and added to the database");
                break;
        }
    }

    @Override
    protected void tryCEvents() {
        for (ServicePoint p : servicePoints) {
            if (!p.isReserved() && p.isOnQueue()) {
                p.StartService();
            }
        }
    }

    public ServicePoint[] getservicePoints() {
        return servicePoints;
    }

    @Override
    protected void results() {
        System.out.println("Simulation ended at " + Clock.getInstance().getClock());
        System.out.println("Results ... ");
        System.out.println("Number of the customers " + Customer.getCount());
    }
}