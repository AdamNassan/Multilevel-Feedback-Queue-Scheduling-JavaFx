package com.example.demo;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HelloApplication extends Application {
    ArrayList<process> processesInSystem = new ArrayList<>();
    ArrayList<process> ArrivalQueue = new ArrayList<>();
    ArrayList<process> IO = new ArrayList<>();
    ArrayList<process> Q1 = new ArrayList<>();
    ArrayList<process> Q2 = new ArrayList<>();
    ArrayList<process> Q3 = new ArrayList<>();
    ArrayList<process> Q4 = new ArrayList<>();
    ArrayList<process> finishQueue = new ArrayList<>();
    ArrayList<Integer> whichProcces = new ArrayList<>();
    ArrayList<Integer> from = new ArrayList<>();
    ArrayList<Integer> to = new ArrayList<>();
    ArrayList<Integer> io = new ArrayList<>();
    ArrayList<Integer> arrival = new ArrayList<>();
    int indexChart=0;
    //there is Process From Queue 1 In CPU
    int timeQ1=1;
    int timeQ2=1;
    int oldQ3processInCpu=-1;
    int oldIndex = -1;
    boolean Pq1InCPU=false;
    boolean Pq2InCPU=false;
    boolean Pq3InCPU=false;
    boolean Pq4InCPU=false;
    boolean flagQ2In=false;
    boolean flagQ3In=false;
    boolean flagQ4In=false;
    boolean firstINQ3=true;
    boolean firstINQ4=true;
    boolean flageGC=true;
    boolean flageGC2=true;
    int glIndex=-1;
    int cycle=0;
    double CPUTimeUti=0;
    int cpuRunningProcess=-1;
    int q1;
    int q2;
    double alfa;
    int pauseIntt;
    int ideal=0;
    String info="";
    String fileName="";

    private final Object pauseLock = new Object();
    @Override

    public void start(Stage stage) throws IOException {
        stage1();
    }
    //Stage one to let the user choose the way of getting the file
    public void stage1() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(30));
        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font myfont2 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40);
        Font myfont3 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 50);
        HBox hBox1 = new HBox(120);
        Button button1 = new Button("File containing data");
        button1.setMinSize(270, 60);
        button1.setFont(myfont);

        Button button2 = new Button("Enter data randomly");
        button2.setMinSize(270, 60);
        button2.setFont(myfont);
        hBox1.getChildren().addAll(button1,button2);


        HBox hBox2 = new HBox(120);
        Label lab1 = new Label("Multilevel Feedback Queue");
        lab1.setTextFill(Color.BLACK);
        lab1.setFont(myfont2);
        lab1.setPrefSize(550,80);
        lab1.setAlignment(Pos.CENTER);

        Label tA1 = new Label("                              Welcome to our project\n\n\n\n     Jehad Hamayel 1200348 <|> Adam Nassan 1202076");
        tA1.setPrefSize(1400,1000);
        tA1.setFont(myfont3);


        hBox2.getChildren().addAll(lab1, tA1);


        BorderPane.setAlignment(lab1,Pos.TOP_CENTER);
        pane.setTop(lab1);
        pane.setCenter(tA1);
        hBox1.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hBox1,Pos.BOTTOM_CENTER);
        pane.setBottom(hBox1);



        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //Either a file that already has data
        button1.setOnAction(e->{
            stage.close();
            stage2();
        });
        //or enter the data randomly
        button2.setOnAction(e->{
            stage.close();
            stage3();
        });

    }
    //Stage 2 if the user chose ready file
    public void stage2(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("alerts");
        alert.setHeaderText(null);
        VBox vbox = new VBox(50);
        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40);

        HBox h = new HBox(5);
        Label l = new Label("");
        l.setFont(myfont);
        l.setTextFill(Color.BLACK);
        l.setPrefSize(250,110);
        h.setLayoutX(100);
        h.setLayoutY(50);
        h.setPrefSize(20,180);
        h.getChildren().add(l);

        HBox h1 = new HBox(40);
        Label l1 = new Label("  File Name \"e.g.: file.txt\"");
        l1.setFont(myfont);
        l1.setTextFill(Color.BLACK);
        l1.setPrefSize(500,110);
        TextField t1 = new TextField();
        t1.setPrefSize(500,50);
        t1.setFont(new Font("Arial",30));
        h1.setLayoutX(100);
        h1.setLayoutY(50);
        h1.setPrefSize(20,30);
        h1.getChildren().addAll(l1, t1);

        HBox h2 = new HBox(5);
        Button button1 = new Button("Choose file");
        button1.setPrefSize(300,120);
        button1.setFont(myfont);
        h2.setAlignment(Pos.BOTTOM_RIGHT);
        h2.getChildren().add(button1);

        vbox.getChildren().addAll(h,h1,h2);
        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
       //If choose file button pressed
        button1.setOnAction(e->{
            Scanner sc = null;
            if(t1.getText()!="") {
                File myFile = new File(t1.getText());
              //Read the file name and go to the next stage
                if(myFile.exists()) {
                    fileName=t1.getText();
                    stage.close();
                    stage4();
                }//Warning if the file doesn't exist
                else {
                    alert.setContentText("The file does not exists");
                    alert.showAndWait();
                }
            //Warning if the user hasn't entered data
            }else {
                alert.setContentText("Please enter valid File Name");
                alert.showAndWait();
            }


        });


    }
    //If the user chose to enter the data randomly
    public void stage3(){
        VBox vbox = new VBox(20);

        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font myfont2 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("alerts");
        alert.setHeaderText(null);

        HBox h1 = new HBox(5);
        Label l1 = new Label("Number of Processes");
        l1.setFont(myfont);
        l1.setTextFill(Color.BLACK);
        l1.setPrefSize(250,110);
        TextField t1 = new TextField();
        t1.setPrefSize(500,50);
        t1.setFont(new Font("Arial",30));
        h1.setLayoutX(100);
        h1.setLayoutY(50);
        h1.setPrefSize(20,30);
        h1.getChildren().addAll(l1, t1);

        HBox h2 = new HBox(5);
        Label l2 = new Label("Max Arrival Time");
        l2.setFont(myfont);
        l2.setTextFill(Color.BLACK);
        l2.setPrefSize(250,110);
        TextField t2 = new TextField();
        t2.setPrefSize(500,50);
        t2.setFont(new Font("Arial",30));
        h2.setLayoutX(100);
        h2.setLayoutY(50);
        h2.setPrefSize(20,30);
        h2.getChildren().addAll(l2, t2);

        HBox h3 = new HBox(5);
        Label l3 = new Label("Max Number \nOf CPU Burst");
        l3.setFont(myfont);
        l3.setTextFill(Color.BLACK);
        l3.setPrefSize(250,110);
        TextField t3 = new TextField();
        t3.setPrefSize(500,50);
        t3.setFont(new Font("Arial",30));
        h3.setLayoutX(100);
        h3.setLayoutY(50);
        h3.setPrefSize(20,30);
        h3.getChildren().addAll(l3, t3);

        HBox h4 = new HBox(5);
        Label l4 = new Label("Min IO burst duration");
        l4.setFont(myfont);
        l4.setTextFill(Color.BLACK);
        l4.setPrefSize(250,110);
        TextField t4 = new TextField();
        t4.setPrefSize(500,50);
        t4.setFont(new Font("Arial",30));
        h4.setLayoutX(100);
        h4.setLayoutY(50);
        h4.setPrefSize(20,30);
        h4.getChildren().addAll(l4, t4);

        HBox h5 = new HBox(5);
        Label l5 = new Label("Max IO burst duration");
        l5.setFont(myfont);
        l5.setTextFill(Color.BLACK);
        l5.setPrefSize(250,110);
        TextField t5 = new TextField();
        t5.setPrefSize(500,50);
        t5.setFont(new Font("Arial",30));
        h5.setLayoutX(100);
        h5.setLayoutY(50);
        h5.setPrefSize(20,30);
        h5.getChildren().addAll(l5, t5);

        HBox h6 = new HBox(5);
        Label l6 = new Label("Min CPU burst duration");
        l6.setFont(myfont);
        l6.setTextFill(Color.BLACK);
        l6.setPrefSize(250,110);
        TextField t6 = new TextField();
        t6.setPrefSize(500,50);
        t6.setFont(new Font("Arial",30));
        h6.setLayoutX(100);
        h6.setLayoutY(50);
        h6.setPrefSize(20,30);
        h6.getChildren().addAll(l6, t6);

        HBox h7 = new HBox(5);
        Label l7 = new Label("Max CPU burst duration");
        l7.setFont(myfont);
        l7.setTextFill(Color.BLACK);
        l7.setPrefSize(250,110);
        TextField t7 = new TextField();
        t7.setPrefSize(500,50);
        t7.setFont(new Font("Arial",30));
        h7.setLayoutX(100);
        h7.setLayoutY(50);
        h7.setPrefSize(20,30);
        h7.getChildren().addAll(l7, t7);

        HBox h8 = new HBox(5);
        Label nulllabel1 = new Label("");
        nulllabel1.setPrefSize(245,55);
        h8.getChildren().add(nulllabel1);

        HBox h9 = new HBox(5);
        Button button1 = new Button("Next ");
        button1.setPrefSize(300,120);
        button1.setFont(myfont2);
        h9.setAlignment(Pos.BOTTOM_RIGHT);
        h9.getChildren().add(button1);

        vbox.getChildren().addAll(h1,h2,h3,h4,h5,h6,h7,h8,h9);
        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //When next button pressed
        button1.setOnAction(e->{
            if(t1.getText()!=""&&t2.getText()!=""&&t3.getText()!=""&&t4.getText()!=""&&t5.getText()!=""&&t6.getText()!=""&&t7.getText()!="") {
                int PID=0;
                int numOfProccesses;
                int maxArrtime;
                int maxNumCPUBurst;
                int minIO;
                int maxIO;
                int minCPU;
                int maxCPU;
                //Read all min max and other value by the user
                try {

                    numOfProccesses=Integer.parseInt(t1.getText());
                    maxArrtime=Integer.parseInt(t2.getText());
                    maxNumCPUBurst=Integer.parseInt(t3.getText());
                    minIO=Integer.parseInt(t4.getText());
                    maxIO=Integer.parseInt(t5.getText());
                    minCPU=Integer.parseInt(t6.getText());
                    maxCPU=Integer.parseInt(t7.getText());
                //Assign max columns depending on the number of CPU, IO bursts
                    int maxColumns=maxNumCPUBurst+(maxNumCPUBurst-1)+2;
                    PrintWriter WorkloadGe = new PrintWriter("WorkloadGeneratorFile.txt");
                    int[][] processes = new int[numOfProccesses][maxColumns];
                    //Assign information to generate method
                    for(int i=0;i<numOfProccesses;i++) {
                        PID=i;
                        WorkloadGenerator(WorkloadGe,processes,PID,maxArrtime,maxNumCPUBurst,minIO,maxIO,minCPU,maxCPU);
                    }
                    //Close the file and go to the next stage
                    WorkloadGe.close();
                    stage.close();
                    stage4();
                }catch (Exception e1) {
                    alert.setContentText("Please enter valid Information");
                    alert.showAndWait();
                }


            }

        });

    }
    //Stage to get q1,q2 and alpha values
    public void stage4(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("alerts");
        alert.setHeaderText(null);
        VBox vbox = new VBox(20);
        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 30);
        Font myfont2 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 30);

        HBox h = new HBox(5);
        Label nulllabel1 = new Label("");
        nulllabel1.setPrefSize(245,175);
        h.getChildren().add(nulllabel1);

        HBox h1 = new HBox(5);
        Label l1 = new Label("  q1");
        l1.setFont(myfont);
        l1.setTextFill(Color.BLACK);
        l1.setPrefSize(250,110);
        TextField t1 = new TextField();
        t1.setPrefSize(500,50);
        t1.setFont(new Font("Arial",50));
        h1.setLayoutX(100);
        h1.setLayoutY(50);
        h1.setPrefSize(20,30);
        h1.getChildren().addAll(l1, t1);

        HBox h2 = new HBox(5);
        Label l2 = new Label("  q2");
        l2.setFont(myfont);
        l2.setTextFill(Color.BLACK);
        l2.setPrefSize(250,110);
        TextField t2 = new TextField();
        t2.setPrefSize(500,50);
        t2.setFont(new Font("Arial",50));
        h2.setLayoutX(100);
        h2.setLayoutY(50);
        h2.setPrefSize(20,30);
        h2.getChildren().addAll(l2, t2);

        HBox h3 = new HBox(5);
        Label l3 = new Label("  Alpha");
        l3.setFont(myfont);
        l3.setTextFill(Color.BLACK);
        l3.setPrefSize(250,110);
        TextField t3 = new TextField();
        t3.setPrefSize(500,50);
        t3.setFont(new Font("Arial",50));
        h3.setLayoutX(100);
        h3.setLayoutY(50);
        h3.setPrefSize(20,30);
        h3.getChildren().addAll(l3, t3);

        HBox h7 = new HBox(5);
        Label l7 = new Label(" interrupt Time:");
        l7.setFont(myfont);
        l7.setTextFill(Color.BLACK);
        l7.setPrefSize(250,110);
        TextField t7 = new TextField();
        t7.setPrefSize(500,50);
        t7.setFont(new Font("Arial",50));
        h7.setLayoutX(100);
        h7.setLayoutY(50);
        h7.setPrefSize(20,30);
        h7.getChildren().addAll(l7, t7);


        HBox h4 = new HBox(5);
        Button button1 = new Button("Next");
        // button1.setAlignment(Pos.BOTTOM_RIGHT);
        button1.setPrefSize(300,120);
        button1.setFont(myfont2);
        h4.setAlignment(Pos.BOTTOM_RIGHT);
        h4.getChildren().add(button1);

        vbox.getChildren().addAll(h,h1,h2,h3,h7,h4);
        vbox.setPadding(new Insets(30));
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //When next button pressed
        button1.setOnAction(e->{
            if(t1.getText()!=""&&t2.getText()!=""&&t3.getText()!="") {
                try {
                    //Read q1,q2,alfa and pauseIntt
                    q1=Integer.parseInt(t1.getText());

                    q2=Integer.parseInt(t2.getText());

                    alfa=Double.parseDouble(t3.getText());

                    pauseIntt=Integer.parseInt(t7.getText());
                    File myFile ;
                    //Choose the file depending on the way that the user chose
                    if(fileName!="") {
                        myFile= new File(fileName);
                    }else {
                        myFile= new File("WorkloadGeneratorFile.txt");
                    }

                    Scanner sc = new Scanner(myFile);
                    String data;
                    while (sc.hasNext()) {
                    //Read the file data
                        data = sc.nextLine();
                        String[] infoData = data.split(" ");
                        for (int y = 0; y < infoData.length; y++)
                            infoData[y] = infoData[y].trim();
                        ArrayList<Integer> CPUAndIOBust = new ArrayList<>();
                        //Assign all cpu and IO bursts into an arraylist
                        for(int p=2;p<infoData.length;p++) {
                            CPUAndIOBust.add(Integer.parseInt(infoData[p]));
                        }
                    //Invoke an object from process class and assign PID,arrival time and CPUIOBurst to it
                        process proc =new process(Integer.parseInt(infoData[0]),Integer.parseInt(infoData[1]),CPUAndIOBust);
                        //add proc info to processesInSystem and ArrivalQueue array lists
                        processesInSystem.add(proc);
                        ArrivalQueue.add(proc);
                    }
                    //Sort arrival queue
                    Collections.sort(ArrivalQueue);

                }catch (Exception e1) {
                    alert.setContentText("Enter Valid values");
                    alert.showAndWait();
                }
                stage.close();
                stage5();
            }else {
                alert.setContentText("Please enter all valid Information");
                alert.showAndWait();
            }
        });
    }
    //All MultiLevel queue scheduling done in this stage
    public void stage5() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(30));
        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font myfont2 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40);
        HBox hBox1 = new HBox(120);
        Button button1 = new Button("Start");
        button1.setMinSize(200, 30);
        button1.setFont(myfont);

        Button button3 = new Button("Next ");
        button3.setMinSize(200, 30);
        button3.setFont(myfont);

        hBox1.getChildren().addAll(button1,button3);
        TextArea tA1 = new TextArea();
        tA1.setPrefSize(400,500);
        tA1.setFont(myfont);
        //When start button pressed
        button1.setOnAction(e1->{
                    String info="";
                    //Keep working until no process remain in any queue
                    while(Q1.size()!=0 || ArrivalQueue.size()!=0 || Q2.size()!=0 ||  Q3.size()!=0 || Q4.size()!=0) {
                        //sort arrival queue depending on arrival time
                        Collections.sort(ArrivalQueue);
                        for(int t=0;t<ArrivalQueue.size();t++) {
                            //If the process reached its arrival time
                            if(cycle==ArrivalQueue.get(t).getArrivalTime()) {
                                //check if it reached its IO time and add to io list
                                //io ArrayList has the pid that we want to remove from IO
                                for(int e=0;e<IO.size();e++) {
                                    if(IO.get(e).getArrivalTime()==cycle) {
                                        io.add(IO.get(e).getID());
                                    }
                                }
                                //Check the value of getInQueue that decide to which queue the process must go
                                //0 or 1 to q1
                                if(ArrivalQueue.get(t).getInQueue()==0 || ArrivalQueue.get(t).getInQueue()==1) {

                                    ArrivalQueue.get(t).setInQueue(1);
                                    ArrivalQueue.get(t).callQ1TimesQuanta(q1);//add to queue1
                                    Q1.add(ArrivalQueue.get(t));//add to arrival queue
                                    arrival.add(ArrivalQueue.get(t).getID());
                                //2 to q2
                                }else if (ArrivalQueue.get(t).getInQueue()==2) {
                                    ArrivalQueue.get(t).callQ2TimesQuanta(q2);
                                    Q2.add(ArrivalQueue.get(t));//add to queue2
                                    arrival.add(ArrivalQueue.get(t).getID());//add to arrival queue
                                //3 to q3
                                }else if (ArrivalQueue.get(t).getInQueue()==3) {
                                    ArrivalQueue.get(t).setQ3Times(3);
                                    Q3.add(ArrivalQueue.get(t));//add to queue3
                                    arrival.add(ArrivalQueue.get(t).getID());//add to arrival queue
                                //4 to q4
                                }else if (ArrivalQueue.get(t).getInQueue()==4) {
                                    Q4.add(ArrivalQueue.get(t));//add to queue4
                                    arrival.add(ArrivalQueue.get(t).getID());//add to arrival queue
                                }
                            }
                        }
                        //Loop to check what we want to remove from IO depending on io list
                        for(int y=0;y<io.size();y++) {
                            for(int p=0;p<IO.size();p++) {
                                if(IO.get(p).getID()==io.get(y)) {
                                    IO.remove(p);
                                    break;
                                }
                            }
                        }
                        //Loop to check what we want to remove from ArrivalQueue depending on arrival list
                        for(int y=0;y<arrival.size();y++) {
                            for(int p=0;p<ArrivalQueue.size();p++) {
                                if(ArrivalQueue.get(p).getID()==arrival.get(y)) {
                                    ArrivalQueue.remove(p);
                                    break;
                                }
                            }
                        }
                        io = new ArrayList<>();
                        arrival= new ArrayList<>();
                        //Check all interrupts
                        //case interrupt by q1 on q2 depending on flag value
                        if (Q1.size()!=0 && Q2.size()!=0 && flagQ2In==true) {
                            System.out.println("Interrupt In Q2 By Q1");
                            cpuRunningProcess=-1;
                            Pq2InCPU =false;//no process from q2 in CPU
                            flagQ2In=false;//return flag to false
                            to.add(indexChart, cycle);//the place of the interrupt
                            System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                            indexChart++;
                            Q2.get(0).setQ2TimesQuanta(Q2.get(0).getQ2TimesQuanta()-timeQ2); //setQ2TimesQuanta to quanta- timeQ2 counter

                            int indexCPUBu=Q2.get(0).getIndex();//assign CPUburst index
                            Q2.get(0).getCPUAndIOBust().set(indexCPUBu,(Q2.get(0).getCPUAndIOBust().get(indexCPUBu)-(timeQ2))); //Calculate remaining time of CpuBurst
                            process temp =Q2.get(0); //add the process to temp to use it later
                            Q2.remove(0);//Remove from Q2
                            //if quanta and CPUIO burst bigger than 0
                            if(temp.getQ2TimesQuanta()>0 && temp.getCPUAndIOBust().get(indexCPUBu)>0) {
                                Q2.add(temp);//return it back to queue2
                                continue;
                            }
                            //if quanta bigger or equal 0 and no CPUIO burst remaining
                            else if (temp.getQ2TimesQuanta()>=0 && temp.getCPUAndIOBust().get(indexCPUBu)==0) {
                                int indexCPUIO=temp.getIndex()+1;
                                //Keep working until reach last index of cpuIOburst
                                if(indexCPUIO < temp.getCPUAndIOBust().size()) {
                                    //Update arrival time and queue and add to IO
                                    temp.setIndex(temp.getIndex()+1);
                                    temp.callQ2TimesQuanta(q2);
                                    temp.setInQueue(2);
                                    temp.setArrivalTime(cycle+temp.getCPUAndIOBust().get(temp.getIndex()));
                                    temp.setIndex(temp.getIndex()+1);
                                    IO.add(temp);
                                    ArrivalQueue.add(temp);

                                    timeQ2=1;
                                    continue;
                                }
                                //When we pass all bursts add to finish queue
                                else {
                                    finishQueue.add(temp);
                                    timeQ2=1;
                                    continue;
                                }

                            }
                            //if quanta 0 and still remaining CPUIO burst --> add to queue3
                            else if(temp.getQ2TimesQuanta()==0 && temp.getCPUAndIOBust().get(indexCPUBu)>0) {
                                temp.setQ3Times(3);
                                temp.setInQueue(3);
                                Q3.add(temp);
                                timeQ2=1;
                                continue;
                            }
                        }
                        //case interrupt by q1 or q2 on q3 depending on flag value
                        if ((Q1.size()!=0 || Q2.size()!=0 )&& Q3.size()!=0 && flagQ3In==true) {

                            System.out.println("Interrupt In Q3 By Q2 Or Q1");

                            int indexCPUIO=Q3.get(glIndex).getIndex()+1;
                            //Keep working until reach last index of cpuIOburst
                            if(indexCPUIO < Q3.get(glIndex).getCPUAndIOBust().size()) {
                                Q3.get(glIndex).setIndex(Q3.get(glIndex).getIndex()+1);
                                Pq3InCPU =false;
                                cpuRunningProcess=-1;
                                to.add(indexChart, cycle);
                                flagQ3In=false;
                                System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                indexChart++;
                                //Update arrival time and queue and add to IO
                                Q3.get(glIndex).setCalc(false);
                                Q3.get(glIndex).setQ3Times(3);
                                Q3.get(glIndex).setInQueue(3);
                                Q3.get(glIndex).setArrivalTime(cycle+Q3.get(glIndex).getCPUAndIOBust().get(Q3.get(glIndex).getIndex()));
                                Q3.get(glIndex).setIndex(Q3.get(glIndex).getIndex()+1);
                                IO.add(Q3.get(glIndex));
                                ArrivalQueue.add(Q3.get(glIndex));
                                Q3.remove(glIndex);
                                firstINQ3=true;
                                continue;
                            }//When we pass all bursts add to finish queue and remove from q3

                            else {
                                Pq3InCPU =false;
                                cpuRunningProcess=-1;
                                to.add(indexChart, cycle);
                                flagQ3In=false;
                                System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                indexChart++;
                                finishQueue.add(Q3.get(glIndex));
                                Q3.remove(glIndex);
                                firstINQ3=true;
                                continue;
                            }


                        }
                        //case interrupt by q1 or q2 or q3 on q4 depending on flag value
                        if ((Q1.size()!=0 || Q2.size()!=0 || Q3.size()!=0)&& Q4.size()!=0 && flagQ4In==true) {
                            System.out.println("Interrupt In Q4 By Q3 Or Q2 Or Q1");
                            //If CpuIO burst still remaining
                            if(Q4.get(0).getCPUAndIOBust().get(Q4.get(0).getIndex())>0) {
                                Pq4InCPU =false;
                                cpuRunningProcess=-1;
                                to.add(indexChart, cycle);
                                System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                indexChart++;
                                process temp =Q4.get(0);
                                Q4.remove(0);
                                Q4.add(temp);
                                flagQ4In=false;
                            }
                            else {
                                int indexCPUIO=Q4.get(0).getIndex()+1;
                                //Keep working until reach last index of cpuIOburst
                                if(indexCPUIO < Q4.get(0).getCPUAndIOBust().size()) {
                                    Q4.get(0).setIndex(Q4.get(0).getIndex()+1);
                                    Pq4InCPU =false;
                                    cpuRunningProcess=-1;
                                    to.add(indexChart, cycle);
                                    flagQ4In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                    indexChart++;
                                    //Update arrival time and queue and add to IO
                                    Q4.get(0).setInQueue(4);
                                    Q4.get(0).setArrivalTime(cycle+Q4.get(0).getCPUAndIOBust().get(Q4.get(0).getIndex()));
                                    Q4.get(0).setIndex(Q4.get(0).getIndex()+1);
                                    IO.add(Q4.get(0));
                                    ArrivalQueue.add(Q4.get(0));
                                    Q4.remove(0);
                                    firstINQ4=true;
                                    continue;
                                }//When we pass all bursts add to finish queue and remove from q4
                                else {
                                    Pq4InCPU =false;
                                    cpuRunningProcess=-1;
                                    to.add(indexChart, cycle);
                                    flagQ4In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                    indexChart++;
                                    finishQueue.add(Q4.get(0));
                                    Q4.remove(0);
                                    firstINQ4=true;
                                    continue;
                                }
                            }
                        }
                        //all queues empty
                        if(Q1.size()==0&&Q2.size()==0&&Q3.size()==0&&Q4.size()==0) {
                            ideal+=1;//increase ideal
                            if(flageGC==true) {
                                whichProcces.add(indexChart, -1);
                                from.add(indexChart,cycle);
                                flageGC=false;

                            }
                        }else {
                            if(flageGC==false ) {
                                to.add(indexChart,cycle);
                                System.out.println("Ideal From: "+from.get(indexChart)+" To: "+to.get(indexChart));
                                indexChart+=1;
                                flageGC=true;
                            }
                        }
                        //Case in queue 1 and no process in cpu
                        if(Q1.size()!=0) {
                            if(cpuRunningProcess == -1) {
                                System.out.println("Q1");
                                timeQ1=1;
                                Pq1InCPU =true;
                                cpuRunningProcess=Q1.get(0).getID();
                                whichProcces.add(indexChart, cpuRunningProcess);
                                from.add(indexChart, cycle);
                                //calculate waiting time for all queues
                                for(int r=1;r<Q1.size();r++) {
                                    Q1.get(r).setWaitingTime(Q1.get(r).getWaitingTime()+1);
                                }
                                for(int r=0;r<Q2.size();r++) {
                                    Q2.get(r).setWaitingTime(Q2.get(r).getWaitingTime()+1);
                                }
                                for(int r=0;r<Q3.size();r++) {
                                    Q3.get(r).setWaitingTime(Q3.get(r).getWaitingTime()+1);
                                }
                                for(int r=0;r<Q4.size();r++) {
                                    Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                }

                            }
                            else {
                                //cases for cpuburst < given quanta and  quantaTimes and equal time in Q1 etc
                                if(timeQ1<=q1&&Q1.get(0).getQ1TimesQuanta()>=Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex())
                                        && Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex())==timeQ1
                                        &&Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex())>=0 && Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex())<=q1) {
                                    int indexCPUIO=Q1.get(0).getIndex()+1;
                                    //Keep working until reach last index of cpuIOburst
                                    if(indexCPUIO < Q1.get(0).getCPUAndIOBust().size()) {
                                        Q1.get(0).setIndex(Q1.get(0).getIndex()+1);
                                        Pq1InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        //Update arrival time and queue and add to IO
                                        Q1.get(0).callQ1TimesQuanta(q1);
                                        Q1.get(0).setInQueue(1);
                                        Q1.get(0).setArrivalTime(cycle+Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex()));
                                        Q1.get(0).setIndex(Q1.get(0).getIndex()+1);
                                        IO.add(Q1.get(0));
                                        ArrivalQueue.add(Q1.get(0));
                                        Q1.remove(0);

                                        timeQ1=1;
                                        continue;
                                    }else {
                                        //When we pass all bursts add to finish queue and remove from q1
                                        Pq1InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        finishQueue.add(Q1.get(0));
                                        Q1.remove(0);

                                        timeQ1=1;
                                        continue;
                                    }

                                 //Mid Case when timeQ1 counter equals q1
                                }else if(timeQ1==q1 && Q1.get(0).getQ1TimesQuanta()>=0 && Q1.get(0).getCPUAndIOBust().get(Q1.get(0).getIndex())>q1) {
                                    cpuRunningProcess=-1;
                                    Pq1InCPU =false;
                                    to.add(indexChart, cycle);
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                    Q1.get(0).setQ1TimesQuanta(Q1.get(0).getQ1TimesQuanta()-q1);//keep decreasing q1 times

                                    int indexCPUBu=Q1.get(0).getIndex();
                                    Q1.get(0).getCPUAndIOBust().set(indexCPUBu,(Q1.get(0).getCPUAndIOBust().get(indexCPUBu)-(timeQ1)));

                                    process temp =Q1.get(0);
                                    indexChart++;
                                    Q1.remove(0);
                                    //if quanta times hasn't finished stay in q1
                                    if(temp.getQ1TimesQuanta()>0) {
                                        Q1.add(temp);
                                    }
                                    //else go to q2
                                    else {
                                        temp.callQ2TimesQuanta(q2);
                                        temp.setInQueue(2);
                                        Q2.add(temp);
                                    }
                                    timeQ1=1;
                                    continue;
                                }
                                //else process is waiting
                                else
                                {
                                    timeQ1=timeQ1+1;
                                    for(int r=1;r<Q1.size();r++) {
                                        Q1.get(r).setWaitingTime(Q1.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q2.size();r++) {
                                        Q2.get(r).setWaitingTime(Q2.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q3.size();r++) {
                                        Q3.get(r).setWaitingTime(Q3.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q4.size();r++) {
                                        Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                    }
                                }
                            }
                        }
                        //when q1 empty work in q2
                        if(Q2.size()!=0 && Q1.size()==0) {
                            //Case no process in cpu
                            if(cpuRunningProcess == -1) {
                                System.out.println("Q2");
                                flagQ2In=true;
                                timeQ2=1;
                                Pq2InCPU =true;
                                cpuRunningProcess=Q2.get(0).getID();
                                whichProcces.add(indexChart, cpuRunningProcess);
                                from.add(indexChart, cycle);
                                //calculate waiting time for all queues
                                for(int r=1;r<Q2.size();r++) {
                                    Q2.get(r).setWaitingTime(Q2.get(r).getWaitingTime()+1);
                                }
                                for(int r=0;r<Q3.size();r++) {
                                    Q3.get(r).setWaitingTime(Q3.get(r).getWaitingTime()+1);
                                }
                                for(int r=0;r<Q4.size();r++) {
                                    Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                }

                            }else {
                                //cases for cpuburst < given quanta and  quantaTimes and equal time in Q2 etc
                                if(timeQ2<=q2&&Q2.get(0).getQ2TimesQuanta()>=Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())
                                        && (Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())==timeQ2 || Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())==0)
                                        &&Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())>=0 && Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())<=q2) {

                                    int indexCPUIO=Q2.get(0).getIndex()+1;
                                    //Keep working until reach last index of cpuIOburst
                                    if(indexCPUIO < Q2.get(0).getCPUAndIOBust().size()) {
                                        Q2.get(0).setIndex(Q2.get(0).getIndex()+1);
                                        Pq2InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        flagQ2In=false;
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        //Update arrival time and queue and add to IO
                                        Q2.get(0).callQ2TimesQuanta(q2);
                                        Q2.get(0).setInQueue(2);
                                        Q2.get(0).setArrivalTime(cycle+Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex()));
                                        Q2.get(0).setIndex(Q2.get(0).getIndex()+1);
                                        IO.add(Q2.get(0));
                                        ArrivalQueue.add(Q2.get(0));
                                        Q2.remove(0);

                                        timeQ2=1;
                                        continue;
                                    }else {
                                        //When we pass all bursts add to finish queue and remove from q2
                                        Pq2InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        flagQ2In=false;
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));

                                        indexChart++;
                                        finishQueue.add(Q2.get(0));
                                        Q2.remove(0);

                                        timeQ2=1;
                                        continue;
                                    }
                                    //Mid Case when timeQ2 counter equals q2
                                }else if(timeQ2==q2 && Q2.get(0).getQ2TimesQuanta()>=0 && Q2.get(0).getCPUAndIOBust().get(Q2.get(0).getIndex())>q2) {
                                    cpuRunningProcess=-1;
                                    Pq2InCPU =false;
                                    to.add(indexChart, cycle);
                                    flagQ2In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));

                                    Q2.get(0).setQ2TimesQuanta(Q2.get(0).getQ2TimesQuanta()-q2);

                                    int indexCPUBu=Q2.get(0).getIndex();
                                    Q2.get(0).getCPUAndIOBust().set(indexCPUBu,(Q2.get(0).getCPUAndIOBust().get(indexCPUBu)-(timeQ2)));

                                    process temp =Q2.get(0);
                                    indexChart++;
                                    Q2.remove(0);
                                    //if quanta times hasn't finished stay in q2
                                    if(temp.getQ2TimesQuanta()>0) {
                                        Q2.add(temp);
                                    }
                                    //else go to q3
                                    else {
                                        temp.setQ3Times(3);
                                        temp.setInQueue(3);
                                        Q3.add(temp);
                                    }
                                    timeQ2=1;
                                    continue;
                                }
                                //else process is waiting
                                else{
                                    timeQ2=timeQ2+1;
                                    for(int r=1;r<Q2.size();r++) {
                                        Q2.get(r).setWaitingTime(Q2.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q3.size();r++) {
                                        Q3.get(r).setWaitingTime(Q3.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q4.size();r++) {
                                        Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                    }
                                }
                            }
                        }//when q1 and q2 empty work in q3
                        if( Q3.size()!=0 && Q2.size()==0 && Q1.size()==0) {
                            //pass in all Q3 elements
                            for(int r=0;r<Q3.size();r++) {
                                if(!Q3.get(r).isCalc()) {
                                    Q3.get(r).setCalc(true);
                                    //Set the guess time depending on the equation
                                    Q3.get(r).setGuessTime(alfa*Q3.get(r).getCPUAndIOBust().get(Q3.get(r).getIndex())+(1-alfa)*Q3.get(r).getGuessTime());
                                    System.out.println("ID: "+Q3.get(r).getID()+" GuessTime:"+Q3.get(r).getGuessTime());
                                }
                            }
                            int Q3processInCpu=-1;
                            int index=-1;
                            double min=Q3.get(0).getGuessTime();
                            Q3processInCpu=Q3.get(0).getID();

                            Pq3InCPU=true;
                            cpuRunningProcess=Q3processInCpu;
                            index=0;
                            glIndex=index;
                            flagQ3In=true;
                            //loop to decide the index of minimum guess time process
                            for(int r=0;r<Q3.size();r++) {
                                if(Q3.get(r).getGuessTime()<min) {
                                    min=Q3.get(r).getGuessTime();
                                    Q3processInCpu=Q3.get(r).getID();
                                    Pq3InCPU=true;
                                    cpuRunningProcess=Q3processInCpu;
                                    index=r;
                                    glIndex=index;
                                }
                            }
                          //flag to check another process with less time came
                            if(firstINQ3==true) {
                                System.out.println("Q3");
                                whichProcces.add(indexChart, cpuRunningProcess);
                                from.add(indexChart, cycle);
                                oldQ3processInCpu=Q3processInCpu;
                                oldIndex=index;
                                firstINQ3=false;
                            }
                            if(oldQ3processInCpu==Q3processInCpu) {
                                if(Q3.get(index).getCPUAndIOBust().get(Q3.get(index).getIndex())>0) {
                                    Q3.get(index).getCPUAndIOBust().set(Q3.get(index).getIndex(),(Q3.get(index).getCPUAndIOBust().get(Q3.get(index).getIndex())-(1)));
                                    for(int r=0;r<Q3.size();r++) {
                                        if(r!=index)
                                            Q3.get(r).setWaitingTime(Q3.get(r).getWaitingTime()+1);
                                    }
                                    for(int r=0;r<Q4.size();r++) {
                                        Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                    }
                                }else {
                                    int indexCPUIO=Q3.get(index).getIndex()+1;

                                    if(indexCPUIO < Q3.get(index).getCPUAndIOBust().size()) {
                                        Q3.get(index).setIndex(Q3.get(index).getIndex()+1);
                                        Pq3InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        flagQ3In=false;
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        Q3.get(index).setCalc(false);
                                        Q3.get(index).setQ3Times(3);
                                        Q3.get(index).setInQueue(3);
                                        Q3.get(index).setArrivalTime(cycle+Q3.get(index).getCPUAndIOBust().get(Q3.get(index).getIndex()));
                                        Q3.get(index).setIndex(Q3.get(index).getIndex()+1);
                                        IO.add(Q3.get(index));
                                        ArrivalQueue.add(Q3.get(index));
                                        Q3.remove(index);
                                        firstINQ3=true;
                                        continue;
                                    }else {
                                        Pq3InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        flagQ3In=false;
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        finishQueue.add(Q3.get(index));
                                        Q3.remove(index);
                                        firstINQ3=true;
                                        continue;
                                    }

                                }
                            }else {
                                if(Q3.get(oldIndex).getQ3Times()>0) {
                                    Q3.get(oldIndex).setQ3Times(Q3.get(oldIndex).getQ3Times()-1);

                                    if(Q3.get(oldIndex).getCPUAndIOBust().get(Q3.get(oldIndex).getIndex())==0) {
                                        int indexCPUIO=Q3.get(oldIndex).getIndex()+1;

                                        if(indexCPUIO < Q3.get(oldIndex).getCPUAndIOBust().size()) {
                                            Q3.get(oldIndex).setIndex(Q3.get(oldIndex).getIndex()+1);
                                            Pq3InCPU =false;
                                            cpuRunningProcess=-1;
                                            to.add(indexChart, cycle);
                                            flagQ3In=false;
                                            System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                            indexChart++;

                                            Q3.get(oldIndex).setQ3Times(3);
                                            Q3.get(oldIndex).setInQueue(3);
                                            Q3.get(oldIndex).setArrivalTime(cycle+Q3.get(oldIndex).getCPUAndIOBust().get(Q3.get(oldIndex).getIndex()));
                                            Q3.get(oldIndex).setIndex(Q3.get(oldIndex).getIndex()+1);
                                            Q3.get(oldIndex).setCalc(false);
                                            IO.add(Q3.get(oldIndex));
                                            ArrivalQueue.add(Q3.get(oldIndex));
                                            Q3.remove(oldIndex);
                                            firstINQ3=true;
                                            continue;
                                        }else {
                                            Pq3InCPU =false;
                                            cpuRunningProcess=-1;
                                            to.add(indexChart, cycle);
                                            flagQ3In=false;
                                            System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                            indexChart++;
                                            finishQueue.add(Q3.get(oldIndex));
                                            Q3.remove(oldIndex);
                                            firstINQ3=true;
                                            continue;
                                        }

                                    }else {
                                        Pq3InCPU =false;
                                        cpuRunningProcess=-1;
                                        to.add(indexChart, cycle);
                                        flagQ3In=false;
                                        System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                        indexChart++;
                                        firstINQ3=true;
                                        continue;
                                    }

                                }else {
                                    process temp =Q3.get(oldIndex);

                                    Q3.remove(oldIndex);
                                    to.add(indexChart, cycle);
                                    flagQ3In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));

                                    indexChart++;
                                    temp.setInQueue(4);
                                    Q4.add(temp);
                                    firstINQ3=true;
                                    continue;
                                }
                            }
                        }
                        //if All queues empty except q4 work on it
                        if( Q4.size()!=0 && Q3.size()==0 && Q2.size()==0 && Q1.size()==0) {
                            //To check if the process in q4
                            if(firstINQ4==true) {
                                System.out.println("Q4");
                                cpuRunningProcess=Q4.get(0).getID();
                                whichProcces.add(indexChart, cpuRunningProcess);
                                from.add(indexChart, cycle);
                                firstINQ4=false;
                                Pq4InCPU =true;
                            }
                            //if there is still remaining burst time
                            if(Q4.get(0).getCPUAndIOBust().get(Q4.get(0).getIndex())>0) {
                                //change index of CPUIO burst
                                Q4.get(0).getCPUAndIOBust().set(Q4.get(0).getIndex(),(Q4.get(0).getCPUAndIOBust().get(Q4.get(0).getIndex())-(1)));
                              //Keep increasing waiting time
                                for(int r=1;r<Q4.size();r++) {
                                    Q4.get(r).setWaitingTime(Q4.get(r).getWaitingTime()+1);
                                }
                            }
                            else {
                                //keep assigning cpu io index
                                int indexCPUIO=Q4.get(0).getIndex()+1;
                                //if we still haven't reached last IO index
                                if(indexCPUIO < Q4.get(0).getCPUAndIOBust().size()) {
                                    Q4.get(0).setIndex(Q4.get(0).getIndex()+1);
                                    Pq4InCPU =false;
                                    cpuRunningProcess=-1;
                                    to.add(indexChart, cycle);
                                    flagQ4In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                    indexChart++;
                                    //Update arrival time and queue and add to IO
                                    Q4.get(0).setInQueue(4);
                                    Q4.get(0).setArrivalTime(cycle+Q4.get(0).getCPUAndIOBust().get(Q4.get(0).getIndex()));
                                    Q4.get(0).setIndex(Q4.get(0).getIndex()+1);
                                    IO.add(Q4.get(0));
                                    ArrivalQueue.add(Q4.get(0));
                                    Q4.remove(0);
                                    firstINQ4=true;
                                    continue;
                                }
                                //If done add to finish queue and remove from q1
                                else {
                                    Pq4InCPU =false;
                                    cpuRunningProcess=-1;
                                    to.add(indexChart, cycle);
                                    flagQ4In=false;
                                    System.out.println("process:"+whichProcces.get(indexChart)+" From:"+from.get(indexChart)+" To:"+to.get(indexChart));
                                    indexChart++;
                                    finishQueue.add(Q4.get(0));
                                    Q4.remove(0);
                                    firstINQ4=true;
                                    continue;
                                }
                            }


                        }
                        //Condition when reach the user pause time
                        if(pauseIntt==cycle) {
                            //print all Processes In CPU
                            if(cpuRunningProcess!=-1) {
                                info=info+"The Processes In CPU: "+ cpuRunningProcess+"\n-------------------------------\n\n";

                            }else
                                info=info+"There is no Processes In CPU \n";
                            info = info+"The Processes In Q1:\n";
                            //print all Processes in queue1
                            for(int r=0;r<Q1.size();r++) {
                                info=info+"Process: "+Q1.get(r).getID()+"\n";
                            }
                            //print all Processes in queue2
                            info = info+"The Processes In Q2:\n";
                            for(int r=0;r<Q2.size();r++) {
                                info=info+"Process: "+Q2.get(r).getID()+"\n";
                            }
                            //print all Processes in queue3
                            info = info+"The Processes In Q3:\n";
                            for(int r=0;r<Q3.size();r++) {
                                info=info+"Process: "+Q3.get(r).getID()+"\n";
                            }
                            //print all Processes in queue4
                            info = info+"The Processes In Q4:\n";
                            for(int r=0;r<Q4.size();r++) {
                                info=info+"Process: "+Q4.get(r).getID()+"\n";
                            }
                            //print all Processes in IO
                            info = info+"\n-------------------------------\nThe Processes In IO:\n";
                            for(int r=0;r<IO.size();r++) {
                                info=info+"Process: "+IO.get(r).getID()+"\n";
                            }
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                        cycle+=1;


                    }
                    tA1.setText(info);
                }
        );

        HBox hBox2 = new HBox(120);
        Label lab1 = new Label("Multilevel Feedback Queue");
        lab1.setTextFill(Color.BLACK);
        lab1.setFont(myfont2);
        lab1.setPrefSize(550,80);
        lab1.setAlignment(Pos.CENTER);




        hBox2.getChildren().addAll(lab1, tA1);


        BorderPane.setAlignment(lab1,Pos.TOP_CENTER);
        pane.setTop(lab1);
        pane.setCenter(tA1);
        hBox1.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hBox1,Pos.BOTTOM_CENTER);
        pane.setBottom(hBox1);


        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        button3.setOnAction(e->{
            stage.close();
            stage6();
        });
    }
    public void stage6() {

        BorderPane pane = new BorderPane();
        Font myfont = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font myfont2 = Font.font("Time new Roman", FontWeight.BOLD, FontPosture.REGULAR, 40);
        pane.setPadding(new Insets(30));
        HBox hBox1 = new HBox(120);
        Label l1 = new Label("  Cpu utilization");
        l1.setFont(myfont);
        l1.setTextFill(Color.BLACK);
        l1.setPrefSize(400,80);
        TextField t1 = new TextField();

        t1.setPrefSize(500,20);
        t1.setFont(new Font("Arial",50));
        double res =(double)(cycle-ideal)/cycle;
        t1.setText(res+"");
        Label l2 = new Label("   Average \nwaiting time");
        l2.setFont(myfont);
        l2.setTextFill(Color.BLACK);
        l2.setPrefSize(350,80);
        TextField t2 = new TextField();
        double sumWait=0;
        int numOfProc=0;
        for(int i=0;i<processesInSystem.size();i++) {

            sumWait+=processesInSystem.get(i).getWaitingTime();
            numOfProc+=1;
        }
        res=sumWait/(double)numOfProc;
        t2.setText(""+res);
        t2.setPrefSize(500,20);
        t2.setFont(new Font("Arial",50));
        hBox1.setLayoutX(100);
        hBox1.setLayoutY(50);
        hBox1.setPrefSize(15,20);
        hBox1.getChildren().addAll(l1,t1,l2, t2);

        Label lab1 = new Label("Gantt Chart");
        lab1.setTextFill(Color.BLACK);
        lab1.setFont(myfont2);
        lab1.setPrefSize(550,80);
        lab1.setAlignment(Pos.CENTER);

        TextArea tA1 = new TextArea();
        tA1.setPrefSize(400,500);
        tA1.setFont(myfont);
        String ganttChart="";
        int counter=1;
        int brevFrom=-1;
        int brevTo=-1;
        for(int r=0;r<=cycle;r++)//pass all whichProcces elements
            for(int y=0;y<whichProcces.size();y++) {
                if(from.get(y)==r) {//check if (from) equals the cycle
                    brevTo=from.get(y);
                    if(whichProcces.get(y)!=-1) {//if there's processes
                        if(brevTo!=brevFrom)//if to and from don't equal each other print normally
                            info = info+"("+from.get(y)+") " +"( Process:"+whichProcces.get(y)+") ";
                        else//else just print process number
                            info = info+"( Process:"+whichProcces.get(y)+") ";
                    }else {//no process so print ideal
                        if(brevTo!=brevFrom)
                            info = info+"("+from.get(y)+") " +"( Ideal ) ";
                        else
                            info = info+"( Ideal ) ";
                    }
                }else if(to.get(y)==r) {////check if (to) equals the cycle
                    brevFrom=to.get(y);
                    if(brevTo!=brevFrom)//if to and from don't equal each other print normally
                        info = info + "("+to.get(y)+") \r";
                    else//else just print space
                        info = info +"\r";
                    if(counter%6==0) {//new line for every 6 processes
                        info = info+"\n"+"("+to.get(y)+")";
                    }
                    counter+=1;
                }

            }
        tA1.setText(info);
        BorderPane.setAlignment(lab1,Pos.TOP_CENTER);
        pane.setTop(lab1);
        pane.setCenter(tA1);
        hBox1.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(hBox1,Pos.BOTTOM_CENTER);
        pane.setBottom(hBox1);


        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Hello To my Project!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        launch();

    }
    //function that assign all our random values to the file
    public static void WorkloadGenerator(PrintWriter WorkloadGe,int[][] processes, int PID ,int maxArrtime,int maxNumCPUBurst,int minIO,int maxIO,int minCPU,int maxCPU) {
        int ArrivalTime = randomNum(maxArrtime,0);
        int NumOfCPUBurst = randomNum(maxNumCPUBurst,1);
        processes[PID][0]=PID;
        processes[PID][1]=ArrivalTime;
        int flagBurse=0;
        int numOfColums=NumOfCPUBurst+(NumOfCPUBurst-1)+2;
        for(int p=2;p<numOfColums;p++) {
            if(flagBurse==0) {//flag indicates its cpu burst
                processes[PID][p]=randomNum(maxCPU,minCPU);
                flagBurse=1;
            }
            else if (flagBurse==1) {//flag indicates its IO burst
                processes[PID][p]=randomNum(maxIO,minIO);
                flagBurse=0;
            }
        }
        for(int y=0;y<numOfColums;y++) {
            System.out.print(processes[PID][y]+" ");
        }
        for(int y=0;y<numOfColums;y++) {//print all info in file
            WorkloadGe.print(processes[PID][y]+" ");
        }
        System.out.println("");
        WorkloadGe.println("");
    }
    //generate random number depending on given range
    public static int randomNum(int Max,int Min) {
        int max = Max;
        int min = Min;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
}

