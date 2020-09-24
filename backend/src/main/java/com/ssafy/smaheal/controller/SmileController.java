package com.ssafy.smaheal.controller;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import com.ssafy.smaheal.model.Smile;
import com.ssafy.smaheal.repository.SmileRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smile")
public class SmileController {
    
    @Autowired
    private SmileRepository smileRepository;
    public static List camList = new LinkedList<>();
    public static List selfList = new LinkedList<>();
    public static List textList = new LinkedList<>();

    @GetMapping("/cameraOn")
    @ApiOperation(value = "웹캠 on")
    public Object cameraOn() throws SQLException, IOException {
        try {
            System.out.println("Camera on Python Call");
            String[] command = new String[4];
            command[0] = "python";
            // 경로 확인
            command[1] = System.getProperty("user.home") + "/s03p23b108/backend/cameraOn.py";
            command[2] = System.getProperty("user.home") + "/s03p23b108/backend/files/haarcascade_frontalface_default.xml";
            command[3] = System.getProperty("user.home") + "/s03p23b108/backend/files/emotion_model.hdf5";
            try {
                execPython(command);
            } catch (Exception e) {
                return new ResponseEntity<>(System.getProperty("user.dir"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(camList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(System.getProperty("user.dir"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/regist")
    @ApiOperation(value = "웃음 기부 등록")
    public Object registDonation(@RequestBody Smile request) throws SQLException, IOException {
        try {
            Smile smile = new Smile();
            smile.setUser_id(request.getUser_id());
            smile.setDonationid(request.getDonationid());
            smile.setPhoto(request.getPhoto());
            smile.setSmileper(request.getSmileper());
            smile.setComment(request.getComment());
            smile.setAgreement(request.getAgreement());
            smileRepository.save(smile);
            return new ResponseEntity<>(smile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/cameraOff")
    @ApiOperation(value = "웹캠 off")
    public Object cameraOff() throws SQLException, IOException, ExecuteException {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM Python.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PostMapping("/smileCheck")
    @ApiOperation("스마일 체크")
    public Object smileCheck(@RequestBody String filename) throws SQLException, IOException {
        try {
            String tempFileName = createFile(filename);
            System.out.println("SmileCheck Python Call");
            String[] command = new String[3];
            command[0] = "python";
            // 경로 확인
            command[1] = System.getProperty("user.home") + "/s03p23b108/backend/imageCheck.py";
            command[2] = tempFileName;
            try {
                execPythonSmileCheck(command);
                
                return new ResponseEntity<>(selfList, HttpStatus.OK);
            } catch (Exception e) {
                return "findFail";
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void execPythonSmileCheck(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        System.out.println("output: " + outputStream.toString());

        String[] outputList = outputStream.toString().split("\n");
        int len = outputList.length;
        String filename = outputList[len - 1].trim();;
        String emotion = outputList[len - 2].trim();
        String happyPer = outputList[len - 3].trim();
        selfList.clear();
        selfList.add(filename);
        selfList.add(emotion);
        selfList.add(happyPer);
    }

    public static void execPython(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        
        String[] outputList = outputStream.toString().split("\n");
        int len = outputList.length;
        if(outputList[len - 1].length() < 6) {
            String fileName = outputList[len - 2].trim();
            String percent = outputList[len - 1].trim();
            camList.clear();
            camList.add(fileName);
            camList.add(percent);
            System.out.println(fileName);
            System.out.println(percent);
        } else {
            camList.add("cancel");
        }
        // System.out.println("output: " + outputStream.toString().split("\n")[0]);
    }

    public static String createFile(String filename) throws FileNotFoundException {
        long time = System.currentTimeMillis();
        String name = Long.toString(time);
        // 경로 정해주기
        File file = new File("./var/lib/jenkins/workspace/Gitlab/frontend/public/textFiles/" + name);
        String str = filename;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
    
    @GetMapping("/textCheck")
    @ApiOperation(value = "응원메세지 감성분석")
    public Object textCheck() throws SQLException, IOException {
        try {
            System.out.println("Text Check Python Call");
            String[] command = new String[3];
            command[0] = "python";
            // 경로 확인
            command[1] = "backend/textCheck.py";
            command[2] = "wow hey";
            try {
                execPython2(command);
            } catch (Exception e) {
                System.out.println("여기서 오류");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(textList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("저기서 오류");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static void execPython2(String[] command) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        String[] outputList = outputStream.toString().split("\n");
        for(String s : outputList){
            textList.add(s);
            System.out.println(s);
        }
    }
}
